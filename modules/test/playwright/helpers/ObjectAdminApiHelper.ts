/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ObjectDefinition,
	ObjectDefinitionApi,
	ObjectField,
	ObjectFolder,
	ObjectFolderApi,
} from '@liferay/object-admin-rest-client-js';

import {getRandomInt} from '../utils/getRandomInt';
import {ApiHelpers} from './ApiHelpers';

export interface CreateObjectField {
	attachmentSource?: string;
	listTypeDefinitionName?: string;
	mandatory?: boolean;
	objectDefinitionLabel?: string;

	objectDefinitionNodes: unknown;
	objectFieldBusinessType: string;
	objectFieldLabel: string;
}

export class ObjectAdminApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = 'object-admin/v1.0';
	}

	async postObjectDefinitionObjectFieldBatch(
		objectDefinitionId: number,
		objectFields: Partial<ObjectField>[]
	): Promise<ObjectField> {
		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/object-definitions/${objectDefinitionId}/object-fields/batch`,
			{data: objectFields}
		);
	}

	async postRandomObjectDefinition(
		status: {code: number},
		objectFields?: Partial<ObjectField>[],
		objectFolderExternalReferenceCode?: string,
		titleObjectFieldName?: string
	): Promise<ObjectDefinition> {
		const objectDefinitionExternalReferenceCode =
			'ObjectDefinition' + getRandomInt();

		const requestBody: ObjectDefinition = {
			active: true,
			externalReferenceCode: objectDefinitionExternalReferenceCode,
			label: {
				en_US: objectDefinitionExternalReferenceCode,
			},
			name: objectDefinitionExternalReferenceCode,
			objectFields: objectFields ?? [
				{
					DBType: ObjectField.DBTypeEnum.String,
					businessType: ObjectField.BusinessTypeEnum.Text,
					externalReferenceCode: 'textField',
					indexed: true,
					indexedAsKeyword: false,
					indexedLanguageId: '',
					label: {en_US: 'textField'},
					listTypeDefinitionId: 0,
					name: 'textField',
					required: false,
					system: false,
					type: ObjectField.TypeEnum.String,
				},
			],
			objectFolderExternalReferenceCode,
			pluralLabel: {
				en_US: objectDefinitionExternalReferenceCode,
			},
			scope: 'company',
			status,
			titleObjectFieldName: titleObjectFieldName ?? 'id',
		};

		if (objectFolderExternalReferenceCode) {
			requestBody.objectFolderExternalReferenceCode =
				objectFolderExternalReferenceCode;
		}

		const objectDefinitionApiClient =
			await this.apiHelpers.buildRestClient(ObjectDefinitionApi);

		return (
			await objectDefinitionApiClient.postObjectDefinition(requestBody)
		).body;
	}

	async postRandomObjectFolder(): Promise<ObjectFolder> {
		const objectFolderExternalReferenceCode =
			'objectFolder' + getRandomInt();

		const objectFolderApiClient =
			await this.apiHelpers.buildRestClient(ObjectFolderApi);

		return (
			await objectFolderApiClient.postObjectFolder({
				externalReferenceCode: objectFolderExternalReferenceCode,
				label: {
					en_US: objectFolderExternalReferenceCode,
				},
				name: objectFolderExternalReferenceCode,
			})
		).body;
	}
}
