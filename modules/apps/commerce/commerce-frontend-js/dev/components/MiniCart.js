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

import launcher from '../../src/main/resources/META-INF/resources/components/mini_cart/entry';

import '../../src/main/resources/META-INF/resources/styles/main.scss';

launcher('mini_cart', 'mini-cart-root-id', {
	cartActionURLs: {
		checkoutURL:
			'http://localhost:8080/group/minium/checkout?p_p_id=com_liferay_commerce_checkout_web_internal_portlet_CommerceCheckoutPortlet\x26p_p_lifecycle=0\x26_com_liferay_commerce_checkout_web_internal_portlet_CommerceCheckoutPortlet_mvcRenderCommandName=\x252Fcommerce_checkout\x252Fcheckout_redirect',
		orderDetailURL:
			'http://localhost:8080/group/minium/pending-orders?p_p_id=com_liferay_commerce_order_content_web_internal_portlet_CommerceOpenOrderContentPortlet\x26p_p_lifecycle=0\x26_com_liferay_commerce_order_content_web_internal_portlet_CommerceOpenOrderContentPortlet_mvcRenderCommandName=\x252Fcommerce_open_order_content\x252Fedit_commerce_order\x26_com_liferay_commerce_order_content_web_internal_portlet_CommerceOpenOrderContentPortlet_commerceOrderUuid=1c25ed61-0a41-b490-fb52-9df18f3f2f33',
		productURLSeparator: '/p/',
		siteDefaultURL: 'http://localhost:8080/group/minium',
	},
	displayDiscountLevels: false,
	displayTotalItemsQuantity: false,
	itemsQuantity: 3,
	orderId: 43621,
	spritemap: 'http://localhost:8080/o/minium-theme/images/clay/icons\x2esvg',
	toggleable: true,
});
