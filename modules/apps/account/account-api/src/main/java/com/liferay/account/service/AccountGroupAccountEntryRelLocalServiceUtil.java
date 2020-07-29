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
 * Provides the local service utility for AccountGroupAccountEntryRel. This utility wraps
 * <code>com.liferay.account.service.impl.AccountGroupAccountEntryRelLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see AccountGroupAccountEntryRelLocalService
 * @generated
 */
public class AccountGroupAccountEntryRelLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.account.service.impl.AccountGroupAccountEntryRelLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the account group account entry rel to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AccountGroupAccountEntryRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param accountGroupAccountEntryRel the account group account entry rel
	 * @return the account group account entry rel that was added
	 */
	public static com.liferay.account.model.AccountGroupAccountEntryRel
		addAccountGroupAccountEntryRel(
			com.liferay.account.model.AccountGroupAccountEntryRel
				accountGroupAccountEntryRel) {

		return getService().addAccountGroupAccountEntryRel(
			accountGroupAccountEntryRel);
	}

	public static com.liferay.account.model.AccountGroupAccountEntryRel
			addAccountGroupAccountEntryRel(
				long accountGroupId, long accountEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addAccountGroupAccountEntryRel(
			accountGroupId, accountEntryId);
	}

	public static void addAccountGroupAccountEntryRels(
			long accountGroupId, long[] accountEntryIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addAccountGroupAccountEntryRels(
			accountGroupId, accountEntryIds);
	}

	/**
	 * Creates a new account group account entry rel with the primary key. Does not add the account group account entry rel to the database.
	 *
	 * @param AccountGroupAccountEntryRelId the primary key for the new account group account entry rel
	 * @return the new account group account entry rel
	 */
	public static com.liferay.account.model.AccountGroupAccountEntryRel
		createAccountGroupAccountEntryRel(long AccountGroupAccountEntryRelId) {

		return getService().createAccountGroupAccountEntryRel(
			AccountGroupAccountEntryRelId);
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
	 * Deletes the account group account entry rel from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AccountGroupAccountEntryRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param accountGroupAccountEntryRel the account group account entry rel
	 * @return the account group account entry rel that was removed
	 */
	public static com.liferay.account.model.AccountGroupAccountEntryRel
		deleteAccountGroupAccountEntryRel(
			com.liferay.account.model.AccountGroupAccountEntryRel
				accountGroupAccountEntryRel) {

		return getService().deleteAccountGroupAccountEntryRel(
			accountGroupAccountEntryRel);
	}

	/**
	 * Deletes the account group account entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AccountGroupAccountEntryRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param AccountGroupAccountEntryRelId the primary key of the account group account entry rel
	 * @return the account group account entry rel that was removed
	 * @throws PortalException if a account group account entry rel with the primary key could not be found
	 */
	public static com.liferay.account.model.AccountGroupAccountEntryRel
			deleteAccountGroupAccountEntryRel(
				long AccountGroupAccountEntryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteAccountGroupAccountEntryRel(
			AccountGroupAccountEntryRelId);
	}

	public static void deleteAccountGroupAccountEntryRels(
			long accountGroupId, long[] accountEntryIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteAccountGroupAccountEntryRels(
			accountGroupId, accountEntryIds);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.account.model.impl.AccountGroupAccountEntryRelModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.account.model.impl.AccountGroupAccountEntryRelModelImpl</code>.
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

	public static com.liferay.account.model.AccountGroupAccountEntryRel
		fetchAccountGroupAccountEntryRel(long AccountGroupAccountEntryRelId) {

		return getService().fetchAccountGroupAccountEntryRel(
			AccountGroupAccountEntryRelId);
	}

	public static com.liferay.account.model.AccountGroupAccountEntryRel
		fetchAccountGroupAccountEntryRel(
			long accountGroupId, long accountEntryId) {

		return getService().fetchAccountGroupAccountEntryRel(
			accountGroupId, accountEntryId);
	}

	/**
	 * Returns the account group account entry rel with the primary key.
	 *
	 * @param AccountGroupAccountEntryRelId the primary key of the account group account entry rel
	 * @return the account group account entry rel
	 * @throws PortalException if a account group account entry rel with the primary key could not be found
	 */
	public static com.liferay.account.model.AccountGroupAccountEntryRel
			getAccountGroupAccountEntryRel(long AccountGroupAccountEntryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAccountGroupAccountEntryRel(
			AccountGroupAccountEntryRelId);
	}

	/**
	 * Returns a range of all the account group account entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.account.model.impl.AccountGroupAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account group account entry rels
	 * @param end the upper bound of the range of account group account entry rels (not inclusive)
	 * @return the range of account group account entry rels
	 */
	public static java.util.List
		<com.liferay.account.model.AccountGroupAccountEntryRel>
			getAccountGroupAccountEntryRels(int start, int end) {

		return getService().getAccountGroupAccountEntryRels(start, end);
	}

	public static java.util.List
		<com.liferay.account.model.AccountGroupAccountEntryRel>
			getAccountGroupAccountEntryRelsByAccountEntryId(
				long accountEntryId) {

		return getService().getAccountGroupAccountEntryRelsByAccountEntryId(
			accountEntryId);
	}

	public static java.util.List
		<com.liferay.account.model.AccountGroupAccountEntryRel>
			getAccountGroupAccountEntryRelsByAccountGroupId(
				long accountGroupId) {

		return getService().getAccountGroupAccountEntryRelsByAccountGroupId(
			accountGroupId);
	}

	/**
	 * Returns the number of account group account entry rels.
	 *
	 * @return the number of account group account entry rels
	 */
	public static int getAccountGroupAccountEntryRelsCount() {
		return getService().getAccountGroupAccountEntryRelsCount();
	}

	public static long getAccountGroupAccountEntryRelsCountByAccountGroupId(
		long accountGroupId) {

		return getService().
			getAccountGroupAccountEntryRelsCountByAccountGroupId(
				accountGroupId);
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

	/**
	 * Updates the account group account entry rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AccountGroupAccountEntryRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param accountGroupAccountEntryRel the account group account entry rel
	 * @return the account group account entry rel that was updated
	 */
	public static com.liferay.account.model.AccountGroupAccountEntryRel
		updateAccountGroupAccountEntryRel(
			com.liferay.account.model.AccountGroupAccountEntryRel
				accountGroupAccountEntryRel) {

		return getService().updateAccountGroupAccountEntryRel(
			accountGroupAccountEntryRel);
	}

	public static AccountGroupAccountEntryRelLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<AccountGroupAccountEntryRelLocalService,
		 AccountGroupAccountEntryRelLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			AccountGroupAccountEntryRelLocalService.class);

		ServiceTracker
			<AccountGroupAccountEntryRelLocalService,
			 AccountGroupAccountEntryRelLocalService> serviceTracker =
				new ServiceTracker
					<AccountGroupAccountEntryRelLocalService,
					 AccountGroupAccountEntryRelLocalService>(
						 bundle.getBundleContext(),
						 AccountGroupAccountEntryRelLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}