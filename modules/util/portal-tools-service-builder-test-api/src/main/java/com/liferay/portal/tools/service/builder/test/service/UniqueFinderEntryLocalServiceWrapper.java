/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link UniqueFinderEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see UniqueFinderEntryLocalService
 * @generated
 */
public class UniqueFinderEntryLocalServiceWrapper
	implements ServiceWrapper<UniqueFinderEntryLocalService>,
			   UniqueFinderEntryLocalService {

	public UniqueFinderEntryLocalServiceWrapper() {
		this(null);
	}

	public UniqueFinderEntryLocalServiceWrapper(
		UniqueFinderEntryLocalService uniqueFinderEntryLocalService) {

		_uniqueFinderEntryLocalService = uniqueFinderEntryLocalService;
	}

	/**
	 * Adds the unique finder entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect UniqueFinderEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param uniqueFinderEntry the unique finder entry
	 * @return the unique finder entry that was added
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.UniqueFinderEntry
		addUniqueFinderEntry(
			com.liferay.portal.tools.service.builder.test.model.
				UniqueFinderEntry uniqueFinderEntry) {

		return _uniqueFinderEntryLocalService.addUniqueFinderEntry(
			uniqueFinderEntry);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _uniqueFinderEntryLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Creates a new unique finder entry with the primary key. Does not add the unique finder entry to the database.
	 *
	 * @param uniqueFinderEntryId the primary key for the new unique finder entry
	 * @return the new unique finder entry
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.UniqueFinderEntry
		createUniqueFinderEntry(long uniqueFinderEntryId) {

		return _uniqueFinderEntryLocalService.createUniqueFinderEntry(
			uniqueFinderEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _uniqueFinderEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	/**
	 * Deletes the unique finder entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect UniqueFinderEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param uniqueFinderEntryId the primary key of the unique finder entry
	 * @return the unique finder entry that was removed
	 * @throws PortalException if a unique finder entry with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.UniqueFinderEntry
			deleteUniqueFinderEntry(long uniqueFinderEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _uniqueFinderEntryLocalService.deleteUniqueFinderEntry(
			uniqueFinderEntryId);
	}

	/**
	 * Deletes the unique finder entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect UniqueFinderEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param uniqueFinderEntry the unique finder entry
	 * @return the unique finder entry that was removed
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.UniqueFinderEntry
		deleteUniqueFinderEntry(
			com.liferay.portal.tools.service.builder.test.model.
				UniqueFinderEntry uniqueFinderEntry) {

		return _uniqueFinderEntryLocalService.deleteUniqueFinderEntry(
			uniqueFinderEntry);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _uniqueFinderEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _uniqueFinderEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _uniqueFinderEntryLocalService.dynamicQuery();
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

		return _uniqueFinderEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.UniqueFinderEntryModelImpl</code>.
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

		return _uniqueFinderEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.UniqueFinderEntryModelImpl</code>.
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

		return _uniqueFinderEntryLocalService.dynamicQuery(
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

		return _uniqueFinderEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _uniqueFinderEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.UniqueFinderEntry
		fetchUniqueFinderEntry(long uniqueFinderEntryId) {

		return _uniqueFinderEntryLocalService.fetchUniqueFinderEntry(
			uniqueFinderEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _uniqueFinderEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _uniqueFinderEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _uniqueFinderEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _uniqueFinderEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns a range of all the unique finder entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.UniqueFinderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of unique finder entries
	 * @param end the upper bound of the range of unique finder entries (not inclusive)
	 * @return the range of unique finder entries
	 */
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.UniqueFinderEntry>
			getUniqueFinderEntries(int start, int end) {

		return _uniqueFinderEntryLocalService.getUniqueFinderEntries(
			start, end);
	}

	/**
	 * Returns the number of unique finder entries.
	 *
	 * @return the number of unique finder entries
	 */
	@Override
	public int getUniqueFinderEntriesCount() {
		return _uniqueFinderEntryLocalService.getUniqueFinderEntriesCount();
	}

	/**
	 * Returns the unique finder entry with the primary key.
	 *
	 * @param uniqueFinderEntryId the primary key of the unique finder entry
	 * @return the unique finder entry
	 * @throws PortalException if a unique finder entry with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.UniqueFinderEntry
			getUniqueFinderEntry(long uniqueFinderEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _uniqueFinderEntryLocalService.getUniqueFinderEntry(
			uniqueFinderEntryId);
	}

	/**
	 * Updates the unique finder entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect UniqueFinderEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param uniqueFinderEntry the unique finder entry
	 * @return the unique finder entry that was updated
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.UniqueFinderEntry
		updateUniqueFinderEntry(
			com.liferay.portal.tools.service.builder.test.model.
				UniqueFinderEntry uniqueFinderEntry) {

		return _uniqueFinderEntryLocalService.updateUniqueFinderEntry(
			uniqueFinderEntry);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _uniqueFinderEntryLocalService.getBasePersistence();
	}

	@Override
	public UniqueFinderEntryLocalService getWrappedService() {
		return _uniqueFinderEntryLocalService;
	}

	@Override
	public void setWrappedService(
		UniqueFinderEntryLocalService uniqueFinderEntryLocalService) {

		_uniqueFinderEntryLocalService = uniqueFinderEntryLocalService;
	}

	private UniqueFinderEntryLocalService _uniqueFinderEntryLocalService;

}
// LIFERAY-SERVICE-BUILDER-HASH:-2070318243