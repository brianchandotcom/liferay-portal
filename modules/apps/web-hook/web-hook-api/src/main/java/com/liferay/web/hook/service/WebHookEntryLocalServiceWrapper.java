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

package com.liferay.web.hook.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link WebHookEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see WebHookEntryLocalService
 * @generated
 */
public class WebHookEntryLocalServiceWrapper
	implements ServiceWrapper<WebHookEntryLocalService>,
			   WebHookEntryLocalService {

	public WebHookEntryLocalServiceWrapper(
		WebHookEntryLocalService webHookEntryLocalService) {

		_webHookEntryLocalService = webHookEntryLocalService;
	}

	@Override
	public com.liferay.web.hook.model.WebHookEntry addWebHookEntry(
			long userId, java.util.Map<java.util.Locale, String> nameMap,
			String destination, String url,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _webHookEntryLocalService.addWebHookEntry(
			userId, nameMap, destination, url, serviceContext);
	}

	/**
	 * Adds the web hook entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect WebHookEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param webHookEntry the web hook entry
	 * @return the web hook entry that was added
	 */
	@Override
	public com.liferay.web.hook.model.WebHookEntry addWebHookEntry(
		com.liferay.web.hook.model.WebHookEntry webHookEntry) {

		return _webHookEntryLocalService.addWebHookEntry(webHookEntry);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _webHookEntryLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Creates a new web hook entry with the primary key. Does not add the web hook entry to the database.
	 *
	 * @param webHookEntryId the primary key for the new web hook entry
	 * @return the new web hook entry
	 */
	@Override
	public com.liferay.web.hook.model.WebHookEntry createWebHookEntry(
		long webHookEntryId) {

		return _webHookEntryLocalService.createWebHookEntry(webHookEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _webHookEntryLocalService.deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the web hook entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect WebHookEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param webHookEntryId the primary key of the web hook entry
	 * @return the web hook entry that was removed
	 * @throws PortalException if a web hook entry with the primary key could not be found
	 */
	@Override
	public com.liferay.web.hook.model.WebHookEntry deleteWebHookEntry(
			long webHookEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _webHookEntryLocalService.deleteWebHookEntry(webHookEntryId);
	}

	/**
	 * Deletes the web hook entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect WebHookEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param webHookEntry the web hook entry
	 * @return the web hook entry that was removed
	 */
	@Override
	public com.liferay.web.hook.model.WebHookEntry deleteWebHookEntry(
		com.liferay.web.hook.model.WebHookEntry webHookEntry) {

		return _webHookEntryLocalService.deleteWebHookEntry(webHookEntry);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _webHookEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _webHookEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _webHookEntryLocalService.dynamicQuery();
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

		return _webHookEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.web.hook.model.impl.WebHookEntryModelImpl</code>.
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

		return _webHookEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.web.hook.model.impl.WebHookEntryModelImpl</code>.
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

		return _webHookEntryLocalService.dynamicQuery(
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

		return _webHookEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _webHookEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.web.hook.model.WebHookEntry fetchWebHookEntry(
		long webHookEntryId) {

		return _webHookEntryLocalService.fetchWebHookEntry(webHookEntryId);
	}

	/**
	 * Returns the web hook entry with the matching UUID and company.
	 *
	 * @param uuid the web hook entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching web hook entry, or <code>null</code> if a matching web hook entry could not be found
	 */
	@Override
	public com.liferay.web.hook.model.WebHookEntry
		fetchWebHookEntryByUuidAndCompanyId(String uuid, long companyId) {

		return _webHookEntryLocalService.fetchWebHookEntryByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _webHookEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _webHookEntryLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _webHookEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _webHookEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _webHookEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns a range of all the web hook entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.web.hook.model.impl.WebHookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of web hook entries
	 * @param end the upper bound of the range of web hook entries (not inclusive)
	 * @return the range of web hook entries
	 */
	@Override
	public java.util.List<com.liferay.web.hook.model.WebHookEntry>
		getWebHookEntries(int start, int end) {

		return _webHookEntryLocalService.getWebHookEntries(start, end);
	}

	/**
	 * Returns the number of web hook entries.
	 *
	 * @return the number of web hook entries
	 */
	@Override
	public int getWebHookEntriesCount() {
		return _webHookEntryLocalService.getWebHookEntriesCount();
	}

	/**
	 * Returns the web hook entry with the primary key.
	 *
	 * @param webHookEntryId the primary key of the web hook entry
	 * @return the web hook entry
	 * @throws PortalException if a web hook entry with the primary key could not be found
	 */
	@Override
	public com.liferay.web.hook.model.WebHookEntry getWebHookEntry(
			long webHookEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _webHookEntryLocalService.getWebHookEntry(webHookEntryId);
	}

	/**
	 * Returns the web hook entry with the matching UUID and company.
	 *
	 * @param uuid the web hook entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching web hook entry
	 * @throws PortalException if a matching web hook entry could not be found
	 */
	@Override
	public com.liferay.web.hook.model.WebHookEntry
			getWebHookEntryByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _webHookEntryLocalService.getWebHookEntryByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public java.util.List<com.liferay.web.hook.model.WebHookEntry> search(
			long companyId, String keywords, int start, int end,
			com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _webHookEntryLocalService.search(
			companyId, keywords, start, end, sort);
	}

	@Override
	public int searchCount(long companyId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _webHookEntryLocalService.searchCount(companyId, keywords);
	}

	@Override
	public com.liferay.web.hook.model.WebHookEntry updateWebHookEntry(
			long webHookEntryId,
			java.util.Map<java.util.Locale, String> nameMap, String destination,
			String url,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _webHookEntryLocalService.updateWebHookEntry(
			webHookEntryId, nameMap, destination, url, serviceContext);
	}

	/**
	 * Updates the web hook entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect WebHookEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param webHookEntry the web hook entry
	 * @return the web hook entry that was updated
	 */
	@Override
	public com.liferay.web.hook.model.WebHookEntry updateWebHookEntry(
		com.liferay.web.hook.model.WebHookEntry webHookEntry) {

		return _webHookEntryLocalService.updateWebHookEntry(webHookEntry);
	}

	@Override
	public WebHookEntryLocalService getWrappedService() {
		return _webHookEntryLocalService;
	}

	@Override
	public void setWrappedService(
		WebHookEntryLocalService webHookEntryLocalService) {

		_webHookEntryLocalService = webHookEntryLocalService;
	}

	private WebHookEntryLocalService _webHookEntryLocalService;

}