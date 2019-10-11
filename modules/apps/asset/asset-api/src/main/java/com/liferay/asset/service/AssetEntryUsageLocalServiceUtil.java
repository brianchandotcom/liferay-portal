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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for AssetEntryUsage. This utility wraps
 * <code>com.liferay.asset.service.impl.AssetEntryUsageLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryUsageLocalService
 * @generated
 */
public class AssetEntryUsageLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.asset.service.impl.AssetEntryUsageLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the asset entry usage to the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetEntryUsage the asset entry usage
	 * @return the asset entry usage that was added
	 */
	public static com.liferay.asset.model.AssetEntryUsage addAssetEntryUsage(
		com.liferay.asset.model.AssetEntryUsage assetEntryUsage) {

		return getService().addAssetEntryUsage(assetEntryUsage);
	}

	public static com.liferay.asset.model.AssetEntryUsage addAssetEntryUsage(
		long groupId, long assetEntryId, long containerType,
		String containerKey, long plid,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return getService().addAssetEntryUsage(
			groupId, assetEntryId, containerType, containerKey, plid,
			serviceContext);
	}

	public static com.liferay.asset.model.AssetEntryUsage
		addDefaultAssetEntryUsage(
			long groupId, long assetEntryId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return getService().addDefaultAssetEntryUsage(
			groupId, assetEntryId, serviceContext);
	}

	/**
	 * Creates a new asset entry usage with the primary key. Does not add the asset entry usage to the database.
	 *
	 * @param assetEntryUsageId the primary key for the new asset entry usage
	 * @return the new asset entry usage
	 */
	public static com.liferay.asset.model.AssetEntryUsage createAssetEntryUsage(
		long assetEntryUsageId) {

		return getService().createAssetEntryUsage(assetEntryUsageId);
	}

	/**
	 * Deletes the asset entry usage from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetEntryUsage the asset entry usage
	 * @return the asset entry usage that was removed
	 */
	public static com.liferay.asset.model.AssetEntryUsage deleteAssetEntryUsage(
		com.liferay.asset.model.AssetEntryUsage assetEntryUsage) {

		return getService().deleteAssetEntryUsage(assetEntryUsage);
	}

	/**
	 * Deletes the asset entry usage with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetEntryUsageId the primary key of the asset entry usage
	 * @return the asset entry usage that was removed
	 * @throws PortalException if a asset entry usage with the primary key could not be found
	 */
	public static com.liferay.asset.model.AssetEntryUsage deleteAssetEntryUsage(
			long assetEntryUsageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteAssetEntryUsage(assetEntryUsageId);
	}

	public static void deleteAssetEntryUsages(long assetEntryId) {
		getService().deleteAssetEntryUsages(assetEntryId);
	}

	public static void deleteAssetEntryUsages(
		long containerType, String containerKey, long plid) {

		getService().deleteAssetEntryUsages(containerType, containerKey, plid);
	}

	public static void deleteAssetEntryUsagesByPlid(long plid) {
		getService().deleteAssetEntryUsagesByPlid(plid);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.asset.model.impl.AssetEntryUsageModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.asset.model.impl.AssetEntryUsageModelImpl</code>.
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

	public static com.liferay.asset.model.AssetEntryUsage fetchAssetEntryUsage(
		long assetEntryUsageId) {

		return getService().fetchAssetEntryUsage(assetEntryUsageId);
	}

	public static com.liferay.asset.model.AssetEntryUsage fetchAssetEntryUsage(
		long assetEntryId, long containerType, String containerKey, long plid) {

		return getService().fetchAssetEntryUsage(
			assetEntryId, containerType, containerKey, plid);
	}

	/**
	 * Returns the asset entry usage matching the UUID and group.
	 *
	 * @param uuid the asset entry usage's UUID
	 * @param groupId the primary key of the group
	 * @return the matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	 */
	public static com.liferay.asset.model.AssetEntryUsage
		fetchAssetEntryUsageByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchAssetEntryUsageByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the asset entry usage with the primary key.
	 *
	 * @param assetEntryUsageId the primary key of the asset entry usage
	 * @return the asset entry usage
	 * @throws PortalException if a asset entry usage with the primary key could not be found
	 */
	public static com.liferay.asset.model.AssetEntryUsage getAssetEntryUsage(
			long assetEntryUsageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAssetEntryUsage(assetEntryUsageId);
	}

	/**
	 * Returns the asset entry usage matching the UUID and group.
	 *
	 * @param uuid the asset entry usage's UUID
	 * @param groupId the primary key of the group
	 * @return the matching asset entry usage
	 * @throws PortalException if a matching asset entry usage could not be found
	 */
	public static com.liferay.asset.model.AssetEntryUsage
			getAssetEntryUsageByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAssetEntryUsageByUuidAndGroupId(uuid, groupId);
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
	public static java.util.List<com.liferay.asset.model.AssetEntryUsage>
		getAssetEntryUsages(int start, int end) {

		return getService().getAssetEntryUsages(start, end);
	}

	public static java.util.List<com.liferay.asset.model.AssetEntryUsage>
		getAssetEntryUsages(long assetEntryId) {

		return getService().getAssetEntryUsages(assetEntryId);
	}

	public static java.util.List<com.liferay.asset.model.AssetEntryUsage>
		getAssetEntryUsages(
			long assetEntryId, int type, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.asset.model.AssetEntryUsage> orderByComparator) {

		return getService().getAssetEntryUsages(
			assetEntryId, type, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.asset.model.AssetEntryUsage>
		getAssetEntryUsages(
			long assetEntryId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.asset.model.AssetEntryUsage> orderByComparator) {

		return getService().getAssetEntryUsages(
			assetEntryId, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.asset.model.AssetEntryUsage>
		getAssetEntryUsagesByPlid(long plid) {

		return getService().getAssetEntryUsagesByPlid(plid);
	}

	/**
	 * Returns the number of asset entry usages.
	 *
	 * @return the number of asset entry usages
	 */
	public static int getAssetEntryUsagesCount() {
		return getService().getAssetEntryUsagesCount();
	}

	public static int getAssetEntryUsagesCount(long assetEntryId) {
		return getService().getAssetEntryUsagesCount(assetEntryId);
	}

	public static int getAssetEntryUsagesCount(long assetEntryId, int type) {
		return getService().getAssetEntryUsagesCount(assetEntryId, type);
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

	public static int getUniqueAssetEntryUsagesCount(long assetEntryId) {
		return getService().getUniqueAssetEntryUsagesCount(assetEntryId);
	}

	public static boolean hasDefaultAssetEntryUsage(long assetEntryId) {
		return getService().hasDefaultAssetEntryUsage(assetEntryId);
	}

	/**
	 * Updates the asset entry usage in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param assetEntryUsage the asset entry usage
	 * @return the asset entry usage that was updated
	 */
	public static com.liferay.asset.model.AssetEntryUsage updateAssetEntryUsage(
		com.liferay.asset.model.AssetEntryUsage assetEntryUsage) {

		return getService().updateAssetEntryUsage(assetEntryUsage);
	}

	public static AssetEntryUsageLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<AssetEntryUsageLocalService, AssetEntryUsageLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			AssetEntryUsageLocalService.class);

		ServiceTracker<AssetEntryUsageLocalService, AssetEntryUsageLocalService>
			serviceTracker =
				new ServiceTracker
					<AssetEntryUsageLocalService, AssetEntryUsageLocalService>(
						bundle.getBundleContext(),
						AssetEntryUsageLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}