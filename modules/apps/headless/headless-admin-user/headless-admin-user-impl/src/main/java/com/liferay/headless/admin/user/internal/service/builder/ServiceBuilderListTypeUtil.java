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

package com.liferay.headless.admin.user.internal.service.builder;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.service.ListTypeLocalServiceUtil;
import com.liferay.portal.kernel.service.ListTypeServiceUtil;

import java.util.Locale;

/**
 * @author Drew Brokke
 */
public class ServiceBuilderListTypeUtil {

	public static long getServiceBuilderListTypeId(String value, String type) {
		ListType listType = ListTypeLocalServiceUtil.addListType(value, type);

		return listType.getListTypeId();
	}

	public static String getServiceBuilderListTypeMessage(
			long listTypeId, Locale locale)
		throws Exception {

		if (listTypeId == 0) {
			return null;
		}

		ListType listType = ListTypeServiceUtil.getListType(listTypeId);

		return LanguageUtil.get(locale, listType.getName());
	}

	public static long toServiceBuilderListTypeId(
		String defaultName, String name, String type) {

		ListType listType = ListTypeLocalServiceUtil.getListType(name, type);

		if (listType == null) {
			listType = ListTypeLocalServiceUtil.getListType(defaultName, type);
		}

		if (listType != null) {
			return listType.getListTypeId();
		}

		return 0;
	}

}