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

package com.liferay.portlet.shopping.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.webserver.WebServerServletTokenUtil;
import com.liferay.portlet.shopping.model.ShoppingCategory;
import com.liferay.portlet.shopping.model.ShoppingItem;
import com.liferay.portlet.shopping.model.ShoppingItemPrice;
import com.liferay.portlet.shopping.service.ShoppingCategoryLocalServiceUtil;
import com.liferay.portlet.shopping.service.ShoppingItemPriceLocalServiceUtil;
import com.liferay.portlet.shopping.util.comparator.ItemNameComparator;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ShoppingItemImpl extends ShoppingItemBaseImpl {

	public static final int STOCK_QUANTITY_INFINITE_STOCK = -1;

	@Override
	public int compareTo(ShoppingItem item) {
		return new ItemNameComparator(true).compare(this, item);
	}

	@Override
	public ShoppingCategory getCategory() {
		ShoppingCategory category = null;

		if (getCategoryId() > 0) {
			try {
				category = ShoppingCategoryLocalServiceUtil.getCategory(
					getCategoryId());
			}
			catch (Exception e) {
				category = new ShoppingCategoryImpl();

				category.setGroupId(getGroupId());

				_log.error(e);
			}
		}
		else {
			category = new ShoppingCategoryImpl();

			category.setGroupId(getGroupId());
		}

		return category;
	}

	@Override
	public String[] getFieldsQuantitiesArray() {
		return _fieldsQuantitiesArray;
	}

	@Override
	public List<ShoppingItemPrice> getItemPrices() throws PortalException {
		return ShoppingItemPriceLocalServiceUtil.getItemPrices(getItemId());
	}

	@Override
	public String getShoppingItemImageURL(ThemeDisplay themeDisplay) {
		if (!isSmallImage()) {
			return null;
		}

		if (Validator.isNotNull(getSmallImageURL())) {
			return getSmallImageURL();
		}

		return themeDisplay.getPathImage() + "/shopping/item?img_id=" +
			getSmallImageId() + "&t=" +
				WebServerServletTokenUtil.getToken(getSmallImageId());
	}

	@Override
	public boolean isInfiniteStock() {
		if (getStockQuantity() == STOCK_QUANTITY_INFINITE_STOCK) {
			return true;
		}

		return false;
	}

	@Override
	public void setFieldsQuantities(String fieldsQuantities) {
		_fieldsQuantitiesArray = StringUtil.split(fieldsQuantities);

		super.setFieldsQuantities(fieldsQuantities);
	}

	@Override
	public void setFieldsQuantitiesArray(String[] fieldsQuantitiesArray) {
		_fieldsQuantitiesArray = fieldsQuantitiesArray;

		super.setFieldsQuantities(StringUtil.merge(fieldsQuantitiesArray));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ShoppingItemImpl.class);

	private String[] _fieldsQuantitiesArray;

}