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

import com.liferay.commerce.application.model.CommerceApplicationModelCProductRel;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

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
	public static CommerceApplicationModelCProductRel
		addCommerceApplicationModelCProductRel(
			CommerceApplicationModelCProductRel
				commerceApplicationModelCProductRel) {

		return getService().addCommerceApplicationModelCProductRel(
			commerceApplicationModelCProductRel);
	}

	public static CommerceApplicationModelCProductRel
			addCommerceApplicationModelCProductRel(
				long userId, long commerceApplicationModelId, long cProductId)
		throws PortalException {

		return getService().addCommerceApplicationModelCProductRel(
			userId, commerceApplicationModelId, cProductId);
	}

	/**
	 * Creates a new commerce application model c product rel with the primary key. Does not add the commerce application model c product rel to the database.
	 *
	 * @param commerceApplicationModelCProductRelId the primary key for the new commerce application model c product rel
	 * @return the new commerce application model c product rel
	 */
	public static CommerceApplicationModelCProductRel
		createCommerceApplicationModelCProductRel(
			long commerceApplicationModelCProductRelId) {

		return getService().createCommerceApplicationModelCProductRel(
			commerceApplicationModelCProductRelId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

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
	public static CommerceApplicationModelCProductRel
		deleteCommerceApplicationModelCProductRel(
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
	public static CommerceApplicationModelCProductRel
			deleteCommerceApplicationModelCProductRel(
				long commerceApplicationModelCProductRelId)
		throws PortalException {

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
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
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
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static CommerceApplicationModelCProductRel
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
	public static CommerceApplicationModelCProductRel
			getCommerceApplicationModelCProductRel(
				long commerceApplicationModelCProductRelId)
		throws PortalException {

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
	public static List<CommerceApplicationModelCProductRel>
		getCommerceApplicationModelCProductRels(int start, int end) {

		return getService().getCommerceApplicationModelCProductRels(start, end);
	}

	public static List<CommerceApplicationModelCProductRel>
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
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

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
	public static CommerceApplicationModelCProductRel
		updateCommerceApplicationModelCProductRel(
			CommerceApplicationModelCProductRel
				commerceApplicationModelCProductRel) {

		return getService().updateCommerceApplicationModelCProductRel(
			commerceApplicationModelCProductRel);
	}

	public static CommerceApplicationModelCProductRelLocalService getService() {
		return _service;
	}

	private static volatile CommerceApplicationModelCProductRelLocalService
		_service;

}