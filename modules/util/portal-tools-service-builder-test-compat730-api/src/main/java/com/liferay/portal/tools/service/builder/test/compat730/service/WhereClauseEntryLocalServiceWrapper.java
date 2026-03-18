/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat730.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link WhereClauseEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see WhereClauseEntryLocalService
 * @generated
 */
public class WhereClauseEntryLocalServiceWrapper
	implements ServiceWrapper<WhereClauseEntryLocalService>,
			   WhereClauseEntryLocalService {

	public WhereClauseEntryLocalServiceWrapper(
		WhereClauseEntryLocalService whereClauseEntryLocalService) {

		_whereClauseEntryLocalService = whereClauseEntryLocalService;
	}

	/**
	 * Adds the where clause entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect WhereClauseEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param whereClauseEntry the where clause entry
	 * @return the where clause entry that was added
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.compat730.model.
		WhereClauseEntry addWhereClauseEntry(
			com.liferay.portal.tools.service.builder.test.compat730.model.
				WhereClauseEntry whereClauseEntry) {

		return _whereClauseEntryLocalService.addWhereClauseEntry(
			whereClauseEntry);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _whereClauseEntryLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Creates a new where clause entry with the primary key. Does not add the where clause entry to the database.
	 *
	 * @param whereClauseEntryId the primary key for the new where clause entry
	 * @return the new where clause entry
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.compat730.model.
		WhereClauseEntry createWhereClauseEntry(long whereClauseEntryId) {

		return _whereClauseEntryLocalService.createWhereClauseEntry(
			whereClauseEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _whereClauseEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	/**
	 * Deletes the where clause entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect WhereClauseEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param whereClauseEntryId the primary key of the where clause entry
	 * @return the where clause entry that was removed
	 * @throws PortalException if a where clause entry with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.compat730.model.
		WhereClauseEntry deleteWhereClauseEntry(long whereClauseEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _whereClauseEntryLocalService.deleteWhereClauseEntry(
			whereClauseEntryId);
	}

	/**
	 * Deletes the where clause entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect WhereClauseEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param whereClauseEntry the where clause entry
	 * @return the where clause entry that was removed
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.compat730.model.
		WhereClauseEntry deleteWhereClauseEntry(
			com.liferay.portal.tools.service.builder.test.compat730.model.
				WhereClauseEntry whereClauseEntry) {

		return _whereClauseEntryLocalService.deleteWhereClauseEntry(
			whereClauseEntry);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _whereClauseEntryLocalService.dynamicQuery();
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

		return _whereClauseEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat730.model.impl.WhereClauseEntryModelImpl</code>.
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

		return _whereClauseEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat730.model.impl.WhereClauseEntryModelImpl</code>.
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

		return _whereClauseEntryLocalService.dynamicQuery(
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

		return _whereClauseEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _whereClauseEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.compat730.model.
		WhereClauseEntry fetchWhereClauseEntry(long whereClauseEntryId) {

		return _whereClauseEntryLocalService.fetchWhereClauseEntry(
			whereClauseEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _whereClauseEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _whereClauseEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _whereClauseEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _whereClauseEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns a range of all the where clause entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat730.model.impl.WhereClauseEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of where clause entries
	 * @param end the upper bound of the range of where clause entries (not inclusive)
	 * @return the range of where clause entries
	 */
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.compat730.model.
			WhereClauseEntry> getWhereClauseEntries(int start, int end) {

		return _whereClauseEntryLocalService.getWhereClauseEntries(start, end);
	}

	/**
	 * Returns the number of where clause entries.
	 *
	 * @return the number of where clause entries
	 */
	@Override
	public int getWhereClauseEntriesCount() {
		return _whereClauseEntryLocalService.getWhereClauseEntriesCount();
	}

	/**
	 * Returns the where clause entry with the primary key.
	 *
	 * @param whereClauseEntryId the primary key of the where clause entry
	 * @return the where clause entry
	 * @throws PortalException if a where clause entry with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.compat730.model.
		WhereClauseEntry getWhereClauseEntry(long whereClauseEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _whereClauseEntryLocalService.getWhereClauseEntry(
			whereClauseEntryId);
	}

	/**
	 * Updates the where clause entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect WhereClauseEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param whereClauseEntry the where clause entry
	 * @return the where clause entry that was updated
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.compat730.model.
		WhereClauseEntry updateWhereClauseEntry(
			com.liferay.portal.tools.service.builder.test.compat730.model.
				WhereClauseEntry whereClauseEntry) {

		return _whereClauseEntryLocalService.updateWhereClauseEntry(
			whereClauseEntry);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _whereClauseEntryLocalService.getBasePersistence();
	}

	@Override
	public WhereClauseEntryLocalService getWrappedService() {
		return _whereClauseEntryLocalService;
	}

	@Override
	public void setWrappedService(
		WhereClauseEntryLocalService whereClauseEntryLocalService) {

		_whereClauseEntryLocalService = whereClauseEntryLocalService;
	}

	private WhereClauseEntryLocalService _whereClauseEntryLocalService;

}
// LIFERAY-SERVICE-BUILDER-HASH:1162177159