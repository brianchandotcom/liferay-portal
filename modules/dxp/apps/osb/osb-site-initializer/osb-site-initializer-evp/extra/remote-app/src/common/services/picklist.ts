/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fetcher from './fetcher';

const resource = 'o/headless-admin-list-type/v1.0/list-type-definitions';

export async function getPicklistByName(name: string) {
	const response = await fetcher(`${resource}?filter=name eq '${name}'`);

	const statuses = response?.items[0].listTypeEntries;

	return statuses;
}
