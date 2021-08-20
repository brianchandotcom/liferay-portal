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

package com.liferay.commerce.service.http;

import com.liferay.commerce.service.CommerceShippingMethodServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CommerceShippingMethodServiceUtil</code> service
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
 * @author Alessio Antonio Rendina
 * @see CommerceShippingMethodServiceSoap
 * @generated
 */
public class CommerceShippingMethodServiceHttp {

	public static com.liferay.commerce.model.CommerceAddressRestriction
			addCommerceAddressRestriction(
				HttpPrincipal httpPrincipal, long groupId,
				long commerceShippingMethodId, long countryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingMethodServiceUtil.class,
				"addCommerceAddressRestriction",
				_addCommerceAddressRestrictionParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, commerceShippingMethodId, countryId);

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

			return (com.liferay.commerce.model.CommerceAddressRestriction)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceAddressRestriction
			addCommerceAddressRestriction(
				HttpPrincipal httpPrincipal, long commerceShippingMethodId,
				long countryId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingMethodServiceUtil.class,
				"addCommerceAddressRestriction",
				_addCommerceAddressRestrictionParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceShippingMethodId, countryId, serviceContext);

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

			return (com.liferay.commerce.model.CommerceAddressRestriction)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceShippingMethod
			addCommerceShippingMethod(
				HttpPrincipal httpPrincipal, long groupId,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				java.io.File imageFile, String engineKey, double priority,
				boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingMethodServiceUtil.class,
				"addCommerceShippingMethod",
				_addCommerceShippingMethodParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, nameMap, descriptionMap, imageFile,
				engineKey, priority, active);

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

			return (com.liferay.commerce.model.CommerceShippingMethod)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceShippingMethod
			createCommerceShippingMethod(
				HttpPrincipal httpPrincipal, long commerceShippingMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingMethodServiceUtil.class,
				"createCommerceShippingMethod",
				_createCommerceShippingMethodParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceShippingMethodId);

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

			return (com.liferay.commerce.model.CommerceShippingMethod)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteCommerceAddressRestriction(
			HttpPrincipal httpPrincipal, long commerceAddressRestrictionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingMethodServiceUtil.class,
				"deleteCommerceAddressRestriction",
				_deleteCommerceAddressRestrictionParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceAddressRestrictionId);

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

	public static void deleteCommerceAddressRestrictions(
			HttpPrincipal httpPrincipal, long commerceShippingMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingMethodServiceUtil.class,
				"deleteCommerceAddressRestrictions",
				_deleteCommerceAddressRestrictionsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceShippingMethodId);

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

	public static void deleteCommerceShippingMethod(
			HttpPrincipal httpPrincipal, long commerceShippingMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingMethodServiceUtil.class,
				"deleteCommerceShippingMethod",
				_deleteCommerceShippingMethodParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceShippingMethodId);

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

	public static com.liferay.commerce.model.CommerceShippingMethod
			fetchCommerceShippingMethod(
				HttpPrincipal httpPrincipal, long groupId, String engineKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingMethodServiceUtil.class,
				"fetchCommerceShippingMethod",
				_fetchCommerceShippingMethodParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, engineKey);

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

			return (com.liferay.commerce.model.CommerceShippingMethod)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.model.CommerceAddressRestriction>
				getCommerceAddressRestrictions(
					HttpPrincipal httpPrincipal, long commerceShippingMethodId,
					int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.model.CommerceAddressRestriction>
							orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingMethodServiceUtil.class,
				"getCommerceAddressRestrictions",
				_getCommerceAddressRestrictionsParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceShippingMethodId, start, end,
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
				<com.liferay.commerce.model.CommerceAddressRestriction>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommerceAddressRestrictionsCount(
			HttpPrincipal httpPrincipal, long commerceShippingMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingMethodServiceUtil.class,
				"getCommerceAddressRestrictionsCount",
				_getCommerceAddressRestrictionsCountParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceShippingMethodId);

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

	public static com.liferay.commerce.model.CommerceShippingMethod
			getCommerceShippingMethod(
				HttpPrincipal httpPrincipal, long commerceShippingMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingMethodServiceUtil.class,
				"getCommerceShippingMethod",
				_getCommerceShippingMethodParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceShippingMethodId);

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

			return (com.liferay.commerce.model.CommerceShippingMethod)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.model.CommerceShippingMethod>
				getCommerceShippingMethods(
					HttpPrincipal httpPrincipal, long groupId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingMethodServiceUtil.class,
				"getCommerceShippingMethods",
				_getCommerceShippingMethodsParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

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
				<com.liferay.commerce.model.CommerceShippingMethod>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.model.CommerceShippingMethod>
				getCommerceShippingMethods(
					HttpPrincipal httpPrincipal, long groupId, boolean active)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingMethodServiceUtil.class,
				"getCommerceShippingMethods",
				_getCommerceShippingMethodsParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, active);

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
				<com.liferay.commerce.model.CommerceShippingMethod>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.model.CommerceShippingMethod>
				getCommerceShippingMethods(
					HttpPrincipal httpPrincipal, long groupId, long countryId,
					boolean active)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingMethodServiceUtil.class,
				"getCommerceShippingMethods",
				_getCommerceShippingMethodsParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, countryId, active);

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
				<com.liferay.commerce.model.CommerceShippingMethod>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommerceShippingMethodsCount(
			HttpPrincipal httpPrincipal, long groupId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingMethodServiceUtil.class,
				"getCommerceShippingMethodsCount",
				_getCommerceShippingMethodsCountParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, active);

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

	public static com.liferay.commerce.model.CommerceShippingMethod setActive(
			HttpPrincipal httpPrincipal, long commerceShippingMethodId,
			boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingMethodServiceUtil.class, "setActive",
				_setActiveParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceShippingMethodId, active);

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

			return (com.liferay.commerce.model.CommerceShippingMethod)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceShippingMethod
			updateCommerceShippingMethod(
				HttpPrincipal httpPrincipal, long commerceShippingMethodId,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				java.io.File imageFile, double priority, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingMethodServiceUtil.class,
				"updateCommerceShippingMethod",
				_updateCommerceShippingMethodParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceShippingMethodId, nameMap, descriptionMap,
				imageFile, priority, active);

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

			return (com.liferay.commerce.model.CommerceShippingMethod)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceShippingMethodServiceHttp.class);

	private static final Class<?>[]
		_addCommerceAddressRestrictionParameterTypes0 = new Class[] {
			long.class, long.class, long.class
		};
	private static final Class<?>[]
		_addCommerceAddressRestrictionParameterTypes1 = new Class[] {
			long.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addCommerceShippingMethodParameterTypes2 =
		new Class[] {
			long.class, java.util.Map.class, java.util.Map.class,
			java.io.File.class, String.class, double.class, boolean.class
		};
	private static final Class<?>[]
		_createCommerceShippingMethodParameterTypes3 = new Class[] {long.class};
	private static final Class<?>[]
		_deleteCommerceAddressRestrictionParameterTypes4 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_deleteCommerceAddressRestrictionsParameterTypes5 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_deleteCommerceShippingMethodParameterTypes6 = new Class[] {long.class};
	private static final Class<?>[]
		_fetchCommerceShippingMethodParameterTypes7 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[]
		_getCommerceAddressRestrictionsParameterTypes8 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getCommerceAddressRestrictionsCountParameterTypes9 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCommerceShippingMethodParameterTypes10 =
		new Class[] {long.class};
	private static final Class<?>[]
		_getCommerceShippingMethodsParameterTypes11 = new Class[] {long.class};
	private static final Class<?>[]
		_getCommerceShippingMethodsParameterTypes12 = new Class[] {
			long.class, boolean.class
		};
	private static final Class<?>[]
		_getCommerceShippingMethodsParameterTypes13 = new Class[] {
			long.class, long.class, boolean.class
		};
	private static final Class<?>[]
		_getCommerceShippingMethodsCountParameterTypes14 = new Class[] {
			long.class, boolean.class
		};
	private static final Class<?>[] _setActiveParameterTypes15 = new Class[] {
		long.class, boolean.class
	};
	private static final Class<?>[]
		_updateCommerceShippingMethodParameterTypes16 = new Class[] {
			long.class, java.util.Map.class, java.util.Map.class,
			java.io.File.class, double.class, boolean.class
		};

}