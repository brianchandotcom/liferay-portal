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

package com.liferay.frontend.js.spa.web.servlet.taglib.util;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.servlet.ServletResponseConstants;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.lang.reflect.Field;

import java.util.List;

/**
 * @author Bruno Basto
 */
public class SPAUtil {

	public static String getPortletsBlacklist(ThemeDisplay themeDisplay) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		List<Portlet> companyPortlets = PortletLocalServiceUtil.getPortlets(
			themeDisplay.getCompanyId());

		for (Portlet portlet : companyPortlets) {
			if (portlet.isActive() && portlet.isReady() &&
				!portlet.isUndeployedPortlet() &&
				!portlet.isSinglePageApplication()) {

				jsonObject.put(portlet.getPortletId(), true);
			}
		}

		return jsonObject.toString();
	}

	public static String getValidStatusCodes() {
		Class<?> clazz = ServletResponseConstants.class;

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Field field : clazz.getDeclaredFields()) {
			try {
				jsonArray.put(field.getInt(null));
			}
			catch (Exception e) {
			}
		}

		return jsonArray.toJSONString();
	}

}