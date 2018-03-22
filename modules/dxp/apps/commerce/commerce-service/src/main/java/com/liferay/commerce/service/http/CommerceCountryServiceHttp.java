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

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.service.CommerceCountryServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CommerceCountryServiceUtil} service utility. The
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
 * @see CommerceCountryServiceSoap
 * @see HttpPrincipal
 * @see CommerceCountryServiceUtil
 * @generated
 */
@ProviderType
public class CommerceCountryServiceHttp {
	public static com.liferay.commerce.model.CommerceCountry addCommerceCountry(
		HttpPrincipal httpPrincipal,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		boolean billingAllowed, boolean shippingAllowed,
		java.lang.String twoLettersISOCode,
		java.lang.String threeLettersISOCode, int numericISOCode,
		boolean subjectToVAT, double priority, boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceCountryServiceUtil.class,
					"addCommerceCountry", _addCommerceCountryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, nameMap,
					billingAllowed, shippingAllowed, twoLettersISOCode,
					threeLettersISOCode, numericISOCode, subjectToVAT,
					priority, active, serviceContext);

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

			return (com.liferay.commerce.model.CommerceCountry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCommerceCountry(HttpPrincipal httpPrincipal,
		long commerceCountryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceCountryServiceUtil.class,
					"deleteCommerceCountry",
					_deleteCommerceCountryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceCountryId);

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

	public static java.util.List<com.liferay.commerce.model.CommerceCountry> getBillingCommerceCountries(
		HttpPrincipal httpPrincipal, long groupId, boolean billingAllowed,
		boolean active) {
		try {
			MethodKey methodKey = new MethodKey(CommerceCountryServiceUtil.class,
					"getBillingCommerceCountries",
					_getBillingCommerceCountriesParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					billingAllowed, active);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.model.CommerceCountry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommerceCountry> getCommerceCountries(
		HttpPrincipal httpPrincipal, long groupId, boolean active) {
		try {
			MethodKey methodKey = new MethodKey(CommerceCountryServiceUtil.class,
					"getCommerceCountries", _getCommerceCountriesParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					active);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.model.CommerceCountry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommerceCountry> getCommerceCountries(
		HttpPrincipal httpPrincipal, long groupId, boolean active, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceCountry> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(CommerceCountryServiceUtil.class,
					"getCommerceCountries", _getCommerceCountriesParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					active, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.model.CommerceCountry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommerceCountry> getCommerceCountries(
		HttpPrincipal httpPrincipal, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceCountry> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(CommerceCountryServiceUtil.class,
					"getCommerceCountries", _getCommerceCountriesParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.model.CommerceCountry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCommerceCountriesCount(HttpPrincipal httpPrincipal,
		long groupId) {
		try {
			MethodKey methodKey = new MethodKey(CommerceCountryServiceUtil.class,
					"getCommerceCountriesCount",
					_getCommerceCountriesCountParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCommerceCountriesCount(HttpPrincipal httpPrincipal,
		long groupId, boolean active) {
		try {
			MethodKey methodKey = new MethodKey(CommerceCountryServiceUtil.class,
					"getCommerceCountriesCount",
					_getCommerceCountriesCountParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					active);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.model.CommerceCountry getCommerceCountry(
		HttpPrincipal httpPrincipal, long commerceCountryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceCountryServiceUtil.class,
					"getCommerceCountry", _getCommerceCountryParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceCountryId);

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

			return (com.liferay.commerce.model.CommerceCountry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommerceCountry> getShippingCommerceCountries(
		HttpPrincipal httpPrincipal, long groupId, boolean shippingAllowed,
		boolean active) {
		try {
			MethodKey methodKey = new MethodKey(CommerceCountryServiceUtil.class,
					"getShippingCommerceCountries",
					_getShippingCommerceCountriesParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					shippingAllowed, active);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.model.CommerceCountry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommerceCountry> getWarehouseCommerceCountries(
		HttpPrincipal httpPrincipal, long groupId, boolean all)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceCountryServiceUtil.class,
					"getWarehouseCommerceCountries",
					_getWarehouseCommerceCountriesParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					all);

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

			return (java.util.List<com.liferay.commerce.model.CommerceCountry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.model.CommerceCountry> searchCommerceCountries(
		HttpPrincipal httpPrincipal,
		com.liferay.portal.kernel.search.SearchContext searchContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceCountryServiceUtil.class,
					"searchCommerceCountries",
					_searchCommerceCountriesParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					searchContext);

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

			return (com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.model.CommerceCountry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.model.CommerceCountry updateCommerceCountry(
		HttpPrincipal httpPrincipal, long commerceCountryId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		boolean billingAllowed, boolean shippingAllowed,
		java.lang.String twoLettersISOCode,
		java.lang.String threeLettersISOCode, int numericISOCode,
		boolean subjectToVAT, double priority, boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceCountryServiceUtil.class,
					"updateCommerceCountry",
					_updateCommerceCountryParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceCountryId, nameMap, billingAllowed,
					shippingAllowed, twoLettersISOCode, threeLettersISOCode,
					numericISOCode, subjectToVAT, priority, active,
					serviceContext);

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

			return (com.liferay.commerce.model.CommerceCountry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceCountryServiceHttp.class);
	private static final Class<?>[] _addCommerceCountryParameterTypes0 = new Class[] {
			java.util.Map.class, boolean.class, boolean.class,
			java.lang.String.class, java.lang.String.class, int.class,
			boolean.class, double.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCommerceCountryParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getBillingCommerceCountriesParameterTypes2 = new Class[] {
			long.class, boolean.class, boolean.class
		};
	private static final Class<?>[] _getCommerceCountriesParameterTypes3 = new Class[] {
			long.class, boolean.class
		};
	private static final Class<?>[] _getCommerceCountriesParameterTypes4 = new Class[] {
			long.class, boolean.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCommerceCountriesParameterTypes5 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCommerceCountriesCountParameterTypes6 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCommerceCountriesCountParameterTypes7 = new Class[] {
			long.class, boolean.class
		};
	private static final Class<?>[] _getCommerceCountryParameterTypes8 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getShippingCommerceCountriesParameterTypes9 =
		new Class[] { long.class, boolean.class, boolean.class };
	private static final Class<?>[] _getWarehouseCommerceCountriesParameterTypes10 =
		new Class[] { long.class, boolean.class };
	private static final Class<?>[] _searchCommerceCountriesParameterTypes11 = new Class[] {
			com.liferay.portal.kernel.search.SearchContext.class
		};
	private static final Class<?>[] _updateCommerceCountryParameterTypes12 = new Class[] {
			long.class, java.util.Map.class, boolean.class, boolean.class,
			java.lang.String.class, java.lang.String.class, int.class,
			boolean.class, double.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
}