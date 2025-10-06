/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Product} from '../../types/product';
import ProductGridView from './product-grid-view';
import ProductListView from './product-list-view';

interface ProductCardProps {
	product: Product;
	viewMode?: string;
}

export function ProductCard({product, viewMode = 'grid'}: ProductCardProps) {
	if (viewMode === 'list') {
		return <ProductListView product={product} />;
	}

	return <ProductGridView product={product} />;
}
