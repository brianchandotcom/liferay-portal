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

package com.liferay.commerce.product.demo.data.creator.internal.util;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

/**
 * @author Alessio Antonio Rendina
 */
public class BaseCPDemoDataCreatorHelper {

	public JSONArray getCatalog() throws IOException, JSONException {
		Class<?> clazz = getClass();

		String catalogPath =
			"com/liferay/commerce/product/demo/data/creator/internal" +
				"/dependencies/products.json";

		String catalogFile = StringUtil.read(
			clazz.getClassLoader(), catalogPath, false);

		JSONArray catalog = JSONFactoryUtil.createJSONArray(catalogFile);

		return catalog;
	}

	public ServiceContext getServiceContext(long userId, long groupId) {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setUserId(userId);
		serviceContext.setScopeGroupId(groupId);

		return serviceContext;
	}

}