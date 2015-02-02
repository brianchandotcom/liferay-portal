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

package com.liferay.portlet.shopping.util.test;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.ServiceContext;
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

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		return addCategory(
			RandomTestUtil.randomString(), parentCategoryId, serviceContext);
	}

	public static ShoppingCategory addCategory(ServiceContext serviceContext)
		throws Exception {

		return ShoppingCategoryServiceUtil.addCategory(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			StringPool.BLANK, serviceContext);
	}

	public static ShoppingCategory addCategory(
			String name, long groupId, long parentCategoryId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

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

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		return addItem(
			RandomTestUtil.randomString(), parentCategoryId, serviceContext);
	}

	public static ShoppingItem addItem(
			String name, long groupId, long parentCategoryId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		return addItem(name, parentCategoryId, serviceContext);
	}

	public static ShoppingItem addItem(
			String name, long parentCategoryId, ServiceContext serviceContext)
		throws Exception {

		long groupId = serviceContext.getScopeGroupId();
		String sku = RandomTestUtil.randomString();
		String description = RandomTestUtil.randomString();
		String properties = StringPool.BLANK;
		String fieldsQuantities = RandomTestUtil.randomString();
		boolean requiresShipping = RandomTestUtil.randomBoolean();
		int stockQuantity = RandomTestUtil.randomInt();
		boolean featured = RandomTestUtil.randomBoolean();
		Boolean sale = RandomTestUtil.randomBoolean();
		boolean smallImage = false;
		String smallImageURL = null;
		File smallImageFile = null;
		boolean mediumImage = false;
		String mediumImageURL = null;
		File mediumImageFile = null;
		boolean largeImage = false;
		String largeImageURL = null;
		File largeImageFile = null;
		List<ShoppingItemField> itemFields = new ArrayList<>();
		List<ShoppingItemPrice> itemPrices = new ArrayList<>();

		return ShoppingItemServiceUtil.addItem(
			groupId, parentCategoryId, sku, name, description, properties,
			fieldsQuantities, requiresShipping, stockQuantity, featured, sale,
			smallImage, smallImageURL, smallImageFile, mediumImage,
			mediumImageURL, mediumImageFile, largeImage, largeImageURL,
			largeImageFile, itemFields, itemPrices, serviceContext);
	}

}