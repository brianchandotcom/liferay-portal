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

package com.liferay.commerce.discount.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CommerceDiscountCommerceAccountGroupRel. This utility wraps
 * <code>com.liferay.commerce.discount.service.impl.CommerceDiscountCommerceAccountGroupRelLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see CommerceDiscountCommerceAccountGroupRelLocalService
 * @generated
 */
public class CommerceDiscountCommerceAccountGroupRelLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.discount.service.impl.CommerceDiscountCommerceAccountGroupRelLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the commerce discount commerce account group rel to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceDiscountCommerceAccountGroupRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceDiscountCommerceAccountGroupRel the commerce discount commerce account group rel
	 * @return the commerce discount commerce account group rel that was added
	 */
	public static
		com.liferay.commerce.discount.model.
			CommerceDiscountCommerceAccountGroupRel
				addCommerceDiscountCommerceAccountGroupRel(
					com.liferay.commerce.discount.model.
						CommerceDiscountCommerceAccountGroupRel
							commerceDiscountCommerceAccountGroupRel) {

		return getService().addCommerceDiscountCommerceAccountGroupRel(
			commerceDiscountCommerceAccountGroupRel);
	}

	public static
		com.liferay.commerce.discount.model.
			CommerceDiscountCommerceAccountGroupRel
					addCommerceDiscountCommerceAccountGroupRel(
						long commerceDiscountId, long commerceAccountGroupId,
						com.liferay.portal.kernel.service.ServiceContext
							serviceContext)
				throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommerceDiscountCommerceAccountGroupRel(
			commerceDiscountId, commerceAccountGroupId, serviceContext);
	}

	/**
	 * Creates a new commerce discount commerce account group rel with the primary key. Does not add the commerce discount commerce account group rel to the database.
	 *
	 * @param commerceDiscountCommerceAccountGroupRelId the primary key for the new commerce discount commerce account group rel
	 * @return the new commerce discount commerce account group rel
	 */
	public static
		com.liferay.commerce.discount.model.
			CommerceDiscountCommerceAccountGroupRel
				createCommerceDiscountCommerceAccountGroupRel(
					long commerceDiscountCommerceAccountGroupRelId) {

		return getService().createCommerceDiscountCommerceAccountGroupRel(
			commerceDiscountCommerceAccountGroupRelId);
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
	 * Deletes the commerce discount commerce account group rel from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceDiscountCommerceAccountGroupRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceDiscountCommerceAccountGroupRel the commerce discount commerce account group rel
	 * @return the commerce discount commerce account group rel that was removed
	 * @throws PortalException
	 */
	public static
		com.liferay.commerce.discount.model.
			CommerceDiscountCommerceAccountGroupRel
					deleteCommerceDiscountCommerceAccountGroupRel(
						com.liferay.commerce.discount.model.
							CommerceDiscountCommerceAccountGroupRel
								commerceDiscountCommerceAccountGroupRel)
				throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCommerceDiscountCommerceAccountGroupRel(
			commerceDiscountCommerceAccountGroupRel);
	}

	/**
	 * Deletes the commerce discount commerce account group rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceDiscountCommerceAccountGroupRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceDiscountCommerceAccountGroupRelId the primary key of the commerce discount commerce account group rel
	 * @return the commerce discount commerce account group rel that was removed
	 * @throws PortalException if a commerce discount commerce account group rel with the primary key could not be found
	 */
	public static
		com.liferay.commerce.discount.model.
			CommerceDiscountCommerceAccountGroupRel
					deleteCommerceDiscountCommerceAccountGroupRel(
						long commerceDiscountCommerceAccountGroupRelId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCommerceDiscountCommerceAccountGroupRel(
			commerceDiscountCommerceAccountGroupRelId);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static void
		deleteCommerceDiscountCommerceAccountGroupRelsBycommerceAccountGroupId(
			long commerceAccountGroupId) {

		getService().
			deleteCommerceDiscountCommerceAccountGroupRelsBycommerceAccountGroupId(
				commerceAccountGroupId);
	}

	public static void
			deleteCommerceDiscountCommerceAccountGroupRelsByCommerceDiscountId(
				long commerceDiscountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().
			deleteCommerceDiscountCommerceAccountGroupRelsByCommerceDiscountId(
				commerceDiscountId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.discount.model.impl.CommerceDiscountCommerceAccountGroupRelModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.discount.model.impl.CommerceDiscountCommerceAccountGroupRelModelImpl</code>.
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

	public static
		com.liferay.commerce.discount.model.
			CommerceDiscountCommerceAccountGroupRel
				fetchCommerceDiscountCommerceAccountGroupRel(
					long commerceDiscountCommerceAccountGroupRelId) {

		return getService().fetchCommerceDiscountCommerceAccountGroupRel(
			commerceDiscountCommerceAccountGroupRelId);
	}

	public static
		com.liferay.commerce.discount.model.
			CommerceDiscountCommerceAccountGroupRel
				fetchCommerceDiscountCommerceAccountGroupRel(
					long commerceDiscountId, long commerceAccountGroupId) {

		return getService().fetchCommerceDiscountCommerceAccountGroupRel(
			commerceDiscountId, commerceAccountGroupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the commerce discount commerce account group rel with the primary key.
	 *
	 * @param commerceDiscountCommerceAccountGroupRelId the primary key of the commerce discount commerce account group rel
	 * @return the commerce discount commerce account group rel
	 * @throws PortalException if a commerce discount commerce account group rel with the primary key could not be found
	 */
	public static
		com.liferay.commerce.discount.model.
			CommerceDiscountCommerceAccountGroupRel
					getCommerceDiscountCommerceAccountGroupRel(
						long commerceDiscountCommerceAccountGroupRelId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceDiscountCommerceAccountGroupRel(
			commerceDiscountCommerceAccountGroupRelId);
	}

	/**
	 * Returns a range of all the commerce discount commerce account group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.discount.model.impl.CommerceDiscountCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount commerce account group rels
	 * @param end the upper bound of the range of commerce discount commerce account group rels (not inclusive)
	 * @return the range of commerce discount commerce account group rels
	 */
	public static java.util.List
		<com.liferay.commerce.discount.model.
			CommerceDiscountCommerceAccountGroupRel>
				getCommerceDiscountCommerceAccountGroupRels(
					int start, int end) {

		return getService().getCommerceDiscountCommerceAccountGroupRels(
			start, end);
	}

	public static java.util.List
		<com.liferay.commerce.discount.model.
			CommerceDiscountCommerceAccountGroupRel>
				getCommerceDiscountCommerceAccountGroupRels(
					long commerceDiscountId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.discount.model.
							CommerceDiscountCommerceAccountGroupRel>
								orderByComparator) {

		return getService().getCommerceDiscountCommerceAccountGroupRels(
			commerceDiscountId, start, end, orderByComparator);
	}

	public static java.util.List
		<com.liferay.commerce.discount.model.
			CommerceDiscountCommerceAccountGroupRel>
				getCommerceDiscountCommerceAccountGroupRels(
					long commerceDiscountId, String name, int start, int end) {

		return getService().getCommerceDiscountCommerceAccountGroupRels(
			commerceDiscountId, name, start, end);
	}

	/**
	 * Returns the number of commerce discount commerce account group rels.
	 *
	 * @return the number of commerce discount commerce account group rels
	 */
	public static int getCommerceDiscountCommerceAccountGroupRelsCount() {
		return getService().getCommerceDiscountCommerceAccountGroupRelsCount();
	}

	public static int getCommerceDiscountCommerceAccountGroupRelsCount(
		long commerceDiscountId) {

		return getService().getCommerceDiscountCommerceAccountGroupRelsCount(
			commerceDiscountId);
	}

	public static int getCommerceDiscountCommerceAccountGroupRelsCount(
		long commerceDiscountId, String name) {

		return getService().getCommerceDiscountCommerceAccountGroupRelsCount(
			commerceDiscountId, name);
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
	 * Updates the commerce discount commerce account group rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceDiscountCommerceAccountGroupRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceDiscountCommerceAccountGroupRel the commerce discount commerce account group rel
	 * @return the commerce discount commerce account group rel that was updated
	 */
	public static
		com.liferay.commerce.discount.model.
			CommerceDiscountCommerceAccountGroupRel
				updateCommerceDiscountCommerceAccountGroupRel(
					com.liferay.commerce.discount.model.
						CommerceDiscountCommerceAccountGroupRel
							commerceDiscountCommerceAccountGroupRel) {

		return getService().updateCommerceDiscountCommerceAccountGroupRel(
			commerceDiscountCommerceAccountGroupRel);
	}

	public static CommerceDiscountCommerceAccountGroupRelLocalService
		getService() {

		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceDiscountCommerceAccountGroupRelLocalService,
		 CommerceDiscountCommerceAccountGroupRelLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceDiscountCommerceAccountGroupRelLocalService.class);

		ServiceTracker
			<CommerceDiscountCommerceAccountGroupRelLocalService,
			 CommerceDiscountCommerceAccountGroupRelLocalService>
				serviceTracker =
					new ServiceTracker
						<CommerceDiscountCommerceAccountGroupRelLocalService,
						 CommerceDiscountCommerceAccountGroupRelLocalService>(
							 bundle.getBundleContext(),
							 CommerceDiscountCommerceAccountGroupRelLocalService.class,
							 null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}