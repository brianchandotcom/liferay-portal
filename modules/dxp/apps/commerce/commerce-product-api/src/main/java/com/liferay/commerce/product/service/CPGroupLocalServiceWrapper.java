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
 * Provides a wrapper for {@link CPGroupLocalService}.
 *
 * @author Marco Leo
 * @see CPGroupLocalService
 * @generated
 */
@ProviderType
public class CPGroupLocalServiceWrapper implements CPGroupLocalService,
	ServiceWrapper<CPGroupLocalService> {
	public CPGroupLocalServiceWrapper(CPGroupLocalService cpGroupLocalService) {
		_cpGroupLocalService = cpGroupLocalService;
	}

	/**
	* Adds the cp group to the database. Also notifies the appropriate model listeners.
	*
	* @param cpGroup the cp group
	* @return the cp group that was added
	*/
	@Override
	public com.liferay.commerce.product.model.CPGroup addCPGroup(
		com.liferay.commerce.product.model.CPGroup cpGroup) {
		return _cpGroupLocalService.addCPGroup(cpGroup);
	}

	@Override
	public com.liferay.commerce.product.model.CPGroup addCPGroup(
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpGroupLocalService.addCPGroup(serviceContext);
	}

	/**
	* Creates a new cp group with the primary key. Does not add the cp group to the database.
	*
	* @param CPGroupId the primary key for the new cp group
	* @return the new cp group
	*/
	@Override
	public com.liferay.commerce.product.model.CPGroup createCPGroup(
		long CPGroupId) {
		return _cpGroupLocalService.createCPGroup(CPGroupId);
	}

	/**
	* Deletes the cp group from the database. Also notifies the appropriate model listeners.
	*
	* @param cpGroup the cp group
	* @return the cp group that was removed
	*/
	@Override
	public com.liferay.commerce.product.model.CPGroup deleteCPGroup(
		com.liferay.commerce.product.model.CPGroup cpGroup) {
		return _cpGroupLocalService.deleteCPGroup(cpGroup);
	}

	/**
	* Deletes the cp group with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPGroupId the primary key of the cp group
	* @return the cp group that was removed
	* @throws PortalException if a cp group with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPGroup deleteCPGroup(
		long CPGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpGroupLocalService.deleteCPGroup(CPGroupId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpGroupLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _cpGroupLocalService.dynamicQuery();
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
		return _cpGroupLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cpGroupLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cpGroupLocalService.dynamicQuery(dynamicQuery, start, end,
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
		return _cpGroupLocalService.dynamicQueryCount(dynamicQuery);
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
		return _cpGroupLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.liferay.commerce.product.model.CPGroup fetchCPGroup(
		long CPGroupId) {
		return _cpGroupLocalService.fetchCPGroup(CPGroupId);
	}

	@Override
	public com.liferay.commerce.product.model.CPGroup fetchCPGroupByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpGroupLocalService.fetchCPGroupByGroupId(groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _cpGroupLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the cp group with the primary key.
	*
	* @param CPGroupId the primary key of the cp group
	* @return the cp group
	* @throws PortalException if a cp group with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPGroup getCPGroup(long CPGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpGroupLocalService.getCPGroup(CPGroupId);
	}

	/**
	* Returns a range of all the cp groups.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp groups
	* @param end the upper bound of the range of cp groups (not inclusive)
	* @return the range of cp groups
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPGroup> getCPGroups(
		int start, int end) {
		return _cpGroupLocalService.getCPGroups(start, end);
	}

	/**
	* Returns the number of cp groups.
	*
	* @return the number of cp groups
	*/
	@Override
	public int getCPGroupsCount() {
		return _cpGroupLocalService.getCPGroupsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _cpGroupLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _cpGroupLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpGroupLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the cp group in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpGroup the cp group
	* @return the cp group that was updated
	*/
	@Override
	public com.liferay.commerce.product.model.CPGroup updateCPGroup(
		com.liferay.commerce.product.model.CPGroup cpGroup) {
		return _cpGroupLocalService.updateCPGroup(cpGroup);
	}

	@Override
	public CPGroupLocalService getWrappedService() {
		return _cpGroupLocalService;
	}

	@Override
	public void setWrappedService(CPGroupLocalService cpGroupLocalService) {
		_cpGroupLocalService = cpGroupLocalService;
	}

	private CPGroupLocalService _cpGroupLocalService;
}