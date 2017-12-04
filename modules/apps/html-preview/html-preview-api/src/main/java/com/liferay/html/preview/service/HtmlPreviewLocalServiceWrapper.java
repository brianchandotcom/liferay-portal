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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link HtmlPreviewLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see HtmlPreviewLocalService
 * @generated
 */
@ProviderType
public class HtmlPreviewLocalServiceWrapper implements HtmlPreviewLocalService,
	ServiceWrapper<HtmlPreviewLocalService> {
	public HtmlPreviewLocalServiceWrapper(
		HtmlPreviewLocalService htmlPreviewLocalService) {
		_htmlPreviewLocalService = htmlPreviewLocalService;
	}

	/**
	* Adds the html preview to the database. Also notifies the appropriate model listeners.
	*
	* @param htmlPreview the html preview
	* @return the html preview that was added
	*/
	@Override
	public com.liferay.html.preview.model.HtmlPreview addHtmlPreview(
		com.liferay.html.preview.model.HtmlPreview htmlPreview) {
		return _htmlPreviewLocalService.addHtmlPreview(htmlPreview);
	}

	/**
	* Creates a new html preview with the primary key. Does not add the html preview to the database.
	*
	* @param htmlPreviewId the primary key for the new html preview
	* @return the new html preview
	*/
	@Override
	public com.liferay.html.preview.model.HtmlPreview createHtmlPreview(
		long htmlPreviewId) {
		return _htmlPreviewLocalService.createHtmlPreview(htmlPreviewId);
	}

	/**
	* Deletes the html preview from the database. Also notifies the appropriate model listeners.
	*
	* @param htmlPreview the html preview
	* @return the html preview that was removed
	*/
	@Override
	public com.liferay.html.preview.model.HtmlPreview deleteHtmlPreview(
		com.liferay.html.preview.model.HtmlPreview htmlPreview) {
		return _htmlPreviewLocalService.deleteHtmlPreview(htmlPreview);
	}

	/**
	* Deletes the html preview with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param htmlPreviewId the primary key of the html preview
	* @return the html preview that was removed
	* @throws PortalException if a html preview with the primary key could not be found
	*/
	@Override
	public com.liferay.html.preview.model.HtmlPreview deleteHtmlPreview(
		long htmlPreviewId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _htmlPreviewLocalService.deleteHtmlPreview(htmlPreviewId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _htmlPreviewLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _htmlPreviewLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _htmlPreviewLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _htmlPreviewLocalService.dynamicQuery(dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _htmlPreviewLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _htmlPreviewLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _htmlPreviewLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.html.preview.model.HtmlPreview fetchHtmlPreview(
		long htmlPreviewId) {
		return _htmlPreviewLocalService.fetchHtmlPreview(htmlPreviewId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _htmlPreviewLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the html preview with the primary key.
	*
	* @param htmlPreviewId the primary key of the html preview
	* @return the html preview
	* @throws PortalException if a html preview with the primary key could not be found
	*/
	@Override
	public com.liferay.html.preview.model.HtmlPreview getHtmlPreview(
		long htmlPreviewId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _htmlPreviewLocalService.getHtmlPreview(htmlPreviewId);
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
	@Override
	public java.util.List<com.liferay.html.preview.model.HtmlPreview> getHtmlPreviews(
		int start, int end) {
		return _htmlPreviewLocalService.getHtmlPreviews(start, end);
	}

	/**
	* Returns the number of html previews.
	*
	* @return the number of html previews
	*/
	@Override
	public int getHtmlPreviewsCount() {
		return _htmlPreviewLocalService.getHtmlPreviewsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _htmlPreviewLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _htmlPreviewLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _htmlPreviewLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the html preview in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param htmlPreview the html preview
	* @return the html preview that was updated
	*/
	@Override
	public com.liferay.html.preview.model.HtmlPreview updateHtmlPreview(
		com.liferay.html.preview.model.HtmlPreview htmlPreview) {
		return _htmlPreviewLocalService.updateHtmlPreview(htmlPreview);
	}

	@Override
	public HtmlPreviewLocalService getWrappedService() {
		return _htmlPreviewLocalService;
	}

	@Override
	public void setWrappedService(
		HtmlPreviewLocalService htmlPreviewLocalService) {
		_htmlPreviewLocalService = htmlPreviewLocalService;
	}

	private HtmlPreviewLocalService _htmlPreviewLocalService;
}