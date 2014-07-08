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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.model.Ticket;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the ticket service. This utility wraps {@link TicketPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TicketPersistence
 * @see TicketPersistenceImpl
 * @generated
 */
@ProviderType
public class TicketUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(Ticket ticket) {
		getPersistence().clearCache(ticket);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Ticket> findWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Ticket> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Ticket> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end, OrderByComparator orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static Ticket update(Ticket ticket) {
		return getPersistence().update(ticket);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static Ticket update(Ticket ticket, ServiceContext serviceContext) {
		return getPersistence().update(ticket, serviceContext);
	}

	/**
	* Returns the ticket where key = &#63; or throws a {@link com.liferay.portal.NoSuchTicketException} if it could not be found.
	*
	* @param key the key
	* @return the matching ticket
	* @throws com.liferay.portal.NoSuchTicketException if a matching ticket could not be found
	*/
	public static com.liferay.portal.model.Ticket findByKey(
		java.lang.String key) throws com.liferay.portal.NoSuchTicketException {
		return getPersistence().findByKey(key);
	}

	/**
	* Returns the ticket where key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param key the key
	* @return the matching ticket, or <code>null</code> if a matching ticket could not be found
	*/
	public static com.liferay.portal.model.Ticket fetchByKey(
		java.lang.String key) {
		return getPersistence().fetchByKey(key);
	}

	/**
	* Returns the ticket where key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param key the key
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching ticket, or <code>null</code> if a matching ticket could not be found
	*/
	public static com.liferay.portal.model.Ticket fetchByKey(
		java.lang.String key, boolean retrieveFromCache) {
		return getPersistence().fetchByKey(key, retrieveFromCache);
	}

	/**
	* Removes the ticket where key = &#63; from the database.
	*
	* @param key the key
	* @return the ticket that was removed
	*/
	public static com.liferay.portal.model.Ticket removeByKey(
		java.lang.String key) throws com.liferay.portal.NoSuchTicketException {
		return getPersistence().removeByKey(key);
	}

	/**
	* Returns the number of tickets where key = &#63;.
	*
	* @param key the key
	* @return the number of matching tickets
	*/
	public static int countByKey(java.lang.String key) {
		return getPersistence().countByKey(key);
	}

	/**
	* Caches the ticket in the entity cache if it is enabled.
	*
	* @param ticket the ticket
	*/
	public static void cacheResult(com.liferay.portal.model.Ticket ticket) {
		getPersistence().cacheResult(ticket);
	}

	/**
	* Caches the tickets in the entity cache if it is enabled.
	*
	* @param tickets the tickets
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portal.model.Ticket> tickets) {
		getPersistence().cacheResult(tickets);
	}

	/**
	* Creates a new ticket with the primary key. Does not add the ticket to the database.
	*
	* @param ticketId the primary key for the new ticket
	* @return the new ticket
	*/
	public static com.liferay.portal.model.Ticket create(long ticketId) {
		return getPersistence().create(ticketId);
	}

	/**
	* Removes the ticket with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param ticketId the primary key of the ticket
	* @return the ticket that was removed
	* @throws com.liferay.portal.NoSuchTicketException if a ticket with the primary key could not be found
	*/
	public static com.liferay.portal.model.Ticket remove(long ticketId)
		throws com.liferay.portal.NoSuchTicketException {
		return getPersistence().remove(ticketId);
	}

	public static com.liferay.portal.model.Ticket updateImpl(
		com.liferay.portal.model.Ticket ticket) {
		return getPersistence().updateImpl(ticket);
	}

	/**
	* Returns the ticket with the primary key or throws a {@link com.liferay.portal.NoSuchTicketException} if it could not be found.
	*
	* @param ticketId the primary key of the ticket
	* @return the ticket
	* @throws com.liferay.portal.NoSuchTicketException if a ticket with the primary key could not be found
	*/
	public static com.liferay.portal.model.Ticket findByPrimaryKey(
		long ticketId) throws com.liferay.portal.NoSuchTicketException {
		return getPersistence().findByPrimaryKey(ticketId);
	}

	/**
	* Returns the ticket with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param ticketId the primary key of the ticket
	* @return the ticket, or <code>null</code> if a ticket with the primary key could not be found
	*/
	public static com.liferay.portal.model.Ticket fetchByPrimaryKey(
		long ticketId) {
		return getPersistence().fetchByPrimaryKey(ticketId);
	}

	public static java.util.Map<java.io.Serializable, com.liferay.portal.model.Ticket> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the tickets.
	*
	* @return the tickets
	*/
	public static java.util.List<com.liferay.portal.model.Ticket> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the tickets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.TicketModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of tickets
	* @param end the upper bound of the range of tickets (not inclusive)
	* @return the range of tickets
	*/
	public static java.util.List<com.liferay.portal.model.Ticket> findAll(
		int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the tickets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.TicketModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of tickets
	* @param end the upper bound of the range of tickets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of tickets
	*/
	public static java.util.List<com.liferay.portal.model.Ticket> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the tickets from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of tickets.
	*
	* @return the number of tickets
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static TicketPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (TicketPersistence)PortalBeanLocatorUtil.locate(TicketPersistence.class.getName());

			ReferenceRegistry.registerReference(TicketUtil.class, "_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setPersistence(TicketPersistence persistence) {
	}

	private static TicketPersistence _persistence;
}