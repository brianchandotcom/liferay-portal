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

import com.liferay.commerce.application.service.CommerceApplicationModelServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CommerceApplicationModelServiceUtil</code> service
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
 * @see CommerceApplicationModelServiceSoap
 * @generated
 */
public class CommerceApplicationModelServiceHttp {

	public static
		com.liferay.commerce.application.model.CommerceApplicationModel
				addCommerceApplicationModel(
					HttpPrincipal httpPrincipal, long userId,
					long commerceApplicationBrandId, String name, String year)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceApplicationModelServiceUtil.class,
				"addCommerceApplicationModel",
				_addCommerceApplicationModelParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, commerceApplicationBrandId, name, year);

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
				CommerceApplicationModel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteCommerceApplicationModel(
			HttpPrincipal httpPrincipal, long commerceApplicationModelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceApplicationModelServiceUtil.class,
				"deleteCommerceApplicationModel",
				_deleteCommerceApplicationModelParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceApplicationModelId);

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
		com.liferay.commerce.application.model.CommerceApplicationModel
				getCommerceApplicationModel(
					HttpPrincipal httpPrincipal,
					long commerceApplicationModelId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceApplicationModelServiceUtil.class,
				"getCommerceApplicationModel",
				_getCommerceApplicationModelParameterTypes2);

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

			return (com.liferay.commerce.application.model.
				CommerceApplicationModel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.application.model.CommerceApplicationModel>
			getCommerceApplicationModels(
				HttpPrincipal httpPrincipal, long commerceApplicationBrandId,
				int start, int end) {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceApplicationModelServiceUtil.class,
				"getCommerceApplicationModels",
				_getCommerceApplicationModelsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceApplicationBrandId, start, end);

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
					CommerceApplicationModel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.application.model.CommerceApplicationModel>
			getCommerceApplicationModelsByCompanyId(
				HttpPrincipal httpPrincipal, long companyId, int start,
				int end) {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceApplicationModelServiceUtil.class,
				"getCommerceApplicationModelsByCompanyId",
				_getCommerceApplicationModelsByCompanyIdParameterTypes4);

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
					CommerceApplicationModel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommerceApplicationModelsCount(
		HttpPrincipal httpPrincipal, long commerceApplicationBrandId) {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceApplicationModelServiceUtil.class,
				"getCommerceApplicationModelsCount",
				_getCommerceApplicationModelsCountParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceApplicationBrandId);

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

	public static int getCommerceApplicationModelsCountByCompanyId(
		HttpPrincipal httpPrincipal, long companyId) {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceApplicationModelServiceUtil.class,
				"getCommerceApplicationModelsCountByCompanyId",
				_getCommerceApplicationModelsCountByCompanyIdParameterTypes6);

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
		com.liferay.commerce.application.model.CommerceApplicationModel
				updateCommerceApplicationModel(
					HttpPrincipal httpPrincipal,
					long commerceApplicationModelId, String name, String year)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceApplicationModelServiceUtil.class,
				"updateCommerceApplicationModel",
				_updateCommerceApplicationModelParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceApplicationModelId, name, year);

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
				CommerceApplicationModel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceApplicationModelServiceHttp.class);

	private static final Class<?>[]
		_addCommerceApplicationModelParameterTypes0 = new Class[] {
			long.class, long.class, String.class, String.class
		};
	private static final Class<?>[]
		_deleteCommerceApplicationModelParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCommerceApplicationModelParameterTypes2 = new Class[] {long.class};
	private static final Class<?>[]
		_getCommerceApplicationModelsParameterTypes3 = new Class[] {
			long.class, int.class, int.class
		};
	private static final Class<?>[]
		_getCommerceApplicationModelsByCompanyIdParameterTypes4 = new Class[] {
			long.class, int.class, int.class
		};
	private static final Class<?>[]
		_getCommerceApplicationModelsCountParameterTypes5 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCommerceApplicationModelsCountByCompanyIdParameterTypes6 =
			new Class[] {long.class};
	private static final Class<?>[]
		_updateCommerceApplicationModelParameterTypes7 = new Class[] {
			long.class, String.class, String.class
		};

}