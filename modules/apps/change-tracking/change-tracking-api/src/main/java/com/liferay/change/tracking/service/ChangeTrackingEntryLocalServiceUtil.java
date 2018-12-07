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
 * Provides the local service utility for ChangeTrackingEntry. This utility wraps
 * {@link com.liferay.change.tracking.service.impl.ChangeTrackingEntryLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ChangeTrackingEntryLocalService
 * @see com.liferay.change.tracking.service.base.ChangeTrackingEntryLocalServiceBaseImpl
 * @see com.liferay.change.tracking.service.impl.ChangeTrackingEntryLocalServiceImpl
 * @generated
 */
@ProviderType
public class ChangeTrackingEntryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.change.tracking.service.impl.ChangeTrackingEntryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static void addChangeTrackingCollectionChangeTrackingEntries(
		long changeTrackingCollectionId,
		java.util.List<com.liferay.change.tracking.model.ChangeTrackingEntry> changeTrackingEntries) {
		getService()
			.addChangeTrackingCollectionChangeTrackingEntries(changeTrackingCollectionId,
			changeTrackingEntries);
	}

	public static void addChangeTrackingCollectionChangeTrackingEntries(
		long changeTrackingCollectionId, long[] changeTrackingEntryIds) {
		getService()
			.addChangeTrackingCollectionChangeTrackingEntries(changeTrackingCollectionId,
			changeTrackingEntryIds);
	}

	public static void addChangeTrackingCollectionChangeTrackingEntry(
		long changeTrackingCollectionId,
		com.liferay.change.tracking.model.ChangeTrackingEntry changeTrackingEntry) {
		getService()
			.addChangeTrackingCollectionChangeTrackingEntry(changeTrackingCollectionId,
			changeTrackingEntry);
	}

	public static void addChangeTrackingCollectionChangeTrackingEntry(
		long changeTrackingCollectionId, long changeTrackingEntryId) {
		getService()
			.addChangeTrackingCollectionChangeTrackingEntry(changeTrackingCollectionId,
			changeTrackingEntryId);
	}

	/**
	* Adds the change tracking entry to the database. Also notifies the appropriate model listeners.
	*
	* @param changeTrackingEntry the change tracking entry
	* @return the change tracking entry that was added
	*/
	public static com.liferay.change.tracking.model.ChangeTrackingEntry addChangeTrackingEntry(
		com.liferay.change.tracking.model.ChangeTrackingEntry changeTrackingEntry) {
		return getService().addChangeTrackingEntry(changeTrackingEntry);
	}

	public static void clearChangeTrackingCollectionChangeTrackingEntries(
		long changeTrackingCollectionId) {
		getService()
			.clearChangeTrackingCollectionChangeTrackingEntries(changeTrackingCollectionId);
	}

	/**
	* Creates a new change tracking entry with the primary key. Does not add the change tracking entry to the database.
	*
	* @param changeTrackingEntryId the primary key for the new change tracking entry
	* @return the new change tracking entry
	*/
	public static com.liferay.change.tracking.model.ChangeTrackingEntry createChangeTrackingEntry(
		long changeTrackingEntryId) {
		return getService().createChangeTrackingEntry(changeTrackingEntryId);
	}

	public static void deleteChangeTrackingCollectionChangeTrackingEntries(
		long changeTrackingCollectionId,
		java.util.List<com.liferay.change.tracking.model.ChangeTrackingEntry> changeTrackingEntries) {
		getService()
			.deleteChangeTrackingCollectionChangeTrackingEntries(changeTrackingCollectionId,
			changeTrackingEntries);
	}

	public static void deleteChangeTrackingCollectionChangeTrackingEntries(
		long changeTrackingCollectionId, long[] changeTrackingEntryIds) {
		getService()
			.deleteChangeTrackingCollectionChangeTrackingEntries(changeTrackingCollectionId,
			changeTrackingEntryIds);
	}

	public static void deleteChangeTrackingCollectionChangeTrackingEntry(
		long changeTrackingCollectionId,
		com.liferay.change.tracking.model.ChangeTrackingEntry changeTrackingEntry) {
		getService()
			.deleteChangeTrackingCollectionChangeTrackingEntry(changeTrackingCollectionId,
			changeTrackingEntry);
	}

	public static void deleteChangeTrackingCollectionChangeTrackingEntry(
		long changeTrackingCollectionId, long changeTrackingEntryId) {
		getService()
			.deleteChangeTrackingCollectionChangeTrackingEntry(changeTrackingCollectionId,
			changeTrackingEntryId);
	}

	/**
	* Deletes the change tracking entry from the database. Also notifies the appropriate model listeners.
	*
	* @param changeTrackingEntry the change tracking entry
	* @return the change tracking entry that was removed
	*/
	public static com.liferay.change.tracking.model.ChangeTrackingEntry deleteChangeTrackingEntry(
		com.liferay.change.tracking.model.ChangeTrackingEntry changeTrackingEntry) {
		return getService().deleteChangeTrackingEntry(changeTrackingEntry);
	}

	/**
	* Deletes the change tracking entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param changeTrackingEntryId the primary key of the change tracking entry
	* @return the change tracking entry that was removed
	* @throws PortalException if a change tracking entry with the primary key could not be found
	*/
	public static com.liferay.change.tracking.model.ChangeTrackingEntry deleteChangeTrackingEntry(
		long changeTrackingEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteChangeTrackingEntry(changeTrackingEntryId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.change.tracking.model.impl.ChangeTrackingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.change.tracking.model.impl.ChangeTrackingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.change.tracking.model.ChangeTrackingEntry fetchChangeTrackingEntry(
		long changeTrackingEntryId) {
		return getService().fetchChangeTrackingEntry(changeTrackingEntryId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static java.util.List<com.liferay.change.tracking.model.ChangeTrackingEntry> getChangeTrackingCollectionChangeTrackingEntries(
		long changeTrackingCollectionId) {
		return getService()
				   .getChangeTrackingCollectionChangeTrackingEntries(changeTrackingCollectionId);
	}

	public static java.util.List<com.liferay.change.tracking.model.ChangeTrackingEntry> getChangeTrackingCollectionChangeTrackingEntries(
		long changeTrackingCollectionId, int start, int end) {
		return getService()
				   .getChangeTrackingCollectionChangeTrackingEntries(changeTrackingCollectionId,
			start, end);
	}

	public static java.util.List<com.liferay.change.tracking.model.ChangeTrackingEntry> getChangeTrackingCollectionChangeTrackingEntries(
		long changeTrackingCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.change.tracking.model.ChangeTrackingEntry> orderByComparator) {
		return getService()
				   .getChangeTrackingCollectionChangeTrackingEntries(changeTrackingCollectionId,
			start, end, orderByComparator);
	}

	public static int getChangeTrackingCollectionChangeTrackingEntriesCount(
		long changeTrackingCollectionId) {
		return getService()
				   .getChangeTrackingCollectionChangeTrackingEntriesCount(changeTrackingCollectionId);
	}

	/**
	* Returns the changeTrackingCollectionIds of the change tracking collections associated with the change tracking entry.
	*
	* @param changeTrackingEntryId the changeTrackingEntryId of the change tracking entry
	* @return long[] the changeTrackingCollectionIds of change tracking collections associated with the change tracking entry
	*/
	public static long[] getChangeTrackingCollectionPrimaryKeys(
		long changeTrackingEntryId) {
		return getService()
				   .getChangeTrackingCollectionPrimaryKeys(changeTrackingEntryId);
	}

	/**
	* Returns a range of all the change tracking entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.change.tracking.model.impl.ChangeTrackingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of change tracking entries
	* @param end the upper bound of the range of change tracking entries (not inclusive)
	* @return the range of change tracking entries
	*/
	public static java.util.List<com.liferay.change.tracking.model.ChangeTrackingEntry> getChangeTrackingEntries(
		int start, int end) {
		return getService().getChangeTrackingEntries(start, end);
	}

	/**
	* Returns the number of change tracking entries.
	*
	* @return the number of change tracking entries
	*/
	public static int getChangeTrackingEntriesCount() {
		return getService().getChangeTrackingEntriesCount();
	}

	/**
	* Returns the change tracking entry with the primary key.
	*
	* @param changeTrackingEntryId the primary key of the change tracking entry
	* @return the change tracking entry
	* @throws PortalException if a change tracking entry with the primary key could not be found
	*/
	public static com.liferay.change.tracking.model.ChangeTrackingEntry getChangeTrackingEntry(
		long changeTrackingEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getChangeTrackingEntry(changeTrackingEntryId);
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

	public static java.util.List<?extends com.liferay.portal.kernel.model.PersistedModel> getPersistedModel(
		long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(resourcePrimKey);
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	public static boolean hasChangeTrackingCollectionChangeTrackingEntries(
		long changeTrackingCollectionId) {
		return getService()
				   .hasChangeTrackingCollectionChangeTrackingEntries(changeTrackingCollectionId);
	}

	public static boolean hasChangeTrackingCollectionChangeTrackingEntry(
		long changeTrackingCollectionId, long changeTrackingEntryId) {
		return getService()
				   .hasChangeTrackingCollectionChangeTrackingEntry(changeTrackingCollectionId,
			changeTrackingEntryId);
	}

	public static void setChangeTrackingCollectionChangeTrackingEntries(
		long changeTrackingCollectionId, long[] changeTrackingEntryIds) {
		getService()
			.setChangeTrackingCollectionChangeTrackingEntries(changeTrackingCollectionId,
			changeTrackingEntryIds);
	}

	/**
	* Updates the change tracking entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param changeTrackingEntry the change tracking entry
	* @return the change tracking entry that was updated
	*/
	public static com.liferay.change.tracking.model.ChangeTrackingEntry updateChangeTrackingEntry(
		com.liferay.change.tracking.model.ChangeTrackingEntry changeTrackingEntry) {
		return getService().updateChangeTrackingEntry(changeTrackingEntry);
	}

	public static ChangeTrackingEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<ChangeTrackingEntryLocalService, ChangeTrackingEntryLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ChangeTrackingEntryLocalService.class);

		ServiceTracker<ChangeTrackingEntryLocalService, ChangeTrackingEntryLocalService> serviceTracker =
			new ServiceTracker<ChangeTrackingEntryLocalService, ChangeTrackingEntryLocalService>(bundle.getBundleContext(),
				ChangeTrackingEntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}