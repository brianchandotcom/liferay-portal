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

package com.liferay.portal.model.impl;

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
	public String getTitle(String languageId) {
		String value = super.getTitle(languageId);

		if (Validator.isNull(value)) {
			try {
				value = getDescriptiveName();
			}
			catch (Exception e) {
				_log.error(e, e);
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
			catch (Exception e) {
				_log.error(e, e);
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