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
 * Provides a wrapper for {@link CPTemplateLayoutEntryLocalService}.
 *
 * @author Marco Leo
 * @see CPTemplateLayoutEntryLocalService
 * @generated
 */
@ProviderType
public class CPTemplateLayoutEntryLocalServiceWrapper
	implements CPTemplateLayoutEntryLocalService,
		ServiceWrapper<CPTemplateLayoutEntryLocalService> {
	public CPTemplateLayoutEntryLocalServiceWrapper(
		CPTemplateLayoutEntryLocalService cpTemplateLayoutEntryLocalService) {
		_cpTemplateLayoutEntryLocalService = cpTemplateLayoutEntryLocalService;
	}

	/**
	* Adds the cp template layout entry to the database. Also notifies the appropriate model listeners.
	*
	* @param cpTemplateLayoutEntry the cp template layout entry
	* @return the cp template layout entry that was added
	*/
	@Override
	public com.liferay.commerce.product.model.CPTemplateLayoutEntry addCPTemplateLayoutEntry(
		com.liferay.commerce.product.model.CPTemplateLayoutEntry cpTemplateLayoutEntry) {
		return _cpTemplateLayoutEntryLocalService.addCPTemplateLayoutEntry(cpTemplateLayoutEntry);
	}

	@Override
	public com.liferay.commerce.product.model.CPTemplateLayoutEntry addCPTemplateLayoutEntry(
		long groupId, long companyId, java.lang.Class<?> clazz, long classPK,
		java.lang.String layoutUuid)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpTemplateLayoutEntryLocalService.addCPTemplateLayoutEntry(groupId,
			companyId, clazz, classPK, layoutUuid);
	}

	@Override
	public com.liferay.commerce.product.model.CPTemplateLayoutEntry addCPTemplateLayoutEntry(
		long groupId, long companyId, long classNameId, long classPK,
		java.lang.String layoutUuid)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpTemplateLayoutEntryLocalService.addCPTemplateLayoutEntry(groupId,
			companyId, classNameId, classPK, layoutUuid);
	}

	/**
	* Creates a new cp template layout entry with the primary key. Does not add the cp template layout entry to the database.
	*
	* @param CPFriendlyURLEntryId the primary key for the new cp template layout entry
	* @return the new cp template layout entry
	*/
	@Override
	public com.liferay.commerce.product.model.CPTemplateLayoutEntry createCPTemplateLayoutEntry(
		long CPFriendlyURLEntryId) {
		return _cpTemplateLayoutEntryLocalService.createCPTemplateLayoutEntry(CPFriendlyURLEntryId);
	}

	/**
	* Deletes the cp template layout entry from the database. Also notifies the appropriate model listeners.
	*
	* @param cpTemplateLayoutEntry the cp template layout entry
	* @return the cp template layout entry that was removed
	*/
	@Override
	public com.liferay.commerce.product.model.CPTemplateLayoutEntry deleteCPTemplateLayoutEntry(
		com.liferay.commerce.product.model.CPTemplateLayoutEntry cpTemplateLayoutEntry) {
		return _cpTemplateLayoutEntryLocalService.deleteCPTemplateLayoutEntry(cpTemplateLayoutEntry);
	}

	/**
	* Deletes the cp template layout entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPFriendlyURLEntryId the primary key of the cp template layout entry
	* @return the cp template layout entry that was removed
	* @throws PortalException if a cp template layout entry with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPTemplateLayoutEntry deleteCPTemplateLayoutEntry(
		long CPFriendlyURLEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpTemplateLayoutEntryLocalService.deleteCPTemplateLayoutEntry(CPFriendlyURLEntryId);
	}

	@Override
	public com.liferay.commerce.product.model.CPTemplateLayoutEntry fetchCPTemplateLayoutEntry(
		long CPFriendlyURLEntryId) {
		return _cpTemplateLayoutEntryLocalService.fetchCPTemplateLayoutEntry(CPFriendlyURLEntryId);
	}

	/**
	* Returns the cp template layout entry matching the UUID and group.
	*
	* @param uuid the cp template layout entry's UUID
	* @param groupId the primary key of the group
	* @return the matching cp template layout entry, or <code>null</code> if a matching cp template layout entry could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPTemplateLayoutEntry fetchCPTemplateLayoutEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _cpTemplateLayoutEntryLocalService.fetchCPTemplateLayoutEntryByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns the cp template layout entry with the primary key.
	*
	* @param CPFriendlyURLEntryId the primary key of the cp template layout entry
	* @return the cp template layout entry
	* @throws PortalException if a cp template layout entry with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPTemplateLayoutEntry getCPTemplateLayoutEntry(
		long CPFriendlyURLEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpTemplateLayoutEntryLocalService.getCPTemplateLayoutEntry(CPFriendlyURLEntryId);
	}

	@Override
	public com.liferay.commerce.product.model.CPTemplateLayoutEntry getCPTemplateLayoutEntry(
		long groupId, long companyId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpTemplateLayoutEntryLocalService.getCPTemplateLayoutEntry(groupId,
			companyId, classNameId, classPK);
	}

	/**
	* Returns the cp template layout entry matching the UUID and group.
	*
	* @param uuid the cp template layout entry's UUID
	* @param groupId the primary key of the group
	* @return the matching cp template layout entry
	* @throws PortalException if a matching cp template layout entry could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPTemplateLayoutEntry getCPTemplateLayoutEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpTemplateLayoutEntryLocalService.getCPTemplateLayoutEntryByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Updates the cp template layout entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpTemplateLayoutEntry the cp template layout entry
	* @return the cp template layout entry that was updated
	*/
	@Override
	public com.liferay.commerce.product.model.CPTemplateLayoutEntry updateCPTemplateLayoutEntry(
		com.liferay.commerce.product.model.CPTemplateLayoutEntry cpTemplateLayoutEntry) {
		return _cpTemplateLayoutEntryLocalService.updateCPTemplateLayoutEntry(cpTemplateLayoutEntry);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _cpTemplateLayoutEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _cpTemplateLayoutEntryLocalService.dynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _cpTemplateLayoutEntryLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _cpTemplateLayoutEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpTemplateLayoutEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpTemplateLayoutEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the number of cp template layout entries.
	*
	* @return the number of cp template layout entries
	*/
	@Override
	public int getCPTemplateLayoutEntriesCount() {
		return _cpTemplateLayoutEntryLocalService.getCPTemplateLayoutEntriesCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _cpTemplateLayoutEntryLocalService.getOSGiServiceIdentifier();
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
		return _cpTemplateLayoutEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPTemplateLayoutEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cpTemplateLayoutEntryLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPTemplateLayoutEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cpTemplateLayoutEntryLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	* Returns a range of all the cp template layout entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPTemplateLayoutEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp template layout entries
	* @param end the upper bound of the range of cp template layout entries (not inclusive)
	* @return the range of cp template layout entries
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPTemplateLayoutEntry> getCPTemplateLayoutEntries(
		int start, int end) {
		return _cpTemplateLayoutEntryLocalService.getCPTemplateLayoutEntries(start,
			end);
	}

	/**
	* Returns all the cp template layout entries matching the UUID and company.
	*
	* @param uuid the UUID of the cp template layout entries
	* @param companyId the primary key of the company
	* @return the matching cp template layout entries, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPTemplateLayoutEntry> getCPTemplateLayoutEntriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _cpTemplateLayoutEntryLocalService.getCPTemplateLayoutEntriesByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of cp template layout entries matching the UUID and company.
	*
	* @param uuid the UUID of the cp template layout entries
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of cp template layout entries
	* @param end the upper bound of the range of cp template layout entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching cp template layout entries, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPTemplateLayoutEntry> getCPTemplateLayoutEntriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPTemplateLayoutEntry> orderByComparator) {
		return _cpTemplateLayoutEntryLocalService.getCPTemplateLayoutEntriesByUuidAndCompanyId(uuid,
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
		return _cpTemplateLayoutEntryLocalService.dynamicQueryCount(dynamicQuery);
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
		return _cpTemplateLayoutEntryLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public void deleteCPTemplateLayoutEntry(long groupId, long companyId,
		java.lang.Class<?> clazz, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpTemplateLayoutEntryLocalService.deleteCPTemplateLayoutEntry(groupId,
			companyId, clazz, classPK);
	}

	@Override
	public CPTemplateLayoutEntryLocalService getWrappedService() {
		return _cpTemplateLayoutEntryLocalService;
	}

	@Override
	public void setWrappedService(
		CPTemplateLayoutEntryLocalService cpTemplateLayoutEntryLocalService) {
		_cpTemplateLayoutEntryLocalService = cpTemplateLayoutEntryLocalService;
	}

	private CPTemplateLayoutEntryLocalService _cpTemplateLayoutEntryLocalService;
}