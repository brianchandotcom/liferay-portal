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

package com.liferay.portal.reports.engine.console.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for Entry. This utility wraps
 * {@link com.liferay.portal.reports.engine.console.service.impl.EntryLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see EntryLocalService
 * @see com.liferay.portal.reports.engine.console.service.base.EntryLocalServiceBaseImpl
 * @see com.liferay.portal.reports.engine.console.service.impl.EntryLocalServiceImpl
 * @generated
 */
@ProviderType
public class EntryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.reports.engine.console.service.impl.EntryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the entry to the database. Also notifies the appropriate model listeners.
	*
	* @param entry the entry
	* @return the entry that was added
	*/
	public static com.liferay.portal.reports.engine.console.model.Entry addEntry(
		com.liferay.portal.reports.engine.console.model.Entry entry) {
		return getService().addEntry(entry);
	}

	public static com.liferay.portal.reports.engine.console.model.Entry addEntry(
		long userId, long groupId, long definitionId, java.lang.String format,
		boolean schedulerRequest, java.util.Date startDate,
		java.util.Date endDate, boolean repeating, java.lang.String recurrence,
		java.lang.String emailNotifications, java.lang.String emailDelivery,
		java.lang.String portletId, java.lang.String pageURL,
		java.lang.String reportName, java.lang.String reportParameters,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addEntry(userId, groupId, definitionId, format,
			schedulerRequest, startDate, endDate, repeating, recurrence,
			emailNotifications, emailDelivery, portletId, pageURL, reportName,
			reportParameters, serviceContext);
	}

	public static void addEntryResources(
		com.liferay.portal.reports.engine.console.model.Entry entry,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.addEntryResources(entry, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void addEntryResources(
		com.liferay.portal.reports.engine.console.model.Entry entry,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.addEntryResources(entry, communityPermissions, guestPermissions);
	}

	/**
	* Creates a new entry with the primary key. Does not add the entry to the database.
	*
	* @param entryId the primary key for the new entry
	* @return the new entry
	*/
	public static com.liferay.portal.reports.engine.console.model.Entry createEntry(
		long entryId) {
		return getService().createEntry(entryId);
	}

	public static void deleteAttachment(long companyId,
		java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteAttachment(companyId, fileName);
	}

	/**
	* Deletes the entry from the database. Also notifies the appropriate model listeners.
	*
	* @param entry the entry
	* @return the entry that was removed
	* @throws PortalException
	*/
	public static com.liferay.portal.reports.engine.console.model.Entry deleteEntry(
		com.liferay.portal.reports.engine.console.model.Entry entry)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteEntry(entry);
	}

	/**
	* Deletes the entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the entry
	* @return the entry that was removed
	* @throws PortalException if a entry with the primary key could not be found
	*/
	public static com.liferay.portal.reports.engine.console.model.Entry deleteEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteEntry(entryId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.reports.engine.console.model.impl.EntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.reports.engine.console.model.impl.EntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.portal.reports.engine.console.model.Entry fetchEntry(
		long entryId) {
		return getService().fetchEntry(entryId);
	}

	public static void generateReport(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().generateReport(entryId);
	}

	public static void generateReport(long entryId, java.lang.String reportName)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().generateReport(entryId, reportName);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns a range of all the entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.reports.engine.console.model.impl.EntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of entries
	* @param end the upper bound of the range of entries (not inclusive)
	* @return the range of entries
	*/
	public static java.util.List<com.liferay.portal.reports.engine.console.model.Entry> getEntries(
		int start, int end) {
		return getService().getEntries(start, end);
	}

	public static java.util.List<com.liferay.portal.reports.engine.console.model.Entry> getEntries(
		long groupId, java.lang.String definitionName,
		java.lang.String userName, java.util.Date createDateGT,
		java.util.Date createDateLT, boolean andSearch, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getService()
				   .getEntries(groupId, definitionName, userName, createDateGT,
			createDateLT, andSearch, start, end, orderByComparator);
	}

	/**
	* Returns the number of entries.
	*
	* @return the number of entries
	*/
	public static int getEntriesCount() {
		return getService().getEntriesCount();
	}

	public static int getEntriesCount(long groupId,
		java.lang.String definitionName, java.lang.String userName,
		java.util.Date createDateGT, java.util.Date createDateLT,
		boolean andSearch) {
		return getService()
				   .getEntriesCount(groupId, definitionName, userName,
			createDateGT, createDateLT, andSearch);
	}

	/**
	* Returns the entry with the primary key.
	*
	* @param entryId the primary key of the entry
	* @return the entry
	* @throws PortalException if a entry with the primary key could not be found
	*/
	public static com.liferay.portal.reports.engine.console.model.Entry getEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getEntry(entryId);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	public static void sendEmails(long entryId, java.lang.String fileName,
		java.lang.String[] emailAddresses, boolean notification)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().sendEmails(entryId, fileName, emailAddresses, notification);
	}

	public static void unscheduleEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().unscheduleEntry(entryId);
	}

	/**
	* Updates the entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param entry the entry
	* @return the entry that was updated
	*/
	public static com.liferay.portal.reports.engine.console.model.Entry updateEntry(
		com.liferay.portal.reports.engine.console.model.Entry entry) {
		return getService().updateEntry(entry);
	}

	public static void updateEntry(long entryId, java.lang.String reportName,
		byte[] reportResults)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().updateEntry(entryId, reportName, reportResults);
	}

	public static void updateEntryStatus(long entryId,
		com.liferay.portal.reports.engine.console.status.ReportStatus status,
		java.lang.String errorMessage)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().updateEntryStatus(entryId, status, errorMessage);
	}

	public static EntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<EntryLocalService, EntryLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(EntryLocalService.class);

		ServiceTracker<EntryLocalService, EntryLocalService> serviceTracker = new ServiceTracker<EntryLocalService, EntryLocalService>(bundle.getBundleContext(),
				EntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}