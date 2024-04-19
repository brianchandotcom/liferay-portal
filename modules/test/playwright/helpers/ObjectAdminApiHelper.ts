/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getRandomInt} from '../utils/getRandomInt';
import {ApiHelpers} from './ApiHelpers';

type TObjectAction = {
	active?: boolean;
	id?: number;
	label: {
		[key: string]: string;
	};
	name: string;
	objectActionExecutorKey: string;
	objectActionTriggerKey: string;
	parameters: {
		[key: string]: number;
	};
};

export class ObjectAdminApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = 'object-admin/v1.0';
	}

	async deleteObjectAction(objectActionId: number) {
		return this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}/object-actions/${objectActionId}`
		);
	}

	async deleteObjectDefinition(objectDefinitionId: number) {
		return this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}/object-definitions/${objectDefinitionId}`
		);
	}

	async deleteObjectFolder(objectFolderId: number) {
		return this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}/object-folders/${objectFolderId}`
		);
	}

	async deleteObjectRelationship(objectRelationshipId: number) {
		return this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}/object-relationships/${objectRelationshipId}`
		);
	}

	async postObjectDefinition(data: DataObject) {
		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/object-definitions`,
			{data}
		);
	}

	async postObjectDefinitionByExternalRefernceCodeObjectAction(
		externalReferenceCode: string,
		objectAction?: TObjectAction
	): Promise<TObjectAction> {
		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/object-definitions/by-external-reference-code/${externalReferenceCode}/object-actions`,
			{data: objectAction}
		);
	}

	async postObjectRelationship(
		objectRelationship: Partial<ObjectRelationship>
	) {
		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/object-definitions/by-external-reference-code/${objectRelationship.objectDefinitionExternalReferenceCode1}/object-relationships`,
			{data: objectRelationship}
		);
	}

	async postRandomObjectDefinition({
		objectFolderExternalReferenceCode,
		status,
	}: {
		objectFolderExternalReferenceCode?: string;
		status: {code: number};
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
			objectFields: [
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
		};

		if (objectFolderExternalReferenceCode) {
			requestBody.objectFolderExternalReferenceCode =
				objectFolderExternalReferenceCode;
		}

		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/object-definitions`,
			{data: requestBody}
		) as Promise<ObjectDefinition>;
	}

	async postRandomObjectFolder(): Promise<ObjectFolder> {
		const objectFolderExternalReferenceCode =
			'objectFolder' + getRandomInt();

		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/object-folders`,
			{
				data: {
					externalReferenceCode: objectFolderExternalReferenceCode,
					label: {
						en_US: objectFolderExternalReferenceCode,
					},
					name: objectFolderExternalReferenceCode,
				},
			}
		);
	}
}
