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

import com.liferay.portal.model.VirtualHost;

/**
 * The persistence interface for the virtual host service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see VirtualHostPersistenceImpl
 * @see VirtualHostUtil
 * @generated
 */
public interface VirtualHostPersistence extends BasePersistence<VirtualHost> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link VirtualHostUtil} to access the virtual host persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the virtual host in the entity cache if it is enabled.
	*
	* @param virtualHost the virtual host
	*/
	public void cacheResult(com.liferay.portal.model.VirtualHost virtualHost);

	/**
	* Caches the virtual hosts in the entity cache if it is enabled.
	*
	* @param virtualHosts the virtual hosts
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.VirtualHost> virtualHosts);

	/**
	* Creates a new virtual host with the primary key. Does not add the virtual host to the database.
	*
	* @param virtualHostId the primary key for the new virtual host
	* @return the new virtual host
	*/
	public com.liferay.portal.model.VirtualHost create(long virtualHostId);

	/**
	* Removes the virtual host with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param virtualHostId the primary key of the virtual host
	* @return the virtual host that was removed
	* @throws com.liferay.portal.NoSuchVirtualHostException if a virtual host with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.VirtualHost remove(long virtualHostId)
		throws com.liferay.portal.NoSuchVirtualHostException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.VirtualHost updateImpl(
		com.liferay.portal.model.VirtualHost virtualHost, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the virtual host with the primary key or throws a {@link com.liferay.portal.NoSuchVirtualHostException} if it could not be found.
	*
	* @param virtualHostId the primary key of the virtual host
	* @return the virtual host
	* @throws com.liferay.portal.NoSuchVirtualHostException if a virtual host with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.VirtualHost findByPrimaryKey(
		long virtualHostId)
		throws com.liferay.portal.NoSuchVirtualHostException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the virtual host with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param virtualHostId the primary key of the virtual host
	* @return the virtual host, or <code>null</code> if a virtual host with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.VirtualHost fetchByPrimaryKey(
		long virtualHostId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the virtual host where hostname = &#63; or throws a {@link com.liferay.portal.NoSuchVirtualHostException} if it could not be found.
	*
	* @param hostname the hostname
	* @return the matching virtual host
	* @throws com.liferay.portal.NoSuchVirtualHostException if a matching virtual host could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.VirtualHost findByHostname(
		java.lang.String hostname)
		throws com.liferay.portal.NoSuchVirtualHostException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the virtual host where hostname = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param hostname the hostname
	* @return the matching virtual host, or <code>null</code> if a matching virtual host could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.VirtualHost fetchByHostname(
		java.lang.String hostname)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the virtual host where hostname = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param hostname the hostname
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching virtual host, or <code>null</code> if a matching virtual host could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.VirtualHost fetchByHostname(
		java.lang.String hostname, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the virtual host where companyId = &#63; and layoutSetId = &#63; or throws a {@link com.liferay.portal.NoSuchVirtualHostException} if it could not be found.
	*
	* @param companyId the company ID
	* @param layoutSetId the layout set ID
	* @return the matching virtual host
	* @throws com.liferay.portal.NoSuchVirtualHostException if a matching virtual host could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.VirtualHost findByC_L(long companyId,
		long layoutSetId)
		throws com.liferay.portal.NoSuchVirtualHostException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the virtual host where companyId = &#63; and layoutSetId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param layoutSetId the layout set ID
	* @return the matching virtual host, or <code>null</code> if a matching virtual host could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.VirtualHost fetchByC_L(long companyId,
		long layoutSetId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the virtual host where companyId = &#63; and layoutSetId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param layoutSetId the layout set ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching virtual host, or <code>null</code> if a matching virtual host could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.VirtualHost fetchByC_L(long companyId,
		long layoutSetId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the virtual hosts.
	*
	* @return the virtual hosts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.VirtualHost> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the virtual hosts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of virtual hosts
	* @param end the upper bound of the range of virtual hosts (not inclusive)
	* @return the range of virtual hosts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.VirtualHost> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the virtual hosts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of virtual hosts
	* @param end the upper bound of the range of virtual hosts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of virtual hosts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.VirtualHost> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the virtual host where hostname = &#63; from the database.
	*
	* @param hostname the hostname
	* @return the virtual host that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.VirtualHost removeByHostname(
		java.lang.String hostname)
		throws com.liferay.portal.NoSuchVirtualHostException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the virtual host where companyId = &#63; and layoutSetId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param layoutSetId the layout set ID
	* @return the virtual host that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.VirtualHost removeByC_L(long companyId,
		long layoutSetId)
		throws com.liferay.portal.NoSuchVirtualHostException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the virtual hosts from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of virtual hosts where hostname = &#63;.
	*
	* @param hostname the hostname
	* @return the number of matching virtual hosts
	* @throws SystemException if a system exception occurred
	*/
	public int countByHostname(java.lang.String hostname)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of virtual hosts where companyId = &#63; and layoutSetId = &#63;.
	*
	* @param companyId the company ID
	* @param layoutSetId the layout set ID
	* @return the number of matching virtual hosts
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_L(long companyId, long layoutSetId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of virtual hosts.
	*
	* @return the number of virtual hosts
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}