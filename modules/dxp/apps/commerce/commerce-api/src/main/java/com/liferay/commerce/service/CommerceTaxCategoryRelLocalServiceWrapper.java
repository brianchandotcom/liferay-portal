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
 * Provides a wrapper for {@link CommerceTaxCategoryRelLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxCategoryRelLocalService
 * @generated
 */
@ProviderType
public class CommerceTaxCategoryRelLocalServiceWrapper
	implements CommerceTaxCategoryRelLocalService,
		ServiceWrapper<CommerceTaxCategoryRelLocalService> {
	public CommerceTaxCategoryRelLocalServiceWrapper(
		CommerceTaxCategoryRelLocalService commerceTaxCategoryRelLocalService) {
		_commerceTaxCategoryRelLocalService = commerceTaxCategoryRelLocalService;
	}

	/**
	* Adds the commerce tax category rel to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceTaxCategoryRel the commerce tax category rel
	* @return the commerce tax category rel that was added
	*/
	@Override
	public com.liferay.commerce.model.CommerceTaxCategoryRel addCommerceTaxCategoryRel(
		com.liferay.commerce.model.CommerceTaxCategoryRel commerceTaxCategoryRel) {
		return _commerceTaxCategoryRelLocalService.addCommerceTaxCategoryRel(commerceTaxCategoryRel);
	}

	@Override
	public com.liferay.commerce.model.CommerceTaxCategoryRel addCommerceTaxCategoryRel(
		long commerceTaxCategoryId, java.lang.String className, long classPK,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTaxCategoryRelLocalService.addCommerceTaxCategoryRel(commerceTaxCategoryId,
			className, classPK, serviceContext);
	}

	/**
	* Creates a new commerce tax category rel with the primary key. Does not add the commerce tax category rel to the database.
	*
	* @param commerceTaxCategoryRelId the primary key for the new commerce tax category rel
	* @return the new commerce tax category rel
	*/
	@Override
	public com.liferay.commerce.model.CommerceTaxCategoryRel createCommerceTaxCategoryRel(
		long commerceTaxCategoryRelId) {
		return _commerceTaxCategoryRelLocalService.createCommerceTaxCategoryRel(commerceTaxCategoryRelId);
	}

	/**
	* Deletes the commerce tax category rel from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceTaxCategoryRel the commerce tax category rel
	* @return the commerce tax category rel that was removed
	*/
	@Override
	public com.liferay.commerce.model.CommerceTaxCategoryRel deleteCommerceTaxCategoryRel(
		com.liferay.commerce.model.CommerceTaxCategoryRel commerceTaxCategoryRel) {
		return _commerceTaxCategoryRelLocalService.deleteCommerceTaxCategoryRel(commerceTaxCategoryRel);
	}

	/**
	* Deletes the commerce tax category rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceTaxCategoryRelId the primary key of the commerce tax category rel
	* @return the commerce tax category rel that was removed
	* @throws PortalException if a commerce tax category rel with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceTaxCategoryRel deleteCommerceTaxCategoryRel(
		long commerceTaxCategoryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTaxCategoryRelLocalService.deleteCommerceTaxCategoryRel(commerceTaxCategoryRelId);
	}

	@Override
	public void deleteCommerceTaxCategoryRels(long commerceTaxCategoryId) {
		_commerceTaxCategoryRelLocalService.deleteCommerceTaxCategoryRels(commerceTaxCategoryId);
	}

	@Override
	public void deleteCommerceTaxCategoryRels(java.lang.String className,
		long classPK) {
		_commerceTaxCategoryRelLocalService.deleteCommerceTaxCategoryRels(className,
			classPK);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTaxCategoryRelLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceTaxCategoryRelLocalService.dynamicQuery();
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
		return _commerceTaxCategoryRelLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceTaxCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceTaxCategoryRelLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceTaxCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceTaxCategoryRelLocalService.dynamicQuery(dynamicQuery,
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
		return _commerceTaxCategoryRelLocalService.dynamicQueryCount(dynamicQuery);
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
		return _commerceTaxCategoryRelLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.model.CommerceTaxCategoryRel fetchCommerceTaxCategoryRel(
		long commerceTaxCategoryRelId) {
		return _commerceTaxCategoryRelLocalService.fetchCommerceTaxCategoryRel(commerceTaxCategoryRelId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _commerceTaxCategoryRelLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the commerce tax category rel with the primary key.
	*
	* @param commerceTaxCategoryRelId the primary key of the commerce tax category rel
	* @return the commerce tax category rel
	* @throws PortalException if a commerce tax category rel with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceTaxCategoryRel getCommerceTaxCategoryRel(
		long commerceTaxCategoryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTaxCategoryRelLocalService.getCommerceTaxCategoryRel(commerceTaxCategoryRelId);
	}

	/**
	* Returns a range of all the commerce tax category rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceTaxCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce tax category rels
	* @param end the upper bound of the range of commerce tax category rels (not inclusive)
	* @return the range of commerce tax category rels
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CommerceTaxCategoryRel> getCommerceTaxCategoryRels(
		int start, int end) {
		return _commerceTaxCategoryRelLocalService.getCommerceTaxCategoryRels(start,
			end);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceTaxCategoryRel> getCommerceTaxCategoryRels(
		java.lang.String className, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceTaxCategoryRel> orderByComparator) {
		return _commerceTaxCategoryRelLocalService.getCommerceTaxCategoryRels(className,
			classPK, start, end, orderByComparator);
	}

	/**
	* Returns the number of commerce tax category rels.
	*
	* @return the number of commerce tax category rels
	*/
	@Override
	public int getCommerceTaxCategoryRelsCount() {
		return _commerceTaxCategoryRelLocalService.getCommerceTaxCategoryRelsCount();
	}

	@Override
	public int getCommerceTaxCategoryRelsCount(java.lang.String className,
		long classPK) {
		return _commerceTaxCategoryRelLocalService.getCommerceTaxCategoryRelsCount(className,
			classPK);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _commerceTaxCategoryRelLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceTaxCategoryRelLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTaxCategoryRelLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the commerce tax category rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceTaxCategoryRel the commerce tax category rel
	* @return the commerce tax category rel that was updated
	*/
	@Override
	public com.liferay.commerce.model.CommerceTaxCategoryRel updateCommerceTaxCategoryRel(
		com.liferay.commerce.model.CommerceTaxCategoryRel commerceTaxCategoryRel) {
		return _commerceTaxCategoryRelLocalService.updateCommerceTaxCategoryRel(commerceTaxCategoryRel);
	}

	@Override
	public CommerceTaxCategoryRelLocalService getWrappedService() {
		return _commerceTaxCategoryRelLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceTaxCategoryRelLocalService commerceTaxCategoryRelLocalService) {
		_commerceTaxCategoryRelLocalService = commerceTaxCategoryRelLocalService;
	}

	private CommerceTaxCategoryRelLocalService _commerceTaxCategoryRelLocalService;
}