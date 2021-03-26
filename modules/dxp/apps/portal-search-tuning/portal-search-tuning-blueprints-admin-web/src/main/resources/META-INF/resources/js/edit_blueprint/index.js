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

import ClayBadge from '@clayui/badge';
import ClayButton from '@clayui/button';
import ClayToolbar from '@clayui/toolbar';
import getCN from 'classnames';
import {useFormik} from 'formik';
import {fetch, navigate} from 'frontend-js-web';
import {PropTypes} from 'prop-types';
import React, {useContext, useRef, useState} from 'react';

import ErrorBoundary from '../shared/ErrorBoundary';
import PageToolbar from '../shared/PageToolbar';
import ThemeContext from '../shared/ThemeContext';
import {CUSTOM_JSON_ELEMENT} from '../utils/data';
import {INPUT_TYPES} from '../utils/inputTypes';
import {
	getElementOutput,
	getUIConfigurationValues,
	isDefined,
	openErrorToast,
	openSuccessToast,
	sub,
} from '../utils/utils';
import Preview from './Preview';
import Sidebar from './Sidebar';
import QueryBuilder from './tabs/QueryBuilder';
import Settings from './tabs/Settings';

// Tabs in display order

/* eslint-disable sort-keys */
const TABS = {
	'query-builder': Liferay.Language.get('query-builder'),
	settings: Liferay.Language.get('settings'),
};
/* eslint-enable sort-keys */

function EditBlueprintForm({
	blueprintId,
	entityJSON,
	initialConfigurationString = '{}',
	initialDescription = {},
	initialSelectedElementsString = '{}',
	indexFields,
	initialTitle = {},
	queryElements = [],
	redirectURL = '',
	searchableAssetTypes,
	searchResultsURL,
	submitFormURL = '',
}) {
	const {namespace} = useContext(ThemeContext);

	const [showSidebar, setShowSidebar] = useState(true);
	const [showPreview, setShowPreview] = useState(false);
	const [tab, setTab] = useState('query-builder');

	const form = useRef();
	const sidebarQueryElements = useRef([
		...queryElements,
		CUSTOM_JSON_ELEMENT,
	]);

	const initialConfiguration = JSON.parse(initialConfigurationString);
	const initialSelectedElements = JSON.parse(initialSelectedElementsString);

	const elementIdCounter = useRef(
		initialSelectedElements['query_configuration'].length
	);

	const validate = (values) => {
		const errors = {};

		const selectedQueryElementsArray = [];

		values.selectedQueryElements.map((element, index) => {
			const configErrors = {};

			if (
				element.uiConfigurationJSON &&
				element.uiConfigurationJSON.fieldSets
			) {
				element.uiConfigurationJSON.fieldSets.map(({fields = []}) => {
					fields.map(({name, type, typeOptions = {}}) => {
						const configValue = element.uiConfigurationValues[name];

						if (
							!typeOptions.optional &&
							(configValue === '' ||
								JSON.stringify(configValue) === '[]' ||
								(type === INPUT_TYPES.FIELD_MAPPING &&
									!configValue.field) ||
								(type === INPUT_TYPES.FIELD_MAPPING_LIST &&
									configValue.every(({field}) => !field)))
						) {
							configErrors[name] = Liferay.Language.get(
								'required'
							);
						}
						else if (
							(name === 'boost' && configValue < 0) ||
							(type === INPUT_TYPES.FIELD_MAPPING &&
								configValue.boost < 0) ||
							(type === INPUT_TYPES.FIELD_MAPPING_LIST &&
								configValue.some(({boost}) => boost < 0))
						) {
							configErrors[name] = Liferay.Language.get(
								'boost-must-be-greater-than-or-equal-to-0'
							);
						}
						else if (
							type === INPUT_TYPES.NUMBER ||
							type === INPUT_TYPES.SLIDER
						) {
							if (
								isDefined(typeOptions.min) &&
								configValue < typeOptions.min
							) {
								configErrors[name] = sub(
									Liferay.Language.get(
										'please-enter-a-value-greater-than-or-equal-to-x'
									),
									[typeOptions.min]
								);
							}

							if (
								isDefined(typeOptions.max) &&
								configValue > typeOptions.max
							) {
								configErrors[name] = sub(
									Liferay.Language.get(
										'please-enter-a-value-less-than-or-equal-to-x'
									),
									[typeOptions.max]
								);
							}
						}
						else if (type === INPUT_TYPES.JSON) {
							try {
								JSON.parse(configValue);
							}
							catch {
								configErrors[name] = Liferay.Language.get(
									'unable-to-apply-changes-due-to-invalid-json'
								);
							}
						}
					});
				});
			}
			else {
				const configValue =
					element.uiConfigurationValues.elementTemplateJSON;

				if (isDefined(configValue)) {
					try {
						JSON.parse(configValue);
					}
					catch {
						configErrors.elementTemplateJSON = Liferay.Language.get(
							'unable-to-apply-changes-due-to-invalid-json'
						);
					}

					if (configValue == '') {
						configErrors.elementTemplateJSON = Liferay.Language.get(
							'required'
						);
					}
				}
			}

			if (Object.keys(configErrors).length > 0) {
				selectedQueryElementsArray[index] = {
					uiConfigurationValues: configErrors,
				};
			}
		});

		if (selectedQueryElementsArray.length > 0) {
			errors.selectedQueryElements = selectedQueryElementsArray;
		}

		[
			'advancedConfig',
			'aggregationConfig',
			'facetConfig',
			'parameterConfig',
			'sortConfig',
		].map((configName) => {
			try {
				JSON.parse(values[configName]);
			}
			catch {
				errors[configName] = Liferay.Language.get(
					'unable-to-apply-changes-due-to-invalid-json'
				);
			}

			if (values[configName] == '') {
				errors[configName] = Liferay.Language.get('required');
			}
		});

		return errors;
	};

	const [previewInfo, setPreviewInfo] = useState(() => ({
		loading: false,
		results: {},
	}));

	const _handleFocusElement = (prefixedId) => {
		const element = document.getElementById(prefixedId);

		if (element) {
			window.scrollTo({
				behavior: 'smooth',
				top:
					element.getBoundingClientRect().top +
					window.pageYOffset -
					55 - // Control menu height
					104 - // Page toolbar height
					20, // Additional padding
			});

			element.classList.remove('focus');

			void element.offsetWidth; // Triggers reflow to restart animation

			element.classList.add('focus');
		}
	};

	const _handleSubmit = (values) => {
		const formData = new FormData(form.current);

		try {
			formData.append(
				`${namespace}configuration`,
				JSON.stringify({
					advanced_configuration: JSON.parse(values.advancedConfig),
					aggregation_configuration: JSON.parse(
						values.aggregationConfig
					),
					facet_configuration: JSON.parse(values.facetConfig),
					framework_configuration: values.frameworkConfig,
					parameter_configuration: JSON.parse(values.parameterConfig),
					query_configuration: values.selectedQueryElements.map(
						getElementOutput
					),
					sort_configuration: JSON.parse(values.sortConfig),
				})
			);

			formData.append(
				`${namespace}selectedElements`,
				JSON.stringify({
					query_configuration: values.selectedQueryElements.map(
						(item) =>
							item.uiConfigurationJSON
								? {
										elementTemplateJSON:
											item.elementTemplateJSON,
										uiConfigurationJSON:
											item.uiConfigurationJSON,
										uiConfigurationValues:
											item.uiConfigurationValues,
								  } // Removes ID field
								: {
										elementTemplateJSON: getElementOutput(
											item
										),
								  }
					),
				})
			);
		}
		catch {
			return;
		}

		formData.append(`${namespace}blueprintId`, blueprintId);
		formData.append(`${namespace}redirect`, redirectURL);

		return fetch(submitFormURL, {
			body: formData,
			method: 'POST',
		})
			.then((response) => response.json())
			.then((responseContent) => {
				if (
					Object.prototype.hasOwnProperty.call(
						responseContent,
						'errors'
					)
				) {
					responseContent.errors.forEach((message) =>
						openErrorToast({message})
					);
				}
				else {
					navigate(redirectURL);
				}
			})
			.catch(() => {
				openErrorToast();
			});
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
			frameworkConfig: initialConfiguration['framework_configuration'],
			parameterConfig: JSON.stringify(
				initialConfiguration['parameter_configuration'],
				null,
				'\t'
			),
			selectedQueryElements: initialSelectedElements[
				'query_configuration'
			].map((selectedElement, index) => ({
				...selectedElement,
				id: index,
			})),
			sortConfig: JSON.stringify(
				initialConfiguration['sort_configuration'],
				null,
				'\t'
			),
		},
		onSubmit: _handleSubmit,
		validate,
	});

	const _handleFetchPreviewSearch = (value, delta, page) => {
		setPreviewInfo((previewInfo) => ({
			...previewInfo,
			loading: true,
		}));

		if (!formik.isValid) {
			setPreviewInfo({
				loading: false,
				results: {
					errors: [
						{
							msg: Liferay.Language.get('the-json-is-invalid'),
							severity: Liferay.Language.get('error'),
						},
					],
				},
			});

			return;
		}

		const formData = new FormData(form.current);

		try {
			formData.append(
				`${namespace}configuration`,
				JSON.stringify({
					advanced_configuration: JSON.parse(
						formik.values.advancedConfig
					),
					aggregation_configuration: JSON.parse(
						formik.values.aggregationConfig
					),
					facet_configuration: JSON.parse(formik.values.facetConfig),
					framework_configuration: formik.values.frameworkConfig,
					parameter_configuration: JSON.parse(
						formik.values.parameterConfig
					),
					query_configuration: formik.values.selectedQueryElements.map(
						getElementOutput
					),
					sort_configuration: JSON.parse(formik.values.sortConfig),
				})
			);
		}
		catch {
			return;
		}

		formData.append(`${namespace}page`, page);
		formData.append(`${namespace}q`, value);
		formData.append(`${namespace}size`, delta);

		return fetch(searchResultsURL, {
			body: formData,
			method: 'POST',
		})
			.then((response) => response.json())
			.then((responseContent) => {
				setPreviewInfo({
					loading: false,
					results: responseContent,
				});
			})
			.catch(() => {
				setTimeout(() => {
					setPreviewInfo({
						loading: false,
						results: {
							errors: [
								{
									msg: Liferay.Language.get(
										'the-json-is-invalid'
									),
									severity: Liferay.Language.get('error'),
								},
							],
						},
					});
				}, 1000);
			});
	};

	const _handleAddElement = (element) => {
		if (formik.touched && formik.touched.selectedQueryElements) {
			formik.setTouched({
				...formik.touched,
				selectedQueryElements: [
					undefined,
					...formik.touched.selectedQueryElements,
				],
			});
		}

		formik.setFieldValue('selectedQueryElements', [
			{
				...element,
				id: elementIdCounter.current++,
				uiConfigurationValues: getUIConfigurationValues(
					element.uiConfigurationJSON
				),
			},
			...formik.values.selectedQueryElements,
		]);
	};

	const _handleDeleteElement = (id) => {
		const index = formik.values.selectedQueryElements.findIndex(
			(item) => item.id == id
		);

		if (formik.touched && formik.touched.selectedQueryElements) {
			formik.setTouched({
				...formik.touched,
				selectedQueryElements: formik.touched.selectedQueryElements.filter(
					(_, i) => i !== index
				),
			});
		}

		formik.setFieldValue(
			'selectedQueryElements',
			formik.values.selectedQueryElements.filter((item) => item.id !== id)
		);

		openSuccessToast({
			message: Liferay.Language.get('element-removed'),
		});
	};

	const _renderTabContent = () => {
		switch (tab) {
			case 'settings':
				return (
					<Settings
						advancedConfig={formik.values.advancedConfig}
						aggregationConfig={formik.values.aggregationConfig}
						errors={formik.errors}
						facetConfig={formik.values.facetConfig}
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
						<Preview
							loading={previewInfo.loading}
							onClose={() => setShowPreview(false)}
							onFetchResults={_handleFetchPreviewSearch}
							onFocusElement={_handleFocusElement}
							results={previewInfo.results}
							visible={showPreview}
						/>

						<Sidebar
							elements={sidebarQueryElements.current}
							onAddElement={_handleAddElement}
							onClose={() => setShowSidebar(false)}
							visible={showSidebar}
						/>

						<div
							className={getCN('query-builder', {
								'open-preview': showPreview,
								'open-sidebar': showSidebar,
							})}
						>
							<QueryBuilder
								entityJSON={entityJSON}
								errors={formik.errors.selectedQueryElements}
								frameworkConfig={formik.values.frameworkConfig}
								indexFields={indexFields}
								onBlur={formik.handleBlur}
								onChange={formik.handleChange}
								onDeleteElement={_handleDeleteElement}
								onFrameworkConfigChange={(value) =>
									formik.setFieldValue('frameworkConfig', {
										...formik.values.frameworkConfig,
										...value,
									})
								}
								onToggleSidebar={() => {
									setShowPreview(false);
									setShowSidebar(!showSidebar);
								}}
								searchableAssetTypes={searchableAssetTypes}
								selectedElements={
									formik.values.selectedQueryElements
								}
								setFieldTouched={formik.setFieldTouched}
								setFieldValue={formik.setFieldValue}
								touched={formik.touched.selectedQueryElements}
							/>
						</div>
					</>
				);
		}
	};

	return (
		<form ref={form}>
			<PageToolbar
				initialDescription={initialDescription}
				initialTitle={initialTitle}
				isSubmitting={formik.isSubmitting}
				onCancel={redirectURL}
				onChangeTab={(tab) => setTab(tab)}
				onSubmit={(event) => {
					event.preventDefault();

					formik.handleSubmit();

					if (!formik.isValid) {
						openErrorToast({
							message: Liferay.Language.get(
								'unable-to-save-due-to-invalid-or-missing-configuration-values'
							),
						});
					}
				}}
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

						{previewInfo.results.errors &&
							!!previewInfo.results.errors.length && (
								<span className="inline-item inline-item-after">
									<ClayBadge
										displayType="danger"
										label={
											previewInfo.results.errors.length
										}
									/>
								</span>
							)}
					</ClayButton>
				</ClayToolbar.Item>
			</PageToolbar>

			{_renderTabContent()}
		</form>
	);
}

EditBlueprintForm.propTypes = {
	blueprintId: PropTypes.string,
	entityJSON: PropTypes.object,
	initialConfigurationString: PropTypes.string,
	initialDescription: PropTypes.object,
	initialSelectedElementsString: PropTypes.string,
	initialTitle: PropTypes.object,
	queryElements: PropTypes.arrayOf(PropTypes.object),
	redirectURL: PropTypes.string,
	searchResultsURL: PropTypes.string,
	searchableAssetTypes: PropTypes.arrayOf(PropTypes.string),
	submitFormURL: PropTypes.string,
};

React.memo(EditBlueprintForm);

export default function ({context, props}) {
	return (
		<ThemeContext.Provider value={context}>
			<div className="edit-blueprint-root">
				<ErrorBoundary>
					<EditBlueprintForm {...props} />
				</ErrorBoundary>
			</div>
		</ThemeContext.Provider>
	);
}
