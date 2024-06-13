/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import type {ApiHelpers} from '../../../helpers/ApiHelpers';

export async function createCategories({
	apiHelpers,
	friendlyUrlCategories,
	site,
	vocabularyName,
}: {
	apiHelpers: ApiHelpers;
	friendlyUrlCategories: {
		name: string;
		name_i18n?: {'ES-es': string};
	}[];
	site: Site;
	vocabularyName: string;
}): Promise<{id: number; name: string}[]> {
	const {id: vocabularyId} =
		await apiHelpers.headlessAdminTaxonomy.postVocabulary({
			name: vocabularyName,
			siteId: site.id,
		});

	const categories = [];
	for (const {name, name_i18n} of friendlyUrlCategories) {
		const {id} = await apiHelpers.headlessAdminTaxonomy.postCategory({
			name,
			name_i18n,
			vocabularyId,
		});

		categories.push({
			id,
			name,
		});
	}

	return categories;
}
