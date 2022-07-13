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
 * Provides a wrapper for {@link FVSFrontendDataSetEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see FVSFrontendDataSetEntryLocalService
 * @generated
 */
public class FVSFrontendDataSetEntryLocalServiceWrapper
	implements FVSFrontendDataSetEntryLocalService,
			   ServiceWrapper<FVSFrontendDataSetEntryLocalService> {

	public FVSFrontendDataSetEntryLocalServiceWrapper() {
		this(null);
	}

	public FVSFrontendDataSetEntryLocalServiceWrapper(
		FVSFrontendDataSetEntryLocalService
			fvsFrontendDataSetEntryLocalService) {

		_fvsFrontendDataSetEntryLocalService =
			fvsFrontendDataSetEntryLocalService;
	}

	/**
	 * Adds the fvs frontend data set entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FVSFrontendDataSetEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param fvsFrontendDataSetEntry the fvs frontend data set entry
	 * @return the fvs frontend data set entry that was added
	 */
	@Override
	public com.liferay.frontend.view.state.model.FVSFrontendDataSetEntry
		addFVSFrontendDataSetEntry(
			com.liferay.frontend.view.state.model.FVSFrontendDataSetEntry
				fvsFrontendDataSetEntry) {

		return _fvsFrontendDataSetEntryLocalService.addFVSFrontendDataSetEntry(
			fvsFrontendDataSetEntry);
	}

	/**
	 * Creates a new fvs frontend data set entry with the primary key. Does not add the fvs frontend data set entry to the database.
	 *
	 * @param fvsFrontendDataSetEntryId the primary key for the new fvs frontend data set entry
	 * @return the new fvs frontend data set entry
	 */
	@Override
	public com.liferay.frontend.view.state.model.FVSFrontendDataSetEntry
		createFVSFrontendDataSetEntry(long fvsFrontendDataSetEntryId) {

		return _fvsFrontendDataSetEntryLocalService.
			createFVSFrontendDataSetEntry(fvsFrontendDataSetEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fvsFrontendDataSetEntryLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the fvs frontend data set entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FVSFrontendDataSetEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param fvsFrontendDataSetEntry the fvs frontend data set entry
	 * @return the fvs frontend data set entry that was removed
	 */
	@Override
	public com.liferay.frontend.view.state.model.FVSFrontendDataSetEntry
		deleteFVSFrontendDataSetEntry(
			com.liferay.frontend.view.state.model.FVSFrontendDataSetEntry
				fvsFrontendDataSetEntry) {

		return _fvsFrontendDataSetEntryLocalService.
			deleteFVSFrontendDataSetEntry(fvsFrontendDataSetEntry);
	}

	/**
	 * Deletes the fvs frontend data set entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FVSFrontendDataSetEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param fvsFrontendDataSetEntryId the primary key of the fvs frontend data set entry
	 * @return the fvs frontend data set entry that was removed
	 * @throws PortalException if a fvs frontend data set entry with the primary key could not be found
	 */
	@Override
	public com.liferay.frontend.view.state.model.FVSFrontendDataSetEntry
			deleteFVSFrontendDataSetEntry(long fvsFrontendDataSetEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fvsFrontendDataSetEntryLocalService.
			deleteFVSFrontendDataSetEntry(fvsFrontendDataSetEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fvsFrontendDataSetEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _fvsFrontendDataSetEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _fvsFrontendDataSetEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _fvsFrontendDataSetEntryLocalService.dynamicQuery();
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

		return _fvsFrontendDataSetEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.frontend.view.state.model.impl.FVSFrontendDataSetEntryModelImpl</code>.
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

		return _fvsFrontendDataSetEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.frontend.view.state.model.impl.FVSFrontendDataSetEntryModelImpl</code>.
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

		return _fvsFrontendDataSetEntryLocalService.dynamicQuery(
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

		return _fvsFrontendDataSetEntryLocalService.dynamicQueryCount(
			dynamicQuery);
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

		return _fvsFrontendDataSetEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.frontend.view.state.model.FVSFrontendDataSetEntry
		fetchFVSFrontendDataSetEntry(long fvsFrontendDataSetEntryId) {

		return _fvsFrontendDataSetEntryLocalService.
			fetchFVSFrontendDataSetEntry(fvsFrontendDataSetEntryId);
	}

	/**
	 * Returns the fvs frontend data set entry with the matching UUID and company.
	 *
	 * @param uuid the fvs frontend data set entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching fvs frontend data set entry, or <code>null</code> if a matching fvs frontend data set entry could not be found
	 */
	@Override
	public com.liferay.frontend.view.state.model.FVSFrontendDataSetEntry
		fetchFVSFrontendDataSetEntryByUuidAndCompanyId(
			String uuid, long companyId) {

		return _fvsFrontendDataSetEntryLocalService.
			fetchFVSFrontendDataSetEntryByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _fvsFrontendDataSetEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _fvsFrontendDataSetEntryLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	/**
	 * Returns a range of all the fvs frontend data set entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.frontend.view.state.model.impl.FVSFrontendDataSetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fvs frontend data set entries
	 * @param end the upper bound of the range of fvs frontend data set entries (not inclusive)
	 * @return the range of fvs frontend data set entries
	 */
	@Override
	public java.util.List
		<com.liferay.frontend.view.state.model.FVSFrontendDataSetEntry>
			getFVSFrontendDataSetEntries(int start, int end) {

		return _fvsFrontendDataSetEntryLocalService.
			getFVSFrontendDataSetEntries(start, end);
	}

	/**
	 * Returns the number of fvs frontend data set entries.
	 *
	 * @return the number of fvs frontend data set entries
	 */
	@Override
	public int getFVSFrontendDataSetEntriesCount() {
		return _fvsFrontendDataSetEntryLocalService.
			getFVSFrontendDataSetEntriesCount();
	}

	/**
	 * Returns the fvs frontend data set entry with the primary key.
	 *
	 * @param fvsFrontendDataSetEntryId the primary key of the fvs frontend data set entry
	 * @return the fvs frontend data set entry
	 * @throws PortalException if a fvs frontend data set entry with the primary key could not be found
	 */
	@Override
	public com.liferay.frontend.view.state.model.FVSFrontendDataSetEntry
			getFVSFrontendDataSetEntry(long fvsFrontendDataSetEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fvsFrontendDataSetEntryLocalService.getFVSFrontendDataSetEntry(
			fvsFrontendDataSetEntryId);
	}

	/**
	 * Returns the fvs frontend data set entry with the matching UUID and company.
	 *
	 * @param uuid the fvs frontend data set entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching fvs frontend data set entry
	 * @throws PortalException if a matching fvs frontend data set entry could not be found
	 */
	@Override
	public com.liferay.frontend.view.state.model.FVSFrontendDataSetEntry
			getFVSFrontendDataSetEntryByUuidAndCompanyId(
				String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fvsFrontendDataSetEntryLocalService.
			getFVSFrontendDataSetEntryByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _fvsFrontendDataSetEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _fvsFrontendDataSetEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fvsFrontendDataSetEntryLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the fvs frontend data set entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FVSFrontendDataSetEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param fvsFrontendDataSetEntry the fvs frontend data set entry
	 * @return the fvs frontend data set entry that was updated
	 */
	@Override
	public com.liferay.frontend.view.state.model.FVSFrontendDataSetEntry
		updateFVSFrontendDataSetEntry(
			com.liferay.frontend.view.state.model.FVSFrontendDataSetEntry
				fvsFrontendDataSetEntry) {

		return _fvsFrontendDataSetEntryLocalService.
			updateFVSFrontendDataSetEntry(fvsFrontendDataSetEntry);
	}

	@Override
	public FVSFrontendDataSetEntryLocalService getWrappedService() {
		return _fvsFrontendDataSetEntryLocalService;
	}

	@Override
	public void setWrappedService(
		FVSFrontendDataSetEntryLocalService
			fvsFrontendDataSetEntryLocalService) {

		_fvsFrontendDataSetEntryLocalService =
			fvsFrontendDataSetEntryLocalService;
	}

	private FVSFrontendDataSetEntryLocalService
		_fvsFrontendDataSetEntryLocalService;

}