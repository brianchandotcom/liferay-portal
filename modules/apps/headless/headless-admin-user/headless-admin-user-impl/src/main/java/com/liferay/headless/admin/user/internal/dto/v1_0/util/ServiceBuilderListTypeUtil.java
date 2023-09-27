/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.internal.dto.v1_0.util;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.service.ListTypeLocalServiceUtil;
import com.liferay.portal.kernel.service.ListTypeServiceUtil;

import java.util.Locale;

/**
 * @author Drew Brokke
 */
public class ServiceBuilderListTypeUtil {

	public static long getServiceBuilderListTypeId(String type, String value) {
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