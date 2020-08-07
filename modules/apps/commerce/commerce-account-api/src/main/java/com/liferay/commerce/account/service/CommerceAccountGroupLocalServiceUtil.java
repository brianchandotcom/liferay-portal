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
 * Provides the local service utility for CommerceAccountGroup. This utility wraps
 * <code>com.liferay.commerce.account.service.impl.CommerceAccountGroupLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see CommerceAccountGroupLocalService
 * @generated
 */
public class CommerceAccountGroupLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.account.service.impl.CommerceAccountGroupLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the commerce account group to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceAccountGroupLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceAccountGroup the commerce account group
	 * @return the commerce account group that was added
	 */
	public static com.liferay.commerce.account.model.CommerceAccountGroup
		addCommerceAccountGroup(
			com.liferay.commerce.account.model.CommerceAccountGroup
				commerceAccountGroup) {

		return getService().addCommerceAccountGroup(commerceAccountGroup);
	}

	public static com.liferay.commerce.account.model.CommerceAccountGroup
			addCommerceAccountGroup(
				long companyId, String name, int type, boolean system,
				String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommerceAccountGroup(
			companyId, name, type, system, externalReferenceCode,
			serviceContext);
	}

	public static void checkGuestCommerceAccountGroup(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().checkGuestCommerceAccountGroup(companyId);
	}

	/**
	 * Creates a new commerce account group with the primary key. Does not add the commerce account group to the database.
	 *
	 * @param commerceAccountGroupId the primary key for the new commerce account group
	 * @return the new commerce account group
	 */
	public static com.liferay.commerce.account.model.CommerceAccountGroup
		createCommerceAccountGroup(long commerceAccountGroupId) {

		return getService().createCommerceAccountGroup(commerceAccountGroupId);
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
	 * Deletes the commerce account group from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceAccountGroupLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceAccountGroup the commerce account group
	 * @return the commerce account group that was removed
	 * @throws PortalException
	 */
	public static com.liferay.commerce.account.model.CommerceAccountGroup
			deleteCommerceAccountGroup(
				com.liferay.commerce.account.model.CommerceAccountGroup
					commerceAccountGroup)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCommerceAccountGroup(commerceAccountGroup);
	}

	/**
	 * Deletes the commerce account group with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceAccountGroupLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceAccountGroupId the primary key of the commerce account group
	 * @return the commerce account group that was removed
	 * @throws PortalException if a commerce account group with the primary key could not be found
	 */
	public static com.liferay.commerce.account.model.CommerceAccountGroup
			deleteCommerceAccountGroup(long commerceAccountGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCommerceAccountGroup(commerceAccountGroupId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.account.model.impl.CommerceAccountGroupModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.account.model.impl.CommerceAccountGroupModelImpl</code>.
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

	public static com.liferay.commerce.account.model.CommerceAccountGroup
		fetchByExternalReferenceCode(
			long companyId, String externalReferenceCode) {

		return getService().fetchByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	public static com.liferay.commerce.account.model.CommerceAccountGroup
		fetchCommerceAccountGroup(long commerceAccountGroupId) {

		return getService().fetchCommerceAccountGroup(commerceAccountGroupId);
	}

	/**
	 * Returns the commerce account group with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the commerce account group's external reference code
	 * @return the matching commerce account group, or <code>null</code> if a matching commerce account group could not be found
	 */
	public static com.liferay.commerce.account.model.CommerceAccountGroup
		fetchCommerceAccountGroupByReferenceCode(
			long companyId, String externalReferenceCode) {

		return getService().fetchCommerceAccountGroupByReferenceCode(
			companyId, externalReferenceCode);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the commerce account group with the primary key.
	 *
	 * @param commerceAccountGroupId the primary key of the commerce account group
	 * @return the commerce account group
	 * @throws PortalException if a commerce account group with the primary key could not be found
	 */
	public static com.liferay.commerce.account.model.CommerceAccountGroup
			getCommerceAccountGroup(long commerceAccountGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceAccountGroup(commerceAccountGroupId);
	}

	/**
	 * Returns a range of all the commerce account groups.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.account.model.impl.CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @return the range of commerce account groups
	 */
	public static java.util.List
		<com.liferay.commerce.account.model.CommerceAccountGroup>
			getCommerceAccountGroups(int start, int end) {

		return getService().getCommerceAccountGroups(start, end);
	}

	public static java.util.List
		<com.liferay.commerce.account.model.CommerceAccountGroup>
			getCommerceAccountGroups(
				long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.account.model.CommerceAccountGroup>
						orderByComparator) {

		return getService().getCommerceAccountGroups(
			companyId, start, end, orderByComparator);
	}

	public static java.util.List
		<com.liferay.commerce.account.model.CommerceAccountGroup>
			getCommerceAccountGroupsByCommerceAccountId(
				long commerceAccountId) {

		return getService().getCommerceAccountGroupsByCommerceAccountId(
			commerceAccountId);
	}

	/**
	 * Returns the number of commerce account groups.
	 *
	 * @return the number of commerce account groups
	 */
	public static int getCommerceAccountGroupsCount() {
		return getService().getCommerceAccountGroupsCount();
	}

	public static int getCommerceAccountGroupsCount(long companyId) {
		return getService().getCommerceAccountGroupsCount(companyId);
	}

	public static java.util.List<Long>
		getCommerceAccountUserIdsFromAccountGroupIds(
			long[] commerceAccountGroupIds, int start, int end) {

		return getService().getCommerceAccountUserIdsFromAccountGroupIds(
			commerceAccountGroupIds, start, end);
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

	public static java.util.List
		<com.liferay.commerce.account.model.CommerceAccountGroup>
				searchCommerceAccountGroups(
					long companyId, String keywords, int start, int end,
					com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().searchCommerceAccountGroups(
			companyId, keywords, start, end, sort);
	}

	public static int searchCommerceAccountsGroupCount(
			long companyId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().searchCommerceAccountsGroupCount(
			companyId, keywords);
	}

	/**
	 * Updates the commerce account group in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceAccountGroupLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceAccountGroup the commerce account group
	 * @return the commerce account group that was updated
	 */
	public static com.liferay.commerce.account.model.CommerceAccountGroup
		updateCommerceAccountGroup(
			com.liferay.commerce.account.model.CommerceAccountGroup
				commerceAccountGroup) {

		return getService().updateCommerceAccountGroup(commerceAccountGroup);
	}

	public static com.liferay.commerce.account.model.CommerceAccountGroup
			updateCommerceAccountGroup(
				long commerceAccountGroupId, String name,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceAccountGroup(
			commerceAccountGroupId, name, serviceContext);
	}

	public static CommerceAccountGroupLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceAccountGroupLocalService, CommerceAccountGroupLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceAccountGroupLocalService.class);

		ServiceTracker
			<CommerceAccountGroupLocalService, CommerceAccountGroupLocalService>
				serviceTracker =
					new ServiceTracker
						<CommerceAccountGroupLocalService,
						 CommerceAccountGroupLocalService>(
							 bundle.getBundleContext(),
							 CommerceAccountGroupLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}