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

import {
	FieldSupport,
	FieldUtil,
	RulesSupport,
	RulesUtil,
	SettingsContext,
} from 'dynamic-data-mapping-form-builder';
import sectionAdded from 'dynamic-data-mapping-form-builder/js/components/LayoutProvider/handlers/sectionAddedHandler.es';

import * as FormSupport from '../../utils/FormSupport.es';
import {PagesVisitor} from '../../utils/visitors.es';
import {EVENT_TYPES} from '../actions/eventTypes.es';

export const deleteField = ({
	clean = false,
	defaultLanguageId,
	editingLanguageId,
	fieldName,
	fieldNameGenerator,
	fieldPage,
	generateFieldNameUsingFieldLabel,
	pages,
}) =>
	pages.map((page, pageIndex) => {
		if (fieldPage === pageIndex) {
			const pagesWithFieldRemoved = FieldSupport.removeField(
				{
					defaultLanguageId,
					editingLanguageId,
					fieldNameGenerator,
					generateFieldNameUsingFieldLabel,
				},
				pages,
				fieldName,
				clean
			);

			return {
				...page,
				rows: clean
					? FormSupport.removeEmptyRows(
							pagesWithFieldRemoved,
							pageIndex
					  )
					: pagesWithFieldRemoved[pageIndex].rows,
			};
		}

		return page;
	});

const updateFieldProperty = ({
	defaultLanguageId,
	editingLanguageId,
	fieldNameGenerator,
	focusedField,
	generateFieldNameUsingFieldLabel,
	pages,
	propertyName,
	propertyValue,
}) => {
	if (
		propertyName === 'fieldReference' &&
		propertyValue !== '' &&
		propertyValue !== focusedField.fieldName
	) {
		focusedField = SettingsContext.updateFieldReference(
			focusedField,
			FieldUtil.findInvalidFieldReference(
				focusedField,
				pages,
				propertyValue
			),
			false
		);
	}

	return SettingsContext.updateField(
		{
			defaultLanguageId,
			editingLanguageId,
			fieldNameGenerator,
			generateFieldNameUsingFieldLabel,
		},
		focusedField,
		propertyName,
		propertyValue
	);
};

/**
 * NOTE: This is a literal copy of the old LayoutProvider logic. Small changes
 * were made only to adapt to the reducer.
 */
export default (state, action, config) => {
	switch (action.type) {
		case EVENT_TYPES.FIELD.ADD: {
			const {
				data: {parentFieldName},
				indexes,
			} = action.payload;

			const {
				availableLanguageIds,
				defaultLanguageId,
				editingLanguageId,
				pages,
			} = state;
			const {
				generateFieldNameUsingFieldLabel,
				getFieldNameGenerator,
			} = config;

			const fieldNameGenerator = getFieldNameGenerator(
				pages,
				generateFieldNameUsingFieldLabel
			);

			const field =
				action.payload.newField ||
				FieldSupport.createField(
					{defaultLanguageId, editingLanguageId, fieldNameGenerator},
					action.payload
				);

			const settingsVisitor = new PagesVisitor(
				field.settingsContext.pages
			);

			const newField = {
				...field,
				settingsContext: {
					...field.settingsContext,
					availableLanguageIds,
					defaultLanguageId,
					pages: settingsVisitor.mapFields((field) =>
						FieldSupport.localizeField(
							field,
							defaultLanguageId,
							editingLanguageId
						)
					),
				},
			};

			return FieldSupport.addField({
				defaultLanguageId,
				editingLanguageId,
				fieldNameGenerator,
				generateFieldNameUsingFieldLabel,
				indexes,
				newField,
				pages,
				parentFieldName,
			});
		}
		case EVENT_TYPES.FIELD.BLUR: {
			const {propertyName, propertyValue} = action.payload;

			let focusedField = state.focusedField;

			if (
				Object.keys(focusedField).length &&
				propertyName === 'fieldReference' &&
				(propertyValue === '' ||
					FieldUtil.findInvalidFieldReference(
						focusedField,
						state.pages,
						propertyValue
					))
			) {
				const {defaultLanguageId, editingLanguageId} = state;

				focusedField = SettingsContext.updateField(
					{
						defaultLanguageId,
						editingLanguageId,
					},
					SettingsContext.updateFieldReference(
						focusedField,
						false,
						true
					),
					propertyName,
					focusedField.fieldName
				);
			}

			return {
				fieldHovered: {},
				focusedField,
			};
		}
		case EVENT_TYPES.FIELD.CLICK: {
			const {activePage, field} = action.payload;
			const {defaultLanguageId, editingLanguageId} = state;

			const visitor = new PagesVisitor(field.settingsContext.pages);

			const focusedField = {
				...field,
				settingsContext: {
					...field.settingsContext,
					currentPage: activePage,
					pages: visitor.mapFields((currentfield) => {
						const {fieldName} = currentfield;

						if (fieldName === 'validation') {
							currentfield = {
								...currentfield,
								validation: {
									...currentfield.validation,
									fieldName: field.fieldName,
								},
							};
						}

						return FieldSupport.localizeField(
							currentfield,
							defaultLanguageId,
							editingLanguageId
						);
					}),
				},
			};

			return {
				activePage,
				focusedField,
				previousFocusedField: focusedField,
			};
		}
		case EVENT_TYPES.FIELD.CHANGE: {
			const {fieldName, propertyName, propertyValue} = action.payload;
			const {
				defaultLanguageId,
				editingLanguageId,
				focusedField,
				pages,
				rules,
			} = state;
			const {
				generateFieldNameUsingFieldLabel,
				getFieldNameGenerator,
			} = config;

			const fieldNameGenerator = getFieldNameGenerator(
				pages,
				generateFieldNameUsingFieldLabel
			);

			if (propertyName === 'name' && propertyValue === '') {
				return state;
			}

			const newFocusedField = updateFieldProperty({
				defaultLanguageId,
				editingLanguageId,
				fieldNameGenerator,
				focusedField: fieldName
					? FieldSupport.getField(pages, fieldName)
					: focusedField,
				generateFieldNameUsingFieldLabel,
				pages,
				propertyName,
				propertyValue,
			});

			const visitor = new PagesVisitor(pages);

			return {
				focusedField: newFocusedField,
				pages: visitor.mapFields(
					(field) => {
						if (field.fieldName === newFocusedField.fieldName) {
							return newFocusedField;
						}

						return field;
					},
					true,
					true
				),
				rules: RulesUtil.updateRulesReferences(
					rules || [],
					focusedField,
					newFocusedField
				),
			};
		}
		case EVENT_TYPES.FIELD.DELETE: {
			const {
				activePage,
				editRule = true,
				fieldName,
				removeEmptyRows = true,
			} = action.payload;
			const {defaultLanguageId, editingLanguageId, pages, rules} = state;
			const {
				generateFieldNameUsingFieldLabel,
				getFieldNameGenerator,
			} = config;

			const fieldNameGenerator = getFieldNameGenerator(
				pages,
				generateFieldNameUsingFieldLabel
			);

			const newPages = deleteField({
				clean: removeEmptyRows,
				defaultLanguageId,
				editingLanguageId,
				fieldName,
				fieldNameGenerator,
				fieldPage: activePage ?? state.activePage,
				generateFieldNameUsingFieldLabel,
				pages,
			});

			return {
				focusedField: {},
				pages: newPages,
				rules: editRule
					? RulesSupport.formatRules(newPages, rules)
					: rules,
			};
		}
		case EVENT_TYPES.FIELD.DUPLICATE: {
			const {fieldName, parentFieldName} = action.payload;
			const {availableLanguageIds, defaultLanguageId, pages} = state;
			const {
				generateFieldNameUsingFieldLabel,
				getFieldNameGenerator,
			} = config;

			const fieldNameGenerator = getFieldNameGenerator(
				pages,
				generateFieldNameUsingFieldLabel
			);

			const originalField = JSON.parse(
				JSON.stringify(
					FormSupport.findFieldByFieldName(pages, fieldName)
				)
			);

			const newField = FieldUtil.createDuplicatedField(originalField, {
				availableLanguageIds,
				defaultLanguageId,
				fieldNameGenerator,
				generateFieldNameUsingFieldLabel,
			});

			let newPages = null;

			if (parentFieldName) {
				const visitor = new PagesVisitor(pages);

				newPages = visitor.mapFields(
					(field) => {
						if (field.fieldName === parentFieldName) {
							const nestedFields = field.nestedFields
								? [...field.nestedFields, newField]
								: [newField];

							field = SettingsContext.updateField(
								{
									availableLanguageIds,
									defaultLanguageId,
									fieldNameGenerator,
									generateFieldNameUsingFieldLabel,
								},
								field,
								'nestedFields',
								nestedFields
							);

							let pages = [{rows: field.rows}];

							const {
								pageIndex,
								rowIndex,
							} = FormSupport.getFieldIndexes(
								pages,
								originalField.fieldName
							);

							const newRow = FormSupport.implAddRow(12, [
								newField.fieldName,
							]);

							pages = FormSupport.addRow(
								pages,
								rowIndex + 1,
								pageIndex,
								newRow
							);

							return SettingsContext.updateField(
								{
									availableLanguageIds,
									defaultLanguageId,
									fieldNameGenerator,
									generateFieldNameUsingFieldLabel,
								},
								field,
								'rows',
								pages[0].rows
							);
						}

						return field;
					},
					true,
					true
				);
			}
			else {
				const {pageIndex, rowIndex} = FormSupport.getFieldIndexes(
					pages,
					originalField.fieldName
				);

				const newRow = FormSupport.implAddRow(12, [newField]);

				newPages = FormSupport.addRow(
					pages,
					rowIndex + 1,
					pageIndex,
					newRow
				);
			}

			return {
				focusedField: {
					...newField,
				},
				pages: newPages,
			};
		}
		case EVENT_TYPES.FIELD.EVALUATE: {
			const {settingsContextPages} = action.payload;
			const {
				defaultLanguageId,
				editingLanguageId,
				focusedField,
				pages,
				rules,
			} = state;
			const {
				generateFieldNameUsingFieldLabel,
				getFieldNameGenerator,
			} = config;

			const fieldName = FieldSupport.getField(
				settingsContextPages,
				'name'
			);
			const focusedFieldName = FieldSupport.getField(
				focusedField.settingsContext.pages,
				'name'
			);

			if (fieldName.instanceId !== focusedFieldName.instanceId) {
				return state;
			}

			const fieldNameGenerator = getFieldNameGenerator(
				pages,
				generateFieldNameUsingFieldLabel
			);

			let newFocusedField = {
				...focusedField,
				settingsContext: {
					...focusedField.settingsContext,
					pages: settingsContextPages,
				},
			};

			const settingsContextVisitor = new PagesVisitor(
				settingsContextPages
			);

			settingsContextVisitor.mapFields(({fieldName, value}) => {
				newFocusedField = updateFieldProperty({
					defaultLanguageId,
					editingLanguageId,
					fieldNameGenerator,
					focusedField: newFocusedField,
					generateFieldNameUsingFieldLabel,
					pages,
					propertyName: fieldName,
					propertyValue: value,
				});
			});

			const visitor = new PagesVisitor(pages);

			const newPages = visitor.mapFields(
				(field) => {
					if (field.fieldName !== fieldName.value) {
						return field;
					}

					return newFocusedField;
				},
				true,
				true
			);

			return {
				focusedField: newFocusedField,
				pages: newPages,
				rules: RulesUtil.updateRulesReferences(
					rules || [],
					focusedField,
					newFocusedField
				),
			};
		}
		case EVENT_TYPES.FIELD.HOVER:
			return {
				fieldHovered: action.payload,
			};
		case EVENT_TYPES.SECTION.ADD: {
			const {
				activePage,
				availableLanguageIds,
				defaultLanguageId,
				editingLanguageId,
				pages,
				rules,
			} = state;
			const {
				fieldTypes,
				generateFieldNameUsingFieldLabel,
				getFieldNameGenerator,
			} = config;

			const fieldNameGenerator = getFieldNameGenerator(
				pages,
				generateFieldNameUsingFieldLabel
			);

			return sectionAdded(
				{
					availableLanguageIds,
					defaultLanguageId,
					editingLanguageId,
					fieldNameGenerator,
					fieldTypes,
					generateFieldNameUsingFieldLabel,
				},
				{
					activePage,
					pages,
					rules,
				},
				action.payload
			);
		}
		default:
			return state;
	}
};
