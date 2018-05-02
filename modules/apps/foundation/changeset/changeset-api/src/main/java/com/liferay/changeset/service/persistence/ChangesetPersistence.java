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

import aQute.bnd.annotation.ProviderType;

import com.liferay.changeset.exception.NoSuchChangesetException;
import com.liferay.changeset.model.Changeset;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the changeset service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.changeset.service.persistence.impl.ChangesetPersistenceImpl
 * @see ChangesetUtil
 * @generated
 */
@ProviderType
public interface ChangesetPersistence extends BasePersistence<Changeset> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ChangesetUtil} to access the changeset persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the changesets where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching changesets
	*/
	public java.util.List<Changeset> findByGroupId(long groupId);

	/**
	* Returns a range of all the changesets where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of changesets
	* @param end the upper bound of the range of changesets (not inclusive)
	* @return the range of matching changesets
	*/
	public java.util.List<Changeset> findByGroupId(long groupId, int start,
		int end);

	/**
	* Returns an ordered range of all the changesets where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of changesets
	* @param end the upper bound of the range of changesets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching changesets
	*/
	public java.util.List<Changeset> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<Changeset> orderByComparator);

	/**
	* Returns an ordered range of all the changesets where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of changesets
	* @param end the upper bound of the range of changesets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching changesets
	*/
	public java.util.List<Changeset> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<Changeset> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first changeset in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching changeset
	* @throws NoSuchChangesetException if a matching changeset could not be found
	*/
	public Changeset findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<Changeset> orderByComparator)
		throws NoSuchChangesetException;

	/**
	* Returns the first changeset in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching changeset, or <code>null</code> if a matching changeset could not be found
	*/
	public Changeset fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<Changeset> orderByComparator);

	/**
	* Returns the last changeset in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching changeset
	* @throws NoSuchChangesetException if a matching changeset could not be found
	*/
	public Changeset findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<Changeset> orderByComparator)
		throws NoSuchChangesetException;

	/**
	* Returns the last changeset in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching changeset, or <code>null</code> if a matching changeset could not be found
	*/
	public Changeset fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<Changeset> orderByComparator);

	/**
	* Returns the changesets before and after the current changeset in the ordered set where groupId = &#63;.
	*
	* @param changesetId the primary key of the current changeset
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next changeset
	* @throws NoSuchChangesetException if a changeset with the primary key could not be found
	*/
	public Changeset[] findByGroupId_PrevAndNext(long changesetId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<Changeset> orderByComparator)
		throws NoSuchChangesetException;

	/**
	* Removes all the changesets where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of changesets where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching changesets
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the changesets where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching changesets
	*/
	public java.util.List<Changeset> findByCompanyId(long companyId);

	/**
	* Returns a range of all the changesets where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of changesets
	* @param end the upper bound of the range of changesets (not inclusive)
	* @return the range of matching changesets
	*/
	public java.util.List<Changeset> findByCompanyId(long companyId, int start,
		int end);

	/**
	* Returns an ordered range of all the changesets where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of changesets
	* @param end the upper bound of the range of changesets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching changesets
	*/
	public java.util.List<Changeset> findByCompanyId(long companyId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<Changeset> orderByComparator);

	/**
	* Returns an ordered range of all the changesets where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of changesets
	* @param end the upper bound of the range of changesets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching changesets
	*/
	public java.util.List<Changeset> findByCompanyId(long companyId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<Changeset> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first changeset in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching changeset
	* @throws NoSuchChangesetException if a matching changeset could not be found
	*/
	public Changeset findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Changeset> orderByComparator)
		throws NoSuchChangesetException;

	/**
	* Returns the first changeset in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching changeset, or <code>null</code> if a matching changeset could not be found
	*/
	public Changeset fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Changeset> orderByComparator);

	/**
	* Returns the last changeset in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching changeset
	* @throws NoSuchChangesetException if a matching changeset could not be found
	*/
	public Changeset findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Changeset> orderByComparator)
		throws NoSuchChangesetException;

	/**
	* Returns the last changeset in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching changeset, or <code>null</code> if a matching changeset could not be found
	*/
	public Changeset fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Changeset> orderByComparator);

	/**
	* Returns the changesets before and after the current changeset in the ordered set where companyId = &#63;.
	*
	* @param changesetId the primary key of the current changeset
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next changeset
	* @throws NoSuchChangesetException if a changeset with the primary key could not be found
	*/
	public Changeset[] findByCompanyId_PrevAndNext(long changesetId,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Changeset> orderByComparator)
		throws NoSuchChangesetException;

	/**
	* Removes all the changesets where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of changesets where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching changesets
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns all the changesets where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the matching changesets
	*/
	public java.util.List<Changeset> findByG_U(long groupId, long userId);

	/**
	* Returns a range of all the changesets where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of changesets
	* @param end the upper bound of the range of changesets (not inclusive)
	* @return the range of matching changesets
	*/
	public java.util.List<Changeset> findByG_U(long groupId, long userId,
		int start, int end);

	/**
	* Returns an ordered range of all the changesets where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of changesets
	* @param end the upper bound of the range of changesets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching changesets
	*/
	public java.util.List<Changeset> findByG_U(long groupId, long userId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Changeset> orderByComparator);

	/**
	* Returns an ordered range of all the changesets where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of changesets
	* @param end the upper bound of the range of changesets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching changesets
	*/
	public java.util.List<Changeset> findByG_U(long groupId, long userId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Changeset> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first changeset in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching changeset
	* @throws NoSuchChangesetException if a matching changeset could not be found
	*/
	public Changeset findByG_U_First(long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<Changeset> orderByComparator)
		throws NoSuchChangesetException;

	/**
	* Returns the first changeset in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching changeset, or <code>null</code> if a matching changeset could not be found
	*/
	public Changeset fetchByG_U_First(long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<Changeset> orderByComparator);

	/**
	* Returns the last changeset in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching changeset
	* @throws NoSuchChangesetException if a matching changeset could not be found
	*/
	public Changeset findByG_U_Last(long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<Changeset> orderByComparator)
		throws NoSuchChangesetException;

	/**
	* Returns the last changeset in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching changeset, or <code>null</code> if a matching changeset could not be found
	*/
	public Changeset fetchByG_U_Last(long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<Changeset> orderByComparator);

	/**
	* Returns the changesets before and after the current changeset in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param changesetId the primary key of the current changeset
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next changeset
	* @throws NoSuchChangesetException if a changeset with the primary key could not be found
	*/
	public Changeset[] findByG_U_PrevAndNext(long changesetId, long groupId,
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator<Changeset> orderByComparator)
		throws NoSuchChangesetException;

	/**
	* Removes all the changesets where groupId = &#63; and userId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	*/
	public void removeByG_U(long groupId, long userId);

	/**
	* Returns the number of changesets where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the number of matching changesets
	*/
	public int countByG_U(long groupId, long userId);

	/**
	* Returns the changeset where groupId = &#63; and name = &#63; or throws a {@link NoSuchChangesetException} if it could not be found.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching changeset
	* @throws NoSuchChangesetException if a matching changeset could not be found
	*/
	public Changeset findByG_N(long groupId, java.lang.String name)
		throws NoSuchChangesetException;

	/**
	* Returns the changeset where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching changeset, or <code>null</code> if a matching changeset could not be found
	*/
	public Changeset fetchByG_N(long groupId, java.lang.String name);

	/**
	* Returns the changeset where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching changeset, or <code>null</code> if a matching changeset could not be found
	*/
	public Changeset fetchByG_N(long groupId, java.lang.String name,
		boolean retrieveFromCache);

	/**
	* Removes the changeset where groupId = &#63; and name = &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the changeset that was removed
	*/
	public Changeset removeByG_N(long groupId, java.lang.String name)
		throws NoSuchChangesetException;

	/**
	* Returns the number of changesets where groupId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching changesets
	*/
	public int countByG_N(long groupId, java.lang.String name);

	/**
	* Returns all the changesets where companyId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the matching changesets
	*/
	public java.util.List<Changeset> findByC_N(long companyId,
		java.lang.String name);

	/**
	* Returns a range of all the changesets where companyId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param start the lower bound of the range of changesets
	* @param end the upper bound of the range of changesets (not inclusive)
	* @return the range of matching changesets
	*/
	public java.util.List<Changeset> findByC_N(long companyId,
		java.lang.String name, int start, int end);

	/**
	* Returns an ordered range of all the changesets where companyId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param start the lower bound of the range of changesets
	* @param end the upper bound of the range of changesets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching changesets
	*/
	public java.util.List<Changeset> findByC_N(long companyId,
		java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Changeset> orderByComparator);

	/**
	* Returns an ordered range of all the changesets where companyId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param start the lower bound of the range of changesets
	* @param end the upper bound of the range of changesets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching changesets
	*/
	public java.util.List<Changeset> findByC_N(long companyId,
		java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Changeset> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first changeset in the ordered set where companyId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching changeset
	* @throws NoSuchChangesetException if a matching changeset could not be found
	*/
	public Changeset findByC_N_First(long companyId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<Changeset> orderByComparator)
		throws NoSuchChangesetException;

	/**
	* Returns the first changeset in the ordered set where companyId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching changeset, or <code>null</code> if a matching changeset could not be found
	*/
	public Changeset fetchByC_N_First(long companyId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<Changeset> orderByComparator);

	/**
	* Returns the last changeset in the ordered set where companyId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching changeset
	* @throws NoSuchChangesetException if a matching changeset could not be found
	*/
	public Changeset findByC_N_Last(long companyId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<Changeset> orderByComparator)
		throws NoSuchChangesetException;

	/**
	* Returns the last changeset in the ordered set where companyId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching changeset, or <code>null</code> if a matching changeset could not be found
	*/
	public Changeset fetchByC_N_Last(long companyId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<Changeset> orderByComparator);

	/**
	* Returns the changesets before and after the current changeset in the ordered set where companyId = &#63; and name = &#63;.
	*
	* @param changesetId the primary key of the current changeset
	* @param companyId the company ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next changeset
	* @throws NoSuchChangesetException if a changeset with the primary key could not be found
	*/
	public Changeset[] findByC_N_PrevAndNext(long changesetId, long companyId,
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<Changeset> orderByComparator)
		throws NoSuchChangesetException;

	/**
	* Removes all the changesets where companyId = &#63; and name = &#63; from the database.
	*
	* @param companyId the company ID
	* @param name the name
	*/
	public void removeByC_N(long companyId, java.lang.String name);

	/**
	* Returns the number of changesets where companyId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the number of matching changesets
	*/
	public int countByC_N(long companyId, java.lang.String name);

	/**
	* Caches the changeset in the entity cache if it is enabled.
	*
	* @param changeset the changeset
	*/
	public void cacheResult(Changeset changeset);

	/**
	* Caches the changesets in the entity cache if it is enabled.
	*
	* @param changesets the changesets
	*/
	public void cacheResult(java.util.List<Changeset> changesets);

	/**
	* Creates a new changeset with the primary key. Does not add the changeset to the database.
	*
	* @param changesetId the primary key for the new changeset
	* @return the new changeset
	*/
	public Changeset create(long changesetId);

	/**
	* Removes the changeset with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param changesetId the primary key of the changeset
	* @return the changeset that was removed
	* @throws NoSuchChangesetException if a changeset with the primary key could not be found
	*/
	public Changeset remove(long changesetId) throws NoSuchChangesetException;

	public Changeset updateImpl(Changeset changeset);

	/**
	* Returns the changeset with the primary key or throws a {@link NoSuchChangesetException} if it could not be found.
	*
	* @param changesetId the primary key of the changeset
	* @return the changeset
	* @throws NoSuchChangesetException if a changeset with the primary key could not be found
	*/
	public Changeset findByPrimaryKey(long changesetId)
		throws NoSuchChangesetException;

	/**
	* Returns the changeset with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param changesetId the primary key of the changeset
	* @return the changeset, or <code>null</code> if a changeset with the primary key could not be found
	*/
	public Changeset fetchByPrimaryKey(long changesetId);

	@Override
	public java.util.Map<java.io.Serializable, Changeset> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the changesets.
	*
	* @return the changesets
	*/
	public java.util.List<Changeset> findAll();

	/**
	* Returns a range of all the changesets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of changesets
	* @param end the upper bound of the range of changesets (not inclusive)
	* @return the range of changesets
	*/
	public java.util.List<Changeset> findAll(int start, int end);

	/**
	* Returns an ordered range of all the changesets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of changesets
	* @param end the upper bound of the range of changesets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of changesets
	*/
	public java.util.List<Changeset> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Changeset> orderByComparator);

	/**
	* Returns an ordered range of all the changesets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of changesets
	* @param end the upper bound of the range of changesets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of changesets
	*/
	public java.util.List<Changeset> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Changeset> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the changesets from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of changesets.
	*
	* @return the number of changesets
	*/
	public int countAll();
}