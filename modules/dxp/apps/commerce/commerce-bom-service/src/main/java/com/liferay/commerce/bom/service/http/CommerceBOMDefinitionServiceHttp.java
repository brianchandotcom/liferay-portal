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

import com.liferay.commerce.bom.service.CommerceBOMDefinitionServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CommerceBOMDefinitionServiceUtil</code> service
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
 * @see CommerceBOMDefinitionServiceSoap
 * @generated
 */
public class CommerceBOMDefinitionServiceHttp {

	public static com.liferay.commerce.bom.model.CommerceBOMDefinition
			addCommerceBOMDefinition(
				HttpPrincipal httpPrincipal, long userId,
				long commerceBOMFolderId, long cpAttachmentFileEntryId,
				String name, String friendlyUrl)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceBOMDefinitionServiceUtil.class,
				"addCommerceBOMDefinition",
				_addCommerceBOMDefinitionParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, commerceBOMFolderId, cpAttachmentFileEntryId,
				name, friendlyUrl);

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

			return (com.liferay.commerce.bom.model.CommerceBOMDefinition)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteCommerceBOMDefinition(
			HttpPrincipal httpPrincipal, long commerceBOMDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceBOMDefinitionServiceUtil.class,
				"deleteCommerceBOMDefinition",
				_deleteCommerceBOMDefinitionParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceBOMDefinitionId);

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

	public static com.liferay.commerce.bom.model.CommerceBOMDefinition
			getCommerceBOMDefinition(
				HttpPrincipal httpPrincipal, long commerceBOMDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceBOMDefinitionServiceUtil.class,
				"getCommerceBOMDefinition",
				_getCommerceBOMDefinitionParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceBOMDefinitionId);

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

			return (com.liferay.commerce.bom.model.CommerceBOMDefinition)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.bom.model.CommerceBOMDefinition>
			getCommerceBOMDefinitions(
				HttpPrincipal httpPrincipal, long commerceBOMFolderId,
				int start, int end) {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceBOMDefinitionServiceUtil.class,
				"getCommerceBOMDefinitions",
				_getCommerceBOMDefinitionsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceBOMFolderId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.commerce.bom.model.CommerceBOMDefinition>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommerceBOMDefinitionsCount(
		HttpPrincipal httpPrincipal, long commerceBOMFolderId) {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceBOMDefinitionServiceUtil.class,
				"getCommerceBOMDefinitionsCount",
				_getCommerceBOMDefinitionsCountParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceBOMFolderId);

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

	public static com.liferay.commerce.bom.model.CommerceBOMDefinition
			updateCommerceBOMDefinition(
				HttpPrincipal httpPrincipal, long commerceBOMDefinitionId,
				long cpAttachmentFileEntryId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceBOMDefinitionServiceUtil.class,
				"updateCommerceBOMDefinition",
				_updateCommerceBOMDefinitionParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceBOMDefinitionId, cpAttachmentFileEntryId,
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

			return (com.liferay.commerce.bom.model.CommerceBOMDefinition)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceBOMDefinitionServiceHttp.class);

	private static final Class<?>[] _addCommerceBOMDefinitionParameterTypes0 =
		new Class[] {
			long.class, long.class, long.class, String.class, String.class
		};
	private static final Class<?>[]
		_deleteCommerceBOMDefinitionParameterTypes1 = new Class[] {long.class};
	private static final Class<?>[] _getCommerceBOMDefinitionParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[] _getCommerceBOMDefinitionsParameterTypes3 =
		new Class[] {long.class, int.class, int.class};
	private static final Class<?>[]
		_getCommerceBOMDefinitionsCountParameterTypes4 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_updateCommerceBOMDefinitionParameterTypes5 = new Class[] {
			long.class, long.class, String.class
		};

}