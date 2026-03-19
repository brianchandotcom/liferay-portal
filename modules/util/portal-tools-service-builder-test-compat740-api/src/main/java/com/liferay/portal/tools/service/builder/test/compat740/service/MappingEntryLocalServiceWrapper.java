/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat740.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link MappingEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see MappingEntryLocalService
 * @generated
 */
public class MappingEntryLocalServiceWrapper
	implements MappingEntryLocalService,
			   ServiceWrapper<MappingEntryLocalService> {

	public MappingEntryLocalServiceWrapper() {
		this(null);
	}

	public MappingEntryLocalServiceWrapper(
		MappingEntryLocalService mappingEntryLocalService) {

		_mappingEntryLocalService = mappingEntryLocalService;
	}

	@Override
	public boolean addBasicEntryMappingEntries(
		long basicEntryId,
		java.util.List
			<com.liferay.portal.tools.service.builder.test.compat740.model.
				MappingEntry> mappingEntries) {

		return _mappingEntryLocalService.addBasicEntryMappingEntries(
			basicEntryId, mappingEntries);
	}

	@Override
	public boolean addBasicEntryMappingEntries(
		long basicEntryId, long[] mappingEntryIds) {

		return _mappingEntryLocalService.addBasicEntryMappingEntries(
			basicEntryId, mappingEntryIds);
	}

	@Override
	public boolean addBasicEntryMappingEntry(
		long basicEntryId, long mappingEntryId) {

		return _mappingEntryLocalService.addBasicEntryMappingEntry(
			basicEntryId, mappingEntryId);
	}

	@Override
	public boolean addBasicEntryMappingEntry(
		long basicEntryId,
		com.liferay.portal.tools.service.builder.test.compat740.model.
			MappingEntry mappingEntry) {

		return _mappingEntryLocalService.addBasicEntryMappingEntry(
			basicEntryId, mappingEntry);
	}

	/**
	 * Adds the mapping entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MappingEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param mappingEntry the mapping entry
	 * @return the mapping entry that was added
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.compat740.model.
			MappingEntry addMappingEntry(
				com.liferay.portal.tools.service.builder.test.compat740.model.
					MappingEntry mappingEntry) {

		return _mappingEntryLocalService.addMappingEntry(mappingEntry);
	}

	@Override
	public void clearBasicEntryMappingEntries(long basicEntryId) {
		_mappingEntryLocalService.clearBasicEntryMappingEntries(basicEntryId);
	}

	/**
	 * Creates a new mapping entry with the primary key. Does not add the mapping entry to the database.
	 *
	 * @param mappingEntryId the primary key for the new mapping entry
	 * @return the new mapping entry
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.compat740.model.
			MappingEntry createMappingEntry(long mappingEntryId) {

		return _mappingEntryLocalService.createMappingEntry(mappingEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mappingEntryLocalService.createPersistedModel(primaryKeyObj);
	}

	@Override
	public void deleteBasicEntryMappingEntries(
		long basicEntryId,
		java.util.List
			<com.liferay.portal.tools.service.builder.test.compat740.model.
				MappingEntry> mappingEntries) {

		_mappingEntryLocalService.deleteBasicEntryMappingEntries(
			basicEntryId, mappingEntries);
	}

	@Override
	public void deleteBasicEntryMappingEntries(
		long basicEntryId, long[] mappingEntryIds) {

		_mappingEntryLocalService.deleteBasicEntryMappingEntries(
			basicEntryId, mappingEntryIds);
	}

	@Override
	public void deleteBasicEntryMappingEntry(
		long basicEntryId, long mappingEntryId) {

		_mappingEntryLocalService.deleteBasicEntryMappingEntry(
			basicEntryId, mappingEntryId);
	}

	@Override
	public void deleteBasicEntryMappingEntry(
		long basicEntryId,
		com.liferay.portal.tools.service.builder.test.compat740.model.
			MappingEntry mappingEntry) {

		_mappingEntryLocalService.deleteBasicEntryMappingEntry(
			basicEntryId, mappingEntry);
	}

	/**
	 * Deletes the mapping entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MappingEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param mappingEntryId the primary key of the mapping entry
	 * @return the mapping entry that was removed
	 * @throws PortalException if a mapping entry with the primary key could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.compat740.model.
			MappingEntry deleteMappingEntry(long mappingEntryId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _mappingEntryLocalService.deleteMappingEntry(mappingEntryId);
	}

	/**
	 * Deletes the mapping entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MappingEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param mappingEntry the mapping entry
	 * @return the mapping entry that was removed
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.compat740.model.
			MappingEntry deleteMappingEntry(
				com.liferay.portal.tools.service.builder.test.compat740.model.
					MappingEntry mappingEntry) {

		return _mappingEntryLocalService.deleteMappingEntry(mappingEntry);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mappingEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _mappingEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _mappingEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _mappingEntryLocalService.dynamicQuery();
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

		return _mappingEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat740.model.impl.MappingEntryModelImpl</code>.
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

		return _mappingEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat740.model.impl.MappingEntryModelImpl</code>.
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

		return _mappingEntryLocalService.dynamicQuery(
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

		return _mappingEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _mappingEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public
		com.liferay.portal.tools.service.builder.test.compat740.model.
			MappingEntry fetchMappingEntry(long mappingEntryId) {

		return _mappingEntryLocalService.fetchMappingEntry(mappingEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _mappingEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.compat740.model.
			MappingEntry> getBasicEntryMappingEntries(long basicEntryId) {

		return _mappingEntryLocalService.getBasicEntryMappingEntries(
			basicEntryId);
	}

	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.compat740.model.
			MappingEntry> getBasicEntryMappingEntries(
				long basicEntryId, int start, int end) {

		return _mappingEntryLocalService.getBasicEntryMappingEntries(
			basicEntryId, start, end);
	}

	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.compat740.model.
			MappingEntry> getBasicEntryMappingEntries(
				long basicEntryId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.tools.service.builder.test.compat740.
						model.MappingEntry> orderByComparator) {

		return _mappingEntryLocalService.getBasicEntryMappingEntries(
			basicEntryId, start, end, orderByComparator);
	}

	@Override
	public int getBasicEntryMappingEntriesCount(long basicEntryId) {
		return _mappingEntryLocalService.getBasicEntryMappingEntriesCount(
			basicEntryId);
	}

	/**
	 * Returns the basicEntryIds of the basic entries associated with the mapping entry.
	 *
	 * @param mappingEntryId the mappingEntryId of the mapping entry
	 * @return long[] the basicEntryIds of basic entries associated with the mapping entry
	 */
	@Override
	public long[] getBasicEntryPrimaryKeys(long mappingEntryId) {
		return _mappingEntryLocalService.getBasicEntryPrimaryKeys(
			mappingEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _mappingEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the mapping entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat740.model.impl.MappingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mapping entries
	 * @param end the upper bound of the range of mapping entries (not inclusive)
	 * @return the range of mapping entries
	 */
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.compat740.model.
			MappingEntry> getMappingEntries(int start, int end) {

		return _mappingEntryLocalService.getMappingEntries(start, end);
	}

	/**
	 * Returns the number of mapping entries.
	 *
	 * @return the number of mapping entries
	 */
	@Override
	public int getMappingEntriesCount() {
		return _mappingEntryLocalService.getMappingEntriesCount();
	}

	/**
	 * Returns the mapping entry with the primary key.
	 *
	 * @param mappingEntryId the primary key of the mapping entry
	 * @return the mapping entry
	 * @throws PortalException if a mapping entry with the primary key could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.compat740.model.
			MappingEntry getMappingEntry(long mappingEntryId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _mappingEntryLocalService.getMappingEntry(mappingEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _mappingEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mappingEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public boolean hasBasicEntryMappingEntries(long basicEntryId) {
		return _mappingEntryLocalService.hasBasicEntryMappingEntries(
			basicEntryId);
	}

	@Override
	public boolean hasBasicEntryMappingEntry(
		long basicEntryId, long mappingEntryId) {

		return _mappingEntryLocalService.hasBasicEntryMappingEntry(
			basicEntryId, mappingEntryId);
	}

	@Override
	public void setBasicEntryMappingEntries(
		long basicEntryId, long[] mappingEntryIds) {

		_mappingEntryLocalService.setBasicEntryMappingEntries(
			basicEntryId, mappingEntryIds);
	}

	/**
	 * Updates the mapping entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MappingEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param mappingEntry the mapping entry
	 * @return the mapping entry that was updated
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.compat740.model.
			MappingEntry updateMappingEntry(
				com.liferay.portal.tools.service.builder.test.compat740.model.
					MappingEntry mappingEntry) {

		return _mappingEntryLocalService.updateMappingEntry(mappingEntry);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _mappingEntryLocalService.getBasePersistence();
	}

	@Override
	public MappingEntryLocalService getWrappedService() {
		return _mappingEntryLocalService;
	}

	@Override
	public void setWrappedService(
		MappingEntryLocalService mappingEntryLocalService) {

		_mappingEntryLocalService = mappingEntryLocalService;
	}

	private MappingEntryLocalService _mappingEntryLocalService;

}
// LIFERAY-SERVICE-BUILDER-HASH:-1541246112