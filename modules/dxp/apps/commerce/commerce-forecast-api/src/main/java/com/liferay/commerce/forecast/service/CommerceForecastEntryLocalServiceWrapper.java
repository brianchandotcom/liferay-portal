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
 * Provides a wrapper for {@link CommerceForecastEntryLocalService}.
 *
 * @author Andrea Di Giorgi
 * @see CommerceForecastEntryLocalService
 * @generated
 */
@ProviderType
public class CommerceForecastEntryLocalServiceWrapper
	implements CommerceForecastEntryLocalService,
		ServiceWrapper<CommerceForecastEntryLocalService> {
	public CommerceForecastEntryLocalServiceWrapper(
		CommerceForecastEntryLocalService commerceForecastEntryLocalService) {
		_commerceForecastEntryLocalService = commerceForecastEntryLocalService;
	}

	/**
	* Adds the commerce forecast entry to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceForecastEntry the commerce forecast entry
	* @return the commerce forecast entry that was added
	*/
	@Override
	public com.liferay.commerce.forecast.model.CommerceForecastEntry addCommerceForecastEntry(
		com.liferay.commerce.forecast.model.CommerceForecastEntry commerceForecastEntry) {
		return _commerceForecastEntryLocalService.addCommerceForecastEntry(commerceForecastEntry);
	}

	@Override
	public com.liferay.commerce.forecast.model.CommerceForecastEntry addCommerceForecastEntry(
		long userId, java.util.Date date, int period, int target,
		long customerId, String sku, java.math.BigDecimal assertivity)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceForecastEntryLocalService.addCommerceForecastEntry(userId,
			date, period, target, customerId, sku, assertivity);
	}

	/**
	* Creates a new commerce forecast entry with the primary key. Does not add the commerce forecast entry to the database.
	*
	* @param commerceForecastEntryId the primary key for the new commerce forecast entry
	* @return the new commerce forecast entry
	*/
	@Override
	public com.liferay.commerce.forecast.model.CommerceForecastEntry createCommerceForecastEntry(
		long commerceForecastEntryId) {
		return _commerceForecastEntryLocalService.createCommerceForecastEntry(commerceForecastEntryId);
	}

	@Override
	public void deleteCommerceForecastEntries(long companyId) {
		_commerceForecastEntryLocalService.deleteCommerceForecastEntries(companyId);
	}

	/**
	* Deletes the commerce forecast entry from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceForecastEntry the commerce forecast entry
	* @return the commerce forecast entry that was removed
	*/
	@Override
	public com.liferay.commerce.forecast.model.CommerceForecastEntry deleteCommerceForecastEntry(
		com.liferay.commerce.forecast.model.CommerceForecastEntry commerceForecastEntry) {
		return _commerceForecastEntryLocalService.deleteCommerceForecastEntry(commerceForecastEntry);
	}

	/**
	* Deletes the commerce forecast entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceForecastEntryId the primary key of the commerce forecast entry
	* @return the commerce forecast entry that was removed
	* @throws PortalException if a commerce forecast entry with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.forecast.model.CommerceForecastEntry deleteCommerceForecastEntry(
		long commerceForecastEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceForecastEntryLocalService.deleteCommerceForecastEntry(commerceForecastEntryId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceForecastEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceForecastEntryLocalService.dynamicQuery();
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
		return _commerceForecastEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.forecast.model.impl.CommerceForecastEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceForecastEntryLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.forecast.model.impl.CommerceForecastEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceForecastEntryLocalService.dynamicQuery(dynamicQuery,
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
		return _commerceForecastEntryLocalService.dynamicQueryCount(dynamicQuery);
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
		return _commerceForecastEntryLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.forecast.model.CommerceForecastEntry fetchCommerceForecastEntry(
		long commerceForecastEntryId) {
		return _commerceForecastEntryLocalService.fetchCommerceForecastEntry(commerceForecastEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _commerceForecastEntryLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns a range of all the commerce forecast entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.forecast.model.impl.CommerceForecastEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce forecast entries
	* @param end the upper bound of the range of commerce forecast entries (not inclusive)
	* @return the range of commerce forecast entries
	*/
	@Override
	public java.util.List<com.liferay.commerce.forecast.model.CommerceForecastEntry> getCommerceForecastEntries(
		int start, int end) {
		return _commerceForecastEntryLocalService.getCommerceForecastEntries(start,
			end);
	}

	/**
	* Returns the number of commerce forecast entries.
	*
	* @return the number of commerce forecast entries
	*/
	@Override
	public int getCommerceForecastEntriesCount() {
		return _commerceForecastEntryLocalService.getCommerceForecastEntriesCount();
	}

	/**
	* Returns the commerce forecast entry with the primary key.
	*
	* @param commerceForecastEntryId the primary key of the commerce forecast entry
	* @return the commerce forecast entry
	* @throws PortalException if a commerce forecast entry with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.forecast.model.CommerceForecastEntry getCommerceForecastEntry(
		long commerceForecastEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceForecastEntryLocalService.getCommerceForecastEntry(commerceForecastEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _commerceForecastEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceForecastEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceForecastEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the commerce forecast entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceForecastEntry the commerce forecast entry
	* @return the commerce forecast entry that was updated
	*/
	@Override
	public com.liferay.commerce.forecast.model.CommerceForecastEntry updateCommerceForecastEntry(
		com.liferay.commerce.forecast.model.CommerceForecastEntry commerceForecastEntry) {
		return _commerceForecastEntryLocalService.updateCommerceForecastEntry(commerceForecastEntry);
	}

	@Override
	public CommerceForecastEntryLocalService getWrappedService() {
		return _commerceForecastEntryLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceForecastEntryLocalService commerceForecastEntryLocalService) {
		_commerceForecastEntryLocalService = commerceForecastEntryLocalService;
	}

	private CommerceForecastEntryLocalService _commerceForecastEntryLocalService;
}