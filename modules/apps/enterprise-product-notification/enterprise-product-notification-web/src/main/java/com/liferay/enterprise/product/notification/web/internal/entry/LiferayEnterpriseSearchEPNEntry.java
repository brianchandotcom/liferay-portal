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

import java.util.Locale;

/**
 * @author Drew Brokke
 */
public class LiferayEnterpriseSearchEPNEntry implements EPNEntry {

	@Override
	public String getBodyHTML(Locale locale) {
		return LanguageUtil.format(
			locale, "enterprise-product-notification-body[" + getKey() + "]",
			new String[] {
				String.format(
					"<a href=\"%s\" target=\"_blank\">",
					_LIFERAY_ENTERPRISE_SEARCH_ACTIVATION_DOCUMENTATION_URL),
				"</a>",
				String.format(
					"<a href=\"mailto:%s\"}>%s</a>",
					_LIFERAY_SALES_EMAIL_ADDRESS, _LIFERAY_SALES_EMAIL_ADDRESS)
			});
	}

	@Override
	public String getKey() {
		return "enterprise.search";
	}

	private static final String
		_LIFERAY_ENTERPRISE_SEARCH_ACTIVATION_DOCUMENTATION_URL =
			"https://learn.liferay.com/dxp/latest/en/using-search/liferay-" +
				"enterprise-search/activating-liferay-enterprise-search.html";

	private static final String _LIFERAY_SALES_EMAIL_ADDRESS =
		"sales@liferay.com";

}