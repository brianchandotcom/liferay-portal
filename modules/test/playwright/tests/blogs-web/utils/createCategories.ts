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
	friendlyUrlCategories: string[];
	site: Site;
	vocabularyName: string;
}) {
	const {id: vocabularyId} =
		await apiHelpers.headlessAdminTaxonomy.createVocabulary({
			name: vocabularyName,
			siteId: site.id,
		});

	for (const categoryName of friendlyUrlCategories) {
		await apiHelpers.headlessAdminTaxonomy.createCategory({
			name: categoryName,
			vocabularyId,
		});
	}
}
