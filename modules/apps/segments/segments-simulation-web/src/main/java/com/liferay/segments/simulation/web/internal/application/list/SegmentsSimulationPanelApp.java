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

package com.liferay.segments.simulation.web.internal.application.list;

import com.liferay.application.list.BaseJSPPanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.product.navigation.simulation.constants.ProductNavigationSimulationConstants;
import com.liferay.segments.configuration.provider.SegmentsConfigurationProvider;
import com.liferay.segments.constants.SegmentsActionKeys;
import com.liferay.segments.constants.SegmentsConstants;
import com.liferay.segments.constants.SegmentsPortletKeys;
import com.liferay.segments.simulation.web.internal.display.context.SegmentsSimulationDisplayContext;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(
	property = {
		"panel.app.order:Integer=200",
		"panel.category.key=" + ProductNavigationSimulationConstants.SIMULATION_PANEL_CATEGORY_KEY
	},
	service = PanelApp.class
)
public class SegmentsSimulationPanelApp extends BaseJSPPanelApp {

	@Override
	public String getJspPath() {
		return "/view.jsp";
	}

	@Override
	public String getLabel(Locale locale) {
		if (FeatureFlagManagerUtil.isEnabled("LPS-186155")) {
			return _language.get(locale, "page-content");
		}

		return _language.get(locale, "segments");
	}

	@Override
	public Portlet getPortlet() {
		return _portlet;
	}

	@Override
	public String getPortletId() {
		return SegmentsPortletKeys.SEGMENTS_SIMULATION;
	}

	@Override
	public boolean include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		httpServletRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			new SegmentsSimulationDisplayContext(
				httpServletRequest, _segmentsConfigurationProvider));

		return super.include(httpServletRequest, httpServletResponse);
	}

	@Override
	public boolean isShow(PermissionChecker permissionChecker, Group group) {
		if (group.isControlPanel()) {
			return false;
		}

		return _portletResourcePermission.contains(
			permissionChecker, group,
			SegmentsActionKeys.SIMULATE_SEGMENTS_ENTRIES);
	}

	@Override
	protected ServletContext getServletContext() {
		return _servletContext;
	}

	@Reference
	private Language _language;

	@Reference(
		target = "(javax.portlet.name=" + SegmentsPortletKeys.SEGMENTS_SIMULATION + ")"
	)
	private Portlet _portlet;

	@Reference(
		target = "(resource.name=" + SegmentsConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

	@Reference
	private SegmentsConfigurationProvider _segmentsConfigurationProvider;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.segments.simulation.web)"
	)
	private ServletContext _servletContext;

}