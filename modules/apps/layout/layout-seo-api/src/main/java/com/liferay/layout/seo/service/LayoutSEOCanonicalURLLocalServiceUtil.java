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

import org.osgi.annotation.versioning.ProviderType;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for LayoutSEOCanonicalURL. This utility wraps
 * <code>com.liferay.layout.seo.service.impl.LayoutSEOCanonicalURLLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSEOCanonicalURLLocalService
 * @generated
 */
@ProviderType
public class LayoutSEOCanonicalURLLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.layout.seo.service.impl.LayoutSEOCanonicalURLLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the layout seo canonical url to the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutSEOCanonicalURL the layout seo canonical url
	 * @return the layout seo canonical url that was added
	 */
	public static com.liferay.layout.seo.model.LayoutSEOCanonicalURL
		addLayoutSEOCanonicalURL(
			com.liferay.layout.seo.model.LayoutSEOCanonicalURL
				layoutSEOCanonicalURL) {

		return getService().addLayoutSEOCanonicalURL(layoutSEOCanonicalURL);
	}

	/**
	 * Creates a new layout seo canonical url with the primary key. Does not add the layout seo canonical url to the database.
	 *
	 * @param layoutSEOCanonicalURLId the primary key for the new layout seo canonical url
	 * @return the new layout seo canonical url
	 */
	public static com.liferay.layout.seo.model.LayoutSEOCanonicalURL
		createLayoutSEOCanonicalURL(long layoutSEOCanonicalURLId) {

		return getService().createLayoutSEOCanonicalURL(
			layoutSEOCanonicalURLId);
	}

	/**
	 * Deletes the layout seo canonical url from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutSEOCanonicalURL the layout seo canonical url
	 * @return the layout seo canonical url that was removed
	 */
	public static com.liferay.layout.seo.model.LayoutSEOCanonicalURL
		deleteLayoutSEOCanonicalURL(
			com.liferay.layout.seo.model.LayoutSEOCanonicalURL
				layoutSEOCanonicalURL) {

		return getService().deleteLayoutSEOCanonicalURL(layoutSEOCanonicalURL);
	}

	/**
	 * Deletes the layout seo canonical url with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutSEOCanonicalURLId the primary key of the layout seo canonical url
	 * @return the layout seo canonical url that was removed
	 * @throws PortalException if a layout seo canonical url with the primary key could not be found
	 */
	public static com.liferay.layout.seo.model.LayoutSEOCanonicalURL
			deleteLayoutSEOCanonicalURL(long layoutSEOCanonicalURLId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteLayoutSEOCanonicalURL(
			layoutSEOCanonicalURLId);
	}

	public static void deleteLayoutSEOCanonicalURL(
			long groupId, boolean privateLayout, long layoutId)
		throws com.liferay.layout.seo.exception.NoSuchCanonicalURLException {

		getService().deleteLayoutSEOCanonicalURL(
			groupId, privateLayout, layoutId);
	}

	public static void deleteLayoutSEOCanonicalURL(String uuid, long groupId)
		throws com.liferay.layout.seo.exception.NoSuchCanonicalURLException {

		getService().deleteLayoutSEOCanonicalURL(uuid, groupId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.layout.seo.model.impl.LayoutSEOCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.layout.seo.model.impl.LayoutSEOCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.layout.seo.model.LayoutSEOCanonicalURL
		fetchLayoutSEOCanonicalURL(long layoutSEOCanonicalURLId) {

		return getService().fetchLayoutSEOCanonicalURL(layoutSEOCanonicalURLId);
	}

	public static com.liferay.layout.seo.model.LayoutSEOCanonicalURL
		fetchLayoutSEOCanonicalURL(
			long groupId, boolean privateLayout, long layoutId) {

		return getService().fetchLayoutSEOCanonicalURL(
			groupId, privateLayout, layoutId);
	}

	/**
	 * Returns the layout seo canonical url matching the UUID and group.
	 *
	 * @param uuid the layout seo canonical url's UUID
	 * @param groupId the primary key of the group
	 * @return the matching layout seo canonical url, or <code>null</code> if a matching layout seo canonical url could not be found
	 */
	public static com.liferay.layout.seo.model.LayoutSEOCanonicalURL
		fetchLayoutSEOCanonicalURLByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchLayoutSEOCanonicalURLByUuidAndGroupId(
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
	 * Returns the layout seo canonical url with the primary key.
	 *
	 * @param layoutSEOCanonicalURLId the primary key of the layout seo canonical url
	 * @return the layout seo canonical url
	 * @throws PortalException if a layout seo canonical url with the primary key could not be found
	 */
	public static com.liferay.layout.seo.model.LayoutSEOCanonicalURL
			getLayoutSEOCanonicalURL(long layoutSEOCanonicalURLId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getLayoutSEOCanonicalURL(layoutSEOCanonicalURLId);
	}

	/**
	 * Returns the layout seo canonical url matching the UUID and group.
	 *
	 * @param uuid the layout seo canonical url's UUID
	 * @param groupId the primary key of the group
	 * @return the matching layout seo canonical url
	 * @throws PortalException if a matching layout seo canonical url could not be found
	 */
	public static com.liferay.layout.seo.model.LayoutSEOCanonicalURL
			getLayoutSEOCanonicalURLByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getLayoutSEOCanonicalURLByUuidAndGroupId(
			uuid, groupId);
	}

	/**
	 * Returns a range of all the layout seo canonical urls.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.layout.seo.model.impl.LayoutSEOCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout seo canonical urls
	 * @param end the upper bound of the range of layout seo canonical urls (not inclusive)
	 * @return the range of layout seo canonical urls
	 */
	public static java.util.List
		<com.liferay.layout.seo.model.LayoutSEOCanonicalURL>
			getLayoutSEOCanonicalURLs(int start, int end) {

		return getService().getLayoutSEOCanonicalURLs(start, end);
	}

	/**
	 * Returns all the layout seo canonical urls matching the UUID and company.
	 *
	 * @param uuid the UUID of the layout seo canonical urls
	 * @param companyId the primary key of the company
	 * @return the matching layout seo canonical urls, or an empty list if no matches were found
	 */
	public static java.util.List
		<com.liferay.layout.seo.model.LayoutSEOCanonicalURL>
			getLayoutSEOCanonicalURLsByUuidAndCompanyId(
				String uuid, long companyId) {

		return getService().getLayoutSEOCanonicalURLsByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of layout seo canonical urls matching the UUID and company.
	 *
	 * @param uuid the UUID of the layout seo canonical urls
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of layout seo canonical urls
	 * @param end the upper bound of the range of layout seo canonical urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching layout seo canonical urls, or an empty list if no matches were found
	 */
	public static java.util.List
		<com.liferay.layout.seo.model.LayoutSEOCanonicalURL>
			getLayoutSEOCanonicalURLsByUuidAndCompanyId(
				String uuid, long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.layout.seo.model.LayoutSEOCanonicalURL>
						orderByComparator) {

		return getService().getLayoutSEOCanonicalURLsByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of layout seo canonical urls.
	 *
	 * @return the number of layout seo canonical urls
	 */
	public static int getLayoutSEOCanonicalURLsCount() {
		return getService().getLayoutSEOCanonicalURLsCount();
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
	 * Updates the layout seo canonical url in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param layoutSEOCanonicalURL the layout seo canonical url
	 * @return the layout seo canonical url that was updated
	 */
	public static com.liferay.layout.seo.model.LayoutSEOCanonicalURL
		updateLayoutSEOCanonicalURL(
			com.liferay.layout.seo.model.LayoutSEOCanonicalURL
				layoutSEOCanonicalURL) {

		return getService().updateLayoutSEOCanonicalURL(layoutSEOCanonicalURL);
	}

	public static com.liferay.layout.seo.model.LayoutSEOCanonicalURL
			updateLayoutSEOCanonicalURL(
				long userId, long groupId, boolean privateLayout, long layoutId,
				boolean enabled,
				java.util.Map<java.util.Locale, String> canonicalURLMap,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateLayoutSEOCanonicalURL(
			userId, groupId, privateLayout, layoutId, enabled, canonicalURLMap,
			serviceContext);
	}

	public static LayoutSEOCanonicalURLLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<LayoutSEOCanonicalURLLocalService, LayoutSEOCanonicalURLLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			LayoutSEOCanonicalURLLocalService.class);

		ServiceTracker
			<LayoutSEOCanonicalURLLocalService,
			 LayoutSEOCanonicalURLLocalService> serviceTracker =
				new ServiceTracker
					<LayoutSEOCanonicalURLLocalService,
					 LayoutSEOCanonicalURLLocalService>(
						 bundle.getBundleContext(),
						 LayoutSEOCanonicalURLLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}