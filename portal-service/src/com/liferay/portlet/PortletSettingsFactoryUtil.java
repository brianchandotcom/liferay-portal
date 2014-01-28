/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.model.Layout;

/**
 * @author Raymond Augé
 * @author Jorge Ferrer
 */
@ProviderType
public class PortletSettingsFactoryUtil {

	public static PortletSettings getPortletCompanySettings(
			long companyId, String portletId)
		throws SystemException {

		return getPortletSettingsFactory().getPortletCompanySettings(
			companyId, portletId);
	}

	public static PortletSettings getPortletInstanceSettings(
			Layout layout, String portletId)
		throws SystemException {

		return getPortletSettingsFactory().getPortletInstanceSettings(
			layout, portletId);
	}

	public static PortletSettingsFactory getPortletSettingsFactory() {
		PortalRuntimePermission.checkGetBeanProperty(
			PortletSettingsFactoryUtil.class);

		return _portletSettingsFactory;
	}

	public static PortletSettings getPortletSiteSettings(
			long groupId, String portletId)
		throws PortalException, SystemException {

		return getPortletSettingsFactory().getPortletSiteSettings(
			groupId, portletId);
	}

	public void setPortletSettingsFactory(
		PortletSettingsFactory portletPreferencesFactory) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_portletSettingsFactory = portletPreferencesFactory;
	}

	private static PortletSettingsFactory _portletSettingsFactory;

}