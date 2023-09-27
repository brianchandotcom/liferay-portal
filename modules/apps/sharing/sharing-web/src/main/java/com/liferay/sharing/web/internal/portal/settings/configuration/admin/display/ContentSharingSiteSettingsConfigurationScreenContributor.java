/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.web.internal.portal.settings.configuration.admin.display;

import com.liferay.portal.kernel.language.Language;
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
		return _language.get(locale, "content-sharing");
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

	@Reference
	private Language _language;

	@Reference(target = "(osgi.web.symbolicname=com.liferay.sharing.web)")
	private ServletContext _servletContext;

}