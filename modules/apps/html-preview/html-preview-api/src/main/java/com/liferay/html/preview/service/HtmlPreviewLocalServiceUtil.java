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

package com.liferay.html.preview.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for HtmlPreview. This utility wraps
 * {@link com.liferay.html.preview.service.impl.HtmlPreviewLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see HtmlPreviewLocalService
 * @see com.liferay.html.preview.service.base.HtmlPreviewLocalServiceBaseImpl
 * @see com.liferay.html.preview.service.impl.HtmlPreviewLocalServiceImpl
 * @generated
 */
@ProviderType
public class HtmlPreviewLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.html.preview.service.impl.HtmlPreviewLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the html preview to the database. Also notifies the appropriate model listeners.
	*
	* @param htmlPreview the html preview
	* @return the html preview that was added
	*/
	public static com.liferay.html.preview.model.HtmlPreview addHtmlPreview(
		com.liferay.html.preview.model.HtmlPreview htmlPreview) {
		return getService().addHtmlPreview(htmlPreview);
	}

	/**
	* Creates a new html preview with the primary key. Does not add the html preview to the database.
	*
	* @param htmlPreviewId the primary key for the new html preview
	* @return the new html preview
	*/
	public static com.liferay.html.preview.model.HtmlPreview createHtmlPreview(
		long htmlPreviewId) {
		return getService().createHtmlPreview(htmlPreviewId);
	}

	/**
	* Deletes the html preview from the database. Also notifies the appropriate model listeners.
	*
	* @param htmlPreview the html preview
	* @return the html preview that was removed
	*/
	public static com.liferay.html.preview.model.HtmlPreview deleteHtmlPreview(
		com.liferay.html.preview.model.HtmlPreview htmlPreview) {
		return getService().deleteHtmlPreview(htmlPreview);
	}

	/**
	* Deletes the html preview with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param htmlPreviewId the primary key of the html preview
	* @return the html preview that was removed
	* @throws PortalException if a html preview with the primary key could not be found
	*/
	public static com.liferay.html.preview.model.HtmlPreview deleteHtmlPreview(
		long htmlPreviewId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteHtmlPreview(htmlPreviewId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.html.preview.model.impl.HtmlPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.html.preview.model.impl.HtmlPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
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

	public static com.liferay.html.preview.model.HtmlPreview fetchHtmlPreview(
		long htmlPreviewId) {
		return getService().fetchHtmlPreview(htmlPreviewId);
	}

	public static com.liferay.html.preview.model.HtmlPreview generateHtmlPreview(
		long userId, long groupId, long classNameId, long classPK,
		java.lang.String content, java.lang.String mimeType,
		boolean asynchronous,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .generateHtmlPreview(userId, groupId, classNameId, classPK,
			content, mimeType, asynchronous, serviceContext);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the html preview with the primary key.
	*
	* @param htmlPreviewId the primary key of the html preview
	* @return the html preview
	* @throws PortalException if a html preview with the primary key could not be found
	*/
	public static com.liferay.html.preview.model.HtmlPreview getHtmlPreview(
		long htmlPreviewId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getHtmlPreview(htmlPreviewId);
	}

	/**
	* Returns a range of all the html previews.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.html.preview.model.impl.HtmlPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of html previews
	* @param end the upper bound of the range of html previews (not inclusive)
	* @return the range of html previews
	*/
	public static java.util.List<com.liferay.html.preview.model.HtmlPreview> getHtmlPreviews(
		int start, int end) {
		return getService().getHtmlPreviews(start, end);
	}

	/**
	* Returns the number of html previews.
	*
	* @return the number of html previews
	*/
	public static int getHtmlPreviewsCount() {
		return getService().getHtmlPreviewsCount();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the html preview in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param htmlPreview the html preview
	* @return the html preview that was updated
	*/
	public static com.liferay.html.preview.model.HtmlPreview updateHtmlPreview(
		com.liferay.html.preview.model.HtmlPreview htmlPreview) {
		return getService().updateHtmlPreview(htmlPreview);
	}

	public static HtmlPreviewLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<HtmlPreviewLocalService, HtmlPreviewLocalService> _serviceTracker =
		ServiceTrackerFactory.open(HtmlPreviewLocalService.class);
}