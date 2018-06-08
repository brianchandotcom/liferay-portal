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

package com.liferay.portal.kernel.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException;
import com.liferay.portal.kernel.model.LayoutRevision;

/**
 * The persistence interface for the layout revision service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.persistence.impl.LayoutRevisionPersistenceImpl
 * @see LayoutRevisionUtil
 * @generated
 */
@ProviderType
public interface LayoutRevisionPersistence extends BasePersistence<LayoutRevision> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutRevisionUtil} to access the layout revision persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the layout revisions where layoutSetBranchId = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @return the matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByLayoutSetBranchId(
		long layoutSetBranchId);

	/**
	* Returns a range of all the layout revisions where layoutSetBranchId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @return the range of matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByLayoutSetBranchId(
		long layoutSetBranchId, int start, int end);

	/**
	* Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByLayoutSetBranchId(
		long layoutSetBranchId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator);

	/**
	* Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByLayoutSetBranchId(
		long layoutSetBranchId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout revision in the ordered set where layoutSetBranchId = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public LayoutRevision findByLayoutSetBranchId_First(
		long layoutSetBranchId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException;

	/**
	* Returns the first layout revision in the ordered set where layoutSetBranchId = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public LayoutRevision fetchByLayoutSetBranchId_First(
		long layoutSetBranchId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator);

	/**
	* Returns the last layout revision in the ordered set where layoutSetBranchId = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public LayoutRevision findByLayoutSetBranchId_Last(long layoutSetBranchId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException;

	/**
	* Returns the last layout revision in the ordered set where layoutSetBranchId = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public LayoutRevision fetchByLayoutSetBranchId_Last(
		long layoutSetBranchId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator);

	/**
	* Returns the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63;.
	*
	* @param layoutRevisionId the primary key of the current layout revision
	* @param layoutSetBranchId the layout set branch ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout revision
	* @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	*/
	public LayoutRevision[] findByLayoutSetBranchId_PrevAndNext(
		long layoutRevisionId, long layoutSetBranchId,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException;

	/**
	* Removes all the layout revisions where layoutSetBranchId = &#63; from the database.
	*
	* @param layoutSetBranchId the layout set branch ID
	*/
	public void removeByLayoutSetBranchId(long layoutSetBranchId);

	/**
	* Returns the number of layout revisions where layoutSetBranchId = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @return the number of matching layout revisions
	*/
	public int countByLayoutSetBranchId(long layoutSetBranchId);

	/**
	* Returns all the layout revisions where plid = &#63;.
	*
	* @param plid the plid
	* @return the matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByPlid(long plid);

	/**
	* Returns a range of all the layout revisions where plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @return the range of matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByPlid(long plid, int start,
		int end);

	/**
	* Returns an ordered range of all the layout revisions where plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByPlid(long plid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator);

	/**
	* Returns an ordered range of all the layout revisions where plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByPlid(long plid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout revision in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public LayoutRevision findByPlid_First(long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException;

	/**
	* Returns the first layout revision in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public LayoutRevision fetchByPlid_First(long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator);

	/**
	* Returns the last layout revision in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public LayoutRevision findByPlid_Last(long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException;

	/**
	* Returns the last layout revision in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public LayoutRevision fetchByPlid_Last(long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator);

	/**
	* Returns the layout revisions before and after the current layout revision in the ordered set where plid = &#63;.
	*
	* @param layoutRevisionId the primary key of the current layout revision
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout revision
	* @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	*/
	public LayoutRevision[] findByPlid_PrevAndNext(long layoutRevisionId,
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException;

	/**
	* Removes all the layout revisions where plid = &#63; from the database.
	*
	* @param plid the plid
	*/
	public void removeByPlid(long plid);

	/**
	* Returns the number of layout revisions where plid = &#63;.
	*
	* @param plid the plid
	* @return the number of matching layout revisions
	*/
	public int countByPlid(long plid);

	/**
	* Returns all the layout revisions where layoutSetBranchId = &#63; and head = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @return the matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByL_H(long layoutSetBranchId,
		boolean head);

	/**
	* Returns a range of all the layout revisions where layoutSetBranchId = &#63; and head = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @return the range of matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByL_H(long layoutSetBranchId,
		boolean head, int start, int end);

	/**
	* Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and head = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByL_H(long layoutSetBranchId,
		boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator);

	/**
	* Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and head = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByL_H(long layoutSetBranchId,
		boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public LayoutRevision findByL_H_First(long layoutSetBranchId, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException;

	/**
	* Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public LayoutRevision fetchByL_H_First(long layoutSetBranchId,
		boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator);

	/**
	* Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public LayoutRevision findByL_H_Last(long layoutSetBranchId, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException;

	/**
	* Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public LayoutRevision fetchByL_H_Last(long layoutSetBranchId, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator);

	/**
	* Returns the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63;.
	*
	* @param layoutRevisionId the primary key of the current layout revision
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout revision
	* @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	*/
	public LayoutRevision[] findByL_H_PrevAndNext(long layoutRevisionId,
		long layoutSetBranchId, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException;

	/**
	* Removes all the layout revisions where layoutSetBranchId = &#63; and head = &#63; from the database.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	*/
	public void removeByL_H(long layoutSetBranchId, boolean head);

	/**
	* Returns the number of layout revisions where layoutSetBranchId = &#63; and head = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @return the number of matching layout revisions
	*/
	public int countByL_H(long layoutSetBranchId, boolean head);

	/**
	* Returns all the layout revisions where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @return the matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByL_P(long layoutSetBranchId,
		long plid);

	/**
	* Returns a range of all the layout revisions where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @return the range of matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByL_P(long layoutSetBranchId,
		long plid, int start, int end);

	/**
	* Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByL_P(long layoutSetBranchId,
		long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator);

	/**
	* Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByL_P(long layoutSetBranchId,
		long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public LayoutRevision findByL_P_First(long layoutSetBranchId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException;

	/**
	* Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public LayoutRevision fetchByL_P_First(long layoutSetBranchId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator);

	/**
	* Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public LayoutRevision findByL_P_Last(long layoutSetBranchId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException;

	/**
	* Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public LayoutRevision fetchByL_P_Last(long layoutSetBranchId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator);

	/**
	* Returns the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* @param layoutRevisionId the primary key of the current layout revision
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout revision
	* @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	*/
	public LayoutRevision[] findByL_P_PrevAndNext(long layoutRevisionId,
		long layoutSetBranchId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException;

	/**
	* Removes all the layout revisions where layoutSetBranchId = &#63; and plid = &#63; from the database.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	*/
	public void removeByL_P(long layoutSetBranchId, long plid);

	/**
	* Returns the number of layout revisions where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @return the number of matching layout revisions
	*/
	public int countByL_P(long layoutSetBranchId, long plid);

	/**
	* Returns all the layout revisions where layoutSetBranchId = &#63; and status = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param status the status
	* @return the matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByL_S(long layoutSetBranchId,
		int status);

	/**
	* Returns a range of all the layout revisions where layoutSetBranchId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param status the status
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @return the range of matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByL_S(long layoutSetBranchId,
		int status, int start, int end);

	/**
	* Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param status the status
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByL_S(long layoutSetBranchId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator);

	/**
	* Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param status the status
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByL_S(long layoutSetBranchId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and status = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public LayoutRevision findByL_S_First(long layoutSetBranchId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException;

	/**
	* Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and status = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public LayoutRevision fetchByL_S_First(long layoutSetBranchId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator);

	/**
	* Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and status = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public LayoutRevision findByL_S_Last(long layoutSetBranchId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException;

	/**
	* Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and status = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public LayoutRevision fetchByL_S_Last(long layoutSetBranchId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator);

	/**
	* Returns the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63; and status = &#63;.
	*
	* @param layoutRevisionId the primary key of the current layout revision
	* @param layoutSetBranchId the layout set branch ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout revision
	* @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	*/
	public LayoutRevision[] findByL_S_PrevAndNext(long layoutRevisionId,
		long layoutSetBranchId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException;

	/**
	* Removes all the layout revisions where layoutSetBranchId = &#63; and status = &#63; from the database.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param status the status
	*/
	public void removeByL_S(long layoutSetBranchId, int status);

	/**
	* Returns the number of layout revisions where layoutSetBranchId = &#63; and status = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param status the status
	* @return the number of matching layout revisions
	*/
	public int countByL_S(long layoutSetBranchId, int status);

	/**
	* Returns all the layout revisions where layoutSetBranchId = &#63; and head = &#63; and status = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param status the status
	* @return the matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByL_H_S(long layoutSetBranchId,
		boolean head, int status);

	/**
	* Returns a range of all the layout revisions where layoutSetBranchId = &#63; and head = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param status the status
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @return the range of matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByL_H_S(long layoutSetBranchId,
		boolean head, int status, int start, int end);

	/**
	* Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and head = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param status the status
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByL_H_S(long layoutSetBranchId,
		boolean head, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator);

	/**
	* Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and head = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param status the status
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByL_H_S(long layoutSetBranchId,
		boolean head, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63; and status = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public LayoutRevision findByL_H_S_First(long layoutSetBranchId,
		boolean head, int status,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException;

	/**
	* Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63; and status = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public LayoutRevision fetchByL_H_S_First(long layoutSetBranchId,
		boolean head, int status,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator);

	/**
	* Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63; and status = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public LayoutRevision findByL_H_S_Last(long layoutSetBranchId,
		boolean head, int status,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException;

	/**
	* Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63; and status = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public LayoutRevision fetchByL_H_S_Last(long layoutSetBranchId,
		boolean head, int status,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator);

	/**
	* Returns the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63; and status = &#63;.
	*
	* @param layoutRevisionId the primary key of the current layout revision
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout revision
	* @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	*/
	public LayoutRevision[] findByL_H_S_PrevAndNext(long layoutRevisionId,
		long layoutSetBranchId, boolean head, int status,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException;

	/**
	* Removes all the layout revisions where layoutSetBranchId = &#63; and head = &#63; and status = &#63; from the database.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param status the status
	*/
	public void removeByL_H_S(long layoutSetBranchId, boolean head, int status);

	/**
	* Returns the number of layout revisions where layoutSetBranchId = &#63; and head = &#63; and status = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param status the status
	* @return the number of matching layout revisions
	*/
	public int countByL_H_S(long layoutSetBranchId, boolean head, int status);

	/**
	* Returns all the layout revisions where head = &#63; and plid = &#63;.
	*
	* @param head the head
	* @param plid the plid
	* @return the matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByH_P(boolean head, long plid);

	/**
	* Returns a range of all the layout revisions where head = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param head the head
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @return the range of matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByH_P(boolean head, long plid,
		int start, int end);

	/**
	* Returns an ordered range of all the layout revisions where head = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param head the head
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByH_P(boolean head, long plid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator);

	/**
	* Returns an ordered range of all the layout revisions where head = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param head the head
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByH_P(boolean head, long plid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout revision in the ordered set where head = &#63; and plid = &#63;.
	*
	* @param head the head
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public LayoutRevision findByH_P_First(boolean head, long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException;

	/**
	* Returns the first layout revision in the ordered set where head = &#63; and plid = &#63;.
	*
	* @param head the head
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public LayoutRevision fetchByH_P_First(boolean head, long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator);

	/**
	* Returns the last layout revision in the ordered set where head = &#63; and plid = &#63;.
	*
	* @param head the head
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public LayoutRevision findByH_P_Last(boolean head, long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException;

	/**
	* Returns the last layout revision in the ordered set where head = &#63; and plid = &#63;.
	*
	* @param head the head
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public LayoutRevision fetchByH_P_Last(boolean head, long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator);

	/**
	* Returns the layout revisions before and after the current layout revision in the ordered set where head = &#63; and plid = &#63;.
	*
	* @param layoutRevisionId the primary key of the current layout revision
	* @param head the head
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout revision
	* @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	*/
	public LayoutRevision[] findByH_P_PrevAndNext(long layoutRevisionId,
		boolean head, long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException;

	/**
	* Removes all the layout revisions where head = &#63; and plid = &#63; from the database.
	*
	* @param head the head
	* @param plid the plid
	*/
	public void removeByH_P(boolean head, long plid);

	/**
	* Returns the number of layout revisions where head = &#63; and plid = &#63;.
	*
	* @param head the head
	* @param plid the plid
	* @return the number of matching layout revisions
	*/
	public int countByH_P(boolean head, long plid);

	/**
	* Returns all the layout revisions where plid = &#63; and status &ne; &#63;.
	*
	* @param plid the plid
	* @param status the status
	* @return the matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByP_NotS(long plid, int status);

	/**
	* Returns a range of all the layout revisions where plid = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plid the plid
	* @param status the status
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @return the range of matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByP_NotS(long plid, int status,
		int start, int end);

	/**
	* Returns an ordered range of all the layout revisions where plid = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plid the plid
	* @param status the status
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByP_NotS(long plid, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator);

	/**
	* Returns an ordered range of all the layout revisions where plid = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plid the plid
	* @param status the status
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByP_NotS(long plid, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout revision in the ordered set where plid = &#63; and status &ne; &#63;.
	*
	* @param plid the plid
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public LayoutRevision findByP_NotS_First(long plid, int status,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException;

	/**
	* Returns the first layout revision in the ordered set where plid = &#63; and status &ne; &#63;.
	*
	* @param plid the plid
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public LayoutRevision fetchByP_NotS_First(long plid, int status,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator);

	/**
	* Returns the last layout revision in the ordered set where plid = &#63; and status &ne; &#63;.
	*
	* @param plid the plid
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public LayoutRevision findByP_NotS_Last(long plid, int status,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException;

	/**
	* Returns the last layout revision in the ordered set where plid = &#63; and status &ne; &#63;.
	*
	* @param plid the plid
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public LayoutRevision fetchByP_NotS_Last(long plid, int status,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator);

	/**
	* Returns the layout revisions before and after the current layout revision in the ordered set where plid = &#63; and status &ne; &#63;.
	*
	* @param layoutRevisionId the primary key of the current layout revision
	* @param plid the plid
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout revision
	* @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	*/
	public LayoutRevision[] findByP_NotS_PrevAndNext(long layoutRevisionId,
		long plid, int status,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException;

	/**
	* Removes all the layout revisions where plid = &#63; and status &ne; &#63; from the database.
	*
	* @param plid the plid
	* @param status the status
	*/
	public void removeByP_NotS(long plid, int status);

	/**
	* Returns the number of layout revisions where plid = &#63; and status &ne; &#63;.
	*
	* @param plid the plid
	* @param status the status
	* @return the number of matching layout revisions
	*/
	public int countByP_NotS(long plid, int status);

	/**
	* Returns all the layout revisions where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param layoutBranchId the layout branch ID
	* @param plid the plid
	* @return the matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByL_L_P(long layoutSetBranchId,
		long layoutBranchId, long plid);

	/**
	* Returns a range of all the layout revisions where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param layoutBranchId the layout branch ID
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @return the range of matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByL_L_P(long layoutSetBranchId,
		long layoutBranchId, long plid, int start, int end);

	/**
	* Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param layoutBranchId the layout branch ID
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByL_L_P(long layoutSetBranchId,
		long layoutBranchId, long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator);

	/**
	* Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param layoutBranchId the layout branch ID
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByL_L_P(long layoutSetBranchId,
		long layoutBranchId, long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param layoutBranchId the layout branch ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public LayoutRevision findByL_L_P_First(long layoutSetBranchId,
		long layoutBranchId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException;

	/**
	* Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param layoutBranchId the layout branch ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public LayoutRevision fetchByL_L_P_First(long layoutSetBranchId,
		long layoutBranchId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator);

	/**
	* Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param layoutBranchId the layout branch ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public LayoutRevision findByL_L_P_Last(long layoutSetBranchId,
		long layoutBranchId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException;

	/**
	* Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param layoutBranchId the layout branch ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public LayoutRevision fetchByL_L_P_Last(long layoutSetBranchId,
		long layoutBranchId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator);

	/**
	* Returns the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	*
	* @param layoutRevisionId the primary key of the current layout revision
	* @param layoutSetBranchId the layout set branch ID
	* @param layoutBranchId the layout branch ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout revision
	* @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	*/
	public LayoutRevision[] findByL_L_P_PrevAndNext(long layoutRevisionId,
		long layoutSetBranchId, long layoutBranchId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException;

	/**
	* Removes all the layout revisions where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63; from the database.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param layoutBranchId the layout branch ID
	* @param plid the plid
	*/
	public void removeByL_L_P(long layoutSetBranchId, long layoutBranchId,
		long plid);

	/**
	* Returns the number of layout revisions where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param layoutBranchId the layout branch ID
	* @param plid the plid
	* @return the number of matching layout revisions
	*/
	public int countByL_L_P(long layoutSetBranchId, long layoutBranchId,
		long plid);

	/**
	* Returns all the layout revisions where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param parentLayoutRevisionId the parent layout revision ID
	* @param plid the plid
	* @return the matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByL_P_P(long layoutSetBranchId,
		long parentLayoutRevisionId, long plid);

	/**
	* Returns a range of all the layout revisions where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param parentLayoutRevisionId the parent layout revision ID
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @return the range of matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByL_P_P(long layoutSetBranchId,
		long parentLayoutRevisionId, long plid, int start, int end);

	/**
	* Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param parentLayoutRevisionId the parent layout revision ID
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByL_P_P(long layoutSetBranchId,
		long parentLayoutRevisionId, long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator);

	/**
	* Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param parentLayoutRevisionId the parent layout revision ID
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByL_P_P(long layoutSetBranchId,
		long parentLayoutRevisionId, long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param parentLayoutRevisionId the parent layout revision ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public LayoutRevision findByL_P_P_First(long layoutSetBranchId,
		long parentLayoutRevisionId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException;

	/**
	* Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param parentLayoutRevisionId the parent layout revision ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public LayoutRevision fetchByL_P_P_First(long layoutSetBranchId,
		long parentLayoutRevisionId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator);

	/**
	* Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param parentLayoutRevisionId the parent layout revision ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public LayoutRevision findByL_P_P_Last(long layoutSetBranchId,
		long parentLayoutRevisionId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException;

	/**
	* Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param parentLayoutRevisionId the parent layout revision ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public LayoutRevision fetchByL_P_P_Last(long layoutSetBranchId,
		long parentLayoutRevisionId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator);

	/**
	* Returns the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	*
	* @param layoutRevisionId the primary key of the current layout revision
	* @param layoutSetBranchId the layout set branch ID
	* @param parentLayoutRevisionId the parent layout revision ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout revision
	* @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	*/
	public LayoutRevision[] findByL_P_P_PrevAndNext(long layoutRevisionId,
		long layoutSetBranchId, long parentLayoutRevisionId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException;

	/**
	* Removes all the layout revisions where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63; from the database.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param parentLayoutRevisionId the parent layout revision ID
	* @param plid the plid
	*/
	public void removeByL_P_P(long layoutSetBranchId,
		long parentLayoutRevisionId, long plid);

	/**
	* Returns the number of layout revisions where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param parentLayoutRevisionId the parent layout revision ID
	* @param plid the plid
	* @return the number of matching layout revisions
	*/
	public int countByL_P_P(long layoutSetBranchId,
		long parentLayoutRevisionId, long plid);

	/**
	* Returns the layout revision where layoutSetBranchId = &#63; and head = &#63; and plid = &#63; or throws a {@link NoSuchLayoutRevisionException} if it could not be found.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param plid the plid
	* @return the matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public LayoutRevision findByL_H_P(long layoutSetBranchId, boolean head,
		long plid) throws NoSuchLayoutRevisionException;

	/**
	* Returns the layout revision where layoutSetBranchId = &#63; and head = &#63; and plid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param plid the plid
	* @return the matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public LayoutRevision fetchByL_H_P(long layoutSetBranchId, boolean head,
		long plid);

	/**
	* Returns the layout revision where layoutSetBranchId = &#63; and head = &#63; and plid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param plid the plid
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public LayoutRevision fetchByL_H_P(long layoutSetBranchId, boolean head,
		long plid, boolean retrieveFromCache);

	/**
	* Removes the layout revision where layoutSetBranchId = &#63; and head = &#63; and plid = &#63; from the database.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param plid the plid
	* @return the layout revision that was removed
	*/
	public LayoutRevision removeByL_H_P(long layoutSetBranchId, boolean head,
		long plid) throws NoSuchLayoutRevisionException;

	/**
	* Returns the number of layout revisions where layoutSetBranchId = &#63; and head = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param plid the plid
	* @return the number of matching layout revisions
	*/
	public int countByL_H_P(long layoutSetBranchId, boolean head, long plid);

	/**
	* Returns all the layout revisions where layoutSetBranchId = &#63; and head = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param plid the plid
	* @return the matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByL_H_P_Collection(
		long layoutSetBranchId, boolean head, long plid);

	/**
	* Returns a range of all the layout revisions where layoutSetBranchId = &#63; and head = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @return the range of matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByL_H_P_Collection(
		long layoutSetBranchId, boolean head, long plid, int start, int end);

	/**
	* Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and head = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByL_H_P_Collection(
		long layoutSetBranchId, boolean head, long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator);

	/**
	* Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and head = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByL_H_P_Collection(
		long layoutSetBranchId, boolean head, long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public LayoutRevision findByL_H_P_Collection_First(long layoutSetBranchId,
		boolean head, long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException;

	/**
	* Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public LayoutRevision fetchByL_H_P_Collection_First(
		long layoutSetBranchId, boolean head, long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator);

	/**
	* Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public LayoutRevision findByL_H_P_Collection_Last(long layoutSetBranchId,
		boolean head, long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException;

	/**
	* Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public LayoutRevision fetchByL_H_P_Collection_Last(long layoutSetBranchId,
		boolean head, long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator);

	/**
	* Returns the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63; and plid = &#63;.
	*
	* @param layoutRevisionId the primary key of the current layout revision
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout revision
	* @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	*/
	public LayoutRevision[] findByL_H_P_Collection_PrevAndNext(
		long layoutRevisionId, long layoutSetBranchId, boolean head, long plid,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException;

	/**
	* Removes all the layout revisions where layoutSetBranchId = &#63; and head = &#63; and plid = &#63; from the database.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param plid the plid
	*/
	public void removeByL_H_P_Collection(long layoutSetBranchId, boolean head,
		long plid);

	/**
	* Returns the number of layout revisions where layoutSetBranchId = &#63; and head = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param plid the plid
	* @return the number of matching layout revisions
	*/
	public int countByL_H_P_Collection(long layoutSetBranchId, boolean head,
		long plid);

	/**
	* Returns all the layout revisions where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param status the status
	* @return the matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByL_P_S(long layoutSetBranchId,
		long plid, int status);

	/**
	* Returns a range of all the layout revisions where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param status the status
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @return the range of matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByL_P_S(long layoutSetBranchId,
		long plid, int status, int start, int end);

	/**
	* Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param status the status
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByL_P_S(long layoutSetBranchId,
		long plid, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator);

	/**
	* Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param status the status
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout revisions
	*/
	public java.util.List<LayoutRevision> findByL_P_S(long layoutSetBranchId,
		long plid, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public LayoutRevision findByL_P_S_First(long layoutSetBranchId, long plid,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException;

	/**
	* Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public LayoutRevision fetchByL_P_S_First(long layoutSetBranchId, long plid,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator);

	/**
	* Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public LayoutRevision findByL_P_S_Last(long layoutSetBranchId, long plid,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException;

	/**
	* Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public LayoutRevision fetchByL_P_S_Last(long layoutSetBranchId, long plid,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator);

	/**
	* Returns the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	*
	* @param layoutRevisionId the primary key of the current layout revision
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout revision
	* @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	*/
	public LayoutRevision[] findByL_P_S_PrevAndNext(long layoutRevisionId,
		long layoutSetBranchId, long plid, int status,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException;

	/**
	* Removes all the layout revisions where layoutSetBranchId = &#63; and plid = &#63; and status = &#63; from the database.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param status the status
	*/
	public void removeByL_P_S(long layoutSetBranchId, long plid, int status);

	/**
	* Returns the number of layout revisions where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param status the status
	* @return the number of matching layout revisions
	*/
	public int countByL_P_S(long layoutSetBranchId, long plid, int status);

	/**
	* Returns the layout revision where layoutSetBranchId = &#63; and layoutBranchId = &#63; and head = &#63; and plid = &#63; or throws a {@link NoSuchLayoutRevisionException} if it could not be found.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param layoutBranchId the layout branch ID
	* @param head the head
	* @param plid the plid
	* @return the matching layout revision
	* @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	*/
	public LayoutRevision findByL_L_H_P(long layoutSetBranchId,
		long layoutBranchId, boolean head, long plid)
		throws NoSuchLayoutRevisionException;

	/**
	* Returns the layout revision where layoutSetBranchId = &#63; and layoutBranchId = &#63; and head = &#63; and plid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param layoutBranchId the layout branch ID
	* @param head the head
	* @param plid the plid
	* @return the matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public LayoutRevision fetchByL_L_H_P(long layoutSetBranchId,
		long layoutBranchId, boolean head, long plid);

	/**
	* Returns the layout revision where layoutSetBranchId = &#63; and layoutBranchId = &#63; and head = &#63; and plid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param layoutBranchId the layout branch ID
	* @param head the head
	* @param plid the plid
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching layout revision, or <code>null</code> if a matching layout revision could not be found
	*/
	public LayoutRevision fetchByL_L_H_P(long layoutSetBranchId,
		long layoutBranchId, boolean head, long plid, boolean retrieveFromCache);

	/**
	* Removes the layout revision where layoutSetBranchId = &#63; and layoutBranchId = &#63; and head = &#63; and plid = &#63; from the database.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param layoutBranchId the layout branch ID
	* @param head the head
	* @param plid the plid
	* @return the layout revision that was removed
	*/
	public LayoutRevision removeByL_L_H_P(long layoutSetBranchId,
		long layoutBranchId, boolean head, long plid)
		throws NoSuchLayoutRevisionException;

	/**
	* Returns the number of layout revisions where layoutSetBranchId = &#63; and layoutBranchId = &#63; and head = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param layoutBranchId the layout branch ID
	* @param head the head
	* @param plid the plid
	* @return the number of matching layout revisions
	*/
	public int countByL_L_H_P(long layoutSetBranchId, long layoutBranchId,
		boolean head, long plid);

	/**
	* Caches the layout revision in the entity cache if it is enabled.
	*
	* @param layoutRevision the layout revision
	*/
	public void cacheResult(LayoutRevision layoutRevision);

	/**
	* Caches the layout revisions in the entity cache if it is enabled.
	*
	* @param layoutRevisions the layout revisions
	*/
	public void cacheResult(java.util.List<LayoutRevision> layoutRevisions);

	/**
	* Creates a new layout revision with the primary key. Does not add the layout revision to the database.
	*
	* @param layoutRevisionId the primary key for the new layout revision
	* @return the new layout revision
	*/
	public LayoutRevision create(long layoutRevisionId);

	/**
	* Removes the layout revision with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutRevisionId the primary key of the layout revision
	* @return the layout revision that was removed
	* @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	*/
	public LayoutRevision remove(long layoutRevisionId)
		throws NoSuchLayoutRevisionException;

	public LayoutRevision updateImpl(LayoutRevision layoutRevision);

	/**
	* Returns the layout revision with the primary key or throws a {@link NoSuchLayoutRevisionException} if it could not be found.
	*
	* @param layoutRevisionId the primary key of the layout revision
	* @return the layout revision
	* @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	*/
	public LayoutRevision findByPrimaryKey(long layoutRevisionId)
		throws NoSuchLayoutRevisionException;

	/**
	* Returns the layout revision with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param layoutRevisionId the primary key of the layout revision
	* @return the layout revision, or <code>null</code> if a layout revision with the primary key could not be found
	*/
	public LayoutRevision fetchByPrimaryKey(long layoutRevisionId);

	@Override
	public java.util.Map<java.io.Serializable, LayoutRevision> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the layout revisions.
	*
	* @return the layout revisions
	*/
	public java.util.List<LayoutRevision> findAll();

	/**
	* Returns a range of all the layout revisions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @return the range of layout revisions
	*/
	public java.util.List<LayoutRevision> findAll(int start, int end);

	/**
	* Returns an ordered range of all the layout revisions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of layout revisions
	*/
	public java.util.List<LayoutRevision> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator);

	/**
	* Returns an ordered range of all the layout revisions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutRevisionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of layout revisions
	*/
	public java.util.List<LayoutRevision> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutRevision> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the layout revisions from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of layout revisions.
	*
	* @return the number of layout revisions
	*/
	public int countAll();
}