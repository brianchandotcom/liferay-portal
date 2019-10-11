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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for AssetDisplayPageEntry. This utility wraps
 * <code>com.liferay.asset.display.page.service.impl.AssetDisplayPageEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see AssetDisplayPageEntryLocalService
 * @generated
 */
public class AssetDisplayPageEntryLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.asset.display.page.service.impl.AssetDisplayPageEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the asset display page entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetDisplayPageEntry the asset display page entry
	 * @return the asset display page entry that was added
	 */
	public static com.liferay.asset.display.page.model.AssetDisplayPageEntry
		addAssetDisplayPageEntry(
			com.liferay.asset.display.page.model.AssetDisplayPageEntry
				assetDisplayPageEntry) {

		return getService().addAssetDisplayPageEntry(assetDisplayPageEntry);
	}

	public static com.liferay.asset.display.page.model.AssetDisplayPageEntry
			addAssetDisplayPageEntry(
				long userId, long groupId, long classNameId, long classPK,
				long layoutPageTemplateEntryId, int type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addAssetDisplayPageEntry(
			userId, groupId, classNameId, classPK, layoutPageTemplateEntryId,
			type, serviceContext);
	}

	public static com.liferay.asset.display.page.model.AssetDisplayPageEntry
			addAssetDisplayPageEntry(
				long userId, long groupId, long classNameId, long classPK,
				long layoutPageTemplateEntryId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addAssetDisplayPageEntry(
			userId, groupId, classNameId, classPK, layoutPageTemplateEntryId,
			serviceContext);
	}

	/**
	 * Creates a new asset display page entry with the primary key. Does not add the asset display page entry to the database.
	 *
	 * @param assetDisplayPageEntryId the primary key for the new asset display page entry
	 * @return the new asset display page entry
	 */
	public static com.liferay.asset.display.page.model.AssetDisplayPageEntry
		createAssetDisplayPageEntry(long assetDisplayPageEntryId) {

		return getService().createAssetDisplayPageEntry(
			assetDisplayPageEntryId);
	}

	/**
	 * Deletes the asset display page entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetDisplayPageEntry the asset display page entry
	 * @return the asset display page entry that was removed
	 */
	public static com.liferay.asset.display.page.model.AssetDisplayPageEntry
		deleteAssetDisplayPageEntry(
			com.liferay.asset.display.page.model.AssetDisplayPageEntry
				assetDisplayPageEntry) {

		return getService().deleteAssetDisplayPageEntry(assetDisplayPageEntry);
	}

	/**
	 * Deletes the asset display page entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetDisplayPageEntryId the primary key of the asset display page entry
	 * @return the asset display page entry that was removed
	 * @throws PortalException if a asset display page entry with the primary key could not be found
	 */
	public static com.liferay.asset.display.page.model.AssetDisplayPageEntry
			deleteAssetDisplayPageEntry(long assetDisplayPageEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteAssetDisplayPageEntry(
			assetDisplayPageEntryId);
	}

	public static void deleteAssetDisplayPageEntry(
			long groupId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteAssetDisplayPageEntry(groupId, classNameId, classPK);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.asset.display.page.model.impl.AssetDisplayPageEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.asset.display.page.model.impl.AssetDisplayPageEntryModelImpl</code>.
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

	public static com.liferay.asset.display.page.model.AssetDisplayPageEntry
		fetchAssetDisplayPageEntry(long assetDisplayPageEntryId) {

		return getService().fetchAssetDisplayPageEntry(assetDisplayPageEntryId);
	}

	public static com.liferay.asset.display.page.model.AssetDisplayPageEntry
		fetchAssetDisplayPageEntry(
			long groupId, long classNameId, long classPK) {

		return getService().fetchAssetDisplayPageEntry(
			groupId, classNameId, classPK);
	}

	/**
	 * Returns the asset display page entry matching the UUID and group.
	 *
	 * @param uuid the asset display page entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching asset display page entry, or <code>null</code> if a matching asset display page entry could not be found
	 */
	public static com.liferay.asset.display.page.model.AssetDisplayPageEntry
		fetchAssetDisplayPageEntryByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchAssetDisplayPageEntryByUuidAndGroupId(
			uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the asset display page entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.asset.display.page.model.impl.AssetDisplayPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset display page entries
	 * @param end the upper bound of the range of asset display page entries (not inclusive)
	 * @return the range of asset display page entries
	 */
	public static java.util.List
		<com.liferay.asset.display.page.model.AssetDisplayPageEntry>
			getAssetDisplayPageEntries(int start, int end) {

		return getService().getAssetDisplayPageEntries(start, end);
	}

	public static java.util.List
		<com.liferay.asset.display.page.model.AssetDisplayPageEntry>
			getAssetDisplayPageEntriesByLayoutPageTemplateEntryId(
				long layoutPageTemplateEntryId) {

		return getService().
			getAssetDisplayPageEntriesByLayoutPageTemplateEntryId(
				layoutPageTemplateEntryId);
	}

	/**
	 * Returns all the asset display page entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the asset display page entries
	 * @param companyId the primary key of the company
	 * @return the matching asset display page entries, or an empty list if no matches were found
	 */
	public static java.util.List
		<com.liferay.asset.display.page.model.AssetDisplayPageEntry>
			getAssetDisplayPageEntriesByUuidAndCompanyId(
				String uuid, long companyId) {

		return getService().getAssetDisplayPageEntriesByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of asset display page entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the asset display page entries
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of asset display page entries
	 * @param end the upper bound of the range of asset display page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching asset display page entries, or an empty list if no matches were found
	 */
	public static java.util.List
		<com.liferay.asset.display.page.model.AssetDisplayPageEntry>
			getAssetDisplayPageEntriesByUuidAndCompanyId(
				String uuid, long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.asset.display.page.model.AssetDisplayPageEntry>
						orderByComparator) {

		return getService().getAssetDisplayPageEntriesByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of asset display page entries.
	 *
	 * @return the number of asset display page entries
	 */
	public static int getAssetDisplayPageEntriesCount() {
		return getService().getAssetDisplayPageEntriesCount();
	}

	public static int
		getAssetDisplayPageEntriesCountByLayoutPageTemplateEntryId(
			long layoutPageTemplateEntryId) {

		return getService().
			getAssetDisplayPageEntriesCountByLayoutPageTemplateEntryId(
				layoutPageTemplateEntryId);
	}

	/**
	 * Returns the asset display page entry with the primary key.
	 *
	 * @param assetDisplayPageEntryId the primary key of the asset display page entry
	 * @return the asset display page entry
	 * @throws PortalException if a asset display page entry with the primary key could not be found
	 */
	public static com.liferay.asset.display.page.model.AssetDisplayPageEntry
			getAssetDisplayPageEntry(long assetDisplayPageEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAssetDisplayPageEntry(assetDisplayPageEntryId);
	}

	/**
	 * Returns the asset display page entry matching the UUID and group.
	 *
	 * @param uuid the asset display page entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching asset display page entry
	 * @throws PortalException if a matching asset display page entry could not be found
	 */
	public static com.liferay.asset.display.page.model.AssetDisplayPageEntry
			getAssetDisplayPageEntryByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAssetDisplayPageEntryByUuidAndGroupId(
			uuid, groupId);
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
	 * Updates the asset display page entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param assetDisplayPageEntry the asset display page entry
	 * @return the asset display page entry that was updated
	 */
	public static com.liferay.asset.display.page.model.AssetDisplayPageEntry
		updateAssetDisplayPageEntry(
			com.liferay.asset.display.page.model.AssetDisplayPageEntry
				assetDisplayPageEntry) {

		return getService().updateAssetDisplayPageEntry(assetDisplayPageEntry);
	}

	public static com.liferay.asset.display.page.model.AssetDisplayPageEntry
			updateAssetDisplayPageEntry(
				long assetDisplayPageEntryId, long layoutPageTemplateEntryId,
				int type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateAssetDisplayPageEntry(
			assetDisplayPageEntryId, layoutPageTemplateEntryId, type);
	}

	public static AssetDisplayPageEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<AssetDisplayPageEntryLocalService, AssetDisplayPageEntryLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			AssetDisplayPageEntryLocalService.class);

		ServiceTracker
			<AssetDisplayPageEntryLocalService,
			 AssetDisplayPageEntryLocalService> serviceTracker =
				new ServiceTracker
					<AssetDisplayPageEntryLocalService,
					 AssetDisplayPageEntryLocalService>(
						 bundle.getBundleContext(),
						 AssetDisplayPageEntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}