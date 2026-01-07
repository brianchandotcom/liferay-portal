/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ObjectDefinition,
	ObjectDefinitionAPI,
	ObjectField,
	ObjectFolder,
	ObjectFolderAPI,
} from '@liferay/object-admin-rest-client-js';

import {getRandomInt} from '../utils/getRandomInt';
import {ApiHelpers} from './ApiHelpers';
export class ObjectAdminApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = 'object-admin/v1.0';
	}

	async getAllObjectDefinitions() {
		return this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}${this.basePath}/object-definitions`
		);
	}

	async getAllObjectDefinitionsFields(objectDefinitionId: number) {
		return this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}${this.basePath}/object-definitions/${objectDefinitionId}/object-fields`
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
		className,
		enableFriendlyURLCustomization,
		objectDefinitionExternalReferenceCode,
		objectFields,
		objectFolderExternalReferenceCode,
		panelCategoryKey,
		scope = 'company',
		status,
		titleObjectFieldName,
	}: {
		className?: string;
		enableFriendlyURLCustomization?: boolean;
		objectDefinitionExternalReferenceCode?: string;
		objectFields?: Partial<ObjectField>[];
		objectFolderExternalReferenceCode?: string;
		panelCategoryKey?: string;
		scope?: 'site' | 'company';
		status: {code: number};
		titleObjectFieldName?: string;
	}) {
		const externalReferenceCode =
			objectDefinitionExternalReferenceCode ??
			`ObjectDefinition${getRandomInt()}`;

		const requestBody: ObjectDefinition = {
			active: true,
			className,
			enableFriendlyURLCustomization,
			externalReferenceCode,
			label: {
				en_US: externalReferenceCode,
			},
			name: externalReferenceCode,
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
					localized: false,
					name: 'textField',
					required: false,
					system: false,
					type: 'String',
				},
			],
			objectFolderExternalReferenceCode,
			panelCategoryKey: panelCategoryKey ?? '',
			pluralLabel: {
				en_US: externalReferenceCode,
			},
			scope,
			status,
			titleObjectFieldName: titleObjectFieldName ?? 'id',
		};

		if (objectFolderExternalReferenceCode) {
			requestBody.objectFolderExternalReferenceCode =
				objectFolderExternalReferenceCode;
		}

		const objectDefinitionAPIClient =
			await this.apiHelpers.buildRestClient(ObjectDefinitionAPI);

		return (
			await objectDefinitionAPIClient.postObjectDefinition(requestBody)
		).body;
	}

	async postRandomObjectFolder(): Promise<ObjectFolder> {
		const objectFolderExternalReferenceCode =
			'objectFolder' + getRandomInt();

		const objectFolderAPIClient =
			await this.apiHelpers.buildRestClient(ObjectFolderAPI);

		return (
			await objectFolderAPIClient.postObjectFolder({
				externalReferenceCode: objectFolderExternalReferenceCode,
				label: {
					en_US: objectFolderExternalReferenceCode,
				},
				name: objectFolderExternalReferenceCode,
			})
		).body;
	}
}
