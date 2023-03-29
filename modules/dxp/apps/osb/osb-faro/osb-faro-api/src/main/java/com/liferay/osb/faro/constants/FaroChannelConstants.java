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

package com.liferay.osb.faro.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author André Miranda
 */
public class FaroChannelConstants {

	public static final int PERMISSION_ALL_USERS = 0;

	public static final int PERMISSION_SELECT_USERS = 1;

	public static Map<String, Integer> getPermissionTypes() {
		return _permissionTypes;
	}

	private static final Map<String, Integer> _permissionTypes =
		new HashMap<String, Integer>() {
			{
				put("allUsers", PERMISSION_ALL_USERS);
				put("selectUsers", PERMISSION_SELECT_USERS);
			}
		};

}