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

package com.liferay.asset.display.page.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AssetDisplayPageEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see AssetDisplayPageEntryLocalService
 * @generated
 */
@ProviderType
public class AssetDisplayPageEntryLocalServiceWrapper
	implements AssetDisplayPageEntryLocalService,
		ServiceWrapper<AssetDisplayPageEntryLocalService> {
	public AssetDisplayPageEntryLocalServiceWrapper(
		AssetDisplayPageEntryLocalService assetDisplayPageEntryLocalService) {
		_assetDisplayPageEntryLocalService = assetDisplayPageEntryLocalService;
	}

	/**
	* Adds the asset display page entry to the database. Also notifies the appropriate model listeners.
	*
	* @param assetDisplayPageEntry the asset display page entry
	* @return the asset display page entry that was added
	*/
	@Override
	public com.liferay.asset.display.page.model.AssetDisplayPageEntry addAssetDisplayPageEntry(
		com.liferay.asset.display.page.model.AssetDisplayPageEntry assetDisplayPageEntry) {
		return _assetDisplayPageEntryLocalService.addAssetDisplayPageEntry(assetDisplayPageEntry);
	}

	@Override
	public com.liferay.asset.display.page.model.AssetDisplayPageEntry addAssetDisplayPageEntry(
		long assetEntryId, long layoutId) {
		return _assetDisplayPageEntryLocalService.addAssetDisplayPageEntry(assetEntryId,
			layoutId);
	}

	/**
	* Creates a new asset display page entry with the primary key. Does not add the asset display page entry to the database.
	*
	* @param assetDisplayPageEntryId the primary key for the new asset display page entry
	* @return the new asset display page entry
	*/
	@Override
	public com.liferay.asset.display.page.model.AssetDisplayPageEntry createAssetDisplayPageEntry(
		long assetDisplayPageEntryId) {
		return _assetDisplayPageEntryLocalService.createAssetDisplayPageEntry(assetDisplayPageEntryId);
	}

	/**
	* Deletes the asset display page entry from the database. Also notifies the appropriate model listeners.
	*
	* @param assetDisplayPageEntry the asset display page entry
	* @return the asset display page entry that was removed
	*/
	@Override
	public com.liferay.asset.display.page.model.AssetDisplayPageEntry deleteAssetDisplayPageEntry(
		com.liferay.asset.display.page.model.AssetDisplayPageEntry assetDisplayPageEntry) {
		return _assetDisplayPageEntryLocalService.deleteAssetDisplayPageEntry(assetDisplayPageEntry);
	}

	/**
	* Deletes the asset display page entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param assetDisplayPageEntryId the primary key of the asset display page entry
	* @return the asset display page entry that was removed
	* @throws PortalException if a asset display page entry with the primary key could not be found
	*/
	@Override
	public com.liferay.asset.display.page.model.AssetDisplayPageEntry deleteAssetDisplayPageEntry(
		long assetDisplayPageEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetDisplayPageEntryLocalService.deleteAssetDisplayPageEntry(assetDisplayPageEntryId);
	}

	@Override
	public void deleteAssetDisplayPageEntry(long assetEntryId, long layoutId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_assetDisplayPageEntryLocalService.deleteAssetDisplayPageEntry(assetEntryId,
			layoutId);
	}

	@Override
	public void deleteAssetDisplayPageEntryByAssetEntryId(long assetEntryId) {
		_assetDisplayPageEntryLocalService.deleteAssetDisplayPageEntryByAssetEntryId(assetEntryId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetDisplayPageEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _assetDisplayPageEntryLocalService.dynamicQuery();
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
		return _assetDisplayPageEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.asset.display.page.model.impl.AssetDisplayPageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _assetDisplayPageEntryLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.asset.display.page.model.impl.AssetDisplayPageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _assetDisplayPageEntryLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
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
		return _assetDisplayPageEntryLocalService.dynamicQueryCount(dynamicQuery);
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
		return _assetDisplayPageEntryLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.asset.display.page.model.AssetDisplayPageEntry fetchAssetDisplayPageEntry(
		long assetDisplayPageEntryId) {
		return _assetDisplayPageEntryLocalService.fetchAssetDisplayPageEntry(assetDisplayPageEntryId);
	}

	@Override
	public com.liferay.asset.display.page.model.AssetDisplayPageEntry fetchAssetDisplayPageEntry(
		long assetEntryId, long layoutId) {
		return _assetDisplayPageEntryLocalService.fetchAssetDisplayPageEntry(assetEntryId,
			layoutId);
	}

	@Override
	public com.liferay.asset.display.page.model.AssetDisplayPageEntry fetchFirstAssetDisplayPageEntry(
		long assetEntryId) {
		return _assetDisplayPageEntryLocalService.fetchFirstAssetDisplayPageEntry(assetEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _assetDisplayPageEntryLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns a range of all the asset display page entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.asset.display.page.model.impl.AssetDisplayPageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset display page entries
	* @param end the upper bound of the range of asset display page entries (not inclusive)
	* @return the range of asset display page entries
	*/
	@Override
	public java.util.List<com.liferay.asset.display.page.model.AssetDisplayPageEntry> getAssetDisplayPageEntries(
		int start, int end) {
		return _assetDisplayPageEntryLocalService.getAssetDisplayPageEntries(start,
			end);
	}

	/**
	* Returns the number of asset display page entries.
	*
	* @return the number of asset display page entries
	*/
	@Override
	public int getAssetDisplayPageEntriesCount() {
		return _assetDisplayPageEntryLocalService.getAssetDisplayPageEntriesCount();
	}

	/**
	* Returns the asset display page entry with the primary key.
	*
	* @param assetDisplayPageEntryId the primary key of the asset display page entry
	* @return the asset display page entry
	* @throws PortalException if a asset display page entry with the primary key could not be found
	*/
	@Override
	public com.liferay.asset.display.page.model.AssetDisplayPageEntry getAssetDisplayPageEntry(
		long assetDisplayPageEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetDisplayPageEntryLocalService.getAssetDisplayPageEntry(assetDisplayPageEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _assetDisplayPageEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _assetDisplayPageEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetDisplayPageEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the asset display page entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param assetDisplayPageEntry the asset display page entry
	* @return the asset display page entry that was updated
	*/
	@Override
	public com.liferay.asset.display.page.model.AssetDisplayPageEntry updateAssetDisplayPageEntry(
		com.liferay.asset.display.page.model.AssetDisplayPageEntry assetDisplayPageEntry) {
		return _assetDisplayPageEntryLocalService.updateAssetDisplayPageEntry(assetDisplayPageEntry);
	}

	@Override
	public AssetDisplayPageEntryLocalService getWrappedService() {
		return _assetDisplayPageEntryLocalService;
	}

	@Override
	public void setWrappedService(
		AssetDisplayPageEntryLocalService assetDisplayPageEntryLocalService) {
		_assetDisplayPageEntryLocalService = assetDisplayPageEntryLocalService;
	}

	private AssetDisplayPageEntryLocalService _assetDisplayPageEntryLocalService;
}