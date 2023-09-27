/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.model.impl;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.TeamLocalServiceUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class RoleImpl extends RoleBaseImpl {

	@Override
	public String getDescriptiveName() throws PortalException {
		String name = getName();

		if (isTeam()) {
			Team team = TeamLocalServiceUtil.getTeam(getClassPK());

			name = team.getName();
		}

		return name;
	}

	@Override
	public String getIconCssClass() {
		String iconCssClass = StringPool.BLANK;

		String roleName = getName();
		int roleType = getType();

		if (roleName.equals(RoleConstants.GUEST)) {
			iconCssClass = "user";
		}
		else if (roleType == RoleConstants.TYPE_ORGANIZATION) {
			iconCssClass = "globe";
		}
		else if (roleType == RoleConstants.TYPE_REGULAR) {
			iconCssClass = "user";
		}
		else if (roleType == RoleConstants.TYPE_SITE) {
			iconCssClass = "globe";
		}
		else if (isTeam()) {
			iconCssClass = "community";
		}

		return iconCssClass;
	}

	@Override
	public String getTitle(String languageId) {
		String value = super.getTitle(languageId);

		if (Validator.isNull(value)) {
			try {
				value = getDescriptiveName();
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}

		return value;
	}

	@Override
	public String getTitle(String languageId, boolean useDefault) {
		String value = super.getTitle(languageId, useDefault);

		if (Validator.isNull(value)) {
			try {
				value = getDescriptiveName();
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}

		return value;
	}

	@Override
	public String getTypeLabel() {
		return RoleConstants.getTypeLabel(getType());
	}

	@Override
	public boolean isSystem() {
		return PortalUtil.isSystemRole(getName());
	}

	@Override
	public boolean isTeam() {
		if (getClassNameId() == ClassNameIds._TEAM_CLASS_NAME_ID) {
			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(RoleImpl.class);

	private static class ClassNameIds {

		private ClassNameIds() {
		}

		private static final long _TEAM_CLASS_NAME_ID =
			PortalUtil.getClassNameId(Team.class);

	}

}