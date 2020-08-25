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

package com.liferay.portal.kernel.service;

/**
 * Provides a wrapper for {@link AccountLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see AccountLocalService
 * @generated
 */
public class AccountLocalServiceWrapper
	implements AccountLocalService, ServiceWrapper<AccountLocalService> {

	public AccountLocalServiceWrapper(AccountLocalService accountLocalService) {
		_accountLocalService = accountLocalService;
	}

	/**
	 * Adds the account to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AccountLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param account the account
	 * @return the account that was added
	 */
	@Override
	public com.liferay.portal.kernel.model.Account addAccount(
		com.liferay.portal.kernel.model.Account account) {

		return _accountLocalService.addAccount(account);
	}

	/**
	 * Creates a new account with the primary key. Does not add the account to the database.
	 *
	 * @param accountId the primary key for the new account
	 * @return the new account
	 */
	@Override
	public com.liferay.portal.kernel.model.Account createAccount(
		long accountId) {

		return _accountLocalService.createAccount(accountId);
	}

	/**
	 * Deletes the account from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AccountLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param account the account
	 * @return the account that was removed
	 */
	@Override
	public com.liferay.portal.kernel.model.Account deleteAccount(
		com.liferay.portal.kernel.model.Account account) {

		return _accountLocalService.deleteAccount(account);
	}

	/**
	 * Deletes the account with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AccountLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param accountId the primary key of the account
	 * @return the account that was removed
	 * @throws PortalException if a account with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.kernel.model.Account deleteAccount(long accountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountLocalService.deleteAccount(accountId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _accountLocalService.dynamicQuery();
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

		return _accountLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.AccountModelImpl</code>.
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

		return _accountLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.AccountModelImpl</code>.
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

		return _accountLocalService.dynamicQuery(
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

		return _accountLocalService.dynamicQueryCount(dynamicQuery);
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

		return _accountLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.kernel.model.Account fetchAccount(
		long accountId) {

		return _accountLocalService.fetchAccount(accountId);
	}

	/**
	 * Returns the account with the primary key.
	 *
	 * @param accountId the primary key of the account
	 * @return the account
	 * @throws PortalException if a account with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.kernel.model.Account getAccount(long accountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountLocalService.getAccount(accountId);
	}

	@Override
	public com.liferay.portal.kernel.model.Account getAccount(
			long companyId, long accountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountLocalService.getAccount(companyId, accountId);
	}

	/**
	 * Returns a range of all the accounts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.AccountModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of accounts
	 * @param end the upper bound of the range of accounts (not inclusive)
	 * @return the range of accounts
	 */
	@Override
	public java.util.List<com.liferay.portal.kernel.model.Account> getAccounts(
		int start, int end) {

		return _accountLocalService.getAccounts(start, end);
	}

	/**
	 * Returns the number of accounts.
	 *
	 * @return the number of accounts
	 */
	@Override
	public int getAccountsCount() {
		return _accountLocalService.getAccountsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _accountLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _accountLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _accountLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the account in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AccountLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param account the account
	 * @return the account that was updated
	 */
	@Override
	public com.liferay.portal.kernel.model.Account updateAccount(
		com.liferay.portal.kernel.model.Account account) {

		return _accountLocalService.updateAccount(account);
	}

	@Override
	public AccountLocalService getWrappedService() {
		return _accountLocalService;
	}

	@Override
	public void setWrappedService(AccountLocalService accountLocalService) {
		_accountLocalService = accountLocalService;
	}

	private AccountLocalService _accountLocalService;

}