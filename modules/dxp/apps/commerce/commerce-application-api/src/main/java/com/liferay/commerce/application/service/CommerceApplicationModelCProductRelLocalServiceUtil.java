/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.application.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CommerceApplicationModelCProductRel. This utility wraps
 * <code>com.liferay.commerce.application.service.impl.CommerceApplicationModelCProductRelLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Luca Pellizzon
 * @see CommerceApplicationModelCProductRelLocalService
 * @generated
 */
public class CommerceApplicationModelCProductRelLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.application.service.impl.CommerceApplicationModelCProductRelLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the commerce application model c product rel to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceApplicationModelCProductRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceApplicationModelCProductRel the commerce application model c product rel
	 * @return the commerce application model c product rel that was added
	 */
	public static
		com.liferay.commerce.application.model.
			CommerceApplicationModelCProductRel
				addCommerceApplicationModelCProductRel(
					com.liferay.commerce.application.model.
						CommerceApplicationModelCProductRel
							commerceApplicationModelCProductRel) {

		return getService().addCommerceApplicationModelCProductRel(
			commerceApplicationModelCProductRel);
	}

	public static
		com.liferay.commerce.application.model.
			CommerceApplicationModelCProductRel
					addCommerceApplicationModelCProductRel(
						long userId, long commerceApplicationModelId,
						long cProductId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommerceApplicationModelCProductRel(
			userId, commerceApplicationModelId, cProductId);
	}

	/**
	 * Creates a new commerce application model c product rel with the primary key. Does not add the commerce application model c product rel to the database.
	 *
	 * @param commerceApplicationModelCProductRelId the primary key for the new commerce application model c product rel
	 * @return the new commerce application model c product rel
	 */
	public static
		com.liferay.commerce.application.model.
			CommerceApplicationModelCProductRel
				createCommerceApplicationModelCProductRel(
					long commerceApplicationModelCProductRelId) {

		return getService().createCommerceApplicationModelCProductRel(
			commerceApplicationModelCProductRelId);
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
	 * Deletes the commerce application model c product rel from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceApplicationModelCProductRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceApplicationModelCProductRel the commerce application model c product rel
	 * @return the commerce application model c product rel that was removed
	 */
	public static
		com.liferay.commerce.application.model.
			CommerceApplicationModelCProductRel
				deleteCommerceApplicationModelCProductRel(
					com.liferay.commerce.application.model.
						CommerceApplicationModelCProductRel
							commerceApplicationModelCProductRel) {

		return getService().deleteCommerceApplicationModelCProductRel(
			commerceApplicationModelCProductRel);
	}

	/**
	 * Deletes the commerce application model c product rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceApplicationModelCProductRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceApplicationModelCProductRelId the primary key of the commerce application model c product rel
	 * @return the commerce application model c product rel that was removed
	 * @throws PortalException if a commerce application model c product rel with the primary key could not be found
	 */
	public static
		com.liferay.commerce.application.model.
			CommerceApplicationModelCProductRel
					deleteCommerceApplicationModelCProductRel(
						long commerceApplicationModelCProductRelId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCommerceApplicationModelCProductRel(
			commerceApplicationModelCProductRelId);
	}

	public static void deleteCommerceApplicationModelCProductRels(
		long commerceApplicationModelId) {

		getService().deleteCommerceApplicationModelCProductRels(
			commerceApplicationModelId);
	}

	public static void deleteCommerceApplicationModelCProductRelsByCProductId(
		long cProductId) {

		getService().deleteCommerceApplicationModelCProductRelsByCProductId(
			cProductId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.application.model.impl.CommerceApplicationModelCProductRelModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.application.model.impl.CommerceApplicationModelCProductRelModelImpl</code>.
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
		com.liferay.commerce.application.model.
			CommerceApplicationModelCProductRel
				fetchCommerceApplicationModelCProductRel(
					long commerceApplicationModelCProductRelId) {

		return getService().fetchCommerceApplicationModelCProductRel(
			commerceApplicationModelCProductRelId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the commerce application model c product rel with the primary key.
	 *
	 * @param commerceApplicationModelCProductRelId the primary key of the commerce application model c product rel
	 * @return the commerce application model c product rel
	 * @throws PortalException if a commerce application model c product rel with the primary key could not be found
	 */
	public static
		com.liferay.commerce.application.model.
			CommerceApplicationModelCProductRel
					getCommerceApplicationModelCProductRel(
						long commerceApplicationModelCProductRelId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceApplicationModelCProductRel(
			commerceApplicationModelCProductRelId);
	}

	/**
	 * Returns a range of all the commerce application model c product rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.application.model.impl.CommerceApplicationModelCProductRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce application model c product rels
	 * @param end the upper bound of the range of commerce application model c product rels (not inclusive)
	 * @return the range of commerce application model c product rels
	 */
	public static java.util.List
		<com.liferay.commerce.application.model.
			CommerceApplicationModelCProductRel>
				getCommerceApplicationModelCProductRels(int start, int end) {

		return getService().getCommerceApplicationModelCProductRels(start, end);
	}

	public static java.util.List
		<com.liferay.commerce.application.model.
			CommerceApplicationModelCProductRel>
				getCommerceApplicationModelCProductRels(
					long commerceApplicationModelId, int start, int end) {

		return getService().getCommerceApplicationModelCProductRels(
			commerceApplicationModelId, start, end);
	}

	/**
	 * Returns the number of commerce application model c product rels.
	 *
	 * @return the number of commerce application model c product rels
	 */
	public static int getCommerceApplicationModelCProductRelsCount() {
		return getService().getCommerceApplicationModelCProductRelsCount();
	}

	public static int getCommerceApplicationModelCProductRelsCount(
		long commerceApplicationModelId) {

		return getService().getCommerceApplicationModelCProductRelsCount(
			commerceApplicationModelId);
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
	 * Updates the commerce application model c product rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceApplicationModelCProductRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceApplicationModelCProductRel the commerce application model c product rel
	 * @return the commerce application model c product rel that was updated
	 */
	public static
		com.liferay.commerce.application.model.
			CommerceApplicationModelCProductRel
				updateCommerceApplicationModelCProductRel(
					com.liferay.commerce.application.model.
						CommerceApplicationModelCProductRel
							commerceApplicationModelCProductRel) {

		return getService().updateCommerceApplicationModelCProductRel(
			commerceApplicationModelCProductRel);
	}

	public static CommerceApplicationModelCProductRelLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceApplicationModelCProductRelLocalService,
		 CommerceApplicationModelCProductRelLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceApplicationModelCProductRelLocalService.class);

		ServiceTracker
			<CommerceApplicationModelCProductRelLocalService,
			 CommerceApplicationModelCProductRelLocalService> serviceTracker =
				new ServiceTracker
					<CommerceApplicationModelCProductRelLocalService,
					 CommerceApplicationModelCProductRelLocalService>(
						 bundle.getBundleContext(),
						 CommerceApplicationModelCProductRelLocalService.class,
						 null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}