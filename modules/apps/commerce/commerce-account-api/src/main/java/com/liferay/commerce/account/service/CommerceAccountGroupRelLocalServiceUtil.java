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
 * Provides the local service utility for CommerceAccountGroupRel. This utility wraps
 * <code>com.liferay.commerce.account.service.impl.CommerceAccountGroupRelLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see CommerceAccountGroupRelLocalService
 * @generated
 */
public class CommerceAccountGroupRelLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.account.service.impl.CommerceAccountGroupRelLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the commerce account group rel to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceAccountGroupRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceAccountGroupRel the commerce account group rel
	 * @return the commerce account group rel that was added
	 */
	public static com.liferay.commerce.account.model.CommerceAccountGroupRel
		addCommerceAccountGroupRel(
			com.liferay.commerce.account.model.CommerceAccountGroupRel
				commerceAccountGroupRel) {

		return getService().addCommerceAccountGroupRel(commerceAccountGroupRel);
	}

	public static com.liferay.commerce.account.model.CommerceAccountGroupRel
			addCommerceAccountGroupRel(
				String className, long classPK, long commerceAccountGroupId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommerceAccountGroupRel(
			className, classPK, commerceAccountGroupId, serviceContext);
	}

	/**
	 * Creates a new commerce account group rel with the primary key. Does not add the commerce account group rel to the database.
	 *
	 * @param commerceAccountGroupRelId the primary key for the new commerce account group rel
	 * @return the new commerce account group rel
	 */
	public static com.liferay.commerce.account.model.CommerceAccountGroupRel
		createCommerceAccountGroupRel(long commerceAccountGroupRelId) {

		return getService().createCommerceAccountGroupRel(
			commerceAccountGroupRelId);
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
	 * Deletes the commerce account group rel from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceAccountGroupRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceAccountGroupRel the commerce account group rel
	 * @return the commerce account group rel that was removed
	 */
	public static com.liferay.commerce.account.model.CommerceAccountGroupRel
		deleteCommerceAccountGroupRel(
			com.liferay.commerce.account.model.CommerceAccountGroupRel
				commerceAccountGroupRel) {

		return getService().deleteCommerceAccountGroupRel(
			commerceAccountGroupRel);
	}

	/**
	 * Deletes the commerce account group rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceAccountGroupRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceAccountGroupRelId the primary key of the commerce account group rel
	 * @return the commerce account group rel that was removed
	 * @throws PortalException if a commerce account group rel with the primary key could not be found
	 */
	public static com.liferay.commerce.account.model.CommerceAccountGroupRel
			deleteCommerceAccountGroupRel(long commerceAccountGroupRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCommerceAccountGroupRel(
			commerceAccountGroupRelId);
	}

	public static void deleteCommerceAccountGroupRels(
		long commerceAccountGroupId) {

		getService().deleteCommerceAccountGroupRels(commerceAccountGroupId);
	}

	public static void deleteCommerceAccountGroupRels(
		String className, long classPK) {

		getService().deleteCommerceAccountGroupRels(className, classPK);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.account.model.impl.CommerceAccountGroupRelModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.account.model.impl.CommerceAccountGroupRelModelImpl</code>.
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

	public static com.liferay.commerce.account.model.CommerceAccountGroupRel
		fetchCommerceAccountGroupRel(long commerceAccountGroupRelId) {

		return getService().fetchCommerceAccountGroupRel(
			commerceAccountGroupRelId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the commerce account group rel with the primary key.
	 *
	 * @param commerceAccountGroupRelId the primary key of the commerce account group rel
	 * @return the commerce account group rel
	 * @throws PortalException if a commerce account group rel with the primary key could not be found
	 */
	public static com.liferay.commerce.account.model.CommerceAccountGroupRel
			getCommerceAccountGroupRel(long commerceAccountGroupRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceAccountGroupRel(
			commerceAccountGroupRelId);
	}

	/**
	 * Returns a range of all the commerce account group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.account.model.impl.CommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce account group rels
	 * @param end the upper bound of the range of commerce account group rels (not inclusive)
	 * @return the range of commerce account group rels
	 */
	public static java.util.List
		<com.liferay.commerce.account.model.CommerceAccountGroupRel>
			getCommerceAccountGroupRels(int start, int end) {

		return getService().getCommerceAccountGroupRels(start, end);
	}

	public static java.util.List
		<com.liferay.commerce.account.model.CommerceAccountGroupRel>
			getCommerceAccountGroupRels(
				long commerceAccountGroupId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.account.model.CommerceAccountGroupRel>
						orderByComparator) {

		return getService().getCommerceAccountGroupRels(
			commerceAccountGroupId, start, end, orderByComparator);
	}

	public static java.util.List
		<com.liferay.commerce.account.model.CommerceAccountGroupRel>
			getCommerceAccountGroupRels(
				String className, long classPK, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.account.model.CommerceAccountGroupRel>
						orderByComparator) {

		return getService().getCommerceAccountGroupRels(
			className, classPK, start, end, orderByComparator);
	}

	/**
	 * Returns the number of commerce account group rels.
	 *
	 * @return the number of commerce account group rels
	 */
	public static int getCommerceAccountGroupRelsCount() {
		return getService().getCommerceAccountGroupRelsCount();
	}

	public static int getCommerceAccountGroupRelsCount(
		long commerceAccountGroupId) {

		return getService().getCommerceAccountGroupRelsCount(
			commerceAccountGroupId);
	}

	public static int getCommerceAccountGroupRelsCount(
		String className, long classPK) {

		return getService().getCommerceAccountGroupRelsCount(
			className, classPK);
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
	 * Updates the commerce account group rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceAccountGroupRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceAccountGroupRel the commerce account group rel
	 * @return the commerce account group rel that was updated
	 */
	public static com.liferay.commerce.account.model.CommerceAccountGroupRel
		updateCommerceAccountGroupRel(
			com.liferay.commerce.account.model.CommerceAccountGroupRel
				commerceAccountGroupRel) {

		return getService().updateCommerceAccountGroupRel(
			commerceAccountGroupRel);
	}

	public static CommerceAccountGroupRelLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceAccountGroupRelLocalService,
		 CommerceAccountGroupRelLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceAccountGroupRelLocalService.class);

		ServiceTracker
			<CommerceAccountGroupRelLocalService,
			 CommerceAccountGroupRelLocalService> serviceTracker =
				new ServiceTracker
					<CommerceAccountGroupRelLocalService,
					 CommerceAccountGroupRelLocalService>(
						 bundle.getBundleContext(),
						 CommerceAccountGroupRelLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}