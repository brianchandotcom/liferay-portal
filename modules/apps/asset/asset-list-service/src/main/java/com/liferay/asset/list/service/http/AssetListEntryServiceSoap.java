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

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>AssetListEntryServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.asset.list.model.AssetListEntrySoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.asset.list.model.AssetListEntry</code>, that is translated to a
 * <code>com.liferay.asset.list.model.AssetListEntrySoap</code>. Methods that SOAP
 * cannot safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetListEntryServiceHttp
 * @generated
 */
public class AssetListEntryServiceSoap {

	public static void addAssetEntrySelection(
			long assetListEntryId, long assetEntryId, long segmentsEntryId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			AssetListEntryServiceUtil.addAssetEntrySelection(
				assetListEntryId, assetEntryId, segmentsEntryId,
				serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void addAssetEntrySelections(
			long assetListEntryId, long[] assetEntryIds, long segmentsEntryId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			AssetListEntryServiceUtil.addAssetEntrySelections(
				assetListEntryId, assetEntryIds, segmentsEntryId,
				serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.list.model.AssetListEntrySoap
			addAssetListEntry(
				long groupId, String title, int type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.asset.list.model.AssetListEntry returnValue =
				AssetListEntryServiceUtil.addAssetListEntry(
					groupId, title, type, serviceContext);

			return com.liferay.asset.list.model.AssetListEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.list.model.AssetListEntrySoap
			addDynamicAssetListEntry(
				long userId, long groupId, String title, String typeSettings,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.asset.list.model.AssetListEntry returnValue =
				AssetListEntryServiceUtil.addDynamicAssetListEntry(
					userId, groupId, title, typeSettings, serviceContext);

			return com.liferay.asset.list.model.AssetListEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.list.model.AssetListEntrySoap
			addManualAssetListEntry(
				long userId, long groupId, String title, long[] assetEntryIds,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.asset.list.model.AssetListEntry returnValue =
				AssetListEntryServiceUtil.addManualAssetListEntry(
					userId, groupId, title, assetEntryIds, serviceContext);

			return com.liferay.asset.list.model.AssetListEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteAssetEntrySelection(
			long assetListEntryId, long segmentsEntryId, int position)
		throws RemoteException {

		try {
			AssetListEntryServiceUtil.deleteAssetEntrySelection(
				assetListEntryId, segmentsEntryId, position);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteAssetListEntries(long[] assetListEntriesIds)
		throws RemoteException {

		try {
			AssetListEntryServiceUtil.deleteAssetListEntries(
				assetListEntriesIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.list.model.AssetListEntrySoap
			deleteAssetListEntry(long assetListEntryId)
		throws RemoteException {

		try {
			com.liferay.asset.list.model.AssetListEntry returnValue =
				AssetListEntryServiceUtil.deleteAssetListEntry(
					assetListEntryId);

			return com.liferay.asset.list.model.AssetListEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteAssetListEntry(
			long assetListEntryId, long segmentsEntryId)
		throws RemoteException {

		try {
			AssetListEntryServiceUtil.deleteAssetListEntry(
				assetListEntryId, segmentsEntryId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.list.model.AssetListEntrySoap
			fetchAssetListEntry(long assetListEntryId)
		throws RemoteException {

		try {
			com.liferay.asset.list.model.AssetListEntry returnValue =
				AssetListEntryServiceUtil.fetchAssetListEntry(assetListEntryId);

			return com.liferay.asset.list.model.AssetListEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.list.model.AssetListEntrySoap[]
			getAssetListEntries(
				long groupId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.asset.list.model.AssetListEntry>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.asset.list.model.AssetListEntry>
				returnValue = AssetListEntryServiceUtil.getAssetListEntries(
					groupId, start, end, orderByComparator);

			return com.liferay.asset.list.model.AssetListEntrySoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.list.model.AssetListEntrySoap[]
			getAssetListEntries(
				long groupId, String title, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.asset.list.model.AssetListEntry>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.asset.list.model.AssetListEntry>
				returnValue = AssetListEntryServiceUtil.getAssetListEntries(
					groupId, title, start, end, orderByComparator);

			return com.liferay.asset.list.model.AssetListEntrySoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.list.model.AssetListEntrySoap[]
			getAssetListEntries(
				long[] groupIds, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.asset.list.model.AssetListEntry>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.asset.list.model.AssetListEntry>
				returnValue = AssetListEntryServiceUtil.getAssetListEntries(
					groupIds, start, end, orderByComparator);

			return com.liferay.asset.list.model.AssetListEntrySoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.list.model.AssetListEntrySoap[]
			getAssetListEntries(
				long[] groupIds, String title, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.asset.list.model.AssetListEntry>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.asset.list.model.AssetListEntry>
				returnValue = AssetListEntryServiceUtil.getAssetListEntries(
					groupIds, title, start, end, orderByComparator);

			return com.liferay.asset.list.model.AssetListEntrySoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getAssetListEntriesCount(long groupId)
		throws RemoteException {

		try {
			int returnValue =
				AssetListEntryServiceUtil.getAssetListEntriesCount(groupId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getAssetListEntriesCount(long groupId, String title)
		throws RemoteException {

		try {
			int returnValue =
				AssetListEntryServiceUtil.getAssetListEntriesCount(
					groupId, title);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getAssetListEntriesCount(long[] groupIds)
		throws RemoteException {

		try {
			int returnValue =
				AssetListEntryServiceUtil.getAssetListEntriesCount(groupIds);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getAssetListEntriesCount(long[] groupIds, String title)
		throws RemoteException {

		try {
			int returnValue =
				AssetListEntryServiceUtil.getAssetListEntriesCount(
					groupIds, title);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.list.model.AssetListEntrySoap
			getAssetListEntry(long assetListEntryId)
		throws RemoteException {

		try {
			com.liferay.asset.list.model.AssetListEntry returnValue =
				AssetListEntryServiceUtil.getAssetListEntry(assetListEntryId);

			return com.liferay.asset.list.model.AssetListEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.list.model.AssetListEntrySoap
			getAssetListEntry(long groupId, String assetListEntryKey)
		throws RemoteException {

		try {
			com.liferay.asset.list.model.AssetListEntry returnValue =
				AssetListEntryServiceUtil.getAssetListEntry(
					groupId, assetListEntryKey);

			return com.liferay.asset.list.model.AssetListEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.list.model.AssetListEntrySoap
			getAssetListEntryByUuidAndGroupId(String uuid, long groupId)
		throws RemoteException {

		try {
			com.liferay.asset.list.model.AssetListEntry returnValue =
				AssetListEntryServiceUtil.getAssetListEntryByUuidAndGroupId(
					uuid, groupId);

			return com.liferay.asset.list.model.AssetListEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void moveAssetEntrySelection(
			long assetListEntryId, long segmentsEntryId, int position,
			int newPosition)
		throws RemoteException {

		try {
			AssetListEntryServiceUtil.moveAssetEntrySelection(
				assetListEntryId, segmentsEntryId, position, newPosition);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void updateAssetListEntry(
			long assetListEntryId, long segmentsEntryId, String typeSettings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			AssetListEntryServiceUtil.updateAssetListEntry(
				assetListEntryId, segmentsEntryId, typeSettings,
				serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.list.model.AssetListEntrySoap
			updateAssetListEntry(long assetListEntryId, String title)
		throws RemoteException {

		try {
			com.liferay.asset.list.model.AssetListEntry returnValue =
				AssetListEntryServiceUtil.updateAssetListEntry(
					assetListEntryId, title);

			return com.liferay.asset.list.model.AssetListEntrySoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void updateAssetListEntryTypeSettings(
			long assetListEntryId, long segmentsEntryId, String typeSettings)
		throws RemoteException {

		try {
			AssetListEntryServiceUtil.updateAssetListEntryTypeSettings(
				assetListEntryId, segmentsEntryId, typeSettings);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		AssetListEntryServiceSoap.class);

}