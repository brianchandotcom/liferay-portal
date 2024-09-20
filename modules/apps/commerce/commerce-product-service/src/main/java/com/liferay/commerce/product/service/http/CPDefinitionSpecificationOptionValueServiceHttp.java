/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.http;

import com.liferay.commerce.product.service.CPDefinitionSpecificationOptionValueServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CPDefinitionSpecificationOptionValueServiceUtil</code> service
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
public class CPDefinitionSpecificationOptionValueServiceHttp {

	public static
		com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue
				addCPDefinitionSpecificationOptionValue(
					HttpPrincipal httpPrincipal, String externalReferenceCode,
					long cpDefinitionId, long cpSpecificationOptionId,
					long cpOptionCategoryId, double priority,
					java.util.Map<java.util.Locale, String> valueMap,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionSpecificationOptionValueServiceUtil.class,
				"addCPDefinitionSpecificationOptionValue",
				_addCPDefinitionSpecificationOptionValueParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, cpDefinitionId,
				cpSpecificationOptionId, cpOptionCategoryId, priority, valueMap,
				serviceContext);

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

			return (com.liferay.commerce.product.model.
				CPDefinitionSpecificationOptionValue)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteCPDefinitionSpecificationOptionValue(
			HttpPrincipal httpPrincipal,
			long cpDefinitionSpecificationOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionSpecificationOptionValueServiceUtil.class,
				"deleteCPDefinitionSpecificationOptionValue",
				_deleteCPDefinitionSpecificationOptionValueParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionSpecificationOptionValueId);

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

	public static void deleteCPDefinitionSpecificationOptionValues(
			HttpPrincipal httpPrincipal, long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionSpecificationOptionValueServiceUtil.class,
				"deleteCPDefinitionSpecificationOptionValues",
				_deleteCPDefinitionSpecificationOptionValuesParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionId);

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
		com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue
				fetchCPDefinitionSpecificationOptionValue(
					HttpPrincipal httpPrincipal,
					long cpDefinitionSpecificationOptionValueId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionSpecificationOptionValueServiceUtil.class,
				"fetchCPDefinitionSpecificationOptionValue",
				_fetchCPDefinitionSpecificationOptionValueParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionSpecificationOptionValueId);

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

			return (com.liferay.commerce.product.model.
				CPDefinitionSpecificationOptionValue)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue
				fetchCPDefinitionSpecificationOptionValueByExternalReferenceCode(
					HttpPrincipal httpPrincipal, String externalReferenceCode,
					long companyId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionSpecificationOptionValueServiceUtil.class,
				"fetchCPDefinitionSpecificationOptionValueByExternalReferenceCode",
				_fetchCPDefinitionSpecificationOptionValueByExternalReferenceCodeParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, companyId);

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

			return (com.liferay.commerce.product.model.
				CPDefinitionSpecificationOptionValue)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue
				getCPDefinitionSpecificationOptionValue(
					HttpPrincipal httpPrincipal,
					long cpDefinitionSpecificationOptionValueId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionSpecificationOptionValueServiceUtil.class,
				"getCPDefinitionSpecificationOptionValue",
				_getCPDefinitionSpecificationOptionValueParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionSpecificationOptionValueId);

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

			return (com.liferay.commerce.product.model.
				CPDefinitionSpecificationOptionValue)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue
				getCPDefinitionSpecificationOptionValueByExternalReferenceCode(
					HttpPrincipal httpPrincipal, String externalReferenceCode,
					long companyId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionSpecificationOptionValueServiceUtil.class,
				"getCPDefinitionSpecificationOptionValueByExternalReferenceCode",
				_getCPDefinitionSpecificationOptionValueByExternalReferenceCodeParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, companyId);

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

			return (com.liferay.commerce.product.model.
				CPDefinitionSpecificationOptionValue)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.product.model.
			CPDefinitionSpecificationOptionValue>
					getCPDefinitionSpecificationOptionValues(
						HttpPrincipal httpPrincipal, long cpDefinitionId,
						int start, int end,
						com.liferay.portal.kernel.util.OrderByComparator
							<com.liferay.commerce.product.model.
								CPDefinitionSpecificationOptionValue>
									orderByComparator)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionSpecificationOptionValueServiceUtil.class,
				"getCPDefinitionSpecificationOptionValues",
				_getCPDefinitionSpecificationOptionValuesParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionId, start, end, orderByComparator);

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
				<com.liferay.commerce.product.model.
					CPDefinitionSpecificationOptionValue>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.product.model.
			CPDefinitionSpecificationOptionValue>
					getCPDefinitionSpecificationOptionValues(
						HttpPrincipal httpPrincipal, long cpDefinitionId,
						long cpOptionCategoryId)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionSpecificationOptionValueServiceUtil.class,
				"getCPDefinitionSpecificationOptionValues",
				_getCPDefinitionSpecificationOptionValuesParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionId, cpOptionCategoryId);

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
				<com.liferay.commerce.product.model.
					CPDefinitionSpecificationOptionValue>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCPDefinitionSpecificationOptionValuesCount(
			HttpPrincipal httpPrincipal, long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionSpecificationOptionValueServiceUtil.class,
				"getCPDefinitionSpecificationOptionValuesCount",
				_getCPDefinitionSpecificationOptionValuesCountParameterTypes9);

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
		com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue
				updateCPDefinitionSpecificationOptionValue(
					HttpPrincipal httpPrincipal, String externalReferenceCode,
					long cpDefinitionSpecificationOptionValueId,
					long cpOptionCategoryId, String key, double priority,
					java.util.Map<java.util.Locale, String> valueMap,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionSpecificationOptionValueServiceUtil.class,
				"updateCPDefinitionSpecificationOptionValue",
				_updateCPDefinitionSpecificationOptionValueParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode,
				cpDefinitionSpecificationOptionValueId, cpOptionCategoryId, key,
				priority, valueMap, serviceContext);

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

			return (com.liferay.commerce.product.model.
				CPDefinitionSpecificationOptionValue)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CPDefinitionSpecificationOptionValueServiceHttp.class);

	private static final Class<?>[]
		_addCPDefinitionSpecificationOptionValueParameterTypes0 = new Class[] {
			String.class, long.class, long.class, long.class, double.class,
			java.util.Map.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_deleteCPDefinitionSpecificationOptionValueParameterTypes1 =
			new Class[] {long.class};
	private static final Class<?>[]
		_deleteCPDefinitionSpecificationOptionValuesParameterTypes2 =
			new Class[] {long.class};
	private static final Class<?>[]
		_fetchCPDefinitionSpecificationOptionValueParameterTypes3 =
			new Class[] {long.class};
	private static final Class<?>[]
		_fetchCPDefinitionSpecificationOptionValueByExternalReferenceCodeParameterTypes4 =
			new Class[] {String.class, long.class};
	private static final Class<?>[]
		_getCPDefinitionSpecificationOptionValueParameterTypes5 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCPDefinitionSpecificationOptionValueByExternalReferenceCodeParameterTypes6 =
			new Class[] {String.class, long.class};
	private static final Class<?>[]
		_getCPDefinitionSpecificationOptionValuesParameterTypes7 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getCPDefinitionSpecificationOptionValuesParameterTypes8 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[]
		_getCPDefinitionSpecificationOptionValuesCountParameterTypes9 =
			new Class[] {long.class};
	private static final Class<?>[]
		_updateCPDefinitionSpecificationOptionValueParameterTypes10 =
			new Class[] {
				String.class, long.class, long.class, String.class,
				double.class, java.util.Map.class,
				com.liferay.portal.kernel.service.ServiceContext.class
			};

}