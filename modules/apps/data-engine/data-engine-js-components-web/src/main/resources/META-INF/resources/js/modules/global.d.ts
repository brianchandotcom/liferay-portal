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

type Direction = 'ltr' | 'rtl';

type FieldChangeEventHandler<T = any> = (event: {
	target: {
		value: T;
	};
}) => void;

type FieldTypeName =
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

type Locale =
	| 'ar_SA'
	| 'ca_ES'
	| 'de_DE'
	| 'en_US'
	| 'es_ES'
	| 'fi_FI'
	| 'fr_FR'
	| 'hu_HU'
	| 'nl_NL'
	| 'ja_JP'
	| 'pt_BR'
	| 'sv_SE'
	| 'zh_CN';
type LocalizedTextKey =
	| 'back'
	| 'there-are-no-entries'
	| 'x-entries'
	| 'x-entry';

type LocalizedValue<T> = {
	[key in Locale]?: T;
};

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

interface Field<T = unknown> {
	fieldName: string;
	localizable?: boolean;
	localizedValue?: LocalizedValue<T>;
	multiple?: unknown;
	nestedFields?: Field[];
	options?: unknown;
	settingsContext: {pages: unknown[]};
	type: FieldTypeName;
	value: T;
}

interface FieldType {
	name: string;
	settingsContext: {pages: unknown[]};
}
