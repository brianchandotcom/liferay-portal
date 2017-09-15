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
 * Provides a wrapper for {@link CAvailabilityRangeEntryLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CAvailabilityRangeEntryLocalService
 * @generated
 */
@ProviderType
public class CAvailabilityRangeEntryLocalServiceWrapper
	implements CAvailabilityRangeEntryLocalService,
		ServiceWrapper<CAvailabilityRangeEntryLocalService> {
	public CAvailabilityRangeEntryLocalServiceWrapper(
		CAvailabilityRangeEntryLocalService cAvailabilityRangeEntryLocalService) {
		_cAvailabilityRangeEntryLocalService = cAvailabilityRangeEntryLocalService;
	}

	/**
	* Adds the c availability range entry to the database. Also notifies the appropriate model listeners.
	*
	* @param cAvailabilityRangeEntry the c availability range entry
	* @return the c availability range entry that was added
	*/
	@Override
	public com.liferay.commerce.model.CAvailabilityRangeEntry addCAvailabilityRangeEntry(
		com.liferay.commerce.model.CAvailabilityRangeEntry cAvailabilityRangeEntry) {
		return _cAvailabilityRangeEntryLocalService.addCAvailabilityRangeEntry(cAvailabilityRangeEntry);
	}

	/**
	* Creates a new c availability range entry with the primary key. Does not add the c availability range entry to the database.
	*
	* @param CAvailabilityRangeEntryId the primary key for the new c availability range entry
	* @return the new c availability range entry
	*/
	@Override
	public com.liferay.commerce.model.CAvailabilityRangeEntry createCAvailabilityRangeEntry(
		long CAvailabilityRangeEntryId) {
		return _cAvailabilityRangeEntryLocalService.createCAvailabilityRangeEntry(CAvailabilityRangeEntryId);
	}

	@Override
	public void deleteCAvailabilityRangeEntries(long groupId) {
		_cAvailabilityRangeEntryLocalService.deleteCAvailabilityRangeEntries(groupId);
	}

	/**
	* Deletes the c availability range entry from the database. Also notifies the appropriate model listeners.
	*
	* @param cAvailabilityRangeEntry the c availability range entry
	* @return the c availability range entry that was removed
	*/
	@Override
	public com.liferay.commerce.model.CAvailabilityRangeEntry deleteCAvailabilityRangeEntry(
		com.liferay.commerce.model.CAvailabilityRangeEntry cAvailabilityRangeEntry) {
		return _cAvailabilityRangeEntryLocalService.deleteCAvailabilityRangeEntry(cAvailabilityRangeEntry);
	}

	/**
	* Deletes the c availability range entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CAvailabilityRangeEntryId the primary key of the c availability range entry
	* @return the c availability range entry that was removed
	* @throws PortalException if a c availability range entry with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CAvailabilityRangeEntry deleteCAvailabilityRangeEntry(
		long CAvailabilityRangeEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cAvailabilityRangeEntryLocalService.deleteCAvailabilityRangeEntry(CAvailabilityRangeEntryId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cAvailabilityRangeEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _cAvailabilityRangeEntryLocalService.dynamicQuery();
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
		return _cAvailabilityRangeEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CAvailabilityRangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cAvailabilityRangeEntryLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CAvailabilityRangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cAvailabilityRangeEntryLocalService.dynamicQuery(dynamicQuery,
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
		return _cAvailabilityRangeEntryLocalService.dynamicQueryCount(dynamicQuery);
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
		return _cAvailabilityRangeEntryLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.model.CAvailabilityRangeEntry fetchCAvailabilityRangeEntry(
		long CAvailabilityRangeEntryId) {
		return _cAvailabilityRangeEntryLocalService.fetchCAvailabilityRangeEntry(CAvailabilityRangeEntryId);
	}

	@Override
	public com.liferay.commerce.model.CAvailabilityRangeEntry fetchCAvailabilityRangeEntry(
		long groupId, long cpDefinitionId) {
		return _cAvailabilityRangeEntryLocalService.fetchCAvailabilityRangeEntry(groupId,
			cpDefinitionId);
	}

	/**
	* Returns the c availability range entry matching the UUID and group.
	*
	* @param uuid the c availability range entry's UUID
	* @param groupId the primary key of the group
	* @return the matching c availability range entry, or <code>null</code> if a matching c availability range entry could not be found
	*/
	@Override
	public com.liferay.commerce.model.CAvailabilityRangeEntry fetchCAvailabilityRangeEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _cAvailabilityRangeEntryLocalService.fetchCAvailabilityRangeEntryByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _cAvailabilityRangeEntryLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns a range of all the c availability range entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CAvailabilityRangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of c availability range entries
	* @param end the upper bound of the range of c availability range entries (not inclusive)
	* @return the range of c availability range entries
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CAvailabilityRangeEntry> getCAvailabilityRangeEntries(
		int start, int end) {
		return _cAvailabilityRangeEntryLocalService.getCAvailabilityRangeEntries(start,
			end);
	}

	/**
	* Returns all the c availability range entries matching the UUID and company.
	*
	* @param uuid the UUID of the c availability range entries
	* @param companyId the primary key of the company
	* @return the matching c availability range entries, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CAvailabilityRangeEntry> getCAvailabilityRangeEntriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _cAvailabilityRangeEntryLocalService.getCAvailabilityRangeEntriesByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of c availability range entries matching the UUID and company.
	*
	* @param uuid the UUID of the c availability range entries
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of c availability range entries
	* @param end the upper bound of the range of c availability range entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching c availability range entries, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CAvailabilityRangeEntry> getCAvailabilityRangeEntriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CAvailabilityRangeEntry> orderByComparator) {
		return _cAvailabilityRangeEntryLocalService.getCAvailabilityRangeEntriesByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of c availability range entries.
	*
	* @return the number of c availability range entries
	*/
	@Override
	public int getCAvailabilityRangeEntriesCount() {
		return _cAvailabilityRangeEntryLocalService.getCAvailabilityRangeEntriesCount();
	}

	/**
	* Returns the c availability range entry with the primary key.
	*
	* @param CAvailabilityRangeEntryId the primary key of the c availability range entry
	* @return the c availability range entry
	* @throws PortalException if a c availability range entry with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CAvailabilityRangeEntry getCAvailabilityRangeEntry(
		long CAvailabilityRangeEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cAvailabilityRangeEntryLocalService.getCAvailabilityRangeEntry(CAvailabilityRangeEntryId);
	}

	/**
	* Returns the c availability range entry matching the UUID and group.
	*
	* @param uuid the c availability range entry's UUID
	* @param groupId the primary key of the group
	* @return the matching c availability range entry
	* @throws PortalException if a matching c availability range entry could not be found
	*/
	@Override
	public com.liferay.commerce.model.CAvailabilityRangeEntry getCAvailabilityRangeEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cAvailabilityRangeEntryLocalService.getCAvailabilityRangeEntryByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _cAvailabilityRangeEntryLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _cAvailabilityRangeEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _cAvailabilityRangeEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cAvailabilityRangeEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the c availability range entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cAvailabilityRangeEntry the c availability range entry
	* @return the c availability range entry that was updated
	*/
	@Override
	public com.liferay.commerce.model.CAvailabilityRangeEntry updateCAvailabilityRangeEntry(
		com.liferay.commerce.model.CAvailabilityRangeEntry cAvailabilityRangeEntry) {
		return _cAvailabilityRangeEntryLocalService.updateCAvailabilityRangeEntry(cAvailabilityRangeEntry);
	}

	@Override
	public com.liferay.commerce.model.CAvailabilityRangeEntry updateCAvailabilityRangeEntry(
		long cAvailabilityRangeEntryId, long cpDefinitionId,
		long commerceAvailabilityRangeId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cAvailabilityRangeEntryLocalService.updateCAvailabilityRangeEntry(cAvailabilityRangeEntryId,
			cpDefinitionId, commerceAvailabilityRangeId, serviceContext);
	}

	@Override
	public CAvailabilityRangeEntryLocalService getWrappedService() {
		return _cAvailabilityRangeEntryLocalService;
	}

	@Override
	public void setWrappedService(
		CAvailabilityRangeEntryLocalService cAvailabilityRangeEntryLocalService) {
		_cAvailabilityRangeEntryLocalService = cAvailabilityRangeEntryLocalService;
	}

	private CAvailabilityRangeEntryLocalService _cAvailabilityRangeEntryLocalService;
}