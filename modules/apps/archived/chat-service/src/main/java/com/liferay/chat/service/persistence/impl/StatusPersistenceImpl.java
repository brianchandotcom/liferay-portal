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

package com.liferay.chat.service.persistence.impl;

import com.liferay.chat.exception.NoSuchStatusException;
import com.liferay.chat.model.Status;
import com.liferay.chat.model.StatusTable;
import com.liferay.chat.model.impl.StatusImpl;
import com.liferay.chat.model.impl.StatusModelImpl;
import com.liferay.chat.service.persistence.StatusPersistence;
import com.liferay.chat.service.persistence.impl.constants.ChatPersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the status service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = {StatusPersistence.class, BasePersistence.class})
public class StatusPersistenceImpl
	extends BasePersistenceImpl<Status> implements StatusPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>StatusUtil</code> to access the status persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		StatusImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByUserId;
	private FinderPath _finderPathCountByUserId;

	/**
	 * Returns the status where userId = &#63; or throws a <code>NoSuchStatusException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @return the matching status
	 * @throws NoSuchStatusException if a matching status could not be found
	 */
	@Override
	public Status findByUserId(long userId) throws NoSuchStatusException {
		Status status = fetchByUserId(userId);

		if (status == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("userId=");
			sb.append(userId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchStatusException(sb.toString());
		}

		return status;
	}

	/**
	 * Returns the status where userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @return the matching status, or <code>null</code> if a matching status could not be found
	 */
	@Override
	public Status fetchByUserId(long userId) {
		return fetchByUserId(userId, true);
	}

	/**
	 * Returns the status where userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching status, or <code>null</code> if a matching status could not be found
	 */
	@Override
	public Status fetchByUserId(long userId, boolean useFinderCache) {
		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {userId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByUserId, finderArgs);
		}

		if (result instanceof Status) {
			Status status = (Status)result;

			if (userId != status.getUserId()) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_STATUS_WHERE);

			sb.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				List<Status> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUserId, finderArgs, list);
					}
				}
				else {
					Status status = list.get(0);

					result = status;

					cacheResult(status);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (Status)result;
		}
	}

	/**
	 * Removes the status where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @return the status that was removed
	 */
	@Override
	public Status removeByUserId(long userId) throws NoSuchStatusException {
		Status status = findByUserId(userId);

		return remove(status);
	}

	/**
	 * Returns the number of statuses where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching statuses
	 */
	@Override
	public int countByUserId(long userId) {
		FinderPath finderPath = _finderPathCountByUserId;

		Object[] finderArgs = new Object[] {userId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_STATUS_WHERE);

			sb.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_USERID_USERID_2 =
		"status.userId = ?";

	private FinderPath _finderPathWithPaginationFindByModifiedDate;
	private FinderPath _finderPathWithoutPaginationFindByModifiedDate;
	private FinderPath _finderPathCountByModifiedDate;

	/**
	 * Returns all the statuses where modifiedDate = &#63;.
	 *
	 * @param modifiedDate the modified date
	 * @return the matching statuses
	 */
	@Override
	public List<Status> findByModifiedDate(long modifiedDate) {
		return findByModifiedDate(
			modifiedDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the statuses where modifiedDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StatusModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedDate the modified date
	 * @param start the lower bound of the range of statuses
	 * @param end the upper bound of the range of statuses (not inclusive)
	 * @return the range of matching statuses
	 */
	@Override
	public List<Status> findByModifiedDate(
		long modifiedDate, int start, int end) {

		return findByModifiedDate(modifiedDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the statuses where modifiedDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StatusModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedDate the modified date
	 * @param start the lower bound of the range of statuses
	 * @param end the upper bound of the range of statuses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching statuses
	 */
	@Override
	public List<Status> findByModifiedDate(
		long modifiedDate, int start, int end,
		OrderByComparator<Status> orderByComparator) {

		return findByModifiedDate(
			modifiedDate, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the statuses where modifiedDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StatusModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedDate the modified date
	 * @param start the lower bound of the range of statuses
	 * @param end the upper bound of the range of statuses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching statuses
	 */
	@Override
	public List<Status> findByModifiedDate(
		long modifiedDate, int start, int end,
		OrderByComparator<Status> orderByComparator, boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByModifiedDate;
				finderArgs = new Object[] {modifiedDate};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByModifiedDate;
			finderArgs = new Object[] {
				modifiedDate, start, end, orderByComparator
			};
		}

		List<Status> list = null;

		if (useFinderCache) {
			list = (List<Status>)finderCache.getResult(finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (Status status : list) {
					if (modifiedDate != status.getModifiedDate()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_STATUS_WHERE);

			sb.append(_FINDER_COLUMN_MODIFIEDDATE_MODIFIEDDATE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(StatusModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(modifiedDate);

				list = (List<Status>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first status in the ordered set where modifiedDate = &#63;.
	 *
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching status
	 * @throws NoSuchStatusException if a matching status could not be found
	 */
	@Override
	public Status findByModifiedDate_First(
			long modifiedDate, OrderByComparator<Status> orderByComparator)
		throws NoSuchStatusException {

		Status status = fetchByModifiedDate_First(
			modifiedDate, orderByComparator);

		if (status != null) {
			return status;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("modifiedDate=");
		sb.append(modifiedDate);

		sb.append("}");

		throw new NoSuchStatusException(sb.toString());
	}

	/**
	 * Returns the first status in the ordered set where modifiedDate = &#63;.
	 *
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching status, or <code>null</code> if a matching status could not be found
	 */
	@Override
	public Status fetchByModifiedDate_First(
		long modifiedDate, OrderByComparator<Status> orderByComparator) {

		List<Status> list = findByModifiedDate(
			modifiedDate, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last status in the ordered set where modifiedDate = &#63;.
	 *
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching status
	 * @throws NoSuchStatusException if a matching status could not be found
	 */
	@Override
	public Status findByModifiedDate_Last(
			long modifiedDate, OrderByComparator<Status> orderByComparator)
		throws NoSuchStatusException {

		Status status = fetchByModifiedDate_Last(
			modifiedDate, orderByComparator);

		if (status != null) {
			return status;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("modifiedDate=");
		sb.append(modifiedDate);

		sb.append("}");

		throw new NoSuchStatusException(sb.toString());
	}

	/**
	 * Returns the last status in the ordered set where modifiedDate = &#63;.
	 *
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching status, or <code>null</code> if a matching status could not be found
	 */
	@Override
	public Status fetchByModifiedDate_Last(
		long modifiedDate, OrderByComparator<Status> orderByComparator) {

		int count = countByModifiedDate(modifiedDate);

		if (count == 0) {
			return null;
		}

		List<Status> list = findByModifiedDate(
			modifiedDate, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the statuses before and after the current status in the ordered set where modifiedDate = &#63;.
	 *
	 * @param statusId the primary key of the current status
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next status
	 * @throws NoSuchStatusException if a status with the primary key could not be found
	 */
	@Override
	public Status[] findByModifiedDate_PrevAndNext(
			long statusId, long modifiedDate,
			OrderByComparator<Status> orderByComparator)
		throws NoSuchStatusException {

		Status status = findByPrimaryKey(statusId);

		Session session = null;

		try {
			session = openSession();

			Status[] array = new StatusImpl[3];

			array[0] = getByModifiedDate_PrevAndNext(
				session, status, modifiedDate, orderByComparator, true);

			array[1] = status;

			array[2] = getByModifiedDate_PrevAndNext(
				session, status, modifiedDate, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected Status getByModifiedDate_PrevAndNext(
		Session session, Status status, long modifiedDate,
		OrderByComparator<Status> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_STATUS_WHERE);

		sb.append(_FINDER_COLUMN_MODIFIEDDATE_MODIFIEDDATE_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(StatusModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(modifiedDate);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(status)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Status> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the statuses where modifiedDate = &#63; from the database.
	 *
	 * @param modifiedDate the modified date
	 */
	@Override
	public void removeByModifiedDate(long modifiedDate) {
		for (Status status :
				findByModifiedDate(
					modifiedDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(status);
		}
	}

	/**
	 * Returns the number of statuses where modifiedDate = &#63;.
	 *
	 * @param modifiedDate the modified date
	 * @return the number of matching statuses
	 */
	@Override
	public int countByModifiedDate(long modifiedDate) {
		FinderPath finderPath = _finderPathCountByModifiedDate;

		Object[] finderArgs = new Object[] {modifiedDate};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_STATUS_WHERE);

			sb.append(_FINDER_COLUMN_MODIFIEDDATE_MODIFIEDDATE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(modifiedDate);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_MODIFIEDDATE_MODIFIEDDATE_2 =
		"status.modifiedDate = ?";

	private FinderPath _finderPathWithPaginationFindByOnline;
	private FinderPath _finderPathWithoutPaginationFindByOnline;
	private FinderPath _finderPathCountByOnline;

	/**
	 * Returns all the statuses where online = &#63;.
	 *
	 * @param online the online
	 * @return the matching statuses
	 */
	@Override
	public List<Status> findByOnline(boolean online) {
		return findByOnline(online, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the statuses where online = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StatusModelImpl</code>.
	 * </p>
	 *
	 * @param online the online
	 * @param start the lower bound of the range of statuses
	 * @param end the upper bound of the range of statuses (not inclusive)
	 * @return the range of matching statuses
	 */
	@Override
	public List<Status> findByOnline(boolean online, int start, int end) {
		return findByOnline(online, start, end, null);
	}

	/**
	 * Returns an ordered range of all the statuses where online = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StatusModelImpl</code>.
	 * </p>
	 *
	 * @param online the online
	 * @param start the lower bound of the range of statuses
	 * @param end the upper bound of the range of statuses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching statuses
	 */
	@Override
	public List<Status> findByOnline(
		boolean online, int start, int end,
		OrderByComparator<Status> orderByComparator) {

		return findByOnline(online, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the statuses where online = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StatusModelImpl</code>.
	 * </p>
	 *
	 * @param online the online
	 * @param start the lower bound of the range of statuses
	 * @param end the upper bound of the range of statuses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching statuses
	 */
	@Override
	public List<Status> findByOnline(
		boolean online, int start, int end,
		OrderByComparator<Status> orderByComparator, boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByOnline;
				finderArgs = new Object[] {online};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByOnline;
			finderArgs = new Object[] {online, start, end, orderByComparator};
		}

		List<Status> list = null;

		if (useFinderCache) {
			list = (List<Status>)finderCache.getResult(finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (Status status : list) {
					if (online != status.isOnline()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_STATUS_WHERE);

			sb.append(_FINDER_COLUMN_ONLINE_ONLINE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(StatusModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(online);

				list = (List<Status>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first status in the ordered set where online = &#63;.
	 *
	 * @param online the online
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching status
	 * @throws NoSuchStatusException if a matching status could not be found
	 */
	@Override
	public Status findByOnline_First(
			boolean online, OrderByComparator<Status> orderByComparator)
		throws NoSuchStatusException {

		Status status = fetchByOnline_First(online, orderByComparator);

		if (status != null) {
			return status;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("online=");
		sb.append(online);

		sb.append("}");

		throw new NoSuchStatusException(sb.toString());
	}

	/**
	 * Returns the first status in the ordered set where online = &#63;.
	 *
	 * @param online the online
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching status, or <code>null</code> if a matching status could not be found
	 */
	@Override
	public Status fetchByOnline_First(
		boolean online, OrderByComparator<Status> orderByComparator) {

		List<Status> list = findByOnline(online, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last status in the ordered set where online = &#63;.
	 *
	 * @param online the online
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching status
	 * @throws NoSuchStatusException if a matching status could not be found
	 */
	@Override
	public Status findByOnline_Last(
			boolean online, OrderByComparator<Status> orderByComparator)
		throws NoSuchStatusException {

		Status status = fetchByOnline_Last(online, orderByComparator);

		if (status != null) {
			return status;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("online=");
		sb.append(online);

		sb.append("}");

		throw new NoSuchStatusException(sb.toString());
	}

	/**
	 * Returns the last status in the ordered set where online = &#63;.
	 *
	 * @param online the online
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching status, or <code>null</code> if a matching status could not be found
	 */
	@Override
	public Status fetchByOnline_Last(
		boolean online, OrderByComparator<Status> orderByComparator) {

		int count = countByOnline(online);

		if (count == 0) {
			return null;
		}

		List<Status> list = findByOnline(
			online, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the statuses before and after the current status in the ordered set where online = &#63;.
	 *
	 * @param statusId the primary key of the current status
	 * @param online the online
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next status
	 * @throws NoSuchStatusException if a status with the primary key could not be found
	 */
	@Override
	public Status[] findByOnline_PrevAndNext(
			long statusId, boolean online,
			OrderByComparator<Status> orderByComparator)
		throws NoSuchStatusException {

		Status status = findByPrimaryKey(statusId);

		Session session = null;

		try {
			session = openSession();

			Status[] array = new StatusImpl[3];

			array[0] = getByOnline_PrevAndNext(
				session, status, online, orderByComparator, true);

			array[1] = status;

			array[2] = getByOnline_PrevAndNext(
				session, status, online, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected Status getByOnline_PrevAndNext(
		Session session, Status status, boolean online,
		OrderByComparator<Status> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_STATUS_WHERE);

		sb.append(_FINDER_COLUMN_ONLINE_ONLINE_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(StatusModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(online);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(status)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Status> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the statuses where online = &#63; from the database.
	 *
	 * @param online the online
	 */
	@Override
	public void removeByOnline(boolean online) {
		for (Status status :
				findByOnline(
					online, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(status);
		}
	}

	/**
	 * Returns the number of statuses where online = &#63;.
	 *
	 * @param online the online
	 * @return the number of matching statuses
	 */
	@Override
	public int countByOnline(boolean online) {
		FinderPath finderPath = _finderPathCountByOnline;

		Object[] finderArgs = new Object[] {online};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_STATUS_WHERE);

			sb.append(_FINDER_COLUMN_ONLINE_ONLINE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(online);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_ONLINE_ONLINE_2 =
		"status.online = ?";

	private FinderPath _finderPathWithPaginationFindByM_O;
	private FinderPath _finderPathWithoutPaginationFindByM_O;
	private FinderPath _finderPathCountByM_O;

	/**
	 * Returns all the statuses where modifiedDate = &#63; and online = &#63;.
	 *
	 * @param modifiedDate the modified date
	 * @param online the online
	 * @return the matching statuses
	 */
	@Override
	public List<Status> findByM_O(long modifiedDate, boolean online) {
		return findByM_O(
			modifiedDate, online, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the statuses where modifiedDate = &#63; and online = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StatusModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedDate the modified date
	 * @param online the online
	 * @param start the lower bound of the range of statuses
	 * @param end the upper bound of the range of statuses (not inclusive)
	 * @return the range of matching statuses
	 */
	@Override
	public List<Status> findByM_O(
		long modifiedDate, boolean online, int start, int end) {

		return findByM_O(modifiedDate, online, start, end, null);
	}

	/**
	 * Returns an ordered range of all the statuses where modifiedDate = &#63; and online = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StatusModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedDate the modified date
	 * @param online the online
	 * @param start the lower bound of the range of statuses
	 * @param end the upper bound of the range of statuses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching statuses
	 */
	@Override
	public List<Status> findByM_O(
		long modifiedDate, boolean online, int start, int end,
		OrderByComparator<Status> orderByComparator) {

		return findByM_O(
			modifiedDate, online, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the statuses where modifiedDate = &#63; and online = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StatusModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedDate the modified date
	 * @param online the online
	 * @param start the lower bound of the range of statuses
	 * @param end the upper bound of the range of statuses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching statuses
	 */
	@Override
	public List<Status> findByM_O(
		long modifiedDate, boolean online, int start, int end,
		OrderByComparator<Status> orderByComparator, boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByM_O;
				finderArgs = new Object[] {modifiedDate, online};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByM_O;
			finderArgs = new Object[] {
				modifiedDate, online, start, end, orderByComparator
			};
		}

		List<Status> list = null;

		if (useFinderCache) {
			list = (List<Status>)finderCache.getResult(finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (Status status : list) {
					if ((modifiedDate != status.getModifiedDate()) ||
						(online != status.isOnline())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_STATUS_WHERE);

			sb.append(_FINDER_COLUMN_M_O_MODIFIEDDATE_2);

			sb.append(_FINDER_COLUMN_M_O_ONLINE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(StatusModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(modifiedDate);

				queryPos.add(online);

				list = (List<Status>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first status in the ordered set where modifiedDate = &#63; and online = &#63;.
	 *
	 * @param modifiedDate the modified date
	 * @param online the online
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching status
	 * @throws NoSuchStatusException if a matching status could not be found
	 */
	@Override
	public Status findByM_O_First(
			long modifiedDate, boolean online,
			OrderByComparator<Status> orderByComparator)
		throws NoSuchStatusException {

		Status status = fetchByM_O_First(
			modifiedDate, online, orderByComparator);

		if (status != null) {
			return status;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("modifiedDate=");
		sb.append(modifiedDate);

		sb.append(", online=");
		sb.append(online);

		sb.append("}");

		throw new NoSuchStatusException(sb.toString());
	}

	/**
	 * Returns the first status in the ordered set where modifiedDate = &#63; and online = &#63;.
	 *
	 * @param modifiedDate the modified date
	 * @param online the online
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching status, or <code>null</code> if a matching status could not be found
	 */
	@Override
	public Status fetchByM_O_First(
		long modifiedDate, boolean online,
		OrderByComparator<Status> orderByComparator) {

		List<Status> list = findByM_O(
			modifiedDate, online, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last status in the ordered set where modifiedDate = &#63; and online = &#63;.
	 *
	 * @param modifiedDate the modified date
	 * @param online the online
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching status
	 * @throws NoSuchStatusException if a matching status could not be found
	 */
	@Override
	public Status findByM_O_Last(
			long modifiedDate, boolean online,
			OrderByComparator<Status> orderByComparator)
		throws NoSuchStatusException {

		Status status = fetchByM_O_Last(
			modifiedDate, online, orderByComparator);

		if (status != null) {
			return status;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("modifiedDate=");
		sb.append(modifiedDate);

		sb.append(", online=");
		sb.append(online);

		sb.append("}");

		throw new NoSuchStatusException(sb.toString());
	}

	/**
	 * Returns the last status in the ordered set where modifiedDate = &#63; and online = &#63;.
	 *
	 * @param modifiedDate the modified date
	 * @param online the online
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching status, or <code>null</code> if a matching status could not be found
	 */
	@Override
	public Status fetchByM_O_Last(
		long modifiedDate, boolean online,
		OrderByComparator<Status> orderByComparator) {

		int count = countByM_O(modifiedDate, online);

		if (count == 0) {
			return null;
		}

		List<Status> list = findByM_O(
			modifiedDate, online, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the statuses before and after the current status in the ordered set where modifiedDate = &#63; and online = &#63;.
	 *
	 * @param statusId the primary key of the current status
	 * @param modifiedDate the modified date
	 * @param online the online
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next status
	 * @throws NoSuchStatusException if a status with the primary key could not be found
	 */
	@Override
	public Status[] findByM_O_PrevAndNext(
			long statusId, long modifiedDate, boolean online,
			OrderByComparator<Status> orderByComparator)
		throws NoSuchStatusException {

		Status status = findByPrimaryKey(statusId);

		Session session = null;

		try {
			session = openSession();

			Status[] array = new StatusImpl[3];

			array[0] = getByM_O_PrevAndNext(
				session, status, modifiedDate, online, orderByComparator, true);

			array[1] = status;

			array[2] = getByM_O_PrevAndNext(
				session, status, modifiedDate, online, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected Status getByM_O_PrevAndNext(
		Session session, Status status, long modifiedDate, boolean online,
		OrderByComparator<Status> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_STATUS_WHERE);

		sb.append(_FINDER_COLUMN_M_O_MODIFIEDDATE_2);

		sb.append(_FINDER_COLUMN_M_O_ONLINE_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(StatusModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(modifiedDate);

		queryPos.add(online);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(status)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Status> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the statuses where modifiedDate = &#63; and online = &#63; from the database.
	 *
	 * @param modifiedDate the modified date
	 * @param online the online
	 */
	@Override
	public void removeByM_O(long modifiedDate, boolean online) {
		for (Status status :
				findByM_O(
					modifiedDate, online, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(status);
		}
	}

	/**
	 * Returns the number of statuses where modifiedDate = &#63; and online = &#63;.
	 *
	 * @param modifiedDate the modified date
	 * @param online the online
	 * @return the number of matching statuses
	 */
	@Override
	public int countByM_O(long modifiedDate, boolean online) {
		FinderPath finderPath = _finderPathCountByM_O;

		Object[] finderArgs = new Object[] {modifiedDate, online};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_STATUS_WHERE);

			sb.append(_FINDER_COLUMN_M_O_MODIFIEDDATE_2);

			sb.append(_FINDER_COLUMN_M_O_ONLINE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(modifiedDate);

				queryPos.add(online);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_M_O_MODIFIEDDATE_2 =
		"status.modifiedDate = ? AND ";

	private static final String _FINDER_COLUMN_M_O_ONLINE_2 =
		"status.online = ?";

	public StatusPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("online", "online_");

		setDBColumnNames(dbColumnNames);

		setModelClass(Status.class);

		setModelImplClass(StatusImpl.class);
		setModelPKClass(long.class);

		setTable(StatusTable.INSTANCE);
	}

	/**
	 * Caches the status in the entity cache if it is enabled.
	 *
	 * @param status the status
	 */
	@Override
	public void cacheResult(Status status) {
		entityCache.putResult(StatusImpl.class, status.getPrimaryKey(), status);

		finderCache.putResult(
			_finderPathFetchByUserId, new Object[] {status.getUserId()},
			status);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the statuses in the entity cache if it is enabled.
	 *
	 * @param statuses the statuses
	 */
	@Override
	public void cacheResult(List<Status> statuses) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (statuses.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (Status status : statuses) {
			if (entityCache.getResult(
					StatusImpl.class, status.getPrimaryKey()) == null) {

				cacheResult(status);
			}
		}
	}

	/**
	 * Clears the cache for all statuses.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(StatusImpl.class);

		finderCache.clearCache(StatusImpl.class);
	}

	/**
	 * Clears the cache for the status.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Status status) {
		entityCache.removeResult(StatusImpl.class, status);
	}

	@Override
	public void clearCache(List<Status> statuses) {
		for (Status status : statuses) {
			entityCache.removeResult(StatusImpl.class, status);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(StatusImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(StatusImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(StatusModelImpl statusModelImpl) {
		Object[] args = new Object[] {statusModelImpl.getUserId()};

		finderCache.putResult(_finderPathCountByUserId, args, Long.valueOf(1));
		finderCache.putResult(_finderPathFetchByUserId, args, statusModelImpl);
	}

	/**
	 * Creates a new status with the primary key. Does not add the status to the database.
	 *
	 * @param statusId the primary key for the new status
	 * @return the new status
	 */
	@Override
	public Status create(long statusId) {
		Status status = new StatusImpl();

		status.setNew(true);
		status.setPrimaryKey(statusId);

		return status;
	}

	/**
	 * Removes the status with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param statusId the primary key of the status
	 * @return the status that was removed
	 * @throws NoSuchStatusException if a status with the primary key could not be found
	 */
	@Override
	public Status remove(long statusId) throws NoSuchStatusException {
		return remove((Serializable)statusId);
	}

	/**
	 * Removes the status with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the status
	 * @return the status that was removed
	 * @throws NoSuchStatusException if a status with the primary key could not be found
	 */
	@Override
	public Status remove(Serializable primaryKey) throws NoSuchStatusException {
		Session session = null;

		try {
			session = openSession();

			Status status = (Status)session.get(StatusImpl.class, primaryKey);

			if (status == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchStatusException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(status);
		}
		catch (NoSuchStatusException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected Status removeImpl(Status status) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(status)) {
				status = (Status)session.get(
					StatusImpl.class, status.getPrimaryKeyObj());
			}

			if (status != null) {
				session.delete(status);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (status != null) {
			clearCache(status);
		}

		return status;
	}

	@Override
	public Status updateImpl(Status status) {
		boolean isNew = status.isNew();

		if (!(status instanceof StatusModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(status.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(status);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in status proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom Status implementation " +
					status.getClass());
		}

		StatusModelImpl statusModelImpl = (StatusModelImpl)status;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(status);
			}
			else {
				status = (Status)session.merge(status);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(StatusImpl.class, statusModelImpl, false, true);

		cacheUniqueFindersCache(statusModelImpl);

		if (isNew) {
			status.setNew(false);
		}

		status.resetOriginalValues();

		return status;
	}

	/**
	 * Returns the status with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the status
	 * @return the status
	 * @throws NoSuchStatusException if a status with the primary key could not be found
	 */
	@Override
	public Status findByPrimaryKey(Serializable primaryKey)
		throws NoSuchStatusException {

		Status status = fetchByPrimaryKey(primaryKey);

		if (status == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchStatusException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return status;
	}

	/**
	 * Returns the status with the primary key or throws a <code>NoSuchStatusException</code> if it could not be found.
	 *
	 * @param statusId the primary key of the status
	 * @return the status
	 * @throws NoSuchStatusException if a status with the primary key could not be found
	 */
	@Override
	public Status findByPrimaryKey(long statusId) throws NoSuchStatusException {
		return findByPrimaryKey((Serializable)statusId);
	}

	/**
	 * Returns the status with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param statusId the primary key of the status
	 * @return the status, or <code>null</code> if a status with the primary key could not be found
	 */
	@Override
	public Status fetchByPrimaryKey(long statusId) {
		return fetchByPrimaryKey((Serializable)statusId);
	}

	/**
	 * Returns all the statuses.
	 *
	 * @return the statuses
	 */
	@Override
	public List<Status> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the statuses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StatusModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of statuses
	 * @param end the upper bound of the range of statuses (not inclusive)
	 * @return the range of statuses
	 */
	@Override
	public List<Status> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the statuses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StatusModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of statuses
	 * @param end the upper bound of the range of statuses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of statuses
	 */
	@Override
	public List<Status> findAll(
		int start, int end, OrderByComparator<Status> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the statuses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StatusModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of statuses
	 * @param end the upper bound of the range of statuses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of statuses
	 */
	@Override
	public List<Status> findAll(
		int start, int end, OrderByComparator<Status> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<Status> list = null;

		if (useFinderCache) {
			list = (List<Status>)finderCache.getResult(finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_STATUS);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_STATUS;

				sql = sql.concat(StatusModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<Status>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the statuses from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (Status status : findAll()) {
			remove(status);
		}
	}

	/**
	 * Returns the number of statuses.
	 *
	 * @return the number of statuses
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_STATUS);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "statusId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_STATUS;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return StatusModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the status persistence.
	 */
	@Activate
	public void activate() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathFetchByUserId = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUserId",
			new String[] {Long.class.getName()}, new String[] {"userId"}, true);

		_finderPathCountByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] {Long.class.getName()}, new String[] {"userId"},
			false);

		_finderPathWithPaginationFindByModifiedDate = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByModifiedDate",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"modifiedDate"}, true);

		_finderPathWithoutPaginationFindByModifiedDate = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByModifiedDate",
			new String[] {Long.class.getName()}, new String[] {"modifiedDate"},
			true);

		_finderPathCountByModifiedDate = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByModifiedDate",
			new String[] {Long.class.getName()}, new String[] {"modifiedDate"},
			false);

		_finderPathWithPaginationFindByOnline = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByOnline",
			new String[] {
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"online_"}, true);

		_finderPathWithoutPaginationFindByOnline = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByOnline",
			new String[] {Boolean.class.getName()}, new String[] {"online_"},
			true);

		_finderPathCountByOnline = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByOnline",
			new String[] {Boolean.class.getName()}, new String[] {"online_"},
			false);

		_finderPathWithPaginationFindByM_O = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByM_O",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"modifiedDate", "online_"}, true);

		_finderPathWithoutPaginationFindByM_O = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByM_O",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"modifiedDate", "online_"}, true);

		_finderPathCountByM_O = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByM_O",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"modifiedDate", "online_"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(StatusImpl.class.getName());
	}

	@Override
	@Reference(
		target = ChatPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = ChatPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = ChatPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_STATUS =
		"SELECT status FROM Status status";

	private static final String _SQL_SELECT_STATUS_WHERE =
		"SELECT status FROM Status status WHERE ";

	private static final String _SQL_COUNT_STATUS =
		"SELECT COUNT(status) FROM Status status";

	private static final String _SQL_COUNT_STATUS_WHERE =
		"SELECT COUNT(status) FROM Status status WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "status.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No Status exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No Status exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		StatusPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"online"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private StatusModelArgumentsResolver _statusModelArgumentsResolver;

}