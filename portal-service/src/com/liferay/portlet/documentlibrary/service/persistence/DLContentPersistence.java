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

import com.liferay.portlet.documentlibrary.model.DLContent;

/**
 * The persistence interface for the document library content service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLContentPersistenceImpl
 * @see DLContentUtil
 * @generated
 */
public interface DLContentPersistence extends BasePersistence<DLContent> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DLContentUtil} to access the document library content persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the document library content in the entity cache if it is enabled.
	*
	* @param dlContent the document library content
	*/
	public void cacheResult(
		com.liferay.portlet.documentlibrary.model.DLContent dlContent);

	/**
	* Caches the document library contents in the entity cache if it is enabled.
	*
	* @param dlContents the document library contents
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.documentlibrary.model.DLContent> dlContents);

	/**
	* Creates a new document library content with the primary key. Does not add the document library content to the database.
	*
	* @param contentId the primary key for the new document library content
	* @return the new document library content
	*/
	public com.liferay.portlet.documentlibrary.model.DLContent create(
		long contentId);

	/**
	* Removes the document library content with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param contentId the primary key of the document library content
	* @return the document library content that was removed
	* @throws com.liferay.portlet.documentlibrary.NoSuchContentException if a document library content with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLContent remove(
		long contentId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchContentException;

	public com.liferay.portlet.documentlibrary.model.DLContent updateImpl(
		com.liferay.portlet.documentlibrary.model.DLContent dlContent,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the document library content with the primary key or throws a {@link com.liferay.portlet.documentlibrary.NoSuchContentException} if it could not be found.
	*
	* @param contentId the primary key of the document library content
	* @return the document library content
	* @throws com.liferay.portlet.documentlibrary.NoSuchContentException if a document library content with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLContent findByPrimaryKey(
		long contentId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchContentException;

	/**
	* Returns the document library content with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param contentId the primary key of the document library content
	* @return the document library content, or <code>null</code> if a document library content with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLContent fetchByPrimaryKey(
		long contentId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the document library contents where companyId = &#63; and repositoryId = &#63;.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @return the matching document library contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLContent> findByC_R(
		long companyId, long repositoryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the document library contents where companyId = &#63; and repositoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param start the lower bound of the range of document library contents
	* @param end the upper bound of the range of document library contents (not inclusive)
	* @return the range of matching document library contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLContent> findByC_R(
		long companyId, long repositoryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the document library contents where companyId = &#63; and repositoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param start the lower bound of the range of document library contents
	* @param end the upper bound of the range of document library contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLContent> findByC_R(
		long companyId, long repositoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first document library content in the ordered set where companyId = &#63; and repositoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library content
	* @throws com.liferay.portlet.documentlibrary.NoSuchContentException if a matching document library content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLContent findByC_R_First(
		long companyId, long repositoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchContentException;

	/**
	* Returns the last document library content in the ordered set where companyId = &#63; and repositoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library content
	* @throws com.liferay.portlet.documentlibrary.NoSuchContentException if a matching document library content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLContent findByC_R_Last(
		long companyId, long repositoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchContentException;

	/**
	* Returns the document library contents before and after the current document library content in the ordered set where companyId = &#63; and repositoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param contentId the primary key of the current document library content
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library content
	* @throws com.liferay.portlet.documentlibrary.NoSuchContentException if a document library content with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLContent[] findByC_R_PrevAndNext(
		long contentId, long companyId, long repositoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchContentException;

	/**
	* Returns all the document library contents where companyId = &#63; and repositoryId = &#63; and path = &#63;.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @return the matching document library contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLContent> findByC_R_P(
		long companyId, long repositoryId, java.lang.String path)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the document library contents where companyId = &#63; and repositoryId = &#63; and path = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @param start the lower bound of the range of document library contents
	* @param end the upper bound of the range of document library contents (not inclusive)
	* @return the range of matching document library contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLContent> findByC_R_P(
		long companyId, long repositoryId, java.lang.String path, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the document library contents where companyId = &#63; and repositoryId = &#63; and path = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @param start the lower bound of the range of document library contents
	* @param end the upper bound of the range of document library contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLContent> findByC_R_P(
		long companyId, long repositoryId, java.lang.String path, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first document library content in the ordered set where companyId = &#63; and repositoryId = &#63; and path = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library content
	* @throws com.liferay.portlet.documentlibrary.NoSuchContentException if a matching document library content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLContent findByC_R_P_First(
		long companyId, long repositoryId, java.lang.String path,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchContentException;

	/**
	* Returns the last document library content in the ordered set where companyId = &#63; and repositoryId = &#63; and path = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library content
	* @throws com.liferay.portlet.documentlibrary.NoSuchContentException if a matching document library content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLContent findByC_R_P_Last(
		long companyId, long repositoryId, java.lang.String path,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchContentException;

	/**
	* Returns the document library contents before and after the current document library content in the ordered set where companyId = &#63; and repositoryId = &#63; and path = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param contentId the primary key of the current document library content
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library content
	* @throws com.liferay.portlet.documentlibrary.NoSuchContentException if a document library content with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLContent[] findByC_R_P_PrevAndNext(
		long contentId, long companyId, long repositoryId,
		java.lang.String path,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchContentException;

	/**
	* Returns all the document library contents where companyId = &#63; and repositoryId = &#63; and path LIKE &#63;.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @return the matching document library contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLContent> findByC_R_LikeP(
		long companyId, long repositoryId, java.lang.String path)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the document library contents where companyId = &#63; and repositoryId = &#63; and path LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @param start the lower bound of the range of document library contents
	* @param end the upper bound of the range of document library contents (not inclusive)
	* @return the range of matching document library contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLContent> findByC_R_LikeP(
		long companyId, long repositoryId, java.lang.String path, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the document library contents where companyId = &#63; and repositoryId = &#63; and path LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @param start the lower bound of the range of document library contents
	* @param end the upper bound of the range of document library contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLContent> findByC_R_LikeP(
		long companyId, long repositoryId, java.lang.String path, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first document library content in the ordered set where companyId = &#63; and repositoryId = &#63; and path LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library content
	* @throws com.liferay.portlet.documentlibrary.NoSuchContentException if a matching document library content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLContent findByC_R_LikeP_First(
		long companyId, long repositoryId, java.lang.String path,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchContentException;

	/**
	* Returns the last document library content in the ordered set where companyId = &#63; and repositoryId = &#63; and path LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library content
	* @throws com.liferay.portlet.documentlibrary.NoSuchContentException if a matching document library content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLContent findByC_R_LikeP_Last(
		long companyId, long repositoryId, java.lang.String path,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchContentException;

	/**
	* Returns the document library contents before and after the current document library content in the ordered set where companyId = &#63; and repositoryId = &#63; and path LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param contentId the primary key of the current document library content
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library content
	* @throws com.liferay.portlet.documentlibrary.NoSuchContentException if a document library content with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLContent[] findByC_R_LikeP_PrevAndNext(
		long contentId, long companyId, long repositoryId,
		java.lang.String path,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchContentException;

	/**
	* Returns the document library content where companyId = &#63; and repositoryId = &#63; and path = &#63; and version = &#63; or throws a {@link com.liferay.portlet.documentlibrary.NoSuchContentException} if it could not be found.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @param version the version
	* @return the matching document library content
	* @throws com.liferay.portlet.documentlibrary.NoSuchContentException if a matching document library content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLContent findByC_R_P_V(
		long companyId, long repositoryId, java.lang.String path,
		java.lang.String version)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchContentException;

	/**
	* Returns the document library content where companyId = &#63; and repositoryId = &#63; and path = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @param version the version
	* @return the matching document library content, or <code>null</code> if a matching document library content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLContent fetchByC_R_P_V(
		long companyId, long repositoryId, java.lang.String path,
		java.lang.String version)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the document library content where companyId = &#63; and repositoryId = &#63; and path = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @param version the version
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching document library content, or <code>null</code> if a matching document library content could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLContent fetchByC_R_P_V(
		long companyId, long repositoryId, java.lang.String path,
		java.lang.String version, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the document library contents.
	*
	* @return the document library contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLContent> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the document library contents.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of document library contents
	* @param end the upper bound of the range of document library contents (not inclusive)
	* @return the range of document library contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLContent> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the document library contents.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of document library contents
	* @param end the upper bound of the range of document library contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of document library contents
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLContent> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the document library contents where companyId = &#63; and repositoryId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_R(long companyId, long repositoryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the document library contents where companyId = &#63; and repositoryId = &#63; and path = &#63; from the database.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_R_P(long companyId, long repositoryId,
		java.lang.String path)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the document library contents where companyId = &#63; and repositoryId = &#63; and path LIKE &#63; from the database.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_R_LikeP(long companyId, long repositoryId,
		java.lang.String path)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the document library content where companyId = &#63; and repositoryId = &#63; and path = &#63; and version = &#63; from the database.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @param version the version
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_R_P_V(long companyId, long repositoryId,
		java.lang.String path, java.lang.String version)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchContentException;

	/**
	* Removes all the document library contents from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library contents where companyId = &#63; and repositoryId = &#63;.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @return the number of matching document library contents
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_R(long companyId, long repositoryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library contents where companyId = &#63; and repositoryId = &#63; and path = &#63;.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @return the number of matching document library contents
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_R_P(long companyId, long repositoryId,
		java.lang.String path)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library contents where companyId = &#63; and repositoryId = &#63; and path LIKE &#63;.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @return the number of matching document library contents
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_R_LikeP(long companyId, long repositoryId,
		java.lang.String path)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library contents where companyId = &#63; and repositoryId = &#63; and path = &#63; and version = &#63;.
	*
	* @param companyId the company ID
	* @param repositoryId the repository ID
	* @param path the path
	* @param version the version
	* @return the number of matching document library contents
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_R_P_V(long companyId, long repositoryId,
		java.lang.String path, java.lang.String version)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of document library contents.
	*
	* @return the number of document library contents
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public DLContent remove(DLContent dlContent) throws SystemException;
}