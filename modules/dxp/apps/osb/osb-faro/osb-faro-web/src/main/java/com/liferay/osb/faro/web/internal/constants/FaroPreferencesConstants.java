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

import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

/**
 * @author Matthew Kong
 */
public class FaroPreferencesConstants {

	public static final String SCOPE_GROUP = "group";

	public static final String SCOPE_USER = "user";

	public static Map<String, Object> getScopes() {
		return _scopes;
	}

	private static final Map<String, Object> _scopes =
		HashMapBuilder.<String, Object>put(
			SCOPE_GROUP, SCOPE_GROUP
		).put(
			SCOPE_USER, SCOPE_USER
		).build();

}