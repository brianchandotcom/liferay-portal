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
 * Provides a wrapper for {@link AssetEntryAssetListEntryRelLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryAssetListEntryRelLocalService
 * @generated
 */
@ProviderType
public class AssetEntryAssetListEntryRelLocalServiceWrapper
	implements AssetEntryAssetListEntryRelLocalService,
			   ServiceWrapper<AssetEntryAssetListEntryRelLocalService> {

	public AssetEntryAssetListEntryRelLocalServiceWrapper(
		AssetEntryAssetListEntryRelLocalService
			assetEntryAssetListEntryRelLocalService) {

		_assetEntryAssetListEntryRelLocalService =
			assetEntryAssetListEntryRelLocalService;
	}

	/**
	 * Adds the asset entry asset list entry rel to the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetEntryAssetListEntryRel the asset entry asset list entry rel
	 * @return the asset entry asset list entry rel that was added
	 */
	@Override
	public com.liferay.asset.list.model.AssetEntryAssetListEntryRel
		addAssetEntryAssetListEntryRel(
			com.liferay.asset.list.model.AssetEntryAssetListEntryRel
				assetEntryAssetListEntryRel) {

		return _assetEntryAssetListEntryRelLocalService.
			addAssetEntryAssetListEntryRel(assetEntryAssetListEntryRel);
	}

	@Override
	public com.liferay.asset.list.model.AssetEntryAssetListEntryRel
			addAssetEntryAssetListEntryRel(
				long assetEntryId, long assetListEntryId, long segmentsEntryId,
				int position,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetEntryAssetListEntryRelLocalService.
			addAssetEntryAssetListEntryRel(
				assetEntryId, assetListEntryId, segmentsEntryId, position,
				serviceContext);
	}

	@Override
	public com.liferay.asset.list.model.AssetEntryAssetListEntryRel
			addAssetEntryAssetListEntryRel(
				long assetEntryId, long assetListEntryId, long segmentsEntryId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetEntryAssetListEntryRelLocalService.
			addAssetEntryAssetListEntryRel(
				assetEntryId, assetListEntryId, segmentsEntryId,
				serviceContext);
	}

	/**
	 * Creates a new asset entry asset list entry rel with the primary key. Does not add the asset entry asset list entry rel to the database.
	 *
	 * @param assetEntryAssetListEntryRelId the primary key for the new asset entry asset list entry rel
	 * @return the new asset entry asset list entry rel
	 */
	@Override
	public com.liferay.asset.list.model.AssetEntryAssetListEntryRel
		createAssetEntryAssetListEntryRel(long assetEntryAssetListEntryRelId) {

		return _assetEntryAssetListEntryRelLocalService.
			createAssetEntryAssetListEntryRel(assetEntryAssetListEntryRelId);
	}

	/**
	 * Deletes the asset entry asset list entry rel from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetEntryAssetListEntryRel the asset entry asset list entry rel
	 * @return the asset entry asset list entry rel that was removed
	 */
	@Override
	public com.liferay.asset.list.model.AssetEntryAssetListEntryRel
		deleteAssetEntryAssetListEntryRel(
			com.liferay.asset.list.model.AssetEntryAssetListEntryRel
				assetEntryAssetListEntryRel) {

		return _assetEntryAssetListEntryRelLocalService.
			deleteAssetEntryAssetListEntryRel(assetEntryAssetListEntryRel);
	}

	/**
	 * Deletes the asset entry asset list entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetEntryAssetListEntryRelId the primary key of the asset entry asset list entry rel
	 * @return the asset entry asset list entry rel that was removed
	 * @throws PortalException if a asset entry asset list entry rel with the primary key could not be found
	 */
	@Override
	public com.liferay.asset.list.model.AssetEntryAssetListEntryRel
			deleteAssetEntryAssetListEntryRel(
				long assetEntryAssetListEntryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetEntryAssetListEntryRelLocalService.
			deleteAssetEntryAssetListEntryRel(assetEntryAssetListEntryRelId);
	}

	@Override
	public com.liferay.asset.list.model.AssetEntryAssetListEntryRel
			deleteAssetEntryAssetListEntryRel(
				long assetListEntryId, long segmentsEntryId, int position)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetEntryAssetListEntryRelLocalService.
			deleteAssetEntryAssetListEntryRel(
				assetListEntryId, segmentsEntryId, position);
	}

	@Override
	public void deleteAssetEntryAssetListEntryRelByAssetListEntryId(
		long assetListEntryId) {

		_assetEntryAssetListEntryRelLocalService.
			deleteAssetEntryAssetListEntryRelByAssetListEntryId(
				assetListEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetEntryAssetListEntryRelLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _assetEntryAssetListEntryRelLocalService.dynamicQuery();
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

		return _assetEntryAssetListEntryRelLocalService.dynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.asset.list.model.impl.AssetEntryAssetListEntryRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

		return _assetEntryAssetListEntryRelLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.asset.list.model.impl.AssetEntryAssetListEntryRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

		return _assetEntryAssetListEntryRelLocalService.dynamicQuery(
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

		return _assetEntryAssetListEntryRelLocalService.dynamicQueryCount(
			dynamicQuery);
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

		return _assetEntryAssetListEntryRelLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.asset.list.model.AssetEntryAssetListEntryRel
		fetchAssetEntryAssetListEntryRel(long assetEntryAssetListEntryRelId) {

		return _assetEntryAssetListEntryRelLocalService.
			fetchAssetEntryAssetListEntryRel(assetEntryAssetListEntryRelId);
	}

	/**
	 * Returns the asset entry asset list entry rel matching the UUID and group.
	 *
	 * @param uuid the asset entry asset list entry rel's UUID
	 * @param groupId the primary key of the group
	 * @return the matching asset entry asset list entry rel, or <code>null</code> if a matching asset entry asset list entry rel could not be found
	 */
	@Override
	public com.liferay.asset.list.model.AssetEntryAssetListEntryRel
		fetchAssetEntryAssetListEntryRelByUuidAndGroupId(
			String uuid, long groupId) {

		return _assetEntryAssetListEntryRelLocalService.
			fetchAssetEntryAssetListEntryRelByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _assetEntryAssetListEntryRelLocalService.
			getActionableDynamicQuery();
	}

	/**
	 * Returns the asset entry asset list entry rel with the primary key.
	 *
	 * @param assetEntryAssetListEntryRelId the primary key of the asset entry asset list entry rel
	 * @return the asset entry asset list entry rel
	 * @throws PortalException if a asset entry asset list entry rel with the primary key could not be found
	 */
	@Override
	public com.liferay.asset.list.model.AssetEntryAssetListEntryRel
			getAssetEntryAssetListEntryRel(long assetEntryAssetListEntryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetEntryAssetListEntryRelLocalService.
			getAssetEntryAssetListEntryRel(assetEntryAssetListEntryRelId);
	}

	/**
	 * Returns the asset entry asset list entry rel matching the UUID and group.
	 *
	 * @param uuid the asset entry asset list entry rel's UUID
	 * @param groupId the primary key of the group
	 * @return the matching asset entry asset list entry rel
	 * @throws PortalException if a matching asset entry asset list entry rel could not be found
	 */
	@Override
	public com.liferay.asset.list.model.AssetEntryAssetListEntryRel
			getAssetEntryAssetListEntryRelByUuidAndGroupId(
				String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetEntryAssetListEntryRelLocalService.
			getAssetEntryAssetListEntryRelByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the asset entry asset list entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.asset.list.model.impl.AssetEntryAssetListEntryRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entry asset list entry rels
	 * @param end the upper bound of the range of asset entry asset list entry rels (not inclusive)
	 * @return the range of asset entry asset list entry rels
	 */
	@Override
	public java.util.List
		<com.liferay.asset.list.model.AssetEntryAssetListEntryRel>
			getAssetEntryAssetListEntryRels(int start, int end) {

		return _assetEntryAssetListEntryRelLocalService.
			getAssetEntryAssetListEntryRels(start, end);
	}

	@Override
	public java.util.List
		<com.liferay.asset.list.model.AssetEntryAssetListEntryRel>
			getAssetEntryAssetListEntryRels(
				long assetListEntryId, int start, int end) {

		return _assetEntryAssetListEntryRelLocalService.
			getAssetEntryAssetListEntryRels(assetListEntryId, start, end);
	}

	@Override
	public java.util.List
		<com.liferay.asset.list.model.AssetEntryAssetListEntryRel>
			getAssetEntryAssetListEntryRels(
				long assetListEntryId, long segmentsEntryId, int start,
				int end) {

		return _assetEntryAssetListEntryRelLocalService.
			getAssetEntryAssetListEntryRels(
				assetListEntryId, segmentsEntryId, start, end);
	}

	/**
	 * Returns all the asset entry asset list entry rels matching the UUID and company.
	 *
	 * @param uuid the UUID of the asset entry asset list entry rels
	 * @param companyId the primary key of the company
	 * @return the matching asset entry asset list entry rels, or an empty list if no matches were found
	 */
	@Override
	public java.util.List
		<com.liferay.asset.list.model.AssetEntryAssetListEntryRel>
			getAssetEntryAssetListEntryRelsByUuidAndCompanyId(
				String uuid, long companyId) {

		return _assetEntryAssetListEntryRelLocalService.
			getAssetEntryAssetListEntryRelsByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of asset entry asset list entry rels matching the UUID and company.
	 *
	 * @param uuid the UUID of the asset entry asset list entry rels
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of asset entry asset list entry rels
	 * @param end the upper bound of the range of asset entry asset list entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching asset entry asset list entry rels, or an empty list if no matches were found
	 */
	@Override
	public java.util.List
		<com.liferay.asset.list.model.AssetEntryAssetListEntryRel>
			getAssetEntryAssetListEntryRelsByUuidAndCompanyId(
				String uuid, long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.asset.list.model.AssetEntryAssetListEntryRel>
						orderByComparator) {

		return _assetEntryAssetListEntryRelLocalService.
			getAssetEntryAssetListEntryRelsByUuidAndCompanyId(
				uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of asset entry asset list entry rels.
	 *
	 * @return the number of asset entry asset list entry rels
	 */
	@Override
	public int getAssetEntryAssetListEntryRelsCount() {
		return _assetEntryAssetListEntryRelLocalService.
			getAssetEntryAssetListEntryRelsCount();
	}

	@Override
	public int getAssetEntryAssetListEntryRelsCount(long assetListEntryId) {
		return _assetEntryAssetListEntryRelLocalService.
			getAssetEntryAssetListEntryRelsCount(assetListEntryId);
	}

	@Override
	public int getAssetEntryAssetListEntryRelsCount(
		long assetListEntryId, long segmentsEntryId) {

		return _assetEntryAssetListEntryRelLocalService.
			getAssetEntryAssetListEntryRelsCount(
				assetListEntryId, segmentsEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _assetEntryAssetListEntryRelLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _assetEntryAssetListEntryRelLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _assetEntryAssetListEntryRelLocalService.
			getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetEntryAssetListEntryRelLocalService.getPersistedModel(
			primaryKeyObj);
	}

	@Override
	public com.liferay.asset.list.model.AssetEntryAssetListEntryRel
			moveAssetEntryAssetListEntryRel(
				long assetListEntryId, long segmentsEntryId, int position,
				int newPosition)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetEntryAssetListEntryRelLocalService.
			moveAssetEntryAssetListEntryRel(
				assetListEntryId, segmentsEntryId, position, newPosition);
	}

	/**
	 * Updates the asset entry asset list entry rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param assetEntryAssetListEntryRel the asset entry asset list entry rel
	 * @return the asset entry asset list entry rel that was updated
	 */
	@Override
	public com.liferay.asset.list.model.AssetEntryAssetListEntryRel
		updateAssetEntryAssetListEntryRel(
			com.liferay.asset.list.model.AssetEntryAssetListEntryRel
				assetEntryAssetListEntryRel) {

		return _assetEntryAssetListEntryRelLocalService.
			updateAssetEntryAssetListEntryRel(assetEntryAssetListEntryRel);
	}

	@Override
	public com.liferay.asset.list.model.AssetEntryAssetListEntryRel
			updateAssetEntryAssetListEntryRel(
				long assetEntryAssetListEntryRelId, long assetEntryId,
				long assetListEntryId, long segmentsEntryId, int position)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetEntryAssetListEntryRelLocalService.
			updateAssetEntryAssetListEntryRel(
				assetEntryAssetListEntryRelId, assetEntryId, assetListEntryId,
				segmentsEntryId, position);
	}

	@Override
	public AssetEntryAssetListEntryRelLocalService getWrappedService() {
		return _assetEntryAssetListEntryRelLocalService;
	}

	@Override
	public void setWrappedService(
		AssetEntryAssetListEntryRelLocalService
			assetEntryAssetListEntryRelLocalService) {

		_assetEntryAssetListEntryRelLocalService =
			assetEntryAssetListEntryRelLocalService;
	}

	private AssetEntryAssetListEntryRelLocalService
		_assetEntryAssetListEntryRelLocalService;

}