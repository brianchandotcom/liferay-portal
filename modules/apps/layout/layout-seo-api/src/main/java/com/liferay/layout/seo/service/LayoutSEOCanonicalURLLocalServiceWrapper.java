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
 * Provides a wrapper for {@link LayoutSEOCanonicalURLLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSEOCanonicalURLLocalService
 * @generated
 */
@ProviderType
public class LayoutSEOCanonicalURLLocalServiceWrapper
	implements LayoutSEOCanonicalURLLocalService,
			   ServiceWrapper<LayoutSEOCanonicalURLLocalService> {

	public LayoutSEOCanonicalURLLocalServiceWrapper(
		LayoutSEOCanonicalURLLocalService layoutSEOCanonicalURLLocalService) {

		_layoutSEOCanonicalURLLocalService = layoutSEOCanonicalURLLocalService;
	}

	/**
	 * Adds the layout seo canonical url to the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutSEOCanonicalURL the layout seo canonical url
	 * @return the layout seo canonical url that was added
	 */
	@Override
	public com.liferay.layout.seo.model.LayoutSEOCanonicalURL
		addLayoutSEOCanonicalURL(
			com.liferay.layout.seo.model.LayoutSEOCanonicalURL
				layoutSEOCanonicalURL) {

		return _layoutSEOCanonicalURLLocalService.addLayoutSEOCanonicalURL(
			layoutSEOCanonicalURL);
	}

	/**
	 * Creates a new layout seo canonical url with the primary key. Does not add the layout seo canonical url to the database.
	 *
	 * @param layoutSEOCanonicalURLId the primary key for the new layout seo canonical url
	 * @return the new layout seo canonical url
	 */
	@Override
	public com.liferay.layout.seo.model.LayoutSEOCanonicalURL
		createLayoutSEOCanonicalURL(long layoutSEOCanonicalURLId) {

		return _layoutSEOCanonicalURLLocalService.createLayoutSEOCanonicalURL(
			layoutSEOCanonicalURLId);
	}

	/**
	 * Deletes the layout seo canonical url from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutSEOCanonicalURL the layout seo canonical url
	 * @return the layout seo canonical url that was removed
	 */
	@Override
	public com.liferay.layout.seo.model.LayoutSEOCanonicalURL
		deleteLayoutSEOCanonicalURL(
			com.liferay.layout.seo.model.LayoutSEOCanonicalURL
				layoutSEOCanonicalURL) {

		return _layoutSEOCanonicalURLLocalService.deleteLayoutSEOCanonicalURL(
			layoutSEOCanonicalURL);
	}

	/**
	 * Deletes the layout seo canonical url with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutSEOCanonicalURLId the primary key of the layout seo canonical url
	 * @return the layout seo canonical url that was removed
	 * @throws PortalException if a layout seo canonical url with the primary key could not be found
	 */
	@Override
	public com.liferay.layout.seo.model.LayoutSEOCanonicalURL
			deleteLayoutSEOCanonicalURL(long layoutSEOCanonicalURLId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutSEOCanonicalURLLocalService.deleteLayoutSEOCanonicalURL(
			layoutSEOCanonicalURLId);
	}

	@Override
	public void deleteLayoutSEOCanonicalURL(
			long groupId, boolean privateLayout, long layoutId)
		throws com.liferay.layout.seo.exception.NoSuchCanonicalURLException {

		_layoutSEOCanonicalURLLocalService.deleteLayoutSEOCanonicalURL(
			groupId, privateLayout, layoutId);
	}

	@Override
	public void deleteLayoutSEOCanonicalURL(String uuid, long groupId)
		throws com.liferay.layout.seo.exception.NoSuchCanonicalURLException {

		_layoutSEOCanonicalURLLocalService.deleteLayoutSEOCanonicalURL(
			uuid, groupId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutSEOCanonicalURLLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _layoutSEOCanonicalURLLocalService.dynamicQuery();
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

		return _layoutSEOCanonicalURLLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _layoutSEOCanonicalURLLocalService.dynamicQuery(
			dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _layoutSEOCanonicalURLLocalService.dynamicQuery(
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

		return _layoutSEOCanonicalURLLocalService.dynamicQueryCount(
			dynamicQuery);
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

		return _layoutSEOCanonicalURLLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.layout.seo.model.LayoutSEOCanonicalURL
		fetchLayoutSEOCanonicalURL(long layoutSEOCanonicalURLId) {

		return _layoutSEOCanonicalURLLocalService.fetchLayoutSEOCanonicalURL(
			layoutSEOCanonicalURLId);
	}

	@Override
	public com.liferay.layout.seo.model.LayoutSEOCanonicalURL
		fetchLayoutSEOCanonicalURL(
			long groupId, boolean privateLayout, long layoutId) {

		return _layoutSEOCanonicalURLLocalService.fetchLayoutSEOCanonicalURL(
			groupId, privateLayout, layoutId);
	}

	/**
	 * Returns the layout seo canonical url matching the UUID and group.
	 *
	 * @param uuid the layout seo canonical url's UUID
	 * @param groupId the primary key of the group
	 * @return the matching layout seo canonical url, or <code>null</code> if a matching layout seo canonical url could not be found
	 */
	@Override
	public com.liferay.layout.seo.model.LayoutSEOCanonicalURL
		fetchLayoutSEOCanonicalURLByUuidAndGroupId(String uuid, long groupId) {

		return _layoutSEOCanonicalURLLocalService.
			fetchLayoutSEOCanonicalURLByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _layoutSEOCanonicalURLLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _layoutSEOCanonicalURLLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _layoutSEOCanonicalURLLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the layout seo canonical url with the primary key.
	 *
	 * @param layoutSEOCanonicalURLId the primary key of the layout seo canonical url
	 * @return the layout seo canonical url
	 * @throws PortalException if a layout seo canonical url with the primary key could not be found
	 */
	@Override
	public com.liferay.layout.seo.model.LayoutSEOCanonicalURL
			getLayoutSEOCanonicalURL(long layoutSEOCanonicalURLId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutSEOCanonicalURLLocalService.getLayoutSEOCanonicalURL(
			layoutSEOCanonicalURLId);
	}

	/**
	 * Returns the layout seo canonical url matching the UUID and group.
	 *
	 * @param uuid the layout seo canonical url's UUID
	 * @param groupId the primary key of the group
	 * @return the matching layout seo canonical url
	 * @throws PortalException if a matching layout seo canonical url could not be found
	 */
	@Override
	public com.liferay.layout.seo.model.LayoutSEOCanonicalURL
			getLayoutSEOCanonicalURLByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutSEOCanonicalURLLocalService.
			getLayoutSEOCanonicalURLByUuidAndGroupId(uuid, groupId);
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
	@Override
	public java.util.List<com.liferay.layout.seo.model.LayoutSEOCanonicalURL>
		getLayoutSEOCanonicalURLs(int start, int end) {

		return _layoutSEOCanonicalURLLocalService.getLayoutSEOCanonicalURLs(
			start, end);
	}

	/**
	 * Returns all the layout seo canonical urls matching the UUID and company.
	 *
	 * @param uuid the UUID of the layout seo canonical urls
	 * @param companyId the primary key of the company
	 * @return the matching layout seo canonical urls, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.layout.seo.model.LayoutSEOCanonicalURL>
		getLayoutSEOCanonicalURLsByUuidAndCompanyId(
			String uuid, long companyId) {

		return _layoutSEOCanonicalURLLocalService.
			getLayoutSEOCanonicalURLsByUuidAndCompanyId(uuid, companyId);
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
	@Override
	public java.util.List<com.liferay.layout.seo.model.LayoutSEOCanonicalURL>
		getLayoutSEOCanonicalURLsByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.layout.seo.model.LayoutSEOCanonicalURL>
					orderByComparator) {

		return _layoutSEOCanonicalURLLocalService.
			getLayoutSEOCanonicalURLsByUuidAndCompanyId(
				uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of layout seo canonical urls.
	 *
	 * @return the number of layout seo canonical urls
	 */
	@Override
	public int getLayoutSEOCanonicalURLsCount() {
		return _layoutSEOCanonicalURLLocalService.
			getLayoutSEOCanonicalURLsCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _layoutSEOCanonicalURLLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutSEOCanonicalURLLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the layout seo canonical url in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param layoutSEOCanonicalURL the layout seo canonical url
	 * @return the layout seo canonical url that was updated
	 */
	@Override
	public com.liferay.layout.seo.model.LayoutSEOCanonicalURL
		updateLayoutSEOCanonicalURL(
			com.liferay.layout.seo.model.LayoutSEOCanonicalURL
				layoutSEOCanonicalURL) {

		return _layoutSEOCanonicalURLLocalService.updateLayoutSEOCanonicalURL(
			layoutSEOCanonicalURL);
	}

	@Override
	public com.liferay.layout.seo.model.LayoutSEOCanonicalURL
			updateLayoutSEOCanonicalURL(
				long userId, long groupId, boolean privateLayout, long layoutId,
				boolean enabled,
				java.util.Map<java.util.Locale, String> canonicalURLMap,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutSEOCanonicalURLLocalService.updateLayoutSEOCanonicalURL(
			userId, groupId, privateLayout, layoutId, enabled, canonicalURLMap,
			serviceContext);
	}

	@Override
	public LayoutSEOCanonicalURLLocalService getWrappedService() {
		return _layoutSEOCanonicalURLLocalService;
	}

	@Override
	public void setWrappedService(
		LayoutSEOCanonicalURLLocalService layoutSEOCanonicalURLLocalService) {

		_layoutSEOCanonicalURLLocalService = layoutSEOCanonicalURLLocalService;
	}

	private LayoutSEOCanonicalURLLocalService
		_layoutSEOCanonicalURLLocalService;

}