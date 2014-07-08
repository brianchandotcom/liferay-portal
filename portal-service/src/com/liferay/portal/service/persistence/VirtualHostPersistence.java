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

package com.liferay.portal.service.persistence;

import aQute.bnd.annotation.ProviderType;

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
@ProviderType
public interface VirtualHostPersistence extends BasePersistence<VirtualHost> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link VirtualHostUtil} to access the virtual host persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns the virtual host where hostname = &#63; or throws a {@link com.liferay.portal.NoSuchVirtualHostException} if it could not be found.
	*
	* @param hostname the hostname
	* @return the matching virtual host
	* @throws com.liferay.portal.NoSuchVirtualHostException if a matching virtual host could not be found
	*/
	public com.liferay.portal.model.VirtualHost findByHostname(
		java.lang.String hostname)
		throws com.liferay.portal.NoSuchVirtualHostException;

	/**
	* Returns the virtual host where hostname = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param hostname the hostname
	* @return the matching virtual host, or <code>null</code> if a matching virtual host could not be found
	*/
	public com.liferay.portal.model.VirtualHost fetchByHostname(
		java.lang.String hostname);

	/**
	* Returns the virtual host where hostname = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param hostname the hostname
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching virtual host, or <code>null</code> if a matching virtual host could not be found
	*/
	public com.liferay.portal.model.VirtualHost fetchByHostname(
		java.lang.String hostname, boolean retrieveFromCache);

	/**
	* Removes the virtual host where hostname = &#63; from the database.
	*
	* @param hostname the hostname
	* @return the virtual host that was removed
	*/
	public com.liferay.portal.model.VirtualHost removeByHostname(
		java.lang.String hostname)
		throws com.liferay.portal.NoSuchVirtualHostException;

	/**
	* Returns the number of virtual hosts where hostname = &#63;.
	*
	* @param hostname the hostname
	* @return the number of matching virtual hosts
	*/
	public int countByHostname(java.lang.String hostname);

	/**
	* Returns the virtual host where companyId = &#63; and layoutSetId = &#63; or throws a {@link com.liferay.portal.NoSuchVirtualHostException} if it could not be found.
	*
	* @param companyId the company ID
	* @param layoutSetId the layout set ID
	* @return the matching virtual host
	* @throws com.liferay.portal.NoSuchVirtualHostException if a matching virtual host could not be found
	*/
	public com.liferay.portal.model.VirtualHost findByC_L(long companyId,
		long layoutSetId) throws com.liferay.portal.NoSuchVirtualHostException;

	/**
	* Returns the virtual host where companyId = &#63; and layoutSetId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param layoutSetId the layout set ID
	* @return the matching virtual host, or <code>null</code> if a matching virtual host could not be found
	*/
	public com.liferay.portal.model.VirtualHost fetchByC_L(long companyId,
		long layoutSetId);

	/**
	* Returns the virtual host where companyId = &#63; and layoutSetId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param layoutSetId the layout set ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching virtual host, or <code>null</code> if a matching virtual host could not be found
	*/
	public com.liferay.portal.model.VirtualHost fetchByC_L(long companyId,
		long layoutSetId, boolean retrieveFromCache);

	/**
	* Removes the virtual host where companyId = &#63; and layoutSetId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param layoutSetId the layout set ID
	* @return the virtual host that was removed
	*/
	public com.liferay.portal.model.VirtualHost removeByC_L(long companyId,
		long layoutSetId) throws com.liferay.portal.NoSuchVirtualHostException;

	/**
	* Returns the number of virtual hosts where companyId = &#63; and layoutSetId = &#63;.
	*
	* @param companyId the company ID
	* @param layoutSetId the layout set ID
	* @return the number of matching virtual hosts
	*/
	public int countByC_L(long companyId, long layoutSetId);

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
	*/
	public com.liferay.portal.model.VirtualHost remove(long virtualHostId)
		throws com.liferay.portal.NoSuchVirtualHostException;

	public com.liferay.portal.model.VirtualHost updateImpl(
		com.liferay.portal.model.VirtualHost virtualHost);

	/**
	* Returns the virtual host with the primary key or throws a {@link com.liferay.portal.NoSuchVirtualHostException} if it could not be found.
	*
	* @param virtualHostId the primary key of the virtual host
	* @return the virtual host
	* @throws com.liferay.portal.NoSuchVirtualHostException if a virtual host with the primary key could not be found
	*/
	public com.liferay.portal.model.VirtualHost findByPrimaryKey(
		long virtualHostId)
		throws com.liferay.portal.NoSuchVirtualHostException;

	/**
	* Returns the virtual host with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param virtualHostId the primary key of the virtual host
	* @return the virtual host, or <code>null</code> if a virtual host with the primary key could not be found
	*/
	public com.liferay.portal.model.VirtualHost fetchByPrimaryKey(
		long virtualHostId);

	@Override
	public java.util.Map<java.io.Serializable, com.liferay.portal.model.VirtualHost> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the virtual hosts.
	*
	* @return the virtual hosts
	*/
	public java.util.List<com.liferay.portal.model.VirtualHost> findAll();

	/**
	* Returns a range of all the virtual hosts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.VirtualHostModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of virtual hosts
	* @param end the upper bound of the range of virtual hosts (not inclusive)
	* @return the range of virtual hosts
	*/
	public java.util.List<com.liferay.portal.model.VirtualHost> findAll(
		int start, int end);

	/**
	* Returns an ordered range of all the virtual hosts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.VirtualHostModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of virtual hosts
	* @param end the upper bound of the range of virtual hosts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of virtual hosts
	*/
	public java.util.List<com.liferay.portal.model.VirtualHost> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Removes all the virtual hosts from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of virtual hosts.
	*
	* @return the number of virtual hosts
	*/
	public int countAll();
}