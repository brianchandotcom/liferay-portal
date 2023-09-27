/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {axios} from '../../../common/services/liferay/api';
import {Liferay} from '../../../common/utils/liferay';

export function getTaxonomyCategories(id, taxonomyCategoryName) {
	const taxonomyCategories = axios.get(
		`/o/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/${id}/taxonomy-categories?search='${taxonomyCategoryName}'`
	);

	return taxonomyCategories;
}

export function getTaxonomyVocabularies() {
	const taxonomyVocabularyName = 'Raylife Industry Type';

	const taxonomyVocabularies = axios.get(
		`o/headless-admin-taxonomy/v1.0/sites/${Liferay.ThemeDisplay.getCompanyGroupId()}/taxonomy-vocabularies?filter=name eq '${taxonomyVocabularyName}'`
	);

	return taxonomyVocabularies;
}
