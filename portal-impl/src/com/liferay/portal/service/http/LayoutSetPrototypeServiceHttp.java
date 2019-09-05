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
import com.liferay.portal.kernel.service.LayoutSetPrototypeServiceUtil;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>LayoutSetPrototypeServiceUtil</code> service
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
 * @see LayoutSetPrototypeServiceSoap
 * @generated
 */
public class LayoutSetPrototypeServiceHttp {

	public static com.liferay.portal.kernel.model.LayoutSetPrototype
			addLayoutSetPrototype(
				HttpPrincipal httpPrincipal,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				boolean active, boolean layoutsUpdateable,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutSetPrototypeServiceUtil.class, "addLayoutSetPrototype",
				_addLayoutSetPrototypeParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, nameMap, descriptionMap, active, layoutsUpdateable,
				serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.model.LayoutSetPrototype)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteLayoutSetPrototype(
			HttpPrincipal httpPrincipal, long layoutSetPrototypeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutSetPrototypeServiceUtil.class, "deleteLayoutSetPrototype",
				_deleteLayoutSetPrototypeParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, layoutSetPrototypeId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.model.LayoutSetPrototype
			fetchLayoutSetPrototype(
				HttpPrincipal httpPrincipal, long layoutSetPrototypeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutSetPrototypeServiceUtil.class, "fetchLayoutSetPrototype",
				_fetchLayoutSetPrototypeParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, layoutSetPrototypeId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.model.LayoutSetPrototype)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.model.LayoutSetPrototype
			getLayoutSetPrototype(
				HttpPrincipal httpPrincipal, long layoutSetPrototypeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutSetPrototypeServiceUtil.class, "getLayoutSetPrototype",
				_getLayoutSetPrototypeParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, layoutSetPrototypeId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.model.LayoutSetPrototype)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.portal.kernel.model.LayoutSetPrototype> search(
				HttpPrincipal httpPrincipal, long companyId, Boolean active,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.kernel.model.LayoutSetPrototype> obc)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutSetPrototypeServiceUtil.class, "search",
				_searchParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, active, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.portal.kernel.model.LayoutSetPrototype>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.model.LayoutSetPrototype
			updateLayoutSetPrototype(
				HttpPrincipal httpPrincipal, long layoutSetPrototypeId,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				boolean active, boolean layoutsUpdateable,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutSetPrototypeServiceUtil.class, "updateLayoutSetPrototype",
				_updateLayoutSetPrototypeParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, layoutSetPrototypeId, nameMap, descriptionMap,
				active, layoutsUpdateable, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.model.LayoutSetPrototype)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.model.LayoutSetPrototype
			updateLayoutSetPrototype(
				HttpPrincipal httpPrincipal, long layoutSetPrototypeId,
				String settings)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutSetPrototypeServiceUtil.class, "updateLayoutSetPrototype",
				_updateLayoutSetPrototypeParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, layoutSetPrototypeId, settings);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.model.LayoutSetPrototype)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		LayoutSetPrototypeServiceHttp.class);

	private static final Class<?>[] _addLayoutSetPrototypeParameterTypes0 =
		new Class[] {
			java.util.Map.class, java.util.Map.class, boolean.class,
			boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteLayoutSetPrototypeParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _fetchLayoutSetPrototypeParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[] _getLayoutSetPrototypeParameterTypes3 =
		new Class[] {long.class};
	private static final Class<?>[] _searchParameterTypes4 = new Class[] {
		long.class, Boolean.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _updateLayoutSetPrototypeParameterTypes5 =
		new Class[] {
			long.class, java.util.Map.class, java.util.Map.class, boolean.class,
			boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateLayoutSetPrototypeParameterTypes6 =
		new Class[] {long.class, String.class};

}