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

package com.liferay.site.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SiteFriendlyURLLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see SiteFriendlyURLLocalService
 * @generated
 */
public class SiteFriendlyURLLocalServiceWrapper
	implements ServiceWrapper<SiteFriendlyURLLocalService>,
			   SiteFriendlyURLLocalService {

	public SiteFriendlyURLLocalServiceWrapper(
		SiteFriendlyURLLocalService siteFriendlyURLLocalService) {

		_siteFriendlyURLLocalService = siteFriendlyURLLocalService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SiteFriendlyURLLocalServiceUtil} to access the site friendly url local service. Add custom service methods to <code>com.liferay.site.service.impl.SiteFriendlyURLLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.liferay.site.model.SiteFriendlyURL addSiteFriendlyURL(
			long userId, long companyId, long groupId, String friendlyURL,
			String languageId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _siteFriendlyURLLocalService.addSiteFriendlyURL(
			userId, companyId, groupId, friendlyURL, languageId,
			serviceContext);
	}

	/**
	 * Adds the site friendly url to the database. Also notifies the appropriate model listeners.
	 *
	 * @param siteFriendlyURL the site friendly url
	 * @return the site friendly url that was added
	 */
	@Override
	public com.liferay.site.model.SiteFriendlyURL addSiteFriendlyURL(
		com.liferay.site.model.SiteFriendlyURL siteFriendlyURL) {

		return _siteFriendlyURLLocalService.addSiteFriendlyURL(siteFriendlyURL);
	}

	@Override
	public java.util.List<com.liferay.site.model.SiteFriendlyURL>
			addSiteFriendlyURLs(
				long userId, long companyId, long groupId,
				java.util.Map<java.util.Locale, String> friendlyURLMap,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _siteFriendlyURLLocalService.addSiteFriendlyURLs(
			userId, companyId, groupId, friendlyURLMap, serviceContext);
	}

	/**
	 * Creates a new site friendly url with the primary key. Does not add the site friendly url to the database.
	 *
	 * @param siteFriendlyURLId the primary key for the new site friendly url
	 * @return the new site friendly url
	 */
	@Override
	public com.liferay.site.model.SiteFriendlyURL createSiteFriendlyURL(
		long siteFriendlyURLId) {

		return _siteFriendlyURLLocalService.createSiteFriendlyURL(
			siteFriendlyURLId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _siteFriendlyURLLocalService.deletePersistedModel(
			persistedModel);
	}

	/**
	 * Deletes the site friendly url with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param siteFriendlyURLId the primary key of the site friendly url
	 * @return the site friendly url that was removed
	 * @throws PortalException if a site friendly url with the primary key could not be found
	 */
	@Override
	public com.liferay.site.model.SiteFriendlyURL deleteSiteFriendlyURL(
			long siteFriendlyURLId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _siteFriendlyURLLocalService.deleteSiteFriendlyURL(
			siteFriendlyURLId);
	}

	@Override
	public com.liferay.site.model.SiteFriendlyURL deleteSiteFriendlyURL(
			long companyId, long groupId, String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _siteFriendlyURLLocalService.deleteSiteFriendlyURL(
			companyId, groupId, languageId);
	}

	/**
	 * Deletes the site friendly url from the database. Also notifies the appropriate model listeners.
	 *
	 * @param siteFriendlyURL the site friendly url
	 * @return the site friendly url that was removed
	 */
	@Override
	public com.liferay.site.model.SiteFriendlyURL deleteSiteFriendlyURL(
		com.liferay.site.model.SiteFriendlyURL siteFriendlyURL) {

		return _siteFriendlyURLLocalService.deleteSiteFriendlyURL(
			siteFriendlyURL);
	}

	@Override
	public void deleteSiteFriendlyURLs(long companyId, long groupId) {
		_siteFriendlyURLLocalService.deleteSiteFriendlyURLs(companyId, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _siteFriendlyURLLocalService.dynamicQuery();
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

		return _siteFriendlyURLLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.site.model.impl.SiteFriendlyURLModelImpl</code>.
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

		return _siteFriendlyURLLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.site.model.impl.SiteFriendlyURLModelImpl</code>.
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

		return _siteFriendlyURLLocalService.dynamicQuery(
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

		return _siteFriendlyURLLocalService.dynamicQueryCount(dynamicQuery);
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

		return _siteFriendlyURLLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.site.model.SiteFriendlyURL fetchSiteFriendlyURL(
		long siteFriendlyURLId) {

		return _siteFriendlyURLLocalService.fetchSiteFriendlyURL(
			siteFriendlyURLId);
	}

	@Override
	public com.liferay.site.model.SiteFriendlyURL fetchSiteFriendlyURL(
		long companyId, long groupId, String languageId) {

		return _siteFriendlyURLLocalService.fetchSiteFriendlyURL(
			companyId, groupId, languageId);
	}

	@Override
	public com.liferay.site.model.SiteFriendlyURL
		fetchSiteFriendlyURLByFriendlyURL(long companyId, String friendlyURL) {

		return _siteFriendlyURLLocalService.fetchSiteFriendlyURLByFriendlyURL(
			companyId, friendlyURL);
	}

	/**
	 * Returns the site friendly url matching the UUID and group.
	 *
	 * @param uuid the site friendly url's UUID
	 * @param groupId the primary key of the group
	 * @return the matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	@Override
	public com.liferay.site.model.SiteFriendlyURL
		fetchSiteFriendlyURLByUuidAndGroupId(String uuid, long groupId) {

		return _siteFriendlyURLLocalService.
			fetchSiteFriendlyURLByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _siteFriendlyURLLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _siteFriendlyURLLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _siteFriendlyURLLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _siteFriendlyURLLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _siteFriendlyURLLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the site friendly url with the primary key.
	 *
	 * @param siteFriendlyURLId the primary key of the site friendly url
	 * @return the site friendly url
	 * @throws PortalException if a site friendly url with the primary key could not be found
	 */
	@Override
	public com.liferay.site.model.SiteFriendlyURL getSiteFriendlyURL(
			long siteFriendlyURLId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _siteFriendlyURLLocalService.getSiteFriendlyURL(
			siteFriendlyURLId);
	}

	/**
	 * Returns the site friendly url matching the UUID and group.
	 *
	 * @param uuid the site friendly url's UUID
	 * @param groupId the primary key of the group
	 * @return the matching site friendly url
	 * @throws PortalException if a matching site friendly url could not be found
	 */
	@Override
	public com.liferay.site.model.SiteFriendlyURL
			getSiteFriendlyURLByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _siteFriendlyURLLocalService.getSiteFriendlyURLByUuidAndGroupId(
			uuid, groupId);
	}

	/**
	 * Returns a range of all the site friendly urls.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.site.model.impl.SiteFriendlyURLModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @return the range of site friendly urls
	 */
	@Override
	public java.util.List<com.liferay.site.model.SiteFriendlyURL>
		getSiteFriendlyURLs(int start, int end) {

		return _siteFriendlyURLLocalService.getSiteFriendlyURLs(start, end);
	}

	@Override
	public java.util.List<com.liferay.site.model.SiteFriendlyURL>
		getSiteFriendlyURLs(long companyId, long groupId) {

		return _siteFriendlyURLLocalService.getSiteFriendlyURLs(
			companyId, groupId);
	}

	/**
	 * Returns all the site friendly urls matching the UUID and company.
	 *
	 * @param uuid the UUID of the site friendly urls
	 * @param companyId the primary key of the company
	 * @return the matching site friendly urls, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.site.model.SiteFriendlyURL>
		getSiteFriendlyURLsByUuidAndCompanyId(String uuid, long companyId) {

		return _siteFriendlyURLLocalService.
			getSiteFriendlyURLsByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of site friendly urls matching the UUID and company.
	 *
	 * @param uuid the UUID of the site friendly urls
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching site friendly urls, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.site.model.SiteFriendlyURL>
		getSiteFriendlyURLsByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.site.model.SiteFriendlyURL> orderByComparator) {

		return _siteFriendlyURLLocalService.
			getSiteFriendlyURLsByUuidAndCompanyId(
				uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of site friendly urls.
	 *
	 * @return the number of site friendly urls
	 */
	@Override
	public int getSiteFriendlyURLsCount() {
		return _siteFriendlyURLLocalService.getSiteFriendlyURLsCount();
	}

	@Override
	public com.liferay.site.model.SiteFriendlyURL updateSiteFriendlyURL(
			long userId, long companyId, long groupId, String friendlyURL,
			String languageId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _siteFriendlyURLLocalService.updateSiteFriendlyURL(
			userId, companyId, groupId, friendlyURL, languageId,
			serviceContext);
	}

	/**
	 * Updates the site friendly url in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param siteFriendlyURL the site friendly url
	 * @return the site friendly url that was updated
	 */
	@Override
	public com.liferay.site.model.SiteFriendlyURL updateSiteFriendlyURL(
		com.liferay.site.model.SiteFriendlyURL siteFriendlyURL) {

		return _siteFriendlyURLLocalService.updateSiteFriendlyURL(
			siteFriendlyURL);
	}

	@Override
	public java.util.List<com.liferay.site.model.SiteFriendlyURL>
			updateSiteFriendlyURLs(
				long userId, long companyId, long groupId,
				java.util.Map<java.util.Locale, String> friendlyURLMap,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _siteFriendlyURLLocalService.updateSiteFriendlyURLs(
			userId, companyId, groupId, friendlyURLMap, serviceContext);
	}

	@Override
	public SiteFriendlyURLLocalService getWrappedService() {
		return _siteFriendlyURLLocalService;
	}

	@Override
	public void setWrappedService(
		SiteFriendlyURLLocalService siteFriendlyURLLocalService) {

		_siteFriendlyURLLocalService = siteFriendlyURLLocalService;
	}

	private SiteFriendlyURLLocalService _siteFriendlyURLLocalService;

}