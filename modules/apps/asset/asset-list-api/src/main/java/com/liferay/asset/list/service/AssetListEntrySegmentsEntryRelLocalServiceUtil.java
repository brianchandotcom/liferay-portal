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
 * Provides the local service utility for AssetListEntrySegmentsEntryRel. This utility wraps
 * <code>com.liferay.asset.list.service.impl.AssetListEntrySegmentsEntryRelLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see AssetListEntrySegmentsEntryRelLocalService
 * @generated
 */
public class AssetListEntrySegmentsEntryRelLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.asset.list.service.impl.AssetListEntrySegmentsEntryRelLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the asset list entry segments entry rel to the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetListEntrySegmentsEntryRel the asset list entry segments entry rel
	 * @return the asset list entry segments entry rel that was added
	 */
	public static com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel
		addAssetListEntrySegmentsEntryRel(
			com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel
				assetListEntrySegmentsEntryRel) {

		return getService().addAssetListEntrySegmentsEntryRel(
			assetListEntrySegmentsEntryRel);
	}

	public static com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel
			addAssetListEntrySegmentsEntryRel(
				long userId, long groupId, long assetListEntryId,
				long segmentsEntryId, String typeSettings,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addAssetListEntrySegmentsEntryRel(
			userId, groupId, assetListEntryId, segmentsEntryId, typeSettings,
			serviceContext);
	}

	/**
	 * Creates a new asset list entry segments entry rel with the primary key. Does not add the asset list entry segments entry rel to the database.
	 *
	 * @param assetListEntrySegmentsEntryRelId the primary key for the new asset list entry segments entry rel
	 * @return the new asset list entry segments entry rel
	 */
	public static com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel
		createAssetListEntrySegmentsEntryRel(
			long assetListEntrySegmentsEntryRelId) {

		return getService().createAssetListEntrySegmentsEntryRel(
			assetListEntrySegmentsEntryRelId);
	}

	/**
	 * Deletes the asset list entry segments entry rel from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetListEntrySegmentsEntryRel the asset list entry segments entry rel
	 * @return the asset list entry segments entry rel that was removed
	 */
	public static com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel
		deleteAssetListEntrySegmentsEntryRel(
			com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel
				assetListEntrySegmentsEntryRel) {

		return getService().deleteAssetListEntrySegmentsEntryRel(
			assetListEntrySegmentsEntryRel);
	}

	/**
	 * Deletes the asset list entry segments entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetListEntrySegmentsEntryRelId the primary key of the asset list entry segments entry rel
	 * @return the asset list entry segments entry rel that was removed
	 * @throws PortalException if a asset list entry segments entry rel with the primary key could not be found
	 */
	public static com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel
			deleteAssetListEntrySegmentsEntryRel(
				long assetListEntrySegmentsEntryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteAssetListEntrySegmentsEntryRel(
			assetListEntrySegmentsEntryRelId);
	}

	public static void deleteAssetListEntrySegmentsEntryRel(
			long assetListEntryId, long segmentsEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteAssetListEntrySegmentsEntryRel(
			assetListEntryId, segmentsEntryId);
	}

	public static void deleteAssetListEntrySegmentsEntryRelByAssetListEntryId(
		long assetListEntryId) {

		getService().deleteAssetListEntrySegmentsEntryRelByAssetListEntryId(
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.asset.list.model.impl.AssetListEntrySegmentsEntryRelModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.asset.list.model.impl.AssetListEntrySegmentsEntryRelModelImpl</code>.
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

	public static com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel
		fetchAssetListEntrySegmentsEntryRel(
			long assetListEntrySegmentsEntryRelId) {

		return getService().fetchAssetListEntrySegmentsEntryRel(
			assetListEntrySegmentsEntryRelId);
	}

	public static com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel
		fetchAssetListEntrySegmentsEntryRel(
			long assetListEntryId, long segmentsEntryId) {

		return getService().fetchAssetListEntrySegmentsEntryRel(
			assetListEntryId, segmentsEntryId);
	}

	/**
	 * Returns the asset list entry segments entry rel matching the UUID and group.
	 *
	 * @param uuid the asset list entry segments entry rel's UUID
	 * @param groupId the primary key of the group
	 * @return the matching asset list entry segments entry rel, or <code>null</code> if a matching asset list entry segments entry rel could not be found
	 */
	public static com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel
		fetchAssetListEntrySegmentsEntryRelByUuidAndGroupId(
			String uuid, long groupId) {

		return getService().fetchAssetListEntrySegmentsEntryRelByUuidAndGroupId(
			uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the asset list entry segments entry rel with the primary key.
	 *
	 * @param assetListEntrySegmentsEntryRelId the primary key of the asset list entry segments entry rel
	 * @return the asset list entry segments entry rel
	 * @throws PortalException if a asset list entry segments entry rel with the primary key could not be found
	 */
	public static com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel
			getAssetListEntrySegmentsEntryRel(
				long assetListEntrySegmentsEntryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAssetListEntrySegmentsEntryRel(
			assetListEntrySegmentsEntryRelId);
	}

	public static com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel
			getAssetListEntrySegmentsEntryRel(
				long assetListEntryId, long segmentsEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAssetListEntrySegmentsEntryRel(
			assetListEntryId, segmentsEntryId);
	}

	/**
	 * Returns the asset list entry segments entry rel matching the UUID and group.
	 *
	 * @param uuid the asset list entry segments entry rel's UUID
	 * @param groupId the primary key of the group
	 * @return the matching asset list entry segments entry rel
	 * @throws PortalException if a matching asset list entry segments entry rel could not be found
	 */
	public static com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel
			getAssetListEntrySegmentsEntryRelByUuidAndGroupId(
				String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAssetListEntrySegmentsEntryRelByUuidAndGroupId(
			uuid, groupId);
	}

	/**
	 * Returns a range of all the asset list entry segments entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.asset.list.model.impl.AssetListEntrySegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset list entry segments entry rels
	 * @param end the upper bound of the range of asset list entry segments entry rels (not inclusive)
	 * @return the range of asset list entry segments entry rels
	 */
	public static java.util.List
		<com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel>
			getAssetListEntrySegmentsEntryRels(int start, int end) {

		return getService().getAssetListEntrySegmentsEntryRels(start, end);
	}

	public static java.util.List
		<com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel>
			getAssetListEntrySegmentsEntryRels(
				long assetListEntryId, int start, int end) {

		return getService().getAssetListEntrySegmentsEntryRels(
			assetListEntryId, start, end);
	}

	/**
	 * Returns all the asset list entry segments entry rels matching the UUID and company.
	 *
	 * @param uuid the UUID of the asset list entry segments entry rels
	 * @param companyId the primary key of the company
	 * @return the matching asset list entry segments entry rels, or an empty list if no matches were found
	 */
	public static java.util.List
		<com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel>
			getAssetListEntrySegmentsEntryRelsByUuidAndCompanyId(
				String uuid, long companyId) {

		return getService().
			getAssetListEntrySegmentsEntryRelsByUuidAndCompanyId(
				uuid, companyId);
	}

	/**
	 * Returns a range of asset list entry segments entry rels matching the UUID and company.
	 *
	 * @param uuid the UUID of the asset list entry segments entry rels
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of asset list entry segments entry rels
	 * @param end the upper bound of the range of asset list entry segments entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching asset list entry segments entry rels, or an empty list if no matches were found
	 */
	public static java.util.List
		<com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel>
			getAssetListEntrySegmentsEntryRelsByUuidAndCompanyId(
				String uuid, long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.asset.list.model.
						AssetListEntrySegmentsEntryRel> orderByComparator) {

		return getService().
			getAssetListEntrySegmentsEntryRelsByUuidAndCompanyId(
				uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of asset list entry segments entry rels.
	 *
	 * @return the number of asset list entry segments entry rels
	 */
	public static int getAssetListEntrySegmentsEntryRelsCount() {
		return getService().getAssetListEntrySegmentsEntryRelsCount();
	}

	public static int getAssetListEntrySegmentsEntryRelsCount(
		long assetListEntryId) {

		return getService().getAssetListEntrySegmentsEntryRelsCount(
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

	/**
	 * Updates the asset list entry segments entry rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param assetListEntrySegmentsEntryRel the asset list entry segments entry rel
	 * @return the asset list entry segments entry rel that was updated
	 */
	public static com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel
		updateAssetListEntrySegmentsEntryRel(
			com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel
				assetListEntrySegmentsEntryRel) {

		return getService().updateAssetListEntrySegmentsEntryRel(
			assetListEntrySegmentsEntryRel);
	}

	public static com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel
		updateAssetListEntrySegmentsEntryRelTypeSettings(
			long assetListEntryId, long segmentsEntryId, String typeSettings) {

		return getService().updateAssetListEntrySegmentsEntryRelTypeSettings(
			assetListEntryId, segmentsEntryId, typeSettings);
	}

	public static AssetListEntrySegmentsEntryRelLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<AssetListEntrySegmentsEntryRelLocalService,
		 AssetListEntrySegmentsEntryRelLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			AssetListEntrySegmentsEntryRelLocalService.class);

		ServiceTracker
			<AssetListEntrySegmentsEntryRelLocalService,
			 AssetListEntrySegmentsEntryRelLocalService> serviceTracker =
				new ServiceTracker
					<AssetListEntrySegmentsEntryRelLocalService,
					 AssetListEntrySegmentsEntryRelLocalService>(
						 bundle.getBundleContext(),
						 AssetListEntrySegmentsEntryRelLocalService.class,
						 null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}