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

package com.liferay.portal.search.tuning.rankings.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.search.tuning.rankings.exception.NoSuchRankingException;
import com.liferay.portal.search.tuning.rankings.model.Ranking;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the ranking service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Bryan Engler
 * @see RankingUtil
 * @generated
 */
@ProviderType
public interface RankingPersistence extends BasePersistence<Ranking> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link RankingUtil} to access the ranking persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the rankings where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching rankings
	 */
	public java.util.List<Ranking> findBycompanyId(long companyId);

	/**
	 * Returns a range of all the rankings where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RankingModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of rankings
	 * @param end the upper bound of the range of rankings (not inclusive)
	 * @return the range of matching rankings
	 */
	public java.util.List<Ranking> findBycompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the rankings where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RankingModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of rankings
	 * @param end the upper bound of the range of rankings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rankings
	 */
	public java.util.List<Ranking> findBycompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Ranking>
			orderByComparator);

	/**
	 * Returns an ordered range of all the rankings where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RankingModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of rankings
	 * @param end the upper bound of the range of rankings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching rankings
	 */
	public java.util.List<Ranking> findBycompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Ranking>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ranking in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ranking
	 * @throws NoSuchRankingException if a matching ranking could not be found
	 */
	public Ranking findBycompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<Ranking>
				orderByComparator)
		throws NoSuchRankingException;

	/**
	 * Returns the first ranking in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ranking, or <code>null</code> if a matching ranking could not be found
	 */
	public Ranking fetchBycompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Ranking>
			orderByComparator);

	/**
	 * Returns the last ranking in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ranking
	 * @throws NoSuchRankingException if a matching ranking could not be found
	 */
	public Ranking findBycompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<Ranking>
				orderByComparator)
		throws NoSuchRankingException;

	/**
	 * Returns the last ranking in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ranking, or <code>null</code> if a matching ranking could not be found
	 */
	public Ranking fetchBycompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Ranking>
			orderByComparator);

	/**
	 * Returns the rankings before and after the current ranking in the ordered set where companyId = &#63;.
	 *
	 * @param rankingId the primary key of the current ranking
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ranking
	 * @throws NoSuchRankingException if a ranking with the primary key could not be found
	 */
	public Ranking[] findBycompanyId_PrevAndNext(
			long rankingId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<Ranking>
				orderByComparator)
		throws NoSuchRankingException;

	/**
	 * Removes all the rankings where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeBycompanyId(long companyId);

	/**
	 * Returns the number of rankings where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching rankings
	 */
	public int countBycompanyId(long companyId);

	/**
	 * Caches the ranking in the entity cache if it is enabled.
	 *
	 * @param ranking the ranking
	 */
	public void cacheResult(Ranking ranking);

	/**
	 * Caches the rankings in the entity cache if it is enabled.
	 *
	 * @param rankings the rankings
	 */
	public void cacheResult(java.util.List<Ranking> rankings);

	/**
	 * Creates a new ranking with the primary key. Does not add the ranking to the database.
	 *
	 * @param rankingId the primary key for the new ranking
	 * @return the new ranking
	 */
	public Ranking create(long rankingId);

	/**
	 * Removes the ranking with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param rankingId the primary key of the ranking
	 * @return the ranking that was removed
	 * @throws NoSuchRankingException if a ranking with the primary key could not be found
	 */
	public Ranking remove(long rankingId) throws NoSuchRankingException;

	public Ranking updateImpl(Ranking ranking);

	/**
	 * Returns the ranking with the primary key or throws a <code>NoSuchRankingException</code> if it could not be found.
	 *
	 * @param rankingId the primary key of the ranking
	 * @return the ranking
	 * @throws NoSuchRankingException if a ranking with the primary key could not be found
	 */
	public Ranking findByPrimaryKey(long rankingId)
		throws NoSuchRankingException;

	/**
	 * Returns the ranking with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param rankingId the primary key of the ranking
	 * @return the ranking, or <code>null</code> if a ranking with the primary key could not be found
	 */
	public Ranking fetchByPrimaryKey(long rankingId);

	/**
	 * Returns all the rankings.
	 *
	 * @return the rankings
	 */
	public java.util.List<Ranking> findAll();

	/**
	 * Returns a range of all the rankings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RankingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of rankings
	 * @param end the upper bound of the range of rankings (not inclusive)
	 * @return the range of rankings
	 */
	public java.util.List<Ranking> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the rankings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RankingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of rankings
	 * @param end the upper bound of the range of rankings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of rankings
	 */
	public java.util.List<Ranking> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Ranking>
			orderByComparator);

	/**
	 * Returns an ordered range of all the rankings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RankingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of rankings
	 * @param end the upper bound of the range of rankings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of rankings
	 */
	public java.util.List<Ranking> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Ranking>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the rankings from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of rankings.
	 *
	 * @return the number of rankings
	 */
	public int countAll();

}