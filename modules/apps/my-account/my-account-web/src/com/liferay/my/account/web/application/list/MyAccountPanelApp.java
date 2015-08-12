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

package com.liferay.my.account.web.application.list;

import com.liferay.application.list.BaseControlPanelEntryPanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.my.account.web.constants.MyAccountPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.PortletLocalService;
import com.liferay.portal.util.PortalUtil;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {
		"panel.category.key=" + PanelCategoryKeys.MY_SPACE_PRODUCTIVITY_CENTER,
		"service.ranking:Integer=100"
	},
	service = PanelApp.class
)
public class MyAccountPanelApp extends BaseControlPanelEntryPanelApp {

	@Override
	public String getParentCategoryKey() {
		return PanelCategoryKeys.MY_SPACE_PRODUCTIVITY_CENTER;
	}

	@Override
	public String getPortletId() {
		return MyAccountPortletKeys.MY_ACCOUNT;
	}

	@Override
	protected Group getGroup(HttpServletRequest request) {
		Group group = null;

		try {
			User user = PortalUtil.getUser(request);

			return user.getGroup();
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}

		return group;
	}

	@Reference(unbind = "-")
	protected void setPortletLocalService(
		PortletLocalService portletLocalService) {

		_portletLocalService = portletLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MyAccountPanelApp.class);

}