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
import com.liferay.portal.kernel.service.RegionServiceUtil;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>RegionServiceUtil</code> service
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
 * @generated
 */
public class RegionServiceHttp {

	public static com.liferay.portal.kernel.model.Region addRegion(
			HttpPrincipal httpPrincipal, long countryId, boolean active,
			String name, double position, String regionCode,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RegionServiceUtil.class, "addRegion",
				_addRegionParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, countryId, active, name, position, regionCode,
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

			return (com.liferay.portal.kernel.model.Region)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteRegion(HttpPrincipal httpPrincipal, long regionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RegionServiceUtil.class, "deleteRegion",
				_deleteRegionParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, regionId);

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

	public static com.liferay.portal.kernel.model.Region fetchRegion(
		HttpPrincipal httpPrincipal, long regionId) {

		try {
			MethodKey methodKey = new MethodKey(
				RegionServiceUtil.class, "fetchRegion",
				_fetchRegionParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, regionId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.model.Region)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Region fetchRegion(
		HttpPrincipal httpPrincipal, long countryId, String regionCode) {

		try {
			MethodKey methodKey = new MethodKey(
				RegionServiceUtil.class, "fetchRegion",
				_fetchRegionParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, countryId, regionCode);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.model.Region)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Region getRegion(
			HttpPrincipal httpPrincipal, long regionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RegionServiceUtil.class, "getRegion",
				_getRegionParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, regionId);

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

			return (com.liferay.portal.kernel.model.Region)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Region getRegion(
			HttpPrincipal httpPrincipal, long countryId, String regionCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RegionServiceUtil.class, "getRegion",
				_getRegionParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, countryId, regionCode);

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

			return (com.liferay.portal.kernel.model.Region)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Region>
		getRegions(HttpPrincipal httpPrincipal) {

		try {
			MethodKey methodKey = new MethodKey(
				RegionServiceUtil.class, "getRegions",
				_getRegionsParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.portal.kernel.model.Region>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Region>
		getRegions(HttpPrincipal httpPrincipal, boolean active) {

		try {
			MethodKey methodKey = new MethodKey(
				RegionServiceUtil.class, "getRegions",
				_getRegionsParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey, active);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.portal.kernel.model.Region>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Region>
		getRegions(HttpPrincipal httpPrincipal, long countryId) {

		try {
			MethodKey methodKey = new MethodKey(
				RegionServiceUtil.class, "getRegions",
				_getRegionsParameterTypes8);

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

			return (java.util.List<com.liferay.portal.kernel.model.Region>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Region>
		getRegions(
			HttpPrincipal httpPrincipal, long countryId, boolean active) {

		try {
			MethodKey methodKey = new MethodKey(
				RegionServiceUtil.class, "getRegions",
				_getRegionsParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, countryId, active);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.portal.kernel.model.Region>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Region>
		getRegions(
			HttpPrincipal httpPrincipal, long countryId, boolean active,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.Region> orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				RegionServiceUtil.class, "getRegions",
				_getRegionsParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, countryId, active, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.portal.kernel.model.Region>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Region>
		getRegions(
			HttpPrincipal httpPrincipal, long countryId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.Region> orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				RegionServiceUtil.class, "getRegions",
				_getRegionsParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, countryId, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.portal.kernel.model.Region>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Region>
			getRegions(
				HttpPrincipal httpPrincipal, long companyId, String a2,
				boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RegionServiceUtil.class, "getRegions",
				_getRegionsParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, a2, active);

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

			return (java.util.List<com.liferay.portal.kernel.model.Region>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getRegionsCount(
		HttpPrincipal httpPrincipal, long countryId) {

		try {
			MethodKey methodKey = new MethodKey(
				RegionServiceUtil.class, "getRegionsCount",
				_getRegionsCountParameterTypes13);

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

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getRegionsCount(
		HttpPrincipal httpPrincipal, long countryId, boolean active) {

		try {
			MethodKey methodKey = new MethodKey(
				RegionServiceUtil.class, "getRegionsCount",
				_getRegionsCountParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, countryId, active);

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

	public static com.liferay.portal.kernel.model.Region updateActive(
			HttpPrincipal httpPrincipal, long regionId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RegionServiceUtil.class, "updateActive",
				_updateActiveParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, regionId, active);

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

			return (com.liferay.portal.kernel.model.Region)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Region updateRegion(
			HttpPrincipal httpPrincipal, long regionId, boolean active,
			String name, double position, String regionCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RegionServiceUtil.class, "updateRegion",
				_updateRegionParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, regionId, active, name, position, regionCode);

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

			return (com.liferay.portal.kernel.model.Region)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(RegionServiceHttp.class);

	private static final Class<?>[] _addRegionParameterTypes0 = new Class[] {
		long.class, boolean.class, String.class, double.class, String.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _deleteRegionParameterTypes1 = new Class[] {
		long.class
	};
	private static final Class<?>[] _fetchRegionParameterTypes2 = new Class[] {
		long.class
	};
	private static final Class<?>[] _fetchRegionParameterTypes3 = new Class[] {
		long.class, String.class
	};
	private static final Class<?>[] _getRegionParameterTypes4 = new Class[] {
		long.class
	};
	private static final Class<?>[] _getRegionParameterTypes5 = new Class[] {
		long.class, String.class
	};
	private static final Class<?>[] _getRegionsParameterTypes6 = new Class[] {};
	private static final Class<?>[] _getRegionsParameterTypes7 = new Class[] {
		boolean.class
	};
	private static final Class<?>[] _getRegionsParameterTypes8 = new Class[] {
		long.class
	};
	private static final Class<?>[] _getRegionsParameterTypes9 = new Class[] {
		long.class, boolean.class
	};
	private static final Class<?>[] _getRegionsParameterTypes10 = new Class[] {
		long.class, boolean.class, int.class, int.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _getRegionsParameterTypes11 = new Class[] {
		long.class, int.class, int.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _getRegionsParameterTypes12 = new Class[] {
		long.class, String.class, boolean.class
	};
	private static final Class<?>[] _getRegionsCountParameterTypes13 =
		new Class[] {long.class};
	private static final Class<?>[] _getRegionsCountParameterTypes14 =
		new Class[] {long.class, boolean.class};
	private static final Class<?>[] _updateActiveParameterTypes15 =
		new Class[] {long.class, boolean.class};
	private static final Class<?>[] _updateRegionParameterTypes16 =
		new Class[] {
			long.class, boolean.class, String.class, double.class, String.class
		};

}