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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.BaseLocalService;
import com.liferay.portal.service.PersistedModelLocalService;

/**
 * Provides the local service interface for AnnouncementsEntry. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see AnnouncementsEntryLocalServiceUtil
 * @see com.liferay.portlet.announcements.service.base.AnnouncementsEntryLocalServiceBaseImpl
 * @see com.liferay.portlet.announcements.service.impl.AnnouncementsEntryLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface AnnouncementsEntryLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AnnouncementsEntryLocalServiceUtil} to access the announcements entry local service. Add custom service methods to {@link com.liferay.portlet.announcements.service.impl.AnnouncementsEntryLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the announcements entry to the database. Also notifies the appropriate model listeners.
	*
	* @param announcementsEntry the announcements entry
	* @return the announcements entry that was added
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portlet.announcements.model.AnnouncementsEntry addAnnouncementsEntry(
		com.liferay.portlet.announcements.model.AnnouncementsEntry announcementsEntry);

	public com.liferay.portlet.announcements.model.AnnouncementsEntry addEntry(
		long userId, long classNameId, long classPK, java.lang.String title,
		java.lang.String content, java.lang.String url, java.lang.String type,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, boolean displayImmediately,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, int priority,
		boolean alert) throws PortalException;

	/**
	* @deprecated As of 6.2.0, replaced by {@link #addEntry(long, long, long,
	String, String, String, String, int, int, int, int, int,
	boolean, int, int, int, int, int, int, boolean)}
	*/
	@java.lang.Deprecated
	public com.liferay.portlet.announcements.model.AnnouncementsEntry addEntry(
		long userId, long classNameId, long classPK, java.lang.String title,
		java.lang.String content, java.lang.String url, java.lang.String type,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, int priority, boolean alert)
		throws PortalException;

	public void checkEntries() throws PortalException;

	/**
	* Creates a new announcements entry with the primary key. Does not add the announcements entry to the database.
	*
	* @param entryId the primary key for the new announcements entry
	* @return the new announcements entry
	*/
	public com.liferay.portlet.announcements.model.AnnouncementsEntry createAnnouncementsEntry(
		long entryId);

	/**
	* Deletes the announcements entry from the database. Also notifies the appropriate model listeners.
	*
	* @param announcementsEntry the announcements entry
	* @return the announcements entry that was removed
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.portlet.announcements.model.AnnouncementsEntry deleteAnnouncementsEntry(
		com.liferay.portlet.announcements.model.AnnouncementsEntry announcementsEntry);

	/**
	* Deletes the announcements entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the announcements entry
	* @return the announcements entry that was removed
	* @throws PortalException if a announcements entry with the primary key could not be found
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.portlet.announcements.model.AnnouncementsEntry deleteAnnouncementsEntry(
		long entryId) throws PortalException;

	public void deleteEntry(
		com.liferay.portlet.announcements.model.AnnouncementsEntry entry)
		throws PortalException;

	public void deleteEntry(long entryId) throws PortalException;

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws PortalException;

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery);

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
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end);

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
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.announcements.model.AnnouncementsEntry fetchAnnouncementsEntry(
		long entryId);

	/**
	* Returns the announcements entry with the matching UUID and company.
	*
	* @param uuid the announcements entry's UUID
	* @param companyId the primary key of the company
	* @return the matching announcements entry, or <code>null</code> if a matching announcements entry could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.announcements.model.AnnouncementsEntry fetchAnnouncementsEntryByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery();

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> getAnnouncementsEntries(
		int start, int end);

	/**
	* Returns the number of announcements entries.
	*
	* @return the number of announcements entries
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getAnnouncementsEntriesCount();

	/**
	* Returns the announcements entry with the primary key.
	*
	* @param entryId the primary key of the announcements entry
	* @return the announcements entry
	* @throws PortalException if a announcements entry with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.announcements.model.AnnouncementsEntry getAnnouncementsEntry(
		long entryId) throws PortalException;

	/**
	* Returns the announcements entry with the matching UUID and company.
	*
	* @param uuid the announcements entry's UUID
	* @param companyId the primary key of the company
	* @return the matching announcements entry
	* @throws PortalException if a matching announcements entry could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.announcements.model.AnnouncementsEntry getAnnouncementsEntryByUuidAndCompanyId(
		java.lang.String uuid, long companyId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> getEntries(
		long classNameId, long classPK, boolean alert, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> getEntries(
		long userId, long classNameId, long[] classPKs, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean alert, int flagValue, int start,
		int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> getEntries(
		long userId, java.util.LinkedHashMap<java.lang.Long, long[]> scopes,
		boolean alert, int flagValue, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> getEntries(
		long userId, java.util.LinkedHashMap<java.lang.Long, long[]> scopes,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean alert, int flagValue, int start,
		int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getEntriesCount(long classNameId, long classPK, boolean alert);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getEntriesCount(long userId, long classNameId, long[] classPKs,
		boolean alert, int flagValue);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getEntriesCount(long userId, long classNameId, long[] classPKs,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean alert, int flagValue);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getEntriesCount(long userId,
		java.util.LinkedHashMap<java.lang.Long, long[]> scopes, boolean alert,
		int flagValue);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getEntriesCount(long userId,
		java.util.LinkedHashMap<java.lang.Long, long[]> scopes,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean alert, int flagValue);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.announcements.model.AnnouncementsEntry getEntry(
		long entryId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> getUserEntries(
		long userId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getUserEntriesCount(long userId);

	/**
	* Updates the announcements entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param announcementsEntry the announcements entry
	* @return the announcements entry that was updated
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portlet.announcements.model.AnnouncementsEntry updateAnnouncementsEntry(
		com.liferay.portlet.announcements.model.AnnouncementsEntry announcementsEntry);

	public com.liferay.portlet.announcements.model.AnnouncementsEntry updateEntry(
		long userId, long entryId, java.lang.String title,
		java.lang.String content, java.lang.String url, java.lang.String type,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, boolean displayImmediately,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, int priority)
		throws PortalException;
}