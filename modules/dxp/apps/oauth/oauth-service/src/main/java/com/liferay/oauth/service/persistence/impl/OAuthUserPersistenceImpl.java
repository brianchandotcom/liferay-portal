/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.oauth.service.persistence.impl;

import com.liferay.oauth.exception.NoSuchUserException;
import com.liferay.oauth.model.OAuthUser;
import com.liferay.oauth.model.impl.OAuthUserImpl;
import com.liferay.oauth.model.impl.OAuthUserModelImpl;
import com.liferay.oauth.service.persistence.OAuthUserPersistence;
import com.liferay.oauth.service.persistence.impl.constants.OAuthPersistenceConstants;
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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

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
 * The persistence implementation for the o auth user service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ivica Cardic
 * @generated
 */
@Component(service = OAuthUserPersistence.class)
public class OAuthUserPersistenceImpl
	extends BasePersistenceImpl<OAuthUser> implements OAuthUserPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>OAuthUserUtil</code> to access the o auth user persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		OAuthUserImpl.class.getName();

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
	 * Returns all the o auth users where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching o auth users
	 */
	@Override
	public List<OAuthUser> findByUserId(long userId) {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth users where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthUserModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth users
	 * @param end the upper bound of the range of o auth users (not inclusive)
	 * @return the range of matching o auth users
	 */
	@Override
	public List<OAuthUser> findByUserId(long userId, int start, int end) {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth users where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthUserModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth users
	 * @param end the upper bound of the range of o auth users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth users
	 */
	@Override
	public List<OAuthUser> findByUserId(
		long userId, int start, int end,
		OrderByComparator<OAuthUser> orderByComparator) {

		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth users where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthUserModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth users
	 * @param end the upper bound of the range of o auth users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth users
	 */
	@Override
	public List<OAuthUser> findByUserId(
		long userId, int start, int end,
		OrderByComparator<OAuthUser> orderByComparator,
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

		List<OAuthUser> list = null;

		if (useFinderCache) {
			list = (List<OAuthUser>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (OAuthUser oAuthUser : list) {
					if (userId != oAuthUser.getUserId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_OAUTHUSER_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(OAuthUserModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = (List<OAuthUser>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first o auth user in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth user
	 * @throws NoSuchUserException if a matching o auth user could not be found
	 */
	@Override
	public OAuthUser findByUserId_First(
			long userId, OrderByComparator<OAuthUser> orderByComparator)
		throws NoSuchUserException {

		OAuthUser oAuthUser = fetchByUserId_First(userId, orderByComparator);

		if (oAuthUser != null) {
			return oAuthUser;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append("}");

		throw new NoSuchUserException(msg.toString());
	}

	/**
	 * Returns the first o auth user in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth user, or <code>null</code> if a matching o auth user could not be found
	 */
	@Override
	public OAuthUser fetchByUserId_First(
		long userId, OrderByComparator<OAuthUser> orderByComparator) {

		List<OAuthUser> list = findByUserId(userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last o auth user in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth user
	 * @throws NoSuchUserException if a matching o auth user could not be found
	 */
	@Override
	public OAuthUser findByUserId_Last(
			long userId, OrderByComparator<OAuthUser> orderByComparator)
		throws NoSuchUserException {

		OAuthUser oAuthUser = fetchByUserId_Last(userId, orderByComparator);

		if (oAuthUser != null) {
			return oAuthUser;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append("}");

		throw new NoSuchUserException(msg.toString());
	}

	/**
	 * Returns the last o auth user in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth user, or <code>null</code> if a matching o auth user could not be found
	 */
	@Override
	public OAuthUser fetchByUserId_Last(
		long userId, OrderByComparator<OAuthUser> orderByComparator) {

		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<OAuthUser> list = findByUserId(
			userId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the o auth users before and after the current o auth user in the ordered set where userId = &#63;.
	 *
	 * @param oAuthUserId the primary key of the current o auth user
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth user
	 * @throws NoSuchUserException if a o auth user with the primary key could not be found
	 */
	@Override
	public OAuthUser[] findByUserId_PrevAndNext(
			long oAuthUserId, long userId,
			OrderByComparator<OAuthUser> orderByComparator)
		throws NoSuchUserException {

		OAuthUser oAuthUser = findByPrimaryKey(oAuthUserId);

		Session session = null;

		try {
			session = openSession();

			OAuthUser[] array = new OAuthUserImpl[3];

			array[0] = getByUserId_PrevAndNext(
				session, oAuthUser, userId, orderByComparator, true);

			array[1] = oAuthUser;

			array[2] = getByUserId_PrevAndNext(
				session, oAuthUser, userId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected OAuthUser getByUserId_PrevAndNext(
		Session session, OAuthUser oAuthUser, long userId,
		OrderByComparator<OAuthUser> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_OAUTHUSER_WHERE);

		query.append(_FINDER_COLUMN_USERID_USERID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(OAuthUserModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(oAuthUser)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<OAuthUser> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the o auth users where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserId(long userId) {
		for (OAuthUser oAuthUser :
				findByUserId(
					userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(oAuthUser);
		}
	}

	/**
	 * Returns the number of o auth users where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching o auth users
	 */
	@Override
	public int countByUserId(long userId) {
		FinderPath finderPath = _finderPathCountByUserId;

		Object[] finderArgs = new Object[] {userId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_OAUTHUSER_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_USERID_USERID_2 =
		"oAuthUser.userId = ?";

	private FinderPath _finderPathWithPaginationFindByOAuthApplicationId;
	private FinderPath _finderPathWithoutPaginationFindByOAuthApplicationId;
	private FinderPath _finderPathCountByOAuthApplicationId;

	/**
	 * Returns all the o auth users where oAuthApplicationId = &#63;.
	 *
	 * @param oAuthApplicationId the o auth application ID
	 * @return the matching o auth users
	 */
	@Override
	public List<OAuthUser> findByOAuthApplicationId(long oAuthApplicationId) {
		return findByOAuthApplicationId(
			oAuthApplicationId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth users where oAuthApplicationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthUserModelImpl</code>.
	 * </p>
	 *
	 * @param oAuthApplicationId the o auth application ID
	 * @param start the lower bound of the range of o auth users
	 * @param end the upper bound of the range of o auth users (not inclusive)
	 * @return the range of matching o auth users
	 */
	@Override
	public List<OAuthUser> findByOAuthApplicationId(
		long oAuthApplicationId, int start, int end) {

		return findByOAuthApplicationId(oAuthApplicationId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth users where oAuthApplicationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthUserModelImpl</code>.
	 * </p>
	 *
	 * @param oAuthApplicationId the o auth application ID
	 * @param start the lower bound of the range of o auth users
	 * @param end the upper bound of the range of o auth users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth users
	 */
	@Override
	public List<OAuthUser> findByOAuthApplicationId(
		long oAuthApplicationId, int start, int end,
		OrderByComparator<OAuthUser> orderByComparator) {

		return findByOAuthApplicationId(
			oAuthApplicationId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth users where oAuthApplicationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthUserModelImpl</code>.
	 * </p>
	 *
	 * @param oAuthApplicationId the o auth application ID
	 * @param start the lower bound of the range of o auth users
	 * @param end the upper bound of the range of o auth users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth users
	 */
	@Override
	public List<OAuthUser> findByOAuthApplicationId(
		long oAuthApplicationId, int start, int end,
		OrderByComparator<OAuthUser> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByOAuthApplicationId;
				finderArgs = new Object[] {oAuthApplicationId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByOAuthApplicationId;
			finderArgs = new Object[] {
				oAuthApplicationId, start, end, orderByComparator
			};
		}

		List<OAuthUser> list = null;

		if (useFinderCache) {
			list = (List<OAuthUser>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (OAuthUser oAuthUser : list) {
					if (oAuthApplicationId !=
							oAuthUser.getOAuthApplicationId()) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_OAUTHUSER_WHERE);

			query.append(
				_FINDER_COLUMN_OAUTHAPPLICATIONID_OAUTHAPPLICATIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(OAuthUserModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(oAuthApplicationId);

				list = (List<OAuthUser>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first o auth user in the ordered set where oAuthApplicationId = &#63;.
	 *
	 * @param oAuthApplicationId the o auth application ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth user
	 * @throws NoSuchUserException if a matching o auth user could not be found
	 */
	@Override
	public OAuthUser findByOAuthApplicationId_First(
			long oAuthApplicationId,
			OrderByComparator<OAuthUser> orderByComparator)
		throws NoSuchUserException {

		OAuthUser oAuthUser = fetchByOAuthApplicationId_First(
			oAuthApplicationId, orderByComparator);

		if (oAuthUser != null) {
			return oAuthUser;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("oAuthApplicationId=");
		msg.append(oAuthApplicationId);

		msg.append("}");

		throw new NoSuchUserException(msg.toString());
	}

	/**
	 * Returns the first o auth user in the ordered set where oAuthApplicationId = &#63;.
	 *
	 * @param oAuthApplicationId the o auth application ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth user, or <code>null</code> if a matching o auth user could not be found
	 */
	@Override
	public OAuthUser fetchByOAuthApplicationId_First(
		long oAuthApplicationId,
		OrderByComparator<OAuthUser> orderByComparator) {

		List<OAuthUser> list = findByOAuthApplicationId(
			oAuthApplicationId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last o auth user in the ordered set where oAuthApplicationId = &#63;.
	 *
	 * @param oAuthApplicationId the o auth application ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth user
	 * @throws NoSuchUserException if a matching o auth user could not be found
	 */
	@Override
	public OAuthUser findByOAuthApplicationId_Last(
			long oAuthApplicationId,
			OrderByComparator<OAuthUser> orderByComparator)
		throws NoSuchUserException {

		OAuthUser oAuthUser = fetchByOAuthApplicationId_Last(
			oAuthApplicationId, orderByComparator);

		if (oAuthUser != null) {
			return oAuthUser;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("oAuthApplicationId=");
		msg.append(oAuthApplicationId);

		msg.append("}");

		throw new NoSuchUserException(msg.toString());
	}

	/**
	 * Returns the last o auth user in the ordered set where oAuthApplicationId = &#63;.
	 *
	 * @param oAuthApplicationId the o auth application ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth user, or <code>null</code> if a matching o auth user could not be found
	 */
	@Override
	public OAuthUser fetchByOAuthApplicationId_Last(
		long oAuthApplicationId,
		OrderByComparator<OAuthUser> orderByComparator) {

		int count = countByOAuthApplicationId(oAuthApplicationId);

		if (count == 0) {
			return null;
		}

		List<OAuthUser> list = findByOAuthApplicationId(
			oAuthApplicationId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the o auth users before and after the current o auth user in the ordered set where oAuthApplicationId = &#63;.
	 *
	 * @param oAuthUserId the primary key of the current o auth user
	 * @param oAuthApplicationId the o auth application ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth user
	 * @throws NoSuchUserException if a o auth user with the primary key could not be found
	 */
	@Override
	public OAuthUser[] findByOAuthApplicationId_PrevAndNext(
			long oAuthUserId, long oAuthApplicationId,
			OrderByComparator<OAuthUser> orderByComparator)
		throws NoSuchUserException {

		OAuthUser oAuthUser = findByPrimaryKey(oAuthUserId);

		Session session = null;

		try {
			session = openSession();

			OAuthUser[] array = new OAuthUserImpl[3];

			array[0] = getByOAuthApplicationId_PrevAndNext(
				session, oAuthUser, oAuthApplicationId, orderByComparator,
				true);

			array[1] = oAuthUser;

			array[2] = getByOAuthApplicationId_PrevAndNext(
				session, oAuthUser, oAuthApplicationId, orderByComparator,
				false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected OAuthUser getByOAuthApplicationId_PrevAndNext(
		Session session, OAuthUser oAuthUser, long oAuthApplicationId,
		OrderByComparator<OAuthUser> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_OAUTHUSER_WHERE);

		query.append(_FINDER_COLUMN_OAUTHAPPLICATIONID_OAUTHAPPLICATIONID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(OAuthUserModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(oAuthApplicationId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(oAuthUser)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<OAuthUser> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the o auth users where oAuthApplicationId = &#63; from the database.
	 *
	 * @param oAuthApplicationId the o auth application ID
	 */
	@Override
	public void removeByOAuthApplicationId(long oAuthApplicationId) {
		for (OAuthUser oAuthUser :
				findByOAuthApplicationId(
					oAuthApplicationId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(oAuthUser);
		}
	}

	/**
	 * Returns the number of o auth users where oAuthApplicationId = &#63;.
	 *
	 * @param oAuthApplicationId the o auth application ID
	 * @return the number of matching o auth users
	 */
	@Override
	public int countByOAuthApplicationId(long oAuthApplicationId) {
		FinderPath finderPath = _finderPathCountByOAuthApplicationId;

		Object[] finderArgs = new Object[] {oAuthApplicationId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_OAUTHUSER_WHERE);

			query.append(
				_FINDER_COLUMN_OAUTHAPPLICATIONID_OAUTHAPPLICATIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(oAuthApplicationId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String
		_FINDER_COLUMN_OAUTHAPPLICATIONID_OAUTHAPPLICATIONID_2 =
			"oAuthUser.oAuthApplicationId = ?";

	private FinderPath _finderPathFetchByAccessToken;
	private FinderPath _finderPathCountByAccessToken;

	/**
	 * Returns the o auth user where accessToken = &#63; or throws a <code>NoSuchUserException</code> if it could not be found.
	 *
	 * @param accessToken the access token
	 * @return the matching o auth user
	 * @throws NoSuchUserException if a matching o auth user could not be found
	 */
	@Override
	public OAuthUser findByAccessToken(String accessToken)
		throws NoSuchUserException {

		OAuthUser oAuthUser = fetchByAccessToken(accessToken);

		if (oAuthUser == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("accessToken=");
			msg.append(accessToken);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchUserException(msg.toString());
		}

		return oAuthUser;
	}

	/**
	 * Returns the o auth user where accessToken = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param accessToken the access token
	 * @return the matching o auth user, or <code>null</code> if a matching o auth user could not be found
	 */
	@Override
	public OAuthUser fetchByAccessToken(String accessToken) {
		return fetchByAccessToken(accessToken, true);
	}

	/**
	 * Returns the o auth user where accessToken = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param accessToken the access token
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching o auth user, or <code>null</code> if a matching o auth user could not be found
	 */
	@Override
	public OAuthUser fetchByAccessToken(
		String accessToken, boolean useFinderCache) {

		accessToken = Objects.toString(accessToken, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {accessToken};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByAccessToken, finderArgs, this);
		}

		if (result instanceof OAuthUser) {
			OAuthUser oAuthUser = (OAuthUser)result;

			if (!Objects.equals(accessToken, oAuthUser.getAccessToken())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_OAUTHUSER_WHERE);

			boolean bindAccessToken = false;

			if (accessToken.isEmpty()) {
				query.append(_FINDER_COLUMN_ACCESSTOKEN_ACCESSTOKEN_3);
			}
			else {
				bindAccessToken = true;

				query.append(_FINDER_COLUMN_ACCESSTOKEN_ACCESSTOKEN_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindAccessToken) {
					qPos.add(accessToken);
				}

				List<OAuthUser> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByAccessToken, finderArgs, list);
					}
				}
				else {
					OAuthUser oAuthUser = list.get(0);

					result = oAuthUser;

					cacheResult(oAuthUser);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByAccessToken, finderArgs);
				}

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
			return (OAuthUser)result;
		}
	}

	/**
	 * Removes the o auth user where accessToken = &#63; from the database.
	 *
	 * @param accessToken the access token
	 * @return the o auth user that was removed
	 */
	@Override
	public OAuthUser removeByAccessToken(String accessToken)
		throws NoSuchUserException {

		OAuthUser oAuthUser = findByAccessToken(accessToken);

		return remove(oAuthUser);
	}

	/**
	 * Returns the number of o auth users where accessToken = &#63;.
	 *
	 * @param accessToken the access token
	 * @return the number of matching o auth users
	 */
	@Override
	public int countByAccessToken(String accessToken) {
		accessToken = Objects.toString(accessToken, "");

		FinderPath finderPath = _finderPathCountByAccessToken;

		Object[] finderArgs = new Object[] {accessToken};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_OAUTHUSER_WHERE);

			boolean bindAccessToken = false;

			if (accessToken.isEmpty()) {
				query.append(_FINDER_COLUMN_ACCESSTOKEN_ACCESSTOKEN_3);
			}
			else {
				bindAccessToken = true;

				query.append(_FINDER_COLUMN_ACCESSTOKEN_ACCESSTOKEN_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindAccessToken) {
					qPos.add(accessToken);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_ACCESSTOKEN_ACCESSTOKEN_2 =
		"oAuthUser.accessToken = ?";

	private static final String _FINDER_COLUMN_ACCESSTOKEN_ACCESSTOKEN_3 =
		"(oAuthUser.accessToken IS NULL OR oAuthUser.accessToken = '')";

	private FinderPath _finderPathFetchByU_OAI;
	private FinderPath _finderPathCountByU_OAI;

	/**
	 * Returns the o auth user where userId = &#63; and oAuthApplicationId = &#63; or throws a <code>NoSuchUserException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @param oAuthApplicationId the o auth application ID
	 * @return the matching o auth user
	 * @throws NoSuchUserException if a matching o auth user could not be found
	 */
	@Override
	public OAuthUser findByU_OAI(long userId, long oAuthApplicationId)
		throws NoSuchUserException {

		OAuthUser oAuthUser = fetchByU_OAI(userId, oAuthApplicationId);

		if (oAuthUser == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", oAuthApplicationId=");
			msg.append(oAuthApplicationId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchUserException(msg.toString());
		}

		return oAuthUser;
	}

	/**
	 * Returns the o auth user where userId = &#63; and oAuthApplicationId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param oAuthApplicationId the o auth application ID
	 * @return the matching o auth user, or <code>null</code> if a matching o auth user could not be found
	 */
	@Override
	public OAuthUser fetchByU_OAI(long userId, long oAuthApplicationId) {
		return fetchByU_OAI(userId, oAuthApplicationId, true);
	}

	/**
	 * Returns the o auth user where userId = &#63; and oAuthApplicationId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param oAuthApplicationId the o auth application ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching o auth user, or <code>null</code> if a matching o auth user could not be found
	 */
	@Override
	public OAuthUser fetchByU_OAI(
		long userId, long oAuthApplicationId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {userId, oAuthApplicationId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByU_OAI, finderArgs, this);
		}

		if (result instanceof OAuthUser) {
			OAuthUser oAuthUser = (OAuthUser)result;

			if ((userId != oAuthUser.getUserId()) ||
				(oAuthApplicationId != oAuthUser.getOAuthApplicationId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_OAUTHUSER_WHERE);

			query.append(_FINDER_COLUMN_U_OAI_USERID_2);

			query.append(_FINDER_COLUMN_U_OAI_OAUTHAPPLICATIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(oAuthApplicationId);

				List<OAuthUser> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByU_OAI, finderArgs, list);
					}
				}
				else {
					OAuthUser oAuthUser = list.get(0);

					result = oAuthUser;

					cacheResult(oAuthUser);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByU_OAI, finderArgs);
				}

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
			return (OAuthUser)result;
		}
	}

	/**
	 * Removes the o auth user where userId = &#63; and oAuthApplicationId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param oAuthApplicationId the o auth application ID
	 * @return the o auth user that was removed
	 */
	@Override
	public OAuthUser removeByU_OAI(long userId, long oAuthApplicationId)
		throws NoSuchUserException {

		OAuthUser oAuthUser = findByU_OAI(userId, oAuthApplicationId);

		return remove(oAuthUser);
	}

	/**
	 * Returns the number of o auth users where userId = &#63; and oAuthApplicationId = &#63;.
	 *
	 * @param userId the user ID
	 * @param oAuthApplicationId the o auth application ID
	 * @return the number of matching o auth users
	 */
	@Override
	public int countByU_OAI(long userId, long oAuthApplicationId) {
		FinderPath finderPath = _finderPathCountByU_OAI;

		Object[] finderArgs = new Object[] {userId, oAuthApplicationId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_OAUTHUSER_WHERE);

			query.append(_FINDER_COLUMN_U_OAI_USERID_2);

			query.append(_FINDER_COLUMN_U_OAI_OAUTHAPPLICATIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(oAuthApplicationId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_U_OAI_USERID_2 =
		"oAuthUser.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_OAI_OAUTHAPPLICATIONID_2 =
		"oAuthUser.oAuthApplicationId = ?";

	public OAuthUserPersistenceImpl() {
		setModelClass(OAuthUser.class);

		setModelImplClass(OAuthUserImpl.class);
		setModelPKClass(long.class);
	}

	/**
	 * Caches the o auth user in the entity cache if it is enabled.
	 *
	 * @param oAuthUser the o auth user
	 */
	@Override
	public void cacheResult(OAuthUser oAuthUser) {
		entityCache.putResult(
			entityCacheEnabled, OAuthUserImpl.class, oAuthUser.getPrimaryKey(),
			oAuthUser);

		finderCache.putResult(
			_finderPathFetchByAccessToken,
			new Object[] {oAuthUser.getAccessToken()}, oAuthUser);

		finderCache.putResult(
			_finderPathFetchByU_OAI,
			new Object[] {
				oAuthUser.getUserId(), oAuthUser.getOAuthApplicationId()
			},
			oAuthUser);

		oAuthUser.resetOriginalValues();
	}

	/**
	 * Caches the o auth users in the entity cache if it is enabled.
	 *
	 * @param oAuthUsers the o auth users
	 */
	@Override
	public void cacheResult(List<OAuthUser> oAuthUsers) {
		for (OAuthUser oAuthUser : oAuthUsers) {
			if (entityCache.getResult(
					entityCacheEnabled, OAuthUserImpl.class,
					oAuthUser.getPrimaryKey()) == null) {

				cacheResult(oAuthUser);
			}
			else {
				oAuthUser.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all o auth users.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(OAuthUserImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the o auth user.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(OAuthUser oAuthUser) {
		entityCache.removeResult(
			entityCacheEnabled, OAuthUserImpl.class, oAuthUser.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((OAuthUserModelImpl)oAuthUser, true);
	}

	@Override
	public void clearCache(List<OAuthUser> oAuthUsers) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (OAuthUser oAuthUser : oAuthUsers) {
			entityCache.removeResult(
				entityCacheEnabled, OAuthUserImpl.class,
				oAuthUser.getPrimaryKey());

			clearUniqueFindersCache((OAuthUserModelImpl)oAuthUser, true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				entityCacheEnabled, OAuthUserImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		OAuthUserModelImpl oAuthUserModelImpl) {

		Object[] args = new Object[] {oAuthUserModelImpl.getAccessToken()};

		finderCache.putResult(
			_finderPathCountByAccessToken, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByAccessToken, args, oAuthUserModelImpl, false);

		args = new Object[] {
			oAuthUserModelImpl.getUserId(),
			oAuthUserModelImpl.getOAuthApplicationId()
		};

		finderCache.putResult(
			_finderPathCountByU_OAI, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByU_OAI, args, oAuthUserModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		OAuthUserModelImpl oAuthUserModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {oAuthUserModelImpl.getAccessToken()};

			finderCache.removeResult(_finderPathCountByAccessToken, args);
			finderCache.removeResult(_finderPathFetchByAccessToken, args);
		}

		if ((oAuthUserModelImpl.getColumnBitmask() &
			 _finderPathFetchByAccessToken.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				oAuthUserModelImpl.getOriginalAccessToken()
			};

			finderCache.removeResult(_finderPathCountByAccessToken, args);
			finderCache.removeResult(_finderPathFetchByAccessToken, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				oAuthUserModelImpl.getUserId(),
				oAuthUserModelImpl.getOAuthApplicationId()
			};

			finderCache.removeResult(_finderPathCountByU_OAI, args);
			finderCache.removeResult(_finderPathFetchByU_OAI, args);
		}

		if ((oAuthUserModelImpl.getColumnBitmask() &
			 _finderPathFetchByU_OAI.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				oAuthUserModelImpl.getOriginalUserId(),
				oAuthUserModelImpl.getOriginalOAuthApplicationId()
			};

			finderCache.removeResult(_finderPathCountByU_OAI, args);
			finderCache.removeResult(_finderPathFetchByU_OAI, args);
		}
	}

	/**
	 * Creates a new o auth user with the primary key. Does not add the o auth user to the database.
	 *
	 * @param oAuthUserId the primary key for the new o auth user
	 * @return the new o auth user
	 */
	@Override
	public OAuthUser create(long oAuthUserId) {
		OAuthUser oAuthUser = new OAuthUserImpl();

		oAuthUser.setNew(true);
		oAuthUser.setPrimaryKey(oAuthUserId);

		oAuthUser.setCompanyId(CompanyThreadLocal.getCompanyId());

		return oAuthUser;
	}

	/**
	 * Removes the o auth user with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param oAuthUserId the primary key of the o auth user
	 * @return the o auth user that was removed
	 * @throws NoSuchUserException if a o auth user with the primary key could not be found
	 */
	@Override
	public OAuthUser remove(long oAuthUserId) throws NoSuchUserException {
		return remove((Serializable)oAuthUserId);
	}

	/**
	 * Removes the o auth user with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the o auth user
	 * @return the o auth user that was removed
	 * @throws NoSuchUserException if a o auth user with the primary key could not be found
	 */
	@Override
	public OAuthUser remove(Serializable primaryKey)
		throws NoSuchUserException {

		Session session = null;

		try {
			session = openSession();

			OAuthUser oAuthUser = (OAuthUser)session.get(
				OAuthUserImpl.class, primaryKey);

			if (oAuthUser == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchUserException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(oAuthUser);
		}
		catch (NoSuchUserException nsee) {
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
	protected OAuthUser removeImpl(OAuthUser oAuthUser) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(oAuthUser)) {
				oAuthUser = (OAuthUser)session.get(
					OAuthUserImpl.class, oAuthUser.getPrimaryKeyObj());
			}

			if (oAuthUser != null) {
				session.delete(oAuthUser);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (oAuthUser != null) {
			clearCache(oAuthUser);
		}

		return oAuthUser;
	}

	@Override
	public OAuthUser updateImpl(OAuthUser oAuthUser) {
		boolean isNew = oAuthUser.isNew();

		if (!(oAuthUser instanceof OAuthUserModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(oAuthUser.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(oAuthUser);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in oAuthUser proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom OAuthUser implementation " +
					oAuthUser.getClass());
		}

		OAuthUserModelImpl oAuthUserModelImpl = (OAuthUserModelImpl)oAuthUser;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (oAuthUser.getCreateDate() == null)) {
			if (serviceContext == null) {
				oAuthUser.setCreateDate(now);
			}
			else {
				oAuthUser.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!oAuthUserModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				oAuthUser.setModifiedDate(now);
			}
			else {
				oAuthUser.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (oAuthUser.isNew()) {
				session.save(oAuthUser);

				oAuthUser.setNew(false);
			}
			else {
				oAuthUser = (OAuthUser)session.merge(oAuthUser);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!_columnBitmaskEnabled) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {oAuthUserModelImpl.getUserId()};

			finderCache.removeResult(_finderPathCountByUserId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUserId, args);

			args = new Object[] {oAuthUserModelImpl.getOAuthApplicationId()};

			finderCache.removeResult(
				_finderPathCountByOAuthApplicationId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByOAuthApplicationId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((oAuthUserModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUserId.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					oAuthUserModelImpl.getOriginalUserId()
				};

				finderCache.removeResult(_finderPathCountByUserId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUserId, args);

				args = new Object[] {oAuthUserModelImpl.getUserId()};

				finderCache.removeResult(_finderPathCountByUserId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUserId, args);
			}

			if ((oAuthUserModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByOAuthApplicationId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					oAuthUserModelImpl.getOriginalOAuthApplicationId()
				};

				finderCache.removeResult(
					_finderPathCountByOAuthApplicationId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByOAuthApplicationId, args);

				args = new Object[] {
					oAuthUserModelImpl.getOAuthApplicationId()
				};

				finderCache.removeResult(
					_finderPathCountByOAuthApplicationId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByOAuthApplicationId, args);
			}
		}

		entityCache.putResult(
			entityCacheEnabled, OAuthUserImpl.class, oAuthUser.getPrimaryKey(),
			oAuthUser, false);

		clearUniqueFindersCache(oAuthUserModelImpl, false);
		cacheUniqueFindersCache(oAuthUserModelImpl);

		oAuthUser.resetOriginalValues();

		return oAuthUser;
	}

	/**
	 * Returns the o auth user with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the o auth user
	 * @return the o auth user
	 * @throws NoSuchUserException if a o auth user with the primary key could not be found
	 */
	@Override
	public OAuthUser findByPrimaryKey(Serializable primaryKey)
		throws NoSuchUserException {

		OAuthUser oAuthUser = fetchByPrimaryKey(primaryKey);

		if (oAuthUser == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchUserException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return oAuthUser;
	}

	/**
	 * Returns the o auth user with the primary key or throws a <code>NoSuchUserException</code> if it could not be found.
	 *
	 * @param oAuthUserId the primary key of the o auth user
	 * @return the o auth user
	 * @throws NoSuchUserException if a o auth user with the primary key could not be found
	 */
	@Override
	public OAuthUser findByPrimaryKey(long oAuthUserId)
		throws NoSuchUserException {

		return findByPrimaryKey((Serializable)oAuthUserId);
	}

	/**
	 * Returns the o auth user with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param oAuthUserId the primary key of the o auth user
	 * @return the o auth user, or <code>null</code> if a o auth user with the primary key could not be found
	 */
	@Override
	public OAuthUser fetchByPrimaryKey(long oAuthUserId) {
		return fetchByPrimaryKey((Serializable)oAuthUserId);
	}

	/**
	 * Returns all the o auth users.
	 *
	 * @return the o auth users
	 */
	@Override
	public List<OAuthUser> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthUserModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth users
	 * @param end the upper bound of the range of o auth users (not inclusive)
	 * @return the range of o auth users
	 */
	@Override
	public List<OAuthUser> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthUserModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth users
	 * @param end the upper bound of the range of o auth users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of o auth users
	 */
	@Override
	public List<OAuthUser> findAll(
		int start, int end, OrderByComparator<OAuthUser> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthUserModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth users
	 * @param end the upper bound of the range of o auth users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of o auth users
	 */
	@Override
	public List<OAuthUser> findAll(
		int start, int end, OrderByComparator<OAuthUser> orderByComparator,
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

		List<OAuthUser> list = null;

		if (useFinderCache) {
			list = (List<OAuthUser>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_OAUTHUSER);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_OAUTHUSER;

				sql = sql.concat(OAuthUserModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<OAuthUser>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the o auth users from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (OAuthUser oAuthUser : findAll()) {
			remove(oAuthUser);
		}
	}

	/**
	 * Returns the number of o auth users.
	 *
	 * @return the number of o auth users
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_OAUTHUSER);

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				finderCache.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

				throw processException(e);
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
		return "oAuthUserId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_OAUTHUSER;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return OAuthUserModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the o auth user persistence.
	 */
	@Activate
	public void activate() {
		OAuthUserModelImpl.setEntityCacheEnabled(entityCacheEnabled);
		OAuthUserModelImpl.setFinderCacheEnabled(finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, OAuthUserImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, OAuthUserImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUserId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, OAuthUserImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUserId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, OAuthUserImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] {Long.class.getName()},
			OAuthUserModelImpl.USERID_COLUMN_BITMASK);

		_finderPathCountByUserId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByOAuthApplicationId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, OAuthUserImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByOAuthApplicationId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByOAuthApplicationId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, OAuthUserImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByOAuthApplicationId", new String[] {Long.class.getName()},
			OAuthUserModelImpl.OAUTHAPPLICATIONID_COLUMN_BITMASK);

		_finderPathCountByOAuthApplicationId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByOAuthApplicationId", new String[] {Long.class.getName()});

		_finderPathFetchByAccessToken = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, OAuthUserImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByAccessToken",
			new String[] {String.class.getName()},
			OAuthUserModelImpl.ACCESSTOKEN_COLUMN_BITMASK);

		_finderPathCountByAccessToken = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByAccessToken",
			new String[] {String.class.getName()});

		_finderPathFetchByU_OAI = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, OAuthUserImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByU_OAI",
			new String[] {Long.class.getName(), Long.class.getName()},
			OAuthUserModelImpl.USERID_COLUMN_BITMASK |
			OAuthUserModelImpl.OAUTHAPPLICATIONID_COLUMN_BITMASK);

		_finderPathCountByU_OAI = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_OAI",
			new String[] {Long.class.getName(), Long.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(OAuthUserImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = OAuthPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.oauth.model.OAuthUser"),
			true);
	}

	@Override
	@Reference(
		target = OAuthPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = OAuthPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	private boolean _columnBitmaskEnabled;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_OAUTHUSER =
		"SELECT oAuthUser FROM OAuthUser oAuthUser";

	private static final String _SQL_SELECT_OAUTHUSER_WHERE =
		"SELECT oAuthUser FROM OAuthUser oAuthUser WHERE ";

	private static final String _SQL_COUNT_OAUTHUSER =
		"SELECT COUNT(oAuthUser) FROM OAuthUser oAuthUser";

	private static final String _SQL_COUNT_OAUTHUSER_WHERE =
		"SELECT COUNT(oAuthUser) FROM OAuthUser oAuthUser WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "oAuthUser.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No OAuthUser exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No OAuthUser exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		OAuthUserPersistenceImpl.class);

	static {
		try {
			Class.forName(OAuthPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new ExceptionInInitializerError(cnfe);
		}
	}

}