/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AJAX from '../../../utilities/AJAX/index';

const VERSION = 'v2.0';

function resolvePath(basePath, priceListId, priceEntryId) {
	let path = `${basePath}${VERSION}/price-lists/${priceListId}/price-entries`;

	if (priceEntryId) {
		path += `/${priceEntryId}`;
	}

	return path;
}

export default function PriceEntry(basePath) {
	return {
		addPriceEntry: (priceListId, json) =>
			AJAX.POST(resolvePath(basePath, priceListId), json),
	};
}
