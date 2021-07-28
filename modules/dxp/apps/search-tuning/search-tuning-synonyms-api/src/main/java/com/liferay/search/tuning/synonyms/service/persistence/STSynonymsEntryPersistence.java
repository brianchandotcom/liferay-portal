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

package com.liferay.search.tuning.synonyms.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.search.tuning.synonyms.exception.NoSuchSTSynonymsEntryException;
import com.liferay.search.tuning.synonyms.model.STSynonymsEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the st synonyms entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Bryan Engler
 * @see STSynonymsEntryUtil
 * @generated
 */
@ProviderType
public interface STSynonymsEntryPersistence
	extends BasePersistence<STSynonymsEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link STSynonymsEntryUtil} to access the st synonyms entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the st synonyms entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching st synonyms entries
	 */
	public java.util.List<STSynonymsEntry> findBycompanyId(long companyId);

	/**
	 * Returns a range of all the st synonyms entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>STSynonymsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of st synonyms entries
	 * @param end the upper bound of the range of st synonyms entries (not inclusive)
	 * @return the range of matching st synonyms entries
	 */
	public java.util.List<STSynonymsEntry> findBycompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the st synonyms entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>STSynonymsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of st synonyms entries
	 * @param end the upper bound of the range of st synonyms entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching st synonyms entries
	 */
	public java.util.List<STSynonymsEntry> findBycompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<STSynonymsEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the st synonyms entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>STSynonymsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of st synonyms entries
	 * @param end the upper bound of the range of st synonyms entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching st synonyms entries
	 */
	public java.util.List<STSynonymsEntry> findBycompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<STSynonymsEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first st synonyms entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching st synonyms entry
	 * @throws NoSuchSTSynonymsEntryException if a matching st synonyms entry could not be found
	 */
	public STSynonymsEntry findBycompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<STSynonymsEntry>
				orderByComparator)
		throws NoSuchSTSynonymsEntryException;

	/**
	 * Returns the first st synonyms entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching st synonyms entry, or <code>null</code> if a matching st synonyms entry could not be found
	 */
	public STSynonymsEntry fetchBycompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<STSynonymsEntry>
			orderByComparator);

	/**
	 * Returns the last st synonyms entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching st synonyms entry
	 * @throws NoSuchSTSynonymsEntryException if a matching st synonyms entry could not be found
	 */
	public STSynonymsEntry findBycompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<STSynonymsEntry>
				orderByComparator)
		throws NoSuchSTSynonymsEntryException;

	/**
	 * Returns the last st synonyms entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching st synonyms entry, or <code>null</code> if a matching st synonyms entry could not be found
	 */
	public STSynonymsEntry fetchBycompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<STSynonymsEntry>
			orderByComparator);

	/**
	 * Returns the st synonyms entries before and after the current st synonyms entry in the ordered set where companyId = &#63;.
	 *
	 * @param STSynonymsEntryId the primary key of the current st synonyms entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next st synonyms entry
	 * @throws NoSuchSTSynonymsEntryException if a st synonyms entry with the primary key could not be found
	 */
	public STSynonymsEntry[] findBycompanyId_PrevAndNext(
			long STSynonymsEntryId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<STSynonymsEntry>
				orderByComparator)
		throws NoSuchSTSynonymsEntryException;

	/**
	 * Removes all the st synonyms entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeBycompanyId(long companyId);

	/**
	 * Returns the number of st synonyms entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching st synonyms entries
	 */
	public int countBycompanyId(long companyId);

	/**
	 * Caches the st synonyms entry in the entity cache if it is enabled.
	 *
	 * @param stSynonymsEntry the st synonyms entry
	 */
	public void cacheResult(STSynonymsEntry stSynonymsEntry);

	/**
	 * Caches the st synonyms entries in the entity cache if it is enabled.
	 *
	 * @param stSynonymsEntries the st synonyms entries
	 */
	public void cacheResult(java.util.List<STSynonymsEntry> stSynonymsEntries);

	/**
	 * Creates a new st synonyms entry with the primary key. Does not add the st synonyms entry to the database.
	 *
	 * @param STSynonymsEntryId the primary key for the new st synonyms entry
	 * @return the new st synonyms entry
	 */
	public STSynonymsEntry create(long STSynonymsEntryId);

	/**
	 * Removes the st synonyms entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param STSynonymsEntryId the primary key of the st synonyms entry
	 * @return the st synonyms entry that was removed
	 * @throws NoSuchSTSynonymsEntryException if a st synonyms entry with the primary key could not be found
	 */
	public STSynonymsEntry remove(long STSynonymsEntryId)
		throws NoSuchSTSynonymsEntryException;

	public STSynonymsEntry updateImpl(STSynonymsEntry stSynonymsEntry);

	/**
	 * Returns the st synonyms entry with the primary key or throws a <code>NoSuchSTSynonymsEntryException</code> if it could not be found.
	 *
	 * @param STSynonymsEntryId the primary key of the st synonyms entry
	 * @return the st synonyms entry
	 * @throws NoSuchSTSynonymsEntryException if a st synonyms entry with the primary key could not be found
	 */
	public STSynonymsEntry findByPrimaryKey(long STSynonymsEntryId)
		throws NoSuchSTSynonymsEntryException;

	/**
	 * Returns the st synonyms entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param STSynonymsEntryId the primary key of the st synonyms entry
	 * @return the st synonyms entry, or <code>null</code> if a st synonyms entry with the primary key could not be found
	 */
	public STSynonymsEntry fetchByPrimaryKey(long STSynonymsEntryId);

	/**
	 * Returns all the st synonyms entries.
	 *
	 * @return the st synonyms entries
	 */
	public java.util.List<STSynonymsEntry> findAll();

	/**
	 * Returns a range of all the st synonyms entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>STSynonymsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of st synonyms entries
	 * @param end the upper bound of the range of st synonyms entries (not inclusive)
	 * @return the range of st synonyms entries
	 */
	public java.util.List<STSynonymsEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the st synonyms entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>STSynonymsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of st synonyms entries
	 * @param end the upper bound of the range of st synonyms entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of st synonyms entries
	 */
	public java.util.List<STSynonymsEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<STSynonymsEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the st synonyms entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>STSynonymsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of st synonyms entries
	 * @param end the upper bound of the range of st synonyms entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of st synonyms entries
	 */
	public java.util.List<STSynonymsEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<STSynonymsEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the st synonyms entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of st synonyms entries.
	 *
	 * @return the number of st synonyms entries
	 */
	public int countAll();

}