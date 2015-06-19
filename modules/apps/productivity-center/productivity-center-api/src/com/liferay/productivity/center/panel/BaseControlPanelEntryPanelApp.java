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

package com.liferay.productivity.center.panel;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.PortletLocalService;
import com.liferay.portlet.ControlPanelEntry;

import java.util.Locale;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseControlPanelEntryPanelApp implements PanelApp {

	@Override
	public String getKey() {
		return getClass().getName();
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(
			locale,
			JavaConstants.JAVAX_PORTLET_TITLE + StringPool.PERIOD +
				getPortletId());
	}

	@Override
	public boolean hasAccessPermission(
			PermissionChecker permissionChecker, Group group)
		throws PortalException {

		try {
			ControlPanelEntry controlPanelEntry = getControlPanelEntry();

			if (controlPanelEntry == null) {
				return false;
			}

			return controlPanelEntry.hasAccessPermission(
				permissionChecker, group, getPortlet());
		}
		catch (PortalException | RuntimeException e) {
			throw e;
		}
		catch (Exception e) {
			throw new PortalException(e);
		}
	}

	protected ControlPanelEntry getControlPanelEntry() {
		Portlet portlet = getPortlet();

		return portlet.getControlPanelEntryInstance();
	}

	protected Portlet getPortlet() {
		return _portletLocalService.getPortletById(getPortletId());
	}

	protected PortletLocalService _portletLocalService;

}