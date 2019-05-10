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

package com.liferay.users.admin.user.action.contributor;

import com.liferay.portal.kernel.model.User;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Drew Brokke
 */
@ProviderType
public interface UserActionContributor {

	public String getConfirmationMessage(PortletRequest portletRequest);

	public String getMessage(PortletRequest portletRequest);

	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse,
		User user, User selUser);

	public boolean isShow(
		PortletRequest portletRequest, User user, User selUser);

	public boolean isShowConfirmationMessage(User selUser);

}