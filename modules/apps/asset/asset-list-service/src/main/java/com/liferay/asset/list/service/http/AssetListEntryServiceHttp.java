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

import com.liferay.asset.list.service.AssetListEntryServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>AssetListEntryServiceUtil</code> service
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
 * @see AssetListEntryServiceSoap
 * @generated
 */
public class AssetListEntryServiceHttp {

	public static void addAssetEntrySelection(
			HttpPrincipal httpPrincipal, long assetListEntryId,
			long assetEntryId, long segmentsEntryId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetListEntryServiceUtil.class, "addAssetEntrySelection",
				_addAssetEntrySelectionParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, assetListEntryId, assetEntryId, segmentsEntryId,
				serviceContext);

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

	public static void addAssetEntrySelections(
			HttpPrincipal httpPrincipal, long assetListEntryId,
			long[] assetEntryIds, long segmentsEntryId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetListEntryServiceUtil.class, "addAssetEntrySelections",
				_addAssetEntrySelectionsParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, assetListEntryId, assetEntryIds, segmentsEntryId,
				serviceContext);

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

	public static com.liferay.asset.list.model.AssetListEntry addAssetListEntry(
			HttpPrincipal httpPrincipal, long groupId, String title, int type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetListEntryServiceUtil.class, "addAssetListEntry",
				_addAssetListEntryParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, title, type, serviceContext);

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

			return (com.liferay.asset.list.model.AssetListEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.asset.list.model.AssetListEntry
			addDynamicAssetListEntry(
				HttpPrincipal httpPrincipal, long userId, long groupId,
				String title, String typeSettings,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetListEntryServiceUtil.class, "addDynamicAssetListEntry",
				_addDynamicAssetListEntryParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, groupId, title, typeSettings,
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

			return (com.liferay.asset.list.model.AssetListEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.asset.list.model.AssetListEntry
			addManualAssetListEntry(
				HttpPrincipal httpPrincipal, long userId, long groupId,
				String title, long[] assetEntryIds,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetListEntryServiceUtil.class, "addManualAssetListEntry",
				_addManualAssetListEntryParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, groupId, title, assetEntryIds,
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

			return (com.liferay.asset.list.model.AssetListEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteAssetEntrySelection(
			HttpPrincipal httpPrincipal, long assetListEntryId,
			long segmentsEntryId, int position)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetListEntryServiceUtil.class, "deleteAssetEntrySelection",
				_deleteAssetEntrySelectionParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, assetListEntryId, segmentsEntryId, position);

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

	public static void deleteAssetListEntries(
			HttpPrincipal httpPrincipal, long[] assetListEntriesIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetListEntryServiceUtil.class, "deleteAssetListEntries",
				_deleteAssetListEntriesParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, assetListEntriesIds);

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

	public static com.liferay.asset.list.model.AssetListEntry
			deleteAssetListEntry(
				HttpPrincipal httpPrincipal, long assetListEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetListEntryServiceUtil.class, "deleteAssetListEntry",
				_deleteAssetListEntryParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, assetListEntryId);

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

			return (com.liferay.asset.list.model.AssetListEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteAssetListEntry(
			HttpPrincipal httpPrincipal, long assetListEntryId,
			long segmentsEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetListEntryServiceUtil.class, "deleteAssetListEntry",
				_deleteAssetListEntryParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, assetListEntryId, segmentsEntryId);

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

	public static com.liferay.asset.list.model.AssetListEntry
			fetchAssetListEntry(
				HttpPrincipal httpPrincipal, long assetListEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetListEntryServiceUtil.class, "fetchAssetListEntry",
				_fetchAssetListEntryParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, assetListEntryId);

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

			return (com.liferay.asset.list.model.AssetListEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.asset.list.model.AssetListEntry>
		getAssetListEntries(
			HttpPrincipal httpPrincipal, long groupId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.asset.list.model.AssetListEntry>
					orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				AssetListEntryServiceUtil.class, "getAssetListEntries",
				_getAssetListEntriesParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<com.liferay.asset.list.model.AssetListEntry>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.asset.list.model.AssetListEntry>
		getAssetListEntries(
			HttpPrincipal httpPrincipal, long groupId, String title, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.asset.list.model.AssetListEntry>
					orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				AssetListEntryServiceUtil.class, "getAssetListEntries",
				_getAssetListEntriesParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, title, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<com.liferay.asset.list.model.AssetListEntry>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getAssetListEntriesCount(
		HttpPrincipal httpPrincipal, long groupId) {

		try {
			MethodKey methodKey = new MethodKey(
				AssetListEntryServiceUtil.class, "getAssetListEntriesCount",
				_getAssetListEntriesCountParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

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

	public static int getAssetListEntriesCount(
		HttpPrincipal httpPrincipal, long groupId, String title) {

		try {
			MethodKey methodKey = new MethodKey(
				AssetListEntryServiceUtil.class, "getAssetListEntriesCount",
				_getAssetListEntriesCountParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, title);

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

	public static com.liferay.asset.list.model.AssetListEntry getAssetListEntry(
			HttpPrincipal httpPrincipal, long assetListEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetListEntryServiceUtil.class, "getAssetListEntry",
				_getAssetListEntryParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, assetListEntryId);

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

			return (com.liferay.asset.list.model.AssetListEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.asset.list.model.AssetListEntry getAssetListEntry(
			HttpPrincipal httpPrincipal, long groupId, String assetListEntryKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetListEntryServiceUtil.class, "getAssetListEntry",
				_getAssetListEntryParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, assetListEntryKey);

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

			return (com.liferay.asset.list.model.AssetListEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.asset.list.model.AssetListEntry
			getAssetListEntryByUuidAndGroupId(
				HttpPrincipal httpPrincipal, String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetListEntryServiceUtil.class,
				"getAssetListEntryByUuidAndGroupId",
				_getAssetListEntryByUuidAndGroupIdParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, uuid, groupId);

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

			return (com.liferay.asset.list.model.AssetListEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void moveAssetEntrySelection(
			HttpPrincipal httpPrincipal, long assetListEntryId,
			long segmentsEntryId, int position, int newPosition)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetListEntryServiceUtil.class, "moveAssetEntrySelection",
				_moveAssetEntrySelectionParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, assetListEntryId, segmentsEntryId, position,
				newPosition);

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

	public static void updateAssetListEntry(
			HttpPrincipal httpPrincipal, long assetListEntryId,
			long segmentsEntryId, String typeSettings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetListEntryServiceUtil.class, "updateAssetListEntry",
				_updateAssetListEntryParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, assetListEntryId, segmentsEntryId, typeSettings,
				serviceContext);

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

	public static com.liferay.asset.list.model.AssetListEntry
			updateAssetListEntry(
				HttpPrincipal httpPrincipal, long assetListEntryId,
				String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetListEntryServiceUtil.class, "updateAssetListEntry",
				_updateAssetListEntryParameterTypes19);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, assetListEntryId, title);

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

			return (com.liferay.asset.list.model.AssetListEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void updateAssetListEntryTypeSettings(
			HttpPrincipal httpPrincipal, long assetListEntryId,
			long segmentsEntryId, String typeSettings)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetListEntryServiceUtil.class,
				"updateAssetListEntryTypeSettings",
				_updateAssetListEntryTypeSettingsParameterTypes20);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, assetListEntryId, segmentsEntryId, typeSettings);

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

	private static Log _log = LogFactoryUtil.getLog(
		AssetListEntryServiceHttp.class);

	private static final Class<?>[] _addAssetEntrySelectionParameterTypes0 =
		new Class[] {
			long.class, long.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addAssetEntrySelectionsParameterTypes1 =
		new Class[] {
			long.class, long[].class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addAssetListEntryParameterTypes2 =
		new Class[] {
			long.class, String.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addDynamicAssetListEntryParameterTypes3 =
		new Class[] {
			long.class, long.class, String.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addManualAssetListEntryParameterTypes4 =
		new Class[] {
			long.class, long.class, String.class, long[].class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteAssetEntrySelectionParameterTypes5 =
		new Class[] {long.class, long.class, int.class};
	private static final Class<?>[] _deleteAssetListEntriesParameterTypes6 =
		new Class[] {long[].class};
	private static final Class<?>[] _deleteAssetListEntryParameterTypes7 =
		new Class[] {long.class};
	private static final Class<?>[] _deleteAssetListEntryParameterTypes8 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _fetchAssetListEntryParameterTypes9 =
		new Class[] {long.class};
	private static final Class<?>[] _getAssetListEntriesParameterTypes10 =
		new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getAssetListEntriesParameterTypes11 =
		new Class[] {
			long.class, String.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getAssetListEntriesCountParameterTypes12 =
		new Class[] {long.class};
	private static final Class<?>[] _getAssetListEntriesCountParameterTypes13 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _getAssetListEntryParameterTypes14 =
		new Class[] {long.class};
	private static final Class<?>[] _getAssetListEntryParameterTypes15 =
		new Class[] {long.class, String.class};
	private static final Class<?>[]
		_getAssetListEntryByUuidAndGroupIdParameterTypes16 = new Class[] {
			String.class, long.class
		};
	private static final Class<?>[] _moveAssetEntrySelectionParameterTypes17 =
		new Class[] {long.class, long.class, int.class, int.class};
	private static final Class<?>[] _updateAssetListEntryParameterTypes18 =
		new Class[] {
			long.class, long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateAssetListEntryParameterTypes19 =
		new Class[] {long.class, String.class};
	private static final Class<?>[]
		_updateAssetListEntryTypeSettingsParameterTypes20 = new Class[] {
			long.class, long.class, String.class
		};

}