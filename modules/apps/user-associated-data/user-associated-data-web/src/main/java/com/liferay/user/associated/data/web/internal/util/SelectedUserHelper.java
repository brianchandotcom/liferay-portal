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

package com.liferay.user.associated.data.web.internal.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.Portal;

import java.util.Objects;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(immediate = true, service = SelectedUserHelper.class)
public class SelectedUserHelper {

	public User getSelectedUser(PortletRequest portletRequest)
		throws PortalException {

		User selectedUser = portal.getSelectedUser(portletRequest);

		if (Objects.equals(portal.getUser(portletRequest), selectedUser)) {
			throw new PortalException(
				"The selected user cannot be the logged in user");
		}

		if (uadAnonymizerHelper.isAnonymousUser(selectedUser)) {
			throw new PortalException(
				"The selected user cannot be the anonymous user");
		}

		return selectedUser;
	}

	public long getSelectedUserId(PortletRequest portletRequest)
		throws PortalException {

		User selectedUser = getSelectedUser(portletRequest);

		return selectedUser.getUserId();
	}

	@Reference
	protected Portal portal;

	@Reference
	protected UADAnonymizerHelper uadAnonymizerHelper;

}