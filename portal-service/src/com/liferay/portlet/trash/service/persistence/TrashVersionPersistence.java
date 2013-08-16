/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.trash.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.trash.model.TrashVersion;

/**
 * The persistence interface for the trash version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TrashVersionPersistenceImpl
 * @see TrashVersionUtil
 * @generated
 */
public interface TrashVersionPersistence extends BasePersistence<TrashVersion> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link TrashVersionUtil} to access the trash version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the trash versions where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @return the matching trash versions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.trash.model.TrashVersion> findByEntryId(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the trash versions where entryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.trash.model.impl.TrashVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param entryId the entry ID
	* @param start the lower bound of the range of trash versions
	* @param end the upper bound of the range of trash versions (not inclusive)
	* @return the range of matching trash versions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.trash.model.TrashVersion> findByEntryId(
		long entryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the trash versions where entryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.trash.model.impl.TrashVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param entryId the entry ID
	* @param start the lower bound of the range of trash versions
	* @param end the upper bound of the range of trash versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching trash versions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.trash.model.TrashVersion> findByEntryId(
		long entryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first trash version in the ordered set where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching trash version
	* @throws com.liferay.portlet.trash.NoSuchVersionException if a matching trash version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.trash.model.TrashVersion findByEntryId_First(
		long entryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.trash.NoSuchVersionException;

	/**
	* Returns the first trash version in the ordered set where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching trash version, or <code>null</code> if a matching trash version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.trash.model.TrashVersion fetchByEntryId_First(
		long entryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last trash version in the ordered set where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching trash version
	* @throws com.liferay.portlet.trash.NoSuchVersionException if a matching trash version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.trash.model.TrashVersion findByEntryId_Last(
		long entryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.trash.NoSuchVersionException;

	/**
	* Returns the last trash version in the ordered set where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching trash version, or <code>null</code> if a matching trash version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.trash.model.TrashVersion fetchByEntryId_Last(
		long entryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the trash versions before and after the current trash version in the ordered set where entryId = &#63;.
	*
	* @param versionId the primary key of the current trash version
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next trash version
	* @throws com.liferay.portlet.trash.NoSuchVersionException if a trash version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.trash.model.TrashVersion[] findByEntryId_PrevAndNext(
		long versionId, long entryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.trash.NoSuchVersionException;

	/**
	* Removes all the trash versions where entryId = &#63; from the database.
	*
	* @param entryId the entry ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByEntryId(long entryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of trash versions where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @return the number of matching trash versions
	* @throws SystemException if a system exception occurred
	*/
	public int countByEntryId(long entryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the trash versions where entryId = &#63; and classNameId = &#63;.
	*
	* @param entryId the entry ID
	* @param classNameId the class name ID
	* @return the matching trash versions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.trash.model.TrashVersion> findByE_C(
		long entryId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the trash versions where entryId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.trash.model.impl.TrashVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param entryId the entry ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of trash versions
	* @param end the upper bound of the range of trash versions (not inclusive)
	* @return the range of matching trash versions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.trash.model.TrashVersion> findByE_C(
		long entryId, long classNameId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the trash versions where entryId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.trash.model.impl.TrashVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param entryId the entry ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of trash versions
	* @param end the upper bound of the range of trash versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching trash versions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.trash.model.TrashVersion> findByE_C(
		long entryId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first trash version in the ordered set where entryId = &#63; and classNameId = &#63;.
	*
	* @param entryId the entry ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching trash version
	* @throws com.liferay.portlet.trash.NoSuchVersionException if a matching trash version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.trash.model.TrashVersion findByE_C_First(
		long entryId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.trash.NoSuchVersionException;

	/**
	* Returns the first trash version in the ordered set where entryId = &#63; and classNameId = &#63;.
	*
	* @param entryId the entry ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching trash version, or <code>null</code> if a matching trash version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.trash.model.TrashVersion fetchByE_C_First(
		long entryId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last trash version in the ordered set where entryId = &#63; and classNameId = &#63;.
	*
	* @param entryId the entry ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching trash version
	* @throws com.liferay.portlet.trash.NoSuchVersionException if a matching trash version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.trash.model.TrashVersion findByE_C_Last(
		long entryId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.trash.NoSuchVersionException;

	/**
	* Returns the last trash version in the ordered set where entryId = &#63; and classNameId = &#63;.
	*
	* @param entryId the entry ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching trash version, or <code>null</code> if a matching trash version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.trash.model.TrashVersion fetchByE_C_Last(
		long entryId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the trash versions before and after the current trash version in the ordered set where entryId = &#63; and classNameId = &#63;.
	*
	* @param versionId the primary key of the current trash version
	* @param entryId the entry ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next trash version
	* @throws com.liferay.portlet.trash.NoSuchVersionException if a trash version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.trash.model.TrashVersion[] findByE_C_PrevAndNext(
		long versionId, long entryId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.trash.NoSuchVersionException;

	/**
	* Removes all the trash versions where entryId = &#63; and classNameId = &#63; from the database.
	*
	* @param entryId the entry ID
	* @param classNameId the class name ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByE_C(long entryId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of trash versions where entryId = &#63; and classNameId = &#63;.
	*
	* @param entryId the entry ID
	* @param classNameId the class name ID
	* @return the number of matching trash versions
	* @throws SystemException if a system exception occurred
	*/
	public int countByE_C(long entryId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the trash versions where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching trash versions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.trash.model.TrashVersion> findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the trash versions where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.trash.model.impl.TrashVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of trash versions
	* @param end the upper bound of the range of trash versions (not inclusive)
	* @return the range of matching trash versions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.trash.model.TrashVersion> findByC_C(
		long classNameId, long classPK, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the trash versions where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.trash.model.impl.TrashVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of trash versions
	* @param end the upper bound of the range of trash versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching trash versions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.trash.model.TrashVersion> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first trash version in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching trash version
	* @throws com.liferay.portlet.trash.NoSuchVersionException if a matching trash version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.trash.model.TrashVersion findByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.trash.NoSuchVersionException;

	/**
	* Returns the first trash version in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching trash version, or <code>null</code> if a matching trash version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.trash.model.TrashVersion fetchByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last trash version in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching trash version
	* @throws com.liferay.portlet.trash.NoSuchVersionException if a matching trash version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.trash.model.TrashVersion findByC_C_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.trash.NoSuchVersionException;

	/**
	* Returns the last trash version in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching trash version, or <code>null</code> if a matching trash version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.trash.model.TrashVersion fetchByC_C_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the trash versions before and after the current trash version in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param versionId the primary key of the current trash version
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next trash version
	* @throws com.liferay.portlet.trash.NoSuchVersionException if a trash version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.trash.model.TrashVersion[] findByC_C_PrevAndNext(
		long versionId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.trash.NoSuchVersionException;

	/**
	* Removes all the trash versions where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of trash versions where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching trash versions
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the trash version where entryId = &#63; and classNameId = &#63; and classPK = &#63; or throws a {@link com.liferay.portlet.trash.NoSuchVersionException} if it could not be found.
	*
	* @param entryId the entry ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching trash version
	* @throws com.liferay.portlet.trash.NoSuchVersionException if a matching trash version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.trash.model.TrashVersion findByE_C_C(
		long entryId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.trash.NoSuchVersionException;

	/**
	* Returns the trash version where entryId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param entryId the entry ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching trash version, or <code>null</code> if a matching trash version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.trash.model.TrashVersion fetchByE_C_C(
		long entryId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the trash version where entryId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param entryId the entry ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching trash version, or <code>null</code> if a matching trash version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.trash.model.TrashVersion fetchByE_C_C(
		long entryId, long classNameId, long classPK, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the trash version where entryId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param entryId the entry ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the trash version that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.trash.model.TrashVersion removeByE_C_C(
		long entryId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.trash.NoSuchVersionException;

	/**
	* Returns the number of trash versions where entryId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param entryId the entry ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching trash versions
	* @throws SystemException if a system exception occurred
	*/
	public int countByE_C_C(long entryId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Caches the trash version in the entity cache if it is enabled.
	*
	* @param trashVersion the trash version
	*/
	public void cacheResult(
		com.liferay.portlet.trash.model.TrashVersion trashVersion);

	/**
	* Caches the trash versions in the entity cache if it is enabled.
	*
	* @param trashVersions the trash versions
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.trash.model.TrashVersion> trashVersions);

	/**
	* Creates a new trash version with the primary key. Does not add the trash version to the database.
	*
	* @param versionId the primary key for the new trash version
	* @return the new trash version
	*/
	public com.liferay.portlet.trash.model.TrashVersion create(long versionId);

	/**
	* Removes the trash version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param versionId the primary key of the trash version
	* @return the trash version that was removed
	* @throws com.liferay.portlet.trash.NoSuchVersionException if a trash version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.trash.model.TrashVersion remove(long versionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.trash.NoSuchVersionException;

	public com.liferay.portlet.trash.model.TrashVersion updateImpl(
		com.liferay.portlet.trash.model.TrashVersion trashVersion)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the trash version with the primary key or throws a {@link com.liferay.portlet.trash.NoSuchVersionException} if it could not be found.
	*
	* @param versionId the primary key of the trash version
	* @return the trash version
	* @throws com.liferay.portlet.trash.NoSuchVersionException if a trash version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.trash.model.TrashVersion findByPrimaryKey(
		long versionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.trash.NoSuchVersionException;

	/**
	* Returns the trash version with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param versionId the primary key of the trash version
	* @return the trash version, or <code>null</code> if a trash version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.trash.model.TrashVersion fetchByPrimaryKey(
		long versionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the trash versions.
	*
	* @return the trash versions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.trash.model.TrashVersion> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the trash versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.trash.model.impl.TrashVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of trash versions
	* @param end the upper bound of the range of trash versions (not inclusive)
	* @return the range of trash versions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.trash.model.TrashVersion> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the trash versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.trash.model.impl.TrashVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of trash versions
	* @param end the upper bound of the range of trash versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of trash versions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.trash.model.TrashVersion> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the trash versions from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of trash versions.
	*
	* @return the number of trash versions
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}