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

package com.liferay.asset.display.page.service.http;

import com.liferay.asset.display.page.service.AssetDisplayPageEntryServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>AssetDisplayPageEntryServiceUtil</code> service
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
 * @see AssetDisplayPageEntryServiceSoap
 * @generated
 */
public class AssetDisplayPageEntryServiceHttp {

	public static com.liferay.asset.display.page.model.AssetDisplayPageEntry
			addAssetDisplayPageEntry(
				HttpPrincipal httpPrincipal, long userId, long groupId,
				long classNameId, long classPK, long layoutPageTemplateEntryId,
				int type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws Exception {

		try {
			MethodKey methodKey = new MethodKey(
				AssetDisplayPageEntryServiceUtil.class,
				"addAssetDisplayPageEntry",
				_addAssetDisplayPageEntryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, groupId, classNameId, classPK,
				layoutPageTemplateEntryId, type, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof Exception) {
					throw (Exception)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.asset.display.page.model.AssetDisplayPageEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.asset.display.page.model.AssetDisplayPageEntry
			addAssetDisplayPageEntry(
				HttpPrincipal httpPrincipal, long userId, long groupId,
				long classNameId, long classPK, long layoutPageTemplateEntryId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws Exception {

		try {
			MethodKey methodKey = new MethodKey(
				AssetDisplayPageEntryServiceUtil.class,
				"addAssetDisplayPageEntry",
				_addAssetDisplayPageEntryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, groupId, classNameId, classPK,
				layoutPageTemplateEntryId, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof Exception) {
					throw (Exception)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.asset.display.page.model.AssetDisplayPageEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteAssetDisplayPageEntry(
			HttpPrincipal httpPrincipal, long groupId, long classNameId,
			long classPK)
		throws Exception {

		try {
			MethodKey methodKey = new MethodKey(
				AssetDisplayPageEntryServiceUtil.class,
				"deleteAssetDisplayPageEntry",
				_deleteAssetDisplayPageEntryParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, classNameId, classPK);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof Exception) {
					throw (Exception)e;
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

	public static com.liferay.asset.display.page.model.AssetDisplayPageEntry
			fetchAssetDisplayPageEntry(
				HttpPrincipal httpPrincipal, long groupId, long classNameId,
				long classPK)
		throws Exception {

		try {
			MethodKey methodKey = new MethodKey(
				AssetDisplayPageEntryServiceUtil.class,
				"fetchAssetDisplayPageEntry",
				_fetchAssetDisplayPageEntryParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, classNameId, classPK);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof Exception) {
					throw (Exception)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.asset.display.page.model.AssetDisplayPageEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.asset.display.page.model.AssetDisplayPageEntry>
			getAssetDisplayPageEntriesByLayoutPageTemplateEntryId(
				HttpPrincipal httpPrincipal, long layoutPageTemplateEntryId) {

		try {
			MethodKey methodKey = new MethodKey(
				AssetDisplayPageEntryServiceUtil.class,
				"getAssetDisplayPageEntriesByLayoutPageTemplateEntryId",
				_getAssetDisplayPageEntriesByLayoutPageTemplateEntryIdParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, layoutPageTemplateEntryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.asset.display.page.model.AssetDisplayPageEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int
		getAssetDisplayPageEntriesCountByLayoutPageTemplateEntryId(
			HttpPrincipal httpPrincipal, long layoutPageTemplateEntryId) {

		try {
			MethodKey methodKey = new MethodKey(
				AssetDisplayPageEntryServiceUtil.class,
				"getAssetDisplayPageEntriesCountByLayoutPageTemplateEntryId",
				_getAssetDisplayPageEntriesCountByLayoutPageTemplateEntryIdParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, layoutPageTemplateEntryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.asset.display.page.model.AssetDisplayPageEntry
			updateAssetDisplayPageEntry(
				HttpPrincipal httpPrincipal, long assetDisplayPageEntryId,
				long layoutPageTemplateEntryId, int type)
		throws Exception {

		try {
			MethodKey methodKey = new MethodKey(
				AssetDisplayPageEntryServiceUtil.class,
				"updateAssetDisplayPageEntry",
				_updateAssetDisplayPageEntryParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, assetDisplayPageEntryId, layoutPageTemplateEntryId,
				type);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof Exception) {
					throw (Exception)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.asset.display.page.model.AssetDisplayPageEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		AssetDisplayPageEntryServiceHttp.class);

	private static final Class<?>[] _addAssetDisplayPageEntryParameterTypes0 =
		new Class[] {
			long.class, long.class, long.class, long.class, long.class,
			int.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addAssetDisplayPageEntryParameterTypes1 =
		new Class[] {
			long.class, long.class, long.class, long.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_deleteAssetDisplayPageEntryParameterTypes2 = new Class[] {
			long.class, long.class, long.class
		};
	private static final Class<?>[] _fetchAssetDisplayPageEntryParameterTypes3 =
		new Class[] {long.class, long.class, long.class};
	private static final Class<?>[]
		_getAssetDisplayPageEntriesByLayoutPageTemplateEntryIdParameterTypes4 =
			new Class[] {long.class};
	private static final Class<?>[]
		_getAssetDisplayPageEntriesCountByLayoutPageTemplateEntryIdParameterTypes5 =
			new Class[] {long.class};
	private static final Class<?>[]
		_updateAssetDisplayPageEntryParameterTypes6 = new Class[] {
			long.class, long.class, int.class
		};

}