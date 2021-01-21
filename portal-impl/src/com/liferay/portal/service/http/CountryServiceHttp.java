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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.CountryServiceUtil;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CountryServiceUtil</code> service
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
 * @author Brian Wing Shun Chan
 * @see CountryServiceSoap
 * @generated
 */
public class CountryServiceHttp {

	public static com.liferay.portal.kernel.model.Country addCountry(
			HttpPrincipal httpPrincipal, String a2, String a3, boolean active,
			boolean billingAllowed, String idd, String name, String number,
			double position, boolean shippingAllowed, boolean subjectToVAT,
			boolean zipRequired,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CountryServiceUtil.class, "addCountry",
				_addCountryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, a2, a3, active, billingAllowed, idd, name, number,
				position, shippingAllowed, subjectToVAT, zipRequired,
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

			return (com.liferay.portal.kernel.model.Country)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Country addCountry(
			HttpPrincipal httpPrincipal, String name, String a2, String a3,
			String number, String idd, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CountryServiceUtil.class, "addCountry",
				_addCountryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, name, a2, a3, number, idd, active);

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

			return (com.liferay.portal.kernel.model.Country)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteCountry(
			HttpPrincipal httpPrincipal, long countryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CountryServiceUtil.class, "deleteCountry",
				_deleteCountryParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, countryId);

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

	public static com.liferay.portal.kernel.model.Country fetchCountry(
		HttpPrincipal httpPrincipal, long countryId) {

		try {
			MethodKey methodKey = new MethodKey(
				CountryServiceUtil.class, "fetchCountry",
				_fetchCountryParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, countryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.model.Country)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Country fetchCountryByA2(
		HttpPrincipal httpPrincipal, long companyId, String a2) {

		try {
			MethodKey methodKey = new MethodKey(
				CountryServiceUtil.class, "fetchCountryByA2",
				_fetchCountryByA2ParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, a2);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.model.Country)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Country fetchCountryByA2(
		HttpPrincipal httpPrincipal, String a2) {

		try {
			MethodKey methodKey = new MethodKey(
				CountryServiceUtil.class, "fetchCountryByA2",
				_fetchCountryByA2ParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey, a2);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.model.Country)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Country fetchCountryByA3(
		HttpPrincipal httpPrincipal, long companyId, String a3) {

		try {
			MethodKey methodKey = new MethodKey(
				CountryServiceUtil.class, "fetchCountryByA3",
				_fetchCountryByA3ParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, a3);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.model.Country)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Country fetchCountryByA3(
		HttpPrincipal httpPrincipal, String a3) {

		try {
			MethodKey methodKey = new MethodKey(
				CountryServiceUtil.class, "fetchCountryByA3",
				_fetchCountryByA3ParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey, a3);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.model.Country)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Country>
		getCompanyCountries(HttpPrincipal httpPrincipal, long companyId) {

		try {
			MethodKey methodKey = new MethodKey(
				CountryServiceUtil.class, "getCompanyCountries",
				_getCompanyCountriesParameterTypes8);

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

			return (java.util.List<com.liferay.portal.kernel.model.Country>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Country>
		getCompanyCountries(
			HttpPrincipal httpPrincipal, long companyId, boolean active) {

		try {
			MethodKey methodKey = new MethodKey(
				CountryServiceUtil.class, "getCompanyCountries",
				_getCompanyCountriesParameterTypes9);

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

			return (java.util.List<com.liferay.portal.kernel.model.Country>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Country>
		getCompanyCountries(
			HttpPrincipal httpPrincipal, long companyId, boolean active,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.Country> orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				CountryServiceUtil.class, "getCompanyCountries",
				_getCompanyCountriesParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, active, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.portal.kernel.model.Country>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Country>
		getCompanyCountries(
			HttpPrincipal httpPrincipal, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.Country> orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				CountryServiceUtil.class, "getCompanyCountries",
				_getCompanyCountriesParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.portal.kernel.model.Country>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCompanyCountriesCount(
		HttpPrincipal httpPrincipal, long companyId) {

		try {
			MethodKey methodKey = new MethodKey(
				CountryServiceUtil.class, "getCompanyCountriesCount",
				_getCompanyCountriesCountParameterTypes12);

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

	public static int getCompanyCountriesCount(
		HttpPrincipal httpPrincipal, long companyId, boolean active) {

		try {
			MethodKey methodKey = new MethodKey(
				CountryServiceUtil.class, "getCompanyCountriesCount",
				_getCompanyCountriesCountParameterTypes13);

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

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Country>
		getCountries(HttpPrincipal httpPrincipal) {

		try {
			MethodKey methodKey = new MethodKey(
				CountryServiceUtil.class, "getCountries",
				_getCountriesParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(methodKey);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.portal.kernel.model.Country>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Country>
		getCountries(HttpPrincipal httpPrincipal, boolean active) {

		try {
			MethodKey methodKey = new MethodKey(
				CountryServiceUtil.class, "getCountries",
				_getCountriesParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(methodKey, active);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.portal.kernel.model.Country>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Country getCountry(
			HttpPrincipal httpPrincipal, long countryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CountryServiceUtil.class, "getCountry",
				_getCountryParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, countryId);

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

			return (com.liferay.portal.kernel.model.Country)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Country getCountryByA2(
			HttpPrincipal httpPrincipal, long companyId, String a2)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CountryServiceUtil.class, "getCountryByA2",
				_getCountryByA2ParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, a2);

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

			return (com.liferay.portal.kernel.model.Country)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Country getCountryByA2(
			HttpPrincipal httpPrincipal, String a2)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CountryServiceUtil.class, "getCountryByA2",
				_getCountryByA2ParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(methodKey, a2);

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

			return (com.liferay.portal.kernel.model.Country)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Country getCountryByA3(
			HttpPrincipal httpPrincipal, long companyId, String a3)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CountryServiceUtil.class, "getCountryByA3",
				_getCountryByA3ParameterTypes19);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, a3);

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

			return (com.liferay.portal.kernel.model.Country)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Country getCountryByA3(
			HttpPrincipal httpPrincipal, String a3)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CountryServiceUtil.class, "getCountryByA3",
				_getCountryByA3ParameterTypes20);

			MethodHandler methodHandler = new MethodHandler(methodKey, a3);

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

			return (com.liferay.portal.kernel.model.Country)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Country getCountryByName(
			HttpPrincipal httpPrincipal, long companyId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CountryServiceUtil.class, "getCountryByName",
				_getCountryByNameParameterTypes21);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, name);

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

			return (com.liferay.portal.kernel.model.Country)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Country getCountryByName(
			HttpPrincipal httpPrincipal, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CountryServiceUtil.class, "getCountryByName",
				_getCountryByNameParameterTypes22);

			MethodHandler methodHandler = new MethodHandler(methodKey, name);

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

			return (com.liferay.portal.kernel.model.Country)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Country getCountryByNumber(
			HttpPrincipal httpPrincipal, long companyId, String number)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CountryServiceUtil.class, "getCountryByNumber",
				_getCountryByNumberParameterTypes23);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, number);

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

			return (com.liferay.portal.kernel.model.Country)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.portal.kernel.model.Country> searchCountries(
				HttpPrincipal httpPrincipal, long companyId, Boolean active,
				String keywords, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.kernel.model.Country> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CountryServiceUtil.class, "searchCountries",
				_searchCountriesParameterTypes24);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, active, keywords, start, end,
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

			return (com.liferay.portal.kernel.search.BaseModelSearchResult
				<com.liferay.portal.kernel.model.Country>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Country updateActive(
			HttpPrincipal httpPrincipal, long countryId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CountryServiceUtil.class, "updateActive",
				_updateActiveParameterTypes25);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, countryId, active);

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

			return (com.liferay.portal.kernel.model.Country)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Country updateCountry(
			HttpPrincipal httpPrincipal, long countryId, String a2, String a3,
			boolean active, boolean billingAllowed, String idd, String name,
			String number, double position, boolean shippingAllowed,
			boolean subjectToVAT)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CountryServiceUtil.class, "updateCountry",
				_updateCountryParameterTypes26);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, countryId, a2, a3, active, billingAllowed, idd, name,
				number, position, shippingAllowed, subjectToVAT);

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

			return (com.liferay.portal.kernel.model.Country)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Country
			updateGroupFilterEnabled(
				HttpPrincipal httpPrincipal, long countryId,
				boolean groupFilterEnabled)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CountryServiceUtil.class, "updateGroupFilterEnabled",
				_updateGroupFilterEnabledParameterTypes27);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, countryId, groupFilterEnabled);

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

			return (com.liferay.portal.kernel.model.Country)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CountryServiceHttp.class);

	private static final Class<?>[] _addCountryParameterTypes0 = new Class[] {
		String.class, String.class, boolean.class, boolean.class, String.class,
		String.class, String.class, double.class, boolean.class, boolean.class,
		boolean.class, com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _addCountryParameterTypes1 = new Class[] {
		String.class, String.class, String.class, String.class, String.class,
		boolean.class
	};
	private static final Class<?>[] _deleteCountryParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[] _fetchCountryParameterTypes3 = new Class[] {
		long.class
	};
	private static final Class<?>[] _fetchCountryByA2ParameterTypes4 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _fetchCountryByA2ParameterTypes5 =
		new Class[] {String.class};
	private static final Class<?>[] _fetchCountryByA3ParameterTypes6 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _fetchCountryByA3ParameterTypes7 =
		new Class[] {String.class};
	private static final Class<?>[] _getCompanyCountriesParameterTypes8 =
		new Class[] {long.class};
	private static final Class<?>[] _getCompanyCountriesParameterTypes9 =
		new Class[] {long.class, boolean.class};
	private static final Class<?>[] _getCompanyCountriesParameterTypes10 =
		new Class[] {
			long.class, boolean.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCompanyCountriesParameterTypes11 =
		new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCompanyCountriesCountParameterTypes12 =
		new Class[] {long.class};
	private static final Class<?>[] _getCompanyCountriesCountParameterTypes13 =
		new Class[] {long.class, boolean.class};
	private static final Class<?>[] _getCountriesParameterTypes14 =
		new Class[] {};
	private static final Class<?>[] _getCountriesParameterTypes15 =
		new Class[] {boolean.class};
	private static final Class<?>[] _getCountryParameterTypes16 = new Class[] {
		long.class
	};
	private static final Class<?>[] _getCountryByA2ParameterTypes17 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _getCountryByA2ParameterTypes18 =
		new Class[] {String.class};
	private static final Class<?>[] _getCountryByA3ParameterTypes19 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _getCountryByA3ParameterTypes20 =
		new Class[] {String.class};
	private static final Class<?>[] _getCountryByNameParameterTypes21 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _getCountryByNameParameterTypes22 =
		new Class[] {String.class};
	private static final Class<?>[] _getCountryByNumberParameterTypes23 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _searchCountriesParameterTypes24 =
		new Class[] {
			long.class, Boolean.class, String.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _updateActiveParameterTypes25 =
		new Class[] {long.class, boolean.class};
	private static final Class<?>[] _updateCountryParameterTypes26 =
		new Class[] {
			long.class, String.class, String.class, boolean.class,
			boolean.class, String.class, String.class, String.class,
			double.class, boolean.class, boolean.class
		};
	private static final Class<?>[] _updateGroupFilterEnabledParameterTypes27 =
		new Class[] {long.class, boolean.class};

}