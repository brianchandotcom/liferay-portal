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

package com.liferay.asset.entry.rel.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AssetEntryDisplayPageRelLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryDisplayPageRelLocalService
 * @generated
 */
@ProviderType
public class AssetEntryDisplayPageRelLocalServiceWrapper
	implements AssetEntryDisplayPageRelLocalService,
		ServiceWrapper<AssetEntryDisplayPageRelLocalService> {
	public AssetEntryDisplayPageRelLocalServiceWrapper(
		AssetEntryDisplayPageRelLocalService assetEntryDisplayPageRelLocalService) {
		_assetEntryDisplayPageRelLocalService = assetEntryDisplayPageRelLocalService;
	}

	/**
	* Adds the asset entry display page rel to the database. Also notifies the appropriate model listeners.
	*
	* @param assetEntryDisplayPageRel the asset entry display page rel
	* @return the asset entry display page rel that was added
	*/
	@Override
	public com.liferay.asset.entry.rel.model.AssetEntryDisplayPageRel addAssetEntryDisplayPageRel(
		com.liferay.asset.entry.rel.model.AssetEntryDisplayPageRel assetEntryDisplayPageRel) {
		return _assetEntryDisplayPageRelLocalService.addAssetEntryDisplayPageRel(assetEntryDisplayPageRel);
	}

	@Override
	public com.liferay.asset.entry.rel.model.AssetEntryDisplayPageRel addAssetEntryDisplayPageRel(
		long assetEntryId, long displayPageId) {
		return _assetEntryDisplayPageRelLocalService.addAssetEntryDisplayPageRel(assetEntryId,
			displayPageId);
	}

	/**
	* Creates a new asset entry display page rel with the primary key. Does not add the asset entry display page rel to the database.
	*
	* @param assetEntryDisplayPageRelId the primary key for the new asset entry display page rel
	* @return the new asset entry display page rel
	*/
	@Override
	public com.liferay.asset.entry.rel.model.AssetEntryDisplayPageRel createAssetEntryDisplayPageRel(
		long assetEntryDisplayPageRelId) {
		return _assetEntryDisplayPageRelLocalService.createAssetEntryDisplayPageRel(assetEntryDisplayPageRelId);
	}

	/**
	* Deletes the asset entry display page rel from the database. Also notifies the appropriate model listeners.
	*
	* @param assetEntryDisplayPageRel the asset entry display page rel
	* @return the asset entry display page rel that was removed
	*/
	@Override
	public com.liferay.asset.entry.rel.model.AssetEntryDisplayPageRel deleteAssetEntryDisplayPageRel(
		com.liferay.asset.entry.rel.model.AssetEntryDisplayPageRel assetEntryDisplayPageRel) {
		return _assetEntryDisplayPageRelLocalService.deleteAssetEntryDisplayPageRel(assetEntryDisplayPageRel);
	}

	/**
	* Deletes the asset entry display page rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param assetEntryDisplayPageRelId the primary key of the asset entry display page rel
	* @return the asset entry display page rel that was removed
	* @throws PortalException if a asset entry display page rel with the primary key could not be found
	*/
	@Override
	public com.liferay.asset.entry.rel.model.AssetEntryDisplayPageRel deleteAssetEntryDisplayPageRel(
		long assetEntryDisplayPageRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetEntryDisplayPageRelLocalService.deleteAssetEntryDisplayPageRel(assetEntryDisplayPageRelId);
	}

	@Override
	public void deleteAssetEntryDisplayPageRel(long assetEntryId,
		long displayPageId)
		throws com.liferay.asset.entry.rel.exception.NoSuchEntryDisplayPageRelException {
		_assetEntryDisplayPageRelLocalService.deleteAssetEntryDisplayPageRel(assetEntryId,
			displayPageId);
	}

	@Override
	public void deleteAssetEntryDisplayPageRelByAssetEntryId(long assetEntryId) {
		_assetEntryDisplayPageRelLocalService.deleteAssetEntryDisplayPageRelByAssetEntryId(assetEntryId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetEntryDisplayPageRelLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _assetEntryDisplayPageRelLocalService.dynamicQuery();
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
		return _assetEntryDisplayPageRelLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.asset.entry.rel.model.impl.AssetEntryDisplayPageRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _assetEntryDisplayPageRelLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.asset.entry.rel.model.impl.AssetEntryDisplayPageRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _assetEntryDisplayPageRelLocalService.dynamicQuery(dynamicQuery,
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
		return _assetEntryDisplayPageRelLocalService.dynamicQueryCount(dynamicQuery);
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
		return _assetEntryDisplayPageRelLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.asset.entry.rel.model.AssetEntryDisplayPageRel fetchAssetEntryDisplayPageRel(
		long assetEntryDisplayPageRelId) {
		return _assetEntryDisplayPageRelLocalService.fetchAssetEntryDisplayPageRel(assetEntryDisplayPageRelId);
	}

	@Override
	public com.liferay.asset.entry.rel.model.AssetEntryDisplayPageRel fetchAssetEntryDisplayPageRel(
		long assetEntryId, long displayPageId) {
		return _assetEntryDisplayPageRelLocalService.fetchAssetEntryDisplayPageRel(assetEntryId,
			displayPageId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _assetEntryDisplayPageRelLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the asset entry display page rel with the primary key.
	*
	* @param assetEntryDisplayPageRelId the primary key of the asset entry display page rel
	* @return the asset entry display page rel
	* @throws PortalException if a asset entry display page rel with the primary key could not be found
	*/
	@Override
	public com.liferay.asset.entry.rel.model.AssetEntryDisplayPageRel getAssetEntryDisplayPageRel(
		long assetEntryDisplayPageRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetEntryDisplayPageRelLocalService.getAssetEntryDisplayPageRel(assetEntryDisplayPageRelId);
	}

	/**
	* Returns a range of all the asset entry display page rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.asset.entry.rel.model.impl.AssetEntryDisplayPageRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset entry display page rels
	* @param end the upper bound of the range of asset entry display page rels (not inclusive)
	* @return the range of asset entry display page rels
	*/
	@Override
	public java.util.List<com.liferay.asset.entry.rel.model.AssetEntryDisplayPageRel> getAssetEntryDisplayPageRels(
		int start, int end) {
		return _assetEntryDisplayPageRelLocalService.getAssetEntryDisplayPageRels(start,
			end);
	}

	/**
	* Returns the number of asset entry display page rels.
	*
	* @return the number of asset entry display page rels
	*/
	@Override
	public int getAssetEntryDisplayPageRelsCount() {
		return _assetEntryDisplayPageRelLocalService.getAssetEntryDisplayPageRelsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _assetEntryDisplayPageRelLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _assetEntryDisplayPageRelLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetEntryDisplayPageRelLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the asset entry display page rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param assetEntryDisplayPageRel the asset entry display page rel
	* @return the asset entry display page rel that was updated
	*/
	@Override
	public com.liferay.asset.entry.rel.model.AssetEntryDisplayPageRel updateAssetEntryDisplayPageRel(
		com.liferay.asset.entry.rel.model.AssetEntryDisplayPageRel assetEntryDisplayPageRel) {
		return _assetEntryDisplayPageRelLocalService.updateAssetEntryDisplayPageRel(assetEntryDisplayPageRel);
	}

	@Override
	public AssetEntryDisplayPageRelLocalService getWrappedService() {
		return _assetEntryDisplayPageRelLocalService;
	}

	@Override
	public void setWrappedService(
		AssetEntryDisplayPageRelLocalService assetEntryDisplayPageRelLocalService) {
		_assetEntryDisplayPageRelLocalService = assetEntryDisplayPageRelLocalService;
	}

	private AssetEntryDisplayPageRelLocalService _assetEntryDisplayPageRelLocalService;
}