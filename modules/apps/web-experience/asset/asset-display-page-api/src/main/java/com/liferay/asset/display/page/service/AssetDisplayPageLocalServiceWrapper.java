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
 * Provides a wrapper for {@link AssetDisplayPageLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see AssetDisplayPageLocalService
 * @generated
 */
@ProviderType
public class AssetDisplayPageLocalServiceWrapper
	implements AssetDisplayPageLocalService,
		ServiceWrapper<AssetDisplayPageLocalService> {
	public AssetDisplayPageLocalServiceWrapper(
		AssetDisplayPageLocalService assetDisplayPageLocalService) {
		_assetDisplayPageLocalService = assetDisplayPageLocalService;
	}

	/**
	* Adds the asset display page to the database. Also notifies the appropriate model listeners.
	*
	* @param assetDisplayPage the asset display page
	* @return the asset display page that was added
	*/
	@Override
	public com.liferay.asset.display.page.model.AssetDisplayPage addAssetDisplayPage(
		com.liferay.asset.display.page.model.AssetDisplayPage assetDisplayPage) {
		return _assetDisplayPageLocalService.addAssetDisplayPage(assetDisplayPage);
	}

	@Override
	public com.liferay.asset.display.page.model.AssetDisplayPage addAssetDisplayPage(
		long assetEntryId, long layoutId) {
		return _assetDisplayPageLocalService.addAssetDisplayPage(assetEntryId,
			layoutId);
	}

	/**
	* Creates a new asset display page with the primary key. Does not add the asset display page to the database.
	*
	* @param assetDisplayPageId the primary key for the new asset display page
	* @return the new asset display page
	*/
	@Override
	public com.liferay.asset.display.page.model.AssetDisplayPage createAssetDisplayPage(
		long assetDisplayPageId) {
		return _assetDisplayPageLocalService.createAssetDisplayPage(assetDisplayPageId);
	}

	/**
	* Deletes the asset display page from the database. Also notifies the appropriate model listeners.
	*
	* @param assetDisplayPage the asset display page
	* @return the asset display page that was removed
	*/
	@Override
	public com.liferay.asset.display.page.model.AssetDisplayPage deleteAssetDisplayPage(
		com.liferay.asset.display.page.model.AssetDisplayPage assetDisplayPage) {
		return _assetDisplayPageLocalService.deleteAssetDisplayPage(assetDisplayPage);
	}

	/**
	* Deletes the asset display page with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param assetDisplayPageId the primary key of the asset display page
	* @return the asset display page that was removed
	* @throws PortalException if a asset display page with the primary key could not be found
	*/
	@Override
	public com.liferay.asset.display.page.model.AssetDisplayPage deleteAssetDisplayPage(
		long assetDisplayPageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetDisplayPageLocalService.deleteAssetDisplayPage(assetDisplayPageId);
	}

	@Override
	public void deleteAssetDisplayPage(long assetEntryId, long layoutId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_assetDisplayPageLocalService.deleteAssetDisplayPage(assetEntryId,
			layoutId);
	}

	@Override
	public void deleteAssetDisplayPageByAssetEntryId(long assetEntryId) {
		_assetDisplayPageLocalService.deleteAssetDisplayPageByAssetEntryId(assetEntryId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetDisplayPageLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _assetDisplayPageLocalService.dynamicQuery();
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
		return _assetDisplayPageLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.asset.display.page.model.impl.AssetDisplayPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _assetDisplayPageLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.asset.display.page.model.impl.AssetDisplayPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _assetDisplayPageLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
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
		return _assetDisplayPageLocalService.dynamicQueryCount(dynamicQuery);
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
		return _assetDisplayPageLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.asset.display.page.model.AssetDisplayPage fetchAssetDisplayPage(
		long assetDisplayPageId) {
		return _assetDisplayPageLocalService.fetchAssetDisplayPage(assetDisplayPageId);
	}

	@Override
	public com.liferay.asset.display.page.model.AssetDisplayPage fetchAssetDisplayPage(
		long assetEntryId, long layoutId) {
		return _assetDisplayPageLocalService.fetchAssetDisplayPage(assetEntryId,
			layoutId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _assetDisplayPageLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the asset display page with the primary key.
	*
	* @param assetDisplayPageId the primary key of the asset display page
	* @return the asset display page
	* @throws PortalException if a asset display page with the primary key could not be found
	*/
	@Override
	public com.liferay.asset.display.page.model.AssetDisplayPage getAssetDisplayPage(
		long assetDisplayPageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetDisplayPageLocalService.getAssetDisplayPage(assetDisplayPageId);
	}

	/**
	* Returns a range of all the asset display pages.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.asset.display.page.model.impl.AssetDisplayPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset display pages
	* @param end the upper bound of the range of asset display pages (not inclusive)
	* @return the range of asset display pages
	*/
	@Override
	public java.util.List<com.liferay.asset.display.page.model.AssetDisplayPage> getAssetDisplayPages(
		int start, int end) {
		return _assetDisplayPageLocalService.getAssetDisplayPages(start, end);
	}

	/**
	* Returns the number of asset display pages.
	*
	* @return the number of asset display pages
	*/
	@Override
	public int getAssetDisplayPagesCount() {
		return _assetDisplayPageLocalService.getAssetDisplayPagesCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _assetDisplayPageLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _assetDisplayPageLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetDisplayPageLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the asset display page in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param assetDisplayPage the asset display page
	* @return the asset display page that was updated
	*/
	@Override
	public com.liferay.asset.display.page.model.AssetDisplayPage updateAssetDisplayPage(
		com.liferay.asset.display.page.model.AssetDisplayPage assetDisplayPage) {
		return _assetDisplayPageLocalService.updateAssetDisplayPage(assetDisplayPage);
	}

	@Override
	public AssetDisplayPageLocalService getWrappedService() {
		return _assetDisplayPageLocalService;
	}

	@Override
	public void setWrappedService(
		AssetDisplayPageLocalService assetDisplayPageLocalService) {
		_assetDisplayPageLocalService = assetDisplayPageLocalService;
	}

	private AssetDisplayPageLocalService _assetDisplayPageLocalService;
}