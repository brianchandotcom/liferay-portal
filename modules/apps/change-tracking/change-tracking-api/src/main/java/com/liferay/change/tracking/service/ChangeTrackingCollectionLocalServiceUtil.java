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

package com.liferay.change.tracking.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for ChangeTrackingCollection. This utility wraps
 * {@link com.liferay.change.tracking.service.impl.ChangeTrackingCollectionLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ChangeTrackingCollectionLocalService
 * @see com.liferay.change.tracking.service.base.ChangeTrackingCollectionLocalServiceBaseImpl
 * @see com.liferay.change.tracking.service.impl.ChangeTrackingCollectionLocalServiceImpl
 * @generated
 */
@ProviderType
public class ChangeTrackingCollectionLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.change.tracking.service.impl.ChangeTrackingCollectionLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the change tracking collection to the database. Also notifies the appropriate model listeners.
	*
	* @param changeTrackingCollection the change tracking collection
	* @return the change tracking collection that was added
	*/
	public static com.liferay.change.tracking.model.ChangeTrackingCollection addChangeTrackingCollection(
		com.liferay.change.tracking.model.ChangeTrackingCollection changeTrackingCollection) {
		return getService().addChangeTrackingCollection(changeTrackingCollection);
	}

	public static void addChangeTrackingEntryChangeTrackingCollection(
		long changeTrackingEntryId,
		com.liferay.change.tracking.model.ChangeTrackingCollection changeTrackingCollection) {
		getService()
			.addChangeTrackingEntryChangeTrackingCollection(changeTrackingEntryId,
			changeTrackingCollection);
	}

	public static void addChangeTrackingEntryChangeTrackingCollection(
		long changeTrackingEntryId, long changeTrackingCollectionId) {
		getService()
			.addChangeTrackingEntryChangeTrackingCollection(changeTrackingEntryId,
			changeTrackingCollectionId);
	}

	public static void addChangeTrackingEntryChangeTrackingCollections(
		long changeTrackingEntryId,
		java.util.List<com.liferay.change.tracking.model.ChangeTrackingCollection> changeTrackingCollections) {
		getService()
			.addChangeTrackingEntryChangeTrackingCollections(changeTrackingEntryId,
			changeTrackingCollections);
	}

	public static void addChangeTrackingEntryChangeTrackingCollections(
		long changeTrackingEntryId, long[] changeTrackingCollectionIds) {
		getService()
			.addChangeTrackingEntryChangeTrackingCollections(changeTrackingEntryId,
			changeTrackingCollectionIds);
	}

	public static void clearChangeTrackingEntryChangeTrackingCollections(
		long changeTrackingEntryId) {
		getService()
			.clearChangeTrackingEntryChangeTrackingCollections(changeTrackingEntryId);
	}

	/**
	* Creates a new change tracking collection with the primary key. Does not add the change tracking collection to the database.
	*
	* @param changeTrackingCollectionId the primary key for the new change tracking collection
	* @return the new change tracking collection
	*/
	public static com.liferay.change.tracking.model.ChangeTrackingCollection createChangeTrackingCollection(
		long changeTrackingCollectionId) {
		return getService()
				   .createChangeTrackingCollection(changeTrackingCollectionId);
	}

	/**
	* Deletes the change tracking collection from the database. Also notifies the appropriate model listeners.
	*
	* @param changeTrackingCollection the change tracking collection
	* @return the change tracking collection that was removed
	*/
	public static com.liferay.change.tracking.model.ChangeTrackingCollection deleteChangeTrackingCollection(
		com.liferay.change.tracking.model.ChangeTrackingCollection changeTrackingCollection) {
		return getService()
				   .deleteChangeTrackingCollection(changeTrackingCollection);
	}

	/**
	* Deletes the change tracking collection with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param changeTrackingCollectionId the primary key of the change tracking collection
	* @return the change tracking collection that was removed
	* @throws PortalException if a change tracking collection with the primary key could not be found
	*/
	public static com.liferay.change.tracking.model.ChangeTrackingCollection deleteChangeTrackingCollection(
		long changeTrackingCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteChangeTrackingCollection(changeTrackingCollectionId);
	}

	public static void deleteChangeTrackingEntryChangeTrackingCollection(
		long changeTrackingEntryId,
		com.liferay.change.tracking.model.ChangeTrackingCollection changeTrackingCollection) {
		getService()
			.deleteChangeTrackingEntryChangeTrackingCollection(changeTrackingEntryId,
			changeTrackingCollection);
	}

	public static void deleteChangeTrackingEntryChangeTrackingCollection(
		long changeTrackingEntryId, long changeTrackingCollectionId) {
		getService()
			.deleteChangeTrackingEntryChangeTrackingCollection(changeTrackingEntryId,
			changeTrackingCollectionId);
	}

	public static void deleteChangeTrackingEntryChangeTrackingCollections(
		long changeTrackingEntryId,
		java.util.List<com.liferay.change.tracking.model.ChangeTrackingCollection> changeTrackingCollections) {
		getService()
			.deleteChangeTrackingEntryChangeTrackingCollections(changeTrackingEntryId,
			changeTrackingCollections);
	}

	public static void deleteChangeTrackingEntryChangeTrackingCollections(
		long changeTrackingEntryId, long[] changeTrackingCollectionIds) {
		getService()
			.deleteChangeTrackingEntryChangeTrackingCollections(changeTrackingEntryId,
			changeTrackingCollectionIds);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.change.tracking.model.impl.ChangeTrackingCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.change.tracking.model.impl.ChangeTrackingCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.change.tracking.model.ChangeTrackingCollection fetchChangeTrackingCollection(
		long changeTrackingCollectionId) {
		return getService()
				   .fetchChangeTrackingCollection(changeTrackingCollectionId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the change tracking collection with the primary key.
	*
	* @param changeTrackingCollectionId the primary key of the change tracking collection
	* @return the change tracking collection
	* @throws PortalException if a change tracking collection with the primary key could not be found
	*/
	public static com.liferay.change.tracking.model.ChangeTrackingCollection getChangeTrackingCollection(
		long changeTrackingCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getChangeTrackingCollection(changeTrackingCollectionId);
	}

	/**
	* Returns a range of all the change tracking collections.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.change.tracking.model.impl.ChangeTrackingCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of change tracking collections
	* @param end the upper bound of the range of change tracking collections (not inclusive)
	* @return the range of change tracking collections
	*/
	public static java.util.List<com.liferay.change.tracking.model.ChangeTrackingCollection> getChangeTrackingCollections(
		int start, int end) {
		return getService().getChangeTrackingCollections(start, end);
	}

	/**
	* Returns the number of change tracking collections.
	*
	* @return the number of change tracking collections
	*/
	public static int getChangeTrackingCollectionsCount() {
		return getService().getChangeTrackingCollectionsCount();
	}

	public static java.util.List<com.liferay.change.tracking.model.ChangeTrackingCollection> getChangeTrackingEntryChangeTrackingCollections(
		long changeTrackingEntryId) {
		return getService()
				   .getChangeTrackingEntryChangeTrackingCollections(changeTrackingEntryId);
	}

	public static java.util.List<com.liferay.change.tracking.model.ChangeTrackingCollection> getChangeTrackingEntryChangeTrackingCollections(
		long changeTrackingEntryId, int start, int end) {
		return getService()
				   .getChangeTrackingEntryChangeTrackingCollections(changeTrackingEntryId,
			start, end);
	}

	public static java.util.List<com.liferay.change.tracking.model.ChangeTrackingCollection> getChangeTrackingEntryChangeTrackingCollections(
		long changeTrackingEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.change.tracking.model.ChangeTrackingCollection> orderByComparator) {
		return getService()
				   .getChangeTrackingEntryChangeTrackingCollections(changeTrackingEntryId,
			start, end, orderByComparator);
	}

	public static int getChangeTrackingEntryChangeTrackingCollectionsCount(
		long changeTrackingEntryId) {
		return getService()
				   .getChangeTrackingEntryChangeTrackingCollectionsCount(changeTrackingEntryId);
	}

	/**
	* Returns the changeTrackingEntryIds of the change tracking entries associated with the change tracking collection.
	*
	* @param changeTrackingCollectionId the changeTrackingCollectionId of the change tracking collection
	* @return long[] the changeTrackingEntryIds of change tracking entries associated with the change tracking collection
	*/
	public static long[] getChangeTrackingEntryPrimaryKeys(
		long changeTrackingCollectionId) {
		return getService()
				   .getChangeTrackingEntryPrimaryKeys(changeTrackingCollectionId);
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

	public static boolean hasChangeTrackingEntryChangeTrackingCollection(
		long changeTrackingEntryId, long changeTrackingCollectionId) {
		return getService()
				   .hasChangeTrackingEntryChangeTrackingCollection(changeTrackingEntryId,
			changeTrackingCollectionId);
	}

	public static boolean hasChangeTrackingEntryChangeTrackingCollections(
		long changeTrackingEntryId) {
		return getService()
				   .hasChangeTrackingEntryChangeTrackingCollections(changeTrackingEntryId);
	}

	public static void setChangeTrackingEntryChangeTrackingCollections(
		long changeTrackingEntryId, long[] changeTrackingCollectionIds) {
		getService()
			.setChangeTrackingEntryChangeTrackingCollections(changeTrackingEntryId,
			changeTrackingCollectionIds);
	}

	/**
	* Updates the change tracking collection in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param changeTrackingCollection the change tracking collection
	* @return the change tracking collection that was updated
	*/
	public static com.liferay.change.tracking.model.ChangeTrackingCollection updateChangeTrackingCollection(
		com.liferay.change.tracking.model.ChangeTrackingCollection changeTrackingCollection) {
		return getService()
				   .updateChangeTrackingCollection(changeTrackingCollection);
	}

	public static ChangeTrackingCollectionLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<ChangeTrackingCollectionLocalService, ChangeTrackingCollectionLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ChangeTrackingCollectionLocalService.class);

		ServiceTracker<ChangeTrackingCollectionLocalService, ChangeTrackingCollectionLocalService> serviceTracker =
			new ServiceTracker<ChangeTrackingCollectionLocalService, ChangeTrackingCollectionLocalService>(bundle.getBundleContext(),
				ChangeTrackingCollectionLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}