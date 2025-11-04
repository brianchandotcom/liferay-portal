import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import Clipboard from 'clipboard';
import Form from 'shared/components/form';
import getCN from 'classnames';
import React, {useEffect, useRef, useState} from 'react';
import {Alert} from 'shared/types';
import {createSalesforce} from 'shared/api/data-source';
import {DataSourceStatuses} from 'shared/util/constants';
import {
	getOAuthWindowErrorMessage,
	getTempCredentials
} from 'shared/util/oauth';
import {OAUTH_CALLBACK_URL} from 'shared/util/oauth';
import {Routes} from 'shared/util/router';
import {sequence} from 'shared/util/promise';
import {sub} from 'shared/util/lang';
import {Text} from '@clayui/core';
import {useParams} from 'react-router-dom';
import {
	validateRequired,
	validateSalesforceDomain
} from 'shared/util/validators';

const ConnectSalesforceAuth = ({addAlert, onCancel, onSubmit}) => {
	const {groupId} = useParams();

	const [isUrlCopied, setIsUrlCopied] = useState(false);
	const [copyTitle, setCopyTitle] = useState(
		Liferay.Language.get('click-to-copy')
	);
	const [inlineAlert, setInlineAlert] = useState(null);
	const [showClientId, setShowClientId] = useState(false);
	const [showClientSecret, setShowClientSecret] = useState(false);

	const _formRef = useRef(null);

	useEffect(() => {
		const _clipboard = new Clipboard('[data-clipboard-text]');

		_clipboard.on('success', event => {
			setCopyTitle(Liferay.Language.get('copied'));

			addAlert({
				alertType: Alert.Types.Success,
				message: Liferay.Language.get('copied')
			});

			event.clearSelection();
		});

		return () => _clipboard.destroy();
	}, []);

	return (
		<Form
			initialValues={{
				clientId: '',
				clientSecret: '',
				salesForceDataSource: ''
			}}
			onSubmit={values => {
				const {setSubmitting} = _formRef.current;

				const authWindow = open(
					Routes.LOADING,
					'authorizeWindow',
					'width=400,height=500'
				);

				return getTempCredentials({
					authWindow,
					baseUrl: values.salesForceDataSource,
					consumerKey: values.clientId,
					consumerSecret: values.clientSecret,
					groupId,
					type: 'salesforce'
				} as any)
					.then(async tempCredentials => {
						if (tempCredentials) {
							createSalesforce({
								accountsConfiguration: {
									enableAllAccounts: false
								},
								contactsConfiguration: {
									enableAllContacts: true,
									enableAllLeads: true
								},
								credentials: tempCredentials,
								groupId,
								name: Liferay.Language.get('salesforce'),
								status: DataSourceStatuses.Active,
								url: values.salesForceDataSource
							} as any)
								.then(() => {
									addAlert({
										alertType: Alert.Types.Success,
										message: Liferay.Language.get(
											'token-authenticated-successfully'
										) // TODO: Define correct message
									});

									onSubmit();
								})
								.catch(err => {
									addAlert({
										alertType: Alert.Types.Error,
										message: err.message
									});
								})
								.finally(() => {
									setSubmitting(false);
								});
						}
					})
					.catch(err => {
						addAlert({
							alertType: Alert.Types.Error,
							message: getOAuthWindowErrorMessage(err),
							timeout: false
						});

						setSubmitting(false);
					});
			}}
			ref={_formRef}
		>
			{({handleSubmit, isSubmitting, isValid, values}) => (
				<Form.Form
					className='oauth-form-root'
					onSubmit={event => {
						if (!isValid) {
							if (
								!values.salesForceDataSource &&
								!values.clientId &&
								!values.clientSecret
							) {
								setInlineAlert(
									Liferay.Language.get(
										'please-enter-a-value-before-continuing'
									)
								);
							} else {
								setInlineAlert(null);
							}
						}

						handleSubmit(event);
					}}
				>
					{inlineAlert && (
						<ClayAlert
							displayType='danger'
							title={Liferay.Language.get('error')}
						>
							{inlineAlert}
						</ClayAlert>
					)}

					<ClayForm.Group
						className={getCN({
							'has-success': isUrlCopied
						})}
					>
						<label htmlFor='callbackURL'>
							<Text weight='semi-bold'>
								{Liferay.Language.get('target-url')}
							</Text>
							<div>
								<Text
									color='secondary'
									size={3}
									weight='normal'
								>
									{Liferay.Language.get(
										'this-is-analytics-cloud-callback-url-salesforce-will-redirect-to-after-a-user-authorizes-the-connection'
									)}
								</Text>
							</div>
						</label>

						<ClayInput.Group>
							<ClayInput.GroupItem prepend>
								<ClayInput
									id='callbackURL'
									insetAfter
									name='callbackURL'
									readOnly={!isUrlCopied}
									type='text'
									value={OAUTH_CALLBACK_URL}
								/>
							</ClayInput.GroupItem>
							<ClayInput.GroupItem append shrink>
								<ClayButton
									aria-label={copyTitle}
									data-clipboard-text={OAUTH_CALLBACK_URL}
									displayType={
										isUrlCopied ? 'success' : 'secondary'
									}
									onClick={() => setIsUrlCopied(true)}
									outline
									title={copyTitle}
								>
									<ClayIcon
										symbol={isUrlCopied ? 'check' : 'copy'}
									/>
								</ClayButton>
							</ClayInput.GroupItem>
						</ClayInput.Group>
					</ClayForm.Group>

					<Form.Input
						className='mb-3'
						id='salesForceDataSource'
						label={Liferay.Language.get('salesforce-data-source')}
						name='salesForceDataSource'
						required
						type='text'
						validate={sequence([
							value =>
								validateRequired(
									value,
									sub(
										Liferay.Language.get(
											'the-x-field-is-required'
										),
										[
											Liferay.Language.get(
												'salesforce-data-source'
											)
										]
									) as string
								),
							validateSalesforceDomain
						])}
					/>

					<Form.Input
						className='mb-3'
						contentAfter={
							<ClayButton
								aria-label={
									showClientId
										? Liferay.Language.get('view')
										: Liferay.Language.get('hidden')
								}
								displayType='secondary'
								onClick={() => setShowClientId(!showClientId)}
							>
								<ClayIcon
									symbol={showClientId ? 'view' : 'hidden'}
								/>
							</ClayButton>
						}
						id='clientId'
						label={Liferay.Language.get('consumer-key-client-id')}
						name='clientId'
						required
						type={showClientId ? 'text' : 'password'}
						validate={value =>
							validateRequired(
								value,
								sub(
									Liferay.Language.get(
										'the-x-field-is-required'
									),
									[
										Liferay.Language.get(
											'consumer-key-client-id'
										)
									]
								) as string
							)
						}
					/>

					<Form.Input
						className='mb-4'
						contentAfter={
							<ClayButton
								aria-label={
									showClientSecret
										? Liferay.Language.get('view')
										: Liferay.Language.get('hidden')
								}
								displayType='secondary'
								onClick={() =>
									setShowClientSecret(!showClientSecret)
								}
							>
								<ClayIcon
									symbol={
										showClientSecret ? 'view' : 'hidden'
									}
								/>
							</ClayButton>
						}
						id='clientSecret'
						label={Liferay.Language.get(
							'consumer-secret-client-secret'
						)}
						name='clientSecret'
						required
						type={showClientSecret ? 'text' : 'password'}
						validate={value =>
							validateRequired(
								value,
								sub(
									Liferay.Language.get(
										'the-x-field-is-required'
									),
									[
										Liferay.Language.get(
											'consumer-secret-client-secret'
										)
									]
								) as string
							)
						}
					/>

					<ClayButton
						block
						disabled={isSubmitting}
						loading={isSubmitting}
						type='submit'
					>
						{Liferay.Language.get('connect')}
					</ClayButton>

					{onCancel && (
						<ClayButton
							block
							borderless
							displayType='secondary'
							onClick={onCancel}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>
					)}
				</Form.Form>
			)}
		</Form>
	);
};

export {ConnectSalesforceAuth};
