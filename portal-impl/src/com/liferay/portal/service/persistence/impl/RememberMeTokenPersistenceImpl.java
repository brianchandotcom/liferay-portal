/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.NoSuchRememberMeTokenException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.RememberMeToken;
import com.liferay.portal.kernel.model.RememberMeTokenTable;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.RememberMeTokenPersistence;
import com.liferay.portal.kernel.service.persistence.RememberMeTokenUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.model.impl.RememberMeTokenImpl;
import com.liferay.portal.model.impl.RememberMeTokenModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.sql.Timestamp;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the remember me token service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class RememberMeTokenPersistenceImpl
	extends BasePersistenceImpl<RememberMeToken>
	implements RememberMeTokenPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>RememberMeTokenUtil</code> to access the remember me token persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		RememberMeTokenImpl.class.getName();

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
	 * Returns all the remember me tokens where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching remember me tokens
	 */
	@Override
	public List<RememberMeToken> findByUserId(long userId) {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the remember me tokens where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RememberMeTokenModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of remember me tokens
	 * @param end the upper bound of the range of remember me tokens (not inclusive)
	 * @return the range of matching remember me tokens
	 */
	@Override
	public List<RememberMeToken> findByUserId(long userId, int start, int end) {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the remember me tokens where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RememberMeTokenModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of remember me tokens
	 * @param end the upper bound of the range of remember me tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching remember me tokens
	 */
	@Override
	public List<RememberMeToken> findByUserId(
		long userId, int start, int end,
		OrderByComparator<RememberMeToken> orderByComparator) {

		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the remember me tokens where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RememberMeTokenModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of remember me tokens
	 * @param end the upper bound of the range of remember me tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching remember me tokens
	 */
	@Override
	public List<RememberMeToken> findByUserId(
		long userId, int start, int end,
		OrderByComparator<RememberMeToken> orderByComparator,
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

		List<RememberMeToken> list = null;

		if (useFinderCache) {
			list = (List<RememberMeToken>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (RememberMeToken rememberMeToken : list) {
					if (userId != rememberMeToken.getUserId()) {
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

			sb.append(_SQL_SELECT_REMEMBERMETOKEN_WHERE);

			sb.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(RememberMeTokenModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				list = (List<RememberMeToken>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first remember me token in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remember me token
	 * @throws NoSuchRememberMeTokenException if a matching remember me token could not be found
	 */
	@Override
	public RememberMeToken findByUserId_First(
			long userId, OrderByComparator<RememberMeToken> orderByComparator)
		throws NoSuchRememberMeTokenException {

		RememberMeToken rememberMeToken = fetchByUserId_First(
			userId, orderByComparator);

		if (rememberMeToken != null) {
			return rememberMeToken;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchRememberMeTokenException(sb.toString());
	}

	/**
	 * Returns the first remember me token in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remember me token, or <code>null</code> if a matching remember me token could not be found
	 */
	@Override
	public RememberMeToken fetchByUserId_First(
		long userId, OrderByComparator<RememberMeToken> orderByComparator) {

		List<RememberMeToken> list = findByUserId(
			userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the remember me tokens where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserId(long userId) {
		for (RememberMeToken rememberMeToken :
				findByUserId(
					userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(rememberMeToken);
		}
	}

	/**
	 * Returns the number of remember me tokens where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching remember me tokens
	 */
	@Override
	public int countByUserId(long userId) {
		FinderPath finderPath = _finderPathCountByUserId;

		Object[] finderArgs = new Object[] {userId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_REMEMBERMETOKEN_WHERE);

			sb.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
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
		"rememberMeToken.userId = ?";

	private FinderPath _finderPathWithPaginationFindByLteExpirationDate;
	private FinderPath _finderPathWithPaginationCountByLteExpirationDate;

	/**
	 * Returns all the remember me tokens where expirationDate &le; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @return the matching remember me tokens
	 */
	@Override
	public List<RememberMeToken> findByLteExpirationDate(Date expirationDate) {
		return findByLteExpirationDate(
			expirationDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the remember me tokens where expirationDate &le; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RememberMeTokenModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of remember me tokens
	 * @param end the upper bound of the range of remember me tokens (not inclusive)
	 * @return the range of matching remember me tokens
	 */
	@Override
	public List<RememberMeToken> findByLteExpirationDate(
		Date expirationDate, int start, int end) {

		return findByLteExpirationDate(expirationDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the remember me tokens where expirationDate &le; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RememberMeTokenModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of remember me tokens
	 * @param end the upper bound of the range of remember me tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching remember me tokens
	 */
	@Override
	public List<RememberMeToken> findByLteExpirationDate(
		Date expirationDate, int start, int end,
		OrderByComparator<RememberMeToken> orderByComparator) {

		return findByLteExpirationDate(
			expirationDate, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the remember me tokens where expirationDate &le; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RememberMeTokenModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of remember me tokens
	 * @param end the upper bound of the range of remember me tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching remember me tokens
	 */
	@Override
	public List<RememberMeToken> findByLteExpirationDate(
		Date expirationDate, int start, int end,
		OrderByComparator<RememberMeToken> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByLteExpirationDate;
		finderArgs = new Object[] {
			_getTime(expirationDate), start, end, orderByComparator
		};

		List<RememberMeToken> list = null;

		if (useFinderCache) {
			list = (List<RememberMeToken>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (RememberMeToken rememberMeToken : list) {
					if (expirationDate.getTime() <
							rememberMeToken.getExpirationDate(
							).getTime()) {

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

			sb.append(_SQL_SELECT_REMEMBERMETOKEN_WHERE);

			boolean bindExpirationDate = false;

			if (expirationDate == null) {
				sb.append(_FINDER_COLUMN_LTEEXPIRATIONDATE_EXPIRATIONDATE_1);
			}
			else {
				bindExpirationDate = true;

				sb.append(_FINDER_COLUMN_LTEEXPIRATIONDATE_EXPIRATIONDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(RememberMeTokenModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindExpirationDate) {
					queryPos.add(new Timestamp(expirationDate.getTime()));
				}

				list = (List<RememberMeToken>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first remember me token in the ordered set where expirationDate &le; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remember me token
	 * @throws NoSuchRememberMeTokenException if a matching remember me token could not be found
	 */
	@Override
	public RememberMeToken findByLteExpirationDate_First(
			Date expirationDate,
			OrderByComparator<RememberMeToken> orderByComparator)
		throws NoSuchRememberMeTokenException {

		RememberMeToken rememberMeToken = fetchByLteExpirationDate_First(
			expirationDate, orderByComparator);

		if (rememberMeToken != null) {
			return rememberMeToken;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("expirationDate<=");
		sb.append(expirationDate);

		sb.append("}");

		throw new NoSuchRememberMeTokenException(sb.toString());
	}

	/**
	 * Returns the first remember me token in the ordered set where expirationDate &le; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remember me token, or <code>null</code> if a matching remember me token could not be found
	 */
	@Override
	public RememberMeToken fetchByLteExpirationDate_First(
		Date expirationDate,
		OrderByComparator<RememberMeToken> orderByComparator) {

		List<RememberMeToken> list = findByLteExpirationDate(
			expirationDate, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the remember me tokens where expirationDate &le; &#63; from the database.
	 *
	 * @param expirationDate the expiration date
	 */
	@Override
	public void removeByLteExpirationDate(Date expirationDate) {
		for (RememberMeToken rememberMeToken :
				findByLteExpirationDate(
					expirationDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(rememberMeToken);
		}
	}

	/**
	 * Returns the number of remember me tokens where expirationDate &le; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @return the number of matching remember me tokens
	 */
	@Override
	public int countByLteExpirationDate(Date expirationDate) {
		FinderPath finderPath =
			_finderPathWithPaginationCountByLteExpirationDate;

		Object[] finderArgs = new Object[] {_getTime(expirationDate)};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_REMEMBERMETOKEN_WHERE);

			boolean bindExpirationDate = false;

			if (expirationDate == null) {
				sb.append(_FINDER_COLUMN_LTEEXPIRATIONDATE_EXPIRATIONDATE_1);
			}
			else {
				bindExpirationDate = true;

				sb.append(_FINDER_COLUMN_LTEEXPIRATIONDATE_EXPIRATIONDATE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindExpirationDate) {
					queryPos.add(new Timestamp(expirationDate.getTime()));
				}

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
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
		_FINDER_COLUMN_LTEEXPIRATIONDATE_EXPIRATIONDATE_1 =
			"rememberMeToken.expirationDate IS NULL";

	private static final String
		_FINDER_COLUMN_LTEEXPIRATIONDATE_EXPIRATIONDATE_2 =
			"rememberMeToken.expirationDate <= ?";

	public RememberMeTokenPersistenceImpl() {
		setModelClass(RememberMeToken.class);

		setModelImplClass(RememberMeTokenImpl.class);
		setModelPKClass(long.class);

		setTable(RememberMeTokenTable.INSTANCE);
	}

	/**
	 * Caches the remember me token in the entity cache if it is enabled.
	 *
	 * @param rememberMeToken the remember me token
	 */
	@Override
	public void cacheResult(RememberMeToken rememberMeToken) {
		EntityCacheUtil.putResult(
			RememberMeTokenImpl.class, rememberMeToken.getPrimaryKey(),
			rememberMeToken);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the remember me tokens in the entity cache if it is enabled.
	 *
	 * @param rememberMeTokens the remember me tokens
	 */
	@Override
	public void cacheResult(List<RememberMeToken> rememberMeTokens) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (rememberMeTokens.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (RememberMeToken rememberMeToken : rememberMeTokens) {
			if (EntityCacheUtil.getResult(
					RememberMeTokenImpl.class,
					rememberMeToken.getPrimaryKey()) == null) {

				cacheResult(rememberMeToken);
			}
		}
	}

	/**
	 * Clears the cache for all remember me tokens.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(RememberMeTokenImpl.class);

		FinderCacheUtil.clearCache(RememberMeTokenImpl.class);
	}

	/**
	 * Clears the cache for the remember me token.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(RememberMeToken rememberMeToken) {
		EntityCacheUtil.removeResult(
			RememberMeTokenImpl.class, rememberMeToken);
	}

	@Override
	public void clearCache(List<RememberMeToken> rememberMeTokens) {
		for (RememberMeToken rememberMeToken : rememberMeTokens) {
			EntityCacheUtil.removeResult(
				RememberMeTokenImpl.class, rememberMeToken);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(RememberMeTokenImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(RememberMeTokenImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new remember me token with the primary key. Does not add the remember me token to the database.
	 *
	 * @param rememberMeTokenId the primary key for the new remember me token
	 * @return the new remember me token
	 */
	@Override
	public RememberMeToken create(long rememberMeTokenId) {
		RememberMeToken rememberMeToken = new RememberMeTokenImpl();

		rememberMeToken.setNew(true);
		rememberMeToken.setPrimaryKey(rememberMeTokenId);

		rememberMeToken.setCompanyId(CompanyThreadLocal.getCompanyId());

		return rememberMeToken;
	}

	/**
	 * Removes the remember me token with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param rememberMeTokenId the primary key of the remember me token
	 * @return the remember me token that was removed
	 * @throws NoSuchRememberMeTokenException if a remember me token with the primary key could not be found
	 */
	@Override
	public RememberMeToken remove(long rememberMeTokenId)
		throws NoSuchRememberMeTokenException {

		return remove((Serializable)rememberMeTokenId);
	}

	/**
	 * Removes the remember me token with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the remember me token
	 * @return the remember me token that was removed
	 * @throws NoSuchRememberMeTokenException if a remember me token with the primary key could not be found
	 */
	@Override
	public RememberMeToken remove(Serializable primaryKey)
		throws NoSuchRememberMeTokenException {

		Session session = null;

		try {
			session = openSession();

			RememberMeToken rememberMeToken = (RememberMeToken)session.get(
				RememberMeTokenImpl.class, primaryKey);

			if (rememberMeToken == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchRememberMeTokenException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(rememberMeToken);
		}
		catch (NoSuchRememberMeTokenException noSuchEntityException) {
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
	protected RememberMeToken removeImpl(RememberMeToken rememberMeToken) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(rememberMeToken)) {
				rememberMeToken = (RememberMeToken)session.get(
					RememberMeTokenImpl.class,
					rememberMeToken.getPrimaryKeyObj());
			}

			if (rememberMeToken != null) {
				session.delete(rememberMeToken);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (rememberMeToken != null) {
			clearCache(rememberMeToken);
		}

		return rememberMeToken;
	}

	@Override
	public RememberMeToken updateImpl(RememberMeToken rememberMeToken) {
		boolean isNew = rememberMeToken.isNew();

		if (!(rememberMeToken instanceof RememberMeTokenModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(rememberMeToken.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					rememberMeToken);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in rememberMeToken proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom RememberMeToken implementation " +
					rememberMeToken.getClass());
		}

		RememberMeTokenModelImpl rememberMeTokenModelImpl =
			(RememberMeTokenModelImpl)rememberMeToken;

		if (isNew && (rememberMeToken.getCreateDate() == null)) {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			Date date = new Date();

			if (serviceContext == null) {
				rememberMeToken.setCreateDate(date);
			}
			else {
				rememberMeToken.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(rememberMeToken);
			}
			else {
				rememberMeToken = (RememberMeToken)session.merge(
					rememberMeToken);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		EntityCacheUtil.putResult(
			RememberMeTokenImpl.class, rememberMeTokenModelImpl, false, true);

		if (isNew) {
			rememberMeToken.setNew(false);
		}

		rememberMeToken.resetOriginalValues();

		return rememberMeToken;
	}

	/**
	 * Returns the remember me token with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the remember me token
	 * @return the remember me token
	 * @throws NoSuchRememberMeTokenException if a remember me token with the primary key could not be found
	 */
	@Override
	public RememberMeToken findByPrimaryKey(Serializable primaryKey)
		throws NoSuchRememberMeTokenException {

		RememberMeToken rememberMeToken = fetchByPrimaryKey(primaryKey);

		if (rememberMeToken == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchRememberMeTokenException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return rememberMeToken;
	}

	/**
	 * Returns the remember me token with the primary key or throws a <code>NoSuchRememberMeTokenException</code> if it could not be found.
	 *
	 * @param rememberMeTokenId the primary key of the remember me token
	 * @return the remember me token
	 * @throws NoSuchRememberMeTokenException if a remember me token with the primary key could not be found
	 */
	@Override
	public RememberMeToken findByPrimaryKey(long rememberMeTokenId)
		throws NoSuchRememberMeTokenException {

		return findByPrimaryKey((Serializable)rememberMeTokenId);
	}

	/**
	 * Returns the remember me token with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param rememberMeTokenId the primary key of the remember me token
	 * @return the remember me token, or <code>null</code> if a remember me token with the primary key could not be found
	 */
	@Override
	public RememberMeToken fetchByPrimaryKey(long rememberMeTokenId) {
		return fetchByPrimaryKey((Serializable)rememberMeTokenId);
	}

	/**
	 * Returns all the remember me tokens.
	 *
	 * @return the remember me tokens
	 */
	@Override
	public List<RememberMeToken> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the remember me tokens.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RememberMeTokenModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remember me tokens
	 * @param end the upper bound of the range of remember me tokens (not inclusive)
	 * @return the range of remember me tokens
	 */
	@Override
	public List<RememberMeToken> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the remember me tokens.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RememberMeTokenModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remember me tokens
	 * @param end the upper bound of the range of remember me tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of remember me tokens
	 */
	@Override
	public List<RememberMeToken> findAll(
		int start, int end,
		OrderByComparator<RememberMeToken> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the remember me tokens.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RememberMeTokenModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remember me tokens
	 * @param end the upper bound of the range of remember me tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of remember me tokens
	 */
	@Override
	public List<RememberMeToken> findAll(
		int start, int end,
		OrderByComparator<RememberMeToken> orderByComparator,
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

		List<RememberMeToken> list = null;

		if (useFinderCache) {
			list = (List<RememberMeToken>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_REMEMBERMETOKEN);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_REMEMBERMETOKEN;

				sql = sql.concat(RememberMeTokenModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<RememberMeToken>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Removes all the remember me tokens from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (RememberMeToken rememberMeToken : findAll()) {
			remove(rememberMeToken);
		}
	}

	/**
	 * Returns the number of remember me tokens.
	 *
	 * @return the number of remember me tokens
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_REMEMBERMETOKEN);

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(
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
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "rememberMeTokenId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_REMEMBERMETOKEN;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return RememberMeTokenModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the remember me token persistence.
	 */
	public void afterPropertiesSet() {
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

		_finderPathWithPaginationFindByLteExpirationDate = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLteExpirationDate",
			new String[] {
				Date.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"expirationDate"}, true);

		_finderPathWithPaginationCountByLteExpirationDate = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByLteExpirationDate",
			new String[] {Date.class.getName()},
			new String[] {"expirationDate"}, false);

		RememberMeTokenUtil.setPersistence(this);
	}

	public void destroy() {
		RememberMeTokenUtil.setPersistence(null);

		EntityCacheUtil.removeCache(RememberMeTokenImpl.class.getName());
	}

	private static Long _getTime(Date date) {
		if (date == null) {
			return null;
		}

		return date.getTime();
	}

	private static final String _SQL_SELECT_REMEMBERMETOKEN =
		"SELECT rememberMeToken FROM RememberMeToken rememberMeToken";

	private static final String _SQL_SELECT_REMEMBERMETOKEN_WHERE =
		"SELECT rememberMeToken FROM RememberMeToken rememberMeToken WHERE ";

	private static final String _SQL_COUNT_REMEMBERMETOKEN =
		"SELECT COUNT(rememberMeToken) FROM RememberMeToken rememberMeToken";

	private static final String _SQL_COUNT_REMEMBERMETOKEN_WHERE =
		"SELECT COUNT(rememberMeToken) FROM RememberMeToken rememberMeToken WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "rememberMeToken.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No RememberMeToken exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No RememberMeToken exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		RememberMeTokenPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return FinderCacheUtil.getFinderCache();
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-1144915232