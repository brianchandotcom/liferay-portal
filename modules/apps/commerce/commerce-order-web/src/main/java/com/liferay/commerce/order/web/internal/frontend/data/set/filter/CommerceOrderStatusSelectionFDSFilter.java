/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.web.internal.frontend.data.set.filter;

import com.liferay.commerce.order.status.CommerceOrderStatusRegistry;
import com.liferay.commerce.order.web.internal.constants.CommerceOrderFDSNames;
import com.liferay.frontend.data.set.constants.FDSEntityFieldTypes;
import com.liferay.frontend.data.set.filter.BaseSelectionFDSFilter;
import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.frontend.data.set.filter.SelectionFDSFilterItem;
import com.liferay.petra.function.transform.TransformUtil;

import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "frontend.data.set.name=" + CommerceOrderFDSNames.ALL_ORDERS,
	service = FDSFilter.class
)
public class CommerceOrderStatusSelectionFDSFilter
	extends BaseSelectionFDSFilter {

	@Override
	public String getEntityFieldType() {
		return FDSEntityFieldTypes.COLLECTION_INTEGER;
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

		return TransformUtil.transform(
			_commerceOrderStatusRegistry.getCommerceOrderStatuses(),
			commerceOrderStatus -> new SelectionFDSFilterItem(
				commerceOrderStatus.getLabel(locale),
				commerceOrderStatus.getKey()));
	}

	@Reference
	private CommerceOrderStatusRegistry _commerceOrderStatusRegistry;

}