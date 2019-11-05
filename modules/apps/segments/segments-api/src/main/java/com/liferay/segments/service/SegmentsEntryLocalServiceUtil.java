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

package com.liferay.segments.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for SegmentsEntry. This utility wraps
 * <code>com.liferay.segments.service.impl.SegmentsEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Eduardo Garcia
 * @see SegmentsEntryLocalService
 * @generated
 */
public class SegmentsEntryLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.segments.service.impl.SegmentsEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the segments entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param segmentsEntry the segments entry
	 * @return the segments entry that was added
	 */
	public static com.liferay.segments.model.SegmentsEntry addSegmentsEntry(
		com.liferay.segments.model.SegmentsEntry segmentsEntry) {

		return getService().addSegmentsEntry(segmentsEntry);
	}

	public static com.liferay.segments.model.SegmentsEntry addSegmentsEntry(
			String segmentsEntryKey,
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			boolean active, String criteria, String source, String type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addSegmentsEntry(
			segmentsEntryKey, nameMap, descriptionMap, active, criteria, source,
			type, serviceContext);
	}

	/**
	 * Creates a new segments entry with the primary key. Does not add the segments entry to the database.
	 *
	 * @param segmentsEntryId the primary key for the new segments entry
	 * @return the new segments entry
	 */
	public static com.liferay.segments.model.SegmentsEntry createSegmentsEntry(
		long segmentsEntryId) {

		return getService().createSegmentsEntry(segmentsEntryId);
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

	public static void deleteSegmentsEntries(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteSegmentsEntries(groupId);
	}

	public static void deleteSegmentsEntries(String source)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteSegmentsEntries(source);
	}

	/**
	 * Deletes the segments entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param segmentsEntryId the primary key of the segments entry
	 * @return the segments entry that was removed
	 * @throws PortalException if a segments entry with the primary key could not be found
	 */
	public static com.liferay.segments.model.SegmentsEntry deleteSegmentsEntry(
			long segmentsEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteSegmentsEntry(segmentsEntryId);
	}

	/**
	 * Deletes the segments entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param segmentsEntry the segments entry
	 * @return the segments entry that was removed
	 * @throws PortalException
	 */
	public static com.liferay.segments.model.SegmentsEntry deleteSegmentsEntry(
			com.liferay.segments.model.SegmentsEntry segmentsEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteSegmentsEntry(segmentsEntry);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.segments.model.impl.SegmentsEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.segments.model.impl.SegmentsEntryModelImpl</code>.
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

	public static com.liferay.segments.model.SegmentsEntry fetchSegmentsEntry(
		long segmentsEntryId) {

		return getService().fetchSegmentsEntry(segmentsEntryId);
	}

	public static com.liferay.segments.model.SegmentsEntry fetchSegmentsEntry(
		long groupId, String segmentsEntryKey,
		boolean includeAncestorSegmentsEntries) {

		return getService().fetchSegmentsEntry(
			groupId, segmentsEntryKey, includeAncestorSegmentsEntries);
	}

	/**
	 * Returns the segments entry matching the UUID and group.
	 *
	 * @param uuid the segments entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching segments entry, or <code>null</code> if a matching segments entry could not be found
	 */
	public static com.liferay.segments.model.SegmentsEntry
		fetchSegmentsEntryByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchSegmentsEntryByUuidAndGroupId(uuid, groupId);
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
	 * Returns a range of all the segments entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.segments.model.impl.SegmentsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @return the range of segments entries
	 */
	public static java.util.List<com.liferay.segments.model.SegmentsEntry>
		getSegmentsEntries(int start, int end) {

		return getService().getSegmentsEntries(start, end);
	}

	public static java.util.List<com.liferay.segments.model.SegmentsEntry>
		getSegmentsEntries(
			long groupId, boolean includeAncestorSegmentsEntries, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.segments.model.SegmentsEntry> orderByComparator) {

		return getService().getSegmentsEntries(
			groupId, includeAncestorSegmentsEntries, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.segments.model.SegmentsEntry>
		getSegmentsEntries(
			long groupId, boolean active, String type, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.segments.model.SegmentsEntry> orderByComparator) {

		return getService().getSegmentsEntries(
			groupId, active, type, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.segments.model.SegmentsEntry>
		getSegmentsEntries(
			long groupId, boolean active, String source, String type, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.segments.model.SegmentsEntry> orderByComparator) {

		return getService().getSegmentsEntries(
			groupId, active, source, type, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.segments.model.SegmentsEntry>
		getSegmentsEntriesBySource(
			String source, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.segments.model.SegmentsEntry> orderByComparator) {

		return getService().getSegmentsEntriesBySource(
			source, start, end, orderByComparator);
	}

	/**
	 * Returns all the segments entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the segments entries
	 * @param companyId the primary key of the company
	 * @return the matching segments entries, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.segments.model.SegmentsEntry>
		getSegmentsEntriesByUuidAndCompanyId(String uuid, long companyId) {

		return getService().getSegmentsEntriesByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of segments entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the segments entries
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of segments entries
	 * @param end the upper bound of the range of segments entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching segments entries, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.segments.model.SegmentsEntry>
		getSegmentsEntriesByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.segments.model.SegmentsEntry> orderByComparator) {

		return getService().getSegmentsEntriesByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of segments entries.
	 *
	 * @return the number of segments entries
	 */
	public static int getSegmentsEntriesCount() {
		return getService().getSegmentsEntriesCount();
	}

	public static int getSegmentsEntriesCount(
		long groupId, boolean includeAncestorSegmentsEntries) {

		return getService().getSegmentsEntriesCount(
			groupId, includeAncestorSegmentsEntries);
	}

	/**
	 * Returns the segments entry with the primary key.
	 *
	 * @param segmentsEntryId the primary key of the segments entry
	 * @return the segments entry
	 * @throws PortalException if a segments entry with the primary key could not be found
	 */
	public static com.liferay.segments.model.SegmentsEntry getSegmentsEntry(
			long segmentsEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getSegmentsEntry(segmentsEntryId);
	}

	/**
	 * Returns the segments entry matching the UUID and group.
	 *
	 * @param uuid the segments entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching segments entry
	 * @throws PortalException if a matching segments entry could not be found
	 */
	public static com.liferay.segments.model.SegmentsEntry
			getSegmentsEntryByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getSegmentsEntryByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #searchSegmentsEntries(long, long, String, boolean,
	 LinkedHashMap, int, int, Sort)}
	 */
	@Deprecated
	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.segments.model.SegmentsEntry> searchSegmentsEntries(
				long companyId, long groupId, String keywords,
				boolean includeAncestorSegmentsEntries, int start, int end,
				com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().searchSegmentsEntries(
			companyId, groupId, keywords, includeAncestorSegmentsEntries, start,
			end, sort);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.segments.model.SegmentsEntry> searchSegmentsEntries(
				long companyId, long groupId, String keywords,
				boolean includeAncestorSegmentsEntries,
				java.util.LinkedHashMap<String, Object> params, int start,
				int end, com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().searchSegmentsEntries(
			companyId, groupId, keywords, includeAncestorSegmentsEntries,
			params, start, end, sort);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.segments.model.SegmentsEntry> searchSegmentsEntries(
				com.liferay.portal.kernel.search.SearchContext searchContext)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().searchSegmentsEntries(searchContext);
	}

	public static com.liferay.segments.model.SegmentsEntry updateSegmentsEntry(
			long segmentsEntryId, String segmentsEntryKey,
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			boolean active, String criteria,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateSegmentsEntry(
			segmentsEntryId, segmentsEntryKey, nameMap, descriptionMap, active,
			criteria, serviceContext);
	}

	/**
	 * Updates the segments entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param segmentsEntry the segments entry
	 * @return the segments entry that was updated
	 */
	public static com.liferay.segments.model.SegmentsEntry updateSegmentsEntry(
		com.liferay.segments.model.SegmentsEntry segmentsEntry) {

		return getService().updateSegmentsEntry(segmentsEntry);
	}

	public static SegmentsEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<SegmentsEntryLocalService, SegmentsEntryLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			SegmentsEntryLocalService.class);

		ServiceTracker<SegmentsEntryLocalService, SegmentsEntryLocalService>
			serviceTracker =
				new ServiceTracker
					<SegmentsEntryLocalService, SegmentsEntryLocalService>(
						bundle.getBundleContext(),
						SegmentsEntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}