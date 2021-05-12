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
 * Provides a wrapper for {@link FVSEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see FVSEntryLocalService
 * @generated
 */
public class FVSEntryLocalServiceWrapper
	implements FVSEntryLocalService, ServiceWrapper<FVSEntryLocalService> {

	public FVSEntryLocalServiceWrapper(
		FVSEntryLocalService fvsEntryLocalService) {

		_fvsEntryLocalService = fvsEntryLocalService;
	}

	/**
	 * Adds the fvs entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FVSEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param fvsEntry the fvs entry
	 * @return the fvs entry that was added
	 */
	@Override
	public com.liferay.frontend.view.state.model.FVSEntry addFVSEntry(
		com.liferay.frontend.view.state.model.FVSEntry fvsEntry) {

		return _fvsEntryLocalService.addFVSEntry(fvsEntry);
	}

	/**
	 * Creates a new fvs entry with the primary key. Does not add the fvs entry to the database.
	 *
	 * @param fvsEntryId the primary key for the new fvs entry
	 * @return the new fvs entry
	 */
	@Override
	public com.liferay.frontend.view.state.model.FVSEntry createFVSEntry(
		long fvsEntryId) {

		return _fvsEntryLocalService.createFVSEntry(fvsEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fvsEntryLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the fvs entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FVSEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param fvsEntry the fvs entry
	 * @return the fvs entry that was removed
	 */
	@Override
	public com.liferay.frontend.view.state.model.FVSEntry deleteFVSEntry(
		com.liferay.frontend.view.state.model.FVSEntry fvsEntry) {

		return _fvsEntryLocalService.deleteFVSEntry(fvsEntry);
	}

	/**
	 * Deletes the fvs entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FVSEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param fvsEntryId the primary key of the fvs entry
	 * @return the fvs entry that was removed
	 * @throws PortalException if a fvs entry with the primary key could not be found
	 */
	@Override
	public com.liferay.frontend.view.state.model.FVSEntry deleteFVSEntry(
			long fvsEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fvsEntryLocalService.deleteFVSEntry(fvsEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fvsEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _fvsEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _fvsEntryLocalService.dynamicQuery();
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

		return _fvsEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.frontend.view.state.model.impl.FVSEntryModelImpl</code>.
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

		return _fvsEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.frontend.view.state.model.impl.FVSEntryModelImpl</code>.
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

		return _fvsEntryLocalService.dynamicQuery(
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

		return _fvsEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _fvsEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.frontend.view.state.model.FVSEntry fetchFVSEntry(
		long fvsEntryId) {

		return _fvsEntryLocalService.fetchFVSEntry(fvsEntryId);
	}

	/**
	 * Returns the fvs entry with the matching UUID and company.
	 *
	 * @param uuid the fvs entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching fvs entry, or <code>null</code> if a matching fvs entry could not be found
	 */
	@Override
	public com.liferay.frontend.view.state.model.FVSEntry
		fetchFVSEntryByUuidAndCompanyId(String uuid, long companyId) {

		return _fvsEntryLocalService.fetchFVSEntryByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _fvsEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _fvsEntryLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	/**
	 * Returns a range of all the fvs entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.frontend.view.state.model.impl.FVSEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fvs entries
	 * @param end the upper bound of the range of fvs entries (not inclusive)
	 * @return the range of fvs entries
	 */
	@Override
	public java.util.List<com.liferay.frontend.view.state.model.FVSEntry>
		getFVSEntries(int start, int end) {

		return _fvsEntryLocalService.getFVSEntries(start, end);
	}

	/**
	 * Returns the number of fvs entries.
	 *
	 * @return the number of fvs entries
	 */
	@Override
	public int getFVSEntriesCount() {
		return _fvsEntryLocalService.getFVSEntriesCount();
	}

	/**
	 * Returns the fvs entry with the primary key.
	 *
	 * @param fvsEntryId the primary key of the fvs entry
	 * @return the fvs entry
	 * @throws PortalException if a fvs entry with the primary key could not be found
	 */
	@Override
	public com.liferay.frontend.view.state.model.FVSEntry getFVSEntry(
			long fvsEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fvsEntryLocalService.getFVSEntry(fvsEntryId);
	}

	/**
	 * Returns the fvs entry with the matching UUID and company.
	 *
	 * @param uuid the fvs entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching fvs entry
	 * @throws PortalException if a matching fvs entry could not be found
	 */
	@Override
	public com.liferay.frontend.view.state.model.FVSEntry
			getFVSEntryByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fvsEntryLocalService.getFVSEntryByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _fvsEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _fvsEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fvsEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the fvs entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FVSEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param fvsEntry the fvs entry
	 * @return the fvs entry that was updated
	 */
	@Override
	public com.liferay.frontend.view.state.model.FVSEntry updateFVSEntry(
		com.liferay.frontend.view.state.model.FVSEntry fvsEntry) {

		return _fvsEntryLocalService.updateFVSEntry(fvsEntry);
	}

	@Override
	public FVSEntryLocalService getWrappedService() {
		return _fvsEntryLocalService;
	}

	@Override
	public void setWrappedService(FVSEntryLocalService fvsEntryLocalService) {
		_fvsEntryLocalService = fvsEntryLocalService;
	}

	private FVSEntryLocalService _fvsEntryLocalService;

}