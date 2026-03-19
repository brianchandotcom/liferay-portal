/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link UndefinedDefaultOrderEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see UndefinedDefaultOrderEntryLocalService
 * @generated
 */
public class UndefinedDefaultOrderEntryLocalServiceWrapper
	implements ServiceWrapper<UndefinedDefaultOrderEntryLocalService>,
			   UndefinedDefaultOrderEntryLocalService {

	public UndefinedDefaultOrderEntryLocalServiceWrapper() {
		this(null);
	}

	public UndefinedDefaultOrderEntryLocalServiceWrapper(
		UndefinedDefaultOrderEntryLocalService
			undefinedDefaultOrderEntryLocalService) {

		_undefinedDefaultOrderEntryLocalService =
			undefinedDefaultOrderEntryLocalService;
	}

	/**
	 * Adds the undefined default order entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect UndefinedDefaultOrderEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param undefinedDefaultOrderEntry the undefined default order entry
	 * @return the undefined default order entry that was added
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.
		UndefinedDefaultOrderEntry addUndefinedDefaultOrderEntry(
			com.liferay.portal.tools.service.builder.test.model.
				UndefinedDefaultOrderEntry undefinedDefaultOrderEntry) {

		return _undefinedDefaultOrderEntryLocalService.
			addUndefinedDefaultOrderEntry(undefinedDefaultOrderEntry);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _undefinedDefaultOrderEntryLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Creates a new undefined default order entry with the primary key. Does not add the undefined default order entry to the database.
	 *
	 * @param undefinedDefaultOrderEntryId the primary key for the new undefined default order entry
	 * @return the new undefined default order entry
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.
		UndefinedDefaultOrderEntry createUndefinedDefaultOrderEntry(
			long undefinedDefaultOrderEntryId) {

		return _undefinedDefaultOrderEntryLocalService.
			createUndefinedDefaultOrderEntry(undefinedDefaultOrderEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _undefinedDefaultOrderEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	/**
	 * Deletes the undefined default order entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect UndefinedDefaultOrderEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param undefinedDefaultOrderEntryId the primary key of the undefined default order entry
	 * @return the undefined default order entry that was removed
	 * @throws PortalException if a undefined default order entry with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.
		UndefinedDefaultOrderEntry deleteUndefinedDefaultOrderEntry(
				long undefinedDefaultOrderEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _undefinedDefaultOrderEntryLocalService.
			deleteUndefinedDefaultOrderEntry(undefinedDefaultOrderEntryId);
	}

	/**
	 * Deletes the undefined default order entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect UndefinedDefaultOrderEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param undefinedDefaultOrderEntry the undefined default order entry
	 * @return the undefined default order entry that was removed
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.
		UndefinedDefaultOrderEntry deleteUndefinedDefaultOrderEntry(
			com.liferay.portal.tools.service.builder.test.model.
				UndefinedDefaultOrderEntry undefinedDefaultOrderEntry) {

		return _undefinedDefaultOrderEntryLocalService.
			deleteUndefinedDefaultOrderEntry(undefinedDefaultOrderEntry);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _undefinedDefaultOrderEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _undefinedDefaultOrderEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _undefinedDefaultOrderEntryLocalService.dynamicQuery();
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

		return _undefinedDefaultOrderEntryLocalService.dynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.UndefinedDefaultOrderEntryModelImpl</code>.
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

		return _undefinedDefaultOrderEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.UndefinedDefaultOrderEntryModelImpl</code>.
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

		return _undefinedDefaultOrderEntryLocalService.dynamicQuery(
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

		return _undefinedDefaultOrderEntryLocalService.dynamicQueryCount(
			dynamicQuery);
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

		return _undefinedDefaultOrderEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.
		UndefinedDefaultOrderEntry fetchUndefinedDefaultOrderEntry(
			long undefinedDefaultOrderEntryId) {

		return _undefinedDefaultOrderEntryLocalService.
			fetchUndefinedDefaultOrderEntry(undefinedDefaultOrderEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _undefinedDefaultOrderEntryLocalService.
			getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _undefinedDefaultOrderEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _undefinedDefaultOrderEntryLocalService.
			getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _undefinedDefaultOrderEntryLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Returns a range of all the undefined default order entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.UndefinedDefaultOrderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of undefined default order entries
	 * @param end the upper bound of the range of undefined default order entries (not inclusive)
	 * @return the range of undefined default order entries
	 */
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.
			UndefinedDefaultOrderEntry> getUndefinedDefaultOrderEntries(
				int start, int end) {

		return _undefinedDefaultOrderEntryLocalService.
			getUndefinedDefaultOrderEntries(start, end);
	}

	/**
	 * Returns the number of undefined default order entries.
	 *
	 * @return the number of undefined default order entries
	 */
	@Override
	public int getUndefinedDefaultOrderEntriesCount() {
		return _undefinedDefaultOrderEntryLocalService.
			getUndefinedDefaultOrderEntriesCount();
	}

	/**
	 * Returns the undefined default order entry with the primary key.
	 *
	 * @param undefinedDefaultOrderEntryId the primary key of the undefined default order entry
	 * @return the undefined default order entry
	 * @throws PortalException if a undefined default order entry with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.
		UndefinedDefaultOrderEntry getUndefinedDefaultOrderEntry(
				long undefinedDefaultOrderEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _undefinedDefaultOrderEntryLocalService.
			getUndefinedDefaultOrderEntry(undefinedDefaultOrderEntryId);
	}

	/**
	 * Updates the undefined default order entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect UndefinedDefaultOrderEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param undefinedDefaultOrderEntry the undefined default order entry
	 * @return the undefined default order entry that was updated
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.
		UndefinedDefaultOrderEntry updateUndefinedDefaultOrderEntry(
			com.liferay.portal.tools.service.builder.test.model.
				UndefinedDefaultOrderEntry undefinedDefaultOrderEntry) {

		return _undefinedDefaultOrderEntryLocalService.
			updateUndefinedDefaultOrderEntry(undefinedDefaultOrderEntry);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _undefinedDefaultOrderEntryLocalService.getBasePersistence();
	}

	@Override
	public UndefinedDefaultOrderEntryLocalService getWrappedService() {
		return _undefinedDefaultOrderEntryLocalService;
	}

	@Override
	public void setWrappedService(
		UndefinedDefaultOrderEntryLocalService
			undefinedDefaultOrderEntryLocalService) {

		_undefinedDefaultOrderEntryLocalService =
			undefinedDefaultOrderEntryLocalService;
	}

	private UndefinedDefaultOrderEntryLocalService
		_undefinedDefaultOrderEntryLocalService;

}
// LIFERAY-SERVICE-BUILDER-HASH:433670254