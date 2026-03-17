/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat730.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.compat730.exception.NoSuchLikeFinderEntryException;
import com.liferay.portal.tools.service.builder.test.compat730.model.LikeFinderEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the like finder entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LikeFinderEntryUtil
 * @generated
 */
@ProviderType
public interface LikeFinderEntryPersistence
	extends BasePersistence<LikeFinderEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LikeFinderEntryUtil} to access the like finder entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the like finder entry where ownerId = &#63; and ownerType = &#63; and portletId = &#63; or throws a <code>NoSuchLikeFinderEntryException</code> if it could not be found.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @return the matching like finder entry
	 * @throws NoSuchLikeFinderEntryException if a matching like finder entry could not be found
	 */
	public LikeFinderEntry findByO_O_P(
			long ownerId, int ownerType, String portletId)
		throws NoSuchLikeFinderEntryException;

	/**
	 * Returns the like finder entry where ownerId = &#63; and ownerType = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @return the matching like finder entry, or <code>null</code> if a matching like finder entry could not be found
	 */
	public LikeFinderEntry fetchByO_O_P(
		long ownerId, int ownerType, String portletId);

	/**
	 * Returns the like finder entry where ownerId = &#63; and ownerType = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching like finder entry, or <code>null</code> if a matching like finder entry could not be found
	 */
	public LikeFinderEntry fetchByO_O_P(
		long ownerId, int ownerType, String portletId, boolean useFinderCache);

	/**
	 * Removes the like finder entry where ownerId = &#63; and ownerType = &#63; and portletId = &#63; from the database.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @return the like finder entry that was removed
	 */
	public LikeFinderEntry removeByO_O_P(
			long ownerId, int ownerType, String portletId)
		throws NoSuchLikeFinderEntryException;

	/**
	 * Returns the number of like finder entries where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @return the number of matching like finder entries
	 */
	public int countByO_O_P(long ownerId, int ownerType, String portletId);

	/**
	 * Returns all the like finder entries where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @return the matching like finder entries
	 */
	public java.util.List<LikeFinderEntry> findByC_O_O_LikeP(
		long companyId, long ownerId, int ownerType, String portletId);

	/**
	 * Returns a range of all the like finder entries where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LikeFinderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of like finder entries
	 * @param end the upper bound of the range of like finder entries (not inclusive)
	 * @return the range of matching like finder entries
	 */
	public java.util.List<LikeFinderEntry> findByC_O_O_LikeP(
		long companyId, long ownerId, int ownerType, String portletId,
		int start, int end);

	/**
	 * Returns an ordered range of all the like finder entries where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LikeFinderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of like finder entries
	 * @param end the upper bound of the range of like finder entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching like finder entries
	 */
	public java.util.List<LikeFinderEntry> findByC_O_O_LikeP(
		long companyId, long ownerId, int ownerType, String portletId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LikeFinderEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the like finder entries where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LikeFinderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of like finder entries
	 * @param end the upper bound of the range of like finder entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching like finder entries
	 */
	public java.util.List<LikeFinderEntry> findByC_O_O_LikeP(
		long companyId, long ownerId, int ownerType, String portletId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LikeFinderEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first like finder entry in the ordered set where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching like finder entry
	 * @throws NoSuchLikeFinderEntryException if a matching like finder entry could not be found
	 */
	public LikeFinderEntry findByC_O_O_LikeP_First(
			long companyId, long ownerId, int ownerType, String portletId,
			com.liferay.portal.kernel.util.OrderByComparator<LikeFinderEntry>
				orderByComparator)
		throws NoSuchLikeFinderEntryException;

	/**
	 * Returns the first like finder entry in the ordered set where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching like finder entry, or <code>null</code> if a matching like finder entry could not be found
	 */
	public LikeFinderEntry fetchByC_O_O_LikeP_First(
		long companyId, long ownerId, int ownerType, String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<LikeFinderEntry>
			orderByComparator);

	/**
	 * Returns the last like finder entry in the ordered set where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching like finder entry
	 * @throws NoSuchLikeFinderEntryException if a matching like finder entry could not be found
	 */
	public LikeFinderEntry findByC_O_O_LikeP_Last(
			long companyId, long ownerId, int ownerType, String portletId,
			com.liferay.portal.kernel.util.OrderByComparator<LikeFinderEntry>
				orderByComparator)
		throws NoSuchLikeFinderEntryException;

	/**
	 * Returns the last like finder entry in the ordered set where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching like finder entry, or <code>null</code> if a matching like finder entry could not be found
	 */
	public LikeFinderEntry fetchByC_O_O_LikeP_Last(
		long companyId, long ownerId, int ownerType, String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<LikeFinderEntry>
			orderByComparator);

	/**
	 * Returns the like finder entries before and after the current like finder entry in the ordered set where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * @param likeFinderEntryId the primary key of the current like finder entry
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next like finder entry
	 * @throws NoSuchLikeFinderEntryException if a like finder entry with the primary key could not be found
	 */
	public LikeFinderEntry[] findByC_O_O_LikeP_PrevAndNext(
			long likeFinderEntryId, long companyId, long ownerId, int ownerType,
			String portletId,
			com.liferay.portal.kernel.util.OrderByComparator<LikeFinderEntry>
				orderByComparator)
		throws NoSuchLikeFinderEntryException;

	/**
	 * Removes all the like finder entries where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 */
	public void removeByC_O_O_LikeP(
		long companyId, long ownerId, int ownerType, String portletId);

	/**
	 * Returns the number of like finder entries where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @return the number of matching like finder entries
	 */
	public int countByC_O_O_LikeP(
		long companyId, long ownerId, int ownerType, String portletId);

	/**
	 * Caches the like finder entry in the entity cache if it is enabled.
	 *
	 * @param likeFinderEntry the like finder entry
	 */
	public void cacheResult(LikeFinderEntry likeFinderEntry);

	/**
	 * Caches the like finder entries in the entity cache if it is enabled.
	 *
	 * @param likeFinderEntries the like finder entries
	 */
	public void cacheResult(java.util.List<LikeFinderEntry> likeFinderEntries);

	/**
	 * Creates a new like finder entry with the primary key. Does not add the like finder entry to the database.
	 *
	 * @param likeFinderEntryId the primary key for the new like finder entry
	 * @return the new like finder entry
	 */
	public LikeFinderEntry create(long likeFinderEntryId);

	/**
	 * Removes the like finder entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param likeFinderEntryId the primary key of the like finder entry
	 * @return the like finder entry that was removed
	 * @throws NoSuchLikeFinderEntryException if a like finder entry with the primary key could not be found
	 */
	public LikeFinderEntry remove(long likeFinderEntryId)
		throws NoSuchLikeFinderEntryException;

	public LikeFinderEntry updateImpl(LikeFinderEntry likeFinderEntry);

	/**
	 * Returns the like finder entry with the primary key or throws a <code>NoSuchLikeFinderEntryException</code> if it could not be found.
	 *
	 * @param likeFinderEntryId the primary key of the like finder entry
	 * @return the like finder entry
	 * @throws NoSuchLikeFinderEntryException if a like finder entry with the primary key could not be found
	 */
	public LikeFinderEntry findByPrimaryKey(long likeFinderEntryId)
		throws NoSuchLikeFinderEntryException;

	/**
	 * Returns the like finder entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param likeFinderEntryId the primary key of the like finder entry
	 * @return the like finder entry, or <code>null</code> if a like finder entry with the primary key could not be found
	 */
	public LikeFinderEntry fetchByPrimaryKey(long likeFinderEntryId);

	/**
	 * Returns all the like finder entries.
	 *
	 * @return the like finder entries
	 */
	public java.util.List<LikeFinderEntry> findAll();

	/**
	 * Returns a range of all the like finder entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LikeFinderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of like finder entries
	 * @param end the upper bound of the range of like finder entries (not inclusive)
	 * @return the range of like finder entries
	 */
	public java.util.List<LikeFinderEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the like finder entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LikeFinderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of like finder entries
	 * @param end the upper bound of the range of like finder entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of like finder entries
	 */
	public java.util.List<LikeFinderEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LikeFinderEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the like finder entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LikeFinderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of like finder entries
	 * @param end the upper bound of the range of like finder entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of like finder entries
	 */
	public java.util.List<LikeFinderEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LikeFinderEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the like finder entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of like finder entries.
	 *
	 * @return the number of like finder entries
	 */
	public int countAll();

}
// SB-Hash:1395840979