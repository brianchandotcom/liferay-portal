/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.LayoutRevision;

/**
 * The persistence interface for the layout revision service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutRevisionPersistenceImpl
 * @see LayoutRevisionUtil
 * @generated
 */
public interface LayoutRevisionPersistence extends BasePersistence<LayoutRevision> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutRevisionUtil} to access the layout revision persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the layout revision in the entity cache if it is enabled.
	*
	* @param layoutRevision the layout revision
	*/
	public void cacheResult(
		com.liferay.portal.model.LayoutRevision layoutRevision);

	/**
	* Caches the layout revisions in the entity cache if it is enabled.
	*
	* @param layoutRevisions the layout revisions
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.LayoutRevision> layoutRevisions);

	/**
	* Creates a new layout revision with the primary key. Does not add the layout revision to the database.
	*
	* @param layoutRevisionId the primary key for the new layout revision
	* @return the new layout revision
	*/
	public com.liferay.portal.model.LayoutRevision create(long layoutRevisionId);

	/**
	* Removes the layout revision with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutRevisionId the primary key of the layout revision
	* @return the layout revision that was removed
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision remove(long layoutRevisionId)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.LayoutRevision updateImpl(
		com.liferay.portal.model.LayoutRevision layoutRevision, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the layout revision with the primary key or throws a {@link com.liferay.portal.NoSuchLayoutRevisionException} if it could not be found.
	*
	* @param layoutRevisionId the primary key of the layout revision
	* @return the layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision findByPrimaryKey(
		long layoutRevisionId)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the layout revision with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param layoutRevisionId the primary key of the layout revision
	* @return the layout revision, or <code>null</code> if a layout revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision fetchByPrimaryKey(
		long layoutRevisionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the layout revisions where layoutSetBranchId = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @return the matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutRevision> findByLayoutSetBranchId(
		long layoutSetBranchId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the layout revisions where layoutSetBranchId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @return the range of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutRevision> findByLayoutSetBranchId(
		long layoutSetBranchId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutRevision> findByLayoutSetBranchId(
		long layoutSetBranchId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first layout revision in the ordered set where layoutSetBranchId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision findByLayoutSetBranchId_First(
		long layoutSetBranchId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last layout revision in the ordered set where layoutSetBranchId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision findByLayoutSetBranchId_Last(
		long layoutSetBranchId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutRevisionId the primary key of the current layout revision
	* @param layoutSetBranchId the layout set branch ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision[] findByLayoutSetBranchId_PrevAndNext(
		long layoutRevisionId, long layoutSetBranchId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the layout revisions where plid = &#63;.
	*
	* @param plid the plid
	* @return the matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutRevision> findByPlid(
		long plid) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the layout revisions where plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @return the range of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutRevision> findByPlid(
		long plid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the layout revisions where plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutRevision> findByPlid(
		long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first layout revision in the ordered set where plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision findByPlid_First(long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last layout revision in the ordered set where plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision findByPlid_Last(long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the layout revisions before and after the current layout revision in the ordered set where plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutRevisionId the primary key of the current layout revision
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision[] findByPlid_PrevAndNext(
		long layoutRevisionId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the layout revisions where layoutSetBranchId = &#63; and head = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @return the matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutRevision> findByL_H(
		long layoutSetBranchId, boolean head)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the layout revisions where layoutSetBranchId = &#63; and head = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @return the range of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutRevision> findByL_H(
		long layoutSetBranchId, boolean head, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and head = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutRevision> findByL_H(
		long layoutSetBranchId, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision findByL_H_First(
		long layoutSetBranchId, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision findByL_H_Last(
		long layoutSetBranchId, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutRevisionId the primary key of the current layout revision
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision[] findByL_H_PrevAndNext(
		long layoutRevisionId, long layoutSetBranchId, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the layout revisions where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @return the matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutRevision> findByL_P(
		long layoutSetBranchId, long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the layout revisions where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @return the range of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutRevision> findByL_P(
		long layoutSetBranchId, long plid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutRevision> findByL_P(
		long layoutSetBranchId, long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision findByL_P_First(
		long layoutSetBranchId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision findByL_P_Last(
		long layoutSetBranchId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutRevisionId the primary key of the current layout revision
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision[] findByL_P_PrevAndNext(
		long layoutRevisionId, long layoutSetBranchId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the layout revisions where layoutSetBranchId = &#63; and status = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param status the status
	* @return the matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutRevision> findByL_S(
		long layoutSetBranchId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the layout revisions where layoutSetBranchId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param status the status
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @return the range of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutRevision> findByL_S(
		long layoutSetBranchId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param status the status
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutRevision> findByL_S(
		long layoutSetBranchId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision findByL_S_First(
		long layoutSetBranchId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision findByL_S_Last(
		long layoutSetBranchId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutRevisionId the primary key of the current layout revision
	* @param layoutSetBranchId the layout set branch ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision[] findByL_S_PrevAndNext(
		long layoutRevisionId, long layoutSetBranchId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the layout revisions where head = &#63; and plid = &#63;.
	*
	* @param head the head
	* @param plid the plid
	* @return the matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutRevision> findByH_P(
		boolean head, long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the layout revisions where head = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param head the head
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @return the range of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutRevision> findByH_P(
		boolean head, long plid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the layout revisions where head = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param head the head
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutRevision> findByH_P(
		boolean head, long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first layout revision in the ordered set where head = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param head the head
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision findByH_P_First(
		boolean head, long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last layout revision in the ordered set where head = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param head the head
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision findByH_P_Last(
		boolean head, long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the layout revisions before and after the current layout revision in the ordered set where head = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutRevisionId the primary key of the current layout revision
	* @param head the head
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision[] findByH_P_PrevAndNext(
		long layoutRevisionId, boolean head, long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the layout revisions where plid = &#63; and status &ne; &#63;.
	*
	* @param plid the plid
	* @param status the status
	* @return the matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutRevision> findByP_NotS(
		long plid, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the layout revisions where plid = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param plid the plid
	* @param status the status
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @return the range of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutRevision> findByP_NotS(
		long plid, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the layout revisions where plid = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param plid the plid
	* @param status the status
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutRevision> findByP_NotS(
		long plid, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first layout revision in the ordered set where plid = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param plid the plid
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision findByP_NotS_First(
		long plid, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last layout revision in the ordered set where plid = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param plid the plid
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision findByP_NotS_Last(
		long plid, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the layout revisions before and after the current layout revision in the ordered set where plid = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutRevisionId the primary key of the current layout revision
	* @param plid the plid
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision[] findByP_NotS_PrevAndNext(
		long layoutRevisionId, long plid, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the layout revisions where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param layoutBranchId the layout branch ID
	* @param plid the plid
	* @return the matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutRevision> findByL_L_P(
		long layoutSetBranchId, long layoutBranchId, long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the layout revisions where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param layoutBranchId the layout branch ID
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @return the range of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutRevision> findByL_L_P(
		long layoutSetBranchId, long layoutBranchId, long plid, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param layoutBranchId the layout branch ID
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutRevision> findByL_L_P(
		long layoutSetBranchId, long layoutBranchId, long plid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param layoutBranchId the layout branch ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision findByL_L_P_First(
		long layoutSetBranchId, long layoutBranchId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param layoutBranchId the layout branch ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision findByL_L_P_Last(
		long layoutSetBranchId, long layoutBranchId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutRevisionId the primary key of the current layout revision
	* @param layoutSetBranchId the layout set branch ID
	* @param layoutBranchId the layout branch ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision[] findByL_L_P_PrevAndNext(
		long layoutRevisionId, long layoutSetBranchId, long layoutBranchId,
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the layout revisions where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param parentLayoutRevisionId the parent layout revision ID
	* @param plid the plid
	* @return the matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutRevision> findByL_P_P(
		long layoutSetBranchId, long parentLayoutRevisionId, long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the layout revisions where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param parentLayoutRevisionId the parent layout revision ID
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @return the range of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutRevision> findByL_P_P(
		long layoutSetBranchId, long parentLayoutRevisionId, long plid,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param parentLayoutRevisionId the parent layout revision ID
	* @param plid the plid
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutRevision> findByL_P_P(
		long layoutSetBranchId, long parentLayoutRevisionId, long plid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param parentLayoutRevisionId the parent layout revision ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision findByL_P_P_First(
		long layoutSetBranchId, long parentLayoutRevisionId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param parentLayoutRevisionId the parent layout revision ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision findByL_P_P_Last(
		long layoutSetBranchId, long parentLayoutRevisionId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutRevisionId the primary key of the current layout revision
	* @param layoutSetBranchId the layout set branch ID
	* @param parentLayoutRevisionId the parent layout revision ID
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision[] findByL_P_P_PrevAndNext(
		long layoutRevisionId, long layoutSetBranchId,
		long parentLayoutRevisionId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the layout revision where layoutSetBranchId = &#63; and head = &#63; and plid = &#63; or throws a {@link com.liferay.portal.NoSuchLayoutRevisionException} if it could not be found.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param plid the plid
	* @return the matching layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision findByL_H_P(
		long layoutSetBranchId, boolean head, long plid)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the layout revision where layoutSetBranchId = &#63; and head = &#63; and plid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param plid the plid
	* @return the matching layout revision, or <code>null</code> if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision fetchByL_H_P(
		long layoutSetBranchId, boolean head, long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the layout revision where layoutSetBranchId = &#63; and head = &#63; and plid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param plid the plid
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching layout revision, or <code>null</code> if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision fetchByL_H_P(
		long layoutSetBranchId, boolean head, long plid,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the layout revisions where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param status the status
	* @return the matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutRevision> findByL_P_S(
		long layoutSetBranchId, long plid, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the layout revisions where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param status the status
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @return the range of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutRevision> findByL_P_S(
		long layoutSetBranchId, long plid, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param status the status
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutRevision> findByL_P_S(
		long layoutSetBranchId, long plid, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision findByL_P_S_First(
		long layoutSetBranchId, long plid, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision findByL_P_S_Last(
		long layoutSetBranchId, long plid, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutRevisionId the primary key of the current layout revision
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision[] findByL_P_S_PrevAndNext(
		long layoutRevisionId, long layoutSetBranchId, long plid, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the layout revisions.
	*
	* @return the layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutRevision> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the layout revisions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @return the range of layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutRevision> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the layout revisions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of layout revisions
	* @param end the upper bound of the range of layout revisions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutRevision> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the layout revisions where layoutSetBranchId = &#63; from the database.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByLayoutSetBranchId(long layoutSetBranchId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the layout revisions where plid = &#63; from the database.
	*
	* @param plid the plid
	* @throws SystemException if a system exception occurred
	*/
	public void removeByPlid(long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the layout revisions where layoutSetBranchId = &#63; and head = &#63; from the database.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @throws SystemException if a system exception occurred
	*/
	public void removeByL_H(long layoutSetBranchId, boolean head)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the layout revisions where layoutSetBranchId = &#63; and plid = &#63; from the database.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @throws SystemException if a system exception occurred
	*/
	public void removeByL_P(long layoutSetBranchId, long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the layout revisions where layoutSetBranchId = &#63; and status = &#63; from the database.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param status the status
	* @throws SystemException if a system exception occurred
	*/
	public void removeByL_S(long layoutSetBranchId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the layout revisions where head = &#63; and plid = &#63; from the database.
	*
	* @param head the head
	* @param plid the plid
	* @throws SystemException if a system exception occurred
	*/
	public void removeByH_P(boolean head, long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the layout revisions where plid = &#63; and status &ne; &#63; from the database.
	*
	* @param plid the plid
	* @param status the status
	* @throws SystemException if a system exception occurred
	*/
	public void removeByP_NotS(long plid, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the layout revisions where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63; from the database.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param layoutBranchId the layout branch ID
	* @param plid the plid
	* @throws SystemException if a system exception occurred
	*/
	public void removeByL_L_P(long layoutSetBranchId, long layoutBranchId,
		long plid) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the layout revisions where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63; from the database.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param parentLayoutRevisionId the parent layout revision ID
	* @param plid the plid
	* @throws SystemException if a system exception occurred
	*/
	public void removeByL_P_P(long layoutSetBranchId,
		long parentLayoutRevisionId, long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the layout revision where layoutSetBranchId = &#63; and head = &#63; and plid = &#63; from the database.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param plid the plid
	* @return the layout revision that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision removeByL_H_P(
		long layoutSetBranchId, boolean head, long plid)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the layout revisions where layoutSetBranchId = &#63; and plid = &#63; and status = &#63; from the database.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param status the status
	* @throws SystemException if a system exception occurred
	*/
	public void removeByL_P_S(long layoutSetBranchId, long plid, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the layout revisions from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of layout revisions where layoutSetBranchId = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @return the number of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public int countByLayoutSetBranchId(long layoutSetBranchId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of layout revisions where plid = &#63;.
	*
	* @param plid the plid
	* @return the number of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public int countByPlid(long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of layout revisions where layoutSetBranchId = &#63; and head = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @return the number of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public int countByL_H(long layoutSetBranchId, boolean head)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of layout revisions where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @return the number of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public int countByL_P(long layoutSetBranchId, long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of layout revisions where layoutSetBranchId = &#63; and status = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param status the status
	* @return the number of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public int countByL_S(long layoutSetBranchId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of layout revisions where head = &#63; and plid = &#63;.
	*
	* @param head the head
	* @param plid the plid
	* @return the number of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public int countByH_P(boolean head, long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of layout revisions where plid = &#63; and status &ne; &#63;.
	*
	* @param plid the plid
	* @param status the status
	* @return the number of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public int countByP_NotS(long plid, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of layout revisions where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param layoutBranchId the layout branch ID
	* @param plid the plid
	* @return the number of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public int countByL_L_P(long layoutSetBranchId, long layoutBranchId,
		long plid) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of layout revisions where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param parentLayoutRevisionId the parent layout revision ID
	* @param plid the plid
	* @return the number of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public int countByL_P_P(long layoutSetBranchId,
		long parentLayoutRevisionId, long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of layout revisions where layoutSetBranchId = &#63; and head = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param head the head
	* @param plid the plid
	* @return the number of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public int countByL_H_P(long layoutSetBranchId, boolean head, long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of layout revisions where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID
	* @param plid the plid
	* @param status the status
	* @return the number of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public int countByL_P_S(long layoutSetBranchId, long plid, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of layout revisions.
	*
	* @return the number of layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}