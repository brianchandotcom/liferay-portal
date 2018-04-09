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

package com.liferay.commerce.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceAvailabilityRangeLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAvailabilityRangeLocalService
 * @generated
 */
@ProviderType
public class CommerceAvailabilityRangeLocalServiceWrapper
	implements CommerceAvailabilityRangeLocalService,
		ServiceWrapper<CommerceAvailabilityRangeLocalService> {
	public CommerceAvailabilityRangeLocalServiceWrapper(
		CommerceAvailabilityRangeLocalService commerceAvailabilityRangeLocalService) {
		_commerceAvailabilityRangeLocalService = commerceAvailabilityRangeLocalService;
	}

	/**
	* Adds the commerce availability range to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceAvailabilityRange the commerce availability range
	* @return the commerce availability range that was added
	*/
	@Override
	public com.liferay.commerce.model.CommerceAvailabilityRange addCommerceAvailabilityRange(
		com.liferay.commerce.model.CommerceAvailabilityRange commerceAvailabilityRange) {
		return _commerceAvailabilityRangeLocalService.addCommerceAvailabilityRange(commerceAvailabilityRange);
	}

	@Override
	public com.liferay.commerce.model.CommerceAvailabilityRange addCommerceAvailabilityRange(
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceAvailabilityRangeLocalService.addCommerceAvailabilityRange(titleMap,
			priority, serviceContext);
	}

	/**
	* Creates a new commerce availability range with the primary key. Does not add the commerce availability range to the database.
	*
	* @param commerceAvailabilityRangeId the primary key for the new commerce availability range
	* @return the new commerce availability range
	*/
	@Override
	public com.liferay.commerce.model.CommerceAvailabilityRange createCommerceAvailabilityRange(
		long commerceAvailabilityRangeId) {
		return _commerceAvailabilityRangeLocalService.createCommerceAvailabilityRange(commerceAvailabilityRangeId);
	}

	/**
	* Deletes the commerce availability range from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceAvailabilityRange the commerce availability range
	* @return the commerce availability range that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.commerce.model.CommerceAvailabilityRange deleteCommerceAvailabilityRange(
		com.liferay.commerce.model.CommerceAvailabilityRange commerceAvailabilityRange)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceAvailabilityRangeLocalService.deleteCommerceAvailabilityRange(commerceAvailabilityRange);
	}

	/**
	* Deletes the commerce availability range with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceAvailabilityRangeId the primary key of the commerce availability range
	* @return the commerce availability range that was removed
	* @throws PortalException if a commerce availability range with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceAvailabilityRange deleteCommerceAvailabilityRange(
		long commerceAvailabilityRangeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceAvailabilityRangeLocalService.deleteCommerceAvailabilityRange(commerceAvailabilityRangeId);
	}

	@Override
	public void deleteCommerceAvailabilityRanges(long groupId) {
		_commerceAvailabilityRangeLocalService.deleteCommerceAvailabilityRanges(groupId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceAvailabilityRangeLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceAvailabilityRangeLocalService.dynamicQuery();
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
		return _commerceAvailabilityRangeLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceAvailabilityRangeLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceAvailabilityRangeLocalService.dynamicQuery(dynamicQuery,
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
		return _commerceAvailabilityRangeLocalService.dynamicQueryCount(dynamicQuery);
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
		return _commerceAvailabilityRangeLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.model.CommerceAvailabilityRange fetchCommerceAvailabilityRange(
		long commerceAvailabilityRangeId) {
		return _commerceAvailabilityRangeLocalService.fetchCommerceAvailabilityRange(commerceAvailabilityRangeId);
	}

	/**
	* Returns the commerce availability range matching the UUID and group.
	*
	* @param uuid the commerce availability range's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce availability range, or <code>null</code> if a matching commerce availability range could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceAvailabilityRange fetchCommerceAvailabilityRangeByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _commerceAvailabilityRangeLocalService.fetchCommerceAvailabilityRangeByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _commerceAvailabilityRangeLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the commerce availability range with the primary key.
	*
	* @param commerceAvailabilityRangeId the primary key of the commerce availability range
	* @return the commerce availability range
	* @throws PortalException if a commerce availability range with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceAvailabilityRange getCommerceAvailabilityRange(
		long commerceAvailabilityRangeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceAvailabilityRangeLocalService.getCommerceAvailabilityRange(commerceAvailabilityRangeId);
	}

	/**
	* Returns the commerce availability range matching the UUID and group.
	*
	* @param uuid the commerce availability range's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce availability range
	* @throws PortalException if a matching commerce availability range could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceAvailabilityRange getCommerceAvailabilityRangeByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceAvailabilityRangeLocalService.getCommerceAvailabilityRangeByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns a range of all the commerce availability ranges.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce availability ranges
	* @param end the upper bound of the range of commerce availability ranges (not inclusive)
	* @return the range of commerce availability ranges
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CommerceAvailabilityRange> getCommerceAvailabilityRanges(
		int start, int end) {
		return _commerceAvailabilityRangeLocalService.getCommerceAvailabilityRanges(start,
			end);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceAvailabilityRange> getCommerceAvailabilityRanges(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceAvailabilityRange> orderByComparator) {
		return _commerceAvailabilityRangeLocalService.getCommerceAvailabilityRanges(groupId,
			start, end, orderByComparator);
	}

	/**
	* Returns all the commerce availability ranges matching the UUID and company.
	*
	* @param uuid the UUID of the commerce availability ranges
	* @param companyId the primary key of the company
	* @return the matching commerce availability ranges, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CommerceAvailabilityRange> getCommerceAvailabilityRangesByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _commerceAvailabilityRangeLocalService.getCommerceAvailabilityRangesByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of commerce availability ranges matching the UUID and company.
	*
	* @param uuid the UUID of the commerce availability ranges
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of commerce availability ranges
	* @param end the upper bound of the range of commerce availability ranges (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching commerce availability ranges, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CommerceAvailabilityRange> getCommerceAvailabilityRangesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceAvailabilityRange> orderByComparator) {
		return _commerceAvailabilityRangeLocalService.getCommerceAvailabilityRangesByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of commerce availability ranges.
	*
	* @return the number of commerce availability ranges
	*/
	@Override
	public int getCommerceAvailabilityRangesCount() {
		return _commerceAvailabilityRangeLocalService.getCommerceAvailabilityRangesCount();
	}

	@Override
	public int getCommerceAvailabilityRangesCount(long groupId) {
		return _commerceAvailabilityRangeLocalService.getCommerceAvailabilityRangesCount(groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _commerceAvailabilityRangeLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _commerceAvailabilityRangeLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceAvailabilityRangeLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceAvailabilityRangeLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the commerce availability range in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceAvailabilityRange the commerce availability range
	* @return the commerce availability range that was updated
	*/
	@Override
	public com.liferay.commerce.model.CommerceAvailabilityRange updateCommerceAvailabilityRange(
		com.liferay.commerce.model.CommerceAvailabilityRange commerceAvailabilityRange) {
		return _commerceAvailabilityRangeLocalService.updateCommerceAvailabilityRange(commerceAvailabilityRange);
	}

	@Override
	public com.liferay.commerce.model.CommerceAvailabilityRange updateCommerceAvailabilityRange(
		long commerceAvailabilityId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceAvailabilityRangeLocalService.updateCommerceAvailabilityRange(commerceAvailabilityId,
			titleMap, priority, serviceContext);
	}

	@Override
	public CommerceAvailabilityRangeLocalService getWrappedService() {
		return _commerceAvailabilityRangeLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceAvailabilityRangeLocalService commerceAvailabilityRangeLocalService) {
		_commerceAvailabilityRangeLocalService = commerceAvailabilityRangeLocalService;
	}

	private CommerceAvailabilityRangeLocalService _commerceAvailabilityRangeLocalService;
}