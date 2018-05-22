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

package com.liferay.blog.apio.internal.permission;

import com.liferay.apio.architect.credentials.Credentials;
import com.liferay.apio.architect.functional.Try;
import com.liferay.blogs.constants.BlogsConstants;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(property = "model.class.name=com.liferay.blogs.model.BlogsEntry")
public class BlogsEntryHasPermissionImpl implements HasPermission<Long, Long> {

	@Override
	public Boolean forAdding(Credentials credentials, Long groupId) {
		return Try.fromFallible(
			() -> _portletResourcePermission.contains(
				(PermissionChecker)credentials.get(), groupId,
				ActionKeys.ADD_ENTRY)
		).orElse(
			false
		);
	}

	@Override
	public Boolean forDeleting(Credentials credentials, Long entryId) {
		return Try.fromFallible(
			() -> _modelResourcePermission.contains(
				(PermissionChecker)credentials.get(), entryId,
				ActionKeys.DELETE)
		).orElse(
			false
		);
	}

	@Override
	public Boolean forUpdating(Credentials credentials, Long entryId) {
		return Try.fromFallible(
			() -> _modelResourcePermission.contains(
				(PermissionChecker)credentials.get(), entryId,
				ActionKeys.UPDATE)
		).orElse(
			false
		);
	}

	@Reference(target = "(model.class.name=com.liferay.blogs.model.BlogsEntry)")
	private ModelResourcePermission _modelResourcePermission;

	@Reference(target = "(resource.name=" + BlogsConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

}