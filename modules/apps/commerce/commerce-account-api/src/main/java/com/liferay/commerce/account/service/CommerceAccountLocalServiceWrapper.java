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

package com.liferay.commerce.account.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceAccountLocalService}.
 *
 * @author Marco Leo
 * @see CommerceAccountLocalService
 * @generated
 */
public class CommerceAccountLocalServiceWrapper
	implements CommerceAccountLocalService,
			   ServiceWrapper<CommerceAccountLocalService> {

	public CommerceAccountLocalServiceWrapper(
		CommerceAccountLocalService commerceAccountLocalService) {

		_commerceAccountLocalService = commerceAccountLocalService;
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			addBusinessCommerceAccount(
				String name, long parentCommerceAccountId, String email,
				String taxId, boolean active, String externalReferenceCode,
				long[] userIds, String[] emailAddresses,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.addBusinessCommerceAccount(
			name, parentCommerceAccountId, email, taxId, active,
			externalReferenceCode, userIds, emailAddresses, serviceContext);
	}

	/**
	 * Adds the commerce account to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceAccountLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceAccount the commerce account
	 * @return the commerce account that was added
	 */
	@Override
	public com.liferay.commerce.account.model.CommerceAccount
		addCommerceAccount(
			com.liferay.commerce.account.model.CommerceAccount
				commerceAccount) {

		return _commerceAccountLocalService.addCommerceAccount(commerceAccount);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			addCommerceAccount(
				String name, long parentCommerceAccountId, String email,
				String taxId, int type, boolean active,
				String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.addCommerceAccount(
			name, parentCommerceAccountId, email, taxId, type, active,
			externalReferenceCode, serviceContext);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			addOrUpdateCommerceAccount(
				String name, long parentCommerceAccountId, boolean logo,
				byte[] logoBytes, String email, String taxId, int type,
				boolean active, String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.addOrUpdateCommerceAccount(
			name, parentCommerceAccountId, logo, logoBytes, email, taxId, type,
			active, externalReferenceCode, serviceContext);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			addPersonalCommerceAccount(
				long userId, String taxId, String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.addPersonalCommerceAccount(
			userId, taxId, externalReferenceCode, serviceContext);
	}

	/**
	 * Creates a new commerce account with the primary key. Does not add the commerce account to the database.
	 *
	 * @param commerceAccountId the primary key for the new commerce account
	 * @return the new commerce account
	 */
	@Override
	public com.liferay.commerce.account.model.CommerceAccount
		createCommerceAccount(long commerceAccountId) {

		return _commerceAccountLocalService.createCommerceAccount(
			commerceAccountId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the commerce account from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceAccountLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceAccount the commerce account
	 * @return the commerce account that was removed
	 * @throws PortalException
	 */
	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			deleteCommerceAccount(
				com.liferay.commerce.account.model.CommerceAccount
					commerceAccount)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.deleteCommerceAccount(
			commerceAccount);
	}

	/**
	 * Deletes the commerce account with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceAccountLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceAccountId the primary key of the commerce account
	 * @return the commerce account that was removed
	 * @throws PortalException if a commerce account with the primary key could not be found
	 */
	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			deleteCommerceAccount(long commerceAccountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.deleteCommerceAccount(
			commerceAccountId);
	}

	@Override
	public void deleteCommerceAccounts(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceAccountLocalService.deleteCommerceAccounts(companyId);
	}

	@Override
	public void deleteLogo(long commerceAccountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceAccountLocalService.deleteLogo(commerceAccountId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _commerceAccountLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _commerceAccountLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceAccountLocalService.dynamicQuery();
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

		return _commerceAccountLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.account.model.impl.CommerceAccountModelImpl</code>.
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

		return _commerceAccountLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.account.model.impl.CommerceAccountModelImpl</code>.
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

		return _commerceAccountLocalService.dynamicQuery(
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

		return _commerceAccountLocalService.dynamicQueryCount(dynamicQuery);
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

		return _commerceAccountLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
		fetchByExternalReferenceCode(
			long companyId, String externalReferenceCode) {

		return _commerceAccountLocalService.fetchByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
		fetchCommerceAccount(long commerceAccountId) {

		return _commerceAccountLocalService.fetchCommerceAccount(
			commerceAccountId);
	}

	/**
	 * Returns the commerce account with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the commerce account's external reference code
	 * @return the matching commerce account, or <code>null</code> if a matching commerce account could not be found
	 */
	@Override
	public com.liferay.commerce.account.model.CommerceAccount
		fetchCommerceAccountByExternalReferenceCode(
			long companyId, String externalReferenceCode) {

		return _commerceAccountLocalService.
			fetchCommerceAccountByExternalReferenceCode(
				companyId, externalReferenceCode);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #fetchCommerceAccountByExternalReferenceCode(long, String)}
	 */
	@Deprecated
	@Override
	public com.liferay.commerce.account.model.CommerceAccount
		fetchCommerceAccountByReferenceCode(
			long companyId, String externalReferenceCode) {

		return _commerceAccountLocalService.fetchCommerceAccountByReferenceCode(
			companyId, externalReferenceCode);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _commerceAccountLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the commerce account with the primary key.
	 *
	 * @param commerceAccountId the primary key of the commerce account
	 * @return the commerce account
	 * @throws PortalException if a commerce account with the primary key could not be found
	 */
	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			getCommerceAccount(long commerceAccountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.getCommerceAccount(
			commerceAccountId);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			getCommerceAccount(long userId, long commerceAccountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.getCommerceAccount(
			userId, commerceAccountId);
	}

	/**
	 * Returns the commerce account with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the commerce account's external reference code
	 * @return the matching commerce account
	 * @throws PortalException if a matching commerce account could not be found
	 */
	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			getCommerceAccountByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.
			getCommerceAccountByExternalReferenceCode(
				companyId, externalReferenceCode);
	}

	@Override
	public com.liferay.portal.kernel.model.Group getCommerceAccountGroup(
			long commerceAccountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.getCommerceAccountGroup(
			commerceAccountId);
	}

	/**
	 * Returns a range of all the commerce accounts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.account.model.impl.CommerceAccountModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce accounts
	 * @param end the upper bound of the range of commerce accounts (not inclusive)
	 * @return the range of commerce accounts
	 */
	@Override
	public java.util.List<com.liferay.commerce.account.model.CommerceAccount>
		getCommerceAccounts(int start, int end) {

		return _commerceAccountLocalService.getCommerceAccounts(start, end);
	}

	/**
	 * Returns the number of commerce accounts.
	 *
	 * @return the number of commerce accounts
	 */
	@Override
	public int getCommerceAccountsCount() {
		return _commerceAccountLocalService.getCommerceAccountsCount();
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			getGuestCommerceAccount(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.getGuestCommerceAccount(companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _commerceAccountLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceAccountLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			getPersonalCommerceAccount(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.getPersonalCommerceAccount(userId);
	}

	@Override
	public java.util.List<com.liferay.commerce.account.model.CommerceAccount>
			getUserCommerceAccounts(
				long userId, Long parentCommerceAccountId, int commerceSiteType,
				String keywords, Boolean active, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.getUserCommerceAccounts(
			userId, parentCommerceAccountId, commerceSiteType, keywords, active,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.account.model.CommerceAccount>
			getUserCommerceAccounts(
				long userId, Long parentCommerceAccountId, int commerceSiteType,
				String keywords, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.getUserCommerceAccounts(
			userId, parentCommerceAccountId, commerceSiteType, keywords, start,
			end);
	}

	@Override
	public int getUserCommerceAccountsCount(
			long userId, Long parentCommerceAccountId, int commerceSiteType,
			String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.getUserCommerceAccountsCount(
			userId, parentCommerceAccountId, commerceSiteType, keywords);
	}

	@Override
	public int getUserCommerceAccountsCount(
			long userId, Long parentCommerceAccountId, int commerceSiteType,
			String keywords, Boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.getUserCommerceAccountsCount(
			userId, parentCommerceAccountId, commerceSiteType, keywords,
			active);
	}

	@Override
	public java.util.List<com.liferay.commerce.account.model.CommerceAccount>
			searchCommerceAccounts(
				long companyId, long parentCommerceAccountId, String keywords,
				int type, Boolean active, int start, int end,
				com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.searchCommerceAccounts(
			companyId, parentCommerceAccountId, keywords, type, active, start,
			end, sort);
	}

	@Override
	public int searchCommerceAccountsCount(
			long companyId, long parentCommerceAccountId, String keywords,
			int type, Boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.searchCommerceAccountsCount(
			companyId, parentCommerceAccountId, keywords, type, active);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount setActive(
			long commerceAccountId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.setActive(
			commerceAccountId, active);
	}

	/**
	 * Updates the commerce account in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceAccountLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceAccount the commerce account
	 * @return the commerce account that was updated
	 */
	@Override
	public com.liferay.commerce.account.model.CommerceAccount
		updateCommerceAccount(
			com.liferay.commerce.account.model.CommerceAccount
				commerceAccount) {

		return _commerceAccountLocalService.updateCommerceAccount(
			commerceAccount);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			updateCommerceAccount(
				long commerceAccountId, String name, boolean logo,
				byte[] logoBytes, String email, String taxId, boolean active,
				long defaultBillingAddressId, long defaultShippingAddressId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.updateCommerceAccount(
			commerceAccountId, name, logo, logoBytes, email, taxId, active,
			defaultBillingAddressId, defaultShippingAddressId, serviceContext);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			updateCommerceAccount(
				long commerceAccountId, String name, boolean logo,
				byte[] logoBytes, String email, String taxId, boolean active,
				long defaultBillingAddressId, long defaultShippingAddressId,
				String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.updateCommerceAccount(
			commerceAccountId, name, logo, logoBytes, email, taxId, active,
			defaultBillingAddressId, defaultShippingAddressId,
			externalReferenceCode, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), pass Default Billing/Shipping Ids
	 */
	@Deprecated
	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			updateCommerceAccount(
				long commerceAccountId, String name, boolean logo,
				byte[] logoBytes, String email, String taxId, boolean active,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.updateCommerceAccount(
			commerceAccountId, name, logo, logoBytes, email, taxId, active,
			serviceContext);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			updateDefaultBillingAddress(
				long commerceAccountId, long commerceAddressId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.updateDefaultBillingAddress(
			commerceAccountId, commerceAddressId);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			updateDefaultShippingAddress(
				long commerceAccountId, long commerceAddressId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.updateDefaultShippingAddress(
			commerceAccountId, commerceAddressId);
	}

	/**
	 * @bridged
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public com.liferay.commerce.account.model.CommerceAccount updateStatus(
			long userId, long commerceAccountId, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext,
			java.util.Map<String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.updateStatus(
			userId, commerceAccountId, status, serviceContext, workflowContext);
	}

	@Override
	public CommerceAccountLocalService getWrappedService() {
		return _commerceAccountLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceAccountLocalService commerceAccountLocalService) {

		_commerceAccountLocalService = commerceAccountLocalService;
	}

	private CommerceAccountLocalService _commerceAccountLocalService;

}