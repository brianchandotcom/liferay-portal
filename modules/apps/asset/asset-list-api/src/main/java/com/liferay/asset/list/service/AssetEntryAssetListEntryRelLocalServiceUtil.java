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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for AssetEntryAssetListEntryRel. This utility wraps
 * <code>com.liferay.asset.list.service.impl.AssetEntryAssetListEntryRelLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryAssetListEntryRelLocalService
 * @generated
 */
@ProviderType
public class AssetEntryAssetListEntryRelLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.asset.list.service.impl.AssetEntryAssetListEntryRelLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the asset entry asset list entry rel to the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetEntryAssetListEntryRel the asset entry asset list entry rel
	 * @return the asset entry asset list entry rel that was added
	 */
	public static com.liferay.asset.list.model.AssetEntryAssetListEntryRel
		addAssetEntryAssetListEntryRel(
			com.liferay.asset.list.model.AssetEntryAssetListEntryRel
				assetEntryAssetListEntryRel) {

		return getService().addAssetEntryAssetListEntryRel(
			assetEntryAssetListEntryRel);
	}

	public static com.liferay.asset.list.model.AssetEntryAssetListEntryRel
			addAssetEntryAssetListEntryRel(
				long assetListEntryId, long assetEntryId, int position,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addAssetEntryAssetListEntryRel(
			assetListEntryId, assetEntryId, position, serviceContext);
	}

	public static com.liferay.asset.list.model.AssetEntryAssetListEntryRel
			addAssetEntryAssetListEntryRel(
				long assetListEntryId, long assetEntryId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addAssetEntryAssetListEntryRel(
			assetListEntryId, assetEntryId, serviceContext);
	}

	/**
	 * Creates a new asset entry asset list entry rel with the primary key. Does not add the asset entry asset list entry rel to the database.
	 *
	 * @param assetEntryAssetListEntryRelId the primary key for the new asset entry asset list entry rel
	 * @return the new asset entry asset list entry rel
	 */
	public static com.liferay.asset.list.model.AssetEntryAssetListEntryRel
		createAssetEntryAssetListEntryRel(long assetEntryAssetListEntryRelId) {

		return getService().createAssetEntryAssetListEntryRel(
			assetEntryAssetListEntryRelId);
	}

	/**
	 * Deletes the asset entry asset list entry rel from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetEntryAssetListEntryRel the asset entry asset list entry rel
	 * @return the asset entry asset list entry rel that was removed
	 */
	public static com.liferay.asset.list.model.AssetEntryAssetListEntryRel
		deleteAssetEntryAssetListEntryRel(
			com.liferay.asset.list.model.AssetEntryAssetListEntryRel
				assetEntryAssetListEntryRel) {

		return getService().deleteAssetEntryAssetListEntryRel(
			assetEntryAssetListEntryRel);
	}

	/**
	 * Deletes the asset entry asset list entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetEntryAssetListEntryRelId the primary key of the asset entry asset list entry rel
	 * @return the asset entry asset list entry rel that was removed
	 * @throws PortalException if a asset entry asset list entry rel with the primary key could not be found
	 */
	public static com.liferay.asset.list.model.AssetEntryAssetListEntryRel
			deleteAssetEntryAssetListEntryRel(
				long assetEntryAssetListEntryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteAssetEntryAssetListEntryRel(
			assetEntryAssetListEntryRelId);
	}

	public static com.liferay.asset.list.model.AssetEntryAssetListEntryRel
			deleteAssetEntryAssetListEntryRel(
				long assetListEntryId, int position)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteAssetEntryAssetListEntryRel(
			assetListEntryId, position);
	}

	public static void deleteAssetEntryAssetListEntryRelByAssetListEntryId(
		long assetListEntryId) {

		getService().deleteAssetEntryAssetListEntryRelByAssetListEntryId(
			assetListEntryId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.asset.list.model.impl.AssetEntryAssetListEntryRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.asset.list.model.impl.AssetEntryAssetListEntryRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.asset.list.model.AssetEntryAssetListEntryRel
		fetchAssetEntryAssetListEntryRel(long assetEntryAssetListEntryRelId) {

		return getService().fetchAssetEntryAssetListEntryRel(
			assetEntryAssetListEntryRelId);
	}

	/**
	 * Returns the asset entry asset list entry rel matching the UUID and group.
	 *
	 * @param uuid the asset entry asset list entry rel's UUID
	 * @param groupId the primary key of the group
	 * @return the matching asset entry asset list entry rel, or <code>null</code> if a matching asset entry asset list entry rel could not be found
	 */
	public static com.liferay.asset.list.model.AssetEntryAssetListEntryRel
		fetchAssetEntryAssetListEntryRelByUuidAndGroupId(
			String uuid, long groupId) {

		return getService().fetchAssetEntryAssetListEntryRelByUuidAndGroupId(
			uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the asset entry asset list entry rel with the primary key.
	 *
	 * @param assetEntryAssetListEntryRelId the primary key of the asset entry asset list entry rel
	 * @return the asset entry asset list entry rel
	 * @throws PortalException if a asset entry asset list entry rel with the primary key could not be found
	 */
	public static com.liferay.asset.list.model.AssetEntryAssetListEntryRel
			getAssetEntryAssetListEntryRel(long assetEntryAssetListEntryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAssetEntryAssetListEntryRel(
			assetEntryAssetListEntryRelId);
	}

	/**
	 * Returns the asset entry asset list entry rel matching the UUID and group.
	 *
	 * @param uuid the asset entry asset list entry rel's UUID
	 * @param groupId the primary key of the group
	 * @return the matching asset entry asset list entry rel
	 * @throws PortalException if a matching asset entry asset list entry rel could not be found
	 */
	public static com.liferay.asset.list.model.AssetEntryAssetListEntryRel
			getAssetEntryAssetListEntryRelByUuidAndGroupId(
				String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAssetEntryAssetListEntryRelByUuidAndGroupId(
			uuid, groupId);
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
	public static java.util.List
		<com.liferay.asset.list.model.AssetEntryAssetListEntryRel>
			getAssetEntryAssetListEntryRels(int start, int end) {

		return getService().getAssetEntryAssetListEntryRels(start, end);
	}

	public static java.util.List
		<com.liferay.asset.list.model.AssetEntryAssetListEntryRel>
			getAssetEntryAssetListEntryRels(
				long assetListEntryId, int start, int end) {

		return getService().getAssetEntryAssetListEntryRels(
			assetListEntryId, start, end);
	}

	/**
	 * Returns all the asset entry asset list entry rels matching the UUID and company.
	 *
	 * @param uuid the UUID of the asset entry asset list entry rels
	 * @param companyId the primary key of the company
	 * @return the matching asset entry asset list entry rels, or an empty list if no matches were found
	 */
	public static java.util.List
		<com.liferay.asset.list.model.AssetEntryAssetListEntryRel>
			getAssetEntryAssetListEntryRelsByUuidAndCompanyId(
				String uuid, long companyId) {

		return getService().getAssetEntryAssetListEntryRelsByUuidAndCompanyId(
			uuid, companyId);
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
	public static java.util.List
		<com.liferay.asset.list.model.AssetEntryAssetListEntryRel>
			getAssetEntryAssetListEntryRelsByUuidAndCompanyId(
				String uuid, long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.asset.list.model.AssetEntryAssetListEntryRel>
						orderByComparator) {

		return getService().getAssetEntryAssetListEntryRelsByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of asset entry asset list entry rels.
	 *
	 * @return the number of asset entry asset list entry rels
	 */
	public static int getAssetEntryAssetListEntryRelsCount() {
		return getService().getAssetEntryAssetListEntryRelsCount();
	}

	public static int getAssetEntryAssetListEntryRelsCount(
		long assetListEntryId) {

		return getService().getAssetEntryAssetListEntryRelsCount(
			assetListEntryId);
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

	public static com.liferay.asset.list.model.AssetEntryAssetListEntryRel
			moveAssetEntryAssetListEntryRel(
				long assetListEntryId, int position, int newPosition)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().moveAssetEntryAssetListEntryRel(
			assetListEntryId, position, newPosition);
	}

	/**
	 * Updates the asset entry asset list entry rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param assetEntryAssetListEntryRel the asset entry asset list entry rel
	 * @return the asset entry asset list entry rel that was updated
	 */
	public static com.liferay.asset.list.model.AssetEntryAssetListEntryRel
		updateAssetEntryAssetListEntryRel(
			com.liferay.asset.list.model.AssetEntryAssetListEntryRel
				assetEntryAssetListEntryRel) {

		return getService().updateAssetEntryAssetListEntryRel(
			assetEntryAssetListEntryRel);
	}

	public static com.liferay.asset.list.model.AssetEntryAssetListEntryRel
			updateAssetEntryAssetListEntryRel(
				long assetEntryAssetListEntryRelId, long assetListEntryId,
				long assetEntryId, int position)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateAssetEntryAssetListEntryRel(
			assetEntryAssetListEntryRelId, assetListEntryId, assetEntryId,
			position);
	}

	public static AssetEntryAssetListEntryRelLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<AssetEntryAssetListEntryRelLocalService,
		 AssetEntryAssetListEntryRelLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			AssetEntryAssetListEntryRelLocalService.class);

		ServiceTracker
			<AssetEntryAssetListEntryRelLocalService,
			 AssetEntryAssetListEntryRelLocalService> serviceTracker =
				new ServiceTracker
					<AssetEntryAssetListEntryRelLocalService,
					 AssetEntryAssetListEntryRelLocalService>(
						 bundle.getBundleContext(),
						 AssetEntryAssetListEntryRelLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}