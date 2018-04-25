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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceDiscountRuleLocalService}.
 *
 * @author Marco Leo
 * @see CommerceDiscountRuleLocalService
 * @generated
 */
@ProviderType
public class CommerceDiscountRuleLocalServiceWrapper
	implements CommerceDiscountRuleLocalService,
		ServiceWrapper<CommerceDiscountRuleLocalService> {
	public CommerceDiscountRuleLocalServiceWrapper(
		CommerceDiscountRuleLocalService commerceDiscountRuleLocalService) {
		_commerceDiscountRuleLocalService = commerceDiscountRuleLocalService;
	}

	/**
	* Adds the commerce discount rule to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceDiscountRule the commerce discount rule
	* @return the commerce discount rule that was added
	*/
	@Override
	public com.liferay.commerce.discount.model.CommerceDiscountRule addCommerceDiscountRule(
		com.liferay.commerce.discount.model.CommerceDiscountRule commerceDiscountRule) {
		return _commerceDiscountRuleLocalService.addCommerceDiscountRule(commerceDiscountRule);
	}

	@Override
	public com.liferay.commerce.discount.model.CommerceDiscountRule addCommerceDiscountRule(
		long commerceDiscountId, java.lang.String type,
		java.lang.String typeSettings,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountRuleLocalService.addCommerceDiscountRule(commerceDiscountId,
			type, typeSettings, serviceContext);
	}

	/**
	* Creates a new commerce discount rule with the primary key. Does not add the commerce discount rule to the database.
	*
	* @param commerceDiscountRuleId the primary key for the new commerce discount rule
	* @return the new commerce discount rule
	*/
	@Override
	public com.liferay.commerce.discount.model.CommerceDiscountRule createCommerceDiscountRule(
		long commerceDiscountRuleId) {
		return _commerceDiscountRuleLocalService.createCommerceDiscountRule(commerceDiscountRuleId);
	}

	/**
	* Deletes the commerce discount rule from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceDiscountRule the commerce discount rule
	* @return the commerce discount rule that was removed
	*/
	@Override
	public com.liferay.commerce.discount.model.CommerceDiscountRule deleteCommerceDiscountRule(
		com.liferay.commerce.discount.model.CommerceDiscountRule commerceDiscountRule) {
		return _commerceDiscountRuleLocalService.deleteCommerceDiscountRule(commerceDiscountRule);
	}

	/**
	* Deletes the commerce discount rule with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceDiscountRuleId the primary key of the commerce discount rule
	* @return the commerce discount rule that was removed
	* @throws PortalException if a commerce discount rule with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.discount.model.CommerceDiscountRule deleteCommerceDiscountRule(
		long commerceDiscountRuleId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountRuleLocalService.deleteCommerceDiscountRule(commerceDiscountRuleId);
	}

	@Override
	public void deleteCommerceDiscountRules(long commerceDiscountId) {
		_commerceDiscountRuleLocalService.deleteCommerceDiscountRules(commerceDiscountId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountRuleLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceDiscountRuleLocalService.dynamicQuery();
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
		return _commerceDiscountRuleLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.discount.model.impl.CommerceDiscountRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceDiscountRuleLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.discount.model.impl.CommerceDiscountRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceDiscountRuleLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
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
		return _commerceDiscountRuleLocalService.dynamicQueryCount(dynamicQuery);
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
		return _commerceDiscountRuleLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.discount.model.CommerceDiscountRule fetchCommerceDiscountRule(
		long commerceDiscountRuleId) {
		return _commerceDiscountRuleLocalService.fetchCommerceDiscountRule(commerceDiscountRuleId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _commerceDiscountRuleLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the commerce discount rule with the primary key.
	*
	* @param commerceDiscountRuleId the primary key of the commerce discount rule
	* @return the commerce discount rule
	* @throws PortalException if a commerce discount rule with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.discount.model.CommerceDiscountRule getCommerceDiscountRule(
		long commerceDiscountRuleId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountRuleLocalService.getCommerceDiscountRule(commerceDiscountRuleId);
	}

	/**
	* Returns a range of all the commerce discount rules.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.discount.model.impl.CommerceDiscountRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce discount rules
	* @param end the upper bound of the range of commerce discount rules (not inclusive)
	* @return the range of commerce discount rules
	*/
	@Override
	public java.util.List<com.liferay.commerce.discount.model.CommerceDiscountRule> getCommerceDiscountRules(
		int start, int end) {
		return _commerceDiscountRuleLocalService.getCommerceDiscountRules(start,
			end);
	}

	/**
	* Returns the number of commerce discount rules.
	*
	* @return the number of commerce discount rules
	*/
	@Override
	public int getCommerceDiscountRulesCount() {
		return _commerceDiscountRuleLocalService.getCommerceDiscountRulesCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _commerceDiscountRuleLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceDiscountRuleLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountRuleLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the commerce discount rule in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceDiscountRule the commerce discount rule
	* @return the commerce discount rule that was updated
	*/
	@Override
	public com.liferay.commerce.discount.model.CommerceDiscountRule updateCommerceDiscountRule(
		com.liferay.commerce.discount.model.CommerceDiscountRule commerceDiscountRule) {
		return _commerceDiscountRuleLocalService.updateCommerceDiscountRule(commerceDiscountRule);
	}

	@Override
	public com.liferay.commerce.discount.model.CommerceDiscountRule updateCommerceDiscountRule(
		long commerceDiscountRuleId, java.lang.String type,
		java.lang.String typeSettings)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountRuleLocalService.updateCommerceDiscountRule(commerceDiscountRuleId,
			type, typeSettings);
	}

	@Override
	public CommerceDiscountRuleLocalService getWrappedService() {
		return _commerceDiscountRuleLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceDiscountRuleLocalService commerceDiscountRuleLocalService) {
		_commerceDiscountRuleLocalService = commerceDiscountRuleLocalService;
	}

	private CommerceDiscountRuleLocalService _commerceDiscountRuleLocalService;
}