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

package com.liferay.fragment.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for FragmentEntry. This utility wraps
 * <code>com.liferay.fragment.service.impl.FragmentEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see FragmentEntryLocalService
 * @generated
 */
public class FragmentEntryLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.fragment.service.impl.FragmentEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the fragment entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param fragmentEntry the fragment entry
	 * @return the fragment entry that was added
	 */
	public static com.liferay.fragment.model.FragmentEntry addFragmentEntry(
		com.liferay.fragment.model.FragmentEntry fragmentEntry) {

		return getService().addFragmentEntry(fragmentEntry);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #addFragmentEntry(long, long, long, String, String, String,
	 String, int, int, ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.fragment.model.FragmentEntry addFragmentEntry(
			long userId, long groupId, long fragmentCollectionId, String name,
			int type, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addFragmentEntry(
			userId, groupId, fragmentCollectionId, name, type, status,
			serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #addFragmentEntry(long, long, long, String, String, String,
	 String, int, int, ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.fragment.model.FragmentEntry addFragmentEntry(
			long userId, long groupId, long fragmentCollectionId, String name,
			int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addFragmentEntry(
			userId, groupId, fragmentCollectionId, name, status,
			serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #addFragmentEntry(long, long, long, String, String, String,
	 String, int, int, ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.fragment.model.FragmentEntry addFragmentEntry(
			long userId, long groupId, long fragmentCollectionId, String name,
			long previewFileEntryId, int type, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addFragmentEntry(
			userId, groupId, fragmentCollectionId, name, previewFileEntryId,
			type, status, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #addFragmentEntry(long, long, long, String, String, String,
	 String, int, int, ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.fragment.model.FragmentEntry addFragmentEntry(
			long userId, long groupId, long fragmentCollectionId, String name,
			long previewFileEntryId, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addFragmentEntry(
			userId, groupId, fragmentCollectionId, name, previewFileEntryId,
			status, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #addFragmentEntry(long, long, long, String, String, String,
	 String, int, int, ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.fragment.model.FragmentEntry addFragmentEntry(
			long userId, long groupId, long fragmentCollectionId,
			String fragmentEntryKey, String name, int type, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addFragmentEntry(
			userId, groupId, fragmentCollectionId, fragmentEntryKey, name, type,
			status, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #addFragmentEntry(long, long, long, String, String, String,
	 String, int, int, ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.fragment.model.FragmentEntry addFragmentEntry(
			long userId, long groupId, long fragmentCollectionId,
			String fragmentEntryKey, String name, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addFragmentEntry(
			userId, groupId, fragmentCollectionId, fragmentEntryKey, name,
			status, serviceContext);
	}

	public static com.liferay.fragment.model.FragmentEntry addFragmentEntry(
			long userId, long groupId, long fragmentCollectionId,
			String fragmentEntryKey, String name, long previewFileEntryId,
			int type, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addFragmentEntry(
			userId, groupId, fragmentCollectionId, fragmentEntryKey, name,
			previewFileEntryId, type, status, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #addFragmentEntry(long, long, long, String, String, String,
	 String, int, int, ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.fragment.model.FragmentEntry addFragmentEntry(
			long userId, long groupId, long fragmentCollectionId,
			String fragmentEntryKey, String name, long previewFileEntryId,
			int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addFragmentEntry(
			userId, groupId, fragmentCollectionId, fragmentEntryKey, name,
			previewFileEntryId, status, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #addFragmentEntry(long, long, long, String, String, String,
	 String, String, String, long, int, int, ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.fragment.model.FragmentEntry addFragmentEntry(
			long userId, long groupId, long fragmentCollectionId, String name,
			String css, String html, String js, int type, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addFragmentEntry(
			userId, groupId, fragmentCollectionId, name, css, html, js, type,
			status, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #addFragmentEntry(long, long, long, String, String, String,
	 String, String, String, long, int, int, ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.fragment.model.FragmentEntry addFragmentEntry(
			long userId, long groupId, long fragmentCollectionId, String name,
			String css, String html, String js, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addFragmentEntry(
			userId, groupId, fragmentCollectionId, name, css, html, js, status,
			serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #addFragmentEntry(long, long, long, String, String, String,
	 String, String, String, long, int, int, ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.fragment.model.FragmentEntry addFragmentEntry(
			long userId, long groupId, long fragmentCollectionId, String name,
			String css, String html, String js, long previewFileEntryId,
			int type, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addFragmentEntry(
			userId, groupId, fragmentCollectionId, name, css, html, js,
			previewFileEntryId, type, status, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #addFragmentEntry(long, long, long, String, String, String,
	 String, String, String, long, int, int, ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.fragment.model.FragmentEntry addFragmentEntry(
			long userId, long groupId, long fragmentCollectionId, String name,
			String css, String html, String js, long previewFileEntryId,
			int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addFragmentEntry(
			userId, groupId, fragmentCollectionId, name, css, html, js,
			previewFileEntryId, status, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #addFragmentEntry(long, long, long, String, String, String,
	 String, String, String, long, int, int, ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.fragment.model.FragmentEntry addFragmentEntry(
			long userId, long groupId, long fragmentCollectionId,
			String fragmentEntryKey, String name, String css, String html,
			String js, int type, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addFragmentEntry(
			userId, groupId, fragmentCollectionId, fragmentEntryKey, name, css,
			html, js, type, status, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #addFragmentEntry(long, long, long, String, String, String,
	 String, String, String, long, int, int, ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.fragment.model.FragmentEntry addFragmentEntry(
			long userId, long groupId, long fragmentCollectionId,
			String fragmentEntryKey, String name, String css, String html,
			String js, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addFragmentEntry(
			userId, groupId, fragmentCollectionId, fragmentEntryKey, name, css,
			html, js, status, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #addFragmentEntry(long, long, long, String, String, String,
	 String, String, String, long, int, int, ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.fragment.model.FragmentEntry addFragmentEntry(
			long userId, long groupId, long fragmentCollectionId,
			String fragmentEntryKey, String name, String css, String html,
			String js, long previewFileEntryId, int type, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addFragmentEntry(
			userId, groupId, fragmentCollectionId, fragmentEntryKey, name, css,
			html, js, previewFileEntryId, type, status, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #addFragmentEntry(long, long, long, String, String, String,
	 String, String, String, long, int, int, ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.fragment.model.FragmentEntry addFragmentEntry(
			long userId, long groupId, long fragmentCollectionId,
			String fragmentEntryKey, String name, String css, String html,
			String js, long previewFileEntryId, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addFragmentEntry(
			userId, groupId, fragmentCollectionId, fragmentEntryKey, name, css,
			html, js, previewFileEntryId, status, serviceContext);
	}

	public static com.liferay.fragment.model.FragmentEntry addFragmentEntry(
			long userId, long groupId, long fragmentCollectionId,
			String fragmentEntryKey, String name, String css, String html,
			String js, String configuration, long previewFileEntryId, int type,
			int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addFragmentEntry(
			userId, groupId, fragmentCollectionId, fragmentEntryKey, name, css,
			html, js, configuration, previewFileEntryId, type, status,
			serviceContext);
	}

	public static com.liferay.fragment.model.FragmentEntry copyFragmentEntry(
			long userId, long groupId, long fragmentEntryId,
			long fragmentCollectionId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().copyFragmentEntry(
			userId, groupId, fragmentEntryId, fragmentCollectionId,
			serviceContext);
	}

	/**
	 * Creates a new fragment entry with the primary key. Does not add the fragment entry to the database.
	 *
	 * @param fragmentEntryId the primary key for the new fragment entry
	 * @return the new fragment entry
	 */
	public static com.liferay.fragment.model.FragmentEntry createFragmentEntry(
		long fragmentEntryId) {

		return getService().createFragmentEntry(fragmentEntryId);
	}

	/**
	 * Deletes the fragment entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fragmentEntry the fragment entry
	 * @return the fragment entry that was removed
	 * @throws PortalException
	 */
	public static com.liferay.fragment.model.FragmentEntry deleteFragmentEntry(
			com.liferay.fragment.model.FragmentEntry fragmentEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteFragmentEntry(fragmentEntry);
	}

	/**
	 * Deletes the fragment entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fragmentEntryId the primary key of the fragment entry
	 * @return the fragment entry that was removed
	 * @throws PortalException if a fragment entry with the primary key could not be found
	 */
	public static com.liferay.fragment.model.FragmentEntry deleteFragmentEntry(
			long fragmentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteFragmentEntry(fragmentEntryId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.fragment.model.impl.FragmentEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.fragment.model.impl.FragmentEntryModelImpl</code>.
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

	public static com.liferay.fragment.model.FragmentEntry fetchFragmentEntry(
		long fragmentEntryId) {

		return getService().fetchFragmentEntry(fragmentEntryId);
	}

	public static com.liferay.fragment.model.FragmentEntry fetchFragmentEntry(
		long groupId, String fragmentEntryKey) {

		return getService().fetchFragmentEntry(groupId, fragmentEntryKey);
	}

	/**
	 * Returns the fragment entry matching the UUID and group.
	 *
	 * @param uuid the fragment entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	public static com.liferay.fragment.model.FragmentEntry
		fetchFragmentEntryByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchFragmentEntryByUuidAndGroupId(uuid, groupId);
	}

	public static String generateFragmentEntryKey(long groupId, String name) {
		return getService().generateFragmentEntryKey(groupId, name);
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

	/**
	 * Returns a range of all the fragment entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.fragment.model.impl.FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of fragment entries
	 */
	public static java.util.List<com.liferay.fragment.model.FragmentEntry>
		getFragmentEntries(int start, int end) {

		return getService().getFragmentEntries(start, end);
	}

	public static java.util.List<com.liferay.fragment.model.FragmentEntry>
		getFragmentEntries(long fragmentCollectionId) {

		return getService().getFragmentEntries(fragmentCollectionId);
	}

	public static java.util.List<com.liferay.fragment.model.FragmentEntry>
		getFragmentEntries(long fragmentCollectionId, int start, int end) {

		return getService().getFragmentEntries(
			fragmentCollectionId, start, end);
	}

	public static java.util.List<com.liferay.fragment.model.FragmentEntry>
		getFragmentEntries(
			long groupId, long fragmentCollectionId, int status) {

		return getService().getFragmentEntries(
			groupId, fragmentCollectionId, status);
	}

	public static java.util.List<com.liferay.fragment.model.FragmentEntry>
		getFragmentEntries(
			long groupId, long fragmentCollectionId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.fragment.model.FragmentEntry> orderByComparator) {

		return getService().getFragmentEntries(
			groupId, fragmentCollectionId, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.fragment.model.FragmentEntry>
		getFragmentEntries(
			long groupId, long fragmentCollectionId, String name, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.fragment.model.FragmentEntry> orderByComparator) {

		return getService().getFragmentEntries(
			groupId, fragmentCollectionId, name, start, end, orderByComparator);
	}

	/**
	 * Returns all the fragment entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the fragment entries
	 * @param companyId the primary key of the company
	 * @return the matching fragment entries, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.fragment.model.FragmentEntry>
		getFragmentEntriesByUuidAndCompanyId(String uuid, long companyId) {

		return getService().getFragmentEntriesByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of fragment entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the fragment entries
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching fragment entries, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.fragment.model.FragmentEntry>
		getFragmentEntriesByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.fragment.model.FragmentEntry> orderByComparator) {

		return getService().getFragmentEntriesByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of fragment entries.
	 *
	 * @return the number of fragment entries
	 */
	public static int getFragmentEntriesCount() {
		return getService().getFragmentEntriesCount();
	}

	public static int getFragmentEntriesCount(long fragmentCollectionId) {
		return getService().getFragmentEntriesCount(fragmentCollectionId);
	}

	/**
	 * Returns the fragment entry with the primary key.
	 *
	 * @param fragmentEntryId the primary key of the fragment entry
	 * @return the fragment entry
	 * @throws PortalException if a fragment entry with the primary key could not be found
	 */
	public static com.liferay.fragment.model.FragmentEntry getFragmentEntry(
			long fragmentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getFragmentEntry(fragmentEntryId);
	}

	/**
	 * Returns the fragment entry matching the UUID and group.
	 *
	 * @param uuid the fragment entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching fragment entry
	 * @throws PortalException if a matching fragment entry could not be found
	 */
	public static com.liferay.fragment.model.FragmentEntry
			getFragmentEntryByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getFragmentEntryByUuidAndGroupId(uuid, groupId);
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

	public static String[] getTempFileNames(
			long userId, long groupId, String folderName)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getTempFileNames(userId, groupId, folderName);
	}

	public static com.liferay.fragment.model.FragmentEntry moveFragmentEntry(
			long fragmentEntryId, long fragmentCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().moveFragmentEntry(
			fragmentEntryId, fragmentCollectionId);
	}

	/**
	 * Updates the fragment entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param fragmentEntry the fragment entry
	 * @return the fragment entry that was updated
	 */
	public static com.liferay.fragment.model.FragmentEntry updateFragmentEntry(
		com.liferay.fragment.model.FragmentEntry fragmentEntry) {

		return getService().updateFragmentEntry(fragmentEntry);
	}

	public static com.liferay.fragment.model.FragmentEntry updateFragmentEntry(
			long fragmentEntryId, long previewFileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateFragmentEntry(
			fragmentEntryId, previewFileEntryId);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #updateFragmentEntry(long, long, String, String, String,
	 String, String, int)}
	 */
	@Deprecated
	public static com.liferay.fragment.model.FragmentEntry updateFragmentEntry(
			long userId, long fragmentEntryId, String name, String css,
			String html, String js, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateFragmentEntry(
			userId, fragmentEntryId, name, css, html, js, status);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #updateFragmentEntry(long, long, String, String, String,
	 String, String, long, int)}
	 */
	@Deprecated
	public static com.liferay.fragment.model.FragmentEntry updateFragmentEntry(
			long userId, long fragmentEntryId, String name, String css,
			String html, String js, long previewFileEntryId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateFragmentEntry(
			userId, fragmentEntryId, name, css, html, js, previewFileEntryId,
			status);
	}

	public static com.liferay.fragment.model.FragmentEntry updateFragmentEntry(
			long userId, long fragmentEntryId, String name, String css,
			String html, String js, String configuration, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateFragmentEntry(
			userId, fragmentEntryId, name, css, html, js, configuration,
			status);
	}

	public static com.liferay.fragment.model.FragmentEntry updateFragmentEntry(
			long userId, long fragmentEntryId, String name, String css,
			String html, String js, String configuration,
			long previewFileEntryId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateFragmentEntry(
			userId, fragmentEntryId, name, css, html, js, configuration,
			previewFileEntryId, status);
	}

	public static com.liferay.fragment.model.FragmentEntry updateFragmentEntry(
			long fragmentEntryId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateFragmentEntry(fragmentEntryId, name);
	}

	public static FragmentEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<FragmentEntryLocalService, FragmentEntryLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			FragmentEntryLocalService.class);

		ServiceTracker<FragmentEntryLocalService, FragmentEntryLocalService>
			serviceTracker =
				new ServiceTracker
					<FragmentEntryLocalService, FragmentEntryLocalService>(
						bundle.getBundleContext(),
						FragmentEntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}