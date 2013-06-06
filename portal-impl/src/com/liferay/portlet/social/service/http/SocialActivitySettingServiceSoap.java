/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.social.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.liferay.portlet.social.service.SocialActivitySettingServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link com.liferay.portlet.social.service.SocialActivitySettingServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portlet.social.model.SocialActivitySettingSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portlet.social.model.SocialActivitySetting}, that is translated to a
 * {@link com.liferay.portlet.social.model.SocialActivitySettingSoap}. Methods that SOAP cannot
 * safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivitySettingServiceHttp
 * @see com.liferay.portlet.social.model.SocialActivitySettingSoap
 * @see com.liferay.portlet.social.service.SocialActivitySettingServiceUtil
 * @generated
 */
public class SocialActivitySettingServiceSoap {
	public static com.liferay.portlet.social.model.SocialActivitySettingSoap[] getActivitySettings(
		long groupId) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.social.model.SocialActivitySetting> returnValue =
				SocialActivitySettingServiceUtil.getActivitySettings(groupId);

			return com.liferay.portlet.social.model.SocialActivitySettingSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static java.lang.String getJSONActivityDefinitions(long groupId,
		java.lang.String className) throws RemoteException {
		try {
			com.liferay.portal.kernel.json.JSONArray returnValue = SocialActivitySettingServiceUtil.getJSONActivityDefinitions(groupId,
					className);

			return returnValue.toString();
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void updateActivitySetting(long groupId,
		java.lang.String className, boolean enabled) throws RemoteException {
		try {
			SocialActivitySettingServiceUtil.updateActivitySetting(groupId,
				className, enabled);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void updateActivitySetting(long groupId,
		java.lang.String className, int activityType,
		com.liferay.portlet.social.model.SocialActivityCounterDefinition activityCounterDefinition)
		throws RemoteException {
		try {
			SocialActivitySettingServiceUtil.updateActivitySetting(groupId,
				className, activityType, activityCounterDefinition);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void updateActivitySettings(long groupId,
		java.lang.String className, int activityType,
		java.util.List<com.liferay.portlet.social.model.SocialActivityCounterDefinition> activityCounterDefinitions)
		throws RemoteException {
		try {
			SocialActivitySettingServiceUtil.updateActivitySettings(groupId,
				className, activityType, activityCounterDefinitions);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(SocialActivitySettingServiceSoap.class);
}