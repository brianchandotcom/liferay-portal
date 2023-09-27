/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.akismet.service.persistence;

import com.liferay.akismet.exception.NoSuchAkismetEntryException;
import com.liferay.akismet.model.AkismetEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the akismet entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Jamie Sammons
 * @see AkismetEntryUtil
 * @generated
 */
@ProviderType
public interface AkismetEntryPersistence extends BasePersistence<AkismetEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AkismetEntryUtil} to access the akismet entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the akismet entries where modifiedDate &lt; &#63;.
	 *
	 * @param modifiedDate the modified date
	 * @return the matching akismet entries
	 */
	public java.util.List<AkismetEntry> findByLtModifiedDate(Date modifiedDate);

	/**
	 * Returns a range of all the akismet entries where modifiedDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AkismetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedDate the modified date
	 * @param start the lower bound of the range of akismet entries
	 * @param end the upper bound of the range of akismet entries (not inclusive)
	 * @return the range of matching akismet entries
	 */
	public java.util.List<AkismetEntry> findByLtModifiedDate(
		Date modifiedDate, int start, int end);

	/**
	 * Returns an ordered range of all the akismet entries where modifiedDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AkismetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedDate the modified date
	 * @param start the lower bound of the range of akismet entries
	 * @param end the upper bound of the range of akismet entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching akismet entries
	 */
	public java.util.List<AkismetEntry> findByLtModifiedDate(
		Date modifiedDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AkismetEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the akismet entries where modifiedDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AkismetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedDate the modified date
	 * @param start the lower bound of the range of akismet entries
	 * @param end the upper bound of the range of akismet entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching akismet entries
	 */
	public java.util.List<AkismetEntry> findByLtModifiedDate(
		Date modifiedDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AkismetEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first akismet entry in the ordered set where modifiedDate &lt; &#63;.
	 *
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching akismet entry
	 * @throws NoSuchAkismetEntryException if a matching akismet entry could not be found
	 */
	public AkismetEntry findByLtModifiedDate_First(
			Date modifiedDate,
			com.liferay.portal.kernel.util.OrderByComparator<AkismetEntry>
				orderByComparator)
		throws NoSuchAkismetEntryException;

	/**
	 * Returns the first akismet entry in the ordered set where modifiedDate &lt; &#63;.
	 *
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching akismet entry, or <code>null</code> if a matching akismet entry could not be found
	 */
	public AkismetEntry fetchByLtModifiedDate_First(
		Date modifiedDate,
		com.liferay.portal.kernel.util.OrderByComparator<AkismetEntry>
			orderByComparator);

	/**
	 * Returns the last akismet entry in the ordered set where modifiedDate &lt; &#63;.
	 *
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching akismet entry
	 * @throws NoSuchAkismetEntryException if a matching akismet entry could not be found
	 */
	public AkismetEntry findByLtModifiedDate_Last(
			Date modifiedDate,
			com.liferay.portal.kernel.util.OrderByComparator<AkismetEntry>
				orderByComparator)
		throws NoSuchAkismetEntryException;

	/**
	 * Returns the last akismet entry in the ordered set where modifiedDate &lt; &#63;.
	 *
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching akismet entry, or <code>null</code> if a matching akismet entry could not be found
	 */
	public AkismetEntry fetchByLtModifiedDate_Last(
		Date modifiedDate,
		com.liferay.portal.kernel.util.OrderByComparator<AkismetEntry>
			orderByComparator);

	/**
	 * Returns the akismet entries before and after the current akismet entry in the ordered set where modifiedDate &lt; &#63;.
	 *
	 * @param akismetEntryId the primary key of the current akismet entry
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next akismet entry
	 * @throws NoSuchAkismetEntryException if a akismet entry with the primary key could not be found
	 */
	public AkismetEntry[] findByLtModifiedDate_PrevAndNext(
			long akismetEntryId, Date modifiedDate,
			com.liferay.portal.kernel.util.OrderByComparator<AkismetEntry>
				orderByComparator)
		throws NoSuchAkismetEntryException;

	/**
	 * Removes all the akismet entries where modifiedDate &lt; &#63; from the database.
	 *
	 * @param modifiedDate the modified date
	 */
	public void removeByLtModifiedDate(Date modifiedDate);

	/**
	 * Returns the number of akismet entries where modifiedDate &lt; &#63;.
	 *
	 * @param modifiedDate the modified date
	 * @return the number of matching akismet entries
	 */
	public int countByLtModifiedDate(Date modifiedDate);

	/**
	 * Returns the akismet entry where classNameId = &#63; and classPK = &#63; or throws a <code>NoSuchAkismetEntryException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching akismet entry
	 * @throws NoSuchAkismetEntryException if a matching akismet entry could not be found
	 */
	public AkismetEntry findByC_C(long classNameId, long classPK)
		throws NoSuchAkismetEntryException;

	/**
	 * Returns the akismet entry where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching akismet entry, or <code>null</code> if a matching akismet entry could not be found
	 */
	public AkismetEntry fetchByC_C(long classNameId, long classPK);

	/**
	 * Returns the akismet entry where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching akismet entry, or <code>null</code> if a matching akismet entry could not be found
	 */
	public AkismetEntry fetchByC_C(
		long classNameId, long classPK, boolean useFinderCache);

	/**
	 * Removes the akismet entry where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the akismet entry that was removed
	 */
	public AkismetEntry removeByC_C(long classNameId, long classPK)
		throws NoSuchAkismetEntryException;

	/**
	 * Returns the number of akismet entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching akismet entries
	 */
	public int countByC_C(long classNameId, long classPK);

	/**
	 * Caches the akismet entry in the entity cache if it is enabled.
	 *
	 * @param akismetEntry the akismet entry
	 */
	public void cacheResult(AkismetEntry akismetEntry);

	/**
	 * Caches the akismet entries in the entity cache if it is enabled.
	 *
	 * @param akismetEntries the akismet entries
	 */
	public void cacheResult(java.util.List<AkismetEntry> akismetEntries);

	/**
	 * Creates a new akismet entry with the primary key. Does not add the akismet entry to the database.
	 *
	 * @param akismetEntryId the primary key for the new akismet entry
	 * @return the new akismet entry
	 */
	public AkismetEntry create(long akismetEntryId);

	/**
	 * Removes the akismet entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param akismetEntryId the primary key of the akismet entry
	 * @return the akismet entry that was removed
	 * @throws NoSuchAkismetEntryException if a akismet entry with the primary key could not be found
	 */
	public AkismetEntry remove(long akismetEntryId)
		throws NoSuchAkismetEntryException;

	public AkismetEntry updateImpl(AkismetEntry akismetEntry);

	/**
	 * Returns the akismet entry with the primary key or throws a <code>NoSuchAkismetEntryException</code> if it could not be found.
	 *
	 * @param akismetEntryId the primary key of the akismet entry
	 * @return the akismet entry
	 * @throws NoSuchAkismetEntryException if a akismet entry with the primary key could not be found
	 */
	public AkismetEntry findByPrimaryKey(long akismetEntryId)
		throws NoSuchAkismetEntryException;

	/**
	 * Returns the akismet entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param akismetEntryId the primary key of the akismet entry
	 * @return the akismet entry, or <code>null</code> if a akismet entry with the primary key could not be found
	 */
	public AkismetEntry fetchByPrimaryKey(long akismetEntryId);

	/**
	 * Returns all the akismet entries.
	 *
	 * @return the akismet entries
	 */
	public java.util.List<AkismetEntry> findAll();

	/**
	 * Returns a range of all the akismet entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AkismetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of akismet entries
	 * @param end the upper bound of the range of akismet entries (not inclusive)
	 * @return the range of akismet entries
	 */
	public java.util.List<AkismetEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the akismet entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AkismetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of akismet entries
	 * @param end the upper bound of the range of akismet entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of akismet entries
	 */
	public java.util.List<AkismetEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AkismetEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the akismet entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AkismetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of akismet entries
	 * @param end the upper bound of the range of akismet entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of akismet entries
	 */
	public java.util.List<AkismetEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AkismetEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the akismet entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of akismet entries.
	 *
	 * @return the number of akismet entries
	 */
	public int countAll();

}