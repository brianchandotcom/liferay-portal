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

package com.liferay.client.extension.service;

import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * Provides the local service utility for ClientExtensionEntry. This utility wraps
 * <code>com.liferay.client.extension.service.impl.ClientExtensionEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ClientExtensionEntryLocalService
 * @generated
 */
public class ClientExtensionEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.client.extension.service.impl.ClientExtensionEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static ClientExtensionEntry addCustomElementClientExtensionEntry(
			String externalReferenceCode, long userId,
			String customElementCSSURLs, String customElementHTMLElementName,
			String customElementURLs, boolean customElementUseESM,
			String description, String friendlyURLMapping, boolean instanceable,
			Map<java.util.Locale, String> nameMap, String portletCategoryName,
			String properties, String sourceCodeURL)
		throws PortalException {

		return getService().addCustomElementClientExtensionEntry(
			externalReferenceCode, userId, customElementCSSURLs,
			customElementHTMLElementName, customElementURLs,
			customElementUseESM, description, friendlyURLMapping, instanceable,
			nameMap, portletCategoryName, properties, sourceCodeURL);
	}

	public static ClientExtensionEntry addIFrameClientExtensionEntry(
			long userId, String description, String friendlyURLMapping,
			String iFrameURL, boolean instanceable,
			Map<java.util.Locale, String> nameMap, String portletCategoryName,
			String properties, String sourceCodeURL)
		throws PortalException {

		return getService().addIFrameClientExtensionEntry(
			userId, description, friendlyURLMapping, iFrameURL, instanceable,
			nameMap, portletCategoryName, properties, sourceCodeURL);
	}

	public static ClientExtensionEntry addOrUpdateCustomElementClientExtensionEntry(
			String externalReferenceCode, long userId,
			String customElementCSSURLs, String customElementHTMLElementName,
			String customElementURLs, boolean customElementUseESM,
			String description, String friendlyURLMapping, boolean instanceable,
			Map<java.util.Locale, String> nameMap, String portletCategoryName,
			String properties, String sourceCodeURL)
		throws PortalException {

		return getService().addOrUpdateCustomElementClientExtensionEntry(
			externalReferenceCode, userId, customElementCSSURLs,
			customElementHTMLElementName, customElementURLs,
			customElementUseESM, description, friendlyURLMapping, instanceable,
			nameMap, portletCategoryName, properties, sourceCodeURL);
	}

	/**
	 * Adds the remote app entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ClientExtensionEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param clientExtensionEntry the remote app entry
	 * @return the remote app entry that was added
	 */
	public static ClientExtensionEntry addClientExtensionEntry(
		ClientExtensionEntry clientExtensionEntry) {

		return getService().addClientExtensionEntry(clientExtensionEntry);
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
	 * @param clientExtensionEntryId the primary key for the new remote app entry
	 * @return the new remote app entry
	 */
	public static ClientExtensionEntry createClientExtensionEntry(long clientExtensionEntryId) {
		return getService().createClientExtensionEntry(clientExtensionEntryId);
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
	 * <strong>Important:</strong> Inspect ClientExtensionEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param clientExtensionEntryId the primary key of the remote app entry
	 * @return the remote app entry that was removed
	 * @throws PortalException if a remote app entry with the primary key could not be found
	 */
	public static ClientExtensionEntry deleteClientExtensionEntry(long clientExtensionEntryId)
		throws PortalException {

		return getService().deleteClientExtensionEntry(clientExtensionEntryId);
	}

	/**
	 * Deletes the remote app entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ClientExtensionEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param clientExtensionEntry the remote app entry
	 * @return the remote app entry that was removed
	 * @throws PortalException
	 */
	public static ClientExtensionEntry deleteClientExtensionEntry(
			ClientExtensionEntry clientExtensionEntry)
		throws PortalException {

		return getService().deleteClientExtensionEntry(clientExtensionEntry);
	}

	public static void deployClientExtensionEntry(ClientExtensionEntry clientExtensionEntry) {
		getService().deployClientExtensionEntry(clientExtensionEntry);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.client.extension.model.impl.ClientExtensionEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.client.extension.model.impl.ClientExtensionEntryModelImpl</code>.
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

	public static ClientExtensionEntry fetchClientExtensionEntry(long clientExtensionEntryId) {
		return getService().fetchClientExtensionEntry(clientExtensionEntryId);
	}

	/**
	 * Returns the remote app entry with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the remote app entry's external reference code
	 * @return the matching remote app entry, or <code>null</code> if a matching remote app entry could not be found
	 */
	public static ClientExtensionEntry fetchClientExtensionEntryByExternalReferenceCode(
		long companyId, String externalReferenceCode) {

		return getService().fetchClientExtensionEntryByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #fetchClientExtensionEntryByExternalReferenceCode(long, String)}
	 */
	@Deprecated
	public static ClientExtensionEntry fetchClientExtensionEntryByReferenceCode(
		long companyId, String externalReferenceCode) {

		return getService().fetchClientExtensionEntryByReferenceCode(
			companyId, externalReferenceCode);
	}

	/**
	 * Returns the remote app entry with the matching UUID and company.
	 *
	 * @param uuid the remote app entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching remote app entry, or <code>null</code> if a matching remote app entry could not be found
	 */
	public static ClientExtensionEntry fetchClientExtensionEntryByUuidAndCompanyId(
		String uuid, long companyId) {

		return getService().fetchClientExtensionEntryByUuidAndCompanyId(
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.client.extension.model.impl.ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remote app entries
	 * @param end the upper bound of the range of remote app entries (not inclusive)
	 * @return the range of remote app entries
	 */
	public static List<ClientExtensionEntry> getClientExtensionEntries(int start, int end) {
		return getService().getClientExtensionEntries(start, end);
	}

	/**
	 * Returns the number of remote app entries.
	 *
	 * @return the number of remote app entries
	 */
	public static int getClientExtensionEntriesCount() {
		return getService().getClientExtensionEntriesCount();
	}

	/**
	 * Returns the remote app entry with the primary key.
	 *
	 * @param clientExtensionEntryId the primary key of the remote app entry
	 * @return the remote app entry
	 * @throws PortalException if a remote app entry with the primary key could not be found
	 */
	public static ClientExtensionEntry getClientExtensionEntry(long clientExtensionEntryId)
		throws PortalException {

		return getService().getClientExtensionEntry(clientExtensionEntryId);
	}

	/**
	 * Returns the remote app entry with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the remote app entry's external reference code
	 * @return the matching remote app entry
	 * @throws PortalException if a matching remote app entry could not be found
	 */
	public static ClientExtensionEntry getClientExtensionEntryByExternalReferenceCode(
			long companyId, String externalReferenceCode)
		throws PortalException {

		return getService().getClientExtensionEntryByExternalReferenceCode(
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
	public static ClientExtensionEntry getClientExtensionEntryByUuidAndCompanyId(
			String uuid, long companyId)
		throws PortalException {

		return getService().getClientExtensionEntryByUuidAndCompanyId(
			uuid, companyId);
	}

	public static List<ClientExtensionEntry> search(
			long companyId, String keywords, int start, int end,
			com.liferay.portal.kernel.search.Sort sort)
		throws PortalException {

		return getService().search(companyId, keywords, start, end, sort);
	}

	public static int searchCount(long companyId, String keywords)
		throws PortalException {

		return getService().searchCount(companyId, keywords);
	}

	public static void undeployClientExtensionEntry(ClientExtensionEntry clientExtensionEntry) {
		getService().undeployClientExtensionEntry(clientExtensionEntry);
	}

	public static ClientExtensionEntry updateCustomElementClientExtensionEntry(
			long userId, long clientExtensionEntryId, String customElementCSSURLs,
			String customElementHTMLElementName, String customElementURLs,
			boolean customElementUseESM, String description,
			String friendlyURLMapping, Map<java.util.Locale, String> nameMap,
			String portletCategoryName, String properties, String sourceCodeURL)
		throws PortalException {

		return getService().updateCustomElementClientExtensionEntry(
			userId, clientExtensionEntryId, customElementCSSURLs,
			customElementHTMLElementName, customElementURLs,
			customElementUseESM, description, friendlyURLMapping, nameMap,
			portletCategoryName, properties, sourceCodeURL);
	}

	public static ClientExtensionEntry updateIFrameClientExtensionEntry(
			long userId, long clientExtensionEntryId, String description,
			String friendlyURLMapping, String iFrameURL,
			Map<java.util.Locale, String> nameMap, String portletCategoryName,
			String properties, String sourceCodeURL)
		throws PortalException {

		return getService().updateIFrameClientExtensionEntry(
			userId, clientExtensionEntryId, description, friendlyURLMapping,
			iFrameURL, nameMap, portletCategoryName, properties, sourceCodeURL);
	}

	/**
	 * Updates the remote app entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ClientExtensionEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param clientExtensionEntry the remote app entry
	 * @return the remote app entry that was updated
	 */
	public static ClientExtensionEntry updateClientExtensionEntry(
		ClientExtensionEntry clientExtensionEntry) {

		return getService().updateClientExtensionEntry(clientExtensionEntry);
	}

	public static ClientExtensionEntry updateStatus(
			long userId, long clientExtensionEntryId, int status)
		throws PortalException {

		return getService().updateStatus(userId, clientExtensionEntryId, status);
	}

	public static ClientExtensionEntryLocalService getService() {
		return _service;
	}

	private static volatile ClientExtensionEntryLocalService _service;

}