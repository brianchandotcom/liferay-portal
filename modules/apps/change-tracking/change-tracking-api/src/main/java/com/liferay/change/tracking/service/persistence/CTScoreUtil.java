/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.service.persistence;

import com.liferay.change.tracking.model.CTScore;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the ct score service. This utility wraps <code>com.liferay.change.tracking.service.persistence.impl.CTScorePersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTScorePersistence
 * @generated
 */
public class CTScoreUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(CTScore ctScore) {
		getPersistence().clearCache(ctScore);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, CTScore> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CTScore> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CTScore> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CTScore> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CTScore> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CTScore update(CTScore ctScore) {
		return getPersistence().update(ctScore);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CTScore update(
		CTScore ctScore, ServiceContext serviceContext) {

		return getPersistence().update(ctScore, serviceContext);
	}

	/**
	 * Returns the ct score where ctCollectionId = &#63; or throws a <code>NoSuchScoreException</code> if it could not be found.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the matching ct score
	 * @throws NoSuchScoreException if a matching ct score could not be found
	 */
	public static CTScore findByCtCollectionId(long ctCollectionId)
		throws com.liferay.change.tracking.exception.NoSuchScoreException {

		return getPersistence().findByCtCollectionId(ctCollectionId);
	}

	/**
	 * Returns the ct score where ctCollectionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the matching ct score, or <code>null</code> if a matching ct score could not be found
	 */
	public static CTScore fetchByCtCollectionId(long ctCollectionId) {
		return getPersistence().fetchByCtCollectionId(ctCollectionId);
	}

	/**
	 * Returns the ct score where ctCollectionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ct score, or <code>null</code> if a matching ct score could not be found
	 */
	public static CTScore fetchByCtCollectionId(
		long ctCollectionId, boolean useFinderCache) {

		return getPersistence().fetchByCtCollectionId(
			ctCollectionId, useFinderCache);
	}

	/**
	 * Removes the ct score where ctCollectionId = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the ct score that was removed
	 */
	public static CTScore removeByCtCollectionId(long ctCollectionId)
		throws com.liferay.change.tracking.exception.NoSuchScoreException {

		return getPersistence().removeByCtCollectionId(ctCollectionId);
	}

	/**
	 * Returns the number of ct scores where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching ct scores
	 */
	public static int countByCtCollectionId(long ctCollectionId) {
		return getPersistence().countByCtCollectionId(ctCollectionId);
	}

	/**
	 * Caches the ct score in the entity cache if it is enabled.
	 *
	 * @param ctScore the ct score
	 */
	public static void cacheResult(CTScore ctScore) {
		getPersistence().cacheResult(ctScore);
	}

	/**
	 * Caches the ct scores in the entity cache if it is enabled.
	 *
	 * @param ctScores the ct scores
	 */
	public static void cacheResult(List<CTScore> ctScores) {
		getPersistence().cacheResult(ctScores);
	}

	/**
	 * Creates a new ct score with the primary key. Does not add the ct score to the database.
	 *
	 * @param ctScoreId the primary key for the new ct score
	 * @return the new ct score
	 */
	public static CTScore create(long ctScoreId) {
		return getPersistence().create(ctScoreId);
	}

	/**
	 * Removes the ct score with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctScoreId the primary key of the ct score
	 * @return the ct score that was removed
	 * @throws NoSuchScoreException if a ct score with the primary key could not be found
	 */
	public static CTScore remove(long ctScoreId)
		throws com.liferay.change.tracking.exception.NoSuchScoreException {

		return getPersistence().remove(ctScoreId);
	}

	public static CTScore updateImpl(CTScore ctScore) {
		return getPersistence().updateImpl(ctScore);
	}

	/**
	 * Returns the ct score with the primary key or throws a <code>NoSuchScoreException</code> if it could not be found.
	 *
	 * @param ctScoreId the primary key of the ct score
	 * @return the ct score
	 * @throws NoSuchScoreException if a ct score with the primary key could not be found
	 */
	public static CTScore findByPrimaryKey(long ctScoreId)
		throws com.liferay.change.tracking.exception.NoSuchScoreException {

		return getPersistence().findByPrimaryKey(ctScoreId);
	}

	/**
	 * Returns the ct score with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ctScoreId the primary key of the ct score
	 * @return the ct score, or <code>null</code> if a ct score with the primary key could not be found
	 */
	public static CTScore fetchByPrimaryKey(long ctScoreId) {
		return getPersistence().fetchByPrimaryKey(ctScoreId);
	}

	/**
	 * Returns all the ct scores.
	 *
	 * @return the ct scores
	 */
	public static List<CTScore> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the ct scores.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTScoreModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct scores
	 * @param end the upper bound of the range of ct scores (not inclusive)
	 * @return the range of ct scores
	 */
	public static List<CTScore> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the ct scores.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTScoreModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct scores
	 * @param end the upper bound of the range of ct scores (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct scores
	 */
	public static List<CTScore> findAll(
		int start, int end, OrderByComparator<CTScore> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ct scores.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTScoreModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct scores
	 * @param end the upper bound of the range of ct scores (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ct scores
	 */
	public static List<CTScore> findAll(
		int start, int end, OrderByComparator<CTScore> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the ct scores from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of ct scores.
	 *
	 * @return the number of ct scores
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CTScorePersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(CTScorePersistence persistence) {
		_persistence = persistence;
	}

	private static volatile CTScorePersistence _persistence;

}
// SB-Hash:99505047