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

package com.liferay.portal.service;

import aQute.bnd.annotation.ProviderType;

/**
 * Provides a wrapper for {@link LayoutFriendlyURLLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutFriendlyURLLocalService
 * @generated
 */
@ProviderType
public class LayoutFriendlyURLLocalServiceWrapper
	implements LayoutFriendlyURLLocalService,
		ServiceWrapper<LayoutFriendlyURLLocalService> {
	public LayoutFriendlyURLLocalServiceWrapper(
		LayoutFriendlyURLLocalService layoutFriendlyURLLocalService) {
		_layoutFriendlyURLLocalService = layoutFriendlyURLLocalService;
	}

	/**
	* Adds the layout friendly u r l to the database. Also notifies the appropriate model listeners.
	*
	* @param layoutFriendlyURL the layout friendly u r l
	* @return the layout friendly u r l that was added
	*/
	@Override
	public com.liferay.portal.model.LayoutFriendlyURL addLayoutFriendlyURL(
		com.liferay.portal.model.LayoutFriendlyURL layoutFriendlyURL) {
		return _layoutFriendlyURLLocalService.addLayoutFriendlyURL(layoutFriendlyURL);
	}

	@Override
	public com.liferay.portal.model.LayoutFriendlyURL addLayoutFriendlyURL(
		long userId, long companyId, long groupId, long plid,
		boolean privateLayout, java.lang.String friendlyURL,
		java.lang.String languageId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutFriendlyURLLocalService.addLayoutFriendlyURL(userId,
			companyId, groupId, plid, privateLayout, friendlyURL, languageId,
			serviceContext);
	}

	@Override
	public java.util.List<com.liferay.portal.model.LayoutFriendlyURL> addLayoutFriendlyURLs(
		long userId, long companyId, long groupId, long plid,
		boolean privateLayout,
		java.util.Map<java.util.Locale, java.lang.String> friendlyURLMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutFriendlyURLLocalService.addLayoutFriendlyURLs(userId,
			companyId, groupId, plid, privateLayout, friendlyURLMap,
			serviceContext);
	}

	/**
	* Creates a new layout friendly u r l with the primary key. Does not add the layout friendly u r l to the database.
	*
	* @param layoutFriendlyURLId the primary key for the new layout friendly u r l
	* @return the new layout friendly u r l
	*/
	@Override
	public com.liferay.portal.model.LayoutFriendlyURL createLayoutFriendlyURL(
		long layoutFriendlyURLId) {
		return _layoutFriendlyURLLocalService.createLayoutFriendlyURL(layoutFriendlyURLId);
	}

	/**
	* Deletes the layout friendly u r l from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutFriendlyURL the layout friendly u r l
	* @return the layout friendly u r l that was removed
	*/
	@Override
	public com.liferay.portal.model.LayoutFriendlyURL deleteLayoutFriendlyURL(
		com.liferay.portal.model.LayoutFriendlyURL layoutFriendlyURL) {
		return _layoutFriendlyURLLocalService.deleteLayoutFriendlyURL(layoutFriendlyURL);
	}

	/**
	* Deletes the layout friendly u r l with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutFriendlyURLId the primary key of the layout friendly u r l
	* @return the layout friendly u r l that was removed
	* @throws PortalException if a layout friendly u r l with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.LayoutFriendlyURL deleteLayoutFriendlyURL(
		long layoutFriendlyURLId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutFriendlyURLLocalService.deleteLayoutFriendlyURL(layoutFriendlyURLId);
	}

	@Override
	public void deleteLayoutFriendlyURL(long plid, java.lang.String languageId) {
		_layoutFriendlyURLLocalService.deleteLayoutFriendlyURL(plid, languageId);
	}

	@Override
	public void deleteLayoutFriendlyURLs(long plid) {
		_layoutFriendlyURLLocalService.deleteLayoutFriendlyURLs(plid);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutFriendlyURLLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _layoutFriendlyURLLocalService.dynamicQuery();
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
		return _layoutFriendlyURLLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _layoutFriendlyURLLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _layoutFriendlyURLLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
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
		return _layoutFriendlyURLLocalService.dynamicQueryCount(dynamicQuery);
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
		return _layoutFriendlyURLLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.portal.model.LayoutFriendlyURL fetchFirstLayoutFriendlyURL(
		long groupId, boolean privateLayout, java.lang.String friendlyURL) {
		return _layoutFriendlyURLLocalService.fetchFirstLayoutFriendlyURL(groupId,
			privateLayout, friendlyURL);
	}

	@Override
	public com.liferay.portal.model.LayoutFriendlyURL fetchLayoutFriendlyURL(
		long groupId, boolean privateLayout, java.lang.String friendlyURL,
		java.lang.String languageId) {
		return _layoutFriendlyURLLocalService.fetchLayoutFriendlyURL(groupId,
			privateLayout, friendlyURL, languageId);
	}

	@Override
	public com.liferay.portal.model.LayoutFriendlyURL fetchLayoutFriendlyURL(
		long layoutFriendlyURLId) {
		return _layoutFriendlyURLLocalService.fetchLayoutFriendlyURL(layoutFriendlyURLId);
	}

	@Override
	public com.liferay.portal.model.LayoutFriendlyURL fetchLayoutFriendlyURL(
		long plid, java.lang.String languageId) {
		return _layoutFriendlyURLLocalService.fetchLayoutFriendlyURL(plid,
			languageId);
	}

	@Override
	public com.liferay.portal.model.LayoutFriendlyURL fetchLayoutFriendlyURL(
		long plid, java.lang.String languageId, boolean useDefault) {
		return _layoutFriendlyURLLocalService.fetchLayoutFriendlyURL(plid,
			languageId, useDefault);
	}

	/**
	* Returns the layout friendly u r l matching the UUID and group.
	*
	* @param uuid the layout friendly u r l's UUID
	* @param groupId the primary key of the group
	* @return the matching layout friendly u r l, or <code>null</code> if a matching layout friendly u r l could not be found
	*/
	@Override
	public com.liferay.portal.model.LayoutFriendlyURL fetchLayoutFriendlyURLByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _layoutFriendlyURLLocalService.fetchLayoutFriendlyURLByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _layoutFriendlyURLLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext) {
		return _layoutFriendlyURLLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _layoutFriendlyURLLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the layout friendly u r l with the primary key.
	*
	* @param layoutFriendlyURLId the primary key of the layout friendly u r l
	* @return the layout friendly u r l
	* @throws PortalException if a layout friendly u r l with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.LayoutFriendlyURL getLayoutFriendlyURL(
		long layoutFriendlyURLId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutFriendlyURLLocalService.getLayoutFriendlyURL(layoutFriendlyURLId);
	}

	@Override
	public com.liferay.portal.model.LayoutFriendlyURL getLayoutFriendlyURL(
		long plid, java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutFriendlyURLLocalService.getLayoutFriendlyURL(plid,
			languageId);
	}

	@Override
	public com.liferay.portal.model.LayoutFriendlyURL getLayoutFriendlyURL(
		long plid, java.lang.String languageId, boolean useDefault)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutFriendlyURLLocalService.getLayoutFriendlyURL(plid,
			languageId, useDefault);
	}

	/**
	* Returns the layout friendly u r l matching the UUID and group.
	*
	* @param uuid the layout friendly u r l's UUID
	* @param groupId the primary key of the group
	* @return the matching layout friendly u r l
	* @throws PortalException if a matching layout friendly u r l could not be found
	*/
	@Override
	public com.liferay.portal.model.LayoutFriendlyURL getLayoutFriendlyURLByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutFriendlyURLLocalService.getLayoutFriendlyURLByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public java.util.List<com.liferay.portal.model.LayoutFriendlyURL> getLayoutFriendlyURLs(
		long plid) {
		return _layoutFriendlyURLLocalService.getLayoutFriendlyURLs(plid);
	}

	@Override
	public java.util.List<com.liferay.portal.model.LayoutFriendlyURL> getLayoutFriendlyURLs(
		long plid, java.lang.String friendlyURL, int start, int end) {
		return _layoutFriendlyURLLocalService.getLayoutFriendlyURLs(plid,
			friendlyURL, start, end);
	}

	/**
	* Returns a range of all the layout friendly u r ls.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout friendly u r ls
	* @param end the upper bound of the range of layout friendly u r ls (not inclusive)
	* @return the range of layout friendly u r ls
	*/
	@Override
	public java.util.List<com.liferay.portal.model.LayoutFriendlyURL> getLayoutFriendlyURLs(
		int start, int end) {
		return _layoutFriendlyURLLocalService.getLayoutFriendlyURLs(start, end);
	}

	/**
	* Returns all the layout friendly u r ls matching the UUID and company.
	*
	* @param uuid the UUID of the layout friendly u r ls
	* @param companyId the primary key of the company
	* @return the matching layout friendly u r ls, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.portal.model.LayoutFriendlyURL> getLayoutFriendlyURLsByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _layoutFriendlyURLLocalService.getLayoutFriendlyURLsByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of layout friendly u r ls matching the UUID and company.
	*
	* @param uuid the UUID of the layout friendly u r ls
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of layout friendly u r ls
	* @param end the upper bound of the range of layout friendly u r ls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching layout friendly u r ls, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.portal.model.LayoutFriendlyURL> getLayoutFriendlyURLsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.LayoutFriendlyURL> orderByComparator) {
		return _layoutFriendlyURLLocalService.getLayoutFriendlyURLsByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of layout friendly u r ls.
	*
	* @return the number of layout friendly u r ls
	*/
	@Override
	public int getLayoutFriendlyURLsCount() {
		return _layoutFriendlyURLLocalService.getLayoutFriendlyURLsCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _layoutFriendlyURLLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutFriendlyURLLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the layout friendly u r l in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param layoutFriendlyURL the layout friendly u r l
	* @return the layout friendly u r l that was updated
	*/
	@Override
	public com.liferay.portal.model.LayoutFriendlyURL updateLayoutFriendlyURL(
		com.liferay.portal.model.LayoutFriendlyURL layoutFriendlyURL) {
		return _layoutFriendlyURLLocalService.updateLayoutFriendlyURL(layoutFriendlyURL);
	}

	@Override
	public com.liferay.portal.model.LayoutFriendlyURL updateLayoutFriendlyURL(
		long userId, long companyId, long groupId, long plid,
		boolean privateLayout, java.lang.String friendlyURL,
		java.lang.String languageId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutFriendlyURLLocalService.updateLayoutFriendlyURL(userId,
			companyId, groupId, plid, privateLayout, friendlyURL, languageId,
			serviceContext);
	}

	@Override
	public java.util.List<com.liferay.portal.model.LayoutFriendlyURL> updateLayoutFriendlyURLs(
		long userId, long companyId, long groupId, long plid,
		boolean privateLayout,
		java.util.Map<java.util.Locale, java.lang.String> friendlyURLMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutFriendlyURLLocalService.updateLayoutFriendlyURLs(userId,
			companyId, groupId, plid, privateLayout, friendlyURLMap,
			serviceContext);
	}

	@Override
	public LayoutFriendlyURLLocalService getWrappedService() {
		return _layoutFriendlyURLLocalService;
	}

	@Override
	public void setWrappedService(
		LayoutFriendlyURLLocalService layoutFriendlyURLLocalService) {
		_layoutFriendlyURLLocalService = layoutFriendlyURLLocalService;
	}

	private LayoutFriendlyURLLocalService _layoutFriendlyURLLocalService;
}