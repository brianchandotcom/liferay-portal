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
 * Provides a wrapper for {@link CommerceDiscountUserSegmentRelLocalService}.
 *
 * @author Marco Leo
 * @see CommerceDiscountUserSegmentRelLocalService
 * @generated
 */
@ProviderType
public class CommerceDiscountUserSegmentRelLocalServiceWrapper
	implements CommerceDiscountUserSegmentRelLocalService,
		ServiceWrapper<CommerceDiscountUserSegmentRelLocalService> {
	public CommerceDiscountUserSegmentRelLocalServiceWrapper(
		CommerceDiscountUserSegmentRelLocalService commerceDiscountUserSegmentRelLocalService) {
		_commerceDiscountUserSegmentRelLocalService = commerceDiscountUserSegmentRelLocalService;
	}

	/**
	* Adds the commerce discount user segment rel to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceDiscountUserSegmentRel the commerce discount user segment rel
	* @return the commerce discount user segment rel that was added
	*/
	@Override
	public com.liferay.commerce.discount.model.CommerceDiscountUserSegmentRel addCommerceDiscountUserSegmentRel(
		com.liferay.commerce.discount.model.CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel) {
		return _commerceDiscountUserSegmentRelLocalService.addCommerceDiscountUserSegmentRel(commerceDiscountUserSegmentRel);
	}

	@Override
	public com.liferay.commerce.discount.model.CommerceDiscountUserSegmentRel addCommerceDiscountUserSegmentRel(
		long commerceDiscountId, long commerceUserSegmentEntryId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountUserSegmentRelLocalService.addCommerceDiscountUserSegmentRel(commerceDiscountId,
			commerceUserSegmentEntryId, serviceContext);
	}

	/**
	* Creates a new commerce discount user segment rel with the primary key. Does not add the commerce discount user segment rel to the database.
	*
	* @param commerceDiscountUserSegmentRelId the primary key for the new commerce discount user segment rel
	* @return the new commerce discount user segment rel
	*/
	@Override
	public com.liferay.commerce.discount.model.CommerceDiscountUserSegmentRel createCommerceDiscountUserSegmentRel(
		long commerceDiscountUserSegmentRelId) {
		return _commerceDiscountUserSegmentRelLocalService.createCommerceDiscountUserSegmentRel(commerceDiscountUserSegmentRelId);
	}

	/**
	* Deletes the commerce discount user segment rel from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceDiscountUserSegmentRel the commerce discount user segment rel
	* @return the commerce discount user segment rel that was removed
	*/
	@Override
	public com.liferay.commerce.discount.model.CommerceDiscountUserSegmentRel deleteCommerceDiscountUserSegmentRel(
		com.liferay.commerce.discount.model.CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel) {
		return _commerceDiscountUserSegmentRelLocalService.deleteCommerceDiscountUserSegmentRel(commerceDiscountUserSegmentRel);
	}

	/**
	* Deletes the commerce discount user segment rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceDiscountUserSegmentRelId the primary key of the commerce discount user segment rel
	* @return the commerce discount user segment rel that was removed
	* @throws PortalException if a commerce discount user segment rel with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.discount.model.CommerceDiscountUserSegmentRel deleteCommerceDiscountUserSegmentRel(
		long commerceDiscountUserSegmentRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountUserSegmentRelLocalService.deleteCommerceDiscountUserSegmentRel(commerceDiscountUserSegmentRelId);
	}

	@Override
	public void deleteCommerceDiscountUserSegmentRelsByCommerceDiscountId(
		long commerceDiscountId) {
		_commerceDiscountUserSegmentRelLocalService.deleteCommerceDiscountUserSegmentRelsByCommerceDiscountId(commerceDiscountId);
	}

	@Override
	public void deleteCommerceDiscountUserSegmentRelsByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId) {
		_commerceDiscountUserSegmentRelLocalService.deleteCommerceDiscountUserSegmentRelsByCommerceUserSegmentEntryId(commerceUserSegmentEntryId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountUserSegmentRelLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceDiscountUserSegmentRelLocalService.dynamicQuery();
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
		return _commerceDiscountUserSegmentRelLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.discount.model.impl.CommerceDiscountUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceDiscountUserSegmentRelLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.discount.model.impl.CommerceDiscountUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceDiscountUserSegmentRelLocalService.dynamicQuery(dynamicQuery,
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
		return _commerceDiscountUserSegmentRelLocalService.dynamicQueryCount(dynamicQuery);
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
		return _commerceDiscountUserSegmentRelLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.discount.model.CommerceDiscountUserSegmentRel fetchCommerceDiscountUserSegmentRel(
		long commerceDiscountUserSegmentRelId) {
		return _commerceDiscountUserSegmentRelLocalService.fetchCommerceDiscountUserSegmentRel(commerceDiscountUserSegmentRelId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _commerceDiscountUserSegmentRelLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the commerce discount user segment rel with the primary key.
	*
	* @param commerceDiscountUserSegmentRelId the primary key of the commerce discount user segment rel
	* @return the commerce discount user segment rel
	* @throws PortalException if a commerce discount user segment rel with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.discount.model.CommerceDiscountUserSegmentRel getCommerceDiscountUserSegmentRel(
		long commerceDiscountUserSegmentRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountUserSegmentRelLocalService.getCommerceDiscountUserSegmentRel(commerceDiscountUserSegmentRelId);
	}

	/**
	* Returns a range of all the commerce discount user segment rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.discount.model.impl.CommerceDiscountUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce discount user segment rels
	* @param end the upper bound of the range of commerce discount user segment rels (not inclusive)
	* @return the range of commerce discount user segment rels
	*/
	@Override
	public java.util.List<com.liferay.commerce.discount.model.CommerceDiscountUserSegmentRel> getCommerceDiscountUserSegmentRels(
		int start, int end) {
		return _commerceDiscountUserSegmentRelLocalService.getCommerceDiscountUserSegmentRels(start,
			end);
	}

	/**
	* Returns the number of commerce discount user segment rels.
	*
	* @return the number of commerce discount user segment rels
	*/
	@Override
	public int getCommerceDiscountUserSegmentRelsCount() {
		return _commerceDiscountUserSegmentRelLocalService.getCommerceDiscountUserSegmentRelsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _commerceDiscountUserSegmentRelLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceDiscountUserSegmentRelLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountUserSegmentRelLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the commerce discount user segment rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceDiscountUserSegmentRel the commerce discount user segment rel
	* @return the commerce discount user segment rel that was updated
	*/
	@Override
	public com.liferay.commerce.discount.model.CommerceDiscountUserSegmentRel updateCommerceDiscountUserSegmentRel(
		com.liferay.commerce.discount.model.CommerceDiscountUserSegmentRel commerceDiscountUserSegmentRel) {
		return _commerceDiscountUserSegmentRelLocalService.updateCommerceDiscountUserSegmentRel(commerceDiscountUserSegmentRel);
	}

	@Override
	public CommerceDiscountUserSegmentRelLocalService getWrappedService() {
		return _commerceDiscountUserSegmentRelLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceDiscountUserSegmentRelLocalService commerceDiscountUserSegmentRelLocalService) {
		_commerceDiscountUserSegmentRelLocalService = commerceDiscountUserSegmentRelLocalService;
	}

	private CommerceDiscountUserSegmentRelLocalService _commerceDiscountUserSegmentRelLocalService;
}