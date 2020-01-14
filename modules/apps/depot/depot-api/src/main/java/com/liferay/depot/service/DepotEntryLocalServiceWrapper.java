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

package com.liferay.depot.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DepotEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DepotEntryLocalService
 * @generated
 */
public class DepotEntryLocalServiceWrapper
	implements DepotEntryLocalService, ServiceWrapper<DepotEntryLocalService> {

	public DepotEntryLocalServiceWrapper(
		DepotEntryLocalService depotEntryLocalService) {

		_depotEntryLocalService = depotEntryLocalService;
	}

	/**
	 * Adds the depot entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param depotEntry the depot entry
	 * @return the depot entry that was added
	 */
	@Override
	public com.liferay.depot.model.DepotEntry addDepotEntry(
		com.liferay.depot.model.DepotEntry depotEntry) {

		return _depotEntryLocalService.addDepotEntry(depotEntry);
	}

	@Override
	public com.liferay.depot.model.DepotEntry addDepotEntry(
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _depotEntryLocalService.addDepotEntry(
			nameMap, descriptionMap, serviceContext);
	}

	/**
	 * Creates a new depot entry with the primary key. Does not add the depot entry to the database.
	 *
	 * @param depotEntryId the primary key for the new depot entry
	 * @return the new depot entry
	 */
	@Override
	public com.liferay.depot.model.DepotEntry createDepotEntry(
		long depotEntryId) {

		return _depotEntryLocalService.createDepotEntry(depotEntryId);
	}

	/**
	 * Deletes the depot entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param depotEntry the depot entry
	 * @return the depot entry that was removed
	 */
	@Override
	public com.liferay.depot.model.DepotEntry deleteDepotEntry(
		com.liferay.depot.model.DepotEntry depotEntry) {

		return _depotEntryLocalService.deleteDepotEntry(depotEntry);
	}

	/**
	 * Deletes the depot entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param depotEntryId the primary key of the depot entry
	 * @return the depot entry that was removed
	 * @throws PortalException if a depot entry with the primary key could not be found
	 */
	@Override
	public com.liferay.depot.model.DepotEntry deleteDepotEntry(
			long depotEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _depotEntryLocalService.deleteDepotEntry(depotEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _depotEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _depotEntryLocalService.dynamicQuery();
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

		return _depotEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.depot.model.impl.DepotEntryModelImpl</code>.
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

		return _depotEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.depot.model.impl.DepotEntryModelImpl</code>.
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

		return _depotEntryLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
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

		return _depotEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _depotEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.depot.model.DepotEntry fetchDepotEntry(
		long depotEntryId) {

		return _depotEntryLocalService.fetchDepotEntry(depotEntryId);
	}

	/**
	 * Returns the depot entry matching the UUID and group.
	 *
	 * @param uuid the depot entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching depot entry, or <code>null</code> if a matching depot entry could not be found
	 */
	@Override
	public com.liferay.depot.model.DepotEntry fetchDepotEntryByUuidAndGroupId(
		String uuid, long groupId) {

		return _depotEntryLocalService.fetchDepotEntryByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _depotEntryLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the depot entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.depot.model.impl.DepotEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of depot entries
	 * @param end the upper bound of the range of depot entries (not inclusive)
	 * @return the range of depot entries
	 */
	@Override
	public java.util.List<com.liferay.depot.model.DepotEntry> getDepotEntries(
		int start, int end) {

		return _depotEntryLocalService.getDepotEntries(start, end);
	}

	/**
	 * Returns all the depot entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the depot entries
	 * @param companyId the primary key of the company
	 * @return the matching depot entries, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.depot.model.DepotEntry>
		getDepotEntriesByUuidAndCompanyId(String uuid, long companyId) {

		return _depotEntryLocalService.getDepotEntriesByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of depot entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the depot entries
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of depot entries
	 * @param end the upper bound of the range of depot entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching depot entries, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.depot.model.DepotEntry>
		getDepotEntriesByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.depot.model.DepotEntry> orderByComparator) {

		return _depotEntryLocalService.getDepotEntriesByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of depot entries.
	 *
	 * @return the number of depot entries
	 */
	@Override
	public int getDepotEntriesCount() {
		return _depotEntryLocalService.getDepotEntriesCount();
	}

	/**
	 * Returns the depot entry with the primary key.
	 *
	 * @param depotEntryId the primary key of the depot entry
	 * @return the depot entry
	 * @throws PortalException if a depot entry with the primary key could not be found
	 */
	@Override
	public com.liferay.depot.model.DepotEntry getDepotEntry(long depotEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _depotEntryLocalService.getDepotEntry(depotEntryId);
	}

	/**
	 * Returns the depot entry matching the UUID and group.
	 *
	 * @param uuid the depot entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching depot entry
	 * @throws PortalException if a matching depot entry could not be found
	 */
	@Override
	public com.liferay.depot.model.DepotEntry getDepotEntryByUuidAndGroupId(
			String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _depotEntryLocalService.getDepotEntryByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _depotEntryLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.depot.model.DepotEntry getGroupDepotEntry(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _depotEntryLocalService.getGroupDepotEntry(groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _depotEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _depotEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _depotEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the depot entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param depotEntry the depot entry
	 * @return the depot entry that was updated
	 */
	@Override
	public com.liferay.depot.model.DepotEntry updateDepotEntry(
		com.liferay.depot.model.DepotEntry depotEntry) {

		return _depotEntryLocalService.updateDepotEntry(depotEntry);
	}

	@Override
	public com.liferay.depot.model.DepotEntry updateDepotEntry(
			long depotEntryId, java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			java.util.Map<String, Boolean> depotAppCustomizationMap,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsProperties,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _depotEntryLocalService.updateDepotEntry(
			depotEntryId, nameMap, descriptionMap, depotAppCustomizationMap,
			typeSettingsProperties, serviceContext);
	}

	@Override
	public DepotEntryLocalService getWrappedService() {
		return _depotEntryLocalService;
	}

	@Override
	public void setWrappedService(
		DepotEntryLocalService depotEntryLocalService) {

		_depotEntryLocalService = depotEntryLocalService;
	}

	private DepotEntryLocalService _depotEntryLocalService;

}