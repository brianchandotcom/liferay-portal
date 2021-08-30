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

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.web.hook.model.WebHookEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * Provides the local service utility for WebHookEntry. This utility wraps
 * <code>com.liferay.web.hook.service.impl.WebHookEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see WebHookEntryLocalService
 * @generated
 */
public class WebHookEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.web.hook.service.impl.WebHookEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static WebHookEntry addWebHookEntry(
			long userId, Map<java.util.Locale, String> nameMap,
			String destination, String url,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addWebHookEntry(
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
	public static WebHookEntry addWebHookEntry(WebHookEntry webHookEntry) {
		return getService().addWebHookEntry(webHookEntry);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Creates a new web hook entry with the primary key. Does not add the web hook entry to the database.
	 *
	 * @param webHookEntryId the primary key for the new web hook entry
	 * @return the new web hook entry
	 */
	public static WebHookEntry createWebHookEntry(long webHookEntryId) {
		return getService().createWebHookEntry(webHookEntryId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
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
	public static WebHookEntry deleteWebHookEntry(long webHookEntryId)
		throws PortalException {

		return getService().deleteWebHookEntry(webHookEntryId);
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
	public static WebHookEntry deleteWebHookEntry(WebHookEntry webHookEntry) {
		return getService().deleteWebHookEntry(webHookEntry);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
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
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static WebHookEntry fetchWebHookEntry(long webHookEntryId) {
		return getService().fetchWebHookEntry(webHookEntryId);
	}

	/**
	 * Returns the web hook entry with the matching UUID and company.
	 *
	 * @param uuid the web hook entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching web hook entry, or <code>null</code> if a matching web hook entry could not be found
	 */
	public static WebHookEntry fetchWebHookEntryByUuidAndCompanyId(
		String uuid, long companyId) {

		return getService().fetchWebHookEntryByUuidAndCompanyId(
			uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
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
	public static List<WebHookEntry> getWebHookEntries(int start, int end) {
		return getService().getWebHookEntries(start, end);
	}

	/**
	 * Returns the number of web hook entries.
	 *
	 * @return the number of web hook entries
	 */
	public static int getWebHookEntriesCount() {
		return getService().getWebHookEntriesCount();
	}

	/**
	 * Returns the web hook entry with the primary key.
	 *
	 * @param webHookEntryId the primary key of the web hook entry
	 * @return the web hook entry
	 * @throws PortalException if a web hook entry with the primary key could not be found
	 */
	public static WebHookEntry getWebHookEntry(long webHookEntryId)
		throws PortalException {

		return getService().getWebHookEntry(webHookEntryId);
	}

	/**
	 * Returns the web hook entry with the matching UUID and company.
	 *
	 * @param uuid the web hook entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching web hook entry
	 * @throws PortalException if a matching web hook entry could not be found
	 */
	public static WebHookEntry getWebHookEntryByUuidAndCompanyId(
			String uuid, long companyId)
		throws PortalException {

		return getService().getWebHookEntryByUuidAndCompanyId(uuid, companyId);
	}

	public static List<WebHookEntry> search(
			long companyId, String keywords, int start, int end,
			com.liferay.portal.kernel.search.Sort sort)
		throws PortalException {

		return getService().search(companyId, keywords, start, end, sort);
	}

	public static int searchCount(long companyId, String keywords)
		throws PortalException {

		return getService().searchCount(companyId, keywords);
	}

	public static WebHookEntry updateWebHookEntry(
			long webHookEntryId, Map<java.util.Locale, String> nameMap,
			String destination, String url,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateWebHookEntry(
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
	public static WebHookEntry updateWebHookEntry(WebHookEntry webHookEntry) {
		return getService().updateWebHookEntry(webHookEntry);
	}

	public static WebHookEntryLocalService getService() {
		return _service;
	}

	private static volatile WebHookEntryLocalService _service;

}