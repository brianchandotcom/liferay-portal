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

package com.liferay.commerce.shop.by.diagram.service.http;

import com.liferay.commerce.shop.by.diagram.service.CSDiagramSettingServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CSDiagramSettingServiceUtil</code> service
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
 * @see CSDiagramSettingServiceSoap
 * @generated
 */
public class CSDiagramSettingServiceHttp {

	public static com.liferay.commerce.shop.by.diagram.model.CSDiagramSetting
			addCSDiagramSetting(
				HttpPrincipal httpPrincipal, long cpDefinitionId,
				long cpAttachmentFileEntryId, String color, double radius,
				String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CSDiagramSettingServiceUtil.class, "addCSDiagramSetting",
				_addCSDiagramSettingParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionId, cpAttachmentFileEntryId, color,
				radius, type);

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

			return (com.liferay.commerce.shop.by.diagram.model.CSDiagramSetting)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.shop.by.diagram.model.CSDiagramSetting
			fetchCSDiagramSettingByCPDefinitionId(
				HttpPrincipal httpPrincipal, long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CSDiagramSettingServiceUtil.class,
				"fetchCSDiagramSettingByCPDefinitionId",
				_fetchCSDiagramSettingByCPDefinitionIdParameterTypes1);

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

			return (com.liferay.commerce.shop.by.diagram.model.CSDiagramSetting)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.shop.by.diagram.model.CSDiagramSetting
			getCSDiagramSetting(
				HttpPrincipal httpPrincipal, long csDiagramSettingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CSDiagramSettingServiceUtil.class, "getCSDiagramSetting",
				_getCSDiagramSettingParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, csDiagramSettingId);

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

			return (com.liferay.commerce.shop.by.diagram.model.CSDiagramSetting)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.shop.by.diagram.model.CSDiagramSetting
			getCSDiagramSettingByCPDefinitionId(
				HttpPrincipal httpPrincipal, long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CSDiagramSettingServiceUtil.class,
				"getCSDiagramSettingByCPDefinitionId",
				_getCSDiagramSettingByCPDefinitionIdParameterTypes3);

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

			return (com.liferay.commerce.shop.by.diagram.model.CSDiagramSetting)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.shop.by.diagram.model.CSDiagramSetting
			updateCSDiagramSetting(
				HttpPrincipal httpPrincipal, long csDiagramSettingId,
				long cpAttachmentFileEntryId, String color, double radius,
				String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CSDiagramSettingServiceUtil.class, "updateCSDiagramSetting",
				_updateCSDiagramSettingParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, csDiagramSettingId, cpAttachmentFileEntryId, color,
				radius, type);

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

			return (com.liferay.commerce.shop.by.diagram.model.CSDiagramSetting)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CSDiagramSettingServiceHttp.class);

	private static final Class<?>[] _addCSDiagramSettingParameterTypes0 =
		new Class[] {
			long.class, long.class, String.class, double.class, String.class
		};
	private static final Class<?>[]
		_fetchCSDiagramSettingByCPDefinitionIdParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCSDiagramSettingParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[]
		_getCSDiagramSettingByCPDefinitionIdParameterTypes3 = new Class[] {
			long.class
		};
	private static final Class<?>[] _updateCSDiagramSettingParameterTypes4 =
		new Class[] {
			long.class, long.class, String.class, double.class, String.class
		};

}