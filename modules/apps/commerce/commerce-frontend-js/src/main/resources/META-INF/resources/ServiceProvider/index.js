/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AdminAccountAPI from './commerce-admin-account/index';
import AdminCatalogAPI from './commerce-admin-catalog/index';
import AdminChannelAPI from './commerce-admin-channel/index';
import AdminInventoryAPI from './commerce-admin-inventory/index';
import AdminOrderAPI from './commerce-admin-order/index';
import AdminPricingAPI from './commerce-admin-pricing/index';
import DeliveryCartAPI from './commerce-delivery-cart/index';
import DeliveryCatalogAPI from './commerce-delivery-catalog/index';

const ServiceProvider = {
	AdminAccountAPI,
	AdminCatalogAPI,
	AdminChannelAPI,
	AdminInventoryAPI,
	AdminOrderAPI,
	AdminPricingAPI,
	DeliveryCartAPI,
	DeliveryCatalogAPI,
};

export default ServiceProvider;
