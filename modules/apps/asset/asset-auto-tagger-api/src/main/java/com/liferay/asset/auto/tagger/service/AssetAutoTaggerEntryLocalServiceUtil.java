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

package com.liferay.asset.auto.tagger.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for AssetAutoTaggerEntry. This utility wraps
 * <code>com.liferay.asset.auto.tagger.service.impl.AssetAutoTaggerEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see AssetAutoTaggerEntryLocalService
 * @generated
 */
public class AssetAutoTaggerEntryLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.asset.auto.tagger.service.impl.AssetAutoTaggerEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the asset auto tagger entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetAutoTaggerEntry the asset auto tagger entry
	 * @return the asset auto tagger entry that was added
	 */
	public static com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry
		addAssetAutoTaggerEntry(
			com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry
				assetAutoTaggerEntry) {

		return getService().addAssetAutoTaggerEntry(assetAutoTaggerEntry);
	}

	public static com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry
		addAssetAutoTaggerEntry(
			com.liferay.asset.kernel.model.AssetEntry assetEntry,
			com.liferay.asset.kernel.model.AssetTag assetTag) {

		return getService().addAssetAutoTaggerEntry(assetEntry, assetTag);
	}

	/**
	 * Creates a new asset auto tagger entry with the primary key. Does not add the asset auto tagger entry to the database.
	 *
	 * @param assetAutoTaggerEntryId the primary key for the new asset auto tagger entry
	 * @return the new asset auto tagger entry
	 */
	public static com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry
		createAssetAutoTaggerEntry(long assetAutoTaggerEntryId) {

		return getService().createAssetAutoTaggerEntry(assetAutoTaggerEntryId);
	}

	/**
	 * Deletes the asset auto tagger entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetAutoTaggerEntry the asset auto tagger entry
	 * @return the asset auto tagger entry that was removed
	 */
	public static com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry
		deleteAssetAutoTaggerEntry(
			com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry
				assetAutoTaggerEntry) {

		return getService().deleteAssetAutoTaggerEntry(assetAutoTaggerEntry);
	}

	/**
	 * Deletes the asset auto tagger entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetAutoTaggerEntryId the primary key of the asset auto tagger entry
	 * @return the asset auto tagger entry that was removed
	 * @throws PortalException if a asset auto tagger entry with the primary key could not be found
	 */
	public static com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry
			deleteAssetAutoTaggerEntry(long assetAutoTaggerEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteAssetAutoTaggerEntry(assetAutoTaggerEntryId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.asset.auto.tagger.model.impl.AssetAutoTaggerEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.asset.auto.tagger.model.impl.AssetAutoTaggerEntryModelImpl</code>.
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

	public static com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry
		fetchAssetAutoTaggerEntry(long assetAutoTaggerEntryId) {

		return getService().fetchAssetAutoTaggerEntry(assetAutoTaggerEntryId);
	}

	public static com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry
		fetchAssetAutoTaggerEntry(long assetEntryId, long assetTagId) {

		return getService().fetchAssetAutoTaggerEntry(assetEntryId, assetTagId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static java.util.List
		<com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry>
			getAssetAutoTaggerEntries(
				com.liferay.asset.kernel.model.AssetEntry assetEntry) {

		return getService().getAssetAutoTaggerEntries(assetEntry);
	}

	public static java.util.List
		<com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry>
			getAssetAutoTaggerEntries(
				com.liferay.asset.kernel.model.AssetTag assetTag) {

		return getService().getAssetAutoTaggerEntries(assetTag);
	}

	/**
	 * Returns a range of all the asset auto tagger entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.asset.auto.tagger.model.impl.AssetAutoTaggerEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset auto tagger entries
	 * @param end the upper bound of the range of asset auto tagger entries (not inclusive)
	 * @return the range of asset auto tagger entries
	 */
	public static java.util.List
		<com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry>
			getAssetAutoTaggerEntries(int start, int end) {

		return getService().getAssetAutoTaggerEntries(start, end);
	}

	/**
	 * Returns the number of asset auto tagger entries.
	 *
	 * @return the number of asset auto tagger entries
	 */
	public static int getAssetAutoTaggerEntriesCount() {
		return getService().getAssetAutoTaggerEntriesCount();
	}

	/**
	 * Returns the asset auto tagger entry with the primary key.
	 *
	 * @param assetAutoTaggerEntryId the primary key of the asset auto tagger entry
	 * @return the asset auto tagger entry
	 * @throws PortalException if a asset auto tagger entry with the primary key could not be found
	 */
	public static com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry
			getAssetAutoTaggerEntry(long assetAutoTaggerEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAssetAutoTaggerEntry(assetAutoTaggerEntryId);
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
	 * Updates the asset auto tagger entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param assetAutoTaggerEntry the asset auto tagger entry
	 * @return the asset auto tagger entry that was updated
	 */
	public static com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry
		updateAssetAutoTaggerEntry(
			com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry
				assetAutoTaggerEntry) {

		return getService().updateAssetAutoTaggerEntry(assetAutoTaggerEntry);
	}

	public static AssetAutoTaggerEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<AssetAutoTaggerEntryLocalService, AssetAutoTaggerEntryLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			AssetAutoTaggerEntryLocalService.class);

		ServiceTracker
			<AssetAutoTaggerEntryLocalService, AssetAutoTaggerEntryLocalService>
				serviceTracker =
					new ServiceTracker
						<AssetAutoTaggerEntryLocalService,
						 AssetAutoTaggerEntryLocalService>(
							 bundle.getBundleContext(),
							 AssetAutoTaggerEntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}