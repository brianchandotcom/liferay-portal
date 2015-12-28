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
 * Provides a wrapper for {@link WebDAVPropsLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see WebDAVPropsLocalService
 * @generated
 */
@ProviderType
public class WebDAVPropsLocalServiceWrapper implements WebDAVPropsLocalService,
	ServiceWrapper<WebDAVPropsLocalService> {
	public WebDAVPropsLocalServiceWrapper(
		WebDAVPropsLocalService webDAVPropsLocalService) {
		_webDAVPropsLocalService = webDAVPropsLocalService;
	}

	/**
	* Adds the web d a v props to the database. Also notifies the appropriate model listeners.
	*
	* @param webDAVProps the web d a v props
	* @return the web d a v props that was added
	*/
	@Override
	public com.liferay.portal.model.WebDAVProps addWebDAVProps(
		com.liferay.portal.model.WebDAVProps webDAVProps) {
		return _webDAVPropsLocalService.addWebDAVProps(webDAVProps);
	}

	/**
	* Creates a new web d a v props with the primary key. Does not add the web d a v props to the database.
	*
	* @param webDavPropsId the primary key for the new web d a v props
	* @return the new web d a v props
	*/
	@Override
	public com.liferay.portal.model.WebDAVProps createWebDAVProps(
		long webDavPropsId) {
		return _webDAVPropsLocalService.createWebDAVProps(webDavPropsId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _webDAVPropsLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public void deleteWebDAVProps(java.lang.String className, long classPK) {
		_webDAVPropsLocalService.deleteWebDAVProps(className, classPK);
	}

	/**
	* Deletes the web d a v props from the database. Also notifies the appropriate model listeners.
	*
	* @param webDAVProps the web d a v props
	* @return the web d a v props that was removed
	*/
	@Override
	public com.liferay.portal.model.WebDAVProps deleteWebDAVProps(
		com.liferay.portal.model.WebDAVProps webDAVProps) {
		return _webDAVPropsLocalService.deleteWebDAVProps(webDAVProps);
	}

	/**
	* Deletes the web d a v props with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param webDavPropsId the primary key of the web d a v props
	* @return the web d a v props that was removed
	* @throws PortalException if a web d a v props with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.WebDAVProps deleteWebDAVProps(
		long webDavPropsId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _webDAVPropsLocalService.deleteWebDAVProps(webDavPropsId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _webDAVPropsLocalService.dynamicQuery();
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
		return _webDAVPropsLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.WebDAVPropsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _webDAVPropsLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.WebDAVPropsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _webDAVPropsLocalService.dynamicQuery(dynamicQuery, start, end,
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
		return _webDAVPropsLocalService.dynamicQueryCount(dynamicQuery);
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
		return _webDAVPropsLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.portal.model.WebDAVProps fetchWebDAVProps(
		long webDavPropsId) {
		return _webDAVPropsLocalService.fetchWebDAVProps(webDavPropsId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _webDAVPropsLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _webDAVPropsLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _webDAVPropsLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _webDAVPropsLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.model.WebDAVProps getWebDAVProps(long companyId,
		java.lang.String className, long classPK) {
		return _webDAVPropsLocalService.getWebDAVProps(companyId, className,
			classPK);
	}

	/**
	* Returns the web d a v props with the primary key.
	*
	* @param webDavPropsId the primary key of the web d a v props
	* @return the web d a v props
	* @throws PortalException if a web d a v props with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.WebDAVProps getWebDAVProps(
		long webDavPropsId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _webDAVPropsLocalService.getWebDAVProps(webDavPropsId);
	}

	/**
	* Returns a range of all the web d a v propses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.WebDAVPropsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of web d a v propses
	* @param end the upper bound of the range of web d a v propses (not inclusive)
	* @return the range of web d a v propses
	*/
	@Override
	public java.util.List<com.liferay.portal.model.WebDAVProps> getWebDAVPropses(
		int start, int end) {
		return _webDAVPropsLocalService.getWebDAVPropses(start, end);
	}

	/**
	* Returns the number of web d a v propses.
	*
	* @return the number of web d a v propses
	*/
	@Override
	public int getWebDAVPropsesCount() {
		return _webDAVPropsLocalService.getWebDAVPropsesCount();
	}

	@Override
	public void storeWebDAVProps(
		com.liferay.portal.model.WebDAVProps webDavProps)
		throws com.liferay.portal.kernel.exception.PortalException {
		_webDAVPropsLocalService.storeWebDAVProps(webDavProps);
	}

	/**
	* Updates the web d a v props in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param webDAVProps the web d a v props
	* @return the web d a v props that was updated
	*/
	@Override
	public com.liferay.portal.model.WebDAVProps updateWebDAVProps(
		com.liferay.portal.model.WebDAVProps webDAVProps) {
		return _webDAVPropsLocalService.updateWebDAVProps(webDAVProps);
	}

	@Override
	public WebDAVPropsLocalService getWrappedService() {
		return _webDAVPropsLocalService;
	}

	@Override
	public void setWrappedService(
		WebDAVPropsLocalService webDAVPropsLocalService) {
		_webDAVPropsLocalService = webDAVPropsLocalService;
	}

	private WebDAVPropsLocalService _webDAVPropsLocalService;
}