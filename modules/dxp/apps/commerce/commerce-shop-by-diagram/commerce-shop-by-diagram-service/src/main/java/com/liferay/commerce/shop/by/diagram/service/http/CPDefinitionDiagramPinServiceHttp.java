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

import com.liferay.commerce.shop.by.diagram.service.CPDefinitionDiagramPinServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CPDefinitionDiagramPinServiceUtil</code> service
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
 * @see CPDefinitionDiagramPinServiceSoap
 * @generated
 */
public class CPDefinitionDiagramPinServiceHttp {

	public static
		com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramPin
				addCPDefinitionDiagramPin(
					HttpPrincipal httpPrincipal, long cpDefinitionId,
					double positionX, double positionY, String sequence)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionDiagramPinServiceUtil.class,
				"addCPDefinitionDiagramPin",
				_addCPDefinitionDiagramPinParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionId, positionX, positionY, sequence);

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
				CPDefinitionDiagramPin)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteCPDefinitionDiagramPin(
			HttpPrincipal httpPrincipal, long cpDefinitionDiagramPinId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionDiagramPinServiceUtil.class,
				"deleteCPDefinitionDiagramPin",
				_deleteCPDefinitionDiagramPinParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionDiagramPinId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
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
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramPin
				getCPDefinitionDiagramPin(
					HttpPrincipal httpPrincipal, long cpDefinitionDiagramPinId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionDiagramPinServiceUtil.class,
				"getCPDefinitionDiagramPin",
				_getCPDefinitionDiagramPinParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionDiagramPinId);

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
				CPDefinitionDiagramPin)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramPin>
				getCPDefinitionDiagramPins(
					HttpPrincipal httpPrincipal, long cpDefinitionId, int start,
					int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionDiagramPinServiceUtil.class,
				"getCPDefinitionDiagramPins",
				_getCPDefinitionDiagramPinsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionId, start, end);

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

			return (java.util.List
				<com.liferay.commerce.shop.by.diagram.model.
					CPDefinitionDiagramPin>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCPDefinitionDiagramPinsCount(
			HttpPrincipal httpPrincipal, long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionDiagramPinServiceUtil.class,
				"getCPDefinitionDiagramPinsCount",
				_getCPDefinitionDiagramPinsCountParameterTypes4);

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

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramPin
				updateCPDefinitionDiagramPin(
					HttpPrincipal httpPrincipal, long cpDefinitionDiagramPinId,
					double positionX, double positionY, String sequence)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionDiagramPinServiceUtil.class,
				"updateCPDefinitionDiagramPin",
				_updateCPDefinitionDiagramPinParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionDiagramPinId, positionX, positionY,
				sequence);

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
				CPDefinitionDiagramPin)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CPDefinitionDiagramPinServiceHttp.class);

	private static final Class<?>[] _addCPDefinitionDiagramPinParameterTypes0 =
		new Class[] {long.class, double.class, double.class, String.class};
	private static final Class<?>[]
		_deleteCPDefinitionDiagramPinParameterTypes1 = new Class[] {long.class};
	private static final Class<?>[] _getCPDefinitionDiagramPinParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[] _getCPDefinitionDiagramPinsParameterTypes3 =
		new Class[] {long.class, int.class, int.class};
	private static final Class<?>[]
		_getCPDefinitionDiagramPinsCountParameterTypes4 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_updateCPDefinitionDiagramPinParameterTypes5 = new Class[] {
			long.class, double.class, double.class, String.class
		};

}