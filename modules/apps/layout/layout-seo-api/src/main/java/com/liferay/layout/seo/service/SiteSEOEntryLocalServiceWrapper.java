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

/**
 * Provides a wrapper for {@link SiteSEOEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see SiteSEOEntryLocalService
 * @generated
 */
public class SiteSEOEntryLocalServiceWrapper
	implements ServiceWrapper<SiteSEOEntryLocalService>,
			   SiteSEOEntryLocalService {

	public SiteSEOEntryLocalServiceWrapper(
		SiteSEOEntryLocalService siteSEOEntryLocalService) {

		_siteSEOEntryLocalService = siteSEOEntryLocalService;
	}

	/**
	 * Adds the site seo entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param siteSEOEntry the site seo entry
	 * @return the site seo entry that was added
	 */
	@Override
	public com.liferay.layout.seo.model.SiteSEOEntry addSiteSEOEntry(
		com.liferay.layout.seo.model.SiteSEOEntry siteSEOEntry) {

		return _siteSEOEntryLocalService.addSiteSEOEntry(siteSEOEntry);
	}

	/**
	 * Creates a new site seo entry with the primary key. Does not add the site seo entry to the database.
	 *
	 * @param siteSEOEntryId the primary key for the new site seo entry
	 * @return the new site seo entry
	 */
	@Override
	public com.liferay.layout.seo.model.SiteSEOEntry createSiteSEOEntry(
		long siteSEOEntryId) {

		return _siteSEOEntryLocalService.createSiteSEOEntry(siteSEOEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _siteSEOEntryLocalService.deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the site seo entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param siteSEOEntryId the primary key of the site seo entry
	 * @return the site seo entry that was removed
	 * @throws PortalException if a site seo entry with the primary key could not be found
	 */
	@Override
	public com.liferay.layout.seo.model.SiteSEOEntry deleteSiteSEOEntry(
			long siteSEOEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _siteSEOEntryLocalService.deleteSiteSEOEntry(siteSEOEntryId);
	}

	/**
	 * Deletes the site seo entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param siteSEOEntry the site seo entry
	 * @return the site seo entry that was removed
	 */
	@Override
	public com.liferay.layout.seo.model.SiteSEOEntry deleteSiteSEOEntry(
		com.liferay.layout.seo.model.SiteSEOEntry siteSEOEntry) {

		return _siteSEOEntryLocalService.deleteSiteSEOEntry(siteSEOEntry);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _siteSEOEntryLocalService.dynamicQuery();
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

		return _siteSEOEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.layout.seo.model.impl.SiteSEOEntryModelImpl</code>.
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

		return _siteSEOEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.layout.seo.model.impl.SiteSEOEntryModelImpl</code>.
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

		return _siteSEOEntryLocalService.dynamicQuery(
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

		return _siteSEOEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _siteSEOEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.layout.seo.model.SiteSEOEntry fetchSiteSEOEntry(
		long siteSEOEntryId) {

		return _siteSEOEntryLocalService.fetchSiteSEOEntry(siteSEOEntryId);
	}

	@Override
	public com.liferay.layout.seo.model.SiteSEOEntry fetchSiteSEOEntryByGroupId(
		long groupId) {

		return _siteSEOEntryLocalService.fetchSiteSEOEntryByGroupId(groupId);
	}

	/**
	 * Returns the site seo entry matching the UUID and group.
	 *
	 * @param uuid the site seo entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching site seo entry, or <code>null</code> if a matching site seo entry could not be found
	 */
	@Override
	public com.liferay.layout.seo.model.SiteSEOEntry
		fetchSiteSEOEntryByUuidAndGroupId(String uuid, long groupId) {

		return _siteSEOEntryLocalService.fetchSiteSEOEntryByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _siteSEOEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _siteSEOEntryLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _siteSEOEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _siteSEOEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _siteSEOEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns a range of all the site seo entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.layout.seo.model.impl.SiteSEOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of site seo entries
	 * @param end the upper bound of the range of site seo entries (not inclusive)
	 * @return the range of site seo entries
	 */
	@Override
	public java.util.List<com.liferay.layout.seo.model.SiteSEOEntry>
		getSiteSEOEntries(int start, int end) {

		return _siteSEOEntryLocalService.getSiteSEOEntries(start, end);
	}

	/**
	 * Returns all the site seo entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the site seo entries
	 * @param companyId the primary key of the company
	 * @return the matching site seo entries, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.layout.seo.model.SiteSEOEntry>
		getSiteSEOEntriesByUuidAndCompanyId(String uuid, long companyId) {

		return _siteSEOEntryLocalService.getSiteSEOEntriesByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of site seo entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the site seo entries
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of site seo entries
	 * @param end the upper bound of the range of site seo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching site seo entries, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.layout.seo.model.SiteSEOEntry>
		getSiteSEOEntriesByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.layout.seo.model.SiteSEOEntry> orderByComparator) {

		return _siteSEOEntryLocalService.getSiteSEOEntriesByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of site seo entries.
	 *
	 * @return the number of site seo entries
	 */
	@Override
	public int getSiteSEOEntriesCount() {
		return _siteSEOEntryLocalService.getSiteSEOEntriesCount();
	}

	/**
	 * Returns the site seo entry with the primary key.
	 *
	 * @param siteSEOEntryId the primary key of the site seo entry
	 * @return the site seo entry
	 * @throws PortalException if a site seo entry with the primary key could not be found
	 */
	@Override
	public com.liferay.layout.seo.model.SiteSEOEntry getSiteSEOEntry(
			long siteSEOEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _siteSEOEntryLocalService.getSiteSEOEntry(siteSEOEntryId);
	}

	/**
	 * Returns the site seo entry matching the UUID and group.
	 *
	 * @param uuid the site seo entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching site seo entry
	 * @throws PortalException if a matching site seo entry could not be found
	 */
	@Override
	public com.liferay.layout.seo.model.SiteSEOEntry
			getSiteSEOEntryByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _siteSEOEntryLocalService.getSiteSEOEntryByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public com.liferay.layout.seo.model.SiteSEOEntry updateSiteSEOEntry(
			long userId, long groupId, long openGraphImageFileEntryId,
			boolean openSiteGraphEnabled,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _siteSEOEntryLocalService.updateSiteSEOEntry(
			userId, groupId, openGraphImageFileEntryId, openSiteGraphEnabled,
			serviceContext);
	}

	/**
	 * Updates the site seo entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param siteSEOEntry the site seo entry
	 * @return the site seo entry that was updated
	 */
	@Override
	public com.liferay.layout.seo.model.SiteSEOEntry updateSiteSEOEntry(
		com.liferay.layout.seo.model.SiteSEOEntry siteSEOEntry) {

		return _siteSEOEntryLocalService.updateSiteSEOEntry(siteSEOEntry);
	}

	@Override
	public SiteSEOEntryLocalService getWrappedService() {
		return _siteSEOEntryLocalService;
	}

	@Override
	public void setWrappedService(
		SiteSEOEntryLocalService siteSEOEntryLocalService) {

		_siteSEOEntryLocalService = siteSEOEntryLocalService;
	}

	private SiteSEOEntryLocalService _siteSEOEntryLocalService;

}