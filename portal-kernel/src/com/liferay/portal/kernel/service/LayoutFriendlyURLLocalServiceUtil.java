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
 * Provides the local service utility for LayoutFriendlyURL. This utility wraps
 * <code>com.liferay.portal.service.impl.LayoutFriendlyURLLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutFriendlyURLLocalService
 * @generated
 */
public class LayoutFriendlyURLLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.LayoutFriendlyURLLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the layout friendly url to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LayoutFriendlyURLLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param layoutFriendlyURL the layout friendly url
	 * @return the layout friendly url that was added
	 */
	public static com.liferay.portal.kernel.model.LayoutFriendlyURL
		addLayoutFriendlyURL(
			com.liferay.portal.kernel.model.LayoutFriendlyURL
				layoutFriendlyURL) {

		return getService().addLayoutFriendlyURL(layoutFriendlyURL);
	}

	public static com.liferay.portal.kernel.model.LayoutFriendlyURL
			addLayoutFriendlyURL(
				long userId, long companyId, long groupId, long plid,
				boolean privateLayout, String friendlyURL, String languageId,
				ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addLayoutFriendlyURL(
			userId, companyId, groupId, plid, privateLayout, friendlyURL,
			languageId, serviceContext);
	}

	public static java.util.List
		<com.liferay.portal.kernel.model.LayoutFriendlyURL>
				addLayoutFriendlyURLs(
					long userId, long companyId, long groupId, long plid,
					boolean privateLayout,
					java.util.Map<java.util.Locale, String> friendlyURLMap,
					ServiceContext serviceContext)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addLayoutFriendlyURLs(
			userId, companyId, groupId, plid, privateLayout, friendlyURLMap,
			serviceContext);
	}

	/**
	 * Creates a new layout friendly url with the primary key. Does not add the layout friendly url to the database.
	 *
	 * @param layoutFriendlyURLId the primary key for the new layout friendly url
	 * @return the new layout friendly url
	 */
	public static com.liferay.portal.kernel.model.LayoutFriendlyURL
		createLayoutFriendlyURL(long layoutFriendlyURLId) {

		return getService().createLayoutFriendlyURL(layoutFriendlyURLId);
	}

	/**
	 * Deletes the layout friendly url from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LayoutFriendlyURLLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param layoutFriendlyURL the layout friendly url
	 * @return the layout friendly url that was removed
	 */
	public static com.liferay.portal.kernel.model.LayoutFriendlyURL
		deleteLayoutFriendlyURL(
			com.liferay.portal.kernel.model.LayoutFriendlyURL
				layoutFriendlyURL) {

		return getService().deleteLayoutFriendlyURL(layoutFriendlyURL);
	}

	/**
	 * Deletes the layout friendly url with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LayoutFriendlyURLLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param layoutFriendlyURLId the primary key of the layout friendly url
	 * @return the layout friendly url that was removed
	 * @throws PortalException if a layout friendly url with the primary key could not be found
	 */
	public static com.liferay.portal.kernel.model.LayoutFriendlyURL
			deleteLayoutFriendlyURL(long layoutFriendlyURLId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteLayoutFriendlyURL(layoutFriendlyURLId);
	}

	public static void deleteLayoutFriendlyURL(long plid, String languageId) {
		getService().deleteLayoutFriendlyURL(plid, languageId);
	}

	public static void deleteLayoutFriendlyURLs(long plid) {
		getService().deleteLayoutFriendlyURLs(plid);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.LayoutFriendlyURLModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.LayoutFriendlyURLModelImpl</code>.
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

	public static com.liferay.portal.kernel.model.LayoutFriendlyURL
		fetchFirstLayoutFriendlyURL(
			long groupId, boolean privateLayout, String friendlyURL) {

		return getService().fetchFirstLayoutFriendlyURL(
			groupId, privateLayout, friendlyURL);
	}

	public static com.liferay.portal.kernel.model.LayoutFriendlyURL
		fetchLayoutFriendlyURL(long layoutFriendlyURLId) {

		return getService().fetchLayoutFriendlyURL(layoutFriendlyURLId);
	}

	public static com.liferay.portal.kernel.model.LayoutFriendlyURL
		fetchLayoutFriendlyURL(
			long groupId, boolean privateLayout, String friendlyURL,
			String languageId) {

		return getService().fetchLayoutFriendlyURL(
			groupId, privateLayout, friendlyURL, languageId);
	}

	public static com.liferay.portal.kernel.model.LayoutFriendlyURL
		fetchLayoutFriendlyURL(long plid, String languageId) {

		return getService().fetchLayoutFriendlyURL(plid, languageId);
	}

	public static com.liferay.portal.kernel.model.LayoutFriendlyURL
		fetchLayoutFriendlyURL(
			long plid, String languageId, boolean useDefault) {

		return getService().fetchLayoutFriendlyURL(
			plid, languageId, useDefault);
	}

	/**
	 * Returns the layout friendly url matching the UUID and group.
	 *
	 * @param uuid the layout friendly url's UUID
	 * @param groupId the primary key of the group
	 * @return the matching layout friendly url, or <code>null</code> if a matching layout friendly url could not be found
	 */
	public static com.liferay.portal.kernel.model.LayoutFriendlyURL
		fetchLayoutFriendlyURLByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchLayoutFriendlyURLByUuidAndGroupId(
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
	 * Returns the layout friendly url with the primary key.
	 *
	 * @param layoutFriendlyURLId the primary key of the layout friendly url
	 * @return the layout friendly url
	 * @throws PortalException if a layout friendly url with the primary key could not be found
	 */
	public static com.liferay.portal.kernel.model.LayoutFriendlyURL
			getLayoutFriendlyURL(long layoutFriendlyURLId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getLayoutFriendlyURL(layoutFriendlyURLId);
	}

	public static com.liferay.portal.kernel.model.LayoutFriendlyURL
			getLayoutFriendlyURL(long plid, String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getLayoutFriendlyURL(plid, languageId);
	}

	public static com.liferay.portal.kernel.model.LayoutFriendlyURL
			getLayoutFriendlyURL(
				long plid, String languageId, boolean useDefault)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getLayoutFriendlyURL(plid, languageId, useDefault);
	}

	/**
	 * Returns the layout friendly url matching the UUID and group.
	 *
	 * @param uuid the layout friendly url's UUID
	 * @param groupId the primary key of the group
	 * @return the matching layout friendly url
	 * @throws PortalException if a matching layout friendly url could not be found
	 */
	public static com.liferay.portal.kernel.model.LayoutFriendlyURL
			getLayoutFriendlyURLByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getLayoutFriendlyURLByUuidAndGroupId(uuid, groupId);
	}

	public static java.util.Map<Long, String> getLayoutFriendlyURLs(
		com.liferay.portal.kernel.model.Group siteGroup,
		java.util.List<com.liferay.portal.kernel.model.Layout> layouts,
		String languageId) {

		return getService().getLayoutFriendlyURLs(
			siteGroup, layouts, languageId);
	}

	/**
	 * Returns a range of all the layout friendly urls.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.LayoutFriendlyURLModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout friendly urls
	 * @param end the upper bound of the range of layout friendly urls (not inclusive)
	 * @return the range of layout friendly urls
	 */
	public static java.util.List
		<com.liferay.portal.kernel.model.LayoutFriendlyURL>
			getLayoutFriendlyURLs(int start, int end) {

		return getService().getLayoutFriendlyURLs(start, end);
	}

	public static java.util.List
		<com.liferay.portal.kernel.model.LayoutFriendlyURL>
			getLayoutFriendlyURLs(long plid) {

		return getService().getLayoutFriendlyURLs(plid);
	}

	public static java.util.List
		<com.liferay.portal.kernel.model.LayoutFriendlyURL>
			getLayoutFriendlyURLs(
				long plid, String friendlyURL, int start, int end) {

		return getService().getLayoutFriendlyURLs(
			plid, friendlyURL, start, end);
	}

	/**
	 * Returns all the layout friendly urls matching the UUID and company.
	 *
	 * @param uuid the UUID of the layout friendly urls
	 * @param companyId the primary key of the company
	 * @return the matching layout friendly urls, or an empty list if no matches were found
	 */
	public static java.util.List
		<com.liferay.portal.kernel.model.LayoutFriendlyURL>
			getLayoutFriendlyURLsByUuidAndCompanyId(
				String uuid, long companyId) {

		return getService().getLayoutFriendlyURLsByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of layout friendly urls matching the UUID and company.
	 *
	 * @param uuid the UUID of the layout friendly urls
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of layout friendly urls
	 * @param end the upper bound of the range of layout friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching layout friendly urls, or an empty list if no matches were found
	 */
	public static java.util.List
		<com.liferay.portal.kernel.model.LayoutFriendlyURL>
			getLayoutFriendlyURLsByUuidAndCompanyId(
				String uuid, long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.kernel.model.LayoutFriendlyURL>
						orderByComparator) {

		return getService().getLayoutFriendlyURLsByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of layout friendly urls.
	 *
	 * @return the number of layout friendly urls
	 */
	public static int getLayoutFriendlyURLsCount() {
		return getService().getLayoutFriendlyURLsCount();
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
	 * Updates the layout friendly url in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LayoutFriendlyURLLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param layoutFriendlyURL the layout friendly url
	 * @return the layout friendly url that was updated
	 */
	public static com.liferay.portal.kernel.model.LayoutFriendlyURL
		updateLayoutFriendlyURL(
			com.liferay.portal.kernel.model.LayoutFriendlyURL
				layoutFriendlyURL) {

		return getService().updateLayoutFriendlyURL(layoutFriendlyURL);
	}

	public static com.liferay.portal.kernel.model.LayoutFriendlyURL
			updateLayoutFriendlyURL(
				long userId, long companyId, long groupId, long plid,
				boolean privateLayout, String friendlyURL, String languageId,
				ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateLayoutFriendlyURL(
			userId, companyId, groupId, plid, privateLayout, friendlyURL,
			languageId, serviceContext);
	}

	public static java.util.List
		<com.liferay.portal.kernel.model.LayoutFriendlyURL>
				updateLayoutFriendlyURLs(
					long userId, long companyId, long groupId, long plid,
					boolean privateLayout,
					java.util.Map<java.util.Locale, String> friendlyURLMap,
					ServiceContext serviceContext)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateLayoutFriendlyURLs(
			userId, companyId, groupId, plid, privateLayout, friendlyURLMap,
			serviceContext);
	}

	public static LayoutFriendlyURLLocalService getService() {
		if (_service == null) {
			_service =
				(LayoutFriendlyURLLocalService)PortalBeanLocatorUtil.locate(
					LayoutFriendlyURLLocalService.class.getName());
		}

		return _service;
	}

	private static LayoutFriendlyURLLocalService _service;

}