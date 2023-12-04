/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.manager;

import com.liferay.portal.kernel.model.Group;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Julio Camarero
 */
public interface RecentGroupManager {

	public void addRecentGroup(
		HttpServletRequest httpServletRequest, Group group);

	public void addRecentGroup(
		HttpServletRequest httpServletRequest, long groupId);

	public List<Group> getRecentGroups(HttpServletRequest httpServletRequest);

}