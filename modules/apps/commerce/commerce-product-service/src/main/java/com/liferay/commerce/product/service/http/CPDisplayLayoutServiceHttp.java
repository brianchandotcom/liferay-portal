/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.http;

import com.liferay.commerce.product.service.CPDisplayLayoutServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CPDisplayLayoutServiceUtil</code> service
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
 * @author Marco Leo
 * @generated
 */
public class CPDisplayLayoutServiceHttp {

	public static com.liferay.commerce.product.model.CPDisplayLayout
			addCPDisplayLayout(
				HttpPrincipal httpPrincipal, long groupId, Class<?> clazz,
				long classPK, String layoutPageTemplateEntryUuid,
				String layoutUuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDisplayLayoutServiceUtil.class, "addCPDisplayLayout",
				_addCPDisplayLayoutParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, clazz, classPK, layoutPageTemplateEntryUuid,
				layoutUuid);

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

			return (com.liferay.commerce.product.model.CPDisplayLayout)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteCPDisplayLayout(
			HttpPrincipal httpPrincipal, long cpDisplayLayoutId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDisplayLayoutServiceUtil.class, "deleteCPDisplayLayout",
				_deleteCPDisplayLayoutParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDisplayLayoutId);

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

	public static com.liferay.commerce.product.model.CPDisplayLayout
			fetchCPDisplayLayout(
				HttpPrincipal httpPrincipal, long cpDisplayLayoutId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDisplayLayoutServiceUtil.class, "fetchCPDisplayLayout",
				_fetchCPDisplayLayoutParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDisplayLayoutId);

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

			return (com.liferay.commerce.product.model.CPDisplayLayout)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.commerce.product.model.CPDisplayLayout>
				searchCPDisplayLayout(
					HttpPrincipal httpPrincipal, long companyId, long groupId,
					String className, Integer type, String keywords, int start,
					int end, com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDisplayLayoutServiceUtil.class, "searchCPDisplayLayout",
				_searchCPDisplayLayoutParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, className, type, keywords, start,
				end, sort);

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

			return (com.liferay.portal.kernel.search.BaseModelSearchResult
				<com.liferay.commerce.product.model.CPDisplayLayout>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.product.model.CPDisplayLayout
			updateCPDisplayLayout(
				HttpPrincipal httpPrincipal, long cpDisplayLayoutId,
				long classPK, String layoutPageTemplateEntryUuid,
				String layoutUuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDisplayLayoutServiceUtil.class, "updateCPDisplayLayout",
				_updateCPDisplayLayoutParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDisplayLayoutId, classPK,
				layoutPageTemplateEntryUuid, layoutUuid);

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

			return (com.liferay.commerce.product.model.CPDisplayLayout)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CPDisplayLayoutServiceHttp.class);

	private static final Class<?>[] _addCPDisplayLayoutParameterTypes0 =
		new Class[] {
			long.class, Class.class, long.class, String.class, String.class
		};
	private static final Class<?>[] _deleteCPDisplayLayoutParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _fetchCPDisplayLayoutParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[] _searchCPDisplayLayoutParameterTypes3 =
		new Class[] {
			long.class, long.class, String.class, Integer.class, String.class,
			int.class, int.class, com.liferay.portal.kernel.search.Sort.class
		};
	private static final Class<?>[] _updateCPDisplayLayoutParameterTypes4 =
		new Class[] {long.class, long.class, String.class, String.class};

}