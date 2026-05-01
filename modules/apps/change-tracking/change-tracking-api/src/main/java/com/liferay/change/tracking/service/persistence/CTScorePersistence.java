/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.service.persistence;

import com.liferay.change.tracking.exception.NoSuchScoreException;
import com.liferay.change.tracking.model.CTScore;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the ct score service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTScoreUtil
 * @generated
 */
@ProviderType
public interface CTScorePersistence extends BasePersistence<CTScore> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CTScoreUtil} to access the ct score persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the ct score where ctCollectionId = &#63; or throws a <code>NoSuchScoreException</code> if it could not be found.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the matching ct score
	 * @throws NoSuchScoreException if a matching ct score could not be found
	 */
	public CTScore findByCtCollectionId(long ctCollectionId)
		throws NoSuchScoreException;

	/**
	 * Returns the ct score where ctCollectionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the matching ct score, or <code>null</code> if a matching ct score could not be found
	 */
	public CTScore fetchByCtCollectionId(long ctCollectionId);

	/**
	 * Returns the ct score where ctCollectionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ct score, or <code>null</code> if a matching ct score could not be found
	 */
	public CTScore fetchByCtCollectionId(
		long ctCollectionId, boolean useFinderCache);

	/**
	 * Removes the ct score where ctCollectionId = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the ct score that was removed
	 */
	public CTScore removeByCtCollectionId(long ctCollectionId)
		throws NoSuchScoreException;

	/**
	 * Returns the number of ct scores where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching ct scores
	 */
	public int countByCtCollectionId(long ctCollectionId);

	/**
	 * Caches the ct score in the entity cache if it is enabled.
	 *
	 * @param ctScore the ct score
	 */
	public void cacheResult(CTScore ctScore);

	/**
	 * Caches the ct scores in the entity cache if it is enabled.
	 *
	 * @param ctScores the ct scores
	 */
	public void cacheResult(java.util.List<CTScore> ctScores);

	/**
	 * Creates a new ct score with the primary key. Does not add the ct score to the database.
	 *
	 * @param ctScoreId the primary key for the new ct score
	 * @return the new ct score
	 */
	public CTScore create(long ctScoreId);

	/**
	 * Removes the ct score with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctScoreId the primary key of the ct score
	 * @return the ct score that was removed
	 * @throws NoSuchScoreException if a ct score with the primary key could not be found
	 */
	public CTScore remove(long ctScoreId) throws NoSuchScoreException;

	public CTScore updateImpl(CTScore ctScore);

	/**
	 * Returns the ct score with the primary key or throws a <code>NoSuchScoreException</code> if it could not be found.
	 *
	 * @param ctScoreId the primary key of the ct score
	 * @return the ct score
	 * @throws NoSuchScoreException if a ct score with the primary key could not be found
	 */
	public CTScore findByPrimaryKey(long ctScoreId) throws NoSuchScoreException;

	/**
	 * Returns the ct score with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ctScoreId the primary key of the ct score
	 * @return the ct score, or <code>null</code> if a ct score with the primary key could not be found
	 */
	public CTScore fetchByPrimaryKey(long ctScoreId);

}
// LIFERAY-SERVICE-BUILDER-HASH:-1397703573