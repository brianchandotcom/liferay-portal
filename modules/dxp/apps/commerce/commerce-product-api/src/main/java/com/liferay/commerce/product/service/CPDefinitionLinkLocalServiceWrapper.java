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
 * Provides a wrapper for {@link CPDefinitionLinkLocalService}.
 *
 * @author Marco Leo
 * @see CPDefinitionLinkLocalService
 * @generated
 */
@ProviderType
public class CPDefinitionLinkLocalServiceWrapper
	implements CPDefinitionLinkLocalService,
		ServiceWrapper<CPDefinitionLinkLocalService> {
	public CPDefinitionLinkLocalServiceWrapper(
		CPDefinitionLinkLocalService cpDefinitionLinkLocalService) {
		_cpDefinitionLinkLocalService = cpDefinitionLinkLocalService;
	}

	/**
	* Adds the cp definition link to the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionLink the cp definition link
	* @return the cp definition link that was added
	*/
	@Override
	public com.liferay.commerce.product.model.CPDefinitionLink addCPDefinitionLink(
		com.liferay.commerce.product.model.CPDefinitionLink cpDefinitionLink) {
		return _cpDefinitionLinkLocalService.addCPDefinitionLink(cpDefinitionLink);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinitionLink addCPDefinitionLink(
		long cpDefinitionId1, long cpDefinitionId2, double priority,
		java.lang.String type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionLinkLocalService.addCPDefinitionLink(cpDefinitionId1,
			cpDefinitionId2, priority, type, serviceContext);
	}

	/**
	* Creates a new cp definition link with the primary key. Does not add the cp definition link to the database.
	*
	* @param CPDefinitionLinkId the primary key for the new cp definition link
	* @return the new cp definition link
	*/
	@Override
	public com.liferay.commerce.product.model.CPDefinitionLink createCPDefinitionLink(
		long CPDefinitionLinkId) {
		return _cpDefinitionLinkLocalService.createCPDefinitionLink(CPDefinitionLinkId);
	}

	/**
	* Deletes the cp definition link from the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionLink the cp definition link
	* @return the cp definition link that was removed
	*/
	@Override
	public com.liferay.commerce.product.model.CPDefinitionLink deleteCPDefinitionLink(
		com.liferay.commerce.product.model.CPDefinitionLink cpDefinitionLink) {
		return _cpDefinitionLinkLocalService.deleteCPDefinitionLink(cpDefinitionLink);
	}

	/**
	* Deletes the cp definition link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDefinitionLinkId the primary key of the cp definition link
	* @return the cp definition link that was removed
	* @throws PortalException if a cp definition link with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPDefinitionLink deleteCPDefinitionLink(
		long CPDefinitionLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionLinkLocalService.deleteCPDefinitionLink(CPDefinitionLinkId);
	}

	@Override
	public void deleteCPDefinitionLinks(long cpDefinitionId) {
		_cpDefinitionLinkLocalService.deleteCPDefinitionLinks(cpDefinitionId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionLinkLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _cpDefinitionLinkLocalService.dynamicQuery();
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
		return _cpDefinitionLinkLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cpDefinitionLinkLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cpDefinitionLinkLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
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
		return _cpDefinitionLinkLocalService.dynamicQueryCount(dynamicQuery);
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
		return _cpDefinitionLinkLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinitionLink fetchCPDefinitionLink(
		long CPDefinitionLinkId) {
		return _cpDefinitionLinkLocalService.fetchCPDefinitionLink(CPDefinitionLinkId);
	}

	/**
	* Returns the cp definition link matching the UUID and group.
	*
	* @param uuid the cp definition link's UUID
	* @param groupId the primary key of the group
	* @return the matching cp definition link, or <code>null</code> if a matching cp definition link could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPDefinitionLink fetchCPDefinitionLinkByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _cpDefinitionLinkLocalService.fetchCPDefinitionLinkByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _cpDefinitionLinkLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the cp definition link with the primary key.
	*
	* @param CPDefinitionLinkId the primary key of the cp definition link
	* @return the cp definition link
	* @throws PortalException if a cp definition link with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPDefinitionLink getCPDefinitionLink(
		long CPDefinitionLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionLinkLocalService.getCPDefinitionLink(CPDefinitionLinkId);
	}

	/**
	* Returns the cp definition link matching the UUID and group.
	*
	* @param uuid the cp definition link's UUID
	* @param groupId the primary key of the group
	* @return the matching cp definition link
	* @throws PortalException if a matching cp definition link could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPDefinitionLink getCPDefinitionLinkByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionLinkLocalService.getCPDefinitionLinkByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns a range of all the cp definition links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @return the range of cp definition links
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinitionLink> getCPDefinitionLinks(
		int start, int end) {
		return _cpDefinitionLinkLocalService.getCPDefinitionLinks(start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinitionLink> getCPDefinitionLinks(
		long cpDefinitionId1, java.lang.String type)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionLinkLocalService.getCPDefinitionLinks(cpDefinitionId1,
			type);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinitionLink> getCPDefinitionLinks(
		long cpDefinitionId1, java.lang.String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPDefinitionLink> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionLinkLocalService.getCPDefinitionLinks(cpDefinitionId1,
			type, start, end, orderByComparator);
	}

	/**
	* Returns all the cp definition links matching the UUID and company.
	*
	* @param uuid the UUID of the cp definition links
	* @param companyId the primary key of the company
	* @return the matching cp definition links, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinitionLink> getCPDefinitionLinksByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _cpDefinitionLinkLocalService.getCPDefinitionLinksByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of cp definition links matching the UUID and company.
	*
	* @param uuid the UUID of the cp definition links
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of cp definition links
	* @param end the upper bound of the range of cp definition links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching cp definition links, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinitionLink> getCPDefinitionLinksByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPDefinitionLink> orderByComparator) {
		return _cpDefinitionLinkLocalService.getCPDefinitionLinksByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of cp definition links.
	*
	* @return the number of cp definition links
	*/
	@Override
	public int getCPDefinitionLinksCount() {
		return _cpDefinitionLinkLocalService.getCPDefinitionLinksCount();
	}

	@Override
	public int getCPDefinitionLinksCount(long cpDefinitionId1,
		java.lang.String type)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionLinkLocalService.getCPDefinitionLinksCount(cpDefinitionId1,
			type);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _cpDefinitionLinkLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _cpDefinitionLinkLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _cpDefinitionLinkLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionLinkLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinitionLink> getReverseCPDefinitionLinks(
		long cpDefinitionId, java.lang.String type) {
		return _cpDefinitionLinkLocalService.getReverseCPDefinitionLinks(cpDefinitionId,
			type);
	}

	/**
	* Updates the cp definition link in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionLink the cp definition link
	* @return the cp definition link that was updated
	*/
	@Override
	public com.liferay.commerce.product.model.CPDefinitionLink updateCPDefinitionLink(
		com.liferay.commerce.product.model.CPDefinitionLink cpDefinitionLink) {
		return _cpDefinitionLinkLocalService.updateCPDefinitionLink(cpDefinitionLink);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinitionLink updateCPDefinitionLink(
		long cpDefinitionLinkId, double priority)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionLinkLocalService.updateCPDefinitionLink(cpDefinitionLinkId,
			priority);
	}

	@Override
	public void updateCPDefinitionLinks(long cpDefinitionId1,
		long[] cpDefinitionIds2, java.lang.String type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpDefinitionLinkLocalService.updateCPDefinitionLinks(cpDefinitionId1,
			cpDefinitionIds2, type, serviceContext);
	}

	@Override
	public CPDefinitionLinkLocalService getWrappedService() {
		return _cpDefinitionLinkLocalService;
	}

	@Override
	public void setWrappedService(
		CPDefinitionLinkLocalService cpDefinitionLinkLocalService) {
		_cpDefinitionLinkLocalService = cpDefinitionLinkLocalService;
	}

	private CPDefinitionLinkLocalService _cpDefinitionLinkLocalService;
}