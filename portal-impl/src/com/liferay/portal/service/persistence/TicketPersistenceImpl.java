/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.NoSuchTicketException;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.Ticket;
import com.liferay.portal.model.impl.TicketImpl;
import com.liferay.portal.model.impl.TicketModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * The persistence implementation for the ticket service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TicketPersistence
 * @see TicketUtil
 * @generated
 */
public class TicketPersistenceImpl extends BasePersistenceImpl<Ticket>
	implements TicketPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link TicketUtil} to access the ticket persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = TicketImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(TicketModelImpl.ENTITY_CACHE_ENABLED,
			TicketModelImpl.FINDER_CACHE_ENABLED, TicketImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(TicketModelImpl.ENTITY_CACHE_ENABLED,
			TicketModelImpl.FINDER_CACHE_ENABLED, TicketImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(TicketModelImpl.ENTITY_CACHE_ENABLED,
			TicketModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_FETCH_BY_KEY = new FinderPath(TicketModelImpl.ENTITY_CACHE_ENABLED,
			TicketModelImpl.FINDER_CACHE_ENABLED, TicketImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByKey",
			new String[] { String.class.getName() },
			TicketModelImpl.KEY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_KEY = new FinderPath(TicketModelImpl.ENTITY_CACHE_ENABLED,
			TicketModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByKey",
			new String[] { String.class.getName() });

	/**
	 * Returns the ticket where key = &#63; or throws a {@link com.liferay.portal.NoSuchTicketException} if it could not be found.
	 *
	 * @param key the key
	 * @return the matching ticket
	 * @throws com.liferay.portal.NoSuchTicketException if a matching ticket could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Ticket findByKey(String key)
		throws NoSuchTicketException, SystemException {
		Ticket ticket = fetchByKey(key);

		if (ticket == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("key=");
			msg.append(key);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchTicketException(msg.toString());
		}

		return ticket;
	}

	/**
	 * Returns the ticket where key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param key the key
	 * @return the matching ticket, or <code>null</code> if a matching ticket could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Ticket fetchByKey(String key) throws SystemException {
		return fetchByKey(key, true);
	}

	/**
	 * Returns the ticket where key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param key the key
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching ticket, or <code>null</code> if a matching ticket could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Ticket fetchByKey(String key, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { key };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_KEY,
					finderArgs, this);
		}

		if (result instanceof Ticket) {
			Ticket ticket = (Ticket)result;

			if (!Validator.equals(key, ticket.getKey())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_TICKET_WHERE);

			boolean bindKey = false;

			if (key == null) {
				query.append(_FINDER_COLUMN_KEY_KEY_1);
			}
			else if (key.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_KEY_KEY_3);
			}
			else {
				bindKey = true;

				query.append(_FINDER_COLUMN_KEY_KEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindKey) {
					qPos.add(key);
				}

				List<Ticket> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_KEY,
						finderArgs, list);
				}
				else {
					if ((list.size() > 1) && _log.isWarnEnabled()) {
						_log.warn(
							"TicketPersistenceImpl.fetchByKey(String, boolean) with parameters (" +
							StringUtil.merge(finderArgs) +
							") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
					}

					Ticket ticket = list.get(0);

					result = ticket;

					cacheResult(ticket);

					if ((ticket.getKey() == null) ||
							!ticket.getKey().equals(key)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_KEY,
							finderArgs, ticket);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_KEY,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (Ticket)result;
		}
	}

	/**
	 * Removes the ticket where key = &#63; from the database.
	 *
	 * @param key the key
	 * @return the ticket that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Ticket removeByKey(String key)
		throws NoSuchTicketException, SystemException {
		Ticket ticket = findByKey(key);

		return remove(ticket);
	}

	/**
	 * Returns the number of tickets where key = &#63;.
	 *
	 * @param key the key
	 * @return the number of matching tickets
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByKey(String key) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_KEY;

		Object[] finderArgs = new Object[] { key };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_TICKET_WHERE);

			boolean bindKey = false;

			if (key == null) {
				query.append(_FINDER_COLUMN_KEY_KEY_1);
			}
			else if (key.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_KEY_KEY_3);
			}
			else {
				bindKey = true;

				query.append(_FINDER_COLUMN_KEY_KEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindKey) {
					qPos.add(key);
				}

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_KEY_KEY_1 = "ticket.key IS NULL";
	private static final String _FINDER_COLUMN_KEY_KEY_2 = "ticket.key = ?";
	private static final String _FINDER_COLUMN_KEY_KEY_3 = "(ticket.key IS NULL OR ticket.key = '')";

	public TicketPersistenceImpl() {
		setModelClass(Ticket.class);
	}

	/**
	 * Caches the ticket in the entity cache if it is enabled.
	 *
	 * @param ticket the ticket
	 */
	@Override
	public void cacheResult(Ticket ticket) {
		EntityCacheUtil.putResult(TicketModelImpl.ENTITY_CACHE_ENABLED,
			TicketImpl.class, ticket.getPrimaryKey(), ticket);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_KEY,
			new Object[] { ticket.getKey() }, ticket);

		ticket.resetOriginalValues();
	}

	/**
	 * Caches the tickets in the entity cache if it is enabled.
	 *
	 * @param tickets the tickets
	 */
	@Override
	public void cacheResult(List<Ticket> tickets) {
		for (Ticket ticket : tickets) {
			if (EntityCacheUtil.getResult(
						TicketModelImpl.ENTITY_CACHE_ENABLED, TicketImpl.class,
						ticket.getPrimaryKey()) == null) {
				cacheResult(ticket);
			}
			else {
				ticket.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all tickets.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(TicketImpl.class.getName());
		}

		EntityCacheUtil.clearCache(TicketImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the ticket.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Ticket ticket) {
		EntityCacheUtil.removeResult(TicketModelImpl.ENTITY_CACHE_ENABLED,
			TicketImpl.class, ticket.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(ticket);
	}

	@Override
	public void clearCache(List<Ticket> tickets) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Ticket ticket : tickets) {
			EntityCacheUtil.removeResult(TicketModelImpl.ENTITY_CACHE_ENABLED,
				TicketImpl.class, ticket.getPrimaryKey());

			clearUniqueFindersCache(ticket);
		}
	}

	protected void cacheUniqueFindersCache(Ticket ticket) {
		if (ticket.isNew()) {
			Object[] args = new Object[] { ticket.getKey() };

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_KEY, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_KEY, args, ticket);
		}
		else {
			TicketModelImpl ticketModelImpl = (TicketModelImpl)ticket;

			if ((ticketModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_KEY.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { ticket.getKey() };

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_KEY, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_KEY, args, ticket);
			}
		}
	}

	protected void clearUniqueFindersCache(Ticket ticket) {
		TicketModelImpl ticketModelImpl = (TicketModelImpl)ticket;

		Object[] args = new Object[] { ticket.getKey() };

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_KEY, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_KEY, args);

		if ((ticketModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_KEY.getColumnBitmask()) != 0) {
			args = new Object[] { ticketModelImpl.getOriginalKey() };

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_KEY, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_KEY, args);
		}
	}

	/**
	 * Creates a new ticket with the primary key. Does not add the ticket to the database.
	 *
	 * @param ticketId the primary key for the new ticket
	 * @return the new ticket
	 */
	@Override
	public Ticket create(long ticketId) {
		Ticket ticket = new TicketImpl();

		ticket.setNew(true);
		ticket.setPrimaryKey(ticketId);

		return ticket;
	}

	/**
	 * Removes the ticket with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ticketId the primary key of the ticket
	 * @return the ticket that was removed
	 * @throws com.liferay.portal.NoSuchTicketException if a ticket with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Ticket remove(long ticketId)
		throws NoSuchTicketException, SystemException {
		return remove((Serializable)ticketId);
	}

	/**
	 * Removes the ticket with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the ticket
	 * @return the ticket that was removed
	 * @throws com.liferay.portal.NoSuchTicketException if a ticket with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Ticket remove(Serializable primaryKey)
		throws NoSuchTicketException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Ticket ticket = (Ticket)session.get(TicketImpl.class, primaryKey);

			if (ticket == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTicketException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(ticket);
		}
		catch (NoSuchTicketException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected Ticket removeImpl(Ticket ticket) throws SystemException {
		ticket = toUnwrappedModel(ticket);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ticket)) {
				ticket = (Ticket)session.get(TicketImpl.class,
						ticket.getPrimaryKeyObj());
			}

			if (ticket != null) {
				session.delete(ticket);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (ticket != null) {
			clearCache(ticket);
		}

		return ticket;
	}

	@Override
	public Ticket updateImpl(com.liferay.portal.model.Ticket ticket)
		throws SystemException {
		ticket = toUnwrappedModel(ticket);

		boolean isNew = ticket.isNew();

		Session session = null;

		try {
			session = openSession();

			if (ticket.isNew()) {
				session.save(ticket);

				ticket.setNew(false);
			}
			else {
				session.merge(ticket);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !TicketModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		EntityCacheUtil.putResult(TicketModelImpl.ENTITY_CACHE_ENABLED,
			TicketImpl.class, ticket.getPrimaryKey(), ticket);

		clearUniqueFindersCache(ticket);
		cacheUniqueFindersCache(ticket);

		ticket.resetOriginalValues();

		return ticket;
	}

	protected Ticket toUnwrappedModel(Ticket ticket) {
		if (ticket instanceof TicketImpl) {
			return ticket;
		}

		TicketImpl ticketImpl = new TicketImpl();

		ticketImpl.setNew(ticket.isNew());
		ticketImpl.setPrimaryKey(ticket.getPrimaryKey());

		ticketImpl.setTicketId(ticket.getTicketId());
		ticketImpl.setCompanyId(ticket.getCompanyId());
		ticketImpl.setCreateDate(ticket.getCreateDate());
		ticketImpl.setClassNameId(ticket.getClassNameId());
		ticketImpl.setClassPK(ticket.getClassPK());
		ticketImpl.setKey(ticket.getKey());
		ticketImpl.setType(ticket.getType());
		ticketImpl.setExtraInfo(ticket.getExtraInfo());
		ticketImpl.setExpirationDate(ticket.getExpirationDate());
		ticketImpl.setMvccVersion(ticket.getMvccVersion());

		return ticketImpl;
	}

	/**
	 * Returns the ticket with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the ticket
	 * @return the ticket
	 * @throws com.liferay.portal.NoSuchTicketException if a ticket with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Ticket findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTicketException, SystemException {
		Ticket ticket = fetchByPrimaryKey(primaryKey);

		if (ticket == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTicketException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return ticket;
	}

	/**
	 * Returns the ticket with the primary key or throws a {@link com.liferay.portal.NoSuchTicketException} if it could not be found.
	 *
	 * @param ticketId the primary key of the ticket
	 * @return the ticket
	 * @throws com.liferay.portal.NoSuchTicketException if a ticket with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Ticket findByPrimaryKey(long ticketId)
		throws NoSuchTicketException, SystemException {
		return findByPrimaryKey((Serializable)ticketId);
	}

	/**
	 * Returns the ticket with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ticket
	 * @return the ticket, or <code>null</code> if a ticket with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Ticket fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		Ticket ticket = (Ticket)EntityCacheUtil.getResult(TicketModelImpl.ENTITY_CACHE_ENABLED,
				TicketImpl.class, primaryKey);

		if (ticket == _nullTicket) {
			return null;
		}

		if (ticket == null) {
			Session session = null;

			try {
				session = openSession();

				ticket = (Ticket)session.get(TicketImpl.class, primaryKey);

				if (ticket != null) {
					cacheResult(ticket);
				}
				else {
					EntityCacheUtil.putResult(TicketModelImpl.ENTITY_CACHE_ENABLED,
						TicketImpl.class, primaryKey, _nullTicket);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(TicketModelImpl.ENTITY_CACHE_ENABLED,
					TicketImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return ticket;
	}

	/**
	 * Returns the ticket with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ticketId the primary key of the ticket
	 * @return the ticket, or <code>null</code> if a ticket with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Ticket fetchByPrimaryKey(long ticketId) throws SystemException {
		return fetchByPrimaryKey((Serializable)ticketId);
	}

	/**
	 * Returns all the tickets.
	 *
	 * @return the tickets
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Ticket> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Ticket> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Ticket> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<Ticket> list = (List<Ticket>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_TICKET);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_TICKET;

				if (pagination) {
					sql = sql.concat(TicketModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<Ticket>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = new UnmodifiableList<Ticket>(list);
				}
				else {
					list = (List<Ticket>)QueryUtil.list(q, getDialect(), start,
							end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the tickets from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeAll() throws SystemException {
		for (Ticket ticket : findAll()) {
			remove(ticket);
		}
	}

	/**
	 * Returns the number of tickets.
	 *
	 * @return the number of tickets
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_TICKET);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	/**
	 * Initializes the ticket persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.Ticket")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Ticket>> listenersList = new ArrayList<ModelListener<Ticket>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Ticket>)InstanceFactory.newInstance(
							getClassLoader(), listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(TicketImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_TICKET = "SELECT ticket FROM Ticket ticket";
	private static final String _SQL_SELECT_TICKET_WHERE = "SELECT ticket FROM Ticket ticket WHERE ";
	private static final String _SQL_COUNT_TICKET = "SELECT COUNT(ticket) FROM Ticket ticket";
	private static final String _SQL_COUNT_TICKET_WHERE = "SELECT COUNT(ticket) FROM Ticket ticket WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "ticket.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Ticket exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Ticket exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(TicketPersistenceImpl.class);
	private static Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"key", "type"
			});
	private static Ticket _nullTicket = new TicketImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<Ticket> toCacheModel() {
				return _nullTicketCacheModel;
			}
		};

	private static CacheModel<Ticket> _nullTicketCacheModel = new CacheModel<Ticket>() {
			@Override
			public Ticket toEntityModel() {
				return _nullTicket;
			}
		};
}