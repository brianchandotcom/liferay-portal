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
 * Provides the local service utility for AccountEntryOrganizationRel. This utility wraps
 * <code>com.liferay.account.service.impl.AccountEntryOrganizationRelLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see AccountEntryOrganizationRelLocalService
 * @generated
 */
public class AccountEntryOrganizationRelLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.account.service.impl.AccountEntryOrganizationRelLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the account entry organization rel to the database. Also notifies the appropriate model listeners.
	 *
	 * @param accountEntryOrganizationRel the account entry organization rel
	 * @return the account entry organization rel that was added
	 */
	public static com.liferay.account.model.AccountEntryOrganizationRel
		addAccountEntryOrganizationRel(
			com.liferay.account.model.AccountEntryOrganizationRel
				accountEntryOrganizationRel) {

		return getService().addAccountEntryOrganizationRel(
			accountEntryOrganizationRel);
	}

	public static com.liferay.account.model.AccountEntryOrganizationRel
			addAccountEntryOrganizationRel(
				long accountEntryId, long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addAccountEntryOrganizationRel(
			accountEntryId, organizationId);
	}

	public static void addAccountEntryOrganizationRels(
			long accountEntryId, long[] organizationIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addAccountEntryOrganizationRels(
			accountEntryId, organizationIds);
	}

	/**
	 * Creates a new account entry organization rel with the primary key. Does not add the account entry organization rel to the database.
	 *
	 * @param accountEntryOrganizationRelId the primary key for the new account entry organization rel
	 * @return the new account entry organization rel
	 */
	public static com.liferay.account.model.AccountEntryOrganizationRel
		createAccountEntryOrganizationRel(long accountEntryOrganizationRelId) {

		return getService().createAccountEntryOrganizationRel(
			accountEntryOrganizationRelId);
	}

	/**
	 * Deletes the account entry organization rel from the database. Also notifies the appropriate model listeners.
	 *
	 * @param accountEntryOrganizationRel the account entry organization rel
	 * @return the account entry organization rel that was removed
	 */
	public static com.liferay.account.model.AccountEntryOrganizationRel
		deleteAccountEntryOrganizationRel(
			com.liferay.account.model.AccountEntryOrganizationRel
				accountEntryOrganizationRel) {

		return getService().deleteAccountEntryOrganizationRel(
			accountEntryOrganizationRel);
	}

	/**
	 * Deletes the account entry organization rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param accountEntryOrganizationRelId the primary key of the account entry organization rel
	 * @return the account entry organization rel that was removed
	 * @throws PortalException if a account entry organization rel with the primary key could not be found
	 */
	public static com.liferay.account.model.AccountEntryOrganizationRel
			deleteAccountEntryOrganizationRel(
				long accountEntryOrganizationRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteAccountEntryOrganizationRel(
			accountEntryOrganizationRelId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.account.model.impl.AccountEntryOrganizationRelModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.account.model.impl.AccountEntryOrganizationRelModelImpl</code>.
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

	public static com.liferay.account.model.AccountEntryOrganizationRel
		fetchAccountEntryOrganizationRel(long accountEntryOrganizationRelId) {

		return getService().fetchAccountEntryOrganizationRel(
			accountEntryOrganizationRelId);
	}

	/**
	 * Returns the account entry organization rel with the primary key.
	 *
	 * @param accountEntryOrganizationRelId the primary key of the account entry organization rel
	 * @return the account entry organization rel
	 * @throws PortalException if a account entry organization rel with the primary key could not be found
	 */
	public static com.liferay.account.model.AccountEntryOrganizationRel
			getAccountEntryOrganizationRel(long accountEntryOrganizationRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAccountEntryOrganizationRel(
			accountEntryOrganizationRelId);
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
	public static java.util.List
		<com.liferay.account.model.AccountEntryOrganizationRel>
			getAccountEntryOrganizationRels(int start, int end) {

		return getService().getAccountEntryOrganizationRels(start, end);
	}

	public static java.util.List
		<com.liferay.account.model.AccountEntryOrganizationRel>
			getAccountEntryOrganizationRels(long accountEntryId) {

		return getService().getAccountEntryOrganizationRels(accountEntryId);
	}

	/**
	 * Returns the number of account entry organization rels.
	 *
	 * @return the number of account entry organization rels
	 */
	public static int getAccountEntryOrganizationRelsCount() {
		return getService().getAccountEntryOrganizationRelsCount();
	}

	public static int getAccountEntryOrganizationRelsCount(
		long accountEntryId) {

		return getService().getAccountEntryOrganizationRelsCount(
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

	public static boolean hasAccountEntryOrganizationRel(
		long accountEntryId, long organizationId) {

		return getService().hasAccountEntryOrganizationRel(
			accountEntryId, organizationId);
	}

	/**
	 * Updates the account entry organization rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param accountEntryOrganizationRel the account entry organization rel
	 * @return the account entry organization rel that was updated
	 */
	public static com.liferay.account.model.AccountEntryOrganizationRel
		updateAccountEntryOrganizationRel(
			com.liferay.account.model.AccountEntryOrganizationRel
				accountEntryOrganizationRel) {

		return getService().updateAccountEntryOrganizationRel(
			accountEntryOrganizationRel);
	}

	public static AccountEntryOrganizationRelLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<AccountEntryOrganizationRelLocalService,
		 AccountEntryOrganizationRelLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			AccountEntryOrganizationRelLocalService.class);

		ServiceTracker
			<AccountEntryOrganizationRelLocalService,
			 AccountEntryOrganizationRelLocalService> serviceTracker =
				new ServiceTracker
					<AccountEntryOrganizationRelLocalService,
					 AccountEntryOrganizationRelLocalService>(
						 bundle.getBundleContext(),
						 AccountEntryOrganizationRelLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}