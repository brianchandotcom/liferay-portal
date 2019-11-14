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
 * Provides the local service utility for SiteSEOEntry. This utility wraps
 * <code>com.liferay.layout.seo.service.impl.SiteSEOEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see SiteSEOEntryLocalService
 * @generated
 */
public class SiteSEOEntryLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.layout.seo.service.impl.SiteSEOEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the site seo entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param siteSEOEntry the site seo entry
	 * @return the site seo entry that was added
	 */
	public static com.liferay.layout.seo.model.SiteSEOEntry addSiteSEOEntry(
		com.liferay.layout.seo.model.SiteSEOEntry siteSEOEntry) {

		return getService().addSiteSEOEntry(siteSEOEntry);
	}

	/**
	 * Creates a new site seo entry with the primary key. Does not add the site seo entry to the database.
	 *
	 * @param siteSEOEntryId the primary key for the new site seo entry
	 * @return the new site seo entry
	 */
	public static com.liferay.layout.seo.model.SiteSEOEntry createSiteSEOEntry(
		long siteSEOEntryId) {

		return getService().createSiteSEOEntry(siteSEOEntryId);
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

	/**
	 * Deletes the site seo entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param siteSEOEntryId the primary key of the site seo entry
	 * @return the site seo entry that was removed
	 * @throws PortalException if a site seo entry with the primary key could not be found
	 */
	public static com.liferay.layout.seo.model.SiteSEOEntry deleteSiteSEOEntry(
			long siteSEOEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteSiteSEOEntry(siteSEOEntryId);
	}

	/**
	 * Deletes the site seo entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param siteSEOEntry the site seo entry
	 * @return the site seo entry that was removed
	 */
	public static com.liferay.layout.seo.model.SiteSEOEntry deleteSiteSEOEntry(
		com.liferay.layout.seo.model.SiteSEOEntry siteSEOEntry) {

		return getService().deleteSiteSEOEntry(siteSEOEntry);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.layout.seo.model.impl.SiteSEOEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.layout.seo.model.impl.SiteSEOEntryModelImpl</code>.
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

	public static com.liferay.layout.seo.model.SiteSEOEntry fetchSiteSEOEntry(
		long siteSEOEntryId) {

		return getService().fetchSiteSEOEntry(siteSEOEntryId);
	}

	/**
	 * Returns the site seo entry matching the UUID and group.
	 *
	 * @param uuid the site seo entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching site seo entry, or <code>null</code> if a matching site seo entry could not be found
	 */
	public static com.liferay.layout.seo.model.SiteSEOEntry
		fetchSiteSEOEntryByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchSiteSEOEntryByUuidAndGroupId(uuid, groupId);
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
	 * Returns a range of all the site seo entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.layout.seo.model.impl.SiteSEOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of site seo entries
	 * @param end the upper bound of the range of site seo entries (not inclusive)
	 * @return the range of site seo entries
	 */
	public static java.util.List<com.liferay.layout.seo.model.SiteSEOEntry>
		getSiteSEOEntries(int start, int end) {

		return getService().getSiteSEOEntries(start, end);
	}

	/**
	 * Returns all the site seo entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the site seo entries
	 * @param companyId the primary key of the company
	 * @return the matching site seo entries, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.layout.seo.model.SiteSEOEntry>
		getSiteSEOEntriesByUuidAndCompanyId(String uuid, long companyId) {

		return getService().getSiteSEOEntriesByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of site seo entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the site seo entries
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of site seo entries
	 * @param end the upper bound of the range of site seo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching site seo entries, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.layout.seo.model.SiteSEOEntry>
		getSiteSEOEntriesByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.layout.seo.model.SiteSEOEntry> orderByComparator) {

		return getService().getSiteSEOEntriesByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of site seo entries.
	 *
	 * @return the number of site seo entries
	 */
	public static int getSiteSEOEntriesCount() {
		return getService().getSiteSEOEntriesCount();
	}

	/**
	 * Returns the site seo entry with the primary key.
	 *
	 * @param siteSEOEntryId the primary key of the site seo entry
	 * @return the site seo entry
	 * @throws PortalException if a site seo entry with the primary key could not be found
	 */
	public static com.liferay.layout.seo.model.SiteSEOEntry getSiteSEOEntry(
			long siteSEOEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getSiteSEOEntry(siteSEOEntryId);
	}

	/**
	 * Returns the site seo entry matching the UUID and group.
	 *
	 * @param uuid the site seo entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching site seo entry
	 * @throws PortalException if a matching site seo entry could not be found
	 */
	public static com.liferay.layout.seo.model.SiteSEOEntry
			getSiteSEOEntryByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getSiteSEOEntryByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Updates the site seo entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param siteSEOEntry the site seo entry
	 * @return the site seo entry that was updated
	 */
	public static com.liferay.layout.seo.model.SiteSEOEntry updateSiteSEOEntry(
		com.liferay.layout.seo.model.SiteSEOEntry siteSEOEntry) {

		return getService().updateSiteSEOEntry(siteSEOEntry);
	}

	public static SiteSEOEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<SiteSEOEntryLocalService, SiteSEOEntryLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(SiteSEOEntryLocalService.class);

		ServiceTracker<SiteSEOEntryLocalService, SiteSEOEntryLocalService>
			serviceTracker =
				new ServiceTracker
					<SiteSEOEntryLocalService, SiteSEOEntryLocalService>(
						bundle.getBundleContext(),
						SiteSEOEntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}