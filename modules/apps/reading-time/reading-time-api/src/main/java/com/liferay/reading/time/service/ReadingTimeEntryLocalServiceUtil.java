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

package com.liferay.reading.time.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for ReadingTimeEntry. This utility wraps
 * <code>com.liferay.reading.time.service.impl.ReadingTimeEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ReadingTimeEntryLocalService
 * @generated
 */
public class ReadingTimeEntryLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.reading.time.service.impl.ReadingTimeEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ReadingTimeEntryLocalServiceUtil} to access the reading time entry local service. Add custom service methods to <code>com.liferay.reading.time.service.impl.ReadingTimeEntryLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static com.liferay.reading.time.model.ReadingTimeEntry
		addReadingTimeEntry(
			com.liferay.portal.kernel.model.GroupedModel groupedModel,
			java.time.Duration readingTimeDuration) {

		return getService().addReadingTimeEntry(
			groupedModel, readingTimeDuration);
	}

	public static com.liferay.reading.time.model.ReadingTimeEntry
		addReadingTimeEntry(
			long groupId, long classNameId, long classPK,
			java.time.Duration readingTimeDuration) {

		return getService().addReadingTimeEntry(
			groupId, classNameId, classPK, readingTimeDuration);
	}

	/**
	 * Adds the reading time entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param readingTimeEntry the reading time entry
	 * @return the reading time entry that was added
	 */
	public static com.liferay.reading.time.model.ReadingTimeEntry
		addReadingTimeEntry(
			com.liferay.reading.time.model.ReadingTimeEntry readingTimeEntry) {

		return getService().addReadingTimeEntry(readingTimeEntry);
	}

	/**
	 * Creates a new reading time entry with the primary key. Does not add the reading time entry to the database.
	 *
	 * @param readingTimeEntryId the primary key for the new reading time entry
	 * @return the new reading time entry
	 */
	public static com.liferay.reading.time.model.ReadingTimeEntry
		createReadingTimeEntry(long readingTimeEntryId) {

		return getService().createReadingTimeEntry(readingTimeEntryId);
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

	public static com.liferay.reading.time.model.ReadingTimeEntry
		deleteReadingTimeEntry(
			com.liferay.portal.kernel.model.GroupedModel groupedModel) {

		return getService().deleteReadingTimeEntry(groupedModel);
	}

	/**
	 * Deletes the reading time entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param readingTimeEntryId the primary key of the reading time entry
	 * @return the reading time entry that was removed
	 * @throws PortalException if a reading time entry with the primary key could not be found
	 */
	public static com.liferay.reading.time.model.ReadingTimeEntry
			deleteReadingTimeEntry(long readingTimeEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteReadingTimeEntry(readingTimeEntryId);
	}

	public static com.liferay.reading.time.model.ReadingTimeEntry
		deleteReadingTimeEntry(long groupId, long classNameId, long classPK) {

		return getService().deleteReadingTimeEntry(
			groupId, classNameId, classPK);
	}

	/**
	 * Deletes the reading time entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param readingTimeEntry the reading time entry
	 * @return the reading time entry that was removed
	 */
	public static com.liferay.reading.time.model.ReadingTimeEntry
		deleteReadingTimeEntry(
			com.liferay.reading.time.model.ReadingTimeEntry readingTimeEntry) {

		return getService().deleteReadingTimeEntry(readingTimeEntry);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.reading.time.model.impl.ReadingTimeEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.reading.time.model.impl.ReadingTimeEntryModelImpl</code>.
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

	public static com.liferay.reading.time.model.ReadingTimeEntry
		fetchOrAddReadingTimeEntry(
			com.liferay.portal.kernel.model.GroupedModel groupedModel) {

		return getService().fetchOrAddReadingTimeEntry(groupedModel);
	}

	public static com.liferay.reading.time.model.ReadingTimeEntry
		fetchReadingTimeEntry(
			com.liferay.portal.kernel.model.GroupedModel groupedModel) {

		return getService().fetchReadingTimeEntry(groupedModel);
	}

	public static com.liferay.reading.time.model.ReadingTimeEntry
		fetchReadingTimeEntry(long readingTimeEntryId) {

		return getService().fetchReadingTimeEntry(readingTimeEntryId);
	}

	public static com.liferay.reading.time.model.ReadingTimeEntry
		fetchReadingTimeEntry(long groupId, long classNameId, long classPK) {

		return getService().fetchReadingTimeEntry(
			groupId, classNameId, classPK);
	}

	/**
	 * Returns the reading time entry matching the UUID and group.
	 *
	 * @param uuid the reading time entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching reading time entry, or <code>null</code> if a matching reading time entry could not be found
	 */
	public static com.liferay.reading.time.model.ReadingTimeEntry
		fetchReadingTimeEntryByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchReadingTimeEntryByUuidAndGroupId(
			uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
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
	 * Returns a range of all the reading time entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.reading.time.model.impl.ReadingTimeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of reading time entries
	 * @param end the upper bound of the range of reading time entries (not inclusive)
	 * @return the range of reading time entries
	 */
	public static java.util.List
		<com.liferay.reading.time.model.ReadingTimeEntry> getReadingTimeEntries(
			int start, int end) {

		return getService().getReadingTimeEntries(start, end);
	}

	/**
	 * Returns all the reading time entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the reading time entries
	 * @param companyId the primary key of the company
	 * @return the matching reading time entries, or an empty list if no matches were found
	 */
	public static java.util.List
		<com.liferay.reading.time.model.ReadingTimeEntry>
			getReadingTimeEntriesByUuidAndCompanyId(
				String uuid, long companyId) {

		return getService().getReadingTimeEntriesByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of reading time entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the reading time entries
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of reading time entries
	 * @param end the upper bound of the range of reading time entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching reading time entries, or an empty list if no matches were found
	 */
	public static java.util.List
		<com.liferay.reading.time.model.ReadingTimeEntry>
			getReadingTimeEntriesByUuidAndCompanyId(
				String uuid, long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.reading.time.model.ReadingTimeEntry>
						orderByComparator) {

		return getService().getReadingTimeEntriesByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of reading time entries.
	 *
	 * @return the number of reading time entries
	 */
	public static int getReadingTimeEntriesCount() {
		return getService().getReadingTimeEntriesCount();
	}

	/**
	 * Returns the reading time entry with the primary key.
	 *
	 * @param readingTimeEntryId the primary key of the reading time entry
	 * @return the reading time entry
	 * @throws PortalException if a reading time entry with the primary key could not be found
	 */
	public static com.liferay.reading.time.model.ReadingTimeEntry
			getReadingTimeEntry(long readingTimeEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getReadingTimeEntry(readingTimeEntryId);
	}

	/**
	 * Returns the reading time entry matching the UUID and group.
	 *
	 * @param uuid the reading time entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching reading time entry
	 * @throws PortalException if a matching reading time entry could not be found
	 */
	public static com.liferay.reading.time.model.ReadingTimeEntry
			getReadingTimeEntryByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getReadingTimeEntryByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.reading.time.model.ReadingTimeEntry
		updateReadingTimeEntry(
			com.liferay.portal.kernel.model.GroupedModel groupedModel) {

		return getService().updateReadingTimeEntry(groupedModel);
	}

	public static com.liferay.reading.time.model.ReadingTimeEntry
		updateReadingTimeEntry(
			long groupId, long classNameId, long classPK,
			java.time.Duration readingTimeDuration) {

		return getService().updateReadingTimeEntry(
			groupId, classNameId, classPK, readingTimeDuration);
	}

	/**
	 * Updates the reading time entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param readingTimeEntry the reading time entry
	 * @return the reading time entry that was updated
	 */
	public static com.liferay.reading.time.model.ReadingTimeEntry
		updateReadingTimeEntry(
			com.liferay.reading.time.model.ReadingTimeEntry readingTimeEntry) {

		return getService().updateReadingTimeEntry(readingTimeEntry);
	}

	public static ReadingTimeEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<ReadingTimeEntryLocalService, ReadingTimeEntryLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			ReadingTimeEntryLocalService.class);

		ServiceTracker
			<ReadingTimeEntryLocalService, ReadingTimeEntryLocalService>
				serviceTracker =
					new ServiceTracker
						<ReadingTimeEntryLocalService,
						 ReadingTimeEntryLocalService>(
							 bundle.getBundleContext(),
							 ReadingTimeEntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}