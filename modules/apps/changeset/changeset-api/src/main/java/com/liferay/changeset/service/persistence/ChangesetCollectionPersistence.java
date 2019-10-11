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

package com.liferay.changeset.service.persistence;

import com.liferay.changeset.exception.NoSuchCollectionException;
import com.liferay.changeset.model.ChangesetCollection;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the changeset collection service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ChangesetCollectionUtil
 * @generated
 */
@ProviderType
public interface ChangesetCollectionPersistence
	extends BasePersistence<ChangesetCollection> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ChangesetCollectionUtil} to access the changeset collection persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the changeset collections where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching changeset collections
	 */
	public java.util.List<ChangesetCollection> findByGroupId(long groupId);

	/**
	 * Returns a range of all the changeset collections where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @return the range of matching changeset collections
	 */
	public java.util.List<ChangesetCollection> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the changeset collections where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching changeset collections
	 */
	public java.util.List<ChangesetCollection> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetCollection>
			orderByComparator);

	/**
	 * Returns an ordered range of all the changeset collections where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching changeset collections
	 */
	public java.util.List<ChangesetCollection> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetCollection>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first changeset collection in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset collection
	 * @throws NoSuchCollectionException if a matching changeset collection could not be found
	 */
	public ChangesetCollection findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ChangesetCollection> orderByComparator)
		throws NoSuchCollectionException;

	/**
	 * Returns the first changeset collection in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset collection, or <code>null</code> if a matching changeset collection could not be found
	 */
	public ChangesetCollection fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetCollection>
			orderByComparator);

	/**
	 * Returns the last changeset collection in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching changeset collection
	 * @throws NoSuchCollectionException if a matching changeset collection could not be found
	 */
	public ChangesetCollection findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ChangesetCollection> orderByComparator)
		throws NoSuchCollectionException;

	/**
	 * Returns the last changeset collection in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching changeset collection, or <code>null</code> if a matching changeset collection could not be found
	 */
	public ChangesetCollection fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetCollection>
			orderByComparator);

	/**
	 * Returns the changeset collections before and after the current changeset collection in the ordered set where groupId = &#63;.
	 *
	 * @param changesetCollectionId the primary key of the current changeset collection
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next changeset collection
	 * @throws NoSuchCollectionException if a changeset collection with the primary key could not be found
	 */
	public ChangesetCollection[] findByGroupId_PrevAndNext(
			long changesetCollectionId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ChangesetCollection> orderByComparator)
		throws NoSuchCollectionException;

	/**
	 * Removes all the changeset collections where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of changeset collections where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching changeset collections
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns all the changeset collections where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching changeset collections
	 */
	public java.util.List<ChangesetCollection> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the changeset collections where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @return the range of matching changeset collections
	 */
	public java.util.List<ChangesetCollection> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the changeset collections where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching changeset collections
	 */
	public java.util.List<ChangesetCollection> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetCollection>
			orderByComparator);

	/**
	 * Returns an ordered range of all the changeset collections where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching changeset collections
	 */
	public java.util.List<ChangesetCollection> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetCollection>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first changeset collection in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset collection
	 * @throws NoSuchCollectionException if a matching changeset collection could not be found
	 */
	public ChangesetCollection findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ChangesetCollection> orderByComparator)
		throws NoSuchCollectionException;

	/**
	 * Returns the first changeset collection in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset collection, or <code>null</code> if a matching changeset collection could not be found
	 */
	public ChangesetCollection fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetCollection>
			orderByComparator);

	/**
	 * Returns the last changeset collection in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching changeset collection
	 * @throws NoSuchCollectionException if a matching changeset collection could not be found
	 */
	public ChangesetCollection findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ChangesetCollection> orderByComparator)
		throws NoSuchCollectionException;

	/**
	 * Returns the last changeset collection in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching changeset collection, or <code>null</code> if a matching changeset collection could not be found
	 */
	public ChangesetCollection fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetCollection>
			orderByComparator);

	/**
	 * Returns the changeset collections before and after the current changeset collection in the ordered set where companyId = &#63;.
	 *
	 * @param changesetCollectionId the primary key of the current changeset collection
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next changeset collection
	 * @throws NoSuchCollectionException if a changeset collection with the primary key could not be found
	 */
	public ChangesetCollection[] findByCompanyId_PrevAndNext(
			long changesetCollectionId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ChangesetCollection> orderByComparator)
		throws NoSuchCollectionException;

	/**
	 * Removes all the changeset collections where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of changeset collections where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching changeset collections
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns all the changeset collections where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching changeset collections
	 */
	public java.util.List<ChangesetCollection> findByG_U(
		long groupId, long userId);

	/**
	 * Returns a range of all the changeset collections where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @return the range of matching changeset collections
	 */
	public java.util.List<ChangesetCollection> findByG_U(
		long groupId, long userId, int start, int end);

	/**
	 * Returns an ordered range of all the changeset collections where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching changeset collections
	 */
	public java.util.List<ChangesetCollection> findByG_U(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetCollection>
			orderByComparator);

	/**
	 * Returns an ordered range of all the changeset collections where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching changeset collections
	 */
	public java.util.List<ChangesetCollection> findByG_U(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetCollection>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first changeset collection in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset collection
	 * @throws NoSuchCollectionException if a matching changeset collection could not be found
	 */
	public ChangesetCollection findByG_U_First(
			long groupId, long userId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ChangesetCollection> orderByComparator)
		throws NoSuchCollectionException;

	/**
	 * Returns the first changeset collection in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset collection, or <code>null</code> if a matching changeset collection could not be found
	 */
	public ChangesetCollection fetchByG_U_First(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetCollection>
			orderByComparator);

	/**
	 * Returns the last changeset collection in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching changeset collection
	 * @throws NoSuchCollectionException if a matching changeset collection could not be found
	 */
	public ChangesetCollection findByG_U_Last(
			long groupId, long userId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ChangesetCollection> orderByComparator)
		throws NoSuchCollectionException;

	/**
	 * Returns the last changeset collection in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching changeset collection, or <code>null</code> if a matching changeset collection could not be found
	 */
	public ChangesetCollection fetchByG_U_Last(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetCollection>
			orderByComparator);

	/**
	 * Returns the changeset collections before and after the current changeset collection in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param changesetCollectionId the primary key of the current changeset collection
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next changeset collection
	 * @throws NoSuchCollectionException if a changeset collection with the primary key could not be found
	 */
	public ChangesetCollection[] findByG_U_PrevAndNext(
			long changesetCollectionId, long groupId, long userId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ChangesetCollection> orderByComparator)
		throws NoSuchCollectionException;

	/**
	 * Removes all the changeset collections where groupId = &#63; and userId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 */
	public void removeByG_U(long groupId, long userId);

	/**
	 * Returns the number of changeset collections where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching changeset collections
	 */
	public int countByG_U(long groupId, long userId);

	/**
	 * Returns the changeset collection where groupId = &#63; and name = &#63; or throws a <code>NoSuchCollectionException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching changeset collection
	 * @throws NoSuchCollectionException if a matching changeset collection could not be found
	 */
	public ChangesetCollection findByG_N(long groupId, String name)
		throws NoSuchCollectionException;

	/**
	 * Returns the changeset collection where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching changeset collection, or <code>null</code> if a matching changeset collection could not be found
	 */
	public ChangesetCollection fetchByG_N(long groupId, String name);

	/**
	 * Returns the changeset collection where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching changeset collection, or <code>null</code> if a matching changeset collection could not be found
	 */
	public ChangesetCollection fetchByG_N(
		long groupId, String name, boolean useFinderCache);

	/**
	 * Removes the changeset collection where groupId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the changeset collection that was removed
	 */
	public ChangesetCollection removeByG_N(long groupId, String name)
		throws NoSuchCollectionException;

	/**
	 * Returns the number of changeset collections where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching changeset collections
	 */
	public int countByG_N(long groupId, String name);

	/**
	 * Returns all the changeset collections where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching changeset collections
	 */
	public java.util.List<ChangesetCollection> findByC_N(
		long companyId, String name);

	/**
	 * Returns a range of all the changeset collections where companyId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @return the range of matching changeset collections
	 */
	public java.util.List<ChangesetCollection> findByC_N(
		long companyId, String name, int start, int end);

	/**
	 * Returns an ordered range of all the changeset collections where companyId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching changeset collections
	 */
	public java.util.List<ChangesetCollection> findByC_N(
		long companyId, String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetCollection>
			orderByComparator);

	/**
	 * Returns an ordered range of all the changeset collections where companyId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching changeset collections
	 */
	public java.util.List<ChangesetCollection> findByC_N(
		long companyId, String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetCollection>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first changeset collection in the ordered set where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset collection
	 * @throws NoSuchCollectionException if a matching changeset collection could not be found
	 */
	public ChangesetCollection findByC_N_First(
			long companyId, String name,
			com.liferay.portal.kernel.util.OrderByComparator
				<ChangesetCollection> orderByComparator)
		throws NoSuchCollectionException;

	/**
	 * Returns the first changeset collection in the ordered set where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset collection, or <code>null</code> if a matching changeset collection could not be found
	 */
	public ChangesetCollection fetchByC_N_First(
		long companyId, String name,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetCollection>
			orderByComparator);

	/**
	 * Returns the last changeset collection in the ordered set where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching changeset collection
	 * @throws NoSuchCollectionException if a matching changeset collection could not be found
	 */
	public ChangesetCollection findByC_N_Last(
			long companyId, String name,
			com.liferay.portal.kernel.util.OrderByComparator
				<ChangesetCollection> orderByComparator)
		throws NoSuchCollectionException;

	/**
	 * Returns the last changeset collection in the ordered set where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching changeset collection, or <code>null</code> if a matching changeset collection could not be found
	 */
	public ChangesetCollection fetchByC_N_Last(
		long companyId, String name,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetCollection>
			orderByComparator);

	/**
	 * Returns the changeset collections before and after the current changeset collection in the ordered set where companyId = &#63; and name = &#63;.
	 *
	 * @param changesetCollectionId the primary key of the current changeset collection
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next changeset collection
	 * @throws NoSuchCollectionException if a changeset collection with the primary key could not be found
	 */
	public ChangesetCollection[] findByC_N_PrevAndNext(
			long changesetCollectionId, long companyId, String name,
			com.liferay.portal.kernel.util.OrderByComparator
				<ChangesetCollection> orderByComparator)
		throws NoSuchCollectionException;

	/**
	 * Removes all the changeset collections where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 */
	public void removeByC_N(long companyId, String name);

	/**
	 * Returns the number of changeset collections where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching changeset collections
	 */
	public int countByC_N(long companyId, String name);

	/**
	 * Caches the changeset collection in the entity cache if it is enabled.
	 *
	 * @param changesetCollection the changeset collection
	 */
	public void cacheResult(ChangesetCollection changesetCollection);

	/**
	 * Caches the changeset collections in the entity cache if it is enabled.
	 *
	 * @param changesetCollections the changeset collections
	 */
	public void cacheResult(
		java.util.List<ChangesetCollection> changesetCollections);

	/**
	 * Creates a new changeset collection with the primary key. Does not add the changeset collection to the database.
	 *
	 * @param changesetCollectionId the primary key for the new changeset collection
	 * @return the new changeset collection
	 */
	public ChangesetCollection create(long changesetCollectionId);

	/**
	 * Removes the changeset collection with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param changesetCollectionId the primary key of the changeset collection
	 * @return the changeset collection that was removed
	 * @throws NoSuchCollectionException if a changeset collection with the primary key could not be found
	 */
	public ChangesetCollection remove(long changesetCollectionId)
		throws NoSuchCollectionException;

	public ChangesetCollection updateImpl(
		ChangesetCollection changesetCollection);

	/**
	 * Returns the changeset collection with the primary key or throws a <code>NoSuchCollectionException</code> if it could not be found.
	 *
	 * @param changesetCollectionId the primary key of the changeset collection
	 * @return the changeset collection
	 * @throws NoSuchCollectionException if a changeset collection with the primary key could not be found
	 */
	public ChangesetCollection findByPrimaryKey(long changesetCollectionId)
		throws NoSuchCollectionException;

	/**
	 * Returns the changeset collection with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param changesetCollectionId the primary key of the changeset collection
	 * @return the changeset collection, or <code>null</code> if a changeset collection with the primary key could not be found
	 */
	public ChangesetCollection fetchByPrimaryKey(long changesetCollectionId);

	/**
	 * Returns all the changeset collections.
	 *
	 * @return the changeset collections
	 */
	public java.util.List<ChangesetCollection> findAll();

	/**
	 * Returns a range of all the changeset collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @return the range of changeset collections
	 */
	public java.util.List<ChangesetCollection> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the changeset collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of changeset collections
	 */
	public java.util.List<ChangesetCollection> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetCollection>
			orderByComparator);

	/**
	 * Returns an ordered range of all the changeset collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of changeset collections
	 * @param end the upper bound of the range of changeset collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of changeset collections
	 */
	public java.util.List<ChangesetCollection> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetCollection>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the changeset collections from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of changeset collections.
	 *
	 * @return the number of changeset collections
	 */
	public int countAll();

}