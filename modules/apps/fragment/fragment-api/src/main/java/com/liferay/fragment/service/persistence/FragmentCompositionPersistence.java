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

package com.liferay.fragment.service.persistence;

import com.liferay.fragment.exception.NoSuchCompositionException;
import com.liferay.fragment.model.FragmentComposition;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the fragment composition service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FragmentCompositionUtil
 * @generated
 */
@ProviderType
public interface FragmentCompositionPersistence
	extends BasePersistence<FragmentComposition>,
			CTPersistence<FragmentComposition> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link FragmentCompositionUtil} to access the fragment composition persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the fragment compositions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching fragment compositions
	 */
	public java.util.List<FragmentComposition> findByUuid(String uuid);

	/**
	 * Returns a range of all the fragment compositions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCompositionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fragment compositions
	 * @param end the upper bound of the range of fragment compositions (not inclusive)
	 * @return the range of matching fragment compositions
	 */
	public java.util.List<FragmentComposition> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the fragment compositions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCompositionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fragment compositions
	 * @param end the upper bound of the range of fragment compositions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment compositions
	 */
	public java.util.List<FragmentComposition> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentComposition>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment compositions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCompositionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fragment compositions
	 * @param end the upper bound of the range of fragment compositions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment compositions
	 */
	public java.util.List<FragmentComposition> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentComposition>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment composition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment composition
	 * @throws NoSuchCompositionException if a matching fragment composition could not be found
	 */
	public FragmentComposition findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentComposition> orderByComparator)
		throws NoSuchCompositionException;

	/**
	 * Returns the first fragment composition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment composition, or <code>null</code> if a matching fragment composition could not be found
	 */
	public FragmentComposition fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentComposition>
			orderByComparator);

	/**
	 * Returns the last fragment composition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment composition
	 * @throws NoSuchCompositionException if a matching fragment composition could not be found
	 */
	public FragmentComposition findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentComposition> orderByComparator)
		throws NoSuchCompositionException;

	/**
	 * Returns the last fragment composition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment composition, or <code>null</code> if a matching fragment composition could not be found
	 */
	public FragmentComposition fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentComposition>
			orderByComparator);

	/**
	 * Returns the fragment compositions before and after the current fragment composition in the ordered set where uuid = &#63;.
	 *
	 * @param fragmentCompositionId the primary key of the current fragment composition
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment composition
	 * @throws NoSuchCompositionException if a fragment composition with the primary key could not be found
	 */
	public FragmentComposition[] findByUuid_PrevAndNext(
			long fragmentCompositionId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentComposition> orderByComparator)
		throws NoSuchCompositionException;

	/**
	 * Removes all the fragment compositions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of fragment compositions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching fragment compositions
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the fragment composition where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchCompositionException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching fragment composition
	 * @throws NoSuchCompositionException if a matching fragment composition could not be found
	 */
	public FragmentComposition findByUUID_G(String uuid, long groupId)
		throws NoSuchCompositionException;

	/**
	 * Returns the fragment composition where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching fragment composition, or <code>null</code> if a matching fragment composition could not be found
	 */
	public FragmentComposition fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the fragment composition where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching fragment composition, or <code>null</code> if a matching fragment composition could not be found
	 */
	public FragmentComposition fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the fragment composition where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the fragment composition that was removed
	 */
	public FragmentComposition removeByUUID_G(String uuid, long groupId)
		throws NoSuchCompositionException;

	/**
	 * Returns the number of fragment compositions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching fragment compositions
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the fragment compositions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching fragment compositions
	 */
	public java.util.List<FragmentComposition> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the fragment compositions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCompositionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fragment compositions
	 * @param end the upper bound of the range of fragment compositions (not inclusive)
	 * @return the range of matching fragment compositions
	 */
	public java.util.List<FragmentComposition> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the fragment compositions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCompositionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fragment compositions
	 * @param end the upper bound of the range of fragment compositions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment compositions
	 */
	public java.util.List<FragmentComposition> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentComposition>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment compositions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCompositionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fragment compositions
	 * @param end the upper bound of the range of fragment compositions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment compositions
	 */
	public java.util.List<FragmentComposition> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentComposition>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment composition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment composition
	 * @throws NoSuchCompositionException if a matching fragment composition could not be found
	 */
	public FragmentComposition findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentComposition> orderByComparator)
		throws NoSuchCompositionException;

	/**
	 * Returns the first fragment composition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment composition, or <code>null</code> if a matching fragment composition could not be found
	 */
	public FragmentComposition fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentComposition>
			orderByComparator);

	/**
	 * Returns the last fragment composition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment composition
	 * @throws NoSuchCompositionException if a matching fragment composition could not be found
	 */
	public FragmentComposition findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentComposition> orderByComparator)
		throws NoSuchCompositionException;

	/**
	 * Returns the last fragment composition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment composition, or <code>null</code> if a matching fragment composition could not be found
	 */
	public FragmentComposition fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentComposition>
			orderByComparator);

	/**
	 * Returns the fragment compositions before and after the current fragment composition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param fragmentCompositionId the primary key of the current fragment composition
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment composition
	 * @throws NoSuchCompositionException if a fragment composition with the primary key could not be found
	 */
	public FragmentComposition[] findByUuid_C_PrevAndNext(
			long fragmentCompositionId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentComposition> orderByComparator)
		throws NoSuchCompositionException;

	/**
	 * Removes all the fragment compositions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of fragment compositions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching fragment compositions
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the fragment compositions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching fragment compositions
	 */
	public java.util.List<FragmentComposition> findByGroupId(long groupId);

	/**
	 * Returns a range of all the fragment compositions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCompositionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment compositions
	 * @param end the upper bound of the range of fragment compositions (not inclusive)
	 * @return the range of matching fragment compositions
	 */
	public java.util.List<FragmentComposition> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the fragment compositions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCompositionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment compositions
	 * @param end the upper bound of the range of fragment compositions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment compositions
	 */
	public java.util.List<FragmentComposition> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentComposition>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment compositions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCompositionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment compositions
	 * @param end the upper bound of the range of fragment compositions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment compositions
	 */
	public java.util.List<FragmentComposition> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentComposition>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment composition in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment composition
	 * @throws NoSuchCompositionException if a matching fragment composition could not be found
	 */
	public FragmentComposition findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentComposition> orderByComparator)
		throws NoSuchCompositionException;

	/**
	 * Returns the first fragment composition in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment composition, or <code>null</code> if a matching fragment composition could not be found
	 */
	public FragmentComposition fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentComposition>
			orderByComparator);

	/**
	 * Returns the last fragment composition in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment composition
	 * @throws NoSuchCompositionException if a matching fragment composition could not be found
	 */
	public FragmentComposition findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentComposition> orderByComparator)
		throws NoSuchCompositionException;

	/**
	 * Returns the last fragment composition in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment composition, or <code>null</code> if a matching fragment composition could not be found
	 */
	public FragmentComposition fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentComposition>
			orderByComparator);

	/**
	 * Returns the fragment compositions before and after the current fragment composition in the ordered set where groupId = &#63;.
	 *
	 * @param fragmentCompositionId the primary key of the current fragment composition
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment composition
	 * @throws NoSuchCompositionException if a fragment composition with the primary key could not be found
	 */
	public FragmentComposition[] findByGroupId_PrevAndNext(
			long fragmentCompositionId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentComposition> orderByComparator)
		throws NoSuchCompositionException;

	/**
	 * Removes all the fragment compositions where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of fragment compositions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching fragment compositions
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns all the fragment compositions where fragmentCollectionId = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @return the matching fragment compositions
	 */
	public java.util.List<FragmentComposition> findByFragmentCollectionId(
		long fragmentCollectionId);

	/**
	 * Returns a range of all the fragment compositions where fragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCompositionModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param start the lower bound of the range of fragment compositions
	 * @param end the upper bound of the range of fragment compositions (not inclusive)
	 * @return the range of matching fragment compositions
	 */
	public java.util.List<FragmentComposition> findByFragmentCollectionId(
		long fragmentCollectionId, int start, int end);

	/**
	 * Returns an ordered range of all the fragment compositions where fragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCompositionModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param start the lower bound of the range of fragment compositions
	 * @param end the upper bound of the range of fragment compositions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment compositions
	 */
	public java.util.List<FragmentComposition> findByFragmentCollectionId(
		long fragmentCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentComposition>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment compositions where fragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCompositionModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param start the lower bound of the range of fragment compositions
	 * @param end the upper bound of the range of fragment compositions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment compositions
	 */
	public java.util.List<FragmentComposition> findByFragmentCollectionId(
		long fragmentCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentComposition>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment composition in the ordered set where fragmentCollectionId = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment composition
	 * @throws NoSuchCompositionException if a matching fragment composition could not be found
	 */
	public FragmentComposition findByFragmentCollectionId_First(
			long fragmentCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentComposition> orderByComparator)
		throws NoSuchCompositionException;

	/**
	 * Returns the first fragment composition in the ordered set where fragmentCollectionId = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment composition, or <code>null</code> if a matching fragment composition could not be found
	 */
	public FragmentComposition fetchByFragmentCollectionId_First(
		long fragmentCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentComposition>
			orderByComparator);

	/**
	 * Returns the last fragment composition in the ordered set where fragmentCollectionId = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment composition
	 * @throws NoSuchCompositionException if a matching fragment composition could not be found
	 */
	public FragmentComposition findByFragmentCollectionId_Last(
			long fragmentCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentComposition> orderByComparator)
		throws NoSuchCompositionException;

	/**
	 * Returns the last fragment composition in the ordered set where fragmentCollectionId = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment composition, or <code>null</code> if a matching fragment composition could not be found
	 */
	public FragmentComposition fetchByFragmentCollectionId_Last(
		long fragmentCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentComposition>
			orderByComparator);

	/**
	 * Returns the fragment compositions before and after the current fragment composition in the ordered set where fragmentCollectionId = &#63;.
	 *
	 * @param fragmentCompositionId the primary key of the current fragment composition
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment composition
	 * @throws NoSuchCompositionException if a fragment composition with the primary key could not be found
	 */
	public FragmentComposition[] findByFragmentCollectionId_PrevAndNext(
			long fragmentCompositionId, long fragmentCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentComposition> orderByComparator)
		throws NoSuchCompositionException;

	/**
	 * Removes all the fragment compositions where fragmentCollectionId = &#63; from the database.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 */
	public void removeByFragmentCollectionId(long fragmentCollectionId);

	/**
	 * Returns the number of fragment compositions where fragmentCollectionId = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @return the number of matching fragment compositions
	 */
	public int countByFragmentCollectionId(long fragmentCollectionId);

	/**
	 * Returns all the fragment compositions where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @return the matching fragment compositions
	 */
	public java.util.List<FragmentComposition> findByG_FCI(
		long groupId, long fragmentCollectionId);

	/**
	 * Returns a range of all the fragment compositions where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCompositionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param start the lower bound of the range of fragment compositions
	 * @param end the upper bound of the range of fragment compositions (not inclusive)
	 * @return the range of matching fragment compositions
	 */
	public java.util.List<FragmentComposition> findByG_FCI(
		long groupId, long fragmentCollectionId, int start, int end);

	/**
	 * Returns an ordered range of all the fragment compositions where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCompositionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param start the lower bound of the range of fragment compositions
	 * @param end the upper bound of the range of fragment compositions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment compositions
	 */
	public java.util.List<FragmentComposition> findByG_FCI(
		long groupId, long fragmentCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentComposition>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment compositions where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCompositionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param start the lower bound of the range of fragment compositions
	 * @param end the upper bound of the range of fragment compositions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment compositions
	 */
	public java.util.List<FragmentComposition> findByG_FCI(
		long groupId, long fragmentCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentComposition>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment composition in the ordered set where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment composition
	 * @throws NoSuchCompositionException if a matching fragment composition could not be found
	 */
	public FragmentComposition findByG_FCI_First(
			long groupId, long fragmentCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentComposition> orderByComparator)
		throws NoSuchCompositionException;

	/**
	 * Returns the first fragment composition in the ordered set where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment composition, or <code>null</code> if a matching fragment composition could not be found
	 */
	public FragmentComposition fetchByG_FCI_First(
		long groupId, long fragmentCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentComposition>
			orderByComparator);

	/**
	 * Returns the last fragment composition in the ordered set where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment composition
	 * @throws NoSuchCompositionException if a matching fragment composition could not be found
	 */
	public FragmentComposition findByG_FCI_Last(
			long groupId, long fragmentCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentComposition> orderByComparator)
		throws NoSuchCompositionException;

	/**
	 * Returns the last fragment composition in the ordered set where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment composition, or <code>null</code> if a matching fragment composition could not be found
	 */
	public FragmentComposition fetchByG_FCI_Last(
		long groupId, long fragmentCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentComposition>
			orderByComparator);

	/**
	 * Returns the fragment compositions before and after the current fragment composition in the ordered set where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * @param fragmentCompositionId the primary key of the current fragment composition
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment composition
	 * @throws NoSuchCompositionException if a fragment composition with the primary key could not be found
	 */
	public FragmentComposition[] findByG_FCI_PrevAndNext(
			long fragmentCompositionId, long groupId, long fragmentCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentComposition> orderByComparator)
		throws NoSuchCompositionException;

	/**
	 * Removes all the fragment compositions where groupId = &#63; and fragmentCollectionId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 */
	public void removeByG_FCI(long groupId, long fragmentCollectionId);

	/**
	 * Returns the number of fragment compositions where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @return the number of matching fragment compositions
	 */
	public int countByG_FCI(long groupId, long fragmentCollectionId);

	/**
	 * Returns the fragment composition where groupId = &#63; and fragmentCompositionKey = &#63; or throws a <code>NoSuchCompositionException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param fragmentCompositionKey the fragment composition key
	 * @return the matching fragment composition
	 * @throws NoSuchCompositionException if a matching fragment composition could not be found
	 */
	public FragmentComposition findByG_FCK(
			long groupId, String fragmentCompositionKey)
		throws NoSuchCompositionException;

	/**
	 * Returns the fragment composition where groupId = &#63; and fragmentCompositionKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param fragmentCompositionKey the fragment composition key
	 * @return the matching fragment composition, or <code>null</code> if a matching fragment composition could not be found
	 */
	public FragmentComposition fetchByG_FCK(
		long groupId, String fragmentCompositionKey);

	/**
	 * Returns the fragment composition where groupId = &#63; and fragmentCompositionKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param fragmentCompositionKey the fragment composition key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching fragment composition, or <code>null</code> if a matching fragment composition could not be found
	 */
	public FragmentComposition fetchByG_FCK(
		long groupId, String fragmentCompositionKey, boolean useFinderCache);

	/**
	 * Removes the fragment composition where groupId = &#63; and fragmentCompositionKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCompositionKey the fragment composition key
	 * @return the fragment composition that was removed
	 */
	public FragmentComposition removeByG_FCK(
			long groupId, String fragmentCompositionKey)
		throws NoSuchCompositionException;

	/**
	 * Returns the number of fragment compositions where groupId = &#63; and fragmentCompositionKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCompositionKey the fragment composition key
	 * @return the number of matching fragment compositions
	 */
	public int countByG_FCK(long groupId, String fragmentCompositionKey);

	/**
	 * Returns all the fragment compositions where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @return the matching fragment compositions
	 */
	public java.util.List<FragmentComposition> findByG_FCI_LikeN(
		long groupId, long fragmentCollectionId, String name);

	/**
	 * Returns a range of all the fragment compositions where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCompositionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param start the lower bound of the range of fragment compositions
	 * @param end the upper bound of the range of fragment compositions (not inclusive)
	 * @return the range of matching fragment compositions
	 */
	public java.util.List<FragmentComposition> findByG_FCI_LikeN(
		long groupId, long fragmentCollectionId, String name, int start,
		int end);

	/**
	 * Returns an ordered range of all the fragment compositions where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCompositionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param start the lower bound of the range of fragment compositions
	 * @param end the upper bound of the range of fragment compositions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment compositions
	 */
	public java.util.List<FragmentComposition> findByG_FCI_LikeN(
		long groupId, long fragmentCollectionId, String name, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentComposition>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment compositions where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCompositionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param start the lower bound of the range of fragment compositions
	 * @param end the upper bound of the range of fragment compositions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment compositions
	 */
	public java.util.List<FragmentComposition> findByG_FCI_LikeN(
		long groupId, long fragmentCollectionId, String name, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentComposition>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment composition in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment composition
	 * @throws NoSuchCompositionException if a matching fragment composition could not be found
	 */
	public FragmentComposition findByG_FCI_LikeN_First(
			long groupId, long fragmentCollectionId, String name,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentComposition> orderByComparator)
		throws NoSuchCompositionException;

	/**
	 * Returns the first fragment composition in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment composition, or <code>null</code> if a matching fragment composition could not be found
	 */
	public FragmentComposition fetchByG_FCI_LikeN_First(
		long groupId, long fragmentCollectionId, String name,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentComposition>
			orderByComparator);

	/**
	 * Returns the last fragment composition in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment composition
	 * @throws NoSuchCompositionException if a matching fragment composition could not be found
	 */
	public FragmentComposition findByG_FCI_LikeN_Last(
			long groupId, long fragmentCollectionId, String name,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentComposition> orderByComparator)
		throws NoSuchCompositionException;

	/**
	 * Returns the last fragment composition in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment composition, or <code>null</code> if a matching fragment composition could not be found
	 */
	public FragmentComposition fetchByG_FCI_LikeN_Last(
		long groupId, long fragmentCollectionId, String name,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentComposition>
			orderByComparator);

	/**
	 * Returns the fragment compositions before and after the current fragment composition in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param fragmentCompositionId the primary key of the current fragment composition
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment composition
	 * @throws NoSuchCompositionException if a fragment composition with the primary key could not be found
	 */
	public FragmentComposition[] findByG_FCI_LikeN_PrevAndNext(
			long fragmentCompositionId, long groupId, long fragmentCollectionId,
			String name,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentComposition> orderByComparator)
		throws NoSuchCompositionException;

	/**
	 * Removes all the fragment compositions where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 */
	public void removeByG_FCI_LikeN(
		long groupId, long fragmentCollectionId, String name);

	/**
	 * Returns the number of fragment compositions where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @return the number of matching fragment compositions
	 */
	public int countByG_FCI_LikeN(
		long groupId, long fragmentCollectionId, String name);

	/**
	 * Returns all the fragment compositions where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @return the matching fragment compositions
	 */
	public java.util.List<FragmentComposition> findByG_FCI_S(
		long groupId, long fragmentCollectionId, int status);

	/**
	 * Returns a range of all the fragment compositions where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCompositionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param start the lower bound of the range of fragment compositions
	 * @param end the upper bound of the range of fragment compositions (not inclusive)
	 * @return the range of matching fragment compositions
	 */
	public java.util.List<FragmentComposition> findByG_FCI_S(
		long groupId, long fragmentCollectionId, int status, int start,
		int end);

	/**
	 * Returns an ordered range of all the fragment compositions where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCompositionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param start the lower bound of the range of fragment compositions
	 * @param end the upper bound of the range of fragment compositions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment compositions
	 */
	public java.util.List<FragmentComposition> findByG_FCI_S(
		long groupId, long fragmentCollectionId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentComposition>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment compositions where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCompositionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param start the lower bound of the range of fragment compositions
	 * @param end the upper bound of the range of fragment compositions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment compositions
	 */
	public java.util.List<FragmentComposition> findByG_FCI_S(
		long groupId, long fragmentCollectionId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentComposition>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment composition in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment composition
	 * @throws NoSuchCompositionException if a matching fragment composition could not be found
	 */
	public FragmentComposition findByG_FCI_S_First(
			long groupId, long fragmentCollectionId, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentComposition> orderByComparator)
		throws NoSuchCompositionException;

	/**
	 * Returns the first fragment composition in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment composition, or <code>null</code> if a matching fragment composition could not be found
	 */
	public FragmentComposition fetchByG_FCI_S_First(
		long groupId, long fragmentCollectionId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentComposition>
			orderByComparator);

	/**
	 * Returns the last fragment composition in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment composition
	 * @throws NoSuchCompositionException if a matching fragment composition could not be found
	 */
	public FragmentComposition findByG_FCI_S_Last(
			long groupId, long fragmentCollectionId, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentComposition> orderByComparator)
		throws NoSuchCompositionException;

	/**
	 * Returns the last fragment composition in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment composition, or <code>null</code> if a matching fragment composition could not be found
	 */
	public FragmentComposition fetchByG_FCI_S_Last(
		long groupId, long fragmentCollectionId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentComposition>
			orderByComparator);

	/**
	 * Returns the fragment compositions before and after the current fragment composition in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * @param fragmentCompositionId the primary key of the current fragment composition
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment composition
	 * @throws NoSuchCompositionException if a fragment composition with the primary key could not be found
	 */
	public FragmentComposition[] findByG_FCI_S_PrevAndNext(
			long fragmentCompositionId, long groupId, long fragmentCollectionId,
			int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentComposition> orderByComparator)
		throws NoSuchCompositionException;

	/**
	 * Removes all the fragment compositions where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 */
	public void removeByG_FCI_S(
		long groupId, long fragmentCollectionId, int status);

	/**
	 * Returns the number of fragment compositions where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @return the number of matching fragment compositions
	 */
	public int countByG_FCI_S(
		long groupId, long fragmentCollectionId, int status);

	/**
	 * Returns all the fragment compositions where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @return the matching fragment compositions
	 */
	public java.util.List<FragmentComposition> findByG_FCI_LikeN_S(
		long groupId, long fragmentCollectionId, String name, int status);

	/**
	 * Returns a range of all the fragment compositions where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCompositionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param start the lower bound of the range of fragment compositions
	 * @param end the upper bound of the range of fragment compositions (not inclusive)
	 * @return the range of matching fragment compositions
	 */
	public java.util.List<FragmentComposition> findByG_FCI_LikeN_S(
		long groupId, long fragmentCollectionId, String name, int status,
		int start, int end);

	/**
	 * Returns an ordered range of all the fragment compositions where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCompositionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param start the lower bound of the range of fragment compositions
	 * @param end the upper bound of the range of fragment compositions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment compositions
	 */
	public java.util.List<FragmentComposition> findByG_FCI_LikeN_S(
		long groupId, long fragmentCollectionId, String name, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentComposition>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment compositions where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCompositionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param start the lower bound of the range of fragment compositions
	 * @param end the upper bound of the range of fragment compositions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment compositions
	 */
	public java.util.List<FragmentComposition> findByG_FCI_LikeN_S(
		long groupId, long fragmentCollectionId, String name, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentComposition>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first fragment composition in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment composition
	 * @throws NoSuchCompositionException if a matching fragment composition could not be found
	 */
	public FragmentComposition findByG_FCI_LikeN_S_First(
			long groupId, long fragmentCollectionId, String name, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentComposition> orderByComparator)
		throws NoSuchCompositionException;

	/**
	 * Returns the first fragment composition in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment composition, or <code>null</code> if a matching fragment composition could not be found
	 */
	public FragmentComposition fetchByG_FCI_LikeN_S_First(
		long groupId, long fragmentCollectionId, String name, int status,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentComposition>
			orderByComparator);

	/**
	 * Returns the last fragment composition in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment composition
	 * @throws NoSuchCompositionException if a matching fragment composition could not be found
	 */
	public FragmentComposition findByG_FCI_LikeN_S_Last(
			long groupId, long fragmentCollectionId, String name, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentComposition> orderByComparator)
		throws NoSuchCompositionException;

	/**
	 * Returns the last fragment composition in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment composition, or <code>null</code> if a matching fragment composition could not be found
	 */
	public FragmentComposition fetchByG_FCI_LikeN_S_Last(
		long groupId, long fragmentCollectionId, String name, int status,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentComposition>
			orderByComparator);

	/**
	 * Returns the fragment compositions before and after the current fragment composition in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * @param fragmentCompositionId the primary key of the current fragment composition
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment composition
	 * @throws NoSuchCompositionException if a fragment composition with the primary key could not be found
	 */
	public FragmentComposition[] findByG_FCI_LikeN_S_PrevAndNext(
			long fragmentCompositionId, long groupId, long fragmentCollectionId,
			String name, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<FragmentComposition> orderByComparator)
		throws NoSuchCompositionException;

	/**
	 * Removes all the fragment compositions where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 */
	public void removeByG_FCI_LikeN_S(
		long groupId, long fragmentCollectionId, String name, int status);

	/**
	 * Returns the number of fragment compositions where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @return the number of matching fragment compositions
	 */
	public int countByG_FCI_LikeN_S(
		long groupId, long fragmentCollectionId, String name, int status);

	/**
	 * Caches the fragment composition in the entity cache if it is enabled.
	 *
	 * @param fragmentComposition the fragment composition
	 */
	public void cacheResult(FragmentComposition fragmentComposition);

	/**
	 * Caches the fragment compositions in the entity cache if it is enabled.
	 *
	 * @param fragmentCompositions the fragment compositions
	 */
	public void cacheResult(
		java.util.List<FragmentComposition> fragmentCompositions);

	/**
	 * Creates a new fragment composition with the primary key. Does not add the fragment composition to the database.
	 *
	 * @param fragmentCompositionId the primary key for the new fragment composition
	 * @return the new fragment composition
	 */
	public FragmentComposition create(long fragmentCompositionId);

	/**
	 * Removes the fragment composition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fragmentCompositionId the primary key of the fragment composition
	 * @return the fragment composition that was removed
	 * @throws NoSuchCompositionException if a fragment composition with the primary key could not be found
	 */
	public FragmentComposition remove(long fragmentCompositionId)
		throws NoSuchCompositionException;

	public FragmentComposition updateImpl(
		FragmentComposition fragmentComposition);

	/**
	 * Returns the fragment composition with the primary key or throws a <code>NoSuchCompositionException</code> if it could not be found.
	 *
	 * @param fragmentCompositionId the primary key of the fragment composition
	 * @return the fragment composition
	 * @throws NoSuchCompositionException if a fragment composition with the primary key could not be found
	 */
	public FragmentComposition findByPrimaryKey(long fragmentCompositionId)
		throws NoSuchCompositionException;

	/**
	 * Returns the fragment composition with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fragmentCompositionId the primary key of the fragment composition
	 * @return the fragment composition, or <code>null</code> if a fragment composition with the primary key could not be found
	 */
	public FragmentComposition fetchByPrimaryKey(long fragmentCompositionId);

	/**
	 * Returns all the fragment compositions.
	 *
	 * @return the fragment compositions
	 */
	public java.util.List<FragmentComposition> findAll();

	/**
	 * Returns a range of all the fragment compositions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCompositionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment compositions
	 * @param end the upper bound of the range of fragment compositions (not inclusive)
	 * @return the range of fragment compositions
	 */
	public java.util.List<FragmentComposition> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the fragment compositions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCompositionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment compositions
	 * @param end the upper bound of the range of fragment compositions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of fragment compositions
	 */
	public java.util.List<FragmentComposition> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentComposition>
			orderByComparator);

	/**
	 * Returns an ordered range of all the fragment compositions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentCompositionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment compositions
	 * @param end the upper bound of the range of fragment compositions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of fragment compositions
	 */
	public java.util.List<FragmentComposition> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentComposition>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the fragment compositions from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of fragment compositions.
	 *
	 * @return the number of fragment compositions
	 */
	public int countAll();

}