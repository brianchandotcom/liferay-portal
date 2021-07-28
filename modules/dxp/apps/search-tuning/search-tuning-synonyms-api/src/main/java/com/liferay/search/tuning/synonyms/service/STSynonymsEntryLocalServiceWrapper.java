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

package com.liferay.search.tuning.synonyms.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link STSynonymsEntryLocalService}.
 *
 * @author Bryan Engler
 * @see STSynonymsEntryLocalService
 * @generated
 */
public class STSynonymsEntryLocalServiceWrapper
	implements ServiceWrapper<STSynonymsEntryLocalService>,
			   STSynonymsEntryLocalService {

	public STSynonymsEntryLocalServiceWrapper(
		STSynonymsEntryLocalService stSynonymsEntryLocalService) {

		_stSynonymsEntryLocalService = stSynonymsEntryLocalService;
	}

	@Override
	public com.liferay.search.tuning.synonyms.model.STSynonymsEntry
		addSTSynonymsEntry(String indexName, String synonyms) {

		return _stSynonymsEntryLocalService.addSTSynonymsEntry(
			indexName, synonyms);
	}

	/**
	 * Adds the st synonyms entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect STSynonymsEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param stSynonymsEntry the st synonyms entry
	 * @return the st synonyms entry that was added
	 */
	@Override
	public com.liferay.search.tuning.synonyms.model.STSynonymsEntry
		addSTSynonymsEntry(
			com.liferay.search.tuning.synonyms.model.STSynonymsEntry
				stSynonymsEntry) {

		return _stSynonymsEntryLocalService.addSTSynonymsEntry(stSynonymsEntry);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _stSynonymsEntryLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Creates a new st synonyms entry with the primary key. Does not add the st synonyms entry to the database.
	 *
	 * @param STSynonymsEntryId the primary key for the new st synonyms entry
	 * @return the new st synonyms entry
	 */
	@Override
	public com.liferay.search.tuning.synonyms.model.STSynonymsEntry
		createSTSynonymsEntry(long STSynonymsEntryId) {

		return _stSynonymsEntryLocalService.createSTSynonymsEntry(
			STSynonymsEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _stSynonymsEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	/**
	 * Deletes the st synonyms entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect STSynonymsEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param STSynonymsEntryId the primary key of the st synonyms entry
	 * @return the st synonyms entry that was removed
	 * @throws PortalException if a st synonyms entry with the primary key could not be found
	 */
	@Override
	public com.liferay.search.tuning.synonyms.model.STSynonymsEntry
			deleteSTSynonymsEntry(long STSynonymsEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _stSynonymsEntryLocalService.deleteSTSynonymsEntry(
			STSynonymsEntryId);
	}

	/**
	 * Deletes the st synonyms entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect STSynonymsEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param stSynonymsEntry the st synonyms entry
	 * @return the st synonyms entry that was removed
	 */
	@Override
	public com.liferay.search.tuning.synonyms.model.STSynonymsEntry
		deleteSTSynonymsEntry(
			com.liferay.search.tuning.synonyms.model.STSynonymsEntry
				stSynonymsEntry) {

		return _stSynonymsEntryLocalService.deleteSTSynonymsEntry(
			stSynonymsEntry);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _stSynonymsEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _stSynonymsEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _stSynonymsEntryLocalService.dynamicQuery();
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

		return _stSynonymsEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.search.tuning.synonyms.model.impl.STSynonymsEntryModelImpl</code>.
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

		return _stSynonymsEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.search.tuning.synonyms.model.impl.STSynonymsEntryModelImpl</code>.
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

		return _stSynonymsEntryLocalService.dynamicQuery(
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

		return _stSynonymsEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _stSynonymsEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.search.tuning.synonyms.model.STSynonymsEntry
		fetchSTSynonymsEntry(long STSynonymsEntryId) {

		return _stSynonymsEntryLocalService.fetchSTSynonymsEntry(
			STSynonymsEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _stSynonymsEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _stSynonymsEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _stSynonymsEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _stSynonymsEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns a range of all the st synonyms entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.search.tuning.synonyms.model.impl.STSynonymsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of st synonyms entries
	 * @param end the upper bound of the range of st synonyms entries (not inclusive)
	 * @return the range of st synonyms entries
	 */
	@Override
	public java.util.List
		<com.liferay.search.tuning.synonyms.model.STSynonymsEntry>
			getSTSynonymsEntries(int start, int end) {

		return _stSynonymsEntryLocalService.getSTSynonymsEntries(start, end);
	}

	@Override
	public java.util.List
		<com.liferay.search.tuning.synonyms.model.STSynonymsEntry>
			getSTSynonymsEntriesByCompanyId(long companyId) {

		return _stSynonymsEntryLocalService.getSTSynonymsEntriesByCompanyId(
			companyId);
	}

	/**
	 * Returns the number of st synonyms entries.
	 *
	 * @return the number of st synonyms entries
	 */
	@Override
	public int getSTSynonymsEntriesCount() {
		return _stSynonymsEntryLocalService.getSTSynonymsEntriesCount();
	}

	/**
	 * Returns the st synonyms entry with the primary key.
	 *
	 * @param STSynonymsEntryId the primary key of the st synonyms entry
	 * @return the st synonyms entry
	 * @throws PortalException if a st synonyms entry with the primary key could not be found
	 */
	@Override
	public com.liferay.search.tuning.synonyms.model.STSynonymsEntry
			getSTSynonymsEntry(long STSynonymsEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _stSynonymsEntryLocalService.getSTSynonymsEntry(
			STSynonymsEntryId);
	}

	@Override
	public com.liferay.search.tuning.synonyms.model.STSynonymsEntry
			updateSTSynonymsEntry(long stSynonymsEntryId, String synonyms)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _stSynonymsEntryLocalService.updateSTSynonymsEntry(
			stSynonymsEntryId, synonyms);
	}

	/**
	 * Updates the st synonyms entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect STSynonymsEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param stSynonymsEntry the st synonyms entry
	 * @return the st synonyms entry that was updated
	 */
	@Override
	public com.liferay.search.tuning.synonyms.model.STSynonymsEntry
		updateSTSynonymsEntry(
			com.liferay.search.tuning.synonyms.model.STSynonymsEntry
				stSynonymsEntry) {

		return _stSynonymsEntryLocalService.updateSTSynonymsEntry(
			stSynonymsEntry);
	}

	@Override
	public STSynonymsEntryLocalService getWrappedService() {
		return _stSynonymsEntryLocalService;
	}

	@Override
	public void setWrappedService(
		STSynonymsEntryLocalService stSynonymsEntryLocalService) {

		_stSynonymsEntryLocalService = stSynonymsEntryLocalService;
	}

	private STSynonymsEntryLocalService _stSynonymsEntryLocalService;

}