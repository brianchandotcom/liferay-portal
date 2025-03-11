/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import type {ApiHelpers} from './ApiHelpers';
import type {postTaxonomyVocabularyTaxonomyCategoryProps} from './HeadlessAdminTaxonomyApiHelper';

export type TCategory = Omit<
	postTaxonomyVocabularyTaxonomyCategoryProps,
	'vocabularyId'
>;

export async function createCategories({
	apiHelpers,
	assetTypes,
	categoryNames,
	siteId,
	vocabularyName,
}: {
	apiHelpers: ApiHelpers;
	assetTypes?: AssetType[];
	categoryNames: TCategory[];
	siteId: string;
	vocabularyName: string;
}): Promise<({id: number} & TCategory)[]> {
	const {id: vocabularyId} =
		await apiHelpers.headlessAdminTaxonomy.postSiteTaxonomyVocabulary({
			assetTypes,
			name: vocabularyName,
			siteId,
		});

	const categories = [];
	for (const {name, name_i18n} of categoryNames) {
		const {id} =
			await apiHelpers.headlessAdminTaxonomy.postTaxonomyVocabularyTaxonomyCategory(
				{
					name,
					name_i18n,
					vocabularyId,
				}
			);

		categories.push({
			id,
			name,
			name_i18n,
			vocabularyId,
		});
	}

	return categories;
}
