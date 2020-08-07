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

package com.liferay.app.builder.web.internal.portlet.tab;

import com.liferay.app.builder.portlet.tab.AppBuilderAppsPortletTab;
import com.liferay.app.builder.rest.resource.v1_0.AppResource;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(
	immediate = true, property = "app.builder.apps.tabs.name=standard",
	service = AppBuilderAppsPortletTab.class
)
public class StandardAppBuilderAppsPortletTab
	implements AppBuilderAppsPortletTab {

	@Override
	public void deleteApp(long appBuilderAppId, User user) throws Exception {
		AppResource appResource = AppResource.builder(
		).user(
			user
		).build();

		appResource.deleteApp(appBuilderAppId);
	}

	@Override
	public String getEditEntryPoint() {
		return _npmResolver.resolveModuleName(
			"app-builder-web/js/pages/apps/edit/EditApp.es");
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(
			ResourceBundleUtil.getModuleAndPortalResourceBundle(
				locale, getClass()),
			"standard");
	}

	@Override
	public String getListEntryPoint() {
		return _npmResolver.resolveModuleName(
			"app-builder-web/js/pages/apps/ListStandardApps.es");
	}

	@Reference
	private Language _language;

	@Reference
	private NPMResolver _npmResolver;

}