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

import com.liferay.portlet.dynamicdatamapping.model.DDMContent;

/**
 * The persistence interface for the d d m content service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMContentPersistenceImpl
 * @see DDMContentUtil
 * @generated
 */
public interface DDMContentPersistence extends BasePersistence<DDMContent> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDMContentUtil} to access the d d m content persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the d d m content in the entity cache if it is enabled.
	*
	* @param ddmContent the d d m content
	*/
	public void cacheResult(
		com.liferay.portlet.dynamicdatamapping.model.DDMContent ddmContent);

	/**
	* Caches the d d m contents in the entity cache if it is enabled.
	*
	* @param ddmContents the d d m contents
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMContent> ddmContents);

	/**
	* Creates a new d d m content with the primary key. Does not add the d d m content to the database.
	*
	* @param contentId the primary key for the new d d m content
	* @return the new d d m content
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMContent create(
		long contentId);

	/**
	* Removes the d d m content with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param contentId the primary key of the d d m content
	* @return the d d m content that was removed
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchContentException if a d d m content with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMContent remove(
		long contentId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchContentException;

	public com.liferay.portlet.dynamicdatamapping.model.DDMContent updateImpl(
		com.liferay.portlet.dynamicdatamapping.model.DDMContent ddmContent,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the d d m content with the primary key or throws a {@link com.liferay.portlet.dynamicdatamapping.NoSuchContentException} if it could not be found.
	*
	* @param contentId the primary key of the d d m content
	* @return the d d m content
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchContentException if a d d m content with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMContent findByPrimaryKey(
		long contentId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchContentException;

	/**
	* Returns the d d m content with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param contentId the primary key of the d d m content
	* @return the d d m content, or <code>null</code> if a d d m content with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMContent fetchByPrimaryKey(
		long contentId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the d d m contents where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching d d m contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMContent> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the d d m contents where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d m contents
	* @param end the upper bound of the range of d d m contents (not inclusive)
	* @return the range of matching d d m contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMContent> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the d d m contents where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d m contents
	* @param end the upper bound of the range of d d m contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMContent> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first d d m content in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m content
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchContentException if a matching d d m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMContent findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchContentException;

	/**
	* Returns the last d d m content in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m content
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchContentException if a matching d d m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMContent findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchContentException;

	/**
	* Returns the d d m contents before and after the current d d m content in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param contentId the primary key of the current d d m content
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m content
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchContentException if a d d m content with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMContent[] findByUuid_PrevAndNext(
		long contentId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchContentException;

	/**
	* Returns the d d m content where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.dynamicdatamapping.NoSuchContentException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching d d m content
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchContentException if a matching d d m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMContent findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchContentException;

	/**
	* Returns the d d m content where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching d d m content, or <code>null</code> if a matching d d m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMContent fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the d d m content where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching d d m content, or <code>null</code> if a matching d d m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMContent fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the d d m contents where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching d d m contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMContent> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the d d m contents where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m contents
	* @param end the upper bound of the range of d d m contents (not inclusive)
	* @return the range of matching d d m contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMContent> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the d d m contents where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m contents
	* @param end the upper bound of the range of d d m contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMContent> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first d d m content in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m content
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchContentException if a matching d d m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMContent findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchContentException;

	/**
	* Returns the last d d m content in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m content
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchContentException if a matching d d m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMContent findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchContentException;

	/**
	* Returns the d d m contents before and after the current d d m content in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param contentId the primary key of the current d d m content
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m content
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchContentException if a d d m content with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMContent[] findByGroupId_PrevAndNext(
		long contentId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchContentException;

	/**
	* Returns all the d d m contents where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching d d m contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMContent> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the d d m contents where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of d d m contents
	* @param end the upper bound of the range of d d m contents (not inclusive)
	* @return the range of matching d d m contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMContent> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the d d m contents where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of d d m contents
	* @param end the upper bound of the range of d d m contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMContent> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first d d m content in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m content
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchContentException if a matching d d m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMContent findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchContentException;

	/**
	* Returns the last d d m content in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m content
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchContentException if a matching d d m content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMContent findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchContentException;

	/**
	* Returns the d d m contents before and after the current d d m content in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param contentId the primary key of the current d d m content
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m content
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchContentException if a d d m content with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMContent[] findByCompanyId_PrevAndNext(
		long contentId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchContentException;

	/**
	* Returns all the d d m contents.
	*
	* @return the d d m contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMContent> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the d d m contents.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d d m contents
	* @param end the upper bound of the range of d d m contents (not inclusive)
	* @return the range of d d m contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMContent> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the d d m contents.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d d m contents
	* @param end the upper bound of the range of d d m contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d d m contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMContent> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the d d m contents where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the d d m content where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the d d m content that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMContent removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchContentException;

	/**
	* Removes all the d d m contents where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the d d m contents where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the d d m contents from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of d d m contents where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching d d m contents
	* @throws SystemException if a system exception occurred
	*/
	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of d d m contents where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching d d m contents
	* @throws SystemException if a system exception occurred
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of d d m contents where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching d d m contents
	* @throws SystemException if a system exception occurred
	*/
	public int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of d d m contents where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching d d m contents
	* @throws SystemException if a system exception occurred
	*/
	public int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of d d m contents.
	*
	* @return the number of d d m contents
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}