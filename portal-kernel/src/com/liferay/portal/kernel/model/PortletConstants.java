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

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletConstants {

	/**
	 * Default preferences.
	 */
	public static final String DEFAULT_PREFERENCES = "<portlet-preferences />";

	/**
	 * Facebook integration method for FBML.
	 *
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static final String FACEBOOK_INTEGRATION_FBML = "fbml";

	/**
	 * Facebook integration method for IFrame.
	 *
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static final String FACEBOOK_INTEGRATION_IFRAME = "iframe";

	/**
	 * Instance separator.
	 *
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static final String INSTANCE_SEPARATOR = "_INSTANCE_";

	/**
	 * Layout separator.
	 */
	public static final String LAYOUT_SEPARATOR = "_LAYOUT_";

	/**
	 * User principal strategy for screen name.
	 */
	public static final String USER_PRINCIPAL_STRATEGY_SCREEN_NAME =
		"screenName";

	/**
	 * User principal strategy for screen name.
	 */
	public static final String USER_PRINCIPAL_STRATEGY_USER_ID = "userId";

	/**
	 * User separator.
	 *
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static final String USER_SEPARATOR = "_USER_";

	/**
	 * War file separator.
	 */
	public static final String WAR_SEPARATOR = "_WAR_";

	/**
	 * Returns a properly assembled portlet ID from the parameters passed. If
	 * the portlet ID contains an instance ID it will be properly retained. If
	 * the portlet ID contains a user ID it will be replaced by the user ID
	 * parameter.
	 *
	 * @param  portletId the portlet ID
	 * @param  userId a user ID
	 * @return the properly assembled portlet ID
	 */
	public static String assemblePortletId(String portletId, long userId) {
		PortletInstance portletInstance = null;

		String rootPortletId = getRootPortletId(portletId);
		String instanceId = getInstanceId(portletId);

		portletInstance = new PortletInstance(
			rootPortletId, userId, instanceId);

		return portletInstance.getPortletInstanceKey();
	}

	/**
	 * Returns a properly assembled portlet ID from the parameters passed. If
	 * the portlet ID contains a user ID it will be replaced by the user ID
	 * parameter. If the portlet ID contains an instance ID it will be replaced
	 * by the instance ID parameter.
	 *
	 * @param  portletId the portlet ID
	 * @param  userId the user ID
	 * @param  instanceId an instance ID. If <code>null</code>, an instance ID
	 *         is derived from the portlet ID.
	 * @return the properly assembled portlet ID
	 */
	public static String assemblePortletId(
		String portletId, long userId, String instanceId) {

		String rootPortletId = getRootPortletId(portletId);

		if (Validator.isNull(instanceId)) {
			instanceId = getInstanceId(portletId);
		}

		PortletInstance portletInstance = new PortletInstance(
			rootPortletId, userId, instanceId);

		return portletInstance.getPortletInstanceKey();
	}

	/**
	 * Returns a properly assembled portlet ID from the parameters passed. If
	 * the portlet ID contains a user ID it will be properly retained. If the
	 * portlet ID contains an instance ID it will be replaced by the instance ID
	 * parameter.
	 *
	 * @param  portletId the portlet ID
	 * @param  instanceId an instance ID
	 * @return the properly assembled portlet ID
	 */
	public static String assemblePortletId(
		String portletId, String instanceId) {

		PortletInstance portletInstance = new PortletInstance(
			portletId, instanceId);

		return portletInstance.getPortletInstanceKey();
	}

	public static String generateInstanceId() {
		return StringUtil.randomString(12);
	}

	/**
	 * Returns the instance ID of the portlet.
	 *
	 * @param  portletId the portlet ID
	 * @return the instance ID of the portlet
	 */
	public static String getInstanceId(String portletId) {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(portletId);

		return portletInstance.getInstanceId();
	}

	/**
	 * Returns the root portlet ID of the portlet.
	 *
	 * @param  portletId the portlet ID
	 * @return the root portlet ID of the portlet
	 */
	public static String getRootPortletId(String portletId) {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(portletId);

		return portletInstance.getPortletName();
	}

	/**
	 * Returns the user ID of the portlet. This only applies when the portlet is
	 * added by a user to a page in customizable mode.
	 *
	 * @param  portletId the portlet ID
	 * @return the user ID of the portlet
	 */
	public static long getUserId(String portletId) {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(portletId);

		return portletInstance.getUserId();
	}

	public static boolean hasIdenticalRootPortletId(
		String portletId1, String portletId2) {

		PortletInstance portletInstance1 =
			PortletInstance.fromPortletInstanceKey(portletId1);
		PortletInstance portletInstance2 =
			PortletInstance.fromPortletInstanceKey(portletId2);

		return portletInstance1.hasIdenticalPortletName(portletInstance2);
	}

	/**
	 * Returns <code>true</code> if the portlet ID contains an instance ID.
	 *
	 * @param  portletId the portlet ID
	 * @return <code>true</code> if the portlet ID contains an instance ID;
	 *         <code>false</code> otherwise
	 */
	public static boolean hasInstanceId(String portletId) {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(portletId);

		return portletInstance.hasInstanceId();
	}

	/**
	 * Returns <code>true</code> if the portlet ID contains a user ID.
	 *
	 * @param  portletId the portlet ID
	 * @return <code>true</code> if the portlet ID contains a user ID;
	 *         <code>false</code> otherwise
	 */
	public static boolean hasUserId(String portletId) {
		PortletInstance portletInstance =
			PortletInstance.fromPortletInstanceKey(portletId);

		return portletInstance.hasUserId();
	}

}