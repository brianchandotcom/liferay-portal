/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ApiHelper from './ApiHelper';

async function getPicklists() {
	return await ApiHelper.get(
		'/o/headless-admin-list-type/v1.0/list-type-definitions'
	);
}

export default {
	getPicklists,
};
