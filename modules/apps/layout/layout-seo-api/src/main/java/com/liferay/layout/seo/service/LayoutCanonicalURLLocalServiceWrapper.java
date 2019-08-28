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

import com.liferay.portal.kernel.service.ServiceWrapper;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides a wrapper for {@link LayoutCanonicalURLLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutCanonicalURLLocalService
 * @generated
 */
@ProviderType
public class LayoutCanonicalURLLocalServiceWrapper
	implements LayoutCanonicalURLLocalService,
			   ServiceWrapper<LayoutCanonicalURLLocalService> {

	public LayoutCanonicalURLLocalServiceWrapper(
		LayoutCanonicalURLLocalService layoutCanonicalURLLocalService) {

		_layoutCanonicalURLLocalService = layoutCanonicalURLLocalService;
	}

	/**
	 * Adds the layout canonical url to the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutCanonicalURL the layout canonical url
	 * @return the layout canonical url that was added
	 */
	@Override
	public com.liferay.layout.seo.model.LayoutCanonicalURL
		addLayoutCanonicalURL(
			com.liferay.layout.seo.model.LayoutCanonicalURL
				layoutCanonicalURL) {

		return _layoutCanonicalURLLocalService.addLayoutCanonicalURL(
			layoutCanonicalURL);
	}

	/**
	 * Creates a new layout canonical url with the primary key. Does not add the layout canonical url to the database.
	 *
	 * @param layoutCanonicalURLId the primary key for the new layout canonical url
	 * @return the new layout canonical url
	 */
	@Override
	public com.liferay.layout.seo.model.LayoutCanonicalURL
		createLayoutCanonicalURL(long layoutCanonicalURLId) {

		return _layoutCanonicalURLLocalService.createLayoutCanonicalURL(
			layoutCanonicalURLId);
	}

	/**
	 * Deletes the layout canonical url from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutCanonicalURL the layout canonical url
	 * @return the layout canonical url that was removed
	 */
	@Override
	public com.liferay.layout.seo.model.LayoutCanonicalURL
		deleteLayoutCanonicalURL(
			com.liferay.layout.seo.model.LayoutCanonicalURL
				layoutCanonicalURL) {

		return _layoutCanonicalURLLocalService.deleteLayoutCanonicalURL(
			layoutCanonicalURL);
	}

	/**
	 * Deletes the layout canonical url with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutCanonicalURLId the primary key of the layout canonical url
	 * @return the layout canonical url that was removed
	 * @throws PortalException if a layout canonical url with the primary key could not be found
	 */
	@Override
	public com.liferay.layout.seo.model.LayoutCanonicalURL
			deleteLayoutCanonicalURL(long layoutCanonicalURLId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutCanonicalURLLocalService.deleteLayoutCanonicalURL(
			layoutCanonicalURLId);
	}

	@Override
	public void deleteLayoutCanonicalURL(
			long groupId, boolean privateLayout, long layoutId)
		throws com.liferay.layout.seo.exception.NoSuchCanonicalURLException {

		_layoutCanonicalURLLocalService.deleteLayoutCanonicalURL(
			groupId, privateLayout, layoutId);
	}

	@Override
	public void deleteLayoutCanonicalURL(String uuid, long groupId)
		throws com.liferay.layout.seo.exception.NoSuchCanonicalURLException {

		_layoutCanonicalURLLocalService.deleteLayoutCanonicalURL(uuid, groupId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutCanonicalURLLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _layoutCanonicalURLLocalService.dynamicQuery();
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

		return _layoutCanonicalURLLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _layoutCanonicalURLLocalService.dynamicQuery(
			dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _layoutCanonicalURLLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
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

		return _layoutCanonicalURLLocalService.dynamicQueryCount(dynamicQuery);
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

		return _layoutCanonicalURLLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.layout.seo.model.LayoutCanonicalURL
		fetchLayoutCanonicalURL(long layoutCanonicalURLId) {

		return _layoutCanonicalURLLocalService.fetchLayoutCanonicalURL(
			layoutCanonicalURLId);
	}

	@Override
	public com.liferay.layout.seo.model.LayoutCanonicalURL
		fetchLayoutCanonicalURL(
			long groupId, boolean privateLayout, long layoutId) {

		return _layoutCanonicalURLLocalService.fetchLayoutCanonicalURL(
			groupId, privateLayout, layoutId);
	}

	/**
	 * Returns the layout canonical url matching the UUID and group.
	 *
	 * @param uuid the layout canonical url's UUID
	 * @param groupId the primary key of the group
	 * @return the matching layout canonical url, or <code>null</code> if a matching layout canonical url could not be found
	 */
	@Override
	public com.liferay.layout.seo.model.LayoutCanonicalURL
		fetchLayoutCanonicalURLByUuidAndGroupId(String uuid, long groupId) {

		return _layoutCanonicalURLLocalService.
			fetchLayoutCanonicalURLByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _layoutCanonicalURLLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _layoutCanonicalURLLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _layoutCanonicalURLLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the layout canonical url with the primary key.
	 *
	 * @param layoutCanonicalURLId the primary key of the layout canonical url
	 * @return the layout canonical url
	 * @throws PortalException if a layout canonical url with the primary key could not be found
	 */
	@Override
	public com.liferay.layout.seo.model.LayoutCanonicalURL
			getLayoutCanonicalURL(long layoutCanonicalURLId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutCanonicalURLLocalService.getLayoutCanonicalURL(
			layoutCanonicalURLId);
	}

	/**
	 * Returns the layout canonical url matching the UUID and group.
	 *
	 * @param uuid the layout canonical url's UUID
	 * @param groupId the primary key of the group
	 * @return the matching layout canonical url
	 * @throws PortalException if a matching layout canonical url could not be found
	 */
	@Override
	public com.liferay.layout.seo.model.LayoutCanonicalURL
			getLayoutCanonicalURLByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutCanonicalURLLocalService.
			getLayoutCanonicalURLByUuidAndGroupId(uuid, groupId);
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
	@Override
	public java.util.List<com.liferay.layout.seo.model.LayoutCanonicalURL>
		getLayoutCanonicalURLs(int start, int end) {

		return _layoutCanonicalURLLocalService.getLayoutCanonicalURLs(
			start, end);
	}

	/**
	 * Returns all the layout canonical urls matching the UUID and company.
	 *
	 * @param uuid the UUID of the layout canonical urls
	 * @param companyId the primary key of the company
	 * @return the matching layout canonical urls, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.layout.seo.model.LayoutCanonicalURL>
		getLayoutCanonicalURLsByUuidAndCompanyId(String uuid, long companyId) {

		return _layoutCanonicalURLLocalService.
			getLayoutCanonicalURLsByUuidAndCompanyId(uuid, companyId);
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
	@Override
	public java.util.List<com.liferay.layout.seo.model.LayoutCanonicalURL>
		getLayoutCanonicalURLsByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.layout.seo.model.LayoutCanonicalURL>
					orderByComparator) {

		return _layoutCanonicalURLLocalService.
			getLayoutCanonicalURLsByUuidAndCompanyId(
				uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of layout canonical urls.
	 *
	 * @return the number of layout canonical urls
	 */
	@Override
	public int getLayoutCanonicalURLsCount() {
		return _layoutCanonicalURLLocalService.getLayoutCanonicalURLsCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _layoutCanonicalURLLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutCanonicalURLLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the layout canonical url in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param layoutCanonicalURL the layout canonical url
	 * @return the layout canonical url that was updated
	 */
	@Override
	public com.liferay.layout.seo.model.LayoutCanonicalURL
		updateLayoutCanonicalURL(
			com.liferay.layout.seo.model.LayoutCanonicalURL
				layoutCanonicalURL) {

		return _layoutCanonicalURLLocalService.updateLayoutCanonicalURL(
			layoutCanonicalURL);
	}

	@Override
	public com.liferay.layout.seo.model.LayoutCanonicalURL
			updateLayoutCanonicalURL(
				long userId, long groupId, boolean privateLayout, long layoutId,
				boolean enabled,
				java.util.Map<java.util.Locale, String> canonicalURLMap,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutCanonicalURLLocalService.updateLayoutCanonicalURL(
			userId, groupId, privateLayout, layoutId, enabled, canonicalURLMap,
			serviceContext);
	}

	@Override
	public LayoutCanonicalURLLocalService getWrappedService() {
		return _layoutCanonicalURLLocalService;
	}

	@Override
	public void setWrappedService(
		LayoutCanonicalURLLocalService layoutCanonicalURLLocalService) {

		_layoutCanonicalURLLocalService = layoutCanonicalURLLocalService;
	}

	private LayoutCanonicalURLLocalService _layoutCanonicalURLLocalService;

}