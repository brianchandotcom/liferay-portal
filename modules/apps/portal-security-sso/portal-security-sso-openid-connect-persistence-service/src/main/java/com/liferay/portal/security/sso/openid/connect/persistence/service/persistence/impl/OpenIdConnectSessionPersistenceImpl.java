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

package com.liferay.portal.security.sso.openid.connect.persistence.service.persistence.impl;

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
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.security.sso.openid.connect.persistence.exception.NoSuchSessionException;
import com.liferay.portal.security.sso.openid.connect.persistence.model.OpenIdConnectSession;
import com.liferay.portal.security.sso.openid.connect.persistence.model.OpenIdConnectSessionTable;
import com.liferay.portal.security.sso.openid.connect.persistence.model.impl.OpenIdConnectSessionImpl;
import com.liferay.portal.security.sso.openid.connect.persistence.model.impl.OpenIdConnectSessionModelImpl;
import com.liferay.portal.security.sso.openid.connect.persistence.service.persistence.OpenIdConnectSessionPersistence;
import com.liferay.portal.security.sso.openid.connect.persistence.service.persistence.OpenIdConnectSessionUtil;
import com.liferay.portal.security.sso.openid.connect.persistence.service.persistence.impl.constants.OpenIdConnectPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the open ID connect session service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Arthur Chan
 * @generated
 */
@Component(
	service = {OpenIdConnectSessionPersistence.class, BasePersistence.class}
)
public class OpenIdConnectSessionPersistenceImpl
	extends BasePersistenceImpl<OpenIdConnectSession>
	implements OpenIdConnectSessionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>OpenIdConnectSessionUtil</code> to access the open ID connect session persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		OpenIdConnectSessionImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUserId;
	private FinderPath _finderPathWithoutPaginationFindByUserId;
	private FinderPath _finderPathCountByUserId;

	/**
	 * Returns all the open ID connect sessions where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching open ID connect sessions
	 */
	@Override
	public List<OpenIdConnectSession> findByUserId(long userId) {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the open ID connect sessions where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OpenIdConnectSessionModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of open ID connect sessions
	 * @param end the upper bound of the range of open ID connect sessions (not inclusive)
	 * @return the range of matching open ID connect sessions
	 */
	@Override
	public List<OpenIdConnectSession> findByUserId(
		long userId, int start, int end) {

		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the open ID connect sessions where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OpenIdConnectSessionModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of open ID connect sessions
	 * @param end the upper bound of the range of open ID connect sessions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching open ID connect sessions
	 */
	@Override
	public List<OpenIdConnectSession> findByUserId(
		long userId, int start, int end,
		OrderByComparator<OpenIdConnectSession> orderByComparator) {

		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the open ID connect sessions where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OpenIdConnectSessionModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of open ID connect sessions
	 * @param end the upper bound of the range of open ID connect sessions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching open ID connect sessions
	 */
	@Override
	public List<OpenIdConnectSession> findByUserId(
		long userId, int start, int end,
		OrderByComparator<OpenIdConnectSession> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUserId;
				finderArgs = new Object[] {userId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUserId;
			finderArgs = new Object[] {userId, start, end, orderByComparator};
		}

		List<OpenIdConnectSession> list = null;

		if (useFinderCache) {
			list = (List<OpenIdConnectSession>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (OpenIdConnectSession openIdConnectSession : list) {
					if (userId != openIdConnectSession.getUserId()) {
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

			sb.append(_SQL_SELECT_OPENIDCONNECTSESSION_WHERE);

			sb.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(OpenIdConnectSessionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				list = (List<OpenIdConnectSession>)QueryUtil.list(
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
	 * Returns the first open ID connect session in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching open ID connect session
	 * @throws NoSuchSessionException if a matching open ID connect session could not be found
	 */
	@Override
	public OpenIdConnectSession findByUserId_First(
			long userId,
			OrderByComparator<OpenIdConnectSession> orderByComparator)
		throws NoSuchSessionException {

		OpenIdConnectSession openIdConnectSession = fetchByUserId_First(
			userId, orderByComparator);

		if (openIdConnectSession != null) {
			return openIdConnectSession;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchSessionException(sb.toString());
	}

	/**
	 * Returns the first open ID connect session in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching open ID connect session, or <code>null</code> if a matching open ID connect session could not be found
	 */
	@Override
	public OpenIdConnectSession fetchByUserId_First(
		long userId,
		OrderByComparator<OpenIdConnectSession> orderByComparator) {

		List<OpenIdConnectSession> list = findByUserId(
			userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last open ID connect session in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching open ID connect session
	 * @throws NoSuchSessionException if a matching open ID connect session could not be found
	 */
	@Override
	public OpenIdConnectSession findByUserId_Last(
			long userId,
			OrderByComparator<OpenIdConnectSession> orderByComparator)
		throws NoSuchSessionException {

		OpenIdConnectSession openIdConnectSession = fetchByUserId_Last(
			userId, orderByComparator);

		if (openIdConnectSession != null) {
			return openIdConnectSession;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchSessionException(sb.toString());
	}

	/**
	 * Returns the last open ID connect session in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching open ID connect session, or <code>null</code> if a matching open ID connect session could not be found
	 */
	@Override
	public OpenIdConnectSession fetchByUserId_Last(
		long userId,
		OrderByComparator<OpenIdConnectSession> orderByComparator) {

		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<OpenIdConnectSession> list = findByUserId(
			userId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the open ID connect sessions before and after the current open ID connect session in the ordered set where userId = &#63;.
	 *
	 * @param openIdConnectSessionId the primary key of the current open ID connect session
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next open ID connect session
	 * @throws NoSuchSessionException if a open ID connect session with the primary key could not be found
	 */
	@Override
	public OpenIdConnectSession[] findByUserId_PrevAndNext(
			long openIdConnectSessionId, long userId,
			OrderByComparator<OpenIdConnectSession> orderByComparator)
		throws NoSuchSessionException {

		OpenIdConnectSession openIdConnectSession = findByPrimaryKey(
			openIdConnectSessionId);

		Session session = null;

		try {
			session = openSession();

			OpenIdConnectSession[] array = new OpenIdConnectSessionImpl[3];

			array[0] = getByUserId_PrevAndNext(
				session, openIdConnectSession, userId, orderByComparator, true);

			array[1] = openIdConnectSession;

			array[2] = getByUserId_PrevAndNext(
				session, openIdConnectSession, userId, orderByComparator,
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

	protected OpenIdConnectSession getByUserId_PrevAndNext(
		Session session, OpenIdConnectSession openIdConnectSession, long userId,
		OrderByComparator<OpenIdConnectSession> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_OPENIDCONNECTSESSION_WHERE);

		sb.append(_FINDER_COLUMN_USERID_USERID_2);

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
			sb.append(OpenIdConnectSessionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(userId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						openIdConnectSession)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<OpenIdConnectSession> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the open ID connect sessions where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserId(long userId) {
		for (OpenIdConnectSession openIdConnectSession :
				findByUserId(
					userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(openIdConnectSession);
		}
	}

	/**
	 * Returns the number of open ID connect sessions where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching open ID connect sessions
	 */
	@Override
	public int countByUserId(long userId) {
		FinderPath finderPath = _finderPathCountByUserId;

		Object[] finderArgs = new Object[] {userId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OPENIDCONNECTSESSION_WHERE);

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
		"openIdConnectSession.userId = ?";

	private FinderPath _finderPathWithPaginationFindByConfigurationPid;
	private FinderPath _finderPathWithoutPaginationFindByConfigurationPid;
	private FinderPath _finderPathCountByConfigurationPid;

	/**
	 * Returns all the open ID connect sessions where configurationPid = &#63;.
	 *
	 * @param configurationPid the configuration pid
	 * @return the matching open ID connect sessions
	 */
	@Override
	public List<OpenIdConnectSession> findByConfigurationPid(
		String configurationPid) {

		return findByConfigurationPid(
			configurationPid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the open ID connect sessions where configurationPid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OpenIdConnectSessionModelImpl</code>.
	 * </p>
	 *
	 * @param configurationPid the configuration pid
	 * @param start the lower bound of the range of open ID connect sessions
	 * @param end the upper bound of the range of open ID connect sessions (not inclusive)
	 * @return the range of matching open ID connect sessions
	 */
	@Override
	public List<OpenIdConnectSession> findByConfigurationPid(
		String configurationPid, int start, int end) {

		return findByConfigurationPid(configurationPid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the open ID connect sessions where configurationPid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OpenIdConnectSessionModelImpl</code>.
	 * </p>
	 *
	 * @param configurationPid the configuration pid
	 * @param start the lower bound of the range of open ID connect sessions
	 * @param end the upper bound of the range of open ID connect sessions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching open ID connect sessions
	 */
	@Override
	public List<OpenIdConnectSession> findByConfigurationPid(
		String configurationPid, int start, int end,
		OrderByComparator<OpenIdConnectSession> orderByComparator) {

		return findByConfigurationPid(
			configurationPid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the open ID connect sessions where configurationPid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OpenIdConnectSessionModelImpl</code>.
	 * </p>
	 *
	 * @param configurationPid the configuration pid
	 * @param start the lower bound of the range of open ID connect sessions
	 * @param end the upper bound of the range of open ID connect sessions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching open ID connect sessions
	 */
	@Override
	public List<OpenIdConnectSession> findByConfigurationPid(
		String configurationPid, int start, int end,
		OrderByComparator<OpenIdConnectSession> orderByComparator,
		boolean useFinderCache) {

		configurationPid = Objects.toString(configurationPid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByConfigurationPid;
				finderArgs = new Object[] {configurationPid};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByConfigurationPid;
			finderArgs = new Object[] {
				configurationPid, start, end, orderByComparator
			};
		}

		List<OpenIdConnectSession> list = null;

		if (useFinderCache) {
			list = (List<OpenIdConnectSession>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (OpenIdConnectSession openIdConnectSession : list) {
					if (!configurationPid.equals(
							openIdConnectSession.getConfigurationPid())) {

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

			sb.append(_SQL_SELECT_OPENIDCONNECTSESSION_WHERE);

			boolean bindConfigurationPid = false;

			if (configurationPid.isEmpty()) {
				sb.append(_FINDER_COLUMN_CONFIGURATIONPID_CONFIGURATIONPID_3);
			}
			else {
				bindConfigurationPid = true;

				sb.append(_FINDER_COLUMN_CONFIGURATIONPID_CONFIGURATIONPID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(OpenIdConnectSessionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindConfigurationPid) {
					queryPos.add(configurationPid);
				}

				list = (List<OpenIdConnectSession>)QueryUtil.list(
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
	 * Returns the first open ID connect session in the ordered set where configurationPid = &#63;.
	 *
	 * @param configurationPid the configuration pid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching open ID connect session
	 * @throws NoSuchSessionException if a matching open ID connect session could not be found
	 */
	@Override
	public OpenIdConnectSession findByConfigurationPid_First(
			String configurationPid,
			OrderByComparator<OpenIdConnectSession> orderByComparator)
		throws NoSuchSessionException {

		OpenIdConnectSession openIdConnectSession =
			fetchByConfigurationPid_First(configurationPid, orderByComparator);

		if (openIdConnectSession != null) {
			return openIdConnectSession;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("configurationPid=");
		sb.append(configurationPid);

		sb.append("}");

		throw new NoSuchSessionException(sb.toString());
	}

	/**
	 * Returns the first open ID connect session in the ordered set where configurationPid = &#63;.
	 *
	 * @param configurationPid the configuration pid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching open ID connect session, or <code>null</code> if a matching open ID connect session could not be found
	 */
	@Override
	public OpenIdConnectSession fetchByConfigurationPid_First(
		String configurationPid,
		OrderByComparator<OpenIdConnectSession> orderByComparator) {

		List<OpenIdConnectSession> list = findByConfigurationPid(
			configurationPid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last open ID connect session in the ordered set where configurationPid = &#63;.
	 *
	 * @param configurationPid the configuration pid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching open ID connect session
	 * @throws NoSuchSessionException if a matching open ID connect session could not be found
	 */
	@Override
	public OpenIdConnectSession findByConfigurationPid_Last(
			String configurationPid,
			OrderByComparator<OpenIdConnectSession> orderByComparator)
		throws NoSuchSessionException {

		OpenIdConnectSession openIdConnectSession =
			fetchByConfigurationPid_Last(configurationPid, orderByComparator);

		if (openIdConnectSession != null) {
			return openIdConnectSession;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("configurationPid=");
		sb.append(configurationPid);

		sb.append("}");

		throw new NoSuchSessionException(sb.toString());
	}

	/**
	 * Returns the last open ID connect session in the ordered set where configurationPid = &#63;.
	 *
	 * @param configurationPid the configuration pid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching open ID connect session, or <code>null</code> if a matching open ID connect session could not be found
	 */
	@Override
	public OpenIdConnectSession fetchByConfigurationPid_Last(
		String configurationPid,
		OrderByComparator<OpenIdConnectSession> orderByComparator) {

		int count = countByConfigurationPid(configurationPid);

		if (count == 0) {
			return null;
		}

		List<OpenIdConnectSession> list = findByConfigurationPid(
			configurationPid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the open ID connect sessions before and after the current open ID connect session in the ordered set where configurationPid = &#63;.
	 *
	 * @param openIdConnectSessionId the primary key of the current open ID connect session
	 * @param configurationPid the configuration pid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next open ID connect session
	 * @throws NoSuchSessionException if a open ID connect session with the primary key could not be found
	 */
	@Override
	public OpenIdConnectSession[] findByConfigurationPid_PrevAndNext(
			long openIdConnectSessionId, String configurationPid,
			OrderByComparator<OpenIdConnectSession> orderByComparator)
		throws NoSuchSessionException {

		configurationPid = Objects.toString(configurationPid, "");

		OpenIdConnectSession openIdConnectSession = findByPrimaryKey(
			openIdConnectSessionId);

		Session session = null;

		try {
			session = openSession();

			OpenIdConnectSession[] array = new OpenIdConnectSessionImpl[3];

			array[0] = getByConfigurationPid_PrevAndNext(
				session, openIdConnectSession, configurationPid,
				orderByComparator, true);

			array[1] = openIdConnectSession;

			array[2] = getByConfigurationPid_PrevAndNext(
				session, openIdConnectSession, configurationPid,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected OpenIdConnectSession getByConfigurationPid_PrevAndNext(
		Session session, OpenIdConnectSession openIdConnectSession,
		String configurationPid,
		OrderByComparator<OpenIdConnectSession> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_OPENIDCONNECTSESSION_WHERE);

		boolean bindConfigurationPid = false;

		if (configurationPid.isEmpty()) {
			sb.append(_FINDER_COLUMN_CONFIGURATIONPID_CONFIGURATIONPID_3);
		}
		else {
			bindConfigurationPid = true;

			sb.append(_FINDER_COLUMN_CONFIGURATIONPID_CONFIGURATIONPID_2);
		}

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
			sb.append(OpenIdConnectSessionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindConfigurationPid) {
			queryPos.add(configurationPid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						openIdConnectSession)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<OpenIdConnectSession> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the open ID connect sessions where configurationPid = &#63; from the database.
	 *
	 * @param configurationPid the configuration pid
	 */
	@Override
	public void removeByConfigurationPid(String configurationPid) {
		for (OpenIdConnectSession openIdConnectSession :
				findByConfigurationPid(
					configurationPid, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(openIdConnectSession);
		}
	}

	/**
	 * Returns the number of open ID connect sessions where configurationPid = &#63;.
	 *
	 * @param configurationPid the configuration pid
	 * @return the number of matching open ID connect sessions
	 */
	@Override
	public int countByConfigurationPid(String configurationPid) {
		configurationPid = Objects.toString(configurationPid, "");

		FinderPath finderPath = _finderPathCountByConfigurationPid;

		Object[] finderArgs = new Object[] {configurationPid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OPENIDCONNECTSESSION_WHERE);

			boolean bindConfigurationPid = false;

			if (configurationPid.isEmpty()) {
				sb.append(_FINDER_COLUMN_CONFIGURATIONPID_CONFIGURATIONPID_3);
			}
			else {
				bindConfigurationPid = true;

				sb.append(_FINDER_COLUMN_CONFIGURATIONPID_CONFIGURATIONPID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindConfigurationPid) {
					queryPos.add(configurationPid);
				}

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

	private static final String
		_FINDER_COLUMN_CONFIGURATIONPID_CONFIGURATIONPID_2 =
			"openIdConnectSession.configurationPid = ?";

	private static final String
		_FINDER_COLUMN_CONFIGURATIONPID_CONFIGURATIONPID_3 =
			"(openIdConnectSession.configurationPid IS NULL OR openIdConnectSession.configurationPid = '')";

	private FinderPath _finderPathFetchByU_C;
	private FinderPath _finderPathCountByU_C;

	/**
	 * Returns the open ID connect session where userId = &#63; and configurationPid = &#63; or throws a <code>NoSuchSessionException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @param configurationPid the configuration pid
	 * @return the matching open ID connect session
	 * @throws NoSuchSessionException if a matching open ID connect session could not be found
	 */
	@Override
	public OpenIdConnectSession findByU_C(long userId, String configurationPid)
		throws NoSuchSessionException {

		OpenIdConnectSession openIdConnectSession = fetchByU_C(
			userId, configurationPid);

		if (openIdConnectSession == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("userId=");
			sb.append(userId);

			sb.append(", configurationPid=");
			sb.append(configurationPid);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchSessionException(sb.toString());
		}

		return openIdConnectSession;
	}

	/**
	 * Returns the open ID connect session where userId = &#63; and configurationPid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param configurationPid the configuration pid
	 * @return the matching open ID connect session, or <code>null</code> if a matching open ID connect session could not be found
	 */
	@Override
	public OpenIdConnectSession fetchByU_C(
		long userId, String configurationPid) {

		return fetchByU_C(userId, configurationPid, true);
	}

	/**
	 * Returns the open ID connect session where userId = &#63; and configurationPid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param configurationPid the configuration pid
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching open ID connect session, or <code>null</code> if a matching open ID connect session could not be found
	 */
	@Override
	public OpenIdConnectSession fetchByU_C(
		long userId, String configurationPid, boolean useFinderCache) {

		configurationPid = Objects.toString(configurationPid, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {userId, configurationPid};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByU_C, finderArgs);
		}

		if (result instanceof OpenIdConnectSession) {
			OpenIdConnectSession openIdConnectSession =
				(OpenIdConnectSession)result;

			if ((userId != openIdConnectSession.getUserId()) ||
				!Objects.equals(
					configurationPid,
					openIdConnectSession.getConfigurationPid())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_OPENIDCONNECTSESSION_WHERE);

			sb.append(_FINDER_COLUMN_U_C_USERID_2);

			boolean bindConfigurationPid = false;

			if (configurationPid.isEmpty()) {
				sb.append(_FINDER_COLUMN_U_C_CONFIGURATIONPID_3);
			}
			else {
				bindConfigurationPid = true;

				sb.append(_FINDER_COLUMN_U_C_CONFIGURATIONPID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				if (bindConfigurationPid) {
					queryPos.add(configurationPid);
				}

				List<OpenIdConnectSession> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByU_C, finderArgs, list);
					}
				}
				else {
					OpenIdConnectSession openIdConnectSession = list.get(0);

					result = openIdConnectSession;

					cacheResult(openIdConnectSession);
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
			return (OpenIdConnectSession)result;
		}
	}

	/**
	 * Removes the open ID connect session where userId = &#63; and configurationPid = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param configurationPid the configuration pid
	 * @return the open ID connect session that was removed
	 */
	@Override
	public OpenIdConnectSession removeByU_C(
			long userId, String configurationPid)
		throws NoSuchSessionException {

		OpenIdConnectSession openIdConnectSession = findByU_C(
			userId, configurationPid);

		return remove(openIdConnectSession);
	}

	/**
	 * Returns the number of open ID connect sessions where userId = &#63; and configurationPid = &#63;.
	 *
	 * @param userId the user ID
	 * @param configurationPid the configuration pid
	 * @return the number of matching open ID connect sessions
	 */
	@Override
	public int countByU_C(long userId, String configurationPid) {
		configurationPid = Objects.toString(configurationPid, "");

		FinderPath finderPath = _finderPathCountByU_C;

		Object[] finderArgs = new Object[] {userId, configurationPid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_OPENIDCONNECTSESSION_WHERE);

			sb.append(_FINDER_COLUMN_U_C_USERID_2);

			boolean bindConfigurationPid = false;

			if (configurationPid.isEmpty()) {
				sb.append(_FINDER_COLUMN_U_C_CONFIGURATIONPID_3);
			}
			else {
				bindConfigurationPid = true;

				sb.append(_FINDER_COLUMN_U_C_CONFIGURATIONPID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				if (bindConfigurationPid) {
					queryPos.add(configurationPid);
				}

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

	private static final String _FINDER_COLUMN_U_C_USERID_2 =
		"openIdConnectSession.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_C_CONFIGURATIONPID_2 =
		"openIdConnectSession.configurationPid = ?";

	private static final String _FINDER_COLUMN_U_C_CONFIGURATIONPID_3 =
		"(openIdConnectSession.configurationPid IS NULL OR openIdConnectSession.configurationPid = '')";

	public OpenIdConnectSessionPersistenceImpl() {
		setModelClass(OpenIdConnectSession.class);

		setModelImplClass(OpenIdConnectSessionImpl.class);
		setModelPKClass(long.class);

		setTable(OpenIdConnectSessionTable.INSTANCE);
	}

	/**
	 * Caches the open ID connect session in the entity cache if it is enabled.
	 *
	 * @param openIdConnectSession the open ID connect session
	 */
	@Override
	public void cacheResult(OpenIdConnectSession openIdConnectSession) {
		entityCache.putResult(
			OpenIdConnectSessionImpl.class,
			openIdConnectSession.getPrimaryKey(), openIdConnectSession);

		finderCache.putResult(
			_finderPathFetchByU_C,
			new Object[] {
				openIdConnectSession.getUserId(),
				openIdConnectSession.getConfigurationPid()
			},
			openIdConnectSession);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the open ID connect sessions in the entity cache if it is enabled.
	 *
	 * @param openIdConnectSessions the open ID connect sessions
	 */
	@Override
	public void cacheResult(List<OpenIdConnectSession> openIdConnectSessions) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (openIdConnectSessions.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (OpenIdConnectSession openIdConnectSession :
				openIdConnectSessions) {

			if (entityCache.getResult(
					OpenIdConnectSessionImpl.class,
					openIdConnectSession.getPrimaryKey()) == null) {

				cacheResult(openIdConnectSession);
			}
		}
	}

	/**
	 * Clears the cache for all open ID connect sessions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(OpenIdConnectSessionImpl.class);

		finderCache.clearCache(OpenIdConnectSessionImpl.class);
	}

	/**
	 * Clears the cache for the open ID connect session.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(OpenIdConnectSession openIdConnectSession) {
		entityCache.removeResult(
			OpenIdConnectSessionImpl.class, openIdConnectSession);
	}

	@Override
	public void clearCache(List<OpenIdConnectSession> openIdConnectSessions) {
		for (OpenIdConnectSession openIdConnectSession :
				openIdConnectSessions) {

			entityCache.removeResult(
				OpenIdConnectSessionImpl.class, openIdConnectSession);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(OpenIdConnectSessionImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				OpenIdConnectSessionImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		OpenIdConnectSessionModelImpl openIdConnectSessionModelImpl) {

		Object[] args = new Object[] {
			openIdConnectSessionModelImpl.getUserId(),
			openIdConnectSessionModelImpl.getConfigurationPid()
		};

		finderCache.putResult(_finderPathCountByU_C, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByU_C, args, openIdConnectSessionModelImpl);
	}

	/**
	 * Creates a new open ID connect session with the primary key. Does not add the open ID connect session to the database.
	 *
	 * @param openIdConnectSessionId the primary key for the new open ID connect session
	 * @return the new open ID connect session
	 */
	@Override
	public OpenIdConnectSession create(long openIdConnectSessionId) {
		OpenIdConnectSession openIdConnectSession =
			new OpenIdConnectSessionImpl();

		openIdConnectSession.setNew(true);
		openIdConnectSession.setPrimaryKey(openIdConnectSessionId);

		openIdConnectSession.setCompanyId(CompanyThreadLocal.getCompanyId());

		return openIdConnectSession;
	}

	/**
	 * Removes the open ID connect session with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param openIdConnectSessionId the primary key of the open ID connect session
	 * @return the open ID connect session that was removed
	 * @throws NoSuchSessionException if a open ID connect session with the primary key could not be found
	 */
	@Override
	public OpenIdConnectSession remove(long openIdConnectSessionId)
		throws NoSuchSessionException {

		return remove((Serializable)openIdConnectSessionId);
	}

	/**
	 * Removes the open ID connect session with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the open ID connect session
	 * @return the open ID connect session that was removed
	 * @throws NoSuchSessionException if a open ID connect session with the primary key could not be found
	 */
	@Override
	public OpenIdConnectSession remove(Serializable primaryKey)
		throws NoSuchSessionException {

		Session session = null;

		try {
			session = openSession();

			OpenIdConnectSession openIdConnectSession =
				(OpenIdConnectSession)session.get(
					OpenIdConnectSessionImpl.class, primaryKey);

			if (openIdConnectSession == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchSessionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(openIdConnectSession);
		}
		catch (NoSuchSessionException noSuchEntityException) {
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
	protected OpenIdConnectSession removeImpl(
		OpenIdConnectSession openIdConnectSession) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(openIdConnectSession)) {
				openIdConnectSession = (OpenIdConnectSession)session.get(
					OpenIdConnectSessionImpl.class,
					openIdConnectSession.getPrimaryKeyObj());
			}

			if (openIdConnectSession != null) {
				session.delete(openIdConnectSession);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (openIdConnectSession != null) {
			clearCache(openIdConnectSession);
		}

		return openIdConnectSession;
	}

	@Override
	public OpenIdConnectSession updateImpl(
		OpenIdConnectSession openIdConnectSession) {

		boolean isNew = openIdConnectSession.isNew();

		if (!(openIdConnectSession instanceof OpenIdConnectSessionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(openIdConnectSession.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					openIdConnectSession);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in openIdConnectSession proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom OpenIdConnectSession implementation " +
					openIdConnectSession.getClass());
		}

		OpenIdConnectSessionModelImpl openIdConnectSessionModelImpl =
			(OpenIdConnectSessionModelImpl)openIdConnectSession;

		if (!openIdConnectSessionModelImpl.hasSetModifiedDate()) {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			Date date = new Date();

			if (serviceContext == null) {
				openIdConnectSession.setModifiedDate(date);
			}
			else {
				openIdConnectSession.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(openIdConnectSession);
			}
			else {
				openIdConnectSession = (OpenIdConnectSession)session.merge(
					openIdConnectSession);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			OpenIdConnectSessionImpl.class, openIdConnectSessionModelImpl,
			false, true);

		cacheUniqueFindersCache(openIdConnectSessionModelImpl);

		if (isNew) {
			openIdConnectSession.setNew(false);
		}

		openIdConnectSession.resetOriginalValues();

		return openIdConnectSession;
	}

	/**
	 * Returns the open ID connect session with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the open ID connect session
	 * @return the open ID connect session
	 * @throws NoSuchSessionException if a open ID connect session with the primary key could not be found
	 */
	@Override
	public OpenIdConnectSession findByPrimaryKey(Serializable primaryKey)
		throws NoSuchSessionException {

		OpenIdConnectSession openIdConnectSession = fetchByPrimaryKey(
			primaryKey);

		if (openIdConnectSession == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchSessionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return openIdConnectSession;
	}

	/**
	 * Returns the open ID connect session with the primary key or throws a <code>NoSuchSessionException</code> if it could not be found.
	 *
	 * @param openIdConnectSessionId the primary key of the open ID connect session
	 * @return the open ID connect session
	 * @throws NoSuchSessionException if a open ID connect session with the primary key could not be found
	 */
	@Override
	public OpenIdConnectSession findByPrimaryKey(long openIdConnectSessionId)
		throws NoSuchSessionException {

		return findByPrimaryKey((Serializable)openIdConnectSessionId);
	}

	/**
	 * Returns the open ID connect session with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param openIdConnectSessionId the primary key of the open ID connect session
	 * @return the open ID connect session, or <code>null</code> if a open ID connect session with the primary key could not be found
	 */
	@Override
	public OpenIdConnectSession fetchByPrimaryKey(long openIdConnectSessionId) {
		return fetchByPrimaryKey((Serializable)openIdConnectSessionId);
	}

	/**
	 * Returns all the open ID connect sessions.
	 *
	 * @return the open ID connect sessions
	 */
	@Override
	public List<OpenIdConnectSession> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the open ID connect sessions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OpenIdConnectSessionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of open ID connect sessions
	 * @param end the upper bound of the range of open ID connect sessions (not inclusive)
	 * @return the range of open ID connect sessions
	 */
	@Override
	public List<OpenIdConnectSession> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the open ID connect sessions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OpenIdConnectSessionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of open ID connect sessions
	 * @param end the upper bound of the range of open ID connect sessions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of open ID connect sessions
	 */
	@Override
	public List<OpenIdConnectSession> findAll(
		int start, int end,
		OrderByComparator<OpenIdConnectSession> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the open ID connect sessions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OpenIdConnectSessionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of open ID connect sessions
	 * @param end the upper bound of the range of open ID connect sessions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of open ID connect sessions
	 */
	@Override
	public List<OpenIdConnectSession> findAll(
		int start, int end,
		OrderByComparator<OpenIdConnectSession> orderByComparator,
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

		List<OpenIdConnectSession> list = null;

		if (useFinderCache) {
			list = (List<OpenIdConnectSession>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_OPENIDCONNECTSESSION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_OPENIDCONNECTSESSION;

				sql = sql.concat(OpenIdConnectSessionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<OpenIdConnectSession>)QueryUtil.list(
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
	 * Removes all the open ID connect sessions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (OpenIdConnectSession openIdConnectSession : findAll()) {
			remove(openIdConnectSession);
		}
	}

	/**
	 * Returns the number of open ID connect sessions.
	 *
	 * @return the number of open ID connect sessions
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_OPENIDCONNECTSESSION);

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
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "openIdConnectSessionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_OPENIDCONNECTSESSION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return OpenIdConnectSessionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the open ID connect session persistence.
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

		_finderPathWithPaginationFindByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"userId"}, true);

		_finderPathWithoutPaginationFindByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] {Long.class.getName()}, new String[] {"userId"}, true);

		_finderPathCountByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] {Long.class.getName()}, new String[] {"userId"},
			false);

		_finderPathWithPaginationFindByConfigurationPid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByConfigurationPid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"configurationPid"}, true);

		_finderPathWithoutPaginationFindByConfigurationPid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByConfigurationPid",
			new String[] {String.class.getName()},
			new String[] {"configurationPid"}, true);

		_finderPathCountByConfigurationPid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByConfigurationPid", new String[] {String.class.getName()},
			new String[] {"configurationPid"}, false);

		_finderPathFetchByU_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByU_C",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"userId", "configurationPid"}, true);

		_finderPathCountByU_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_C",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"userId", "configurationPid"}, false);

		_setOpenIdConnectSessionUtilPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		_setOpenIdConnectSessionUtilPersistence(null);

		entityCache.removeCache(OpenIdConnectSessionImpl.class.getName());
	}

	private void _setOpenIdConnectSessionUtilPersistence(
		OpenIdConnectSessionPersistence openIdConnectSessionPersistence) {

		try {
			Field field = OpenIdConnectSessionUtil.class.getDeclaredField(
				"_persistence");

			field.setAccessible(true);

			field.set(null, openIdConnectSessionPersistence);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	@Override
	@Reference(
		target = OpenIdConnectPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = OpenIdConnectPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = OpenIdConnectPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_OPENIDCONNECTSESSION =
		"SELECT openIdConnectSession FROM OpenIdConnectSession openIdConnectSession";

	private static final String _SQL_SELECT_OPENIDCONNECTSESSION_WHERE =
		"SELECT openIdConnectSession FROM OpenIdConnectSession openIdConnectSession WHERE ";

	private static final String _SQL_COUNT_OPENIDCONNECTSESSION =
		"SELECT COUNT(openIdConnectSession) FROM OpenIdConnectSession openIdConnectSession";

	private static final String _SQL_COUNT_OPENIDCONNECTSESSION_WHERE =
		"SELECT COUNT(openIdConnectSession) FROM OpenIdConnectSession openIdConnectSession WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"openIdConnectSession.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No OpenIdConnectSession exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No OpenIdConnectSession exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		OpenIdConnectSessionPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private OpenIdConnectSessionModelArgumentsResolver
		_openIdConnectSessionModelArgumentsResolver;

}