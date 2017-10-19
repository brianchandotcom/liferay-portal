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

package com.liferay.reading.time.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ReadingTimeEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ReadingTimeEntryLocalService
 * @generated
 */
@ProviderType
public class ReadingTimeEntryLocalServiceWrapper
	implements ReadingTimeEntryLocalService,
		ServiceWrapper<ReadingTimeEntryLocalService> {
	public ReadingTimeEntryLocalServiceWrapper(
		ReadingTimeEntryLocalService readingTimeEntryLocalService) {
		_readingTimeEntryLocalService = readingTimeEntryLocalService;
	}

	@Override
	public com.liferay.reading.time.model.ReadingTimeEntry addReadingTimeEntry(
		long classNameId, long classPK, long readingTimeInSeconds) {
		return _readingTimeEntryLocalService.addReadingTimeEntry(classNameId,
			classPK, readingTimeInSeconds);
	}

	/**
	* Adds the reading time entry to the database. Also notifies the appropriate model listeners.
	*
	* @param readingTimeEntry the reading time entry
	* @return the reading time entry that was added
	*/
	@Override
	public com.liferay.reading.time.model.ReadingTimeEntry addReadingTimeEntry(
		com.liferay.reading.time.model.ReadingTimeEntry readingTimeEntry) {
		return _readingTimeEntryLocalService.addReadingTimeEntry(readingTimeEntry);
	}

	/**
	* Creates a new reading time entry with the primary key. Does not add the reading time entry to the database.
	*
	* @param readingTimeEntryId the primary key for the new reading time entry
	* @return the new reading time entry
	*/
	@Override
	public com.liferay.reading.time.model.ReadingTimeEntry createReadingTimeEntry(
		long readingTimeEntryId) {
		return _readingTimeEntryLocalService.createReadingTimeEntry(readingTimeEntryId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _readingTimeEntryLocalService.deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the reading time entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param readingTimeEntryId the primary key of the reading time entry
	* @return the reading time entry that was removed
	* @throws PortalException if a reading time entry with the primary key could not be found
	*/
	@Override
	public com.liferay.reading.time.model.ReadingTimeEntry deleteReadingTimeEntry(
		long readingTimeEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _readingTimeEntryLocalService.deleteReadingTimeEntry(readingTimeEntryId);
	}

	/**
	* Deletes the reading time entry from the database. Also notifies the appropriate model listeners.
	*
	* @param readingTimeEntry the reading time entry
	* @return the reading time entry that was removed
	*/
	@Override
	public com.liferay.reading.time.model.ReadingTimeEntry deleteReadingTimeEntry(
		com.liferay.reading.time.model.ReadingTimeEntry readingTimeEntry) {
		return _readingTimeEntryLocalService.deleteReadingTimeEntry(readingTimeEntry);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _readingTimeEntryLocalService.dynamicQuery();
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
		return _readingTimeEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.reading.time.model.impl.ReadingTimeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _readingTimeEntryLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.reading.time.model.impl.ReadingTimeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _readingTimeEntryLocalService.dynamicQuery(dynamicQuery, start,
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
		return _readingTimeEntryLocalService.dynamicQueryCount(dynamicQuery);
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
		return _readingTimeEntryLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.reading.time.model.ReadingTimeEntry fetchReadingTimeEntry(
		long readingTimeEntryId) {
		return _readingTimeEntryLocalService.fetchReadingTimeEntry(readingTimeEntryId);
	}

	@Override
	public com.liferay.reading.time.model.ReadingTimeEntry fetchReadingTimeEntry(
		long classNameId, long classPK) {
		return _readingTimeEntryLocalService.fetchReadingTimeEntry(classNameId,
			classPK);
	}

	/**
	* Returns the reading time entry with the matching UUID and company.
	*
	* @param uuid the reading time entry's UUID
	* @param companyId the primary key of the company
	* @return the matching reading time entry, or <code>null</code> if a matching reading time entry could not be found
	*/
	@Override
	public com.liferay.reading.time.model.ReadingTimeEntry fetchReadingTimeEntryByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _readingTimeEntryLocalService.fetchReadingTimeEntryByUuidAndCompanyId(uuid,
			companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _readingTimeEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _readingTimeEntryLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _readingTimeEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _readingTimeEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _readingTimeEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the reading time entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.reading.time.model.impl.ReadingTimeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of reading time entries
	* @param end the upper bound of the range of reading time entries (not inclusive)
	* @return the range of reading time entries
	*/
	@Override
	public java.util.List<com.liferay.reading.time.model.ReadingTimeEntry> getReadingTimeEntries(
		int start, int end) {
		return _readingTimeEntryLocalService.getReadingTimeEntries(start, end);
	}

	/**
	* Returns the number of reading time entries.
	*
	* @return the number of reading time entries
	*/
	@Override
	public int getReadingTimeEntriesCount() {
		return _readingTimeEntryLocalService.getReadingTimeEntriesCount();
	}

	/**
	* Returns the reading time entry with the primary key.
	*
	* @param readingTimeEntryId the primary key of the reading time entry
	* @return the reading time entry
	* @throws PortalException if a reading time entry with the primary key could not be found
	*/
	@Override
	public com.liferay.reading.time.model.ReadingTimeEntry getReadingTimeEntry(
		long readingTimeEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _readingTimeEntryLocalService.getReadingTimeEntry(readingTimeEntryId);
	}

	/**
	* Returns the reading time entry with the matching UUID and company.
	*
	* @param uuid the reading time entry's UUID
	* @param companyId the primary key of the company
	* @return the matching reading time entry
	* @throws PortalException if a matching reading time entry could not be found
	*/
	@Override
	public com.liferay.reading.time.model.ReadingTimeEntry getReadingTimeEntryByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _readingTimeEntryLocalService.getReadingTimeEntryByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Updates the reading time entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param readingTimeEntry the reading time entry
	* @return the reading time entry that was updated
	*/
	@Override
	public com.liferay.reading.time.model.ReadingTimeEntry updateReadingTimeEntry(
		com.liferay.reading.time.model.ReadingTimeEntry readingTimeEntry) {
		return _readingTimeEntryLocalService.updateReadingTimeEntry(readingTimeEntry);
	}

	@Override
	public ReadingTimeEntryLocalService getWrappedService() {
		return _readingTimeEntryLocalService;
	}

	@Override
	public void setWrappedService(
		ReadingTimeEntryLocalService readingTimeEntryLocalService) {
		_readingTimeEntryLocalService = readingTimeEntryLocalService;
	}

	private ReadingTimeEntryLocalService _readingTimeEntryLocalService;
}