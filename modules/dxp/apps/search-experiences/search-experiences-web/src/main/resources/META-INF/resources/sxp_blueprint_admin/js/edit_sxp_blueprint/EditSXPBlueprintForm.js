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
import {addParams} from '../utils/fetch';
import {INPUT_TYPES} from '../utils/inputTypes';
import {openErrorToast, openSuccessToast} from '../utils/toasts';
import {
	cleanUIConfigurationJSON,
	getSXPElementOutput,
	getUIConfigurationValues,
	isDefined,
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
	entityJSON,
	initialConfiguration = {},
	initialDescription = {},
	initialSXPElementInstances = {},
	initialTitle = {},
	sxpBlueprintId,
}) {
	const {defaultLocale, namespace, redirectURL} = useContext(ThemeContext);

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

	const sxpElementIdCounter = useRef(
		initialSXPElementInstances.queryConfiguration?.queryEntries?.length || 0
	);

	const _getFormInput = (key) => {
		for (const pair of new FormData(form.current).entries()) {
			if (pair[0].includes(`${namespace}${key}`)) {
				return pair[1];
			}
		}

		return '';
	};

	/**
	 * Formats the form values for the "configuration" parameter to send to
	 * the server. Sets defaults so the JSON.parse calls don't break.
	 * @param {Object} values Form values
	 * @return {Object}
	 */
	const _getConfiguration = ({
		advancedConfig,
		aggregationConfig,
		applyIndexerClauses,
		facetConfig,
		frameworkConfig,
		highlightConfig,
		parameterConfig,
		selectedQuerySXPElements,
		sortConfig,
	}) => ({
		advanced: advancedConfig ? JSON.parse(advancedConfig) : {},
		aggregationConfiguration: aggregationConfig
			? JSON.parse(aggregationConfig)
			: {},
		facet: facetConfig ? JSON.parse(facetConfig) : {},
		general: frameworkConfig,
		highlight: highlightConfig ? JSON.parse(highlightConfig) : {},
		parameters: parameterConfig ? JSON.parse(parameterConfig) : {},
		queryConfiguration: {
			applyIndexerClauses,
			queryEntries: selectedQuerySXPElements.map(getSXPElementOutput),
		},
		sortConfiguration: sortConfig ? JSON.parse(sortConfig) : {},
	});

	const _getSelectedSXPElements = (values) => ({
		queryConfiguration: {
			queryEntries: values.selectedQuerySXPElements.map((item) =>
				item.uiConfigurationJSON
					? {
							sxpElementTemplateJSON: item.sxpElementTemplateJSON,
							uiConfigurationJSON: item.uiConfigurationJSON,
							uiConfigurationValues: item.uiConfigurationValues,
					  } // Removes ID field
					: {
							sxpElementTemplateJSON: getSXPElementOutput(item),
					  }
			),
		},
	});

	const _handleFormikSubmit = async (values) => {
		let configuration;
		let elementInstances;

		try {
			configuration = _getConfiguration(values);
			elementInstances = _getSelectedSXPElements(values);
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

		try {

			// If the warning modal is already open, assume the form was submitted
			// using the "Continue To Save" action and should skip the schema
			// validation step.

			// TODO: Update this once a validation REST endpoint is decided

			if (!showSubmitWarningModal) {
				const validateErrors = {errors: []};

				/*
				const validateErrors = await fetch(
					'/o/search-experiences-rest/v1.0/sxp-blueprints/validate',
					{
						body: JSON.stringify({
							description_i18n: {
								[defaultLocale]: _getFormInput('description'),
							},
							elementInstances,
							title_i18n: {[defaultLocale]: _getFormInput('title')},
						}),
						headers: new Headers({
							'Content-Type': 'application/json',
						}),
						method: 'POST',
					}
				).then((response) => response.json());
			*/

				if (validateErrors.errors?.length) {
					setErrors(validateErrors.errors);
					setShowSubmitWarningModal(true);

					return;
				}
			}

			const responseContent = await fetch(
				`/o/search-experiences-rest/v1.0/sxp-blueprints/${sxpBlueprintId}`,
				{
					body: JSON.stringify({
						configuration,
						description_i18n: {
							[defaultLocale]: _getFormInput('description'),
						},
						elementInstances,
						title_i18n: {[defaultLocale]: _getFormInput('title')},
					}),
					headers: new Headers({
						'Content-Type': 'application/json',
					}),
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
				if (
					isDefined(sxpElementTemplateJSON.enabled) &&
					!sxpElementTemplateJSON.enabled
				) {
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
				initialConfiguration.advanced,
				null,
				'\t'
			),
			aggregationConfig: JSON.stringify(
				initialConfiguration.aggregationConfiguration,
				null,
				'\t'
			),
			applyIndexerClauses:
				initialConfiguration.queryConfiguration?.applyIndexerClauses ||
				true,
			facetConfig: JSON.stringify(initialConfiguration.facet, null, '\t'),
			frameworkConfig: initialConfiguration.general || {},
			highlightConfig: JSON.stringify(
				initialConfiguration.highlight,
				null,
				'\t'
			),
			parameterConfig: JSON.stringify(
				initialConfiguration.parameters,
				null,
				'\t'
			),
			selectedQuerySXPElements:
				initialSXPElementInstances?.queryConfiguration?.queryEntries.map(
					(selectedSXPElement, index) => ({
						...selectedSXPElement,
						id: index,
					})
				) || [],
			sortConfig: JSON.stringify(
				initialConfiguration.sortConfiguration,
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

	const _handleFetchPreviewSearch = (value, delta, page /* attributes*/) => {
		setPreviewInfo((previewInfo) => ({
			...previewInfo,
			loading: true,
		}));

		let configuration;

		try {
			configuration = _getConfiguration(formik.values);
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
				body: JSON.stringify({
					configuration,

					// TO DO: Enable when preview attributes available

					// previewAttributes: attributes.filter(
					// 	(attribute) => attribute.key
					// ),

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

	const _handleApplyIndexerClausesChange = (value) =>
		formik.setFieldValue('applyIndexerClauses', value);

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
						applyIndexerClauses={formik.values.applyIndexerClauses}
						frameworkConfig={formik.values.frameworkConfig}
						onApplyIndexerClausesChange={
							_handleApplyIndexerClausesChange
						}
						onFrameworkConfigChange={_handleFrameworkConfigChange}
					/>
				);
			default:
				return (
					<>
						<AddSXPElementSidebar
							onAddSXPElement={_handleAddSXPElement}
							onToggle={setShowSidebar}
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
	initialConfiguration: PropTypes.object,
	initialDescription: PropTypes.object,
	initialSXPElementInstances: PropTypes.object,
	initialTitle: PropTypes.object,
	sxpBlueprintId: PropTypes.string,
};

export default React.memo(EditSXPBlueprintForm);
