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

package com.liferay.commerce.product.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CPRuleAssetCategoryRelLocalService}.
 *
 * @author Marco Leo
 * @see CPRuleAssetCategoryRelLocalService
 * @generated
 */
@ProviderType
public class CPRuleAssetCategoryRelLocalServiceWrapper
	implements CPRuleAssetCategoryRelLocalService,
		ServiceWrapper<CPRuleAssetCategoryRelLocalService> {
	public CPRuleAssetCategoryRelLocalServiceWrapper(
		CPRuleAssetCategoryRelLocalService cpRuleAssetCategoryRelLocalService) {
		_cpRuleAssetCategoryRelLocalService = cpRuleAssetCategoryRelLocalService;
	}

	/**
	* Adds the cp rule asset category rel to the database. Also notifies the appropriate model listeners.
	*
	* @param cpRuleAssetCategoryRel the cp rule asset category rel
	* @return the cp rule asset category rel that was added
	*/
	@Override
	public com.liferay.commerce.product.model.CPRuleAssetCategoryRel addCPRuleAssetCategoryRel(
		com.liferay.commerce.product.model.CPRuleAssetCategoryRel cpRuleAssetCategoryRel) {
		return _cpRuleAssetCategoryRelLocalService.addCPRuleAssetCategoryRel(cpRuleAssetCategoryRel);
	}

	@Override
	public com.liferay.commerce.product.model.CPRuleAssetCategoryRel addCPRuleAssetCategoryRel(
		long cpRuleId, long assetCategoryId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpRuleAssetCategoryRelLocalService.addCPRuleAssetCategoryRel(cpRuleId,
			assetCategoryId, serviceContext);
	}

	/**
	* Creates a new cp rule asset category rel with the primary key. Does not add the cp rule asset category rel to the database.
	*
	* @param CPRuleAssetCategoryRelId the primary key for the new cp rule asset category rel
	* @return the new cp rule asset category rel
	*/
	@Override
	public com.liferay.commerce.product.model.CPRuleAssetCategoryRel createCPRuleAssetCategoryRel(
		long CPRuleAssetCategoryRelId) {
		return _cpRuleAssetCategoryRelLocalService.createCPRuleAssetCategoryRel(CPRuleAssetCategoryRelId);
	}

	/**
	* Deletes the cp rule asset category rel from the database. Also notifies the appropriate model listeners.
	*
	* @param cpRuleAssetCategoryRel the cp rule asset category rel
	* @return the cp rule asset category rel that was removed
	*/
	@Override
	public com.liferay.commerce.product.model.CPRuleAssetCategoryRel deleteCPRuleAssetCategoryRel(
		com.liferay.commerce.product.model.CPRuleAssetCategoryRel cpRuleAssetCategoryRel) {
		return _cpRuleAssetCategoryRelLocalService.deleteCPRuleAssetCategoryRel(cpRuleAssetCategoryRel);
	}

	/**
	* Deletes the cp rule asset category rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPRuleAssetCategoryRelId the primary key of the cp rule asset category rel
	* @return the cp rule asset category rel that was removed
	* @throws PortalException if a cp rule asset category rel with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPRuleAssetCategoryRel deleteCPRuleAssetCategoryRel(
		long CPRuleAssetCategoryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpRuleAssetCategoryRelLocalService.deleteCPRuleAssetCategoryRel(CPRuleAssetCategoryRelId);
	}

	@Override
	public void deleteCPRuleAssetCategoryRelsByAssetCategoryId(
		long assetCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpRuleAssetCategoryRelLocalService.deleteCPRuleAssetCategoryRelsByAssetCategoryId(assetCategoryId);
	}

	@Override
	public void deleteCPRuleAssetCategoryRelsByCPRuleId(long cpRuleId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpRuleAssetCategoryRelLocalService.deleteCPRuleAssetCategoryRelsByCPRuleId(cpRuleId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpRuleAssetCategoryRelLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _cpRuleAssetCategoryRelLocalService.dynamicQuery();
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
		return _cpRuleAssetCategoryRelLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPRuleAssetCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cpRuleAssetCategoryRelLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPRuleAssetCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cpRuleAssetCategoryRelLocalService.dynamicQuery(dynamicQuery,
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
		return _cpRuleAssetCategoryRelLocalService.dynamicQueryCount(dynamicQuery);
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
		return _cpRuleAssetCategoryRelLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.product.model.CPRuleAssetCategoryRel fetchCPRuleAssetCategoryRel(
		long CPRuleAssetCategoryRelId) {
		return _cpRuleAssetCategoryRelLocalService.fetchCPRuleAssetCategoryRel(CPRuleAssetCategoryRelId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _cpRuleAssetCategoryRelLocalService.getActionableDynamicQuery();
	}

	@Override
	public long[] getAssetCategoryIds(long cpRuleId) {
		return _cpRuleAssetCategoryRelLocalService.getAssetCategoryIds(cpRuleId);
	}

	/**
	* Returns the cp rule asset category rel with the primary key.
	*
	* @param CPRuleAssetCategoryRelId the primary key of the cp rule asset category rel
	* @return the cp rule asset category rel
	* @throws PortalException if a cp rule asset category rel with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPRuleAssetCategoryRel getCPRuleAssetCategoryRel(
		long CPRuleAssetCategoryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpRuleAssetCategoryRelLocalService.getCPRuleAssetCategoryRel(CPRuleAssetCategoryRelId);
	}

	/**
	* Returns a range of all the cp rule asset category rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPRuleAssetCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp rule asset category rels
	* @param end the upper bound of the range of cp rule asset category rels (not inclusive)
	* @return the range of cp rule asset category rels
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPRuleAssetCategoryRel> getCPRuleAssetCategoryRels(
		int start, int end) {
		return _cpRuleAssetCategoryRelLocalService.getCPRuleAssetCategoryRels(start,
			end);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPRuleAssetCategoryRel> getCPRuleAssetCategoryRels(
		long cpRuleId) {
		return _cpRuleAssetCategoryRelLocalService.getCPRuleAssetCategoryRels(cpRuleId);
	}

	/**
	* Returns the number of cp rule asset category rels.
	*
	* @return the number of cp rule asset category rels
	*/
	@Override
	public int getCPRuleAssetCategoryRelsCount() {
		return _cpRuleAssetCategoryRelLocalService.getCPRuleAssetCategoryRelsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _cpRuleAssetCategoryRelLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpRuleAssetCategoryRelLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpRuleAssetCategoryRelLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the cp rule asset category rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpRuleAssetCategoryRel the cp rule asset category rel
	* @return the cp rule asset category rel that was updated
	*/
	@Override
	public com.liferay.commerce.product.model.CPRuleAssetCategoryRel updateCPRuleAssetCategoryRel(
		com.liferay.commerce.product.model.CPRuleAssetCategoryRel cpRuleAssetCategoryRel) {
		return _cpRuleAssetCategoryRelLocalService.updateCPRuleAssetCategoryRel(cpRuleAssetCategoryRel);
	}

	@Override
	public CPRuleAssetCategoryRelLocalService getWrappedService() {
		return _cpRuleAssetCategoryRelLocalService;
	}

	@Override
	public void setWrappedService(
		CPRuleAssetCategoryRelLocalService cpRuleAssetCategoryRelLocalService) {
		_cpRuleAssetCategoryRelLocalService = cpRuleAssetCategoryRelLocalService;
	}

	private CPRuleAssetCategoryRelLocalService _cpRuleAssetCategoryRelLocalService;
}