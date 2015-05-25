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
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;

/**
 * @author Pei-Jung Lan
 */
@OSGiBeanProperties(property = {"service.ranking:Integer=10"})
public class LayoutCustomizationSettingsFormNavigatorEntry
	extends BaseLayoutFormNavigatorEntry {

	@Override
	public String getKey() {
		return "customization-settings";
	}

	@Override
	public boolean isVisible(User user, Layout layout) {
		try {
			Group group = layout.getGroup();

			if (!group.isUser() && layout.isTypePortlet()) {
				return true;
			}
		}
		catch (PortalException pe) {
			_log.error("Unable to display form for customization settings", pe);
		}

		return false;
	}

	@Override
	protected String getJspPath() {
		return "/html/portlet/layouts_admin/layout/customization_settings.jsp";
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutCustomizationSettingsFormNavigatorEntry.class);

}