/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.social.model.SocialRequest;
import com.liferay.portlet.social.service.base.SocialRequestServiceBaseImpl;
import com.liferay.portlet.social.service.permission.SocialRequestPermission;

/**
 * The implementation of the social request remote service.
 *
 * @author Brian Wing Shun Chan
 */
public class SocialRequestServiceImpl extends SocialRequestServiceBaseImpl {

	@Override
	public SocialRequest updateRequest(
			long requestId, int status, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		SocialRequestPermission.check(
			getPermissionChecker(), requestId, ActionKeys.UPDATE);

		return socialRequestLocalService.updateRequest(
			requestId, status, themeDisplay);
	}

}