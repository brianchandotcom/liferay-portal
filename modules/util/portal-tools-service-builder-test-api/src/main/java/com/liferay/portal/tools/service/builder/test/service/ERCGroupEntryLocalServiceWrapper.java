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
 * Provides a wrapper for {@link ERCGroupEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ERCGroupEntryLocalService
 * @generated
 */
public class ERCGroupEntryLocalServiceWrapper
	implements ERCGroupEntryLocalService,
			   ServiceWrapper<ERCGroupEntryLocalService> {

	public ERCGroupEntryLocalServiceWrapper(
		ERCGroupEntryLocalService ercGroupEntryLocalService) {

		_ercGroupEntryLocalService = ercGroupEntryLocalService;
	}

	/**
	 * Adds the erc group entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ERCGroupEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ercGroupEntry the erc group entry
	 * @return the erc group entry that was added
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.ERCGroupEntry
		addERCGroupEntry(
			com.liferay.portal.tools.service.builder.test.model.ERCGroupEntry
				ercGroupEntry) {

		return _ercGroupEntryLocalService.addERCGroupEntry(ercGroupEntry);
	}

	/**
	 * Creates a new erc group entry with the primary key. Does not add the erc group entry to the database.
	 *
	 * @param ercGroupEntryId the primary key for the new erc group entry
	 * @return the new erc group entry
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.ERCGroupEntry
		createERCGroupEntry(long ercGroupEntryId) {

		return _ercGroupEntryLocalService.createERCGroupEntry(ercGroupEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ercGroupEntryLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the erc group entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ERCGroupEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ercGroupEntry the erc group entry
	 * @return the erc group entry that was removed
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.ERCGroupEntry
		deleteERCGroupEntry(
			com.liferay.portal.tools.service.builder.test.model.ERCGroupEntry
				ercGroupEntry) {

		return _ercGroupEntryLocalService.deleteERCGroupEntry(ercGroupEntry);
	}

	/**
	 * Deletes the erc group entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ERCGroupEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ercGroupEntryId the primary key of the erc group entry
	 * @return the erc group entry that was removed
	 * @throws PortalException if a erc group entry with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.ERCGroupEntry
			deleteERCGroupEntry(long ercGroupEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ercGroupEntryLocalService.deleteERCGroupEntry(ercGroupEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ercGroupEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _ercGroupEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _ercGroupEntryLocalService.dynamicQuery();
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

		return _ercGroupEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.ERCGroupEntryModelImpl</code>.
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

		return _ercGroupEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.ERCGroupEntryModelImpl</code>.
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

		return _ercGroupEntryLocalService.dynamicQuery(
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

		return _ercGroupEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _ercGroupEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.ERCGroupEntry
		fetchERCGroupEntry(long ercGroupEntryId) {

		return _ercGroupEntryLocalService.fetchERCGroupEntry(ercGroupEntryId);
	}

	/**
	 * Returns the erc group entry with the matching external reference code and group.
	 *
	 * @param groupId the primary key of the group
	 * @param externalReferenceCode the erc group entry's external reference code
	 * @return the matching erc group entry, or <code>null</code> if a matching erc group entry could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.ERCGroupEntry
		fetchERCGroupEntryByReferenceCode(
			long groupId, String externalReferenceCode) {

		return _ercGroupEntryLocalService.fetchERCGroupEntryByReferenceCode(
			groupId, externalReferenceCode);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _ercGroupEntryLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the erc group entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.ERCGroupEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc group entries
	 * @param end the upper bound of the range of erc group entries (not inclusive)
	 * @return the range of erc group entries
	 */
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.ERCGroupEntry>
			getERCGroupEntries(int start, int end) {

		return _ercGroupEntryLocalService.getERCGroupEntries(start, end);
	}

	/**
	 * Returns the number of erc group entries.
	 *
	 * @return the number of erc group entries
	 */
	@Override
	public int getERCGroupEntriesCount() {
		return _ercGroupEntryLocalService.getERCGroupEntriesCount();
	}

	/**
	 * Returns the erc group entry with the primary key.
	 *
	 * @param ercGroupEntryId the primary key of the erc group entry
	 * @return the erc group entry
	 * @throws PortalException if a erc group entry with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.ERCGroupEntry
			getERCGroupEntry(long ercGroupEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ercGroupEntryLocalService.getERCGroupEntry(ercGroupEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _ercGroupEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _ercGroupEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ercGroupEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the erc group entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ERCGroupEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ercGroupEntry the erc group entry
	 * @return the erc group entry that was updated
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.ERCGroupEntry
		updateERCGroupEntry(
			com.liferay.portal.tools.service.builder.test.model.ERCGroupEntry
				ercGroupEntry) {

		return _ercGroupEntryLocalService.updateERCGroupEntry(ercGroupEntry);
	}

	@Override
	public ERCGroupEntryLocalService getWrappedService() {
		return _ercGroupEntryLocalService;
	}

	@Override
	public void setWrappedService(
		ERCGroupEntryLocalService ercGroupEntryLocalService) {

		_ercGroupEntryLocalService = ercGroupEntryLocalService;
	}

	private ERCGroupEntryLocalService _ercGroupEntryLocalService;

}