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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AssetListEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see AssetListEntryLocalService
 * @generated
 */
public class AssetListEntryLocalServiceWrapper
	implements AssetListEntryLocalService,
			   ServiceWrapper<AssetListEntryLocalService> {

	public AssetListEntryLocalServiceWrapper(
		AssetListEntryLocalService assetListEntryLocalService) {

		_assetListEntryLocalService = assetListEntryLocalService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetListEntryLocalServiceUtil} to access the asset list entry local service. Add custom service methods to <code>com.liferay.asset.list.service.impl.AssetListEntryLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public void addAssetEntrySelection(
			long assetListEntryId, long assetEntryId, long segmentsEntryId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_assetListEntryLocalService.addAssetEntrySelection(
			assetListEntryId, assetEntryId, segmentsEntryId, serviceContext);
	}

	@Override
	public void addAssetEntrySelections(
			long assetListEntryId, long[] assetEntryIds, long segmentsEntryId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_assetListEntryLocalService.addAssetEntrySelections(
			assetListEntryId, assetEntryIds, segmentsEntryId, serviceContext);
	}

	/**
	 * Adds the asset list entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetListEntry the asset list entry
	 * @return the asset list entry that was added
	 */
	@Override
	public com.liferay.asset.list.model.AssetListEntry addAssetListEntry(
		com.liferay.asset.list.model.AssetListEntry assetListEntry) {

		return _assetListEntryLocalService.addAssetListEntry(assetListEntry);
	}

	@Override
	public com.liferay.asset.list.model.AssetListEntry addAssetListEntry(
			long userId, long groupId, String title, int type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetListEntryLocalService.addAssetListEntry(
			userId, groupId, title, type, serviceContext);
	}

	@Override
	public com.liferay.asset.list.model.AssetListEntry addAssetListEntry(
			long userId, long groupId, String title, int type,
			String typeSettings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetListEntryLocalService.addAssetListEntry(
			userId, groupId, title, type, typeSettings, serviceContext);
	}

	@Override
	public com.liferay.asset.list.model.AssetListEntry addDynamicAssetListEntry(
			long userId, long groupId, String title, String typeSettings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetListEntryLocalService.addDynamicAssetListEntry(
			userId, groupId, title, typeSettings, serviceContext);
	}

	@Override
	public com.liferay.asset.list.model.AssetListEntry addManualAssetListEntry(
			long userId, long groupId, String title, long[] assetEntryIds,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetListEntryLocalService.addManualAssetListEntry(
			userId, groupId, title, assetEntryIds, serviceContext);
	}

	/**
	 * Creates a new asset list entry with the primary key. Does not add the asset list entry to the database.
	 *
	 * @param assetListEntryId the primary key for the new asset list entry
	 * @return the new asset list entry
	 */
	@Override
	public com.liferay.asset.list.model.AssetListEntry createAssetListEntry(
		long assetListEntryId) {

		return _assetListEntryLocalService.createAssetListEntry(
			assetListEntryId);
	}

	@Override
	public void deleteAssetEntrySelection(
			long assetListEntryId, long segmentsEntryId, int position)
		throws com.liferay.portal.kernel.exception.PortalException {

		_assetListEntryLocalService.deleteAssetEntrySelection(
			assetListEntryId, segmentsEntryId, position);
	}

	/**
	 * Deletes the asset list entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetListEntry the asset list entry
	 * @return the asset list entry that was removed
	 */
	@Override
	public com.liferay.asset.list.model.AssetListEntry deleteAssetListEntry(
		com.liferay.asset.list.model.AssetListEntry assetListEntry) {

		return _assetListEntryLocalService.deleteAssetListEntry(assetListEntry);
	}

	/**
	 * Deletes the asset list entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetListEntryId the primary key of the asset list entry
	 * @return the asset list entry that was removed
	 * @throws PortalException if a asset list entry with the primary key could not be found
	 */
	@Override
	public com.liferay.asset.list.model.AssetListEntry deleteAssetListEntry(
			long assetListEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetListEntryLocalService.deleteAssetListEntry(
			assetListEntryId);
	}

	@Override
	public com.liferay.asset.list.model.AssetListEntry deleteAssetListEntry(
			long assetListEntryId, long segmentsEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetListEntryLocalService.deleteAssetListEntry(
			assetListEntryId, segmentsEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetListEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _assetListEntryLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _assetListEntryLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _assetListEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _assetListEntryLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _assetListEntryLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _assetListEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.asset.list.model.AssetListEntry fetchAssetListEntry(
		long assetListEntryId) {

		return _assetListEntryLocalService.fetchAssetListEntry(
			assetListEntryId);
	}

	/**
	 * Returns the asset list entry matching the UUID and group.
	 *
	 * @param uuid the asset list entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching asset list entry, or <code>null</code> if a matching asset list entry could not be found
	 */
	@Override
	public com.liferay.asset.list.model.AssetListEntry
		fetchAssetListEntryByUuidAndGroupId(String uuid, long groupId) {

		return _assetListEntryLocalService.fetchAssetListEntryByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _assetListEntryLocalService.getActionableDynamicQuery();
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
	@Override
	public java.util.List<com.liferay.asset.list.model.AssetListEntry>
		getAssetListEntries(int start, int end) {

		return _assetListEntryLocalService.getAssetListEntries(start, end);
	}

	@Override
	public java.util.List<com.liferay.asset.list.model.AssetListEntry>
		getAssetListEntries(long groupId) {

		return _assetListEntryLocalService.getAssetListEntries(groupId);
	}

	/**
	 * Returns all the asset list entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the asset list entries
	 * @param companyId the primary key of the company
	 * @return the matching asset list entries, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.asset.list.model.AssetListEntry>
		getAssetListEntriesByUuidAndCompanyId(String uuid, long companyId) {

		return _assetListEntryLocalService.
			getAssetListEntriesByUuidAndCompanyId(uuid, companyId);
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
	@Override
	public java.util.List<com.liferay.asset.list.model.AssetListEntry>
		getAssetListEntriesByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.asset.list.model.AssetListEntry>
					orderByComparator) {

		return _assetListEntryLocalService.
			getAssetListEntriesByUuidAndCompanyId(
				uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of asset list entries.
	 *
	 * @return the number of asset list entries
	 */
	@Override
	public int getAssetListEntriesCount() {
		return _assetListEntryLocalService.getAssetListEntriesCount();
	}

	/**
	 * Returns the asset list entry with the primary key.
	 *
	 * @param assetListEntryId the primary key of the asset list entry
	 * @return the asset list entry
	 * @throws PortalException if a asset list entry with the primary key could not be found
	 */
	@Override
	public com.liferay.asset.list.model.AssetListEntry getAssetListEntry(
			long assetListEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetListEntryLocalService.getAssetListEntry(assetListEntryId);
	}

	@Override
	public com.liferay.asset.list.model.AssetListEntry getAssetListEntry(
			long groupId, String assetListEntryKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetListEntryLocalService.getAssetListEntry(
			groupId, assetListEntryKey);
	}

	/**
	 * Returns the asset list entry matching the UUID and group.
	 *
	 * @param uuid the asset list entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching asset list entry
	 * @throws PortalException if a matching asset list entry could not be found
	 */
	@Override
	public com.liferay.asset.list.model.AssetListEntry
			getAssetListEntryByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetListEntryLocalService.getAssetListEntryByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _assetListEntryLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _assetListEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _assetListEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetListEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public void moveAssetEntrySelection(
			long assetListEntryId, long segmentsEntryId, int position,
			int newPosition)
		throws com.liferay.portal.kernel.exception.PortalException {

		_assetListEntryLocalService.moveAssetEntrySelection(
			assetListEntryId, segmentsEntryId, position, newPosition);
	}

	/**
	 * Updates the asset list entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param assetListEntry the asset list entry
	 * @return the asset list entry that was updated
	 */
	@Override
	public com.liferay.asset.list.model.AssetListEntry updateAssetListEntry(
		com.liferay.asset.list.model.AssetListEntry assetListEntry) {

		return _assetListEntryLocalService.updateAssetListEntry(assetListEntry);
	}

	@Override
	public com.liferay.asset.list.model.AssetListEntry updateAssetListEntry(
			long assetListEntryId, long segmentsEntryId, String typeSettings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetListEntryLocalService.updateAssetListEntry(
			assetListEntryId, segmentsEntryId, typeSettings, serviceContext);
	}

	@Override
	public com.liferay.asset.list.model.AssetListEntry updateAssetListEntry(
			long assetListEntryId, String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetListEntryLocalService.updateAssetListEntry(
			assetListEntryId, title);
	}

	@Override
	public void updateAssetListEntryTypeSettings(
			long assetListEntryId, long segmentsEntryId, String typeSettings)
		throws com.liferay.portal.kernel.exception.PortalException {

		_assetListEntryLocalService.updateAssetListEntryTypeSettings(
			assetListEntryId, segmentsEntryId, typeSettings);
	}

	@Override
	public AssetListEntryLocalService getWrappedService() {
		return _assetListEntryLocalService;
	}

	@Override
	public void setWrappedService(
		AssetListEntryLocalService assetListEntryLocalService) {

		_assetListEntryLocalService = assetListEntryLocalService;
	}

	private AssetListEntryLocalService _assetListEntryLocalService;

}