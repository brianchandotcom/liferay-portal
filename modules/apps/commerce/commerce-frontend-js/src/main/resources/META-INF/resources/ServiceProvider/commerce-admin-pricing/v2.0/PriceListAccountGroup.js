/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AJAX from '../../../utilities/AJAX/index';

const PRICE_LISTS_PATH = '/price-lists';

const PRICE_LIST_RULES_PATH = '/price-list-account-groups';

const VERSION = 'v2.0';

function resolvePath(
	basePath = '',
	priceListId = '',
	priceListAccountGroupId = ''
) {
	return `${basePath}${VERSION}${PRICE_LISTS_PATH}/${priceListId}${PRICE_LIST_RULES_PATH}/${priceListAccountGroupId}`;
}

export default function PriceListAccountGroup(basePath) {
	return {
		addPriceListAccountGroup: (priceListId, json) =>
			AJAX.POST(resolvePath(basePath, priceListId), json),
	};
}
