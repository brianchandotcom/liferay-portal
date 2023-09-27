/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Gets a data definition from a field
 *
 * @param {object} field - The field
 * @param {Object[]} field.nestedFields - The array containing all nested fields.
 * 										  It may be undefined
 * @param {object} field.settingsContext - The settings context of a field
 */
export declare function fieldToDataDefinition({
	nestedFields,
	settingsContext,
}: Field): DataDefinition;
export declare function getDDMFormFieldSettingsContext({
	dataDefinitionField,
	defaultLanguageId,
	editingLanguageId,
	fieldTypes,
}: {
	dataDefinitionField: DataDefinition;
	defaultLanguageId: Locale;
	editingLanguageId: Locale;
	fieldTypes: FieldType[];
}): {
	pages: any;
};

/**
 * **Private function** exported for test purpose only
 */
export declare function _fromDDMFormToDataDefinitionPropertyName(
	propertyName: string
): string;
interface DataDefinition {
	customProperties: DataDefinitionCustomProperties;
	defaultValue?: unknown;
	fieldType?: unknown;
	indexType?: unknown;
	indexable?: unknown;
	label?: unknown;
	localizable?: unknown;
	name?: unknown;
	nestedDataDefinitionFields: DataDefinition[];
	readOnly?: unknown;
	repeatable?: unknown;
	required?: unknown;
	showLabel?: unknown;
	tip?: unknown;
}
interface DataDefinitionCustomProperties {
	options?: LocalizedValue<unknown>;
	[key: string]: unknown;
}
export interface Field<T = unknown> {
	fieldName: string;
	localizable?: boolean;
	localizedValue?: LocalizedValue<T>;
	multiple?: unknown;
	nestedFields?: Field[];
	options?: unknown;
	settingsContext: {
		pages: unknown[];
	};
	type: FieldTypeName;
	value: T;
}
export interface FieldType {
	label: string;
	name: FieldTypeName;
	settingsContext: {
		pages: unknown[];
	};
}
export declare type FieldTypeName =
	| 'checkbox_multiple'
	| 'captcha'
	| 'checkbox'
	| 'color'
	| 'date'
	| 'document_library'
	| 'fieldset'
	| 'grid'
	| 'help_text'
	| 'image'
	| 'key_value'
	| 'localizable_text'
	| 'multi_language_option_select'
	| 'numeric'
	| 'numeric_input_mask'
	| 'object_field'
	| 'object-relationship'
	| 'options'
	| 'password'
	| 'paragraph'
	| 'redirect_button'
	| 'radio'
	| 'rich_text'
	| 'search_location'
	| 'separator'
	| 'select'
	| 'text'
	| 'validation';
declare type Locale = Liferay.Language.Locale;
declare type LocalizedValue<T> = Liferay.Language.LocalizedValue<T>;
export {};
