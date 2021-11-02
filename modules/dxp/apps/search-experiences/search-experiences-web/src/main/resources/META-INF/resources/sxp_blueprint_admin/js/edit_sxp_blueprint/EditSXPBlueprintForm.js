/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayButton from '@clayui/button';
import ClayToolbar from '@clayui/toolbar';
import getCN from 'classnames';
import {useFormik} from 'formik';
import {fetch, navigate} from 'frontend-js-web';
import {PropTypes} from 'prop-types';
import React, {useCallback, useContext, useRef, useState} from 'react';

import PageToolbar from '../shared/PageToolbar';
import SubmitWarningModal from '../shared/SubmitWarningModal';
import ThemeContext from '../shared/ThemeContext';
import {DEFAULT_ERROR} from '../utils/constants';
import {CUSTOM_JSON_SXP_ELEMENT} from '../utils/data';
import {addParams} from '../utils/fetch';
import {INPUT_TYPES} from '../utils/inputTypes';
import {openErrorToast, openSuccessToast} from '../utils/toasts';
import {
	cleanUIConfigurationJSON,
	getSXPElementOutput,
	getUIConfigurationValues,
} from '../utils/utils';
import {
	validateBoost,
	validateJSON,
	validateNumberRange,
	validateRequired,
} from '../utils/validation';
import AddSXPElementSidebar from './AddSXPElementSidebar';
import PreviewSidebar from './PreviewSidebar';
import ClauseContributorsTab from './clause_contributors_tab/index';
import QueryBuilderTab from './query_builder_tab/index';
import SettingsTab from './settings_tab/index';

// Tabs in display order
/* eslint-disable sort-keys */
const TABS = {
	'query-builder': Liferay.Language.get('query-builder'),
	'clause-contributors': Liferay.Language.get('clause-contributors'),
	'settings': Liferay.Language.get('settings'),
};
/* eslint-enable sort-keys */

function EditSXPBlueprintForm({
	sxpBlueprintId,
	entityJSON,
	indexFields,
	initialConfigurationString = '{}',
	initialDescription = {},
	initialSelectedSXPElementsString = '{}',
	initialTitle = {},
	querySXPElements = [],
}) {
	const {namespace, redirectURL} = useContext(ThemeContext);

	const [errors, setErrors] = useState([]);
	const [previewInfo, setPreviewInfo] = useState(() => ({
		loading: false,
		results: {},
	}));
	const [showSidebar, setShowSidebar] = useState(true);
	const [showPreview, setShowPreview] = useState(false);
	const [showSubmitWarningModal, setShowSubmitWarningModal] = useState(false);
	const [tab, setTab] = useState('query-builder');

	const form = useRef();
	const sidebarQuerySXPElements = useRef([
		...querySXPElements,
		CUSTOM_JSON_SXP_ELEMENT,
	]);

	const initialConfiguration = JSON.parse(initialConfigurationString);
	const initialSelectedSXPElements = JSON.parse(
		initialSelectedSXPElementsString
	);

	if (!initialSelectedSXPElements['query_configuration']) {
		initialSelectedSXPElements['query_configuration'] = [];
	}

	const sxpElementIdCounter = useRef(
		initialSelectedSXPElements['query_configuration'].length
	);

	/**
	 * Abstracts title and description from the existing form, as
	 * query builder has several inputs that should not be included in
	 * submission.
	 * @param {FormData} formData
	 */
	const _appendTitleAndDescription = (formData) => {
		for (const pair of new FormData(form.current).entries()) {
			if (
				pair[0].includes(`${namespace}title`) ||
				pair[0].includes(`${namespace}description`)
			) {
				formData.append(pair[0], pair[1]);
			}
		}
	};

	/**
	 * Formats the form values for the "configuration" parameter to send to
	 * the server. Sets defaults so the JSON.parse calls don't break.
	 * @param {Object} values Form values
	 * @return {String}
	 */
	const _getConfigurationString = ({
		advancedConfig,
		aggregationConfig,
		facetConfig,
		frameworkConfig,
		highlightConfig,
		parameterConfig,
		selectedQuerySXPElements,
		sortConfig,
	}) => {
		return JSON.stringify({
			advanced_configuration: advancedConfig
				? JSON.parse(advancedConfig)
				: {},
			aggregation_configuration: aggregationConfig
				? JSON.parse(aggregationConfig)
				: {},
			facet_configuration: facetConfig ? JSON.parse(facetConfig) : [],
			framework_configuration: frameworkConfig,
			highlight_configuration: highlightConfig
				? JSON.parse(highlightConfig)
				: {},
			parameter_configuration: parameterConfig
				? JSON.parse(parameterConfig)
				: {},
			query_configuration: selectedQuerySXPElements.map(
				getSXPElementOutput
			),
			sort_configuration: sortConfig ? JSON.parse(sortConfig) : {},
		});
	};

	const _handleFormikSubmit = async (values) => {
		const formData = new FormData();

		_appendTitleAndDescription(formData);

		try {
			formData.append(
				`${namespace}configuration`,
				_getConfigurationString(values)
			);

			formData.append(
				`${namespace}selectedSXPElements`,
				JSON.stringify({
					query_configuration: values.selectedQuerySXPElements.map(
						(item) =>
							item.uiConfigurationJSON
								? {
										sxpElementTemplateJSON:
											item.sxpElementTemplateJSON,
										uiConfigurationJSON:
											item.uiConfigurationJSON,
										uiConfigurationValues:
											item.uiConfigurationValues,
								  } // Removes ID field
								: {
										sxpElementTemplateJSON: getSXPElementOutput(
											item
										),
								  }
					),
				})
			);
		}
		catch (error) {
			openErrorToast({
				message: Liferay.Language.get(
					'the-configuration-has-missing-or-invalid-values'
				),
			});

			if (process.env.NODE_ENV === 'development') {
				console.error(error);
			}

			return;
		}

		formData.append(`${namespace}sxpBlueprintId`, sxpBlueprintId);
		formData.append(`${namespace}redirect`, redirectURL);

		try {

			// If the warning modal is already open, assume the form was submitted
			// using the "Continue To Save" action and should skip the schema
			// validation step.

			if (!showSubmitWarningModal) {
				const validateErrors = await fetch(
					'/o/search-experiences-rest/v1.0/sxp-blueprints/validate',
					{
						body: formData,
						method: 'POST',
					}
				).then((response) => response.json());

				if (validateErrors.errors?.length) {
					setErrors(validateErrors.errors);
					setShowSubmitWarningModal(true);

					return;
				}
			}

			const responseContent = await fetch(
				`/o/search-experiences-rest/v1.0/sxp-blueprints/${sxpBlueprintId}`,
				{
					body: formData,
					method: 'PATCH',
				}
			).then((response) => {
				if (!response.ok) {
					setShowSubmitWarningModal(false);

					throw DEFAULT_ERROR;
				}

				return response.json();
			});

			if (
				Object.prototype.hasOwnProperty.call(responseContent, 'errors')
			) {
				responseContent.errors.forEach((message) =>
					openErrorToast({message})
				);
			}
			else {
				navigate(redirectURL);
			}
		}
		catch (error) {
			openErrorToast();

			if (process.env.NODE_ENV === 'development') {
				console.error(error);
			}
		}
	};

	const _handleFormikValidate = (values) => {
		const errors = {};

		// Validate the elements added to the query builder

		const selectedQuerySXPElementsArray = [];

		values.selectedQuerySXPElements.map(
			(
				{
					sxpElementTemplateJSON,
					uiConfigurationJSON,
					uiConfigurationValues,
				},
				index
			) => {
				if (!sxpElementTemplateJSON.enabled) {
					return;
				}

				const configErrors = {};
				const fieldSets = cleanUIConfigurationJSON(uiConfigurationJSON)
					.fieldSets;

				if (fieldSets.length > 0) {
					fieldSets.map(({fields}) => {
						fields.map(({name, type, typeOptions = {}}) => {
							const configValue = uiConfigurationValues[name];

							const configError =
								validateRequired(
									configValue,
									type,
									typeOptions.required,
									typeOptions.nullable
								) ||
								validateBoost(configValue, type) ||
								validateNumberRange(
									configValue,
									type,
									typeOptions
								) ||
								validateJSON(configValue, type);

							if (configError) {
								configErrors[name] = configError;
							}
						});
					});
				}
				else if (!uiConfigurationJSON) {
					const configValue =
						uiConfigurationValues?.sxpElementTemplateJSON;

					const configError =
						validateRequired(configValue, INPUT_TYPES.JSON) ||
						validateJSON(configValue, INPUT_TYPES.JSON);

					if (configError) {
						configErrors.sxpElementTemplateJSON = configError;
					}
				}

				if (Object.keys(configErrors).length > 0) {
					selectedQuerySXPElementsArray[index] = {
						uiConfigurationValues: configErrors,
					};
				}
			}
		);

		if (selectedQuerySXPElementsArray.length > 0) {
			errors.selectedQuerySXPElements = selectedQuerySXPElementsArray;
		}

		// Validate all JSON inputs on the settings tab

		[
			'advancedConfig',
			'aggregationConfig',
			'facetConfig',
			'highlightConfig',
			'parameterConfig',
			'sortConfig',
		].map((configName) => {
			const configError = validateJSON(
				values[configName],
				INPUT_TYPES.JSON
			);

			if (configError) {
				errors[configName] = configError;
			}
		});

		return errors;
	};

	const formik = useFormik({
		initialValues: {
			advancedConfig: JSON.stringify(
				initialConfiguration['advanced_configuration'],
				null,
				'\t'
			),
			aggregationConfig: JSON.stringify(
				initialConfiguration['aggregation_configuration'],
				null,
				'\t'
			),
			facetConfig: JSON.stringify(
				initialConfiguration['facet_configuration'],
				null,
				'\t'
			),
			frameworkConfig:
				initialConfiguration['framework_configuration'] || {},
			highlightConfig: JSON.stringify(
				initialConfiguration['highlight_configuration'],
				null,
				'\t'
			),
			parameterConfig: JSON.stringify(
				initialConfiguration['parameter_configuration'],
				null,
				'\t'
			),
			selectedQuerySXPElements: initialSelectedSXPElements[
				'query_configuration'
			].map((selectedSXPElement, index) => ({
				...selectedSXPElement,
				id: index,
			})),
			sortConfig: JSON.stringify(
				initialConfiguration['sort_configuration'],
				null,
				'\t'
			),
		},
		onSubmit: _handleFormikSubmit,
		validate: _handleFormikValidate,
	});

	const _handleAddSXPElement = (sxpElement) => {
		if (formik.touched?.selectedQuerySXPElements) {
			formik.setTouched({
				...formik.touched,
				selectedQuerySXPElements: [
					undefined,
					...formik.touched.selectedQuerySXPElements,
				],
			});
		}

		formik.setFieldValue('selectedQuerySXPElements', [
			{
				...sxpElement,
				id: sxpElementIdCounter.current++,
				uiConfigurationValues: getUIConfigurationValues(
					sxpElement.uiConfigurationJSON
				),
			},
			...formik.values.selectedQuerySXPElements,
		]);

		openSuccessToast({
			message: Liferay.Language.get('element-added'),
		});
	};

	const _handleDeleteSXPElement = (id) => {
		const index = formik.values.selectedQuerySXPElements.findIndex(
			(item) => item.id == id
		);

		if (formik.touched?.selectedQuerySXPElements) {
			formik.setTouched({
				...formik.touched,
				selectedQuerySXPElements: formik.touched.selectedQuerySXPElements.filter(
					(_, i) => i !== index
				),
			});
		}

		formik.setFieldValue(
			'selectedQuerySXPElements',
			formik.values.selectedQuerySXPElements.filter(
				(item) => item.id !== id
			)
		);

		openSuccessToast({
			message: Liferay.Language.get('element-removed'),
		});
	};

	const _handleFetchPreviewSearch = (value, delta, page, attributes) => {
		setPreviewInfo((previewInfo) => ({
			...previewInfo,
			loading: true,
		}));

		const formData = new FormData();

		_appendTitleAndDescription(formData);

		try {
			formData.append(
				`${namespace}configuration`,
				_getConfigurationString(formik.values)
			);
		}
		catch (error) {
			setPreviewInfo({
				loading: false,
				results: {
					errors: [
						{
							msg: Liferay.Language.get(
								'the-configuration-has-missing-or-invalid-values'
							),
						},
					],
				},
			});

			if (process.env.NODE_ENV === 'development') {
				console.error(error);
			}

			return;
		}

		formData.append(`${namespace}page`, page);
		formData.append(`${namespace}query`, value);
		formData.append(`${namespace}pageSize`, delta);

		formData.append(
			`${namespace}previewAttributes`,
			JSON.stringify(attributes.filter((attribute) => attribute.key))
		);

		const resultsError = {
			errors: [
				{
					msg: DEFAULT_ERROR,
					severity: Liferay.Language.get('error'),
				},
			],
		};

		return fetch(
			addParams('/o/search-experiences-rest/v1.0/search', {
				page,
				pageSize: delta,
				query: value,
			}),
			{

				// body: formData,

				body: JSON.stringify({
					configuration: {
						general: {explain: true, includeResponseString: true},
					},
				}),
				headers: new Headers({
					'Content-Type': 'application/json',
				}),
				method: 'POST',
			}
		)
			.then((response) => {
				if (!response.ok) {
					return resultsError;
				}

				return response.json();
			})
			.then((responseContent) => {
				setPreviewInfo({
					loading: false,
					results: {
						...responseContent,
						warnings: !formik.isValid
							? [
									{
										msg: Liferay.Language.get(
											'the-configuration-has-missing-or-invalid-values'
										),
									},
							  ]
							: [],
					},
				});
			})
			.catch(() => {
				setTimeout(() => {
					setPreviewInfo({
						loading: false,
						results: resultsError,
					});
				}, 1000);
			});
	};

	const _handleFocusSXPElement = (prefixedId) => {
		const sxpElement = document.getElementById(prefixedId);

		if (sxpElement) {
			window.scrollTo({
				behavior: 'smooth',
				top:
					sxpElement.getBoundingClientRect().top +
					window.pageYOffset -
					55 - // Control menu height
					104 - // Page toolbar height
					20, // Additional padding
			});

			sxpElement.classList.remove('focus');

			void sxpElement.offsetWidth; // Triggers reflow to restart animation

			sxpElement.classList.add('focus');
		}
	};

	const _handleFrameworkConfigChange = useCallback(
		(value) =>
			formik.setFieldValue('frameworkConfig', {
				...formik.values.frameworkConfig,
				...value,
			}),
		[formik]
	);

	const _handleSubmit = (event) => {
		event.preventDefault();

		formik.handleSubmit();

		if (!formik.isValid) {
			openErrorToast({
				message: Liferay.Language.get(
					'unable-to-save-due-to-invalid-or-missing-configuration-values'
				),
			});
		}
	};

	const _renderTabContent = () => {
		switch (tab) {
			case 'settings':
				return (
					<SettingsTab
						advancedConfig={formik.values.advancedConfig}
						aggregationConfig={formik.values.aggregationConfig}
						errors={formik.errors}
						facetConfig={formik.values.facetConfig}
						highlightConfig={formik.values.highlightConfig}
						parameterConfig={formik.values.parameterConfig}
						setFieldTouched={formik.setFieldTouched}
						setFieldValue={formik.setFieldValue}
						sortConfig={formik.values.sortConfig}
						touched={formik.touched}
					/>
				);
			case 'clause-contributors':
				return (
					<ClauseContributorsTab
						applyIndexerClauses={
							formik.values.frameworkConfig[
								'apply_indexer_clauses'
							]
						}
						clauseContributors={
							formik.values.frameworkConfig['clause_contributors']
						}
						onFrameworkConfigChange={_handleFrameworkConfigChange}
					/>
				);
			default:
				return (
					<>
						<AddSXPElementSidebar
							emptyMessage={Liferay.Language.get(
								'no-query-elements-found'
							)}
							onAddSXPElement={_handleAddSXPElement}
							onToggle={setShowSidebar}
							sxpElements={sidebarQuerySXPElements.current}
							title={Liferay.Language.get('add-query-elements')}
							visible={showSidebar}
						/>

						<div
							className={getCN('query-builder', {
								'open-preview': showPreview,
								'open-sidebar': showSidebar,
							})}
						>
							<QueryBuilderTab
								entityJSON={entityJSON}
								errors={formik.errors.selectedQuerySXPElements}
								frameworkConfig={formik.values.frameworkConfig}
								indexFields={indexFields}
								isSubmitting={
									formik.isSubmitting || previewInfo.loading
								}
								onBlur={formik.handleBlur}
								onChange={formik.handleChange}
								onDeleteSXPElement={_handleDeleteSXPElement}
								onFrameworkConfigChange={
									_handleFrameworkConfigChange
								}
								onToggleSidebar={() => {
									setShowPreview(false);
									setShowSidebar(!showSidebar);
								}}
								selectedSXPElements={
									formik.values.selectedQuerySXPElements
								}
								setFieldTouched={formik.setFieldTouched}
								setFieldValue={formik.setFieldValue}
								touched={
									formik.touched.selectedQuerySXPElements
								}
							/>
						</div>
					</>
				);
		}
	};

	return (
		<form ref={form}>
			<SubmitWarningModal
				errors={errors}
				isSubmitting={formik.isSubmitting}
				message={Liferay.Language.get(
					'the-blueprint-configuration-has-errors-that-may-cause-unexpected-results.-use-the-preview-panel-to-review-these-errors'
				)}
				onClose={() => setShowSubmitWarningModal(false)}
				onSubmit={_handleSubmit}
				visible={showSubmitWarningModal}
			/>

			<PageToolbar
				initialDescription={initialDescription}
				initialTitle={initialTitle}
				isSubmitting={formik.isSubmitting}
				onCancel={redirectURL}
				onChangeTab={setTab}
				onSubmit={_handleSubmit}
				tab={tab}
				tabs={TABS}
			>
				<ClayToolbar.Item>
					<ClayButton
						borderless
						className={getCN({
							active: showPreview,
						})}
						displayType="secondary"
						onClick={() => {
							setShowSidebar(false);
							setShowPreview(!showPreview);
						}}
						small
					>
						{Liferay.Language.get('preview')}
					</ClayButton>
				</ClayToolbar.Item>
			</PageToolbar>

			<PreviewSidebar
				loading={previewInfo.loading}
				onFetchResults={_handleFetchPreviewSearch}
				onFocusSXPElement={_handleFocusSXPElement}
				onToggle={setShowPreview}
				results={previewInfo.results}
				visible={showPreview}
			/>

			<div
				className={getCN({
					'open-preview': showPreview,
				})}
			>
				{_renderTabContent()}
			</div>
		</form>
	);
}

EditSXPBlueprintForm.propTypes = {
	entityJSON: PropTypes.object,
	indexFields: PropTypes.arrayOf(PropTypes.object),
	initialConfigurationString: PropTypes.string,
	initialDescription: PropTypes.object,
	initialSelectedSXPElementsString: PropTypes.string,
	initialTitle: PropTypes.object,
	querySXPElements: PropTypes.arrayOf(PropTypes.object),
	sxpBlueprintId: PropTypes.string,
};

export default React.memo(EditSXPBlueprintForm);
