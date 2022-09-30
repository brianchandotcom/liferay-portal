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

package com.liferay.object.web.internal.info.permission;

import com.liferay.info.permission.provider.InfoPermissionProvider;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.permission.PortletPermission;

/**
 * @author Lourdes Fernández Besada
 */
public class ObjectEntryInfoPermissionProvider
	implements InfoPermissionProvider<ObjectEntry> {

	public ObjectEntryInfoPermissionProvider(
		ObjectDefinition objectDefinition,
		PortletLocalService portletLocalService,
		PortletPermission portletPermission) {

		_objectDefinition = objectDefinition;
		_portletLocalService = portletLocalService;
		_portletPermission = portletPermission;
	}

	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker) {
		Portlet portlet = _portletLocalService.getPortletById(
			_objectDefinition.getCompanyId(), _objectDefinition.getPortletId());

		if (!portlet.isActive()) {
			return false;
		}

		try {
			return _portletPermission.contains(
				permissionChecker, portlet.getRootPortletId(), ActionKeys.VIEW);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryInfoPermissionProvider.class);

	private final ObjectDefinition _objectDefinition;
	private final PortletLocalService _portletLocalService;
	private final PortletPermission _portletPermission;

}