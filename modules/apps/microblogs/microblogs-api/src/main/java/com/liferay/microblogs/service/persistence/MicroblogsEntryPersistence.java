/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.microblogs.service.persistence;

import com.liferay.microblogs.exception.NoSuchEntryException;
import com.liferay.microblogs.model.MicroblogsEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the microblogs entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MicroblogsEntryUtil
 * @generated
 */
@ProviderType
public interface MicroblogsEntryPersistence
	extends BasePersistence<MicroblogsEntry> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MicroblogsEntryUtil} to access the microblogs entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the microblogs entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the microblogs entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @return the range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the microblogs entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the microblogs entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first microblogs entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first microblogs entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator);

	/**
	 * Returns the last microblogs entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last microblogs entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator);

	/**
	 * Returns the microblogs entries before and after the current microblogs entry in the ordered set where companyId = &#63;.
	 *
	 * @param microblogsEntryId the primary key of the current microblogs entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next microblogs entry
	 * @throws NoSuchEntryException if a microblogs entry with the primary key could not be found
	 */
	public MicroblogsEntry[] findByCompanyId_PrevAndNext(
			long microblogsEntryId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the microblogs entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of microblogs entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching microblogs entries
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns all the microblogs entries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByUserId(long userId);

	/**
	 * Returns a range of all the microblogs entries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @return the range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByUserId(
		long userId, int start, int end);

	/**
	 * Returns an ordered range of all the microblogs entries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the microblogs entries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first microblogs entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry findByUserId_First(
			long userId,
			com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first microblogs entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry fetchByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator);

	/**
	 * Returns the last microblogs entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry findByUserId_Last(
			long userId,
			com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last microblogs entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry fetchByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator);

	/**
	 * Returns the microblogs entries before and after the current microblogs entry in the ordered set where userId = &#63;.
	 *
	 * @param microblogsEntryId the primary key of the current microblogs entry
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next microblogs entry
	 * @throws NoSuchEntryException if a microblogs entry with the primary key could not be found
	 */
	public MicroblogsEntry[] findByUserId_PrevAndNext(
			long microblogsEntryId, long userId,
			com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the microblogs entries where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	public void removeByUserId(long userId);

	/**
	 * Returns the number of microblogs entries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching microblogs entries
	 */
	public int countByUserId(long userId);

	/**
	 * Returns all the microblogs entries where userId = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @return the matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByU_T(long userId, int type);

	/**
	 * Returns a range of all the microblogs entries where userId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @return the range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByU_T(
		long userId, int type, int start, int end);

	/**
	 * Returns an ordered range of all the microblogs entries where userId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByU_T(
		long userId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the microblogs entries where userId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByU_T(
		long userId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first microblogs entry in the ordered set where userId = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry findByU_T_First(
			long userId, int type,
			com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first microblogs entry in the ordered set where userId = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry fetchByU_T_First(
		long userId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator);

	/**
	 * Returns the last microblogs entry in the ordered set where userId = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry findByU_T_Last(
			long userId, int type,
			com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last microblogs entry in the ordered set where userId = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry fetchByU_T_Last(
		long userId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator);

	/**
	 * Returns the microblogs entries before and after the current microblogs entry in the ordered set where userId = &#63; and type = &#63;.
	 *
	 * @param microblogsEntryId the primary key of the current microblogs entry
	 * @param userId the user ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next microblogs entry
	 * @throws NoSuchEntryException if a microblogs entry with the primary key could not be found
	 */
	public MicroblogsEntry[] findByU_T_PrevAndNext(
			long microblogsEntryId, long userId, int type,
			com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the microblogs entries where userId = &#63; and type = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param type the type
	 */
	public void removeByU_T(long userId, int type);

	/**
	 * Returns the number of microblogs entries where userId = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @return the number of matching microblogs entries
	 */
	public int countByU_T(long userId, int type);

	/**
	 * Returns all the microblogs entries where creatorClassNameId = &#63; and creatorClassPK = &#63;.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @return the matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByCCNI_CCPK(
		long creatorClassNameId, long creatorClassPK);

	/**
	 * Returns a range of all the microblogs entries where creatorClassNameId = &#63; and creatorClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @return the range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByCCNI_CCPK(
		long creatorClassNameId, long creatorClassPK, int start, int end);

	/**
	 * Returns an ordered range of all the microblogs entries where creatorClassNameId = &#63; and creatorClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByCCNI_CCPK(
		long creatorClassNameId, long creatorClassPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the microblogs entries where creatorClassNameId = &#63; and creatorClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByCCNI_CCPK(
		long creatorClassNameId, long creatorClassPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first microblogs entry in the ordered set where creatorClassNameId = &#63; and creatorClassPK = &#63;.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry findByCCNI_CCPK_First(
			long creatorClassNameId, long creatorClassPK,
			com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first microblogs entry in the ordered set where creatorClassNameId = &#63; and creatorClassPK = &#63;.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry fetchByCCNI_CCPK_First(
		long creatorClassNameId, long creatorClassPK,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator);

	/**
	 * Returns the last microblogs entry in the ordered set where creatorClassNameId = &#63; and creatorClassPK = &#63;.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry findByCCNI_CCPK_Last(
			long creatorClassNameId, long creatorClassPK,
			com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last microblogs entry in the ordered set where creatorClassNameId = &#63; and creatorClassPK = &#63;.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry fetchByCCNI_CCPK_Last(
		long creatorClassNameId, long creatorClassPK,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator);

	/**
	 * Returns the microblogs entries before and after the current microblogs entry in the ordered set where creatorClassNameId = &#63; and creatorClassPK = &#63;.
	 *
	 * @param microblogsEntryId the primary key of the current microblogs entry
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next microblogs entry
	 * @throws NoSuchEntryException if a microblogs entry with the primary key could not be found
	 */
	public MicroblogsEntry[] findByCCNI_CCPK_PrevAndNext(
			long microblogsEntryId, long creatorClassNameId,
			long creatorClassPK,
			com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns all the microblogs entries where creatorClassNameId = &#63; and creatorClassPK = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPKs the creator class pks
	 * @return the matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByCCNI_CCPK(
		long creatorClassNameId, long[] creatorClassPKs);

	/**
	 * Returns a range of all the microblogs entries where creatorClassNameId = &#63; and creatorClassPK = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPKs the creator class pks
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @return the range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByCCNI_CCPK(
		long creatorClassNameId, long[] creatorClassPKs, int start, int end);

	/**
	 * Returns an ordered range of all the microblogs entries where creatorClassNameId = &#63; and creatorClassPK = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPKs the creator class pks
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByCCNI_CCPK(
		long creatorClassNameId, long[] creatorClassPKs, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the microblogs entries where creatorClassNameId = &#63; and creatorClassPK = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByCCNI_CCPK(
		long creatorClassNameId, long[] creatorClassPKs, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the microblogs entries where creatorClassNameId = &#63; and creatorClassPK = &#63; from the database.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 */
	public void removeByCCNI_CCPK(long creatorClassNameId, long creatorClassPK);

	/**
	 * Returns the number of microblogs entries where creatorClassNameId = &#63; and creatorClassPK = &#63;.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @return the number of matching microblogs entries
	 */
	public int countByCCNI_CCPK(long creatorClassNameId, long creatorClassPK);

	/**
	 * Returns the number of microblogs entries where creatorClassNameId = &#63; and creatorClassPK = any &#63;.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPKs the creator class pks
	 * @return the number of matching microblogs entries
	 */
	public int countByCCNI_CCPK(
		long creatorClassNameId, long[] creatorClassPKs);

	/**
	 * Returns all the microblogs entries where creatorClassNameId = &#63; and type = &#63;.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 * @return the matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByCCNI_T(
		long creatorClassNameId, int type);

	/**
	 * Returns a range of all the microblogs entries where creatorClassNameId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @return the range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByCCNI_T(
		long creatorClassNameId, int type, int start, int end);

	/**
	 * Returns an ordered range of all the microblogs entries where creatorClassNameId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByCCNI_T(
		long creatorClassNameId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the microblogs entries where creatorClassNameId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByCCNI_T(
		long creatorClassNameId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first microblogs entry in the ordered set where creatorClassNameId = &#63; and type = &#63;.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry findByCCNI_T_First(
			long creatorClassNameId, int type,
			com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first microblogs entry in the ordered set where creatorClassNameId = &#63; and type = &#63;.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry fetchByCCNI_T_First(
		long creatorClassNameId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator);

	/**
	 * Returns the last microblogs entry in the ordered set where creatorClassNameId = &#63; and type = &#63;.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry findByCCNI_T_Last(
			long creatorClassNameId, int type,
			com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last microblogs entry in the ordered set where creatorClassNameId = &#63; and type = &#63;.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry fetchByCCNI_T_Last(
		long creatorClassNameId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator);

	/**
	 * Returns the microblogs entries before and after the current microblogs entry in the ordered set where creatorClassNameId = &#63; and type = &#63;.
	 *
	 * @param microblogsEntryId the primary key of the current microblogs entry
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next microblogs entry
	 * @throws NoSuchEntryException if a microblogs entry with the primary key could not be found
	 */
	public MicroblogsEntry[] findByCCNI_T_PrevAndNext(
			long microblogsEntryId, long creatorClassNameId, int type,
			com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the microblogs entries where creatorClassNameId = &#63; and type = &#63; from the database.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 */
	public void removeByCCNI_T(long creatorClassNameId, int type);

	/**
	 * Returns the number of microblogs entries where creatorClassNameId = &#63; and type = &#63;.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 * @return the number of matching microblogs entries
	 */
	public int countByCCNI_T(long creatorClassNameId, int type);

	/**
	 * Returns all the microblogs entries where type = &#63; and parentMicroblogsEntryId = &#63;.
	 *
	 * @param type the type
	 * @param parentMicroblogsEntryId the parent microblogs entry ID
	 * @return the matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByT_P(
		int type, long parentMicroblogsEntryId);

	/**
	 * Returns a range of all the microblogs entries where type = &#63; and parentMicroblogsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param type the type
	 * @param parentMicroblogsEntryId the parent microblogs entry ID
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @return the range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByT_P(
		int type, long parentMicroblogsEntryId, int start, int end);

	/**
	 * Returns an ordered range of all the microblogs entries where type = &#63; and parentMicroblogsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param type the type
	 * @param parentMicroblogsEntryId the parent microblogs entry ID
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByT_P(
		int type, long parentMicroblogsEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the microblogs entries where type = &#63; and parentMicroblogsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param type the type
	 * @param parentMicroblogsEntryId the parent microblogs entry ID
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByT_P(
		int type, long parentMicroblogsEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first microblogs entry in the ordered set where type = &#63; and parentMicroblogsEntryId = &#63;.
	 *
	 * @param type the type
	 * @param parentMicroblogsEntryId the parent microblogs entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry findByT_P_First(
			int type, long parentMicroblogsEntryId,
			com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first microblogs entry in the ordered set where type = &#63; and parentMicroblogsEntryId = &#63;.
	 *
	 * @param type the type
	 * @param parentMicroblogsEntryId the parent microblogs entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry fetchByT_P_First(
		int type, long parentMicroblogsEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator);

	/**
	 * Returns the last microblogs entry in the ordered set where type = &#63; and parentMicroblogsEntryId = &#63;.
	 *
	 * @param type the type
	 * @param parentMicroblogsEntryId the parent microblogs entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry findByT_P_Last(
			int type, long parentMicroblogsEntryId,
			com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last microblogs entry in the ordered set where type = &#63; and parentMicroblogsEntryId = &#63;.
	 *
	 * @param type the type
	 * @param parentMicroblogsEntryId the parent microblogs entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry fetchByT_P_Last(
		int type, long parentMicroblogsEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator);

	/**
	 * Returns the microblogs entries before and after the current microblogs entry in the ordered set where type = &#63; and parentMicroblogsEntryId = &#63;.
	 *
	 * @param microblogsEntryId the primary key of the current microblogs entry
	 * @param type the type
	 * @param parentMicroblogsEntryId the parent microblogs entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next microblogs entry
	 * @throws NoSuchEntryException if a microblogs entry with the primary key could not be found
	 */
	public MicroblogsEntry[] findByT_P_PrevAndNext(
			long microblogsEntryId, int type, long parentMicroblogsEntryId,
			com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the microblogs entries where type = &#63; and parentMicroblogsEntryId = &#63; from the database.
	 *
	 * @param type the type
	 * @param parentMicroblogsEntryId the parent microblogs entry ID
	 */
	public void removeByT_P(int type, long parentMicroblogsEntryId);

	/**
	 * Returns the number of microblogs entries where type = &#63; and parentMicroblogsEntryId = &#63;.
	 *
	 * @param type the type
	 * @param parentMicroblogsEntryId the parent microblogs entry ID
	 * @return the number of matching microblogs entries
	 */
	public int countByT_P(int type, long parentMicroblogsEntryId);

	/**
	 * Returns all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @return the matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByC_CCNI_CCPK(
		long companyId, long creatorClassNameId, long creatorClassPK);

	/**
	 * Returns a range of all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @return the range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByC_CCNI_CCPK(
		long companyId, long creatorClassNameId, long creatorClassPK, int start,
		int end);

	/**
	 * Returns an ordered range of all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByC_CCNI_CCPK(
		long companyId, long creatorClassNameId, long creatorClassPK, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByC_CCNI_CCPK(
		long companyId, long creatorClassNameId, long creatorClassPK, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first microblogs entry in the ordered set where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry findByC_CCNI_CCPK_First(
			long companyId, long creatorClassNameId, long creatorClassPK,
			com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first microblogs entry in the ordered set where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry fetchByC_CCNI_CCPK_First(
		long companyId, long creatorClassNameId, long creatorClassPK,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator);

	/**
	 * Returns the last microblogs entry in the ordered set where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry findByC_CCNI_CCPK_Last(
			long companyId, long creatorClassNameId, long creatorClassPK,
			com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last microblogs entry in the ordered set where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry fetchByC_CCNI_CCPK_Last(
		long companyId, long creatorClassNameId, long creatorClassPK,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator);

	/**
	 * Returns the microblogs entries before and after the current microblogs entry in the ordered set where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63;.
	 *
	 * @param microblogsEntryId the primary key of the current microblogs entry
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next microblogs entry
	 * @throws NoSuchEntryException if a microblogs entry with the primary key could not be found
	 */
	public MicroblogsEntry[] findByC_CCNI_CCPK_PrevAndNext(
			long microblogsEntryId, long companyId, long creatorClassNameId,
			long creatorClassPK,
			com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPKs the creator class pks
	 * @return the matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByC_CCNI_CCPK(
		long companyId, long creatorClassNameId, long[] creatorClassPKs);

	/**
	 * Returns a range of all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPKs the creator class pks
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @return the range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByC_CCNI_CCPK(
		long companyId, long creatorClassNameId, long[] creatorClassPKs,
		int start, int end);

	/**
	 * Returns an ordered range of all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPKs the creator class pks
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByC_CCNI_CCPK(
		long companyId, long creatorClassNameId, long[] creatorClassPKs,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByC_CCNI_CCPK(
		long companyId, long creatorClassNameId, long[] creatorClassPKs,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 */
	public void removeByC_CCNI_CCPK(
		long companyId, long creatorClassNameId, long creatorClassPK);

	/**
	 * Returns the number of microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @return the number of matching microblogs entries
	 */
	public int countByC_CCNI_CCPK(
		long companyId, long creatorClassNameId, long creatorClassPK);

	/**
	 * Returns the number of microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = any &#63;.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPKs the creator class pks
	 * @return the number of matching microblogs entries
	 */
	public int countByC_CCNI_CCPK(
		long companyId, long creatorClassNameId, long[] creatorClassPKs);

	/**
	 * Returns all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 * @return the matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByC_CCNI_T(
		long companyId, long creatorClassNameId, int type);

	/**
	 * Returns a range of all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @return the range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByC_CCNI_T(
		long companyId, long creatorClassNameId, int type, int start, int end);

	/**
	 * Returns an ordered range of all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByC_CCNI_T(
		long companyId, long creatorClassNameId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByC_CCNI_T(
		long companyId, long creatorClassNameId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first microblogs entry in the ordered set where companyId = &#63; and creatorClassNameId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry findByC_CCNI_T_First(
			long companyId, long creatorClassNameId, int type,
			com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first microblogs entry in the ordered set where companyId = &#63; and creatorClassNameId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry fetchByC_CCNI_T_First(
		long companyId, long creatorClassNameId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator);

	/**
	 * Returns the last microblogs entry in the ordered set where companyId = &#63; and creatorClassNameId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry findByC_CCNI_T_Last(
			long companyId, long creatorClassNameId, int type,
			com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last microblogs entry in the ordered set where companyId = &#63; and creatorClassNameId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry fetchByC_CCNI_T_Last(
		long companyId, long creatorClassNameId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator);

	/**
	 * Returns the microblogs entries before and after the current microblogs entry in the ordered set where companyId = &#63; and creatorClassNameId = &#63; and type = &#63;.
	 *
	 * @param microblogsEntryId the primary key of the current microblogs entry
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next microblogs entry
	 * @throws NoSuchEntryException if a microblogs entry with the primary key could not be found
	 */
	public MicroblogsEntry[] findByC_CCNI_T_PrevAndNext(
			long microblogsEntryId, long companyId, long creatorClassNameId,
			int type,
			com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and type = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 */
	public void removeByC_CCNI_T(
		long companyId, long creatorClassNameId, int type);

	/**
	 * Returns the number of microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 * @return the number of matching microblogs entries
	 */
	public int countByC_CCNI_T(
		long companyId, long creatorClassNameId, int type);

	/**
	 * Returns all the microblogs entries where creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @return the matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByCCNI_CCPK_T(
		long creatorClassNameId, long creatorClassPK, int type);

	/**
	 * Returns a range of all the microblogs entries where creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @return the range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByCCNI_CCPK_T(
		long creatorClassNameId, long creatorClassPK, int type, int start,
		int end);

	/**
	 * Returns an ordered range of all the microblogs entries where creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByCCNI_CCPK_T(
		long creatorClassNameId, long creatorClassPK, int type, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the microblogs entries where creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByCCNI_CCPK_T(
		long creatorClassNameId, long creatorClassPK, int type, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first microblogs entry in the ordered set where creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry findByCCNI_CCPK_T_First(
			long creatorClassNameId, long creatorClassPK, int type,
			com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first microblogs entry in the ordered set where creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry fetchByCCNI_CCPK_T_First(
		long creatorClassNameId, long creatorClassPK, int type,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator);

	/**
	 * Returns the last microblogs entry in the ordered set where creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry findByCCNI_CCPK_T_Last(
			long creatorClassNameId, long creatorClassPK, int type,
			com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last microblogs entry in the ordered set where creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry fetchByCCNI_CCPK_T_Last(
		long creatorClassNameId, long creatorClassPK, int type,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator);

	/**
	 * Returns the microblogs entries before and after the current microblogs entry in the ordered set where creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;.
	 *
	 * @param microblogsEntryId the primary key of the current microblogs entry
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next microblogs entry
	 * @throws NoSuchEntryException if a microblogs entry with the primary key could not be found
	 */
	public MicroblogsEntry[] findByCCNI_CCPK_T_PrevAndNext(
			long microblogsEntryId, long creatorClassNameId,
			long creatorClassPK, int type,
			com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns all the microblogs entries where creatorClassNameId = &#63; and creatorClassPK = any &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPKs the creator class pks
	 * @param type the type
	 * @return the matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByCCNI_CCPK_T(
		long creatorClassNameId, long[] creatorClassPKs, int type);

	/**
	 * Returns a range of all the microblogs entries where creatorClassNameId = &#63; and creatorClassPK = any &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPKs the creator class pks
	 * @param type the type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @return the range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByCCNI_CCPK_T(
		long creatorClassNameId, long[] creatorClassPKs, int type, int start,
		int end);

	/**
	 * Returns an ordered range of all the microblogs entries where creatorClassNameId = &#63; and creatorClassPK = any &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPKs the creator class pks
	 * @param type the type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByCCNI_CCPK_T(
		long creatorClassNameId, long[] creatorClassPKs, int type, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the microblogs entries where creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByCCNI_CCPK_T(
		long creatorClassNameId, long[] creatorClassPKs, int type, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the microblogs entries where creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63; from the database.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 */
	public void removeByCCNI_CCPK_T(
		long creatorClassNameId, long creatorClassPK, int type);

	/**
	 * Returns the number of microblogs entries where creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @return the number of matching microblogs entries
	 */
	public int countByCCNI_CCPK_T(
		long creatorClassNameId, long creatorClassPK, int type);

	/**
	 * Returns the number of microblogs entries where creatorClassNameId = &#63; and creatorClassPK = any &#63; and type = &#63;.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPKs the creator class pks
	 * @param type the type
	 * @return the number of matching microblogs entries
	 */
	public int countByCCNI_CCPK_T(
		long creatorClassNameId, long[] creatorClassPKs, int type);

	/**
	 * Returns all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @return the matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByC_CCNI_CCPK_T(
		long companyId, long creatorClassNameId, long creatorClassPK, int type);

	/**
	 * Returns a range of all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @return the range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByC_CCNI_CCPK_T(
		long companyId, long creatorClassNameId, long creatorClassPK, int type,
		int start, int end);

	/**
	 * Returns an ordered range of all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByC_CCNI_CCPK_T(
		long companyId, long creatorClassNameId, long creatorClassPK, int type,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByC_CCNI_CCPK_T(
		long companyId, long creatorClassNameId, long creatorClassPK, int type,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first microblogs entry in the ordered set where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry findByC_CCNI_CCPK_T_First(
			long companyId, long creatorClassNameId, long creatorClassPK,
			int type,
			com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first microblogs entry in the ordered set where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry fetchByC_CCNI_CCPK_T_First(
		long companyId, long creatorClassNameId, long creatorClassPK, int type,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator);

	/**
	 * Returns the last microblogs entry in the ordered set where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry findByC_CCNI_CCPK_T_Last(
			long companyId, long creatorClassNameId, long creatorClassPK,
			int type,
			com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last microblogs entry in the ordered set where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry fetchByC_CCNI_CCPK_T_Last(
		long companyId, long creatorClassNameId, long creatorClassPK, int type,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator);

	/**
	 * Returns the microblogs entries before and after the current microblogs entry in the ordered set where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;.
	 *
	 * @param microblogsEntryId the primary key of the current microblogs entry
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next microblogs entry
	 * @throws NoSuchEntryException if a microblogs entry with the primary key could not be found
	 */
	public MicroblogsEntry[] findByC_CCNI_CCPK_T_PrevAndNext(
			long microblogsEntryId, long companyId, long creatorClassNameId,
			long creatorClassPK, int type,
			com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = any &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPKs the creator class pks
	 * @param type the type
	 * @return the matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByC_CCNI_CCPK_T(
		long companyId, long creatorClassNameId, long[] creatorClassPKs,
		int type);

	/**
	 * Returns a range of all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = any &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPKs the creator class pks
	 * @param type the type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @return the range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByC_CCNI_CCPK_T(
		long companyId, long creatorClassNameId, long[] creatorClassPKs,
		int type, int start, int end);

	/**
	 * Returns an ordered range of all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = any &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPKs the creator class pks
	 * @param type the type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByC_CCNI_CCPK_T(
		long companyId, long creatorClassNameId, long[] creatorClassPKs,
		int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByC_CCNI_CCPK_T(
		long companyId, long creatorClassNameId, long[] creatorClassPKs,
		int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 */
	public void removeByC_CCNI_CCPK_T(
		long companyId, long creatorClassNameId, long creatorClassPK, int type);

	/**
	 * Returns the number of microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @return the number of matching microblogs entries
	 */
	public int countByC_CCNI_CCPK_T(
		long companyId, long creatorClassNameId, long creatorClassPK, int type);

	/**
	 * Returns the number of microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = any &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPKs the creator class pks
	 * @param type the type
	 * @return the number of matching microblogs entries
	 */
	public int countByC_CCNI_CCPK_T(
		long companyId, long creatorClassNameId, long[] creatorClassPKs,
		int type);

	/**
	 * Returns all the microblogs entries where userId = &#63; and createDate = &#63; and type = &#63; and socialRelationType = &#63;.
	 *
	 * @param userId the user ID
	 * @param createDate the create date
	 * @param type the type
	 * @param socialRelationType the social relation type
	 * @return the matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByU_C_T_S(
		long userId, Date createDate, int type, int socialRelationType);

	/**
	 * Returns a range of all the microblogs entries where userId = &#63; and createDate = &#63; and type = &#63; and socialRelationType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param createDate the create date
	 * @param type the type
	 * @param socialRelationType the social relation type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @return the range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByU_C_T_S(
		long userId, Date createDate, int type, int socialRelationType,
		int start, int end);

	/**
	 * Returns an ordered range of all the microblogs entries where userId = &#63; and createDate = &#63; and type = &#63; and socialRelationType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param createDate the create date
	 * @param type the type
	 * @param socialRelationType the social relation type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByU_C_T_S(
		long userId, Date createDate, int type, int socialRelationType,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the microblogs entries where userId = &#63; and createDate = &#63; and type = &#63; and socialRelationType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param createDate the create date
	 * @param type the type
	 * @param socialRelationType the social relation type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findByU_C_T_S(
		long userId, Date createDate, int type, int socialRelationType,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first microblogs entry in the ordered set where userId = &#63; and createDate = &#63; and type = &#63; and socialRelationType = &#63;.
	 *
	 * @param userId the user ID
	 * @param createDate the create date
	 * @param type the type
	 * @param socialRelationType the social relation type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry findByU_C_T_S_First(
			long userId, Date createDate, int type, int socialRelationType,
			com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first microblogs entry in the ordered set where userId = &#63; and createDate = &#63; and type = &#63; and socialRelationType = &#63;.
	 *
	 * @param userId the user ID
	 * @param createDate the create date
	 * @param type the type
	 * @param socialRelationType the social relation type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry fetchByU_C_T_S_First(
		long userId, Date createDate, int type, int socialRelationType,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator);

	/**
	 * Returns the last microblogs entry in the ordered set where userId = &#63; and createDate = &#63; and type = &#63; and socialRelationType = &#63;.
	 *
	 * @param userId the user ID
	 * @param createDate the create date
	 * @param type the type
	 * @param socialRelationType the social relation type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry findByU_C_T_S_Last(
			long userId, Date createDate, int type, int socialRelationType,
			com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last microblogs entry in the ordered set where userId = &#63; and createDate = &#63; and type = &#63; and socialRelationType = &#63;.
	 *
	 * @param userId the user ID
	 * @param createDate the create date
	 * @param type the type
	 * @param socialRelationType the social relation type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	public MicroblogsEntry fetchByU_C_T_S_Last(
		long userId, Date createDate, int type, int socialRelationType,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator);

	/**
	 * Returns the microblogs entries before and after the current microblogs entry in the ordered set where userId = &#63; and createDate = &#63; and type = &#63; and socialRelationType = &#63;.
	 *
	 * @param microblogsEntryId the primary key of the current microblogs entry
	 * @param userId the user ID
	 * @param createDate the create date
	 * @param type the type
	 * @param socialRelationType the social relation type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next microblogs entry
	 * @throws NoSuchEntryException if a microblogs entry with the primary key could not be found
	 */
	public MicroblogsEntry[] findByU_C_T_S_PrevAndNext(
			long microblogsEntryId, long userId, Date createDate, int type,
			int socialRelationType,
			com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the microblogs entries where userId = &#63; and createDate = &#63; and type = &#63; and socialRelationType = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param createDate the create date
	 * @param type the type
	 * @param socialRelationType the social relation type
	 */
	public void removeByU_C_T_S(
		long userId, Date createDate, int type, int socialRelationType);

	/**
	 * Returns the number of microblogs entries where userId = &#63; and createDate = &#63; and type = &#63; and socialRelationType = &#63;.
	 *
	 * @param userId the user ID
	 * @param createDate the create date
	 * @param type the type
	 * @param socialRelationType the social relation type
	 * @return the number of matching microblogs entries
	 */
	public int countByU_C_T_S(
		long userId, Date createDate, int type, int socialRelationType);

	/**
	 * Caches the microblogs entry in the entity cache if it is enabled.
	 *
	 * @param microblogsEntry the microblogs entry
	 */
	public void cacheResult(MicroblogsEntry microblogsEntry);

	/**
	 * Caches the microblogs entries in the entity cache if it is enabled.
	 *
	 * @param microblogsEntries the microblogs entries
	 */
	public void cacheResult(java.util.List<MicroblogsEntry> microblogsEntries);

	/**
	 * Creates a new microblogs entry with the primary key. Does not add the microblogs entry to the database.
	 *
	 * @param microblogsEntryId the primary key for the new microblogs entry
	 * @return the new microblogs entry
	 */
	public MicroblogsEntry create(long microblogsEntryId);

	/**
	 * Removes the microblogs entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param microblogsEntryId the primary key of the microblogs entry
	 * @return the microblogs entry that was removed
	 * @throws NoSuchEntryException if a microblogs entry with the primary key could not be found
	 */
	public MicroblogsEntry remove(long microblogsEntryId)
		throws NoSuchEntryException;

	public MicroblogsEntry updateImpl(MicroblogsEntry microblogsEntry);

	/**
	 * Returns the microblogs entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param microblogsEntryId the primary key of the microblogs entry
	 * @return the microblogs entry
	 * @throws NoSuchEntryException if a microblogs entry with the primary key could not be found
	 */
	public MicroblogsEntry findByPrimaryKey(long microblogsEntryId)
		throws NoSuchEntryException;

	/**
	 * Returns the microblogs entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param microblogsEntryId the primary key of the microblogs entry
	 * @return the microblogs entry, or <code>null</code> if a microblogs entry with the primary key could not be found
	 */
	public MicroblogsEntry fetchByPrimaryKey(long microblogsEntryId);

	/**
	 * Returns all the microblogs entries.
	 *
	 * @return the microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findAll();

	/**
	 * Returns a range of all the microblogs entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @return the range of microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the microblogs entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the microblogs entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of microblogs entries
	 */
	public java.util.List<MicroblogsEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MicroblogsEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the microblogs entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of microblogs entries.
	 *
	 * @return the number of microblogs entries
	 */
	public int countAll();

}