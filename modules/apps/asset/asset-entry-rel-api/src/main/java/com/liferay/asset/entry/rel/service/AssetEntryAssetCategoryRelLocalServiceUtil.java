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

package com.liferay.asset.entry.rel.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for AssetEntryAssetCategoryRel. This utility wraps
 * <code>com.liferay.asset.entry.rel.service.impl.AssetEntryAssetCategoryRelLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryAssetCategoryRelLocalService
 * @generated
 */
public class AssetEntryAssetCategoryRelLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.asset.entry.rel.service.impl.AssetEntryAssetCategoryRelLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the asset entry asset category rel to the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetEntryAssetCategoryRel the asset entry asset category rel
	 * @return the asset entry asset category rel that was added
	 */
	public static com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRel
		addAssetEntryAssetCategoryRel(
			com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRel
				assetEntryAssetCategoryRel) {

		return getService().addAssetEntryAssetCategoryRel(
			assetEntryAssetCategoryRel);
	}

	public static com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRel
		addAssetEntryAssetCategoryRel(long assetEntryId, long assetCategoryId) {

		return getService().addAssetEntryAssetCategoryRel(
			assetEntryId, assetCategoryId);
	}

	public static com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRel
		addAssetEntryAssetCategoryRel(
			long assetEntryId, long assetCategoryId, int priority) {

		return getService().addAssetEntryAssetCategoryRel(
			assetEntryId, assetCategoryId, priority);
	}

	/**
	 * Creates a new asset entry asset category rel with the primary key. Does not add the asset entry asset category rel to the database.
	 *
	 * @param assetEntryAssetCategoryRelId the primary key for the new asset entry asset category rel
	 * @return the new asset entry asset category rel
	 */
	public static com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRel
		createAssetEntryAssetCategoryRel(long assetEntryAssetCategoryRelId) {

		return getService().createAssetEntryAssetCategoryRel(
			assetEntryAssetCategoryRelId);
	}

	/**
	 * Deletes the asset entry asset category rel from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetEntryAssetCategoryRel the asset entry asset category rel
	 * @return the asset entry asset category rel that was removed
	 */
	public static com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRel
		deleteAssetEntryAssetCategoryRel(
			com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRel
				assetEntryAssetCategoryRel) {

		return getService().deleteAssetEntryAssetCategoryRel(
			assetEntryAssetCategoryRel);
	}

	/**
	 * Deletes the asset entry asset category rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetEntryAssetCategoryRelId the primary key of the asset entry asset category rel
	 * @return the asset entry asset category rel that was removed
	 * @throws PortalException if a asset entry asset category rel with the primary key could not be found
	 */
	public static com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRel
			deleteAssetEntryAssetCategoryRel(long assetEntryAssetCategoryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteAssetEntryAssetCategoryRel(
			assetEntryAssetCategoryRelId);
	}

	public static void deleteAssetEntryAssetCategoryRel(
		long assetEntryId, long assetCategoryId) {

		getService().deleteAssetEntryAssetCategoryRel(
			assetEntryId, assetCategoryId);
	}

	public static void deleteAssetEntryAssetCategoryRelByAssetCategoryId(
		long assetCategoryId) {

		getService().deleteAssetEntryAssetCategoryRelByAssetCategoryId(
			assetCategoryId);
	}

	public static void deleteAssetEntryAssetCategoryRelByAssetEntryId(
		long assetEntryId) {

		getService().deleteAssetEntryAssetCategoryRelByAssetEntryId(
			assetEntryId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.asset.entry.rel.model.impl.AssetEntryAssetCategoryRelModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.asset.entry.rel.model.impl.AssetEntryAssetCategoryRelModelImpl</code>.
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

	public static com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRel
		fetchAssetEntryAssetCategoryRel(long assetEntryAssetCategoryRelId) {

		return getService().fetchAssetEntryAssetCategoryRel(
			assetEntryAssetCategoryRelId);
	}

	public static com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRel
		fetchAssetEntryAssetCategoryRel(
			long assetEntryId, long assetCategoryId) {

		return getService().fetchAssetEntryAssetCategoryRel(
			assetEntryId, assetCategoryId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static long[] getAssetCategoryPrimaryKeys(long assetEntryId) {
		return getService().getAssetCategoryPrimaryKeys(assetEntryId);
	}

	/**
	 * Returns the asset entry asset category rel with the primary key.
	 *
	 * @param assetEntryAssetCategoryRelId the primary key of the asset entry asset category rel
	 * @return the asset entry asset category rel
	 * @throws PortalException if a asset entry asset category rel with the primary key could not be found
	 */
	public static com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRel
			getAssetEntryAssetCategoryRel(long assetEntryAssetCategoryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAssetEntryAssetCategoryRel(
			assetEntryAssetCategoryRelId);
	}

	/**
	 * Returns a range of all the asset entry asset category rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.asset.entry.rel.model.impl.AssetEntryAssetCategoryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entry asset category rels
	 * @param end the upper bound of the range of asset entry asset category rels (not inclusive)
	 * @return the range of asset entry asset category rels
	 */
	public static java.util.List
		<com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRel>
			getAssetEntryAssetCategoryRels(int start, int end) {

		return getService().getAssetEntryAssetCategoryRels(start, end);
	}

	public static java.util.List
		<com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRel>
			getAssetEntryAssetCategoryRelsByAssetCategoryId(
				long assetCategoryId) {

		return getService().getAssetEntryAssetCategoryRelsByAssetCategoryId(
			assetCategoryId);
	}

	public static java.util.List
		<com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRel>
			getAssetEntryAssetCategoryRelsByAssetCategoryId(
				long assetCategoryId, int start, int end) {

		return getService().getAssetEntryAssetCategoryRelsByAssetCategoryId(
			assetCategoryId, start, end);
	}

	public static java.util.List
		<com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRel>
			getAssetEntryAssetCategoryRelsByAssetCategoryId(
				long assetCategoryId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.asset.entry.rel.model.
						AssetEntryAssetCategoryRel> orderByComparator) {

		return getService().getAssetEntryAssetCategoryRelsByAssetCategoryId(
			assetCategoryId, start, end, orderByComparator);
	}

	public static java.util.List
		<com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRel>
			getAssetEntryAssetCategoryRelsByAssetEntryId(long assetEntryId) {

		return getService().getAssetEntryAssetCategoryRelsByAssetEntryId(
			assetEntryId);
	}

	public static java.util.List
		<com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRel>
			getAssetEntryAssetCategoryRelsByAssetEntryId(
				long assetEntryId, int start, int end) {

		return getService().getAssetEntryAssetCategoryRelsByAssetEntryId(
			assetEntryId, start, end);
	}

	public static java.util.List
		<com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRel>
			getAssetEntryAssetCategoryRelsByAssetEntryId(
				long assetEntryId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.asset.entry.rel.model.
						AssetEntryAssetCategoryRel> orderByComparator) {

		return getService().getAssetEntryAssetCategoryRelsByAssetEntryId(
			assetEntryId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of asset entry asset category rels.
	 *
	 * @return the number of asset entry asset category rels
	 */
	public static int getAssetEntryAssetCategoryRelsCount() {
		return getService().getAssetEntryAssetCategoryRelsCount();
	}

	public static int getAssetEntryAssetCategoryRelsCount(long assetEntryId) {
		return getService().getAssetEntryAssetCategoryRelsCount(assetEntryId);
	}

	public static long[] getAssetEntryPrimaryKeys(long assetCategoryId) {
		return getService().getAssetEntryPrimaryKeys(assetCategoryId);
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
	 * Updates the asset entry asset category rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param assetEntryAssetCategoryRel the asset entry asset category rel
	 * @return the asset entry asset category rel that was updated
	 */
	public static com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRel
		updateAssetEntryAssetCategoryRel(
			com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRel
				assetEntryAssetCategoryRel) {

		return getService().updateAssetEntryAssetCategoryRel(
			assetEntryAssetCategoryRel);
	}

	public static AssetEntryAssetCategoryRelLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<AssetEntryAssetCategoryRelLocalService,
		 AssetEntryAssetCategoryRelLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			AssetEntryAssetCategoryRelLocalService.class);

		ServiceTracker
			<AssetEntryAssetCategoryRelLocalService,
			 AssetEntryAssetCategoryRelLocalService> serviceTracker =
				new ServiceTracker
					<AssetEntryAssetCategoryRelLocalService,
					 AssetEntryAssetCategoryRelLocalService>(
						 bundle.getBundleContext(),
						 AssetEntryAssetCategoryRelLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}