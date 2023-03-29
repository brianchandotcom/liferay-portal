/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.web.internal.constants;

import com.liferay.osb.faro.constants.FaroUserConstants;
import com.liferay.portal.kernel.model.RoleConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Matthew Kong
 */
public class UserConstants {

	public static Map<String, String> getRoleNames() {
		return _roleNames;
	}

	public static Map<String, Integer> getStatuses() {
		return _statuses;
	}

	private static final Map<String, String> _roleNames =
		new HashMap<String, String>() {
			{
				put("administrator", RoleConstants.SITE_ADMINISTRATOR);
				put("member", RoleConstants.SITE_MEMBER);
				put("owner", RoleConstants.SITE_OWNER);
			}
		};
	private static final Map<String, Integer> _statuses =
		new HashMap<String, Integer>() {
			{
				put("approved", FaroUserConstants.STATUS_APPROVED);
				put("pending", FaroUserConstants.STATUS_PENDING);
			}
		};

}