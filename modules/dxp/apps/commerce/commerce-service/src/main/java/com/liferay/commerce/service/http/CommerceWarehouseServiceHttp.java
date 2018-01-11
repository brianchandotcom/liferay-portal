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

import com.liferay.commerce.service.CommerceWarehouseServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CommerceWarehouseServiceUtil} service utility. The
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
 * @see CommerceWarehouseServiceSoap
 * @see HttpPrincipal
 * @see CommerceWarehouseServiceUtil
 * @generated
 */
@ProviderType
public class CommerceWarehouseServiceHttp {
	public static com.liferay.commerce.model.CommerceWarehouse addCommerceWarehouse(
		HttpPrincipal httpPrincipal, java.lang.String name,
		java.lang.String description, boolean active, java.lang.String street1,
		java.lang.String street2, java.lang.String street3,
		java.lang.String city, java.lang.String zip, long commerceRegionId,
		long commerceCountryId, double latitude, double longitude,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceWarehouseServiceUtil.class,
					"addCommerceWarehouse", _addCommerceWarehouseParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, name,
					description, active, street1, street2, street3, city, zip,
					commerceRegionId, commerceCountryId, latitude, longitude,
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

			return (com.liferay.commerce.model.CommerceWarehouse)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCommerceWarehouse(HttpPrincipal httpPrincipal,
		long commerceWarehouseId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceWarehouseServiceUtil.class,
					"deleteCommerceWarehouse",
					_deleteCommerceWarehouseParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceWarehouseId);

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

	public static com.liferay.commerce.model.CommerceWarehouse geolocateCommerceWarehouse(
		HttpPrincipal httpPrincipal, long commerceWarehouseId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceWarehouseServiceUtil.class,
					"geolocateCommerceWarehouse",
					_geolocateCommerceWarehouseParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceWarehouseId);

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

			return (com.liferay.commerce.model.CommerceWarehouse)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.model.CommerceWarehouse getCommerceWarehouse(
		HttpPrincipal httpPrincipal, long commerceWarehouseId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceWarehouseServiceUtil.class,
					"getCommerceWarehouse", _getCommerceWarehouseParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceWarehouseId);

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

			return (com.liferay.commerce.model.CommerceWarehouse)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommerceWarehouse> getCommerceWarehouses(
		HttpPrincipal httpPrincipal, long groupId, boolean active, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceWarehouse> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(CommerceWarehouseServiceUtil.class,
					"getCommerceWarehouses",
					_getCommerceWarehousesParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					active, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.model.CommerceWarehouse>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommerceWarehouse> getCommerceWarehouses(
		HttpPrincipal httpPrincipal, long groupId, boolean active,
		long commerceCountryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceWarehouse> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceWarehouseServiceUtil.class,
					"getCommerceWarehouses",
					_getCommerceWarehousesParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					active, commerceCountryId, start, end, orderByComparator);

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

			return (java.util.List<com.liferay.commerce.model.CommerceWarehouse>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommerceWarehouse> getCommerceWarehouses(
		HttpPrincipal httpPrincipal, long groupId, long commerceCountryId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceWarehouse> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(CommerceWarehouseServiceUtil.class,
					"getCommerceWarehouses",
					_getCommerceWarehousesParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					commerceCountryId, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.model.CommerceWarehouse>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCommerceWarehousesCount(HttpPrincipal httpPrincipal,
		long groupId, boolean active, long commerceCountryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceWarehouseServiceUtil.class,
					"getCommerceWarehousesCount",
					_getCommerceWarehousesCountParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					active, commerceCountryId);

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

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCommerceWarehousesCount(HttpPrincipal httpPrincipal,
		long groupId, long commerceCountryId) {
		try {
			MethodKey methodKey = new MethodKey(CommerceWarehouseServiceUtil.class,
					"getCommerceWarehousesCount",
					_getCommerceWarehousesCountParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					commerceCountryId);

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

	public static com.liferay.commerce.model.CommerceWarehouse getDefaultCommerceWarehouse(
		HttpPrincipal httpPrincipal,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceWarehouseServiceUtil.class,
					"getDefaultCommerceWarehouse",
					_getDefaultCommerceWarehouseParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey,
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

			return (com.liferay.commerce.model.CommerceWarehouse)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommerceWarehouse> search(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String keywords,
		boolean all, long commerceCountryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceWarehouse> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceWarehouseServiceUtil.class,
					"search", _searchParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					keywords, all, commerceCountryId, start, end,
					orderByComparator);

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

			return (java.util.List<com.liferay.commerce.model.CommerceWarehouse>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int searchCount(HttpPrincipal httpPrincipal, long groupId,
		java.lang.String keywords, boolean all, long commerceCountryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceWarehouseServiceUtil.class,
					"searchCount", _searchCountParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					keywords, all, commerceCountryId);

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

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.model.CommerceWarehouse setActive(
		HttpPrincipal httpPrincipal, long commerceWarehouseId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceWarehouseServiceUtil.class,
					"setActive", _setActiveParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceWarehouseId, active);

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

			return (com.liferay.commerce.model.CommerceWarehouse)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.model.CommerceWarehouse updateCommerceWarehouse(
		HttpPrincipal httpPrincipal, long commerceWarehouseId,
		java.lang.String name, java.lang.String description, boolean active,
		java.lang.String street1, java.lang.String street2,
		java.lang.String street3, java.lang.String city, java.lang.String zip,
		long commerceRegionId, long commerceCountryId, double latitude,
		double longitude,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceWarehouseServiceUtil.class,
					"updateCommerceWarehouse",
					_updateCommerceWarehouseParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceWarehouseId, name, description, active, street1,
					street2, street3, city, zip, commerceRegionId,
					commerceCountryId, latitude, longitude, serviceContext);

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

			return (com.liferay.commerce.model.CommerceWarehouse)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.model.CommerceWarehouse updateDefaultCommerceWarehouse(
		HttpPrincipal httpPrincipal, long commerceWarehouseId,
		java.lang.String name, java.lang.String street1,
		java.lang.String street2, java.lang.String street3,
		java.lang.String city, java.lang.String zip, long commerceRegionId,
		long commerceCountryId, double latitude, double longitude,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceWarehouseServiceUtil.class,
					"updateDefaultCommerceWarehouse",
					_updateDefaultCommerceWarehouseParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceWarehouseId, name, street1, street2, street3, city,
					zip, commerceRegionId, commerceCountryId, latitude,
					longitude, serviceContext);

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

			return (com.liferay.commerce.model.CommerceWarehouse)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceWarehouseServiceHttp.class);
	private static final Class<?>[] _addCommerceWarehouseParameterTypes0 = new Class[] {
			java.lang.String.class, java.lang.String.class, boolean.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, long.class, long.class, double.class,
			double.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCommerceWarehouseParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _geolocateCommerceWarehouseParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCommerceWarehouseParameterTypes3 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCommerceWarehousesParameterTypes4 = new Class[] {
			long.class, boolean.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCommerceWarehousesParameterTypes5 = new Class[] {
			long.class, boolean.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCommerceWarehousesParameterTypes6 = new Class[] {
			long.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCommerceWarehousesCountParameterTypes7 = new Class[] {
			long.class, boolean.class, long.class
		};
	private static final Class<?>[] _getCommerceWarehousesCountParameterTypes8 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _getDefaultCommerceWarehouseParameterTypes9 = new Class[] {
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _searchParameterTypes10 = new Class[] {
			long.class, java.lang.String.class, boolean.class, long.class,
			int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _searchCountParameterTypes11 = new Class[] {
			long.class, java.lang.String.class, boolean.class, long.class
		};
	private static final Class<?>[] _setActiveParameterTypes12 = new Class[] {
			long.class, boolean.class
		};
	private static final Class<?>[] _updateCommerceWarehouseParameterTypes13 = new Class[] {
			long.class, java.lang.String.class, java.lang.String.class,
			boolean.class, java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, long.class, long.class, double.class,
			double.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateDefaultCommerceWarehouseParameterTypes14 =
		new Class[] {
			long.class, java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class, long.class,
			long.class, double.class, double.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
}