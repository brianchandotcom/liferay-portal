/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.users.admin.demo.data.creator;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Pei-Jung Lan
 */
@ProviderType
public interface SiteMemberUserDemoDataCreator extends UserDemoDataCreator {

	public User create(long groupId) throws PortalException;

	public User create(long groupId, String emailAddress)
		throws PortalException;

	public User create(long groupId, String emailAddress, long[] roleIds)
		throws PortalException;

}