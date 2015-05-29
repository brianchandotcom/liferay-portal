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
import com.liferay.portal.model.Group;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.User;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 */
@Component(
	property = {"service.ranking:Integer=20"},
	service = FormNavigatorEntry.class
)
public class LayoutSetAdvancedFormNavigatorEntry
	extends BaseLayoutSetFormNavigatorEntry {

	@Override
	public String getKey() {
		return "advanced";
	}

	@Override
	public boolean isVisible(User user, LayoutSet layoutSet) {
		try {
			Group group = layoutSet.getGroup();

			if (group.isGuest()) {
				return false;
			}
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}

		return true;
	}

	@Override
	protected String getJspPath() {
		return "/html/portlet/layouts_admin/layout_set/advanced.jsp";
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSetAdvancedFormNavigatorEntry.class);

}