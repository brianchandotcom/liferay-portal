/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getRandomId from './getRandomId';
import normalizeName from './normalizeName';

// Constants

export const FIELD_TYPES = [
	'text',
	'long-text',
	'rich-text',
	'integer',
	'decimal',
	'single-select',
	'multiselect',
	'date',
	'datetime',
	'boolean',
	'upload',
] as const;

export const FIELD_TYPE_LABEL: Record<FieldType, string> = {
	'boolean': Liferay.Language.get('boolean'),
	'date': Liferay.Language.get('date'),
	'datetime': Liferay.Language.get('date-and-time'),
	'decimal': Liferay.Language.get('decimal'),
	'integer': Liferay.Language.get('numeric'),
	'long-text': Liferay.Language.get('long-text'),
	'multiselect': Liferay.Language.get('multiselect'),
	'rich-text': Liferay.Language.get('rich-text'),
	'single-select': Liferay.Language.get('single-select'),
	'text': Liferay.Language.get('text'),
	'upload': Liferay.Language.get('upload'),
} as const;

export const FIELD_TYPE_ICON: Record<FieldType, string> = {
	'boolean': 'check-square',
	'date': 'calendar',
	'datetime': 'date-time',
	'decimal': 'decimal',
	'integer': 'number',
	'long-text': 'field-area',
	'multiselect': 'select-from-list',
	'rich-text': 'textbox',
	'single-select': 'select',
	'text': 'custom-field',
	'upload': 'upload',
} as const;

export const FIELD_TYPE_BUSINESS_TYPE: Record<FieldType, string> = {
	'boolean': 'Boolean',
	'date': 'Date',
	'datetime': 'DateTime',
	'decimal': 'Decimal',
	'integer': 'Integer',
	'long-text': 'LongText',
	'multiselect': 'MultiselectPicklist',
	'rich-text': 'RichText',
	'single-select': 'Picklist',
	'text': 'Text',
	'upload': 'Attachment',
} as const;

export const FIELD_TYPE_DB_TYPE: Record<FieldType, string> = {
	'boolean': 'Boolean',
	'date': 'Date',
	'datetime': 'DateTime',
	'decimal': 'BigDecimal',
	'integer': 'Integer',
	'long-text': 'Clob',
	'multiselect': 'String',
	'rich-text': 'Clob',
	'single-select': 'String',
	'text': 'String',
	'upload': 'Long',
} as const;

export const DB_TYPE_FIELD_TYPE: Record<string, FieldType> = {
	BigDecimal: 'decimal',
	Boolean: 'boolean',
	Clob: 'long-text',
	Date: 'date',
	DateTime: 'datetime',
	Integer: 'integer',
	Long: 'upload',
	RichText: 'rich-text',
	SingleSelect: 'single-select',
	String: 'text',
	Upload: 'upload',
} as const;

// Types

type BaseField = {
	erc: string;
	indexableConfig:
		| {
				indexed: false;
		  }
		| {
				indexed: true;
				indexedAsKeyword: boolean;
				indexedLanguageId?: Liferay.Language.Locale;
		  };
	label: Liferay.Language.LocalizedValue<string>;
	localized: boolean;
	name: string;
	required: boolean;
};

export type TextField = BaseField & {
	settings: {
		maxLength?: number;
		showCounter?: boolean;
		uniqueValues?: boolean;
	};
	type: 'text';
};

export type Field =
	| TextField
	| (BaseField & {
			settings: {timeStorage: 'convertToUTC'};
			type: 'datetime';
	  })
	| (BaseField & {
			settings: {
				acceptedFileExtensions: string;
				fileSource: 'userComputer';
				maximumFileSize: number;
			};
			type: 'upload';
	  })
	| (BaseField & {
			settings: {};
			type: Exclude<FieldType, ['datetime', 'text', 'upload']>;
	  });

export type FieldType = (typeof FIELD_TYPES)[number];

export type FieldBusinessType =
	(typeof FIELD_TYPE_BUSINESS_TYPE)[keyof typeof FIELD_TYPE_BUSINESS_TYPE];

// Functions

export function getDefaultField(type: FieldType): Field {
	const base = {
		erc: getRandomId(),
		indexableConfig: {
			indexed: true,
			indexedAsKeyword: false,
			indexedLanguageId: Liferay.ThemeDisplay.getDefaultLanguageId(),
		},
		label: {
			[Liferay.ThemeDisplay.getDefaultLanguageId()]:
				FIELD_TYPE_LABEL[type],
		},
		localized: Liferay.FeatureFlags['LPD-32050'],
		name: normalizeName(type),
		required: false,
		settings: {},
	};

	if (type === 'datetime') {
		return {
			...base,
			settings: {
				timeStorage: 'convertToUTC',
			},
			type: 'datetime',
		};
	}
	else if (type === 'upload') {
		return {
			...base,
			settings: {
				acceptedFileExtensions: 'jpeg, jpg, pdf, png',
				fileSource: 'userComputer',
				maximumFileSize: 100,
			},
			type: 'upload',
		};
	}

	return {...base, type};
}
