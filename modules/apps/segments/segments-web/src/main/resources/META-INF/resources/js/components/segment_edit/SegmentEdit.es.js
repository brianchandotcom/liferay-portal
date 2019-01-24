import React, {Component} from 'react';
import PropTypes from 'prop-types';
import ClayButton from '../shared/ClayButton.es';
import ClaySpinner from '../shared/ClaySpinner.es';
import debounce from 'lodash.debounce';
import ThemeContext from '../../ThemeContext.es';
import TitleEditor from '../title_editor/TitleEditor.es';
import {getPluralMessage, sub} from '../../utils/utils.es';
import {
	SOURCES,
	SUPPORTED_CONJUNCTIONS,
	SUPPORTED_OPERATORS,
	SUPPORTED_PROPERTY_TYPES
} from '../../utils/constants.es';
import {FieldArray, withFormik} from 'formik';
import ContributorBuilder from '../criteria_builder/ContributorBuilder.es';

const DEFAULT_SEGMENT_NAME = Liferay.Language.get('unnamed-segment');

class SegmentEdit extends Component {
	static contextType = ThemeContext;

	static propTypes = {
		contributors: PropTypes.arrayOf(
			PropTypes.shape(
				{
					conjunctionId: PropTypes.string,
					initialQuery: PropTypes.string,
					inputId: PropTypes.string,
					modelLabel: PropTypes.string,
					properties: PropTypes.array
				}
			)
		),
		formId: PropTypes.string,
		handleBlur: PropTypes.func,
		handleChange: PropTypes.func,
		initialMembersCount: PropTypes.number,
		initialSegmentActive: PropTypes.bool,
		initialSegmentName: PropTypes.string,
		locale: PropTypes.string.isRequired,
		portletNamespace: PropTypes.string,
		previewMembersURL: PropTypes.string,
		propertyGroups: PropTypes.array,
		redirect: PropTypes.string,
		requestMembersCountURL: PropTypes.string,
		setValues: PropTypes.func,
		source: PropTypes.string,
		values: PropTypes.object
	};

	static defaultProps = {
		initialMembersCount: 0,
		initialSegmentActive: true,
		initialSegmentName: DEFAULT_SEGMENT_NAME,
		portletNamespace: ''
	};

	state = {
		membersCount: this.props.initialMembersCount
	};

	constructor(props) {
		super(props);

		this._debouncedFetchMembersCount = debounce(
			this._fetchMembersCount,
			500
		);
	}

	_fetchMembersCount = () => {
		const formElement = document.getElementById(this.props.formId);

		const formData = new FormData(formElement);

		fetch(
			this.props.requestMembersCountURL,
			{
				body: formData,
				method: 'POST'
			}
		).then(
			response => response.json()
		).then(
			membersCount => {
				this.setState(
					{
						membersCount,
						membersCountLoading: false
					}
				);
			}
		).catch(
			() => {
				this.setState({membersCountLoading: false});

				Liferay.Util.openToast(
					{
						message: Liferay.Language.get('an-unexpected-error-occurred'),
						title: Liferay.Language.get('error'),
						type: 'danger'
					}
				);
			}
		);
	};

	_handleQueryChange = () => {
		this.setState({membersCountLoading: true});

		this._debouncedFetchMembersCount();
	};

	_handleSegmentNameBlur = event => {
		const {
			handleBlur,
			setValues,
			values
		} = this.props;

		if (values.name === '') {
			setValues(
				{
					...values,
					name: DEFAULT_SEGMENT_NAME
				}
			);
		}

		handleBlur(event);
	};

	_handleSourceIconMouseOver = event => {
		const message = this.props.source === SOURCES.ASAH_FARO_BACKEND.name ?
			SOURCES.ASAH_FARO_BACKEND.label :
			SOURCES.DEFAULT.label;

		Liferay.Portal.ToolTip.show(event.currentTarget, message);
	};

	_renderContributors = () => {
		const {contributors, propertyGroups} = this.props;

		return (
			(propertyGroups && contributors) ?
				<ContributorBuilder
					initialContributors={contributors}
					onQueryChange={this._handleQueryChange}
					propertyGroups={propertyGroups}
					supportedConjunctions={SUPPORTED_CONJUNCTIONS}
					supportedOperators={SUPPORTED_OPERATORS}
					supportedPropertyTypes={SUPPORTED_PROPERTY_TYPES}
				/> :
				null
		);
	};

	_handlePreviewClick = url => () => {
		Liferay.Util.openWindow(
			{
				dialog: {
					bodyContent: `<iframe frameborder="0" width="100%" height="100%" src="${url}"></iframe>`,
					destroyOnHide: true
				},
				id: 'segment-members-dialog',
				title: sub(Liferay.Language.get('x-members'), [this.props.values.name])
			}
		);
	}

	render() {
		const {
			handleChange,
			locale,
			portletNamespace,
			previewMembersURL,
			redirect,
			source,
			values
		} = this.props;

		const {membersCount, membersCountLoading} = this.state;

		const {assetsPath} = this.context;

		return (
			<div className="segment-edit-page-root">
				<input
					name={`${portletNamespace}name`}
					type="hidden"
					value={values.name}
				/>

				<input
					name={`${portletNamespace}name_${locale}`}
					type="hidden"
					value={values.name}
				/>

				<input
					name={`${portletNamespace}key`}
					type="hidden"
					value={values.name}
				/>

				<input
					name={`${portletNamespace}active`}
					type="hidden"
					value={values.active}
				/>

				<div className="form-header">
					<div className="container-fluid container-fluid-max-xl form-header-container">
						<div className="form-header-section-left">
							<TitleEditor
								inputName="name"
								onBlur={this._handleSegmentNameBlur}
								onChange={handleChange}
								placeholder={DEFAULT_SEGMENT_NAME}
								value={values.name}
							/>

							<img
								className="source-icon"
								data-testid="source-icon"
								onMouseOver={this._handleSourceIconMouseOver}
								src={source === SOURCES.ASAH_FARO_BACKEND.name ?
									`${assetsPath}${SOURCES.ASAH_FARO_BACKEND.icon}` :
									`${assetsPath}${SOURCES.DEFAULT.icon}`
								}
							/>
						</div>

						<div className="form-header-section-right">
							<div className="btn-group mr-3">
								<div className="btn-group-item">
									<ClaySpinner
										className="mr-4"
										loading={membersCountLoading}
										size="sm"
									/>

									<div className="members-count mr-3">
										{getPluralMessage(
											Liferay.Language.get('x-member'),
											Liferay.Language.get('x-members'),
											membersCount
										)}
									</div>

									{previewMembersURL &&
										<ClayButton
											borderless
											label={Liferay.Language.get('preview-members')}
											onClick={this._handlePreviewClick(previewMembersURL)}
											size="sm"
											type="button"
										/>
									}
								</div>
							</div>

							<div className="btn-group">
								<div className="btn-group-item">
									<ClayButton
										href={redirect}
										label={Liferay.Language.get('cancel')}
										size="sm"
									/>
								</div>

								<div className="btn-group-item">
									<ClayButton
										label={Liferay.Language.get('save')}
										size="sm"
										style="primary"
										type="submit"
									/>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div className="form-body">
					<FieldArray
						name="contributors"
						render={this._renderContributors}
					/>
				</div>
			</div>
		);
	}
}

export default withFormik(
	{
		mapPropsToValues: props => (
			{
				active: props.initialSegmentActive || true,
				contributors: props.contributors || [],
				name: props.initialSegmentName || DEFAULT_SEGMENT_NAME
			}
		)
	}
)(SegmentEdit);