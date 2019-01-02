import React, {Component, Fragment} from 'react';
import PropTypes from 'prop-types';
import ClayButton from '../shared/ClayButton.es';
import ClaySelect from '../shared/ClaySelect.es';
import ClaySpinner from '../shared/ClaySpinner.es';
import debounce from 'lodash.debounce';
import TitleEditor from '../title_editor/TitleEditor.es';
import ODataQueryBuilder from '../odata_query_builder/ODataQueryBuilder.es';
import {SUPPORTED_CONJUNCTIONS} from '../../utils/constants.es';
import {sub} from '../../utils/utils.es';
import {FieldArray, withFormik} from 'formik';

const DEFAULT_SEGMENT_NAME = Liferay.Language.get('unnamed-segment');

class SegmentEdit extends Component {
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
		redirect: PropTypes.string,
		requestMembersCountURL: PropTypes.string,
		setValues: PropTypes.func,
		values: PropTypes.object
	};

	static defaultProps = {
		initialSegmentActive: true,
		initialSegmentName: DEFAULT_SEGMENT_NAME,
		portletNamespace: ''
	};

	state = {
		membersCount: this.props.initialMembersCount || 0,
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

	_handleSegmentNameBlur = event => {
		const {handleBlur, setValues, values} = this.props;

		if (values.name === '') {
			setValues({...values, name: DEFAULT_SEGMENT_NAME});
		}

		handleBlur(event);
	};

	_handleQueryChange = () => {
		this.setState({membersCountLoading: true});

		this._debouncedFetchMembersCount();
	};

	_renderContributors = () => {
		const {handleChange, values} = this.props;

		return (
			values.contributors.map(
				(contributor, index) => {
					return (
						<Fragment key={contributor.inputId}>
							<input
								id={contributor.conjunctionInputId}
								name={contributor.conjunctionInputId}
								type="hidden"
								value={contributor.conjunctionId ||
									SUPPORTED_CONJUNCTIONS[0].name}
							/>

							{index !== 0 &&
								<ClaySelect
									className="contributor-conjunction"
									name={`contributors.${index}.conjunctionId`}
									onChange={handleChange}
									options={SUPPORTED_CONJUNCTIONS.map(
										({label, name}) => ({
											label: label.toUpperCase(),
											value: name
										})
									)}
									selected={contributor.conjunctionId}
								/>
							}

							<ODataQueryBuilder
								initialQuery={contributor.initialQuery}
								inputId={contributor.inputId}
								modelLabel={contributor.modelLabel}
								onChange={this._handleQueryChange}
								properties={contributor.properties}
							/>
						</Fragment>
					);
				}
			)
		);
	};

	render() {
		const {
			handleChange,
			locale,
			portletNamespace,
			previewMembersURL,
			redirect,
			values
		} = this.props;

		const {membersCount, membersCountLoading} = this.state;

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

				<div className="form-header-root">
					<div className="container-fluid container-fluid-max-xl form-header-container">
						<div className="form-header-section-left">
							<TitleEditor
								inputName="name"
								onBlur={this._handleSegmentNameBlur}
								onChange={handleChange}
								placeholder={DEFAULT_SEGMENT_NAME}
								value={values.name}
							/>
						</div>

						<div className="form-header-section-right">
							<ClaySpinner
								loading={membersCountLoading}
								size="sm"
							/>

							<div className="members-count">
								{sub(
									Liferay.Language.get('x-members'),
									[membersCount]
								)}
							</div>

							{previewMembersURL &&
								<ClayButton
									className="members-preview-button"
									href={previewMembersURL}
									label={Liferay.Language.get('preview-members')}
								/>
							}

							<div className="btn-group">
								<div className="btn-group-item">
									<ClayButton
										borderless
										href={redirect}
										label={Liferay.Language.get('cancel')}
									/>
								</div>

								<div className="btn-group-item">
									<ClayButton
										label={Liferay.Language.get('save')}
										style="primary"
										type="submit"
									/>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div>
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