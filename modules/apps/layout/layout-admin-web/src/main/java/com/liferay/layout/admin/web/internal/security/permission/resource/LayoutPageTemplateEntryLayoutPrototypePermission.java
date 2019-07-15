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

package com.liferay.layout.admin.web.internal.security.permission.resource;

import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.LayoutPrototypePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Couso
 */
@Component(
	immediate = true, property = "extended=true",
	service = LayoutPrototypePermission.class
)
public class LayoutPageTemplateEntryLayoutPrototypePermission
	implements LayoutPrototypePermission {

	@Override
	public void check(
			PermissionChecker permissionChecker, long layoutPrototypeId,
			String actionId)
		throws PrincipalException {

		if (!contains(permissionChecker, layoutPrototypeId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, LayoutPrototype.class.getName(),
				layoutPrototypeId, actionId);
		}
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, long layoutPrototypeId,
		String actionId) {

		try {
			if (LayoutPageTemplateEntryPermission.contains(
					permissionChecker,
					_getLayoutPageTemplateEntry(layoutPrototypeId), actionId)) {

				return true;
			}
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(pe, pe);
			}
		}

		return false;
	}

	private LayoutPageTemplateEntry _getLayoutPageTemplateEntry(
		long layoutPrototypeId) {

		return _layoutPageTemplateEntryLocalService.
			fetchFirstLayoutPageTemplateEntry(layoutPrototypeId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutPageTemplateEntryLayoutPrototypePermission.class);

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

}