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
 * Provides the local service utility for LayoutCanonicalURL. This utility wraps
 * <code>com.liferay.layout.seo.service.impl.LayoutCanonicalURLLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutCanonicalURLLocalService
 * @generated
 */
@ProviderType
public class LayoutCanonicalURLLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.layout.seo.service.impl.LayoutCanonicalURLLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the layout canonical url to the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutCanonicalURL the layout canonical url
	 * @return the layout canonical url that was added
	 */
	public static com.liferay.layout.seo.model.LayoutCanonicalURL
		addLayoutCanonicalURL(
			com.liferay.layout.seo.model.LayoutCanonicalURL
				layoutCanonicalURL) {

		return getService().addLayoutCanonicalURL(layoutCanonicalURL);
	}

	/**
	 * Creates a new layout canonical url with the primary key. Does not add the layout canonical url to the database.
	 *
	 * @param layoutCanonicalURLId the primary key for the new layout canonical url
	 * @return the new layout canonical url
	 */
	public static com.liferay.layout.seo.model.LayoutCanonicalURL
		createLayoutCanonicalURL(long layoutCanonicalURLId) {

		return getService().createLayoutCanonicalURL(layoutCanonicalURLId);
	}

	/**
	 * Deletes the layout canonical url from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutCanonicalURL the layout canonical url
	 * @return the layout canonical url that was removed
	 */
	public static com.liferay.layout.seo.model.LayoutCanonicalURL
		deleteLayoutCanonicalURL(
			com.liferay.layout.seo.model.LayoutCanonicalURL
				layoutCanonicalURL) {

		return getService().deleteLayoutCanonicalURL(layoutCanonicalURL);
	}

	/**
	 * Deletes the layout canonical url with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutCanonicalURLId the primary key of the layout canonical url
	 * @return the layout canonical url that was removed
	 * @throws PortalException if a layout canonical url with the primary key could not be found
	 */
	public static com.liferay.layout.seo.model.LayoutCanonicalURL
			deleteLayoutCanonicalURL(long layoutCanonicalURLId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteLayoutCanonicalURL(layoutCanonicalURLId);
	}

	public static void deleteLayoutCanonicalURL(
			long groupId, boolean privateLayout, long layoutId)
		throws com.liferay.layout.seo.exception.NoSuchCanonicalURLException {

		getService().deleteLayoutCanonicalURL(groupId, privateLayout, layoutId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.layout.seo.model.impl.LayoutCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.layout.seo.model.impl.LayoutCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.layout.seo.model.LayoutCanonicalURL
		fetchLayoutCanonicalURL(long layoutCanonicalURLId) {

		return getService().fetchLayoutCanonicalURL(layoutCanonicalURLId);
	}

	public static com.liferay.layout.seo.model.LayoutCanonicalURL
		fetchLayoutCanonicalURL(
			long groupId, boolean privateLayout, long layoutId) {

		return getService().fetchLayoutCanonicalURL(
			groupId, privateLayout, layoutId);
	}

	/**
	 * Returns the layout canonical url matching the UUID and group.
	 *
	 * @param uuid the layout canonical url's UUID
	 * @param groupId the primary key of the group
	 * @return the matching layout canonical url, or <code>null</code> if a matching layout canonical url could not be found
	 */
	public static com.liferay.layout.seo.model.LayoutCanonicalURL
		fetchLayoutCanonicalURLByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchLayoutCanonicalURLByUuidAndGroupId(
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
	 * Returns the layout canonical url with the primary key.
	 *
	 * @param layoutCanonicalURLId the primary key of the layout canonical url
	 * @return the layout canonical url
	 * @throws PortalException if a layout canonical url with the primary key could not be found
	 */
	public static com.liferay.layout.seo.model.LayoutCanonicalURL
			getLayoutCanonicalURL(long layoutCanonicalURLId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getLayoutCanonicalURL(layoutCanonicalURLId);
	}

	/**
	 * Returns the layout canonical url matching the UUID and group.
	 *
	 * @param uuid the layout canonical url's UUID
	 * @param groupId the primary key of the group
	 * @return the matching layout canonical url
	 * @throws PortalException if a matching layout canonical url could not be found
	 */
	public static com.liferay.layout.seo.model.LayoutCanonicalURL
			getLayoutCanonicalURLByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getLayoutCanonicalURLByUuidAndGroupId(
			uuid, groupId);
	}

	/**
	 * Returns a range of all the layout canonical urls.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.layout.seo.model.impl.LayoutCanonicalURLModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout canonical urls
	 * @param end the upper bound of the range of layout canonical urls (not inclusive)
	 * @return the range of layout canonical urls
	 */
	public static java.util.List
		<com.liferay.layout.seo.model.LayoutCanonicalURL>
			getLayoutCanonicalURLs(int start, int end) {

		return getService().getLayoutCanonicalURLs(start, end);
	}

	/**
	 * Returns all the layout canonical urls matching the UUID and company.
	 *
	 * @param uuid the UUID of the layout canonical urls
	 * @param companyId the primary key of the company
	 * @return the matching layout canonical urls, or an empty list if no matches were found
	 */
	public static java.util.List
		<com.liferay.layout.seo.model.LayoutCanonicalURL>
			getLayoutCanonicalURLsByUuidAndCompanyId(
				String uuid, long companyId) {

		return getService().getLayoutCanonicalURLsByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of layout canonical urls matching the UUID and company.
	 *
	 * @param uuid the UUID of the layout canonical urls
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of layout canonical urls
	 * @param end the upper bound of the range of layout canonical urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching layout canonical urls, or an empty list if no matches were found
	 */
	public static java.util.List
		<com.liferay.layout.seo.model.LayoutCanonicalURL>
			getLayoutCanonicalURLsByUuidAndCompanyId(
				String uuid, long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.layout.seo.model.LayoutCanonicalURL>
						orderByComparator) {

		return getService().getLayoutCanonicalURLsByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of layout canonical urls.
	 *
	 * @return the number of layout canonical urls
	 */
	public static int getLayoutCanonicalURLsCount() {
		return getService().getLayoutCanonicalURLsCount();
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
	 * Updates the layout canonical url in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param layoutCanonicalURL the layout canonical url
	 * @return the layout canonical url that was updated
	 */
	public static com.liferay.layout.seo.model.LayoutCanonicalURL
		updateLayoutCanonicalURL(
			com.liferay.layout.seo.model.LayoutCanonicalURL
				layoutCanonicalURL) {

		return getService().updateLayoutCanonicalURL(layoutCanonicalURL);
	}

	public static com.liferay.layout.seo.model.LayoutCanonicalURL
			updateLayoutCanonicalURL(
				long userId, long groupId, boolean privateLayout, long layoutId,
				boolean enabled,
				java.util.Map<java.util.Locale, String> canonicalURLMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateLayoutCanonicalURL(
			userId, groupId, privateLayout, layoutId, enabled, canonicalURLMap);
	}

	public static LayoutCanonicalURLLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<LayoutCanonicalURLLocalService, LayoutCanonicalURLLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			LayoutCanonicalURLLocalService.class);

		ServiceTracker
			<LayoutCanonicalURLLocalService, LayoutCanonicalURLLocalService>
				serviceTracker =
					new ServiceTracker
						<LayoutCanonicalURLLocalService,
						 LayoutCanonicalURLLocalService>(
							 bundle.getBundleContext(),
							 LayoutCanonicalURLLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}