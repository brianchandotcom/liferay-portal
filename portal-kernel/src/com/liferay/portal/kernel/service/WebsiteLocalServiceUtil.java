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

package com.liferay.portal.kernel.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * Provides the local service utility for Website. This utility wraps
 * <code>com.liferay.portal.service.impl.WebsiteLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see WebsiteLocalService
 * @generated
 */
public class WebsiteLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.WebsiteLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.kernel.model.Website addWebsite(
			long userId, String className, long classPK, String url,
			long typeId, boolean primary, ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addWebsite(
			userId, className, classPK, url, typeId, primary, serviceContext);
	}

	/**
	 * Adds the website to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect WebsiteLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param website the website
	 * @return the website that was added
	 */
	public static com.liferay.portal.kernel.model.Website addWebsite(
		com.liferay.portal.kernel.model.Website website) {

		return getService().addWebsite(website);
	}

	/**
	 * Creates a new website with the primary key. Does not add the website to the database.
	 *
	 * @param websiteId the primary key for the new website
	 * @return the new website
	 */
	public static com.liferay.portal.kernel.model.Website createWebsite(
		long websiteId) {

		return getService().createWebsite(websiteId);
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
	 * Deletes the website with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect WebsiteLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param websiteId the primary key of the website
	 * @return the website that was removed
	 * @throws PortalException if a website with the primary key could not be found
	 */
	public static com.liferay.portal.kernel.model.Website deleteWebsite(
			long websiteId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteWebsite(websiteId);
	}

	/**
	 * Deletes the website from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect WebsiteLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param website the website
	 * @return the website that was removed
	 */
	public static com.liferay.portal.kernel.model.Website deleteWebsite(
		com.liferay.portal.kernel.model.Website website) {

		return getService().deleteWebsite(website);
	}

	public static void deleteWebsites(
		long companyId, String className, long classPK) {

		getService().deleteWebsites(companyId, className, classPK);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.WebsiteModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.WebsiteModelImpl</code>.
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

	public static com.liferay.portal.kernel.model.Website fetchWebsite(
		long websiteId) {

		return getService().fetchWebsite(websiteId);
	}

	/**
	 * Returns the website with the matching UUID and company.
	 *
	 * @param uuid the website's UUID
	 * @param companyId the primary key of the company
	 * @return the matching website, or <code>null</code> if a matching website could not be found
	 */
	public static com.liferay.portal.kernel.model.Website
		fetchWebsiteByUuidAndCompanyId(String uuid, long companyId) {

		return getService().fetchWebsiteByUuidAndCompanyId(uuid, companyId);
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

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the website with the primary key.
	 *
	 * @param websiteId the primary key of the website
	 * @return the website
	 * @throws PortalException if a website with the primary key could not be found
	 */
	public static com.liferay.portal.kernel.model.Website getWebsite(
			long websiteId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getWebsite(websiteId);
	}

	/**
	 * Returns the website with the matching UUID and company.
	 *
	 * @param uuid the website's UUID
	 * @param companyId the primary key of the company
	 * @return the matching website
	 * @throws PortalException if a matching website could not be found
	 */
	public static com.liferay.portal.kernel.model.Website
			getWebsiteByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getWebsiteByUuidAndCompanyId(uuid, companyId);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Website>
		getWebsites() {

		return getService().getWebsites();
	}

	/**
	 * Returns a range of all the websites.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.WebsiteModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of websites
	 * @param end the upper bound of the range of websites (not inclusive)
	 * @return the range of websites
	 */
	public static java.util.List<com.liferay.portal.kernel.model.Website>
		getWebsites(int start, int end) {

		return getService().getWebsites(start, end);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Website>
		getWebsites(long companyId, String className, long classPK) {

		return getService().getWebsites(companyId, className, classPK);
	}

	/**
	 * Returns the number of websites.
	 *
	 * @return the number of websites
	 */
	public static int getWebsitesCount() {
		return getService().getWebsitesCount();
	}

	public static com.liferay.portal.kernel.model.Website updateWebsite(
			long websiteId, String url, long typeId, boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateWebsite(websiteId, url, typeId, primary);
	}

	/**
	 * Updates the website in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect WebsiteLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param website the website
	 * @return the website that was updated
	 */
	public static com.liferay.portal.kernel.model.Website updateWebsite(
		com.liferay.portal.kernel.model.Website website) {

		return getService().updateWebsite(website);
	}

	public static WebsiteLocalService getService() {
		if (_service == null) {
			_service = (WebsiteLocalService)PortalBeanLocatorUtil.locate(
				WebsiteLocalService.class.getName());
		}

		return _service;
	}

	private static WebsiteLocalService _service;

}