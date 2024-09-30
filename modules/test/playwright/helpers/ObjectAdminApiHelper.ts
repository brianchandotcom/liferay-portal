/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ObjectAdminRestClient,
	ObjectField,
	ObjectFolder,
} from '../../../apps/object/object-admin-rest-client-js/src/main/resources/META-INF/resources/node';
import {getRandomInt} from '../utils/getRandomInt';
import {ApiHelpers} from './ApiHelpers';

export class ObjectAdminApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = 'object-admin/v1.0';
	}

	async getObjectDefinitionByExternalReferenceCode(
		externalReferenceCode: string
	): Promise<ObjectDefinition> {
		return this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}${this.basePath}/object-definitions/by-external-reference-code/${externalReferenceCode}`
		);
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

	async postRandomObjectDefinition({
		objectFields,
		objectFolderExternalReferenceCode,
		status,
		titleObjectFieldName,
	}: {
		objectFields?: Partial<ObjectField>[];
		objectFolderExternalReferenceCode?: string;
		status: {code: number};
		titleObjectFieldName?: string;
	}) {
		const objectDefinitionExternalReferenceCode =
			'ObjectDefinition' + getRandomInt();

		const requestBody = {
			active: true,
			externalReferenceCode: objectDefinitionExternalReferenceCode,
			label: {
				en_US: objectDefinitionExternalReferenceCode,
			},
			name: objectDefinitionExternalReferenceCode,
			objectFields: objectFields ?? [
				{
					DBType: 'String',
					businessType: 'Text',
					externalReferenceCode: 'textField',
					indexed: true,
					indexedAsKeyword: false,
					indexedLanguageId: '',
					label: {en_US: 'textField'},
					listTypeDefinitionId: 0,
					name: 'textField',
					required: false,
					system: false,
					type: 'String',
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

		const objectAdminRestClient = await this.apiHelpers.buildRestClient(
			ObjectAdminRestClient
		);

		return objectAdminRestClient.objectDefinition.postObjectDefinition({
			requestBody,
		});
	}

	async postRandomObjectFolder(): Promise<ObjectFolder> {
		const objectFolderExternalReferenceCode =
			'objectFolder' + getRandomInt();

		const objectAdminRestClient = await this.apiHelpers.buildRestClient(
			ObjectAdminRestClient
		);

		return objectAdminRestClient.objectFolder.postObjectFolder({
			requestBody: {
				externalReferenceCode: objectFolderExternalReferenceCode,
				label: {
					en_US: objectFolderExternalReferenceCode,
				},
				name: objectFolderExternalReferenceCode,
			},
		});
	}
}
