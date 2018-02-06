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

import aQute.bnd.annotation.ProviderType;

import com.liferay.fragment.exception.NoSuchEntryLinkException;
import com.liferay.fragment.model.FragmentEntryLink;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the fragment entry link service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.fragment.service.persistence.impl.FragmentEntryLinkPersistenceImpl
 * @see FragmentEntryLinkUtil
 * @generated
 */
@ProviderType
public interface FragmentEntryLinkPersistence extends BasePersistence<FragmentEntryLink> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link FragmentEntryLinkUtil} to access the fragment entry link persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the fragment entry links where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching fragment entry links
	*/
	public java.util.List<FragmentEntryLink> findByGroupId(long groupId);

	/**
	* Returns a range of all the fragment entry links where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of fragment entry links
	* @param end the upper bound of the range of fragment entry links (not inclusive)
	* @return the range of matching fragment entry links
	*/
	public java.util.List<FragmentEntryLink> findByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the fragment entry links where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of fragment entry links
	* @param end the upper bound of the range of fragment entry links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching fragment entry links
	*/
	public java.util.List<FragmentEntryLink> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryLink> orderByComparator);

	/**
	* Returns an ordered range of all the fragment entry links where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of fragment entry links
	* @param end the upper bound of the range of fragment entry links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching fragment entry links
	*/
	public java.util.List<FragmentEntryLink> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first fragment entry link in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching fragment entry link
	* @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	*/
	public FragmentEntryLink findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException;

	/**
	* Returns the first fragment entry link in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	*/
	public FragmentEntryLink fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryLink> orderByComparator);

	/**
	* Returns the last fragment entry link in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching fragment entry link
	* @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	*/
	public FragmentEntryLink findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException;

	/**
	* Returns the last fragment entry link in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	*/
	public FragmentEntryLink fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryLink> orderByComparator);

	/**
	* Returns the fragment entry links before and after the current fragment entry link in the ordered set where groupId = &#63;.
	*
	* @param fragmentEntryLinkId the primary key of the current fragment entry link
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next fragment entry link
	* @throws NoSuchEntryLinkException if a fragment entry link with the primary key could not be found
	*/
	public FragmentEntryLink[] findByGroupId_PrevAndNext(
		long fragmentEntryLinkId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException;

	/**
	* Removes all the fragment entry links where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of fragment entry links where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching fragment entry links
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the fragment entry links where groupId = &#63; and fragmentEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param fragmentEntryId the fragment entry ID
	* @return the matching fragment entry links
	*/
	public java.util.List<FragmentEntryLink> findByG_F(long groupId,
		long fragmentEntryId);

	/**
	* Returns a range of all the fragment entry links where groupId = &#63; and fragmentEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param fragmentEntryId the fragment entry ID
	* @param start the lower bound of the range of fragment entry links
	* @param end the upper bound of the range of fragment entry links (not inclusive)
	* @return the range of matching fragment entry links
	*/
	public java.util.List<FragmentEntryLink> findByG_F(long groupId,
		long fragmentEntryId, int start, int end);

	/**
	* Returns an ordered range of all the fragment entry links where groupId = &#63; and fragmentEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param fragmentEntryId the fragment entry ID
	* @param start the lower bound of the range of fragment entry links
	* @param end the upper bound of the range of fragment entry links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching fragment entry links
	*/
	public java.util.List<FragmentEntryLink> findByG_F(long groupId,
		long fragmentEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryLink> orderByComparator);

	/**
	* Returns an ordered range of all the fragment entry links where groupId = &#63; and fragmentEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param fragmentEntryId the fragment entry ID
	* @param start the lower bound of the range of fragment entry links
	* @param end the upper bound of the range of fragment entry links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching fragment entry links
	*/
	public java.util.List<FragmentEntryLink> findByG_F(long groupId,
		long fragmentEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first fragment entry link in the ordered set where groupId = &#63; and fragmentEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param fragmentEntryId the fragment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching fragment entry link
	* @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	*/
	public FragmentEntryLink findByG_F_First(long groupId,
		long fragmentEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException;

	/**
	* Returns the first fragment entry link in the ordered set where groupId = &#63; and fragmentEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param fragmentEntryId the fragment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	*/
	public FragmentEntryLink fetchByG_F_First(long groupId,
		long fragmentEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryLink> orderByComparator);

	/**
	* Returns the last fragment entry link in the ordered set where groupId = &#63; and fragmentEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param fragmentEntryId the fragment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching fragment entry link
	* @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	*/
	public FragmentEntryLink findByG_F_Last(long groupId, long fragmentEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException;

	/**
	* Returns the last fragment entry link in the ordered set where groupId = &#63; and fragmentEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param fragmentEntryId the fragment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	*/
	public FragmentEntryLink fetchByG_F_Last(long groupId,
		long fragmentEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryLink> orderByComparator);

	/**
	* Returns the fragment entry links before and after the current fragment entry link in the ordered set where groupId = &#63; and fragmentEntryId = &#63;.
	*
	* @param fragmentEntryLinkId the primary key of the current fragment entry link
	* @param groupId the group ID
	* @param fragmentEntryId the fragment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next fragment entry link
	* @throws NoSuchEntryLinkException if a fragment entry link with the primary key could not be found
	*/
	public FragmentEntryLink[] findByG_F_PrevAndNext(long fragmentEntryLinkId,
		long groupId, long fragmentEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException;

	/**
	* Removes all the fragment entry links where groupId = &#63; and fragmentEntryId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param fragmentEntryId the fragment entry ID
	*/
	public void removeByG_F(long groupId, long fragmentEntryId);

	/**
	* Returns the number of fragment entry links where groupId = &#63; and fragmentEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param fragmentEntryId the fragment entry ID
	* @return the number of matching fragment entry links
	*/
	public int countByG_F(long groupId, long fragmentEntryId);

	/**
	* Returns all the fragment entry links where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching fragment entry links
	*/
	public java.util.List<FragmentEntryLink> findByG_C_C(long groupId,
		long classNameId, long classPK);

	/**
	* Returns a range of all the fragment entry links where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of fragment entry links
	* @param end the upper bound of the range of fragment entry links (not inclusive)
	* @return the range of matching fragment entry links
	*/
	public java.util.List<FragmentEntryLink> findByG_C_C(long groupId,
		long classNameId, long classPK, int start, int end);

	/**
	* Returns an ordered range of all the fragment entry links where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of fragment entry links
	* @param end the upper bound of the range of fragment entry links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching fragment entry links
	*/
	public java.util.List<FragmentEntryLink> findByG_C_C(long groupId,
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryLink> orderByComparator);

	/**
	* Returns an ordered range of all the fragment entry links where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of fragment entry links
	* @param end the upper bound of the range of fragment entry links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching fragment entry links
	*/
	public java.util.List<FragmentEntryLink> findByG_C_C(long groupId,
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first fragment entry link in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching fragment entry link
	* @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	*/
	public FragmentEntryLink findByG_C_C_First(long groupId, long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException;

	/**
	* Returns the first fragment entry link in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	*/
	public FragmentEntryLink fetchByG_C_C_First(long groupId, long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryLink> orderByComparator);

	/**
	* Returns the last fragment entry link in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching fragment entry link
	* @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	*/
	public FragmentEntryLink findByG_C_C_Last(long groupId, long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException;

	/**
	* Returns the last fragment entry link in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	*/
	public FragmentEntryLink fetchByG_C_C_Last(long groupId, long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryLink> orderByComparator);

	/**
	* Returns the fragment entry links before and after the current fragment entry link in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param fragmentEntryLinkId the primary key of the current fragment entry link
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next fragment entry link
	* @throws NoSuchEntryLinkException if a fragment entry link with the primary key could not be found
	*/
	public FragmentEntryLink[] findByG_C_C_PrevAndNext(
		long fragmentEntryLinkId, long groupId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException;

	/**
	* Removes all the fragment entry links where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	*/
	public void removeByG_C_C(long groupId, long classNameId, long classPK);

	/**
	* Returns the number of fragment entry links where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the number of matching fragment entry links
	*/
	public int countByG_C_C(long groupId, long classNameId, long classPK);

	/**
	* Caches the fragment entry link in the entity cache if it is enabled.
	*
	* @param fragmentEntryLink the fragment entry link
	*/
	public void cacheResult(FragmentEntryLink fragmentEntryLink);

	/**
	* Caches the fragment entry links in the entity cache if it is enabled.
	*
	* @param fragmentEntryLinks the fragment entry links
	*/
	public void cacheResult(
		java.util.List<FragmentEntryLink> fragmentEntryLinks);

	/**
	* Creates a new fragment entry link with the primary key. Does not add the fragment entry link to the database.
	*
	* @param fragmentEntryLinkId the primary key for the new fragment entry link
	* @return the new fragment entry link
	*/
	public FragmentEntryLink create(long fragmentEntryLinkId);

	/**
	* Removes the fragment entry link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fragmentEntryLinkId the primary key of the fragment entry link
	* @return the fragment entry link that was removed
	* @throws NoSuchEntryLinkException if a fragment entry link with the primary key could not be found
	*/
	public FragmentEntryLink remove(long fragmentEntryLinkId)
		throws NoSuchEntryLinkException;

	public FragmentEntryLink updateImpl(FragmentEntryLink fragmentEntryLink);

	/**
	* Returns the fragment entry link with the primary key or throws a {@link NoSuchEntryLinkException} if it could not be found.
	*
	* @param fragmentEntryLinkId the primary key of the fragment entry link
	* @return the fragment entry link
	* @throws NoSuchEntryLinkException if a fragment entry link with the primary key could not be found
	*/
	public FragmentEntryLink findByPrimaryKey(long fragmentEntryLinkId)
		throws NoSuchEntryLinkException;

	/**
	* Returns the fragment entry link with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param fragmentEntryLinkId the primary key of the fragment entry link
	* @return the fragment entry link, or <code>null</code> if a fragment entry link with the primary key could not be found
	*/
	public FragmentEntryLink fetchByPrimaryKey(long fragmentEntryLinkId);

	@Override
	public java.util.Map<java.io.Serializable, FragmentEntryLink> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the fragment entry links.
	*
	* @return the fragment entry links
	*/
	public java.util.List<FragmentEntryLink> findAll();

	/**
	* Returns a range of all the fragment entry links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of fragment entry links
	* @param end the upper bound of the range of fragment entry links (not inclusive)
	* @return the range of fragment entry links
	*/
	public java.util.List<FragmentEntryLink> findAll(int start, int end);

	/**
	* Returns an ordered range of all the fragment entry links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of fragment entry links
	* @param end the upper bound of the range of fragment entry links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of fragment entry links
	*/
	public java.util.List<FragmentEntryLink> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryLink> orderByComparator);

	/**
	* Returns an ordered range of all the fragment entry links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FragmentEntryLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of fragment entry links
	* @param end the upper bound of the range of fragment entry links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of fragment entry links
	*/
	public java.util.List<FragmentEntryLink> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the fragment entry links from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of fragment entry links.
	*
	* @return the number of fragment entry links
	*/
	public int countAll();
}