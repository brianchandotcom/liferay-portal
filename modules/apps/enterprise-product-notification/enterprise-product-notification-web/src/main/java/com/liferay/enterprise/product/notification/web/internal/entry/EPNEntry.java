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

package com.liferay.enterprise.product.notification.web.internal.entry;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;

import java.util.Locale;

/**
 * @author Drew Brokke
 */
public interface EPNEntry {

	public String getBodyHTML(Locale locale);

	public default String getDisplayName(Locale locale) {
		return LanguageUtil.get(
			locale, "enterprise-product-notification-title[" + getKey() + "]");
	}

	public String getKey();

	public default boolean isShow() {
		return GetterUtil.getBoolean(
			PropsUtil.get("enterprise.product." + getKey() + ".enabled"));
	}

}