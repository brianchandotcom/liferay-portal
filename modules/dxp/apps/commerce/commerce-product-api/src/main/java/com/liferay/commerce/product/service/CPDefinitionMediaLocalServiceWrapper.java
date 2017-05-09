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
 * Provides a wrapper for {@link CPDefinitionMediaLocalService}.
 *
 * @author Marco Leo
 * @see CPDefinitionMediaLocalService
 * @generated
 */
@ProviderType
public class CPDefinitionMediaLocalServiceWrapper
	implements CPDefinitionMediaLocalService,
		ServiceWrapper<CPDefinitionMediaLocalService> {
	public CPDefinitionMediaLocalServiceWrapper(
		CPDefinitionMediaLocalService cpDefinitionMediaLocalService) {
		_cpDefinitionMediaLocalService = cpDefinitionMediaLocalService;
	}

	/**
	* Adds the cp definition media to the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionMedia the cp definition media
	* @return the cp definition media that was added
	*/
	@Override
	public com.liferay.commerce.product.model.CPDefinitionMedia addCPDefinitionMedia(
		com.liferay.commerce.product.model.CPDefinitionMedia cpDefinitionMedia) {
		return _cpDefinitionMediaLocalService.addCPDefinitionMedia(cpDefinitionMedia);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinitionMedia addCPDefinitionMedia(
		long cpDefinitionId, long fileEntryId, java.lang.String ddmContent,
		int priority, long CPMediaTypeId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionMediaLocalService.addCPDefinitionMedia(cpDefinitionId,
			fileEntryId, ddmContent, priority, CPMediaTypeId, serviceContext);
	}

	/**
	* Creates a new cp definition media with the primary key. Does not add the cp definition media to the database.
	*
	* @param CPDefinitionMediaId the primary key for the new cp definition media
	* @return the new cp definition media
	*/
	@Override
	public com.liferay.commerce.product.model.CPDefinitionMedia createCPDefinitionMedia(
		long CPDefinitionMediaId) {
		return _cpDefinitionMediaLocalService.createCPDefinitionMedia(CPDefinitionMediaId);
	}

	/**
	* Deletes the cp definition media from the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionMedia the cp definition media
	* @return the cp definition media that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.commerce.product.model.CPDefinitionMedia deleteCPDefinitionMedia(
		com.liferay.commerce.product.model.CPDefinitionMedia cpDefinitionMedia)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionMediaLocalService.deleteCPDefinitionMedia(cpDefinitionMedia);
	}

	/**
	* Deletes the cp definition media with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDefinitionMediaId the primary key of the cp definition media
	* @return the cp definition media that was removed
	* @throws PortalException if a cp definition media with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPDefinitionMedia deleteCPDefinitionMedia(
		long CPDefinitionMediaId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionMediaLocalService.deleteCPDefinitionMedia(CPDefinitionMediaId);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinitionMedia fetchCPDefinitionMedia(
		long CPDefinitionMediaId) {
		return _cpDefinitionMediaLocalService.fetchCPDefinitionMedia(CPDefinitionMediaId);
	}

	/**
	* Returns the cp definition media matching the UUID and group.
	*
	* @param uuid the cp definition media's UUID
	* @param groupId the primary key of the group
	* @return the matching cp definition media, or <code>null</code> if a matching cp definition media could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPDefinitionMedia fetchCPDefinitionMediaByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _cpDefinitionMediaLocalService.fetchCPDefinitionMediaByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns the cp definition media with the primary key.
	*
	* @param CPDefinitionMediaId the primary key of the cp definition media
	* @return the cp definition media
	* @throws PortalException if a cp definition media with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPDefinitionMedia getCPDefinitionMedia(
		long CPDefinitionMediaId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionMediaLocalService.getCPDefinitionMedia(CPDefinitionMediaId);
	}

	/**
	* Returns the cp definition media matching the UUID and group.
	*
	* @param uuid the cp definition media's UUID
	* @param groupId the primary key of the group
	* @return the matching cp definition media
	* @throws PortalException if a matching cp definition media could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPDefinitionMedia getCPDefinitionMediaByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionMediaLocalService.getCPDefinitionMediaByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Updates the cp definition media in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionMedia the cp definition media
	* @return the cp definition media that was updated
	*/
	@Override
	public com.liferay.commerce.product.model.CPDefinitionMedia updateCPDefinitionMedia(
		com.liferay.commerce.product.model.CPDefinitionMedia cpDefinitionMedia) {
		return _cpDefinitionMediaLocalService.updateCPDefinitionMedia(cpDefinitionMedia);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinitionMedia updateCPDefinitionMedia(
		long cpDefinitionMediaId, java.lang.String ddmContent, int priority,
		long CPMediaTypeId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionMediaLocalService.updateCPDefinitionMedia(cpDefinitionMediaId,
			ddmContent, priority, CPMediaTypeId, serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CPMediaType deleteCPMediaType(
		long cpMediaTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionMediaLocalService.deleteCPMediaType(cpMediaTypeId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _cpDefinitionMediaLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _cpDefinitionMediaLocalService.dynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _cpDefinitionMediaLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _cpDefinitionMediaLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionMediaLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionMediaLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the number of cp definition medias.
	*
	* @return the number of cp definition medias
	*/
	@Override
	public int getCPDefinitionMediasCount() {
		return _cpDefinitionMediaLocalService.getCPDefinitionMediasCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _cpDefinitionMediaLocalService.getOSGiServiceIdentifier();
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
		return _cpDefinitionMediaLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPDefinitionMediaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cpDefinitionMediaLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPDefinitionMediaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cpDefinitionMediaLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	* Returns a range of all the cp definition medias.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPDefinitionMediaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition medias
	* @param end the upper bound of the range of cp definition medias (not inclusive)
	* @return the range of cp definition medias
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinitionMedia> getCPDefinitionMedias(
		int start, int end) {
		return _cpDefinitionMediaLocalService.getCPDefinitionMedias(start, end);
	}

	/**
	* Returns all the cp definition medias matching the UUID and company.
	*
	* @param uuid the UUID of the cp definition medias
	* @param companyId the primary key of the company
	* @return the matching cp definition medias, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinitionMedia> getCPDefinitionMediasByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _cpDefinitionMediaLocalService.getCPDefinitionMediasByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of cp definition medias matching the UUID and company.
	*
	* @param uuid the UUID of the cp definition medias
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of cp definition medias
	* @param end the upper bound of the range of cp definition medias (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching cp definition medias, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinitionMedia> getCPDefinitionMediasByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPDefinitionMedia> orderByComparator) {
		return _cpDefinitionMediaLocalService.getCPDefinitionMediasByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
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
		return _cpDefinitionMediaLocalService.dynamicQueryCount(dynamicQuery);
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
		return _cpDefinitionMediaLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public CPDefinitionMediaLocalService getWrappedService() {
		return _cpDefinitionMediaLocalService;
	}

	@Override
	public void setWrappedService(
		CPDefinitionMediaLocalService cpDefinitionMediaLocalService) {
		_cpDefinitionMediaLocalService = cpDefinitionMediaLocalService;
	}

	private CPDefinitionMediaLocalService _cpDefinitionMediaLocalService;
}