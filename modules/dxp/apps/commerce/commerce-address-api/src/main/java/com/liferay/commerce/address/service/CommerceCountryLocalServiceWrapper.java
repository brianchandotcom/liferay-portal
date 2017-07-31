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

package com.liferay.commerce.address.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceCountryLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceCountryLocalService
 * @generated
 */
@ProviderType
public class CommerceCountryLocalServiceWrapper
	implements CommerceCountryLocalService,
		ServiceWrapper<CommerceCountryLocalService> {
	public CommerceCountryLocalServiceWrapper(
		CommerceCountryLocalService commerceCountryLocalService) {
		_commerceCountryLocalService = commerceCountryLocalService;
	}

	/**
	* Adds the commerce country to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceCountry the commerce country
	* @return the commerce country that was added
	*/
	@Override
	public com.liferay.commerce.address.model.CommerceCountry addCommerceCountry(
		com.liferay.commerce.address.model.CommerceCountry commerceCountry) {
		return _commerceCountryLocalService.addCommerceCountry(commerceCountry);
	}

	@Override
	public com.liferay.commerce.address.model.CommerceCountry addCommerceCountry(
		java.lang.String name, boolean allowsBilling, boolean allowsShipping,
		java.lang.String twoLettersISOCode,
		java.lang.String threeLettersISOCode, int numericISOCode, int priority,
		boolean published,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCountryLocalService.addCommerceCountry(name,
			allowsBilling, allowsShipping, twoLettersISOCode,
			threeLettersISOCode, numericISOCode, priority, published,
			serviceContext);
	}

	/**
	* Creates a new commerce country with the primary key. Does not add the commerce country to the database.
	*
	* @param commerceCountryId the primary key for the new commerce country
	* @return the new commerce country
	*/
	@Override
	public com.liferay.commerce.address.model.CommerceCountry createCommerceCountry(
		long commerceCountryId) {
		return _commerceCountryLocalService.createCommerceCountry(commerceCountryId);
	}

	/**
	* Deletes the commerce country from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceCountry the commerce country
	* @return the commerce country that was removed
	*/
	@Override
	public com.liferay.commerce.address.model.CommerceCountry deleteCommerceCountry(
		com.liferay.commerce.address.model.CommerceCountry commerceCountry) {
		return _commerceCountryLocalService.deleteCommerceCountry(commerceCountry);
	}

	/**
	* Deletes the commerce country with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceCountryId the primary key of the commerce country
	* @return the commerce country that was removed
	* @throws PortalException if a commerce country with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.address.model.CommerceCountry deleteCommerceCountry(
		long commerceCountryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCountryLocalService.deleteCommerceCountry(commerceCountryId);
	}

	@Override
	public com.liferay.commerce.address.model.CommerceCountry fetchCommerceCountry(
		long commerceCountryId) {
		return _commerceCountryLocalService.fetchCommerceCountry(commerceCountryId);
	}

	/**
	* Returns the commerce country with the primary key.
	*
	* @param commerceCountryId the primary key of the commerce country
	* @return the commerce country
	* @throws PortalException if a commerce country with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.address.model.CommerceCountry getCommerceCountry(
		long commerceCountryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCountryLocalService.getCommerceCountry(commerceCountryId);
	}

	/**
	* Updates the commerce country in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceCountry the commerce country
	* @return the commerce country that was updated
	*/
	@Override
	public com.liferay.commerce.address.model.CommerceCountry updateCommerceCountry(
		com.liferay.commerce.address.model.CommerceCountry commerceCountry) {
		return _commerceCountryLocalService.updateCommerceCountry(commerceCountry);
	}

	@Override
	public com.liferay.commerce.address.model.CommerceCountry updateCommerceCountry(
		long commerceCountryId, java.lang.String name, boolean allowsBilling,
		boolean allowsShipping, java.lang.String twoLettersISOCode,
		java.lang.String threeLettersISOCode, int numericISOCode, int priority,
		boolean published)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCountryLocalService.updateCommerceCountry(commerceCountryId,
			name, allowsBilling, allowsShipping, twoLettersISOCode,
			threeLettersISOCode, numericISOCode, priority, published);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _commerceCountryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceCountryLocalService.dynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _commerceCountryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCountryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCountryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the number of commerce countries.
	*
	* @return the number of commerce countries
	*/
	@Override
	public int getCommerceCountriesCount() {
		return _commerceCountryLocalService.getCommerceCountriesCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceCountryLocalService.getOSGiServiceIdentifier();
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
		return _commerceCountryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.address.model.impl.CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceCountryLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.address.model.impl.CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceCountryLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	* Returns a range of all the commerce countries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.address.model.impl.CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @return the range of commerce countries
	*/
	@Override
	public java.util.List<com.liferay.commerce.address.model.CommerceCountry> getCommerceCountries(
		int start, int end) {
		return _commerceCountryLocalService.getCommerceCountries(start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.address.model.CommerceCountry> getCommerceCountries(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.address.model.CommerceCountry> orderByComparator) {
		return _commerceCountryLocalService.getCommerceCountries(start, end,
			orderByComparator);
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
		return _commerceCountryLocalService.dynamicQueryCount(dynamicQuery);
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
		return _commerceCountryLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public CommerceCountryLocalService getWrappedService() {
		return _commerceCountryLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceCountryLocalService commerceCountryLocalService) {
		_commerceCountryLocalService = commerceCountryLocalService;
	}

	private CommerceCountryLocalService _commerceCountryLocalService;
}