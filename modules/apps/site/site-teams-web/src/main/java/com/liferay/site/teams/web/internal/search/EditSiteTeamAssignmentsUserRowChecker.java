/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.teams.web.internal.search;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.TeamLocalServiceUtil;

import jakarta.portlet.PortletResponse;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Lianne Louie
 */
public class EditSiteTeamAssignmentsUserRowChecker
	extends EmptyOnClickRowChecker {

	public EditSiteTeamAssignmentsUserRowChecker(
		PortletResponse portletResponse, long teamId) {

		super(portletResponse);

		_teamId = teamId;
	}

	@Override
	public String getRowCheckBox(
		HttpServletRequest httpServletRequest, boolean checked,
		boolean disabled, String primaryKey) {

		if (disabled) {
			return StringPool.BLANK;
		}

		return super.getRowCheckBox(
			httpServletRequest, checked, disabled, primaryKey);
	}

	@Override
	public boolean isDisabled(Object object) {
		User user = (User)object;

		if (!TeamLocalServiceUtil.hasUserTeam(user.getUserId(), _teamId)) {
			return true;
		}

		return super.isDisabled(object);
	}

	private final long _teamId;

}