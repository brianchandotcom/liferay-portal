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

package com.liferay.changeset.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for ChangesetCollection. This utility wraps
 * <code>com.liferay.changeset.service.impl.ChangesetCollectionLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ChangesetCollectionLocalService
 * @generated
 */
public class ChangesetCollectionLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.changeset.service.impl.ChangesetCollectionLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the changeset collection to the database. Also notifies the appropriate model listeners.
	 *
	 * @param changesetCollection the changeset collection
	 * @return the changeset collection that was added
	 */
	public static com.liferay.changeset.model.ChangesetCollection
		addChangesetCollection(
			com.liferay.changeset.model.ChangesetCollection
				changesetCollection) {

		return getService().addChangesetCollection(changesetCollection);
	}

	public static com.liferay.changeset.model.ChangesetCollection
			addChangesetCollection(
				long userId, long groupId, String name, String description)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addChangesetCollection(
			userId, groupId, name, description);
	}

	/**
	 * Creates a new changeset collection with the primary key. Does not add the changeset collection to the database.
	 *
	 * @param changesetCollectionId the primary key for the new changeset collection
	 * @return the new changeset collection
	 */
	public static com.liferay.changeset.model.ChangesetCollection
		createChangesetCollection(long changesetCollectionId) {

		return getService().createChangesetCollection(changesetCollectionId);
	}

	/**
	 * Deletes the changeset collection from the database. Also notifies the appropriate model listeners.
	 *
	 * @param changesetCollection the changeset collection
	 * @return the changeset collection that was removed
	 */
	public static com.liferay.changeset.model.ChangesetCollection
		deleteChangesetCollection(
			com.liferay.changeset.model.ChangesetCollection
				changesetCollection) {

		return getService().deleteChangesetCollection(changesetCollection);
	}

	/**
	 * Deletes the changeset collection with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param changesetCollectionId the primary key of the changeset collection
	 * @return the changeset collection that was removed
	 * @throws PortalException if a changeset collection with the primary key could not be found
	 */
	public static com.liferay.changeset.model.ChangesetCollection
			deleteChangesetCollection(long changesetCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteChangesetCollection(changesetCollectionId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.changeset.model.impl.ChangesetCollectionModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.changeset.model.impl.ChangesetCollectionModelImpl</code>.
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

	public static com.liferay.changeset.model.ChangesetCollection
		fetchChangesetCollection(long changesetCollectionId) {

		return getService().fetchChangesetCollection(changesetCollectionId);
	}

	public static com.liferay.changeset.model.ChangesetCollection
		fetchChangesetCollection(long groupId, String name) {

		return getService().fetchChangesetCollection(groupId, name);
	}

	public static com.liferay.changeset.model.ChangesetCollection
			fetchOrAddChangesetCollection(long groupId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchOrAddChangesetCollection(groupId, name);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the changeset collection with the primary key.
	 *
	 * @param changesetCollectionId the primary key of the changeset collection
	 * @return the changeset collection
	 * @throws PortalException if a changeset collection with the primary key could not be found
	 */
	public static com.liferay.changeset.model.ChangesetCollection
			getChangesetCollection(long changesetCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getChangesetCollection(changesetCollectionId);
	}

	public static com.liferay.changeset.model.ChangesetCollection
			getChangesetCollection(long groupId, String name)
		throws com.liferay.changeset.exception.NoSuchCollectionException {

		return getService().getChangesetCollection(groupId, name);
	}

	/**
	 * Returns a range of all the changeset collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.changeset.model.impl.ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @return the range of changeset collections
	 */
	public static java.util.List
		<com.liferay.changeset.model.ChangesetCollection>
			getChangesetCollections(int start, int end) {

		return getService().getChangesetCollections(start, end);
	}

	/**
	 * Returns the number of changeset collections.
	 *
	 * @return the number of changeset collections
	 */
	public static int getChangesetCollectionsCount() {
		return getService().getChangesetCollectionsCount();
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
	 * Updates the changeset collection in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param changesetCollection the changeset collection
	 * @return the changeset collection that was updated
	 */
	public static com.liferay.changeset.model.ChangesetCollection
		updateChangesetCollection(
			com.liferay.changeset.model.ChangesetCollection
				changesetCollection) {

		return getService().updateChangesetCollection(changesetCollection);
	}

	public static ChangesetCollectionLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<ChangesetCollectionLocalService, ChangesetCollectionLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			ChangesetCollectionLocalService.class);

		ServiceTracker
			<ChangesetCollectionLocalService, ChangesetCollectionLocalService>
				serviceTracker =
					new ServiceTracker
						<ChangesetCollectionLocalService,
						 ChangesetCollectionLocalService>(
							 bundle.getBundleContext(),
							 ChangesetCollectionLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}