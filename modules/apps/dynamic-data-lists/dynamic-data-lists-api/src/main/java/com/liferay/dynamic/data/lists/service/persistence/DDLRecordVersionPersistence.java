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

package com.liferay.dynamic.data.lists.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.lists.exception.NoSuchRecordVersionException;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the d d l record version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.dynamic.data.lists.service.persistence.impl.DDLRecordVersionPersistenceImpl
 * @see DDLRecordVersionUtil
 * @generated
 */
@ProviderType
public interface DDLRecordVersionPersistence extends BasePersistence<DDLRecordVersion> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDLRecordVersionUtil} to access the d d l record version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the d d l record versions where recordId = &#63;.
	*
	* @param recordId the record ID
	* @return the matching d d l record versions
	*/
	public java.util.List<DDLRecordVersion> findByRecordId(long recordId);

	/**
	* Returns a range of all the d d l record versions where recordId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param recordId the record ID
	* @param start the lower bound of the range of d d l record versions
	* @param end the upper bound of the range of d d l record versions (not inclusive)
	* @return the range of matching d d l record versions
	*/
	public java.util.List<DDLRecordVersion> findByRecordId(long recordId,
		int start, int end);

	/**
	* Returns an ordered range of all the d d l record versions where recordId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param recordId the record ID
	* @param start the lower bound of the range of d d l record versions
	* @param end the upper bound of the range of d d l record versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d l record versions
	*/
	public java.util.List<DDLRecordVersion> findByRecordId(long recordId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator);

	/**
	* Returns an ordered range of all the d d l record versions where recordId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param recordId the record ID
	* @param start the lower bound of the range of d d l record versions
	* @param end the upper bound of the range of d d l record versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d l record versions
	*/
	public java.util.List<DDLRecordVersion> findByRecordId(long recordId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d l record version in the ordered set where recordId = &#63;.
	*
	* @param recordId the record ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l record version
	* @throws NoSuchRecordVersionException if a matching d d l record version could not be found
	*/
	public DDLRecordVersion findByRecordId_First(long recordId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator)
		throws NoSuchRecordVersionException;

	/**
	* Returns the first d d l record version in the ordered set where recordId = &#63;.
	*
	* @param recordId the record ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l record version, or <code>null</code> if a matching d d l record version could not be found
	*/
	public DDLRecordVersion fetchByRecordId_First(long recordId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator);

	/**
	* Returns the last d d l record version in the ordered set where recordId = &#63;.
	*
	* @param recordId the record ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l record version
	* @throws NoSuchRecordVersionException if a matching d d l record version could not be found
	*/
	public DDLRecordVersion findByRecordId_Last(long recordId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator)
		throws NoSuchRecordVersionException;

	/**
	* Returns the last d d l record version in the ordered set where recordId = &#63;.
	*
	* @param recordId the record ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l record version, or <code>null</code> if a matching d d l record version could not be found
	*/
	public DDLRecordVersion fetchByRecordId_Last(long recordId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator);

	/**
	* Returns the d d l record versions before and after the current d d l record version in the ordered set where recordId = &#63;.
	*
	* @param recordVersionId the primary key of the current d d l record version
	* @param recordId the record ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d l record version
	* @throws NoSuchRecordVersionException if a d d l record version with the primary key could not be found
	*/
	public DDLRecordVersion[] findByRecordId_PrevAndNext(long recordVersionId,
		long recordId,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator)
		throws NoSuchRecordVersionException;

	/**
	* Removes all the d d l record versions where recordId = &#63; from the database.
	*
	* @param recordId the record ID
	*/
	public void removeByRecordId(long recordId);

	/**
	* Returns the number of d d l record versions where recordId = &#63;.
	*
	* @param recordId the record ID
	* @return the number of matching d d l record versions
	*/
	public int countByRecordId(long recordId);

	/**
	* Returns the d d l record version where recordId = &#63; and version = &#63; or throws a {@link NoSuchRecordVersionException} if it could not be found.
	*
	* @param recordId the record ID
	* @param version the version
	* @return the matching d d l record version
	* @throws NoSuchRecordVersionException if a matching d d l record version could not be found
	*/
	public DDLRecordVersion findByR_V(long recordId, java.lang.String version)
		throws NoSuchRecordVersionException;

	/**
	* Returns the d d l record version where recordId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param recordId the record ID
	* @param version the version
	* @return the matching d d l record version, or <code>null</code> if a matching d d l record version could not be found
	*/
	public DDLRecordVersion fetchByR_V(long recordId, java.lang.String version);

	/**
	* Returns the d d l record version where recordId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param recordId the record ID
	* @param version the version
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching d d l record version, or <code>null</code> if a matching d d l record version could not be found
	*/
	public DDLRecordVersion fetchByR_V(long recordId, java.lang.String version,
		boolean retrieveFromCache);

	/**
	* Removes the d d l record version where recordId = &#63; and version = &#63; from the database.
	*
	* @param recordId the record ID
	* @param version the version
	* @return the d d l record version that was removed
	*/
	public DDLRecordVersion removeByR_V(long recordId, java.lang.String version)
		throws NoSuchRecordVersionException;

	/**
	* Returns the number of d d l record versions where recordId = &#63; and version = &#63;.
	*
	* @param recordId the record ID
	* @param version the version
	* @return the number of matching d d l record versions
	*/
	public int countByR_V(long recordId, java.lang.String version);

	/**
	* Returns all the d d l record versions where recordId = &#63; and status = &#63;.
	*
	* @param recordId the record ID
	* @param status the status
	* @return the matching d d l record versions
	*/
	public java.util.List<DDLRecordVersion> findByR_S(long recordId, int status);

	/**
	* Returns a range of all the d d l record versions where recordId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param recordId the record ID
	* @param status the status
	* @param start the lower bound of the range of d d l record versions
	* @param end the upper bound of the range of d d l record versions (not inclusive)
	* @return the range of matching d d l record versions
	*/
	public java.util.List<DDLRecordVersion> findByR_S(long recordId,
		int status, int start, int end);

	/**
	* Returns an ordered range of all the d d l record versions where recordId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param recordId the record ID
	* @param status the status
	* @param start the lower bound of the range of d d l record versions
	* @param end the upper bound of the range of d d l record versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d l record versions
	*/
	public java.util.List<DDLRecordVersion> findByR_S(long recordId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator);

	/**
	* Returns an ordered range of all the d d l record versions where recordId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param recordId the record ID
	* @param status the status
	* @param start the lower bound of the range of d d l record versions
	* @param end the upper bound of the range of d d l record versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d l record versions
	*/
	public java.util.List<DDLRecordVersion> findByR_S(long recordId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d l record version in the ordered set where recordId = &#63; and status = &#63;.
	*
	* @param recordId the record ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l record version
	* @throws NoSuchRecordVersionException if a matching d d l record version could not be found
	*/
	public DDLRecordVersion findByR_S_First(long recordId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator)
		throws NoSuchRecordVersionException;

	/**
	* Returns the first d d l record version in the ordered set where recordId = &#63; and status = &#63;.
	*
	* @param recordId the record ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l record version, or <code>null</code> if a matching d d l record version could not be found
	*/
	public DDLRecordVersion fetchByR_S_First(long recordId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator);

	/**
	* Returns the last d d l record version in the ordered set where recordId = &#63; and status = &#63;.
	*
	* @param recordId the record ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l record version
	* @throws NoSuchRecordVersionException if a matching d d l record version could not be found
	*/
	public DDLRecordVersion findByR_S_Last(long recordId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator)
		throws NoSuchRecordVersionException;

	/**
	* Returns the last d d l record version in the ordered set where recordId = &#63; and status = &#63;.
	*
	* @param recordId the record ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l record version, or <code>null</code> if a matching d d l record version could not be found
	*/
	public DDLRecordVersion fetchByR_S_Last(long recordId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator);

	/**
	* Returns the d d l record versions before and after the current d d l record version in the ordered set where recordId = &#63; and status = &#63;.
	*
	* @param recordVersionId the primary key of the current d d l record version
	* @param recordId the record ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d l record version
	* @throws NoSuchRecordVersionException if a d d l record version with the primary key could not be found
	*/
	public DDLRecordVersion[] findByR_S_PrevAndNext(long recordVersionId,
		long recordId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator)
		throws NoSuchRecordVersionException;

	/**
	* Removes all the d d l record versions where recordId = &#63; and status = &#63; from the database.
	*
	* @param recordId the record ID
	* @param status the status
	*/
	public void removeByR_S(long recordId, int status);

	/**
	* Returns the number of d d l record versions where recordId = &#63; and status = &#63;.
	*
	* @param recordId the record ID
	* @param status the status
	* @return the number of matching d d l record versions
	*/
	public int countByR_S(long recordId, int status);

	/**
	* Caches the d d l record version in the entity cache if it is enabled.
	*
	* @param ddlRecordVersion the d d l record version
	*/
	public void cacheResult(DDLRecordVersion ddlRecordVersion);

	/**
	* Caches the d d l record versions in the entity cache if it is enabled.
	*
	* @param ddlRecordVersions the d d l record versions
	*/
	public void cacheResult(java.util.List<DDLRecordVersion> ddlRecordVersions);

	/**
	* Creates a new d d l record version with the primary key. Does not add the d d l record version to the database.
	*
	* @param recordVersionId the primary key for the new d d l record version
	* @return the new d d l record version
	*/
	public DDLRecordVersion create(long recordVersionId);

	/**
	* Removes the d d l record version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param recordVersionId the primary key of the d d l record version
	* @return the d d l record version that was removed
	* @throws NoSuchRecordVersionException if a d d l record version with the primary key could not be found
	*/
	public DDLRecordVersion remove(long recordVersionId)
		throws NoSuchRecordVersionException;

	public DDLRecordVersion updateImpl(DDLRecordVersion ddlRecordVersion);

	/**
	* Returns the d d l record version with the primary key or throws a {@link NoSuchRecordVersionException} if it could not be found.
	*
	* @param recordVersionId the primary key of the d d l record version
	* @return the d d l record version
	* @throws NoSuchRecordVersionException if a d d l record version with the primary key could not be found
	*/
	public DDLRecordVersion findByPrimaryKey(long recordVersionId)
		throws NoSuchRecordVersionException;

	/**
	* Returns the d d l record version with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param recordVersionId the primary key of the d d l record version
	* @return the d d l record version, or <code>null</code> if a d d l record version with the primary key could not be found
	*/
	public DDLRecordVersion fetchByPrimaryKey(long recordVersionId);

	@Override
	public java.util.Map<java.io.Serializable, DDLRecordVersion> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the d d l record versions.
	*
	* @return the d d l record versions
	*/
	public java.util.List<DDLRecordVersion> findAll();

	/**
	* Returns a range of all the d d l record versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d l record versions
	* @param end the upper bound of the range of d d l record versions (not inclusive)
	* @return the range of d d l record versions
	*/
	public java.util.List<DDLRecordVersion> findAll(int start, int end);

	/**
	* Returns an ordered range of all the d d l record versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d l record versions
	* @param end the upper bound of the range of d d l record versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d d l record versions
	*/
	public java.util.List<DDLRecordVersion> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator);

	/**
	* Returns an ordered range of all the d d l record versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d l record versions
	* @param end the upper bound of the range of d d l record versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of d d l record versions
	*/
	public java.util.List<DDLRecordVersion> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDLRecordVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the d d l record versions from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of d d l record versions.
	*
	* @return the number of d d l record versions
	*/
	public int countAll();
}