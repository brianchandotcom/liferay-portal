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

package com.liferay.portal.tools.service.builder.test.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for NestedSetsTreeEntry. This utility wraps
 * <code>com.liferay.portal.tools.service.builder.test.service.impl.NestedSetsTreeEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see NestedSetsTreeEntryLocalService
 * @generated
 */
public class NestedSetsTreeEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.tools.service.builder.test.service.impl.NestedSetsTreeEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.NestedSetsTreeEntry
			addNestedSetsTreeEntry(long groupId) {

		return getService().addNestedSetsTreeEntry(groupId);
	}

	/**
	 * Adds the nested sets tree entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param nestedSetsTreeEntry the nested sets tree entry
	 * @return the nested sets tree entry that was added
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.NestedSetsTreeEntry
			addNestedSetsTreeEntry(
				com.liferay.portal.tools.service.builder.test.model.
					NestedSetsTreeEntry nestedSetsTreeEntry) {

		return getService().addNestedSetsTreeEntry(nestedSetsTreeEntry);
	}

	/**
	 * Creates a new nested sets tree entry with the primary key. Does not add the nested sets tree entry to the database.
	 *
	 * @param nestedSetsTreeEntryId the primary key for the new nested sets tree entry
	 * @return the new nested sets tree entry
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.NestedSetsTreeEntry
			createNestedSetsTreeEntry(long nestedSetsTreeEntryId) {

		return getService().createNestedSetsTreeEntry(nestedSetsTreeEntryId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			createPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the nested sets tree entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param nestedSetsTreeEntryId the primary key of the nested sets tree entry
	 * @return the nested sets tree entry that was removed
	 * @throws PortalException if a nested sets tree entry with the primary key could not be found
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.NestedSetsTreeEntry
				deleteNestedSetsTreeEntry(long nestedSetsTreeEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteNestedSetsTreeEntry(nestedSetsTreeEntryId);
	}

	/**
	 * Deletes the nested sets tree entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param nestedSetsTreeEntry the nested sets tree entry
	 * @return the nested sets tree entry that was removed
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.NestedSetsTreeEntry
			deleteNestedSetsTreeEntry(
				com.liferay.portal.tools.service.builder.test.model.
					NestedSetsTreeEntry nestedSetsTreeEntry) {

		return getService().deleteNestedSetsTreeEntry(nestedSetsTreeEntry);
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

	public static <T> T dslQuery(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return getService().dslQuery(dslQuery);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.NestedSetsTreeEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.NestedSetsTreeEntryModelImpl</code>.
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

	public static
		com.liferay.portal.tools.service.builder.test.model.NestedSetsTreeEntry
			fetchNestedSetsTreeEntry(long nestedSetsTreeEntryId) {

		return getService().fetchNestedSetsTreeEntry(nestedSetsTreeEntryId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the nested sets tree entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.NestedSetsTreeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of nested sets tree entries
	 * @param end the upper bound of the range of nested sets tree entries (not inclusive)
	 * @return the range of nested sets tree entries
	 */
	public static java.util.List
		<com.liferay.portal.tools.service.builder.test.model.
			NestedSetsTreeEntry> getNestedSetsTreeEntries(int start, int end) {

		return getService().getNestedSetsTreeEntries(start, end);
	}

	/**
	 * Returns the number of nested sets tree entries.
	 *
	 * @return the number of nested sets tree entries
	 */
	public static int getNestedSetsTreeEntriesCount() {
		return getService().getNestedSetsTreeEntriesCount();
	}

	/**
	 * Returns the nested sets tree entry with the primary key.
	 *
	 * @param nestedSetsTreeEntryId the primary key of the nested sets tree entry
	 * @return the nested sets tree entry
	 * @throws PortalException if a nested sets tree entry with the primary key could not be found
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.NestedSetsTreeEntry
				getNestedSetsTreeEntry(long nestedSetsTreeEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getNestedSetsTreeEntry(nestedSetsTreeEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the nested sets tree entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param nestedSetsTreeEntry the nested sets tree entry
	 * @return the nested sets tree entry that was updated
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.NestedSetsTreeEntry
			updateNestedSetsTreeEntry(
				com.liferay.portal.tools.service.builder.test.model.
					NestedSetsTreeEntry nestedSetsTreeEntry) {

		return getService().updateNestedSetsTreeEntry(nestedSetsTreeEntry);
	}

	public static NestedSetsTreeEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<NestedSetsTreeEntryLocalService, NestedSetsTreeEntryLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			NestedSetsTreeEntryLocalService.class);

		ServiceTracker
			<NestedSetsTreeEntryLocalService, NestedSetsTreeEntryLocalService>
				serviceTracker =
					new ServiceTracker
						<NestedSetsTreeEntryLocalService,
						 NestedSetsTreeEntryLocalService>(
							 bundle.getBundleContext(),
							 NestedSetsTreeEntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}