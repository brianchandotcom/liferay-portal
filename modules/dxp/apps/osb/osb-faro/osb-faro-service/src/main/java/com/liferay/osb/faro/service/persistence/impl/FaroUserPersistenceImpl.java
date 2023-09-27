/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.service.persistence.impl;

import com.liferay.osb.faro.exception.NoSuchFaroUserException;
import com.liferay.osb.faro.model.FaroUser;
import com.liferay.osb.faro.model.FaroUserTable;
import com.liferay.osb.faro.model.impl.FaroUserImpl;
import com.liferay.osb.faro.model.impl.FaroUserModelImpl;
import com.liferay.osb.faro.service.persistence.FaroUserPersistence;
import com.liferay.osb.faro.service.persistence.FaroUserUtil;
import com.liferay.osb.faro.service.persistence.impl.constants.OSBFaroPersistenceConstants;
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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.HashMap;
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
 * The persistence implementation for the faro user service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Matthew Kong
 * @generated
 */
@Component(service = FaroUserPersistence.class)
public class FaroUserPersistenceImpl
	extends BasePersistenceImpl<FaroUser> implements FaroUserPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>FaroUserUtil</code> to access the faro user persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		FaroUserImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the faro users where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching faro users
	 */
	@Override
	public List<FaroUser> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the faro users where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroUserModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of faro users
	 * @param end the upper bound of the range of faro users (not inclusive)
	 * @return the range of matching faro users
	 */
	@Override
	public List<FaroUser> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the faro users where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroUserModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of faro users
	 * @param end the upper bound of the range of faro users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching faro users
	 */
	@Override
	public List<FaroUser> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<FaroUser> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the faro users where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroUserModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of faro users
	 * @param end the upper bound of the range of faro users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching faro users
	 */
	@Override
	public List<FaroUser> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<FaroUser> orderByComparator, boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByGroupId;
				finderArgs = new Object[] {groupId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByGroupId;
			finderArgs = new Object[] {groupId, start, end, orderByComparator};
		}

		List<FaroUser> list = null;

		if (useFinderCache) {
			list = (List<FaroUser>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FaroUser faroUser : list) {
					if (groupId != faroUser.getGroupId()) {
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

			sb.append(_SQL_SELECT_FAROUSER_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FaroUserModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<FaroUser>)QueryUtil.list(
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
	 * Returns the first faro user in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro user
	 * @throws NoSuchFaroUserException if a matching faro user could not be found
	 */
	@Override
	public FaroUser findByGroupId_First(
			long groupId, OrderByComparator<FaroUser> orderByComparator)
		throws NoSuchFaroUserException {

		FaroUser faroUser = fetchByGroupId_First(groupId, orderByComparator);

		if (faroUser != null) {
			return faroUser;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchFaroUserException(sb.toString());
	}

	/**
	 * Returns the first faro user in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro user, or <code>null</code> if a matching faro user could not be found
	 */
	@Override
	public FaroUser fetchByGroupId_First(
		long groupId, OrderByComparator<FaroUser> orderByComparator) {

		List<FaroUser> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last faro user in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro user
	 * @throws NoSuchFaroUserException if a matching faro user could not be found
	 */
	@Override
	public FaroUser findByGroupId_Last(
			long groupId, OrderByComparator<FaroUser> orderByComparator)
		throws NoSuchFaroUserException {

		FaroUser faroUser = fetchByGroupId_Last(groupId, orderByComparator);

		if (faroUser != null) {
			return faroUser;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchFaroUserException(sb.toString());
	}

	/**
	 * Returns the last faro user in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro user, or <code>null</code> if a matching faro user could not be found
	 */
	@Override
	public FaroUser fetchByGroupId_Last(
		long groupId, OrderByComparator<FaroUser> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<FaroUser> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the faro users before and after the current faro user in the ordered set where groupId = &#63;.
	 *
	 * @param faroUserId the primary key of the current faro user
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next faro user
	 * @throws NoSuchFaroUserException if a faro user with the primary key could not be found
	 */
	@Override
	public FaroUser[] findByGroupId_PrevAndNext(
			long faroUserId, long groupId,
			OrderByComparator<FaroUser> orderByComparator)
		throws NoSuchFaroUserException {

		FaroUser faroUser = findByPrimaryKey(faroUserId);

		Session session = null;

		try {
			session = openSession();

			FaroUser[] array = new FaroUserImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, faroUser, groupId, orderByComparator, true);

			array[1] = faroUser;

			array[2] = getByGroupId_PrevAndNext(
				session, faroUser, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FaroUser getByGroupId_PrevAndNext(
		Session session, FaroUser faroUser, long groupId,
		OrderByComparator<FaroUser> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_FAROUSER_WHERE);

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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
			sb.append(FaroUserModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(faroUser)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FaroUser> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the faro users where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (FaroUser faroUser :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(faroUser);
		}
	}

	/**
	 * Returns the number of faro users where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching faro users
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] {groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_FAROUSER_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"faroUser.groupId = ?";

	private FinderPath _finderPathFetchByKey;
	private FinderPath _finderPathCountByKey;

	/**
	 * Returns the faro user where key = &#63; or throws a <code>NoSuchFaroUserException</code> if it could not be found.
	 *
	 * @param key the key
	 * @return the matching faro user
	 * @throws NoSuchFaroUserException if a matching faro user could not be found
	 */
	@Override
	public FaroUser findByKey(String key) throws NoSuchFaroUserException {
		FaroUser faroUser = fetchByKey(key);

		if (faroUser == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("key=");
			sb.append(key);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchFaroUserException(sb.toString());
		}

		return faroUser;
	}

	/**
	 * Returns the faro user where key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param key the key
	 * @return the matching faro user, or <code>null</code> if a matching faro user could not be found
	 */
	@Override
	public FaroUser fetchByKey(String key) {
		return fetchByKey(key, true);
	}

	/**
	 * Returns the faro user where key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param key the key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching faro user, or <code>null</code> if a matching faro user could not be found
	 */
	@Override
	public FaroUser fetchByKey(String key, boolean useFinderCache) {
		key = Objects.toString(key, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {key};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByKey, finderArgs, this);
		}

		if (result instanceof FaroUser) {
			FaroUser faroUser = (FaroUser)result;

			if (!Objects.equals(key, faroUser.getKey())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_FAROUSER_WHERE);

			boolean bindKey = false;

			if (key.isEmpty()) {
				sb.append(_FINDER_COLUMN_KEY_KEY_3);
			}
			else {
				bindKey = true;

				sb.append(_FINDER_COLUMN_KEY_KEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindKey) {
					queryPos.add(key);
				}

				List<FaroUser> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByKey, finderArgs, list);
					}
				}
				else {
					FaroUser faroUser = list.get(0);

					result = faroUser;

					cacheResult(faroUser);
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
			return (FaroUser)result;
		}
	}

	/**
	 * Removes the faro user where key = &#63; from the database.
	 *
	 * @param key the key
	 * @return the faro user that was removed
	 */
	@Override
	public FaroUser removeByKey(String key) throws NoSuchFaroUserException {
		FaroUser faroUser = findByKey(key);

		return remove(faroUser);
	}

	/**
	 * Returns the number of faro users where key = &#63;.
	 *
	 * @param key the key
	 * @return the number of matching faro users
	 */
	@Override
	public int countByKey(String key) {
		key = Objects.toString(key, "");

		FinderPath finderPath = _finderPathCountByKey;

		Object[] finderArgs = new Object[] {key};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_FAROUSER_WHERE);

			boolean bindKey = false;

			if (key.isEmpty()) {
				sb.append(_FINDER_COLUMN_KEY_KEY_3);
			}
			else {
				bindKey = true;

				sb.append(_FINDER_COLUMN_KEY_KEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindKey) {
					queryPos.add(key);
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

	private static final String _FINDER_COLUMN_KEY_KEY_2 = "faroUser.key = ?";

	private static final String _FINDER_COLUMN_KEY_KEY_3 =
		"(faroUser.key IS NULL OR faroUser.key = '')";

	private FinderPath _finderPathFetchByG_L;
	private FinderPath _finderPathCountByG_L;

	/**
	 * Returns the faro user where groupId = &#63; and liveUserId = &#63; or throws a <code>NoSuchFaroUserException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param liveUserId the live user ID
	 * @return the matching faro user
	 * @throws NoSuchFaroUserException if a matching faro user could not be found
	 */
	@Override
	public FaroUser findByG_L(long groupId, long liveUserId)
		throws NoSuchFaroUserException {

		FaroUser faroUser = fetchByG_L(groupId, liveUserId);

		if (faroUser == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", liveUserId=");
			sb.append(liveUserId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchFaroUserException(sb.toString());
		}

		return faroUser;
	}

	/**
	 * Returns the faro user where groupId = &#63; and liveUserId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param liveUserId the live user ID
	 * @return the matching faro user, or <code>null</code> if a matching faro user could not be found
	 */
	@Override
	public FaroUser fetchByG_L(long groupId, long liveUserId) {
		return fetchByG_L(groupId, liveUserId, true);
	}

	/**
	 * Returns the faro user where groupId = &#63; and liveUserId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param liveUserId the live user ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching faro user, or <code>null</code> if a matching faro user could not be found
	 */
	@Override
	public FaroUser fetchByG_L(
		long groupId, long liveUserId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {groupId, liveUserId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByG_L, finderArgs, this);
		}

		if (result instanceof FaroUser) {
			FaroUser faroUser = (FaroUser)result;

			if ((groupId != faroUser.getGroupId()) ||
				(liveUserId != faroUser.getLiveUserId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_FAROUSER_WHERE);

			sb.append(_FINDER_COLUMN_G_L_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_L_LIVEUSERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(liveUserId);

				List<FaroUser> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByG_L, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {groupId, liveUserId};
							}

							_log.warn(
								"FaroUserPersistenceImpl.fetchByG_L(long, long, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					FaroUser faroUser = list.get(0);

					result = faroUser;

					cacheResult(faroUser);
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
			return (FaroUser)result;
		}
	}

	/**
	 * Removes the faro user where groupId = &#63; and liveUserId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param liveUserId the live user ID
	 * @return the faro user that was removed
	 */
	@Override
	public FaroUser removeByG_L(long groupId, long liveUserId)
		throws NoSuchFaroUserException {

		FaroUser faroUser = findByG_L(groupId, liveUserId);

		return remove(faroUser);
	}

	/**
	 * Returns the number of faro users where groupId = &#63; and liveUserId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param liveUserId the live user ID
	 * @return the number of matching faro users
	 */
	@Override
	public int countByG_L(long groupId, long liveUserId) {
		FinderPath finderPath = _finderPathCountByG_L;

		Object[] finderArgs = new Object[] {groupId, liveUserId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_FAROUSER_WHERE);

			sb.append(_FINDER_COLUMN_G_L_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_L_LIVEUSERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(liveUserId);

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

	private static final String _FINDER_COLUMN_G_L_GROUPID_2 =
		"faroUser.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_L_LIVEUSERID_2 =
		"faroUser.liveUserId = ?";

	private FinderPath _finderPathWithPaginationFindByG_R;
	private FinderPath _finderPathWithoutPaginationFindByG_R;
	private FinderPath _finderPathCountByG_R;

	/**
	 * Returns all the faro users where groupId = &#63; and roleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @return the matching faro users
	 */
	@Override
	public List<FaroUser> findByG_R(long groupId, long roleId) {
		return findByG_R(
			groupId, roleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the faro users where groupId = &#63; and roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroUserModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param start the lower bound of the range of faro users
	 * @param end the upper bound of the range of faro users (not inclusive)
	 * @return the range of matching faro users
	 */
	@Override
	public List<FaroUser> findByG_R(
		long groupId, long roleId, int start, int end) {

		return findByG_R(groupId, roleId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the faro users where groupId = &#63; and roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroUserModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param start the lower bound of the range of faro users
	 * @param end the upper bound of the range of faro users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching faro users
	 */
	@Override
	public List<FaroUser> findByG_R(
		long groupId, long roleId, int start, int end,
		OrderByComparator<FaroUser> orderByComparator) {

		return findByG_R(groupId, roleId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the faro users where groupId = &#63; and roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroUserModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param start the lower bound of the range of faro users
	 * @param end the upper bound of the range of faro users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching faro users
	 */
	@Override
	public List<FaroUser> findByG_R(
		long groupId, long roleId, int start, int end,
		OrderByComparator<FaroUser> orderByComparator, boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_R;
				finderArgs = new Object[] {groupId, roleId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_R;
			finderArgs = new Object[] {
				groupId, roleId, start, end, orderByComparator
			};
		}

		List<FaroUser> list = null;

		if (useFinderCache) {
			list = (List<FaroUser>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FaroUser faroUser : list) {
					if ((groupId != faroUser.getGroupId()) ||
						(roleId != faroUser.getRoleId())) {

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

			sb.append(_SQL_SELECT_FAROUSER_WHERE);

			sb.append(_FINDER_COLUMN_G_R_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_R_ROLEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FaroUserModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(roleId);

				list = (List<FaroUser>)QueryUtil.list(
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
	 * Returns the first faro user in the ordered set where groupId = &#63; and roleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro user
	 * @throws NoSuchFaroUserException if a matching faro user could not be found
	 */
	@Override
	public FaroUser findByG_R_First(
			long groupId, long roleId,
			OrderByComparator<FaroUser> orderByComparator)
		throws NoSuchFaroUserException {

		FaroUser faroUser = fetchByG_R_First(
			groupId, roleId, orderByComparator);

		if (faroUser != null) {
			return faroUser;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", roleId=");
		sb.append(roleId);

		sb.append("}");

		throw new NoSuchFaroUserException(sb.toString());
	}

	/**
	 * Returns the first faro user in the ordered set where groupId = &#63; and roleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro user, or <code>null</code> if a matching faro user could not be found
	 */
	@Override
	public FaroUser fetchByG_R_First(
		long groupId, long roleId,
		OrderByComparator<FaroUser> orderByComparator) {

		List<FaroUser> list = findByG_R(
			groupId, roleId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last faro user in the ordered set where groupId = &#63; and roleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro user
	 * @throws NoSuchFaroUserException if a matching faro user could not be found
	 */
	@Override
	public FaroUser findByG_R_Last(
			long groupId, long roleId,
			OrderByComparator<FaroUser> orderByComparator)
		throws NoSuchFaroUserException {

		FaroUser faroUser = fetchByG_R_Last(groupId, roleId, orderByComparator);

		if (faroUser != null) {
			return faroUser;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", roleId=");
		sb.append(roleId);

		sb.append("}");

		throw new NoSuchFaroUserException(sb.toString());
	}

	/**
	 * Returns the last faro user in the ordered set where groupId = &#63; and roleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro user, or <code>null</code> if a matching faro user could not be found
	 */
	@Override
	public FaroUser fetchByG_R_Last(
		long groupId, long roleId,
		OrderByComparator<FaroUser> orderByComparator) {

		int count = countByG_R(groupId, roleId);

		if (count == 0) {
			return null;
		}

		List<FaroUser> list = findByG_R(
			groupId, roleId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the faro users before and after the current faro user in the ordered set where groupId = &#63; and roleId = &#63;.
	 *
	 * @param faroUserId the primary key of the current faro user
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next faro user
	 * @throws NoSuchFaroUserException if a faro user with the primary key could not be found
	 */
	@Override
	public FaroUser[] findByG_R_PrevAndNext(
			long faroUserId, long groupId, long roleId,
			OrderByComparator<FaroUser> orderByComparator)
		throws NoSuchFaroUserException {

		FaroUser faroUser = findByPrimaryKey(faroUserId);

		Session session = null;

		try {
			session = openSession();

			FaroUser[] array = new FaroUserImpl[3];

			array[0] = getByG_R_PrevAndNext(
				session, faroUser, groupId, roleId, orderByComparator, true);

			array[1] = faroUser;

			array[2] = getByG_R_PrevAndNext(
				session, faroUser, groupId, roleId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FaroUser getByG_R_PrevAndNext(
		Session session, FaroUser faroUser, long groupId, long roleId,
		OrderByComparator<FaroUser> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_FAROUSER_WHERE);

		sb.append(_FINDER_COLUMN_G_R_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_R_ROLEID_2);

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
			sb.append(FaroUserModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(roleId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(faroUser)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FaroUser> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the faro users where groupId = &#63; and roleId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 */
	@Override
	public void removeByG_R(long groupId, long roleId) {
		for (FaroUser faroUser :
				findByG_R(
					groupId, roleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(faroUser);
		}
	}

	/**
	 * Returns the number of faro users where groupId = &#63; and roleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @return the number of matching faro users
	 */
	@Override
	public int countByG_R(long groupId, long roleId) {
		FinderPath finderPath = _finderPathCountByG_R;

		Object[] finderArgs = new Object[] {groupId, roleId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_FAROUSER_WHERE);

			sb.append(_FINDER_COLUMN_G_R_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_R_ROLEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(roleId);

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

	private static final String _FINDER_COLUMN_G_R_GROUPID_2 =
		"faroUser.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_R_ROLEID_2 =
		"faroUser.roleId = ?";

	private FinderPath _finderPathFetchByG_E;
	private FinderPath _finderPathCountByG_E;

	/**
	 * Returns the faro user where groupId = &#63; and emailAddress = &#63; or throws a <code>NoSuchFaroUserException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param emailAddress the email address
	 * @return the matching faro user
	 * @throws NoSuchFaroUserException if a matching faro user could not be found
	 */
	@Override
	public FaroUser findByG_E(long groupId, String emailAddress)
		throws NoSuchFaroUserException {

		FaroUser faroUser = fetchByG_E(groupId, emailAddress);

		if (faroUser == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", emailAddress=");
			sb.append(emailAddress);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchFaroUserException(sb.toString());
		}

		return faroUser;
	}

	/**
	 * Returns the faro user where groupId = &#63; and emailAddress = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param emailAddress the email address
	 * @return the matching faro user, or <code>null</code> if a matching faro user could not be found
	 */
	@Override
	public FaroUser fetchByG_E(long groupId, String emailAddress) {
		return fetchByG_E(groupId, emailAddress, true);
	}

	/**
	 * Returns the faro user where groupId = &#63; and emailAddress = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param emailAddress the email address
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching faro user, or <code>null</code> if a matching faro user could not be found
	 */
	@Override
	public FaroUser fetchByG_E(
		long groupId, String emailAddress, boolean useFinderCache) {

		emailAddress = Objects.toString(emailAddress, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {groupId, emailAddress};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByG_E, finderArgs, this);
		}

		if (result instanceof FaroUser) {
			FaroUser faroUser = (FaroUser)result;

			if ((groupId != faroUser.getGroupId()) ||
				!Objects.equals(emailAddress, faroUser.getEmailAddress())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_FAROUSER_WHERE);

			sb.append(_FINDER_COLUMN_G_E_GROUPID_2);

			boolean bindEmailAddress = false;

			if (emailAddress.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_E_EMAILADDRESS_3);
			}
			else {
				bindEmailAddress = true;

				sb.append(_FINDER_COLUMN_G_E_EMAILADDRESS_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindEmailAddress) {
					queryPos.add(emailAddress);
				}

				List<FaroUser> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByG_E, finderArgs, list);
					}
				}
				else {
					FaroUser faroUser = list.get(0);

					result = faroUser;

					cacheResult(faroUser);
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
			return (FaroUser)result;
		}
	}

	/**
	 * Removes the faro user where groupId = &#63; and emailAddress = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param emailAddress the email address
	 * @return the faro user that was removed
	 */
	@Override
	public FaroUser removeByG_E(long groupId, String emailAddress)
		throws NoSuchFaroUserException {

		FaroUser faroUser = findByG_E(groupId, emailAddress);

		return remove(faroUser);
	}

	/**
	 * Returns the number of faro users where groupId = &#63; and emailAddress = &#63;.
	 *
	 * @param groupId the group ID
	 * @param emailAddress the email address
	 * @return the number of matching faro users
	 */
	@Override
	public int countByG_E(long groupId, String emailAddress) {
		emailAddress = Objects.toString(emailAddress, "");

		FinderPath finderPath = _finderPathCountByG_E;

		Object[] finderArgs = new Object[] {groupId, emailAddress};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_FAROUSER_WHERE);

			sb.append(_FINDER_COLUMN_G_E_GROUPID_2);

			boolean bindEmailAddress = false;

			if (emailAddress.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_E_EMAILADDRESS_3);
			}
			else {
				bindEmailAddress = true;

				sb.append(_FINDER_COLUMN_G_E_EMAILADDRESS_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindEmailAddress) {
					queryPos.add(emailAddress);
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

	private static final String _FINDER_COLUMN_G_E_GROUPID_2 =
		"faroUser.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_E_EMAILADDRESS_2 =
		"faroUser.emailAddress = ?";

	private static final String _FINDER_COLUMN_G_E_EMAILADDRESS_3 =
		"(faroUser.emailAddress IS NULL OR faroUser.emailAddress = '')";

	private FinderPath _finderPathWithPaginationFindByG_S;
	private FinderPath _finderPathWithoutPaginationFindByG_S;
	private FinderPath _finderPathCountByG_S;

	/**
	 * Returns all the faro users where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching faro users
	 */
	@Override
	public List<FaroUser> findByG_S(long groupId, int status) {
		return findByG_S(
			groupId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the faro users where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroUserModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of faro users
	 * @param end the upper bound of the range of faro users (not inclusive)
	 * @return the range of matching faro users
	 */
	@Override
	public List<FaroUser> findByG_S(
		long groupId, int status, int start, int end) {

		return findByG_S(groupId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the faro users where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroUserModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of faro users
	 * @param end the upper bound of the range of faro users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching faro users
	 */
	@Override
	public List<FaroUser> findByG_S(
		long groupId, int status, int start, int end,
		OrderByComparator<FaroUser> orderByComparator) {

		return findByG_S(groupId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the faro users where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroUserModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of faro users
	 * @param end the upper bound of the range of faro users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching faro users
	 */
	@Override
	public List<FaroUser> findByG_S(
		long groupId, int status, int start, int end,
		OrderByComparator<FaroUser> orderByComparator, boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_S;
				finderArgs = new Object[] {groupId, status};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_S;
			finderArgs = new Object[] {
				groupId, status, start, end, orderByComparator
			};
		}

		List<FaroUser> list = null;

		if (useFinderCache) {
			list = (List<FaroUser>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FaroUser faroUser : list) {
					if ((groupId != faroUser.getGroupId()) ||
						(status != faroUser.getStatus())) {

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

			sb.append(_SQL_SELECT_FAROUSER_WHERE);

			sb.append(_FINDER_COLUMN_G_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FaroUserModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(status);

				list = (List<FaroUser>)QueryUtil.list(
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
	 * Returns the first faro user in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro user
	 * @throws NoSuchFaroUserException if a matching faro user could not be found
	 */
	@Override
	public FaroUser findByG_S_First(
			long groupId, int status,
			OrderByComparator<FaroUser> orderByComparator)
		throws NoSuchFaroUserException {

		FaroUser faroUser = fetchByG_S_First(
			groupId, status, orderByComparator);

		if (faroUser != null) {
			return faroUser;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchFaroUserException(sb.toString());
	}

	/**
	 * Returns the first faro user in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro user, or <code>null</code> if a matching faro user could not be found
	 */
	@Override
	public FaroUser fetchByG_S_First(
		long groupId, int status,
		OrderByComparator<FaroUser> orderByComparator) {

		List<FaroUser> list = findByG_S(
			groupId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last faro user in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro user
	 * @throws NoSuchFaroUserException if a matching faro user could not be found
	 */
	@Override
	public FaroUser findByG_S_Last(
			long groupId, int status,
			OrderByComparator<FaroUser> orderByComparator)
		throws NoSuchFaroUserException {

		FaroUser faroUser = fetchByG_S_Last(groupId, status, orderByComparator);

		if (faroUser != null) {
			return faroUser;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchFaroUserException(sb.toString());
	}

	/**
	 * Returns the last faro user in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro user, or <code>null</code> if a matching faro user could not be found
	 */
	@Override
	public FaroUser fetchByG_S_Last(
		long groupId, int status,
		OrderByComparator<FaroUser> orderByComparator) {

		int count = countByG_S(groupId, status);

		if (count == 0) {
			return null;
		}

		List<FaroUser> list = findByG_S(
			groupId, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the faro users before and after the current faro user in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param faroUserId the primary key of the current faro user
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next faro user
	 * @throws NoSuchFaroUserException if a faro user with the primary key could not be found
	 */
	@Override
	public FaroUser[] findByG_S_PrevAndNext(
			long faroUserId, long groupId, int status,
			OrderByComparator<FaroUser> orderByComparator)
		throws NoSuchFaroUserException {

		FaroUser faroUser = findByPrimaryKey(faroUserId);

		Session session = null;

		try {
			session = openSession();

			FaroUser[] array = new FaroUserImpl[3];

			array[0] = getByG_S_PrevAndNext(
				session, faroUser, groupId, status, orderByComparator, true);

			array[1] = faroUser;

			array[2] = getByG_S_PrevAndNext(
				session, faroUser, groupId, status, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FaroUser getByG_S_PrevAndNext(
		Session session, FaroUser faroUser, long groupId, int status,
		OrderByComparator<FaroUser> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_FAROUSER_WHERE);

		sb.append(_FINDER_COLUMN_G_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_S_STATUS_2);

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
			sb.append(FaroUserModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(faroUser)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FaroUser> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the faro users where groupId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 */
	@Override
	public void removeByG_S(long groupId, int status) {
		for (FaroUser faroUser :
				findByG_S(
					groupId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(faroUser);
		}
	}

	/**
	 * Returns the number of faro users where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching faro users
	 */
	@Override
	public int countByG_S(long groupId, int status) {
		FinderPath finderPath = _finderPathCountByG_S;

		Object[] finderArgs = new Object[] {groupId, status};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_FAROUSER_WHERE);

			sb.append(_FINDER_COLUMN_G_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(status);

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

	private static final String _FINDER_COLUMN_G_S_GROUPID_2 =
		"faroUser.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_S_STATUS_2 =
		"faroUser.status = ?";

	private FinderPath _finderPathWithPaginationFindByL_S;
	private FinderPath _finderPathWithoutPaginationFindByL_S;
	private FinderPath _finderPathCountByL_S;

	/**
	 * Returns all the faro users where liveUserId = &#63; and status = &#63;.
	 *
	 * @param liveUserId the live user ID
	 * @param status the status
	 * @return the matching faro users
	 */
	@Override
	public List<FaroUser> findByL_S(long liveUserId, int status) {
		return findByL_S(
			liveUserId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the faro users where liveUserId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroUserModelImpl</code>.
	 * </p>
	 *
	 * @param liveUserId the live user ID
	 * @param status the status
	 * @param start the lower bound of the range of faro users
	 * @param end the upper bound of the range of faro users (not inclusive)
	 * @return the range of matching faro users
	 */
	@Override
	public List<FaroUser> findByL_S(
		long liveUserId, int status, int start, int end) {

		return findByL_S(liveUserId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the faro users where liveUserId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroUserModelImpl</code>.
	 * </p>
	 *
	 * @param liveUserId the live user ID
	 * @param status the status
	 * @param start the lower bound of the range of faro users
	 * @param end the upper bound of the range of faro users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching faro users
	 */
	@Override
	public List<FaroUser> findByL_S(
		long liveUserId, int status, int start, int end,
		OrderByComparator<FaroUser> orderByComparator) {

		return findByL_S(
			liveUserId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the faro users where liveUserId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroUserModelImpl</code>.
	 * </p>
	 *
	 * @param liveUserId the live user ID
	 * @param status the status
	 * @param start the lower bound of the range of faro users
	 * @param end the upper bound of the range of faro users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching faro users
	 */
	@Override
	public List<FaroUser> findByL_S(
		long liveUserId, int status, int start, int end,
		OrderByComparator<FaroUser> orderByComparator, boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByL_S;
				finderArgs = new Object[] {liveUserId, status};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByL_S;
			finderArgs = new Object[] {
				liveUserId, status, start, end, orderByComparator
			};
		}

		List<FaroUser> list = null;

		if (useFinderCache) {
			list = (List<FaroUser>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FaroUser faroUser : list) {
					if ((liveUserId != faroUser.getLiveUserId()) ||
						(status != faroUser.getStatus())) {

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

			sb.append(_SQL_SELECT_FAROUSER_WHERE);

			sb.append(_FINDER_COLUMN_L_S_LIVEUSERID_2);

			sb.append(_FINDER_COLUMN_L_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FaroUserModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(liveUserId);

				queryPos.add(status);

				list = (List<FaroUser>)QueryUtil.list(
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
	 * Returns the first faro user in the ordered set where liveUserId = &#63; and status = &#63;.
	 *
	 * @param liveUserId the live user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro user
	 * @throws NoSuchFaroUserException if a matching faro user could not be found
	 */
	@Override
	public FaroUser findByL_S_First(
			long liveUserId, int status,
			OrderByComparator<FaroUser> orderByComparator)
		throws NoSuchFaroUserException {

		FaroUser faroUser = fetchByL_S_First(
			liveUserId, status, orderByComparator);

		if (faroUser != null) {
			return faroUser;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("liveUserId=");
		sb.append(liveUserId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchFaroUserException(sb.toString());
	}

	/**
	 * Returns the first faro user in the ordered set where liveUserId = &#63; and status = &#63;.
	 *
	 * @param liveUserId the live user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro user, or <code>null</code> if a matching faro user could not be found
	 */
	@Override
	public FaroUser fetchByL_S_First(
		long liveUserId, int status,
		OrderByComparator<FaroUser> orderByComparator) {

		List<FaroUser> list = findByL_S(
			liveUserId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last faro user in the ordered set where liveUserId = &#63; and status = &#63;.
	 *
	 * @param liveUserId the live user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro user
	 * @throws NoSuchFaroUserException if a matching faro user could not be found
	 */
	@Override
	public FaroUser findByL_S_Last(
			long liveUserId, int status,
			OrderByComparator<FaroUser> orderByComparator)
		throws NoSuchFaroUserException {

		FaroUser faroUser = fetchByL_S_Last(
			liveUserId, status, orderByComparator);

		if (faroUser != null) {
			return faroUser;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("liveUserId=");
		sb.append(liveUserId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchFaroUserException(sb.toString());
	}

	/**
	 * Returns the last faro user in the ordered set where liveUserId = &#63; and status = &#63;.
	 *
	 * @param liveUserId the live user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro user, or <code>null</code> if a matching faro user could not be found
	 */
	@Override
	public FaroUser fetchByL_S_Last(
		long liveUserId, int status,
		OrderByComparator<FaroUser> orderByComparator) {

		int count = countByL_S(liveUserId, status);

		if (count == 0) {
			return null;
		}

		List<FaroUser> list = findByL_S(
			liveUserId, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the faro users before and after the current faro user in the ordered set where liveUserId = &#63; and status = &#63;.
	 *
	 * @param faroUserId the primary key of the current faro user
	 * @param liveUserId the live user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next faro user
	 * @throws NoSuchFaroUserException if a faro user with the primary key could not be found
	 */
	@Override
	public FaroUser[] findByL_S_PrevAndNext(
			long faroUserId, long liveUserId, int status,
			OrderByComparator<FaroUser> orderByComparator)
		throws NoSuchFaroUserException {

		FaroUser faroUser = findByPrimaryKey(faroUserId);

		Session session = null;

		try {
			session = openSession();

			FaroUser[] array = new FaroUserImpl[3];

			array[0] = getByL_S_PrevAndNext(
				session, faroUser, liveUserId, status, orderByComparator, true);

			array[1] = faroUser;

			array[2] = getByL_S_PrevAndNext(
				session, faroUser, liveUserId, status, orderByComparator,
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

	protected FaroUser getByL_S_PrevAndNext(
		Session session, FaroUser faroUser, long liveUserId, int status,
		OrderByComparator<FaroUser> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_FAROUSER_WHERE);

		sb.append(_FINDER_COLUMN_L_S_LIVEUSERID_2);

		sb.append(_FINDER_COLUMN_L_S_STATUS_2);

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
			sb.append(FaroUserModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(liveUserId);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(faroUser)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FaroUser> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the faro users where liveUserId = &#63; and status = &#63; from the database.
	 *
	 * @param liveUserId the live user ID
	 * @param status the status
	 */
	@Override
	public void removeByL_S(long liveUserId, int status) {
		for (FaroUser faroUser :
				findByL_S(
					liveUserId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(faroUser);
		}
	}

	/**
	 * Returns the number of faro users where liveUserId = &#63; and status = &#63;.
	 *
	 * @param liveUserId the live user ID
	 * @param status the status
	 * @return the number of matching faro users
	 */
	@Override
	public int countByL_S(long liveUserId, int status) {
		FinderPath finderPath = _finderPathCountByL_S;

		Object[] finderArgs = new Object[] {liveUserId, status};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_FAROUSER_WHERE);

			sb.append(_FINDER_COLUMN_L_S_LIVEUSERID_2);

			sb.append(_FINDER_COLUMN_L_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(liveUserId);

				queryPos.add(status);

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

	private static final String _FINDER_COLUMN_L_S_LIVEUSERID_2 =
		"faroUser.liveUserId = ? AND ";

	private static final String _FINDER_COLUMN_L_S_STATUS_2 =
		"faroUser.status = ?";

	private FinderPath _finderPathWithPaginationFindByE_S;
	private FinderPath _finderPathWithoutPaginationFindByE_S;
	private FinderPath _finderPathCountByE_S;

	/**
	 * Returns all the faro users where emailAddress = &#63; and status = &#63;.
	 *
	 * @param emailAddress the email address
	 * @param status the status
	 * @return the matching faro users
	 */
	@Override
	public List<FaroUser> findByE_S(String emailAddress, int status) {
		return findByE_S(
			emailAddress, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the faro users where emailAddress = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroUserModelImpl</code>.
	 * </p>
	 *
	 * @param emailAddress the email address
	 * @param status the status
	 * @param start the lower bound of the range of faro users
	 * @param end the upper bound of the range of faro users (not inclusive)
	 * @return the range of matching faro users
	 */
	@Override
	public List<FaroUser> findByE_S(
		String emailAddress, int status, int start, int end) {

		return findByE_S(emailAddress, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the faro users where emailAddress = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroUserModelImpl</code>.
	 * </p>
	 *
	 * @param emailAddress the email address
	 * @param status the status
	 * @param start the lower bound of the range of faro users
	 * @param end the upper bound of the range of faro users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching faro users
	 */
	@Override
	public List<FaroUser> findByE_S(
		String emailAddress, int status, int start, int end,
		OrderByComparator<FaroUser> orderByComparator) {

		return findByE_S(
			emailAddress, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the faro users where emailAddress = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroUserModelImpl</code>.
	 * </p>
	 *
	 * @param emailAddress the email address
	 * @param status the status
	 * @param start the lower bound of the range of faro users
	 * @param end the upper bound of the range of faro users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching faro users
	 */
	@Override
	public List<FaroUser> findByE_S(
		String emailAddress, int status, int start, int end,
		OrderByComparator<FaroUser> orderByComparator, boolean useFinderCache) {

		emailAddress = Objects.toString(emailAddress, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByE_S;
				finderArgs = new Object[] {emailAddress, status};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByE_S;
			finderArgs = new Object[] {
				emailAddress, status, start, end, orderByComparator
			};
		}

		List<FaroUser> list = null;

		if (useFinderCache) {
			list = (List<FaroUser>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FaroUser faroUser : list) {
					if (!emailAddress.equals(faroUser.getEmailAddress()) ||
						(status != faroUser.getStatus())) {

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

			sb.append(_SQL_SELECT_FAROUSER_WHERE);

			boolean bindEmailAddress = false;

			if (emailAddress.isEmpty()) {
				sb.append(_FINDER_COLUMN_E_S_EMAILADDRESS_3);
			}
			else {
				bindEmailAddress = true;

				sb.append(_FINDER_COLUMN_E_S_EMAILADDRESS_2);
			}

			sb.append(_FINDER_COLUMN_E_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FaroUserModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindEmailAddress) {
					queryPos.add(emailAddress);
				}

				queryPos.add(status);

				list = (List<FaroUser>)QueryUtil.list(
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
	 * Returns the first faro user in the ordered set where emailAddress = &#63; and status = &#63;.
	 *
	 * @param emailAddress the email address
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro user
	 * @throws NoSuchFaroUserException if a matching faro user could not be found
	 */
	@Override
	public FaroUser findByE_S_First(
			String emailAddress, int status,
			OrderByComparator<FaroUser> orderByComparator)
		throws NoSuchFaroUserException {

		FaroUser faroUser = fetchByE_S_First(
			emailAddress, status, orderByComparator);

		if (faroUser != null) {
			return faroUser;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("emailAddress=");
		sb.append(emailAddress);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchFaroUserException(sb.toString());
	}

	/**
	 * Returns the first faro user in the ordered set where emailAddress = &#63; and status = &#63;.
	 *
	 * @param emailAddress the email address
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro user, or <code>null</code> if a matching faro user could not be found
	 */
	@Override
	public FaroUser fetchByE_S_First(
		String emailAddress, int status,
		OrderByComparator<FaroUser> orderByComparator) {

		List<FaroUser> list = findByE_S(
			emailAddress, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last faro user in the ordered set where emailAddress = &#63; and status = &#63;.
	 *
	 * @param emailAddress the email address
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro user
	 * @throws NoSuchFaroUserException if a matching faro user could not be found
	 */
	@Override
	public FaroUser findByE_S_Last(
			String emailAddress, int status,
			OrderByComparator<FaroUser> orderByComparator)
		throws NoSuchFaroUserException {

		FaroUser faroUser = fetchByE_S_Last(
			emailAddress, status, orderByComparator);

		if (faroUser != null) {
			return faroUser;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("emailAddress=");
		sb.append(emailAddress);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchFaroUserException(sb.toString());
	}

	/**
	 * Returns the last faro user in the ordered set where emailAddress = &#63; and status = &#63;.
	 *
	 * @param emailAddress the email address
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro user, or <code>null</code> if a matching faro user could not be found
	 */
	@Override
	public FaroUser fetchByE_S_Last(
		String emailAddress, int status,
		OrderByComparator<FaroUser> orderByComparator) {

		int count = countByE_S(emailAddress, status);

		if (count == 0) {
			return null;
		}

		List<FaroUser> list = findByE_S(
			emailAddress, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the faro users before and after the current faro user in the ordered set where emailAddress = &#63; and status = &#63;.
	 *
	 * @param faroUserId the primary key of the current faro user
	 * @param emailAddress the email address
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next faro user
	 * @throws NoSuchFaroUserException if a faro user with the primary key could not be found
	 */
	@Override
	public FaroUser[] findByE_S_PrevAndNext(
			long faroUserId, String emailAddress, int status,
			OrderByComparator<FaroUser> orderByComparator)
		throws NoSuchFaroUserException {

		emailAddress = Objects.toString(emailAddress, "");

		FaroUser faroUser = findByPrimaryKey(faroUserId);

		Session session = null;

		try {
			session = openSession();

			FaroUser[] array = new FaroUserImpl[3];

			array[0] = getByE_S_PrevAndNext(
				session, faroUser, emailAddress, status, orderByComparator,
				true);

			array[1] = faroUser;

			array[2] = getByE_S_PrevAndNext(
				session, faroUser, emailAddress, status, orderByComparator,
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

	protected FaroUser getByE_S_PrevAndNext(
		Session session, FaroUser faroUser, String emailAddress, int status,
		OrderByComparator<FaroUser> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_FAROUSER_WHERE);

		boolean bindEmailAddress = false;

		if (emailAddress.isEmpty()) {
			sb.append(_FINDER_COLUMN_E_S_EMAILADDRESS_3);
		}
		else {
			bindEmailAddress = true;

			sb.append(_FINDER_COLUMN_E_S_EMAILADDRESS_2);
		}

		sb.append(_FINDER_COLUMN_E_S_STATUS_2);

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
			sb.append(FaroUserModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindEmailAddress) {
			queryPos.add(emailAddress);
		}

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(faroUser)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FaroUser> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the faro users where emailAddress = &#63; and status = &#63; from the database.
	 *
	 * @param emailAddress the email address
	 * @param status the status
	 */
	@Override
	public void removeByE_S(String emailAddress, int status) {
		for (FaroUser faroUser :
				findByE_S(
					emailAddress, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(faroUser);
		}
	}

	/**
	 * Returns the number of faro users where emailAddress = &#63; and status = &#63;.
	 *
	 * @param emailAddress the email address
	 * @param status the status
	 * @return the number of matching faro users
	 */
	@Override
	public int countByE_S(String emailAddress, int status) {
		emailAddress = Objects.toString(emailAddress, "");

		FinderPath finderPath = _finderPathCountByE_S;

		Object[] finderArgs = new Object[] {emailAddress, status};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_FAROUSER_WHERE);

			boolean bindEmailAddress = false;

			if (emailAddress.isEmpty()) {
				sb.append(_FINDER_COLUMN_E_S_EMAILADDRESS_3);
			}
			else {
				bindEmailAddress = true;

				sb.append(_FINDER_COLUMN_E_S_EMAILADDRESS_2);
			}

			sb.append(_FINDER_COLUMN_E_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindEmailAddress) {
					queryPos.add(emailAddress);
				}

				queryPos.add(status);

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

	private static final String _FINDER_COLUMN_E_S_EMAILADDRESS_2 =
		"faroUser.emailAddress = ? AND ";

	private static final String _FINDER_COLUMN_E_S_EMAILADDRESS_3 =
		"(faroUser.emailAddress IS NULL OR faroUser.emailAddress = '') AND ";

	private static final String _FINDER_COLUMN_E_S_STATUS_2 =
		"faroUser.status = ?";

	public FaroUserPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("key", "key_");

		setDBColumnNames(dbColumnNames);

		setModelClass(FaroUser.class);

		setModelImplClass(FaroUserImpl.class);
		setModelPKClass(long.class);

		setTable(FaroUserTable.INSTANCE);
	}

	/**
	 * Caches the faro user in the entity cache if it is enabled.
	 *
	 * @param faroUser the faro user
	 */
	@Override
	public void cacheResult(FaroUser faroUser) {
		entityCache.putResult(
			FaroUserImpl.class, faroUser.getPrimaryKey(), faroUser);

		finderCache.putResult(
			_finderPathFetchByKey, new Object[] {faroUser.getKey()}, faroUser);

		finderCache.putResult(
			_finderPathFetchByG_L,
			new Object[] {faroUser.getGroupId(), faroUser.getLiveUserId()},
			faroUser);

		finderCache.putResult(
			_finderPathFetchByG_E,
			new Object[] {faroUser.getGroupId(), faroUser.getEmailAddress()},
			faroUser);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the faro users in the entity cache if it is enabled.
	 *
	 * @param faroUsers the faro users
	 */
	@Override
	public void cacheResult(List<FaroUser> faroUsers) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (faroUsers.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (FaroUser faroUser : faroUsers) {
			if (entityCache.getResult(
					FaroUserImpl.class, faroUser.getPrimaryKey()) == null) {

				cacheResult(faroUser);
			}
		}
	}

	/**
	 * Clears the cache for all faro users.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(FaroUserImpl.class);

		finderCache.clearCache(FaroUserImpl.class);
	}

	/**
	 * Clears the cache for the faro user.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(FaroUser faroUser) {
		entityCache.removeResult(FaroUserImpl.class, faroUser);
	}

	@Override
	public void clearCache(List<FaroUser> faroUsers) {
		for (FaroUser faroUser : faroUsers) {
			entityCache.removeResult(FaroUserImpl.class, faroUser);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FaroUserImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(FaroUserImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		FaroUserModelImpl faroUserModelImpl) {

		Object[] args = new Object[] {faroUserModelImpl.getKey()};

		finderCache.putResult(_finderPathCountByKey, args, Long.valueOf(1));
		finderCache.putResult(_finderPathFetchByKey, args, faroUserModelImpl);

		args = new Object[] {
			faroUserModelImpl.getGroupId(), faroUserModelImpl.getLiveUserId()
		};

		finderCache.putResult(_finderPathCountByG_L, args, Long.valueOf(1));
		finderCache.putResult(_finderPathFetchByG_L, args, faroUserModelImpl);

		args = new Object[] {
			faroUserModelImpl.getGroupId(), faroUserModelImpl.getEmailAddress()
		};

		finderCache.putResult(_finderPathCountByG_E, args, Long.valueOf(1));
		finderCache.putResult(_finderPathFetchByG_E, args, faroUserModelImpl);
	}

	/**
	 * Creates a new faro user with the primary key. Does not add the faro user to the database.
	 *
	 * @param faroUserId the primary key for the new faro user
	 * @return the new faro user
	 */
	@Override
	public FaroUser create(long faroUserId) {
		FaroUser faroUser = new FaroUserImpl();

		faroUser.setNew(true);
		faroUser.setPrimaryKey(faroUserId);

		faroUser.setCompanyId(CompanyThreadLocal.getCompanyId());

		return faroUser;
	}

	/**
	 * Removes the faro user with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param faroUserId the primary key of the faro user
	 * @return the faro user that was removed
	 * @throws NoSuchFaroUserException if a faro user with the primary key could not be found
	 */
	@Override
	public FaroUser remove(long faroUserId) throws NoSuchFaroUserException {
		return remove((Serializable)faroUserId);
	}

	/**
	 * Removes the faro user with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the faro user
	 * @return the faro user that was removed
	 * @throws NoSuchFaroUserException if a faro user with the primary key could not be found
	 */
	@Override
	public FaroUser remove(Serializable primaryKey)
		throws NoSuchFaroUserException {

		Session session = null;

		try {
			session = openSession();

			FaroUser faroUser = (FaroUser)session.get(
				FaroUserImpl.class, primaryKey);

			if (faroUser == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFaroUserException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(faroUser);
		}
		catch (NoSuchFaroUserException noSuchEntityException) {
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
	protected FaroUser removeImpl(FaroUser faroUser) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(faroUser)) {
				faroUser = (FaroUser)session.get(
					FaroUserImpl.class, faroUser.getPrimaryKeyObj());
			}

			if (faroUser != null) {
				session.delete(faroUser);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (faroUser != null) {
			clearCache(faroUser);
		}

		return faroUser;
	}

	@Override
	public FaroUser updateImpl(FaroUser faroUser) {
		boolean isNew = faroUser.isNew();

		if (!(faroUser instanceof FaroUserModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(faroUser.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(faroUser);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in faroUser proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom FaroUser implementation " +
					faroUser.getClass());
		}

		FaroUserModelImpl faroUserModelImpl = (FaroUserModelImpl)faroUser;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(faroUser);
			}
			else {
				faroUser = (FaroUser)session.merge(faroUser);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			FaroUserImpl.class, faroUserModelImpl, false, true);

		cacheUniqueFindersCache(faroUserModelImpl);

		if (isNew) {
			faroUser.setNew(false);
		}

		faroUser.resetOriginalValues();

		return faroUser;
	}

	/**
	 * Returns the faro user with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the faro user
	 * @return the faro user
	 * @throws NoSuchFaroUserException if a faro user with the primary key could not be found
	 */
	@Override
	public FaroUser findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFaroUserException {

		FaroUser faroUser = fetchByPrimaryKey(primaryKey);

		if (faroUser == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFaroUserException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return faroUser;
	}

	/**
	 * Returns the faro user with the primary key or throws a <code>NoSuchFaroUserException</code> if it could not be found.
	 *
	 * @param faroUserId the primary key of the faro user
	 * @return the faro user
	 * @throws NoSuchFaroUserException if a faro user with the primary key could not be found
	 */
	@Override
	public FaroUser findByPrimaryKey(long faroUserId)
		throws NoSuchFaroUserException {

		return findByPrimaryKey((Serializable)faroUserId);
	}

	/**
	 * Returns the faro user with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param faroUserId the primary key of the faro user
	 * @return the faro user, or <code>null</code> if a faro user with the primary key could not be found
	 */
	@Override
	public FaroUser fetchByPrimaryKey(long faroUserId) {
		return fetchByPrimaryKey((Serializable)faroUserId);
	}

	/**
	 * Returns all the faro users.
	 *
	 * @return the faro users
	 */
	@Override
	public List<FaroUser> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the faro users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroUserModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro users
	 * @param end the upper bound of the range of faro users (not inclusive)
	 * @return the range of faro users
	 */
	@Override
	public List<FaroUser> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the faro users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroUserModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro users
	 * @param end the upper bound of the range of faro users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of faro users
	 */
	@Override
	public List<FaroUser> findAll(
		int start, int end, OrderByComparator<FaroUser> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the faro users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroUserModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro users
	 * @param end the upper bound of the range of faro users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of faro users
	 */
	@Override
	public List<FaroUser> findAll(
		int start, int end, OrderByComparator<FaroUser> orderByComparator,
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

		List<FaroUser> list = null;

		if (useFinderCache) {
			list = (List<FaroUser>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_FAROUSER);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_FAROUSER;

				sql = sql.concat(FaroUserModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<FaroUser>)QueryUtil.list(
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
	 * Removes all the faro users from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (FaroUser faroUser : findAll()) {
			remove(faroUser);
		}
	}

	/**
	 * Returns the number of faro users.
	 *
	 * @return the number of faro users
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_FAROUSER);

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
		return "faroUserId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_FAROUSER;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return FaroUserModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the faro user persistence.
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

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId"}, true);

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			true);

		_finderPathCountByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			false);

		_finderPathFetchByKey = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByKey",
			new String[] {String.class.getName()}, new String[] {"key_"}, true);

		_finderPathCountByKey = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByKey",
			new String[] {String.class.getName()}, new String[] {"key_"},
			false);

		_finderPathFetchByG_L = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByG_L",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "liveUserId"}, true);

		_finderPathCountByG_L = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_L",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "liveUserId"}, false);

		_finderPathWithPaginationFindByG_R = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_R",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "roleId"}, true);

		_finderPathWithoutPaginationFindByG_R = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_R",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "roleId"}, true);

		_finderPathCountByG_R = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_R",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "roleId"}, false);

		_finderPathFetchByG_E = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByG_E",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "emailAddress"}, true);

		_finderPathCountByG_E = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_E",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "emailAddress"}, false);

		_finderPathWithPaginationFindByG_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "status"}, true);

		_finderPathWithoutPaginationFindByG_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"groupId", "status"}, true);

		_finderPathCountByG_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"groupId", "status"}, false);

		_finderPathWithPaginationFindByL_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByL_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"liveUserId", "status"}, true);

		_finderPathWithoutPaginationFindByL_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByL_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"liveUserId", "status"}, true);

		_finderPathCountByL_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByL_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"liveUserId", "status"}, false);

		_finderPathWithPaginationFindByE_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByE_S",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"emailAddress", "status"}, true);

		_finderPathWithoutPaginationFindByE_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByE_S",
			new String[] {String.class.getName(), Integer.class.getName()},
			new String[] {"emailAddress", "status"}, true);

		_finderPathCountByE_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByE_S",
			new String[] {String.class.getName(), Integer.class.getName()},
			new String[] {"emailAddress", "status"}, false);

		FaroUserUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		FaroUserUtil.setPersistence(null);

		entityCache.removeCache(FaroUserImpl.class.getName());
	}

	@Override
	@Reference(
		target = OSBFaroPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = OSBFaroPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = OSBFaroPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_FAROUSER =
		"SELECT faroUser FROM FaroUser faroUser";

	private static final String _SQL_SELECT_FAROUSER_WHERE =
		"SELECT faroUser FROM FaroUser faroUser WHERE ";

	private static final String _SQL_COUNT_FAROUSER =
		"SELECT COUNT(faroUser) FROM FaroUser faroUser";

	private static final String _SQL_COUNT_FAROUSER_WHERE =
		"SELECT COUNT(faroUser) FROM FaroUser faroUser WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "faroUser.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No FaroUser exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No FaroUser exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		FaroUserPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"key"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}