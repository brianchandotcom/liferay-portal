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
	'integer': Liferay.Language.get('integer'),
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

// Types

type BaseField = {
	erc: string;
	label: string;
	localized: boolean;
	name: string;
	required: boolean;
};

export type Field =
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
			type: Exclude<FieldType, 'datetime'>;
	  });

export type FieldType = (typeof FIELD_TYPES)[number];

export type FieldBusinessType =
	(typeof FIELD_TYPE_BUSINESS_TYPE)[keyof typeof FIELD_TYPE_BUSINESS_TYPE];

// Functions

export function getDefaultField(type: FieldType): Field {
	const base = {
		erc: getRandomId(),
		label: FIELD_TYPE_LABEL[type],
		localized: false,
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
