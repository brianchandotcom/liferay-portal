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
 * Provides the local service utility for AccountGroupRel. This utility wraps
 * <code>com.liferay.account.service.impl.AccountGroupRelLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see AccountGroupRelLocalService
 * @generated
 */
public class AccountGroupRelLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.account.service.impl.AccountGroupRelLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the account group rel to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AccountGroupRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param accountGroupRel the account group rel
	 * @return the account group rel that was added
	 */
	public static com.liferay.account.model.AccountGroupRel addAccountGroupRel(
		com.liferay.account.model.AccountGroupRel accountGroupRel) {

		return getService().addAccountGroupRel(accountGroupRel);
	}

	public static com.liferay.account.model.AccountGroupRel addAccountGroupRel(
			long accountGroupId, String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addAccountGroupRel(
			accountGroupId, className, classPK);
	}

	public static void addAccountGroupRels(
			long accountGroupId, String className, long[] classPKs)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addAccountGroupRels(accountGroupId, className, classPKs);
	}

	/**
	 * Creates a new account group rel with the primary key. Does not add the account group rel to the database.
	 *
	 * @param accountGroupRelId the primary key for the new account group rel
	 * @return the new account group rel
	 */
	public static com.liferay.account.model.AccountGroupRel
		createAccountGroupRel(long accountGroupRelId) {

		return getService().createAccountGroupRel(accountGroupRelId);
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
	 * Deletes the account group rel from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AccountGroupRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param accountGroupRel the account group rel
	 * @return the account group rel that was removed
	 */
	public static com.liferay.account.model.AccountGroupRel
		deleteAccountGroupRel(
			com.liferay.account.model.AccountGroupRel accountGroupRel) {

		return getService().deleteAccountGroupRel(accountGroupRel);
	}

	/**
	 * Deletes the account group rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AccountGroupRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param accountGroupRelId the primary key of the account group rel
	 * @return the account group rel that was removed
	 * @throws PortalException if a account group rel with the primary key could not be found
	 */
	public static com.liferay.account.model.AccountGroupRel
			deleteAccountGroupRel(long accountGroupRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteAccountGroupRel(accountGroupRelId);
	}

	public static void deleteAccountGroupRels(
			long accountGroupId, String className, long[] classPKs)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteAccountGroupRels(
			accountGroupId, className, classPKs);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.account.model.impl.AccountGroupRelModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.account.model.impl.AccountGroupRelModelImpl</code>.
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

	public static com.liferay.account.model.AccountGroupRel
		fetchAccountGroupRel(long accountGroupRelId) {

		return getService().fetchAccountGroupRel(accountGroupRelId);
	}

	public static com.liferay.account.model.AccountGroupRel
		fetchAccountGroupRel(
			long accountGroupId, String className, long classPK) {

		return getService().fetchAccountGroupRel(
			accountGroupId, className, classPK);
	}

	/**
	 * Returns the account group rel with the primary key.
	 *
	 * @param accountGroupRelId the primary key of the account group rel
	 * @return the account group rel
	 * @throws PortalException if a account group rel with the primary key could not be found
	 */
	public static com.liferay.account.model.AccountGroupRel getAccountGroupRel(
			long accountGroupRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAccountGroupRel(accountGroupRelId);
	}

	/**
	 * Returns a range of all the account group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.account.model.impl.AccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account group rels
	 * @param end the upper bound of the range of account group rels (not inclusive)
	 * @return the range of account group rels
	 */
	public static java.util.List<com.liferay.account.model.AccountGroupRel>
		getAccountGroupRels(int start, int end) {

		return getService().getAccountGroupRels(start, end);
	}

	public static java.util.List<com.liferay.account.model.AccountGroupRel>
		getAccountGroupRels(String className, long classPK) {

		return getService().getAccountGroupRels(className, classPK);
	}

	public static java.util.List<com.liferay.account.model.AccountGroupRel>
		getAccountGroupRelsByAccountGroupId(long accountGroupId) {

		return getService().getAccountGroupRelsByAccountGroupId(accountGroupId);
	}

	/**
	 * Returns the number of account group rels.
	 *
	 * @return the number of account group rels
	 */
	public static int getAccountGroupRelsCount() {
		return getService().getAccountGroupRelsCount();
	}

	public static long getAccountGroupRelsCountByAccountGroupId(
		long accountGroupId) {

		return getService().getAccountGroupRelsCountByAccountGroupId(
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
	 * Updates the account group rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AccountGroupRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param accountGroupRel the account group rel
	 * @return the account group rel that was updated
	 */
	public static com.liferay.account.model.AccountGroupRel
		updateAccountGroupRel(
			com.liferay.account.model.AccountGroupRel accountGroupRel) {

		return getService().updateAccountGroupRel(accountGroupRel);
	}

	public static AccountGroupRelLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<AccountGroupRelLocalService, AccountGroupRelLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			AccountGroupRelLocalService.class);

		ServiceTracker<AccountGroupRelLocalService, AccountGroupRelLocalService>
			serviceTracker =
				new ServiceTracker
					<AccountGroupRelLocalService, AccountGroupRelLocalService>(
						bundle.getBundleContext(),
						AccountGroupRelLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}