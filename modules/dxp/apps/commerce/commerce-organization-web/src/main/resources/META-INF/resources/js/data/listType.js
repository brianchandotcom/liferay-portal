/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetchFromHeadless} from '../utils/fetch';

const LIST_TYPE_DEFINITIONS_ENDPOINT =
	'/o/headless-admin-list-type/v1.0/list-type-definitions';

export function getListTypeEntries(id) {
	return fetchFromHeadless(
		`${LIST_TYPE_DEFINITIONS_ENDPOINT}/${id}/list-type-entries`,
		{},
		null,
		true
	);
}
