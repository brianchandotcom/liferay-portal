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

import * as FormSupport from '../../utils/FormSupport.es';
import {
	addFieldToPage,
	createField,
	getField,
	localizeField,
	removeField,
} from '../../utils/fieldSupport';
import {formatRules} from '../../utils/rulesSupport';
import {updateField, updateFieldReference} from '../../utils/settingsContext';
import {PagesVisitor} from '../../utils/visitors.es';
import {EVENT_TYPES} from '../actions/eventTypes.es';
import {
	createDuplicatedField,
	findInvalidFieldReference,
} from '../utils/fields';
import {updateRulesReferences} from '../utils/rules';
import sectionAdded from '../utils/sectionAddedHandler';
import {enableSubmitButton} from '../utils/submitButtonController.es';

export function deleteField({
	clean = false,
	defaultLanguageId,
	editingLanguageId,
	fieldName,
	fieldNameGenerator,
	fieldPage,
	generateFieldNameUsingFieldLabel,
	pages,
}) {
	return pages.map((page, pageIndex) => {
		if (fieldPage === pageIndex) {
			const pagesWithFieldRemoved = removeField(
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
}

function isParameterRelatedToField(parameter, fieldName) {

	/* TODO: enforce parameter type consistency and remove this normalization */
	const json =
		typeof parameter === 'string' ? parameter : JSON.stringify(parameter);

	return json.includes(fieldName);
}

/* TODO: enforce parameter type consistency and remove this function */
function normalizeParameter(parameter, defaultLanguageId) {
	let normalizedParameter = parameter;

	if (typeof normalizedParameter === 'string') {
		normalizedParameter = JSON.parse(parameter);
	}

	if (normalizedParameter[defaultLanguageId]) {
		normalizedParameter = normalizedParameter[defaultLanguageId];
	}

	return normalizedParameter;
}

function updateFieldAffectedByActivatingRepeatable({
	defaultLanguageId,
	editingLanguageId,
	field,
	fieldNameGenerator,
	generateFieldNameUsingFieldLabel,
	repeatableFieldName,
}) {
	if (
		field.type === 'date' &&
		field.validation?.parameter &&
		isParameterRelatedToField(
			field.validation.parameter,
			repeatableFieldName
		)
	) {
		const {endsOn, startsFrom} = normalizeParameter(
			field.validation.parameter,
			defaultLanguageId
		);

		const removeDateField = (validation) => {
			if (repeatableFieldName !== validation.dateFieldName) {
				return;
			}

			if (validation.type === 'dateField') {
				validation.type = 'responseDate';
			}
			delete validation.dateFieldName;
		};
		removeDateField(endsOn);
		removeDateField(startsFrom);

		const validation = {
			...field.validation,

			/* TODO: define a proper parameter type and apply it here */
			parameter: JSON.stringify({
				endsOn,
				startsFrom,
			}),
		};

		return updateField(
			{
				defaultLanguageId,
				editingLanguageId,
				fieldNameGenerator,
				generateFieldNameUsingFieldLabel,
			},
			field,
			'validation',
			validation
		);
	}

	return field;
}

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
		focusedField = updateFieldReference(
			focusedField,
			findInvalidFieldReference(focusedField, pages, propertyValue),
			false
		);
	}

	return updateField(
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
export default function fieldEditableReducer(state, action, config) {
	switch (action.type) {
		case EVENT_TYPES.FIELD.ADD: {
			const {data, fieldType, indexes} = action.payload;
			const {
				availableLanguageIds,
				defaultLanguageId,
				editingLanguageId,
				pages,
			} = state;
			const {
				generateFieldNameUsingFieldLabel,
				getFieldNameGenerator,
				portletNamespace,
			} = config;

			const fieldNameGenerator = getFieldNameGenerator(
				pages,
				generateFieldNameUsingFieldLabel
			);

			const field =
				action.payload.newField ||
				createField({
					defaultLanguageId,
					editingLanguageId,
					fieldNameGenerator,
					fieldType,
					portletNamespace,
				});

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
						localizeField(
							field,
							defaultLanguageId,
							editingLanguageId
						)
					),
				},
			};

			const updatedPages = addFieldToPage({
				defaultLanguageId,
				editingLanguageId,
				fieldNameGenerator,
				generateFieldNameUsingFieldLabel,
				indexes,
				newField,
				pages,
				parentFieldName: data?.parentFieldName,
			});

			return {
				activePage: indexes.pageIndex,
				focusedField: newField,
				pages: updatedPages,
			};
		}
		case EVENT_TYPES.FIELD.BLUR: {
			const {propertyName, propertyValue} = action.payload;

			let focusedField = state.focusedField;

			if (
				Object.keys(focusedField).length &&
				propertyName === 'fieldReference' &&
				(propertyValue === '' ||
					findInvalidFieldReference(
						focusedField,
						state.pages,
						propertyValue
					))
			) {
				const {defaultLanguageId, editingLanguageId} = state;

				focusedField = updateField(
					{
						defaultLanguageId,
						editingLanguageId,
					},
					updateFieldReference(focusedField, false, true),
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

						return localizeField(
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
			};
		}
		case EVENT_TYPES.FIELD.CHANGE: {
			const {fieldInstance, propertyName, propertyValue} = action.payload;
			let {fieldName} = action.payload;
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

			if (!fieldName && fieldInstance) {
				fieldName = fieldInstance.fieldName;
			}

			const newFocusedField = updateFieldProperty({
				defaultLanguageId,
				editingLanguageId,
				fieldNameGenerator,
				focusedField: fieldName
					? getField(pages, fieldName)
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
						if (propertyValue && propertyName === 'repeatable') {
							return updateFieldAffectedByActivatingRepeatable({
								defaultLanguageId,
								editingLanguageId,
								field,
								fieldNameGenerator,
								generateFieldNameUsingFieldLabel,
								repeatableFieldName: newFocusedField.fieldName,
							});
						}

						return field;
					},
					false,
					true
				),
				rules: updateRulesReferences(
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
				rules: editRule ? formatRules(newPages, rules) : rules,
			};
		}
		case EVENT_TYPES.FIELD.DUPLICATE: {
			const {fieldName, parentFieldName} = action.payload;
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

			const originalField = JSON.parse(
				JSON.stringify(
					FormSupport.findFieldByFieldName(pages, fieldName)
				)
			);

			const newField = createDuplicatedField(originalField, {
				availableLanguageIds,
				defaultLanguageId,
				editingLanguageId,
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

							field = updateField(
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

							return updateField(
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
				submitButtonId,
			} = config;

			const fieldName = getField(settingsContextPages, 'name');
			const focusedFieldName = getField(
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

			enableSubmitButton(submitButtonId);

			return {
				focusedField: newFocusedField,
				pages: newPages,
				rules: updateRulesReferences(
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
		case EVENT_TYPES.DND.MOVE: {
			const {focusedField, pages} = state;

			if (!focusedField.fieldName) {
				return state;
			}

			const updatedFocusedField = FormSupport.findFieldByFieldName(
				pages,
				focusedField.fieldName
			);

			return {
				focusedField: updatedFocusedField,
			};
		}
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
}
