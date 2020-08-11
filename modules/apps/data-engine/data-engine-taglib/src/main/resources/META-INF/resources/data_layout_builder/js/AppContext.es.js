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

import {PagesVisitor} from 'dynamic-data-mapping-form-renderer';
import {createContext} from 'react';

import {
	ADD_CUSTOM_OBJECT_FIELD,
	ADD_DATA_LAYOUT_RULE,
	DELETE_DATA_DEFINITION_FIELD,
	DELETE_DATA_LAYOUT_FIELD,
	DELETE_DATA_LAYOUT_RULE,
	EDIT_CUSTOM_OBJECT_FIELD,
	SWITCH_SIDEBAR_PANEL,
	UPDATE_APP_PROPS,
	UPDATE_CONFIG,
	UPDATE_DATA_DEFINITION,
	UPDATE_DATA_LAYOUT,
	UPDATE_DATA_LAYOUT_NAME,
	UPDATE_DATA_LAYOUT_RULE,
	UPDATE_EDITING_DATA_DEFINITION_ID,
	UPDATE_EDITING_LANGUAGE_ID,
	UPDATE_FIELDSETS,
	UPDATE_FIELD_TYPES,
	UPDATE_FOCUSED_CUSTOM_OBJECT_FIELD,
	UPDATE_FOCUSED_FIELD,
	UPDATE_IDS,
	UPDATE_PAGES,
} from './actions.es';
import {getAllDataDefinitionFieldsFromAllFieldSets} from './utils/dataDefinition.es';
import * as DataLayoutVisitor from './utils/dataLayoutVisitor.es';
import generateDataDefinitionFieldName from './utils/generateDataDefinitionFieldName.es';

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
		name: {},
	},
	dataDefinitionId: 0,
	dataLayout: {
		dataLayoutPages: [],
		dataRules: [],
		name: {},
		paginationMode: 'wizard',
	},
	dataLayoutId: 0,
	editingDataDefinitionId: 0,
	editingLanguageId: themeDisplay.getLanguageId(),
	fieldSets: [],
	fieldTypes: [],
	focusedCustomObjectField: {},
	focusedField: {},
	sidebarOpen: true,
	sidebarPanelId: 'fields',
	spritemap: `${Liferay.ThemeDisplay.getPathThemeImages()}/lexicon/icons.svg`,
};

const addCustomObjectField = ({
	dataDefinition,
	dataLayoutBuilder,
	fieldSets,
	fieldTypeName,
	fieldTypes,
}) => {
	const fieldType = fieldTypes.find(({name}) => name === fieldTypeName);
	const dataDefinitionField = dataLayoutBuilder.getDataDefinitionField(
		fieldType
	);

	return {
		...dataDefinitionField,
		label: {
			[themeDisplay.getLanguageId()]: fieldType.label,
		},
		name: generateDataDefinitionFieldName(
			[
				...dataDefinition.dataDefinitionFields,
				...getAllDataDefinitionFieldsFromAllFieldSets(fieldSets),
			],
			fieldType.label
		),
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
		dataLayoutPages: DataLayoutVisitor.deleteField(
			dataLayout.dataLayoutPages,
			fieldName
		),
	};
};

const editFocusedCustomObjectField = ({
	focusedCustomObjectField,
	propertyName,
	propertyValue,
}) => {
	let localizableProperty = false;
	const {settingsContext} = focusedCustomObjectField;
	const visitor = new PagesVisitor(settingsContext.pages);
	const newSettingsContext = {
		...settingsContext,
		pages: visitor.mapFields((field) => {
			const {fieldName, localizable} = field;

			if (fieldName === propertyName) {
				localizableProperty = localizable;

				return {
					...field,
					localizedValue: {
						...field.localizedValue,
						[themeDisplay.getLanguageId()]: propertyValue,
					},
					value: propertyValue,
				};
			}

			return field;
		}),
	};

	if (localizableProperty) {
		propertyValue = {
			[themeDisplay.getLanguageId()]: propertyValue,
		};
	}

	return {
		...focusedCustomObjectField,
		[propertyName]: propertyValue,
		settingsContext: newSettingsContext,
	};
};

const setDataDefinitionFields = (
	dataLayoutBuilder,
	dataDefinition,
	dataLayout
) => {
	const {dataDefinitionFields} = dataDefinition;
	const {dataLayoutPages} = dataLayout;

	const {pages} = dataLayoutBuilder.getStore();
	const visitor = new PagesVisitor(pages);

	const newFields = [];

	visitor.mapFields((field) => {
		const definitionField = dataLayoutBuilder.getDataDefinitionField(field);

		newFields.push(definitionField);
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
	const {dataRules} = dataLayout;
	const {pages} = dataLayoutBuilder.getStore();
	const {layout} = dataLayoutBuilder.getDataDefinitionAndDataLayout(
		pages,
		dataRules || []
	);

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
					dataLayoutBuilder,
					fieldSets,
					fieldTypeName,
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
						settingsContext: dataLayoutBuilder.getDDMFormFieldSettingsContext(
							newCustomObjectField
						),
					},
				};
			}
			case ADD_DATA_LAYOUT_RULE: {
				let {dataRule} = action.payload;
				const {
					dataLayout: {dataRules},
				} = state;

				dataRule = DataLayoutVisitor.normalizeRule(dataRule);

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

				dataLayoutBuilder.dispatch('ruleDeleted', {
					ruleId: ruleEditedIndex,
				});

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
				const editedFocusedCustomObjectField = editFocusedCustomObjectField(
					{
						...action.payload,
						focusedCustomObjectField,
					}
				);
				const {
					nestedDataDefinitionFields,
					settingsContext,
				} = editedFocusedCustomObjectField;

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
									return {
										...dataLayoutBuilder.getDataDefinitionField(
											editedFocusedCustomObjectField
										),
										nestedDataDefinitionFields,
									};
								}

								return dataDefinitionField;
							}
						),
					},
					focusedCustomObjectField: {
						...editedFocusedCustomObjectField,
						settingsContext,
					},
				};
			}
			case SWITCH_SIDEBAR_PANEL: {
				const {sidebarOpen, sidebarPanelId} = action.payload;

				return {
					...state,
					sidebarOpen,
					sidebarPanelId,
				};
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
				};
			}
			case UPDATE_DATA_LAYOUT: {
				const {dataLayout} = action.payload;

				return {
					...state,
					dataLayout: {
						...state.dataLayout,
						...dataLayout,
						dataRules: dataLayoutBuilder
							.getLayoutProvider()
							.getRules(),
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
				let {dataRule} = action.payload;
				const {
					dataLayout: {dataRules},
				} = state;

				dataRule = DataLayoutVisitor.normalizeRule(dataRule);

				return {
					...state,
					dataLayout: {
						...state.dataLayout,
						dataRules: dataRules.map((rule, index) => {
							if (index === dataRule.ruleEditedIndex) {
								return dataRule;
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
					focusedCustomObjectField = {
						...dataDefinitionField,
						settingsContext: dataLayoutBuilder.getDDMFormFieldSettingsContext(
							dataDefinitionField
						),
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
						sidebarPanelId: 'fields',
					};
				}

				return {
					...state,
					focusedCustomObjectField: {},
					focusedField: {},
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
