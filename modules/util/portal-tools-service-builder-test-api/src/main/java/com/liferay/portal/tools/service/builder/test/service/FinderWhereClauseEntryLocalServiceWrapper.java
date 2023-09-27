/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link FinderWhereClauseEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see FinderWhereClauseEntryLocalService
 * @generated
 */
public class FinderWhereClauseEntryLocalServiceWrapper
	implements FinderWhereClauseEntryLocalService,
			   ServiceWrapper<FinderWhereClauseEntryLocalService> {

	public FinderWhereClauseEntryLocalServiceWrapper() {
		this(null);
	}

	public FinderWhereClauseEntryLocalServiceWrapper(
		FinderWhereClauseEntryLocalService finderWhereClauseEntryLocalService) {

		_finderWhereClauseEntryLocalService =
			finderWhereClauseEntryLocalService;
	}

	/**
	 * Adds the finder where clause entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FinderWhereClauseEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param finderWhereClauseEntry the finder where clause entry
	 * @return the finder where clause entry that was added
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.
			FinderWhereClauseEntry addFinderWhereClauseEntry(
				com.liferay.portal.tools.service.builder.test.model.
					FinderWhereClauseEntry finderWhereClauseEntry) {

		return _finderWhereClauseEntryLocalService.addFinderWhereClauseEntry(
			finderWhereClauseEntry);
	}

	/**
	 * Creates a new finder where clause entry with the primary key. Does not add the finder where clause entry to the database.
	 *
	 * @param finderWhereClauseEntryId the primary key for the new finder where clause entry
	 * @return the new finder where clause entry
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.
			FinderWhereClauseEntry createFinderWhereClauseEntry(
				long finderWhereClauseEntryId) {

		return _finderWhereClauseEntryLocalService.createFinderWhereClauseEntry(
			finderWhereClauseEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _finderWhereClauseEntryLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the finder where clause entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FinderWhereClauseEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param finderWhereClauseEntry the finder where clause entry
	 * @return the finder where clause entry that was removed
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.
			FinderWhereClauseEntry deleteFinderWhereClauseEntry(
				com.liferay.portal.tools.service.builder.test.model.
					FinderWhereClauseEntry finderWhereClauseEntry) {

		return _finderWhereClauseEntryLocalService.deleteFinderWhereClauseEntry(
			finderWhereClauseEntry);
	}

	/**
	 * Deletes the finder where clause entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FinderWhereClauseEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param finderWhereClauseEntryId the primary key of the finder where clause entry
	 * @return the finder where clause entry that was removed
	 * @throws PortalException if a finder where clause entry with the primary key could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.
			FinderWhereClauseEntry deleteFinderWhereClauseEntry(
					long finderWhereClauseEntryId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _finderWhereClauseEntryLocalService.deleteFinderWhereClauseEntry(
			finderWhereClauseEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _finderWhereClauseEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _finderWhereClauseEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _finderWhereClauseEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _finderWhereClauseEntryLocalService.dynamicQuery();
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

		return _finderWhereClauseEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.FinderWhereClauseEntryModelImpl</code>.
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

		return _finderWhereClauseEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.FinderWhereClauseEntryModelImpl</code>.
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

		return _finderWhereClauseEntryLocalService.dynamicQuery(
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

		return _finderWhereClauseEntryLocalService.dynamicQueryCount(
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

		return _finderWhereClauseEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.
			FinderWhereClauseEntry fetchFinderWhereClauseEntry(
				long finderWhereClauseEntryId) {

		return _finderWhereClauseEntryLocalService.fetchFinderWhereClauseEntry(
			finderWhereClauseEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _finderWhereClauseEntryLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the finder where clause entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.FinderWhereClauseEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of finder where clause entries
	 * @param end the upper bound of the range of finder where clause entries (not inclusive)
	 * @return the range of finder where clause entries
	 */
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.
			FinderWhereClauseEntry> getFinderWhereClauseEntries(
				int start, int end) {

		return _finderWhereClauseEntryLocalService.getFinderWhereClauseEntries(
			start, end);
	}

	/**
	 * Returns the number of finder where clause entries.
	 *
	 * @return the number of finder where clause entries
	 */
	@Override
	public int getFinderWhereClauseEntriesCount() {
		return _finderWhereClauseEntryLocalService.
			getFinderWhereClauseEntriesCount();
	}

	/**
	 * Returns the finder where clause entry with the primary key.
	 *
	 * @param finderWhereClauseEntryId the primary key of the finder where clause entry
	 * @return the finder where clause entry
	 * @throws PortalException if a finder where clause entry with the primary key could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.
			FinderWhereClauseEntry getFinderWhereClauseEntry(
					long finderWhereClauseEntryId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _finderWhereClauseEntryLocalService.getFinderWhereClauseEntry(
			finderWhereClauseEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _finderWhereClauseEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _finderWhereClauseEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _finderWhereClauseEntryLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the finder where clause entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FinderWhereClauseEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param finderWhereClauseEntry the finder where clause entry
	 * @return the finder where clause entry that was updated
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.
			FinderWhereClauseEntry updateFinderWhereClauseEntry(
				com.liferay.portal.tools.service.builder.test.model.
					FinderWhereClauseEntry finderWhereClauseEntry) {

		return _finderWhereClauseEntryLocalService.updateFinderWhereClauseEntry(
			finderWhereClauseEntry);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _finderWhereClauseEntryLocalService.getBasePersistence();
	}

	@Override
	public FinderWhereClauseEntryLocalService getWrappedService() {
		return _finderWhereClauseEntryLocalService;
	}

	@Override
	public void setWrappedService(
		FinderWhereClauseEntryLocalService finderWhereClauseEntryLocalService) {

		_finderWhereClauseEntryLocalService =
			finderWhereClauseEntryLocalService;
	}

	private FinderWhereClauseEntryLocalService
		_finderWhereClauseEntryLocalService;

}