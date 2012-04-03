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

package com.liferay.portlet.dynamicdatalists.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion;

/**
 * The persistence interface for the d d l record version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordVersionPersistenceImpl
 * @see DDLRecordVersionUtil
 * @generated
 */
public interface DDLRecordVersionPersistence extends BasePersistence<DDLRecordVersion> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDLRecordVersionUtil} to access the d d l record version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the d d l record version in the entity cache if it is enabled.
	*
	* @param ddlRecordVersion the d d l record version
	*/
	public void cacheResult(
		com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion ddlRecordVersion);

	/**
	* Caches the d d l record versions in the entity cache if it is enabled.
	*
	* @param ddlRecordVersions the d d l record versions
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion> ddlRecordVersions);

	/**
	* Creates a new d d l record version with the primary key. Does not add the d d l record version to the database.
	*
	* @param recordVersionId the primary key for the new d d l record version
	* @return the new d d l record version
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion create(
		long recordVersionId);

	/**
	* Removes the d d l record version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param recordVersionId the primary key of the d d l record version
	* @return the d d l record version that was removed
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException if a d d l record version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion remove(
		long recordVersionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException;

	public com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion updateImpl(
		com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion ddlRecordVersion,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the d d l record version with the primary key or throws a {@link com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException} if it could not be found.
	*
	* @param recordVersionId the primary key of the d d l record version
	* @return the d d l record version
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException if a d d l record version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion findByPrimaryKey(
		long recordVersionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException;

	/**
	* Returns the d d l record version with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param recordVersionId the primary key of the d d l record version
	* @return the d d l record version, or <code>null</code> if a d d l record version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion fetchByPrimaryKey(
		long recordVersionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the d d l record versions where recordId = &#63;.
	*
	* @param recordId the record ID
	* @return the matching d d l record versions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion> findByRecordId(
		long recordId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the d d l record versions where recordId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param recordId the record ID
	* @param start the lower bound of the range of d d l record versions
	* @param end the upper bound of the range of d d l record versions (not inclusive)
	* @return the range of matching d d l record versions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion> findByRecordId(
		long recordId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the d d l record versions where recordId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param recordId the record ID
	* @param start the lower bound of the range of d d l record versions
	* @param end the upper bound of the range of d d l record versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d l record versions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion> findByRecordId(
		long recordId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first d d l record version in the ordered set where recordId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param recordId the record ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l record version
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException if a matching d d l record version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion findByRecordId_First(
		long recordId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException;

	/**
	* Returns the last d d l record version in the ordered set where recordId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param recordId the record ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l record version
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException if a matching d d l record version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion findByRecordId_Last(
		long recordId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException;

	/**
	* Returns the d d l record versions before and after the current d d l record version in the ordered set where recordId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param recordVersionId the primary key of the current d d l record version
	* @param recordId the record ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d l record version
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException if a d d l record version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion[] findByRecordId_PrevAndNext(
		long recordVersionId, long recordId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException;

	/**
	* Returns the d d l record version where recordId = &#63; and version = &#63; or throws a {@link com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException} if it could not be found.
	*
	* @param recordId the record ID
	* @param version the version
	* @return the matching d d l record version
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException if a matching d d l record version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion findByR_V(
		long recordId, java.lang.String version)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException;

	/**
	* Returns the d d l record version where recordId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param recordId the record ID
	* @param version the version
	* @return the matching d d l record version, or <code>null</code> if a matching d d l record version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion fetchByR_V(
		long recordId, java.lang.String version)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the d d l record version where recordId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param recordId the record ID
	* @param version the version
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching d d l record version, or <code>null</code> if a matching d d l record version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion fetchByR_V(
		long recordId, java.lang.String version, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the d d l record versions where recordId = &#63; and status = &#63;.
	*
	* @param recordId the record ID
	* @param status the status
	* @return the matching d d l record versions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion> findByR_S(
		long recordId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the d d l record versions where recordId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param recordId the record ID
	* @param status the status
	* @param start the lower bound of the range of d d l record versions
	* @param end the upper bound of the range of d d l record versions (not inclusive)
	* @return the range of matching d d l record versions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion> findByR_S(
		long recordId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the d d l record versions where recordId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param recordId the record ID
	* @param status the status
	* @param start the lower bound of the range of d d l record versions
	* @param end the upper bound of the range of d d l record versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d l record versions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion> findByR_S(
		long recordId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first d d l record version in the ordered set where recordId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param recordId the record ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l record version
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException if a matching d d l record version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion findByR_S_First(
		long recordId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException;

	/**
	* Returns the last d d l record version in the ordered set where recordId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param recordId the record ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l record version
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException if a matching d d l record version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion findByR_S_Last(
		long recordId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException;

	/**
	* Returns the d d l record versions before and after the current d d l record version in the ordered set where recordId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param recordVersionId the primary key of the current d d l record version
	* @param recordId the record ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d l record version
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException if a d d l record version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion[] findByR_S_PrevAndNext(
		long recordVersionId, long recordId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException;

	/**
	* Returns all the d d l record versions.
	*
	* @return the d d l record versions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the d d l record versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d d l record versions
	* @param end the upper bound of the range of d d l record versions (not inclusive)
	* @return the range of d d l record versions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the d d l record versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d d l record versions
	* @param end the upper bound of the range of d d l record versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d d l record versions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the d d l record versions where recordId = &#63; from the database.
	*
	* @param recordId the record ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByRecordId(long recordId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the d d l record version where recordId = &#63; and version = &#63; from the database.
	*
	* @param recordId the record ID
	* @param version the version
	* @return the d d l record version that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion removeByR_V(
		long recordId, java.lang.String version)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException;

	/**
	* Removes all the d d l record versions where recordId = &#63; and status = &#63; from the database.
	*
	* @param recordId the record ID
	* @param status the status
	* @throws SystemException if a system exception occurred
	*/
	public void removeByR_S(long recordId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the d d l record versions from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of d d l record versions where recordId = &#63;.
	*
	* @param recordId the record ID
	* @return the number of matching d d l record versions
	* @throws SystemException if a system exception occurred
	*/
	public int countByRecordId(long recordId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of d d l record versions where recordId = &#63; and version = &#63;.
	*
	* @param recordId the record ID
	* @param version the version
	* @return the number of matching d d l record versions
	* @throws SystemException if a system exception occurred
	*/
	public int countByR_V(long recordId, java.lang.String version)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of d d l record versions where recordId = &#63; and status = &#63;.
	*
	* @param recordId the record ID
	* @param status the status
	* @return the number of matching d d l record versions
	* @throws SystemException if a system exception occurred
	*/
	public int countByR_S(long recordId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of d d l record versions.
	*
	* @return the number of d d l record versions
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}