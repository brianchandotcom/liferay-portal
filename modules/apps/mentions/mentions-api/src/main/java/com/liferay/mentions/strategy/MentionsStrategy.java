/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mentions.strategy;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Cristina González
 */
@ProviderType
public interface MentionsStrategy {

	public List<User> getUsers(
			long companyId, long groupId, long userId, String query,
			JSONObject jsonObject)
		throws PortalException;

	public List<User> getUsers(
			long companyId, long userId, String query, JSONObject jsonObject)
		throws PortalException;

}