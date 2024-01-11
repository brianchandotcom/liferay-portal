/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getRandomInt} from '../utils/util';
import {ApiHelpers} from './ApiHelpers';

export class ObjectAdminApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = 'object-admin/v1.0';
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
			data
		);
	}

	async postRandomObjectDefinition(
		objectFolderExternalReferenceCode: string
	) {
		const objectDefinitionExternalReferenceCode =
			'ObjectDefinition' + getRandomInt();

		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/object-definitions`,
			{
				externalReferenceCode: objectDefinitionExternalReferenceCode,
				label: {
					en_US: objectDefinitionExternalReferenceCode,
				},
				name: objectDefinitionExternalReferenceCode,
				objectFolderExternalReferenceCode,
				pluralLabel: {
					en_US: objectDefinitionExternalReferenceCode,
				},
				scope: 'company',
			}
		);
	}

	async postRandomObjectFolder() {
		const objectFolderExternalReferenceCode =
			'objectFolder' + getRandomInt();

		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/object-folders`,
			{
				externalReferenceCode: objectFolderExternalReferenceCode,
				label: {
					en_US: objectFolderExternalReferenceCode,
				},
				name: objectFolderExternalReferenceCode,
			}
		);
	}
}
