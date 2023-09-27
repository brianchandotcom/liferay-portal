/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {axios} from './liferay/api';
import {Liferay} from './liferay/liferay';

const headlessAPI = '/o/headless-admin-taxonomy/v1.0';

const siteId = Liferay.ThemeDisplay.getCompanyGroupId();

export function getVocabulariesByExternalReferenceCode(
	externalReferenceCode: string
) {
	return axios.get(
		`${headlessAPI}/sites/${siteId}/taxonomy-vocabularies/by-external-reference-code/${externalReferenceCode}?fields=id`
	);
}
