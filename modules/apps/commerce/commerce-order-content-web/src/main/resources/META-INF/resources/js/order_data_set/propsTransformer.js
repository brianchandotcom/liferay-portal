/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	PENDING_ORDERS_FDS_NAME,
	PENDING_ORDER_ITEMS_FDS_NAME,
	PLACED_ORDERS_FDS_NAME,
	PLACED_ORDER_ITEMS_FDS_NAME,
	PLACED_ORDER_RETURNS_FDS_NAME,
} from './constants';
import PendingOrderItemsFDSPropsTransformer from './props_transformers/PendingOrderItemsFDSPropsTransformer';
import PendingOrdersFDSPropsTransformer from './props_transformers/PendingOrdersFDSPropsTransformer';
import PlacedOrderItemsFDSPropsTransformer from './props_transformers/PlacedOrderItemsFDSPropsTransformer';
import PlacedOrderReturnsFDSPropsTransformer from './props_transformers/PlacedOrderReturnsFDSPropsTransformer';
import PlacedOrdersFDSPropsTransformer from './props_transformers/PlacedOrdersFDSPropsTransformer';

const PROPS_TRANSFORMERS = {
	[PENDING_ORDER_ITEMS_FDS_NAME]: PendingOrderItemsFDSPropsTransformer,
	[PENDING_ORDERS_FDS_NAME]: PendingOrdersFDSPropsTransformer,
	[PLACED_ORDER_ITEMS_FDS_NAME]: PlacedOrderItemsFDSPropsTransformer,
	[PLACED_ORDER_RETURNS_FDS_NAME]: PlacedOrderReturnsFDSPropsTransformer,
	[PLACED_ORDERS_FDS_NAME]: PlacedOrdersFDSPropsTransformer,
};

const OrderDataSetPropsTransformer = (props) =>
	PROPS_TRANSFORMERS[props.id](props);

export default OrderDataSetPropsTransformer;
