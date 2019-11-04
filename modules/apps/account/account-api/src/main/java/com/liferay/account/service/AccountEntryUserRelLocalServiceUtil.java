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
 * Provides the local service utility for AccountEntryUserRel. This utility wraps
 * <code>com.liferay.account.service.impl.AccountEntryUserRelLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see AccountEntryUserRelLocalService
 * @generated
 */
public class AccountEntryUserRelLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.account.service.impl.AccountEntryUserRelLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the account entry user rel to the database. Also notifies the appropriate model listeners.
	 *
	 * @param accountEntryUserRel the account entry user rel
	 * @return the account entry user rel that was added
	 */
	public static com.liferay.account.model.AccountEntryUserRel
		addAccountEntryUserRel(
			com.liferay.account.model.AccountEntryUserRel accountEntryUserRel) {

		return getService().addAccountEntryUserRel(accountEntryUserRel);
	}

	public static com.liferay.account.model.AccountEntryUserRel
			addAccountEntryUserRel(long accountEntryId, long accountUserId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addAccountEntryUserRel(
			accountEntryId, accountUserId);
	}

	public static com.liferay.account.model.AccountEntryUserRel
			addAccountEntryUserRel(
				long accountEntryId, long creatorUserId, String screenName,
				String emailAddress, java.util.Locale locale, String firstName,
				String middleName, String lastName, long prefixId,
				long suffixId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addAccountEntryUserRel(
			accountEntryId, creatorUserId, screenName, emailAddress, locale,
			firstName, middleName, lastName, prefixId, suffixId);
	}

	public static void addAccountEntryUserRels(
			long accountEntryId, long[] accountUserIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addAccountEntryUserRels(accountEntryId, accountUserIds);
	}

	/**
	 * Creates a new account entry user rel with the primary key. Does not add the account entry user rel to the database.
	 *
	 * @param accountEntryUserRelId the primary key for the new account entry user rel
	 * @return the new account entry user rel
	 */
	public static com.liferay.account.model.AccountEntryUserRel
		createAccountEntryUserRel(long accountEntryUserRelId) {

		return getService().createAccountEntryUserRel(accountEntryUserRelId);
	}

	/**
	 * Deletes the account entry user rel from the database. Also notifies the appropriate model listeners.
	 *
	 * @param accountEntryUserRel the account entry user rel
	 * @return the account entry user rel that was removed
	 */
	public static com.liferay.account.model.AccountEntryUserRel
		deleteAccountEntryUserRel(
			com.liferay.account.model.AccountEntryUserRel accountEntryUserRel) {

		return getService().deleteAccountEntryUserRel(accountEntryUserRel);
	}

	/**
	 * Deletes the account entry user rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param accountEntryUserRelId the primary key of the account entry user rel
	 * @return the account entry user rel that was removed
	 * @throws PortalException if a account entry user rel with the primary key could not be found
	 */
	public static com.liferay.account.model.AccountEntryUserRel
			deleteAccountEntryUserRel(long accountEntryUserRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteAccountEntryUserRel(accountEntryUserRelId);
	}

	public static void deleteAccountEntryUserRels(
			long accountEntryId, long[] accountUserIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteAccountEntryUserRels(accountEntryId, accountUserIds);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.account.model.impl.AccountEntryUserRelModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.account.model.impl.AccountEntryUserRelModelImpl</code>.
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

	public static com.liferay.account.model.AccountEntryUserRel
		fetchAccountEntryUserRel(long accountEntryUserRelId) {

		return getService().fetchAccountEntryUserRel(accountEntryUserRelId);
	}

	/**
	 * Returns the account entry user rel with the primary key.
	 *
	 * @param accountEntryUserRelId the primary key of the account entry user rel
	 * @return the account entry user rel
	 * @throws PortalException if a account entry user rel with the primary key could not be found
	 */
	public static com.liferay.account.model.AccountEntryUserRel
			getAccountEntryUserRel(long accountEntryUserRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAccountEntryUserRel(accountEntryUserRelId);
	}

	/**
	 * Returns a range of all the account entry user rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.account.model.impl.AccountEntryUserRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account entry user rels
	 * @param end the upper bound of the range of account entry user rels (not inclusive)
	 * @return the range of account entry user rels
	 */
	public static java.util.List<com.liferay.account.model.AccountEntryUserRel>
		getAccountEntryUserRels(int start, int end) {

		return getService().getAccountEntryUserRels(start, end);
	}

	public static java.util.List<com.liferay.account.model.AccountEntryUserRel>
		getAccountEntryUserRelsByAccountEntryId(long accountEntryId) {

		return getService().getAccountEntryUserRelsByAccountEntryId(
			accountEntryId);
	}

	public static java.util.List<com.liferay.account.model.AccountEntryUserRel>
		getAccountEntryUserRelsByAccountUserId(long accountUserId) {

		return getService().getAccountEntryUserRelsByAccountUserId(
			accountUserId);
	}

	/**
	 * Returns the number of account entry user rels.
	 *
	 * @return the number of account entry user rels
	 */
	public static int getAccountEntryUserRelsCount() {
		return getService().getAccountEntryUserRelsCount();
	}

	public static long getAccountEntryUserRelsCountByAccountEntryId(
		long accountEntryId) {

		return getService().getAccountEntryUserRelsCountByAccountEntryId(
			accountEntryId);
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

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the account entry user rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param accountEntryUserRel the account entry user rel
	 * @return the account entry user rel that was updated
	 */
	public static com.liferay.account.model.AccountEntryUserRel
		updateAccountEntryUserRel(
			com.liferay.account.model.AccountEntryUserRel accountEntryUserRel) {

		return getService().updateAccountEntryUserRel(accountEntryUserRel);
	}

	public static AccountEntryUserRelLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<AccountEntryUserRelLocalService, AccountEntryUserRelLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			AccountEntryUserRelLocalService.class);

		ServiceTracker
			<AccountEntryUserRelLocalService, AccountEntryUserRelLocalService>
				serviceTracker =
					new ServiceTracker
						<AccountEntryUserRelLocalService,
						 AccountEntryUserRelLocalService>(
							 bundle.getBundleContext(),
							 AccountEntryUserRelLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}