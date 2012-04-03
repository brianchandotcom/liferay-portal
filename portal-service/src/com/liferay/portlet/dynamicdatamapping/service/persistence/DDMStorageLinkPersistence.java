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

package com.liferay.portlet.dynamicdatamapping.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink;

/**
 * The persistence interface for the d d m storage link service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStorageLinkPersistenceImpl
 * @see DDMStorageLinkUtil
 * @generated
 */
public interface DDMStorageLinkPersistence extends BasePersistence<DDMStorageLink> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDMStorageLinkUtil} to access the d d m storage link persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the d d m storage link in the entity cache if it is enabled.
	*
	* @param ddmStorageLink the d d m storage link
	*/
	public void cacheResult(
		com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink ddmStorageLink);

	/**
	* Caches the d d m storage links in the entity cache if it is enabled.
	*
	* @param ddmStorageLinks the d d m storage links
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink> ddmStorageLinks);

	/**
	* Creates a new d d m storage link with the primary key. Does not add the d d m storage link to the database.
	*
	* @param storageLinkId the primary key for the new d d m storage link
	* @return the new d d m storage link
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink create(
		long storageLinkId);

	/**
	* Removes the d d m storage link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param storageLinkId the primary key of the d d m storage link
	* @return the d d m storage link that was removed
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStorageLinkException if a d d m storage link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink remove(
		long storageLinkId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStorageLinkException;

	public com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink updateImpl(
		com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink ddmStorageLink,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the d d m storage link with the primary key or throws a {@link com.liferay.portlet.dynamicdatamapping.NoSuchStorageLinkException} if it could not be found.
	*
	* @param storageLinkId the primary key of the d d m storage link
	* @return the d d m storage link
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStorageLinkException if a d d m storage link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink findByPrimaryKey(
		long storageLinkId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStorageLinkException;

	/**
	* Returns the d d m storage link with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param storageLinkId the primary key of the d d m storage link
	* @return the d d m storage link, or <code>null</code> if a d d m storage link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink fetchByPrimaryKey(
		long storageLinkId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the d d m storage links where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching d d m storage links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the d d m storage links where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d m storage links
	* @param end the upper bound of the range of d d m storage links (not inclusive)
	* @return the range of matching d d m storage links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the d d m storage links where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d m storage links
	* @param end the upper bound of the range of d d m storage links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m storage links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first d d m storage link in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m storage link
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStorageLinkException if a matching d d m storage link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStorageLinkException;

	/**
	* Returns the last d d m storage link in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m storage link
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStorageLinkException if a matching d d m storage link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStorageLinkException;

	/**
	* Returns the d d m storage links before and after the current d d m storage link in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param storageLinkId the primary key of the current d d m storage link
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m storage link
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStorageLinkException if a d d m storage link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink[] findByUuid_PrevAndNext(
		long storageLinkId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStorageLinkException;

	/**
	* Returns the d d m storage link where classPK = &#63; or throws a {@link com.liferay.portlet.dynamicdatamapping.NoSuchStorageLinkException} if it could not be found.
	*
	* @param classPK the class p k
	* @return the matching d d m storage link
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStorageLinkException if a matching d d m storage link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink findByClassPK(
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStorageLinkException;

	/**
	* Returns the d d m storage link where classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param classPK the class p k
	* @return the matching d d m storage link, or <code>null</code> if a matching d d m storage link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink fetchByClassPK(
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the d d m storage link where classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param classPK the class p k
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching d d m storage link, or <code>null</code> if a matching d d m storage link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink fetchByClassPK(
		long classPK, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the d d m storage links where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @return the matching d d m storage links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink> findByStructureId(
		long structureId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the d d m storage links where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID
	* @param start the lower bound of the range of d d m storage links
	* @param end the upper bound of the range of d d m storage links (not inclusive)
	* @return the range of matching d d m storage links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink> findByStructureId(
		long structureId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the d d m storage links where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID
	* @param start the lower bound of the range of d d m storage links
	* @param end the upper bound of the range of d d m storage links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m storage links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink> findByStructureId(
		long structureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first d d m storage link in the ordered set where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m storage link
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStorageLinkException if a matching d d m storage link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink findByStructureId_First(
		long structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStorageLinkException;

	/**
	* Returns the last d d m storage link in the ordered set where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m storage link
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStorageLinkException if a matching d d m storage link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink findByStructureId_Last(
		long structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStorageLinkException;

	/**
	* Returns the d d m storage links before and after the current d d m storage link in the ordered set where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param storageLinkId the primary key of the current d d m storage link
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m storage link
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStorageLinkException if a d d m storage link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink[] findByStructureId_PrevAndNext(
		long storageLinkId, long structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStorageLinkException;

	/**
	* Returns all the d d m storage links.
	*
	* @return the d d m storage links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the d d m storage links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d d m storage links
	* @param end the upper bound of the range of d d m storage links (not inclusive)
	* @return the range of d d m storage links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the d d m storage links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d d m storage links
	* @param end the upper bound of the range of d d m storage links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d d m storage links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the d d m storage links where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the d d m storage link where classPK = &#63; from the database.
	*
	* @param classPK the class p k
	* @return the d d m storage link that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink removeByClassPK(
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStorageLinkException;

	/**
	* Removes all the d d m storage links where structureId = &#63; from the database.
	*
	* @param structureId the structure ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByStructureId(long structureId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the d d m storage links from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of d d m storage links where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching d d m storage links
	* @throws SystemException if a system exception occurred
	*/
	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of d d m storage links where classPK = &#63;.
	*
	* @param classPK the class p k
	* @return the number of matching d d m storage links
	* @throws SystemException if a system exception occurred
	*/
	public int countByClassPK(long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of d d m storage links where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @return the number of matching d d m storage links
	* @throws SystemException if a system exception occurred
	*/
	public int countByStructureId(long structureId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of d d m storage links.
	*
	* @return the number of d d m storage links
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}