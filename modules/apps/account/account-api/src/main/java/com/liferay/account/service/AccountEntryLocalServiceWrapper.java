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

package com.liferay.account.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AccountEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see AccountEntryLocalService
 * @generated
 */
public class AccountEntryLocalServiceWrapper
	implements AccountEntryLocalService,
			   ServiceWrapper<AccountEntryLocalService> {

	public AccountEntryLocalServiceWrapper() {
		this(null);
	}

	public AccountEntryLocalServiceWrapper(
		AccountEntryLocalService accountEntryLocalService) {

		_accountEntryLocalService = accountEntryLocalService;
	}

	@Override
	public void activateAccountEntries(long[] accountEntryIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		_accountEntryLocalService.activateAccountEntries(accountEntryIds);
	}

	@Override
	public com.liferay.account.model.AccountEntry activateAccountEntry(
			com.liferay.account.model.AccountEntry accountEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryLocalService.activateAccountEntry(accountEntry);
	}

	@Override
	public com.liferay.account.model.AccountEntry activateAccountEntry(
			long accountEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryLocalService.activateAccountEntry(accountEntryId);
	}

	/**
	 * Adds the account entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AccountEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param accountEntry the account entry
	 * @return the account entry that was added
	 */
	@Override
	public com.liferay.account.model.AccountEntry addAccountEntry(
		com.liferay.account.model.AccountEntry accountEntry) {

		return _accountEntryLocalService.addAccountEntry(accountEntry);
	}

	/**
	 * @param userId the creator's userId. Required.
	 * @param parentAccountEntryId the parent account's ID. Use 0 if there
	 is no parent account.
	 * @param name the account's name. Required.
	 * @param description the account's description. Optional.
	 * @param domains an array of email domains associated with the account.
	 This will define which users can be managed by the account
	 administrator, as well as optionally restricting membership to
	 users with matching email address domains. Optional.
	 * @param emailAddress the email address associated with this account.
	 Optional.
	 * @param logoBytes the account's logo bytes. Optional.
	 * @param taxIdNumber the account's tax ID number. Optional.
	 * @param type the account's type. Must be one of: "business", "person"
	 * @param status the account's workflow status. If workflow is enabled for
	 the AccountEntry model, this parameter is ignored.
	 * @param serviceContext the service context object. This object can contain
	 information about the account's asset tags and categories, and
	 custom fields.
	 * @return the newly created AccountEntry instance
	 * @throws PortalException
	 */
	@Override
	public com.liferay.account.model.AccountEntry addAccountEntry(
			long userId, long parentAccountEntryId, String name,
			String description, String[] domains, String emailAddress,
			byte[] logoBytes, String taxIdNumber, String type, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryLocalService.addAccountEntry(
			userId, parentAccountEntryId, name, description, domains,
			emailAddress, logoBytes, taxIdNumber, type, status, serviceContext);
	}

	@Override
	public com.liferay.account.model.AccountEntry addOrUpdateAccountEntry(
			String externalReferenceCode, long userId,
			long parentAccountEntryId, String name, String description,
			String[] domains, String emailAddress, byte[] logoBytes,
			String taxIdNumber, String type, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryLocalService.addOrUpdateAccountEntry(
			externalReferenceCode, userId, parentAccountEntryId, name,
			description, domains, emailAddress, logoBytes, taxIdNumber, type,
			status, serviceContext);
	}

	/**
	 * Creates a new account entry with the primary key. Does not add the account entry to the database.
	 *
	 * @param accountEntryId the primary key for the new account entry
	 * @return the new account entry
	 */
	@Override
	public com.liferay.account.model.AccountEntry createAccountEntry(
		long accountEntryId) {

		return _accountEntryLocalService.createAccountEntry(accountEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryLocalService.createPersistedModel(primaryKeyObj);
	}

	@Override
	public void deactivateAccountEntries(long[] accountEntryIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		_accountEntryLocalService.deactivateAccountEntries(accountEntryIds);
	}

	@Override
	public com.liferay.account.model.AccountEntry deactivateAccountEntry(
			com.liferay.account.model.AccountEntry accountEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryLocalService.deactivateAccountEntry(accountEntry);
	}

	@Override
	public com.liferay.account.model.AccountEntry deactivateAccountEntry(
			long accountEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryLocalService.deactivateAccountEntry(accountEntryId);
	}

	@Override
	public void deleteAccountEntries(long[] accountEntryIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		_accountEntryLocalService.deleteAccountEntries(accountEntryIds);
	}

	@Override
	public void deleteAccountEntriesByCompanyId(long companyId) {
		_accountEntryLocalService.deleteAccountEntriesByCompanyId(companyId);
	}

	/**
	 * Deletes the account entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AccountEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param accountEntry the account entry
	 * @return the account entry that was removed
	 * @throws PortalException
	 */
	@Override
	public com.liferay.account.model.AccountEntry deleteAccountEntry(
			com.liferay.account.model.AccountEntry accountEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryLocalService.deleteAccountEntry(accountEntry);
	}

	/**
	 * Deletes the account entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AccountEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param accountEntryId the primary key of the account entry
	 * @return the account entry that was removed
	 * @throws PortalException if a account entry with the primary key could not be found
	 */
	@Override
	public com.liferay.account.model.AccountEntry deleteAccountEntry(
			long accountEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryLocalService.deleteAccountEntry(accountEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _accountEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _accountEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _accountEntryLocalService.dynamicQuery();
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

		return _accountEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.account.model.impl.AccountEntryModelImpl</code>.
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

		return _accountEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.account.model.impl.AccountEntryModelImpl</code>.
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

		return _accountEntryLocalService.dynamicQuery(
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

		return _accountEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _accountEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.account.model.AccountEntry fetchAccountEntry(
		long accountEntryId) {

		return _accountEntryLocalService.fetchAccountEntry(accountEntryId);
	}

	/**
	 * Returns the account entry with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the account entry's external reference code
	 * @return the matching account entry, or <code>null</code> if a matching account entry could not be found
	 */
	@Override
	public com.liferay.account.model.AccountEntry
		fetchAccountEntryByExternalReferenceCode(
			long companyId, String externalReferenceCode) {

		return _accountEntryLocalService.
			fetchAccountEntryByExternalReferenceCode(
				companyId, externalReferenceCode);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #fetchAccountEntryByExternalReferenceCode(long, String)}
	 */
	@Deprecated
	@Override
	public com.liferay.account.model.AccountEntry
		fetchAccountEntryByReferenceCode(
			long companyId, String externalReferenceCode) {

		return _accountEntryLocalService.fetchAccountEntryByReferenceCode(
			companyId, externalReferenceCode);
	}

	/**
	 * Returns the account entry with the matching UUID and company.
	 *
	 * @param uuid the account entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching account entry, or <code>null</code> if a matching account entry could not be found
	 */
	@Override
	public com.liferay.account.model.AccountEntry
		fetchAccountEntryByUuidAndCompanyId(String uuid, long companyId) {

		return _accountEntryLocalService.fetchAccountEntryByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public com.liferay.account.model.AccountEntry fetchPersonAccountEntry(
		long userId) {

		return _accountEntryLocalService.fetchPersonAccountEntry(userId);
	}

	@Override
	public com.liferay.account.model.AccountEntry fetchUserAccountEntry(
		long userId, long accountEntryId) {

		return _accountEntryLocalService.fetchUserAccountEntry(
			userId, accountEntryId);
	}

	/**
	 * Returns a range of all the account entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.account.model.impl.AccountEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account entries
	 * @param end the upper bound of the range of account entries (not inclusive)
	 * @return the range of account entries
	 */
	@Override
	public java.util.List<com.liferay.account.model.AccountEntry>
		getAccountEntries(int start, int end) {

		return _accountEntryLocalService.getAccountEntries(start, end);
	}

	@Override
	public java.util.List<com.liferay.account.model.AccountEntry>
		getAccountEntries(
			long companyId, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.account.model.AccountEntry> orderByComparator) {

		return _accountEntryLocalService.getAccountEntries(
			companyId, status, start, end, orderByComparator);
	}

	/**
	 * Returns the number of account entries.
	 *
	 * @return the number of account entries
	 */
	@Override
	public int getAccountEntriesCount() {
		return _accountEntryLocalService.getAccountEntriesCount();
	}

	@Override
	public int getAccountEntriesCount(long companyId, int status) {
		return _accountEntryLocalService.getAccountEntriesCount(
			companyId, status);
	}

	/**
	 * Returns the account entry with the primary key.
	 *
	 * @param accountEntryId the primary key of the account entry
	 * @return the account entry
	 * @throws PortalException if a account entry with the primary key could not be found
	 */
	@Override
	public com.liferay.account.model.AccountEntry getAccountEntry(
			long accountEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryLocalService.getAccountEntry(accountEntryId);
	}

	/**
	 * Returns the account entry with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the account entry's external reference code
	 * @return the matching account entry
	 * @throws PortalException if a matching account entry could not be found
	 */
	@Override
	public com.liferay.account.model.AccountEntry
			getAccountEntryByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryLocalService.getAccountEntryByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	/**
	 * Returns the account entry with the matching UUID and company.
	 *
	 * @param uuid the account entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching account entry
	 * @throws PortalException if a matching account entry could not be found
	 */
	@Override
	public com.liferay.account.model.AccountEntry
			getAccountEntryByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryLocalService.getAccountEntryByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _accountEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _accountEntryLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.account.model.AccountEntry getGuestAccountEntry(
			long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryLocalService.getGuestAccountEntry(companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _accountEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _accountEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public java.util.List<com.liferay.account.model.AccountEntry>
			getUserAccountEntries(
				long userId, Long parentAccountEntryId, String keywords,
				String[] types, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryLocalService.getUserAccountEntries(
			userId, parentAccountEntryId, keywords, types, start, end);
	}

	@Override
	public java.util.List<com.liferay.account.model.AccountEntry>
			getUserAccountEntries(
				long userId, Long parentAccountEntryId, String keywords,
				String[] types, Integer status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryLocalService.getUserAccountEntries(
			userId, parentAccountEntryId, keywords, types, status, start, end);
	}

	@Override
	public java.util.List<com.liferay.account.model.AccountEntry>
			getUserAccountEntries(
				long userId, Long parentAccountEntryId, String keywords,
				String[] types, Integer status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.account.model.AccountEntry> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryLocalService.getUserAccountEntries(
			userId, parentAccountEntryId, keywords, types, status, start, end,
			orderByComparator);
	}

	@Override
	public int getUserAccountEntriesCount(
			long userId, Long parentAccountEntryId, String keywords,
			String[] types)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryLocalService.getUserAccountEntriesCount(
			userId, parentAccountEntryId, keywords, types);
	}

	@Override
	public int getUserAccountEntriesCount(
			long userId, Long parentAccountEntryId, String keywords,
			String[] types, Integer status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryLocalService.getUserAccountEntriesCount(
			userId, parentAccountEntryId, keywords, types, status);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.account.model.AccountEntry> searchAccountEntries(
			long companyId, String keywords,
			java.util.LinkedHashMap<String, Object> params, int cur, int delta,
			String orderByField, boolean reverse) {

		return _accountEntryLocalService.searchAccountEntries(
			companyId, keywords, params, cur, delta, orderByField, reverse);
	}

	/**
	 * Updates the account entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AccountEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param accountEntry the account entry
	 * @return the account entry that was updated
	 */
	@Override
	public com.liferay.account.model.AccountEntry updateAccountEntry(
		com.liferay.account.model.AccountEntry accountEntry) {

		return _accountEntryLocalService.updateAccountEntry(accountEntry);
	}

	/**
	 * @param accountEntryId the account's ID. Required.
	 * @param parentAccountEntryId the parent account's ID. Use 0 if there
	 is no parent account.
	 * @param name the account's name. Required.
	 * @param description the account's description. Optional.
	 * @param domains an array of email domains associated with the account.
	 This will define which users can be managed by the account
	 administrator, as well as optionally restricting membership to
	 users with matching email address domains. Optional.
	 * @param emailAddress the email address associated with this account.
	 Optional.
	 * @param logoBytes the account's logo bytes. Optional.
	 * @param taxIdNumber the account's tax ID number. Optional.
	 * @param status the account's workflow status. If workflow is enabled for
	 the AccountEntry model, this parameter is ignored.
	 * @param serviceContext the service context object. This object can contain
	 information about the account's asset tags and categories, and
	 custom fields.
	 * @return the updated AccountEntry instance
	 * @throws PortalException
	 */
	@Override
	public com.liferay.account.model.AccountEntry updateAccountEntry(
			long accountEntryId, long parentAccountEntryId, String name,
			String description, boolean deleteLogo, String[] domains,
			String emailAddress, byte[] logoBytes, String taxIdNumber,
			int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryLocalService.updateAccountEntry(
			accountEntryId, parentAccountEntryId, name, description, deleteLogo,
			domains, emailAddress, logoBytes, taxIdNumber, status,
			serviceContext);
	}

	@Override
	public com.liferay.account.model.AccountEntry updateDefaultBillingAddressId(
			long accountEntryId, long addressId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryLocalService.updateDefaultBillingAddressId(
			accountEntryId, addressId);
	}

	@Override
	public com.liferay.account.model.AccountEntry
			updateDefaultShippingAddressId(long accountEntryId, long addressId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryLocalService.updateDefaultShippingAddressId(
			accountEntryId, addressId);
	}

	@Override
	public com.liferay.account.model.AccountEntry updateDomains(
			long accountEntryId, String[] domains)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryLocalService.updateDomains(accountEntryId, domains);
	}

	@Override
	public com.liferay.account.model.AccountEntry updateExternalReferenceCode(
			com.liferay.account.model.AccountEntry accountEntry,
			String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryLocalService.updateExternalReferenceCode(
			accountEntry, externalReferenceCode);
	}

	@Override
	public com.liferay.account.model.AccountEntry updateExternalReferenceCode(
			long accountEntryId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryLocalService.updateExternalReferenceCode(
			accountEntryId, externalReferenceCode);
	}

	@Override
	public com.liferay.account.model.AccountEntry updateRestrictMembership(
			long accountEntryId, boolean restrictMembership)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryLocalService.updateRestrictMembership(
			accountEntryId, restrictMembership);
	}

	@Override
	public com.liferay.account.model.AccountEntry updateStatus(
			com.liferay.account.model.AccountEntry accountEntry, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryLocalService.updateStatus(accountEntry, status);
	}

	@Override
	public com.liferay.account.model.AccountEntry updateStatus(
			long accountEntryId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryLocalService.updateStatus(accountEntryId, status);
	}

	@Override
	public com.liferay.account.model.AccountEntry updateStatus(
			long userId, long accountEntryId, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext,
			java.util.Map<String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryLocalService.updateStatus(
			userId, accountEntryId, status, serviceContext, workflowContext);
	}

	@Override
	public AccountEntryLocalService getWrappedService() {
		return _accountEntryLocalService;
	}

	@Override
	public void setWrappedService(
		AccountEntryLocalService accountEntryLocalService) {

		_accountEntryLocalService = accountEntryLocalService;
	}

	private AccountEntryLocalService _accountEntryLocalService;

}