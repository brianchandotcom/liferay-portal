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

package com.liferay.commerce.product.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CommerceChannelRel. This utility wraps
 * <code>com.liferay.commerce.product.service.impl.CommerceChannelRelLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see CommerceChannelRelLocalService
 * @generated
 */
public class CommerceChannelRelLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.product.service.impl.CommerceChannelRelLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the commerce channel rel to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceChannelRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceChannelRel the commerce channel rel
	 * @return the commerce channel rel that was added
	 */
	public static com.liferay.commerce.product.model.CommerceChannelRel
		addCommerceChannelRel(
			com.liferay.commerce.product.model.CommerceChannelRel
				commerceChannelRel) {

		return getService().addCommerceChannelRel(commerceChannelRel);
	}

	public static com.liferay.commerce.product.model.CommerceChannelRel
			addCommerceChannelRel(
				String className, long classPK, long commerceChannelId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommerceChannelRel(
			className, classPK, commerceChannelId, serviceContext);
	}

	/**
	 * Creates a new commerce channel rel with the primary key. Does not add the commerce channel rel to the database.
	 *
	 * @param commerceChannelRelId the primary key for the new commerce channel rel
	 * @return the new commerce channel rel
	 */
	public static com.liferay.commerce.product.model.CommerceChannelRel
		createCommerceChannelRel(long commerceChannelRelId) {

		return getService().createCommerceChannelRel(commerceChannelRelId);
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
	 * Deletes the commerce channel rel from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceChannelRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceChannelRel the commerce channel rel
	 * @return the commerce channel rel that was removed
	 */
	public static com.liferay.commerce.product.model.CommerceChannelRel
		deleteCommerceChannelRel(
			com.liferay.commerce.product.model.CommerceChannelRel
				commerceChannelRel) {

		return getService().deleteCommerceChannelRel(commerceChannelRel);
	}

	/**
	 * Deletes the commerce channel rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceChannelRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceChannelRelId the primary key of the commerce channel rel
	 * @return the commerce channel rel that was removed
	 * @throws PortalException if a commerce channel rel with the primary key could not be found
	 */
	public static com.liferay.commerce.product.model.CommerceChannelRel
			deleteCommerceChannelRel(long commerceChannelRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCommerceChannelRel(commerceChannelRelId);
	}

	public static void deleteCommerceChannelRels(long commerceChannelId) {
		getService().deleteCommerceChannelRels(commerceChannelId);
	}

	public static void deleteCommerceChannelRels(
		String className, long classPK) {

		getService().deleteCommerceChannelRels(className, classPK);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CommerceChannelRelModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CommerceChannelRelModelImpl</code>.
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

	public static com.liferay.commerce.product.model.CommerceChannelRel
		fetchCommerceChannelRel(long commerceChannelRelId) {

		return getService().fetchCommerceChannelRel(commerceChannelRelId);
	}

	public static com.liferay.commerce.product.model.CommerceChannelRel
		fetchCommerceChannelRel(
			String className, long classPK, long commerceChannelId) {

		return getService().fetchCommerceChannelRel(
			className, classPK, commerceChannelId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the commerce channel rel with the primary key.
	 *
	 * @param commerceChannelRelId the primary key of the commerce channel rel
	 * @return the commerce channel rel
	 * @throws PortalException if a commerce channel rel with the primary key could not be found
	 */
	public static com.liferay.commerce.product.model.CommerceChannelRel
			getCommerceChannelRel(long commerceChannelRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceChannelRel(commerceChannelRelId);
	}

	/**
	 * Returns a range of all the commerce channel rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CommerceChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce channel rels
	 * @param end the upper bound of the range of commerce channel rels (not inclusive)
	 * @return the range of commerce channel rels
	 */
	public static java.util.List
		<com.liferay.commerce.product.model.CommerceChannelRel>
			getCommerceChannelRels(int start, int end) {

		return getService().getCommerceChannelRels(start, end);
	}

	public static java.util.List
		<com.liferay.commerce.product.model.CommerceChannelRel>
			getCommerceChannelRels(
				long commerceChannelId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.product.model.CommerceChannelRel>
						orderByComparator) {

		return getService().getCommerceChannelRels(
			commerceChannelId, start, end, orderByComparator);
	}

	public static java.util.List
		<com.liferay.commerce.product.model.CommerceChannelRel>
			getCommerceChannelRels(
				String className, long classPK, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.product.model.CommerceChannelRel>
						orderByComparator) {

		return getService().getCommerceChannelRels(
			className, classPK, start, end, orderByComparator);
	}

	public static java.util.List
		<com.liferay.commerce.product.model.CommerceChannelRel>
			getCommerceChannelRels(
				String className, long classPK, String name, int start,
				int end) {

		return getService().getCommerceChannelRels(
			className, classPK, name, start, end);
	}

	/**
	 * Returns the number of commerce channel rels.
	 *
	 * @return the number of commerce channel rels
	 */
	public static int getCommerceChannelRelsCount() {
		return getService().getCommerceChannelRelsCount();
	}

	public static int getCommerceChannelRelsCount(long commerceChannelId) {
		return getService().getCommerceChannelRelsCount(commerceChannelId);
	}

	public static int getCommerceChannelRelsCount(
		String className, long classPK) {

		return getService().getCommerceChannelRelsCount(className, classPK);
	}

	public static int getCommerceChannelRelsCount(
		String className, long classPK, String name) {

		return getService().getCommerceChannelRelsCount(
			className, classPK, name);
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
	 * Updates the commerce channel rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceChannelRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceChannelRel the commerce channel rel
	 * @return the commerce channel rel that was updated
	 */
	public static com.liferay.commerce.product.model.CommerceChannelRel
		updateCommerceChannelRel(
			com.liferay.commerce.product.model.CommerceChannelRel
				commerceChannelRel) {

		return getService().updateCommerceChannelRel(commerceChannelRel);
	}

	public static CommerceChannelRelLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceChannelRelLocalService, CommerceChannelRelLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceChannelRelLocalService.class);

		ServiceTracker
			<CommerceChannelRelLocalService, CommerceChannelRelLocalService>
				serviceTracker =
					new ServiceTracker
						<CommerceChannelRelLocalService,
						 CommerceChannelRelLocalService>(
							 bundle.getBundleContext(),
							 CommerceChannelRelLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}