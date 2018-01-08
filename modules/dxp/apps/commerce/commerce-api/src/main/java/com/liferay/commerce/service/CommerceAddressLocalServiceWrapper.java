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
 * Provides a wrapper for {@link CommerceAddressLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAddressLocalService
 * @generated
 */
@ProviderType
public class CommerceAddressLocalServiceWrapper
	implements CommerceAddressLocalService,
		ServiceWrapper<CommerceAddressLocalService> {
	public CommerceAddressLocalServiceWrapper(
		CommerceAddressLocalService commerceAddressLocalService) {
		_commerceAddressLocalService = commerceAddressLocalService;
	}

	/**
	* Adds the commerce address to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceAddress the commerce address
	* @return the commerce address that was added
	*/
	@Override
	public com.liferay.commerce.model.CommerceAddress addCommerceAddress(
		com.liferay.commerce.model.CommerceAddress commerceAddress) {
		return _commerceAddressLocalService.addCommerceAddress(commerceAddress);
	}

	@Override
	public com.liferay.commerce.model.CommerceAddress addCommerceAddress(
		java.lang.String className, long classPK, java.lang.String name,
		java.lang.String description, java.lang.String street1,
		java.lang.String street2, java.lang.String street3,
		java.lang.String city, java.lang.String zip, long commerceRegionId,
		long commerceCountryId, java.lang.String phoneNumber,
		boolean defaultBilling, boolean defaultShipping,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceAddressLocalService.addCommerceAddress(className,
			classPK, name, description, street1, street2, street3, city, zip,
			commerceRegionId, commerceCountryId, phoneNumber, defaultBilling,
			defaultShipping, serviceContext);
	}

	@Override
	public com.liferay.commerce.model.CommerceAddress copyCommerceAddress(
		long commerceAddressId, java.lang.String className, long classPK,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceAddressLocalService.copyCommerceAddress(commerceAddressId,
			className, classPK, serviceContext);
	}

	/**
	* Creates a new commerce address with the primary key. Does not add the commerce address to the database.
	*
	* @param commerceAddressId the primary key for the new commerce address
	* @return the new commerce address
	*/
	@Override
	public com.liferay.commerce.model.CommerceAddress createCommerceAddress(
		long commerceAddressId) {
		return _commerceAddressLocalService.createCommerceAddress(commerceAddressId);
	}

	/**
	* Deletes the commerce address from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceAddress the commerce address
	* @return the commerce address that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.commerce.model.CommerceAddress deleteCommerceAddress(
		com.liferay.commerce.model.CommerceAddress commerceAddress)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceAddressLocalService.deleteCommerceAddress(commerceAddress);
	}

	/**
	* Deletes the commerce address with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceAddressId the primary key of the commerce address
	* @return the commerce address that was removed
	* @throws PortalException if a commerce address with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceAddress deleteCommerceAddress(
		long commerceAddressId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceAddressLocalService.deleteCommerceAddress(commerceAddressId);
	}

	@Override
	public void deleteCommerceAddresses(java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceAddressLocalService.deleteCommerceAddresses(className, classPK);
	}

	@Override
	public void deleteCountryCommerceAddresses(long commerceCountryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceAddressLocalService.deleteCountryCommerceAddresses(commerceCountryId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceAddressLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public void deleteRegionCommerceAddresses(long commerceRegionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceAddressLocalService.deleteRegionCommerceAddresses(commerceRegionId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceAddressLocalService.dynamicQuery();
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
		return _commerceAddressLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceAddressLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceAddressLocalService.dynamicQuery(dynamicQuery, start,
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
		return _commerceAddressLocalService.dynamicQueryCount(dynamicQuery);
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
		return _commerceAddressLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.model.CommerceAddress fetchCommerceAddress(
		long commerceAddressId) {
		return _commerceAddressLocalService.fetchCommerceAddress(commerceAddressId);
	}

	@Override
	public com.liferay.commerce.model.CommerceAddress geolocateCommerceAddress(
		long commerceAddressId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceAddressLocalService.geolocateCommerceAddress(commerceAddressId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _commerceAddressLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the commerce address with the primary key.
	*
	* @param commerceAddressId the primary key of the commerce address
	* @return the commerce address
	* @throws PortalException if a commerce address with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceAddress getCommerceAddress(
		long commerceAddressId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceAddressLocalService.getCommerceAddress(commerceAddressId);
	}

	/**
	* Returns a range of all the commerce addresses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce addresses
	* @param end the upper bound of the range of commerce addresses (not inclusive)
	* @return the range of commerce addresses
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CommerceAddress> getCommerceAddresses(
		int start, int end) {
		return _commerceAddressLocalService.getCommerceAddresses(start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceAddress> getCommerceAddresses(
		long groupId, java.lang.String className, long classPK) {
		return _commerceAddressLocalService.getCommerceAddresses(groupId,
			className, classPK);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceAddress> getCommerceAddresses(
		long groupId, java.lang.String className, long classPK, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceAddress> orderByComparator) {
		return _commerceAddressLocalService.getCommerceAddresses(groupId,
			className, classPK, start, end, orderByComparator);
	}

	/**
	* Returns the number of commerce addresses.
	*
	* @return the number of commerce addresses
	*/
	@Override
	public int getCommerceAddressesCount() {
		return _commerceAddressLocalService.getCommerceAddressesCount();
	}

	@Override
	public int getCommerceAddressesCount(long groupId,
		java.lang.String className, long classPK) {
		return _commerceAddressLocalService.getCommerceAddressesCount(groupId,
			className, classPK);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _commerceAddressLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceAddressLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceAddressLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the commerce address in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceAddress the commerce address
	* @return the commerce address that was updated
	*/
	@Override
	public com.liferay.commerce.model.CommerceAddress updateCommerceAddress(
		com.liferay.commerce.model.CommerceAddress commerceAddress) {
		return _commerceAddressLocalService.updateCommerceAddress(commerceAddress);
	}

	@Override
	public com.liferay.commerce.model.CommerceAddress updateCommerceAddress(
		long commerceAddressId, java.lang.String name,
		java.lang.String description, java.lang.String street1,
		java.lang.String street2, java.lang.String street3,
		java.lang.String city, java.lang.String zip, long commerceRegionId,
		long commerceCountryId, java.lang.String phoneNumber,
		boolean defaultBilling, boolean defaultShipping,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceAddressLocalService.updateCommerceAddress(commerceAddressId,
			name, description, street1, street2, street3, city, zip,
			commerceRegionId, commerceCountryId, phoneNumber, defaultBilling,
			defaultShipping, serviceContext);
	}

	@Override
	public CommerceAddressLocalService getWrappedService() {
		return _commerceAddressLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceAddressLocalService commerceAddressLocalService) {
		_commerceAddressLocalService = commerceAddressLocalService;
	}

	private CommerceAddressLocalService _commerceAddressLocalService;
}