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

package com.liferay.portal.search.tuning.rankings.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link RankingLocalService}.
 *
 * @author Bryan Engler
 * @see RankingLocalService
 * @generated
 */
public class RankingLocalServiceWrapper
	implements RankingLocalService, ServiceWrapper<RankingLocalService> {

	public RankingLocalServiceWrapper(RankingLocalService rankingLocalService) {
		_rankingLocalService = rankingLocalService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Use <code>RankingLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>RankingLocalServiceUtil</code>.
	 */
	@Override
	public com.liferay.portal.search.tuning.rankings.model.Ranking addRanking(
		java.util.List<String> aliases,
		java.util.List<String> hiddenDocumentIds, boolean inactive,
		String indexName, String name,
		java.util.Map<Integer, String> documentIdsMap, String queryString) {

		return _rankingLocalService.addRanking(
			aliases, hiddenDocumentIds, inactive, indexName, name,
			documentIdsMap, queryString);
	}

	/**
	 * Adds the ranking to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RankingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ranking the ranking
	 * @return the ranking that was added
	 */
	@Override
	public com.liferay.portal.search.tuning.rankings.model.Ranking addRanking(
		com.liferay.portal.search.tuning.rankings.model.Ranking ranking) {

		return _rankingLocalService.addRanking(ranking);
	}

	@Override
	public com.liferay.portal.search.tuning.rankings.model.Ranking addRanking(
		String indexName, String name, String queryString) {

		return _rankingLocalService.addRanking(indexName, name, queryString);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _rankingLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Creates a new ranking with the primary key. Does not add the ranking to the database.
	 *
	 * @param rankingId the primary key for the new ranking
	 * @return the new ranking
	 */
	@Override
	public com.liferay.portal.search.tuning.rankings.model.Ranking
		createRanking(long rankingId) {

		return _rankingLocalService.createRanking(rankingId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _rankingLocalService.deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the ranking with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RankingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param rankingId the primary key of the ranking
	 * @return the ranking that was removed
	 * @throws PortalException if a ranking with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.search.tuning.rankings.model.Ranking
			deleteRanking(long rankingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _rankingLocalService.deleteRanking(rankingId);
	}

	/**
	 * Deletes the ranking from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RankingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ranking the ranking
	 * @return the ranking that was removed
	 */
	@Override
	public com.liferay.portal.search.tuning.rankings.model.Ranking
		deleteRanking(
			com.liferay.portal.search.tuning.rankings.model.Ranking ranking) {

		return _rankingLocalService.deleteRanking(ranking);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _rankingLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _rankingLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _rankingLocalService.dynamicQuery();
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

		return _rankingLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.search.tuning.rankings.model.impl.RankingModelImpl</code>.
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

		return _rankingLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.search.tuning.rankings.model.impl.RankingModelImpl</code>.
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

		return _rankingLocalService.dynamicQuery(
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

		return _rankingLocalService.dynamicQueryCount(dynamicQuery);
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

		return _rankingLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.search.tuning.rankings.model.Ranking fetchRanking(
		long rankingId) {

		return _rankingLocalService.fetchRanking(rankingId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _rankingLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _rankingLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _rankingLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _rankingLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the ranking with the primary key.
	 *
	 * @param rankingId the primary key of the ranking
	 * @return the ranking
	 * @throws PortalException if a ranking with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.search.tuning.rankings.model.Ranking getRanking(
			long rankingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _rankingLocalService.getRanking(rankingId);
	}

	/**
	 * Returns a range of all the rankings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.search.tuning.rankings.model.impl.RankingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of rankings
	 * @param end the upper bound of the range of rankings (not inclusive)
	 * @return the range of rankings
	 */
	@Override
	public java.util.List
		<com.liferay.portal.search.tuning.rankings.model.Ranking> getRankings(
			int start, int end) {

		return _rankingLocalService.getRankings(start, end);
	}

	@Override
	public java.util.List
		<com.liferay.portal.search.tuning.rankings.model.Ranking>
			getRankingsByCompanyId(long companyId) {

		return _rankingLocalService.getRankingsByCompanyId(companyId);
	}

	/**
	 * Returns the number of rankings.
	 *
	 * @return the number of rankings
	 */
	@Override
	public int getRankingsCount() {
		return _rankingLocalService.getRankingsCount();
	}

	@Override
	public com.liferay.portal.search.tuning.rankings.model.Ranking
			updateRanking(
				long rankingId, java.util.List<String> aliases,
				java.util.List<String> hiddenDocumentIds, boolean inactive,
				String name, java.util.Map<Integer, String> documentIdsMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _rankingLocalService.updateRanking(
			rankingId, aliases, hiddenDocumentIds, inactive, name,
			documentIdsMap);
	}

	/**
	 * Updates the ranking in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RankingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ranking the ranking
	 * @return the ranking that was updated
	 */
	@Override
	public com.liferay.portal.search.tuning.rankings.model.Ranking
		updateRanking(
			com.liferay.portal.search.tuning.rankings.model.Ranking ranking) {

		return _rankingLocalService.updateRanking(ranking);
	}

	@Override
	public RankingLocalService getWrappedService() {
		return _rankingLocalService;
	}

	@Override
	public void setWrappedService(RankingLocalService rankingLocalService) {
		_rankingLocalService = rankingLocalService;
	}

	private RankingLocalService _rankingLocalService;

}