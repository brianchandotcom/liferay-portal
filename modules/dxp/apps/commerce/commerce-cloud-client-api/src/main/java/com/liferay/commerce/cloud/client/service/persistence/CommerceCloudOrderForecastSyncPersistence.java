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

package com.liferay.commerce.cloud.client.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.cloud.client.exception.NoSuchCloudOrderForecastSyncException;
import com.liferay.commerce.cloud.client.model.CommerceCloudOrderForecastSync;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import java.util.Date;

/**
 * The persistence interface for the commerce cloud order forecast sync service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Andrea Di Giorgi
 * @see com.liferay.commerce.cloud.client.service.persistence.impl.CommerceCloudOrderForecastSyncPersistenceImpl
 * @see CommerceCloudOrderForecastSyncUtil
 * @generated
 */
@ProviderType
public interface CommerceCloudOrderForecastSyncPersistence
	extends BasePersistence<CommerceCloudOrderForecastSync> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceCloudOrderForecastSyncUtil} to access the commerce cloud order forecast sync persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns the commerce cloud order forecast sync where commerceOrderId = &#63; or throws a {@link NoSuchCloudOrderForecastSyncException} if it could not be found.
	*
	* @param commerceOrderId the commerce order ID
	* @return the matching commerce cloud order forecast sync
	* @throws NoSuchCloudOrderForecastSyncException if a matching commerce cloud order forecast sync could not be found
	*/
	public CommerceCloudOrderForecastSync findByCommerceOrderId(
		long commerceOrderId) throws NoSuchCloudOrderForecastSyncException;

	/**
	* Returns the commerce cloud order forecast sync where commerceOrderId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param commerceOrderId the commerce order ID
	* @return the matching commerce cloud order forecast sync, or <code>null</code> if a matching commerce cloud order forecast sync could not be found
	*/
	public CommerceCloudOrderForecastSync fetchByCommerceOrderId(
		long commerceOrderId);

	/**
	* Returns the commerce cloud order forecast sync where commerceOrderId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param commerceOrderId the commerce order ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce cloud order forecast sync, or <code>null</code> if a matching commerce cloud order forecast sync could not be found
	*/
	public CommerceCloudOrderForecastSync fetchByCommerceOrderId(
		long commerceOrderId, boolean retrieveFromCache);

	/**
	* Removes the commerce cloud order forecast sync where commerceOrderId = &#63; from the database.
	*
	* @param commerceOrderId the commerce order ID
	* @return the commerce cloud order forecast sync that was removed
	*/
	public CommerceCloudOrderForecastSync removeByCommerceOrderId(
		long commerceOrderId) throws NoSuchCloudOrderForecastSyncException;

	/**
	* Returns the number of commerce cloud order forecast syncs where commerceOrderId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @return the number of matching commerce cloud order forecast syncs
	*/
	public int countByCommerceOrderId(long commerceOrderId);

	/**
	* Returns all the commerce cloud order forecast syncs where syncDate = &#63;.
	*
	* @param syncDate the sync date
	* @return the matching commerce cloud order forecast syncs
	*/
	public java.util.List<CommerceCloudOrderForecastSync> findBySyncDate(
		Date syncDate);

	/**
	* Returns a range of all the commerce cloud order forecast syncs where syncDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCloudOrderForecastSyncModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param syncDate the sync date
	* @param start the lower bound of the range of commerce cloud order forecast syncs
	* @param end the upper bound of the range of commerce cloud order forecast syncs (not inclusive)
	* @return the range of matching commerce cloud order forecast syncs
	*/
	public java.util.List<CommerceCloudOrderForecastSync> findBySyncDate(
		Date syncDate, int start, int end);

	/**
	* Returns an ordered range of all the commerce cloud order forecast syncs where syncDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCloudOrderForecastSyncModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param syncDate the sync date
	* @param start the lower bound of the range of commerce cloud order forecast syncs
	* @param end the upper bound of the range of commerce cloud order forecast syncs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce cloud order forecast syncs
	*/
	public java.util.List<CommerceCloudOrderForecastSync> findBySyncDate(
		Date syncDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCloudOrderForecastSync> orderByComparator);

	/**
	* Returns an ordered range of all the commerce cloud order forecast syncs where syncDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCloudOrderForecastSyncModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param syncDate the sync date
	* @param start the lower bound of the range of commerce cloud order forecast syncs
	* @param end the upper bound of the range of commerce cloud order forecast syncs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce cloud order forecast syncs
	*/
	public java.util.List<CommerceCloudOrderForecastSync> findBySyncDate(
		Date syncDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCloudOrderForecastSync> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce cloud order forecast sync in the ordered set where syncDate = &#63;.
	*
	* @param syncDate the sync date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cloud order forecast sync
	* @throws NoSuchCloudOrderForecastSyncException if a matching commerce cloud order forecast sync could not be found
	*/
	public CommerceCloudOrderForecastSync findBySyncDate_First(Date syncDate,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCloudOrderForecastSync> orderByComparator)
		throws NoSuchCloudOrderForecastSyncException;

	/**
	* Returns the first commerce cloud order forecast sync in the ordered set where syncDate = &#63;.
	*
	* @param syncDate the sync date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cloud order forecast sync, or <code>null</code> if a matching commerce cloud order forecast sync could not be found
	*/
	public CommerceCloudOrderForecastSync fetchBySyncDate_First(Date syncDate,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCloudOrderForecastSync> orderByComparator);

	/**
	* Returns the last commerce cloud order forecast sync in the ordered set where syncDate = &#63;.
	*
	* @param syncDate the sync date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cloud order forecast sync
	* @throws NoSuchCloudOrderForecastSyncException if a matching commerce cloud order forecast sync could not be found
	*/
	public CommerceCloudOrderForecastSync findBySyncDate_Last(Date syncDate,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCloudOrderForecastSync> orderByComparator)
		throws NoSuchCloudOrderForecastSyncException;

	/**
	* Returns the last commerce cloud order forecast sync in the ordered set where syncDate = &#63;.
	*
	* @param syncDate the sync date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cloud order forecast sync, or <code>null</code> if a matching commerce cloud order forecast sync could not be found
	*/
	public CommerceCloudOrderForecastSync fetchBySyncDate_Last(Date syncDate,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCloudOrderForecastSync> orderByComparator);

	/**
	* Returns the commerce cloud order forecast syncs before and after the current commerce cloud order forecast sync in the ordered set where syncDate = &#63;.
	*
	* @param commerceCloudOrderForecastSyncId the primary key of the current commerce cloud order forecast sync
	* @param syncDate the sync date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce cloud order forecast sync
	* @throws NoSuchCloudOrderForecastSyncException if a commerce cloud order forecast sync with the primary key could not be found
	*/
	public CommerceCloudOrderForecastSync[] findBySyncDate_PrevAndNext(
		long commerceCloudOrderForecastSyncId, Date syncDate,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCloudOrderForecastSync> orderByComparator)
		throws NoSuchCloudOrderForecastSyncException;

	/**
	* Removes all the commerce cloud order forecast syncs where syncDate = &#63; from the database.
	*
	* @param syncDate the sync date
	*/
	public void removeBySyncDate(Date syncDate);

	/**
	* Returns the number of commerce cloud order forecast syncs where syncDate = &#63;.
	*
	* @param syncDate the sync date
	* @return the number of matching commerce cloud order forecast syncs
	*/
	public int countBySyncDate(Date syncDate);

	/**
	* Caches the commerce cloud order forecast sync in the entity cache if it is enabled.
	*
	* @param commerceCloudOrderForecastSync the commerce cloud order forecast sync
	*/
	public void cacheResult(
		CommerceCloudOrderForecastSync commerceCloudOrderForecastSync);

	/**
	* Caches the commerce cloud order forecast syncs in the entity cache if it is enabled.
	*
	* @param commerceCloudOrderForecastSyncs the commerce cloud order forecast syncs
	*/
	public void cacheResult(
		java.util.List<CommerceCloudOrderForecastSync> commerceCloudOrderForecastSyncs);

	/**
	* Creates a new commerce cloud order forecast sync with the primary key. Does not add the commerce cloud order forecast sync to the database.
	*
	* @param commerceCloudOrderForecastSyncId the primary key for the new commerce cloud order forecast sync
	* @return the new commerce cloud order forecast sync
	*/
	public CommerceCloudOrderForecastSync create(
		long commerceCloudOrderForecastSyncId);

	/**
	* Removes the commerce cloud order forecast sync with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceCloudOrderForecastSyncId the primary key of the commerce cloud order forecast sync
	* @return the commerce cloud order forecast sync that was removed
	* @throws NoSuchCloudOrderForecastSyncException if a commerce cloud order forecast sync with the primary key could not be found
	*/
	public CommerceCloudOrderForecastSync remove(
		long commerceCloudOrderForecastSyncId)
		throws NoSuchCloudOrderForecastSyncException;

	public CommerceCloudOrderForecastSync updateImpl(
		CommerceCloudOrderForecastSync commerceCloudOrderForecastSync);

	/**
	* Returns the commerce cloud order forecast sync with the primary key or throws a {@link NoSuchCloudOrderForecastSyncException} if it could not be found.
	*
	* @param commerceCloudOrderForecastSyncId the primary key of the commerce cloud order forecast sync
	* @return the commerce cloud order forecast sync
	* @throws NoSuchCloudOrderForecastSyncException if a commerce cloud order forecast sync with the primary key could not be found
	*/
	public CommerceCloudOrderForecastSync findByPrimaryKey(
		long commerceCloudOrderForecastSyncId)
		throws NoSuchCloudOrderForecastSyncException;

	/**
	* Returns the commerce cloud order forecast sync with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceCloudOrderForecastSyncId the primary key of the commerce cloud order forecast sync
	* @return the commerce cloud order forecast sync, or <code>null</code> if a commerce cloud order forecast sync with the primary key could not be found
	*/
	public CommerceCloudOrderForecastSync fetchByPrimaryKey(
		long commerceCloudOrderForecastSyncId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceCloudOrderForecastSync> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce cloud order forecast syncs.
	*
	* @return the commerce cloud order forecast syncs
	*/
	public java.util.List<CommerceCloudOrderForecastSync> findAll();

	/**
	* Returns a range of all the commerce cloud order forecast syncs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCloudOrderForecastSyncModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce cloud order forecast syncs
	* @param end the upper bound of the range of commerce cloud order forecast syncs (not inclusive)
	* @return the range of commerce cloud order forecast syncs
	*/
	public java.util.List<CommerceCloudOrderForecastSync> findAll(int start,
		int end);

	/**
	* Returns an ordered range of all the commerce cloud order forecast syncs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCloudOrderForecastSyncModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce cloud order forecast syncs
	* @param end the upper bound of the range of commerce cloud order forecast syncs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce cloud order forecast syncs
	*/
	public java.util.List<CommerceCloudOrderForecastSync> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCloudOrderForecastSync> orderByComparator);

	/**
	* Returns an ordered range of all the commerce cloud order forecast syncs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCloudOrderForecastSyncModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce cloud order forecast syncs
	* @param end the upper bound of the range of commerce cloud order forecast syncs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce cloud order forecast syncs
	*/
	public java.util.List<CommerceCloudOrderForecastSync> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCloudOrderForecastSync> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce cloud order forecast syncs from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce cloud order forecast syncs.
	*
	* @return the number of commerce cloud order forecast syncs
	*/
	public int countAll();

	@Override
	public java.util.Set<String> getBadColumnNames();
}