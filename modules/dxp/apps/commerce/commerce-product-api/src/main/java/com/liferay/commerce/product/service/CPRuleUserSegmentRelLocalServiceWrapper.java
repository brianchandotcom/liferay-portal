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
 * Provides a wrapper for {@link CPRuleUserSegmentRelLocalService}.
 *
 * @author Marco Leo
 * @see CPRuleUserSegmentRelLocalService
 * @generated
 */
@ProviderType
public class CPRuleUserSegmentRelLocalServiceWrapper
	implements CPRuleUserSegmentRelLocalService,
		ServiceWrapper<CPRuleUserSegmentRelLocalService> {
	public CPRuleUserSegmentRelLocalServiceWrapper(
		CPRuleUserSegmentRelLocalService cpRuleUserSegmentRelLocalService) {
		_cpRuleUserSegmentRelLocalService = cpRuleUserSegmentRelLocalService;
	}

	/**
	* Adds the cp rule user segment rel to the database. Also notifies the appropriate model listeners.
	*
	* @param cpRuleUserSegmentRel the cp rule user segment rel
	* @return the cp rule user segment rel that was added
	*/
	@Override
	public com.liferay.commerce.product.model.CPRuleUserSegmentRel addCPRuleUserSegmentRel(
		com.liferay.commerce.product.model.CPRuleUserSegmentRel cpRuleUserSegmentRel) {
		return _cpRuleUserSegmentRelLocalService.addCPRuleUserSegmentRel(cpRuleUserSegmentRel);
	}

	@Override
	public com.liferay.commerce.product.model.CPRuleUserSegmentRel addCPRuleUserSegmentRel(
		long cpRuleId, long commerceUserSegmentEntryId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpRuleUserSegmentRelLocalService.addCPRuleUserSegmentRel(cpRuleId,
			commerceUserSegmentEntryId, serviceContext);
	}

	/**
	* Creates a new cp rule user segment rel with the primary key. Does not add the cp rule user segment rel to the database.
	*
	* @param CPRuleUserSegmentRelId the primary key for the new cp rule user segment rel
	* @return the new cp rule user segment rel
	*/
	@Override
	public com.liferay.commerce.product.model.CPRuleUserSegmentRel createCPRuleUserSegmentRel(
		long CPRuleUserSegmentRelId) {
		return _cpRuleUserSegmentRelLocalService.createCPRuleUserSegmentRel(CPRuleUserSegmentRelId);
	}

	/**
	* Deletes the cp rule user segment rel from the database. Also notifies the appropriate model listeners.
	*
	* @param cpRuleUserSegmentRel the cp rule user segment rel
	* @return the cp rule user segment rel that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.commerce.product.model.CPRuleUserSegmentRel deleteCPRuleUserSegmentRel(
		com.liferay.commerce.product.model.CPRuleUserSegmentRel cpRuleUserSegmentRel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpRuleUserSegmentRelLocalService.deleteCPRuleUserSegmentRel(cpRuleUserSegmentRel);
	}

	/**
	* Deletes the cp rule user segment rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPRuleUserSegmentRelId the primary key of the cp rule user segment rel
	* @return the cp rule user segment rel that was removed
	* @throws PortalException if a cp rule user segment rel with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPRuleUserSegmentRel deleteCPRuleUserSegmentRel(
		long CPRuleUserSegmentRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpRuleUserSegmentRelLocalService.deleteCPRuleUserSegmentRel(CPRuleUserSegmentRelId);
	}

	@Override
	public void deleteCPRuleUserSegmentRelsByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpRuleUserSegmentRelLocalService.deleteCPRuleUserSegmentRelsByCommerceUserSegmentEntryId(commerceUserSegmentEntryId);
	}

	@Override
	public void deleteCPRuleUserSegmentRelsByCPRuleId(long cpRuleId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpRuleUserSegmentRelLocalService.deleteCPRuleUserSegmentRelsByCPRuleId(cpRuleId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpRuleUserSegmentRelLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _cpRuleUserSegmentRelLocalService.dynamicQuery();
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
		return _cpRuleUserSegmentRelLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPRuleUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cpRuleUserSegmentRelLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPRuleUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cpRuleUserSegmentRelLocalService.dynamicQuery(dynamicQuery,
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
		return _cpRuleUserSegmentRelLocalService.dynamicQueryCount(dynamicQuery);
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
		return _cpRuleUserSegmentRelLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.product.model.CPRuleUserSegmentRel fetchCPRuleUserSegmentRel(
		long CPRuleUserSegmentRelId) {
		return _cpRuleUserSegmentRelLocalService.fetchCPRuleUserSegmentRel(CPRuleUserSegmentRelId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _cpRuleUserSegmentRelLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the cp rule user segment rel with the primary key.
	*
	* @param CPRuleUserSegmentRelId the primary key of the cp rule user segment rel
	* @return the cp rule user segment rel
	* @throws PortalException if a cp rule user segment rel with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPRuleUserSegmentRel getCPRuleUserSegmentRel(
		long CPRuleUserSegmentRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpRuleUserSegmentRelLocalService.getCPRuleUserSegmentRel(CPRuleUserSegmentRelId);
	}

	/**
	* Returns a range of all the cp rule user segment rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPRuleUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp rule user segment rels
	* @param end the upper bound of the range of cp rule user segment rels (not inclusive)
	* @return the range of cp rule user segment rels
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPRuleUserSegmentRel> getCPRuleUserSegmentRels(
		int start, int end) {
		return _cpRuleUserSegmentRelLocalService.getCPRuleUserSegmentRels(start,
			end);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPRuleUserSegmentRel> getCPRuleUserSegmentRels(
		long cpRuleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPRuleUserSegmentRel> orderByComparator) {
		return _cpRuleUserSegmentRelLocalService.getCPRuleUserSegmentRels(cpRuleId,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of cp rule user segment rels.
	*
	* @return the number of cp rule user segment rels
	*/
	@Override
	public int getCPRuleUserSegmentRelsCount() {
		return _cpRuleUserSegmentRelLocalService.getCPRuleUserSegmentRelsCount();
	}

	@Override
	public int getCPRuleUserSegmentRelsCount(long cpRuleId) {
		return _cpRuleUserSegmentRelLocalService.getCPRuleUserSegmentRelsCount(cpRuleId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _cpRuleUserSegmentRelLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _cpRuleUserSegmentRelLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpRuleUserSegmentRelLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the cp rule user segment rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpRuleUserSegmentRel the cp rule user segment rel
	* @return the cp rule user segment rel that was updated
	*/
	@Override
	public com.liferay.commerce.product.model.CPRuleUserSegmentRel updateCPRuleUserSegmentRel(
		com.liferay.commerce.product.model.CPRuleUserSegmentRel cpRuleUserSegmentRel) {
		return _cpRuleUserSegmentRelLocalService.updateCPRuleUserSegmentRel(cpRuleUserSegmentRel);
	}

	@Override
	public CPRuleUserSegmentRelLocalService getWrappedService() {
		return _cpRuleUserSegmentRelLocalService;
	}

	@Override
	public void setWrappedService(
		CPRuleUserSegmentRelLocalService cpRuleUserSegmentRelLocalService) {
		_cpRuleUserSegmentRelLocalService = cpRuleUserSegmentRelLocalService;
	}

	private CPRuleUserSegmentRelLocalService _cpRuleUserSegmentRelLocalService;
}