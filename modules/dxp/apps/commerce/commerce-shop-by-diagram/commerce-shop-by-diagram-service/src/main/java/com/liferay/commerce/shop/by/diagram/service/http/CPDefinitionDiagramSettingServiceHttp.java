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

import com.liferay.commerce.shop.by.diagram.service.CPDefinitionDiagramSettingServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CPDefinitionDiagramSettingServiceUtil</code> service
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
 * @author Andrea Sbarra
 * @see CPDefinitionDiagramSettingServiceSoap
 * @generated
 */
public class CPDefinitionDiagramSettingServiceHttp {

	public static
		com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting
				addCPDefinitionDiagramSetting(
					HttpPrincipal httpPrincipal, long cpDefinitionId,
					long cpAttachmentFileEntryId, String color, double radius,
					String type)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionDiagramSettingServiceUtil.class,
				"addCPDefinitionDiagramSetting",
				_addCPDefinitionDiagramSettingParameterTypes0);

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

			return (com.liferay.commerce.shop.by.diagram.model.
				CPDefinitionDiagramSetting)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting
				fetchCPDefinitionDiagramSettingByCPDefinitionId(
					HttpPrincipal httpPrincipal, long cpDefinitionId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionDiagramSettingServiceUtil.class,
				"fetchCPDefinitionDiagramSettingByCPDefinitionId",
				_fetchCPDefinitionDiagramSettingByCPDefinitionIdParameterTypes1);

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
				CPDefinitionDiagramSetting)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting
				getCPDefinitionDiagramSetting(
					HttpPrincipal httpPrincipal,
					long cpDefinitionDiagramSettingId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionDiagramSettingServiceUtil.class,
				"getCPDefinitionDiagramSetting",
				_getCPDefinitionDiagramSettingParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionDiagramSettingId);

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
				CPDefinitionDiagramSetting)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting
				getCPDefinitionDiagramSettingByCPDefinitionId(
					HttpPrincipal httpPrincipal, long cpDefinitionId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionDiagramSettingServiceUtil.class,
				"getCPDefinitionDiagramSettingByCPDefinitionId",
				_getCPDefinitionDiagramSettingByCPDefinitionIdParameterTypes3);

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
				CPDefinitionDiagramSetting)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting
				updateCPDefinitionDiagramSetting(
					HttpPrincipal httpPrincipal,
					long cpDefinitionDiagramSettingId,
					long cpAttachmentFileEntryId, String color, double radius,
					String type)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionDiagramSettingServiceUtil.class,
				"updateCPDefinitionDiagramSetting",
				_updateCPDefinitionDiagramSettingParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionDiagramSettingId,
				cpAttachmentFileEntryId, color, radius, type);

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
				CPDefinitionDiagramSetting)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CPDefinitionDiagramSettingServiceHttp.class);

	private static final Class<?>[]
		_addCPDefinitionDiagramSettingParameterTypes0 = new Class[] {
			long.class, long.class, String.class, double.class, String.class
		};
	private static final Class<?>[]
		_fetchCPDefinitionDiagramSettingByCPDefinitionIdParameterTypes1 =
			new Class[] {long.class};
	private static final Class<?>[]
		_getCPDefinitionDiagramSettingParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCPDefinitionDiagramSettingByCPDefinitionIdParameterTypes3 =
			new Class[] {long.class};
	private static final Class<?>[]
		_updateCPDefinitionDiagramSettingParameterTypes4 = new Class[] {
			long.class, long.class, String.class, double.class, String.class
		};

}