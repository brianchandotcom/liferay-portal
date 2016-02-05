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

package com.liferay.portlet.social.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portlet.social.service.base.SocialRequestServiceBaseImpl;
import com.liferay.social.kernel.model.SocialRequest;
import com.liferay.social.kernel.service.permission.SocialRequestPermissionUtil;

/**
 * Provides the remote service for updating social requests. Its methods include
 * permission checks.
 *
 * @author Brian Wing Shun Chan
 */
public class SocialRequestServiceImpl extends SocialRequestServiceBaseImpl {

	@Override
	public SocialRequest updateRequest(
			long requestId, int status, ThemeDisplay themeDisplay)
		throws PortalException {

		SocialRequestPermissionUtil.check(
			getPermissionChecker(), requestId, ActionKeys.UPDATE);

		return socialRequestLocalService.updateRequest(
			requestId, status, themeDisplay);
	}

}