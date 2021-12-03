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

package com.liferay.portal.language.override.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link PLOEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see PLOEntryLocalService
 * @generated
 */
public class PLOEntryLocalServiceWrapper
	implements PLOEntryLocalService, ServiceWrapper<PLOEntryLocalService> {

	public PLOEntryLocalServiceWrapper() {
		this(null);
	}

	public PLOEntryLocalServiceWrapper(
		PLOEntryLocalService ploEntryLocalService) {

		_ploEntryLocalService = ploEntryLocalService;
	}

	@Override
	public com.liferay.portal.language.override.model.PLOEntry
			addOrUpdatePLOEntry(
				long companyId, long userId, String key, String languageId,
				String value)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ploEntryLocalService.addOrUpdatePLOEntry(
			companyId, userId, key, languageId, value);
	}

	/**
	 * Adds the plo entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PLOEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ploEntry the plo entry
	 * @return the plo entry that was added
	 */
	@Override
	public com.liferay.portal.language.override.model.PLOEntry addPLOEntry(
		com.liferay.portal.language.override.model.PLOEntry ploEntry) {

		return _ploEntryLocalService.addPLOEntry(ploEntry);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ploEntryLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Creates a new plo entry with the primary key. Does not add the plo entry to the database.
	 *
	 * @param ploEntryId the primary key for the new plo entry
	 * @return the new plo entry
	 */
	@Override
	public com.liferay.portal.language.override.model.PLOEntry createPLOEntry(
		long ploEntryId) {

		return _ploEntryLocalService.createPLOEntry(ploEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ploEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public void deletePLOEntries(long companyId, String key) {
		_ploEntryLocalService.deletePLOEntries(companyId, key);
	}

	/**
	 * Deletes the plo entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PLOEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ploEntryId the primary key of the plo entry
	 * @return the plo entry that was removed
	 * @throws PortalException if a plo entry with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.language.override.model.PLOEntry deletePLOEntry(
			long ploEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ploEntryLocalService.deletePLOEntry(ploEntryId);
	}

	@Override
	public com.liferay.portal.language.override.model.PLOEntry deletePLOEntry(
		long companyId, String key, String languageId) {

		return _ploEntryLocalService.deletePLOEntry(companyId, key, languageId);
	}

	/**
	 * Deletes the plo entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PLOEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ploEntry the plo entry
	 * @return the plo entry that was removed
	 */
	@Override
	public com.liferay.portal.language.override.model.PLOEntry deletePLOEntry(
		com.liferay.portal.language.override.model.PLOEntry ploEntry) {

		return _ploEntryLocalService.deletePLOEntry(ploEntry);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _ploEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _ploEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _ploEntryLocalService.dynamicQuery();
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

		return _ploEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.language.override.model.impl.PLOEntryModelImpl</code>.
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

		return _ploEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.language.override.model.impl.PLOEntryModelImpl</code>.
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

		return _ploEntryLocalService.dynamicQuery(
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

		return _ploEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _ploEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.language.override.model.PLOEntry fetchPLOEntry(
		long ploEntryId) {

		return _ploEntryLocalService.fetchPLOEntry(ploEntryId);
	}

	@Override
	public com.liferay.portal.language.override.model.PLOEntry fetchPLOEntry(
		long companyId, String key, String languageId) {

		return _ploEntryLocalService.fetchPLOEntry(companyId, key, languageId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _ploEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _ploEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _ploEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ploEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns a range of all the plo entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.language.override.model.impl.PLOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of plo entries
	 * @param end the upper bound of the range of plo entries (not inclusive)
	 * @return the range of plo entries
	 */
	@Override
	public java.util.List<com.liferay.portal.language.override.model.PLOEntry>
		getPLOEntries(int start, int end) {

		return _ploEntryLocalService.getPLOEntries(start, end);
	}

	@Override
	public java.util.List<com.liferay.portal.language.override.model.PLOEntry>
		getPLOEntries(long companyId) {

		return _ploEntryLocalService.getPLOEntries(companyId);
	}

	@Override
	public java.util.List<com.liferay.portal.language.override.model.PLOEntry>
		getPLOEntriesByLanguageId(long companyId, String languageId) {

		return _ploEntryLocalService.getPLOEntriesByLanguageId(
			companyId, languageId);
	}

	/**
	 * Returns the number of plo entries.
	 *
	 * @return the number of plo entries
	 */
	@Override
	public int getPLOEntriesCount() {
		return _ploEntryLocalService.getPLOEntriesCount();
	}

	/**
	 * Returns the plo entry with the primary key.
	 *
	 * @param ploEntryId the primary key of the plo entry
	 * @return the plo entry
	 * @throws PortalException if a plo entry with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.language.override.model.PLOEntry getPLOEntry(
			long ploEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ploEntryLocalService.getPLOEntry(ploEntryId);
	}

	@Override
	public void setPLOEntries(
			long companyId, long userId, String key,
			java.util.Map<java.util.Locale, String> localizationMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		_ploEntryLocalService.setPLOEntries(
			companyId, userId, key, localizationMap);
	}

	/**
	 * Updates the plo entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PLOEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ploEntry the plo entry
	 * @return the plo entry that was updated
	 */
	@Override
	public com.liferay.portal.language.override.model.PLOEntry updatePLOEntry(
		com.liferay.portal.language.override.model.PLOEntry ploEntry) {

		return _ploEntryLocalService.updatePLOEntry(ploEntry);
	}

	@Override
	public PLOEntryLocalService getWrappedService() {
		return _ploEntryLocalService;
	}

	@Override
	public void setWrappedService(PLOEntryLocalService ploEntryLocalService) {
		_ploEntryLocalService = ploEntryLocalService;
	}

	private PLOEntryLocalService _ploEntryLocalService;

}