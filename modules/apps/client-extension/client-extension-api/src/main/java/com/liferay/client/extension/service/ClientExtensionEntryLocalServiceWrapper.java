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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ClientExtensionEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ClientExtensionEntryLocalService
 * @generated
 */
public class ClientExtensionEntryLocalServiceWrapper
	implements ClientExtensionEntryLocalService,
			   ServiceWrapper<ClientExtensionEntryLocalService> {

	public ClientExtensionEntryLocalServiceWrapper() {
		this(null);
	}

	public ClientExtensionEntryLocalServiceWrapper(
		ClientExtensionEntryLocalService clientExtensionEntryLocalService) {

		_clientExtensionEntryLocalService = clientExtensionEntryLocalService;
	}

	@Override
	public com.liferay.client.extension.model.ClientExtensionEntry
			addCustomElementClientExtensionEntry(
				String externalReferenceCode, long userId,
				String customElementCSSURLs,
				String customElementHTMLElementName, String customElementURLs,
				boolean customElementUseESM, String description,
				String friendlyURLMapping, boolean instanceable,
				java.util.Map<java.util.Locale, String> nameMap,
				String portletCategoryName, String properties,
				String sourceCodeURL)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryLocalService.addCustomElementClientExtensionEntry(
			externalReferenceCode, userId, customElementCSSURLs,
			customElementHTMLElementName, customElementURLs,
			customElementUseESM, description, friendlyURLMapping, instanceable,
			nameMap, portletCategoryName, properties, sourceCodeURL);
	}

	@Override
	public com.liferay.client.extension.model.ClientExtensionEntry
			addIFrameClientExtensionEntry(
				long userId, String description, String friendlyURLMapping,
				String iFrameURL, boolean instanceable,
				java.util.Map<java.util.Locale, String> nameMap,
				String portletCategoryName, String properties,
				String sourceCodeURL)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryLocalService.addIFrameClientExtensionEntry(
			userId, description, friendlyURLMapping, iFrameURL, instanceable,
			nameMap, portletCategoryName, properties, sourceCodeURL);
	}

	@Override
	public com.liferay.client.extension.model.ClientExtensionEntry
			addOrUpdateCustomElementClientExtensionEntry(
				String externalReferenceCode, long userId,
				String customElementCSSURLs,
				String customElementHTMLElementName, String customElementURLs,
				boolean customElementUseESM, String description,
				String friendlyURLMapping, boolean instanceable,
				java.util.Map<java.util.Locale, String> nameMap,
				String portletCategoryName, String properties,
				String sourceCodeURL)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryLocalService.
			addOrUpdateCustomElementClientExtensionEntry(
				externalReferenceCode, userId, customElementCSSURLs,
				customElementHTMLElementName, customElementURLs,
				customElementUseESM, description, friendlyURLMapping,
				instanceable, nameMap, portletCategoryName, properties,
				sourceCodeURL);
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
	@Override
	public com.liferay.client.extension.model.ClientExtensionEntry addClientExtensionEntry(
		com.liferay.client.extension.model.ClientExtensionEntry clientExtensionEntry) {

		return _clientExtensionEntryLocalService.addClientExtensionEntry(clientExtensionEntry);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Creates a new remote app entry with the primary key. Does not add the remote app entry to the database.
	 *
	 * @param clientExtensionEntryId the primary key for the new remote app entry
	 * @return the new remote app entry
	 */
	@Override
	public com.liferay.client.extension.model.ClientExtensionEntry
		createClientExtensionEntry(long clientExtensionEntryId) {

		return _clientExtensionEntryLocalService.createClientExtensionEntry(
			clientExtensionEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryLocalService.deletePersistedModel(persistedModel);
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
	@Override
	public com.liferay.client.extension.model.ClientExtensionEntry
			deleteClientExtensionEntry(long clientExtensionEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryLocalService.deleteClientExtensionEntry(
			clientExtensionEntryId);
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
	@Override
	public com.liferay.client.extension.model.ClientExtensionEntry
			deleteClientExtensionEntry(
				com.liferay.client.extension.model.ClientExtensionEntry
					clientExtensionEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryLocalService.deleteClientExtensionEntry(clientExtensionEntry);
	}

	@Override
	public void deployClientExtensionEntry(
		com.liferay.client.extension.model.ClientExtensionEntry clientExtensionEntry) {

		_clientExtensionEntryLocalService.deployClientExtensionEntry(clientExtensionEntry);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _clientExtensionEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _clientExtensionEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _clientExtensionEntryLocalService.dynamicQuery();
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

		return _clientExtensionEntryLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _clientExtensionEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _clientExtensionEntryLocalService.dynamicQuery(
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

		return _clientExtensionEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _clientExtensionEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.client.extension.model.ClientExtensionEntry
		fetchClientExtensionEntry(long clientExtensionEntryId) {

		return _clientExtensionEntryLocalService.fetchClientExtensionEntry(
			clientExtensionEntryId);
	}

	/**
	 * Returns the remote app entry with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the remote app entry's external reference code
	 * @return the matching remote app entry, or <code>null</code> if a matching remote app entry could not be found
	 */
	@Override
	public com.liferay.client.extension.model.ClientExtensionEntry
		fetchClientExtensionEntryByExternalReferenceCode(
			long companyId, String externalReferenceCode) {

		return _clientExtensionEntryLocalService.
			fetchClientExtensionEntryByExternalReferenceCode(
				companyId, externalReferenceCode);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #fetchClientExtensionEntryByExternalReferenceCode(long, String)}
	 */
	@Deprecated
	@Override
	public com.liferay.client.extension.model.ClientExtensionEntry
		fetchClientExtensionEntryByReferenceCode(
			long companyId, String externalReferenceCode) {

		return _clientExtensionEntryLocalService.fetchClientExtensionEntryByReferenceCode(
			companyId, externalReferenceCode);
	}

	/**
	 * Returns the remote app entry with the matching UUID and company.
	 *
	 * @param uuid the remote app entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching remote app entry, or <code>null</code> if a matching remote app entry could not be found
	 */
	@Override
	public com.liferay.client.extension.model.ClientExtensionEntry
		fetchClientExtensionEntryByUuidAndCompanyId(String uuid, long companyId) {

		return _clientExtensionEntryLocalService.
			fetchClientExtensionEntryByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _clientExtensionEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _clientExtensionEntryLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _clientExtensionEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _clientExtensionEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryLocalService.getPersistedModel(primaryKeyObj);
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
	@Override
	public java.util.List<com.liferay.client.extension.model.ClientExtensionEntry>
		getClientExtensionEntries(int start, int end) {

		return _clientExtensionEntryLocalService.getClientExtensionEntries(start, end);
	}

	/**
	 * Returns the number of remote app entries.
	 *
	 * @return the number of remote app entries
	 */
	@Override
	public int getClientExtensionEntriesCount() {
		return _clientExtensionEntryLocalService.getClientExtensionEntriesCount();
	}

	/**
	 * Returns the remote app entry with the primary key.
	 *
	 * @param clientExtensionEntryId the primary key of the remote app entry
	 * @return the remote app entry
	 * @throws PortalException if a remote app entry with the primary key could not be found
	 */
	@Override
	public com.liferay.client.extension.model.ClientExtensionEntry getClientExtensionEntry(
			long clientExtensionEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryLocalService.getClientExtensionEntry(clientExtensionEntryId);
	}

	/**
	 * Returns the remote app entry with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the remote app entry's external reference code
	 * @return the matching remote app entry
	 * @throws PortalException if a matching remote app entry could not be found
	 */
	@Override
	public com.liferay.client.extension.model.ClientExtensionEntry
			getClientExtensionEntryByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryLocalService.
			getClientExtensionEntryByExternalReferenceCode(
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
	@Override
	public com.liferay.client.extension.model.ClientExtensionEntry
			getClientExtensionEntryByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryLocalService.getClientExtensionEntryByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public java.util.List<com.liferay.client.extension.model.ClientExtensionEntry>
			search(
				long companyId, String keywords, int start, int end,
				com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryLocalService.search(
			companyId, keywords, start, end, sort);
	}

	@Override
	public int searchCount(long companyId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryLocalService.searchCount(companyId, keywords);
	}

	@Override
	public void undeployClientExtensionEntry(
		com.liferay.client.extension.model.ClientExtensionEntry clientExtensionEntry) {

		_clientExtensionEntryLocalService.undeployClientExtensionEntry(clientExtensionEntry);
	}

	@Override
	public com.liferay.client.extension.model.ClientExtensionEntry
			updateCustomElementClientExtensionEntry(
				long userId, long clientExtensionEntryId, String customElementCSSURLs,
				String customElementHTMLElementName, String customElementURLs,
				boolean customElementUseESM, String description,
				String friendlyURLMapping,
				java.util.Map<java.util.Locale, String> nameMap,
				String portletCategoryName, String properties,
				String sourceCodeURL)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryLocalService.updateCustomElementClientExtensionEntry(
			userId, clientExtensionEntryId, customElementCSSURLs,
			customElementHTMLElementName, customElementURLs,
			customElementUseESM, description, friendlyURLMapping, nameMap,
			portletCategoryName, properties, sourceCodeURL);
	}

	@Override
	public com.liferay.client.extension.model.ClientExtensionEntry
			updateIFrameClientExtensionEntry(
				long userId, long clientExtensionEntryId, String description,
				String friendlyURLMapping, String iFrameURL,
				java.util.Map<java.util.Locale, String> nameMap,
				String portletCategoryName, String properties,
				String sourceCodeURL)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryLocalService.updateIFrameClientExtensionEntry(
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
	@Override
	public com.liferay.client.extension.model.ClientExtensionEntry
		updateClientExtensionEntry(
			com.liferay.client.extension.model.ClientExtensionEntry clientExtensionEntry) {

		return _clientExtensionEntryLocalService.updateClientExtensionEntry(clientExtensionEntry);
	}

	@Override
	public com.liferay.client.extension.model.ClientExtensionEntry updateStatus(
			long userId, long clientExtensionEntryId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryLocalService.updateStatus(
			userId, clientExtensionEntryId, status);
	}

	@Override
	public ClientExtensionEntryLocalService getWrappedService() {
		return _clientExtensionEntryLocalService;
	}

	@Override
	public void setWrappedService(
		ClientExtensionEntryLocalService clientExtensionEntryLocalService) {

		_clientExtensionEntryLocalService = clientExtensionEntryLocalService;
	}

	private ClientExtensionEntryLocalService _clientExtensionEntryLocalService;

}