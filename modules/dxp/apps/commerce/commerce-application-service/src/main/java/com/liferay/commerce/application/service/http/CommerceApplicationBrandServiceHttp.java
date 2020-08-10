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

package com.liferay.commerce.application.service.http;

import com.liferay.commerce.application.service.CommerceApplicationBrandServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CommerceApplicationBrandServiceUtil</code> service
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
 * @see CommerceApplicationBrandServiceSoap
 * @generated
 */
public class CommerceApplicationBrandServiceHttp {

	public static
		com.liferay.commerce.application.model.CommerceApplicationBrand
				addCommerceApplicationBrand(
					HttpPrincipal httpPrincipal, long userId, String name,
					boolean logo, byte[] logoBytes)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceApplicationBrandServiceUtil.class,
				"addCommerceApplicationBrand",
				_addCommerceApplicationBrandParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, name, logo, logoBytes);

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

			return (com.liferay.commerce.application.model.
				CommerceApplicationBrand)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteCommerceApplicationBrand(
			HttpPrincipal httpPrincipal, long commerceApplicationBrandId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceApplicationBrandServiceUtil.class,
				"deleteCommerceApplicationBrand",
				_deleteCommerceApplicationBrandParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceApplicationBrandId);

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
		com.liferay.commerce.application.model.CommerceApplicationBrand
				getCommerceApplicationBrand(
					HttpPrincipal httpPrincipal,
					long commerceApplicationBrandId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceApplicationBrandServiceUtil.class,
				"getCommerceApplicationBrand",
				_getCommerceApplicationBrandParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceApplicationBrandId);

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

			return (com.liferay.commerce.application.model.
				CommerceApplicationBrand)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.application.model.CommerceApplicationBrand>
			getCommerceApplicationBrands(
				HttpPrincipal httpPrincipal, long companyId, int start,
				int end) {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceApplicationBrandServiceUtil.class,
				"getCommerceApplicationBrands",
				_getCommerceApplicationBrandsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.commerce.application.model.
					CommerceApplicationBrand>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommerceApplicationBrandsCount(
		HttpPrincipal httpPrincipal, long companyId) {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceApplicationBrandServiceUtil.class,
				"getCommerceApplicationBrandsCount",
				_getCommerceApplicationBrandsCountParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId);

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
		com.liferay.commerce.application.model.CommerceApplicationBrand
				updateCommerceApplicationBrand(
					HttpPrincipal httpPrincipal,
					long commerceApplicationBrandId, String name, boolean logo,
					byte[] logoBytes)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceApplicationBrandServiceUtil.class,
				"updateCommerceApplicationBrand",
				_updateCommerceApplicationBrandParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceApplicationBrandId, name, logo, logoBytes);

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

			return (com.liferay.commerce.application.model.
				CommerceApplicationBrand)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceApplicationBrandServiceHttp.class);

	private static final Class<?>[]
		_addCommerceApplicationBrandParameterTypes0 = new Class[] {
			long.class, String.class, boolean.class, byte[].class
		};
	private static final Class<?>[]
		_deleteCommerceApplicationBrandParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCommerceApplicationBrandParameterTypes2 = new Class[] {long.class};
	private static final Class<?>[]
		_getCommerceApplicationBrandsParameterTypes3 = new Class[] {
			long.class, int.class, int.class
		};
	private static final Class<?>[]
		_getCommerceApplicationBrandsCountParameterTypes4 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_updateCommerceApplicationBrandParameterTypes5 = new Class[] {
			long.class, String.class, boolean.class, byte[].class
		};

}