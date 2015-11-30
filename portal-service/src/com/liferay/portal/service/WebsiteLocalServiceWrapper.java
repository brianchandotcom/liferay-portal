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
 * Provides a wrapper for {@link WebsiteLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see WebsiteLocalService
 * @generated
 */
@ProviderType
public class WebsiteLocalServiceWrapper implements WebsiteLocalService,
	ServiceWrapper<WebsiteLocalService> {
	public WebsiteLocalServiceWrapper(WebsiteLocalService websiteLocalService) {
		_websiteLocalService = websiteLocalService;
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #addWebsite(long, String,
	long, String, int, boolean, ServiceContext)}
	*/
	@Deprecated
	@Override
	public com.liferay.portal.model.Website addWebsite(long userId,
		java.lang.String className, long classPK, java.lang.String url,
		long typeId, boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _websiteLocalService.addWebsite(userId, className, classPK, url,
			typeId, primary);
	}

	@Override
	public com.liferay.portal.model.Website addWebsite(long userId,
		java.lang.String className, long classPK, java.lang.String url,
		long typeId, boolean primary,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _websiteLocalService.addWebsite(userId, className, classPK, url,
			typeId, primary, serviceContext);
	}

	/**
	* Adds the website to the database. Also notifies the appropriate model listeners.
	*
	* @param website the website
	* @return the website that was added
	*/
	@Override
	public com.liferay.portal.model.Website addWebsite(
		com.liferay.portal.model.Website website) {
		return _websiteLocalService.addWebsite(website);
	}

	/**
	* Creates a new website with the primary key. Does not add the website to the database.
	*
	* @param websiteId the primary key for the new website
	* @return the new website
	*/
	@Override
	public com.liferay.portal.model.Website createWebsite(long websiteId) {
		return _websiteLocalService.createWebsite(websiteId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _websiteLocalService.deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the website from the database. Also notifies the appropriate model listeners.
	*
	* @param website the website
	* @return the website that was removed
	*/
	@Override
	public com.liferay.portal.model.Website deleteWebsite(
		com.liferay.portal.model.Website website) {
		return _websiteLocalService.deleteWebsite(website);
	}

	/**
	* Deletes the website with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param websiteId the primary key of the website
	* @return the website that was removed
	* @throws PortalException if a website with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.Website deleteWebsite(long websiteId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _websiteLocalService.deleteWebsite(websiteId);
	}

	@Override
	public void deleteWebsites(long companyId, java.lang.String className,
		long classPK) {
		_websiteLocalService.deleteWebsites(companyId, className, classPK);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _websiteLocalService.dynamicQuery();
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
		return _websiteLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.WebsiteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _websiteLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.WebsiteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _websiteLocalService.dynamicQuery(dynamicQuery, start, end,
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
		return _websiteLocalService.dynamicQueryCount(dynamicQuery);
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
		return _websiteLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.model.Website fetchWebsite(long websiteId) {
		return _websiteLocalService.fetchWebsite(websiteId);
	}

	/**
	* Returns the website with the matching UUID and company.
	*
	* @param uuid the website's UUID
	* @param companyId the primary key of the company
	* @return the matching website, or <code>null</code> if a matching website could not be found
	*/
	@Override
	public com.liferay.portal.model.Website fetchWebsiteByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _websiteLocalService.fetchWebsiteByUuidAndCompanyId(uuid,
			companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _websiteLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext) {
		return _websiteLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _websiteLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _websiteLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _websiteLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the website with the primary key.
	*
	* @param websiteId the primary key of the website
	* @return the website
	* @throws PortalException if a website with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.Website getWebsite(long websiteId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _websiteLocalService.getWebsite(websiteId);
	}

	/**
	* Returns the website with the matching UUID and company.
	*
	* @param uuid the website's UUID
	* @param companyId the primary key of the company
	* @return the matching website
	* @throws PortalException if a matching website could not be found
	*/
	@Override
	public com.liferay.portal.model.Website getWebsiteByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _websiteLocalService.getWebsiteByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public java.util.List<com.liferay.portal.model.Website> getWebsites() {
		return _websiteLocalService.getWebsites();
	}

	@Override
	public java.util.List<com.liferay.portal.model.Website> getWebsites(
		long companyId, java.lang.String className, long classPK) {
		return _websiteLocalService.getWebsites(companyId, className, classPK);
	}

	/**
	* Returns a range of all the websites.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.WebsiteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of websites
	* @param end the upper bound of the range of websites (not inclusive)
	* @return the range of websites
	*/
	@Override
	public java.util.List<com.liferay.portal.model.Website> getWebsites(
		int start, int end) {
		return _websiteLocalService.getWebsites(start, end);
	}

	/**
	* Returns the number of websites.
	*
	* @return the number of websites
	*/
	@Override
	public int getWebsitesCount() {
		return _websiteLocalService.getWebsitesCount();
	}

	/**
	* Updates the website in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param website the website
	* @return the website that was updated
	*/
	@Override
	public com.liferay.portal.model.Website updateWebsite(
		com.liferay.portal.model.Website website) {
		return _websiteLocalService.updateWebsite(website);
	}

	@Override
	public com.liferay.portal.model.Website updateWebsite(long websiteId,
		java.lang.String url, long typeId, boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _websiteLocalService.updateWebsite(websiteId, url, typeId,
			primary);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public WebsiteLocalService getWrappedWebsiteLocalService() {
		return _websiteLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedWebsiteLocalService(
		WebsiteLocalService websiteLocalService) {
		_websiteLocalService = websiteLocalService;
	}

	@Override
	public WebsiteLocalService getWrappedService() {
		return _websiteLocalService;
	}

	@Override
	public void setWrappedService(WebsiteLocalService websiteLocalService) {
		_websiteLocalService = websiteLocalService;
	}

	private WebsiteLocalService _websiteLocalService;
}