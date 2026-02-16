/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectField} from '../../common/types/ObjectDefinition';
import {Uuid} from '../types/Uuid';
import getRandomId from './getRandomId';
import getUuid from './getUuid';
import normalizeString from './normalizeString';

// Constants

export const FIELD_TYPES = [
	'text',
	'long-text',
	'rich-text',
	'integer',
	'decimal',
	'select-from-list',
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
	'rich-text': Liferay.Language.get('rich-text'),
	'select-from-list': Liferay.Language.get('select-from-list'),
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
	'rich-text': 'textbox',
	'select-from-list': 'select',
	'text': 'custom-field',
	'upload': 'upload',
} as const;

export function getFieldBusinessType(
	field: Field
): ObjectField['businessType'] {
	if (field.type === 'select-from-list') {
		if ((field as SelectFromListField).multiselection) {
			return 'MultiselectPicklist';
		}

		return 'Picklist';
	}

	switch (field.type) {
		case 'boolean':
			return 'Boolean';
		case 'date':
			return 'Date';
		case 'datetime':
			return 'DateTime';
		case 'decimal':
			return 'Decimal';
		case 'integer':
			return 'Integer';
		case 'long-text':
			return 'LongText';
		case 'rich-text':
			return 'RichText';
		case 'text':
			return 'Text';
		case 'upload':
			return 'Attachment';
		default:
			throw new Error(`Unsupported field type: ${field.type}`);
	}
}

export const FIELD_TYPE_TO_DB_TYPE: Record<FieldType, string> = {
	'boolean': 'Boolean',
	'date': 'Date',
	'datetime': 'DateTime',
	'decimal': 'Double',
	'integer': 'Integer',
	'long-text': 'Clob',
	'rich-text': 'Clob',
	'select-from-list': 'String',
	'text': 'String',
	'upload': 'Long',
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
	locked: boolean;
	name: string;
	parent: Uuid;
	required: boolean;
	settings: {};
	uuid: Uuid;
};

export type UniqueValuesSettingsField = {
	settings: {
		uniqueValues?: boolean;
	};
};

export type MaxLengthSettingsField = {
	settings: {
		maxLength?: number;
		showCounter?: boolean;
	};
};

export type DateTimeField = BaseField & {
	settings: {timeStorage: 'convertToUTC' | 'useInputAsEntered'};
	type: 'datetime';
};

export type LongTextField = BaseField & {
	type: 'long-text';
} & MaxLengthSettingsField;

export type NumericField = BaseField & {
	type: 'integer';
} & UniqueValuesSettingsField;

export type SelectFromListField = BaseField & {
	multiselection: boolean;
	picklistId: number;
	type: 'select-from-list';
};

export type TextField = BaseField & {
	type: 'text';
} & MaxLengthSettingsField &
	UniqueValuesSettingsField;

export type UploadField = BaseField & {
	type: 'upload';
} & {
	settings: {
		acceptedFileExtensions: string;
		fileSource: 'userComputerToDocumentsAndMedia' | 'documentsAndMedia';
		maximumFileSize: number;
		showFilesInLibrary?: boolean;
		storageDLFolderPath?: string;
	};
};

export type Field =
	| DateTimeField
	| LongTextField
	| NumericField
	| SelectFromListField
	| TextField
	| UploadField
	| (BaseField & {
			settings: {};
			type: Exclude<
				FieldType,
				[
					'datetime',
					'long-text',
					'multiselect',
					'numeric',
					'select-from-list',
					'text',
					'upload',
				]
			>;
	  });

export type FieldType = (typeof FIELD_TYPES)[number];

// Functions

export function getDefaultField({
	label,
	locked = false,
	name,
	parent,
	required = false,
	type,
}: {
	label?: string;
	locked?: boolean;
	name?: string;
	parent: Uuid;
	required?: boolean;
	type: FieldType;
}): Field {
	const base = {
		erc: getRandomId(),
		indexableConfig: {
			indexed: true,
			indexedAsKeyword: false,
			indexedLanguageId: Liferay.ThemeDisplay.getDefaultLanguageId(),
		},
		label: {
			[Liferay.ThemeDisplay.getDefaultLanguageId()]:
				label ?? FIELD_TYPE_LABEL[type],
		},
		localized: true,
		locked,
		name: name ?? normalizeString(FIELD_TYPE_LABEL[type], {style: 'camel'}),
		parent,
		required,
		settings: {},
		uuid: getUuid(),
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
	else if (type === 'select-from-list') {
		return {
			...base,
			multiselection: false,
			picklistId: 0,
			type: 'select-from-list',
		};
	}
	else if (type === 'upload') {
		return {
			...base,
			settings: {
				acceptedFileExtensions: 'jpeg, jpg, pdf, png',
				fileSource: 'userComputerToDocumentsAndMedia',
				maximumFileSize: 100,
			},
			type: 'upload',
		};
	}

	return {...base, type};
}
