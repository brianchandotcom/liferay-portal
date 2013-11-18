/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.shopping.util;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.shopping.model.ShoppingCategory;
import com.liferay.portlet.shopping.model.ShoppingCategoryConstants;
import com.liferay.portlet.shopping.model.ShoppingItem;
import com.liferay.portlet.shopping.model.ShoppingItemField;
import com.liferay.portlet.shopping.model.ShoppingItemPrice;
import com.liferay.portlet.shopping.service.ShoppingCategoryServiceUtil;
import com.liferay.portlet.shopping.service.ShoppingItemServiceUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eric Chin
 */
public class ShoppingTestUtil {

	public static ShoppingCategory addCategory(long groupId) throws Exception {
		return addCategory(
			groupId, ShoppingCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);
	}

	public static ShoppingCategory addCategory(
			long groupId, long parentCategoryId)
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			groupId);

		return addCategory(
			ServiceTestUtil.randomString(), parentCategoryId, serviceContext);
	}

	public static ShoppingCategory addCategory(ServiceContext serviceContext)
		throws Exception {

		return ShoppingCategoryServiceUtil.addCategory(
			TestPropsValues.getUserId(), ServiceTestUtil.randomString(),
			StringPool.BLANK, serviceContext);
	}

	public static ShoppingCategory addCategory(
			String name, long groupId, long parentCategoryId)
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			groupId);

		return addCategory(name, parentCategoryId, serviceContext);
	}

	public static ShoppingCategory addCategory(
			String name, long parentCategoryId, ServiceContext serviceContext)
		throws Exception {

		return ShoppingCategoryServiceUtil.addCategory(
			parentCategoryId, name, StringPool.BLANK, serviceContext);
	}

	public static ShoppingItem addItem(long groupId) throws Exception {
		return addItem(
			groupId, ShoppingCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);
	}

	public static ShoppingItem addItem(long groupId, long parentCategoryId)
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			groupId);

		return addItem(
			ServiceTestUtil.randomString(), parentCategoryId, serviceContext);
	}

	public static ShoppingItem addItem(
			String name, long groupId, long parentCategoryId)
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			groupId);

		return addItem(name, parentCategoryId, serviceContext);
	}

	public static ShoppingItem addItem(
			String name, long parentCategoryId, ServiceContext serviceContext)
		throws Exception {

		long groupId = serviceContext.getScopeGroupId();
		String sku = ServiceTestUtil.randomString();
		String description = ServiceTestUtil.randomString();
		String properties = StringPool.BLANK;
		String fieldsQuantities = ServiceTestUtil.randomString();
		boolean requiresShipping = ServiceTestUtil.randomBoolean();
		int stockQuantity = ServiceTestUtil.randomInt();
		boolean featured = ServiceTestUtil.randomBoolean();
		Boolean sale = ServiceTestUtil.randomBoolean();
		boolean smallImage = false;
		String smallImageURL = null;
		File smallImageFile = null;
		boolean mediumImage = false;
		String mediumImageURL = null;
		File mediumImageFile = null;
		boolean largeImage = false;
		String largeImageURL = null;
		File largeImageFile = null;
		List<ShoppingItemField> itemFields = new ArrayList<ShoppingItemField>();
		List<ShoppingItemPrice> itemPrices = new ArrayList<ShoppingItemPrice>();

		return ShoppingItemServiceUtil.addItem(
			groupId, parentCategoryId, sku, name, description, properties,
			fieldsQuantities, requiresShipping, stockQuantity, featured, sale,
			smallImage, smallImageURL, smallImageFile, mediumImage,
			mediumImageURL, mediumImageFile, largeImage, largeImageURL,
			largeImageFile, itemFields, itemPrices, serviceContext);
	}

}