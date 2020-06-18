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

import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.portlet.tab.AppBuilderAppPortletTab;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(
	immediate = true, property = "app.builder.app.tab.name=standard",
	service = AppBuilderAppPortletTab.class
)
public class StandardAppBuilderAppPortletTab
	implements AppBuilderAppPortletTab {

	@Override
	public List<Long> getDataLayoutIds(
		AppBuilderApp appBuilderApp, long dataRecordId) {

		return Collections.singletonList(
			appBuilderApp.getDdmStructureLayoutId());
	}

	@Override
	public String getEditEntryPoint() {
		return _npmResolver.resolveModuleName(
			"app-builder-web/js/pages/entry/EditEntry.es");
	}

	@Override
	public String getListEntryPoint() {
		return _npmResolver.resolveModuleName(
			"app-builder-web/js/pages/entry/ListEntries.es");
	}

	@Override
	public String getViewEntryPoint() {
		return _npmResolver.resolveModuleName(
			"app-builder-web/js/pages/entry/ViewEntry.es");
	}

	@Reference
	private NPMResolver _npmResolver;

}