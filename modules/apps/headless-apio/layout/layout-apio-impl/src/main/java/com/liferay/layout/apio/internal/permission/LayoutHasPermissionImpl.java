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

package com.liferay.layout.apio.internal.permission;

import com.liferay.apio.architect.credentials.Credentials;
import com.liferay.apio.architect.functional.Try;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Hernández
 */
@Component(property = "model.class.name=com.liferay.portal.kernel.model.Layout")
public class LayoutHasPermissionImpl implements HasPermission<Long, Long> {

	@Override
	public Boolean forDeleting(Credentials credentials, Long plid) {
		return Try.fromFallible(
			() -> LayoutPermissionUtil.contains(
				(PermissionChecker)credentials.get(), plid, ActionKeys.DELETE)
		).orElse(
			false
		);
	}

}