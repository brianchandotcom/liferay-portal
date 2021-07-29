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
	addFieldToColumn,
	removeEmptyRows as removeEmptyRowsUtil,
} from './FormSupport.es';
import {FIELD_TYPE_FIELDSET} from './constants';
import {normalizeFieldName} from './fields.es';
import {generateName, getRepeatedIndex, parseName} from './repeatable.es';
import {updateField} from './settingsContext';
import {PagesVisitor} from './visitors.es';

export const addFieldToPage = ({
	defaultLanguageId,
	editingLanguageId,
	fieldNameGenerator,
	generateFieldNameUsingFieldLabel,
	indexes,
	newField,
	pages,
	parentFieldName,
}) => {
	const {columnIndex, pageIndex, rowIndex} = indexes;

	if (!parentFieldName) {
		return addFieldToColumn(
			pages,
			pageIndex,
			rowIndex,
			columnIndex,
			newField
		);
	}

	const visitor = new PagesVisitor(pages);

	return visitor.mapFields(
		(field) => {
			if (field.fieldName === parentFieldName) {
				const nestedFields = field.nestedFields
					? [...field.nestedFields, newField]
					: [newField];

				field = updateField(
					{
						defaultLanguageId,
						editingLanguageId,
						fieldNameGenerator,
						generateFieldNameUsingFieldLabel,
					},
					field,
					'nestedFields',
					nestedFields
				);

				const {rows} = field;
				const pages = addFieldToColumn(
					[
						{
							rows:
								typeof rows === 'string'
									? JSON.parse(rows)
									: rows,
						},
					], // TODO: Check if row can be a string
					0,
					rowIndex,
					columnIndex,
					newField.fieldName
				);

				return updateField(
					{
						defaultLanguageId,
						editingLanguageId,
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
};

export const generateInstanceId = (isNumbersOnly) =>
	Math.random()
		.toString(isNumbersOnly ? 10 : 36)
		.substr(2, 8);

export const getDefaultFieldName = (isOptionField = false, fieldType = '') => {
	const defaultFieldName = fieldType?.label
		? normalizeFieldName(fieldType.label)
		: isOptionField
		? Liferay.Language.get('option')
		: Liferay.Language.get('field');

	return defaultFieldName + generateInstanceId(true);
};

export const removeField = (
	props,
	pages,
	fieldName,
	removeEmptyRows = true
) => {
	const visitor = new PagesVisitor(pages);

	const filter = (fields) =>
		fields
			.filter((field) => field.fieldName !== fieldName)
			.map((field) => {
				const nestedFields = field.nestedFields
					? filter(field.nestedFields)
					: [];

				field = updateField(props, field, 'nestedFields', nestedFields);

				if (field.type !== FIELD_TYPE_FIELDSET) {
					return {
						...field,
						nestedFields,
					};
				}

				let rows = [];

				if (field.rows) {
					const visitor = new PagesVisitor([
						{
							rows:
								typeof field.rows === 'string'
									? JSON.parse(field.rows)
									: field.rows || [],
						},
					]);

					const pages = visitor.mapColumns((column) => ({
						...column,
						fields: column.fields.filter(
							(nestedFieldName) => fieldName !== nestedFieldName
						),
					}));

					rows = removeEmptyRows
						? removeEmptyRowsUtil(pages, 0)
						: pages[0].rows;

					field = updateField(props, field, 'rows', rows);
				}

				return {
					...field,
					nestedFields,
					rows,
				};
			})
			.filter(({nestedFields = [], type}) => {
				if (type === FIELD_TYPE_FIELDSET && !nestedFields.length) {
					return false;
				}

				return true;
			});

	return visitor.mapColumns((column) => ({
		...column,
		fields: filter(column.fields),
	}));
};

export const getFieldProperties = (
	{pages},
	defaultLanguageId,
	editingLanguageId
) => {
	const properties = {};
	const visitor = new PagesVisitor(pages);

	visitor.mapFields(
		({fieldName, localizable, localizedValue = {}, type, value}) => {
			if (
				localizable &&
				localizedValue[editingLanguageId] !== undefined
			) {
				properties[fieldName] = localizedValue[editingLanguageId];
			}
			else if (localizable && localizedValue[defaultLanguageId]) {
				properties[fieldName] = localizedValue[defaultLanguageId];
			}
			else if (type == 'options') {
				if (!value[editingLanguageId] && value[defaultLanguageId]) {
					properties[fieldName] = value[defaultLanguageId];
				}
				else {
					properties[fieldName] = value[editingLanguageId];
				}
			}
			else if (type == 'validation') {
				if (!value.errorMessage[editingLanguageId]) {
					value.errorMessage[editingLanguageId] =
						value.errorMessage[defaultLanguageId];
				}

				if (!value.parameter[editingLanguageId]) {
					value.parameter[editingLanguageId] =
						value.parameter[defaultLanguageId];
				}

				properties[fieldName] = value;
			}
			else {
				properties[fieldName] = value;
			}
		}
	);

	return properties;
};

export const normalizeSettingsContextPages = (
	pages,
	defaultLanguageId,
	editingLanguageId,
	fieldType,
	generatedFieldName
) => {
	const visitor = new PagesVisitor(pages);

	return visitor.mapFields(
		(field) => {
			const {fieldName} = field;

			if (fieldName === 'fieldReference' || fieldName === 'name') {
				field = {
					...field,
					value: generatedFieldName,
				};
			}
			else if (fieldName === 'label') {
				const localizedValue = {
					...field.localizedValue,
					[editingLanguageId]: fieldType.label,
				};

				if (
					editingLanguageId !== defaultLanguageId &&
					!localizedValue[defaultLanguageId]
				) {
					localizedValue[defaultLanguageId] = fieldType.label;
				}

				field = {
					...field,
					localizedValue,
					type: 'text',
					value: fieldType.label,
				};
			}
			else if (fieldName === 'type') {
				field = {
					...field,
					value: fieldType.name,
				};
			}
			else if (fieldName === 'validation') {
				field = {
					...field,
					validation: {
						...field.validation,
						fieldName: generatedFieldName,
					},
				};
			}

			if (field.dataType === 'ddm-options') {
				field = {
					...field,
					value: {
						...field.value,
						[editingLanguageId]:
							field.value[editingLanguageId] ??
							field.value[field.locale],
					},
				};

				field.value[defaultLanguageId] =
					field.value[defaultLanguageId] ??
					field.value[editingLanguageId];
			}

			if (field.localizable) {
				const {localizedValue} = field;

				localizedValue[defaultLanguageId] =
					localizedValue[defaultLanguageId] ??
					localizedValue[field.locale];

				const availableLocales = Object.keys(localizedValue);

				availableLocales.forEach((availableLocale) => {
					if (
						availableLocale !== defaultLanguageId &&
						availableLocale !== editingLanguageId &&
						!localizedValue[availableLocale]
					) {
						delete localizedValue[availableLocale];
					}
				});
			}

			const instanceId = generateInstanceId();

			if (field.type === 'rich_text' && field.editorConfig) {
				field = {
					...field,
					editorConfig: updateEditorConfigInstanceId(
						field.editorConfig,
						instanceId
					),
				};
			}

			return {
				...field,
				defaultLanguageId,
				instanceId,
				locale: defaultLanguageId,
				name: generateName(field.name, {
					instanceId,
					repeatedIndex: getRepeatedIndex(field.name),
				}),
			};
		},
		false,
		true
	);
};

export const createField = (props, event) => {
	const {
		defaultLanguageId,
		editingLanguageId,
		fieldNameGenerator,
		portletNamespace,
		spritemap,
	} = props;
	const {
		fieldType,
		skipFieldNameGeneration = false,
		useFieldName = '',
	} = event;

	let newFieldName = useFieldName;

	if (!useFieldName) {
		if (skipFieldNameGeneration) {
			const {settingsContext} = fieldType;
			const visitor = new PagesVisitor(settingsContext.pages);

			visitor.mapFields(({fieldName, value}) => {
				if (fieldName === 'name') {
					newFieldName = value;
				}
			});
		}
		else {
			newFieldName = fieldNameGenerator(
				getDefaultFieldName(false, fieldType)
			);
		}
	}

	const instanceId = generateInstanceId();

	const newField = {
		...fieldType,
		fieldName: newFieldName,
		fieldReference: newFieldName,
		name: generateName(null, {
			editingLanguageId,
			fieldName: newFieldName,
			instanceId,
			portletNamespace,
			repeatedIndex: 0,
		}),
		settingsContext: {
			...fieldType.settingsContext,
			defaultLanguageId,
			editingLanguageId,
			pages: normalizeSettingsContextPages(
				[...fieldType.settingsContext.pages],
				defaultLanguageId,
				editingLanguageId,
				fieldType,
				newFieldName
			),
			type: fieldType.name,
		},
	};

	const {
		editorConfig,
		fieldName,
		fieldReference,
		name,
		settingsContext,
	} = newField;

	return {
		...getFieldProperties(
			settingsContext,
			defaultLanguageId,
			editingLanguageId
		),
		editorConfig,
		fieldName,
		fieldReference,
		instanceId,
		name,
		settingsContext,
		spritemap,
		type: fieldType.name,
	};
};

export const updateEditorConfigInstanceId = (editorConfig, instanceId) => {
	const updatedEditorConfig = {...editorConfig};
	for (const [key, value] of Object.entries(updatedEditorConfig)) {
		if (typeof value === 'string') {
			const parsedName = parseName(decodeURIComponent(value));

			if (parsedName.instanceId) {
				updatedEditorConfig[key] = value.replace(
					parsedName.instanceId,
					instanceId
				);
			}
		}
	}

	return updatedEditorConfig;
};

export const formatFieldName = (instanceId, languageId, value) => {
	return `ddm$$${value}$${instanceId}$0$$${languageId}`;
};

export const getField = (pages, fieldName) => {
	const visitor = new PagesVisitor(pages);

	return visitor.findField((field) => field.fieldName === fieldName);
};

export const getParentField = (pages, fieldName) => {
	let parentField = null;
	const visitor = new PagesVisitor(pages);

	visitor.visitFields((field) => {
		const nestedFieldsVisitor = new PagesVisitor(field.nestedFields || []);

		if (nestedFieldsVisitor.containsField(fieldName)) {
			parentField = field;
		}

		return false;
	});

	return parentField;
};

export const isFieldSet = (field) =>
	field.type === FIELD_TYPE_FIELDSET && field.ddmStructureId;

export const getParentFieldSet = (pages, fieldName) => {
	let parentField = getParentField(pages, fieldName);

	while (parentField) {
		if (isFieldSet(parentField)) {
			return parentField;
		}

		parentField = getParentField(pages, parentField.fieldName);
	}

	return null;
};

export const isFieldSetChild = (pages, fieldName) => {
	return !!getParentFieldSet(pages, fieldName);
};

export const localizeField = (field, defaultLanguageId, editingLanguageId) => {
	let value = field.value;

	if (
		field.dataType === 'json' &&
		field.fieldName !== 'rows' &&
		typeof value === 'object'
	) {
		value = JSON.stringify(value);
	}

	if (
		field.dataType === 'json' &&
		field.fieldName === 'rows' &&
		typeof value === 'string'
	) {
		value = JSON.parse(value);
	}

	if (field.localizable && field.localizedValue) {
		let localizedValue = field.localizedValue[editingLanguageId];

		if (localizedValue === undefined) {
			localizedValue = field.localizedValue[defaultLanguageId];
		}

		if (localizedValue !== undefined) {
			value = localizedValue;
		}
	}
	else if (field.dataType === 'ddm-options') {
		if (value[editingLanguageId] === undefined) {
			value = {
				...value,
				[editingLanguageId]:
					value[defaultLanguageId]?.map((option) => {
						return {...option, edited: false};
					}) ?? [],
			};
		}
		else {
			value = {
				...value,
				[editingLanguageId]: [
					...value[editingLanguageId].map((option) => {
						if (
							typeof option.edited === 'undefined' ||
							option.edited
						) {
							return option;
						}

						const {label} = value[defaultLanguageId].find(
							(defaultOption) =>
								defaultOption.value === option.value
						);

						return {...option, edited: false, label};
					}),
				],
			};
		}
	}

	return {
		...field,
		defaultLanguageId,
		editingLanguageId,
		localizedValue: {
			...(field.localizedValue || {}),
			[editingLanguageId]: value,
		},
		value,
	};
};
