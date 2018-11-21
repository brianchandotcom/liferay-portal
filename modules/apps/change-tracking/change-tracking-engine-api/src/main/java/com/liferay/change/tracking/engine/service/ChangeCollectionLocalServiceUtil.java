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

package com.liferay.change.tracking.engine.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for ChangeCollection. This utility wraps
 * {@link com.liferay.change.tracking.engine.service.impl.ChangeCollectionLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ChangeCollectionLocalService
 * @see com.liferay.change.tracking.engine.service.base.ChangeCollectionLocalServiceBaseImpl
 * @see com.liferay.change.tracking.engine.service.impl.ChangeCollectionLocalServiceImpl
 * @generated
 */
@ProviderType
public class ChangeCollectionLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.change.tracking.engine.service.impl.ChangeCollectionLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the change collection to the database. Also notifies the appropriate model listeners.
	*
	* @param changeCollection the change collection
	* @return the change collection that was added
	*/
	public static com.liferay.change.tracking.engine.model.ChangeCollection addChangeCollection(
		com.liferay.change.tracking.engine.model.ChangeCollection changeCollection) {
		return getService().addChangeCollection(changeCollection);
	}

	public static void addChangeEntryChangeCollection(long changeEntryId,
		com.liferay.change.tracking.engine.model.ChangeCollection changeCollection) {
		getService()
			.addChangeEntryChangeCollection(changeEntryId, changeCollection);
	}

	public static void addChangeEntryChangeCollection(long changeEntryId,
		long changeCollectionId) {
		getService()
			.addChangeEntryChangeCollection(changeEntryId, changeCollectionId);
	}

	public static void addChangeEntryChangeCollections(long changeEntryId,
		java.util.List<com.liferay.change.tracking.engine.model.ChangeCollection> changeCollections) {
		getService()
			.addChangeEntryChangeCollections(changeEntryId, changeCollections);
	}

	public static void addChangeEntryChangeCollections(long changeEntryId,
		long[] changeCollectionIds) {
		getService()
			.addChangeEntryChangeCollections(changeEntryId, changeCollectionIds);
	}

	public static void clearChangeEntryChangeCollections(long changeEntryId) {
		getService().clearChangeEntryChangeCollections(changeEntryId);
	}

	/**
	* Creates a new change collection with the primary key. Does not add the change collection to the database.
	*
	* @param changeCollectionId the primary key for the new change collection
	* @return the new change collection
	*/
	public static com.liferay.change.tracking.engine.model.ChangeCollection createChangeCollection(
		long changeCollectionId) {
		return getService().createChangeCollection(changeCollectionId);
	}

	/**
	* Deletes the change collection from the database. Also notifies the appropriate model listeners.
	*
	* @param changeCollection the change collection
	* @return the change collection that was removed
	*/
	public static com.liferay.change.tracking.engine.model.ChangeCollection deleteChangeCollection(
		com.liferay.change.tracking.engine.model.ChangeCollection changeCollection) {
		return getService().deleteChangeCollection(changeCollection);
	}

	/**
	* Deletes the change collection with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param changeCollectionId the primary key of the change collection
	* @return the change collection that was removed
	* @throws PortalException if a change collection with the primary key could not be found
	*/
	public static com.liferay.change.tracking.engine.model.ChangeCollection deleteChangeCollection(
		long changeCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteChangeCollection(changeCollectionId);
	}

	public static void deleteChangeEntryChangeCollection(long changeEntryId,
		com.liferay.change.tracking.engine.model.ChangeCollection changeCollection) {
		getService()
			.deleteChangeEntryChangeCollection(changeEntryId, changeCollection);
	}

	public static void deleteChangeEntryChangeCollection(long changeEntryId,
		long changeCollectionId) {
		getService()
			.deleteChangeEntryChangeCollection(changeEntryId, changeCollectionId);
	}

	public static void deleteChangeEntryChangeCollections(long changeEntryId,
		java.util.List<com.liferay.change.tracking.engine.model.ChangeCollection> changeCollections) {
		getService()
			.deleteChangeEntryChangeCollections(changeEntryId, changeCollections);
	}

	public static void deleteChangeEntryChangeCollections(long changeEntryId,
		long[] changeCollectionIds) {
		getService()
			.deleteChangeEntryChangeCollections(changeEntryId,
			changeCollectionIds);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.change.tracking.engine.model.impl.ChangeCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.change.tracking.engine.model.impl.ChangeCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.change.tracking.engine.model.ChangeCollection fetchChangeCollection(
		long changeCollectionId) {
		return getService().fetchChangeCollection(changeCollectionId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the change collection with the primary key.
	*
	* @param changeCollectionId the primary key of the change collection
	* @return the change collection
	* @throws PortalException if a change collection with the primary key could not be found
	*/
	public static com.liferay.change.tracking.engine.model.ChangeCollection getChangeCollection(
		long changeCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getChangeCollection(changeCollectionId);
	}

	/**
	* Returns a range of all the change collections.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.change.tracking.engine.model.impl.ChangeCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of change collections
	* @param end the upper bound of the range of change collections (not inclusive)
	* @return the range of change collections
	*/
	public static java.util.List<com.liferay.change.tracking.engine.model.ChangeCollection> getChangeCollections(
		int start, int end) {
		return getService().getChangeCollections(start, end);
	}

	/**
	* Returns the number of change collections.
	*
	* @return the number of change collections
	*/
	public static int getChangeCollectionsCount() {
		return getService().getChangeCollectionsCount();
	}

	public static java.util.List<com.liferay.change.tracking.engine.model.ChangeCollection> getChangeEntryChangeCollections(
		long changeEntryId) {
		return getService().getChangeEntryChangeCollections(changeEntryId);
	}

	public static java.util.List<com.liferay.change.tracking.engine.model.ChangeCollection> getChangeEntryChangeCollections(
		long changeEntryId, int start, int end) {
		return getService()
				   .getChangeEntryChangeCollections(changeEntryId, start, end);
	}

	public static java.util.List<com.liferay.change.tracking.engine.model.ChangeCollection> getChangeEntryChangeCollections(
		long changeEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.change.tracking.engine.model.ChangeCollection> orderByComparator) {
		return getService()
				   .getChangeEntryChangeCollections(changeEntryId, start, end,
			orderByComparator);
	}

	public static int getChangeEntryChangeCollectionsCount(long changeEntryId) {
		return getService().getChangeEntryChangeCollectionsCount(changeEntryId);
	}

	/**
	* Returns the changeEntryIds of the change entries associated with the change collection.
	*
	* @param changeCollectionId the changeCollectionId of the change collection
	* @return long[] the changeEntryIds of change entries associated with the change collection
	*/
	public static long[] getChangeEntryPrimaryKeys(long changeCollectionId) {
		return getService().getChangeEntryPrimaryKeys(changeCollectionId);
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

	public static boolean hasChangeEntryChangeCollection(long changeEntryId,
		long changeCollectionId) {
		return getService()
				   .hasChangeEntryChangeCollection(changeEntryId,
			changeCollectionId);
	}

	public static boolean hasChangeEntryChangeCollections(long changeEntryId) {
		return getService().hasChangeEntryChangeCollections(changeEntryId);
	}

	public static void setChangeEntryChangeCollections(long changeEntryId,
		long[] changeCollectionIds) {
		getService()
			.setChangeEntryChangeCollections(changeEntryId, changeCollectionIds);
	}

	/**
	* Updates the change collection in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param changeCollection the change collection
	* @return the change collection that was updated
	*/
	public static com.liferay.change.tracking.engine.model.ChangeCollection updateChangeCollection(
		com.liferay.change.tracking.engine.model.ChangeCollection changeCollection) {
		return getService().updateChangeCollection(changeCollection);
	}

	public static ChangeCollectionLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<ChangeCollectionLocalService, ChangeCollectionLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ChangeCollectionLocalService.class);

		ServiceTracker<ChangeCollectionLocalService, ChangeCollectionLocalService> serviceTracker =
			new ServiceTracker<ChangeCollectionLocalService, ChangeCollectionLocalService>(bundle.getBundleContext(),
				ChangeCollectionLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}