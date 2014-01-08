/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.http.TunnelUtil;

import com.liferay.portlet.asset.service.AssetCategoryPropertyServiceUtil;

/**
 * Provides the HTTP utility for the
 * {@link com.liferay.portlet.asset.service.AssetCategoryPropertyServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link com.liferay.portal.security.auth.HttpPrincipal} parameter.
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
 * @see AssetCategoryPropertyServiceSoap
 * @see com.liferay.portal.security.auth.HttpPrincipal
 * @see com.liferay.portlet.asset.service.AssetCategoryPropertyServiceUtil
 * @generated
 */
public class AssetCategoryPropertyServiceHttp {
	public static com.liferay.portlet.asset.model.AssetCategoryProperty addCategoryProperty(
		HttpPrincipal httpPrincipal, long entryId, java.lang.String key,
		java.lang.String value)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(AssetCategoryPropertyServiceUtil.class,
					"addCategoryProperty", _addCategoryPropertyParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, entryId,
					key, value);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.asset.model.AssetCategoryProperty)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCategoryProperty(HttpPrincipal httpPrincipal,
		long categoryPropertyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(AssetCategoryPropertyServiceUtil.class,
					"deleteCategoryProperty",
					_deleteCategoryPropertyParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					categoryPropertyId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> getCategoryProperties(
		HttpPrincipal httpPrincipal, long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(AssetCategoryPropertyServiceUtil.class,
					"getCategoryProperties",
					_getCategoryPropertiesParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey, entryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> getCategoryPropertyValues(
		HttpPrincipal httpPrincipal, long companyId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(AssetCategoryPropertyServiceUtil.class,
					"getCategoryPropertyValues",
					_getCategoryPropertyValuesParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, key);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.asset.model.AssetCategoryProperty updateCategoryProperty(
		HttpPrincipal httpPrincipal, long categoryPropertyId,
		java.lang.String key, java.lang.String value)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(AssetCategoryPropertyServiceUtil.class,
					"updateCategoryProperty",
					_updateCategoryPropertyParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					categoryPropertyId, key, value);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.asset.model.AssetCategoryProperty)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.asset.model.AssetCategoryProperty updateCategoryProperty(
		HttpPrincipal httpPrincipal, long userId, long categoryPropertyId,
		java.lang.String key, java.lang.String value)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(AssetCategoryPropertyServiceUtil.class,
					"updateCategoryProperty",
					_updateCategoryPropertyParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId,
					categoryPropertyId, key, value);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.asset.model.AssetCategoryProperty)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(AssetCategoryPropertyServiceHttp.class);
	private static final Class<?>[] _addCategoryPropertyParameterTypes0 = new Class[] {
			long.class, java.lang.String.class, java.lang.String.class
		};
	private static final Class<?>[] _deleteCategoryPropertyParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCategoryPropertiesParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCategoryPropertyValuesParameterTypes3 = new Class[] {
			long.class, java.lang.String.class
		};
	private static final Class<?>[] _updateCategoryPropertyParameterTypes4 = new Class[] {
			long.class, java.lang.String.class, java.lang.String.class
		};
	private static final Class<?>[] _updateCategoryPropertyParameterTypes5 = new Class[] {
			long.class, long.class, java.lang.String.class,
			java.lang.String.class
		};
}