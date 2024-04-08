/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from './ApiHelpers';

interface createVocabularyProps {
	assetTypes: {
		required: boolean;
		subtype: 'AllAssetSubtypes';
		type: 'AllAssetTypes';
	}[];
	name: string;
	siteId: string;
}

export class HeadlessAdminTaxonomyApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = 'headless-admin-taxonomy/v1.0';
	}

	/**
	 * It allows creating a vocabulary inside a site.
	 *
	 * @param siteId the id of the site in which the vocabulary will be created
	 * @param name the name of the vocabulary
	 * @param assetTypes the asset types to which the vocabulary can be used
	 */

	async createVocabulary({
		assetTypes,
		name,
		siteId,
	}: createVocabularyProps): Promise<{id: number}> {
		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/sites/${siteId}/taxonomy-vocabularies`,
			{assetTypes, name}
		);
	}
}
