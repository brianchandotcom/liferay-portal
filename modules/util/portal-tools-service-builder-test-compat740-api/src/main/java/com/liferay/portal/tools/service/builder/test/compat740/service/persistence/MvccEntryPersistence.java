/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat740.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.compat740.exception.NoSuchMvccEntryException;
import com.liferay.portal.tools.service.builder.test.compat740.model.MvccEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the mvcc entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MvccEntryUtil
 * @generated
 */
@ProviderType
public interface MvccEntryPersistence extends BasePersistence<MvccEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MvccEntryUtil} to access the mvcc entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the mvcc entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching mvcc entries
	 */
	public java.util.List<MvccEntry> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the mvcc entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MvccEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of mvcc entries
	 * @param end the upper bound of the range of mvcc entries (not inclusive)
	 * @return the range of matching mvcc entries
	 */
	public java.util.List<MvccEntry> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the mvcc entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MvccEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of mvcc entries
	 * @param end the upper bound of the range of mvcc entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching mvcc entries
	 */
	public java.util.List<MvccEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MvccEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the mvcc entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MvccEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of mvcc entries
	 * @param end the upper bound of the range of mvcc entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching mvcc entries
	 */
	public java.util.List<MvccEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MvccEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first mvcc entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mvcc entry
	 * @throws NoSuchMvccEntryException if a matching mvcc entry could not be found
	 */
	public MvccEntry findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<MvccEntry>
				orderByComparator)
		throws NoSuchMvccEntryException;

	/**
	 * Returns the first mvcc entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mvcc entry, or <code>null</code> if a matching mvcc entry could not be found
	 */
	public MvccEntry fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<MvccEntry>
			orderByComparator);

	/**
	 * Returns the last mvcc entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mvcc entry
	 * @throws NoSuchMvccEntryException if a matching mvcc entry could not be found
	 */
	public MvccEntry findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<MvccEntry>
				orderByComparator)
		throws NoSuchMvccEntryException;

	/**
	 * Returns the last mvcc entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mvcc entry, or <code>null</code> if a matching mvcc entry could not be found
	 */
	public MvccEntry fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<MvccEntry>
			orderByComparator);

	/**
	 * Returns the mvcc entries before and after the current mvcc entry in the ordered set where companyId = &#63;.
	 *
	 * @param mvccEntryId the primary key of the current mvcc entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next mvcc entry
	 * @throws NoSuchMvccEntryException if a mvcc entry with the primary key could not be found
	 */
	public MvccEntry[] findByCompanyId_PrevAndNext(
			long mvccEntryId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<MvccEntry>
				orderByComparator)
		throws NoSuchMvccEntryException;

	/**
	 * Removes all the mvcc entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of mvcc entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching mvcc entries
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns the mvcc entry where companyId = &#63; and name = &#63; or throws a <code>NoSuchMvccEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching mvcc entry
	 * @throws NoSuchMvccEntryException if a matching mvcc entry could not be found
	 */
	public MvccEntry findByC_N(long companyId, String name)
		throws NoSuchMvccEntryException;

	/**
	 * Returns the mvcc entry where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching mvcc entry, or <code>null</code> if a matching mvcc entry could not be found
	 */
	public MvccEntry fetchByC_N(long companyId, String name);

	/**
	 * Returns the mvcc entry where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching mvcc entry, or <code>null</code> if a matching mvcc entry could not be found
	 */
	public MvccEntry fetchByC_N(
		long companyId, String name, boolean useFinderCache);

	/**
	 * Removes the mvcc entry where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the mvcc entry that was removed
	 */
	public MvccEntry removeByC_N(long companyId, String name)
		throws NoSuchMvccEntryException;

	/**
	 * Returns the number of mvcc entries where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching mvcc entries
	 */
	public int countByC_N(long companyId, String name);

	/**
	 * Caches the mvcc entry in the entity cache if it is enabled.
	 *
	 * @param mvccEntry the mvcc entry
	 */
	public void cacheResult(MvccEntry mvccEntry);

	/**
	 * Caches the mvcc entries in the entity cache if it is enabled.
	 *
	 * @param mvccEntries the mvcc entries
	 */
	public void cacheResult(java.util.List<MvccEntry> mvccEntries);

	/**
	 * Creates a new mvcc entry with the primary key. Does not add the mvcc entry to the database.
	 *
	 * @param mvccEntryId the primary key for the new mvcc entry
	 * @return the new mvcc entry
	 */
	public MvccEntry create(long mvccEntryId);

	/**
	 * Removes the mvcc entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param mvccEntryId the primary key of the mvcc entry
	 * @return the mvcc entry that was removed
	 * @throws NoSuchMvccEntryException if a mvcc entry with the primary key could not be found
	 */
	public MvccEntry remove(long mvccEntryId) throws NoSuchMvccEntryException;

	public MvccEntry updateImpl(MvccEntry mvccEntry);

	/**
	 * Returns the mvcc entry with the primary key or throws a <code>NoSuchMvccEntryException</code> if it could not be found.
	 *
	 * @param mvccEntryId the primary key of the mvcc entry
	 * @return the mvcc entry
	 * @throws NoSuchMvccEntryException if a mvcc entry with the primary key could not be found
	 */
	public MvccEntry findByPrimaryKey(long mvccEntryId)
		throws NoSuchMvccEntryException;

	/**
	 * Returns the mvcc entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param mvccEntryId the primary key of the mvcc entry
	 * @return the mvcc entry, or <code>null</code> if a mvcc entry with the primary key could not be found
	 */
	public MvccEntry fetchByPrimaryKey(long mvccEntryId);

	/**
	 * Returns all the mvcc entries.
	 *
	 * @return the mvcc entries
	 */
	public java.util.List<MvccEntry> findAll();

	/**
	 * Returns a range of all the mvcc entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MvccEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mvcc entries
	 * @param end the upper bound of the range of mvcc entries (not inclusive)
	 * @return the range of mvcc entries
	 */
	public java.util.List<MvccEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the mvcc entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MvccEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mvcc entries
	 * @param end the upper bound of the range of mvcc entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of mvcc entries
	 */
	public java.util.List<MvccEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MvccEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the mvcc entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MvccEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mvcc entries
	 * @param end the upper bound of the range of mvcc entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of mvcc entries
	 */
	public java.util.List<MvccEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MvccEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the mvcc entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of mvcc entries.
	 *
	 * @return the number of mvcc entries
	 */
	public int countAll();

}