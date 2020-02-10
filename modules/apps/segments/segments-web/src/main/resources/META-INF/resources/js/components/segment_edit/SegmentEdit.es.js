/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayButton from '@clayui/button';
import {FieldArray, withFormik} from 'formik';
import {debounce, fetch} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {Component} from 'react';

import ThemeContext from '../../ThemeContext.es';
import {
	SOURCES,
	SUPPORTED_CONJUNCTIONS,
	SUPPORTED_OPERATORS,
	SUPPORTED_PROPERTY_TYPES
} from '../../utils/constants.es';
import {
	applyConjunctionChangeToContributor,
	applyCriteriaChangeToContributors,
	initialContributorsToContributors
} from '../../utils/contributors.es';
import {initialContributorShape} from '../../utils/types.es';
import {sub} from '../../utils/utils.es';
import ContributorInputs from '../criteria_builder/ContributorInputs.es';
import ContributorsBuilder from '../criteria_builder/ContributorsBuilder.es';
import ClayToggle from '../shared/ClayToggle.es';
import LocalizedInput from '../title_editor/LocalizedInput.es';

class SegmentEdit extends Component {
	static contextType = ThemeContext;

	static propTypes = {
		availableLocales: PropTypes.object.isRequired,
		contributors: PropTypes.arrayOf(initialContributorShape),
		defaultLanguageId: PropTypes.string.isRequired,
		errors: PropTypes.object,
		formId: PropTypes.string,
		handleBlur: PropTypes.func,
		handleChange: PropTypes.func,
		hasUpdatePermission: PropTypes.bool,
		initialMembersCount: PropTypes.number,
		initialSegmentActive: PropTypes.bool,
		initialSegmentName: PropTypes.object,
		locale: PropTypes.string.isRequired,
		portletNamespace: PropTypes.string,
		previewMembersURL: PropTypes.string,
		propertyGroups: PropTypes.array,
		redirect: PropTypes.string.isRequired,
		requestMembersCountURL: PropTypes.string,
		setFieldValue: PropTypes.func,
		setValues: PropTypes.func,
		showInEditMode: PropTypes.bool,
		source: PropTypes.string,
		validateForm: PropTypes.func,
		values: PropTypes.object
	};

	static defaultProps = {
		contributors: [],
		handleBlur: () => {},
		initialSegmentActive: true,
		initialSegmentName: {},
		portletNamespace: '',
		showInEditMode: false
	};

	constructor(props) {
		super(props);

		const {
			contributors: initialContributors,
			initialMembersCount,
			propertyGroups,
			showInEditMode,
			values
		} = props;

		const contributors = initialContributorsToContributors(
			initialContributors,
			propertyGroups
		);

		this.state = {
			contributors,
			disabledSave: this._isQueryEmpty(contributors),
			editing: showInEditMode,
			hasChanged: false,
			membersCount: initialMembersCount,
			validTitle: !!values.name[props.defaultLanguageId]
		};

		this._debouncedFetchMembersCount = debounce(
			this._fetchMembersCount,
			500
		);
	}

	_handleCriteriaEdit = () => {
		this.setState({
			editing: !this.state.editing
		});
	};

	_handleLocalizedInputChange = (event, newValues, invalid) => {
		this.props.setFieldValue('name', newValues);
		this.setState({
			hasChanged: true,
			validTitle: !invalid
		});
	};

	_fetchMembersCount = () => {
		const formElement = document.getElementById(this.props.formId);

		const formData = new FormData(formElement);

		fetch(this.props.requestMembersCountURL, {
			body: formData,
			method: 'POST'
		})
			.then(response => response.json())
			.then(membersCount => {
				this.setState({
					membersCount,
					membersCountLoading: false
				});
			})
			.catch(() => {
				this.setState({membersCountLoading: false});

				Liferay.Util.openToast({
					message: Liferay.Language.get(
						'an-unexpected-error-occurred'
					),
					title: Liferay.Language.get('error'),
					type: 'danger'
				});
			});
	};

	_handleQueryChange = (criteriaChange, index) => {
		this.setState(prevState => {
			const contributors = applyCriteriaChangeToContributors(
				prevState.contributors,
				{
					criteriaChange,
					propertyKey: index
				}
			);

			return {
				contributors,
				disabledSave: this._isQueryEmpty(contributors),
				hasChanged: true,
				membersCountLoading: true
			};
		}, this._debouncedFetchMembersCount);
	};

	_handleSegmentNameBlur = event => {
		const {handleBlur} = this.props;

		handleBlur(event);
	};

	_handleSourceIconMouseOver = event => {
		const message =
			this.props.source === SOURCES.ASAH_FARO_BACKEND.name
				? SOURCES.ASAH_FARO_BACKEND.label
				: SOURCES.DEFAULT.label;

		Liferay.Portal.ToolTip.show(event.currentTarget, message);
	};

	_handleConjunctionChange = conjunctionName => {
		this.setState(prevState => {
			const contributors = applyConjunctionChangeToContributor(
				prevState.contributors,
				conjunctionName
			);

			return {
				contributors,
				hasChanged: true,
				membersCountLoading: true
			};
		}, this._debouncedFetchMembersCount);
	};

	/**
	 * Checks if every query in each contributor has a value.
	 * @return {boolean} True if none of the contributor's queries have a value.
	 */
	_isQueryEmpty = contributors =>
		contributors.every(contributor => !contributor.query);

	_renderContributors = () => {
		const {
			locale,
			propertyGroups,
			requestMembersCountURL,
			values
		} = this.props;

		const {
			contributors,
			editing,
			membersCount,
			membersCountLoading
		} = this.state;

		const emptyContributors = this._isQueryEmpty(contributors);

		const segmentName = values.name[locale];

		return propertyGroups && contributors ? (
			<ContributorsBuilder
				contributors={contributors}
				editing={editing}
				emptyContributors={emptyContributors}
				membersCount={membersCount}
				membersCountLoading={membersCountLoading}
				onConjunctionChange={this._handleConjunctionChange}
				onPreviewMembers={this._handlePreviewMembers}
				onQueryChange={this._handleQueryChange}
				propertyGroups={propertyGroups}
				requestMembersCountURL={requestMembersCountURL}
				segmentName={segmentName}
				supportedConjunctions={SUPPORTED_CONJUNCTIONS}
				supportedOperators={SUPPORTED_OPERATORS}
				supportedPropertyTypes={SUPPORTED_PROPERTY_TYPES}
			/>
		) : null;
	};

	/**
	 * Decides wether to redirect the user or warn
	 *
	 * @memberof SegmentEdit
	 */
	_handleCancelButton = () => {
		const {hasChanged} = this.state;

		if (hasChanged) {
			const confirmed = confirm(
				Liferay.Language.get('criteria-cancel-confirmation-message')
			);

			if (confirmed) {
				this._redirect();
			}
		}
		else {
			this._redirect();
		}
	};

	/**
	 * This redirects to the `redirect` prop.
	 *
	 * @memberof SegmentEdit
	 */
	_redirect = () => {
		Liferay.Util.navigate(this.props.redirect);
	};

	/**
	 * Opens a modal from to show the view from `this.props.previewMembersURL`
	 *
	 * @memberof SegmentEdit
	 */
	_handlePreviewMembers = () => {
		const {locale, previewMembersURL, values} = this.props;
		const {name} = values;
		const segmentLocalizedName = name[locale];

		Liferay.Util.openWindow({
			dialog: {
				destroyOnHide: true
			},
			id: 'segment-members-dialog',
			title: sub(Liferay.Language.get('x-members'), [
				Liferay.Util.escape(segmentLocalizedName)
			]),
			uri: previewMembersURL
		});
	};

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

		validateForm().then(errors => {
			const errorMessages = Object.values(errors);

			if (errorMessages.length) {
				event.preventDefault();

				errorMessages.forEach(message => {
					Liferay.Util.openToast({
						message,
						title: Liferay.Language.get('error'),
						type: 'danger'
					});
				});
			}
		});
	};

	_renderLocalizedInputs = () => {
		const {defaultLanguageId, portletNamespace, values} = this.props;

		const langs = Object.keys(values.name);

		return langs.map(key => {
			let returnVal;
			const value = values.name[key];

			if (key === defaultLanguageId) {
				returnVal = (
					<React.Fragment key={key}>
						<input
							name={`${portletNamespace}name_${key}`}
							readOnly
							type="hidden"
							value={value}
						/>
						<input
							name={`${portletNamespace}key`}
							readOnly
							type="hidden"
							value={value}
						/>
						<input
							name={`${portletNamespace}name`}
							readOnly
							type="hidden"
							value={value}
						/>
					</React.Fragment>
				);
			}
			else {
				returnVal = (
					<React.Fragment key={key}>
						<input
							name={`${portletNamespace}name_${key}`}
							readOnly
							type="hidden"
							value={value}
						/>
					</React.Fragment>
				);
			}

			return returnVal;
		});
	};

	render() {
		const {
			availableLocales,
			defaultLanguageId,
			hasUpdatePermission,
			portletNamespace,
			source,
			values
		} = this.props;

		const {contributors, disabledSave, editing, validTitle} = this.state;

		const {assetsPath} = this.context;

		const disabledSaveButton = disabledSave || !validTitle;

		const placeholder = Liferay.Language.get('untitled-segment');

		return (
			<div className="segment-edit-page-root">
				<input
					name={`${portletNamespace}active`}
					type="hidden"
					value={values.active}
				/>

				<div className="form-header">
					<div className="container-fluid container-fluid-max-xl form-header-container">
						<div className="form-header-section-left">
							<FieldArray
								name="values.name"
								render={this._renderLocalizedInputs}
							/>

							<LocalizedInput
								availableLanguages={availableLocales}
								defaultLang={defaultLanguageId}
								initialLanguageId={defaultLanguageId}
								initialOpen={false}
								initialValues={values.name}
								onChange={this._handleLocalizedInputChange}
								placeholder={placeholder}
								portletNamespace={portletNamespace}
								readOnly={!editing}
							/>

							<img
								className="source-icon"
								data-testid="source-icon"
								onMouseOver={this._handleSourceIconMouseOver}
								src={
									source === SOURCES.ASAH_FARO_BACKEND.name
										? `${assetsPath}${SOURCES.ASAH_FARO_BACKEND.icon}`
										: `${assetsPath}${SOURCES.DEFAULT.icon}`
								}
							/>
						</div>

						{hasUpdatePermission && (
							<div className="form-header-section-right">
								<div className="btn-group">
									<div className="btn-group-item mr-2">
										<ClayToggle
											checked={editing}
											className="toggle-editing"
											iconOff="pencil"
											iconOn="pencil"
											onChange={this._handleCriteriaEdit}
										/>
									</div>
								</div>

								<div className="btn-group">
									<div className="btn-group-item">
										<ClayButton
											className="text-capitalize"
											displayType="secondary"
											onClick={this._handleCancelButton}
											small
										>
											{Liferay.Language.get('cancel')}
										</ClayButton>
									</div>

									<div className="btn-group-item">
										<ClayButton
											className="text-capitalize"
											disabled={disabledSaveButton}
											displayType="primary"
											onClick={this._handleValidate}
											small={true}
											type="submit"
										>
											{Liferay.Language.get('save')}
										</ClayButton>
									</div>
								</div>
							</div>
						)}
					</div>
				</div>

				<div className="form-body">
					<FieldArray
						name="contributors"
						render={this._renderContributors}
					/>
					<ContributorInputs contributors={contributors} />
				</div>
			</div>
		);
	}
}

export default withFormik({
	mapPropsToValues: props => ({
		active: props.initialSegmentActive || true,
		contributors: props.contributors || [],
		name: props.initialSegmentName || {}
	}),
	validate: values => {
		const errors = {};

		if (!values.name) {
			errors.name = Liferay.Language.get('segment-name-is-required');
		}

		return errors;
	}
})(SegmentEdit);
