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

package com.liferay.asset.publisher.web.internal.util;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class AssetRendererUtil {

	public static String getAssetRendererUserFullName(
		AssetRenderer assetRenderer, HttpServletRequest httpServletRequest) {

		long assetRendererUserId = assetRenderer.getUserId();

		if (assetRendererUserId > 0) {
			User user = UserLocalServiceUtil.fetchUser(assetRendererUserId);

			if (user == null) {
				return LanguageUtil.get(httpServletRequest, "deleted-user");
			}

			return user.getFullName();
		}

		return LanguageUtil.get(httpServletRequest, "anonymous");
	}

}