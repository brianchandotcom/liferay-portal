/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AJAX from '../../../utilities/AJAX/index';

const PRICE_MODIFIERS_PATH = '/price-modifiers';

const PRICE_MODIFIER_RULES_PATH = '/price-modifier-products';

const VERSION = 'v2.0';

function resolvePath(
	basePath = '',
	priceModifierId = '',
	priceModifierProductId = ''
) {
	return `${basePath}${VERSION}${PRICE_MODIFIERS_PATH}/${priceModifierId}${PRICE_MODIFIER_RULES_PATH}/${priceModifierProductId}`;
}

export default function PriceModifierProduct(basePath) {
	return {
		addPriceModifierProduct: (priceModifierId, json) =>
			AJAX.POST(resolvePath(basePath, priceModifierId), json),
	};
}
