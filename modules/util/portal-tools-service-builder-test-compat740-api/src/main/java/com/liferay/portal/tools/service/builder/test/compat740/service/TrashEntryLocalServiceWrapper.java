/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat740.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link TrashEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see TrashEntryLocalService
 * @generated
 */
public class TrashEntryLocalServiceWrapper
	implements ServiceWrapper<TrashEntryLocalService>, TrashEntryLocalService {

	public TrashEntryLocalServiceWrapper() {
		this(null);
	}

	public TrashEntryLocalServiceWrapper(
		TrashEntryLocalService trashEntryLocalService) {

		_trashEntryLocalService = trashEntryLocalService;
	}

	/**
	 * Adds the trash entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect TrashEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param trashEntry the trash entry
	 * @return the trash entry that was added
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.compat740.model.TrashEntry
			addTrashEntry(
				com.liferay.portal.tools.service.builder.test.compat740.model.
					TrashEntry trashEntry) {

		return _trashEntryLocalService.addTrashEntry(trashEntry);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _trashEntryLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Creates a new trash entry with the primary key. Does not add the trash entry to the database.
	 *
	 * @param trashEntryId the primary key for the new trash entry
	 * @return the new trash entry
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.compat740.model.TrashEntry
			createTrashEntry(long trashEntryId) {

		return _trashEntryLocalService.createTrashEntry(trashEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _trashEntryLocalService.deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the trash entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect TrashEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param trashEntryId the primary key of the trash entry
	 * @return the trash entry that was removed
	 * @throws PortalException if a trash entry with the primary key could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.compat740.model.TrashEntry
				deleteTrashEntry(long trashEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _trashEntryLocalService.deleteTrashEntry(trashEntryId);
	}

	/**
	 * Deletes the trash entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect TrashEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param trashEntry the trash entry
	 * @return the trash entry that was removed
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.compat740.model.TrashEntry
			deleteTrashEntry(
				com.liferay.portal.tools.service.builder.test.compat740.model.
					TrashEntry trashEntry) {

		return _trashEntryLocalService.deleteTrashEntry(trashEntry);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _trashEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _trashEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _trashEntryLocalService.dynamicQuery();
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

		return _trashEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat740.model.impl.TrashEntryModelImpl</code>.
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

		return _trashEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat740.model.impl.TrashEntryModelImpl</code>.
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

		return _trashEntryLocalService.dynamicQuery(
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

		return _trashEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _trashEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public
		com.liferay.portal.tools.service.builder.test.compat740.model.TrashEntry
			fetchTrashEntry(long trashEntryId) {

		return _trashEntryLocalService.fetchTrashEntry(trashEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _trashEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _trashEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _trashEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _trashEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns a range of all the trash entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat740.model.impl.TrashEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of trash entries
	 * @param end the upper bound of the range of trash entries (not inclusive)
	 * @return the range of trash entries
	 */
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.compat740.model.
			TrashEntry> getTrashEntries(int start, int end) {

		return _trashEntryLocalService.getTrashEntries(start, end);
	}

	/**
	 * Returns the number of trash entries.
	 *
	 * @return the number of trash entries
	 */
	@Override
	public int getTrashEntriesCount() {
		return _trashEntryLocalService.getTrashEntriesCount();
	}

	/**
	 * Returns the trash entry with the primary key.
	 *
	 * @param trashEntryId the primary key of the trash entry
	 * @return the trash entry
	 * @throws PortalException if a trash entry with the primary key could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.compat740.model.TrashEntry
				getTrashEntry(long trashEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _trashEntryLocalService.getTrashEntry(trashEntryId);
	}

	/**
	 * Updates the trash entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect TrashEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param trashEntry the trash entry
	 * @return the trash entry that was updated
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.compat740.model.TrashEntry
			updateTrashEntry(
				com.liferay.portal.tools.service.builder.test.compat740.model.
					TrashEntry trashEntry) {

		return _trashEntryLocalService.updateTrashEntry(trashEntry);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _trashEntryLocalService.getBasePersistence();
	}

	@Override
	public TrashEntryLocalService getWrappedService() {
		return _trashEntryLocalService;
	}

	@Override
	public void setWrappedService(
		TrashEntryLocalService trashEntryLocalService) {

		_trashEntryLocalService = trashEntryLocalService;
	}

	private TrashEntryLocalService _trashEntryLocalService;

}
// SB-Hash:-79596932