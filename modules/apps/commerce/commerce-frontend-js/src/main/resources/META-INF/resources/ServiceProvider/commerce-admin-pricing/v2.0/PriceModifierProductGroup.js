/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import AJAX from '../../../utilities/AJAX/index';

const PRICE_MODIFIERS_PATH = '/price-modifiers';

const PRICE_MODIFIER_RULES_PATH = '/price-modifier-product-groups';

const VERSION = 'v2.0';

function resolvePath(
	basePath = '',
	priceModifierId = '',
	priceModifierProductGroupId = ''
) {
	return `${basePath}${VERSION}${PRICE_MODIFIERS_PATH}/${priceModifierId}${PRICE_MODIFIER_RULES_PATH}/${priceModifierProductGroupId}`;
}

export default (basePath) => ({
	addPriceModifierProductGroup: (priceModifierId, json) =>
		AJAX.POST(resolvePath(basePath, priceModifierId), json),
});
