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

package com.liferay.commerce.inventory.web.internal.frontend;

import com.liferay.commerce.frontend.CommerceDataSetDataProvider;
import com.liferay.commerce.frontend.Filter;
import com.liferay.commerce.frontend.Pagination;
import com.liferay.commerce.inventory.model.CommerceInventoryBookedQuantity;
import com.liferay.commerce.inventory.service.CommerceInventoryBookedQuantityService;
import com.liferay.commerce.inventory.web.internal.model.BookedQuantity;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.text.DateFormat;
import java.text.Format;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = "commerce.data.provider.key=" + CommerceInventoryDataSetConstants.COMMERCE_DATA_SET_KEY_INVENTORY_BOOKED,
	service = CommerceDataSetDataProvider.class
)
public class CommerceInventoryBookedDataSetDataProvider
	implements CommerceDataSetDataProvider<BookedQuantity> {

	@Override
	public int countItems(HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		String sku = ParamUtil.getString(httpServletRequest, "sku");

		return _commerceInventoryBookedQuantityService.
			getCommerceInventoryBookedQuantitiesCount(
				_portal.getCompanyId(httpServletRequest), sku);
	}

	@Override
	public List<BookedQuantity> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		List<BookedQuantity> bookedQuantities = new ArrayList<>();

		String sku = ParamUtil.getString(httpServletRequest, "sku");

		List<CommerceInventoryBookedQuantity>
			commerceInventoryBookedQuantities =
				_commerceInventoryBookedQuantityService.
					getCommerceInventoryBookedQuantities(
						_portal.getCompanyId(httpServletRequest), sku,
						pagination.getStartPosition(),
						pagination.getEndPosition());

		for (CommerceInventoryBookedQuantity commerceInventoryBookedQuantity :
				commerceInventoryBookedQuantities) {

			CommerceOrderItem commerceOrderItem =
				_commerceOrderItemLocalService.
					fetchCommerceOrderItemByBookedQuantityId(
						commerceInventoryBookedQuantity.
							getCommerceInventoryBookedQuantityId());

			bookedQuantities.add(
				new BookedQuantity(
					_getAccountName(commerceOrderItem),
					_getCommerceOrderId(commerceOrderItem),
					commerceInventoryBookedQuantity.getQuantity(),
					_getExpirationDate(
						commerceInventoryBookedQuantity.getExpirationDate(),
						httpServletRequest)));
		}

		return bookedQuantities;
	}

	private String _getAccountName(CommerceOrderItem commerceOrderItem)
		throws PortalException {

		if (commerceOrderItem == null) {
			return StringPool.BLANK;
		}

		CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

		return commerceOrder.getCommerceAccountName();
	}

	private long _getCommerceOrderId(CommerceOrderItem commerceOrderItem) {
		if (commerceOrderItem == null) {
			return 0;
		}

		return commerceOrderItem.getCommerceOrderId();
	}

	private String _getExpirationDate(
		Date expirationDate, HttpServletRequest httpServletRequest) {

		if (expirationDate == null) {
			return LanguageUtil.get(httpServletRequest, "never-expire");
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Format dateTimeFormat = FastDateFormatFactoryUtil.getDateTime(
			DateFormat.MEDIUM, DateFormat.MEDIUM, themeDisplay.getLocale(),
			themeDisplay.getTimeZone());

		return dateTimeFormat.format(expirationDate);
	}

	@Reference
	private CommerceInventoryBookedQuantityService
		_commerceInventoryBookedQuantityService;

	@Reference
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;

	@Reference
	private Portal _portal;

}