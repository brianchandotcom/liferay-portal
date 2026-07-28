/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link TimestampTypeEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see TimestampTypeEntryLocalService
 * @generated
 */
public class TimestampTypeEntryLocalServiceWrapper
	implements ServiceWrapper<TimestampTypeEntryLocalService>,
			   TimestampTypeEntryLocalService {

	public TimestampTypeEntryLocalServiceWrapper() {
		this(null);
	}

	public TimestampTypeEntryLocalServiceWrapper(
		TimestampTypeEntryLocalService timestampTypeEntryLocalService) {

		_timestampTypeEntryLocalService = timestampTypeEntryLocalService;
	}

	/**
	 * Adds the timestamp type entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect TimestampTypeEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param timestampTypeEntry the timestamp type entry
	 * @return the timestamp type entry that was added
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.TimestampTypeEntry
			addTimestampTypeEntry(
				com.liferay.portal.tools.service.builder.test.model.
					TimestampTypeEntry timestampTypeEntry) {

		return _timestampTypeEntryLocalService.addTimestampTypeEntry(
			timestampTypeEntry);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _timestampTypeEntryLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Creates a new timestamp type entry with the primary key. Does not add the timestamp type entry to the database.
	 *
	 * @param timestampTypeEntryId the primary key for the new timestamp type entry
	 * @return the new timestamp type entry
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.TimestampTypeEntry
			createTimestampTypeEntry(long timestampTypeEntryId) {

		return _timestampTypeEntryLocalService.createTimestampTypeEntry(
			timestampTypeEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _timestampTypeEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	/**
	 * Deletes the timestamp type entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect TimestampTypeEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param timestampTypeEntryId the primary key of the timestamp type entry
	 * @return the timestamp type entry that was removed
	 * @throws PortalException if a timestamp type entry with the primary key could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.TimestampTypeEntry
				deleteTimestampTypeEntry(long timestampTypeEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _timestampTypeEntryLocalService.deleteTimestampTypeEntry(
			timestampTypeEntryId);
	}

	/**
	 * Deletes the timestamp type entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect TimestampTypeEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param timestampTypeEntry the timestamp type entry
	 * @return the timestamp type entry that was removed
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.TimestampTypeEntry
			deleteTimestampTypeEntry(
				com.liferay.portal.tools.service.builder.test.model.
					TimestampTypeEntry timestampTypeEntry) {

		return _timestampTypeEntryLocalService.deleteTimestampTypeEntry(
			timestampTypeEntry);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _timestampTypeEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _timestampTypeEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _timestampTypeEntryLocalService.dynamicQuery();
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

		return _timestampTypeEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.TimestampTypeEntryModelImpl</code>.
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

		return _timestampTypeEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.TimestampTypeEntryModelImpl</code>.
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

		return _timestampTypeEntryLocalService.dynamicQuery(
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

		return _timestampTypeEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _timestampTypeEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.TimestampTypeEntry
			fetchTimestampTypeEntry(long timestampTypeEntryId) {

		return _timestampTypeEntryLocalService.fetchTimestampTypeEntry(
			timestampTypeEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _timestampTypeEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _timestampTypeEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _timestampTypeEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _timestampTypeEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns a range of all the timestamp type entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.TimestampTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of timestamp type entries
	 * @param end the upper bound of the range of timestamp type entries (not inclusive)
	 * @return the range of timestamp type entries
	 */
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.TimestampTypeEntry>
			getTimestampTypeEntries(int start, int end) {

		return _timestampTypeEntryLocalService.getTimestampTypeEntries(
			start, end);
	}

	/**
	 * Returns the number of timestamp type entries.
	 *
	 * @return the number of timestamp type entries
	 */
	@Override
	public int getTimestampTypeEntriesCount() {
		return _timestampTypeEntryLocalService.getTimestampTypeEntriesCount();
	}

	/**
	 * Returns the timestamp type entry with the primary key.
	 *
	 * @param timestampTypeEntryId the primary key of the timestamp type entry
	 * @return the timestamp type entry
	 * @throws PortalException if a timestamp type entry with the primary key could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.TimestampTypeEntry
				getTimestampTypeEntry(long timestampTypeEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _timestampTypeEntryLocalService.getTimestampTypeEntry(
			timestampTypeEntryId);
	}

	/**
	 * Updates the timestamp type entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect TimestampTypeEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param timestampTypeEntry the timestamp type entry
	 * @return the timestamp type entry that was updated
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.TimestampTypeEntry
			updateTimestampTypeEntry(
				com.liferay.portal.tools.service.builder.test.model.
					TimestampTypeEntry timestampTypeEntry) {

		return _timestampTypeEntryLocalService.updateTimestampTypeEntry(
			timestampTypeEntry);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _timestampTypeEntryLocalService.getBasePersistence();
	}

	@Override
	public TimestampTypeEntryLocalService getWrappedService() {
		return _timestampTypeEntryLocalService;
	}

	@Override
	public void setWrappedService(
		TimestampTypeEntryLocalService timestampTypeEntryLocalService) {

		_timestampTypeEntryLocalService = timestampTypeEntryLocalService;
	}

	private TimestampTypeEntryLocalService _timestampTypeEntryLocalService;

}
// LIFERAY-SERVICE-BUILDER-HASH:-1951392037