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

package com.liferay.sharing.web.internal.portal.settings.configuration.admin.display;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.site.settings.configuration.admin.display.SiteSettingsConfigurationScreenContributor;

import java.util.Locale;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = SiteSettingsConfigurationScreenContributor.class)
public class ContentSharingSiteSettingsConfigurationScreenContributor
	implements SiteSettingsConfigurationScreenContributor {

	@Override
	public String getCategoryKey() {
		return "sharing";
	}

	@Override
	public String getJspPath() {
		return "/site_settings/content_sharing.jsp";
	}

	@Override
	public String getKey() {
		return "site-configuration-content-sharing";
	}

	@Override
	public String getName(Locale locale) {
		return LanguageUtil.get(locale, "content-sharing");
	}

	@Override
	public String getSaveMVCActionCommandName() {
		return "/sharing/edit_content_sharing";
	}

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public boolean isVisible(Group group) {
		int contentSharingWithChildrenEnabled = PrefsPropsUtil.getInteger(
			group.getCompanyId(),
			PropsKeys.SITES_CONTENT_SHARING_WITH_CHILDREN_ENABLED);

		if (contentSharingWithChildrenEnabled == 0) {
			return false;
		}

		return true;
	}

	@Reference(target = "(osgi.web.symbolicname=com.liferay.sharing.web)")
	private ServletContext _servletContext;

}