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
 * Provides the local service utility for LayoutSEOSite. This utility wraps
 * <code>com.liferay.layout.seo.service.impl.LayoutSEOSiteLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSEOSiteLocalService
 * @generated
 */
public class LayoutSEOSiteLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.layout.seo.service.impl.LayoutSEOSiteLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the layout seo site to the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutSEOSite the layout seo site
	 * @return the layout seo site that was added
	 */
	public static com.liferay.layout.seo.model.LayoutSEOSite addLayoutSEOSite(
		com.liferay.layout.seo.model.LayoutSEOSite layoutSEOSite) {

		return getService().addLayoutSEOSite(layoutSEOSite);
	}

	/**
	 * Creates a new layout seo site with the primary key. Does not add the layout seo site to the database.
	 *
	 * @param layoutSEOSiteId the primary key for the new layout seo site
	 * @return the new layout seo site
	 */
	public static com.liferay.layout.seo.model.LayoutSEOSite
		createLayoutSEOSite(long layoutSEOSiteId) {

		return getService().createLayoutSEOSite(layoutSEOSiteId);
	}

	/**
	 * Deletes the layout seo site from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutSEOSite the layout seo site
	 * @return the layout seo site that was removed
	 */
	public static com.liferay.layout.seo.model.LayoutSEOSite
		deleteLayoutSEOSite(
			com.liferay.layout.seo.model.LayoutSEOSite layoutSEOSite) {

		return getService().deleteLayoutSEOSite(layoutSEOSite);
	}

	/**
	 * Deletes the layout seo site with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutSEOSiteId the primary key of the layout seo site
	 * @return the layout seo site that was removed
	 * @throws PortalException if a layout seo site with the primary key could not be found
	 */
	public static com.liferay.layout.seo.model.LayoutSEOSite
			deleteLayoutSEOSite(long layoutSEOSiteId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteLayoutSEOSite(layoutSEOSiteId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.layout.seo.model.impl.LayoutSEOSiteModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.layout.seo.model.impl.LayoutSEOSiteModelImpl</code>.
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

	public static com.liferay.layout.seo.model.LayoutSEOSite fetchLayoutSEOSite(
		long layoutSEOSiteId) {

		return getService().fetchLayoutSEOSite(layoutSEOSiteId);
	}

	public static com.liferay.layout.seo.model.LayoutSEOSite
		fetchLayoutSEOSiteByGroupId(long groupId) {

		return getService().fetchLayoutSEOSiteByGroupId(groupId);
	}

	/**
	 * Returns the layout seo site matching the UUID and group.
	 *
	 * @param uuid the layout seo site's UUID
	 * @param groupId the primary key of the group
	 * @return the matching layout seo site, or <code>null</code> if a matching layout seo site could not be found
	 */
	public static com.liferay.layout.seo.model.LayoutSEOSite
		fetchLayoutSEOSiteByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchLayoutSEOSiteByUuidAndGroupId(uuid, groupId);
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
	 * Returns the layout seo site with the primary key.
	 *
	 * @param layoutSEOSiteId the primary key of the layout seo site
	 * @return the layout seo site
	 * @throws PortalException if a layout seo site with the primary key could not be found
	 */
	public static com.liferay.layout.seo.model.LayoutSEOSite getLayoutSEOSite(
			long layoutSEOSiteId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getLayoutSEOSite(layoutSEOSiteId);
	}

	/**
	 * Returns the layout seo site matching the UUID and group.
	 *
	 * @param uuid the layout seo site's UUID
	 * @param groupId the primary key of the group
	 * @return the matching layout seo site
	 * @throws PortalException if a matching layout seo site could not be found
	 */
	public static com.liferay.layout.seo.model.LayoutSEOSite
			getLayoutSEOSiteByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getLayoutSEOSiteByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the layout seo sites.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.layout.seo.model.impl.LayoutSEOSiteModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout seo sites
	 * @param end the upper bound of the range of layout seo sites (not inclusive)
	 * @return the range of layout seo sites
	 */
	public static java.util.List<com.liferay.layout.seo.model.LayoutSEOSite>
		getLayoutSEOSites(int start, int end) {

		return getService().getLayoutSEOSites(start, end);
	}

	/**
	 * Returns all the layout seo sites matching the UUID and company.
	 *
	 * @param uuid the UUID of the layout seo sites
	 * @param companyId the primary key of the company
	 * @return the matching layout seo sites, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.layout.seo.model.LayoutSEOSite>
		getLayoutSEOSitesByUuidAndCompanyId(String uuid, long companyId) {

		return getService().getLayoutSEOSitesByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of layout seo sites matching the UUID and company.
	 *
	 * @param uuid the UUID of the layout seo sites
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of layout seo sites
	 * @param end the upper bound of the range of layout seo sites (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching layout seo sites, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.layout.seo.model.LayoutSEOSite>
		getLayoutSEOSitesByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.layout.seo.model.LayoutSEOSite>
					orderByComparator) {

		return getService().getLayoutSEOSitesByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of layout seo sites.
	 *
	 * @return the number of layout seo sites
	 */
	public static int getLayoutSEOSitesCount() {
		return getService().getLayoutSEOSitesCount();
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
	 * Updates the layout seo site in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param layoutSEOSite the layout seo site
	 * @return the layout seo site that was updated
	 */
	public static com.liferay.layout.seo.model.LayoutSEOSite
		updateLayoutSEOSite(
			com.liferay.layout.seo.model.LayoutSEOSite layoutSEOSite) {

		return getService().updateLayoutSEOSite(layoutSEOSite);
	}

	public static com.liferay.layout.seo.model.LayoutSEOSite
			updateLayoutSEOSite(
				long userId, long groupId, boolean openGraphEnabled,
				long openGraphImageFileEntryId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateLayoutSEOSite(
			userId, groupId, openGraphEnabled, openGraphImageFileEntryId,
			serviceContext);
	}

	public static LayoutSEOSiteLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<LayoutSEOSiteLocalService, LayoutSEOSiteLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			LayoutSEOSiteLocalService.class);

		ServiceTracker<LayoutSEOSiteLocalService, LayoutSEOSiteLocalService>
			serviceTracker =
				new ServiceTracker
					<LayoutSEOSiteLocalService, LayoutSEOSiteLocalService>(
						bundle.getBundleContext(),
						LayoutSEOSiteLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}