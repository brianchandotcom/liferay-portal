/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.service;

import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

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

	/**
	 * Adds the client extension entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ClientExtensionEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param clientExtensionEntry the client extension entry
	 * @return the client extension entry that was added
	 */
	@Override
	public ClientExtensionEntry addClientExtensionEntry(
		ClientExtensionEntry clientExtensionEntry) {

		return _clientExtensionEntryLocalService.addClientExtensionEntry(
			clientExtensionEntry);
	}

	@Override
	public ClientExtensionEntry addClientExtensionEntry(
			String externalReferenceCode, long userId, String description,
			java.util.Map<java.util.Locale, String> nameMap, String properties,
			String sourceCodeURL, String type, String typeSettings)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryLocalService.addClientExtensionEntry(
			externalReferenceCode, userId, description, nameMap, properties,
			sourceCodeURL, type, typeSettings);
	}

	@Override
	public ClientExtensionEntry addOrUpdateClientExtensionEntry(
			String externalReferenceCode, long userId, String description,
			java.util.Map<java.util.Locale, String> nameMap, String properties,
			String sourceCodeURL, String type, String typeSettings)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryLocalService.
			addOrUpdateClientExtensionEntry(
				externalReferenceCode, userId, description, nameMap, properties,
				sourceCodeURL, type, typeSettings);
	}

	/**
	 * Creates a new client extension entry with the primary key. Does not add the client extension entry to the database.
	 *
	 * @param clientExtensionEntryId the primary key for the new client extension entry
	 * @return the new client extension entry
	 */
	@Override
	public ClientExtensionEntry createClientExtensionEntry(
		long clientExtensionEntryId) {

		return _clientExtensionEntryLocalService.createClientExtensionEntry(
			clientExtensionEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the client extension entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ClientExtensionEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param clientExtensionEntry the client extension entry
	 * @return the client extension entry that was removed
	 * @throws PortalException
	 */
	@Override
	public ClientExtensionEntry deleteClientExtensionEntry(
			ClientExtensionEntry clientExtensionEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryLocalService.deleteClientExtensionEntry(
			clientExtensionEntry);
	}

	/**
	 * Deletes the client extension entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ClientExtensionEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param clientExtensionEntryId the primary key of the client extension entry
	 * @return the client extension entry that was removed
	 * @throws PortalException if a client extension entry with the primary key could not be found
	 */
	@Override
	public ClientExtensionEntry deleteClientExtensionEntry(
			long clientExtensionEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryLocalService.deleteClientExtensionEntry(
			clientExtensionEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public void deployClientExtensionEntry(
			ClientExtensionEntry clientExtensionEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		_clientExtensionEntryLocalService.deployClientExtensionEntry(
			clientExtensionEntry);
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

		return _clientExtensionEntryLocalService.dynamicQueryCount(
			dynamicQuery);
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
	public ClientExtensionEntry fetchClientExtensionEntry(
		long clientExtensionEntryId) {

		return _clientExtensionEntryLocalService.fetchClientExtensionEntry(
			clientExtensionEntryId);
	}

	@Override
	public ClientExtensionEntry
		fetchClientExtensionEntryByExternalReferenceCode(
			String externalReferenceCode, long companyId) {

		return _clientExtensionEntryLocalService.
			fetchClientExtensionEntryByExternalReferenceCode(
				externalReferenceCode, companyId);
	}

	/**
	 * Returns the client extension entry with the matching UUID and company.
	 *
	 * @param uuid the client extension entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching client extension entry, or <code>null</code> if a matching client extension entry could not be found
	 */
	@Override
	public ClientExtensionEntry fetchClientExtensionEntryByUuidAndCompanyId(
		String uuid, long companyId) {

		return _clientExtensionEntryLocalService.
			fetchClientExtensionEntryByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _clientExtensionEntryLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the client extension entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.client.extension.model.impl.ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @return the range of client extension entries
	 */
	@Override
	public java.util.List<ClientExtensionEntry> getClientExtensionEntries(
		int start, int end) {

		return _clientExtensionEntryLocalService.getClientExtensionEntries(
			start, end);
	}

	@Override
	public java.util.List<ClientExtensionEntry> getClientExtensionEntries(
		long companyId, int start, int end) {

		return _clientExtensionEntryLocalService.getClientExtensionEntries(
			companyId, start, end);
	}

	@Override
	public java.util.List<ClientExtensionEntry> getClientExtensionEntries(
		long companyId, String type, int start, int end) {

		return _clientExtensionEntryLocalService.getClientExtensionEntries(
			companyId, type, start, end);
	}

	/**
	 * Returns the number of client extension entries.
	 *
	 * @return the number of client extension entries
	 */
	@Override
	public int getClientExtensionEntriesCount() {
		return _clientExtensionEntryLocalService.
			getClientExtensionEntriesCount();
	}

	@Override
	public int getClientExtensionEntriesCount(long companyId) {
		return _clientExtensionEntryLocalService.getClientExtensionEntriesCount(
			companyId);
	}

	@Override
	public int getClientExtensionEntriesCount(long companyId, String type) {
		return _clientExtensionEntryLocalService.getClientExtensionEntriesCount(
			companyId, type);
	}

	/**
	 * Returns the client extension entry with the primary key.
	 *
	 * @param clientExtensionEntryId the primary key of the client extension entry
	 * @return the client extension entry
	 * @throws PortalException if a client extension entry with the primary key could not be found
	 */
	@Override
	public ClientExtensionEntry getClientExtensionEntry(
			long clientExtensionEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryLocalService.getClientExtensionEntry(
			clientExtensionEntryId);
	}

	@Override
	public ClientExtensionEntry getClientExtensionEntryByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryLocalService.
			getClientExtensionEntryByExternalReferenceCode(
				externalReferenceCode, companyId);
	}

	/**
	 * Returns the client extension entry with the matching UUID and company.
	 *
	 * @param uuid the client extension entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching client extension entry
	 * @throws PortalException if a matching client extension entry could not be found
	 */
	@Override
	public ClientExtensionEntry getClientExtensionEntryByUuidAndCompanyId(
			String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryLocalService.
			getClientExtensionEntryByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _clientExtensionEntryLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _clientExtensionEntryLocalService.
			getIndexableActionableDynamicQuery();
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

		return _clientExtensionEntryLocalService.getPersistedModel(
			primaryKeyObj);
	}

	@Override
	public java.util.List<ClientExtensionEntry> search(
			long companyId, String keywords, int start, int end,
			com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryLocalService.search(
			companyId, keywords, start, end, sort);
	}

	@Override
	public int searchCount(long companyId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryLocalService.searchCount(
			companyId, keywords);
	}

	@Override
	public void undeployClientExtensionEntry(
		ClientExtensionEntry clientExtensionEntry) {

		_clientExtensionEntryLocalService.undeployClientExtensionEntry(
			clientExtensionEntry);
	}

	/**
	 * Updates the client extension entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ClientExtensionEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param clientExtensionEntry the client extension entry
	 * @return the client extension entry that was updated
	 */
	@Override
	public ClientExtensionEntry updateClientExtensionEntry(
		ClientExtensionEntry clientExtensionEntry) {

		return _clientExtensionEntryLocalService.updateClientExtensionEntry(
			clientExtensionEntry);
	}

	@Override
	public ClientExtensionEntry updateClientExtensionEntry(
			long userId, long clientExtensionEntryId, String description,
			java.util.Map<java.util.Locale, String> nameMap, String properties,
			String sourceCodeURL, String typeSettings)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryLocalService.updateClientExtensionEntry(
			userId, clientExtensionEntryId, description, nameMap, properties,
			sourceCodeURL, typeSettings);
	}

	@Override
	public ClientExtensionEntry updateStatus(
			long userId, long clientExtensionEntryId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _clientExtensionEntryLocalService.updateStatus(
			userId, clientExtensionEntryId, status);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _clientExtensionEntryLocalService.getBasePersistence();
	}

	@Override
	public CTPersistence<ClientExtensionEntry> getCTPersistence() {
		return _clientExtensionEntryLocalService.getCTPersistence();
	}

	@Override
	public Class<ClientExtensionEntry> getModelClass() {
		return _clientExtensionEntryLocalService.getModelClass();
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<ClientExtensionEntry>, R, E>
				updateUnsafeFunction)
		throws E {

		return _clientExtensionEntryLocalService.updateWithUnsafeFunction(
			updateUnsafeFunction);
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