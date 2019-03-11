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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AssetListSegmentRelLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see AssetListSegmentRelLocalService
 * @generated
 */
@ProviderType
public class AssetListSegmentRelLocalServiceWrapper
	implements AssetListSegmentRelLocalService,
			   ServiceWrapper<AssetListSegmentRelLocalService> {

	public AssetListSegmentRelLocalServiceWrapper(
		AssetListSegmentRelLocalService assetListSegmentRelLocalService) {

		_assetListSegmentRelLocalService = assetListSegmentRelLocalService;
	}

	/**
	 * Adds the asset list segment rel to the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetListSegmentRel the asset list segment rel
	 * @return the asset list segment rel that was added
	 */
	@Override
	public com.liferay.asset.list.model.AssetListSegmentRel
		addAssetListSegmentRel(
			com.liferay.asset.list.model.AssetListSegmentRel
				assetListSegmentRel) {

		return _assetListSegmentRelLocalService.addAssetListSegmentRel(
			assetListSegmentRel);
	}

	/**
	 * Creates a new asset list segment rel with the primary key. Does not add the asset list segment rel to the database.
	 *
	 * @param assetListSegmentRelId the primary key for the new asset list segment rel
	 * @return the new asset list segment rel
	 */
	@Override
	public com.liferay.asset.list.model.AssetListSegmentRel
		createAssetListSegmentRel(long assetListSegmentRelId) {

		return _assetListSegmentRelLocalService.createAssetListSegmentRel(
			assetListSegmentRelId);
	}

	/**
	 * Deletes the asset list segment rel from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetListSegmentRel the asset list segment rel
	 * @return the asset list segment rel that was removed
	 */
	@Override
	public com.liferay.asset.list.model.AssetListSegmentRel
		deleteAssetListSegmentRel(
			com.liferay.asset.list.model.AssetListSegmentRel
				assetListSegmentRel) {

		return _assetListSegmentRelLocalService.deleteAssetListSegmentRel(
			assetListSegmentRel);
	}

	/**
	 * Deletes the asset list segment rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetListSegmentRelId the primary key of the asset list segment rel
	 * @return the asset list segment rel that was removed
	 * @throws PortalException if a asset list segment rel with the primary key could not be found
	 */
	@Override
	public com.liferay.asset.list.model.AssetListSegmentRel
			deleteAssetListSegmentRel(long assetListSegmentRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetListSegmentRelLocalService.deleteAssetListSegmentRel(
			assetListSegmentRelId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetListSegmentRelLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _assetListSegmentRelLocalService.dynamicQuery();
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

		return _assetListSegmentRelLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.asset.list.model.impl.AssetListSegmentRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

		return _assetListSegmentRelLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.asset.list.model.impl.AssetListSegmentRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

		return _assetListSegmentRelLocalService.dynamicQuery(
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

		return _assetListSegmentRelLocalService.dynamicQueryCount(dynamicQuery);
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

		return _assetListSegmentRelLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.asset.list.model.AssetListSegmentRel
		fetchAssetListSegmentRel(long assetListSegmentRelId) {

		return _assetListSegmentRelLocalService.fetchAssetListSegmentRel(
			assetListSegmentRelId);
	}

	/**
	 * Returns the asset list segment rel matching the UUID and group.
	 *
	 * @param uuid the asset list segment rel's UUID
	 * @param groupId the primary key of the group
	 * @return the matching asset list segment rel, or <code>null</code> if a matching asset list segment rel could not be found
	 */
	@Override
	public com.liferay.asset.list.model.AssetListSegmentRel
		fetchAssetListSegmentRelByUuidAndGroupId(String uuid, long groupId) {

		return _assetListSegmentRelLocalService.
			fetchAssetListSegmentRelByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _assetListSegmentRelLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the asset list segment rel with the primary key.
	 *
	 * @param assetListSegmentRelId the primary key of the asset list segment rel
	 * @return the asset list segment rel
	 * @throws PortalException if a asset list segment rel with the primary key could not be found
	 */
	@Override
	public com.liferay.asset.list.model.AssetListSegmentRel
			getAssetListSegmentRel(long assetListSegmentRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetListSegmentRelLocalService.getAssetListSegmentRel(
			assetListSegmentRelId);
	}

	/**
	 * Returns the asset list segment rel matching the UUID and group.
	 *
	 * @param uuid the asset list segment rel's UUID
	 * @param groupId the primary key of the group
	 * @return the matching asset list segment rel
	 * @throws PortalException if a matching asset list segment rel could not be found
	 */
	@Override
	public com.liferay.asset.list.model.AssetListSegmentRel
			getAssetListSegmentRelByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetListSegmentRelLocalService.
			getAssetListSegmentRelByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the asset list segment rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.asset.list.model.impl.AssetListSegmentRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset list segment rels
	 * @param end the upper bound of the range of asset list segment rels (not inclusive)
	 * @return the range of asset list segment rels
	 */
	@Override
	public java.util.List<com.liferay.asset.list.model.AssetListSegmentRel>
		getAssetListSegmentRels(int start, int end) {

		return _assetListSegmentRelLocalService.getAssetListSegmentRels(
			start, end);
	}

	/**
	 * Returns all the asset list segment rels matching the UUID and company.
	 *
	 * @param uuid the UUID of the asset list segment rels
	 * @param companyId the primary key of the company
	 * @return the matching asset list segment rels, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.asset.list.model.AssetListSegmentRel>
		getAssetListSegmentRelsByUuidAndCompanyId(String uuid, long companyId) {

		return _assetListSegmentRelLocalService.
			getAssetListSegmentRelsByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of asset list segment rels matching the UUID and company.
	 *
	 * @param uuid the UUID of the asset list segment rels
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of asset list segment rels
	 * @param end the upper bound of the range of asset list segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching asset list segment rels, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.asset.list.model.AssetListSegmentRel>
		getAssetListSegmentRelsByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.asset.list.model.AssetListSegmentRel>
					orderByComparator) {

		return _assetListSegmentRelLocalService.
			getAssetListSegmentRelsByUuidAndCompanyId(
				uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of asset list segment rels.
	 *
	 * @return the number of asset list segment rels
	 */
	@Override
	public int getAssetListSegmentRelsCount() {
		return _assetListSegmentRelLocalService.getAssetListSegmentRelsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _assetListSegmentRelLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _assetListSegmentRelLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _assetListSegmentRelLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetListSegmentRelLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the asset list segment rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param assetListSegmentRel the asset list segment rel
	 * @return the asset list segment rel that was updated
	 */
	@Override
	public com.liferay.asset.list.model.AssetListSegmentRel
		updateAssetListSegmentRel(
			com.liferay.asset.list.model.AssetListSegmentRel
				assetListSegmentRel) {

		return _assetListSegmentRelLocalService.updateAssetListSegmentRel(
			assetListSegmentRel);
	}

	@Override
	public AssetListSegmentRelLocalService getWrappedService() {
		return _assetListSegmentRelLocalService;
	}

	@Override
	public void setWrappedService(
		AssetListSegmentRelLocalService assetListSegmentRelLocalService) {

		_assetListSegmentRelLocalService = assetListSegmentRelLocalService;
	}

	private AssetListSegmentRelLocalService _assetListSegmentRelLocalService;

}