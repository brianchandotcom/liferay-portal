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
 * Provides a wrapper for {@link CommerceDiscountLocalService}.
 *
 * @author Marco Leo
 * @see CommerceDiscountLocalService
 * @generated
 */
@ProviderType
public class CommerceDiscountLocalServiceWrapper
	implements CommerceDiscountLocalService,
		ServiceWrapper<CommerceDiscountLocalService> {
	public CommerceDiscountLocalServiceWrapper(
		CommerceDiscountLocalService commerceDiscountLocalService) {
		_commerceDiscountLocalService = commerceDiscountLocalService;
	}

	/**
	* Adds the commerce discount to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceDiscount the commerce discount
	* @return the commerce discount that was added
	*/
	@Override
	public com.liferay.commerce.discount.model.CommerceDiscount addCommerceDiscount(
		com.liferay.commerce.discount.model.CommerceDiscount commerceDiscount) {
		return _commerceDiscountLocalService.addCommerceDiscount(commerceDiscount);
	}

	@Override
	public com.liferay.commerce.discount.model.CommerceDiscount addCommerceDiscount(
		String title, String target, boolean useCouponCode, String couponCode,
		boolean usePercentage, java.math.BigDecimal maximumDiscountAmount,
		java.math.BigDecimal level1, java.math.BigDecimal level2,
		java.math.BigDecimal level3, String limitationType,
		int limitationTimes, boolean cumulative, boolean active,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountLocalService.addCommerceDiscount(title, target,
			useCouponCode, couponCode, usePercentage, maximumDiscountAmount,
			level1, level2, level3, limitationType, limitationTimes,
			cumulative, active, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	@Override
	public void checkCommerceDiscounts()
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceDiscountLocalService.checkCommerceDiscounts();
	}

	/**
	* Creates a new commerce discount with the primary key. Does not add the commerce discount to the database.
	*
	* @param commerceDiscountId the primary key for the new commerce discount
	* @return the new commerce discount
	*/
	@Override
	public com.liferay.commerce.discount.model.CommerceDiscount createCommerceDiscount(
		long commerceDiscountId) {
		return _commerceDiscountLocalService.createCommerceDiscount(commerceDiscountId);
	}

	/**
	* Deletes the commerce discount from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceDiscount the commerce discount
	* @return the commerce discount that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.commerce.discount.model.CommerceDiscount deleteCommerceDiscount(
		com.liferay.commerce.discount.model.CommerceDiscount commerceDiscount)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountLocalService.deleteCommerceDiscount(commerceDiscount);
	}

	/**
	* Deletes the commerce discount with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceDiscountId the primary key of the commerce discount
	* @return the commerce discount that was removed
	* @throws PortalException if a commerce discount with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.discount.model.CommerceDiscount deleteCommerceDiscount(
		long commerceDiscountId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountLocalService.deleteCommerceDiscount(commerceDiscountId);
	}

	@Override
	public void deleteCommerceDiscounts(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceDiscountLocalService.deleteCommerceDiscounts(groupId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceDiscountLocalService.dynamicQuery();
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
		return _commerceDiscountLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.discount.model.impl.CommerceDiscountModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceDiscountLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.discount.model.impl.CommerceDiscountModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceDiscountLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
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
		return _commerceDiscountLocalService.dynamicQueryCount(dynamicQuery);
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
		return _commerceDiscountLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.discount.model.CommerceDiscount fetchCommerceDiscount(
		long commerceDiscountId) {
		return _commerceDiscountLocalService.fetchCommerceDiscount(commerceDiscountId);
	}

	/**
	* Returns the commerce discount matching the UUID and group.
	*
	* @param uuid the commerce discount's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	*/
	@Override
	public com.liferay.commerce.discount.model.CommerceDiscount fetchCommerceDiscountByUuidAndGroupId(
		String uuid, long groupId) {
		return _commerceDiscountLocalService.fetchCommerceDiscountByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _commerceDiscountLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the commerce discount with the primary key.
	*
	* @param commerceDiscountId the primary key of the commerce discount
	* @return the commerce discount
	* @throws PortalException if a commerce discount with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.discount.model.CommerceDiscount getCommerceDiscount(
		long commerceDiscountId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountLocalService.getCommerceDiscount(commerceDiscountId);
	}

	/**
	* Returns the commerce discount matching the UUID and group.
	*
	* @param uuid the commerce discount's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce discount
	* @throws PortalException if a matching commerce discount could not be found
	*/
	@Override
	public com.liferay.commerce.discount.model.CommerceDiscount getCommerceDiscountByUuidAndGroupId(
		String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountLocalService.getCommerceDiscountByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns a range of all the commerce discounts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.discount.model.impl.CommerceDiscountModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce discounts
	* @param end the upper bound of the range of commerce discounts (not inclusive)
	* @return the range of commerce discounts
	*/
	@Override
	public java.util.List<com.liferay.commerce.discount.model.CommerceDiscount> getCommerceDiscounts(
		int start, int end) {
		return _commerceDiscountLocalService.getCommerceDiscounts(start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.discount.model.CommerceDiscount> getCommerceDiscounts(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.discount.model.CommerceDiscount> orderByComparator) {
		return _commerceDiscountLocalService.getCommerceDiscounts(groupId,
			start, end, orderByComparator);
	}

	/**
	* Returns all the commerce discounts matching the UUID and company.
	*
	* @param uuid the UUID of the commerce discounts
	* @param companyId the primary key of the company
	* @return the matching commerce discounts, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.discount.model.CommerceDiscount> getCommerceDiscountsByUuidAndCompanyId(
		String uuid, long companyId) {
		return _commerceDiscountLocalService.getCommerceDiscountsByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of commerce discounts matching the UUID and company.
	*
	* @param uuid the UUID of the commerce discounts
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of commerce discounts
	* @param end the upper bound of the range of commerce discounts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching commerce discounts, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.discount.model.CommerceDiscount> getCommerceDiscountsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.discount.model.CommerceDiscount> orderByComparator) {
		return _commerceDiscountLocalService.getCommerceDiscountsByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of commerce discounts.
	*
	* @return the number of commerce discounts
	*/
	@Override
	public int getCommerceDiscountsCount() {
		return _commerceDiscountLocalService.getCommerceDiscountsCount();
	}

	@Override
	public int getCommerceDiscountsCount(long groupId) {
		return _commerceDiscountLocalService.getCommerceDiscountsCount(groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _commerceDiscountLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _commerceDiscountLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceDiscountLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.commerce.discount.model.CommerceDiscount incrementCommerceDiscountNumberOfUse(
		long commerceDiscountId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountLocalService.incrementCommerceDiscountNumberOfUse(commerceDiscountId);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.discount.model.CommerceDiscount> searchCommerceDiscounts(
		long companyId, long groupId, String keywords, int status, int start,
		int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountLocalService.searchCommerceDiscounts(companyId,
			groupId, keywords, status, start, end, sort);
	}

	/**
	* Updates the commerce discount in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceDiscount the commerce discount
	* @return the commerce discount that was updated
	*/
	@Override
	public com.liferay.commerce.discount.model.CommerceDiscount updateCommerceDiscount(
		com.liferay.commerce.discount.model.CommerceDiscount commerceDiscount) {
		return _commerceDiscountLocalService.updateCommerceDiscount(commerceDiscount);
	}

	@Override
	public com.liferay.commerce.discount.model.CommerceDiscount updateCommerceDiscount(
		long commerceDiscountId, String title, String target,
		boolean useCouponCode, String couponCode, boolean usePercentage,
		java.math.BigDecimal maximumDiscountAmount,
		java.math.BigDecimal level1, java.math.BigDecimal level2,
		java.math.BigDecimal level3, String limitationType,
		int limitationTimes, boolean cumulative, boolean active,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountLocalService.updateCommerceDiscount(commerceDiscountId,
			title, target, useCouponCode, couponCode, usePercentage,
			maximumDiscountAmount, level1, level2, level3, limitationType,
			limitationTimes, cumulative, active, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	@Override
	public com.liferay.commerce.discount.model.CommerceDiscount updateStatus(
		long userId, long commerceDiscountId, int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext,
		java.util.Map<String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountLocalService.updateStatus(userId,
			commerceDiscountId, status, serviceContext, workflowContext);
	}

	@Override
	public CommerceDiscountLocalService getWrappedService() {
		return _commerceDiscountLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceDiscountLocalService commerceDiscountLocalService) {
		_commerceDiscountLocalService = commerceDiscountLocalService;
	}

	private CommerceDiscountLocalService _commerceDiscountLocalService;
}