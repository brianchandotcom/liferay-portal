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

package com.liferay.commerce.product.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.service.CPOptionValueServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CPOptionValueServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link HttpPrincipal} parameter.
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
 * @see CPOptionValueServiceSoap
 * @see HttpPrincipal
 * @see CPOptionValueServiceUtil
 * @generated
 */
@ProviderType
public class CPOptionValueServiceHttp {
	public static com.liferay.commerce.product.model.CPOptionValue addCPOptionValue(
		HttpPrincipal httpPrincipal, long cpOptionId,
		java.util.Map<java.util.Locale, String> titleMap, double priority,
		String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPOptionValueServiceUtil.class,
					"addCPOptionValue", _addCPOptionValueParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpOptionId, titleMap, priority, key, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.product.model.CPOptionValue)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCPOptionValue(HttpPrincipal httpPrincipal,
		long cpOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPOptionValueServiceUtil.class,
					"deleteCPOptionValue", _deleteCPOptionValueParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpOptionValueId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPOptionValue fetchCPOptionValue(
		HttpPrincipal httpPrincipal, long cpOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPOptionValueServiceUtil.class,
					"fetchCPOptionValue", _fetchCPOptionValueParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpOptionValueId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.product.model.CPOptionValue)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPOptionValue getCPOptionValue(
		HttpPrincipal httpPrincipal, long cpOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPOptionValueServiceUtil.class,
					"getCPOptionValue", _getCPOptionValueParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpOptionValueId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.product.model.CPOptionValue)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPOptionValue updateCPOptionValue(
		HttpPrincipal httpPrincipal, long cpOptionValueId,
		java.util.Map<java.util.Locale, String> titleMap, double priority,
		String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPOptionValueServiceUtil.class,
					"updateCPOptionValue", _updateCPOptionValueParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpOptionValueId, titleMap, priority, key, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.product.model.CPOptionValue)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CPOptionValueServiceHttp.class);
	private static final Class<?>[] _addCPOptionValueParameterTypes0 = new Class[] {
			long.class, java.util.Map.class, double.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCPOptionValueParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _fetchCPOptionValueParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCPOptionValueParameterTypes3 = new Class[] {
			long.class
		};
	private static final Class<?>[] _updateCPOptionValueParameterTypes4 = new Class[] {
			long.class, java.util.Map.class, double.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
}