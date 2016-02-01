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

import com.liferay.portal.exception.NoSuchTicketException;
import com.liferay.portal.model.Ticket;

/**
 * The persistence interface for the ticket service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.persistence.impl.TicketPersistenceImpl
 * @see TicketUtil
 * @generated
 */
@ProviderType
public interface TicketPersistence extends BasePersistence<Ticket> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link TicketUtil} to access the ticket persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns the ticket where key = &#63; or throws a {@link NoSuchTicketException} if it could not be found.
	*
	* @param key the key
	* @return the matching ticket
	* @throws NoSuchTicketException if a matching ticket could not be found
	*/
	public Ticket findByKey(java.lang.String key) throws NoSuchTicketException;

	/**
	* Returns the ticket where key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param key the key
	* @return the matching ticket, or <code>null</code> if a matching ticket could not be found
	*/
	public Ticket fetchByKey(java.lang.String key);

	/**
	* Returns the ticket where key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param key the key
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching ticket, or <code>null</code> if a matching ticket could not be found
	*/
	public Ticket fetchByKey(java.lang.String key, boolean retrieveFromCache);

	/**
	* Removes the ticket where key = &#63; from the database.
	*
	* @param key the key
	* @return the ticket that was removed
	*/
	public Ticket removeByKey(java.lang.String key)
		throws NoSuchTicketException;

	/**
	* Returns the number of tickets where key = &#63;.
	*
	* @param key the key
	* @return the number of matching tickets
	*/
	public int countByKey(java.lang.String key);

	/**
	* Returns all the tickets where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @return the matching tickets
	*/
	public java.util.List<Ticket> findByC_C_T(long classNameId, long classPK,
		int type);

	/**
	* Returns a range of all the tickets where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link TicketModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param start the lower bound of the range of tickets
	* @param end the upper bound of the range of tickets (not inclusive)
	* @return the range of matching tickets
	*/
	public java.util.List<Ticket> findByC_C_T(long classNameId, long classPK,
		int type, int start, int end);

	/**
	* Returns an ordered range of all the tickets where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link TicketModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param start the lower bound of the range of tickets
	* @param end the upper bound of the range of tickets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching tickets
	*/
	public java.util.List<Ticket> findByC_C_T(long classNameId, long classPK,
		int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Ticket> orderByComparator);

	/**
	* Returns an ordered range of all the tickets where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link TicketModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param start the lower bound of the range of tickets
	* @param end the upper bound of the range of tickets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching tickets
	*/
	public java.util.List<Ticket> findByC_C_T(long classNameId, long classPK,
		int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Ticket> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first ticket in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ticket
	* @throws NoSuchTicketException if a matching ticket could not be found
	*/
	public Ticket findByC_C_T_First(long classNameId, long classPK, int type,
		com.liferay.portal.kernel.util.OrderByComparator<Ticket> orderByComparator)
		throws NoSuchTicketException;

	/**
	* Returns the first ticket in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ticket, or <code>null</code> if a matching ticket could not be found
	*/
	public Ticket fetchByC_C_T_First(long classNameId, long classPK, int type,
		com.liferay.portal.kernel.util.OrderByComparator<Ticket> orderByComparator);

	/**
	* Returns the last ticket in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ticket
	* @throws NoSuchTicketException if a matching ticket could not be found
	*/
	public Ticket findByC_C_T_Last(long classNameId, long classPK, int type,
		com.liferay.portal.kernel.util.OrderByComparator<Ticket> orderByComparator)
		throws NoSuchTicketException;

	/**
	* Returns the last ticket in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ticket, or <code>null</code> if a matching ticket could not be found
	*/
	public Ticket fetchByC_C_T_Last(long classNameId, long classPK, int type,
		com.liferay.portal.kernel.util.OrderByComparator<Ticket> orderByComparator);

	/**
	* Returns the tickets before and after the current ticket in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param ticketId the primary key of the current ticket
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next ticket
	* @throws NoSuchTicketException if a ticket with the primary key could not be found
	*/
	public Ticket[] findByC_C_T_PrevAndNext(long ticketId, long classNameId,
		long classPK, int type,
		com.liferay.portal.kernel.util.OrderByComparator<Ticket> orderByComparator)
		throws NoSuchTicketException;

	/**
	* Removes all the tickets where classNameId = &#63; and classPK = &#63; and type = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	*/
	public void removeByC_C_T(long classNameId, long classPK, int type);

	/**
	* Returns the number of tickets where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @return the number of matching tickets
	*/
	public int countByC_C_T(long classNameId, long classPK, int type);

	/**
	* Caches the ticket in the entity cache if it is enabled.
	*
	* @param ticket the ticket
	*/
	public void cacheResult(Ticket ticket);

	/**
	* Caches the tickets in the entity cache if it is enabled.
	*
	* @param tickets the tickets
	*/
	public void cacheResult(java.util.List<Ticket> tickets);

	/**
	* Creates a new ticket with the primary key. Does not add the ticket to the database.
	*
	* @param ticketId the primary key for the new ticket
	* @return the new ticket
	*/
	public Ticket create(long ticketId);

	/**
	* Removes the ticket with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param ticketId the primary key of the ticket
	* @return the ticket that was removed
	* @throws NoSuchTicketException if a ticket with the primary key could not be found
	*/
	public Ticket remove(long ticketId) throws NoSuchTicketException;

	public Ticket updateImpl(Ticket ticket);

	/**
	* Returns the ticket with the primary key or throws a {@link NoSuchTicketException} if it could not be found.
	*
	* @param ticketId the primary key of the ticket
	* @return the ticket
	* @throws NoSuchTicketException if a ticket with the primary key could not be found
	*/
	public Ticket findByPrimaryKey(long ticketId) throws NoSuchTicketException;

	/**
	* Returns the ticket with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param ticketId the primary key of the ticket
	* @return the ticket, or <code>null</code> if a ticket with the primary key could not be found
	*/
	public Ticket fetchByPrimaryKey(long ticketId);

	@Override
	public java.util.Map<java.io.Serializable, Ticket> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the tickets.
	*
	* @return the tickets
	*/
	public java.util.List<Ticket> findAll();

	/**
	* Returns a range of all the tickets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link TicketModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of tickets
	* @param end the upper bound of the range of tickets (not inclusive)
	* @return the range of tickets
	*/
	public java.util.List<Ticket> findAll(int start, int end);

	/**
	* Returns an ordered range of all the tickets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link TicketModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of tickets
	* @param end the upper bound of the range of tickets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of tickets
	*/
	public java.util.List<Ticket> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Ticket> orderByComparator);

	/**
	* Returns an ordered range of all the tickets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link TicketModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of tickets
	* @param end the upper bound of the range of tickets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of tickets
	*/
	public java.util.List<Ticket> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Ticket> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the tickets from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of tickets.
	*
	* @return the number of tickets
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}