/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AJAX from '../../../utilities/AJAX/index';

const PRICE_LISTS_PATH = '/price-lists';

const PRICE_LIST_RULES_PATH = '/price-list-accounts';

const VERSION = 'v2.0';

function resolvePath(basePath = '', priceListId = '', priceListAccountId = '') {
	return `${basePath}${VERSION}${PRICE_LISTS_PATH}/${priceListId}${PRICE_LIST_RULES_PATH}/${priceListAccountId}`;
}

export default function PriceListAccount(basePath) {
	return {
		addPriceListAccount: (priceListId, json) =>
			AJAX.POST(resolvePath(basePath, priceListId), json),
	};
}
