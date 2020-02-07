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
 * Provides the local service utility for AccountRole. This utility wraps
 * <code>com.liferay.account.service.impl.AccountRoleLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see AccountRoleLocalService
 * @generated
 */
public class AccountRoleLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.account.service.impl.AccountRoleLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the account role to the database. Also notifies the appropriate model listeners.
	 *
	 * @param accountRole the account role
	 * @return the account role that was added
	 */
	public static com.liferay.account.model.AccountRole addAccountRole(
		com.liferay.account.model.AccountRole accountRole) {

		return getService().addAccountRole(accountRole);
	}

	public static com.liferay.account.model.AccountRole addAccountRole(
			long userId, long accountEntryId, String name,
			java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addAccountRole(
			userId, accountEntryId, name, titleMap, descriptionMap);
	}

	public static void associateUser(
			long accountEntryId, long accountRoleId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().associateUser(accountEntryId, accountRoleId, userId);
	}

	/**
	 * Creates a new account role with the primary key. Does not add the account role to the database.
	 *
	 * @param accountRoleId the primary key for the new account role
	 * @return the new account role
	 */
	public static com.liferay.account.model.AccountRole createAccountRole(
		long accountRoleId) {

		return getService().createAccountRole(accountRoleId);
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
	 * Deletes the account role from the database. Also notifies the appropriate model listeners.
	 *
	 * @param accountRole the account role
	 * @return the account role that was removed
	 * @throws PortalException
	 */
	public static com.liferay.account.model.AccountRole deleteAccountRole(
			com.liferay.account.model.AccountRole accountRole)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteAccountRole(accountRole);
	}

	/**
	 * Deletes the account role with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param accountRoleId the primary key of the account role
	 * @return the account role that was removed
	 * @throws PortalException if a account role with the primary key could not be found
	 */
	public static com.liferay.account.model.AccountRole deleteAccountRole(
			long accountRoleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteAccountRole(accountRoleId);
	}

	public static void deleteAccountRolesByCompanyId(long companyId) {
		getService().deleteAccountRolesByCompanyId(companyId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.account.model.impl.AccountRoleModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.account.model.impl.AccountRoleModelImpl</code>.
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

	public static com.liferay.account.model.AccountRole fetchAccountRole(
		long accountRoleId) {

		return getService().fetchAccountRole(accountRoleId);
	}

	public static com.liferay.account.model.AccountRole
		fetchAccountRoleByRoleId(long roleId) {

		return getService().fetchAccountRoleByRoleId(roleId);
	}

	/**
	 * Returns the account role with the primary key.
	 *
	 * @param accountRoleId the primary key of the account role
	 * @return the account role
	 * @throws PortalException if a account role with the primary key could not be found
	 */
	public static com.liferay.account.model.AccountRole getAccountRole(
			long accountRoleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAccountRole(accountRoleId);
	}

	public static com.liferay.account.model.AccountRole getAccountRoleByRoleId(
			long roleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAccountRoleByRoleId(roleId);
	}

	/**
	 * Returns a range of all the account roles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.account.model.impl.AccountRoleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account roles
	 * @param end the upper bound of the range of account roles (not inclusive)
	 * @return the range of account roles
	 */
	public static java.util.List<com.liferay.account.model.AccountRole>
		getAccountRoles(int start, int end) {

		return getService().getAccountRoles(start, end);
	}

	public static java.util.List<com.liferay.account.model.AccountRole>
			getAccountRoles(long accountEntryId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAccountRoles(accountEntryId, userId);
	}

	public static java.util.List<com.liferay.account.model.AccountRole>
		getAccountRolesByAccountEntryIds(long[] accountEntryIds) {

		return getService().getAccountRolesByAccountEntryIds(accountEntryIds);
	}

	/**
	 * Returns the number of account roles.
	 *
	 * @return the number of account roles
	 */
	public static int getAccountRolesCount() {
		return getService().getAccountRolesCount();
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
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

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.account.model.AccountRole> searchAccountRoles(
			long accountEntryId, String keywords, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator obc) {

		return getService().searchAccountRoles(
			accountEntryId, keywords, start, end, obc);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.account.model.AccountRole> searchAccountRoles(
			long[] accountEntryIds, String keywords, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator obc) {

		return getService().searchAccountRoles(
			accountEntryIds, keywords, start, end, obc);
	}

	public static void unassociateUser(
			long accountEntryId, long accountRoleId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().unassociateUser(accountEntryId, accountRoleId, userId);
	}

	/**
	 * Updates the account role in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param accountRole the account role
	 * @return the account role that was updated
	 */
	public static com.liferay.account.model.AccountRole updateAccountRole(
		com.liferay.account.model.AccountRole accountRole) {

		return getService().updateAccountRole(accountRole);
	}

	public static AccountRoleLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<AccountRoleLocalService, AccountRoleLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(AccountRoleLocalService.class);

		ServiceTracker<AccountRoleLocalService, AccountRoleLocalService>
			serviceTracker =
				new ServiceTracker
					<AccountRoleLocalService, AccountRoleLocalService>(
						bundle.getBundleContext(),
						AccountRoleLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}