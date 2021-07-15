/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.tuning.rankings.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link STRankingsEntryLocalService}.
 *
 * @author Bryan Engler
 * @see STRankingsEntryLocalService
 * @generated
 */
public class STRankingsEntryLocalServiceWrapper
	implements ServiceWrapper<STRankingsEntryLocalService>,
			   STRankingsEntryLocalService {

	public STRankingsEntryLocalServiceWrapper(
		STRankingsEntryLocalService stRankingsEntryLocalService) {

		_stRankingsEntryLocalService = stRankingsEntryLocalService;
	}

	/**
	 * Adds the st rankings entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect STRankingsEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param stRankingsEntry the st rankings entry
	 * @return the st rankings entry that was added
	 */
	@Override
	public com.liferay.search.tuning.rankings.model.STRankingsEntry
		addSTRankingsEntry(
			com.liferay.search.tuning.rankings.model.STRankingsEntry
				stRankingsEntry) {

		return _stRankingsEntryLocalService.addSTRankingsEntry(stRankingsEntry);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _stRankingsEntryLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Creates a new st rankings entry with the primary key. Does not add the st rankings entry to the database.
	 *
	 * @param STRankingsEntryId the primary key for the new st rankings entry
	 * @return the new st rankings entry
	 */
	@Override
	public com.liferay.search.tuning.rankings.model.STRankingsEntry
		createSTRankingsEntry(long STRankingsEntryId) {

		return _stRankingsEntryLocalService.createSTRankingsEntry(
			STRankingsEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _stRankingsEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	/**
	 * Deletes the st rankings entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect STRankingsEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param STRankingsEntryId the primary key of the st rankings entry
	 * @return the st rankings entry that was removed
	 * @throws PortalException if a st rankings entry with the primary key could not be found
	 */
	@Override
	public com.liferay.search.tuning.rankings.model.STRankingsEntry
			deleteSTRankingsEntry(long STRankingsEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _stRankingsEntryLocalService.deleteSTRankingsEntry(
			STRankingsEntryId);
	}

	/**
	 * Deletes the st rankings entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect STRankingsEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param stRankingsEntry the st rankings entry
	 * @return the st rankings entry that was removed
	 */
	@Override
	public com.liferay.search.tuning.rankings.model.STRankingsEntry
		deleteSTRankingsEntry(
			com.liferay.search.tuning.rankings.model.STRankingsEntry
				stRankingsEntry) {

		return _stRankingsEntryLocalService.deleteSTRankingsEntry(
			stRankingsEntry);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _stRankingsEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _stRankingsEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _stRankingsEntryLocalService.dynamicQuery();
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

		return _stRankingsEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.search.tuning.rankings.model.impl.STRankingsEntryModelImpl</code>.
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

		return _stRankingsEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.search.tuning.rankings.model.impl.STRankingsEntryModelImpl</code>.
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

		return _stRankingsEntryLocalService.dynamicQuery(
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

		return _stRankingsEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _stRankingsEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.search.tuning.rankings.model.STRankingsEntry
		fetchSTRankingsEntry(long STRankingsEntryId) {

		return _stRankingsEntryLocalService.fetchSTRankingsEntry(
			STRankingsEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _stRankingsEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _stRankingsEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _stRankingsEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _stRankingsEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns a range of all the st rankings entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.search.tuning.rankings.model.impl.STRankingsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of st rankings entries
	 * @param end the upper bound of the range of st rankings entries (not inclusive)
	 * @return the range of st rankings entries
	 */
	@Override
	public java.util.List
		<com.liferay.search.tuning.rankings.model.STRankingsEntry>
			getSTRankingsEntries(int start, int end) {

		return _stRankingsEntryLocalService.getSTRankingsEntries(start, end);
	}

	/**
	 * Returns the number of st rankings entries.
	 *
	 * @return the number of st rankings entries
	 */
	@Override
	public int getSTRankingsEntriesCount() {
		return _stRankingsEntryLocalService.getSTRankingsEntriesCount();
	}

	/**
	 * Returns the st rankings entry with the primary key.
	 *
	 * @param STRankingsEntryId the primary key of the st rankings entry
	 * @return the st rankings entry
	 * @throws PortalException if a st rankings entry with the primary key could not be found
	 */
	@Override
	public com.liferay.search.tuning.rankings.model.STRankingsEntry
			getSTRankingsEntry(long STRankingsEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _stRankingsEntryLocalService.getSTRankingsEntry(
			STRankingsEntryId);
	}

	/**
	 * Updates the st rankings entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect STRankingsEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param stRankingsEntry the st rankings entry
	 * @return the st rankings entry that was updated
	 */
	@Override
	public com.liferay.search.tuning.rankings.model.STRankingsEntry
		updateSTRankingsEntry(
			com.liferay.search.tuning.rankings.model.STRankingsEntry
				stRankingsEntry) {

		return _stRankingsEntryLocalService.updateSTRankingsEntry(
			stRankingsEntry);
	}

	@Override
	public STRankingsEntryLocalService getWrappedService() {
		return _stRankingsEntryLocalService;
	}

	@Override
	public void setWrappedService(
		STRankingsEntryLocalService stRankingsEntryLocalService) {

		_stRankingsEntryLocalService = stRankingsEntryLocalService;
	}

	private STRankingsEntryLocalService _stRankingsEntryLocalService;

}