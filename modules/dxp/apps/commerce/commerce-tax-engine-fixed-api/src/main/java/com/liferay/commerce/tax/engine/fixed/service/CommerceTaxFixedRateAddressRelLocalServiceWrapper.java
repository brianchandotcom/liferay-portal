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

package com.liferay.commerce.tax.engine.fixed.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceTaxFixedRateAddressRelLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxFixedRateAddressRelLocalService
 * @generated
 */
@ProviderType
public class CommerceTaxFixedRateAddressRelLocalServiceWrapper
	implements CommerceTaxFixedRateAddressRelLocalService,
		ServiceWrapper<CommerceTaxFixedRateAddressRelLocalService> {
	public CommerceTaxFixedRateAddressRelLocalServiceWrapper(
		CommerceTaxFixedRateAddressRelLocalService commerceTaxFixedRateAddressRelLocalService) {
		_commerceTaxFixedRateAddressRelLocalService = commerceTaxFixedRateAddressRelLocalService;
	}

	/**
	* Adds the commerce tax fixed rate address rel to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceTaxFixedRateAddressRel the commerce tax fixed rate address rel
	* @return the commerce tax fixed rate address rel that was added
	*/
	@Override
	public com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel addCommerceTaxFixedRateAddressRel(
		com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel) {
		return _commerceTaxFixedRateAddressRelLocalService.addCommerceTaxFixedRateAddressRel(commerceTaxFixedRateAddressRel);
	}

	@Override
	public com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel addCommerceTaxFixedRateAddressRel(
		long commerceTaxMethodId, long commerceTaxFixedRateId,
		long commerceCountryId, long commerceRegionId, java.lang.String zip,
		double rate,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTaxFixedRateAddressRelLocalService.addCommerceTaxFixedRateAddressRel(commerceTaxMethodId,
			commerceTaxFixedRateId, commerceCountryId, commerceRegionId, zip,
			rate, serviceContext);
	}

	/**
	* Creates a new commerce tax fixed rate address rel with the primary key. Does not add the commerce tax fixed rate address rel to the database.
	*
	* @param commerceTaxFixedRateAddressRelId the primary key for the new commerce tax fixed rate address rel
	* @return the new commerce tax fixed rate address rel
	*/
	@Override
	public com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel createCommerceTaxFixedRateAddressRel(
		long commerceTaxFixedRateAddressRelId) {
		return _commerceTaxFixedRateAddressRelLocalService.createCommerceTaxFixedRateAddressRel(commerceTaxFixedRateAddressRelId);
	}

	/**
	* Deletes the commerce tax fixed rate address rel from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceTaxFixedRateAddressRel the commerce tax fixed rate address rel
	* @return the commerce tax fixed rate address rel that was removed
	*/
	@Override
	public com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel deleteCommerceTaxFixedRateAddressRel(
		com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel) {
		return _commerceTaxFixedRateAddressRelLocalService.deleteCommerceTaxFixedRateAddressRel(commerceTaxFixedRateAddressRel);
	}

	/**
	* Deletes the commerce tax fixed rate address rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceTaxFixedRateAddressRelId the primary key of the commerce tax fixed rate address rel
	* @return the commerce tax fixed rate address rel that was removed
	* @throws PortalException if a commerce tax fixed rate address rel with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel deleteCommerceTaxFixedRateAddressRel(
		long commerceTaxFixedRateAddressRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTaxFixedRateAddressRelLocalService.deleteCommerceTaxFixedRateAddressRel(commerceTaxFixedRateAddressRelId);
	}

	@Override
	public void deleteCommerceTaxFixedRateAddressRels(long commerceTaxMethodId) {
		_commerceTaxFixedRateAddressRelLocalService.deleteCommerceTaxFixedRateAddressRels(commerceTaxMethodId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTaxFixedRateAddressRelLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceTaxFixedRateAddressRelLocalService.dynamicQuery();
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
		return _commerceTaxFixedRateAddressRelLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.tax.engine.fixed.model.impl.CommerceTaxFixedRateAddressRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceTaxFixedRateAddressRelLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.tax.engine.fixed.model.impl.CommerceTaxFixedRateAddressRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceTaxFixedRateAddressRelLocalService.dynamicQuery(dynamicQuery,
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
		return _commerceTaxFixedRateAddressRelLocalService.dynamicQueryCount(dynamicQuery);
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
		return _commerceTaxFixedRateAddressRelLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel fetchCommerceTaxFixedRateAddressRel(
		long commerceTaxFixedRateAddressRelId) {
		return _commerceTaxFixedRateAddressRelLocalService.fetchCommerceTaxFixedRateAddressRel(commerceTaxFixedRateAddressRelId);
	}

	@Override
	public com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel fetchCommerceTaxFixedRateAddressRel(
		long commerceTaxMethodId, long commerceCountryId,
		long commerceRegionId, java.lang.String zip) {
		return _commerceTaxFixedRateAddressRelLocalService.fetchCommerceTaxFixedRateAddressRel(commerceTaxMethodId,
			commerceCountryId, commerceRegionId, zip);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _commerceTaxFixedRateAddressRelLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the commerce tax fixed rate address rel with the primary key.
	*
	* @param commerceTaxFixedRateAddressRelId the primary key of the commerce tax fixed rate address rel
	* @return the commerce tax fixed rate address rel
	* @throws PortalException if a commerce tax fixed rate address rel with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel getCommerceTaxFixedRateAddressRel(
		long commerceTaxFixedRateAddressRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTaxFixedRateAddressRelLocalService.getCommerceTaxFixedRateAddressRel(commerceTaxFixedRateAddressRelId);
	}

	/**
	* Returns a range of all the commerce tax fixed rate address rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.tax.engine.fixed.model.impl.CommerceTaxFixedRateAddressRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce tax fixed rate address rels
	* @param end the upper bound of the range of commerce tax fixed rate address rels (not inclusive)
	* @return the range of commerce tax fixed rate address rels
	*/
	@Override
	public java.util.List<com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel> getCommerceTaxFixedRateAddressRels(
		int start, int end) {
		return _commerceTaxFixedRateAddressRelLocalService.getCommerceTaxFixedRateAddressRels(start,
			end);
	}

	@Override
	public java.util.List<com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel> getCommerceTaxFixedRateAddressRels(
		long commerceTaxFixedRateId, int start, int end) {
		return _commerceTaxFixedRateAddressRelLocalService.getCommerceTaxFixedRateAddressRels(commerceTaxFixedRateId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel> getCommerceTaxFixedRateAddressRels(
		long commerceTaxFixedRateId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel> orderByComparator) {
		return _commerceTaxFixedRateAddressRelLocalService.getCommerceTaxFixedRateAddressRels(commerceTaxFixedRateId,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of commerce tax fixed rate address rels.
	*
	* @return the number of commerce tax fixed rate address rels
	*/
	@Override
	public int getCommerceTaxFixedRateAddressRelsCount() {
		return _commerceTaxFixedRateAddressRelLocalService.getCommerceTaxFixedRateAddressRelsCount();
	}

	@Override
	public int getCommerceTaxFixedRateAddressRelsCount(
		long commerceTaxFixedRateId) {
		return _commerceTaxFixedRateAddressRelLocalService.getCommerceTaxFixedRateAddressRelsCount(commerceTaxFixedRateId);
	}

	@Override
	public java.util.List<com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel> getCommerceTaxMethodFixedRateAddressRels(
		long commerceTaxMethodId, int start, int end) {
		return _commerceTaxFixedRateAddressRelLocalService.getCommerceTaxMethodFixedRateAddressRels(commerceTaxMethodId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel> getCommerceTaxMethodFixedRateAddressRels(
		long commerceTaxMethodId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel> orderByComparator) {
		return _commerceTaxFixedRateAddressRelLocalService.getCommerceTaxMethodFixedRateAddressRels(commerceTaxMethodId,
			start, end, orderByComparator);
	}

	@Override
	public int getCommerceTaxMethodFixedRateAddressRelsCount(
		long commerceTaxMethodId) {
		return _commerceTaxFixedRateAddressRelLocalService.getCommerceTaxMethodFixedRateAddressRelsCount(commerceTaxMethodId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _commerceTaxFixedRateAddressRelLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceTaxFixedRateAddressRelLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTaxFixedRateAddressRelLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the commerce tax fixed rate address rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceTaxFixedRateAddressRel the commerce tax fixed rate address rel
	* @return the commerce tax fixed rate address rel that was updated
	*/
	@Override
	public com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel updateCommerceTaxFixedRateAddressRel(
		com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel) {
		return _commerceTaxFixedRateAddressRelLocalService.updateCommerceTaxFixedRateAddressRel(commerceTaxFixedRateAddressRel);
	}

	@Override
	public com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel updateCommerceTaxFixedRateAddressRel(
		long commerceTaxFixedRateAddressRelId, long commerceCountryId,
		long commerceRegionId, java.lang.String zip, double rate)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTaxFixedRateAddressRelLocalService.updateCommerceTaxFixedRateAddressRel(commerceTaxFixedRateAddressRelId,
			commerceCountryId, commerceRegionId, zip, rate);
	}

	@Override
	public CommerceTaxFixedRateAddressRelLocalService getWrappedService() {
		return _commerceTaxFixedRateAddressRelLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceTaxFixedRateAddressRelLocalService commerceTaxFixedRateAddressRelLocalService) {
		_commerceTaxFixedRateAddressRelLocalService = commerceTaxFixedRateAddressRelLocalService;
	}

	private CommerceTaxFixedRateAddressRelLocalService _commerceTaxFixedRateAddressRelLocalService;
}