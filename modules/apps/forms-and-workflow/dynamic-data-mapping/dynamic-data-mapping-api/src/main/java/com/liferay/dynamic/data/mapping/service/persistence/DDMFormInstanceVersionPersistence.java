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

import com.liferay.dynamic.data.mapping.exception.NoSuchFormInstanceVersionException;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the ddm form instance version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.dynamic.data.mapping.service.persistence.impl.DDMFormInstanceVersionPersistenceImpl
 * @see DDMFormInstanceVersionUtil
 * @generated
 */
@ProviderType
public interface DDMFormInstanceVersionPersistence extends BasePersistence<DDMFormInstanceVersion> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDMFormInstanceVersionUtil} to access the ddm form instance version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the ddm form instance versions where formInstanceId = &#63;.
	*
	* @param formInstanceId the form instance ID
	* @return the matching ddm form instance versions
	*/
	public java.util.List<DDMFormInstanceVersion> findByFormInstanceId(
		long formInstanceId);

	/**
	* Returns a range of all the ddm form instance versions where formInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMFormInstanceVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param formInstanceId the form instance ID
	* @param start the lower bound of the range of ddm form instance versions
	* @param end the upper bound of the range of ddm form instance versions (not inclusive)
	* @return the range of matching ddm form instance versions
	*/
	public java.util.List<DDMFormInstanceVersion> findByFormInstanceId(
		long formInstanceId, int start, int end);

	/**
	* Returns an ordered range of all the ddm form instance versions where formInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMFormInstanceVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param formInstanceId the form instance ID
	* @param start the lower bound of the range of ddm form instance versions
	* @param end the upper bound of the range of ddm form instance versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching ddm form instance versions
	*/
	public java.util.List<DDMFormInstanceVersion> findByFormInstanceId(
		long formInstanceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstanceVersion> orderByComparator);

	/**
	* Returns an ordered range of all the ddm form instance versions where formInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMFormInstanceVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param formInstanceId the form instance ID
	* @param start the lower bound of the range of ddm form instance versions
	* @param end the upper bound of the range of ddm form instance versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching ddm form instance versions
	*/
	public java.util.List<DDMFormInstanceVersion> findByFormInstanceId(
		long formInstanceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstanceVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first ddm form instance version in the ordered set where formInstanceId = &#63;.
	*
	* @param formInstanceId the form instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ddm form instance version
	* @throws NoSuchFormInstanceVersionException if a matching ddm form instance version could not be found
	*/
	public DDMFormInstanceVersion findByFormInstanceId_First(
		long formInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstanceVersion> orderByComparator)
		throws NoSuchFormInstanceVersionException;

	/**
	* Returns the first ddm form instance version in the ordered set where formInstanceId = &#63;.
	*
	* @param formInstanceId the form instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ddm form instance version, or <code>null</code> if a matching ddm form instance version could not be found
	*/
	public DDMFormInstanceVersion fetchByFormInstanceId_First(
		long formInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstanceVersion> orderByComparator);

	/**
	* Returns the last ddm form instance version in the ordered set where formInstanceId = &#63;.
	*
	* @param formInstanceId the form instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ddm form instance version
	* @throws NoSuchFormInstanceVersionException if a matching ddm form instance version could not be found
	*/
	public DDMFormInstanceVersion findByFormInstanceId_Last(
		long formInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstanceVersion> orderByComparator)
		throws NoSuchFormInstanceVersionException;

	/**
	* Returns the last ddm form instance version in the ordered set where formInstanceId = &#63;.
	*
	* @param formInstanceId the form instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ddm form instance version, or <code>null</code> if a matching ddm form instance version could not be found
	*/
	public DDMFormInstanceVersion fetchByFormInstanceId_Last(
		long formInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstanceVersion> orderByComparator);

	/**
	* Returns the ddm form instance versions before and after the current ddm form instance version in the ordered set where formInstanceId = &#63;.
	*
	* @param formInstanceVersionId the primary key of the current ddm form instance version
	* @param formInstanceId the form instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next ddm form instance version
	* @throws NoSuchFormInstanceVersionException if a ddm form instance version with the primary key could not be found
	*/
	public DDMFormInstanceVersion[] findByFormInstanceId_PrevAndNext(
		long formInstanceVersionId, long formInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstanceVersion> orderByComparator)
		throws NoSuchFormInstanceVersionException;

	/**
	* Removes all the ddm form instance versions where formInstanceId = &#63; from the database.
	*
	* @param formInstanceId the form instance ID
	*/
	public void removeByFormInstanceId(long formInstanceId);

	/**
	* Returns the number of ddm form instance versions where formInstanceId = &#63;.
	*
	* @param formInstanceId the form instance ID
	* @return the number of matching ddm form instance versions
	*/
	public int countByFormInstanceId(long formInstanceId);

	/**
	* Returns the ddm form instance version where formInstanceId = &#63; and version = &#63; or throws a {@link NoSuchFormInstanceVersionException} if it could not be found.
	*
	* @param formInstanceId the form instance ID
	* @param version the version
	* @return the matching ddm form instance version
	* @throws NoSuchFormInstanceVersionException if a matching ddm form instance version could not be found
	*/
	public DDMFormInstanceVersion findByF_V(long formInstanceId,
		java.lang.String version) throws NoSuchFormInstanceVersionException;

	/**
	* Returns the ddm form instance version where formInstanceId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param formInstanceId the form instance ID
	* @param version the version
	* @return the matching ddm form instance version, or <code>null</code> if a matching ddm form instance version could not be found
	*/
	public DDMFormInstanceVersion fetchByF_V(long formInstanceId,
		java.lang.String version);

	/**
	* Returns the ddm form instance version where formInstanceId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param formInstanceId the form instance ID
	* @param version the version
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching ddm form instance version, or <code>null</code> if a matching ddm form instance version could not be found
	*/
	public DDMFormInstanceVersion fetchByF_V(long formInstanceId,
		java.lang.String version, boolean retrieveFromCache);

	/**
	* Removes the ddm form instance version where formInstanceId = &#63; and version = &#63; from the database.
	*
	* @param formInstanceId the form instance ID
	* @param version the version
	* @return the ddm form instance version that was removed
	*/
	public DDMFormInstanceVersion removeByF_V(long formInstanceId,
		java.lang.String version) throws NoSuchFormInstanceVersionException;

	/**
	* Returns the number of ddm form instance versions where formInstanceId = &#63; and version = &#63;.
	*
	* @param formInstanceId the form instance ID
	* @param version the version
	* @return the number of matching ddm form instance versions
	*/
	public int countByF_V(long formInstanceId, java.lang.String version);

	/**
	* Returns all the ddm form instance versions where formInstanceId = &#63; and status = &#63;.
	*
	* @param formInstanceId the form instance ID
	* @param status the status
	* @return the matching ddm form instance versions
	*/
	public java.util.List<DDMFormInstanceVersion> findByF_S(
		long formInstanceId, int status);

	/**
	* Returns a range of all the ddm form instance versions where formInstanceId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMFormInstanceVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param formInstanceId the form instance ID
	* @param status the status
	* @param start the lower bound of the range of ddm form instance versions
	* @param end the upper bound of the range of ddm form instance versions (not inclusive)
	* @return the range of matching ddm form instance versions
	*/
	public java.util.List<DDMFormInstanceVersion> findByF_S(
		long formInstanceId, int status, int start, int end);

	/**
	* Returns an ordered range of all the ddm form instance versions where formInstanceId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMFormInstanceVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param formInstanceId the form instance ID
	* @param status the status
	* @param start the lower bound of the range of ddm form instance versions
	* @param end the upper bound of the range of ddm form instance versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching ddm form instance versions
	*/
	public java.util.List<DDMFormInstanceVersion> findByF_S(
		long formInstanceId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstanceVersion> orderByComparator);

	/**
	* Returns an ordered range of all the ddm form instance versions where formInstanceId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMFormInstanceVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param formInstanceId the form instance ID
	* @param status the status
	* @param start the lower bound of the range of ddm form instance versions
	* @param end the upper bound of the range of ddm form instance versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching ddm form instance versions
	*/
	public java.util.List<DDMFormInstanceVersion> findByF_S(
		long formInstanceId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstanceVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first ddm form instance version in the ordered set where formInstanceId = &#63; and status = &#63;.
	*
	* @param formInstanceId the form instance ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ddm form instance version
	* @throws NoSuchFormInstanceVersionException if a matching ddm form instance version could not be found
	*/
	public DDMFormInstanceVersion findByF_S_First(long formInstanceId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstanceVersion> orderByComparator)
		throws NoSuchFormInstanceVersionException;

	/**
	* Returns the first ddm form instance version in the ordered set where formInstanceId = &#63; and status = &#63;.
	*
	* @param formInstanceId the form instance ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ddm form instance version, or <code>null</code> if a matching ddm form instance version could not be found
	*/
	public DDMFormInstanceVersion fetchByF_S_First(long formInstanceId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstanceVersion> orderByComparator);

	/**
	* Returns the last ddm form instance version in the ordered set where formInstanceId = &#63; and status = &#63;.
	*
	* @param formInstanceId the form instance ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ddm form instance version
	* @throws NoSuchFormInstanceVersionException if a matching ddm form instance version could not be found
	*/
	public DDMFormInstanceVersion findByF_S_Last(long formInstanceId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstanceVersion> orderByComparator)
		throws NoSuchFormInstanceVersionException;

	/**
	* Returns the last ddm form instance version in the ordered set where formInstanceId = &#63; and status = &#63;.
	*
	* @param formInstanceId the form instance ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ddm form instance version, or <code>null</code> if a matching ddm form instance version could not be found
	*/
	public DDMFormInstanceVersion fetchByF_S_Last(long formInstanceId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstanceVersion> orderByComparator);

	/**
	* Returns the ddm form instance versions before and after the current ddm form instance version in the ordered set where formInstanceId = &#63; and status = &#63;.
	*
	* @param formInstanceVersionId the primary key of the current ddm form instance version
	* @param formInstanceId the form instance ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next ddm form instance version
	* @throws NoSuchFormInstanceVersionException if a ddm form instance version with the primary key could not be found
	*/
	public DDMFormInstanceVersion[] findByF_S_PrevAndNext(
		long formInstanceVersionId, long formInstanceId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstanceVersion> orderByComparator)
		throws NoSuchFormInstanceVersionException;

	/**
	* Removes all the ddm form instance versions where formInstanceId = &#63; and status = &#63; from the database.
	*
	* @param formInstanceId the form instance ID
	* @param status the status
	*/
	public void removeByF_S(long formInstanceId, int status);

	/**
	* Returns the number of ddm form instance versions where formInstanceId = &#63; and status = &#63;.
	*
	* @param formInstanceId the form instance ID
	* @param status the status
	* @return the number of matching ddm form instance versions
	*/
	public int countByF_S(long formInstanceId, int status);

	/**
	* Caches the ddm form instance version in the entity cache if it is enabled.
	*
	* @param ddmFormInstanceVersion the ddm form instance version
	*/
	public void cacheResult(DDMFormInstanceVersion ddmFormInstanceVersion);

	/**
	* Caches the ddm form instance versions in the entity cache if it is enabled.
	*
	* @param ddmFormInstanceVersions the ddm form instance versions
	*/
	public void cacheResult(
		java.util.List<DDMFormInstanceVersion> ddmFormInstanceVersions);

	/**
	* Creates a new ddm form instance version with the primary key. Does not add the ddm form instance version to the database.
	*
	* @param formInstanceVersionId the primary key for the new ddm form instance version
	* @return the new ddm form instance version
	*/
	public DDMFormInstanceVersion create(long formInstanceVersionId);

	/**
	* Removes the ddm form instance version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param formInstanceVersionId the primary key of the ddm form instance version
	* @return the ddm form instance version that was removed
	* @throws NoSuchFormInstanceVersionException if a ddm form instance version with the primary key could not be found
	*/
	public DDMFormInstanceVersion remove(long formInstanceVersionId)
		throws NoSuchFormInstanceVersionException;

	public DDMFormInstanceVersion updateImpl(
		DDMFormInstanceVersion ddmFormInstanceVersion);

	/**
	* Returns the ddm form instance version with the primary key or throws a {@link NoSuchFormInstanceVersionException} if it could not be found.
	*
	* @param formInstanceVersionId the primary key of the ddm form instance version
	* @return the ddm form instance version
	* @throws NoSuchFormInstanceVersionException if a ddm form instance version with the primary key could not be found
	*/
	public DDMFormInstanceVersion findByPrimaryKey(long formInstanceVersionId)
		throws NoSuchFormInstanceVersionException;

	/**
	* Returns the ddm form instance version with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param formInstanceVersionId the primary key of the ddm form instance version
	* @return the ddm form instance version, or <code>null</code> if a ddm form instance version with the primary key could not be found
	*/
	public DDMFormInstanceVersion fetchByPrimaryKey(long formInstanceVersionId);

	@Override
	public java.util.Map<java.io.Serializable, DDMFormInstanceVersion> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the ddm form instance versions.
	*
	* @return the ddm form instance versions
	*/
	public java.util.List<DDMFormInstanceVersion> findAll();

	/**
	* Returns a range of all the ddm form instance versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMFormInstanceVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of ddm form instance versions
	* @param end the upper bound of the range of ddm form instance versions (not inclusive)
	* @return the range of ddm form instance versions
	*/
	public java.util.List<DDMFormInstanceVersion> findAll(int start, int end);

	/**
	* Returns an ordered range of all the ddm form instance versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMFormInstanceVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of ddm form instance versions
	* @param end the upper bound of the range of ddm form instance versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of ddm form instance versions
	*/
	public java.util.List<DDMFormInstanceVersion> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstanceVersion> orderByComparator);

	/**
	* Returns an ordered range of all the ddm form instance versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMFormInstanceVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of ddm form instance versions
	* @param end the upper bound of the range of ddm form instance versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of ddm form instance versions
	*/
	public java.util.List<DDMFormInstanceVersion> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstanceVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the ddm form instance versions from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of ddm form instance versions.
	*
	* @return the number of ddm form instance versions
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}