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

package com.liferay.layout.admin.web.servlet.taglib.ui;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntry;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.User;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = {"service.ranking:Integer=20"},
	service = FormNavigatorEntry.class
)
public class LayoutEmbeddedPortletsFormNavigatorEntry
	extends BaseLayoutFormNavigatorEntry {

	@Override
	public String getKey() {
		return "embedded-portlets";
	}

	@Override
	public boolean isVisible(User user, Layout layout) {
		if (!layout.isSupportsEmbeddedPortlets()) {
			return false;
		}

		try {
			List<Portlet> embeddedPortlets = new ArrayList<>();

			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)layout.getLayoutType();

			List<String> portletIds = layoutTypePortlet.getPortletIds();

			for (Portlet portlet : layoutTypePortlet.getAllPortlets(false)) {
				if (!portletIds.contains(portlet.getPortletId())) {
					embeddedPortlets.add(portlet);
				}
			}

			if (!embeddedPortlets.isEmpty()) {
				return true;
			}
		}
		catch (PortalException pe) {
			_log.error("Unable to display form for embedded portlets", pe);
		}

		return false;
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.layout.admin.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	@Override
	protected String getJspPath() {
		return "/layout/embedded_portlets.jsp";
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutEmbeddedPortletsFormNavigatorEntry.class);

}