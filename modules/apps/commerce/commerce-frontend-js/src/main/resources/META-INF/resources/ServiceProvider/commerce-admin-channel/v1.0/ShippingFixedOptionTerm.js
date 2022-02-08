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

const SHIPPING_FIXED_OPTION_PATH = '/shipping-fixed-options';

const SHIPPING_FIXED_OPTION_TERMS_PATH = '/shipping-fixed-option-terms';

const VERSION = 'v1.0';

function resolvePath(
	basePath = '',
	shippingFixedOptionId = '',
	shippingFixedOptionTermId = ''
) {
	return `${basePath}${VERSION}${SHIPPING_FIXED_OPTION_PATH}/${shippingFixedOptionId}${SHIPPING_FIXED_OPTION_TERMS_PATH}/${shippingFixedOptionTermId}`;
}

export default function ShippingFixedOptionTerm(basePath) {
	return {
		addShippingFixedOptionTerm: (shippingFixedOptionId, json) =>
			AJAX.POST(resolvePath(basePath, shippingFixedOptionId), json),
	};
}
