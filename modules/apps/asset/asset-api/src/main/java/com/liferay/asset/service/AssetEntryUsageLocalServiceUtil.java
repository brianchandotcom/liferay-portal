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

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for AssetEntryUsage. This utility wraps
 * {@link com.liferay.asset.service.impl.AssetEntryUsageLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryUsageLocalService
 * @see com.liferay.asset.service.base.AssetEntryUsageLocalServiceBaseImpl
 * @see com.liferay.asset.service.impl.AssetEntryUsageLocalServiceImpl
 * @generated
 */
@ProviderType
public class AssetEntryUsageLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.asset.service.impl.AssetEntryUsageLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
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
		long userId, long groupId, long assetEntryId, long classNameId,
		long classPK, String portletId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addAssetEntryUsage(userId, groupId, assetEntryId,
			classNameId, classPK, portletId, serviceContext);
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

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.asset.model.impl.AssetEntryUsageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.asset.model.impl.AssetEntryUsageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
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

	/**
	* Returns the asset entry usage matching the UUID and group.
	*
	* @param uuid the asset entry usage's UUID
	* @param groupId the primary key of the group
	* @return the matching asset entry usage, or <code>null</code> if a matching asset entry usage could not be found
	*/
	public static com.liferay.asset.model.AssetEntryUsage fetchAssetEntryUsageByUuidAndGroupId(
		String uuid, long groupId) {
		return getService().fetchAssetEntryUsageByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
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
	public static com.liferay.asset.model.AssetEntryUsage getAssetEntryUsageByUuidAndGroupId(
		String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getAssetEntryUsageByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the asset entry usages.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.asset.model.impl.AssetEntryUsageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset entry usages
	* @param end the upper bound of the range of asset entry usages (not inclusive)
	* @return the range of asset entry usages
	*/
	public static java.util.List<com.liferay.asset.model.AssetEntryUsage> getAssetEntryUsages(
		int start, int end) {
		return getService().getAssetEntryUsages(start, end);
	}

	public static java.util.List<com.liferay.asset.model.AssetEntryUsage> getAssetEntryUsages(
		long assetEntryId) {
		return getService().getAssetEntryUsages(assetEntryId);
	}

	public static java.util.List<com.liferay.asset.model.AssetEntryUsage> getAssetEntryUsages(
		long assetEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.asset.model.AssetEntryUsage> orderByComparator) {
		return getService()
				   .getAssetEntryUsages(assetEntryId, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.asset.model.AssetEntryUsage> getAssetEntryUsages(
		long assetEntryId, long classNameId) {
		return getService().getAssetEntryUsages(assetEntryId, classNameId);
	}

	public static java.util.List<com.liferay.asset.model.AssetEntryUsage> getAssetEntryUsages(
		long assetEntryId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.asset.model.AssetEntryUsage> orderByComparator) {
		return getService()
				   .getAssetEntryUsages(assetEntryId, classNameId, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.asset.model.AssetEntryUsage> getAssetEntryUsages(
		long classNameId, long classPK, String portletId) {
		return getService().getAssetEntryUsages(classNameId, classPK, portletId);
	}

	/**
	* Returns all the asset entry usages matching the UUID and company.
	*
	* @param uuid the UUID of the asset entry usages
	* @param companyId the primary key of the company
	* @return the matching asset entry usages, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.asset.model.AssetEntryUsage> getAssetEntryUsagesByUuidAndCompanyId(
		String uuid, long companyId) {
		return getService()
				   .getAssetEntryUsagesByUuidAndCompanyId(uuid, companyId);
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
	public static java.util.List<com.liferay.asset.model.AssetEntryUsage> getAssetEntryUsagesByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.asset.model.AssetEntryUsage> orderByComparator) {
		return getService()
				   .getAssetEntryUsagesByUuidAndCompanyId(uuid, companyId,
			start, end, orderByComparator);
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

	public static int getAssetEntryUsagesCount(long assetEntryId,
		long classNameId) {
		return getService().getAssetEntryUsagesCount(assetEntryId, classNameId);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
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

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
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

	private static ServiceTracker<AssetEntryUsageLocalService, AssetEntryUsageLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(AssetEntryUsageLocalService.class);

		ServiceTracker<AssetEntryUsageLocalService, AssetEntryUsageLocalService> serviceTracker =
			new ServiceTracker<AssetEntryUsageLocalService, AssetEntryUsageLocalService>(bundle.getBundleContext(),
				AssetEntryUsageLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}