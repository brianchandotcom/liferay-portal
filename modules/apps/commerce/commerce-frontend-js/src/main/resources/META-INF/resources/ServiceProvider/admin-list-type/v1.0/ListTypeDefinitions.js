/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AJAX from '../../../utilities/AJAX/index';

const LIST_TYPE_DEFINITIONS_PATH = '/list-type-definitions';

const VERSION = 'v1.0';

function resolvePath(basePath = '', externalReferenceCode) {
	return `${basePath}${VERSION}${LIST_TYPE_DEFINITIONS_PATH}/by-external-reference-code/${externalReferenceCode}`;
}

export default function Account(basePath) {
	return {
		baseURL: resolvePath(basePath),

		getListTypeEntries: (externalReferenceCode, ...params) =>
			AJAX.GET(
				resolvePath(basePath, externalReferenceCode) +
					'/list-type-entries',
				{},
				...params
			),
	};
}
