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

package com.liferay.commerce.bom.service.http;

import com.liferay.commerce.bom.service.CommerceBOMFolderApplicationRelServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CommerceBOMFolderApplicationRelServiceUtil</code> service
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
 * @author Luca Pellizzon
 * @see CommerceBOMFolderApplicationRelServiceSoap
 * @generated
 */
public class CommerceBOMFolderApplicationRelServiceHttp {

	public static com.liferay.commerce.bom.model.CommerceBOMFolderApplicationRel
			addCommerceBOMFolderApplicationRel(
				HttpPrincipal httpPrincipal, long userId,
				long commerceBOMFolderId, long commerceApplicationModelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceBOMFolderApplicationRelServiceUtil.class,
				"addCommerceBOMFolderApplicationRel",
				_addCommerceBOMFolderApplicationRelParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, commerceBOMFolderId,
				commerceApplicationModelId);

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

			return (com.liferay.commerce.bom.model.
				CommerceBOMFolderApplicationRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteCommerceBOMFolderApplicationRel(
			HttpPrincipal httpPrincipal, long commerceBOMFolderApplicationRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceBOMFolderApplicationRelServiceUtil.class,
				"deleteCommerceBOMFolderApplicationRel",
				_deleteCommerceBOMFolderApplicationRelParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceBOMFolderApplicationRelId);

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

	public static java.util.List
		<com.liferay.commerce.bom.model.CommerceBOMFolderApplicationRel>
				getCommerceBOMFolderApplicationRelsByCAMId(
					HttpPrincipal httpPrincipal,
					long commerceApplicationModelId, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceBOMFolderApplicationRelServiceUtil.class,
				"getCommerceBOMFolderApplicationRelsByCAMId",
				_getCommerceBOMFolderApplicationRelsByCAMIdParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceApplicationModelId, start, end);

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
				<com.liferay.commerce.bom.model.
					CommerceBOMFolderApplicationRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.bom.model.CommerceBOMFolderApplicationRel>
				getCommerceBOMFolderApplicationRelsByCommerceBOMFolderId(
					HttpPrincipal httpPrincipal, long commerceBOMFolderId,
					int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceBOMFolderApplicationRelServiceUtil.class,
				"getCommerceBOMFolderApplicationRelsByCommerceBOMFolderId",
				_getCommerceBOMFolderApplicationRelsByCommerceBOMFolderIdParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceBOMFolderId, start, end);

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
				<com.liferay.commerce.bom.model.
					CommerceBOMFolderApplicationRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommerceBOMFolderApplicationRelsCountByCAMId(
			HttpPrincipal httpPrincipal, long commerceApplicationModelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceBOMFolderApplicationRelServiceUtil.class,
				"getCommerceBOMFolderApplicationRelsCountByCAMId",
				_getCommerceBOMFolderApplicationRelsCountByCAMIdParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceApplicationModelId);

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

	public static int
			getCommerceBOMFolderApplicationRelsCountByCommerceBOMFolderId(
				HttpPrincipal httpPrincipal, long commerceBOMFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceBOMFolderApplicationRelServiceUtil.class,
				"getCommerceBOMFolderApplicationRelsCountByCommerceBOMFolderId",
				_getCommerceBOMFolderApplicationRelsCountByCommerceBOMFolderIdParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceBOMFolderId);

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

	private static Log _log = LogFactoryUtil.getLog(
		CommerceBOMFolderApplicationRelServiceHttp.class);

	private static final Class<?>[]
		_addCommerceBOMFolderApplicationRelParameterTypes0 = new Class[] {
			long.class, long.class, long.class
		};
	private static final Class<?>[]
		_deleteCommerceBOMFolderApplicationRelParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCommerceBOMFolderApplicationRelsByCAMIdParameterTypes2 =
			new Class[] {long.class, int.class, int.class};
	private static final Class<?>[]
		_getCommerceBOMFolderApplicationRelsByCommerceBOMFolderIdParameterTypes3 =
			new Class[] {long.class, int.class, int.class};
	private static final Class<?>[]
		_getCommerceBOMFolderApplicationRelsCountByCAMIdParameterTypes4 =
			new Class[] {long.class};
	private static final Class<?>[]
		_getCommerceBOMFolderApplicationRelsCountByCommerceBOMFolderIdParameterTypes5 =
			new Class[] {long.class};

}