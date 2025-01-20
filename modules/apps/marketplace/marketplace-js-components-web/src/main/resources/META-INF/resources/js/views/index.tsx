/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MarketplaceModal} from './Modal';
import {MarketplaceProducts} from './Products';
import {MarketplacePurchase} from './Purchase';
import {MarketplaceStorefront} from './Storefront';

const Marketplace: {
	Modal: typeof MarketplaceModal;
	Products: typeof MarketplaceProducts;
	Purchase: typeof MarketplacePurchase;
	Storefront: typeof MarketplaceStorefront;
} = () => null;

Marketplace.Modal = MarketplaceModal;
Marketplace.Products = MarketplaceProducts;
Marketplace.Storefront = MarketplaceStorefront;
Marketplace.Purchase = MarketplacePurchase;

export {Marketplace};
