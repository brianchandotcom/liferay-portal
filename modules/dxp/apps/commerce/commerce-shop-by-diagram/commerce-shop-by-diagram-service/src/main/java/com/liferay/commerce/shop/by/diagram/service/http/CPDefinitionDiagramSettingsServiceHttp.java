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

package com.liferay.commerce.shop.by.diagram.service.http;

import com.liferay.commerce.shop.by.diagram.service.CPDefinitionDiagramSettingsServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CPDefinitionDiagramSettingsServiceUtil</code> service
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
 * @author Alessio Antonio Rendina
 * @see CPDefinitionDiagramSettingsServiceSoap
 * @generated
 */
public class CPDefinitionDiagramSettingsServiceHttp {

	public static
		com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettings
				addCPDefinitionDiagramSettings(
					HttpPrincipal httpPrincipal, long userId,
					long cpDefinitionId, long assetCategoryId, String name)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionDiagramSettingsServiceUtil.class,
				"addCPDefinitionDiagramSettings",
				_addCPDefinitionDiagramSettingsParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, cpDefinitionId, assetCategoryId, name);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.commerce.shop.by.diagram.model.
				CPDefinitionDiagramSettings)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettings
				fetchCPDefinitionDiagramSettingsByCPDefinitionId(
					HttpPrincipal httpPrincipal, long cpDefinitionId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionDiagramSettingsServiceUtil.class,
				"fetchCPDefinitionDiagramSettingsByCPDefinitionId",
				_fetchCPDefinitionDiagramSettingsByCPDefinitionIdParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.commerce.shop.by.diagram.model.
				CPDefinitionDiagramSettings)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettings
				getCPDefinitionDiagramSettings(
					HttpPrincipal httpPrincipal,
					long cpDefinitionDiagramSettingsId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionDiagramSettingsServiceUtil.class,
				"getCPDefinitionDiagramSettings",
				_getCPDefinitionDiagramSettingsParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionDiagramSettingsId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.commerce.shop.by.diagram.model.
				CPDefinitionDiagramSettings)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettings
				getCPDefinitionDiagramSettingsByCPDefinitionId(
					HttpPrincipal httpPrincipal, long cpDefinitionId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionDiagramSettingsServiceUtil.class,
				"getCPDefinitionDiagramSettingsByCPDefinitionId",
				_getCPDefinitionDiagramSettingsByCPDefinitionIdParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.commerce.shop.by.diagram.model.
				CPDefinitionDiagramSettings)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettings>
			getCPDefinitionDiagramSettingsList(
				HttpPrincipal httpPrincipal, long assetCategoryId, int start,
				int end) {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionDiagramSettingsServiceUtil.class,
				"getCPDefinitionDiagramSettingsList",
				_getCPDefinitionDiagramSettingsListParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, assetCategoryId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.commerce.shop.by.diagram.model.
					CPDefinitionDiagramSettings>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCPDefinitionDiagramSettingsListCount(
		HttpPrincipal httpPrincipal, long assetCategoryId) {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionDiagramSettingsServiceUtil.class,
				"getCPDefinitionDiagramSettingsListCount",
				_getCPDefinitionDiagramSettingsListCountParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, assetCategoryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettings
				updateCPDefinitionDiagramSettings(
					HttpPrincipal httpPrincipal,
					long cpDefinitionDiagramSettingsId, long assetCategoryId,
					String name)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionDiagramSettingsServiceUtil.class,
				"updateCPDefinitionDiagramSettings",
				_updateCPDefinitionDiagramSettingsParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionDiagramSettingsId, assetCategoryId,
				name);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.commerce.shop.by.diagram.model.
				CPDefinitionDiagramSettings)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CPDefinitionDiagramSettingsServiceHttp.class);

	private static final Class<?>[]
		_addCPDefinitionDiagramSettingsParameterTypes0 = new Class[] {
			long.class, long.class, long.class, String.class
		};
	private static final Class<?>[]
		_fetchCPDefinitionDiagramSettingsByCPDefinitionIdParameterTypes1 =
			new Class[] {long.class};
	private static final Class<?>[]
		_getCPDefinitionDiagramSettingsParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCPDefinitionDiagramSettingsByCPDefinitionIdParameterTypes3 =
			new Class[] {long.class};
	private static final Class<?>[]
		_getCPDefinitionDiagramSettingsListParameterTypes4 = new Class[] {
			long.class, int.class, int.class
		};
	private static final Class<?>[]
		_getCPDefinitionDiagramSettingsListCountParameterTypes5 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_updateCPDefinitionDiagramSettingsParameterTypes6 = new Class[] {
			long.class, long.class, String.class
		};

}