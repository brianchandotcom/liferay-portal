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
 * Provides a wrapper for {@link AccountEntryOrganizationRelLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see AccountEntryOrganizationRelLocalService
 * @generated
 */
public class AccountEntryOrganizationRelLocalServiceWrapper
	implements AccountEntryOrganizationRelLocalService,
			   ServiceWrapper<AccountEntryOrganizationRelLocalService> {

	public AccountEntryOrganizationRelLocalServiceWrapper(
		AccountEntryOrganizationRelLocalService
			accountEntryOrganizationRelLocalService) {

		_accountEntryOrganizationRelLocalService =
			accountEntryOrganizationRelLocalService;
	}

	/**
	 * Adds the account entry organization rel to the database. Also notifies the appropriate model listeners.
	 *
	 * @param accountEntryOrganizationRel the account entry organization rel
	 * @return the account entry organization rel that was added
	 */
	@Override
	public com.liferay.account.model.AccountEntryOrganizationRel
		addAccountEntryOrganizationRel(
			com.liferay.account.model.AccountEntryOrganizationRel
				accountEntryOrganizationRel) {

		return _accountEntryOrganizationRelLocalService.
			addAccountEntryOrganizationRel(accountEntryOrganizationRel);
	}

	@Override
	public com.liferay.account.model.AccountEntryOrganizationRel
			addAccountEntryOrganizationRel(
				long accountEntryId, long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryOrganizationRelLocalService.
			addAccountEntryOrganizationRel(accountEntryId, organizationId);
	}

	@Override
	public void addAccountEntryOrganizationRels(
			long accountEntryId, long[] organizationIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		_accountEntryOrganizationRelLocalService.
			addAccountEntryOrganizationRels(accountEntryId, organizationIds);
	}

	/**
	 * Creates a new account entry organization rel with the primary key. Does not add the account entry organization rel to the database.
	 *
	 * @param accountEntryOrganizationRelId the primary key for the new account entry organization rel
	 * @return the new account entry organization rel
	 */
	@Override
	public com.liferay.account.model.AccountEntryOrganizationRel
		createAccountEntryOrganizationRel(long accountEntryOrganizationRelId) {

		return _accountEntryOrganizationRelLocalService.
			createAccountEntryOrganizationRel(accountEntryOrganizationRelId);
	}

	/**
	 * Deletes the account entry organization rel from the database. Also notifies the appropriate model listeners.
	 *
	 * @param accountEntryOrganizationRel the account entry organization rel
	 * @return the account entry organization rel that was removed
	 */
	@Override
	public com.liferay.account.model.AccountEntryOrganizationRel
		deleteAccountEntryOrganizationRel(
			com.liferay.account.model.AccountEntryOrganizationRel
				accountEntryOrganizationRel) {

		return _accountEntryOrganizationRelLocalService.
			deleteAccountEntryOrganizationRel(accountEntryOrganizationRel);
	}

	/**
	 * Deletes the account entry organization rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param accountEntryOrganizationRelId the primary key of the account entry organization rel
	 * @return the account entry organization rel that was removed
	 * @throws PortalException if a account entry organization rel with the primary key could not be found
	 */
	@Override
	public com.liferay.account.model.AccountEntryOrganizationRel
			deleteAccountEntryOrganizationRel(
				long accountEntryOrganizationRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryOrganizationRelLocalService.
			deleteAccountEntryOrganizationRel(accountEntryOrganizationRelId);
	}

	@Override
	public void deleteAccountEntryOrganizationRel(
			long accountEntryId, long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_accountEntryOrganizationRelLocalService.
			deleteAccountEntryOrganizationRel(accountEntryId, organizationId);
	}

	@Override
	public void deleteAccountEntryOrganizationRels(
			long accountEntryId, long[] organizationIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		_accountEntryOrganizationRelLocalService.
			deleteAccountEntryOrganizationRels(accountEntryId, organizationIds);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryOrganizationRelLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _accountEntryOrganizationRelLocalService.dynamicQuery();
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

		return _accountEntryOrganizationRelLocalService.dynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.account.model.impl.AccountEntryOrganizationRelModelImpl</code>.
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

		return _accountEntryOrganizationRelLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.account.model.impl.AccountEntryOrganizationRelModelImpl</code>.
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

		return _accountEntryOrganizationRelLocalService.dynamicQuery(
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

		return _accountEntryOrganizationRelLocalService.dynamicQueryCount(
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

		return _accountEntryOrganizationRelLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.account.model.AccountEntryOrganizationRel
		fetchAccountEntryOrganizationRel(long accountEntryOrganizationRelId) {

		return _accountEntryOrganizationRelLocalService.
			fetchAccountEntryOrganizationRel(accountEntryOrganizationRelId);
	}

	/**
	 * Returns the account entry organization rel with the primary key.
	 *
	 * @param accountEntryOrganizationRelId the primary key of the account entry organization rel
	 * @return the account entry organization rel
	 * @throws PortalException if a account entry organization rel with the primary key could not be found
	 */
	@Override
	public com.liferay.account.model.AccountEntryOrganizationRel
			getAccountEntryOrganizationRel(long accountEntryOrganizationRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryOrganizationRelLocalService.
			getAccountEntryOrganizationRel(accountEntryOrganizationRelId);
	}

	/**
	 * Returns a range of all the account entry organization rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.account.model.impl.AccountEntryOrganizationRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account entry organization rels
	 * @param end the upper bound of the range of account entry organization rels (not inclusive)
	 * @return the range of account entry organization rels
	 */
	@Override
	public java.util.List<com.liferay.account.model.AccountEntryOrganizationRel>
		getAccountEntryOrganizationRels(int start, int end) {

		return _accountEntryOrganizationRelLocalService.
			getAccountEntryOrganizationRels(start, end);
	}

	@Override
	public java.util.List<com.liferay.account.model.AccountEntryOrganizationRel>
		getAccountEntryOrganizationRels(long accountEntryId) {

		return _accountEntryOrganizationRelLocalService.
			getAccountEntryOrganizationRels(accountEntryId);
	}

	@Override
	public java.util.List<com.liferay.account.model.AccountEntryOrganizationRel>
		getAccountEntryOrganizationRelsByOrganizationId(long organizationId) {

		return _accountEntryOrganizationRelLocalService.
			getAccountEntryOrganizationRelsByOrganizationId(organizationId);
	}

	/**
	 * Returns the number of account entry organization rels.
	 *
	 * @return the number of account entry organization rels
	 */
	@Override
	public int getAccountEntryOrganizationRelsCount() {
		return _accountEntryOrganizationRelLocalService.
			getAccountEntryOrganizationRelsCount();
	}

	@Override
	public int getAccountEntryOrganizationRelsCount(long accountEntryId) {
		return _accountEntryOrganizationRelLocalService.
			getAccountEntryOrganizationRelsCount(accountEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _accountEntryOrganizationRelLocalService.
			getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _accountEntryOrganizationRelLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _accountEntryOrganizationRelLocalService.
			getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryOrganizationRelLocalService.getPersistedModel(
			primaryKeyObj);
	}

	@Override
	public boolean hasAccountEntryOrganizationRel(
		long accountEntryId, long organizationId) {

		return _accountEntryOrganizationRelLocalService.
			hasAccountEntryOrganizationRel(accountEntryId, organizationId);
	}

	/**
	 * Updates the account entry organization rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param accountEntryOrganizationRel the account entry organization rel
	 * @return the account entry organization rel that was updated
	 */
	@Override
	public com.liferay.account.model.AccountEntryOrganizationRel
		updateAccountEntryOrganizationRel(
			com.liferay.account.model.AccountEntryOrganizationRel
				accountEntryOrganizationRel) {

		return _accountEntryOrganizationRelLocalService.
			updateAccountEntryOrganizationRel(accountEntryOrganizationRel);
	}

	@Override
	public AccountEntryOrganizationRelLocalService getWrappedService() {
		return _accountEntryOrganizationRelLocalService;
	}

	@Override
	public void setWrappedService(
		AccountEntryOrganizationRelLocalService
			accountEntryOrganizationRelLocalService) {

		_accountEntryOrganizationRelLocalService =
			accountEntryOrganizationRelLocalService;
	}

	private AccountEntryOrganizationRelLocalService
		_accountEntryOrganizationRelLocalService;

}