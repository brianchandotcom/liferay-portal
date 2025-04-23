/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.content.web.internal.frontend.data.set;

import com.liferay.commerce.order.content.web.internal.constants.CommerceOrderFragmentFDSNames;
import com.liferay.frontend.data.set.SystemFDSEntry;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "frontend.data.set.name=" + CommerceOrderFragmentFDSNames.PLACED_ORDER_SHIPMENTS,
	service = SystemFDSEntry.class
)
public class PlacedCommerceOrderShipmentsSystemFDSEntry
	implements SystemFDSEntry {

	@Override
	public String getDescription() {
		return null;
	}

	@Override
	public String getName() {
		return CommerceOrderFragmentFDSNames.PLACED_ORDER_SHIPMENTS;
	}

	@Override
	public String getRESTApplication() {
		return "/headless-commerce-delivery-order/v1.0";
	}

	@Override
	public String getRESTEndpoint() {
		return "/v1.0/placed-orders/{placedOrderId}/shipments";
	}

	@Override
	public String getRESTSchema() {
		return "Shipment";
	}

	@Override
	public String getTitle() {
		return "Placed Order Shipments";
	}

}