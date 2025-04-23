/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.content.web.internal.frontend.data.set;

import com.liferay.commerce.order.content.web.internal.constants.CommerceOrderFragmentFDSNames;
import com.liferay.frontend.data.set.SystemFDSEntry;
import com.liferay.petra.string.StringPool;

import org.osgi.service.component.annotations.Component;

/**
 * @author Gianmarco Brunialti Masera
 */
@Component(
	property = "frontend.data.set.name=" + CommerceOrderFragmentFDSNames.PLACED_ORDER_RETURNS,
	service = SystemFDSEntry.class
)
public class PlacedCommerceOrderReturnsSystemFDSEntry
	implements SystemFDSEntry {

	@Override
	public String getDescription() {
		return null;
	}

	@Override
	public String getName() {
		return CommerceOrderFragmentFDSNames.PLACED_ORDER_RETURNS;
	}

	@Override
	public String getRESTApplication() {
		return "/commerce/returns";
	}

	@Override
	public String getRESTEndpoint() {
		return StringPool.BLANK;
	}

	@Override
	public String getRESTSchema() {
		return "CommerceReturn";
	}

	@Override
	public String getTitle() {
		return "Order Returns";
	}

}