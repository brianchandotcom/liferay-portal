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

package com.liferay.site.admin.web.servlet.taglib.ui;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorConstants;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntry;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.User;
import com.liferay.portal.service.LayoutSetLocalService;
import com.liferay.portal.service.LayoutSetPrototypeLocalService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.Locale;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	property = {"service.ranking:Integer=10"},
	service = FormNavigatorEntry.class
)
public class SiteSiteTemplateFormNavigatorEntry
	extends BaseSiteFormNavigatorEntry {

	@Override
	public String getCategoryKey() {
		return FormNavigatorConstants.CATEGORY_KEY_SITES_BASIC_INFORMATION;
	}

	@Override
	public String getKey() {
		return "site-template";
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "site-template");
	}

	@Override
	public boolean isVisible(User user, Group group) {
		if ((group == null) || group.isCompany()) {
			return false;
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		LayoutSet privateLayoutSet = null;
		LayoutSet publicLayoutSet = null;

		try {
			privateLayoutSet = _layoutSetLocalService.getLayoutSet(
				group.getGroupId(), true);
			publicLayoutSet = _layoutSetLocalService.getLayoutSet(
				group.getGroupId(), false);
		}
		catch (PortalException e) {
			return false;
		}

		if ((privateLayoutSet == null) && (publicLayoutSet == null)) {
			return false;
		}

		LayoutSetPrototype privateLayoutSetPrototype = null;

		LayoutSetPrototype publicLayoutSetPrototype = null;

		if (Validator.isNotNull(privateLayoutSet.getLayoutSetPrototypeUuid())) {
			privateLayoutSetPrototype =
				_layoutSetPrototypeLocalService.
					fetchLayoutSetPrototypeByUuidAndCompanyId(
						privateLayoutSet.getLayoutSetPrototypeUuid(),
						themeDisplay.getCompanyId());
		}

		if (Validator.isNotNull(publicLayoutSet.getLayoutSetPrototypeUuid())) {
			publicLayoutSetPrototype =
				_layoutSetPrototypeLocalService.
					fetchLayoutSetPrototypeByUuidAndCompanyId(
						publicLayoutSet.getLayoutSetPrototypeUuid(),
						themeDisplay.getCompanyId());
		}

		if ((publicLayoutSetPrototype == null) &&
			(privateLayoutSetPrototype == null)) {

			return false;
		}

		return true;
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.site.admin.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	@Override
	protected String getJspPath() {
		return "/site/site_template.jsp";
	}

	@Reference(unbind = "-")
	protected void setLayoutSetLocalService(
		LayoutSetLocalService layoutSetLocalService) {

		_layoutSetLocalService = layoutSetLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutSetPrototypeLocalService(
		LayoutSetPrototypeLocalService layoutSetPrototypeLocalService) {

		_layoutSetPrototypeLocalService = layoutSetPrototypeLocalService;
	}

	private LayoutSetLocalService _layoutSetLocalService;
	private LayoutSetPrototypeLocalService _layoutSetPrototypeLocalService;

}