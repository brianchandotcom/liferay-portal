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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for AccountGroup. This utility wraps
 * <code>com.liferay.account.service.impl.AccountGroupLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see AccountGroupLocalService
 * @generated
 */
public class AccountGroupLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.account.service.impl.AccountGroupLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the account group to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AccountGroupLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param accountGroup the account group
	 * @return the account group that was added
	 */
	public static com.liferay.account.model.AccountGroup addAccountGroup(
		com.liferay.account.model.AccountGroup accountGroup) {

		return getService().addAccountGroup(accountGroup);
	}

	public static com.liferay.account.model.AccountGroup addAccountGroup(
			long userId, String description, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addAccountGroup(userId, description, name);
	}

	/**
	 * Creates a new account group with the primary key. Does not add the account group to the database.
	 *
	 * @param accountGroupId the primary key for the new account group
	 * @return the new account group
	 */
	public static com.liferay.account.model.AccountGroup createAccountGroup(
		long accountGroupId) {

		return getService().createAccountGroup(accountGroupId);
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
	 * Deletes the account group from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AccountGroupLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param accountGroup the account group
	 * @return the account group that was removed
	 */
	public static com.liferay.account.model.AccountGroup deleteAccountGroup(
		com.liferay.account.model.AccountGroup accountGroup) {

		return getService().deleteAccountGroup(accountGroup);
	}

	/**
	 * Deletes the account group with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AccountGroupLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param accountGroupId the primary key of the account group
	 * @return the account group that was removed
	 * @throws PortalException if a account group with the primary key could not be found
	 */
	public static com.liferay.account.model.AccountGroup deleteAccountGroup(
			long accountGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteAccountGroup(accountGroupId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.account.model.impl.AccountGroupModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.account.model.impl.AccountGroupModelImpl</code>.
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

	public static com.liferay.account.model.AccountGroup fetchAccountGroup(
		long accountGroupId) {

		return getService().fetchAccountGroup(accountGroupId);
	}

	/**
	 * Returns the account group with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the account group's external reference code
	 * @return the matching account group, or <code>null</code> if a matching account group could not be found
	 */
	public static com.liferay.account.model.AccountGroup
		fetchAccountGroupByReferenceCode(
			long companyId, String externalReferenceCode) {

		return getService().fetchAccountGroupByReferenceCode(
			companyId, externalReferenceCode);
	}

	/**
	 * Returns the account group with the primary key.
	 *
	 * @param accountGroupId the primary key of the account group
	 * @return the account group
	 * @throws PortalException if a account group with the primary key could not be found
	 */
	public static com.liferay.account.model.AccountGroup getAccountGroup(
			long accountGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAccountGroup(accountGroupId);
	}

	/**
	 * Returns a range of all the account groups.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.account.model.impl.AccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account groups
	 * @param end the upper bound of the range of account groups (not inclusive)
	 * @return the range of account groups
	 */
	public static java.util.List<com.liferay.account.model.AccountGroup>
		getAccountGroups(int start, int end) {

		return getService().getAccountGroups(start, end);
	}

	public static java.util.List<com.liferay.account.model.AccountGroup>
		getAccountGroups(
			long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.account.model.AccountGroup> orderByComparator) {

		return getService().getAccountGroups(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of account groups.
	 *
	 * @return the number of account groups
	 */
	public static int getAccountGroupsCount() {
		return getService().getAccountGroupsCount();
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.account.model.AccountGroup getDefaultAccountGroup(
		long companyId) {

		return getService().getDefaultAccountGroup(companyId);
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

	public static boolean hasDefaultAccountGroup(long companyId) {
		return getService().hasDefaultAccountGroup(companyId);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.account.model.AccountGroup> searchAccountGroups(
			long companyId, String keywords, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.account.model.AccountGroup> orderByComparator) {

		return getService().searchAccountGroups(
			companyId, keywords, start, end, orderByComparator);
	}

	/**
	 * Updates the account group in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AccountGroupLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param accountGroup the account group
	 * @return the account group that was updated
	 */
	public static com.liferay.account.model.AccountGroup updateAccountGroup(
		com.liferay.account.model.AccountGroup accountGroup) {

		return getService().updateAccountGroup(accountGroup);
	}

	public static com.liferay.account.model.AccountGroup updateAccountGroup(
			long accountGroupId, String description, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateAccountGroup(
			accountGroupId, description, name);
	}

	public static AccountGroupLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<AccountGroupLocalService, AccountGroupLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(AccountGroupLocalService.class);

		ServiceTracker<AccountGroupLocalService, AccountGroupLocalService>
			serviceTracker =
				new ServiceTracker
					<AccountGroupLocalService, AccountGroupLocalService>(
						bundle.getBundleContext(),
						AccountGroupLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}