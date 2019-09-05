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

import org.osgi.annotation.versioning.ProviderType;
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
@ProviderType
public class AccountEntryUserRelLocalServiceUtil {

	/*
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

	/**
	 * Creates a new account entry user rel with the primary key. Does not add the account entry user rel to the database.
	 *
	 * @param accountEntryUserRelPK the primary key for the new account entry user rel
	 * @return the new account entry user rel
	 */
	public static com.liferay.account.model.AccountEntryUserRel
		createAccountEntryUserRel(
			com.liferay.account.service.persistence.AccountEntryUserRelPK
				accountEntryUserRelPK) {

		return getService().createAccountEntryUserRel(accountEntryUserRelPK);
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
	 * @param accountEntryUserRelPK the primary key of the account entry user rel
	 * @return the account entry user rel that was removed
	 * @throws PortalException if a account entry user rel with the primary key could not be found
	 */
	public static com.liferay.account.model.AccountEntryUserRel
			deleteAccountEntryUserRel(
				com.liferay.account.service.persistence.AccountEntryUserRelPK
					accountEntryUserRelPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteAccountEntryUserRel(accountEntryUserRelPK);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.account.model.impl.AccountEntryUserRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.account.model.impl.AccountEntryUserRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		fetchAccountEntryUserRel(
			com.liferay.account.service.persistence.AccountEntryUserRelPK
				accountEntryUserRelPK) {

		return getService().fetchAccountEntryUserRel(accountEntryUserRelPK);
	}

	/**
	 * Returns the account entry user rel with the primary key.
	 *
	 * @param accountEntryUserRelPK the primary key of the account entry user rel
	 * @return the account entry user rel
	 * @throws PortalException if a account entry user rel with the primary key could not be found
	 */
	public static com.liferay.account.model.AccountEntryUserRel
			getAccountEntryUserRel(
				com.liferay.account.service.persistence.AccountEntryUserRelPK
					accountEntryUserRelPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAccountEntryUserRel(accountEntryUserRelPK);
	}

	/**
	 * Returns a range of all the account entry user rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.account.model.impl.AccountEntryUserRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	/**
	 * Returns the number of account entry user rels.
	 *
	 * @return the number of account entry user rels
	 */
	public static int getAccountEntryUserRelsCount() {
		return getService().getAccountEntryUserRelsCount();
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