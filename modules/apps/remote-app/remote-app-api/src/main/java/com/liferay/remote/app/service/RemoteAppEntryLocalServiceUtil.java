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

package com.liferay.remote.app.service;

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.remote.app.model.RemoteAppEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * Provides the local service utility for RemoteAppEntry. This utility wraps
 * <code>com.liferay.remote.app.service.impl.RemoteAppEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see RemoteAppEntryLocalService
 * @generated
 */
public class RemoteAppEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.remote.app.service.impl.RemoteAppEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static RemoteAppEntry addCustomElementRemoteAppEntry(
			String externalReferenceCode, long userId,
			String customElementCSSURLs, String customElementHTMLElementName,
			String customElementURLs, boolean customElementUseESM,
			String description, String friendlyURLMapping, boolean instanceable,
			Map<java.util.Locale, String> nameMap, String portletCategoryName,
			String properties, String sourceCodeURL)
		throws PortalException {

		return getService().addCustomElementRemoteAppEntry(
			externalReferenceCode, userId, customElementCSSURLs,
			customElementHTMLElementName, customElementURLs,
			customElementUseESM, description, friendlyURLMapping, instanceable,
			nameMap, portletCategoryName, properties, sourceCodeURL);
	}

	public static RemoteAppEntry addIFrameRemoteAppEntry(
			long userId, String description, String friendlyURLMapping,
			String iFrameURL, boolean instanceable,
			Map<java.util.Locale, String> nameMap, String portletCategoryName,
			String properties, String sourceCodeURL)
		throws PortalException {

		return getService().addIFrameRemoteAppEntry(
			userId, description, friendlyURLMapping, iFrameURL, instanceable,
			nameMap, portletCategoryName, properties, sourceCodeURL);
	}

	public static RemoteAppEntry addOrUpdateCustomElementRemoteAppEntry(
			String externalReferenceCode, long userId,
			String customElementCSSURLs, String customElementHTMLElementName,
			String customElementURLs, boolean customElementUseESM,
			String description, String friendlyURLMapping, boolean instanceable,
			Map<java.util.Locale, String> nameMap, String portletCategoryName,
			String properties, String sourceCodeURL)
		throws PortalException {

		return getService().addOrUpdateCustomElementRemoteAppEntry(
			externalReferenceCode, userId, customElementCSSURLs,
			customElementHTMLElementName, customElementURLs,
			customElementUseESM, description, friendlyURLMapping, instanceable,
			nameMap, portletCategoryName, properties, sourceCodeURL);
	}

	/**
	 * Adds the remote app entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RemoteAppEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param remoteAppEntry the remote app entry
	 * @return the remote app entry that was added
	 */
	public static RemoteAppEntry addRemoteAppEntry(
		RemoteAppEntry remoteAppEntry) {

		return getService().addRemoteAppEntry(remoteAppEntry);
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
	 * Creates a new remote app entry with the primary key. Does not add the remote app entry to the database.
	 *
	 * @param remoteAppEntryId the primary key for the new remote app entry
	 * @return the new remote app entry
	 */
	public static RemoteAppEntry createRemoteAppEntry(long remoteAppEntryId) {
		return getService().createRemoteAppEntry(remoteAppEntryId);
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
	 * Deletes the remote app entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RemoteAppEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param remoteAppEntryId the primary key of the remote app entry
	 * @return the remote app entry that was removed
	 * @throws PortalException if a remote app entry with the primary key could not be found
	 */
	public static RemoteAppEntry deleteRemoteAppEntry(long remoteAppEntryId)
		throws PortalException {

		return getService().deleteRemoteAppEntry(remoteAppEntryId);
	}

	/**
	 * Deletes the remote app entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RemoteAppEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param remoteAppEntry the remote app entry
	 * @return the remote app entry that was removed
	 * @throws PortalException
	 */
	public static RemoteAppEntry deleteRemoteAppEntry(
			RemoteAppEntry remoteAppEntry)
		throws PortalException {

		return getService().deleteRemoteAppEntry(remoteAppEntry);
	}

	public static void deployRemoteAppEntry(RemoteAppEntry remoteAppEntry) {
		getService().deployRemoteAppEntry(remoteAppEntry);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.remote.app.model.impl.RemoteAppEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.remote.app.model.impl.RemoteAppEntryModelImpl</code>.
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

	public static RemoteAppEntry fetchRemoteAppEntry(long remoteAppEntryId) {
		return getService().fetchRemoteAppEntry(remoteAppEntryId);
	}

	/**
	 * Returns the remote app entry with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the remote app entry's external reference code
	 * @return the matching remote app entry, or <code>null</code> if a matching remote app entry could not be found
	 */
	public static RemoteAppEntry fetchRemoteAppEntryByExternalReferenceCode(
		long companyId, String externalReferenceCode) {

		return getService().fetchRemoteAppEntryByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #fetchRemoteAppEntryByExternalReferenceCode(long, String)}
	 */
	@Deprecated
	public static RemoteAppEntry fetchRemoteAppEntryByReferenceCode(
		long companyId, String externalReferenceCode) {

		return getService().fetchRemoteAppEntryByReferenceCode(
			companyId, externalReferenceCode);
	}

	/**
	 * Returns the remote app entry with the matching UUID and company.
	 *
	 * @param uuid the remote app entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching remote app entry, or <code>null</code> if a matching remote app entry could not be found
	 */
	public static RemoteAppEntry fetchRemoteAppEntryByUuidAndCompanyId(
		String uuid, long companyId) {

		return getService().fetchRemoteAppEntryByUuidAndCompanyId(
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
	 * Returns a range of all the remote app entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.remote.app.model.impl.RemoteAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remote app entries
	 * @param end the upper bound of the range of remote app entries (not inclusive)
	 * @return the range of remote app entries
	 */
	public static List<RemoteAppEntry> getRemoteAppEntries(int start, int end) {
		return getService().getRemoteAppEntries(start, end);
	}

	/**
	 * Returns the number of remote app entries.
	 *
	 * @return the number of remote app entries
	 */
	public static int getRemoteAppEntriesCount() {
		return getService().getRemoteAppEntriesCount();
	}

	/**
	 * Returns the remote app entry with the primary key.
	 *
	 * @param remoteAppEntryId the primary key of the remote app entry
	 * @return the remote app entry
	 * @throws PortalException if a remote app entry with the primary key could not be found
	 */
	public static RemoteAppEntry getRemoteAppEntry(long remoteAppEntryId)
		throws PortalException {

		return getService().getRemoteAppEntry(remoteAppEntryId);
	}

	/**
	 * Returns the remote app entry with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the remote app entry's external reference code
	 * @return the matching remote app entry
	 * @throws PortalException if a matching remote app entry could not be found
	 */
	public static RemoteAppEntry getRemoteAppEntryByExternalReferenceCode(
			long companyId, String externalReferenceCode)
		throws PortalException {

		return getService().getRemoteAppEntryByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	/**
	 * Returns the remote app entry with the matching UUID and company.
	 *
	 * @param uuid the remote app entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching remote app entry
	 * @throws PortalException if a matching remote app entry could not be found
	 */
	public static RemoteAppEntry getRemoteAppEntryByUuidAndCompanyId(
			String uuid, long companyId)
		throws PortalException {

		return getService().getRemoteAppEntryByUuidAndCompanyId(
			uuid, companyId);
	}

	public static List<RemoteAppEntry> search(
			long companyId, String keywords, int start, int end,
			com.liferay.portal.kernel.search.Sort sort)
		throws PortalException {

		return getService().search(companyId, keywords, start, end, sort);
	}

	public static int searchCount(long companyId, String keywords)
		throws PortalException {

		return getService().searchCount(companyId, keywords);
	}

	public static void undeployRemoteAppEntry(RemoteAppEntry remoteAppEntry) {
		getService().undeployRemoteAppEntry(remoteAppEntry);
	}

	public static RemoteAppEntry updateCustomElementRemoteAppEntry(
			long userId, long remoteAppEntryId, String customElementCSSURLs,
			String customElementHTMLElementName, String customElementURLs,
			boolean customElementUseESM, String description,
			String friendlyURLMapping, Map<java.util.Locale, String> nameMap,
			String portletCategoryName, String properties, String sourceCodeURL)
		throws PortalException {

		return getService().updateCustomElementRemoteAppEntry(
			userId, remoteAppEntryId, customElementCSSURLs,
			customElementHTMLElementName, customElementURLs,
			customElementUseESM, description, friendlyURLMapping, nameMap,
			portletCategoryName, properties, sourceCodeURL);
	}

	public static RemoteAppEntry updateIFrameRemoteAppEntry(
			long userId, long remoteAppEntryId, String description,
			String friendlyURLMapping, String iFrameURL,
			Map<java.util.Locale, String> nameMap, String portletCategoryName,
			String properties, String sourceCodeURL)
		throws PortalException {

		return getService().updateIFrameRemoteAppEntry(
			userId, remoteAppEntryId, description, friendlyURLMapping,
			iFrameURL, nameMap, portletCategoryName, properties, sourceCodeURL);
	}

	/**
	 * Updates the remote app entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RemoteAppEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param remoteAppEntry the remote app entry
	 * @return the remote app entry that was updated
	 */
	public static RemoteAppEntry updateRemoteAppEntry(
		RemoteAppEntry remoteAppEntry) {

		return getService().updateRemoteAppEntry(remoteAppEntry);
	}

	public static RemoteAppEntry updateStatus(
			long userId, long remoteAppEntryId, int status)
		throws PortalException {

		return getService().updateStatus(userId, remoteAppEntryId, status);
	}

	public static RemoteAppEntryLocalService getService() {
		return _service;
	}

	private static volatile RemoteAppEntryLocalService _service;

}