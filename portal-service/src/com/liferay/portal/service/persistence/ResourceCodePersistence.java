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

import com.liferay.portal.model.ResourceCode;

/**
 * The persistence interface for the resource code service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ResourceCodePersistenceImpl
 * @see ResourceCodeUtil
 * @generated
 */
public interface ResourceCodePersistence extends BasePersistence<ResourceCode> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ResourceCodeUtil} to access the resource code persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the resource code in the entity cache if it is enabled.
	*
	* @param resourceCode the resource code
	*/
	public void cacheResult(com.liferay.portal.model.ResourceCode resourceCode);

	/**
	* Caches the resource codes in the entity cache if it is enabled.
	*
	* @param resourceCodes the resource codes
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.ResourceCode> resourceCodes);

	/**
	* Creates a new resource code with the primary key. Does not add the resource code to the database.
	*
	* @param codeId the primary key for the new resource code
	* @return the new resource code
	*/
	public com.liferay.portal.model.ResourceCode create(long codeId);

	/**
	* Removes the resource code with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param codeId the primary key of the resource code
	* @return the resource code that was removed
	* @throws com.liferay.portal.NoSuchResourceCodeException if a resource code with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceCode remove(long codeId)
		throws com.liferay.portal.NoSuchResourceCodeException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ResourceCode updateImpl(
		com.liferay.portal.model.ResourceCode resourceCode, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the resource code with the primary key or throws a {@link com.liferay.portal.NoSuchResourceCodeException} if it could not be found.
	*
	* @param codeId the primary key of the resource code
	* @return the resource code
	* @throws com.liferay.portal.NoSuchResourceCodeException if a resource code with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceCode findByPrimaryKey(long codeId)
		throws com.liferay.portal.NoSuchResourceCodeException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the resource code with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param codeId the primary key of the resource code
	* @return the resource code, or <code>null</code> if a resource code with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceCode fetchByPrimaryKey(long codeId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the resource codes where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching resource codes
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourceCode> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the resource codes where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of resource codes
	* @param end the upper bound of the range of resource codes (not inclusive)
	* @return the range of matching resource codes
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourceCode> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the resource codes where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of resource codes
	* @param end the upper bound of the range of resource codes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resource codes
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourceCode> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first resource code in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource code
	* @throws com.liferay.portal.NoSuchResourceCodeException if a matching resource code could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceCode findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceCodeException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last resource code in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource code
	* @throws com.liferay.portal.NoSuchResourceCodeException if a matching resource code could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceCode findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceCodeException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the resource codes before and after the current resource code in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param codeId the primary key of the current resource code
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next resource code
	* @throws com.liferay.portal.NoSuchResourceCodeException if a resource code with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceCode[] findByCompanyId_PrevAndNext(
		long codeId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceCodeException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the resource codes where name = &#63;.
	*
	* @param name the name
	* @return the matching resource codes
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourceCode> findByName(
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the resource codes where name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param name the name
	* @param start the lower bound of the range of resource codes
	* @param end the upper bound of the range of resource codes (not inclusive)
	* @return the range of matching resource codes
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourceCode> findByName(
		java.lang.String name, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the resource codes where name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param name the name
	* @param start the lower bound of the range of resource codes
	* @param end the upper bound of the range of resource codes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resource codes
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourceCode> findByName(
		java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first resource code in the ordered set where name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource code
	* @throws com.liferay.portal.NoSuchResourceCodeException if a matching resource code could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceCode findByName_First(
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceCodeException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last resource code in the ordered set where name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource code
	* @throws com.liferay.portal.NoSuchResourceCodeException if a matching resource code could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceCode findByName_Last(
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceCodeException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the resource codes before and after the current resource code in the ordered set where name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param codeId the primary key of the current resource code
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next resource code
	* @throws com.liferay.portal.NoSuchResourceCodeException if a resource code with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceCode[] findByName_PrevAndNext(
		long codeId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceCodeException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the resource code where companyId = &#63; and name = &#63; and scope = &#63; or throws a {@link com.liferay.portal.NoSuchResourceCodeException} if it could not be found.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @return the matching resource code
	* @throws com.liferay.portal.NoSuchResourceCodeException if a matching resource code could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceCode findByC_N_S(long companyId,
		java.lang.String name, int scope)
		throws com.liferay.portal.NoSuchResourceCodeException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the resource code where companyId = &#63; and name = &#63; and scope = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @return the matching resource code, or <code>null</code> if a matching resource code could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceCode fetchByC_N_S(long companyId,
		java.lang.String name, int scope)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the resource code where companyId = &#63; and name = &#63; and scope = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching resource code, or <code>null</code> if a matching resource code could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceCode fetchByC_N_S(long companyId,
		java.lang.String name, int scope, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the resource codes.
	*
	* @return the resource codes
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourceCode> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the resource codes.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of resource codes
	* @param end the upper bound of the range of resource codes (not inclusive)
	* @return the range of resource codes
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourceCode> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the resource codes.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of resource codes
	* @param end the upper bound of the range of resource codes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of resource codes
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourceCode> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the resource codes where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the resource codes where name = &#63; from the database.
	*
	* @param name the name
	* @throws SystemException if a system exception occurred
	*/
	public void removeByName(java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the resource code where companyId = &#63; and name = &#63; and scope = &#63; from the database.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @return the resource code that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceCode removeByC_N_S(long companyId,
		java.lang.String name, int scope)
		throws com.liferay.portal.NoSuchResourceCodeException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the resource codes from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of resource codes where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching resource codes
	* @throws SystemException if a system exception occurred
	*/
	public int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of resource codes where name = &#63;.
	*
	* @param name the name
	* @return the number of matching resource codes
	* @throws SystemException if a system exception occurred
	*/
	public int countByName(java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of resource codes where companyId = &#63; and name = &#63; and scope = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @return the number of matching resource codes
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_N_S(long companyId, java.lang.String name, int scope)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of resource codes.
	*
	* @return the number of resource codes
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}