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

package com.liferay.portal.security.audit.event.generators.internal.constants;

/**
 * @author Brian Wing Shun Chan
 */
public interface EventTypes {

	public static final String ADD = "ADD";

	public static final String ASSIGN = "ASSIGN";

	public static final String DELETE = "DELETE";

	public static final String IMPERSONATE = "IMPERSONATE";

	public static final String LOGIN = "LOGIN";

	public static final String LOGIN_FAILURE = "LOGIN_FAILURE";

	public static final String LOGOUT = "LOGOUT";

	public static final String UNASSIGN = "UNASSIGN";

	public static final String UPDATE = "UPDATE";

}