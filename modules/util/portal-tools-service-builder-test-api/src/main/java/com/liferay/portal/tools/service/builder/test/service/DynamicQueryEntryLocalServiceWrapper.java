/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link DynamicQueryEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DynamicQueryEntryLocalService
 * @generated
 */
public class DynamicQueryEntryLocalServiceWrapper
	implements DynamicQueryEntryLocalService,
			   ServiceWrapper<DynamicQueryEntryLocalService> {

	public DynamicQueryEntryLocalServiceWrapper() {
		this(null);
	}

	public DynamicQueryEntryLocalServiceWrapper(
		DynamicQueryEntryLocalService dynamicQueryEntryLocalService) {

		_dynamicQueryEntryLocalService = dynamicQueryEntryLocalService;
	}

	/**
	 * Adds the dynamic query entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DynamicQueryEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param dynamicQueryEntry the dynamic query entry
	 * @return the dynamic query entry that was added
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.DynamicQueryEntry
		addDynamicQueryEntry(
			com.liferay.portal.tools.service.builder.test.model.
				DynamicQueryEntry dynamicQueryEntry) {

		return _dynamicQueryEntryLocalService.addDynamicQueryEntry(
			dynamicQueryEntry);
	}

	/**
	 * Creates a new dynamic query entry with the primary key. Does not add the dynamic query entry to the database.
	 *
	 * @param dynamicQueryEntryId the primary key for the new dynamic query entry
	 * @return the new dynamic query entry
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.DynamicQueryEntry
		createDynamicQueryEntry(long dynamicQueryEntryId) {

		return _dynamicQueryEntryLocalService.createDynamicQueryEntry(
			dynamicQueryEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dynamicQueryEntryLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the dynamic query entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DynamicQueryEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param dynamicQueryEntry the dynamic query entry
	 * @return the dynamic query entry that was removed
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.DynamicQueryEntry
		deleteDynamicQueryEntry(
			com.liferay.portal.tools.service.builder.test.model.
				DynamicQueryEntry dynamicQueryEntry) {

		return _dynamicQueryEntryLocalService.deleteDynamicQueryEntry(
			dynamicQueryEntry);
	}

	/**
	 * Deletes the dynamic query entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DynamicQueryEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param dynamicQueryEntryId the primary key of the dynamic query entry
	 * @return the dynamic query entry that was removed
	 * @throws PortalException if a dynamic query entry with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.DynamicQueryEntry
			deleteDynamicQueryEntry(long dynamicQueryEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dynamicQueryEntryLocalService.deleteDynamicQueryEntry(
			dynamicQueryEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dynamicQueryEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _dynamicQueryEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _dynamicQueryEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _dynamicQueryEntryLocalService.dynamicQuery();
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

		return _dynamicQueryEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.DynamicQueryEntryModelImpl</code>.
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

		return _dynamicQueryEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.DynamicQueryEntryModelImpl</code>.
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

		return _dynamicQueryEntryLocalService.dynamicQuery(
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

		return _dynamicQueryEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _dynamicQueryEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.DynamicQueryEntry
		fetchDynamicQueryEntry(long dynamicQueryEntryId) {

		return _dynamicQueryEntryLocalService.fetchDynamicQueryEntry(
			dynamicQueryEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _dynamicQueryEntryLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the dynamic query entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.DynamicQueryEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dynamic query entries
	 * @param end the upper bound of the range of dynamic query entries (not inclusive)
	 * @return the range of dynamic query entries
	 */
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.DynamicQueryEntry>
			getDynamicQueryEntries(int start, int end) {

		return _dynamicQueryEntryLocalService.getDynamicQueryEntries(
			start, end);
	}

	/**
	 * Returns the number of dynamic query entries.
	 *
	 * @return the number of dynamic query entries
	 */
	@Override
	public int getDynamicQueryEntriesCount() {
		return _dynamicQueryEntryLocalService.getDynamicQueryEntriesCount();
	}

	/**
	 * Returns the dynamic query entry with the primary key.
	 *
	 * @param dynamicQueryEntryId the primary key of the dynamic query entry
	 * @return the dynamic query entry
	 * @throws PortalException if a dynamic query entry with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.DynamicQueryEntry
			getDynamicQueryEntry(long dynamicQueryEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dynamicQueryEntryLocalService.getDynamicQueryEntry(
			dynamicQueryEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _dynamicQueryEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _dynamicQueryEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dynamicQueryEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the dynamic query entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DynamicQueryEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param dynamicQueryEntry the dynamic query entry
	 * @return the dynamic query entry that was updated
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.DynamicQueryEntry
		updateDynamicQueryEntry(
			com.liferay.portal.tools.service.builder.test.model.
				DynamicQueryEntry dynamicQueryEntry) {

		return _dynamicQueryEntryLocalService.updateDynamicQueryEntry(
			dynamicQueryEntry);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _dynamicQueryEntryLocalService.getBasePersistence();
	}

	@Override
	public DynamicQueryEntryLocalService getWrappedService() {
		return _dynamicQueryEntryLocalService;
	}

	@Override
	public void setWrappedService(
		DynamicQueryEntryLocalService dynamicQueryEntryLocalService) {

		_dynamicQueryEntryLocalService = dynamicQueryEntryLocalService;
	}

	private DynamicQueryEntryLocalService _dynamicQueryEntryLocalService;

}
// LIFERAY-SERVICE-BUILDER-HASH:778585811