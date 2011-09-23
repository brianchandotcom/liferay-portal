/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.documentlibrary.model.DLFileEntryType;

/**
 * The persistence interface for the document library file entry type service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryTypePersistenceImpl
 * @see DLFileEntryTypeUtil
 * @generated
 */
public interface DLFileEntryTypePersistence extends BasePersistence<DLFileEntryType> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DLFileEntryTypeUtil} to access the document library file entry type persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the document library file entry type in the entity cache if it is enabled.
	*
	* @param dlFileEntryType the document library file entry type
	*/
	public void cacheResult(
		com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType);

	/**
	* Caches the document library file entry types in the entity cache if it is enabled.
	*
	* @param dlFileEntryTypes the document library file entry types
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> dlFileEntryTypes);

	/**
	* Creates a new document library file entry type with the primary key. Does not add the document library file entry type to the database.
	*
	* @param fileEntryTypeId the primary key for the new document library file entry type
	* @return the new document library file entry type
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntryType create(
		long fileEntryTypeId);

	/**
	* Removes the document library file entry type with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileEntryTypeId the primary key of the document library file entry type
	* @return the document library file entry type that was removed
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException if a document library file entry type with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntryType remove(
		long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException;

	public com.liferay.portlet.documentlibrary.model.DLFileEntryType updateImpl(
		com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the document library file entry type with the primary key or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException} if it could not be found.
	*
	* @param fileEntryTypeId the primary key of the document library file entry type
	* @return the document library file entry type
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException if a document library file entry type with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntryType findByPrimaryKey(
		long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException;

	/**
	* Returns the document library file entry type with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param fileEntryTypeId the primary key of the document library file entry type
	* @return the document library file entry type, or <code>null</code> if a document library file entry type with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntryType fetchByPrimaryKey(
		long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the document library file entry types where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching document library file entry types
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the document library file entry types where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of document library file entry types
	* @param end the upper bound of the range of document library file entry types (not inclusive)
	* @return the range of matching document library file entry types
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the document library file entry types where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of document library file entry types
	* @param end the upper bound of the range of document library file entry types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entry types
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first document library file entry type in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry type
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException if a matching document library file entry type could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntryType findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException;

	/**
	* Returns the last document library file entry type in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry type
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException if a matching document library file entry type could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntryType findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException;

	/**
	* Returns the document library file entry types before and after the current document library file entry type in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileEntryTypeId the primary key of the current document library file entry type
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file entry type
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException if a document library file entry type with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntryType[] findByGroupId_PrevAndNext(
		long fileEntryTypeId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException;

	/**
	* Returns all the document library file entry types where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupIds the group IDs
	* @return the matching document library file entry types
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> findByGroupId(
		long[] groupIds)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the document library file entry types where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of document library file entry types
	* @param end the upper bound of the range of document library file entry types (not inclusive)
	* @return the range of matching document library file entry types
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> findByGroupId(
		long[] groupIds, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the document library file entry types where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of document library file entry types
	* @param end the upper bound of the range of document library file entry types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entry types
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> findByGroupId(
		long[] groupIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the document library file entry types that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching document library file entry types that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> filterFindByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the document library file entry types that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of document library file entry types
	* @param end the upper bound of the range of document library file entry types (not inclusive)
	* @return the range of matching document library file entry types that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> filterFindByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the document library file entry types that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of document library file entry types
	* @param end the upper bound of the range of document library file entry types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entry types that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the document library file entry types before and after the current document library file entry type in the ordered set of document library file entry types that the user has permission to view where groupId = &#63;.
	*
	* @param fileEntryTypeId the primary key of the current document library file entry type
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file entry type
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException if a document library file entry type with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntryType[] filterFindByGroupId_PrevAndNext(
		long fileEntryTypeId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException;

	/**
	* Returns all the document library file entry types that the user has permission to view where groupId = any &#63;.
	*
	* @param groupIds the group IDs
	* @return the matching document library file entry types that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> filterFindByGroupId(
		long[] groupIds)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the document library file entry types that the user has permission to view where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of document library file entry types
	* @param end the upper bound of the range of document library file entry types (not inclusive)
	* @return the range of matching document library file entry types that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> filterFindByGroupId(
		long[] groupIds, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the document library file entry types that the user has permission to view where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of document library file entry types
	* @param end the upper bound of the range of document library file entry types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entry types that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> filterFindByGroupId(
		long[] groupIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the document library file entry types where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @return the matching document library file entry types
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> findByG_N_D(
		long groupId, java.lang.String name, java.lang.String description)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the document library file entry types where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @param start the lower bound of the range of document library file entry types
	* @param end the upper bound of the range of document library file entry types (not inclusive)
	* @return the range of matching document library file entry types
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> findByG_N_D(
		long groupId, java.lang.String name, java.lang.String description,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the document library file entry types where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @param start the lower bound of the range of document library file entry types
	* @param end the upper bound of the range of document library file entry types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entry types
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> findByG_N_D(
		long groupId, java.lang.String name, java.lang.String description,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first document library file entry type in the ordered set where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file entry type
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException if a matching document library file entry type could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntryType findByG_N_D_First(
		long groupId, java.lang.String name, java.lang.String description,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException;

	/**
	* Returns the last document library file entry type in the ordered set where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file entry type
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException if a matching document library file entry type could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntryType findByG_N_D_Last(
		long groupId, java.lang.String name, java.lang.String description,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException;

	/**
	* Returns the document library file entry types before and after the current document library file entry type in the ordered set where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileEntryTypeId the primary key of the current document library file entry type
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file entry type
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException if a document library file entry type with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntryType[] findByG_N_D_PrevAndNext(
		long fileEntryTypeId, long groupId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException;

	/**
	* Returns all the document library file entry types that the user has permission to view where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @return the matching document library file entry types that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> filterFindByG_N_D(
		long groupId, java.lang.String name, java.lang.String description)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the document library file entry types that the user has permission to view where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @param start the lower bound of the range of document library file entry types
	* @param end the upper bound of the range of document library file entry types (not inclusive)
	* @return the range of matching document library file entry types that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> filterFindByG_N_D(
		long groupId, java.lang.String name, java.lang.String description,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the document library file entry types that the user has permissions to view where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @param start the lower bound of the range of document library file entry types
	* @param end the upper bound of the range of document library file entry types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file entry types that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> filterFindByG_N_D(
		long groupId, java.lang.String name, java.lang.String description,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the document library file entry types before and after the current document library file entry type in the ordered set of document library file entry types that the user has permission to view where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* @param fileEntryTypeId the primary key of the current document library file entry type
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file entry type
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException if a document library file entry type with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileEntryType[] filterFindByG_N_D_PrevAndNext(
		long fileEntryTypeId, long groupId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException;

	/**
	* Returns all the document library file entry types.
	*
	* @return the document library file entry types
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the document library file entry types.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of document library file entry types
	* @param end the upper bound of the range of document library file entry types (not inclusive)
	* @return the range of document library file entry types
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the document library file entry types.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of document library file entry types
	* @param end the upper bound of the range of document library file entry types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of document library file entry types
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the document library file entry types where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the document library file entry types where groupId = &#63; and name = &#63; and description = &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_N_D(long groupId, java.lang.String name,
		java.lang.String description)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the document library file entry types from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library file entry types where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching document library file entry types
	* @throws SystemException if a system exception occurred
	*/
	public int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library file entry types where groupId = any &#63;.
	*
	* @param groupIds the group IDs
	* @return the number of matching document library file entry types
	* @throws SystemException if a system exception occurred
	*/
	public int countByGroupId(long[] groupIds)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library file entry types that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching document library file entry types that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library file entry types that the user has permission to view where groupId = any &#63;.
	*
	* @param groupIds the group IDs
	* @return the number of matching document library file entry types that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByGroupId(long[] groupIds)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library file entry types where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @return the number of matching document library file entry types
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_N_D(long groupId, java.lang.String name,
		java.lang.String description)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library file entry types that the user has permission to view where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param description the description
	* @return the number of matching document library file entry types that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByG_N_D(long groupId, java.lang.String name,
		java.lang.String description)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library file entry types.
	*
	* @return the number of document library file entry types
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the document library folders associated with the document library file entry type.
	*
	* @param pk the primary key of the document library file entry type
	* @return the document library folders associated with the document library file entry type
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getDLFolders(
		long pk) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the document library folders associated with the document library file entry type.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the document library file entry type
	* @param start the lower bound of the range of document library file entry types
	* @param end the upper bound of the range of document library file entry types (not inclusive)
	* @return the range of document library folders associated with the document library file entry type
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getDLFolders(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the document library folders associated with the document library file entry type.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the document library file entry type
	* @param start the lower bound of the range of document library file entry types
	* @param end the upper bound of the range of document library file entry types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of document library folders associated with the document library file entry type
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getDLFolders(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library folders associated with the document library file entry type.
	*
	* @param pk the primary key of the document library file entry type
	* @return the number of document library folders associated with the document library file entry type
	* @throws SystemException if a system exception occurred
	*/
	public int getDLFoldersSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns <code>true</code> if the document library folder is associated with the document library file entry type.
	*
	* @param pk the primary key of the document library file entry type
	* @param dlFolderPK the primary key of the document library folder
	* @return <code>true</code> if the document library folder is associated with the document library file entry type; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public boolean containsDLFolder(long pk, long dlFolderPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns <code>true</code> if the document library file entry type has any document library folders associated with it.
	*
	* @param pk the primary key of the document library file entry type to check for associations with document library folders
	* @return <code>true</code> if the document library file entry type has any document library folders associated with it; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public boolean containsDLFolders(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the document library file entry type and the document library folder. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library file entry type
	* @param dlFolderPK the primary key of the document library folder
	* @throws SystemException if a system exception occurred
	*/
	public void addDLFolder(long pk, long dlFolderPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the document library file entry type and the document library folder. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library file entry type
	* @param dlFolder the document library folder
	* @throws SystemException if a system exception occurred
	*/
	public void addDLFolder(long pk,
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the document library file entry type and the document library folders. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library file entry type
	* @param dlFolderPKs the primary keys of the document library folders
	* @throws SystemException if a system exception occurred
	*/
	public void addDLFolders(long pk, long[] dlFolderPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the document library file entry type and the document library folders. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library file entry type
	* @param dlFolders the document library folders
	* @throws SystemException if a system exception occurred
	*/
	public void addDLFolders(long pk,
		java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> dlFolders)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Clears all associations between the document library file entry type and its document library folders. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library file entry type to clear the associated document library folders from
	* @throws SystemException if a system exception occurred
	*/
	public void clearDLFolders(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the document library file entry type and the document library folder. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library file entry type
	* @param dlFolderPK the primary key of the document library folder
	* @throws SystemException if a system exception occurred
	*/
	public void removeDLFolder(long pk, long dlFolderPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the document library file entry type and the document library folder. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library file entry type
	* @param dlFolder the document library folder
	* @throws SystemException if a system exception occurred
	*/
	public void removeDLFolder(long pk,
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the document library file entry type and the document library folders. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library file entry type
	* @param dlFolderPKs the primary keys of the document library folders
	* @throws SystemException if a system exception occurred
	*/
	public void removeDLFolders(long pk, long[] dlFolderPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the document library file entry type and the document library folders. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library file entry type
	* @param dlFolders the document library folders
	* @throws SystemException if a system exception occurred
	*/
	public void removeDLFolders(long pk,
		java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> dlFolders)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Sets the document library folders associated with the document library file entry type, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library file entry type
	* @param dlFolderPKs the primary keys of the document library folders to be associated with the document library file entry type
	* @throws SystemException if a system exception occurred
	*/
	public void setDLFolders(long pk, long[] dlFolderPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Sets the document library folders associated with the document library file entry type, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library file entry type
	* @param dlFolders the document library folders to be associated with the document library file entry type
	* @throws SystemException if a system exception occurred
	*/
	public void setDLFolders(long pk,
		java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> dlFolders)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the d d m structures associated with the document library file entry type.
	*
	* @param pk the primary key of the document library file entry type
	* @return the d d m structures associated with the document library file entry type
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getDDMStructures(
		long pk) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the d d m structures associated with the document library file entry type.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the document library file entry type
	* @param start the lower bound of the range of document library file entry types
	* @param end the upper bound of the range of document library file entry types (not inclusive)
	* @return the range of d d m structures associated with the document library file entry type
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getDDMStructures(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the d d m structures associated with the document library file entry type.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the document library file entry type
	* @param start the lower bound of the range of document library file entry types
	* @param end the upper bound of the range of document library file entry types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d d m structures associated with the document library file entry type
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getDDMStructures(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of d d m structures associated with the document library file entry type.
	*
	* @param pk the primary key of the document library file entry type
	* @return the number of d d m structures associated with the document library file entry type
	* @throws SystemException if a system exception occurred
	*/
	public int getDDMStructuresSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns <code>true</code> if the d d m structure is associated with the document library file entry type.
	*
	* @param pk the primary key of the document library file entry type
	* @param ddmStructurePK the primary key of the d d m structure
	* @return <code>true</code> if the d d m structure is associated with the document library file entry type; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public boolean containsDDMStructure(long pk, long ddmStructurePK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns <code>true</code> if the document library file entry type has any d d m structures associated with it.
	*
	* @param pk the primary key of the document library file entry type to check for associations with d d m structures
	* @return <code>true</code> if the document library file entry type has any d d m structures associated with it; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public boolean containsDDMStructures(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the document library file entry type and the d d m structure. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library file entry type
	* @param ddmStructurePK the primary key of the d d m structure
	* @throws SystemException if a system exception occurred
	*/
	public void addDDMStructure(long pk, long ddmStructurePK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the document library file entry type and the d d m structure. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library file entry type
	* @param ddmStructure the d d m structure
	* @throws SystemException if a system exception occurred
	*/
	public void addDDMStructure(long pk,
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the document library file entry type and the d d m structures. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library file entry type
	* @param ddmStructurePKs the primary keys of the d d m structures
	* @throws SystemException if a system exception occurred
	*/
	public void addDDMStructures(long pk, long[] ddmStructurePKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the document library file entry type and the d d m structures. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library file entry type
	* @param ddmStructures the d d m structures
	* @throws SystemException if a system exception occurred
	*/
	public void addDDMStructures(long pk,
		java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> ddmStructures)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Clears all associations between the document library file entry type and its d d m structures. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library file entry type to clear the associated d d m structures from
	* @throws SystemException if a system exception occurred
	*/
	public void clearDDMStructures(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the document library file entry type and the d d m structure. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library file entry type
	* @param ddmStructurePK the primary key of the d d m structure
	* @throws SystemException if a system exception occurred
	*/
	public void removeDDMStructure(long pk, long ddmStructurePK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the document library file entry type and the d d m structure. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library file entry type
	* @param ddmStructure the d d m structure
	* @throws SystemException if a system exception occurred
	*/
	public void removeDDMStructure(long pk,
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the document library file entry type and the d d m structures. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library file entry type
	* @param ddmStructurePKs the primary keys of the d d m structures
	* @throws SystemException if a system exception occurred
	*/
	public void removeDDMStructures(long pk, long[] ddmStructurePKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the document library file entry type and the d d m structures. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library file entry type
	* @param ddmStructures the d d m structures
	* @throws SystemException if a system exception occurred
	*/
	public void removeDDMStructures(long pk,
		java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> ddmStructures)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Sets the d d m structures associated with the document library file entry type, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library file entry type
	* @param ddmStructurePKs the primary keys of the d d m structures to be associated with the document library file entry type
	* @throws SystemException if a system exception occurred
	*/
	public void setDDMStructures(long pk, long[] ddmStructurePKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Sets the d d m structures associated with the document library file entry type, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the document library file entry type
	* @param ddmStructures the d d m structures to be associated with the document library file entry type
	* @throws SystemException if a system exception occurred
	*/
	public void setDDMStructures(long pk,
		java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> ddmStructures)
		throws com.liferay.portal.kernel.exception.SystemException;

	public DLFileEntryType remove(DLFileEntryType dlFileEntryType)
		throws SystemException;
}