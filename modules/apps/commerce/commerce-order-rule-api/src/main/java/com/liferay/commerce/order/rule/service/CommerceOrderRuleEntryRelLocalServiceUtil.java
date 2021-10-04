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

package com.liferay.commerce.order.rule.service;

import com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for CommerceOrderRuleEntryRel. This utility wraps
 * <code>com.liferay.commerce.order.rule.service.impl.CommerceOrderRuleEntryRelLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Luca Pellizzon
 * @see CommerceOrderRuleEntryRelLocalService
 * @generated
 */
public class CommerceOrderRuleEntryRelLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.order.rule.service.impl.CommerceOrderRuleEntryRelLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the commerce order rule entry rel to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceOrderRuleEntryRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceOrderRuleEntryRel the commerce order rule entry rel
	 * @return the commerce order rule entry rel that was added
	 */
	public static CommerceOrderRuleEntryRel addCommerceOrderRuleEntryRel(
		CommerceOrderRuleEntryRel commerceOrderRuleEntryRel) {

		return getService().addCommerceOrderRuleEntryRel(
			commerceOrderRuleEntryRel);
	}

	public static CommerceOrderRuleEntryRel addCommerceOrderRuleEntryRel(
			long userId, String className, long classPK,
			long commerceOrderRuleEntryId)
		throws PortalException {

		return getService().addCommerceOrderRuleEntryRel(
			userId, className, classPK, commerceOrderRuleEntryId);
	}

	/**
	 * Creates a new commerce order rule entry rel with the primary key. Does not add the commerce order rule entry rel to the database.
	 *
	 * @param commerceOrderRuleEntryRelId the primary key for the new commerce order rule entry rel
	 * @return the new commerce order rule entry rel
	 */
	public static CommerceOrderRuleEntryRel createCommerceOrderRuleEntryRel(
		long commerceOrderRuleEntryRelId) {

		return getService().createCommerceOrderRuleEntryRel(
			commerceOrderRuleEntryRelId);
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
	 * Deletes the commerce order rule entry rel from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceOrderRuleEntryRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceOrderRuleEntryRel the commerce order rule entry rel
	 * @return the commerce order rule entry rel that was removed
	 * @throws PortalException
	 */
	public static CommerceOrderRuleEntryRel deleteCommerceOrderRuleEntryRel(
			CommerceOrderRuleEntryRel commerceOrderRuleEntryRel)
		throws PortalException {

		return getService().deleteCommerceOrderRuleEntryRel(
			commerceOrderRuleEntryRel);
	}

	/**
	 * Deletes the commerce order rule entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceOrderRuleEntryRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceOrderRuleEntryRelId the primary key of the commerce order rule entry rel
	 * @return the commerce order rule entry rel that was removed
	 * @throws PortalException if a commerce order rule entry rel with the primary key could not be found
	 */
	public static CommerceOrderRuleEntryRel deleteCommerceOrderRuleEntryRel(
			long commerceOrderRuleEntryRelId)
		throws PortalException {

		return getService().deleteCommerceOrderRuleEntryRel(
			commerceOrderRuleEntryRelId);
	}

	public static void deleteCommerceOrderRuleEntryRels(
			long commerceOrderRuleEntryId)
		throws PortalException {

		getService().deleteCommerceOrderRuleEntryRels(commerceOrderRuleEntryId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.order.rule.model.impl.CommerceOrderRuleEntryRelModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.order.rule.model.impl.CommerceOrderRuleEntryRelModelImpl</code>.
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

	public static CommerceOrderRuleEntryRel fetchCommerceOrderRuleEntryRel(
		long commerceOrderRuleEntryRelId) {

		return getService().fetchCommerceOrderRuleEntryRel(
			commerceOrderRuleEntryRelId);
	}

	public static CommerceOrderRuleEntryRel fetchCommerceOrderRuleEntryRel(
		String className, long classPK, long commerceOrderRuleEntryId) {

		return getService().fetchCommerceOrderRuleEntryRel(
			className, classPK, commerceOrderRuleEntryId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static List<CommerceOrderRuleEntryRel>
			getCommerceOrderRuleEntryAccountEntryRels(
				long commerceOrderRuleEntryId, String keywords, int start,
				int end)
		throws PortalException {

		return getService().getCommerceOrderRuleEntryAccountEntryRels(
			commerceOrderRuleEntryId, keywords, start, end);
	}

	public static int getCommerceOrderRuleEntryAccountEntryRelsCount(
			long commerceOrderRuleEntryId, String keywords)
		throws PortalException {

		return getService().getCommerceOrderRuleEntryAccountEntryRelsCount(
			commerceOrderRuleEntryId, keywords);
	}

	public static List<CommerceOrderRuleEntryRel>
			getCommerceOrderRuleEntryAccountGroupRels(
				long commerceOrderRuleEntryId, String keywords, int start,
				int end)
		throws PortalException {

		return getService().getCommerceOrderRuleEntryAccountGroupRels(
			commerceOrderRuleEntryId, keywords, start, end);
	}

	public static int getCommerceOrderRuleEntryAccountGroupRelsCount(
			long commerceOrderRuleEntryId, String keywords)
		throws PortalException {

		return getService().getCommerceOrderRuleEntryAccountGroupRelsCount(
			commerceOrderRuleEntryId, keywords);
	}

	public static List<CommerceOrderRuleEntryRel>
			getCommerceOrderRuleEntryCommerceChannelRels(
				long commerceOrderRuleEntryId, String keywords, int start,
				int end)
		throws PortalException {

		return getService().getCommerceOrderRuleEntryCommerceChannelRels(
			commerceOrderRuleEntryId, keywords, start, end);
	}

	public static int getCommerceOrderRuleEntryCommerceChannelRelsCount(
			long commerceOrderRuleEntryId, String keywords)
		throws PortalException {

		return getService().getCommerceOrderRuleEntryCommerceChannelRelsCount(
			commerceOrderRuleEntryId, keywords);
	}

	public static List<CommerceOrderRuleEntryRel>
			getCommerceOrderRuleEntryCommerceOrderTypeRels(
				long commerceOrderRuleEntryId, String keywords, int start,
				int end)
		throws PortalException {

		return getService().getCommerceOrderRuleEntryCommerceOrderTypeRels(
			commerceOrderRuleEntryId, keywords, start, end);
	}

	public static int getCommerceOrderRuleEntryCommerceOrderTypeRelsCount(
			long commerceOrderRuleEntryId, String keywords)
		throws PortalException {

		return getService().getCommerceOrderRuleEntryCommerceOrderTypeRelsCount(
			commerceOrderRuleEntryId, keywords);
	}

	/**
	 * Returns the commerce order rule entry rel with the primary key.
	 *
	 * @param commerceOrderRuleEntryRelId the primary key of the commerce order rule entry rel
	 * @return the commerce order rule entry rel
	 * @throws PortalException if a commerce order rule entry rel with the primary key could not be found
	 */
	public static CommerceOrderRuleEntryRel getCommerceOrderRuleEntryRel(
			long commerceOrderRuleEntryRelId)
		throws PortalException {

		return getService().getCommerceOrderRuleEntryRel(
			commerceOrderRuleEntryRelId);
	}

	/**
	 * Returns a range of all the commerce order rule entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.order.rule.model.impl.CommerceOrderRuleEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order rule entry rels
	 * @param end the upper bound of the range of commerce order rule entry rels (not inclusive)
	 * @return the range of commerce order rule entry rels
	 */
	public static List<CommerceOrderRuleEntryRel> getCommerceOrderRuleEntryRels(
		int start, int end) {

		return getService().getCommerceOrderRuleEntryRels(start, end);
	}

	public static List<CommerceOrderRuleEntryRel> getCommerceOrderRuleEntryRels(
		long commerceOrderRuleEntryId) {

		return getService().getCommerceOrderRuleEntryRels(
			commerceOrderRuleEntryId);
	}

	public static List<CommerceOrderRuleEntryRel> getCommerceOrderRuleEntryRels(
		long commerceOrderRuleEntryId, int start, int end,
		OrderByComparator<CommerceOrderRuleEntryRel> orderByComparator) {

		return getService().getCommerceOrderRuleEntryRels(
			commerceOrderRuleEntryId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of commerce order rule entry rels.
	 *
	 * @return the number of commerce order rule entry rels
	 */
	public static int getCommerceOrderRuleEntryRelsCount() {
		return getService().getCommerceOrderRuleEntryRelsCount();
	}

	public static int getCommerceOrderRuleEntryRelsCount(
		long commerceOrderRuleEntryId) {

		return getService().getCommerceOrderRuleEntryRelsCount(
			commerceOrderRuleEntryId);
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
	 * Updates the commerce order rule entry rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceOrderRuleEntryRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceOrderRuleEntryRel the commerce order rule entry rel
	 * @return the commerce order rule entry rel that was updated
	 */
	public static CommerceOrderRuleEntryRel updateCommerceOrderRuleEntryRel(
		CommerceOrderRuleEntryRel commerceOrderRuleEntryRel) {

		return getService().updateCommerceOrderRuleEntryRel(
			commerceOrderRuleEntryRel);
	}

	public static CommerceOrderRuleEntryRelLocalService getService() {
		return _service;
	}

	private static volatile CommerceOrderRuleEntryRelLocalService _service;

}