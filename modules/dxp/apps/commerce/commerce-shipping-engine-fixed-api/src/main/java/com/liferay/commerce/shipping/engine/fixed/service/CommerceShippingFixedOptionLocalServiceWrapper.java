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

package com.liferay.commerce.shipping.engine.fixed.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceShippingFixedOptionLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingFixedOptionLocalService
 * @generated
 */
@ProviderType
public class CommerceShippingFixedOptionLocalServiceWrapper
	implements CommerceShippingFixedOptionLocalService,
		ServiceWrapper<CommerceShippingFixedOptionLocalService> {
	public CommerceShippingFixedOptionLocalServiceWrapper(
		CommerceShippingFixedOptionLocalService commerceShippingFixedOptionLocalService) {
		_commerceShippingFixedOptionLocalService = commerceShippingFixedOptionLocalService;
	}

	/**
	* Adds the commerce shipping fixed option to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceShippingFixedOption the commerce shipping fixed option
	* @return the commerce shipping fixed option that was added
	*/
	@Override
	public com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption addCommerceShippingFixedOption(
		com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption commerceShippingFixedOption) {
		return _commerceShippingFixedOptionLocalService.addCommerceShippingFixedOption(commerceShippingFixedOption);
	}

	@Override
	public com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption addCommerceShippingFixedOption(
		long commerceShippingMethodId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		double amount, double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceShippingFixedOptionLocalService.addCommerceShippingFixedOption(commerceShippingMethodId,
			nameMap, descriptionMap, amount, priority, serviceContext);
	}

	/**
	* Creates a new commerce shipping fixed option with the primary key. Does not add the commerce shipping fixed option to the database.
	*
	* @param commerceShippingFixedOptionId the primary key for the new commerce shipping fixed option
	* @return the new commerce shipping fixed option
	*/
	@Override
	public com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption createCommerceShippingFixedOption(
		long commerceShippingFixedOptionId) {
		return _commerceShippingFixedOptionLocalService.createCommerceShippingFixedOption(commerceShippingFixedOptionId);
	}

	/**
	* Deletes the commerce shipping fixed option from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceShippingFixedOption the commerce shipping fixed option
	* @return the commerce shipping fixed option that was removed
	*/
	@Override
	public com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption deleteCommerceShippingFixedOption(
		com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption commerceShippingFixedOption) {
		return _commerceShippingFixedOptionLocalService.deleteCommerceShippingFixedOption(commerceShippingFixedOption);
	}

	/**
	* Deletes the commerce shipping fixed option with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceShippingFixedOptionId the primary key of the commerce shipping fixed option
	* @return the commerce shipping fixed option that was removed
	* @throws PortalException if a commerce shipping fixed option with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption deleteCommerceShippingFixedOption(
		long commerceShippingFixedOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceShippingFixedOptionLocalService.deleteCommerceShippingFixedOption(commerceShippingFixedOptionId);
	}

	@Override
	public void deleteCommerceShippingFixedOptions(
		long commerceShippingMethodId) {
		_commerceShippingFixedOptionLocalService.deleteCommerceShippingFixedOptions(commerceShippingMethodId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceShippingFixedOptionLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceShippingFixedOptionLocalService.dynamicQuery();
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
		return _commerceShippingFixedOptionLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.shipping.engine.fixed.model.impl.CommerceShippingFixedOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceShippingFixedOptionLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.shipping.engine.fixed.model.impl.CommerceShippingFixedOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceShippingFixedOptionLocalService.dynamicQuery(dynamicQuery,
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
		return _commerceShippingFixedOptionLocalService.dynamicQueryCount(dynamicQuery);
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
		return _commerceShippingFixedOptionLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption fetchCommerceShippingFixedOption(
		long commerceShippingFixedOptionId) {
		return _commerceShippingFixedOptionLocalService.fetchCommerceShippingFixedOption(commerceShippingFixedOptionId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _commerceShippingFixedOptionLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the commerce shipping fixed option with the primary key.
	*
	* @param commerceShippingFixedOptionId the primary key of the commerce shipping fixed option
	* @return the commerce shipping fixed option
	* @throws PortalException if a commerce shipping fixed option with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption getCommerceShippingFixedOption(
		long commerceShippingFixedOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceShippingFixedOptionLocalService.getCommerceShippingFixedOption(commerceShippingFixedOptionId);
	}

	/**
	* Returns a range of all the commerce shipping fixed options.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.shipping.engine.fixed.model.impl.CommerceShippingFixedOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce shipping fixed options
	* @param end the upper bound of the range of commerce shipping fixed options (not inclusive)
	* @return the range of commerce shipping fixed options
	*/
	@Override
	public java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption> getCommerceShippingFixedOptions(
		int start, int end) {
		return _commerceShippingFixedOptionLocalService.getCommerceShippingFixedOptions(start,
			end);
	}

	@Override
	public java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption> getCommerceShippingFixedOptions(
		long commerceShippingMethodId, int start, int end) {
		return _commerceShippingFixedOptionLocalService.getCommerceShippingFixedOptions(commerceShippingMethodId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption> getCommerceShippingFixedOptions(
		long commerceShippingMethodId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption> orderByComparator) {
		return _commerceShippingFixedOptionLocalService.getCommerceShippingFixedOptions(commerceShippingMethodId,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of commerce shipping fixed options.
	*
	* @return the number of commerce shipping fixed options
	*/
	@Override
	public int getCommerceShippingFixedOptionsCount() {
		return _commerceShippingFixedOptionLocalService.getCommerceShippingFixedOptionsCount();
	}

	@Override
	public int getCommerceShippingFixedOptionsCount(
		long commerceShippingMethodId) {
		return _commerceShippingFixedOptionLocalService.getCommerceShippingFixedOptionsCount(commerceShippingMethodId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _commerceShippingFixedOptionLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceShippingFixedOptionLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceShippingFixedOptionLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the commerce shipping fixed option in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceShippingFixedOption the commerce shipping fixed option
	* @return the commerce shipping fixed option that was updated
	*/
	@Override
	public com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption updateCommerceShippingFixedOption(
		com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption commerceShippingFixedOption) {
		return _commerceShippingFixedOptionLocalService.updateCommerceShippingFixedOption(commerceShippingFixedOption);
	}

	@Override
	public com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption updateCommerceShippingFixedOption(
		long commerceShippingFixedOptionId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		double amount, double priority)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceShippingFixedOptionLocalService.updateCommerceShippingFixedOption(commerceShippingFixedOptionId,
			nameMap, descriptionMap, amount, priority);
	}

	@Override
	public CommerceShippingFixedOptionLocalService getWrappedService() {
		return _commerceShippingFixedOptionLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceShippingFixedOptionLocalService commerceShippingFixedOptionLocalService) {
		_commerceShippingFixedOptionLocalService = commerceShippingFixedOptionLocalService;
	}

	private CommerceShippingFixedOptionLocalService _commerceShippingFixedOptionLocalService;
}