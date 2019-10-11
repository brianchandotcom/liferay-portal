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
 * Provides the local service utility for AssetListEntryUsage. This utility wraps
 * <code>com.liferay.asset.list.service.impl.AssetListEntryUsageLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see AssetListEntryUsageLocalService
 * @generated
 */
public class AssetListEntryUsageLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.asset.list.service.impl.AssetListEntryUsageLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the asset list entry usage to the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetListEntryUsage the asset list entry usage
	 * @return the asset list entry usage that was added
	 */
	public static com.liferay.asset.list.model.AssetListEntryUsage
		addAssetListEntryUsage(
			com.liferay.asset.list.model.AssetListEntryUsage
				assetListEntryUsage) {

		return getService().addAssetListEntryUsage(assetListEntryUsage);
	}

	public static com.liferay.asset.list.model.AssetListEntryUsage
			addAssetListEntryUsage(
				long userId, long groupId, long assetListEntryId,
				long classNameId, long classPK, String portletId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addAssetListEntryUsage(
			userId, groupId, assetListEntryId, classNameId, classPK, portletId,
			serviceContext);
	}

	/**
	 * Creates a new asset list entry usage with the primary key. Does not add the asset list entry usage to the database.
	 *
	 * @param assetListEntryUsageId the primary key for the new asset list entry usage
	 * @return the new asset list entry usage
	 */
	public static com.liferay.asset.list.model.AssetListEntryUsage
		createAssetListEntryUsage(long assetListEntryUsageId) {

		return getService().createAssetListEntryUsage(assetListEntryUsageId);
	}

	/**
	 * Deletes the asset list entry usage from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetListEntryUsage the asset list entry usage
	 * @return the asset list entry usage that was removed
	 */
	public static com.liferay.asset.list.model.AssetListEntryUsage
		deleteAssetListEntryUsage(
			com.liferay.asset.list.model.AssetListEntryUsage
				assetListEntryUsage) {

		return getService().deleteAssetListEntryUsage(assetListEntryUsage);
	}

	/**
	 * Deletes the asset list entry usage with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetListEntryUsageId the primary key of the asset list entry usage
	 * @return the asset list entry usage that was removed
	 * @throws PortalException if a asset list entry usage with the primary key could not be found
	 */
	public static com.liferay.asset.list.model.AssetListEntryUsage
			deleteAssetListEntryUsage(long assetListEntryUsageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteAssetListEntryUsage(assetListEntryUsageId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.asset.list.model.impl.AssetListEntryUsageModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.asset.list.model.impl.AssetListEntryUsageModelImpl</code>.
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

	public static com.liferay.asset.list.model.AssetListEntryUsage
		fetchAssetListEntryUsage(long assetListEntryUsageId) {

		return getService().fetchAssetListEntryUsage(assetListEntryUsageId);
	}

	public static com.liferay.asset.list.model.AssetListEntryUsage
		fetchAssetListEntryUsage(
			long classNameId, long classPK, String portletId) {

		return getService().fetchAssetListEntryUsage(
			classNameId, classPK, portletId);
	}

	/**
	 * Returns the asset list entry usage matching the UUID and group.
	 *
	 * @param uuid the asset list entry usage's UUID
	 * @param groupId the primary key of the group
	 * @return the matching asset list entry usage, or <code>null</code> if a matching asset list entry usage could not be found
	 */
	public static com.liferay.asset.list.model.AssetListEntryUsage
		fetchAssetListEntryUsageByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchAssetListEntryUsageByUuidAndGroupId(
			uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the asset list entry usage with the primary key.
	 *
	 * @param assetListEntryUsageId the primary key of the asset list entry usage
	 * @return the asset list entry usage
	 * @throws PortalException if a asset list entry usage with the primary key could not be found
	 */
	public static com.liferay.asset.list.model.AssetListEntryUsage
			getAssetListEntryUsage(long assetListEntryUsageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAssetListEntryUsage(assetListEntryUsageId);
	}

	/**
	 * Returns the asset list entry usage matching the UUID and group.
	 *
	 * @param uuid the asset list entry usage's UUID
	 * @param groupId the primary key of the group
	 * @return the matching asset list entry usage
	 * @throws PortalException if a matching asset list entry usage could not be found
	 */
	public static com.liferay.asset.list.model.AssetListEntryUsage
			getAssetListEntryUsageByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAssetListEntryUsageByUuidAndGroupId(
			uuid, groupId);
	}

	/**
	 * Returns a range of all the asset list entry usages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.asset.list.model.impl.AssetListEntryUsageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @return the range of asset list entry usages
	 */
	public static java.util.List
		<com.liferay.asset.list.model.AssetListEntryUsage>
			getAssetListEntryUsages(int start, int end) {

		return getService().getAssetListEntryUsages(start, end);
	}

	public static java.util.List
		<com.liferay.asset.list.model.AssetListEntryUsage>
			getAssetListEntryUsages(long assetListEntryId) {

		return getService().getAssetListEntryUsages(assetListEntryId);
	}

	public static java.util.List
		<com.liferay.asset.list.model.AssetListEntryUsage>
			getAssetListEntryUsages(
				long assetListEntryId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.asset.list.model.AssetListEntryUsage>
						orderByComparator) {

		return getService().getAssetListEntryUsages(
			assetListEntryId, start, end, orderByComparator);
	}

	public static java.util.List
		<com.liferay.asset.list.model.AssetListEntryUsage>
			getAssetListEntryUsages(long assetListEntryId, long classNameId) {

		return getService().getAssetListEntryUsages(
			assetListEntryId, classNameId);
	}

	public static java.util.List
		<com.liferay.asset.list.model.AssetListEntryUsage>
			getAssetListEntryUsages(
				long assetListEntryId, long classNameId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.asset.list.model.AssetListEntryUsage>
						orderByComparator) {

		return getService().getAssetListEntryUsages(
			assetListEntryId, classNameId, start, end, orderByComparator);
	}

	/**
	 * Returns all the asset list entry usages matching the UUID and company.
	 *
	 * @param uuid the UUID of the asset list entry usages
	 * @param companyId the primary key of the company
	 * @return the matching asset list entry usages, or an empty list if no matches were found
	 */
	public static java.util.List
		<com.liferay.asset.list.model.AssetListEntryUsage>
			getAssetListEntryUsagesByUuidAndCompanyId(
				String uuid, long companyId) {

		return getService().getAssetListEntryUsagesByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of asset list entry usages matching the UUID and company.
	 *
	 * @param uuid the UUID of the asset list entry usages
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of asset list entry usages
	 * @param end the upper bound of the range of asset list entry usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching asset list entry usages, or an empty list if no matches were found
	 */
	public static java.util.List
		<com.liferay.asset.list.model.AssetListEntryUsage>
			getAssetListEntryUsagesByUuidAndCompanyId(
				String uuid, long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.asset.list.model.AssetListEntryUsage>
						orderByComparator) {

		return getService().getAssetListEntryUsagesByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of asset list entry usages.
	 *
	 * @return the number of asset list entry usages
	 */
	public static int getAssetListEntryUsagesCount() {
		return getService().getAssetListEntryUsagesCount();
	}

	public static int getAssetListEntryUsagesCount(long assetListEntryId) {
		return getService().getAssetListEntryUsagesCount(assetListEntryId);
	}

	public static int getAssetListEntryUsagesCount(
		long assetListEntryId, long classNameId) {

		return getService().getAssetListEntryUsagesCount(
			assetListEntryId, classNameId);
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
	 * Updates the asset list entry usage in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param assetListEntryUsage the asset list entry usage
	 * @return the asset list entry usage that was updated
	 */
	public static com.liferay.asset.list.model.AssetListEntryUsage
		updateAssetListEntryUsage(
			com.liferay.asset.list.model.AssetListEntryUsage
				assetListEntryUsage) {

		return getService().updateAssetListEntryUsage(assetListEntryUsage);
	}

	public static AssetListEntryUsageLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<AssetListEntryUsageLocalService, AssetListEntryUsageLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			AssetListEntryUsageLocalService.class);

		ServiceTracker
			<AssetListEntryUsageLocalService, AssetListEntryUsageLocalService>
				serviceTracker =
					new ServiceTracker
						<AssetListEntryUsageLocalService,
						 AssetListEntryUsageLocalService>(
							 bundle.getBundleContext(),
							 AssetListEntryUsageLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}