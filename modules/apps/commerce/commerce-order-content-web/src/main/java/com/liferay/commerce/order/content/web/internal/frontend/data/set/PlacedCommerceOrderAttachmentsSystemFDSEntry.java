/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.content.web.internal.frontend.data.set;

import com.liferay.commerce.order.content.web.internal.constants.CommerceOrderFragmentFDSNames;
import com.liferay.frontend.data.set.SystemFDSEntry;

import jakarta.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Tancredi Covioli
 */
@Component(
	property = "frontend.data.set.name=" + CommerceOrderFragmentFDSNames.PLACED_ORDER_ATTACHMENTS,
	service = SystemFDSEntry.class
)
public class PlacedCommerceOrderAttachmentsSystemFDSEntry
	implements SystemFDSEntry {

	@Override
	public String getAdditionalAPIURLParameters(
		HttpServletRequest httpServletRequest) {

		return "r_commerceOrderToCommerceOrderAttachments_commerceOrderId eq " +
			"'{commerceOrderId}'";
	}

	@Override
	public String getDescription() {
		return null;
	}

	@Override
	public String getName() {
		return CommerceOrderFragmentFDSNames.PLACED_ORDER_ATTACHMENTS;
	}

	@Override
	public String getRESTApplication() {
		return "/commerce/order-attachments";
	}

	@Override
	public String getRESTEndpoint() {
		return "/";
	}

	@Override
	public String getRESTSchema() {
		return "CommerceOrderAttachment";
	}

	@Override
	public String getTitle() {
		return "Order Attachments";
	}

}