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

package com.liferay.asset.kernel.action;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Jürgen Kappler
 */
@ProviderType
public interface AssetEntryAction<T> {

	public String getDialogTitle(Locale locale);

	public String getDialogURL(
			HttpServletRequest httpServletRequest,
			AssetRenderer<T> assetRenderer)
		throws PortalException;

	public String getIcon();

	public String getMessage(Locale locale);

	public boolean hasPermission(
			PermissionChecker permissionChecker, AssetRenderer<T> assetRenderer)
		throws PortalException;

}