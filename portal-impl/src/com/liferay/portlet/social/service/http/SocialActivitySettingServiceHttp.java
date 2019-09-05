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
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.social.kernel.service.SocialActivitySettingServiceUtil;

/**
 * Provides the HTTP utility for the
 * <code>SocialActivitySettingServiceUtil</code> service
 * utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * <code>HttpPrincipal</code> parameter.
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties to
 * configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivitySettingServiceSoap
 * @generated
 */
public class SocialActivitySettingServiceHttp {

	public static com.liferay.social.kernel.model.SocialActivityDefinition
			getActivityDefinition(
				HttpPrincipal httpPrincipal, long groupId, String className,
				int activityType)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SocialActivitySettingServiceUtil.class, "getActivityDefinition",
				_getActivityDefinitionParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, className, activityType);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.social.kernel.model.SocialActivityDefinition)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.social.kernel.model.SocialActivityDefinition>
				getActivityDefinitions(
					HttpPrincipal httpPrincipal, long groupId, String className)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SocialActivitySettingServiceUtil.class,
				"getActivityDefinitions",
				_getActivityDefinitionsParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, className);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.social.kernel.model.SocialActivityDefinition>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.social.kernel.model.SocialActivitySetting>
				getActivitySettings(HttpPrincipal httpPrincipal, long groupId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SocialActivitySettingServiceUtil.class, "getActivitySettings",
				_getActivitySettingsParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.social.kernel.model.SocialActivitySetting>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.json.JSONArray
			getJSONActivityDefinitions(
				HttpPrincipal httpPrincipal, long groupId, String className)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SocialActivitySettingServiceUtil.class,
				"getJSONActivityDefinitions",
				_getJSONActivityDefinitionsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, className);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.json.JSONArray)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void updateActivitySetting(
			HttpPrincipal httpPrincipal, long groupId, String className,
			boolean enabled)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SocialActivitySettingServiceUtil.class, "updateActivitySetting",
				_updateActivitySettingParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, className, enabled);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void updateActivitySetting(
			HttpPrincipal httpPrincipal, long groupId, String className,
			int activityType,
			com.liferay.social.kernel.model.SocialActivityCounterDefinition
				activityCounterDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SocialActivitySettingServiceUtil.class, "updateActivitySetting",
				_updateActivitySettingParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, className, activityType,
				activityCounterDefinition);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void updateActivitySettings(
			HttpPrincipal httpPrincipal, long groupId, String className,
			int activityType,
			java.util.List
				<com.liferay.social.kernel.model.
					SocialActivityCounterDefinition> activityCounterDefinitions)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SocialActivitySettingServiceUtil.class,
				"updateActivitySettings",
				_updateActivitySettingsParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, className, activityType,
				activityCounterDefinitions);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		SocialActivitySettingServiceHttp.class);

	private static final Class<?>[] _getActivityDefinitionParameterTypes0 =
		new Class[] {long.class, String.class, int.class};
	private static final Class<?>[] _getActivityDefinitionsParameterTypes1 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _getActivitySettingsParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[] _getJSONActivityDefinitionsParameterTypes3 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _updateActivitySettingParameterTypes4 =
		new Class[] {long.class, String.class, boolean.class};
	private static final Class<?>[] _updateActivitySettingParameterTypes5 =
		new Class[] {
			long.class, String.class, int.class,
			com.liferay.social.kernel.model.SocialActivityCounterDefinition.
				class
		};
	private static final Class<?>[] _updateActivitySettingsParameterTypes6 =
		new Class[] {long.class, String.class, int.class, java.util.List.class};

}