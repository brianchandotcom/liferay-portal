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

package com.liferay.frontend.view.state.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link FVSActiveEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see FVSActiveEntryLocalService
 * @generated
 */
public class FVSActiveEntryLocalServiceWrapper
	implements FVSActiveEntryLocalService,
			   ServiceWrapper<FVSActiveEntryLocalService> {

	public FVSActiveEntryLocalServiceWrapper(
		FVSActiveEntryLocalService fvsActiveEntryLocalService) {

		_fvsActiveEntryLocalService = fvsActiveEntryLocalService;
	}

	/**
	 * Adds the fvs active entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FVSActiveEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param fvsActiveEntry the fvs active entry
	 * @return the fvs active entry that was added
	 */
	@Override
	public com.liferay.frontend.view.state.model.FVSActiveEntry
		addFVSActiveEntry(
			com.liferay.frontend.view.state.model.FVSActiveEntry
				fvsActiveEntry) {

		return _fvsActiveEntryLocalService.addFVSActiveEntry(fvsActiveEntry);
	}

	/**
	 * Creates a new fvs active entry with the primary key. Does not add the fvs active entry to the database.
	 *
	 * @param fvsActiveEntryId the primary key for the new fvs active entry
	 * @return the new fvs active entry
	 */
	@Override
	public com.liferay.frontend.view.state.model.FVSActiveEntry
		createFVSActiveEntry(long fvsActiveEntryId) {

		return _fvsActiveEntryLocalService.createFVSActiveEntry(
			fvsActiveEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fvsActiveEntryLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the fvs active entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FVSActiveEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param fvsActiveEntry the fvs active entry
	 * @return the fvs active entry that was removed
	 */
	@Override
	public com.liferay.frontend.view.state.model.FVSActiveEntry
		deleteFVSActiveEntry(
			com.liferay.frontend.view.state.model.FVSActiveEntry
				fvsActiveEntry) {

		return _fvsActiveEntryLocalService.deleteFVSActiveEntry(fvsActiveEntry);
	}

	/**
	 * Deletes the fvs active entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FVSActiveEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param fvsActiveEntryId the primary key of the fvs active entry
	 * @return the fvs active entry that was removed
	 * @throws PortalException if a fvs active entry with the primary key could not be found
	 */
	@Override
	public com.liferay.frontend.view.state.model.FVSActiveEntry
			deleteFVSActiveEntry(long fvsActiveEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fvsActiveEntryLocalService.deleteFVSActiveEntry(
			fvsActiveEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fvsActiveEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _fvsActiveEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _fvsActiveEntryLocalService.dynamicQuery();
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

		return _fvsActiveEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.frontend.view.state.model.impl.FVSActiveEntryModelImpl</code>.
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

		return _fvsActiveEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.frontend.view.state.model.impl.FVSActiveEntryModelImpl</code>.
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

		return _fvsActiveEntryLocalService.dynamicQuery(
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

		return _fvsActiveEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _fvsActiveEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.frontend.view.state.model.FVSActiveEntry
		fetchFVSActiveEntry(long fvsActiveEntryId) {

		return _fvsActiveEntryLocalService.fetchFVSActiveEntry(
			fvsActiveEntryId);
	}

	/**
	 * Returns the fvs active entry with the matching UUID and company.
	 *
	 * @param uuid the fvs active entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching fvs active entry, or <code>null</code> if a matching fvs active entry could not be found
	 */
	@Override
	public com.liferay.frontend.view.state.model.FVSActiveEntry
		fetchFVSActiveEntryByUuidAndCompanyId(String uuid, long companyId) {

		return _fvsActiveEntryLocalService.
			fetchFVSActiveEntryByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _fvsActiveEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _fvsActiveEntryLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	/**
	 * Returns a range of all the fvs active entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.frontend.view.state.model.impl.FVSActiveEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fvs active entries
	 * @param end the upper bound of the range of fvs active entries (not inclusive)
	 * @return the range of fvs active entries
	 */
	@Override
	public java.util.List<com.liferay.frontend.view.state.model.FVSActiveEntry>
		getFVSActiveEntries(int start, int end) {

		return _fvsActiveEntryLocalService.getFVSActiveEntries(start, end);
	}

	/**
	 * Returns the number of fvs active entries.
	 *
	 * @return the number of fvs active entries
	 */
	@Override
	public int getFVSActiveEntriesCount() {
		return _fvsActiveEntryLocalService.getFVSActiveEntriesCount();
	}

	/**
	 * Returns the fvs active entry with the primary key.
	 *
	 * @param fvsActiveEntryId the primary key of the fvs active entry
	 * @return the fvs active entry
	 * @throws PortalException if a fvs active entry with the primary key could not be found
	 */
	@Override
	public com.liferay.frontend.view.state.model.FVSActiveEntry
			getFVSActiveEntry(long fvsActiveEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fvsActiveEntryLocalService.getFVSActiveEntry(fvsActiveEntryId);
	}

	/**
	 * Returns the fvs active entry with the matching UUID and company.
	 *
	 * @param uuid the fvs active entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching fvs active entry
	 * @throws PortalException if a matching fvs active entry could not be found
	 */
	@Override
	public com.liferay.frontend.view.state.model.FVSActiveEntry
			getFVSActiveEntryByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fvsActiveEntryLocalService.getFVSActiveEntryByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _fvsActiveEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _fvsActiveEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fvsActiveEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the fvs active entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FVSActiveEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param fvsActiveEntry the fvs active entry
	 * @return the fvs active entry that was updated
	 */
	@Override
	public com.liferay.frontend.view.state.model.FVSActiveEntry
		updateFVSActiveEntry(
			com.liferay.frontend.view.state.model.FVSActiveEntry
				fvsActiveEntry) {

		return _fvsActiveEntryLocalService.updateFVSActiveEntry(fvsActiveEntry);
	}

	@Override
	public FVSActiveEntryLocalService getWrappedService() {
		return _fvsActiveEntryLocalService;
	}

	@Override
	public void setWrappedService(
		FVSActiveEntryLocalService fvsActiveEntryLocalService) {

		_fvsActiveEntryLocalService = fvsActiveEntryLocalService;
	}

	private FVSActiveEntryLocalService _fvsActiveEntryLocalService;

}