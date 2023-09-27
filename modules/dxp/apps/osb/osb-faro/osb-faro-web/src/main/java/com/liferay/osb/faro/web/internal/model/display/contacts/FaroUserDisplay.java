/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.liferay.osb.faro.constants.FaroUserConstants;
import com.liferay.osb.faro.model.FaroUser;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Date;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class FaroUserDisplay {

	public FaroUserDisplay() {
	}

	public FaroUserDisplay(FaroUser faroUser) {
		if (faroUser.getStatus() == FaroUserConstants.STATUS_APPROVED) {
			User user = UserLocalServiceUtil.fetchUser(
				faroUser.getLiveUserId());

			if (user != null) {
				_emailAddress = user.getEmailAddress();
				_languageId = user.getLanguageId();
				_lastLoginDate = user.getLastLoginDate();
				_name = user.getFullName();
				_screenName = user.getScreenName();
			}
		}

		if (Validator.isNull(_emailAddress)) {
			_emailAddress = faroUser.getEmailAddress();
		}

		_groupId = faroUser.getGroupId();
		_id = faroUser.getFaroUserId();
		_userId = faroUser.getLiveUserId();

		Role role = RoleLocalServiceUtil.fetchRole(faroUser.getRoleId());

		if (role != null) {
			_roleName = role.getName();
		}

		_status = faroUser.getStatus();
	}

	public FaroUserDisplay(User user) {
		_emailAddress = user.getEmailAddress();
		_languageId = user.getLanguageId();
		_name = user.getFullName();
		_screenName = user.getScreenName();
		_userId = user.getUserId();
	}

	private String _emailAddress;
	private long _groupId;
	private long _id;
	private String _languageId;
	private Date _lastLoginDate;
	private String _name;
	private String _roleName;
	private String _screenName;
	private int _status;
	private long _userId;

}