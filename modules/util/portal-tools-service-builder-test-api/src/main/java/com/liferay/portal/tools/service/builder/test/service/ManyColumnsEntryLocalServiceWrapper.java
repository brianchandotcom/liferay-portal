/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link ManyColumnsEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ManyColumnsEntryLocalService
 * @generated
 */
public class ManyColumnsEntryLocalServiceWrapper
	implements ManyColumnsEntryLocalService,
			   ServiceWrapper<ManyColumnsEntryLocalService> {

	public ManyColumnsEntryLocalServiceWrapper() {
		this(null);
	}

	public ManyColumnsEntryLocalServiceWrapper(
		ManyColumnsEntryLocalService manyColumnsEntryLocalService) {

		_manyColumnsEntryLocalService = manyColumnsEntryLocalService;
	}

	/**
	 * Adds the many columns entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ManyColumnsEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param manyColumnsEntry the many columns entry
	 * @return the many columns entry that was added
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.ManyColumnsEntry
		addManyColumnsEntry(
			com.liferay.portal.tools.service.builder.test.model.ManyColumnsEntry
				manyColumnsEntry) {

		return _manyColumnsEntryLocalService.addManyColumnsEntry(
			manyColumnsEntry);
	}

	/**
	 * Creates a new many columns entry with the primary key. Does not add the many columns entry to the database.
	 *
	 * @param manyColumnsEntryId the primary key for the new many columns entry
	 * @return the new many columns entry
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.ManyColumnsEntry
		createManyColumnsEntry(long manyColumnsEntryId) {

		return _manyColumnsEntryLocalService.createManyColumnsEntry(
			manyColumnsEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _manyColumnsEntryLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the many columns entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ManyColumnsEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param manyColumnsEntryId the primary key of the many columns entry
	 * @return the many columns entry that was removed
	 * @throws PortalException if a many columns entry with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.ManyColumnsEntry
			deleteManyColumnsEntry(long manyColumnsEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _manyColumnsEntryLocalService.deleteManyColumnsEntry(
			manyColumnsEntryId);
	}

	/**
	 * Deletes the many columns entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ManyColumnsEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param manyColumnsEntry the many columns entry
	 * @return the many columns entry that was removed
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.ManyColumnsEntry
		deleteManyColumnsEntry(
			com.liferay.portal.tools.service.builder.test.model.ManyColumnsEntry
				manyColumnsEntry) {

		return _manyColumnsEntryLocalService.deleteManyColumnsEntry(
			manyColumnsEntry);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _manyColumnsEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _manyColumnsEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _manyColumnsEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _manyColumnsEntryLocalService.dynamicQuery();
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

		return _manyColumnsEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.ManyColumnsEntryModelImpl</code>.
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

		return _manyColumnsEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.ManyColumnsEntryModelImpl</code>.
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

		return _manyColumnsEntryLocalService.dynamicQuery(
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

		return _manyColumnsEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _manyColumnsEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.ManyColumnsEntry
		fetchManyColumnsEntry(long manyColumnsEntryId) {

		return _manyColumnsEntryLocalService.fetchManyColumnsEntry(
			manyColumnsEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _manyColumnsEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _manyColumnsEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the many columns entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.ManyColumnsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of many columns entries
	 * @param end the upper bound of the range of many columns entries (not inclusive)
	 * @return the range of many columns entries
	 */
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.ManyColumnsEntry>
			getManyColumnsEntries(int start, int end) {

		return _manyColumnsEntryLocalService.getManyColumnsEntries(start, end);
	}

	/**
	 * Returns the number of many columns entries.
	 *
	 * @return the number of many columns entries
	 */
	@Override
	public int getManyColumnsEntriesCount() {
		return _manyColumnsEntryLocalService.getManyColumnsEntriesCount();
	}

	/**
	 * Returns the many columns entry with the primary key.
	 *
	 * @param manyColumnsEntryId the primary key of the many columns entry
	 * @return the many columns entry
	 * @throws PortalException if a many columns entry with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.ManyColumnsEntry
			getManyColumnsEntry(long manyColumnsEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _manyColumnsEntryLocalService.getManyColumnsEntry(
			manyColumnsEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _manyColumnsEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _manyColumnsEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the many columns entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ManyColumnsEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param manyColumnsEntry the many columns entry
	 * @return the many columns entry that was updated
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.ManyColumnsEntry
		updateManyColumnsEntry(
			com.liferay.portal.tools.service.builder.test.model.ManyColumnsEntry
				manyColumnsEntry) {

		return _manyColumnsEntryLocalService.updateManyColumnsEntry(
			manyColumnsEntry);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _manyColumnsEntryLocalService.getBasePersistence();
	}

	@Override
	public ManyColumnsEntryLocalService getWrappedService() {
		return _manyColumnsEntryLocalService;
	}

	@Override
	public void setWrappedService(
		ManyColumnsEntryLocalService manyColumnsEntryLocalService) {

		_manyColumnsEntryLocalService = manyColumnsEntryLocalService;
	}

	private ManyColumnsEntryLocalService _manyColumnsEntryLocalService;

}