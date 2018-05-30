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

package com.liferay.commerce.product.type.virtual.order.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.type.virtual.order.service.CommerceVirtualOrderItemServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CommerceVirtualOrderItemServiceUtil} service utility. The
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
 * @author Alessio Antonio Rendina
 * @see CommerceVirtualOrderItemServiceSoap
 * @see HttpPrincipal
 * @see CommerceVirtualOrderItemServiceUtil
 * @generated
 */
@ProviderType
public class CommerceVirtualOrderItemServiceHttp {
	public static java.io.File getFile(HttpPrincipal httpPrincipal,
		long commerceVirtualOrderItemId) throws Exception {
		try {
			MethodKey methodKey = new MethodKey(CommerceVirtualOrderItemServiceUtil.class,
					"getFile", _getFileParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceVirtualOrderItemId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof Exception) {
					throw (Exception)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.io.File)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem updateCommerceVirtualOrderItem(
		HttpPrincipal httpPrincipal, long commerceVirtualOrderItemId,
		long fileEntryId, String url, int activationStatus, long duration,
		int usages, int maxUsages, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceVirtualOrderItemServiceUtil.class,
					"updateCommerceVirtualOrderItem",
					_updateCommerceVirtualOrderItemParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceVirtualOrderItemId, fileEntryId, url,
					activationStatus, duration, usages, maxUsages, active);

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

			return (com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceVirtualOrderItemServiceHttp.class);
	private static final Class<?>[] _getFileParameterTypes0 = new Class[] {
			long.class
		};
	private static final Class<?>[] _updateCommerceVirtualOrderItemParameterTypes1 =
		new Class[] {
			long.class, long.class, String.class, int.class, long.class,
			int.class, int.class, boolean.class
		};
}