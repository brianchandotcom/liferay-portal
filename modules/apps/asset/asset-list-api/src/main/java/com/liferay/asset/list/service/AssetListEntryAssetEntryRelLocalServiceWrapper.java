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
 * Provides a wrapper for {@link AssetListEntryAssetEntryRelLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see AssetListEntryAssetEntryRelLocalService
 * @generated
 */
public class AssetListEntryAssetEntryRelLocalServiceWrapper
	implements AssetListEntryAssetEntryRelLocalService,
			   ServiceWrapper<AssetListEntryAssetEntryRelLocalService> {

	public AssetListEntryAssetEntryRelLocalServiceWrapper(
		AssetListEntryAssetEntryRelLocalService
			assetListEntryAssetEntryRelLocalService) {

		_assetListEntryAssetEntryRelLocalService =
			assetListEntryAssetEntryRelLocalService;
	}

	/**
	 * Adds the asset list entry asset entry rel to the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetListEntryAssetEntryRel the asset list entry asset entry rel
	 * @return the asset list entry asset entry rel that was added
	 */
	@Override
	public com.liferay.asset.list.model.AssetListEntryAssetEntryRel
		addAssetListEntryAssetEntryRel(
			com.liferay.asset.list.model.AssetListEntryAssetEntryRel
				assetListEntryAssetEntryRel) {

		return _assetListEntryAssetEntryRelLocalService.
			addAssetListEntryAssetEntryRel(assetListEntryAssetEntryRel);
	}

	@Override
	public com.liferay.asset.list.model.AssetListEntryAssetEntryRel
			addAssetListEntryAssetEntryRel(
				long assetListEntryId, long assetEntryId, long segmentsEntryId,
				int position,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetListEntryAssetEntryRelLocalService.
			addAssetListEntryAssetEntryRel(
				assetListEntryId, assetEntryId, segmentsEntryId, position,
				serviceContext);
	}

	@Override
	public com.liferay.asset.list.model.AssetListEntryAssetEntryRel
			addAssetListEntryAssetEntryRel(
				long assetListEntryId, long assetEntryId, long segmentsEntryId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetListEntryAssetEntryRelLocalService.
			addAssetListEntryAssetEntryRel(
				assetListEntryId, assetEntryId, segmentsEntryId,
				serviceContext);
	}

	/**
	 * Creates a new asset list entry asset entry rel with the primary key. Does not add the asset list entry asset entry rel to the database.
	 *
	 * @param assetListEntryAssetEntryRelId the primary key for the new asset list entry asset entry rel
	 * @return the new asset list entry asset entry rel
	 */
	@Override
	public com.liferay.asset.list.model.AssetListEntryAssetEntryRel
		createAssetListEntryAssetEntryRel(long assetListEntryAssetEntryRelId) {

		return _assetListEntryAssetEntryRelLocalService.
			createAssetListEntryAssetEntryRel(assetListEntryAssetEntryRelId);
	}

	/**
	 * Deletes the asset list entry asset entry rel from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetListEntryAssetEntryRel the asset list entry asset entry rel
	 * @return the asset list entry asset entry rel that was removed
	 */
	@Override
	public com.liferay.asset.list.model.AssetListEntryAssetEntryRel
		deleteAssetListEntryAssetEntryRel(
			com.liferay.asset.list.model.AssetListEntryAssetEntryRel
				assetListEntryAssetEntryRel) {

		return _assetListEntryAssetEntryRelLocalService.
			deleteAssetListEntryAssetEntryRel(assetListEntryAssetEntryRel);
	}

	/**
	 * Deletes the asset list entry asset entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetListEntryAssetEntryRelId the primary key of the asset list entry asset entry rel
	 * @return the asset list entry asset entry rel that was removed
	 * @throws PortalException if a asset list entry asset entry rel with the primary key could not be found
	 */
	@Override
	public com.liferay.asset.list.model.AssetListEntryAssetEntryRel
			deleteAssetListEntryAssetEntryRel(
				long assetListEntryAssetEntryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetListEntryAssetEntryRelLocalService.
			deleteAssetListEntryAssetEntryRel(assetListEntryAssetEntryRelId);
	}

	@Override
	public com.liferay.asset.list.model.AssetListEntryAssetEntryRel
			deleteAssetListEntryAssetEntryRel(
				long assetListEntryId, long segmentsEntryId, int position)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetListEntryAssetEntryRelLocalService.
			deleteAssetListEntryAssetEntryRel(
				assetListEntryId, segmentsEntryId, position);
	}

	@Override
	public void deleteAssetListEntryAssetEntryRelByAssetListEntryId(
		long assetListEntryId) {

		_assetListEntryAssetEntryRelLocalService.
			deleteAssetListEntryAssetEntryRelByAssetListEntryId(
				assetListEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetListEntryAssetEntryRelLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _assetListEntryAssetEntryRelLocalService.dynamicQuery();
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

		return _assetListEntryAssetEntryRelLocalService.dynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.asset.list.model.impl.AssetListEntryAssetEntryRelModelImpl</code>.
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

		return _assetListEntryAssetEntryRelLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.asset.list.model.impl.AssetListEntryAssetEntryRelModelImpl</code>.
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

		return _assetListEntryAssetEntryRelLocalService.dynamicQuery(
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

		return _assetListEntryAssetEntryRelLocalService.dynamicQueryCount(
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

		return _assetListEntryAssetEntryRelLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.asset.list.model.AssetListEntryAssetEntryRel
		fetchAssetListEntryAssetEntryRel(long assetListEntryAssetEntryRelId) {

		return _assetListEntryAssetEntryRelLocalService.
			fetchAssetListEntryAssetEntryRel(assetListEntryAssetEntryRelId);
	}

	/**
	 * Returns the asset list entry asset entry rel matching the UUID and group.
	 *
	 * @param uuid the asset list entry asset entry rel's UUID
	 * @param groupId the primary key of the group
	 * @return the matching asset list entry asset entry rel, or <code>null</code> if a matching asset list entry asset entry rel could not be found
	 */
	@Override
	public com.liferay.asset.list.model.AssetListEntryAssetEntryRel
		fetchAssetListEntryAssetEntryRelByUuidAndGroupId(
			String uuid, long groupId) {

		return _assetListEntryAssetEntryRelLocalService.
			fetchAssetListEntryAssetEntryRelByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _assetListEntryAssetEntryRelLocalService.
			getActionableDynamicQuery();
	}

	/**
	 * Returns the asset list entry asset entry rel with the primary key.
	 *
	 * @param assetListEntryAssetEntryRelId the primary key of the asset list entry asset entry rel
	 * @return the asset list entry asset entry rel
	 * @throws PortalException if a asset list entry asset entry rel with the primary key could not be found
	 */
	@Override
	public com.liferay.asset.list.model.AssetListEntryAssetEntryRel
			getAssetListEntryAssetEntryRel(long assetListEntryAssetEntryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetListEntryAssetEntryRelLocalService.
			getAssetListEntryAssetEntryRel(assetListEntryAssetEntryRelId);
	}

	/**
	 * Returns the asset list entry asset entry rel matching the UUID and group.
	 *
	 * @param uuid the asset list entry asset entry rel's UUID
	 * @param groupId the primary key of the group
	 * @return the matching asset list entry asset entry rel
	 * @throws PortalException if a matching asset list entry asset entry rel could not be found
	 */
	@Override
	public com.liferay.asset.list.model.AssetListEntryAssetEntryRel
			getAssetListEntryAssetEntryRelByUuidAndGroupId(
				String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetListEntryAssetEntryRelLocalService.
			getAssetListEntryAssetEntryRelByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the asset list entry asset entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.asset.list.model.impl.AssetListEntryAssetEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset list entry asset entry rels
	 * @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	 * @return the range of asset list entry asset entry rels
	 */
	@Override
	public java.util.List
		<com.liferay.asset.list.model.AssetListEntryAssetEntryRel>
			getAssetListEntryAssetEntryRels(int start, int end) {

		return _assetListEntryAssetEntryRelLocalService.
			getAssetListEntryAssetEntryRels(start, end);
	}

	@Override
	public java.util.List
		<com.liferay.asset.list.model.AssetListEntryAssetEntryRel>
			getAssetListEntryAssetEntryRels(
				long assetListEntryId, int start, int end) {

		return _assetListEntryAssetEntryRelLocalService.
			getAssetListEntryAssetEntryRels(assetListEntryId, start, end);
	}

	@Override
	public java.util.List
		<com.liferay.asset.list.model.AssetListEntryAssetEntryRel>
			getAssetListEntryAssetEntryRels(
				long assetListEntryId, long segmentsEntryId, int start,
				int end) {

		return _assetListEntryAssetEntryRelLocalService.
			getAssetListEntryAssetEntryRels(
				assetListEntryId, segmentsEntryId, start, end);
	}

	/**
	 * Returns all the asset list entry asset entry rels matching the UUID and company.
	 *
	 * @param uuid the UUID of the asset list entry asset entry rels
	 * @param companyId the primary key of the company
	 * @return the matching asset list entry asset entry rels, or an empty list if no matches were found
	 */
	@Override
	public java.util.List
		<com.liferay.asset.list.model.AssetListEntryAssetEntryRel>
			getAssetListEntryAssetEntryRelsByUuidAndCompanyId(
				String uuid, long companyId) {

		return _assetListEntryAssetEntryRelLocalService.
			getAssetListEntryAssetEntryRelsByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of asset list entry asset entry rels matching the UUID and company.
	 *
	 * @param uuid the UUID of the asset list entry asset entry rels
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of asset list entry asset entry rels
	 * @param end the upper bound of the range of asset list entry asset entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching asset list entry asset entry rels, or an empty list if no matches were found
	 */
	@Override
	public java.util.List
		<com.liferay.asset.list.model.AssetListEntryAssetEntryRel>
			getAssetListEntryAssetEntryRelsByUuidAndCompanyId(
				String uuid, long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.asset.list.model.AssetListEntryAssetEntryRel>
						orderByComparator) {

		return _assetListEntryAssetEntryRelLocalService.
			getAssetListEntryAssetEntryRelsByUuidAndCompanyId(
				uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of asset list entry asset entry rels.
	 *
	 * @return the number of asset list entry asset entry rels
	 */
	@Override
	public int getAssetListEntryAssetEntryRelsCount() {
		return _assetListEntryAssetEntryRelLocalService.
			getAssetListEntryAssetEntryRelsCount();
	}

	@Override
	public int getAssetListEntryAssetEntryRelsCount(long assetListEntryId) {
		return _assetListEntryAssetEntryRelLocalService.
			getAssetListEntryAssetEntryRelsCount(assetListEntryId);
	}

	@Override
	public int getAssetListEntryAssetEntryRelsCount(
		long assetListEntryId, long segmentsEntryId) {

		return _assetListEntryAssetEntryRelLocalService.
			getAssetListEntryAssetEntryRelsCount(
				assetListEntryId, segmentsEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _assetListEntryAssetEntryRelLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _assetListEntryAssetEntryRelLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _assetListEntryAssetEntryRelLocalService.
			getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetListEntryAssetEntryRelLocalService.getPersistedModel(
			primaryKeyObj);
	}

	@Override
	public com.liferay.asset.list.model.AssetListEntryAssetEntryRel
			moveAssetListEntryAssetEntryRel(
				long assetListEntryId, long segmentsEntryId, int position,
				int newPosition)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetListEntryAssetEntryRelLocalService.
			moveAssetListEntryAssetEntryRel(
				assetListEntryId, segmentsEntryId, position, newPosition);
	}

	/**
	 * Updates the asset list entry asset entry rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param assetListEntryAssetEntryRel the asset list entry asset entry rel
	 * @return the asset list entry asset entry rel that was updated
	 */
	@Override
	public com.liferay.asset.list.model.AssetListEntryAssetEntryRel
		updateAssetListEntryAssetEntryRel(
			com.liferay.asset.list.model.AssetListEntryAssetEntryRel
				assetListEntryAssetEntryRel) {

		return _assetListEntryAssetEntryRelLocalService.
			updateAssetListEntryAssetEntryRel(assetListEntryAssetEntryRel);
	}

	@Override
	public com.liferay.asset.list.model.AssetListEntryAssetEntryRel
			updateAssetListEntryAssetEntryRel(
				long assetListEntryAssetEntryRelId, long assetListEntryId,
				long assetEntryId, long segmentsEntryId, int position)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetListEntryAssetEntryRelLocalService.
			updateAssetListEntryAssetEntryRel(
				assetListEntryAssetEntryRelId, assetListEntryId, assetEntryId,
				segmentsEntryId, position);
	}

	@Override
	public AssetListEntryAssetEntryRelLocalService getWrappedService() {
		return _assetListEntryAssetEntryRelLocalService;
	}

	@Override
	public void setWrappedService(
		AssetListEntryAssetEntryRelLocalService
			assetListEntryAssetEntryRelLocalService) {

		_assetListEntryAssetEntryRelLocalService =
			assetListEntryAssetEntryRelLocalService;
	}

	private AssetListEntryAssetEntryRelLocalService
		_assetListEntryAssetEntryRelLocalService;

}