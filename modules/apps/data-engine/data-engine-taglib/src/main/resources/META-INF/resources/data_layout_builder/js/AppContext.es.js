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

import {PagesVisitor} from 'data-engine-js-components-web';
import {FieldSupport} from 'dynamic-data-mapping-form-builder';
import {createContext} from 'react';

import {
	ADD_CUSTOM_OBJECT_FIELD,
	ADD_DATA_LAYOUT_RULE,
	DELETE_DATA_DEFINITION_FIELD,
	DELETE_DATA_LAYOUT_FIELD,
	DELETE_DATA_LAYOUT_RULE,
	EDIT_CUSTOM_OBJECT_FIELD,
	SET_FORM_RENDERER_CUSTOM_FIELDS,
	SWITCH_SIDEBAR_PANEL,
	UPDATE_APP_PROPS,
	UPDATE_CONFIG,
	UPDATE_DATA_DEFINITION,
	UPDATE_DATA_DEFINITION_AVAILABLE_LANGUAGE,
	UPDATE_DATA_DEFINITION_FIELDS,
	UPDATE_DATA_LAYOUT,
	UPDATE_DATA_LAYOUT_FIELDS,
	UPDATE_DATA_LAYOUT_NAME,
	UPDATE_DATA_LAYOUT_RULE,
	UPDATE_EDITING_DATA_DEFINITION_ID,
	UPDATE_EDITING_LANGUAGE_ID,
	UPDATE_FIELDSETS,
	UPDATE_FIELD_TYPES,
	UPDATE_FOCUSED_CUSTOM_OBJECT_FIELD,
	UPDATE_FOCUSED_FIELD,
	UPDATE_HOVERED_FIELD,
	UPDATE_IDS,
	UPDATE_PAGES,
} from './actions.es';
import {
	getDDMFormFieldSettingsContext,
	getDataDefinitionAndDataLayout,
	getDataDefinitionField as convertFieldToDataDefinition,
} from './utils/dataConverter.es';
import {getDataDefinitionField} from './utils/dataDefinition.es';
import * as DataLayoutVisitor from './utils/dataLayoutVisitor.es';
import {normalizeRule} from './utils/normalizers.es';

const AppContext = createContext();

const initialState = {
	appProps: {},
	config: {
		allowFieldSets: false,
		allowNestedFields: true,
		allowRules: false,
		disabledProperties: [],
		disabledTabs: [],
		multiPage: true,
		ruleSettings: {},
		unimplementedProperties: [],
	},
	dataDefinition: {
		availableLanguageIds: [],
		dataDefinitionFields: [],
		defaultLanguageId: themeDisplay.getDefaultLanguageId(),
		name: {},
	},
	dataDefinitionId: 0,
	dataLayout: {
		dataLayoutFields: {},
		dataLayoutPages: [],
		dataRules: [],
		name: {},
		paginationMode: 'wizard',
	},
	dataLayoutId: 0,
	editingDataDefinitionId: 0,
	editingLanguageId: themeDisplay.getDefaultLanguageId(),
	fieldSets: [],
	fieldTypes: [],
	focusedCustomObjectField: {},
	focusedField: {},
	hoveredField: {},
	initialAvailableLanguageIds: [],
	sidebarOpen: true,
	sidebarPanelId: 'fields',
	spritemap: `${Liferay.ThemeDisplay.getPathThemeImages()}/clay/icons.svg`,
};

const addCustomObjectField = ({fieldTypeName, fieldTypes}) => {
	const fieldType = fieldTypes.find(({name}) => name === fieldTypeName);
	const dataDefinitionField = convertFieldToDataDefinition(fieldType);

	return {
		...dataDefinitionField,
		label: {
			[themeDisplay.getLanguageId()]: fieldType.label,
		},
		name: FieldSupport.getDefaultFieldName(),
	};
};

const deleteDataDefinitionField = (dataDefinition, fieldName) => {
	return {
		...dataDefinition,
		dataDefinitionFields: dataDefinition.dataDefinitionFields.filter(
			(field) => field.name !== fieldName
		),
	};
};

const deleteDataLayoutField = (dataLayout, fieldName) => {
	return {
		...dataLayout,
		dataLayoutFields: {
			...dataLayout.dataLayoutFields,
			[fieldName]: {
				...dataLayout.dataLayoutFields[fieldName],
				required: false,
			},
		},
		dataLayoutPages: DataLayoutVisitor.deleteField(
			dataLayout.dataLayoutPages,
			fieldName
		),
	};
};

const editFocusedCustomObjectField = (
	{propertyName, propertyValue},
	{
		dataDefinition: {defaultLanguageId},
		editingLanguageId,
		focusedCustomObjectField,
	}
) => {
	let localizedValue;
	const {
		nestedFields,
		settingsContext: oldSettingsContext,
	} = focusedCustomObjectField;
	const visitor = new PagesVisitor(oldSettingsContext.pages);
	const settingsContext = {
		...oldSettingsContext,
		pages: visitor.mapFields((field) => {
			const {fieldName} = field;

			if (fieldName === propertyName) {
				localizedValue = {
					...field.localizedValue,
					[editingLanguageId || defaultLanguageId]: propertyValue,
				};

				return {
					...field,
					localizedValue,
					value: propertyValue,
				};
			}

			return field;
		}),
	};

	const dataDefinition = convertFieldToDataDefinition({
		nestedFields,
		settingsContext,
	});

	return {
		...dataDefinition,
		settingsContext,
	};
};

/**
 * Get unformatted definition field
 * @param {object} dataDefinition
 * @param {object} field
 */
const getDefinitionField = (dataDefinition, {fieldName}) => {
	return getDataDefinitionField(dataDefinition, fieldName);
};

const setDataDefinitionFields = (
	dataLayoutBuilder,
	dataDefinition,
	dataLayout
) => {
	const {dataDefinitionFields} = dataDefinition;
	const {dataLayoutPages} = dataLayout;

	const {
		pages,
	} = dataLayoutBuilder.formBuilderWithLayoutProvider.refs.layoutProvider.state;
	const visitor = new PagesVisitor(pages);

	const newFields = [];

	visitor.mapFields((field) => {
		const convertedField = convertFieldToDataDefinition(field);

		if (dataLayoutBuilder.props.contentType === 'app-builder') {
			const definitionField = getDefinitionField(dataDefinition, field);

			newFields.push({
				...convertedField,
				customProperties: {
					...convertedField.customProperties,
					labelAtStructureLevel:
						definitionField?.customProperties
							?.labelAtStructureLevel ??
						convertedField?.customProperties?.labelAtStructureLevel,
				},
				label: definitionField?.label ?? convertedField?.label,
				required: !!definitionField?.required,
			});
		}
		else {
			newFields.push(convertedField);
		}
	});

	return newFields.concat(
		dataDefinitionFields.filter(
			(field) =>
				!DataLayoutVisitor.containsField(dataLayoutPages, field.name) &&
				!newFields.some(({name}) => name === field.name)
		)
	);
};

const setDataLayout = (dataLayout, dataLayoutBuilder) => {
	const {dataLayoutFields, dataRules: rules} = dataLayout;
	const layoutProvider =
		dataLayoutBuilder.formBuilderWithLayoutProvider.refs.layoutProvider;
	const {
		state: {pages},
	} = layoutProvider;

	const {
		availableLanguageIds,
		contentType,
		defaultLanguageId,
	} = dataLayoutBuilder.props;
	const {
		availableLanguageIds: availableLanguageIdsState,
	} = dataLayoutBuilder.state;

	const {layout} = getDataDefinitionAndDataLayout({
		availableLanguageIds: availableLanguageIdsState ?? availableLanguageIds,
		defaultLanguageId,
		pages,
		paginationMode: layoutProvider.getPaginationMode(),
		rules,
	});

	if (contentType === 'app-builder') {
		const visitor = new PagesVisitor(pages);
		const fields = [];

		visitor.mapFields((field) => {
			const formattedDefinitionField = convertFieldToDataDefinition(
				field
			);

			fields.push(formattedDefinitionField);
		});

		return {
			...layout,
			dataLayoutFields: fields.reduce((allFields, field) => {
				return {
					...allFields,
					[field.name]: {
						...dataLayoutFields[field.name],
						required: !!field?.required,
					},
				};
			}, {}),
		};
	}

	return layout;
};

const createReducer = (dataLayoutBuilder) => {
	return (state = initialState, action) => {
		switch (action.type) {
			case ADD_CUSTOM_OBJECT_FIELD: {
				const {fieldTypeName} = action.payload;
				const {dataDefinition, fieldSets, fieldTypes} = state;
				const newCustomObjectField = addCustomObjectField({
					dataDefinition,
					fieldSets,
					fieldTypeName,
					fieldTypes,
				});

				const {
					appContext: [{editingLanguageId}],
				} = dataLayoutBuilder.props;

				const settingsContext = getDDMFormFieldSettingsContext({
					dataDefinitionField: newCustomObjectField,
					editingLanguageId,
					fieldTypes,
				});

				return {
					...state,
					dataDefinition: {
						...dataDefinition,
						dataDefinitionFields: [
							...dataDefinition.dataDefinitionFields,
							newCustomObjectField,
						],
					},
					focusedCustomObjectField: {
						...newCustomObjectField,
						settingsContext,
					},
				};
			}
			case ADD_DATA_LAYOUT_RULE: {
				let {dataRule} = action.payload;
				const {
					dataLayout: {dataRules},
				} = state;

				dataRule = normalizeRule(dataRule);

				return {
					...state,
					dataLayout: {
						...state.dataLayout,
						dataRules: dataRules.concat(dataRule),
					},
				};
			}
			case DELETE_DATA_DEFINITION_FIELD: {
				const {fieldName} = action.payload;
				const {dataDefinition} = state;

				return {
					...state,
					dataDefinition: deleteDataDefinitionField(
						dataDefinition,
						fieldName
					),
				};
			}
			case DELETE_DATA_LAYOUT_FIELD: {
				const {fieldName} = action.payload;
				const {dataLayout} = state;

				return {
					...state,
					dataLayout: deleteDataLayoutField(dataLayout, fieldName),
				};
			}
			case DELETE_DATA_LAYOUT_RULE: {
				const {ruleEditedIndex} = action.payload;

				const {
					dataLayout: {dataRules},
				} = state;

				dataLayoutBuilder.formBuilderWithLayoutProvider.refs.layoutProvider?.dispatch?.(
					'ruleDeleted',
					{
						ruleId: ruleEditedIndex,
					}
				);

				return {
					...state,
					dataLayout: {
						...state.dataLayout,
						dataRules: dataRules.filter(
							(_rule, index) => index !== ruleEditedIndex
						),
					},
				};
			}
			case EDIT_CUSTOM_OBJECT_FIELD: {
				const {dataDefinition, focusedCustomObjectField} = state;

				const focusedDataDefinitionField = editFocusedCustomObjectField(
					action.payload,
					state
				);

				return {
					...state,
					dataDefinition: {
						...dataDefinition,
						dataDefinitionFields: dataDefinition.dataDefinitionFields.map(
							(dataDefinitionField) => {
								if (
									dataDefinitionField.name ===
									focusedCustomObjectField.name
								) {
									return focusedDataDefinitionField;
								}

								return dataDefinitionField;
							}
						),
					},
					focusedCustomObjectField: focusedDataDefinitionField,
				};
			}
			case SET_FORM_RENDERER_CUSTOM_FIELDS: {
				return {
					...state,
					customFields: action.payload,
				};
			}
			case SWITCH_SIDEBAR_PANEL: {
				const {sidebarOpen, sidebarPanelId} = action.payload;

				if ((sidebarOpen || sidebarOpen === false) && sidebarPanelId) {
					return {
						...state,
						sidebarOpen,
						sidebarPanelId,
					};
				}
				else {
					return {
						...state,
					};
				}
			}
			case UPDATE_APP_PROPS: {
				return {
					...state,
					appProps: action.payload,
				};
			}
			case UPDATE_DATA_DEFINITION: {
				const {dataDefinition} = action.payload;

				return {
					...state,
					dataDefinition: {
						...state.dataDefinition,
						...dataDefinition,
					},
					initialAvailableLanguageIds:
						dataDefinition.availableLanguageIds,
				};
			}
			case UPDATE_DATA_DEFINITION_AVAILABLE_LANGUAGE: {
				const {dataDefinition} = state;

				return {
					...state,
					dataDefinition: {
						...dataDefinition,
						availableLanguageIds: [
							...new Set([
								...dataDefinition.availableLanguageIds,
								action.payload,
							]),
						],
					},
				};
			}
			case UPDATE_DATA_DEFINITION_FIELDS: {
				const {dataDefinitionFields} = action.payload;

				return {
					...state,
					dataDefinition: {
						...state.dataDefinition,
						dataDefinitionFields,
					},
				};
			}
			case UPDATE_DATA_LAYOUT: {
				const {dataLayout} = action.payload;

				return {
					...state,
					dataLayout: {
						...state.dataLayout,
						...dataLayout,
						dataRules: dataLayoutBuilder.formBuilderWithLayoutProvider.refs.layoutProvider.getRules(),
					},
				};
			}
			case UPDATE_DATA_LAYOUT_FIELDS: {
				const {dataLayoutFields} = action.payload;

				return {
					...state,
					dataLayout: {
						...state.dataLayout,
						dataLayoutFields,
					},
				};
			}
			case UPDATE_DATA_LAYOUT_NAME: {
				const {name} = action.payload;

				return {
					...state,
					dataLayout: {
						...state.dataLayout,
						name,
					},
				};
			}
			case UPDATE_DATA_LAYOUT_RULE: {
				const {dataRule, loc} = action.payload;
				const {
					dataLayout: {dataRules},
				} = state;

				return {
					...state,
					dataLayout: {
						...state.dataLayout,
						dataRules: dataRules.map((rule, index) => {
							if (index === loc) {
								return normalizeRule(dataRule);
							}

							return rule;
						}),
					},
				};
			}
			case UPDATE_EDITING_DATA_DEFINITION_ID: {
				const {editingDataDefinitionId} = action.payload;

				return {
					...state,
					editingDataDefinitionId,
				};
			}
			case UPDATE_EDITING_LANGUAGE_ID: {
				return {
					...state,
					editingLanguageId: action.payload,
				};
			}
			case UPDATE_FIELD_TYPES: {
				const {fieldTypes} = action.payload;

				return {
					...state,
					fieldTypes,
				};
			}
			case UPDATE_FIELDSETS: {
				const {dataDefinitionId, editingDataDefinitionId} = state;
				let {fieldSets} = action.payload;

				if (dataDefinitionId) {
					fieldSets = fieldSets.filter(
						(item) => item.id !== dataDefinitionId
					);
				}

				if (editingDataDefinitionId) {
					fieldSets = fieldSets.filter(
						(item) => item.id !== editingDataDefinitionId
					);
				}

				return {
					...state,
					fieldSets,
				};
			}
			case UPDATE_FOCUSED_CUSTOM_OBJECT_FIELD: {
				const {dataDefinitionField} = action.payload;
				let focusedCustomObjectField = {};

				if (Object.keys(dataDefinitionField).length > 0) {
					const {
						appContext: [{editingLanguageId}],
						fieldTypes,
					} = dataLayoutBuilder.props;

					const settingsContext = getDDMFormFieldSettingsContext({
						dataDefinitionField,
						editingLanguageId,
						fieldTypes,
					});

					focusedCustomObjectField = {
						...dataDefinitionField,
						settingsContext,
					};

					return {
						...state,
						focusedCustomObjectField,
						focusedField: {},
					};
				}

				return {
					...state,
					focusedCustomObjectField: {},
				};
			}
			case UPDATE_FOCUSED_FIELD: {
				const {focusedField} = action.payload;

				if (Object.keys(focusedField).length > 0) {
					return {
						...state,
						focusedCustomObjectField: {},
						focusedField: {
							...focusedField,
							settingsContext: {
								...focusedField.settingsContext,
								editingLanguageId: state.editingLanguageId,
							},
						},
						sidebarOpen: true,
						sidebarPanelId: 'fields',
					};
				}

				return {
					...state,
					focusedCustomObjectField: {},
					focusedField: {},
				};
			}
			case UPDATE_HOVERED_FIELD: {
				const {hoveredField = {}} = action.payload;

				const newHoveredField = getDataDefinitionField(
					state.dataDefinition,
					hoveredField.fieldName
				);

				return {
					...state,
					hoveredField: newHoveredField || state.hoveredField,
				};
			}
			case UPDATE_CONFIG: {
				const {config} = action.payload;

				return {
					...state,
					config: config || state.config,
				};
			}
			case UPDATE_IDS: {
				const {dataDefinitionId, dataLayoutId} = action.payload;

				return {
					...state,
					dataDefinitionId,
					dataLayoutId,
				};
			}
			case UPDATE_PAGES: {
				const {dataDefinition, dataLayout} = state;

				return {
					...state,
					dataDefinition: {
						...dataDefinition,
						dataDefinitionFields: setDataDefinitionFields(
							dataLayoutBuilder,
							dataDefinition,
							dataLayout
						),
					},
					dataLayout: {
						...dataLayout,
						...setDataLayout(dataLayout, dataLayoutBuilder),
					},
				};
			}
			default:
				return state;
		}
	};
};

export default AppContext;

export {initialState, createReducer};
