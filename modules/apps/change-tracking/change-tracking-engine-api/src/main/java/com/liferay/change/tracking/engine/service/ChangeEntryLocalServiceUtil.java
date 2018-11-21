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
 * Provides the local service utility for ChangeEntry. This utility wraps
 * {@link com.liferay.change.tracking.engine.service.impl.ChangeEntryLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ChangeEntryLocalService
 * @see com.liferay.change.tracking.engine.service.base.ChangeEntryLocalServiceBaseImpl
 * @see com.liferay.change.tracking.engine.service.impl.ChangeEntryLocalServiceImpl
 * @generated
 */
@ProviderType
public class ChangeEntryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.change.tracking.engine.service.impl.ChangeEntryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static void addChangeCollectionChangeEntries(
		long changeCollectionId,
		java.util.List<com.liferay.change.tracking.engine.model.ChangeEntry> changeEntries) {
		getService()
			.addChangeCollectionChangeEntries(changeCollectionId, changeEntries);
	}

	public static void addChangeCollectionChangeEntries(
		long changeCollectionId, long[] changeEntryIds) {
		getService()
			.addChangeCollectionChangeEntries(changeCollectionId, changeEntryIds);
	}

	public static void addChangeCollectionChangeEntry(long changeCollectionId,
		com.liferay.change.tracking.engine.model.ChangeEntry changeEntry) {
		getService()
			.addChangeCollectionChangeEntry(changeCollectionId, changeEntry);
	}

	public static void addChangeCollectionChangeEntry(long changeCollectionId,
		long changeEntryId) {
		getService()
			.addChangeCollectionChangeEntry(changeCollectionId, changeEntryId);
	}

	/**
	* Adds the change entry to the database. Also notifies the appropriate model listeners.
	*
	* @param changeEntry the change entry
	* @return the change entry that was added
	*/
	public static com.liferay.change.tracking.engine.model.ChangeEntry addChangeEntry(
		com.liferay.change.tracking.engine.model.ChangeEntry changeEntry) {
		return getService().addChangeEntry(changeEntry);
	}

	public static void clearChangeCollectionChangeEntries(
		long changeCollectionId) {
		getService().clearChangeCollectionChangeEntries(changeCollectionId);
	}

	/**
	* Creates a new change entry with the primary key. Does not add the change entry to the database.
	*
	* @param changeEntryId the primary key for the new change entry
	* @return the new change entry
	*/
	public static com.liferay.change.tracking.engine.model.ChangeEntry createChangeEntry(
		long changeEntryId) {
		return getService().createChangeEntry(changeEntryId);
	}

	public static void deleteChangeCollectionChangeEntries(
		long changeCollectionId,
		java.util.List<com.liferay.change.tracking.engine.model.ChangeEntry> changeEntries) {
		getService()
			.deleteChangeCollectionChangeEntries(changeCollectionId,
			changeEntries);
	}

	public static void deleteChangeCollectionChangeEntries(
		long changeCollectionId, long[] changeEntryIds) {
		getService()
			.deleteChangeCollectionChangeEntries(changeCollectionId,
			changeEntryIds);
	}

	public static void deleteChangeCollectionChangeEntry(
		long changeCollectionId,
		com.liferay.change.tracking.engine.model.ChangeEntry changeEntry) {
		getService()
			.deleteChangeCollectionChangeEntry(changeCollectionId, changeEntry);
	}

	public static void deleteChangeCollectionChangeEntry(
		long changeCollectionId, long changeEntryId) {
		getService()
			.deleteChangeCollectionChangeEntry(changeCollectionId, changeEntryId);
	}

	/**
	* Deletes the change entry from the database. Also notifies the appropriate model listeners.
	*
	* @param changeEntry the change entry
	* @return the change entry that was removed
	*/
	public static com.liferay.change.tracking.engine.model.ChangeEntry deleteChangeEntry(
		com.liferay.change.tracking.engine.model.ChangeEntry changeEntry) {
		return getService().deleteChangeEntry(changeEntry);
	}

	/**
	* Deletes the change entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param changeEntryId the primary key of the change entry
	* @return the change entry that was removed
	* @throws PortalException if a change entry with the primary key could not be found
	*/
	public static com.liferay.change.tracking.engine.model.ChangeEntry deleteChangeEntry(
		long changeEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteChangeEntry(changeEntryId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.change.tracking.engine.model.impl.ChangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.change.tracking.engine.model.impl.ChangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.change.tracking.engine.model.ChangeEntry fetchChangeEntry(
		long changeEntryId) {
		return getService().fetchChangeEntry(changeEntryId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static java.util.List<com.liferay.change.tracking.engine.model.ChangeEntry> getChangeCollectionChangeEntries(
		long changeCollectionId) {
		return getService().getChangeCollectionChangeEntries(changeCollectionId);
	}

	public static java.util.List<com.liferay.change.tracking.engine.model.ChangeEntry> getChangeCollectionChangeEntries(
		long changeCollectionId, int start, int end) {
		return getService()
				   .getChangeCollectionChangeEntries(changeCollectionId, start,
			end);
	}

	public static java.util.List<com.liferay.change.tracking.engine.model.ChangeEntry> getChangeCollectionChangeEntries(
		long changeCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.change.tracking.engine.model.ChangeEntry> orderByComparator) {
		return getService()
				   .getChangeCollectionChangeEntries(changeCollectionId, start,
			end, orderByComparator);
	}

	public static int getChangeCollectionChangeEntriesCount(
		long changeCollectionId) {
		return getService()
				   .getChangeCollectionChangeEntriesCount(changeCollectionId);
	}

	/**
	* Returns the changeCollectionIds of the change collections associated with the change entry.
	*
	* @param changeEntryId the changeEntryId of the change entry
	* @return long[] the changeCollectionIds of change collections associated with the change entry
	*/
	public static long[] getChangeCollectionPrimaryKeys(long changeEntryId) {
		return getService().getChangeCollectionPrimaryKeys(changeEntryId);
	}

	/**
	* Returns a range of all the change entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.change.tracking.engine.model.impl.ChangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of change entries
	* @param end the upper bound of the range of change entries (not inclusive)
	* @return the range of change entries
	*/
	public static java.util.List<com.liferay.change.tracking.engine.model.ChangeEntry> getChangeEntries(
		int start, int end) {
		return getService().getChangeEntries(start, end);
	}

	/**
	* Returns the number of change entries.
	*
	* @return the number of change entries
	*/
	public static int getChangeEntriesCount() {
		return getService().getChangeEntriesCount();
	}

	/**
	* Returns the change entry with the primary key.
	*
	* @param changeEntryId the primary key of the change entry
	* @return the change entry
	* @throws PortalException if a change entry with the primary key could not be found
	*/
	public static com.liferay.change.tracking.engine.model.ChangeEntry getChangeEntry(
		long changeEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getChangeEntry(changeEntryId);
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

	public static boolean hasChangeCollectionChangeEntries(
		long changeCollectionId) {
		return getService().hasChangeCollectionChangeEntries(changeCollectionId);
	}

	public static boolean hasChangeCollectionChangeEntry(
		long changeCollectionId, long changeEntryId) {
		return getService()
				   .hasChangeCollectionChangeEntry(changeCollectionId,
			changeEntryId);
	}

	public static void setChangeCollectionChangeEntries(
		long changeCollectionId, long[] changeEntryIds) {
		getService()
			.setChangeCollectionChangeEntries(changeCollectionId, changeEntryIds);
	}

	/**
	* Updates the change entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param changeEntry the change entry
	* @return the change entry that was updated
	*/
	public static com.liferay.change.tracking.engine.model.ChangeEntry updateChangeEntry(
		com.liferay.change.tracking.engine.model.ChangeEntry changeEntry) {
		return getService().updateChangeEntry(changeEntry);
	}

	public static ChangeEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<ChangeEntryLocalService, ChangeEntryLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ChangeEntryLocalService.class);

		ServiceTracker<ChangeEntryLocalService, ChangeEntryLocalService> serviceTracker =
			new ServiceTracker<ChangeEntryLocalService, ChangeEntryLocalService>(bundle.getBundleContext(),
				ChangeEntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}