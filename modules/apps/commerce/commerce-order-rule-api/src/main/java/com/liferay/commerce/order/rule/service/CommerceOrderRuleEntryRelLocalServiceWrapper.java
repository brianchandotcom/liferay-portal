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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceOrderRuleEntryRelLocalService}.
 *
 * @author Luca Pellizzon
 * @see CommerceOrderRuleEntryRelLocalService
 * @generated
 */
public class CommerceOrderRuleEntryRelLocalServiceWrapper
	implements CommerceOrderRuleEntryRelLocalService,
			   ServiceWrapper<CommerceOrderRuleEntryRelLocalService> {

	public CommerceOrderRuleEntryRelLocalServiceWrapper(
		CommerceOrderRuleEntryRelLocalService
			commerceOrderRuleEntryRelLocalService) {

		_commerceOrderRuleEntryRelLocalService =
			commerceOrderRuleEntryRelLocalService;
	}

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
	@Override
	public com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel
		addCommerceOrderRuleEntryRel(
			com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel
				commerceOrderRuleEntryRel) {

		return _commerceOrderRuleEntryRelLocalService.
			addCommerceOrderRuleEntryRel(commerceOrderRuleEntryRel);
	}

	@Override
	public com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel
			addCommerceOrderRuleEntryRel(
				long userId, String className, long classPK,
				long commerceOrderRuleEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderRuleEntryRelLocalService.
			addCommerceOrderRuleEntryRel(
				userId, className, classPK, commerceOrderRuleEntryId);
	}

	/**
	 * Creates a new commerce order rule entry rel with the primary key. Does not add the commerce order rule entry rel to the database.
	 *
	 * @param commerceOrderRuleEntryRelId the primary key for the new commerce order rule entry rel
	 * @return the new commerce order rule entry rel
	 */
	@Override
	public com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel
		createCommerceOrderRuleEntryRel(long commerceOrderRuleEntryRelId) {

		return _commerceOrderRuleEntryRelLocalService.
			createCommerceOrderRuleEntryRel(commerceOrderRuleEntryRelId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderRuleEntryRelLocalService.createPersistedModel(
			primaryKeyObj);
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
	@Override
	public com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel
			deleteCommerceOrderRuleEntryRel(
				com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel
					commerceOrderRuleEntryRel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderRuleEntryRelLocalService.
			deleteCommerceOrderRuleEntryRel(commerceOrderRuleEntryRel);
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
	@Override
	public com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel
			deleteCommerceOrderRuleEntryRel(long commerceOrderRuleEntryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderRuleEntryRelLocalService.
			deleteCommerceOrderRuleEntryRel(commerceOrderRuleEntryRelId);
	}

	@Override
	public void deleteCommerceOrderRuleEntryRels(long commerceOrderRuleEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceOrderRuleEntryRelLocalService.deleteCommerceOrderRuleEntryRels(
			commerceOrderRuleEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderRuleEntryRelLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _commerceOrderRuleEntryRelLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _commerceOrderRuleEntryRelLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceOrderRuleEntryRelLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _commerceOrderRuleEntryRelLocalService.dynamicQuery(
			dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _commerceOrderRuleEntryRelLocalService.dynamicQuery(
			dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _commerceOrderRuleEntryRelLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _commerceOrderRuleEntryRelLocalService.dynamicQueryCount(
			dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _commerceOrderRuleEntryRelLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel
		fetchCommerceOrderRuleEntryRel(long commerceOrderRuleEntryRelId) {

		return _commerceOrderRuleEntryRelLocalService.
			fetchCommerceOrderRuleEntryRel(commerceOrderRuleEntryRelId);
	}

	@Override
	public com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel
		fetchCommerceOrderRuleEntryRel(
			String className, long classPK, long commerceOrderRuleEntryId) {

		return _commerceOrderRuleEntryRelLocalService.
			fetchCommerceOrderRuleEntryRel(
				className, classPK, commerceOrderRuleEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _commerceOrderRuleEntryRelLocalService.
			getActionableDynamicQuery();
	}

	@Override
	public java.util.List
		<com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel>
				getCommerceOrderRuleEntryAccountEntryRels(
					long commerceOrderRuleEntryId, String keywords, int start,
					int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderRuleEntryRelLocalService.
			getCommerceOrderRuleEntryAccountEntryRels(
				commerceOrderRuleEntryId, keywords, start, end);
	}

	@Override
	public int getCommerceOrderRuleEntryAccountEntryRelsCount(
			long commerceOrderRuleEntryId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderRuleEntryRelLocalService.
			getCommerceOrderRuleEntryAccountEntryRelsCount(
				commerceOrderRuleEntryId, keywords);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel>
				getCommerceOrderRuleEntryAccountGroupRels(
					long commerceOrderRuleEntryId, String keywords, int start,
					int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderRuleEntryRelLocalService.
			getCommerceOrderRuleEntryAccountGroupRels(
				commerceOrderRuleEntryId, keywords, start, end);
	}

	@Override
	public int getCommerceOrderRuleEntryAccountGroupRelsCount(
			long commerceOrderRuleEntryId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderRuleEntryRelLocalService.
			getCommerceOrderRuleEntryAccountGroupRelsCount(
				commerceOrderRuleEntryId, keywords);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel>
				getCommerceOrderRuleEntryCommerceChannelRels(
					long commerceOrderRuleEntryId, String keywords, int start,
					int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderRuleEntryRelLocalService.
			getCommerceOrderRuleEntryCommerceChannelRels(
				commerceOrderRuleEntryId, keywords, start, end);
	}

	@Override
	public int getCommerceOrderRuleEntryCommerceChannelRelsCount(
			long commerceOrderRuleEntryId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderRuleEntryRelLocalService.
			getCommerceOrderRuleEntryCommerceChannelRelsCount(
				commerceOrderRuleEntryId, keywords);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel>
				getCommerceOrderRuleEntryCommerceOrderTypeRels(
					long commerceOrderRuleEntryId, String keywords, int start,
					int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderRuleEntryRelLocalService.
			getCommerceOrderRuleEntryCommerceOrderTypeRels(
				commerceOrderRuleEntryId, keywords, start, end);
	}

	@Override
	public int getCommerceOrderRuleEntryCommerceOrderTypeRelsCount(
			long commerceOrderRuleEntryId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderRuleEntryRelLocalService.
			getCommerceOrderRuleEntryCommerceOrderTypeRelsCount(
				commerceOrderRuleEntryId, keywords);
	}

	/**
	 * Returns the commerce order rule entry rel with the primary key.
	 *
	 * @param commerceOrderRuleEntryRelId the primary key of the commerce order rule entry rel
	 * @return the commerce order rule entry rel
	 * @throws PortalException if a commerce order rule entry rel with the primary key could not be found
	 */
	@Override
	public com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel
			getCommerceOrderRuleEntryRel(long commerceOrderRuleEntryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderRuleEntryRelLocalService.
			getCommerceOrderRuleEntryRel(commerceOrderRuleEntryRelId);
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
	@Override
	public java.util.List
		<com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel>
			getCommerceOrderRuleEntryRels(int start, int end) {

		return _commerceOrderRuleEntryRelLocalService.
			getCommerceOrderRuleEntryRels(start, end);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel>
			getCommerceOrderRuleEntryRels(long commerceOrderRuleEntryId) {

		return _commerceOrderRuleEntryRelLocalService.
			getCommerceOrderRuleEntryRels(commerceOrderRuleEntryId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel>
			getCommerceOrderRuleEntryRels(
				long commerceOrderRuleEntryId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.order.rule.model.
						CommerceOrderRuleEntryRel> orderByComparator) {

		return _commerceOrderRuleEntryRelLocalService.
			getCommerceOrderRuleEntryRels(
				commerceOrderRuleEntryId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of commerce order rule entry rels.
	 *
	 * @return the number of commerce order rule entry rels
	 */
	@Override
	public int getCommerceOrderRuleEntryRelsCount() {
		return _commerceOrderRuleEntryRelLocalService.
			getCommerceOrderRuleEntryRelsCount();
	}

	@Override
	public int getCommerceOrderRuleEntryRelsCount(
		long commerceOrderRuleEntryId) {

		return _commerceOrderRuleEntryRelLocalService.
			getCommerceOrderRuleEntryRelsCount(commerceOrderRuleEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _commerceOrderRuleEntryRelLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceOrderRuleEntryRelLocalService.
			getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderRuleEntryRelLocalService.getPersistedModel(
			primaryKeyObj);
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
	@Override
	public com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel
		updateCommerceOrderRuleEntryRel(
			com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel
				commerceOrderRuleEntryRel) {

		return _commerceOrderRuleEntryRelLocalService.
			updateCommerceOrderRuleEntryRel(commerceOrderRuleEntryRel);
	}

	@Override
	public CommerceOrderRuleEntryRelLocalService getWrappedService() {
		return _commerceOrderRuleEntryRelLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceOrderRuleEntryRelLocalService
			commerceOrderRuleEntryRelLocalService) {

		_commerceOrderRuleEntryRelLocalService =
			commerceOrderRuleEntryRelLocalService;
	}

	private CommerceOrderRuleEntryRelLocalService
		_commerceOrderRuleEntryRelLocalService;

}