/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export type FieldType =
	| 'action'
	| 'boolean'
	| 'captcha'
	| 'categories'
	| 'date'
	| 'date-time'
	| 'datetime'
	| 'decimal'
	| 'display-page'
	| 'file'
	| 'formButton'
	| 'friendly-url'
	| 'grid'
	| 'html'
	| 'image'
	| 'integer'
	| 'localizationSelect'
	| 'long-text'
	| 'multiselect'
	| 'number'
	| 'option'
	| 'phone-number'
	| 'relationship'
	| 'rich-text'
	| 'select'
	| 'single-select'
	| 'stepper'
	| 'tags'
	| 'text'
	| 'upload'
	| 'url';

export type MappingField = {
	attributes?: MappingFieldAttributes;
	externalKey: string;
	key: string;
	label: string;
	localizable: boolean;
	name: string;
	repeatable: boolean;
	required: boolean;
	type: FieldType;
	typeLabel: string;
};

export type MappingFieldAttributes = {
	countrySource?: string;
	options?: {label: string; value: string}[];
};

export type MappingFieldSet = {
	fields: MappingFields;
	label?: string;
	name?: string;
	relationship?: boolean;
};

export type MappingFields = Array<MappingField | MappingFieldSet>;
