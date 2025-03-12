/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {State} from '../contexts/StateContext';
import {ObjectDefinition, ObjectField} from '../types/ObjectDefinition';
import {DB_TYPE_FIELD_TYPE, Field} from './field';

export default function buildState(
	objectDefinition: ObjectDefinition
): State | null {
	if (!objectDefinition) {
		return null;
	}

	const fields = new Map<string, Field>();

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

		fields.set(objectField.name, {
			erc: objectField.externalReferenceCode,
			indexableConfig,
			label: objectField.label,
			localized: objectField.localized,
			name: objectField.name,
			required: objectField.required,
			settings: getSettings(objectField),
			type: DB_TYPE_FIELD_TYPE[objectField.DBType],
		});
	});

	const isPublished = objectDefinition.status?.label === 'approved';

	return {
		erc: objectDefinition.externalReferenceCode,
		error: null,
		fields,
		id: objectDefinition.id ?? null,
		label: objectDefinition.label,
		name: objectDefinition.name ?? '',
		publishedFields: isPublished ? new Set(fields.keys()) : new Set(),
		selection: [],
		status: isPublished ? 'published' : 'draft',
	};
}

function getSettings(objectField: ObjectField): Field['settings'] {
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
