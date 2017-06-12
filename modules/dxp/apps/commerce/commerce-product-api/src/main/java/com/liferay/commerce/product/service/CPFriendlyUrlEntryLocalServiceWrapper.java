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
 * Provides a wrapper for {@link CPFriendlyUrlEntryLocalService}.
 *
 * @author Marco Leo
 * @see CPFriendlyUrlEntryLocalService
 * @generated
 */
@ProviderType
public class CPFriendlyUrlEntryLocalServiceWrapper
	implements CPFriendlyUrlEntryLocalService,
		ServiceWrapper<CPFriendlyUrlEntryLocalService> {
	public CPFriendlyUrlEntryLocalServiceWrapper(
		CPFriendlyUrlEntryLocalService cpFriendlyUrlEntryLocalService) {
		_cpFriendlyUrlEntryLocalService = cpFriendlyUrlEntryLocalService;
	}

	/**
	* Adds the cp friendly url entry to the database. Also notifies the appropriate model listeners.
	*
	* @param cpFriendlyUrlEntry the cp friendly url entry
	* @return the cp friendly url entry that was added
	*/
	@Override
	public com.liferay.commerce.product.model.CPFriendlyUrlEntry addCPFriendlyUrlEntry(
		com.liferay.commerce.product.model.CPFriendlyUrlEntry cpFriendlyUrlEntry) {
		return _cpFriendlyUrlEntryLocalService.addCPFriendlyUrlEntry(cpFriendlyUrlEntry);
	}

	/**
	* Creates a new cp friendly url entry with the primary key. Does not add the cp friendly url entry to the database.
	*
	* @param CPFriendlyUrlEntryId the primary key for the new cp friendly url entry
	* @return the new cp friendly url entry
	*/
	@Override
	public com.liferay.commerce.product.model.CPFriendlyUrlEntry createCPFriendlyUrlEntry(
		long CPFriendlyUrlEntryId) {
		return _cpFriendlyUrlEntryLocalService.createCPFriendlyUrlEntry(CPFriendlyUrlEntryId);
	}

	/**
	* Deletes the cp friendly url entry from the database. Also notifies the appropriate model listeners.
	*
	* @param cpFriendlyUrlEntry the cp friendly url entry
	* @return the cp friendly url entry that was removed
	*/
	@Override
	public com.liferay.commerce.product.model.CPFriendlyUrlEntry deleteCPFriendlyUrlEntry(
		com.liferay.commerce.product.model.CPFriendlyUrlEntry cpFriendlyUrlEntry) {
		return _cpFriendlyUrlEntryLocalService.deleteCPFriendlyUrlEntry(cpFriendlyUrlEntry);
	}

	/**
	* Deletes the cp friendly url entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPFriendlyUrlEntryId the primary key of the cp friendly url entry
	* @return the cp friendly url entry that was removed
	* @throws PortalException if a cp friendly url entry with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPFriendlyUrlEntry deleteCPFriendlyUrlEntry(
		long CPFriendlyUrlEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpFriendlyUrlEntryLocalService.deleteCPFriendlyUrlEntry(CPFriendlyUrlEntryId);
	}

	@Override
	public com.liferay.commerce.product.model.CPFriendlyUrlEntry fetchCPFriendlyUrlEntry(
		long CPFriendlyUrlEntryId) {
		return _cpFriendlyUrlEntryLocalService.fetchCPFriendlyUrlEntry(CPFriendlyUrlEntryId);
	}

	/**
	* Returns the cp friendly url entry matching the UUID and group.
	*
	* @param uuid the cp friendly url entry's UUID
	* @param groupId the primary key of the group
	* @return the matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPFriendlyUrlEntry fetchCPFriendlyUrlEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _cpFriendlyUrlEntryLocalService.fetchCPFriendlyUrlEntryByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns the cp friendly url entry with the primary key.
	*
	* @param CPFriendlyUrlEntryId the primary key of the cp friendly url entry
	* @return the cp friendly url entry
	* @throws PortalException if a cp friendly url entry with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPFriendlyUrlEntry getCPFriendlyUrlEntry(
		long CPFriendlyUrlEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpFriendlyUrlEntryLocalService.getCPFriendlyUrlEntry(CPFriendlyUrlEntryId);
	}

	/**
	* Returns the cp friendly url entry matching the UUID and group.
	*
	* @param uuid the cp friendly url entry's UUID
	* @param groupId the primary key of the group
	* @return the matching cp friendly url entry
	* @throws PortalException if a matching cp friendly url entry could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPFriendlyUrlEntry getCPFriendlyUrlEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpFriendlyUrlEntryLocalService.getCPFriendlyUrlEntryByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Updates the cp friendly url entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpFriendlyUrlEntry the cp friendly url entry
	* @return the cp friendly url entry that was updated
	*/
	@Override
	public com.liferay.commerce.product.model.CPFriendlyUrlEntry updateCPFriendlyUrlEntry(
		com.liferay.commerce.product.model.CPFriendlyUrlEntry cpFriendlyUrlEntry) {
		return _cpFriendlyUrlEntryLocalService.updateCPFriendlyUrlEntry(cpFriendlyUrlEntry);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _cpFriendlyUrlEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _cpFriendlyUrlEntryLocalService.dynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _cpFriendlyUrlEntryLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _cpFriendlyUrlEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpFriendlyUrlEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpFriendlyUrlEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the number of cp friendly url entries.
	*
	* @return the number of cp friendly url entries
	*/
	@Override
	public int getCPFriendlyUrlEntriesCount() {
		return _cpFriendlyUrlEntryLocalService.getCPFriendlyUrlEntriesCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _cpFriendlyUrlEntryLocalService.getOSGiServiceIdentifier();
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
		return _cpFriendlyUrlEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPFriendlyUrlEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cpFriendlyUrlEntryLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPFriendlyUrlEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cpFriendlyUrlEntryLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	* Returns a range of all the cp friendly url entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPFriendlyUrlEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp friendly url entries
	* @param end the upper bound of the range of cp friendly url entries (not inclusive)
	* @return the range of cp friendly url entries
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPFriendlyUrlEntry> getCPFriendlyUrlEntries(
		int start, int end) {
		return _cpFriendlyUrlEntryLocalService.getCPFriendlyUrlEntries(start,
			end);
	}

	/**
	* Returns all the cp friendly url entries matching the UUID and company.
	*
	* @param uuid the UUID of the cp friendly url entries
	* @param companyId the primary key of the company
	* @return the matching cp friendly url entries, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPFriendlyUrlEntry> getCPFriendlyUrlEntriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _cpFriendlyUrlEntryLocalService.getCPFriendlyUrlEntriesByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of cp friendly url entries matching the UUID and company.
	*
	* @param uuid the UUID of the cp friendly url entries
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of cp friendly url entries
	* @param end the upper bound of the range of cp friendly url entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching cp friendly url entries, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPFriendlyUrlEntry> getCPFriendlyUrlEntriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPFriendlyUrlEntry> orderByComparator) {
		return _cpFriendlyUrlEntryLocalService.getCPFriendlyUrlEntriesByUuidAndCompanyId(uuid,
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
		return _cpFriendlyUrlEntryLocalService.dynamicQueryCount(dynamicQuery);
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
		return _cpFriendlyUrlEntryLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public CPFriendlyUrlEntryLocalService getWrappedService() {
		return _cpFriendlyUrlEntryLocalService;
	}

	@Override
	public void setWrappedService(
		CPFriendlyUrlEntryLocalService cpFriendlyUrlEntryLocalService) {
		_cpFriendlyUrlEntryLocalService = cpFriendlyUrlEntryLocalService;
	}

	private CPFriendlyUrlEntryLocalService _cpFriendlyUrlEntryLocalService;
}