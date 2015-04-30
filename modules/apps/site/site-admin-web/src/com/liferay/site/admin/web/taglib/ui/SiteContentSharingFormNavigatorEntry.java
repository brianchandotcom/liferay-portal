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

package com.liferay.site.admin.web.taglib.ui;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorConstants;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;

import java.util.Locale;

/**
 * @author Sergio González
 */
@OSGiBeanProperties(property = {"service.ranking:Integer=20"})
public class SiteContentSharingFormNavigatorEntry
	extends BaseSiteFormNavigatorEntry {

	@Override
	public String getCategoryKey() {
		return FormNavigatorConstants.CATEGORY_KEY_SITES_ADVANCED;
	}

	@Override
	public String getKey() {
		return "content-sharing";
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "content-sharing");
	}

	@Override
	public boolean isVisible(User user, Group group) {
		if (group == null) {
			return false;
		}

		int contentSharingWithChildrenEnabled = PrefsPropsUtil.getInteger(
			group.getCompanyId(),
			PropsKeys.SITES_CONTENT_SHARING_WITH_CHILDREN_ENABLED);

		if (contentSharingWithChildrenEnabled == 0) {
			return false;
		}

		return true;
	}

	@Override
	protected String getJspPath() {
		return "/site/content_sharing.jsp";
	}

}