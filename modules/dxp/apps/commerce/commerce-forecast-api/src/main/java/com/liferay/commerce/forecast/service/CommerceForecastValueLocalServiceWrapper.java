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

package com.liferay.commerce.forecast.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceForecastValueLocalService}.
 *
 * @author Andrea Di Giorgi
 * @see CommerceForecastValueLocalService
 * @generated
 */
@ProviderType
public class CommerceForecastValueLocalServiceWrapper
	implements CommerceForecastValueLocalService,
		ServiceWrapper<CommerceForecastValueLocalService> {
	public CommerceForecastValueLocalServiceWrapper(
		CommerceForecastValueLocalService commerceForecastValueLocalService) {
		_commerceForecastValueLocalService = commerceForecastValueLocalService;
	}

	/**
	* Adds the commerce forecast value to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceForecastValue the commerce forecast value
	* @return the commerce forecast value that was added
	*/
	@Override
	public com.liferay.commerce.forecast.model.CommerceForecastValue addCommerceForecastValue(
		com.liferay.commerce.forecast.model.CommerceForecastValue commerceForecastValue) {
		return _commerceForecastValueLocalService.addCommerceForecastValue(commerceForecastValue);
	}

	@Override
	public com.liferay.commerce.forecast.model.CommerceForecastValue addCommerceForecastValue(
		long userId, long commerceForecastEntryId, java.util.Date date,
		java.math.BigDecimal value)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceForecastValueLocalService.addCommerceForecastValue(userId,
			commerceForecastEntryId, date, value);
	}

	/**
	* Creates a new commerce forecast value with the primary key. Does not add the commerce forecast value to the database.
	*
	* @param commerceForecastValueId the primary key for the new commerce forecast value
	* @return the new commerce forecast value
	*/
	@Override
	public com.liferay.commerce.forecast.model.CommerceForecastValue createCommerceForecastValue(
		long commerceForecastValueId) {
		return _commerceForecastValueLocalService.createCommerceForecastValue(commerceForecastValueId);
	}

	/**
	* Deletes the commerce forecast value from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceForecastValue the commerce forecast value
	* @return the commerce forecast value that was removed
	*/
	@Override
	public com.liferay.commerce.forecast.model.CommerceForecastValue deleteCommerceForecastValue(
		com.liferay.commerce.forecast.model.CommerceForecastValue commerceForecastValue) {
		return _commerceForecastValueLocalService.deleteCommerceForecastValue(commerceForecastValue);
	}

	/**
	* Deletes the commerce forecast value with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceForecastValueId the primary key of the commerce forecast value
	* @return the commerce forecast value that was removed
	* @throws PortalException if a commerce forecast value with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.forecast.model.CommerceForecastValue deleteCommerceForecastValue(
		long commerceForecastValueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceForecastValueLocalService.deleteCommerceForecastValue(commerceForecastValueId);
	}

	@Override
	public void deleteCommerceForecastValues(long commerceForecastEntryId) {
		_commerceForecastValueLocalService.deleteCommerceForecastValues(commerceForecastEntryId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceForecastValueLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceForecastValueLocalService.dynamicQuery();
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
		return _commerceForecastValueLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.forecast.model.impl.CommerceForecastValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceForecastValueLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.forecast.model.impl.CommerceForecastValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceForecastValueLocalService.dynamicQuery(dynamicQuery,
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
		return _commerceForecastValueLocalService.dynamicQueryCount(dynamicQuery);
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
		return _commerceForecastValueLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.forecast.model.CommerceForecastValue fetchCommerceForecastValue(
		long commerceForecastValueId) {
		return _commerceForecastValueLocalService.fetchCommerceForecastValue(commerceForecastValueId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _commerceForecastValueLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the commerce forecast value with the primary key.
	*
	* @param commerceForecastValueId the primary key of the commerce forecast value
	* @return the commerce forecast value
	* @throws PortalException if a commerce forecast value with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.forecast.model.CommerceForecastValue getCommerceForecastValue(
		long commerceForecastValueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceForecastValueLocalService.getCommerceForecastValue(commerceForecastValueId);
	}

	/**
	* Returns a range of all the commerce forecast values.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.forecast.model.impl.CommerceForecastValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce forecast values
	* @param end the upper bound of the range of commerce forecast values (not inclusive)
	* @return the range of commerce forecast values
	*/
	@Override
	public java.util.List<com.liferay.commerce.forecast.model.CommerceForecastValue> getCommerceForecastValues(
		int start, int end) {
		return _commerceForecastValueLocalService.getCommerceForecastValues(start,
			end);
	}

	/**
	* Returns the number of commerce forecast values.
	*
	* @return the number of commerce forecast values
	*/
	@Override
	public int getCommerceForecastValuesCount() {
		return _commerceForecastValueLocalService.getCommerceForecastValuesCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _commerceForecastValueLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceForecastValueLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceForecastValueLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the commerce forecast value in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceForecastValue the commerce forecast value
	* @return the commerce forecast value that was updated
	*/
	@Override
	public com.liferay.commerce.forecast.model.CommerceForecastValue updateCommerceForecastValue(
		com.liferay.commerce.forecast.model.CommerceForecastValue commerceForecastValue) {
		return _commerceForecastValueLocalService.updateCommerceForecastValue(commerceForecastValue);
	}

	@Override
	public CommerceForecastValueLocalService getWrappedService() {
		return _commerceForecastValueLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceForecastValueLocalService commerceForecastValueLocalService) {
		_commerceForecastValueLocalService = commerceForecastValueLocalService;
	}

	private CommerceForecastValueLocalService _commerceForecastValueLocalService;
}