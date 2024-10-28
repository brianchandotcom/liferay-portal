/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.content.web.internal.frontend.data.set.filter;

import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.order.content.web.internal.constants.CommerceOrderFragmentFDSNames;
import com.liferay.commerce.order.status.CommerceOrderStatus;
import com.liferay.commerce.order.status.CommerceOrderStatusRegistry;
import com.liferay.frontend.data.set.constants.FDSEntityFieldTypes;
import com.liferay.frontend.data.set.filter.BaseSelectionFDSFilter;
import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.frontend.data.set.filter.SelectionFDSFilterItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gianmarco Brunialti Masera
 */
@Component(
	property = "frontend.data.set.name=" + CommerceOrderFragmentFDSNames.PLACED_ORDERS,
	service = FDSFilter.class
)
public class CommercePlacedOrdersOrderStatusSelectionFDSFilter
	extends BaseSelectionFDSFilter {

	@Override
	public String getEntityFieldType() {
		return FDSEntityFieldTypes.COLLECTION;
	}

	@Override
	public String getId() {
		return "orderStatus";
	}

	@Override
	public String getLabel() {
		return "order-status";
	}

	@Override
	public List<SelectionFDSFilterItem> getSelectionFDSFilterItems(
		Locale locale) {

		List<SelectionFDSFilterItem> selectionFDSFilterItems =
			new ArrayList<>();

		for (CommerceOrderStatus commerceOrderStatus :
				_commerceOrderStatusRegistry.getCommerceOrderStatuses()) {

			if (_commerceOrderOpenStatuses.contains(
					commerceOrderStatus.getKey())) {

				continue;
			}

			selectionFDSFilterItems.add(
				new SelectionFDSFilterItem(
					commerceOrderStatus.getLabel(locale),
					commerceOrderStatus.getKey()));
		}

		return selectionFDSFilterItems;
	}

	private static final List<Integer> _commerceOrderOpenStatuses =
		Arrays.asList(
			CommerceOrderConstants.ORDER_STATUS_IN_PROGRESS,
			CommerceOrderConstants.ORDER_STATUS_OPEN);

	@Reference
	private CommerceOrderStatusRegistry _commerceOrderStatusRegistry;

}