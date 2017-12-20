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
 * Provides a wrapper for {@link CShippingFixedOptionRelLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CShippingFixedOptionRelLocalService
 * @generated
 */
@ProviderType
public class CShippingFixedOptionRelLocalServiceWrapper
	implements CShippingFixedOptionRelLocalService,
		ServiceWrapper<CShippingFixedOptionRelLocalService> {
	public CShippingFixedOptionRelLocalServiceWrapper(
		CShippingFixedOptionRelLocalService cShippingFixedOptionRelLocalService) {
		_cShippingFixedOptionRelLocalService = cShippingFixedOptionRelLocalService;
	}

	/**
	* Adds the c shipping fixed option rel to the database. Also notifies the appropriate model listeners.
	*
	* @param cShippingFixedOptionRel the c shipping fixed option rel
	* @return the c shipping fixed option rel that was added
	*/
	@Override
	public com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel addCShippingFixedOptionRel(
		com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel cShippingFixedOptionRel) {
		return _cShippingFixedOptionRelLocalService.addCShippingFixedOptionRel(cShippingFixedOptionRel);
	}

	@Override
	public com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel addCShippingFixedOptionRel(
		long commerceShippingMethodId, long commerceShippingFixedOptionId,
		long commerceWarehouseId, long commerceCountryId,
		long commerceRegionId, java.lang.String zip, double weightFrom,
		double weightTo, double fixedPrice, double rateUnitWeightPrice,
		double ratePercentage,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cShippingFixedOptionRelLocalService.addCShippingFixedOptionRel(commerceShippingMethodId,
			commerceShippingFixedOptionId, commerceWarehouseId,
			commerceCountryId, commerceRegionId, zip, weightFrom, weightTo,
			fixedPrice, rateUnitWeightPrice, ratePercentage, serviceContext);
	}

	/**
	* Creates a new c shipping fixed option rel with the primary key. Does not add the c shipping fixed option rel to the database.
	*
	* @param CShippingFixedOptionRelId the primary key for the new c shipping fixed option rel
	* @return the new c shipping fixed option rel
	*/
	@Override
	public com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel createCShippingFixedOptionRel(
		long CShippingFixedOptionRelId) {
		return _cShippingFixedOptionRelLocalService.createCShippingFixedOptionRel(CShippingFixedOptionRelId);
	}

	/**
	* Deletes the c shipping fixed option rel from the database. Also notifies the appropriate model listeners.
	*
	* @param cShippingFixedOptionRel the c shipping fixed option rel
	* @return the c shipping fixed option rel that was removed
	*/
	@Override
	public com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel deleteCShippingFixedOptionRel(
		com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel cShippingFixedOptionRel) {
		return _cShippingFixedOptionRelLocalService.deleteCShippingFixedOptionRel(cShippingFixedOptionRel);
	}

	/**
	* Deletes the c shipping fixed option rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CShippingFixedOptionRelId the primary key of the c shipping fixed option rel
	* @return the c shipping fixed option rel that was removed
	* @throws PortalException if a c shipping fixed option rel with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel deleteCShippingFixedOptionRel(
		long CShippingFixedOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cShippingFixedOptionRelLocalService.deleteCShippingFixedOptionRel(CShippingFixedOptionRelId);
	}

	@Override
	public void deleteCShippingFixedOptionRels(
		long commerceShippingFixedOptionId) {
		_cShippingFixedOptionRelLocalService.deleteCShippingFixedOptionRels(commerceShippingFixedOptionId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cShippingFixedOptionRelLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _cShippingFixedOptionRelLocalService.dynamicQuery();
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
		return _cShippingFixedOptionRelLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.shipping.engine.fixed.model.impl.CShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cShippingFixedOptionRelLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.shipping.engine.fixed.model.impl.CShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cShippingFixedOptionRelLocalService.dynamicQuery(dynamicQuery,
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
		return _cShippingFixedOptionRelLocalService.dynamicQueryCount(dynamicQuery);
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
		return _cShippingFixedOptionRelLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel fetchCShippingFixedOptionRel(
		long CShippingFixedOptionRelId) {
		return _cShippingFixedOptionRelLocalService.fetchCShippingFixedOptionRel(CShippingFixedOptionRelId);
	}

	@Override
	public com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel fetchCShippingFixedOptionRel(
		long commerceShippingFixedOptionId, long commerceCountryId,
		long commerceRegionId, java.lang.String zip, double weight) {
		return _cShippingFixedOptionRelLocalService.fetchCShippingFixedOptionRel(commerceShippingFixedOptionId,
			commerceCountryId, commerceRegionId, zip, weight);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _cShippingFixedOptionRelLocalService.getActionableDynamicQuery();
	}

	@Override
	public java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel> getCommerceShippingMethodFixedOptionRels(
		long commerceShippingMethodId, int start, int end) {
		return _cShippingFixedOptionRelLocalService.getCommerceShippingMethodFixedOptionRels(commerceShippingMethodId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel> getCommerceShippingMethodFixedOptionRels(
		long commerceShippingMethodId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel> orderByComparator) {
		return _cShippingFixedOptionRelLocalService.getCommerceShippingMethodFixedOptionRels(commerceShippingMethodId,
			start, end, orderByComparator);
	}

	@Override
	public int getCommerceShippingMethodFixedOptionRelsCount(
		long commerceShippingMethodId) {
		return _cShippingFixedOptionRelLocalService.getCommerceShippingMethodFixedOptionRelsCount(commerceShippingMethodId);
	}

	/**
	* Returns the c shipping fixed option rel with the primary key.
	*
	* @param CShippingFixedOptionRelId the primary key of the c shipping fixed option rel
	* @return the c shipping fixed option rel
	* @throws PortalException if a c shipping fixed option rel with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel getCShippingFixedOptionRel(
		long CShippingFixedOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cShippingFixedOptionRelLocalService.getCShippingFixedOptionRel(CShippingFixedOptionRelId);
	}

	/**
	* Returns a range of all the c shipping fixed option rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.shipping.engine.fixed.model.impl.CShippingFixedOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of c shipping fixed option rels
	* @param end the upper bound of the range of c shipping fixed option rels (not inclusive)
	* @return the range of c shipping fixed option rels
	*/
	@Override
	public java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel> getCShippingFixedOptionRels(
		int start, int end) {
		return _cShippingFixedOptionRelLocalService.getCShippingFixedOptionRels(start,
			end);
	}

	@Override
	public java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel> getCShippingFixedOptionRels(
		long commerceShippingFixedOptionId, int start, int end) {
		return _cShippingFixedOptionRelLocalService.getCShippingFixedOptionRels(commerceShippingFixedOptionId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel> getCShippingFixedOptionRels(
		long commerceShippingFixedOptionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel> orderByComparator) {
		return _cShippingFixedOptionRelLocalService.getCShippingFixedOptionRels(commerceShippingFixedOptionId,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of c shipping fixed option rels.
	*
	* @return the number of c shipping fixed option rels
	*/
	@Override
	public int getCShippingFixedOptionRelsCount() {
		return _cShippingFixedOptionRelLocalService.getCShippingFixedOptionRelsCount();
	}

	@Override
	public int getCShippingFixedOptionRelsCount(
		long commerceShippingFixedOptionId) {
		return _cShippingFixedOptionRelLocalService.getCShippingFixedOptionRelsCount(commerceShippingFixedOptionId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _cShippingFixedOptionRelLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _cShippingFixedOptionRelLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cShippingFixedOptionRelLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the c shipping fixed option rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cShippingFixedOptionRel the c shipping fixed option rel
	* @return the c shipping fixed option rel that was updated
	*/
	@Override
	public com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel updateCShippingFixedOptionRel(
		com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel cShippingFixedOptionRel) {
		return _cShippingFixedOptionRelLocalService.updateCShippingFixedOptionRel(cShippingFixedOptionRel);
	}

	@Override
	public com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel updateCShippingFixedOptionRel(
		long cShippingFixedOptionRelId, long commerceWarehouseId,
		long commerceCountryId, long commerceRegionId, java.lang.String zip,
		double weightFrom, double weightTo, double fixedPrice,
		double rateUnitWeightPrice, double ratePercentage)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cShippingFixedOptionRelLocalService.updateCShippingFixedOptionRel(cShippingFixedOptionRelId,
			commerceWarehouseId, commerceCountryId, commerceRegionId, zip,
			weightFrom, weightTo, fixedPrice, rateUnitWeightPrice,
			ratePercentage);
	}

	@Override
	public CShippingFixedOptionRelLocalService getWrappedService() {
		return _cShippingFixedOptionRelLocalService;
	}

	@Override
	public void setWrappedService(
		CShippingFixedOptionRelLocalService cShippingFixedOptionRelLocalService) {
		_cShippingFixedOptionRelLocalService = cShippingFixedOptionRelLocalService;
	}

	private CShippingFixedOptionRelLocalService _cShippingFixedOptionRelLocalService;
}