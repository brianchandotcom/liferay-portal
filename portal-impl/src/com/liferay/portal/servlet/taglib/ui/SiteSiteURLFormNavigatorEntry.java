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

package com.liferay.portal.servlet.taglib.ui;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;

import java.util.Locale;

/**
 * @author Sergio González
 */
@OSGiBeanProperties(property = {"service.ranking:Integer=30"})
public class SiteSiteURLFormNavigatorEntry extends BaseSiteFormNavigatorEntry {

	@Override
	public String getCategoryKey() {
		return "basic-information";
	}

	@Override
	public String getKey() {
		return "url";
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "url");
	}

	@Override
	public boolean isVisible(User user, Group group) {
		if ((group == null) || group.isCompany()) {
			return false;
		}

		return true;
	}

	@Override
	protected String getJspPath() {
		return "/html/portlet/sites_admin/site/site_url.jsp";
	}

}