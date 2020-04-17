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

package com.liferay.app.builder.internal.data.engine.content.type;

import com.liferay.app.builder.constants.AppBuilderConstants;
import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.data.engine.content.type.DataDefinitionContentType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.Portal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jeyvison Nascimento
 */
@Component(
	immediate = true, property = "content.type=app-builder",
	service = DataDefinitionContentType.class
)
public class AppBuilderDataDefinitionContentType
	implements DataDefinitionContentType {

	@Override
	public long getClassNameId() {
		return _portal.getClassNameId(AppBuilderApp.class);
	}

	@Override
	public String getContentType() {
		return "app-builder";
	}

	@Override
	public String getPortletResourceName() {
		return AppBuilderConstants.RESOURCE_NAME;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, long companyId, long groupId,
			String resourceName, long primKey, long userId, String actionId)
		throws PortalException {

		if (_portletResourcePermission.contains(
				PermissionThreadLocal.getPermissionChecker(), groupId,
				ActionKeys.MANAGE)) {

			return true;
		}

		return DataDefinitionContentType.super.hasPermission(
			permissionChecker, companyId, groupId, resourceName, primKey,
			userId, actionId);
	}

	@Override
	public boolean hasPortletPermission(
			PermissionChecker permissionChecker, long groupId, String actionId)
		throws PortalException {

		if (_portletResourcePermission.contains(
				PermissionThreadLocal.getPermissionChecker(), groupId,
				ActionKeys.MANAGE)) {

			return true;
		}

		return _portletResourcePermission.contains(
			permissionChecker, groupId, actionId);
	}

	@Override
	public boolean isDataRecordCollectionPermissionCheckingEnabled() {
		return true;
	}

	@Reference
	private Portal _portal;

	@Reference(
		target = "(resource.name=" + AppBuilderConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}