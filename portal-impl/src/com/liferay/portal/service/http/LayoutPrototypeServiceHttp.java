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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.LayoutPrototypeServiceUtil;
import com.liferay.portal.service.http.TunnelUtil;

/**
 * Provides the HTTP utility for the
 * {@link LayoutPrototypeServiceUtil} service utility. The
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
 * @author Brian Wing Shun Chan
 * @see LayoutPrototypeServiceSoap
 * @see HttpPrincipal
 * @see LayoutPrototypeServiceUtil
 * @generated
 */
@ProviderType
public class LayoutPrototypeServiceHttp {
	public static com.liferay.portal.model.LayoutPrototype addLayoutPrototype(
		HttpPrincipal httpPrincipal,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		boolean active, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutPrototypeServiceUtil.class,
					"addLayoutPrototype", _addLayoutPrototypeParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, nameMap,
					descriptionMap, active, serviceContext);

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

			return (com.liferay.portal.model.LayoutPrototype)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.model.LayoutPrototype addLayoutPrototype(
		HttpPrincipal httpPrincipal,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.lang.String description, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutPrototypeServiceUtil.class,
					"addLayoutPrototype", _addLayoutPrototypeParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey, nameMap,
					description, active);

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

			return (com.liferay.portal.model.LayoutPrototype)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.model.LayoutPrototype addLayoutPrototype(
		HttpPrincipal httpPrincipal,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.lang.String description, boolean active,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutPrototypeServiceUtil.class,
					"addLayoutPrototype", _addLayoutPrototypeParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey, nameMap,
					description, active, serviceContext);

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

			return (com.liferay.portal.model.LayoutPrototype)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteLayoutPrototype(HttpPrincipal httpPrincipal,
		long layoutPrototypeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutPrototypeServiceUtil.class,
					"deleteLayoutPrototype",
					_deleteLayoutPrototypeParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					layoutPrototypeId);

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

	public static com.liferay.portal.model.LayoutPrototype fetchLayoutPrototype(
		HttpPrincipal httpPrincipal, long layoutPrototypeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutPrototypeServiceUtil.class,
					"fetchLayoutPrototype", _fetchLayoutPrototypeParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					layoutPrototypeId);

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

			return (com.liferay.portal.model.LayoutPrototype)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.model.LayoutPrototype getLayoutPrototype(
		HttpPrincipal httpPrincipal, long layoutPrototypeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutPrototypeServiceUtil.class,
					"getLayoutPrototype", _getLayoutPrototypeParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					layoutPrototypeId);

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

			return (com.liferay.portal.model.LayoutPrototype)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.model.LayoutPrototype> search(
		HttpPrincipal httpPrincipal, long companyId, java.lang.Boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.LayoutPrototype> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutPrototypeServiceUtil.class,
					"search", _searchParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, active, obc);

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

			return (java.util.List<com.liferay.portal.model.LayoutPrototype>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.model.LayoutPrototype updateLayoutPrototype(
		HttpPrincipal httpPrincipal, long layoutPrototypeId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		boolean active, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutPrototypeServiceUtil.class,
					"updateLayoutPrototype",
					_updateLayoutPrototypeParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					layoutPrototypeId, nameMap, descriptionMap, active,
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

			return (com.liferay.portal.model.LayoutPrototype)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.model.LayoutPrototype updateLayoutPrototype(
		HttpPrincipal httpPrincipal, long layoutPrototypeId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.lang.String description, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutPrototypeServiceUtil.class,
					"updateLayoutPrototype",
					_updateLayoutPrototypeParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					layoutPrototypeId, nameMap, description, active);

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

			return (com.liferay.portal.model.LayoutPrototype)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.model.LayoutPrototype updateLayoutPrototype(
		HttpPrincipal httpPrincipal, long layoutPrototypeId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.lang.String description, boolean active,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutPrototypeServiceUtil.class,
					"updateLayoutPrototype",
					_updateLayoutPrototypeParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					layoutPrototypeId, nameMap, description, active,
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

			return (com.liferay.portal.model.LayoutPrototype)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(LayoutPrototypeServiceHttp.class);
	private static final Class<?>[] _addLayoutPrototypeParameterTypes0 = new Class[] {
			java.util.Map.class, java.util.Map.class, boolean.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _addLayoutPrototypeParameterTypes1 = new Class[] {
			java.util.Map.class, java.lang.String.class, boolean.class
		};
	private static final Class<?>[] _addLayoutPrototypeParameterTypes2 = new Class[] {
			java.util.Map.class, java.lang.String.class, boolean.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteLayoutPrototypeParameterTypes3 = new Class[] {
			long.class
		};
	private static final Class<?>[] _fetchLayoutPrototypeParameterTypes4 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getLayoutPrototypeParameterTypes5 = new Class[] {
			long.class
		};
	private static final Class<?>[] _searchParameterTypes6 = new Class[] {
			long.class, java.lang.Boolean.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _updateLayoutPrototypeParameterTypes7 = new Class[] {
			long.class, java.util.Map.class, java.util.Map.class, boolean.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _updateLayoutPrototypeParameterTypes8 = new Class[] {
			long.class, java.util.Map.class, java.lang.String.class,
			boolean.class
		};
	private static final Class<?>[] _updateLayoutPrototypeParameterTypes9 = new Class[] {
			long.class, java.util.Map.class, java.lang.String.class,
			boolean.class, com.liferay.portal.service.ServiceContext.class
		};
}