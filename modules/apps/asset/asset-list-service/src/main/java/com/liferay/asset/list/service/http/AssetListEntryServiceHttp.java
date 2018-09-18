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

package com.liferay.asset.list.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.list.service.AssetListEntryServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link AssetListEntryServiceUtil} service utility. The
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
 * @see AssetListEntryServiceSoap
 * @see HttpPrincipal
 * @see AssetListEntryServiceUtil
 * @generated
 */
@ProviderType
public class AssetListEntryServiceHttp {
	public static com.liferay.asset.list.model.AssetListEntry addAssetListEntry(
		HttpPrincipal httpPrincipal, long groupId, String title, int type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(AssetListEntryServiceUtil.class,
					"addAssetListEntry", _addAssetListEntryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					title, type, serviceContext);

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

			return (com.liferay.asset.list.model.AssetListEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void addAssetListEntryAssetEntryRel(
		HttpPrincipal httpPrincipal, long assetListEntryId, long assetEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(AssetListEntryServiceUtil.class,
					"addAssetListEntryAssetEntryRel",
					_addAssetListEntryAssetEntryRelParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					assetListEntryId, assetEntryId);

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

	public static void deleteAssetListEntries(HttpPrincipal httpPrincipal,
		long[] assetListEntriesIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(AssetListEntryServiceUtil.class,
					"deleteAssetListEntries",
					_deleteAssetListEntriesParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					assetListEntriesIds);

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

	public static com.liferay.asset.list.model.AssetListEntry deleteAssetListEntry(
		HttpPrincipal httpPrincipal, long assetListEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(AssetListEntryServiceUtil.class,
					"deleteAssetListEntry", _deleteAssetListEntryParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					assetListEntryId);

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

			return (com.liferay.asset.list.model.AssetListEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteAssetListEntryAssetEntryRel(
		HttpPrincipal httpPrincipal, long assetListEntryId, int position)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(AssetListEntryServiceUtil.class,
					"deleteAssetListEntryAssetEntryRel",
					_deleteAssetListEntryAssetEntryRelParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					assetListEntryId, position);

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

	public static com.liferay.asset.list.model.AssetListEntry fetchAssetListEntry(
		HttpPrincipal httpPrincipal, long assetListEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(AssetListEntryServiceUtil.class,
					"fetchAssetListEntry", _fetchAssetListEntryParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					assetListEntryId);

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

			return (com.liferay.asset.list.model.AssetListEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.asset.list.model.AssetListEntry> getAssetListEntries(
		HttpPrincipal httpPrincipal, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.asset.list.model.AssetListEntry> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(AssetListEntryServiceUtil.class,
					"getAssetListEntries", _getAssetListEntriesParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.asset.list.model.AssetListEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.asset.list.model.AssetListEntry> getAssetListEntries(
		HttpPrincipal httpPrincipal, long groupId, String title, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.asset.list.model.AssetListEntry> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(AssetListEntryServiceUtil.class,
					"getAssetListEntries", _getAssetListEntriesParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					title, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.asset.list.model.AssetListEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getAssetListEntriesCount(HttpPrincipal httpPrincipal,
		long groupId) {
		try {
			MethodKey methodKey = new MethodKey(AssetListEntryServiceUtil.class,
					"getAssetListEntriesCount",
					_getAssetListEntriesCountParameterTypes8);

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

	public static int getAssetListEntriesCount(HttpPrincipal httpPrincipal,
		long groupId, String title) {
		try {
			MethodKey methodKey = new MethodKey(AssetListEntryServiceUtil.class,
					"getAssetListEntriesCount",
					_getAssetListEntriesCountParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					title);

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

	public static void moveAssetListEntryAssetEntryRel(
		HttpPrincipal httpPrincipal, long assetListEntryId, int position,
		int newPosition)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(AssetListEntryServiceUtil.class,
					"moveAssetListEntryAssetEntryRel",
					_moveAssetListEntryAssetEntryRelParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					assetListEntryId, position, newPosition);

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

	public static com.liferay.asset.list.model.AssetListEntry updateAssetListEntry(
		HttpPrincipal httpPrincipal, long assetListEntryId, String title)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(AssetListEntryServiceUtil.class,
					"updateAssetListEntry",
					_updateAssetListEntryParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					assetListEntryId, title);

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

			return (com.liferay.asset.list.model.AssetListEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.asset.list.model.AssetListEntry updateAssetListEntrySettings(
		HttpPrincipal httpPrincipal, long assetListEntryId, String typeSettings)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(AssetListEntryServiceUtil.class,
					"updateAssetListEntrySettings",
					_updateAssetListEntrySettingsParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					assetListEntryId, typeSettings);

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

			return (com.liferay.asset.list.model.AssetListEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(AssetListEntryServiceHttp.class);
	private static final Class<?>[] _addAssetListEntryParameterTypes0 = new Class[] {
			long.class, String.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addAssetListEntryAssetEntryRelParameterTypes1 =
		new Class[] { long.class, long.class };
	private static final Class<?>[] _deleteAssetListEntriesParameterTypes2 = new Class[] {
			long[].class
		};
	private static final Class<?>[] _deleteAssetListEntryParameterTypes3 = new Class[] {
			long.class
		};
	private static final Class<?>[] _deleteAssetListEntryAssetEntryRelParameterTypes4 =
		new Class[] { long.class, int.class };
	private static final Class<?>[] _fetchAssetListEntryParameterTypes5 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getAssetListEntriesParameterTypes6 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getAssetListEntriesParameterTypes7 = new Class[] {
			long.class, String.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getAssetListEntriesCountParameterTypes8 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getAssetListEntriesCountParameterTypes9 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[] _moveAssetListEntryAssetEntryRelParameterTypes10 =
		new Class[] { long.class, int.class, int.class };
	private static final Class<?>[] _updateAssetListEntryParameterTypes11 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[] _updateAssetListEntrySettingsParameterTypes12 =
		new Class[] { long.class, String.class };
}