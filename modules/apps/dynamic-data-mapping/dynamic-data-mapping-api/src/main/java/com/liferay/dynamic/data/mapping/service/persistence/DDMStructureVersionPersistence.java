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

package com.liferay.dynamic.data.mapping.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.mapping.exception.NoSuchStructureVersionException;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the d d m structure version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.dynamic.data.mapping.service.persistence.impl.DDMStructureVersionPersistenceImpl
 * @see DDMStructureVersionUtil
 * @generated
 */
@ProviderType
public interface DDMStructureVersionPersistence extends BasePersistence<DDMStructureVersion> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDMStructureVersionUtil} to access the d d m structure version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the d d m structure versions where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @return the matching d d m structure versions
	*/
	public java.util.List<DDMStructureVersion> findByStructureId(
		long structureId);

	/**
	* Returns a range of all the d d m structure versions where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param structureId the structure ID
	* @param start the lower bound of the range of d d m structure versions
	* @param end the upper bound of the range of d d m structure versions (not inclusive)
	* @return the range of matching d d m structure versions
	*/
	public java.util.List<DDMStructureVersion> findByStructureId(
		long structureId, int start, int end);

	/**
	* Returns an ordered range of all the d d m structure versions where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param structureId the structure ID
	* @param start the lower bound of the range of d d m structure versions
	* @param end the upper bound of the range of d d m structure versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structure versions
	*/
	public java.util.List<DDMStructureVersion> findByStructureId(
		long structureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureVersion> orderByComparator);

	/**
	* Returns an ordered range of all the d d m structure versions where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param structureId the structure ID
	* @param start the lower bound of the range of d d m structure versions
	* @param end the upper bound of the range of d d m structure versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m structure versions
	*/
	public java.util.List<DDMStructureVersion> findByStructureId(
		long structureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d m structure version in the ordered set where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure version
	* @throws NoSuchStructureVersionException if a matching d d m structure version could not be found
	*/
	public DDMStructureVersion findByStructureId_First(long structureId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureVersion> orderByComparator)
		throws NoSuchStructureVersionException;

	/**
	* Returns the first d d m structure version in the ordered set where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure version, or <code>null</code> if a matching d d m structure version could not be found
	*/
	public DDMStructureVersion fetchByStructureId_First(long structureId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureVersion> orderByComparator);

	/**
	* Returns the last d d m structure version in the ordered set where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure version
	* @throws NoSuchStructureVersionException if a matching d d m structure version could not be found
	*/
	public DDMStructureVersion findByStructureId_Last(long structureId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureVersion> orderByComparator)
		throws NoSuchStructureVersionException;

	/**
	* Returns the last d d m structure version in the ordered set where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure version, or <code>null</code> if a matching d d m structure version could not be found
	*/
	public DDMStructureVersion fetchByStructureId_Last(long structureId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureVersion> orderByComparator);

	/**
	* Returns the d d m structure versions before and after the current d d m structure version in the ordered set where structureId = &#63;.
	*
	* @param structureVersionId the primary key of the current d d m structure version
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure version
	* @throws NoSuchStructureVersionException if a d d m structure version with the primary key could not be found
	*/
	public DDMStructureVersion[] findByStructureId_PrevAndNext(
		long structureVersionId, long structureId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureVersion> orderByComparator)
		throws NoSuchStructureVersionException;

	/**
	* Removes all the d d m structure versions where structureId = &#63; from the database.
	*
	* @param structureId the structure ID
	*/
	public void removeByStructureId(long structureId);

	/**
	* Returns the number of d d m structure versions where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @return the number of matching d d m structure versions
	*/
	public int countByStructureId(long structureId);

	/**
	* Returns the d d m structure version where structureId = &#63; and version = &#63; or throws a {@link NoSuchStructureVersionException} if it could not be found.
	*
	* @param structureId the structure ID
	* @param version the version
	* @return the matching d d m structure version
	* @throws NoSuchStructureVersionException if a matching d d m structure version could not be found
	*/
	public DDMStructureVersion findByS_V(long structureId,
		java.lang.String version) throws NoSuchStructureVersionException;

	/**
	* Returns the d d m structure version where structureId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param structureId the structure ID
	* @param version the version
	* @return the matching d d m structure version, or <code>null</code> if a matching d d m structure version could not be found
	*/
	public DDMStructureVersion fetchByS_V(long structureId,
		java.lang.String version);

	/**
	* Returns the d d m structure version where structureId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param structureId the structure ID
	* @param version the version
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching d d m structure version, or <code>null</code> if a matching d d m structure version could not be found
	*/
	public DDMStructureVersion fetchByS_V(long structureId,
		java.lang.String version, boolean retrieveFromCache);

	/**
	* Removes the d d m structure version where structureId = &#63; and version = &#63; from the database.
	*
	* @param structureId the structure ID
	* @param version the version
	* @return the d d m structure version that was removed
	*/
	public DDMStructureVersion removeByS_V(long structureId,
		java.lang.String version) throws NoSuchStructureVersionException;

	/**
	* Returns the number of d d m structure versions where structureId = &#63; and version = &#63;.
	*
	* @param structureId the structure ID
	* @param version the version
	* @return the number of matching d d m structure versions
	*/
	public int countByS_V(long structureId, java.lang.String version);

	/**
	* Returns all the d d m structure versions where structureId = &#63; and status = &#63;.
	*
	* @param structureId the structure ID
	* @param status the status
	* @return the matching d d m structure versions
	*/
	public java.util.List<DDMStructureVersion> findByS_S(long structureId,
		int status);

	/**
	* Returns a range of all the d d m structure versions where structureId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param structureId the structure ID
	* @param status the status
	* @param start the lower bound of the range of d d m structure versions
	* @param end the upper bound of the range of d d m structure versions (not inclusive)
	* @return the range of matching d d m structure versions
	*/
	public java.util.List<DDMStructureVersion> findByS_S(long structureId,
		int status, int start, int end);

	/**
	* Returns an ordered range of all the d d m structure versions where structureId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param structureId the structure ID
	* @param status the status
	* @param start the lower bound of the range of d d m structure versions
	* @param end the upper bound of the range of d d m structure versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structure versions
	*/
	public java.util.List<DDMStructureVersion> findByS_S(long structureId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureVersion> orderByComparator);

	/**
	* Returns an ordered range of all the d d m structure versions where structureId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param structureId the structure ID
	* @param status the status
	* @param start the lower bound of the range of d d m structure versions
	* @param end the upper bound of the range of d d m structure versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m structure versions
	*/
	public java.util.List<DDMStructureVersion> findByS_S(long structureId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d m structure version in the ordered set where structureId = &#63; and status = &#63;.
	*
	* @param structureId the structure ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure version
	* @throws NoSuchStructureVersionException if a matching d d m structure version could not be found
	*/
	public DDMStructureVersion findByS_S_First(long structureId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureVersion> orderByComparator)
		throws NoSuchStructureVersionException;

	/**
	* Returns the first d d m structure version in the ordered set where structureId = &#63; and status = &#63;.
	*
	* @param structureId the structure ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure version, or <code>null</code> if a matching d d m structure version could not be found
	*/
	public DDMStructureVersion fetchByS_S_First(long structureId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureVersion> orderByComparator);

	/**
	* Returns the last d d m structure version in the ordered set where structureId = &#63; and status = &#63;.
	*
	* @param structureId the structure ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure version
	* @throws NoSuchStructureVersionException if a matching d d m structure version could not be found
	*/
	public DDMStructureVersion findByS_S_Last(long structureId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureVersion> orderByComparator)
		throws NoSuchStructureVersionException;

	/**
	* Returns the last d d m structure version in the ordered set where structureId = &#63; and status = &#63;.
	*
	* @param structureId the structure ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure version, or <code>null</code> if a matching d d m structure version could not be found
	*/
	public DDMStructureVersion fetchByS_S_Last(long structureId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureVersion> orderByComparator);

	/**
	* Returns the d d m structure versions before and after the current d d m structure version in the ordered set where structureId = &#63; and status = &#63;.
	*
	* @param structureVersionId the primary key of the current d d m structure version
	* @param structureId the structure ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure version
	* @throws NoSuchStructureVersionException if a d d m structure version with the primary key could not be found
	*/
	public DDMStructureVersion[] findByS_S_PrevAndNext(
		long structureVersionId, long structureId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureVersion> orderByComparator)
		throws NoSuchStructureVersionException;

	/**
	* Removes all the d d m structure versions where structureId = &#63; and status = &#63; from the database.
	*
	* @param structureId the structure ID
	* @param status the status
	*/
	public void removeByS_S(long structureId, int status);

	/**
	* Returns the number of d d m structure versions where structureId = &#63; and status = &#63;.
	*
	* @param structureId the structure ID
	* @param status the status
	* @return the number of matching d d m structure versions
	*/
	public int countByS_S(long structureId, int status);

	/**
	* Caches the d d m structure version in the entity cache if it is enabled.
	*
	* @param ddmStructureVersion the d d m structure version
	*/
	public void cacheResult(DDMStructureVersion ddmStructureVersion);

	/**
	* Caches the d d m structure versions in the entity cache if it is enabled.
	*
	* @param ddmStructureVersions the d d m structure versions
	*/
	public void cacheResult(
		java.util.List<DDMStructureVersion> ddmStructureVersions);

	/**
	* Creates a new d d m structure version with the primary key. Does not add the d d m structure version to the database.
	*
	* @param structureVersionId the primary key for the new d d m structure version
	* @return the new d d m structure version
	*/
	public DDMStructureVersion create(long structureVersionId);

	/**
	* Removes the d d m structure version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param structureVersionId the primary key of the d d m structure version
	* @return the d d m structure version that was removed
	* @throws NoSuchStructureVersionException if a d d m structure version with the primary key could not be found
	*/
	public DDMStructureVersion remove(long structureVersionId)
		throws NoSuchStructureVersionException;

	public DDMStructureVersion updateImpl(
		DDMStructureVersion ddmStructureVersion);

	/**
	* Returns the d d m structure version with the primary key or throws a {@link NoSuchStructureVersionException} if it could not be found.
	*
	* @param structureVersionId the primary key of the d d m structure version
	* @return the d d m structure version
	* @throws NoSuchStructureVersionException if a d d m structure version with the primary key could not be found
	*/
	public DDMStructureVersion findByPrimaryKey(long structureVersionId)
		throws NoSuchStructureVersionException;

	/**
	* Returns the d d m structure version with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param structureVersionId the primary key of the d d m structure version
	* @return the d d m structure version, or <code>null</code> if a d d m structure version with the primary key could not be found
	*/
	public DDMStructureVersion fetchByPrimaryKey(long structureVersionId);

	@Override
	public java.util.Map<java.io.Serializable, DDMStructureVersion> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the d d m structure versions.
	*
	* @return the d d m structure versions
	*/
	public java.util.List<DDMStructureVersion> findAll();

	/**
	* Returns a range of all the d d m structure versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m structure versions
	* @param end the upper bound of the range of d d m structure versions (not inclusive)
	* @return the range of d d m structure versions
	*/
	public java.util.List<DDMStructureVersion> findAll(int start, int end);

	/**
	* Returns an ordered range of all the d d m structure versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m structure versions
	* @param end the upper bound of the range of d d m structure versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d d m structure versions
	*/
	public java.util.List<DDMStructureVersion> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureVersion> orderByComparator);

	/**
	* Returns an ordered range of all the d d m structure versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m structure versions
	* @param end the upper bound of the range of d d m structure versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of d d m structure versions
	*/
	public java.util.List<DDMStructureVersion> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the d d m structure versions from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of d d m structure versions.
	*
	* @return the number of d d m structure versions
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}