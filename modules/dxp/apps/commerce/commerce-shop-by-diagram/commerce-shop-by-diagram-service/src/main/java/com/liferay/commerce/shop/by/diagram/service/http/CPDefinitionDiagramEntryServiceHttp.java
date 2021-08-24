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

import com.liferay.commerce.shop.by.diagram.service.CPDefinitionDiagramEntryServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CPDefinitionDiagramEntryServiceUtil</code> service
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
 * @see CPDefinitionDiagramEntryServiceSoap
 * @generated
 */
public class CPDefinitionDiagramEntryServiceHttp {

	public static
		com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry
				addCPDefinitionDiagramEntry(
					HttpPrincipal httpPrincipal, long cpDefinitionId,
					String cpInstanceUuid, long cProductId, boolean diagram,
					int number, String sequence, String sku,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionDiagramEntryServiceUtil.class,
				"addCPDefinitionDiagramEntry",
				_addCPDefinitionDiagramEntryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionId, cpInstanceUuid, cProductId, diagram,
				number, sequence, sku, serviceContext);

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
				CPDefinitionDiagramEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteCPDefinitionDiagramEntry(
			HttpPrincipal httpPrincipal, long cpDefinitionDiagramEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionDiagramEntryServiceUtil.class,
				"deleteCPDefinitionDiagramEntry",
				_deleteCPDefinitionDiagramEntryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionDiagramEntryId);

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
		com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry
				fetchCPDefinitionDiagramEntry(
					HttpPrincipal httpPrincipal, long cpDefinitionId,
					String sequence)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionDiagramEntryServiceUtil.class,
				"fetchCPDefinitionDiagramEntry",
				_fetchCPDefinitionDiagramEntryParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionId, sequence);

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
				CPDefinitionDiagramEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry>
				getCPDefinitionDiagramEntries(
					HttpPrincipal httpPrincipal, long cpDefinitionId, int start,
					int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionDiagramEntryServiceUtil.class,
				"getCPDefinitionDiagramEntries",
				_getCPDefinitionDiagramEntriesParameterTypes3);

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
					CPDefinitionDiagramEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCPDefinitionDiagramEntriesCount(
			HttpPrincipal httpPrincipal, long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionDiagramEntryServiceUtil.class,
				"getCPDefinitionDiagramEntriesCount",
				_getCPDefinitionDiagramEntriesCountParameterTypes4);

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
		com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry
				getCPDefinitionDiagramEntry(
					HttpPrincipal httpPrincipal,
					long cpDefinitionDiagramEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionDiagramEntryServiceUtil.class,
				"getCPDefinitionDiagramEntry",
				_getCPDefinitionDiagramEntryParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionDiagramEntryId);

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
				CPDefinitionDiagramEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry
				getCPDefinitionDiagramEntry(
					HttpPrincipal httpPrincipal, long cpDefinitionId,
					String sequence)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionDiagramEntryServiceUtil.class,
				"getCPDefinitionDiagramEntry",
				_getCPDefinitionDiagramEntryParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionId, sequence);

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
				CPDefinitionDiagramEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry
				updateCPDefinitionDiagramEntry(
					HttpPrincipal httpPrincipal,
					long cpDefinitionDiagramEntryId, String cpInstanceUuid,
					long cProductId, boolean diagram, int number, String sku,
					String sequence,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionDiagramEntryServiceUtil.class,
				"updateCPDefinitionDiagramEntry",
				_updateCPDefinitionDiagramEntryParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionDiagramEntryId, cpInstanceUuid,
				cProductId, diagram, number, sku, sequence, serviceContext);

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
				CPDefinitionDiagramEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CPDefinitionDiagramEntryServiceHttp.class);

	private static final Class<?>[]
		_addCPDefinitionDiagramEntryParameterTypes0 = new Class[] {
			long.class, String.class, long.class, boolean.class, int.class,
			String.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_deleteCPDefinitionDiagramEntryParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_fetchCPDefinitionDiagramEntryParameterTypes2 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[]
		_getCPDefinitionDiagramEntriesParameterTypes3 = new Class[] {
			long.class, int.class, int.class
		};
	private static final Class<?>[]
		_getCPDefinitionDiagramEntriesCountParameterTypes4 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCPDefinitionDiagramEntryParameterTypes5 = new Class[] {long.class};
	private static final Class<?>[]
		_getCPDefinitionDiagramEntryParameterTypes6 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[]
		_updateCPDefinitionDiagramEntryParameterTypes7 = new Class[] {
			long.class, String.class, long.class, boolean.class, int.class,
			String.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};

}