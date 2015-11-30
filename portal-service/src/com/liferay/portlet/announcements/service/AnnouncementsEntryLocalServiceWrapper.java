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

package com.liferay.portlet.announcements.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AnnouncementsEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see AnnouncementsEntryLocalService
 * @generated
 */
@ProviderType
public class AnnouncementsEntryLocalServiceWrapper
	implements AnnouncementsEntryLocalService,
		ServiceWrapper<AnnouncementsEntryLocalService> {
	public AnnouncementsEntryLocalServiceWrapper(
		AnnouncementsEntryLocalService announcementsEntryLocalService) {
		_announcementsEntryLocalService = announcementsEntryLocalService;
	}

	/**
	* Adds the announcements entry to the database. Also notifies the appropriate model listeners.
	*
	* @param announcementsEntry the announcements entry
	* @return the announcements entry that was added
	*/
	@Override
	public com.liferay.portlet.announcements.model.AnnouncementsEntry addAnnouncementsEntry(
		com.liferay.portlet.announcements.model.AnnouncementsEntry announcementsEntry) {
		return _announcementsEntryLocalService.addAnnouncementsEntry(announcementsEntry);
	}

	@Override
	public com.liferay.portlet.announcements.model.AnnouncementsEntry addEntry(
		long userId, long classNameId, long classPK, java.lang.String title,
		java.lang.String content, java.lang.String url, java.lang.String type,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, boolean displayImmediately,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, int priority,
		boolean alert)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _announcementsEntryLocalService.addEntry(userId, classNameId,
			classPK, title, content, url, type, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, displayImmediately, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, priority, alert);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #addEntry(long, long, long,
	String, String, String, String, int, int, int, int, int,
	boolean, int, int, int, int, int, int, boolean)}
	*/
	@Deprecated
	@Override
	public com.liferay.portlet.announcements.model.AnnouncementsEntry addEntry(
		long userId, long classNameId, long classPK, java.lang.String title,
		java.lang.String content, java.lang.String url, java.lang.String type,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, int priority, boolean alert)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _announcementsEntryLocalService.addEntry(userId, classNameId,
			classPK, title, content, url, type, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			priority, alert);
	}

	@Override
	public void checkEntries()
		throws com.liferay.portal.kernel.exception.PortalException {
		_announcementsEntryLocalService.checkEntries();
	}

	/**
	* Creates a new announcements entry with the primary key. Does not add the announcements entry to the database.
	*
	* @param entryId the primary key for the new announcements entry
	* @return the new announcements entry
	*/
	@Override
	public com.liferay.portlet.announcements.model.AnnouncementsEntry createAnnouncementsEntry(
		long entryId) {
		return _announcementsEntryLocalService.createAnnouncementsEntry(entryId);
	}

	/**
	* Deletes the announcements entry from the database. Also notifies the appropriate model listeners.
	*
	* @param announcementsEntry the announcements entry
	* @return the announcements entry that was removed
	*/
	@Override
	public com.liferay.portlet.announcements.model.AnnouncementsEntry deleteAnnouncementsEntry(
		com.liferay.portlet.announcements.model.AnnouncementsEntry announcementsEntry) {
		return _announcementsEntryLocalService.deleteAnnouncementsEntry(announcementsEntry);
	}

	/**
	* Deletes the announcements entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the announcements entry
	* @return the announcements entry that was removed
	* @throws PortalException if a announcements entry with the primary key could not be found
	*/
	@Override
	public com.liferay.portlet.announcements.model.AnnouncementsEntry deleteAnnouncementsEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _announcementsEntryLocalService.deleteAnnouncementsEntry(entryId);
	}

	@Override
	public void deleteEntry(
		com.liferay.portlet.announcements.model.AnnouncementsEntry entry)
		throws com.liferay.portal.kernel.exception.PortalException {
		_announcementsEntryLocalService.deleteEntry(entry);
	}

	@Override
	public void deleteEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_announcementsEntryLocalService.deleteEntry(entryId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _announcementsEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _announcementsEntryLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _announcementsEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.announcements.model.impl.AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _announcementsEntryLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.announcements.model.impl.AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _announcementsEntryLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _announcementsEntryLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _announcementsEntryLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.portlet.announcements.model.AnnouncementsEntry fetchAnnouncementsEntry(
		long entryId) {
		return _announcementsEntryLocalService.fetchAnnouncementsEntry(entryId);
	}

	/**
	* Returns the announcements entry with the matching UUID and company.
	*
	* @param uuid the announcements entry's UUID
	* @param companyId the primary key of the company
	* @return the matching announcements entry, or <code>null</code> if a matching announcements entry could not be found
	*/
	@Override
	public com.liferay.portlet.announcements.model.AnnouncementsEntry fetchAnnouncementsEntryByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _announcementsEntryLocalService.fetchAnnouncementsEntryByUuidAndCompanyId(uuid,
			companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _announcementsEntryLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns a range of all the announcements entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.announcements.model.impl.AnnouncementsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of announcements entries
	* @param end the upper bound of the range of announcements entries (not inclusive)
	* @return the range of announcements entries
	*/
	@Override
	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> getAnnouncementsEntries(
		int start, int end) {
		return _announcementsEntryLocalService.getAnnouncementsEntries(start,
			end);
	}

	/**
	* Returns the number of announcements entries.
	*
	* @return the number of announcements entries
	*/
	@Override
	public int getAnnouncementsEntriesCount() {
		return _announcementsEntryLocalService.getAnnouncementsEntriesCount();
	}

	/**
	* Returns the announcements entry with the primary key.
	*
	* @param entryId the primary key of the announcements entry
	* @return the announcements entry
	* @throws PortalException if a announcements entry with the primary key could not be found
	*/
	@Override
	public com.liferay.portlet.announcements.model.AnnouncementsEntry getAnnouncementsEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _announcementsEntryLocalService.getAnnouncementsEntry(entryId);
	}

	/**
	* Returns the announcements entry with the matching UUID and company.
	*
	* @param uuid the announcements entry's UUID
	* @param companyId the primary key of the company
	* @return the matching announcements entry
	* @throws PortalException if a matching announcements entry could not be found
	*/
	@Override
	public com.liferay.portlet.announcements.model.AnnouncementsEntry getAnnouncementsEntryByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _announcementsEntryLocalService.getAnnouncementsEntryByUuidAndCompanyId(uuid,
			companyId);
	}

	@Override
	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> getEntries(
		long classNameId, long classPK, boolean alert, int start, int end) {
		return _announcementsEntryLocalService.getEntries(classNameId, classPK,
			alert, start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> getEntries(
		long userId, long classNameId, long[] classPKs, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean alert, int flagValue, int start,
		int end) {
		return _announcementsEntryLocalService.getEntries(userId, classNameId,
			classPKs, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, alert, flagValue, start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> getEntries(
		long userId, java.util.LinkedHashMap<java.lang.Long, long[]> scopes,
		boolean alert, int flagValue, int start, int end) {
		return _announcementsEntryLocalService.getEntries(userId, scopes,
			alert, flagValue, start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> getEntries(
		long userId, java.util.LinkedHashMap<java.lang.Long, long[]> scopes,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean alert, int flagValue, int start,
		int end) {
		return _announcementsEntryLocalService.getEntries(userId, scopes,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			alert, flagValue, start, end);
	}

	@Override
	public int getEntriesCount(long classNameId, long classPK, boolean alert) {
		return _announcementsEntryLocalService.getEntriesCount(classNameId,
			classPK, alert);
	}

	@Override
	public int getEntriesCount(long userId, long classNameId, long[] classPKs,
		boolean alert, int flagValue) {
		return _announcementsEntryLocalService.getEntriesCount(userId,
			classNameId, classPKs, alert, flagValue);
	}

	@Override
	public int getEntriesCount(long userId, long classNameId, long[] classPKs,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean alert, int flagValue) {
		return _announcementsEntryLocalService.getEntriesCount(userId,
			classNameId, classPKs, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, alert, flagValue);
	}

	@Override
	public int getEntriesCount(long userId,
		java.util.LinkedHashMap<java.lang.Long, long[]> scopes, boolean alert,
		int flagValue) {
		return _announcementsEntryLocalService.getEntriesCount(userId, scopes,
			alert, flagValue);
	}

	@Override
	public int getEntriesCount(long userId,
		java.util.LinkedHashMap<java.lang.Long, long[]> scopes,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean alert, int flagValue) {
		return _announcementsEntryLocalService.getEntriesCount(userId, scopes,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			alert, flagValue);
	}

	@Override
	public com.liferay.portlet.announcements.model.AnnouncementsEntry getEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _announcementsEntryLocalService.getEntry(entryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext) {
		return _announcementsEntryLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _announcementsEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _announcementsEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _announcementsEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> getUserEntries(
		long userId, int start, int end) {
		return _announcementsEntryLocalService.getUserEntries(userId, start, end);
	}

	@Override
	public int getUserEntriesCount(long userId) {
		return _announcementsEntryLocalService.getUserEntriesCount(userId);
	}

	/**
	* Updates the announcements entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param announcementsEntry the announcements entry
	* @return the announcements entry that was updated
	*/
	@Override
	public com.liferay.portlet.announcements.model.AnnouncementsEntry updateAnnouncementsEntry(
		com.liferay.portlet.announcements.model.AnnouncementsEntry announcementsEntry) {
		return _announcementsEntryLocalService.updateAnnouncementsEntry(announcementsEntry);
	}

	@Override
	public com.liferay.portlet.announcements.model.AnnouncementsEntry updateEntry(
		long userId, long entryId, java.lang.String title,
		java.lang.String content, java.lang.String url, java.lang.String type,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, boolean displayImmediately,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, int priority)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _announcementsEntryLocalService.updateEntry(userId, entryId,
			title, content, url, type, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			displayImmediately, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			priority);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public AnnouncementsEntryLocalService getWrappedAnnouncementsEntryLocalService() {
		return _announcementsEntryLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedAnnouncementsEntryLocalService(
		AnnouncementsEntryLocalService announcementsEntryLocalService) {
		_announcementsEntryLocalService = announcementsEntryLocalService;
	}

	@Override
	public AnnouncementsEntryLocalService getWrappedService() {
		return _announcementsEntryLocalService;
	}

	@Override
	public void setWrappedService(
		AnnouncementsEntryLocalService announcementsEntryLocalService) {
		_announcementsEntryLocalService = announcementsEntryLocalService;
	}

	private AnnouncementsEntryLocalService _announcementsEntryLocalService;
}