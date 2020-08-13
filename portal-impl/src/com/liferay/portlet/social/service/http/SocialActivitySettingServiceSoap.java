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

package com.liferay.portlet.social.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.social.kernel.service.SocialActivitySettingServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>SocialActivitySettingServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.social.kernel.model.SocialActivitySettingSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.social.kernel.model.SocialActivitySetting</code>, that is translated to a
 * <code>com.liferay.social.kernel.model.SocialActivitySettingSoap</code>. Methods that SOAP
 * cannot safely wire are skipped.
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
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class SocialActivitySettingServiceSoap {

	public static com.liferay.social.kernel.model.SocialActivitySettingSoap[]
			getActivitySettings(long groupId)
		throws RemoteException {

		try {
			java.util.List
				<com.liferay.social.kernel.model.SocialActivitySetting>
					returnValue =
						SocialActivitySettingServiceUtil.getActivitySettings(
							groupId);

			return com.liferay.social.kernel.model.SocialActivitySettingSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static String getJSONActivityDefinitions(
			long groupId, String className)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.json.JSONArray returnValue =
				SocialActivitySettingServiceUtil.getJSONActivityDefinitions(
					groupId, className);

			return returnValue.toString();
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void updateActivitySetting(
			long groupId, String className, boolean enabled)
		throws RemoteException {

		try {
			SocialActivitySettingServiceUtil.updateActivitySetting(
				groupId, className, enabled);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void updateActivitySetting(
			long groupId, String className, int activityType,
			com.liferay.social.kernel.model.SocialActivityCounterDefinition
				activityCounterDefinition)
		throws RemoteException {

		try {
			SocialActivitySettingServiceUtil.updateActivitySetting(
				groupId, className, activityType, activityCounterDefinition);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void updateActivitySettings(
			long groupId, String className, int activityType,
			java.util.List
				<com.liferay.social.kernel.model.
					SocialActivityCounterDefinition> activityCounterDefinitions)
		throws RemoteException {

		try {
			SocialActivitySettingServiceUtil.updateActivitySettings(
				groupId, className, activityType, activityCounterDefinitions);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		SocialActivitySettingServiceSoap.class);

}