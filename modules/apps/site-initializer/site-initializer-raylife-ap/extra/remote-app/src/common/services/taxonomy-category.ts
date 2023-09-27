/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {axios} from './liferay/api';

const headlessAPI = '/o/headless-admin-taxonomy/v1.0/taxonomy-vocabularies';

export function getCategoriesByVocabulary(vocabularyId: number) {
	return axios.get(
		`${headlessAPI}/${vocabularyId}/taxonomy-categories/?fields=id,name,externalReferenceCode`
	);
}
