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

package com.liferay.commerce.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.service.CommerceRegionServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CommerceRegionServiceUtil} service utility. The
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
 * @see CommerceRegionServiceSoap
 * @see HttpPrincipal
 * @see CommerceRegionServiceUtil
 * @generated
 */
@ProviderType
public class CommerceRegionServiceHttp {
	public static com.liferay.commerce.model.CommerceRegion addCommerceRegion(
		HttpPrincipal httpPrincipal, long commerceCountryId, String name,
		String code, double priority, boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceRegionServiceUtil.class,
					"addCommerceRegion", _addCommerceRegionParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceCountryId, name, code, priority, active,
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

			return (com.liferay.commerce.model.CommerceRegion)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCommerceRegion(HttpPrincipal httpPrincipal,
		long commerceRegionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceRegionServiceUtil.class,
					"deleteCommerceRegion", _deleteCommerceRegionParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceRegionId);

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

	public static com.liferay.commerce.model.CommerceRegion getCommerceRegion(
		HttpPrincipal httpPrincipal, long commerceRegionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceRegionServiceUtil.class,
					"getCommerceRegion", _getCommerceRegionParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceRegionId);

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

			return (com.liferay.commerce.model.CommerceRegion)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommerceRegion> getCommerceRegions(
		HttpPrincipal httpPrincipal, long commerceCountryId, boolean active) {
		try {
			MethodKey methodKey = new MethodKey(CommerceRegionServiceUtil.class,
					"getCommerceRegions", _getCommerceRegionsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceCountryId, active);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.model.CommerceRegion>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommerceRegion> getCommerceRegions(
		HttpPrincipal httpPrincipal, long commerceCountryId, boolean active,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceRegion> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(CommerceRegionServiceUtil.class,
					"getCommerceRegions", _getCommerceRegionsParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceCountryId, active, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.model.CommerceRegion>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommerceRegion> getCommerceRegions(
		HttpPrincipal httpPrincipal, long commerceCountryId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceRegion> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(CommerceRegionServiceUtil.class,
					"getCommerceRegions", _getCommerceRegionsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceCountryId, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.model.CommerceRegion>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCommerceRegionsCount(HttpPrincipal httpPrincipal,
		long commerceCountryId) {
		try {
			MethodKey methodKey = new MethodKey(CommerceRegionServiceUtil.class,
					"getCommerceRegionsCount",
					_getCommerceRegionsCountParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey,
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

	public static int getCommerceRegionsCount(HttpPrincipal httpPrincipal,
		long commerceCountryId, boolean active) {
		try {
			MethodKey methodKey = new MethodKey(CommerceRegionServiceUtil.class,
					"getCommerceRegionsCount",
					_getCommerceRegionsCountParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceCountryId, active);

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

	public static com.liferay.commerce.model.CommerceRegion setActive(
		HttpPrincipal httpPrincipal, long commerceRegionId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceRegionServiceUtil.class,
					"setActive", _setActiveParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceRegionId, active);

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

			return (com.liferay.commerce.model.CommerceRegion)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.model.CommerceRegion updateCommerceRegion(
		HttpPrincipal httpPrincipal, long commerceRegionId, String name,
		String code, double priority, boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceRegionServiceUtil.class,
					"updateCommerceRegion", _updateCommerceRegionParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceRegionId, name, code, priority, active,
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

			return (com.liferay.commerce.model.CommerceRegion)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceRegionServiceHttp.class);
	private static final Class<?>[] _addCommerceRegionParameterTypes0 = new Class[] {
			long.class, String.class, String.class, double.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCommerceRegionParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCommerceRegionParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCommerceRegionsParameterTypes3 = new Class[] {
			long.class, boolean.class
		};
	private static final Class<?>[] _getCommerceRegionsParameterTypes4 = new Class[] {
			long.class, boolean.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCommerceRegionsParameterTypes5 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCommerceRegionsCountParameterTypes6 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCommerceRegionsCountParameterTypes7 = new Class[] {
			long.class, boolean.class
		};
	private static final Class<?>[] _setActiveParameterTypes8 = new Class[] {
			long.class, boolean.class
		};
	private static final Class<?>[] _updateCommerceRegionParameterTypes9 = new Class[] {
			long.class, String.class, String.class, double.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
}