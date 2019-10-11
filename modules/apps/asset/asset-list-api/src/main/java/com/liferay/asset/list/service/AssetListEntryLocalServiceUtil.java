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

package com.liferay.asset.list.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for AssetListEntry. This utility wraps
 * <code>com.liferay.asset.list.service.impl.AssetListEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see AssetListEntryLocalService
 * @generated
 */
public class AssetListEntryLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.asset.list.service.impl.AssetListEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetListEntryLocalServiceUtil} to access the asset list entry local service. Add custom service methods to <code>com.liferay.asset.list.service.impl.AssetListEntryLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static void addAssetEntrySelection(
			long assetListEntryId, long assetEntryId, long segmentsEntryId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addAssetEntrySelection(
			assetListEntryId, assetEntryId, segmentsEntryId, serviceContext);
	}

	public static void addAssetEntrySelections(
			long assetListEntryId, long[] assetEntryIds, long segmentsEntryId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addAssetEntrySelections(
			assetListEntryId, assetEntryIds, segmentsEntryId, serviceContext);
	}

	/**
	 * Adds the asset list entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetListEntry the asset list entry
	 * @return the asset list entry that was added
	 */
	public static com.liferay.asset.list.model.AssetListEntry addAssetListEntry(
		com.liferay.asset.list.model.AssetListEntry assetListEntry) {

		return getService().addAssetListEntry(assetListEntry);
	}

	public static com.liferay.asset.list.model.AssetListEntry addAssetListEntry(
			long userId, long groupId, String title, int type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addAssetListEntry(
			userId, groupId, title, type, serviceContext);
	}

	public static com.liferay.asset.list.model.AssetListEntry addAssetListEntry(
			long userId, long groupId, String title, int type,
			String typeSettings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addAssetListEntry(
			userId, groupId, title, type, typeSettings, serviceContext);
	}

	public static com.liferay.asset.list.model.AssetListEntry
			addDynamicAssetListEntry(
				long userId, long groupId, String title, String typeSettings,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addDynamicAssetListEntry(
			userId, groupId, title, typeSettings, serviceContext);
	}

	public static com.liferay.asset.list.model.AssetListEntry
			addManualAssetListEntry(
				long userId, long groupId, String title, long[] assetEntryIds,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addManualAssetListEntry(
			userId, groupId, title, assetEntryIds, serviceContext);
	}

	/**
	 * Creates a new asset list entry with the primary key. Does not add the asset list entry to the database.
	 *
	 * @param assetListEntryId the primary key for the new asset list entry
	 * @return the new asset list entry
	 */
	public static com.liferay.asset.list.model.AssetListEntry
		createAssetListEntry(long assetListEntryId) {

		return getService().createAssetListEntry(assetListEntryId);
	}

	public static void deleteAssetEntrySelection(
			long assetListEntryId, long segmentsEntryId, int position)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteAssetEntrySelection(
			assetListEntryId, segmentsEntryId, position);
	}

	/**
	 * Deletes the asset list entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetListEntry the asset list entry
	 * @return the asset list entry that was removed
	 */
	public static com.liferay.asset.list.model.AssetListEntry
		deleteAssetListEntry(
			com.liferay.asset.list.model.AssetListEntry assetListEntry) {

		return getService().deleteAssetListEntry(assetListEntry);
	}

	/**
	 * Deletes the asset list entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetListEntryId the primary key of the asset list entry
	 * @return the asset list entry that was removed
	 * @throws PortalException if a asset list entry with the primary key could not be found
	 */
	public static com.liferay.asset.list.model.AssetListEntry
			deleteAssetListEntry(long assetListEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteAssetListEntry(assetListEntryId);
	}

	public static com.liferay.asset.list.model.AssetListEntry
			deleteAssetListEntry(long assetListEntryId, long segmentsEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteAssetListEntry(
			assetListEntryId, segmentsEntryId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.asset.list.model.impl.AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.asset.list.model.impl.AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.asset.list.model.AssetListEntry
		fetchAssetListEntry(long assetListEntryId) {

		return getService().fetchAssetListEntry(assetListEntryId);
	}

	/**
	 * Returns the asset list entry matching the UUID and group.
	 *
	 * @param uuid the asset list entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching asset list entry, or <code>null</code> if a matching asset list entry could not be found
	 */
	public static com.liferay.asset.list.model.AssetListEntry
		fetchAssetListEntryByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchAssetListEntryByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the asset list entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.asset.list.model.impl.AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @return the range of asset list entries
	 */
	public static java.util.List<com.liferay.asset.list.model.AssetListEntry>
		getAssetListEntries(int start, int end) {

		return getService().getAssetListEntries(start, end);
	}

	public static java.util.List<com.liferay.asset.list.model.AssetListEntry>
		getAssetListEntries(long groupId) {

		return getService().getAssetListEntries(groupId);
	}

	/**
	 * Returns all the asset list entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the asset list entries
	 * @param companyId the primary key of the company
	 * @return the matching asset list entries, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.asset.list.model.AssetListEntry>
		getAssetListEntriesByUuidAndCompanyId(String uuid, long companyId) {

		return getService().getAssetListEntriesByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of asset list entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the asset list entries
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching asset list entries, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.asset.list.model.AssetListEntry>
		getAssetListEntriesByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.asset.list.model.AssetListEntry>
					orderByComparator) {

		return getService().getAssetListEntriesByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of asset list entries.
	 *
	 * @return the number of asset list entries
	 */
	public static int getAssetListEntriesCount() {
		return getService().getAssetListEntriesCount();
	}

	/**
	 * Returns the asset list entry with the primary key.
	 *
	 * @param assetListEntryId the primary key of the asset list entry
	 * @return the asset list entry
	 * @throws PortalException if a asset list entry with the primary key could not be found
	 */
	public static com.liferay.asset.list.model.AssetListEntry getAssetListEntry(
			long assetListEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAssetListEntry(assetListEntryId);
	}

	public static com.liferay.asset.list.model.AssetListEntry getAssetListEntry(
			long groupId, String assetListEntryKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAssetListEntry(groupId, assetListEntryKey);
	}

	/**
	 * Returns the asset list entry matching the UUID and group.
	 *
	 * @param uuid the asset list entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching asset list entry
	 * @throws PortalException if a matching asset list entry could not be found
	 */
	public static com.liferay.asset.list.model.AssetListEntry
			getAssetListEntryByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAssetListEntryByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static void moveAssetEntrySelection(
			long assetListEntryId, long segmentsEntryId, int position,
			int newPosition)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().moveAssetEntrySelection(
			assetListEntryId, segmentsEntryId, position, newPosition);
	}

	/**
	 * Updates the asset list entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param assetListEntry the asset list entry
	 * @return the asset list entry that was updated
	 */
	public static com.liferay.asset.list.model.AssetListEntry
		updateAssetListEntry(
			com.liferay.asset.list.model.AssetListEntry assetListEntry) {

		return getService().updateAssetListEntry(assetListEntry);
	}

	public static com.liferay.asset.list.model.AssetListEntry
			updateAssetListEntry(
				long assetListEntryId, long segmentsEntryId,
				String typeSettings,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateAssetListEntry(
			assetListEntryId, segmentsEntryId, typeSettings, serviceContext);
	}

	public static com.liferay.asset.list.model.AssetListEntry
			updateAssetListEntry(long assetListEntryId, String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateAssetListEntry(assetListEntryId, title);
	}

	public static void updateAssetListEntryTypeSettings(
			long assetListEntryId, long segmentsEntryId, String typeSettings)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateAssetListEntryTypeSettings(
			assetListEntryId, segmentsEntryId, typeSettings);
	}

	public static AssetListEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<AssetListEntryLocalService, AssetListEntryLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			AssetListEntryLocalService.class);

		ServiceTracker<AssetListEntryLocalService, AssetListEntryLocalService>
			serviceTracker =
				new ServiceTracker
					<AssetListEntryLocalService, AssetListEntryLocalService>(
						bundle.getBundleContext(),
						AssetListEntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}