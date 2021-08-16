/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.discount.service.http;

import com.liferay.commerce.discount.service.CommerceDiscountRelServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CommerceDiscountRelServiceUtil</code> service
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
 * @see CommerceDiscountRelServiceSoap
 * @generated
 */
public class CommerceDiscountRelServiceHttp {

	public static com.liferay.commerce.discount.model.CommerceDiscountRel
			addCommerceDiscountRel(
				HttpPrincipal httpPrincipal, long commerceDiscountId,
				String className, long classPK,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountRelServiceUtil.class, "addCommerceDiscountRel",
				_addCommerceDiscountRelParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceDiscountId, className, classPK,
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

			return (com.liferay.commerce.discount.model.CommerceDiscountRel)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteCommerceDiscountRel(
			HttpPrincipal httpPrincipal, long commerceDiscountRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountRelServiceUtil.class,
				"deleteCommerceDiscountRel",
				_deleteCommerceDiscountRelParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceDiscountRelId);

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

	public static com.liferay.commerce.discount.model.CommerceDiscountRel
			fetchCommerceDiscountRel(
				HttpPrincipal httpPrincipal, String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountRelServiceUtil.class,
				"fetchCommerceDiscountRel",
				_fetchCommerceDiscountRelParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, className, classPK);

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

			return (com.liferay.commerce.discount.model.CommerceDiscountRel)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.discount.model.CommerceDiscountRel>
				getCategoriesByCommerceDiscountId(
					HttpPrincipal httpPrincipal, long commerceDiscountId,
					String name, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountRelServiceUtil.class,
				"getCategoriesByCommerceDiscountId",
				_getCategoriesByCommerceDiscountIdParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceDiscountId, name, start, end);

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
				<com.liferay.commerce.discount.model.CommerceDiscountRel>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCategoriesByCommerceDiscountIdCount(
			HttpPrincipal httpPrincipal, long commerceDiscountId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountRelServiceUtil.class,
				"getCategoriesByCommerceDiscountIdCount",
				_getCategoriesByCommerceDiscountIdCountParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceDiscountId, name);

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

	public static long[] getClassPKs(
			HttpPrincipal httpPrincipal, long commerceDiscountId,
			String className)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountRelServiceUtil.class, "getClassPKs",
				_getClassPKsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceDiscountId, className);

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

			return (long[])returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.discount.model.CommerceDiscountRel
			getCommerceDiscountRel(
				HttpPrincipal httpPrincipal, long commerceDiscountRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountRelServiceUtil.class, "getCommerceDiscountRel",
				_getCommerceDiscountRelParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceDiscountRelId);

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

			return (com.liferay.commerce.discount.model.CommerceDiscountRel)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.discount.model.CommerceDiscountRel>
				getCommerceDiscountRels(
					HttpPrincipal httpPrincipal, long commerceDiscountId,
					String className)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountRelServiceUtil.class, "getCommerceDiscountRels",
				_getCommerceDiscountRelsParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceDiscountId, className);

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
				<com.liferay.commerce.discount.model.CommerceDiscountRel>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.discount.model.CommerceDiscountRel>
				getCommerceDiscountRels(
					HttpPrincipal httpPrincipal, long commerceDiscountId,
					String className, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.discount.model.
							CommerceDiscountRel> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountRelServiceUtil.class, "getCommerceDiscountRels",
				_getCommerceDiscountRelsParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceDiscountId, className, start, end,
				orderByComparator);

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
				<com.liferay.commerce.discount.model.CommerceDiscountRel>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommerceDiscountRelsCount(
			HttpPrincipal httpPrincipal, long commerceDiscountId,
			String className)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountRelServiceUtil.class,
				"getCommerceDiscountRelsCount",
				_getCommerceDiscountRelsCountParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceDiscountId, className);

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

	public static java.util.List
		<com.liferay.commerce.discount.model.CommerceDiscountRel>
				getCommercePricingClassesByCommerceDiscountId(
					HttpPrincipal httpPrincipal, long commerceDiscountId,
					String title, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountRelServiceUtil.class,
				"getCommercePricingClassesByCommerceDiscountId",
				_getCommercePricingClassesByCommerceDiscountIdParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceDiscountId, title, start, end);

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
				<com.liferay.commerce.discount.model.CommerceDiscountRel>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommercePricingClassesByCommerceDiscountIdCount(
			HttpPrincipal httpPrincipal, long commerceDiscountId, String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountRelServiceUtil.class,
				"getCommercePricingClassesByCommerceDiscountIdCount",
				_getCommercePricingClassesByCommerceDiscountIdCountParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceDiscountId, title);

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

	public static java.util.List
		<com.liferay.commerce.discount.model.CommerceDiscountRel>
				getCPDefinitionsByCommerceDiscountId(
					HttpPrincipal httpPrincipal, long commerceDiscountId,
					String name, String languageId, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountRelServiceUtil.class,
				"getCPDefinitionsByCommerceDiscountId",
				_getCPDefinitionsByCommerceDiscountIdParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceDiscountId, name, languageId, start, end);

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
				<com.liferay.commerce.discount.model.CommerceDiscountRel>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCPDefinitionsByCommerceDiscountIdCount(
			HttpPrincipal httpPrincipal, long commerceDiscountId, String name,
			String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountRelServiceUtil.class,
				"getCPDefinitionsByCommerceDiscountIdCount",
				_getCPDefinitionsByCommerceDiscountIdCountParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceDiscountId, name, languageId);

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

	public static java.util.List
		<com.liferay.commerce.discount.model.CommerceDiscountRel>
				getCPInstancesByCommerceDiscountId(
					HttpPrincipal httpPrincipal, long commerceDiscountId,
					String sku, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountRelServiceUtil.class,
				"getCPInstancesByCommerceDiscountId",
				_getCPInstancesByCommerceDiscountIdParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceDiscountId, sku, start, end);

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
				<com.liferay.commerce.discount.model.CommerceDiscountRel>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCPInstancesByCommerceDiscountIdCount(
			HttpPrincipal httpPrincipal, long commerceDiscountId, String sku)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountRelServiceUtil.class,
				"getCPInstancesByCommerceDiscountIdCount",
				_getCPInstancesByCommerceDiscountIdCountParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceDiscountId, sku);

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
		CommerceDiscountRelServiceHttp.class);

	private static final Class<?>[] _addCommerceDiscountRelParameterTypes0 =
		new Class[] {
			long.class, String.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCommerceDiscountRelParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _fetchCommerceDiscountRelParameterTypes2 =
		new Class[] {String.class, long.class};
	private static final Class<?>[]
		_getCategoriesByCommerceDiscountIdParameterTypes3 = new Class[] {
			long.class, String.class, int.class, int.class
		};
	private static final Class<?>[]
		_getCategoriesByCommerceDiscountIdCountParameterTypes4 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[] _getClassPKsParameterTypes5 = new Class[] {
		long.class, String.class
	};
	private static final Class<?>[] _getCommerceDiscountRelParameterTypes6 =
		new Class[] {long.class};
	private static final Class<?>[] _getCommerceDiscountRelsParameterTypes7 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _getCommerceDiscountRelsParameterTypes8 =
		new Class[] {
			long.class, String.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getCommerceDiscountRelsCountParameterTypes9 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[]
		_getCommercePricingClassesByCommerceDiscountIdParameterTypes10 =
			new Class[] {long.class, String.class, int.class, int.class};
	private static final Class<?>[]
		_getCommercePricingClassesByCommerceDiscountIdCountParameterTypes11 =
			new Class[] {long.class, String.class};
	private static final Class<?>[]
		_getCPDefinitionsByCommerceDiscountIdParameterTypes12 = new Class[] {
			long.class, String.class, String.class, int.class, int.class
		};
	private static final Class<?>[]
		_getCPDefinitionsByCommerceDiscountIdCountParameterTypes13 =
			new Class[] {long.class, String.class, String.class};
	private static final Class<?>[]
		_getCPInstancesByCommerceDiscountIdParameterTypes14 = new Class[] {
			long.class, String.class, int.class, int.class
		};
	private static final Class<?>[]
		_getCPInstancesByCommerceDiscountIdCountParameterTypes15 = new Class[] {
			long.class, String.class
		};

}