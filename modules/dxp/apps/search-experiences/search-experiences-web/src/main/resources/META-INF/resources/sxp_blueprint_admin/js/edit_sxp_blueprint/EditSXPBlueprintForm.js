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
import {setNestedObjectValues, useFormik} from 'formik';
import {fetch, navigate} from 'frontend-js-web';
import {PropTypes} from 'prop-types';
import React, {
	useCallback,
	useContext,
	useEffect,
	useRef,
	useState,
} from 'react';

import LearnMessage from '../shared/LearnMessage';
import PageToolbar from '../shared/PageToolbar';
import Sidebar from '../shared/Sidebar';
import SubmitWarningModal from '../shared/SubmitWarningModal';
import ThemeContext from '../shared/ThemeContext';
import {DEFAULT_ERROR, SIDEBARS} from '../utils/constants';
import {fetchData, fetchPreviewSearch} from '../utils/fetch';
import {INPUT_TYPES} from '../utils/inputTypes';
import {getLocalizedText} from '../utils/language';
import {setItemAddSXPElementSidebar} from '../utils/sessionStorage';
import {TEST_IDS} from '../utils/testIds';
import {
	openErrorToast,
	openSuccessToast,
	setInitialSuccessToast,
} from '../utils/toasts';
import useShouldConfirmBeforeNavigate from '../utils/useShouldConfirmBeforeNavigate';
import {
	cleanUIConfiguration,
	filterAndSortClassNames,
	getConfigurationEntry,
	getResultsError,
	getUIConfigurationValues,
	isCustomJSONSXPElement,
	isDefined,
	parseCustomSXPElement,
	transformToSearchContextAttributes,
	transformToSearchPreviewHits,
} from '../utils/utils';
import {
	validateBoost,
	validateJSON,
	validateNumberRange,
	validateRequired,
} from '../utils/validation';
import AddSXPElementSidebar from './add_sxp_element_sidebar/index';
import ClauseContributorsSidebar from './clause_contributors_sidebar/index';
import ConfigurationTab from './configuration_tab/index';
import PreviewSidebar from './preview_sidebar/index';
import QueryBuilderTab from './query_builder_tab/index';

// Tabs in display order
/* eslint-disable sort-keys */
const TABS = {
	'query-builder': Liferay.Language.get('query-builder'),
	'configuration': Liferay.Language.get('configuration'),
};
/* eslint-enable sort-keys */

function EditSXPBlueprintForm({
	entityJSON,
	initialConfiguration = {},
	initialDescription = {},
	initialSXPElementInstances = [],
	initialTitle = {},
	sxpBlueprintId,
}) {
	const {defaultLocale, locale, redirectURL} = useContext(ThemeContext);

	const formRef = useRef();
	const sxpElementIdCounterRef = useRef(
		initialSXPElementInstances.length || 0
	);

	const [errors, setErrors] = useState([]);
	const [previewInfo, setPreviewInfo] = useState(() => ({
		loading: false,
		results: {},
	}));
	const [openSidebar, setOpenSidebar] = useState(SIDEBARS.ADD_SXP_ELEMENT);
	const [showSubmitWarningModal, setShowSubmitWarningModal] = useState(false);
	const [tab, setTab] = useState('query-builder');

	const [indexFields, setIndexFields] = useState(null);
	const [keywordQueryContributors, setKeywordQueryContributors] = useState(
		null
	);
	const [
		modelPrefilterContributors,
		setModelPrefilterContributors,
	] = useState(null);
	const [
		queryPrefilterContributors,
		setQueryPrefilterContributors,
	] = useState(null);
	const [searchableTypes, setSearchableTypes] = useState(null);

	/**
	 * This method must go before the useFormik hook.
	 */
	const _handleFormikSubmit = async (values) => {
		let configuration;
		let elementInstances;

		try {
			configuration = _getConfiguration(values);
			elementInstances = _getElementInstances(values);
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
							configuration,
							description_i18n: {
								[defaultLocale]: formik.values.description,
							},
							elementInstances,
							title_i18n: {[defaultLocale]: formik.values.title},
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

						// Update defaultLocale in title_i18n and description_i18n in
						// case the instance defaultLocale differs from the original
						// entry's defaultLocale.

						description_i18n: {
							[defaultLocale]: formik.values.description,
						},
						elementInstances,
						title_i18n: {[defaultLocale]: formik.values.title},
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
				setInitialSuccessToast(
					Liferay.Language.get('the-blueprint-was-saved-successfully')
				);

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

	/**
	 * This method must go before the useFormik hook.
	 */
	const _handleFormikValidate = (values) => {
		const errors = {};

		// Validate the elements added to the query builder

		const elementInstancesArray = [];

		values.elementInstances.map(
			({sxpElement, uiConfigurationValues}, index) => {
				const enabled =
					sxpElement.elementDefinition?.configuration
						?.queryConfiguration?.queryEntries?.[0]?.enabled;
				const uiConfiguration =
					sxpElement.elementDefinition?.uiConfiguration;

				if (isDefined(enabled) && !enabled) {
					return;
				}

				const configErrors = {};
				const fieldSets = cleanUIConfiguration(uiConfiguration)
					.fieldSets;

				if (
					fieldSets.length > 0 &&
					!isCustomJSONSXPElement(uiConfigurationValues)
				) {
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
				else if (isCustomJSONSXPElement(uiConfigurationValues)) {
					const configValue = uiConfigurationValues?.sxpElement;

					const configError =
						validateRequired(configValue, INPUT_TYPES.JSON) ||
						validateJSON(configValue, INPUT_TYPES.JSON);

					if (configError) {
						configErrors.sxpElement = configError;
					}
				}

				if (Object.keys(configErrors).length > 0) {
					elementInstancesArray[index] = {
						uiConfigurationValues: configErrors,
					};
				}
			}
		);

		if (elementInstancesArray.length > 0) {
			errors.elementInstances = elementInstancesArray;
		}

		// Validate all JSON inputs on the configuration tab

		[
			'advancedConfig',
			'aggregationConfig',
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
				initialConfiguration.advancedConfiguration,
				null,
				'\t'
			),
			aggregationConfig: JSON.stringify(
				initialConfiguration.aggregationConfiguration,
				null,
				'\t'
			),
			applyIndexerClauses:
				initialConfiguration.queryConfiguration?.applyIndexerClauses,
			description: getLocalizedText(initialDescription, defaultLocale),
			elementInstances: initialSXPElementInstances.map(
				(elementInstance, index) => ({
					...elementInstance,
					id: index,
				})
			),
			frameworkConfig: initialConfiguration.generalConfiguration || {
				clauseContributorsExcludes: [],
				clauseContributorsIncludes: [],
			},
			highlightConfig: JSON.stringify(
				initialConfiguration.highlightConfiguration,
				null,
				'\t'
			),
			parameterConfig: JSON.stringify(
				initialConfiguration.parameterConfiguration,
				null,
				'\t'
			),
			sortConfig: JSON.stringify(
				initialConfiguration.sortConfiguration,
				null,
				'\t'
			),
			title: getLocalizedText(initialTitle, defaultLocale),
		},
		onSubmit: _handleFormikSubmit,
		validate: _handleFormikValidate,
	});

	useShouldConfirmBeforeNavigate(formik.dirty && !formik.isSubmitting);

	useEffect(() => {
		fetchData(
			`/o/search-experiences-rest/v1.0/searchable-asset-names/${locale}`,
			{method: 'GET'},
			(responseContent) => setSearchableTypes(responseContent.items),
			() => setSearchableTypes([])
		);

		fetchData(
			`/o/search-experiences-rest/v1.0/field-mapping-infos`,
			{method: 'GET'},
			(responseContent) => setIndexFields(responseContent.items),
			() => setIndexFields([])
		);

		[
			{
				setProperty: setKeywordQueryContributors,
				url:
					'/o/search-experiences-rest/v1.0/keyword-query-contributors',
			},
			{
				setProperty: setModelPrefilterContributors,
				url:
					'/o/search-experiences-rest/v1.0/model-prefilter-contributors',
			},
			{
				setProperty: setQueryPrefilterContributors,
				url:
					'/o/search-experiences-rest/v1.0/query-prefilter-contributors',
			},
		].forEach(({setProperty, url}) =>
			fetchData(
				url,
				{method: 'GET'},
				(responseContent) =>
					setProperty(filterAndSortClassNames(responseContent.items)),
				() => setProperty([])
			)
		);

		setItemAddSXPElementSidebar('open');
	}, []); //eslint-disable-line

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
		frameworkConfig,
		highlightConfig,
		parameterConfig,
		sortConfig,
	}) => ({
		advancedConfiguration: advancedConfig ? JSON.parse(advancedConfig) : {},
		aggregationConfiguration: aggregationConfig
			? JSON.parse(aggregationConfig)
			: {},
		generalConfiguration: frameworkConfig,
		highlightConfiguration: highlightConfig
			? JSON.parse(highlightConfig)
			: {},
		parameterConfiguration: parameterConfig
			? JSON.parse(parameterConfig)
			: {},
		queryConfiguration: {
			applyIndexerClauses,
		},
		sortConfiguration: sortConfig ? JSON.parse(sortConfig) : {},
	});

	const _getElementInstances = (values) =>
		values.elementInstances.map(
			({
				id, // eslint-disable-line
				sxpElement,
				sxpElementId,
				type,
				uiConfigurationValues,
			}) => ({
				configurationEntry: getConfigurationEntry({
					sxpElement,
					uiConfigurationValues,
				}),
				sxpElement: parseCustomSXPElement(
					sxpElement,
					uiConfigurationValues
				),
				sxpElementId,
				type,
				uiConfigurationValues,
			})
		);

	const _handleAddSXPElement = (sxpElement) => {
		if (formik.touched?.elementInstances) {
			formik.setTouched({
				...formik.touched,
				elementInstances: [
					undefined,
					...formik.touched.elementInstances,
				],
			});
		}

		formik.setFieldValue('elementInstances', [
			{
				id: sxpElementIdCounterRef.current++,
				sxpElement,
				uiConfigurationValues: getUIConfigurationValues(sxpElement),
			},
			...formik.values.elementInstances,
		]);

		openSuccessToast({
			message: Liferay.Language.get('element-added'),
		});
	};

	const _handleApplyIndexerClausesChange = (value) => {
		formik.setFieldValue('applyIndexerClauses', value);
	};

	const _handleChangeTab = (tab) => {
		if (
			tab !== 'query-builder' &&
			(openSidebar === SIDEBARS.CLAUSE_CONTRIBUTORS ||
				openSidebar === SIDEBARS.INDEXER_CLAUSES)
		) {
			setOpenSidebar('');
		}

		setTab(tab);
	};

	const _handleChangeTitleAndDescription = ({description, title}) => {
		formik.setFieldValue('description', description);
		formik.setFieldValue('title', title);
	};

	const _handleCloseSidebar = () => {
		setOpenSidebar('');
	};

	const _handleDeleteSXPElement = (id) => {
		const index = formik.values.elementInstances.findIndex(
			(item) => item.id === id
		);

		if (formik.touched?.elementInstances) {
			formik.setTouched({
				...formik.touched,
				elementInstances: formik.touched.elementInstances.filter(
					(_, i) => i !== index
				),
			});
		}

		formik.setFieldValue(
			'elementInstances',
			formik.values.elementInstances.filter((item) => item.id !== id)
		);

		openSuccessToast({
			message: Liferay.Language.get('element-removed'),
		});
	};

	/**
	 * Used by the preview sidebar to perform searches.
	 * @param {string} query The keyword search query
	 * @param {number} delta The number of results to return
	 * @param {number} page The page to return
	 * @param {Array} attributes The search context attributes
	 */
	const _handleFetchPreviewSearch = async (
		query,
		delta,
		page,
		attributes
	) => {
		setPreviewInfo((previewInfo) => ({
			...previewInfo,
			loading: true,
		}));

		let configuration;
		let elementInstances;

		try {
			configuration = _getConfiguration(formik.values);
			elementInstances = _getElementInstances(formik.values);

			// Touch inputs with errors to show validation errors.

			const errors = await formik.validateForm();

			formik.setTouched(setNestedObjectValues(errors, true));

			// Don't perform a search if there are missing required fields.

			if (!formik.isValid) {
				throw Liferay.Language.get(
					'the-configuration-has-missing-or-invalid-values'
				);
			}
		}
		catch (error) {

			// Add a delay so the loading indicator is visible before showing
			// the error message. This provides feedback that a new search has
			// been made.

			setTimeout(() => {
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
			}, 100);

			if (process.env.NODE_ENV === 'development') {
				console.error(error);
			}

			return;
		}

		const parseResponseContent = (responseContent) => {
			const exceptionKey = 'java.lang.RuntimeException';

			if (
				responseContent.searchHits?.totalHits > 0 ||
				!responseContent.responseString?.startsWith(exceptionKey)
			) {
				return responseContent;
			}

			let exceptionClass;

			const exceptionKeyIndex = responseContent.responseString.indexOf(
				':',
				exceptionKey.length + 1
			);

			if (exceptionKeyIndex !== -1) {
				exceptionClass = responseContent.responseString.substring(
					exceptionKey.length + 1,
					exceptionKeyIndex
				);
			}

			let msg;

			const errorObjectIndex = responseContent.responseString.indexOf(
				'{"error":{'
			);

			if (errorObjectIndex > 0) {
				const errorJSONObject = JSON.parse(
					responseContent.responseString.substring(errorObjectIndex)
				);

				msg = errorJSONObject.error.root_cause[0]?.reason;
			}

			return getResultsError({
				exceptionClass,
				exceptionTrace: responseContent.responseString,
				msg,
			});
		};

		return fetchPreviewSearch(
			{
				page,
				pageSize: delta,
				query,
			},
			{
				body: JSON.stringify({
					configuration: {
						...configuration,
						generalConfiguration: {
							...configuration?.generalConfiguration,
							emptySearchEnabled: true,
							explain: true,
							includeResponseString: true,
							languageId: Liferay.ThemeDisplay.getLanguageId(),
						},
						searchContextAttributes: transformToSearchContextAttributes(
							attributes
						),
					},
					elementInstances,
				}),
			}
		)
			.then((response) => {
				if (!response.ok) {
					if (response.status === 400) {
						return response.json().then((json) => {
							return getResultsError({msg: json.title});
						});
					}
				}

				return response.json();
			})
			.then((responseContent) => {
				setPreviewInfo({
					loading: false,
					results: parseResponseContent(responseContent),
				});
			})
			.catch(() => {
				setTimeout(() => {
					setPreviewInfo({
						loading: false,
						results: getResultsError(),
					});
				}, 100);
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

	const _handleToggleSidebar = (type) => () => {
		if (openSidebar === SIDEBARS.ADD_SXP_ELEMENT) {
			setItemAddSXPElementSidebar('closed');
		}

		setOpenSidebar(openSidebar === type ? '' : type);
	};

	const _renderTabContent = () => {
		switch (tab) {
			case 'configuration':
				return (
					<ConfigurationTab
						advancedConfig={formik.values.advancedConfig}
						aggregationConfig={formik.values.aggregationConfig}
						errors={formik.errors}
						highlightConfig={formik.values.highlightConfig}
						parameterConfig={formik.values.parameterConfig}
						setFieldTouched={formik.setFieldTouched}
						setFieldValue={formik.setFieldValue}
						sortConfig={formik.values.sortConfig}
						touched={formik.touched}
					/>
				);
			default:
				return (
					<>
						<AddSXPElementSidebar
							onAddSXPElement={_handleAddSXPElement}
							onClose={_handleCloseSidebar}
							visible={openSidebar === SIDEBARS.ADD_SXP_ELEMENT}
						/>

						<ClauseContributorsSidebar
							frameworkConfig={formik.values.frameworkConfig}
							initialClauseContributorsList={[
								{
									label: 'KeywordQueryContributor',
									value: keywordQueryContributors,
								},
								{
									label: 'ModelPrefilterContributor',
									value: modelPrefilterContributors,
								},
								{
									label: 'QueryPrefilterContributor',
									value: queryPrefilterContributors,
								},
							]}
							onClose={_handleCloseSidebar}
							onFrameworkConfigChange={
								_handleFrameworkConfigChange
							}
							visible={
								openSidebar === SIDEBARS.CLAUSE_CONTRIBUTORS
							}
						/>

						<Sidebar
							className="info-sidebar"
							onClose={_handleCloseSidebar}
							title={Liferay.Language.get(
								'search-framework-indexer-clauses'
							)}
							visible={openSidebar === SIDEBARS.INDEXER_CLAUSES}
						>
							<div className="container-fluid text-secondary">
								<span className="help-text">
									{Liferay.Language.get(
										'search-framework-indexer-clauses-description'
									)}
								</span>

								<LearnMessage resourceKey="query-clause-contributors-configuration" />
							</div>
						</Sidebar>

						<div
							className={getCN({
								'open-add-sxp-element':
									openSidebar === SIDEBARS.ADD_SXP_ELEMENT,
								'open-clause-contributors':
									openSidebar ===
									SIDEBARS.CLAUSE_CONTRIBUTORS,
								'open-info':
									openSidebar === SIDEBARS.INDEXER_CLAUSES,
							})}
						>
							<QueryBuilderTab
								applyIndexerClauses={
									formik.values.applyIndexerClauses
								}
								clauseContributorsList={[
									...keywordQueryContributors,
									...modelPrefilterContributors,
									...queryPrefilterContributors,
								]}
								elementInstances={
									formik.values.elementInstances
								}
								entityJSON={entityJSON}
								errors={formik.errors.elementInstances}
								frameworkConfig={formik.values.frameworkConfig}
								indexFields={indexFields}
								isSubmitting={
									formik.isSubmitting || previewInfo.loading
								}
								onApplyIndexerClausesChange={
									_handleApplyIndexerClausesChange
								}
								onBlur={formik.handleBlur}
								onChange={formik.handleChange}
								onDeleteSXPElement={_handleDeleteSXPElement}
								onFrameworkConfigChange={
									_handleFrameworkConfigChange
								}
								openSidebar={openSidebar}
								searchableTypes={searchableTypes}
								setFieldTouched={formik.setFieldTouched}
								setFieldValue={formik.setFieldValue}
								setOpenSidebar={setOpenSidebar}
								touched={formik.touched.elementInstances}
							/>
						</div>
					</>
				);
		}
	};

	if (
		!indexFields ||
		!keywordQueryContributors ||
		!modelPrefilterContributors ||
		!queryPrefilterContributors ||
		!searchableTypes
	) {
		return null;
	}

	return (
		<form ref={formRef}>
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
				description={formik.values.description}
				isSubmitting={formik.isSubmitting}
				onCancel={redirectURL}
				onChangeTab={_handleChangeTab}
				onChangeTitleAndDescription={_handleChangeTitleAndDescription}
				onSubmit={_handleSubmit}
				tab={tab}
				tabs={TABS}
				title={formik.values.title}
			>
				<ClayToolbar.Item>
					<ClayButton
						borderless
						className={getCN({
							active: openSidebar === SIDEBARS.PREVIEW,
						})}
						data-testid={TEST_IDS.PREVIEW_SIDEBAR_BUTTON}
						displayType="secondary"
						onClick={_handleToggleSidebar(SIDEBARS.PREVIEW)}
						small
					>
						{Liferay.Language.get('preview')}
					</ClayButton>
				</ClayToolbar.Item>
			</PageToolbar>

			<PreviewSidebar
				errors={previewInfo.results.errors}
				hits={transformToSearchPreviewHits(previewInfo.results)}
				loading={previewInfo.loading}
				onClose={_handleCloseSidebar}
				onFetchResults={_handleFetchPreviewSearch}
				onFocusSXPElement={_handleFocusSXPElement}
				responseString={previewInfo.results.responseString}
				totalHits={previewInfo.results.searchHits?.totalHits}
				visible={openSidebar === SIDEBARS.PREVIEW}
				warnings={previewInfo.results.warnings}
			/>

			<div
				className={getCN({
					'open-preview': openSidebar === SIDEBARS.PREVIEW,
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
	initialSXPElementInstances: PropTypes.arrayOf(PropTypes.object),
	initialTitle: PropTypes.object,
	sxpBlueprintId: PropTypes.string,
};

export default React.memo(EditSXPBlueprintForm);
