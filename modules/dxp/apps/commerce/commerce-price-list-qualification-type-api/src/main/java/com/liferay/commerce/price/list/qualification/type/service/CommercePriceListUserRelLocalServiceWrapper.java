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

package com.liferay.commerce.price.list.qualification.type.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommercePriceListUserRelLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListUserRelLocalService
 * @generated
 */
@ProviderType
public class CommercePriceListUserRelLocalServiceWrapper
	implements CommercePriceListUserRelLocalService,
		ServiceWrapper<CommercePriceListUserRelLocalService> {
	public CommercePriceListUserRelLocalServiceWrapper(
		CommercePriceListUserRelLocalService commercePriceListUserRelLocalService) {
		_commercePriceListUserRelLocalService = commercePriceListUserRelLocalService;
	}

	/**
	* Adds the commerce price list user rel to the database. Also notifies the appropriate model listeners.
	*
	* @param commercePriceListUserRel the commerce price list user rel
	* @return the commerce price list user rel that was added
	*/
	@Override
	public com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel addCommercePriceListUserRel(
		com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel commercePriceListUserRel) {
		return _commercePriceListUserRelLocalService.addCommercePriceListUserRel(commercePriceListUserRel);
	}

	@Override
	public com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel addCommercePriceListUserRel(
		long commercePriceListQualificationTypeRelId,
		java.lang.String className, long classPK,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListUserRelLocalService.addCommercePriceListUserRel(commercePriceListQualificationTypeRelId,
			className, classPK, serviceContext);
	}

	/**
	* Creates a new commerce price list user rel with the primary key. Does not add the commerce price list user rel to the database.
	*
	* @param commercePriceListUserRelId the primary key for the new commerce price list user rel
	* @return the new commerce price list user rel
	*/
	@Override
	public com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel createCommercePriceListUserRel(
		long commercePriceListUserRelId) {
		return _commercePriceListUserRelLocalService.createCommercePriceListUserRel(commercePriceListUserRelId);
	}

	/**
	* Deletes the commerce price list user rel from the database. Also notifies the appropriate model listeners.
	*
	* @param commercePriceListUserRel the commerce price list user rel
	* @return the commerce price list user rel that was removed
	*/
	@Override
	public com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel deleteCommercePriceListUserRel(
		com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel commercePriceListUserRel) {
		return _commercePriceListUserRelLocalService.deleteCommercePriceListUserRel(commercePriceListUserRel);
	}

	/**
	* Deletes the commerce price list user rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commercePriceListUserRelId the primary key of the commerce price list user rel
	* @return the commerce price list user rel that was removed
	* @throws PortalException if a commerce price list user rel with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel deleteCommercePriceListUserRel(
		long commercePriceListUserRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListUserRelLocalService.deleteCommercePriceListUserRel(commercePriceListUserRelId);
	}

	@Override
	public void deleteCommercePriceListUserRels(
		long commercePriceListQualificationTypeId) {
		_commercePriceListUserRelLocalService.deleteCommercePriceListUserRels(commercePriceListQualificationTypeId);
	}

	@Override
	public void deleteCommercePriceListUserRels(
		long commercePriceListQualificationTypeRelId, java.lang.String className) {
		_commercePriceListUserRelLocalService.deleteCommercePriceListUserRels(commercePriceListQualificationTypeRelId,
			className);
	}

	@Override
	public void deleteCommercePriceListUserRels(
		long commercePriceListQualificationTypeRelId,
		java.lang.String className, long classPK) {
		_commercePriceListUserRelLocalService.deleteCommercePriceListUserRels(commercePriceListQualificationTypeRelId,
			className, classPK);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListUserRelLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commercePriceListUserRelLocalService.dynamicQuery();
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
		return _commercePriceListUserRelLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.price.list.qualification.type.model.impl.CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commercePriceListUserRelLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.price.list.qualification.type.model.impl.CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commercePriceListUserRelLocalService.dynamicQuery(dynamicQuery,
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
		return _commercePriceListUserRelLocalService.dynamicQueryCount(dynamicQuery);
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
		return _commercePriceListUserRelLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel fetchCommercePriceListUserRel(
		long commercePriceListUserRelId) {
		return _commercePriceListUserRelLocalService.fetchCommercePriceListUserRel(commercePriceListUserRelId);
	}

	/**
	* Returns the commerce price list user rel matching the UUID and group.
	*
	* @param uuid the commerce price list user rel's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce price list user rel, or <code>null</code> if a matching commerce price list user rel could not be found
	*/
	@Override
	public com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel fetchCommercePriceListUserRelByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _commercePriceListUserRelLocalService.fetchCommercePriceListUserRelByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _commercePriceListUserRelLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the commerce price list user rel with the primary key.
	*
	* @param commercePriceListUserRelId the primary key of the commerce price list user rel
	* @return the commerce price list user rel
	* @throws PortalException if a commerce price list user rel with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel getCommercePriceListUserRel(
		long commercePriceListUserRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListUserRelLocalService.getCommercePriceListUserRel(commercePriceListUserRelId);
	}

	/**
	* Returns the commerce price list user rel matching the UUID and group.
	*
	* @param uuid the commerce price list user rel's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce price list user rel
	* @throws PortalException if a matching commerce price list user rel could not be found
	*/
	@Override
	public com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel getCommercePriceListUserRelByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListUserRelLocalService.getCommercePriceListUserRelByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns a range of all the commerce price list user rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.price.list.qualification.type.model.impl.CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce price list user rels
	* @param end the upper bound of the range of commerce price list user rels (not inclusive)
	* @return the range of commerce price list user rels
	*/
	@Override
	public java.util.List<com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel> getCommercePriceListUserRels(
		int start, int end) {
		return _commercePriceListUserRelLocalService.getCommercePriceListUserRels(start,
			end);
	}

	@Override
	public java.util.List<com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel> getCommercePriceListUserRels(
		long commercePriceListQualificationTypeRelId, java.lang.String className) {
		return _commercePriceListUserRelLocalService.getCommercePriceListUserRels(commercePriceListQualificationTypeRelId,
			className);
	}

	@Override
	public java.util.List<com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel> getCommercePriceListUserRels(
		long commercePriceListQualificationTypeRelId,
		java.lang.String className, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel> orderByComparator) {
		return _commercePriceListUserRelLocalService.getCommercePriceListUserRels(commercePriceListQualificationTypeRelId,
			className, start, end, orderByComparator);
	}

	/**
	* Returns all the commerce price list user rels matching the UUID and company.
	*
	* @param uuid the UUID of the commerce price list user rels
	* @param companyId the primary key of the company
	* @return the matching commerce price list user rels, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel> getCommercePriceListUserRelsByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _commercePriceListUserRelLocalService.getCommercePriceListUserRelsByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of commerce price list user rels matching the UUID and company.
	*
	* @param uuid the UUID of the commerce price list user rels
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of commerce price list user rels
	* @param end the upper bound of the range of commerce price list user rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching commerce price list user rels, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel> getCommercePriceListUserRelsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel> orderByComparator) {
		return _commercePriceListUserRelLocalService.getCommercePriceListUserRelsByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of commerce price list user rels.
	*
	* @return the number of commerce price list user rels
	*/
	@Override
	public int getCommercePriceListUserRelsCount() {
		return _commercePriceListUserRelLocalService.getCommercePriceListUserRelsCount();
	}

	@Override
	public int getCommercePriceListUserRelsCount(
		long commercePriceListQualificationTypeRelId, java.lang.String className) {
		return _commercePriceListUserRelLocalService.getCommercePriceListUserRelsCount(commercePriceListQualificationTypeRelId,
			className);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _commercePriceListUserRelLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _commercePriceListUserRelLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commercePriceListUserRelLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListUserRelLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the commerce price list user rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commercePriceListUserRel the commerce price list user rel
	* @return the commerce price list user rel that was updated
	*/
	@Override
	public com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel updateCommercePriceListUserRel(
		com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel commercePriceListUserRel) {
		return _commercePriceListUserRelLocalService.updateCommercePriceListUserRel(commercePriceListUserRel);
	}

	@Override
	public com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel updateCommercePriceListUserRel(
		long commercePriceListUserRelId,
		long commercePriceListQualificationTypeRelId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListUserRelLocalService.updateCommercePriceListUserRel(commercePriceListUserRelId,
			commercePriceListQualificationTypeRelId, serviceContext);
	}

	@Override
	public CommercePriceListUserRelLocalService getWrappedService() {
		return _commercePriceListUserRelLocalService;
	}

	@Override
	public void setWrappedService(
		CommercePriceListUserRelLocalService commercePriceListUserRelLocalService) {
		_commercePriceListUserRelLocalService = commercePriceListUserRelLocalService;
	}

	private CommercePriceListUserRelLocalService _commercePriceListUserRelLocalService;
}