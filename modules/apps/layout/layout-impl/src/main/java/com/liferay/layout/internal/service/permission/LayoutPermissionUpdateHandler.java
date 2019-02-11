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

package com.liferay.layout.internal.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.permission.PermissionUpdateHandler;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	property = "model.class.name=com.liferay.portal.kernel.model.Layout",
	service = PermissionUpdateHandler.class
)
public class LayoutPermissionUpdateHandler implements PermissionUpdateHandler {

	@Override
	public void updatedPermission(String primKey) {
		Layout layout = _layoutLocalService.fetchLayout(
			GetterUtil.getLong(primKey));

		if (layout == null) {
			return;
		}

		layout.setModifiedDate(new Date());

		try {
			_layoutLocalService.updateLayout(layout);
		}
		catch (PortalException pe) {
			_log.error(pe.getMessage(), pe);
		}
	}

	@Reference(unbind = "-")
	protected void setLayoutSetLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutPermissionUpdateHandler.class);

	private LayoutLocalService _layoutLocalService;

}