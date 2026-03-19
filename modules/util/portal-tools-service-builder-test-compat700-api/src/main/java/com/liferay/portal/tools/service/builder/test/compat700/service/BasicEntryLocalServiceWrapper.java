/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat700.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link BasicEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see BasicEntryLocalService
 * @generated
 */
public class BasicEntryLocalServiceWrapper
	implements BasicEntryLocalService, ServiceWrapper<BasicEntryLocalService> {

	public BasicEntryLocalServiceWrapper(
		BasicEntryLocalService basicEntryLocalService) {

		_basicEntryLocalService = basicEntryLocalService;
	}

	/**
	 * Adds the basic entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BasicEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param basicEntry the basic entry
	 * @return the basic entry that was added
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.compat700.model.BasicEntry
			addBasicEntry(
				com.liferay.portal.tools.service.builder.test.compat700.model.
					BasicEntry basicEntry) {

		return _basicEntryLocalService.addBasicEntry(basicEntry);
	}

	@Override
	public void addMappingEntryBasicEntries(
		long mappingEntryId,
		java.util.List
			<com.liferay.portal.tools.service.builder.test.compat700.model.
				BasicEntry> basicEntries) {

		_basicEntryLocalService.addMappingEntryBasicEntries(
			mappingEntryId, basicEntries);
	}

	@Override
	public void addMappingEntryBasicEntries(
		long mappingEntryId, long[] basicEntryIds) {

		_basicEntryLocalService.addMappingEntryBasicEntries(
			mappingEntryId, basicEntryIds);
	}

	@Override
	public void addMappingEntryBasicEntry(
		long mappingEntryId,
		com.liferay.portal.tools.service.builder.test.compat700.model.BasicEntry
			basicEntry) {

		_basicEntryLocalService.addMappingEntryBasicEntry(
			mappingEntryId, basicEntry);
	}

	@Override
	public void addMappingEntryBasicEntry(
		long mappingEntryId, long basicEntryId) {

		_basicEntryLocalService.addMappingEntryBasicEntry(
			mappingEntryId, basicEntryId);
	}

	@Override
	public void clearMappingEntryBasicEntries(long mappingEntryId) {
		_basicEntryLocalService.clearMappingEntryBasicEntries(mappingEntryId);
	}

	/**
	 * Creates a new basic entry with the primary key. Does not add the basic entry to the database.
	 *
	 * @param basicEntryId the primary key for the new basic entry
	 * @return the new basic entry
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.compat700.model.BasicEntry
			createBasicEntry(long basicEntryId) {

		return _basicEntryLocalService.createBasicEntry(basicEntryId);
	}

	/**
	 * Deletes the basic entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BasicEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param basicEntry the basic entry
	 * @return the basic entry that was removed
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.compat700.model.BasicEntry
			deleteBasicEntry(
				com.liferay.portal.tools.service.builder.test.compat700.model.
					BasicEntry basicEntry) {

		return _basicEntryLocalService.deleteBasicEntry(basicEntry);
	}

	/**
	 * Deletes the basic entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BasicEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param basicEntryId the primary key of the basic entry
	 * @return the basic entry that was removed
	 * @throws PortalException if a basic entry with the primary key could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.compat700.model.BasicEntry
				deleteBasicEntry(long basicEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _basicEntryLocalService.deleteBasicEntry(basicEntryId);
	}

	@Override
	public void deleteMappingEntryBasicEntries(
		long mappingEntryId,
		java.util.List
			<com.liferay.portal.tools.service.builder.test.compat700.model.
				BasicEntry> basicEntries) {

		_basicEntryLocalService.deleteMappingEntryBasicEntries(
			mappingEntryId, basicEntries);
	}

	@Override
	public void deleteMappingEntryBasicEntries(
		long mappingEntryId, long[] basicEntryIds) {

		_basicEntryLocalService.deleteMappingEntryBasicEntries(
			mappingEntryId, basicEntryIds);
	}

	@Override
	public void deleteMappingEntryBasicEntry(
		long mappingEntryId,
		com.liferay.portal.tools.service.builder.test.compat700.model.BasicEntry
			basicEntry) {

		_basicEntryLocalService.deleteMappingEntryBasicEntry(
			mappingEntryId, basicEntry);
	}

	@Override
	public void deleteMappingEntryBasicEntry(
		long mappingEntryId, long basicEntryId) {

		_basicEntryLocalService.deleteMappingEntryBasicEntry(
			mappingEntryId, basicEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _basicEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _basicEntryLocalService.dynamicQuery();
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

		return _basicEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat700.model.impl.BasicEntryModelImpl</code>.
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

		return _basicEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat700.model.impl.BasicEntryModelImpl</code>.
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

		return _basicEntryLocalService.dynamicQuery(
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

		return _basicEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _basicEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public
		com.liferay.portal.tools.service.builder.test.compat700.model.BasicEntry
			fetchBasicEntry(long basicEntryId) {

		return _basicEntryLocalService.fetchBasicEntry(basicEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _basicEntryLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the basic entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat700.model.impl.BasicEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of basic entries
	 * @param end the upper bound of the range of basic entries (not inclusive)
	 * @return the range of basic entries
	 */
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.compat700.model.
			BasicEntry> getBasicEntries(int start, int end) {

		return _basicEntryLocalService.getBasicEntries(start, end);
	}

	/**
	 * Returns the number of basic entries.
	 *
	 * @return the number of basic entries
	 */
	@Override
	public int getBasicEntriesCount() {
		return _basicEntryLocalService.getBasicEntriesCount();
	}

	/**
	 * Returns the basic entry with the primary key.
	 *
	 * @param basicEntryId the primary key of the basic entry
	 * @return the basic entry
	 * @throws PortalException if a basic entry with the primary key could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.compat700.model.BasicEntry
				getBasicEntry(long basicEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _basicEntryLocalService.getBasicEntry(basicEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _basicEntryLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.compat700.model.
			BasicEntry> getMappingEntryBasicEntries(long mappingEntryId) {

		return _basicEntryLocalService.getMappingEntryBasicEntries(
			mappingEntryId);
	}

	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.compat700.model.
			BasicEntry> getMappingEntryBasicEntries(
				long mappingEntryId, int start, int end) {

		return _basicEntryLocalService.getMappingEntryBasicEntries(
			mappingEntryId, start, end);
	}

	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.compat700.model.
			BasicEntry> getMappingEntryBasicEntries(
				long mappingEntryId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.tools.service.builder.test.compat700.
						model.BasicEntry> orderByComparator) {

		return _basicEntryLocalService.getMappingEntryBasicEntries(
			mappingEntryId, start, end, orderByComparator);
	}

	@Override
	public int getMappingEntryBasicEntriesCount(long mappingEntryId) {
		return _basicEntryLocalService.getMappingEntryBasicEntriesCount(
			mappingEntryId);
	}

	/**
	 * Returns the mappingEntryIds of the mapping entries associated with the basic entry.
	 *
	 * @param basicEntryId the basicEntryId of the basic entry
	 * @return long[] the mappingEntryIds of mapping entries associated with the basic entry
	 */
	@Override
	public long[] getMappingEntryPrimaryKeys(long basicEntryId) {
		return _basicEntryLocalService.getMappingEntryPrimaryKeys(basicEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _basicEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _basicEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public boolean hasMappingEntryBasicEntries(long mappingEntryId) {
		return _basicEntryLocalService.hasMappingEntryBasicEntries(
			mappingEntryId);
	}

	@Override
	public boolean hasMappingEntryBasicEntry(
		long mappingEntryId, long basicEntryId) {

		return _basicEntryLocalService.hasMappingEntryBasicEntry(
			mappingEntryId, basicEntryId);
	}

	@Override
	public void setMappingEntryBasicEntries(
		long mappingEntryId, long[] basicEntryIds) {

		_basicEntryLocalService.setMappingEntryBasicEntries(
			mappingEntryId, basicEntryIds);
	}

	/**
	 * Updates the basic entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BasicEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param basicEntry the basic entry
	 * @return the basic entry that was updated
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.compat700.model.BasicEntry
			updateBasicEntry(
				com.liferay.portal.tools.service.builder.test.compat700.model.
					BasicEntry basicEntry) {

		return _basicEntryLocalService.updateBasicEntry(basicEntry);
	}

	@Override
	public BasicEntryLocalService getWrappedService() {
		return _basicEntryLocalService;
	}

	@Override
	public void setWrappedService(
		BasicEntryLocalService basicEntryLocalService) {

		_basicEntryLocalService = basicEntryLocalService;
	}

	private BasicEntryLocalService _basicEntryLocalService;

}
// LIFERAY-SERVICE-BUILDER-HASH:1446514780