/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat740.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.portal.tools.service.builder.test.compat740.exception.NoSuchCTEntryException;
import com.liferay.portal.tools.service.builder.test.compat740.model.CTEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the ct entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTEntryUtil
 * @generated
 */
@ProviderType
public interface CTEntryPersistence
	extends BasePersistence<CTEntry>, CTPersistence<CTEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CTEntryUtil} to access the ct entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the ct entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching ct entries
	 */
	public java.util.List<CTEntry> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the ct entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of matching ct entries
	 */
	public java.util.List<CTEntry> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the ct entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct entries
	 */
	public java.util.List<CTEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ct entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct entries
	 */
	public java.util.List<CTEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ct entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry
	 * @throws NoSuchCTEntryException if a matching ct entry could not be found
	 */
	public CTEntry findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
				orderByComparator)
		throws NoSuchCTEntryException;

	/**
	 * Returns the first ct entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	public CTEntry fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
			orderByComparator);

	/**
	 * Returns the last ct entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry
	 * @throws NoSuchCTEntryException if a matching ct entry could not be found
	 */
	public CTEntry findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
				orderByComparator)
		throws NoSuchCTEntryException;

	/**
	 * Returns the last ct entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	public CTEntry fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
			orderByComparator);

	/**
	 * Returns the ct entries before and after the current ct entry in the ordered set where companyId = &#63;.
	 *
	 * @param ctEntryId the primary key of the current ct entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct entry
	 * @throws NoSuchCTEntryException if a ct entry with the primary key could not be found
	 */
	public CTEntry[] findByCompanyId_PrevAndNext(
			long ctEntryId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
				orderByComparator)
		throws NoSuchCTEntryException;

	/**
	 * Removes all the ct entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of ct entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching ct entries
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns the ct entry where companyId = &#63; and name = &#63; or throws a <code>NoSuchCTEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching ct entry
	 * @throws NoSuchCTEntryException if a matching ct entry could not be found
	 */
	public CTEntry findByC_N(long companyId, String name)
		throws NoSuchCTEntryException;

	/**
	 * Returns the ct entry where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	public CTEntry fetchByC_N(long companyId, String name);

	/**
	 * Returns the ct entry where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	public CTEntry fetchByC_N(
		long companyId, String name, boolean useFinderCache);

	/**
	 * Removes the ct entry where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the ct entry that was removed
	 */
	public CTEntry removeByC_N(long companyId, String name)
		throws NoSuchCTEntryException;

	/**
	 * Returns the number of ct entries where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching ct entries
	 */
	public int countByC_N(long companyId, String name);

	/**
	 * Caches the ct entry in the entity cache if it is enabled.
	 *
	 * @param ctEntry the ct entry
	 */
	public void cacheResult(CTEntry ctEntry);

	/**
	 * Caches the ct entries in the entity cache if it is enabled.
	 *
	 * @param ctEntries the ct entries
	 */
	public void cacheResult(java.util.List<CTEntry> ctEntries);

	/**
	 * Creates a new ct entry with the primary key. Does not add the ct entry to the database.
	 *
	 * @param ctEntryId the primary key for the new ct entry
	 * @return the new ct entry
	 */
	public CTEntry create(long ctEntryId);

	/**
	 * Removes the ct entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctEntryId the primary key of the ct entry
	 * @return the ct entry that was removed
	 * @throws NoSuchCTEntryException if a ct entry with the primary key could not be found
	 */
	public CTEntry remove(long ctEntryId) throws NoSuchCTEntryException;

	public CTEntry updateImpl(CTEntry ctEntry);

	/**
	 * Returns the ct entry with the primary key or throws a <code>NoSuchCTEntryException</code> if it could not be found.
	 *
	 * @param ctEntryId the primary key of the ct entry
	 * @return the ct entry
	 * @throws NoSuchCTEntryException if a ct entry with the primary key could not be found
	 */
	public CTEntry findByPrimaryKey(long ctEntryId)
		throws NoSuchCTEntryException;

	/**
	 * Returns the ct entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ctEntryId the primary key of the ct entry
	 * @return the ct entry, or <code>null</code> if a ct entry with the primary key could not be found
	 */
	public CTEntry fetchByPrimaryKey(long ctEntryId);

	/**
	 * Returns all the ct entries.
	 *
	 * @return the ct entries
	 */
	public java.util.List<CTEntry> findAll();

	/**
	 * Returns a range of all the ct entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of ct entries
	 */
	public java.util.List<CTEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the ct entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct entries
	 */
	public java.util.List<CTEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ct entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ct entries
	 */
	public java.util.List<CTEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the ct entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of ct entries.
	 *
	 * @return the number of ct entries
	 */
	public int countAll();

}
// LIFERAY-SERVICE-BUILDER-HASH:-855946974