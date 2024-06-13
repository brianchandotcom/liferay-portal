/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from './ApiHelpers';

interface postVocabularyProps {
	assetTypes?: AssetType[];
	name: string;
	siteId: string;
}

export interface postCategoryProps {
	name: string;
	name_i18n?: {['ES-es']: string};
	vocabularyId: number;
}

interface patchCategoryProps {
	id: number;
	name: string;
}

interface postTagProps {
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
	 * @param [assetTypes] the asset types to which the vocabulary can be used
	 */

	async postVocabulary({
		assetTypes,
		name,
		siteId,
	}: postVocabularyProps): Promise<{id: number}> {
		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/sites/${siteId}/taxonomy-vocabularies`,
			{data: {assetTypes, name}}
		);
	}

	/**
	 * It allows creating a category inside a vocabulary.
	 *
	 * @param name the name of the category
	 * @param vocabularyId the parent vocabulary id
	 */

	async postCategory({
		name,
		name_i18n,
		vocabularyId,
	}: postCategoryProps): Promise<{id: number}> {
		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/taxonomy-vocabularies/${vocabularyId}/taxonomy-categories`,
			{data: {name, name_i18n}}
		);
	}

	/**
	 * It allows update a category name
	 *
	 * @param name the new name of the category
	 * @param id the category id
	 */

	async patchCategory({id, name}: patchCategoryProps): Promise<{id: number}> {
		return this.apiHelpers.patch(
			`${this.apiHelpers.baseUrl}${this.basePath}/taxonomy-categories/${id}`,
			{name}
		);
	}

	/**
	 * It allows creating a tag inside a site.
	 *
	 * @param name the name of the tag
	 * @param siteId the id of the site in which the tag will be created
	 */

	async postTag({name, siteId}: postTagProps): Promise<{id: number}> {
		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/sites/${siteId}/keywords`,
			{data: {name}}
		);
	}

	/**
	 * It allows deleting a tag.
	 *
	 * @param id the id of the tag
	 */

	async deleteTag({id}: {id: number}) {
		return this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}/keywords/${id}`
		);
	}
}
