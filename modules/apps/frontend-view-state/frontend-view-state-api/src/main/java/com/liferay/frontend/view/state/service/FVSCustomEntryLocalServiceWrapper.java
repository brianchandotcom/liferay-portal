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
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link FVSCustomEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see FVSCustomEntryLocalService
 * @generated
 */
public class FVSCustomEntryLocalServiceWrapper
	implements FVSCustomEntryLocalService,
			   ServiceWrapper<FVSCustomEntryLocalService> {

	public FVSCustomEntryLocalServiceWrapper() {
		this(null);
	}

	public FVSCustomEntryLocalServiceWrapper(
		FVSCustomEntryLocalService fvsCustomEntryLocalService) {

		_fvsCustomEntryLocalService = fvsCustomEntryLocalService;
	}

	/**
	 * Adds the fvs custom entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FVSCustomEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param fvsCustomEntry the fvs custom entry
	 * @return the fvs custom entry that was added
	 */
	@Override
	public com.liferay.frontend.view.state.model.FVSCustomEntry
		addFVSCustomEntry(
			com.liferay.frontend.view.state.model.FVSCustomEntry
				fvsCustomEntry) {

		return _fvsCustomEntryLocalService.addFVSCustomEntry(fvsCustomEntry);
	}

	/**
	 * Creates a new fvs custom entry with the primary key. Does not add the fvs custom entry to the database.
	 *
	 * @param fvsCustomEntryId the primary key for the new fvs custom entry
	 * @return the new fvs custom entry
	 */
	@Override
	public com.liferay.frontend.view.state.model.FVSCustomEntry
		createFVSCustomEntry(long fvsCustomEntryId) {

		return _fvsCustomEntryLocalService.createFVSCustomEntry(
			fvsCustomEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fvsCustomEntryLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the fvs custom entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FVSCustomEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param fvsCustomEntry the fvs custom entry
	 * @return the fvs custom entry that was removed
	 */
	@Override
	public com.liferay.frontend.view.state.model.FVSCustomEntry
		deleteFVSCustomEntry(
			com.liferay.frontend.view.state.model.FVSCustomEntry
				fvsCustomEntry) {

		return _fvsCustomEntryLocalService.deleteFVSCustomEntry(fvsCustomEntry);
	}

	/**
	 * Deletes the fvs custom entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FVSCustomEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param fvsCustomEntryId the primary key of the fvs custom entry
	 * @return the fvs custom entry that was removed
	 * @throws PortalException if a fvs custom entry with the primary key could not be found
	 */
	@Override
	public com.liferay.frontend.view.state.model.FVSCustomEntry
			deleteFVSCustomEntry(long fvsCustomEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fvsCustomEntryLocalService.deleteFVSCustomEntry(
			fvsCustomEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fvsCustomEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _fvsCustomEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _fvsCustomEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _fvsCustomEntryLocalService.dynamicQuery();
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

		return _fvsCustomEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.frontend.view.state.model.impl.FVSCustomEntryModelImpl</code>.
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

		return _fvsCustomEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.frontend.view.state.model.impl.FVSCustomEntryModelImpl</code>.
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

		return _fvsCustomEntryLocalService.dynamicQuery(
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

		return _fvsCustomEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _fvsCustomEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.frontend.view.state.model.FVSCustomEntry
		fetchFVSCustomEntry(long fvsCustomEntryId) {

		return _fvsCustomEntryLocalService.fetchFVSCustomEntry(
			fvsCustomEntryId);
	}

	/**
	 * Returns the fvs custom entry with the matching UUID and company.
	 *
	 * @param uuid the fvs custom entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching fvs custom entry, or <code>null</code> if a matching fvs custom entry could not be found
	 */
	@Override
	public com.liferay.frontend.view.state.model.FVSCustomEntry
		fetchFVSCustomEntryByUuidAndCompanyId(String uuid, long companyId) {

		return _fvsCustomEntryLocalService.
			fetchFVSCustomEntryByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _fvsCustomEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _fvsCustomEntryLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	/**
	 * Returns a range of all the fvs custom entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.frontend.view.state.model.impl.FVSCustomEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fvs custom entries
	 * @param end the upper bound of the range of fvs custom entries (not inclusive)
	 * @return the range of fvs custom entries
	 */
	@Override
	public java.util.List<com.liferay.frontend.view.state.model.FVSCustomEntry>
		getFVSCustomEntries(int start, int end) {

		return _fvsCustomEntryLocalService.getFVSCustomEntries(start, end);
	}

	/**
	 * Returns the number of fvs custom entries.
	 *
	 * @return the number of fvs custom entries
	 */
	@Override
	public int getFVSCustomEntriesCount() {
		return _fvsCustomEntryLocalService.getFVSCustomEntriesCount();
	}

	/**
	 * Returns the fvs custom entry with the primary key.
	 *
	 * @param fvsCustomEntryId the primary key of the fvs custom entry
	 * @return the fvs custom entry
	 * @throws PortalException if a fvs custom entry with the primary key could not be found
	 */
	@Override
	public com.liferay.frontend.view.state.model.FVSCustomEntry
			getFVSCustomEntry(long fvsCustomEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fvsCustomEntryLocalService.getFVSCustomEntry(fvsCustomEntryId);
	}

	/**
	 * Returns the fvs custom entry with the matching UUID and company.
	 *
	 * @param uuid the fvs custom entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching fvs custom entry
	 * @throws PortalException if a matching fvs custom entry could not be found
	 */
	@Override
	public com.liferay.frontend.view.state.model.FVSCustomEntry
			getFVSCustomEntryByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fvsCustomEntryLocalService.getFVSCustomEntryByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _fvsCustomEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _fvsCustomEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fvsCustomEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the fvs custom entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FVSCustomEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param fvsCustomEntry the fvs custom entry
	 * @return the fvs custom entry that was updated
	 */
	@Override
	public com.liferay.frontend.view.state.model.FVSCustomEntry
		updateFVSCustomEntry(
			com.liferay.frontend.view.state.model.FVSCustomEntry
				fvsCustomEntry) {

		return _fvsCustomEntryLocalService.updateFVSCustomEntry(fvsCustomEntry);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _fvsCustomEntryLocalService.getBasePersistence();
	}

	@Override
	public FVSCustomEntryLocalService getWrappedService() {
		return _fvsCustomEntryLocalService;
	}

	@Override
	public void setWrappedService(
		FVSCustomEntryLocalService fvsCustomEntryLocalService) {

		_fvsCustomEntryLocalService = fvsCustomEntryLocalService;
	}

	private FVSCustomEntryLocalService _fvsCustomEntryLocalService;

}