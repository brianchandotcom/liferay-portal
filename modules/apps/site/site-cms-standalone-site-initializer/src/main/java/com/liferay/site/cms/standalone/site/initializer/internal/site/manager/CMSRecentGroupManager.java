/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.standalone.site.initializer.internal.site.manager;

import com.liferay.portal.kernel.model.Group;
import com.liferay.site.manager.RecentGroupManager;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez Álvarez
 */
@Component(
	property = "service.ranking:Integer=100", service = RecentGroupManager.class
)
public class CMSRecentGroupManager implements RecentGroupManager {

	@Override
	public void addRecentGroup(
		HttpServletRequest httpServletRequest, Group group) {
	}

	@Override
	public void addRecentGroup(
		HttpServletRequest httpServletRequest, long groupId) {
	}

	@Override
	public List<Group> getRecentGroups(HttpServletRequest httpServletRequest) {
		return Collections.emptyList();
	}

}