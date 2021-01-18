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

package com.liferay.layout.seo.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for LayoutSEOEntry. This utility wraps
 * <code>com.liferay.layout.seo.service.impl.LayoutSEOEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSEOEntryLocalService
 * @generated
 */
public class LayoutSEOEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.layout.seo.service.impl.LayoutSEOEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the layout seo entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LayoutSEOEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param layoutSEOEntry the layout seo entry
	 * @return the layout seo entry that was added
	 */
	public static com.liferay.layout.seo.model.LayoutSEOEntry addLayoutSEOEntry(
		com.liferay.layout.seo.model.LayoutSEOEntry layoutSEOEntry) {

		return getService().addLayoutSEOEntry(layoutSEOEntry);
	}

	/**
	 * Creates a new layout seo entry with the primary key. Does not add the layout seo entry to the database.
	 *
	 * @param layoutSEOEntryId the primary key for the new layout seo entry
	 * @return the new layout seo entry
	 */
	public static com.liferay.layout.seo.model.LayoutSEOEntry
		createLayoutSEOEntry(long layoutSEOEntryId) {

		return getService().createLayoutSEOEntry(layoutSEOEntryId);
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
	 * Deletes the layout seo entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LayoutSEOEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param layoutSEOEntry the layout seo entry
	 * @return the layout seo entry that was removed
	 */
	public static com.liferay.layout.seo.model.LayoutSEOEntry
		deleteLayoutSEOEntry(
			com.liferay.layout.seo.model.LayoutSEOEntry layoutSEOEntry) {

		return getService().deleteLayoutSEOEntry(layoutSEOEntry);
	}

	/**
	 * Deletes the layout seo entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LayoutSEOEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param layoutSEOEntryId the primary key of the layout seo entry
	 * @return the layout seo entry that was removed
	 * @throws PortalException if a layout seo entry with the primary key could not be found
	 */
	public static com.liferay.layout.seo.model.LayoutSEOEntry
			deleteLayoutSEOEntry(long layoutSEOEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteLayoutSEOEntry(layoutSEOEntryId);
	}

	public static void deleteLayoutSEOEntry(
			long groupId, boolean privateLayout, long layoutId)
		throws com.liferay.layout.seo.exception.NoSuchEntryException {

		getService().deleteLayoutSEOEntry(groupId, privateLayout, layoutId);
	}

	public static void deleteLayoutSEOEntry(String uuid, long groupId)
		throws com.liferay.layout.seo.exception.NoSuchEntryException {

		getService().deleteLayoutSEOEntry(uuid, groupId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.layout.seo.model.impl.LayoutSEOEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.layout.seo.model.impl.LayoutSEOEntryModelImpl</code>.
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

	public static com.liferay.layout.seo.model.LayoutSEOEntry
		fetchLayoutSEOEntry(long layoutSEOEntryId) {

		return getService().fetchLayoutSEOEntry(layoutSEOEntryId);
	}

	public static com.liferay.layout.seo.model.LayoutSEOEntry
		fetchLayoutSEOEntry(
			long groupId, boolean privateLayout, long layoutId) {

		return getService().fetchLayoutSEOEntry(
			groupId, privateLayout, layoutId);
	}

	/**
	 * Returns the layout seo entry matching the UUID and group.
	 *
	 * @param uuid the layout seo entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching layout seo entry, or <code>null</code> if a matching layout seo entry could not be found
	 */
	public static com.liferay.layout.seo.model.LayoutSEOEntry
		fetchLayoutSEOEntryByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchLayoutSEOEntryByUuidAndGroupId(uuid, groupId);
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
	 * Returns a range of all the layout seo entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.layout.seo.model.impl.LayoutSEOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout seo entries
	 * @param end the upper bound of the range of layout seo entries (not inclusive)
	 * @return the range of layout seo entries
	 */
	public static java.util.List<com.liferay.layout.seo.model.LayoutSEOEntry>
		getLayoutSEOEntries(int start, int end) {

		return getService().getLayoutSEOEntries(start, end);
	}

	/**
	 * Returns all the layout seo entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the layout seo entries
	 * @param companyId the primary key of the company
	 * @return the matching layout seo entries, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.layout.seo.model.LayoutSEOEntry>
		getLayoutSEOEntriesByUuidAndCompanyId(String uuid, long companyId) {

		return getService().getLayoutSEOEntriesByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of layout seo entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the layout seo entries
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of layout seo entries
	 * @param end the upper bound of the range of layout seo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching layout seo entries, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.layout.seo.model.LayoutSEOEntry>
		getLayoutSEOEntriesByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.layout.seo.model.LayoutSEOEntry>
					orderByComparator) {

		return getService().getLayoutSEOEntriesByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of layout seo entries.
	 *
	 * @return the number of layout seo entries
	 */
	public static int getLayoutSEOEntriesCount() {
		return getService().getLayoutSEOEntriesCount();
	}

	/**
	 * Returns the layout seo entry with the primary key.
	 *
	 * @param layoutSEOEntryId the primary key of the layout seo entry
	 * @return the layout seo entry
	 * @throws PortalException if a layout seo entry with the primary key could not be found
	 */
	public static com.liferay.layout.seo.model.LayoutSEOEntry getLayoutSEOEntry(
			long layoutSEOEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getLayoutSEOEntry(layoutSEOEntryId);
	}

	/**
	 * Returns the layout seo entry matching the UUID and group.
	 *
	 * @param uuid the layout seo entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching layout seo entry
	 * @throws PortalException if a matching layout seo entry could not be found
	 */
	public static com.liferay.layout.seo.model.LayoutSEOEntry
			getLayoutSEOEntryByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getLayoutSEOEntryByUuidAndGroupId(uuid, groupId);
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

	public static com.liferay.layout.seo.model.LayoutSEOEntry
			updateCustomMetaTags(
				long userId, long groupId, boolean privateLayout, long layoutId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCustomMetaTags(
			userId, groupId, privateLayout, layoutId, serviceContext);
	}

	/**
	 * Updates the layout seo entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LayoutSEOEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param layoutSEOEntry the layout seo entry
	 * @return the layout seo entry that was updated
	 */
	public static com.liferay.layout.seo.model.LayoutSEOEntry
		updateLayoutSEOEntry(
			com.liferay.layout.seo.model.LayoutSEOEntry layoutSEOEntry) {

		return getService().updateLayoutSEOEntry(layoutSEOEntry);
	}

	public static com.liferay.layout.seo.model.LayoutSEOEntry
			updateLayoutSEOEntry(
				long userId, long groupId, boolean privateLayout, long layoutId,
				boolean canonicalURLEnabled,
				java.util.Map<java.util.Locale, String> canonicalURLMap,
				boolean openGraphDescriptionEnabled,
				java.util.Map<java.util.Locale, String> openGraphDescriptionMap,
				java.util.Map<java.util.Locale, String> openGraphImageAltMap,
				long openGraphImageFileEntryId, boolean openGraphTitleEnabled,
				java.util.Map<java.util.Locale, String> openGraphTitleMap,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateLayoutSEOEntry(
			userId, groupId, privateLayout, layoutId, canonicalURLEnabled,
			canonicalURLMap, openGraphDescriptionEnabled,
			openGraphDescriptionMap, openGraphImageAltMap,
			openGraphImageFileEntryId, openGraphTitleEnabled, openGraphTitleMap,
			serviceContext);
	}

	public static com.liferay.layout.seo.model.LayoutSEOEntry
			updateLayoutSEOEntry(
				long userId, long groupId, boolean privateLayout, long layoutId,
				boolean canonicalURLEnabled,
				java.util.Map<java.util.Locale, String> canonicalURLMap,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateLayoutSEOEntry(
			userId, groupId, privateLayout, layoutId, canonicalURLEnabled,
			canonicalURLMap, serviceContext);
	}

	public static LayoutSEOEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<LayoutSEOEntryLocalService, LayoutSEOEntryLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			LayoutSEOEntryLocalService.class);

		ServiceTracker<LayoutSEOEntryLocalService, LayoutSEOEntryLocalService>
			serviceTracker =
				new ServiceTracker
					<LayoutSEOEntryLocalService, LayoutSEOEntryLocalService>(
						bundle.getBundleContext(),
						LayoutSEOEntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}