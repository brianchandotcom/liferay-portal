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

package com.liferay.portal.tools.service.builder.test.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ERCCompanyEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ERCCompanyEntryLocalService
 * @generated
 */
public class ERCCompanyEntryLocalServiceWrapper
	implements ERCCompanyEntryLocalService,
			   ServiceWrapper<ERCCompanyEntryLocalService> {

	public ERCCompanyEntryLocalServiceWrapper(
		ERCCompanyEntryLocalService ercCompanyEntryLocalService) {

		_ercCompanyEntryLocalService = ercCompanyEntryLocalService;
	}

	/**
	 * Adds the erc company entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ERCCompanyEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ercCompanyEntry the erc company entry
	 * @return the erc company entry that was added
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.ERCCompanyEntry
		addERCCompanyEntry(
			com.liferay.portal.tools.service.builder.test.model.ERCCompanyEntry
				ercCompanyEntry) {

		return _ercCompanyEntryLocalService.addERCCompanyEntry(ercCompanyEntry);
	}

	/**
	 * Creates a new erc company entry with the primary key. Does not add the erc company entry to the database.
	 *
	 * @param ercCompanyEntryId the primary key for the new erc company entry
	 * @return the new erc company entry
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.ERCCompanyEntry
		createERCCompanyEntry(long ercCompanyEntryId) {

		return _ercCompanyEntryLocalService.createERCCompanyEntry(
			ercCompanyEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ercCompanyEntryLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the erc company entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ERCCompanyEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ercCompanyEntry the erc company entry
	 * @return the erc company entry that was removed
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.ERCCompanyEntry
		deleteERCCompanyEntry(
			com.liferay.portal.tools.service.builder.test.model.ERCCompanyEntry
				ercCompanyEntry) {

		return _ercCompanyEntryLocalService.deleteERCCompanyEntry(
			ercCompanyEntry);
	}

	/**
	 * Deletes the erc company entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ERCCompanyEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ercCompanyEntryId the primary key of the erc company entry
	 * @return the erc company entry that was removed
	 * @throws PortalException if a erc company entry with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.ERCCompanyEntry
			deleteERCCompanyEntry(long ercCompanyEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ercCompanyEntryLocalService.deleteERCCompanyEntry(
			ercCompanyEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ercCompanyEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _ercCompanyEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _ercCompanyEntryLocalService.dynamicQuery();
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

		return _ercCompanyEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.ERCCompanyEntryModelImpl</code>.
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

		return _ercCompanyEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.ERCCompanyEntryModelImpl</code>.
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

		return _ercCompanyEntryLocalService.dynamicQuery(
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

		return _ercCompanyEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _ercCompanyEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.ERCCompanyEntry
		fetchERCCompanyEntry(long ercCompanyEntryId) {

		return _ercCompanyEntryLocalService.fetchERCCompanyEntry(
			ercCompanyEntryId);
	}

	/**
	 * Returns the erc company entry with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the erc company entry's external reference code
	 * @return the matching erc company entry, or <code>null</code> if a matching erc company entry could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.ERCCompanyEntry
		fetchERCCompanyEntryByReferenceCode(
			long companyId, String externalReferenceCode) {

		return _ercCompanyEntryLocalService.fetchERCCompanyEntryByReferenceCode(
			companyId, externalReferenceCode);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _ercCompanyEntryLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the erc company entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.ERCCompanyEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc company entries
	 * @param end the upper bound of the range of erc company entries (not inclusive)
	 * @return the range of erc company entries
	 */
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.ERCCompanyEntry>
			getERCCompanyEntries(int start, int end) {

		return _ercCompanyEntryLocalService.getERCCompanyEntries(start, end);
	}

	/**
	 * Returns the number of erc company entries.
	 *
	 * @return the number of erc company entries
	 */
	@Override
	public int getERCCompanyEntriesCount() {
		return _ercCompanyEntryLocalService.getERCCompanyEntriesCount();
	}

	/**
	 * Returns the erc company entry with the primary key.
	 *
	 * @param ercCompanyEntryId the primary key of the erc company entry
	 * @return the erc company entry
	 * @throws PortalException if a erc company entry with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.ERCCompanyEntry
			getERCCompanyEntry(long ercCompanyEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ercCompanyEntryLocalService.getERCCompanyEntry(
			ercCompanyEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _ercCompanyEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _ercCompanyEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ercCompanyEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the erc company entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ERCCompanyEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ercCompanyEntry the erc company entry
	 * @return the erc company entry that was updated
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.ERCCompanyEntry
		updateERCCompanyEntry(
			com.liferay.portal.tools.service.builder.test.model.ERCCompanyEntry
				ercCompanyEntry) {

		return _ercCompanyEntryLocalService.updateERCCompanyEntry(
			ercCompanyEntry);
	}

	@Override
	public ERCCompanyEntryLocalService getWrappedService() {
		return _ercCompanyEntryLocalService;
	}

	@Override
	public void setWrappedService(
		ERCCompanyEntryLocalService ercCompanyEntryLocalService) {

		_ercCompanyEntryLocalService = ercCompanyEntryLocalService;
	}

	private ERCCompanyEntryLocalService _ercCompanyEntryLocalService;

}