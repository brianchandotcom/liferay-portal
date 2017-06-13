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

import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.util.Validator;

import java.util.Objects;

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
		return PortletIdCodec.encode(
			getRootPortletId(portletId), userId, getInstanceId(portletId));
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

		if (Validator.isNull(instanceId)) {
			instanceId = getInstanceId(portletId);
		}

		return PortletIdCodec.encode(
			getRootPortletId(portletId), userId, instanceId);
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

		PortletIdCodec.validatePortletName(portletId);

		return PortletIdCodec.encode(portletId, instanceId);
	}

	public static String generateInstanceId() {
		return PortletIdCodec.generateInstanceId();
	}

	/**
	 * Returns the instance ID of the portlet.
	 *
	 * @param  portletId the portlet ID
	 * @return the instance ID of the portlet
	 */
	public static String getInstanceId(String portletId) {
		return PortletIdCodec.decodeInstanceId(portletId);
	}

	/**
	 * Returns the root portlet ID of the portlet.
	 *
	 * @param  portletId the portlet ID
	 * @return the root portlet ID of the portlet
	 */
	public static String getRootPortletId(String portletId) {
		return PortletIdCodec.decodePortletName(portletId);
	}

	/**
	 * Returns the user ID of the portlet. This only applies when the portlet is
	 * added by a user to a page in customizable mode.
	 *
	 * @param  portletId the portlet ID
	 * @return the user ID of the portlet
	 */
	public static long getUserId(String portletId) {
		return PortletIdCodec.decodeUserId(portletId);
	}

	public static boolean hasIdenticalRootPortletId(
		String portletId1, String portletId2) {

		return Objects.equals(
			PortletIdCodec.decodePortletName(portletId1),
			PortletIdCodec.decodePortletName(portletId2));
	}

	/**
	 * Returns <code>true</code> if the portlet ID contains an instance ID.
	 *
	 * @param  portletId the portlet ID
	 * @return <code>true</code> if the portlet ID contains an instance ID;
	 *         <code>false</code> otherwise
	 */
	public static boolean hasInstanceId(String portletId) {
		return PortletIdCodec.hasInstanceId(portletId);
	}

	/**
	 * Returns <code>true</code> if the portlet ID contains a user ID.
	 *
	 * @param  portletId the portlet ID
	 * @return <code>true</code> if the portlet ID contains a user ID;
	 *         <code>false</code> otherwise
	 */
	public static boolean hasUserId(String portletId) {
		return PortletIdCodec.hasUserId(portletId);
	}

}