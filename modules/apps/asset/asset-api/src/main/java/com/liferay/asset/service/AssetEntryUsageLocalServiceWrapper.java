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

package com.liferay.asset.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AssetEntryUsageLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryUsageLocalService
 * @deprecated As of Mueller (7.2.x), replaced by {@link
 com.liferay.layout.service.impl.LayoutClassedModelUsageLocalServiceImpl}
 * @generated
 */
@Deprecated
public class AssetEntryUsageLocalServiceWrapper
	implements AssetEntryUsageLocalService,
			   ServiceWrapper<AssetEntryUsageLocalService> {

	public AssetEntryUsageLocalServiceWrapper(
		AssetEntryUsageLocalService assetEntryUsageLocalService) {

		_assetEntryUsageLocalService = assetEntryUsageLocalService;
	}

	/**
	 * Adds the asset entry usage to the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetEntryUsage the asset entry usage
	 * @return the asset entry usage that was added
	 */
	@Override
	public com.liferay.asset.model.AssetEntryUsage addAssetEntryUsage(
		com.liferay.asset.model.AssetEntryUsage assetEntryUsage) {

		return _assetEntryUsageLocalService.addAssetEntryUsage(assetEntryUsage);
	}

	@Override
	public com.liferay.asset.model.AssetEntryUsage addAssetEntryUsage(
		long groupId, long assetEntryId, long containerType,
		String containerKey, long plid,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _assetEntryUsageLocalService.addAssetEntryUsage(
			groupId, assetEntryId, containerType, containerKey, plid,
			serviceContext);
	}

	@Override
	public com.liferay.asset.model.AssetEntryUsage addDefaultAssetEntryUsage(
		long groupId, long assetEntryId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _assetEntryUsageLocalService.addDefaultAssetEntryUsage(
			groupId, assetEntryId, serviceContext);
	}

	/**
	 * Creates a new asset entry usage with the primary key. Does not add the asset entry usage to the database.
	 *
	 * @param assetEntryUsageId the primary key for the new asset entry usage
	 * @return the new asset entry usage
	 */
	@Override
	public com.liferay.asset.model.AssetEntryUsage createAssetEntryUsage(
		long assetEntryUsageId) {

		return _assetEntryUsageLocalService.createAssetEntryUsage(
			assetEntryUsageId);
	}

	/**
	 * Deletes the asset entry usage from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetEntryUsage the asset entry usage
	 * @return the asset entry usage that was removed
	 */
	@Override
	public com.liferay.asset.model.AssetEntryUsage deleteAssetEntryUsage(
		com.liferay.asset.model.AssetEntryUsage assetEntryUsage) {

		return _assetEntryUsageLocalService.deleteAssetEntryUsage(
			assetEntryUsage);
	}

	/**
	 * Deletes the asset entry usage with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetEntryUsageId the primary key of the asset entry usage
	 * @return the asset entry usage that was removed
	 * @throws PortalException if a asset entry usage with the primary key could not be found
	 */
	@Override
	public com.liferay.asset.model.AssetEntryUsage deleteAssetEntryUsage(
			long assetEntryUsageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetEntryUsageLocalService.deleteAssetEntryUsage(
			assetEntryUsageId);
	}

	@Override
	public void deleteAssetEntryUsages(long assetEntryId) {
		_assetEntryUsageLocalService.deleteAssetEntryUsages(assetEntryId);
	}

	@Override
	public void deleteAssetEntryUsages(
		long containerType, String containerKey, long plid) {

		_assetEntryUsageLocalService.deleteAssetEntryUsages(
			containerType, containerKey, plid);
	}

	@Override
	public void deleteAssetEntryUsagesByPlid(long plid) {
		_assetEntryUsageLocalService.deleteAssetEntryUsagesByPlid(plid);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetEntryUsageLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _assetEntryUsageLocalService.dynamicQuery();
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

		return _assetEntryUsageLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.asset.model.impl.AssetEntryUsageModelImpl</code>.
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

		return _assetEntryUsageLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.asset.model.impl.AssetEntryUsageModelImpl</code>.
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

		return _assetEntryUsageLocalService.dynamicQuery(
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

		return _assetEntryUsageLocalService.dynamicQueryCount(dynamicQuery);
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

		return _assetEntryUsageLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.asset.model.AssetEntryUsage fetchAssetEntryUsage(
		long assetEntryUsageId) {

		return _assetEntryUsageLocalService.fetchAssetEntryUsage(
			assetEntryUsageId);
	}

	@Override
	public com.liferay.asset.model.AssetEntryUsage fetchAssetEntryUsage(
		long assetEntryId, long containerType, String containerKey, long plid) {

		return _assetEntryUsageLocalService.fetchAssetEntryUsage(
			assetEntryId, containerType, containerKey, plid);
	}

	/**
	 * Returns the asset entry usage matching the UUID and group.
	 *
	 * @param uuid the asset entry usage's UUID
	 * @param groupId the primary key of the group
	 * @return the matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	 */
	@Override
	public com.liferay.asset.model.AssetEntryUsage
		fetchAssetEntryUsageByUuidAndGroupId(String uuid, long groupId) {

		return _assetEntryUsageLocalService.
			fetchAssetEntryUsageByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _assetEntryUsageLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the asset entry usage with the primary key.
	 *
	 * @param assetEntryUsageId the primary key of the asset entry usage
	 * @return the asset entry usage
	 * @throws PortalException if a asset entry usage with the primary key could not be found
	 */
	@Override
	public com.liferay.asset.model.AssetEntryUsage getAssetEntryUsage(
			long assetEntryUsageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetEntryUsageLocalService.getAssetEntryUsage(
			assetEntryUsageId);
	}

	/**
	 * Returns the asset entry usage matching the UUID and group.
	 *
	 * @param uuid the asset entry usage's UUID
	 * @param groupId the primary key of the group
	 * @return the matching asset entry usage
	 * @throws PortalException if a matching asset entry usage could not be found
	 */
	@Override
	public com.liferay.asset.model.AssetEntryUsage
			getAssetEntryUsageByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetEntryUsageLocalService.getAssetEntryUsageByUuidAndGroupId(
			uuid, groupId);
	}

	/**
	 * Returns a range of all the asset entry usages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.asset.model.impl.AssetEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @return the range of asset entry usages
	 */
	@Override
	public java.util.List<com.liferay.asset.model.AssetEntryUsage>
		getAssetEntryUsages(int start, int end) {

		return _assetEntryUsageLocalService.getAssetEntryUsages(start, end);
	}

	@Override
	public java.util.List<com.liferay.asset.model.AssetEntryUsage>
		getAssetEntryUsages(long assetEntryId) {

		return _assetEntryUsageLocalService.getAssetEntryUsages(assetEntryId);
	}

	@Override
	public java.util.List<com.liferay.asset.model.AssetEntryUsage>
		getAssetEntryUsages(
			long assetEntryId, int type, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.asset.model.AssetEntryUsage> orderByComparator) {

		return _assetEntryUsageLocalService.getAssetEntryUsages(
			assetEntryId, type, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.asset.model.AssetEntryUsage>
		getAssetEntryUsages(
			long assetEntryId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.asset.model.AssetEntryUsage> orderByComparator) {

		return _assetEntryUsageLocalService.getAssetEntryUsages(
			assetEntryId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.asset.model.AssetEntryUsage>
		getAssetEntryUsagesByPlid(long plid) {

		return _assetEntryUsageLocalService.getAssetEntryUsagesByPlid(plid);
	}

	/**
	 * Returns all the asset entry usages matching the UUID and company.
	 *
	 * @param uuid the UUID of the asset entry usages
	 * @param companyId the primary key of the company
	 * @return the matching asset entry usages, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.asset.model.AssetEntryUsage>
		getAssetEntryUsagesByUuidAndCompanyId(String uuid, long companyId) {

		return _assetEntryUsageLocalService.
			getAssetEntryUsagesByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of asset entry usages matching the UUID and company.
	 *
	 * @param uuid the UUID of the asset entry usages
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of asset entry usages
	 * @param end the upper bound of the range of asset entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching asset entry usages, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.asset.model.AssetEntryUsage>
		getAssetEntryUsagesByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.asset.model.AssetEntryUsage> orderByComparator) {

		return _assetEntryUsageLocalService.
			getAssetEntryUsagesByUuidAndCompanyId(
				uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of asset entry usages.
	 *
	 * @return the number of asset entry usages
	 */
	@Override
	public int getAssetEntryUsagesCount() {
		return _assetEntryUsageLocalService.getAssetEntryUsagesCount();
	}

	@Override
	public int getAssetEntryUsagesCount(long assetEntryId) {
		return _assetEntryUsageLocalService.getAssetEntryUsagesCount(
			assetEntryId);
	}

	@Override
	public int getAssetEntryUsagesCount(long assetEntryId, int type) {
		return _assetEntryUsageLocalService.getAssetEntryUsagesCount(
			assetEntryId, type);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _assetEntryUsageLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _assetEntryUsageLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _assetEntryUsageLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetEntryUsageLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public int getUniqueAssetEntryUsagesCount(long assetEntryId) {
		return _assetEntryUsageLocalService.getUniqueAssetEntryUsagesCount(
			assetEntryId);
	}

	@Override
	public boolean hasDefaultAssetEntryUsage(long assetEntryId) {
		return _assetEntryUsageLocalService.hasDefaultAssetEntryUsage(
			assetEntryId);
	}

	/**
	 * Updates the asset entry usage in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param assetEntryUsage the asset entry usage
	 * @return the asset entry usage that was updated
	 */
	@Override
	public com.liferay.asset.model.AssetEntryUsage updateAssetEntryUsage(
		com.liferay.asset.model.AssetEntryUsage assetEntryUsage) {

		return _assetEntryUsageLocalService.updateAssetEntryUsage(
			assetEntryUsage);
	}

	@Override
	public AssetEntryUsageLocalService getWrappedService() {
		return _assetEntryUsageLocalService;
	}

	@Override
	public void setWrappedService(
		AssetEntryUsageLocalService assetEntryUsageLocalService) {

		_assetEntryUsageLocalService = assetEntryUsageLocalService;
	}

	private AssetEntryUsageLocalService _assetEntryUsageLocalService;

}