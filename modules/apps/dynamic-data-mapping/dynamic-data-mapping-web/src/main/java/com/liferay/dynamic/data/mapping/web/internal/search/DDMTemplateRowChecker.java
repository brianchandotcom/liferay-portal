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

package com.liferay.dynamic.data.mapping.web.internal.search;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.web.internal.security.permission.resource.DDMTemplatePermission;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;

import javax.portlet.PortletResponse;

/**
 * @author laszlo.pap
 */
public class DDMTemplateRowChecker extends EmptyOnClickRowChecker {

	public DDMTemplateRowChecker(PortletResponse portletResponse) {
		super(portletResponse);
	}

	@Override
	public boolean isDisabled(Object obj) {
		DDMTemplate template = (DDMTemplate)obj;

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			if (!DDMTemplatePermission.contains(
					permissionChecker, template, ActionKeys.DELETE)) {

				return true;
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return super.isDisabled(obj);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMTemplateRowChecker.class);

}