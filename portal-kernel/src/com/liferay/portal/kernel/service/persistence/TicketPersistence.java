/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.persistence;

import com.liferay.portal.kernel.exception.NoSuchTicketException;
import com.liferay.portal.kernel.model.Ticket;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the ticket service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
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
	 * Returns the ticket where key = &#63; or throws a <code>NoSuchTicketException</code> if it could not be found.
	 *
	 * @param key the key
	 * @return the matching ticket
	 * @throws NoSuchTicketException if a matching ticket could not be found
	 */
	public Ticket findByKey(String key) throws NoSuchTicketException;

	/**
	 * Returns the ticket where key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param key the key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ticket, or <code>null</code> if a matching ticket could not be found
	 */
	public Ticket fetchByKey(String key, boolean useFinderCache);

	/**
	 * Removes the ticket where key = &#63; from the database.
	 *
	 * @param key the key
	 * @return the ticket that was removed
	 */
	public Ticket removeByKey(String key) throws NoSuchTicketException;

	/**
	 * Returns the number of tickets where key = &#63;.
	 *
	 * @param key the key
	 * @return the number of matching tickets
	 */
	public int countByKey(String key);

	/**
	 * Returns an ordered range of all the tickets where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.TicketModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of tickets
	 * @param end the upper bound of the range of tickets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching tickets
	 */
	public java.util.List<Ticket> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Ticket>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ticket in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ticket
	 * @throws NoSuchTicketException if a matching ticket could not be found
	 */
	public Ticket findByC_C_C_First(
			long companyId, long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator<Ticket>
				orderByComparator)
		throws NoSuchTicketException;

	/**
	 * Returns the first ticket in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ticket, or <code>null</code> if a matching ticket could not be found
	 */
	public Ticket fetchByC_C_C_First(
		long companyId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<Ticket>
			orderByComparator);

	/**
	 * Removes all the tickets where companyId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	public void removeByC_C_C(long companyId, long classNameId, long classPK);

	/**
	 * Returns the number of tickets where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching tickets
	 */
	public int countByC_C_C(long companyId, long classNameId, long classPK);

	/**
	 * Returns an ordered range of all the tickets where companyId = &#63; and type = &#63; and emailAddress = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.TicketModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param emailAddress the email address
	 * @param start the lower bound of the range of tickets
	 * @param end the upper bound of the range of tickets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching tickets
	 */
	public java.util.List<Ticket> findByC_T_EA(
		long companyId, int type, String emailAddress, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Ticket>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ticket in the ordered set where companyId = &#63; and type = &#63; and emailAddress = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param emailAddress the email address
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ticket
	 * @throws NoSuchTicketException if a matching ticket could not be found
	 */
	public Ticket findByC_T_EA_First(
			long companyId, int type, String emailAddress,
			com.liferay.portal.kernel.util.OrderByComparator<Ticket>
				orderByComparator)
		throws NoSuchTicketException;

	/**
	 * Returns the first ticket in the ordered set where companyId = &#63; and type = &#63; and emailAddress = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param emailAddress the email address
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ticket, or <code>null</code> if a matching ticket could not be found
	 */
	public Ticket fetchByC_T_EA_First(
		long companyId, int type, String emailAddress,
		com.liferay.portal.kernel.util.OrderByComparator<Ticket>
			orderByComparator);

	/**
	 * Removes all the tickets where companyId = &#63; and type = &#63; and emailAddress = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param emailAddress the email address
	 */
	public void removeByC_T_EA(long companyId, int type, String emailAddress);

	/**
	 * Returns the number of tickets where companyId = &#63; and type = &#63; and emailAddress = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param emailAddress the email address
	 * @return the number of matching tickets
	 */
	public int countByC_T_EA(long companyId, int type, String emailAddress);

	/**
	 * Returns an ordered range of all the tickets where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.TicketModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of tickets
	 * @param end the upper bound of the range of tickets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching tickets
	 */
	public java.util.List<Ticket> findByC_C_T(
		long classNameId, long classPK, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Ticket>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ticket in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ticket
	 * @throws NoSuchTicketException if a matching ticket could not be found
	 */
	public Ticket findByC_C_T_First(
			long classNameId, long classPK, int type,
			com.liferay.portal.kernel.util.OrderByComparator<Ticket>
				orderByComparator)
		throws NoSuchTicketException;

	/**
	 * Returns the first ticket in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ticket, or <code>null</code> if a matching ticket could not be found
	 */
	public Ticket fetchByC_C_T_First(
		long classNameId, long classPK, int type,
		com.liferay.portal.kernel.util.OrderByComparator<Ticket>
			orderByComparator);

	/**
	 * Removes all the tickets where classNameId = &#63; and classPK = &#63; and type = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 */
	public void removeByC_C_T(long classNameId, long classPK, int type);

	/**
	 * Returns the number of tickets where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @return the number of matching tickets
	 */
	public int countByC_C_T(long classNameId, long classPK, int type);

	/**
	 * Returns an ordered range of all the tickets where companyId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.TicketModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of tickets
	 * @param end the upper bound of the range of tickets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching tickets
	 */
	public java.util.List<Ticket> findByC_C_C_T(
		long companyId, long classNameId, long classPK, int type, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<Ticket>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ticket in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ticket
	 * @throws NoSuchTicketException if a matching ticket could not be found
	 */
	public Ticket findByC_C_C_T_First(
			long companyId, long classNameId, long classPK, int type,
			com.liferay.portal.kernel.util.OrderByComparator<Ticket>
				orderByComparator)
		throws NoSuchTicketException;

	/**
	 * Returns the first ticket in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ticket, or <code>null</code> if a matching ticket could not be found
	 */
	public Ticket fetchByC_C_C_T_First(
		long companyId, long classNameId, long classPK, int type,
		com.liferay.portal.kernel.util.OrderByComparator<Ticket>
			orderByComparator);

	/**
	 * Removes all the tickets where companyId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 */
	public void removeByC_C_C_T(
		long companyId, long classNameId, long classPK, int type);

	/**
	 * Returns the number of tickets where companyId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @return the number of matching tickets
	 */
	public int countByC_C_C_T(
		long companyId, long classNameId, long classPK, int type);

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
	 * Returns the ticket with the primary key or throws a <code>NoSuchTicketException</code> if it could not be found.
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

	/**
	 * Returns the ticket where key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param key the key
	 * @return the matching ticket, or <code>null</code> if a matching ticket could not be found
	 */
	public default Ticket fetchByKey(String key) {
		return fetchByKey(key, true);
	}

	/**
	 * Returns all the tickets where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching tickets
	 */
	public default java.util.List<Ticket> findByC_C_C(
		long companyId, long classNameId, long classPK) {

		return findByC_C_C(
			companyId, classNameId, classPK,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the tickets where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.TicketModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of tickets
	 * @param end the upper bound of the range of tickets (not inclusive)
	 * @return the range of matching tickets
	 */
	public default java.util.List<Ticket> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end) {

		return findByC_C_C(
			companyId, classNameId, classPK, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the tickets where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.TicketModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of tickets
	 * @param end the upper bound of the range of tickets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching tickets
	 */
	public default java.util.List<Ticket> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Ticket>
			orderByComparator) {

		return findByC_C_C(
			companyId, classNameId, classPK, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns all the tickets where companyId = &#63; and type = &#63; and emailAddress = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param emailAddress the email address
	 * @return the matching tickets
	 */
	public default java.util.List<Ticket> findByC_T_EA(
		long companyId, int type, String emailAddress) {

		return findByC_T_EA(
			companyId, type, emailAddress,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the tickets where companyId = &#63; and type = &#63; and emailAddress = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.TicketModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param emailAddress the email address
	 * @param start the lower bound of the range of tickets
	 * @param end the upper bound of the range of tickets (not inclusive)
	 * @return the range of matching tickets
	 */
	public default java.util.List<Ticket> findByC_T_EA(
		long companyId, int type, String emailAddress, int start, int end) {

		return findByC_T_EA(
			companyId, type, emailAddress, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the tickets where companyId = &#63; and type = &#63; and emailAddress = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.TicketModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param emailAddress the email address
	 * @param start the lower bound of the range of tickets
	 * @param end the upper bound of the range of tickets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching tickets
	 */
	public default java.util.List<Ticket> findByC_T_EA(
		long companyId, int type, String emailAddress, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Ticket>
			orderByComparator) {

		return findByC_T_EA(
			companyId, type, emailAddress, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the tickets where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @return the matching tickets
	 */
	public default java.util.List<Ticket> findByC_C_T(
		long classNameId, long classPK, int type) {

		return findByC_C_T(
			classNameId, classPK, type,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the tickets where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.TicketModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of tickets
	 * @param end the upper bound of the range of tickets (not inclusive)
	 * @return the range of matching tickets
	 */
	public default java.util.List<Ticket> findByC_C_T(
		long classNameId, long classPK, int type, int start, int end) {

		return findByC_C_T(classNameId, classPK, type, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the tickets where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.TicketModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of tickets
	 * @param end the upper bound of the range of tickets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching tickets
	 */
	public default java.util.List<Ticket> findByC_C_T(
		long classNameId, long classPK, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Ticket>
			orderByComparator) {

		return findByC_C_T(
			classNameId, classPK, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the tickets where companyId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @return the matching tickets
	 */
	public default java.util.List<Ticket> findByC_C_C_T(
		long companyId, long classNameId, long classPK, int type) {

		return findByC_C_C_T(
			companyId, classNameId, classPK, type,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the tickets where companyId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.TicketModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of tickets
	 * @param end the upper bound of the range of tickets (not inclusive)
	 * @return the range of matching tickets
	 */
	public default java.util.List<Ticket> findByC_C_C_T(
		long companyId, long classNameId, long classPK, int type, int start,
		int end) {

		return findByC_C_C_T(
			companyId, classNameId, classPK, type, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the tickets where companyId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.TicketModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of tickets
	 * @param end the upper bound of the range of tickets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching tickets
	 */
	public default java.util.List<Ticket> findByC_C_C_T(
		long companyId, long classNameId, long classPK, int type, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<Ticket>
			orderByComparator) {

		return findByC_C_C_T(
			companyId, classNameId, classPK, type, start, end,
			orderByComparator, true);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:688359375