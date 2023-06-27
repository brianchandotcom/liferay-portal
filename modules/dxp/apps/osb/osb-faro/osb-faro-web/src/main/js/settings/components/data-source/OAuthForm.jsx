import autobind from 'autobind-decorator';
import ClayButton from '@clayui/button';
import ClayLink from '@clayui/link';
import Form, {
	toPromise,
	validateProtocol,
	validateRequired
} from 'shared/components/form';
import getCN from 'classnames';
import Loading, {Align} from 'shared/components/Loading';
import NavigationWarning from 'shared/components/NavigationWarning';
import React from 'react';
import Sheet from 'shared/components/Sheet';
import {addAlert} from 'shared/actions/alerts';
import {Alert} from 'shared/types';
import {compose, withHistory} from 'shared/hoc';
import {connect} from 'react-redux';
import {DataSource} from 'shared/util/records';
import {DataSourceStates, DataSourceTypes} from 'shared/util/constants';
import {get, isUndefined} from 'lodash';
import {getOAuthWindowErrorMessage} from 'shared/util/oauth';
import {getTempCredentials} from 'shared/util/oauth';
import {hasChanges} from 'shared/util/react';
import {isDataSourceValid} from 'shared/util/data-sources';
import {isOAuthErrorString, OAUTH_CALLBACK_URL} from 'shared/util/oauth';
import {Map} from 'immutable';
import {parseFromJSON} from 'shared/util/request';
import {PropTypes} from 'prop-types';
import {Routes, toRoute} from 'shared/util/router';
import {sequence} from 'shared/util/promise';
import {validateUniqueName} from 'shared/util/data-sources';

export class OAuthForm extends React.Component {
	static defaultProps = {
		authorized: false,
		defaultUrl: '',
		redirectRoute: Routes.SETTINGS_DATA_SOURCE
	};

	static propTypes = {
		addAlert: PropTypes.func.isRequired,
		authorized: PropTypes.bool,
		dataSource: PropTypes.instanceOf(DataSource),
		defaultUrl: PropTypes.string,
		groupId: PropTypes.string.isRequired,
		history: PropTypes.object.isRequired,
		id: PropTypes.string,
		instruction: PropTypes.oneOfType([
			PropTypes.array,
			PropTypes.func,
			PropTypes.string
		]),
		onSubmit: PropTypes.func.isRequired,
		redirectRoute: PropTypes.string,
		type: PropTypes.oneOf([
			DataSourceTypes.Liferay,
			DataSourceTypes.Salesforce
		]).isRequired
	};

	state = {
		credentialsModified: false,
		editing: true,
		nameModified: false,
		ownerRemoved: false
	};

	constructor(props) {
		super(props);

		const {authorized, dataSource, id} = this.props;

		const dataSourceState = get(dataSource, 'state');

		this._cachedNameValues = {};

		this._formRef = React.createRef();

		this.state = {
			...this.state,
			editing:
				authorized &&
				(!id || (dataSource && !isDataSourceValid(dataSourceState)))
		};
	}

	componentDidMount() {
		const {
			props: {authorized, dataSource, id},
			state: {editing}
		} = this;

		const dataSourceState = get(dataSource, 'state');

		this.checkCredentials(dataSourceState);

		if (id && editing && authorized) {
			this._formRef.current.validateForm();
		}
	}

	componentDidUpdate(prevProps, prevState) {
		if (hasChanges(prevProps, this.props, 'dataSource')) {
			const dataSourceState = get(this.props, ['dataSource', 'state']);

			this.checkCredentials(dataSourceState);
		}

		if (hasChanges(prevState, this.state, 'editing')) {
			this._formRef.current.validateForm();

			this.setState({
				credentialsModified: false,
				nameModified: false,
				ownerRemoved: false
			});
		}
	}

	authorize(values) {
		const {consumerKey, consumerSecret, url} = values;

		const {addAlert, groupId, type} = this.props;

		const authWindow = open(
			Routes.LOADING,
			'authorizeWindow',
			'width=400,height=500'
		);

		return getTempCredentials({
			authWindow,
			baseUrl: url,
			consumerKey,
			consumerSecret,
			groupId,
			type
		})
			.then(tempCredentials => {
				this.setState({credentialsModified: false});

				return this.save(values, tempCredentials);
			})
			.catch(err => {
				addAlert({
					alertType: Alert.Types.Error,
					message: getOAuthWindowErrorMessage(err),
					timeout: false
				});

				const {setSubmitting} = this._formRef.current;

				setSubmitting(false);
			});
	}

	checkCredentials(dataSourceState) {
		if (dataSourceState === DataSourceStates.CredentialsInvalid) {
			this.handleRemoveOwner();
		}
	}

	clearConsumerSecretInput() {
		this._formRef.current.setFieldValue('consumerSecret', '');
	}

	@autobind
	handleCancel() {
		this._formRef.current.resetForm();

		this.setState({editing: false});
	}

	/**
	 * Fires when input has been changed but before validation.
	 */
	@autobind
	handleCredentialsModified() {
		this.setState({credentialsModified: true});
	}

	@autobind
	handleNameModified(event) {
		const {name, value} = event.currentTarget;

		const {initialValues} = this._formRef.current;

		this.setState({
			nameModified: value !== initialValues[name]
		});
	}

	@autobind
	handleRemoveOwner() {
		this.setState({ownerRemoved: true});
	}

	@autobind
	handleSubmit(values) {
		const {credentialsModified, ownerRemoved} = this.state;

		if (credentialsModified || ownerRemoved) {
			return this.authorize(values);
		}

		return this.save(values);
	}

	@autobind
	hasNavigationWarning() {
		const {
			credentialsModified,
			editing,
			nameModified,
			ownerRemoved
		} = this.state;

		return (credentialsModified || nameModified || ownerRemoved) && editing;
	}

	@autobind
	save(values, tempCredentials) {
		const {
			addAlert,
			groupId,
			history,
			onSubmit,
			redirectRoute
		} = this.props;

		const {setSubmitting} = this._formRef.current;

		onSubmit({...values, tempCredentials})
			.then(({payload: {id, state}}) => {
				this.setState({editing: false});

				setSubmitting(false);

				if (isDataSourceValid(state)) {
					addAlert({
						alertType: Alert.Types.Success,
						message: Liferay.Language.get(
							'data-source-credentials-saved'
						)
					});
				} else {
					addAlert({
						alertType: Alert.Types.Error,
						message: Liferay.Language.get('authentication-error'),
						timeout: false
					});
				}

				if (tempCredentials) {
					history.push(
						toRoute(redirectRoute, {
							groupId,
							id
						})
					);
				}
			})
			.catch(({message}) => {
				const jsonMessage = parseFromJSON(message);

				const oAuthError = isOAuthErrorString(
					get(jsonMessage, 'error')
				);

				addAlert({
					alertType: Alert.Types.Error,
					message: oAuthError
						? Liferay.Language.get(
								'there-was-a-problem-authenticating,-please-check-your-credentials-and-try-again'
						  )
						: Liferay.Language.get('error'),
					timeout: false
				});

				setSubmitting(false);

				if (!jsonMessage || oAuthError) {
					this.clearConsumerSecretInput();
				}
			});
	}

	@autobind
	handleToggleEditing() {
		this.setState({editing: true});
	}

	@autobind
	handleValidateName(value) {
		const {groupId} = this.props;

		let error = '';

		if (isUndefined(this._cachedNameValues[value])) {
			error = validateUniqueName({groupId, value});

			this._cachedNameValues[value] = error;
		} else {
			error = this._cachedNameValues[value];
		}

		return toPromise(error);
	}

	renderNavigationButtons({isSubmitting: submitting, isValid, isValidating}) {
		const {
			props: {groupId, id},
			state: {credentialsModified, editing, nameModified, ownerRemoved}
		} = this;

		const valid = isValid && !isValidating;

		if (!editing) {
			return (
				<ClayButton
					className='button-root edit'
					displayType='secondary'
					key='EDIT'
					onClick={this.handleToggleEditing}
				>
					{Liferay.Language.get('edit')}
				</ClayButton>
			);
		} else {
			return (
				<>
					<ClayButton
						className='button-root submit'
						disabled={
							submitting ||
							!valid ||
							(!credentialsModified &&
								!nameModified &&
								!ownerRemoved)
						}
						key='SUBMIT'
						type='submit'
					>
						{submitting && <Loading align={Align.Left} />}

						{Liferay.Language.get('authorize-&-save')}
					</ClayButton>

					{id ? (
						<ClayButton
							className='button-root'
							disabled={submitting}
							displayType='secondary'
							onClick={this.handleCancel}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>
					) : (
						<ClayLink
							button
							className='button-root'
							displayType='secondary'
							href={toRoute(Routes.SETTINGS_DATA_SOURCE_LIST, {
								groupId
							})}
						>
							{Liferay.Language.get('cancel')}
						</ClayLink>
					)}
				</>
			);
		}
	}

	render() {
		const {
			props: {authorized, dataSource, defaultUrl, id, instruction},
			state: {editing, nameModified, ownerRemoved}
		} = this;

		const dataSourceState = get(dataSource, 'state');

		const dataSourceCredentialsInvalid =
			dataSourceState === DataSourceStates.CredentialsInvalid ||
			dataSourceState === DataSourceStates.UrlInvalid;

		const credentialsIMap = (
			get(dataSource, 'credentials') || new Map()
		).filter(Boolean);

		const oAuthOwnerIMap =
			dataSource && dataSource.getIn(['credentials', 'oAuthOwner']);

		return (
			<Form
				initialValues={{
					consumerKey:
						credentialsIMap.get('oAuthConsumerKey', '') ||
						credentialsIMap.get('oAuthClientId', ''),
					consumerSecret:
						credentialsIMap.get('oAuthConsumerSecret', '') ||
						credentialsIMap.get('oAuthClientSecret', ''),
					dataSourceName: get(dataSource, 'name', ''),
					url: get(dataSource, 'url', defaultUrl)
				}}
				isInitialValid={id}
				onSubmit={this.handleSubmit}
				ref={this._formRef}
			>
				{({handleSubmit, isSubmitting, ...otherProps}) => (
					<Form.Form
						className='oauth-form-root'
						onSubmit={handleSubmit}
					>
						<NavigationWarning when={this.hasNavigationWarning()} />

						<Sheet.Body>
							<Sheet.Section>
								<Sheet.Text>{instruction}</Sheet.Text>

								<div className='callback-url'>
									<b>
										{Liferay.Language.get(
											'analytics-cloud-callback-url'
										)}
									</b>

									<pre key='url'>{OAUTH_CALLBACK_URL}</pre>
								</div>
							</Sheet.Section>

							<Sheet.Section>
								<Sheet.Subtitle>
									{Liferay.Language.get('data-source')}
								</Sheet.Subtitle>

								<Form.Input
									disabled={!editing || isSubmitting}
									label={Liferay.Language.get('name')}
									name='dataSourceName'
									onChange={this.handleNameModified}
									required
									validate={
										id && !nameModified
											? validateRequired
											: sequence([
													validateRequired,
													this.handleValidateName
											  ])
									}
								/>

								<Form.Input
									disabled={id || !editing || isSubmitting}
									label={Liferay.Language.get('url')}
									name='url'
									required
									validate={sequence([
										validateRequired,
										validateProtocol
									])}
								/>
							</Sheet.Section>

							<Sheet.Section>
								<Sheet.Subtitle>
									{Liferay.Language.get('client-credentials')}
								</Sheet.Subtitle>

								<Form.Input
									autoComplete='new-password'
									disabled={!editing || isSubmitting}
									label={`${Liferay.Language.get(
										'consumer-key'
									)}/${Liferay.Language.get('client-id')}`}
									name='consumerKey'
									onChange={this.handleCredentialsModified}
									required
									validate={validateRequired}
								/>

								<Form.PasswordInput
									autoComplete='new-password'
									disabled={!editing || isSubmitting}
									label={`${Liferay.Language.get(
										'consumer-secret'
									)}/${Liferay.Language.get(
										'client-secret'
									)}`}
									name='consumerSecret'
									onChange={this.handleCredentialsModified}
									required
									validate={validateRequired}
								/>
							</Sheet.Section>

							<Sheet.Section>
								<h5>{Liferay.Language.get('oauth-owner')}</h5>

								<div
									className={getCN('oauth-owner', {
										disabled: !editing || isSubmitting
									})}
								>
									{oAuthOwnerIMap && !ownerRemoved ? (
										<>
											<div className='name'>
												{oAuthOwnerIMap.get('name')}
											</div>

											<div className='email-address'>
												{oAuthOwnerIMap.get(
													'emailAddress'
												)}
											</div>

											{editing &&
												(dataSourceCredentialsInvalid ? (
													<em>
														{Liferay.Language.get(
															'authorization-token-has-expired,-please-reauthorize'
														)}
													</em>
												) : (
													<ClayButton
														className='button-root'
														disabled={isSubmitting}
														displayType='secondary'
														onClick={
															this
																.handleRemoveOwner
														}
														size='sm'
													>
														{Liferay.Language.get(
															'remove'
														)}
													</ClayButton>
												))}
										</>
									) : (
										<em>
											{Liferay.Language.get(
												'authorize-to-add-a-new-owner'
											)}
										</em>
									)}
								</div>
							</Sheet.Section>
						</Sheet.Body>

						{authorized && (
							<Sheet.Footer
								className='form-actions'
								divider={editing}
							>
								{this.renderNavigationButtons({
									isSubmitting,
									...otherProps
								})}
							</Sheet.Footer>
						)}
					</Form.Form>
				)}
			</Form>
		);
	}
}

export default compose(withHistory, connect(null, {addAlert}))(OAuthForm);
