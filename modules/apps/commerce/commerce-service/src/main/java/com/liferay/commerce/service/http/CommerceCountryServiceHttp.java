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

import com.liferay.commerce.service.CommerceCountryServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CommerceCountryServiceUtil</code> service
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
 * @see CommerceCountryServiceSoap
 * @generated
 */
public class CommerceCountryServiceHttp {

	public static com.liferay.commerce.model.CommerceCountry addCommerceCountry(
			HttpPrincipal httpPrincipal,
			java.util.Map<java.util.Locale, String> nameMap,
			boolean billingAllowed, boolean shippingAllowed,
			String twoLettersISOCode, String threeLettersISOCode,
			int numericISOCode, boolean subjectToVAT, double priority,
			boolean active,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceCountryServiceUtil.class, "addCommerceCountry",
				_addCommerceCountryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, nameMap, billingAllowed, shippingAllowed,
				twoLettersISOCode, threeLettersISOCode, numericISOCode,
				subjectToVAT, priority, active, serviceContext);

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

			return (com.liferay.commerce.model.CommerceCountry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteCommerceCountry(
			HttpPrincipal httpPrincipal, long commerceCountryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceCountryServiceUtil.class, "deleteCommerceCountry",
				_deleteCommerceCountryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceCountryId);

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

	public static com.liferay.commerce.model.CommerceCountry
			fetchCommerceCountry(
				HttpPrincipal httpPrincipal, long companyId,
				String twoLettersISOCode)
		throws com.liferay.portal.kernel.security.auth.PrincipalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceCountryServiceUtil.class, "fetchCommerceCountry",
				_fetchCommerceCountryParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, twoLettersISOCode);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.security.auth.
							PrincipalException) {

					throw (com.liferay.portal.kernel.security.auth.
						PrincipalException)exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.commerce.model.CommerceCountry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommerceCountry>
		getBillingCommerceCountries(
			HttpPrincipal httpPrincipal, long companyId, boolean billingAllowed,
			boolean active) {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceCountryServiceUtil.class, "getBillingCommerceCountries",
				_getBillingCommerceCountriesParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, billingAllowed, active);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.commerce.model.CommerceCountry>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommerceCountry>
		getBillingCommerceCountriesByChannelId(
			HttpPrincipal httpPrincipal, long commerceChannelId, int start,
			int end) {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceCountryServiceUtil.class,
				"getBillingCommerceCountriesByChannelId",
				_getBillingCommerceCountriesByChannelIdParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceChannelId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.commerce.model.CommerceCountry>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommerceCountry>
		getCommerceCountries(
			HttpPrincipal httpPrincipal, long companyId, boolean active) {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceCountryServiceUtil.class, "getCommerceCountries",
				_getCommerceCountriesParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, active);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.commerce.model.CommerceCountry>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommerceCountry>
			getCommerceCountries(
				HttpPrincipal httpPrincipal, long companyId, boolean active,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.model.CommerceCountry>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceCountryServiceUtil.class, "getCommerceCountries",
				_getCommerceCountriesParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, active, start, end, orderByComparator);

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

			return (java.util.List<com.liferay.commerce.model.CommerceCountry>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommerceCountry>
			getCommerceCountries(
				HttpPrincipal httpPrincipal, long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.model.CommerceCountry>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceCountryServiceUtil.class, "getCommerceCountries",
				_getCommerceCountriesParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, start, end, orderByComparator);

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

			return (java.util.List<com.liferay.commerce.model.CommerceCountry>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommerceCountriesCount(
			HttpPrincipal httpPrincipal, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceCountryServiceUtil.class, "getCommerceCountriesCount",
				_getCommerceCountriesCountParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId);

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

	public static int getCommerceCountriesCount(
			HttpPrincipal httpPrincipal, long companyId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceCountryServiceUtil.class, "getCommerceCountriesCount",
				_getCommerceCountriesCountParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, active);

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

	public static com.liferay.commerce.model.CommerceCountry getCommerceCountry(
			HttpPrincipal httpPrincipal, long commerceCountryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceCountryServiceUtil.class, "getCommerceCountry",
				_getCommerceCountryParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceCountryId);

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

			return (com.liferay.commerce.model.CommerceCountry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceCountry getCommerceCountry(
			HttpPrincipal httpPrincipal, long companyId,
			String twoLettersISOCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceCountryServiceUtil.class, "getCommerceCountry",
				_getCommerceCountryParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, twoLettersISOCode);

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

			return (com.liferay.commerce.model.CommerceCountry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommerceCountry>
		getShippingCommerceCountries(
			HttpPrincipal httpPrincipal, long companyId,
			boolean shippingAllowed, boolean active) {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceCountryServiceUtil.class,
				"getShippingCommerceCountries",
				_getShippingCommerceCountriesParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, shippingAllowed, active);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.commerce.model.CommerceCountry>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommerceCountry>
		getShippingCommerceCountriesByChannelId(
			HttpPrincipal httpPrincipal, long commerceChannelId, int start,
			int end) {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceCountryServiceUtil.class,
				"getShippingCommerceCountriesByChannelId",
				_getShippingCommerceCountriesByChannelIdParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceChannelId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.commerce.model.CommerceCountry>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommerceCountry>
			getWarehouseCommerceCountries(
				HttpPrincipal httpPrincipal, long companyId, boolean all)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceCountryServiceUtil.class,
				"getWarehouseCommerceCountries",
				_getWarehouseCommerceCountriesParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, all);

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

			return (java.util.List<com.liferay.commerce.model.CommerceCountry>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.commerce.model.CommerceCountry> searchCommerceCountries(
				HttpPrincipal httpPrincipal, long companyId, Boolean active,
				String keywords, int start, int end,
				com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceCountryServiceUtil.class, "searchCommerceCountries",
				_searchCommerceCountriesParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, active, keywords, start, end, sort);

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
				<com.liferay.commerce.model.CommerceCountry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.commerce.model.CommerceCountry> searchCommerceCountries(
				HttpPrincipal httpPrincipal,
				com.liferay.portal.kernel.search.SearchContext searchContext)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceCountryServiceUtil.class, "searchCommerceCountries",
				_searchCommerceCountriesParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, searchContext);

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
				<com.liferay.commerce.model.CommerceCountry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceCountry setActive(
			HttpPrincipal httpPrincipal, long commerceCountryId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceCountryServiceUtil.class, "setActive",
				_setActiveParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceCountryId, active);

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

			return (com.liferay.commerce.model.CommerceCountry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceCountry
			updateCommerceCountry(
				HttpPrincipal httpPrincipal, long commerceCountryId,
				java.util.Map<java.util.Locale, String> nameMap,
				boolean billingAllowed, boolean shippingAllowed,
				String twoLettersISOCode, String threeLettersISOCode,
				int numericISOCode, boolean subjectToVAT, double priority,
				boolean active,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceCountryServiceUtil.class, "updateCommerceCountry",
				_updateCommerceCountryParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceCountryId, nameMap, billingAllowed,
				shippingAllowed, twoLettersISOCode, threeLettersISOCode,
				numericISOCode, subjectToVAT, priority, active, serviceContext);

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

			return (com.liferay.commerce.model.CommerceCountry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceCountry
			updateCommerceCountryChannelFilter(
				HttpPrincipal httpPrincipal, long commerceCountryId,
				boolean enable)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceCountryServiceUtil.class,
				"updateCommerceCountryChannelFilter",
				_updateCommerceCountryChannelFilterParameterTypes19);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceCountryId, enable);

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

			return (com.liferay.commerce.model.CommerceCountry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceCountryServiceHttp.class);

	private static final Class<?>[] _addCommerceCountryParameterTypes0 =
		new Class[] {
			java.util.Map.class, boolean.class, boolean.class, String.class,
			String.class, int.class, boolean.class, double.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCommerceCountryParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _fetchCommerceCountryParameterTypes2 =
		new Class[] {long.class, String.class};
	private static final Class<?>[]
		_getBillingCommerceCountriesParameterTypes3 = new Class[] {
			long.class, boolean.class, boolean.class
		};
	private static final Class<?>[]
		_getBillingCommerceCountriesByChannelIdParameterTypes4 = new Class[] {
			long.class, int.class, int.class
		};
	private static final Class<?>[] _getCommerceCountriesParameterTypes5 =
		new Class[] {long.class, boolean.class};
	private static final Class<?>[] _getCommerceCountriesParameterTypes6 =
		new Class[] {
			long.class, boolean.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCommerceCountriesParameterTypes7 =
		new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCommerceCountriesCountParameterTypes8 =
		new Class[] {long.class};
	private static final Class<?>[] _getCommerceCountriesCountParameterTypes9 =
		new Class[] {long.class, boolean.class};
	private static final Class<?>[] _getCommerceCountryParameterTypes10 =
		new Class[] {long.class};
	private static final Class<?>[] _getCommerceCountryParameterTypes11 =
		new Class[] {long.class, String.class};
	private static final Class<?>[]
		_getShippingCommerceCountriesParameterTypes12 = new Class[] {
			long.class, boolean.class, boolean.class
		};
	private static final Class<?>[]
		_getShippingCommerceCountriesByChannelIdParameterTypes13 = new Class[] {
			long.class, int.class, int.class
		};
	private static final Class<?>[]
		_getWarehouseCommerceCountriesParameterTypes14 = new Class[] {
			long.class, boolean.class
		};
	private static final Class<?>[] _searchCommerceCountriesParameterTypes15 =
		new Class[] {
			long.class, Boolean.class, String.class, int.class, int.class,
			com.liferay.portal.kernel.search.Sort.class
		};
	private static final Class<?>[] _searchCommerceCountriesParameterTypes16 =
		new Class[] {com.liferay.portal.kernel.search.SearchContext.class};
	private static final Class<?>[] _setActiveParameterTypes17 = new Class[] {
		long.class, boolean.class
	};
	private static final Class<?>[] _updateCommerceCountryParameterTypes18 =
		new Class[] {
			long.class, java.util.Map.class, boolean.class, boolean.class,
			String.class, String.class, int.class, boolean.class, double.class,
			boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_updateCommerceCountryChannelFilterParameterTypes19 = new Class[] {
			long.class, boolean.class
		};

}