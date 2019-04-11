import ClayButton from '../shared/ClayButton.es';
import ClaySpinner from '../shared/ClaySpinner.es';
import ClayToggle from '../shared/ClayToggle.es';
import ContributorBuilder from '../criteria_builder/ContributorBuilder.es';
import PropTypes from 'prop-types';
import React, {Component} from 'react';
import ThemeContext from '../../ThemeContext.es';
import TitleEditor from '../title_editor/TitleEditor.es';
import {debounce} from 'metal-debounce';
import {FieldArray, withFormik} from 'formik';
import {getPluralMessage, sub} from '../../utils/utils.es';
import {
	SOURCES,
	SUPPORTED_CONJUNCTIONS,
	SUPPORTED_OPERATORS,
	SUPPORTED_PROPERTY_TYPES
} from '../../utils/constants.es';

const DEFAULT_SEGMENT_NAME = Liferay.Language.get('unnamed-segment');

class SegmentEdit extends Component {
	static contextType = ThemeContext;

	static propTypes = {
		contributors: PropTypes.arrayOf(
			PropTypes.shape(
				{
					conjunctionId: PropTypes.string,
					conjunctionInputId: PropTypes.string,
					initialQuery: PropTypes.string,
					inputId: PropTypes.string,
					propertyKey: PropTypes.string
				}
			)
		),
		errors: PropTypes.object,
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
		redirect: PropTypes.string.isRequired,
		requestMembersCountURL: PropTypes.string,
		setValues: PropTypes.func,
		source: PropTypes.string,
		validateForm: PropTypes.func,
		values: PropTypes.object
	};

	static defaultProps = {
		contributors: [],
		initialMembersCount: 0,
		initialSegmentActive: true,
		portletNamespace: ''
	};

	state = {
		changesUnsaved: false,
		editing: false,
		membersCount: this.props.initialMembersCount,
		membersCountLoading: false
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
		this.setState(
			{
				changesUnsaved: true,
				membersCountLoading: true
			},
			this._debouncedFetchMembersCount
		);

	};

	_handleCriteriaEdit = () => {
		this.setState(
			{
				editing: !this.state.editing
			}
		);
	}

	_handleSegmentNameBlur = event => {
		const {
			handleBlur
		} = this.props;

		handleBlur(event);
	};

	_handleSourceIconMouseOver = event => {
		const message = this.props.source === SOURCES.ASAH_FARO_BACKEND.name ?
			SOURCES.ASAH_FARO_BACKEND.label :
			SOURCES.DEFAULT.label;

		Liferay.Portal.ToolTip.show(event.currentTarget, message);
	};

	/**
	 * Checks if every query in each contributor has a value.
	 * @return {boolean} True if none of the contributor's queries have a value.
	 */
	_isQueryEmpty = () => this.props.contributors.every(
		({initialQuery, inputId}) => {
			const input = document.getElementById(inputId);

			return input ? !input.value : !initialQuery;
		}
	);

	_renderContributors = () => {
		const {contributors, propertyGroups} = this.props;

		const {editing} = this.state;

		const emptyQuery = this._isQueryEmpty();

		return (
			(propertyGroups && contributors) ?
				<ContributorBuilder
					editing={editing}
					empty={emptyQuery}
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

	/**
	 * Validates fields with validation and prevents the default form submission
	 * if there are any errors.
	 *
	 * Form submission is defined by the action attribute on the <form> element
	 * outside of this component. Since we are leveraging the <aui:form> taglib
	 * to handle submission and formik to handle value changes and validation,
	 * this method uses formik to validate and prevents the taglib form action
	 * from being called.
	 * @param {Class} event Event to prevent a form submission from occurring.
	 */
	_handleValidate = event => {
		const {validateForm} = this.props;

		event.persist();

		validateForm().then(
			errors => {
				const errorMessages = Object.values(errors);

				if (errorMessages.length) {
					event.preventDefault();

					errorMessages.forEach(
						message => {
							Liferay.Util.openToast(
								{
									message,
									title: Liferay.Language.get('error'),
									type: 'danger'
								}
							);
						}
					);
				}
			}
		);
	};

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

		const {changesUnsaved, editing, membersCount, membersCountLoading} = this.state;

		const {assetsPath} = this.context;

		const disabledCancel = !editing;
		const disabledSave = !editing || this._isQueryEmpty();
		const editingToggleDisabled = changesUnsaved;

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
							<div className="btn-group">
								<div className="btn-group-item">
									<ClaySpinner
										className="mr-4"
										loading={membersCountLoading}
										size="sm"
									/>

									<ClayButton
										label={getPluralMessage(
											Liferay.Language.get('x-member'),
											Liferay.Language.get('x-members'),
											membersCount
										)}
										onClick={this._handlePreviewClick(previewMembersURL)}
										size="sm"
										type="button"
									/>
								</div>
								<div className="btn-group-item mr-2">
									<ClayToggle
										checked={editing}
										className="toggle-editing"
										disabled={editingToggleDisabled}
										iconOff="view"
										iconOn="pencil"
										onChange={this._handleCriteriaEdit}
									/>
								</div>
							</div>

							<div className="btn-group">
								<div className="btn-group-item">
									<ClayButton
										disabled={disabledCancel}
										href={redirect}
										label={Liferay.Language.get('cancel')}
										size="sm"
									/>
								</div>

								<div className="btn-group-item">
									<ClayButton
										disabled={disabledSave}
										label={Liferay.Language.get('save')}
										onClick={this._handleValidate}
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
				name: props.initialSegmentName
			}
		),
		validate: values => {
			const errors = {};

			if (!values.name) {
				errors.name = Liferay.Language.get('segment-name-is-required');
			}

			return errors;
		}
	}
)(SegmentEdit);