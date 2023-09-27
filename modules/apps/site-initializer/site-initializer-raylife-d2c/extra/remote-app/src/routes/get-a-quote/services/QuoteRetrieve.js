/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {axios} from '../../../common/services/liferay/api';
import {getGuestPermissionToken} from '../../../common/services/token';
import {Liferay} from '../../../common/utils/liferay';

const QuoteRetrieveAPI = 'o/c/quoteretrieves';

export async function createQuoteRetrieve(payload) {
	if (Liferay.ThemeDisplay.getUserName()) {
		return axios.post(
			`${QuoteRetrieveAPI}/scopes/${Liferay.ThemeDisplay.getScopeGroupId()}`,
			payload
		);
	}

	const {access_token} = await getGuestPermissionToken();

	return axios.post(
		`${QuoteRetrieveAPI}/scopes/${Liferay.ThemeDisplay.getScopeGroupId()}`,
		payload,
		{
			headers: {
				'Authorization': `Bearer ${access_token}`,
				'Content-Type': 'application/json',
			},
		}
	);
}
