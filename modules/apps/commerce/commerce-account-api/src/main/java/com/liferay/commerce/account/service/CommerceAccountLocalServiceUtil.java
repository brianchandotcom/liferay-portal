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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CommerceAccount. This utility wraps
 * <code>com.liferay.commerce.account.service.impl.CommerceAccountLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see CommerceAccountLocalService
 * @generated
 */
public class CommerceAccountLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.account.service.impl.CommerceAccountLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.account.model.CommerceAccount
			addBusinessCommerceAccount(
				String name, long parentCommerceAccountId, String email,
				String taxId, boolean active, String externalReferenceCode,
				long[] userIds, String[] emailAddresses,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addBusinessCommerceAccount(
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
	public static com.liferay.commerce.account.model.CommerceAccount
		addCommerceAccount(
			com.liferay.commerce.account.model.CommerceAccount
				commerceAccount) {

		return getService().addCommerceAccount(commerceAccount);
	}

	public static com.liferay.commerce.account.model.CommerceAccount
			addCommerceAccount(
				String name, long parentCommerceAccountId, String email,
				String taxId, int type, boolean active,
				String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommerceAccount(
			name, parentCommerceAccountId, email, taxId, type, active,
			externalReferenceCode, serviceContext);
	}

	public static com.liferay.commerce.account.model.CommerceAccount
			addPersonalCommerceAccount(
				long userId, String taxId, String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addPersonalCommerceAccount(
			userId, taxId, externalReferenceCode, serviceContext);
	}

	/**
	 * Creates a new commerce account with the primary key. Does not add the commerce account to the database.
	 *
	 * @param commerceAccountId the primary key for the new commerce account
	 * @return the new commerce account
	 */
	public static com.liferay.commerce.account.model.CommerceAccount
		createCommerceAccount(long commerceAccountId) {

		return getService().createCommerceAccount(commerceAccountId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			createPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().createPersistedModel(primaryKeyObj);
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
	public static com.liferay.commerce.account.model.CommerceAccount
			deleteCommerceAccount(
				com.liferay.commerce.account.model.CommerceAccount
					commerceAccount)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCommerceAccount(commerceAccount);
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
	public static com.liferay.commerce.account.model.CommerceAccount
			deleteCommerceAccount(long commerceAccountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCommerceAccount(commerceAccountId);
	}

	public static void deleteCommerceAccounts(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCommerceAccounts(companyId);
	}

	public static void deleteLogo(long commerceAccountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteLogo(commerceAccountId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return getService().dslQuery(dslQuery);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQuery(dynamicQuery);
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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

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
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.commerce.account.model.CommerceAccount
		fetchByExternalReferenceCode(
			long companyId, String externalReferenceCode) {

		return getService().fetchByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	public static com.liferay.commerce.account.model.CommerceAccount
		fetchCommerceAccount(long commerceAccountId) {

		return getService().fetchCommerceAccount(commerceAccountId);
	}

	/**
	 * Returns the commerce account with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the commerce account's external reference code
	 * @return the matching commerce account, or <code>null</code> if a matching commerce account could not be found
	 */
	public static com.liferay.commerce.account.model.CommerceAccount
		fetchCommerceAccountByReferenceCode(
			long companyId, String externalReferenceCode) {

		return getService().fetchCommerceAccountByReferenceCode(
			companyId, externalReferenceCode);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the commerce account with the primary key.
	 *
	 * @param commerceAccountId the primary key of the commerce account
	 * @return the commerce account
	 * @throws PortalException if a commerce account with the primary key could not be found
	 */
	public static com.liferay.commerce.account.model.CommerceAccount
			getCommerceAccount(long commerceAccountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceAccount(commerceAccountId);
	}

	public static com.liferay.commerce.account.model.CommerceAccount
			getCommerceAccount(long userId, long commerceAccountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceAccount(userId, commerceAccountId);
	}

	public static com.liferay.portal.kernel.model.Group getCommerceAccountGroup(
			long commerceAccountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceAccountGroup(commerceAccountId);
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
	public static java.util.List
		<com.liferay.commerce.account.model.CommerceAccount>
			getCommerceAccounts(int start, int end) {

		return getService().getCommerceAccounts(start, end);
	}

	/**
	 * Returns the number of commerce accounts.
	 *
	 * @return the number of commerce accounts
	 */
	public static int getCommerceAccountsCount() {
		return getService().getCommerceAccountsCount();
	}

	public static com.liferay.commerce.account.model.CommerceAccount
			getGuestCommerceAccount(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getGuestCommerceAccount(companyId);
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
	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static com.liferay.commerce.account.model.CommerceAccount
			getPersonalCommerceAccount(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersonalCommerceAccount(userId);
	}

	public static java.util.List
		<com.liferay.commerce.account.model.CommerceAccount>
				getUserCommerceAccounts(
					long userId, Long parentCommerceAccountId,
					int commerceSiteType, String keywords, Boolean active,
					int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getUserCommerceAccounts(
			userId, parentCommerceAccountId, commerceSiteType, keywords, active,
			start, end);
	}

	public static java.util.List
		<com.liferay.commerce.account.model.CommerceAccount>
				getUserCommerceAccounts(
					long userId, Long parentCommerceAccountId,
					int commerceSiteType, String keywords, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getUserCommerceAccounts(
			userId, parentCommerceAccountId, commerceSiteType, keywords, start,
			end);
	}

	public static int getUserCommerceAccountsCount(
			long userId, Long parentCommerceAccountId, int commerceSiteType,
			String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getUserCommerceAccountsCount(
			userId, parentCommerceAccountId, commerceSiteType, keywords);
	}

	public static int getUserCommerceAccountsCount(
			long userId, Long parentCommerceAccountId, int commerceSiteType,
			String keywords, Boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getUserCommerceAccountsCount(
			userId, parentCommerceAccountId, commerceSiteType, keywords,
			active);
	}

	public static java.util.List
		<com.liferay.commerce.account.model.CommerceAccount>
				searchCommerceAccounts(
					long companyId, long parentCommerceAccountId,
					String keywords, int type, Boolean active, int start,
					int end, com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().searchCommerceAccounts(
			companyId, parentCommerceAccountId, keywords, type, active, start,
			end, sort);
	}

	public static int searchCommerceAccountsCount(
			long companyId, long parentCommerceAccountId, String keywords,
			int type, Boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().searchCommerceAccountsCount(
			companyId, parentCommerceAccountId, keywords, type, active);
	}

	public static com.liferay.commerce.account.model.CommerceAccount setActive(
			long commerceAccountId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().setActive(commerceAccountId, active);
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
	public static com.liferay.commerce.account.model.CommerceAccount
		updateCommerceAccount(
			com.liferay.commerce.account.model.CommerceAccount
				commerceAccount) {

		return getService().updateCommerceAccount(commerceAccount);
	}

	public static com.liferay.commerce.account.model.CommerceAccount
			updateCommerceAccount(
				long commerceAccountId, String name, boolean logo,
				byte[] logoBytes, String email, String taxId, boolean active,
				long defaultBillingAddressId, long defaultShippingAddressId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceAccount(
			commerceAccountId, name, logo, logoBytes, email, taxId, active,
			defaultBillingAddressId, defaultShippingAddressId, serviceContext);
	}

	public static com.liferay.commerce.account.model.CommerceAccount
			updateCommerceAccount(
				long commerceAccountId, String name, boolean logo,
				byte[] logoBytes, String email, String taxId, boolean active,
				long defaultBillingAddressId, long defaultShippingAddressId,
				String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceAccount(
			commerceAccountId, name, logo, logoBytes, email, taxId, active,
			defaultBillingAddressId, defaultShippingAddressId,
			externalReferenceCode, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), pass Default Billing/Shipping Ids
	 */
	@Deprecated
	public static com.liferay.commerce.account.model.CommerceAccount
			updateCommerceAccount(
				long commerceAccountId, String name, boolean logo,
				byte[] logoBytes, String email, String taxId, boolean active,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceAccount(
			commerceAccountId, name, logo, logoBytes, email, taxId, active,
			serviceContext);
	}

	public static com.liferay.commerce.account.model.CommerceAccount
			updateDefaultBillingAddress(
				long commerceAccountId, long commerceAddressId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateDefaultBillingAddress(
			commerceAccountId, commerceAddressId);
	}

	public static com.liferay.commerce.account.model.CommerceAccount
			updateDefaultShippingAddress(
				long commerceAccountId, long commerceAddressId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateDefaultShippingAddress(
			commerceAccountId, commerceAddressId);
	}

	public static com.liferay.commerce.account.model.CommerceAccount
			updateStatus(
				long userId, long commerceAccountId, int status,
				com.liferay.portal.kernel.service.ServiceContext serviceContext,
				java.util.Map<String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateStatus(
			userId, commerceAccountId, status, serviceContext, workflowContext);
	}

	public static com.liferay.commerce.account.model.CommerceAccount
			upsertCommerceAccount(
				String name, long parentCommerceAccountId, boolean logo,
				byte[] logoBytes, String email, String taxId, int type,
				boolean active, String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().upsertCommerceAccount(
			name, parentCommerceAccountId, logo, logoBytes, email, taxId, type,
			active, externalReferenceCode, serviceContext);
	}

	public static CommerceAccountLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceAccountLocalService, CommerceAccountLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceAccountLocalService.class);

		ServiceTracker<CommerceAccountLocalService, CommerceAccountLocalService>
			serviceTracker =
				new ServiceTracker
					<CommerceAccountLocalService, CommerceAccountLocalService>(
						bundle.getBundleContext(),
						CommerceAccountLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}