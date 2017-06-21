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

package com.liferay.powwow.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import com.liferay.powwow.exception.NoSuchServerException;
import com.liferay.powwow.model.PowwowServer;

/**
 * The persistence interface for the powwow server service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Shinn Lok
 * @see com.liferay.powwow.service.persistence.impl.PowwowServerPersistenceImpl
 * @see PowwowServerUtil
 * @generated
 */
@ProviderType
public interface PowwowServerPersistence extends BasePersistence<PowwowServer> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link PowwowServerUtil} to access the powwow server persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the powwow servers where providerType = &#63; and active = &#63;.
	*
	* @param providerType the provider type
	* @param active the active
	* @return the matching powwow servers
	*/
	public java.util.List<PowwowServer> findByPT_A(
		java.lang.String providerType, boolean active);

	/**
	* Returns a range of all the powwow servers where providerType = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowServerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param providerType the provider type
	* @param active the active
	* @param start the lower bound of the range of powwow servers
	* @param end the upper bound of the range of powwow servers (not inclusive)
	* @return the range of matching powwow servers
	*/
	public java.util.List<PowwowServer> findByPT_A(
		java.lang.String providerType, boolean active, int start, int end);

	/**
	* Returns an ordered range of all the powwow servers where providerType = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowServerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param providerType the provider type
	* @param active the active
	* @param start the lower bound of the range of powwow servers
	* @param end the upper bound of the range of powwow servers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching powwow servers
	*/
	public java.util.List<PowwowServer> findByPT_A(
		java.lang.String providerType, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PowwowServer> orderByComparator);

	/**
	* Returns an ordered range of all the powwow servers where providerType = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowServerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param providerType the provider type
	* @param active the active
	* @param start the lower bound of the range of powwow servers
	* @param end the upper bound of the range of powwow servers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching powwow servers
	*/
	public java.util.List<PowwowServer> findByPT_A(
		java.lang.String providerType, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PowwowServer> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first powwow server in the ordered set where providerType = &#63; and active = &#63;.
	*
	* @param providerType the provider type
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching powwow server
	* @throws NoSuchServerException if a matching powwow server could not be found
	*/
	public PowwowServer findByPT_A_First(java.lang.String providerType,
		boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<PowwowServer> orderByComparator)
		throws NoSuchServerException;

	/**
	* Returns the first powwow server in the ordered set where providerType = &#63; and active = &#63;.
	*
	* @param providerType the provider type
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching powwow server, or <code>null</code> if a matching powwow server could not be found
	*/
	public PowwowServer fetchByPT_A_First(java.lang.String providerType,
		boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<PowwowServer> orderByComparator);

	/**
	* Returns the last powwow server in the ordered set where providerType = &#63; and active = &#63;.
	*
	* @param providerType the provider type
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching powwow server
	* @throws NoSuchServerException if a matching powwow server could not be found
	*/
	public PowwowServer findByPT_A_Last(java.lang.String providerType,
		boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<PowwowServer> orderByComparator)
		throws NoSuchServerException;

	/**
	* Returns the last powwow server in the ordered set where providerType = &#63; and active = &#63;.
	*
	* @param providerType the provider type
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching powwow server, or <code>null</code> if a matching powwow server could not be found
	*/
	public PowwowServer fetchByPT_A_Last(java.lang.String providerType,
		boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<PowwowServer> orderByComparator);

	/**
	* Returns the powwow servers before and after the current powwow server in the ordered set where providerType = &#63; and active = &#63;.
	*
	* @param powwowServerId the primary key of the current powwow server
	* @param providerType the provider type
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next powwow server
	* @throws NoSuchServerException if a powwow server with the primary key could not be found
	*/
	public PowwowServer[] findByPT_A_PrevAndNext(long powwowServerId,
		java.lang.String providerType, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<PowwowServer> orderByComparator)
		throws NoSuchServerException;

	/**
	* Removes all the powwow servers where providerType = &#63; and active = &#63; from the database.
	*
	* @param providerType the provider type
	* @param active the active
	*/
	public void removeByPT_A(java.lang.String providerType, boolean active);

	/**
	* Returns the number of powwow servers where providerType = &#63; and active = &#63;.
	*
	* @param providerType the provider type
	* @param active the active
	* @return the number of matching powwow servers
	*/
	public int countByPT_A(java.lang.String providerType, boolean active);

	/**
	* Caches the powwow server in the entity cache if it is enabled.
	*
	* @param powwowServer the powwow server
	*/
	public void cacheResult(PowwowServer powwowServer);

	/**
	* Caches the powwow servers in the entity cache if it is enabled.
	*
	* @param powwowServers the powwow servers
	*/
	public void cacheResult(java.util.List<PowwowServer> powwowServers);

	/**
	* Creates a new powwow server with the primary key. Does not add the powwow server to the database.
	*
	* @param powwowServerId the primary key for the new powwow server
	* @return the new powwow server
	*/
	public PowwowServer create(long powwowServerId);

	/**
	* Removes the powwow server with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param powwowServerId the primary key of the powwow server
	* @return the powwow server that was removed
	* @throws NoSuchServerException if a powwow server with the primary key could not be found
	*/
	public PowwowServer remove(long powwowServerId)
		throws NoSuchServerException;

	public PowwowServer updateImpl(PowwowServer powwowServer);

	/**
	* Returns the powwow server with the primary key or throws a {@link NoSuchServerException} if it could not be found.
	*
	* @param powwowServerId the primary key of the powwow server
	* @return the powwow server
	* @throws NoSuchServerException if a powwow server with the primary key could not be found
	*/
	public PowwowServer findByPrimaryKey(long powwowServerId)
		throws NoSuchServerException;

	/**
	* Returns the powwow server with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param powwowServerId the primary key of the powwow server
	* @return the powwow server, or <code>null</code> if a powwow server with the primary key could not be found
	*/
	public PowwowServer fetchByPrimaryKey(long powwowServerId);

	@Override
	public java.util.Map<java.io.Serializable, PowwowServer> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the powwow servers.
	*
	* @return the powwow servers
	*/
	public java.util.List<PowwowServer> findAll();

	/**
	* Returns a range of all the powwow servers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowServerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of powwow servers
	* @param end the upper bound of the range of powwow servers (not inclusive)
	* @return the range of powwow servers
	*/
	public java.util.List<PowwowServer> findAll(int start, int end);

	/**
	* Returns an ordered range of all the powwow servers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowServerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of powwow servers
	* @param end the upper bound of the range of powwow servers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of powwow servers
	*/
	public java.util.List<PowwowServer> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PowwowServer> orderByComparator);

	/**
	* Returns an ordered range of all the powwow servers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowServerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of powwow servers
	* @param end the upper bound of the range of powwow servers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of powwow servers
	*/
	public java.util.List<PowwowServer> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PowwowServer> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the powwow servers from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of powwow servers.
	*
	* @return the number of powwow servers
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}