/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {State, Uuid} from '../contexts/StateContext';
import {ObjectDefinition, ObjectField} from '../types/ObjectDefinition';
import {Field, FieldType} from './field';
import getUuid from './getUuid';

export default function buildState(
	objectDefinition: ObjectDefinition
): State | null {
	if (!objectDefinition) {
		return null;
	}

	const fields = new Map<Uuid, Field>();

	objectDefinition.objectFields?.forEach((objectField) => {
		if (objectField.system) {
			return;
		}

		const indexableConfig = {
			indexed: objectField.indexed,
		} as Field['indexableConfig'];

		if (indexableConfig.indexed) {
			indexableConfig.indexedAsKeyword =
				objectField.indexedAsKeyword ?? false;
			indexableConfig.indexedLanguageId =
				objectField.indexedLanguageId !== ''
					? objectField.indexedLanguageId
					: undefined;
		}

		const uuid = getUuid();

		const field = {
			erc: objectField.externalReferenceCode,
			indexableConfig,
			label: objectField.label,
			localized: objectField.localized,
			name: objectField.name,
			picklistId: objectField.listTypeDefinitionId?.toString(),
			required: objectField.required,
			settings: getFieldSettings(objectField),
			type: getFieldType(objectField),
			uuid,
		};

		fields.set(uuid, field);
	});

	const isPublished = objectDefinition.status?.label === 'approved';

	return {
		erc: objectDefinition.externalReferenceCode,
		error: null,
		fields,
		id: objectDefinition.id ?? null,
		invalids: new Set(),
		label: objectDefinition.label,
		name: objectDefinition.name ?? '',
		publishedFields: isPublished ? new Set(fields.keys()) : new Set(),
		selection: [],
		spaces: getSpaces(objectDefinition),
		status: isPublished ? 'published' : 'draft',
		uuid: getUuid(),
	};
}

function getFieldSettings(objectField: ObjectField): Field['settings'] {
	const settings: Record<string, any> = {};

	const objectFieldSettings: Record<string, any> = {};

	for (const objectFieldSetting of objectField.objectFieldSettings ?? []) {
		objectFieldSettings[objectFieldSetting.name] = objectFieldSetting.value;
	}

	if (objectField.businessType === 'Attachment') {
		settings.acceptedFileExtensions =
			objectFieldSettings.acceptedFileExtensions;
		settings.fileSource = objectFieldSettings.fileSource;
		settings.maximumFileSize = objectFieldSettings.maximumFileSize;

		if (objectFieldSettings.fileSource === 'userComputer') {
			settings.showFilesInDocumentsAndMedia =
				objectFieldSettings.showFilesInDocumentsAndMedia;
			settings.storageDLFolderPath =
				objectFieldSettings.storageDLFolderPath;
		}
	}
	else if (objectField.businessType === 'DateTime') {
		settings.timeStorage = objectFieldSettings.timeStorage;
	}
	else if (
		objectField.businessType === 'Integer' ||
		objectField.businessType === 'LongText' ||
		objectField.businessType === 'Text'
	) {
		if (objectFieldSettings.maxLength) {
			settings.maxLength = objectFieldSettings.maxLength;
		}

		if (objectFieldSettings.showCounter) {
			settings.showCounter = objectFieldSettings.showCounter;
		}

		if (objectFieldSettings.uniqueValues) {
			settings.uniqueValues = objectFieldSettings.uniqueValues;
		}
	}

	return settings as Field['settings'];
}

function getFieldType(objectField: ObjectField): FieldType {
	if (objectField.businessType === 'Picklist') {
		return 'single-select';
	}
	else if (objectField.businessType === 'MultiselectPicklist') {
		return 'multiselect';
	}

	const DB_TYPE_TO_FIELD_TYPE: Record<string, FieldType> = {
		BigDecimal: 'decimal',
		Boolean: 'boolean',
		Clob: 'long-text',
		Date: 'date',
		DateTime: 'datetime',
		Integer: 'integer',
		Long: 'upload',
		RichText: 'rich-text',
		String: 'text',
		Upload: 'upload',
	} as const;

	return DB_TYPE_TO_FIELD_TYPE[objectField.DBType];
}

function getSpaces(objectDefinition: ObjectDefinition) {
	const settings = objectDefinition.objectDefinitionSettings || [];

	const acceptedGroupExternalReferenceCodes = settings.find(
		({name}) => name === 'acceptedGroupExternalReferenceCodes'
	)?.value;

	const acceptAllGroups = settings.find(
		({name}) => name === 'acceptAllGroups'
	)?.value;

	const spaces =
		acceptAllGroups === 'true'
			? 'all'
			: acceptedGroupExternalReferenceCodes?.split(',') || [];

	return spaces;
}
