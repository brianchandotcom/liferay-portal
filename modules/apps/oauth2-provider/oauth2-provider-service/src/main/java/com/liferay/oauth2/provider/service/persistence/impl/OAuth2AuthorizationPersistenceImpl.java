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

package com.liferay.oauth2.provider.service.persistence.impl;

import com.liferay.oauth2.provider.exception.NoSuchOAuth2AuthorizationException;
import com.liferay.oauth2.provider.model.OAuth2Authorization;
import com.liferay.oauth2.provider.model.OAuth2ScopeGrant;
import com.liferay.oauth2.provider.model.impl.OAuth2AuthorizationImpl;
import com.liferay.oauth2.provider.model.impl.OAuth2AuthorizationModelImpl;
import com.liferay.oauth2.provider.service.persistence.OAuth2AuthorizationPersistence;
import com.liferay.oauth2.provider.service.persistence.impl.constants.OAuthTwoPersistenceConstants;
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
import com.liferay.portal.kernel.service.persistence.impl.TableMapper;
import com.liferay.portal.kernel.service.persistence.impl.TableMapperFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the o auth2 authorization service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = OAuth2AuthorizationPersistence.class)
public class OAuth2AuthorizationPersistenceImpl
	extends BasePersistenceImpl<OAuth2Authorization>
	implements OAuth2AuthorizationPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>OAuth2AuthorizationUtil</code> to access the o auth2 authorization persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		OAuth2AuthorizationImpl.class.getName();

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
	 * Returns all the o auth2 authorizations where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching o auth2 authorizations
	 */
	@Override
	public List<OAuth2Authorization> findByUserId(long userId) {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth2 authorizations where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2AuthorizationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth2 authorizations
	 * @param end the upper bound of the range of o auth2 authorizations (not inclusive)
	 * @return the range of matching o auth2 authorizations
	 */
	@Override
	public List<OAuth2Authorization> findByUserId(
		long userId, int start, int end) {

		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth2 authorizations where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2AuthorizationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth2 authorizations
	 * @param end the upper bound of the range of o auth2 authorizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth2 authorizations
	 */
	@Override
	public List<OAuth2Authorization> findByUserId(
		long userId, int start, int end,
		OrderByComparator<OAuth2Authorization> orderByComparator) {

		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth2 authorizations where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2AuthorizationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth2 authorizations
	 * @param end the upper bound of the range of o auth2 authorizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth2 authorizations
	 */
	@Override
	public List<OAuth2Authorization> findByUserId(
		long userId, int start, int end,
		OrderByComparator<OAuth2Authorization> orderByComparator,
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

		List<OAuth2Authorization> list = null;

		if (useFinderCache) {
			list = (List<OAuth2Authorization>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (OAuth2Authorization oAuth2Authorization : list) {
					if (userId != oAuth2Authorization.getUserId()) {
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

			query.append(_SQL_SELECT_OAUTH2AUTHORIZATION_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(OAuth2AuthorizationModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = (List<OAuth2Authorization>)QueryUtil.list(
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
	 * Returns the first o auth2 authorization in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 authorization
	 * @throws NoSuchOAuth2AuthorizationException if a matching o auth2 authorization could not be found
	 */
	@Override
	public OAuth2Authorization findByUserId_First(
			long userId,
			OrderByComparator<OAuth2Authorization> orderByComparator)
		throws NoSuchOAuth2AuthorizationException {

		OAuth2Authorization oAuth2Authorization = fetchByUserId_First(
			userId, orderByComparator);

		if (oAuth2Authorization != null) {
			return oAuth2Authorization;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append("}");

		throw new NoSuchOAuth2AuthorizationException(msg.toString());
	}

	/**
	 * Returns the first o auth2 authorization in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 authorization, or <code>null</code> if a matching o auth2 authorization could not be found
	 */
	@Override
	public OAuth2Authorization fetchByUserId_First(
		long userId, OrderByComparator<OAuth2Authorization> orderByComparator) {

		List<OAuth2Authorization> list = findByUserId(
			userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last o auth2 authorization in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth2 authorization
	 * @throws NoSuchOAuth2AuthorizationException if a matching o auth2 authorization could not be found
	 */
	@Override
	public OAuth2Authorization findByUserId_Last(
			long userId,
			OrderByComparator<OAuth2Authorization> orderByComparator)
		throws NoSuchOAuth2AuthorizationException {

		OAuth2Authorization oAuth2Authorization = fetchByUserId_Last(
			userId, orderByComparator);

		if (oAuth2Authorization != null) {
			return oAuth2Authorization;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append("}");

		throw new NoSuchOAuth2AuthorizationException(msg.toString());
	}

	/**
	 * Returns the last o auth2 authorization in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth2 authorization, or <code>null</code> if a matching o auth2 authorization could not be found
	 */
	@Override
	public OAuth2Authorization fetchByUserId_Last(
		long userId, OrderByComparator<OAuth2Authorization> orderByComparator) {

		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<OAuth2Authorization> list = findByUserId(
			userId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the o auth2 authorizations before and after the current o auth2 authorization in the ordered set where userId = &#63;.
	 *
	 * @param oAuth2AuthorizationId the primary key of the current o auth2 authorization
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth2 authorization
	 * @throws NoSuchOAuth2AuthorizationException if a o auth2 authorization with the primary key could not be found
	 */
	@Override
	public OAuth2Authorization[] findByUserId_PrevAndNext(
			long oAuth2AuthorizationId, long userId,
			OrderByComparator<OAuth2Authorization> orderByComparator)
		throws NoSuchOAuth2AuthorizationException {

		OAuth2Authorization oAuth2Authorization = findByPrimaryKey(
			oAuth2AuthorizationId);

		Session session = null;

		try {
			session = openSession();

			OAuth2Authorization[] array = new OAuth2AuthorizationImpl[3];

			array[0] = getByUserId_PrevAndNext(
				session, oAuth2Authorization, userId, orderByComparator, true);

			array[1] = oAuth2Authorization;

			array[2] = getByUserId_PrevAndNext(
				session, oAuth2Authorization, userId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected OAuth2Authorization getByUserId_PrevAndNext(
		Session session, OAuth2Authorization oAuth2Authorization, long userId,
		OrderByComparator<OAuth2Authorization> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_OAUTH2AUTHORIZATION_WHERE);

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
			query.append(OAuth2AuthorizationModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						oAuth2Authorization)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<OAuth2Authorization> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the o auth2 authorizations where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserId(long userId) {
		for (OAuth2Authorization oAuth2Authorization :
				findByUserId(
					userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(oAuth2Authorization);
		}
	}

	/**
	 * Returns the number of o auth2 authorizations where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching o auth2 authorizations
	 */
	@Override
	public int countByUserId(long userId) {
		FinderPath finderPath = _finderPathCountByUserId;

		Object[] finderArgs = new Object[] {userId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_OAUTH2AUTHORIZATION_WHERE);

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
		"oAuth2Authorization.userId = ?";

	private FinderPath _finderPathWithPaginationFindByOAuth2ApplicationId;
	private FinderPath _finderPathWithoutPaginationFindByOAuth2ApplicationId;
	private FinderPath _finderPathCountByOAuth2ApplicationId;

	/**
	 * Returns all the o auth2 authorizations where oAuth2ApplicationId = &#63;.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @return the matching o auth2 authorizations
	 */
	@Override
	public List<OAuth2Authorization> findByOAuth2ApplicationId(
		long oAuth2ApplicationId) {

		return findByOAuth2ApplicationId(
			oAuth2ApplicationId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth2 authorizations where oAuth2ApplicationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2AuthorizationModelImpl</code>.
	 * </p>
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param start the lower bound of the range of o auth2 authorizations
	 * @param end the upper bound of the range of o auth2 authorizations (not inclusive)
	 * @return the range of matching o auth2 authorizations
	 */
	@Override
	public List<OAuth2Authorization> findByOAuth2ApplicationId(
		long oAuth2ApplicationId, int start, int end) {

		return findByOAuth2ApplicationId(oAuth2ApplicationId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth2 authorizations where oAuth2ApplicationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2AuthorizationModelImpl</code>.
	 * </p>
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param start the lower bound of the range of o auth2 authorizations
	 * @param end the upper bound of the range of o auth2 authorizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth2 authorizations
	 */
	@Override
	public List<OAuth2Authorization> findByOAuth2ApplicationId(
		long oAuth2ApplicationId, int start, int end,
		OrderByComparator<OAuth2Authorization> orderByComparator) {

		return findByOAuth2ApplicationId(
			oAuth2ApplicationId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth2 authorizations where oAuth2ApplicationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2AuthorizationModelImpl</code>.
	 * </p>
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param start the lower bound of the range of o auth2 authorizations
	 * @param end the upper bound of the range of o auth2 authorizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth2 authorizations
	 */
	@Override
	public List<OAuth2Authorization> findByOAuth2ApplicationId(
		long oAuth2ApplicationId, int start, int end,
		OrderByComparator<OAuth2Authorization> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByOAuth2ApplicationId;
				finderArgs = new Object[] {oAuth2ApplicationId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByOAuth2ApplicationId;
			finderArgs = new Object[] {
				oAuth2ApplicationId, start, end, orderByComparator
			};
		}

		List<OAuth2Authorization> list = null;

		if (useFinderCache) {
			list = (List<OAuth2Authorization>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (OAuth2Authorization oAuth2Authorization : list) {
					if (oAuth2ApplicationId !=
							oAuth2Authorization.getOAuth2ApplicationId()) {

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

			query.append(_SQL_SELECT_OAUTH2AUTHORIZATION_WHERE);

			query.append(
				_FINDER_COLUMN_OAUTH2APPLICATIONID_OAUTH2APPLICATIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(OAuth2AuthorizationModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(oAuth2ApplicationId);

				list = (List<OAuth2Authorization>)QueryUtil.list(
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
	 * Returns the first o auth2 authorization in the ordered set where oAuth2ApplicationId = &#63;.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 authorization
	 * @throws NoSuchOAuth2AuthorizationException if a matching o auth2 authorization could not be found
	 */
	@Override
	public OAuth2Authorization findByOAuth2ApplicationId_First(
			long oAuth2ApplicationId,
			OrderByComparator<OAuth2Authorization> orderByComparator)
		throws NoSuchOAuth2AuthorizationException {

		OAuth2Authorization oAuth2Authorization =
			fetchByOAuth2ApplicationId_First(
				oAuth2ApplicationId, orderByComparator);

		if (oAuth2Authorization != null) {
			return oAuth2Authorization;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("oAuth2ApplicationId=");
		msg.append(oAuth2ApplicationId);

		msg.append("}");

		throw new NoSuchOAuth2AuthorizationException(msg.toString());
	}

	/**
	 * Returns the first o auth2 authorization in the ordered set where oAuth2ApplicationId = &#63;.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 authorization, or <code>null</code> if a matching o auth2 authorization could not be found
	 */
	@Override
	public OAuth2Authorization fetchByOAuth2ApplicationId_First(
		long oAuth2ApplicationId,
		OrderByComparator<OAuth2Authorization> orderByComparator) {

		List<OAuth2Authorization> list = findByOAuth2ApplicationId(
			oAuth2ApplicationId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last o auth2 authorization in the ordered set where oAuth2ApplicationId = &#63;.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth2 authorization
	 * @throws NoSuchOAuth2AuthorizationException if a matching o auth2 authorization could not be found
	 */
	@Override
	public OAuth2Authorization findByOAuth2ApplicationId_Last(
			long oAuth2ApplicationId,
			OrderByComparator<OAuth2Authorization> orderByComparator)
		throws NoSuchOAuth2AuthorizationException {

		OAuth2Authorization oAuth2Authorization =
			fetchByOAuth2ApplicationId_Last(
				oAuth2ApplicationId, orderByComparator);

		if (oAuth2Authorization != null) {
			return oAuth2Authorization;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("oAuth2ApplicationId=");
		msg.append(oAuth2ApplicationId);

		msg.append("}");

		throw new NoSuchOAuth2AuthorizationException(msg.toString());
	}

	/**
	 * Returns the last o auth2 authorization in the ordered set where oAuth2ApplicationId = &#63;.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth2 authorization, or <code>null</code> if a matching o auth2 authorization could not be found
	 */
	@Override
	public OAuth2Authorization fetchByOAuth2ApplicationId_Last(
		long oAuth2ApplicationId,
		OrderByComparator<OAuth2Authorization> orderByComparator) {

		int count = countByOAuth2ApplicationId(oAuth2ApplicationId);

		if (count == 0) {
			return null;
		}

		List<OAuth2Authorization> list = findByOAuth2ApplicationId(
			oAuth2ApplicationId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the o auth2 authorizations before and after the current o auth2 authorization in the ordered set where oAuth2ApplicationId = &#63;.
	 *
	 * @param oAuth2AuthorizationId the primary key of the current o auth2 authorization
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth2 authorization
	 * @throws NoSuchOAuth2AuthorizationException if a o auth2 authorization with the primary key could not be found
	 */
	@Override
	public OAuth2Authorization[] findByOAuth2ApplicationId_PrevAndNext(
			long oAuth2AuthorizationId, long oAuth2ApplicationId,
			OrderByComparator<OAuth2Authorization> orderByComparator)
		throws NoSuchOAuth2AuthorizationException {

		OAuth2Authorization oAuth2Authorization = findByPrimaryKey(
			oAuth2AuthorizationId);

		Session session = null;

		try {
			session = openSession();

			OAuth2Authorization[] array = new OAuth2AuthorizationImpl[3];

			array[0] = getByOAuth2ApplicationId_PrevAndNext(
				session, oAuth2Authorization, oAuth2ApplicationId,
				orderByComparator, true);

			array[1] = oAuth2Authorization;

			array[2] = getByOAuth2ApplicationId_PrevAndNext(
				session, oAuth2Authorization, oAuth2ApplicationId,
				orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected OAuth2Authorization getByOAuth2ApplicationId_PrevAndNext(
		Session session, OAuth2Authorization oAuth2Authorization,
		long oAuth2ApplicationId,
		OrderByComparator<OAuth2Authorization> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_OAUTH2AUTHORIZATION_WHERE);

		query.append(_FINDER_COLUMN_OAUTH2APPLICATIONID_OAUTH2APPLICATIONID_2);

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
			query.append(OAuth2AuthorizationModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(oAuth2ApplicationId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						oAuth2Authorization)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<OAuth2Authorization> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the o auth2 authorizations where oAuth2ApplicationId = &#63; from the database.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 */
	@Override
	public void removeByOAuth2ApplicationId(long oAuth2ApplicationId) {
		for (OAuth2Authorization oAuth2Authorization :
				findByOAuth2ApplicationId(
					oAuth2ApplicationId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(oAuth2Authorization);
		}
	}

	/**
	 * Returns the number of o auth2 authorizations where oAuth2ApplicationId = &#63;.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID
	 * @return the number of matching o auth2 authorizations
	 */
	@Override
	public int countByOAuth2ApplicationId(long oAuth2ApplicationId) {
		FinderPath finderPath = _finderPathCountByOAuth2ApplicationId;

		Object[] finderArgs = new Object[] {oAuth2ApplicationId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_OAUTH2AUTHORIZATION_WHERE);

			query.append(
				_FINDER_COLUMN_OAUTH2APPLICATIONID_OAUTH2APPLICATIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(oAuth2ApplicationId);

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
		_FINDER_COLUMN_OAUTH2APPLICATIONID_OAUTH2APPLICATIONID_2 =
			"oAuth2Authorization.oAuth2ApplicationId = ?";

	private FinderPath _finderPathWithPaginationFindByAccessTokenContentHash;
	private FinderPath _finderPathWithoutPaginationFindByAccessTokenContentHash;
	private FinderPath _finderPathCountByAccessTokenContentHash;

	/**
	 * Returns all the o auth2 authorizations where accessTokenContentHash = &#63;.
	 *
	 * @param accessTokenContentHash the access token content hash
	 * @return the matching o auth2 authorizations
	 */
	@Override
	public List<OAuth2Authorization> findByAccessTokenContentHash(
		long accessTokenContentHash) {

		return findByAccessTokenContentHash(
			accessTokenContentHash, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth2 authorizations where accessTokenContentHash = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2AuthorizationModelImpl</code>.
	 * </p>
	 *
	 * @param accessTokenContentHash the access token content hash
	 * @param start the lower bound of the range of o auth2 authorizations
	 * @param end the upper bound of the range of o auth2 authorizations (not inclusive)
	 * @return the range of matching o auth2 authorizations
	 */
	@Override
	public List<OAuth2Authorization> findByAccessTokenContentHash(
		long accessTokenContentHash, int start, int end) {

		return findByAccessTokenContentHash(
			accessTokenContentHash, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth2 authorizations where accessTokenContentHash = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2AuthorizationModelImpl</code>.
	 * </p>
	 *
	 * @param accessTokenContentHash the access token content hash
	 * @param start the lower bound of the range of o auth2 authorizations
	 * @param end the upper bound of the range of o auth2 authorizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth2 authorizations
	 */
	@Override
	public List<OAuth2Authorization> findByAccessTokenContentHash(
		long accessTokenContentHash, int start, int end,
		OrderByComparator<OAuth2Authorization> orderByComparator) {

		return findByAccessTokenContentHash(
			accessTokenContentHash, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth2 authorizations where accessTokenContentHash = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2AuthorizationModelImpl</code>.
	 * </p>
	 *
	 * @param accessTokenContentHash the access token content hash
	 * @param start the lower bound of the range of o auth2 authorizations
	 * @param end the upper bound of the range of o auth2 authorizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth2 authorizations
	 */
	@Override
	public List<OAuth2Authorization> findByAccessTokenContentHash(
		long accessTokenContentHash, int start, int end,
		OrderByComparator<OAuth2Authorization> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByAccessTokenContentHash;
				finderArgs = new Object[] {accessTokenContentHash};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByAccessTokenContentHash;
			finderArgs = new Object[] {
				accessTokenContentHash, start, end, orderByComparator
			};
		}

		List<OAuth2Authorization> list = null;

		if (useFinderCache) {
			list = (List<OAuth2Authorization>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (OAuth2Authorization oAuth2Authorization : list) {
					if (accessTokenContentHash !=
							oAuth2Authorization.getAccessTokenContentHash()) {

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

			query.append(_SQL_SELECT_OAUTH2AUTHORIZATION_WHERE);

			query.append(
				_FINDER_COLUMN_ACCESSTOKENCONTENTHASH_ACCESSTOKENCONTENTHASH_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(OAuth2AuthorizationModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(accessTokenContentHash);

				list = (List<OAuth2Authorization>)QueryUtil.list(
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
	 * Returns the first o auth2 authorization in the ordered set where accessTokenContentHash = &#63;.
	 *
	 * @param accessTokenContentHash the access token content hash
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 authorization
	 * @throws NoSuchOAuth2AuthorizationException if a matching o auth2 authorization could not be found
	 */
	@Override
	public OAuth2Authorization findByAccessTokenContentHash_First(
			long accessTokenContentHash,
			OrderByComparator<OAuth2Authorization> orderByComparator)
		throws NoSuchOAuth2AuthorizationException {

		OAuth2Authorization oAuth2Authorization =
			fetchByAccessTokenContentHash_First(
				accessTokenContentHash, orderByComparator);

		if (oAuth2Authorization != null) {
			return oAuth2Authorization;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("accessTokenContentHash=");
		msg.append(accessTokenContentHash);

		msg.append("}");

		throw new NoSuchOAuth2AuthorizationException(msg.toString());
	}

	/**
	 * Returns the first o auth2 authorization in the ordered set where accessTokenContentHash = &#63;.
	 *
	 * @param accessTokenContentHash the access token content hash
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 authorization, or <code>null</code> if a matching o auth2 authorization could not be found
	 */
	@Override
	public OAuth2Authorization fetchByAccessTokenContentHash_First(
		long accessTokenContentHash,
		OrderByComparator<OAuth2Authorization> orderByComparator) {

		List<OAuth2Authorization> list = findByAccessTokenContentHash(
			accessTokenContentHash, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last o auth2 authorization in the ordered set where accessTokenContentHash = &#63;.
	 *
	 * @param accessTokenContentHash the access token content hash
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth2 authorization
	 * @throws NoSuchOAuth2AuthorizationException if a matching o auth2 authorization could not be found
	 */
	@Override
	public OAuth2Authorization findByAccessTokenContentHash_Last(
			long accessTokenContentHash,
			OrderByComparator<OAuth2Authorization> orderByComparator)
		throws NoSuchOAuth2AuthorizationException {

		OAuth2Authorization oAuth2Authorization =
			fetchByAccessTokenContentHash_Last(
				accessTokenContentHash, orderByComparator);

		if (oAuth2Authorization != null) {
			return oAuth2Authorization;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("accessTokenContentHash=");
		msg.append(accessTokenContentHash);

		msg.append("}");

		throw new NoSuchOAuth2AuthorizationException(msg.toString());
	}

	/**
	 * Returns the last o auth2 authorization in the ordered set where accessTokenContentHash = &#63;.
	 *
	 * @param accessTokenContentHash the access token content hash
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth2 authorization, or <code>null</code> if a matching o auth2 authorization could not be found
	 */
	@Override
	public OAuth2Authorization fetchByAccessTokenContentHash_Last(
		long accessTokenContentHash,
		OrderByComparator<OAuth2Authorization> orderByComparator) {

		int count = countByAccessTokenContentHash(accessTokenContentHash);

		if (count == 0) {
			return null;
		}

		List<OAuth2Authorization> list = findByAccessTokenContentHash(
			accessTokenContentHash, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the o auth2 authorizations before and after the current o auth2 authorization in the ordered set where accessTokenContentHash = &#63;.
	 *
	 * @param oAuth2AuthorizationId the primary key of the current o auth2 authorization
	 * @param accessTokenContentHash the access token content hash
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth2 authorization
	 * @throws NoSuchOAuth2AuthorizationException if a o auth2 authorization with the primary key could not be found
	 */
	@Override
	public OAuth2Authorization[] findByAccessTokenContentHash_PrevAndNext(
			long oAuth2AuthorizationId, long accessTokenContentHash,
			OrderByComparator<OAuth2Authorization> orderByComparator)
		throws NoSuchOAuth2AuthorizationException {

		OAuth2Authorization oAuth2Authorization = findByPrimaryKey(
			oAuth2AuthorizationId);

		Session session = null;

		try {
			session = openSession();

			OAuth2Authorization[] array = new OAuth2AuthorizationImpl[3];

			array[0] = getByAccessTokenContentHash_PrevAndNext(
				session, oAuth2Authorization, accessTokenContentHash,
				orderByComparator, true);

			array[1] = oAuth2Authorization;

			array[2] = getByAccessTokenContentHash_PrevAndNext(
				session, oAuth2Authorization, accessTokenContentHash,
				orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected OAuth2Authorization getByAccessTokenContentHash_PrevAndNext(
		Session session, OAuth2Authorization oAuth2Authorization,
		long accessTokenContentHash,
		OrderByComparator<OAuth2Authorization> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_OAUTH2AUTHORIZATION_WHERE);

		query.append(
			_FINDER_COLUMN_ACCESSTOKENCONTENTHASH_ACCESSTOKENCONTENTHASH_2);

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
			query.append(OAuth2AuthorizationModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(accessTokenContentHash);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						oAuth2Authorization)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<OAuth2Authorization> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the o auth2 authorizations where accessTokenContentHash = &#63; from the database.
	 *
	 * @param accessTokenContentHash the access token content hash
	 */
	@Override
	public void removeByAccessTokenContentHash(long accessTokenContentHash) {
		for (OAuth2Authorization oAuth2Authorization :
				findByAccessTokenContentHash(
					accessTokenContentHash, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(oAuth2Authorization);
		}
	}

	/**
	 * Returns the number of o auth2 authorizations where accessTokenContentHash = &#63;.
	 *
	 * @param accessTokenContentHash the access token content hash
	 * @return the number of matching o auth2 authorizations
	 */
	@Override
	public int countByAccessTokenContentHash(long accessTokenContentHash) {
		FinderPath finderPath = _finderPathCountByAccessTokenContentHash;

		Object[] finderArgs = new Object[] {accessTokenContentHash};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_OAUTH2AUTHORIZATION_WHERE);

			query.append(
				_FINDER_COLUMN_ACCESSTOKENCONTENTHASH_ACCESSTOKENCONTENTHASH_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(accessTokenContentHash);

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
		_FINDER_COLUMN_ACCESSTOKENCONTENTHASH_ACCESSTOKENCONTENTHASH_2 =
			"oAuth2Authorization.accessTokenContentHash = ?";

	private FinderPath _finderPathWithPaginationFindByRefreshTokenContentHash;
	private FinderPath
		_finderPathWithoutPaginationFindByRefreshTokenContentHash;
	private FinderPath _finderPathCountByRefreshTokenContentHash;

	/**
	 * Returns all the o auth2 authorizations where refreshTokenContentHash = &#63;.
	 *
	 * @param refreshTokenContentHash the refresh token content hash
	 * @return the matching o auth2 authorizations
	 */
	@Override
	public List<OAuth2Authorization> findByRefreshTokenContentHash(
		long refreshTokenContentHash) {

		return findByRefreshTokenContentHash(
			refreshTokenContentHash, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the o auth2 authorizations where refreshTokenContentHash = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2AuthorizationModelImpl</code>.
	 * </p>
	 *
	 * @param refreshTokenContentHash the refresh token content hash
	 * @param start the lower bound of the range of o auth2 authorizations
	 * @param end the upper bound of the range of o auth2 authorizations (not inclusive)
	 * @return the range of matching o auth2 authorizations
	 */
	@Override
	public List<OAuth2Authorization> findByRefreshTokenContentHash(
		long refreshTokenContentHash, int start, int end) {

		return findByRefreshTokenContentHash(
			refreshTokenContentHash, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth2 authorizations where refreshTokenContentHash = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2AuthorizationModelImpl</code>.
	 * </p>
	 *
	 * @param refreshTokenContentHash the refresh token content hash
	 * @param start the lower bound of the range of o auth2 authorizations
	 * @param end the upper bound of the range of o auth2 authorizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth2 authorizations
	 */
	@Override
	public List<OAuth2Authorization> findByRefreshTokenContentHash(
		long refreshTokenContentHash, int start, int end,
		OrderByComparator<OAuth2Authorization> orderByComparator) {

		return findByRefreshTokenContentHash(
			refreshTokenContentHash, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth2 authorizations where refreshTokenContentHash = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2AuthorizationModelImpl</code>.
	 * </p>
	 *
	 * @param refreshTokenContentHash the refresh token content hash
	 * @param start the lower bound of the range of o auth2 authorizations
	 * @param end the upper bound of the range of o auth2 authorizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth2 authorizations
	 */
	@Override
	public List<OAuth2Authorization> findByRefreshTokenContentHash(
		long refreshTokenContentHash, int start, int end,
		OrderByComparator<OAuth2Authorization> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByRefreshTokenContentHash;
				finderArgs = new Object[] {refreshTokenContentHash};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByRefreshTokenContentHash;
			finderArgs = new Object[] {
				refreshTokenContentHash, start, end, orderByComparator
			};
		}

		List<OAuth2Authorization> list = null;

		if (useFinderCache) {
			list = (List<OAuth2Authorization>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (OAuth2Authorization oAuth2Authorization : list) {
					if (refreshTokenContentHash !=
							oAuth2Authorization.getRefreshTokenContentHash()) {

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

			query.append(_SQL_SELECT_OAUTH2AUTHORIZATION_WHERE);

			query.append(
				_FINDER_COLUMN_REFRESHTOKENCONTENTHASH_REFRESHTOKENCONTENTHASH_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(OAuth2AuthorizationModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(refreshTokenContentHash);

				list = (List<OAuth2Authorization>)QueryUtil.list(
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
	 * Returns the first o auth2 authorization in the ordered set where refreshTokenContentHash = &#63;.
	 *
	 * @param refreshTokenContentHash the refresh token content hash
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 authorization
	 * @throws NoSuchOAuth2AuthorizationException if a matching o auth2 authorization could not be found
	 */
	@Override
	public OAuth2Authorization findByRefreshTokenContentHash_First(
			long refreshTokenContentHash,
			OrderByComparator<OAuth2Authorization> orderByComparator)
		throws NoSuchOAuth2AuthorizationException {

		OAuth2Authorization oAuth2Authorization =
			fetchByRefreshTokenContentHash_First(
				refreshTokenContentHash, orderByComparator);

		if (oAuth2Authorization != null) {
			return oAuth2Authorization;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("refreshTokenContentHash=");
		msg.append(refreshTokenContentHash);

		msg.append("}");

		throw new NoSuchOAuth2AuthorizationException(msg.toString());
	}

	/**
	 * Returns the first o auth2 authorization in the ordered set where refreshTokenContentHash = &#63;.
	 *
	 * @param refreshTokenContentHash the refresh token content hash
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 authorization, or <code>null</code> if a matching o auth2 authorization could not be found
	 */
	@Override
	public OAuth2Authorization fetchByRefreshTokenContentHash_First(
		long refreshTokenContentHash,
		OrderByComparator<OAuth2Authorization> orderByComparator) {

		List<OAuth2Authorization> list = findByRefreshTokenContentHash(
			refreshTokenContentHash, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last o auth2 authorization in the ordered set where refreshTokenContentHash = &#63;.
	 *
	 * @param refreshTokenContentHash the refresh token content hash
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth2 authorization
	 * @throws NoSuchOAuth2AuthorizationException if a matching o auth2 authorization could not be found
	 */
	@Override
	public OAuth2Authorization findByRefreshTokenContentHash_Last(
			long refreshTokenContentHash,
			OrderByComparator<OAuth2Authorization> orderByComparator)
		throws NoSuchOAuth2AuthorizationException {

		OAuth2Authorization oAuth2Authorization =
			fetchByRefreshTokenContentHash_Last(
				refreshTokenContentHash, orderByComparator);

		if (oAuth2Authorization != null) {
			return oAuth2Authorization;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("refreshTokenContentHash=");
		msg.append(refreshTokenContentHash);

		msg.append("}");

		throw new NoSuchOAuth2AuthorizationException(msg.toString());
	}

	/**
	 * Returns the last o auth2 authorization in the ordered set where refreshTokenContentHash = &#63;.
	 *
	 * @param refreshTokenContentHash the refresh token content hash
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth2 authorization, or <code>null</code> if a matching o auth2 authorization could not be found
	 */
	@Override
	public OAuth2Authorization fetchByRefreshTokenContentHash_Last(
		long refreshTokenContentHash,
		OrderByComparator<OAuth2Authorization> orderByComparator) {

		int count = countByRefreshTokenContentHash(refreshTokenContentHash);

		if (count == 0) {
			return null;
		}

		List<OAuth2Authorization> list = findByRefreshTokenContentHash(
			refreshTokenContentHash, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the o auth2 authorizations before and after the current o auth2 authorization in the ordered set where refreshTokenContentHash = &#63;.
	 *
	 * @param oAuth2AuthorizationId the primary key of the current o auth2 authorization
	 * @param refreshTokenContentHash the refresh token content hash
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth2 authorization
	 * @throws NoSuchOAuth2AuthorizationException if a o auth2 authorization with the primary key could not be found
	 */
	@Override
	public OAuth2Authorization[] findByRefreshTokenContentHash_PrevAndNext(
			long oAuth2AuthorizationId, long refreshTokenContentHash,
			OrderByComparator<OAuth2Authorization> orderByComparator)
		throws NoSuchOAuth2AuthorizationException {

		OAuth2Authorization oAuth2Authorization = findByPrimaryKey(
			oAuth2AuthorizationId);

		Session session = null;

		try {
			session = openSession();

			OAuth2Authorization[] array = new OAuth2AuthorizationImpl[3];

			array[0] = getByRefreshTokenContentHash_PrevAndNext(
				session, oAuth2Authorization, refreshTokenContentHash,
				orderByComparator, true);

			array[1] = oAuth2Authorization;

			array[2] = getByRefreshTokenContentHash_PrevAndNext(
				session, oAuth2Authorization, refreshTokenContentHash,
				orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected OAuth2Authorization getByRefreshTokenContentHash_PrevAndNext(
		Session session, OAuth2Authorization oAuth2Authorization,
		long refreshTokenContentHash,
		OrderByComparator<OAuth2Authorization> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_OAUTH2AUTHORIZATION_WHERE);

		query.append(
			_FINDER_COLUMN_REFRESHTOKENCONTENTHASH_REFRESHTOKENCONTENTHASH_2);

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
			query.append(OAuth2AuthorizationModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(refreshTokenContentHash);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						oAuth2Authorization)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<OAuth2Authorization> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the o auth2 authorizations where refreshTokenContentHash = &#63; from the database.
	 *
	 * @param refreshTokenContentHash the refresh token content hash
	 */
	@Override
	public void removeByRefreshTokenContentHash(long refreshTokenContentHash) {
		for (OAuth2Authorization oAuth2Authorization :
				findByRefreshTokenContentHash(
					refreshTokenContentHash, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(oAuth2Authorization);
		}
	}

	/**
	 * Returns the number of o auth2 authorizations where refreshTokenContentHash = &#63;.
	 *
	 * @param refreshTokenContentHash the refresh token content hash
	 * @return the number of matching o auth2 authorizations
	 */
	@Override
	public int countByRefreshTokenContentHash(long refreshTokenContentHash) {
		FinderPath finderPath = _finderPathCountByRefreshTokenContentHash;

		Object[] finderArgs = new Object[] {refreshTokenContentHash};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_OAUTH2AUTHORIZATION_WHERE);

			query.append(
				_FINDER_COLUMN_REFRESHTOKENCONTENTHASH_REFRESHTOKENCONTENTHASH_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(refreshTokenContentHash);

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
		_FINDER_COLUMN_REFRESHTOKENCONTENTHASH_REFRESHTOKENCONTENTHASH_2 =
			"oAuth2Authorization.refreshTokenContentHash = ?";

	public OAuth2AuthorizationPersistenceImpl() {
		setModelClass(OAuth2Authorization.class);

		setModelImplClass(OAuth2AuthorizationImpl.class);
		setModelPKClass(long.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put(
			"oAuth2ApplicationScopeAliasesId", "oA2AScopeAliasesId");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the o auth2 authorization in the entity cache if it is enabled.
	 *
	 * @param oAuth2Authorization the o auth2 authorization
	 */
	@Override
	public void cacheResult(OAuth2Authorization oAuth2Authorization) {
		entityCache.putResult(
			entityCacheEnabled, OAuth2AuthorizationImpl.class,
			oAuth2Authorization.getPrimaryKey(), oAuth2Authorization);

		oAuth2Authorization.resetOriginalValues();
	}

	/**
	 * Caches the o auth2 authorizations in the entity cache if it is enabled.
	 *
	 * @param oAuth2Authorizations the o auth2 authorizations
	 */
	@Override
	public void cacheResult(List<OAuth2Authorization> oAuth2Authorizations) {
		for (OAuth2Authorization oAuth2Authorization : oAuth2Authorizations) {
			if (entityCache.getResult(
					entityCacheEnabled, OAuth2AuthorizationImpl.class,
					oAuth2Authorization.getPrimaryKey()) == null) {

				cacheResult(oAuth2Authorization);
			}
			else {
				oAuth2Authorization.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all o auth2 authorizations.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(OAuth2AuthorizationImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the o auth2 authorization.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(OAuth2Authorization oAuth2Authorization) {
		entityCache.removeResult(
			entityCacheEnabled, OAuth2AuthorizationImpl.class,
			oAuth2Authorization.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<OAuth2Authorization> oAuth2Authorizations) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (OAuth2Authorization oAuth2Authorization : oAuth2Authorizations) {
			entityCache.removeResult(
				entityCacheEnabled, OAuth2AuthorizationImpl.class,
				oAuth2Authorization.getPrimaryKey());
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				entityCacheEnabled, OAuth2AuthorizationImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new o auth2 authorization with the primary key. Does not add the o auth2 authorization to the database.
	 *
	 * @param oAuth2AuthorizationId the primary key for the new o auth2 authorization
	 * @return the new o auth2 authorization
	 */
	@Override
	public OAuth2Authorization create(long oAuth2AuthorizationId) {
		OAuth2Authorization oAuth2Authorization = new OAuth2AuthorizationImpl();

		oAuth2Authorization.setNew(true);
		oAuth2Authorization.setPrimaryKey(oAuth2AuthorizationId);

		oAuth2Authorization.setCompanyId(CompanyThreadLocal.getCompanyId());

		return oAuth2Authorization;
	}

	/**
	 * Removes the o auth2 authorization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param oAuth2AuthorizationId the primary key of the o auth2 authorization
	 * @return the o auth2 authorization that was removed
	 * @throws NoSuchOAuth2AuthorizationException if a o auth2 authorization with the primary key could not be found
	 */
	@Override
	public OAuth2Authorization remove(long oAuth2AuthorizationId)
		throws NoSuchOAuth2AuthorizationException {

		return remove((Serializable)oAuth2AuthorizationId);
	}

	/**
	 * Removes the o auth2 authorization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the o auth2 authorization
	 * @return the o auth2 authorization that was removed
	 * @throws NoSuchOAuth2AuthorizationException if a o auth2 authorization with the primary key could not be found
	 */
	@Override
	public OAuth2Authorization remove(Serializable primaryKey)
		throws NoSuchOAuth2AuthorizationException {

		Session session = null;

		try {
			session = openSession();

			OAuth2Authorization oAuth2Authorization =
				(OAuth2Authorization)session.get(
					OAuth2AuthorizationImpl.class, primaryKey);

			if (oAuth2Authorization == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchOAuth2AuthorizationException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(oAuth2Authorization);
		}
		catch (NoSuchOAuth2AuthorizationException nsee) {
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
	protected OAuth2Authorization removeImpl(
		OAuth2Authorization oAuth2Authorization) {

		oAuth2AuthorizationToOAuth2ScopeGrantTableMapper.
			deleteLeftPrimaryKeyTableMappings(
				oAuth2Authorization.getPrimaryKey());

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(oAuth2Authorization)) {
				oAuth2Authorization = (OAuth2Authorization)session.get(
					OAuth2AuthorizationImpl.class,
					oAuth2Authorization.getPrimaryKeyObj());
			}

			if (oAuth2Authorization != null) {
				session.delete(oAuth2Authorization);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (oAuth2Authorization != null) {
			clearCache(oAuth2Authorization);
		}

		return oAuth2Authorization;
	}

	@Override
	public OAuth2Authorization updateImpl(
		OAuth2Authorization oAuth2Authorization) {

		boolean isNew = oAuth2Authorization.isNew();

		if (!(oAuth2Authorization instanceof OAuth2AuthorizationModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(oAuth2Authorization.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					oAuth2Authorization);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in oAuth2Authorization proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom OAuth2Authorization implementation " +
					oAuth2Authorization.getClass());
		}

		OAuth2AuthorizationModelImpl oAuth2AuthorizationModelImpl =
			(OAuth2AuthorizationModelImpl)oAuth2Authorization;

		Session session = null;

		try {
			session = openSession();

			if (oAuth2Authorization.isNew()) {
				session.save(oAuth2Authorization);

				oAuth2Authorization.setNew(false);
			}
			else {
				oAuth2Authorization = (OAuth2Authorization)session.merge(
					oAuth2Authorization);
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
			Object[] args = new Object[] {
				oAuth2AuthorizationModelImpl.getUserId()
			};

			finderCache.removeResult(_finderPathCountByUserId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUserId, args);

			args = new Object[] {
				oAuth2AuthorizationModelImpl.getOAuth2ApplicationId()
			};

			finderCache.removeResult(
				_finderPathCountByOAuth2ApplicationId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByOAuth2ApplicationId, args);

			args = new Object[] {
				oAuth2AuthorizationModelImpl.getAccessTokenContentHash()
			};

			finderCache.removeResult(
				_finderPathCountByAccessTokenContentHash, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByAccessTokenContentHash, args);

			args = new Object[] {
				oAuth2AuthorizationModelImpl.getRefreshTokenContentHash()
			};

			finderCache.removeResult(
				_finderPathCountByRefreshTokenContentHash, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByRefreshTokenContentHash,
				args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((oAuth2AuthorizationModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUserId.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					oAuth2AuthorizationModelImpl.getOriginalUserId()
				};

				finderCache.removeResult(_finderPathCountByUserId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUserId, args);

				args = new Object[] {oAuth2AuthorizationModelImpl.getUserId()};

				finderCache.removeResult(_finderPathCountByUserId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUserId, args);
			}

			if ((oAuth2AuthorizationModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByOAuth2ApplicationId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					oAuth2AuthorizationModelImpl.
						getOriginalOAuth2ApplicationId()
				};

				finderCache.removeResult(
					_finderPathCountByOAuth2ApplicationId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByOAuth2ApplicationId,
					args);

				args = new Object[] {
					oAuth2AuthorizationModelImpl.getOAuth2ApplicationId()
				};

				finderCache.removeResult(
					_finderPathCountByOAuth2ApplicationId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByOAuth2ApplicationId,
					args);
			}

			if ((oAuth2AuthorizationModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByAccessTokenContentHash.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					oAuth2AuthorizationModelImpl.
						getOriginalAccessTokenContentHash()
				};

				finderCache.removeResult(
					_finderPathCountByAccessTokenContentHash, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByAccessTokenContentHash,
					args);

				args = new Object[] {
					oAuth2AuthorizationModelImpl.getAccessTokenContentHash()
				};

				finderCache.removeResult(
					_finderPathCountByAccessTokenContentHash, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByAccessTokenContentHash,
					args);
			}

			if ((oAuth2AuthorizationModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByRefreshTokenContentHash.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					oAuth2AuthorizationModelImpl.
						getOriginalRefreshTokenContentHash()
				};

				finderCache.removeResult(
					_finderPathCountByRefreshTokenContentHash, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByRefreshTokenContentHash,
					args);

				args = new Object[] {
					oAuth2AuthorizationModelImpl.getRefreshTokenContentHash()
				};

				finderCache.removeResult(
					_finderPathCountByRefreshTokenContentHash, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByRefreshTokenContentHash,
					args);
			}
		}

		entityCache.putResult(
			entityCacheEnabled, OAuth2AuthorizationImpl.class,
			oAuth2Authorization.getPrimaryKey(), oAuth2Authorization, false);

		oAuth2Authorization.resetOriginalValues();

		return oAuth2Authorization;
	}

	/**
	 * Returns the o auth2 authorization with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the o auth2 authorization
	 * @return the o auth2 authorization
	 * @throws NoSuchOAuth2AuthorizationException if a o auth2 authorization with the primary key could not be found
	 */
	@Override
	public OAuth2Authorization findByPrimaryKey(Serializable primaryKey)
		throws NoSuchOAuth2AuthorizationException {

		OAuth2Authorization oAuth2Authorization = fetchByPrimaryKey(primaryKey);

		if (oAuth2Authorization == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchOAuth2AuthorizationException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return oAuth2Authorization;
	}

	/**
	 * Returns the o auth2 authorization with the primary key or throws a <code>NoSuchOAuth2AuthorizationException</code> if it could not be found.
	 *
	 * @param oAuth2AuthorizationId the primary key of the o auth2 authorization
	 * @return the o auth2 authorization
	 * @throws NoSuchOAuth2AuthorizationException if a o auth2 authorization with the primary key could not be found
	 */
	@Override
	public OAuth2Authorization findByPrimaryKey(long oAuth2AuthorizationId)
		throws NoSuchOAuth2AuthorizationException {

		return findByPrimaryKey((Serializable)oAuth2AuthorizationId);
	}

	/**
	 * Returns the o auth2 authorization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param oAuth2AuthorizationId the primary key of the o auth2 authorization
	 * @return the o auth2 authorization, or <code>null</code> if a o auth2 authorization with the primary key could not be found
	 */
	@Override
	public OAuth2Authorization fetchByPrimaryKey(long oAuth2AuthorizationId) {
		return fetchByPrimaryKey((Serializable)oAuth2AuthorizationId);
	}

	/**
	 * Returns all the o auth2 authorizations.
	 *
	 * @return the o auth2 authorizations
	 */
	@Override
	public List<OAuth2Authorization> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth2 authorizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2AuthorizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth2 authorizations
	 * @param end the upper bound of the range of o auth2 authorizations (not inclusive)
	 * @return the range of o auth2 authorizations
	 */
	@Override
	public List<OAuth2Authorization> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth2 authorizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2AuthorizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth2 authorizations
	 * @param end the upper bound of the range of o auth2 authorizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of o auth2 authorizations
	 */
	@Override
	public List<OAuth2Authorization> findAll(
		int start, int end,
		OrderByComparator<OAuth2Authorization> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth2 authorizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2AuthorizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth2 authorizations
	 * @param end the upper bound of the range of o auth2 authorizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of o auth2 authorizations
	 */
	@Override
	public List<OAuth2Authorization> findAll(
		int start, int end,
		OrderByComparator<OAuth2Authorization> orderByComparator,
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

		List<OAuth2Authorization> list = null;

		if (useFinderCache) {
			list = (List<OAuth2Authorization>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_OAUTH2AUTHORIZATION);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_OAUTH2AUTHORIZATION;

				sql = sql.concat(OAuth2AuthorizationModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<OAuth2Authorization>)QueryUtil.list(
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
	 * Removes all the o auth2 authorizations from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (OAuth2Authorization oAuth2Authorization : findAll()) {
			remove(oAuth2Authorization);
		}
	}

	/**
	 * Returns the number of o auth2 authorizations.
	 *
	 * @return the number of o auth2 authorizations
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_OAUTH2AUTHORIZATION);

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

	/**
	 * Returns the primaryKeys of o auth2 scope grants associated with the o auth2 authorization.
	 *
	 * @param pk the primary key of the o auth2 authorization
	 * @return long[] of the primaryKeys of o auth2 scope grants associated with the o auth2 authorization
	 */
	@Override
	public long[] getOAuth2ScopeGrantPrimaryKeys(long pk) {
		long[] pks =
			oAuth2AuthorizationToOAuth2ScopeGrantTableMapper.
				getRightPrimaryKeys(pk);

		return pks.clone();
	}

	/**
	 * Returns all the o auth2 authorization associated with the o auth2 scope grant.
	 *
	 * @param pk the primary key of the o auth2 scope grant
	 * @return the o auth2 authorizations associated with the o auth2 scope grant
	 */
	@Override
	public List<OAuth2Authorization> getOAuth2ScopeGrantOAuth2Authorizations(
		long pk) {

		return getOAuth2ScopeGrantOAuth2Authorizations(
			pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns all the o auth2 authorization associated with the o auth2 scope grant.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2AuthorizationModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the o auth2 scope grant
	 * @param start the lower bound of the range of o auth2 scope grants
	 * @param end the upper bound of the range of o auth2 scope grants (not inclusive)
	 * @return the range of o auth2 authorizations associated with the o auth2 scope grant
	 */
	@Override
	public List<OAuth2Authorization> getOAuth2ScopeGrantOAuth2Authorizations(
		long pk, int start, int end) {

		return getOAuth2ScopeGrantOAuth2Authorizations(pk, start, end, null);
	}

	/**
	 * Returns all the o auth2 authorization associated with the o auth2 scope grant.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2AuthorizationModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the o auth2 scope grant
	 * @param start the lower bound of the range of o auth2 scope grants
	 * @param end the upper bound of the range of o auth2 scope grants (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of o auth2 authorizations associated with the o auth2 scope grant
	 */
	@Override
	public List<OAuth2Authorization> getOAuth2ScopeGrantOAuth2Authorizations(
		long pk, int start, int end,
		OrderByComparator<OAuth2Authorization> orderByComparator) {

		return oAuth2AuthorizationToOAuth2ScopeGrantTableMapper.
			getLeftBaseModels(pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of o auth2 scope grants associated with the o auth2 authorization.
	 *
	 * @param pk the primary key of the o auth2 authorization
	 * @return the number of o auth2 scope grants associated with the o auth2 authorization
	 */
	@Override
	public int getOAuth2ScopeGrantsSize(long pk) {
		long[] pks =
			oAuth2AuthorizationToOAuth2ScopeGrantTableMapper.
				getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the o auth2 scope grant is associated with the o auth2 authorization.
	 *
	 * @param pk the primary key of the o auth2 authorization
	 * @param oAuth2ScopeGrantPK the primary key of the o auth2 scope grant
	 * @return <code>true</code> if the o auth2 scope grant is associated with the o auth2 authorization; <code>false</code> otherwise
	 */
	@Override
	public boolean containsOAuth2ScopeGrant(long pk, long oAuth2ScopeGrantPK) {
		return oAuth2AuthorizationToOAuth2ScopeGrantTableMapper.
			containsTableMapping(pk, oAuth2ScopeGrantPK);
	}

	/**
	 * Returns <code>true</code> if the o auth2 authorization has any o auth2 scope grants associated with it.
	 *
	 * @param pk the primary key of the o auth2 authorization to check for associations with o auth2 scope grants
	 * @return <code>true</code> if the o auth2 authorization has any o auth2 scope grants associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsOAuth2ScopeGrants(long pk) {
		if (getOAuth2ScopeGrantsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the o auth2 authorization and the o auth2 scope grant. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 authorization
	 * @param oAuth2ScopeGrantPK the primary key of the o auth2 scope grant
	 */
	@Override
	public void addOAuth2ScopeGrant(long pk, long oAuth2ScopeGrantPK) {
		OAuth2Authorization oAuth2Authorization = fetchByPrimaryKey(pk);

		if (oAuth2Authorization == null) {
			oAuth2AuthorizationToOAuth2ScopeGrantTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, oAuth2ScopeGrantPK);
		}
		else {
			oAuth2AuthorizationToOAuth2ScopeGrantTableMapper.addTableMapping(
				oAuth2Authorization.getCompanyId(), pk, oAuth2ScopeGrantPK);
		}
	}

	/**
	 * Adds an association between the o auth2 authorization and the o auth2 scope grant. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 authorization
	 * @param oAuth2ScopeGrant the o auth2 scope grant
	 */
	@Override
	public void addOAuth2ScopeGrant(
		long pk, OAuth2ScopeGrant oAuth2ScopeGrant) {

		OAuth2Authorization oAuth2Authorization = fetchByPrimaryKey(pk);

		if (oAuth2Authorization == null) {
			oAuth2AuthorizationToOAuth2ScopeGrantTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk,
				oAuth2ScopeGrant.getPrimaryKey());
		}
		else {
			oAuth2AuthorizationToOAuth2ScopeGrantTableMapper.addTableMapping(
				oAuth2Authorization.getCompanyId(), pk,
				oAuth2ScopeGrant.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the o auth2 authorization and the o auth2 scope grants. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 authorization
	 * @param oAuth2ScopeGrantPKs the primary keys of the o auth2 scope grants
	 */
	@Override
	public void addOAuth2ScopeGrants(long pk, long[] oAuth2ScopeGrantPKs) {
		long companyId = 0;

		OAuth2Authorization oAuth2Authorization = fetchByPrimaryKey(pk);

		if (oAuth2Authorization == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = oAuth2Authorization.getCompanyId();
		}

		oAuth2AuthorizationToOAuth2ScopeGrantTableMapper.addTableMappings(
			companyId, pk, oAuth2ScopeGrantPKs);
	}

	/**
	 * Adds an association between the o auth2 authorization and the o auth2 scope grants. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 authorization
	 * @param oAuth2ScopeGrants the o auth2 scope grants
	 */
	@Override
	public void addOAuth2ScopeGrants(
		long pk, List<OAuth2ScopeGrant> oAuth2ScopeGrants) {

		addOAuth2ScopeGrants(
			pk,
			ListUtil.toLongArray(
				oAuth2ScopeGrants,
				OAuth2ScopeGrant.O_AUTH2_SCOPE_GRANT_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the o auth2 authorization and its o auth2 scope grants. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 authorization to clear the associated o auth2 scope grants from
	 */
	@Override
	public void clearOAuth2ScopeGrants(long pk) {
		oAuth2AuthorizationToOAuth2ScopeGrantTableMapper.
			deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the o auth2 authorization and the o auth2 scope grant. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 authorization
	 * @param oAuth2ScopeGrantPK the primary key of the o auth2 scope grant
	 */
	@Override
	public void removeOAuth2ScopeGrant(long pk, long oAuth2ScopeGrantPK) {
		oAuth2AuthorizationToOAuth2ScopeGrantTableMapper.deleteTableMapping(
			pk, oAuth2ScopeGrantPK);
	}

	/**
	 * Removes the association between the o auth2 authorization and the o auth2 scope grant. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 authorization
	 * @param oAuth2ScopeGrant the o auth2 scope grant
	 */
	@Override
	public void removeOAuth2ScopeGrant(
		long pk, OAuth2ScopeGrant oAuth2ScopeGrant) {

		oAuth2AuthorizationToOAuth2ScopeGrantTableMapper.deleteTableMapping(
			pk, oAuth2ScopeGrant.getPrimaryKey());
	}

	/**
	 * Removes the association between the o auth2 authorization and the o auth2 scope grants. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 authorization
	 * @param oAuth2ScopeGrantPKs the primary keys of the o auth2 scope grants
	 */
	@Override
	public void removeOAuth2ScopeGrants(long pk, long[] oAuth2ScopeGrantPKs) {
		oAuth2AuthorizationToOAuth2ScopeGrantTableMapper.deleteTableMappings(
			pk, oAuth2ScopeGrantPKs);
	}

	/**
	 * Removes the association between the o auth2 authorization and the o auth2 scope grants. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 authorization
	 * @param oAuth2ScopeGrants the o auth2 scope grants
	 */
	@Override
	public void removeOAuth2ScopeGrants(
		long pk, List<OAuth2ScopeGrant> oAuth2ScopeGrants) {

		removeOAuth2ScopeGrants(
			pk,
			ListUtil.toLongArray(
				oAuth2ScopeGrants,
				OAuth2ScopeGrant.O_AUTH2_SCOPE_GRANT_ID_ACCESSOR));
	}

	/**
	 * Sets the o auth2 scope grants associated with the o auth2 authorization, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 authorization
	 * @param oAuth2ScopeGrantPKs the primary keys of the o auth2 scope grants to be associated with the o auth2 authorization
	 */
	@Override
	public void setOAuth2ScopeGrants(long pk, long[] oAuth2ScopeGrantPKs) {
		Set<Long> newOAuth2ScopeGrantPKsSet = SetUtil.fromArray(
			oAuth2ScopeGrantPKs);
		Set<Long> oldOAuth2ScopeGrantPKsSet = SetUtil.fromArray(
			oAuth2AuthorizationToOAuth2ScopeGrantTableMapper.
				getRightPrimaryKeys(pk));

		Set<Long> removeOAuth2ScopeGrantPKsSet = new HashSet<Long>(
			oldOAuth2ScopeGrantPKsSet);

		removeOAuth2ScopeGrantPKsSet.removeAll(newOAuth2ScopeGrantPKsSet);

		oAuth2AuthorizationToOAuth2ScopeGrantTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeOAuth2ScopeGrantPKsSet));

		newOAuth2ScopeGrantPKsSet.removeAll(oldOAuth2ScopeGrantPKsSet);

		long companyId = 0;

		OAuth2Authorization oAuth2Authorization = fetchByPrimaryKey(pk);

		if (oAuth2Authorization == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = oAuth2Authorization.getCompanyId();
		}

		oAuth2AuthorizationToOAuth2ScopeGrantTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newOAuth2ScopeGrantPKsSet));
	}

	/**
	 * Sets the o auth2 scope grants associated with the o auth2 authorization, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the o auth2 authorization
	 * @param oAuth2ScopeGrants the o auth2 scope grants to be associated with the o auth2 authorization
	 */
	@Override
	public void setOAuth2ScopeGrants(
		long pk, List<OAuth2ScopeGrant> oAuth2ScopeGrants) {

		try {
			long[] oAuth2ScopeGrantPKs = new long[oAuth2ScopeGrants.size()];

			for (int i = 0; i < oAuth2ScopeGrants.size(); i++) {
				OAuth2ScopeGrant oAuth2ScopeGrant = oAuth2ScopeGrants.get(i);

				oAuth2ScopeGrantPKs[i] = oAuth2ScopeGrant.getPrimaryKey();
			}

			setOAuth2ScopeGrants(pk, oAuth2ScopeGrantPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
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
		return "oAuth2AuthorizationId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_OAUTH2AUTHORIZATION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return OAuth2AuthorizationModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the o auth2 authorization persistence.
	 */
	@Activate
	public void activate() {
		OAuth2AuthorizationModelImpl.setEntityCacheEnabled(entityCacheEnabled);
		OAuth2AuthorizationModelImpl.setFinderCacheEnabled(finderCacheEnabled);

		oAuth2AuthorizationToOAuth2ScopeGrantTableMapper =
			TableMapperFactory.getTableMapper(
				"OA2Auths_OA2ScopeGrants#oAuth2AuthorizationId",
				"OA2Auths_OA2ScopeGrants", "companyId", "oAuth2AuthorizationId",
				"oAuth2ScopeGrantId", this, OAuth2ScopeGrant.class);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			OAuth2AuthorizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			OAuth2AuthorizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUserId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			OAuth2AuthorizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUserId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			OAuth2AuthorizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] {Long.class.getName()},
			OAuth2AuthorizationModelImpl.USERID_COLUMN_BITMASK);

		_finderPathCountByUserId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByOAuth2ApplicationId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			OAuth2AuthorizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByOAuth2ApplicationId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByOAuth2ApplicationId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			OAuth2AuthorizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByOAuth2ApplicationId", new String[] {Long.class.getName()},
			OAuth2AuthorizationModelImpl.OAUTH2APPLICATIONID_COLUMN_BITMASK);

		_finderPathCountByOAuth2ApplicationId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByOAuth2ApplicationId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByAccessTokenContentHash = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			OAuth2AuthorizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByAccessTokenContentHash",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByAccessTokenContentHash =
			new FinderPath(
				entityCacheEnabled, finderCacheEnabled,
				OAuth2AuthorizationImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByAccessTokenContentHash",
				new String[] {Long.class.getName()},
				OAuth2AuthorizationModelImpl.
					ACCESSTOKENCONTENTHASH_COLUMN_BITMASK);

		_finderPathCountByAccessTokenContentHash = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByAccessTokenContentHash",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByRefreshTokenContentHash = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			OAuth2AuthorizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByRefreshTokenContentHash",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByRefreshTokenContentHash =
			new FinderPath(
				entityCacheEnabled, finderCacheEnabled,
				OAuth2AuthorizationImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByRefreshTokenContentHash",
				new String[] {Long.class.getName()},
				OAuth2AuthorizationModelImpl.
					REFRESHTOKENCONTENTHASH_COLUMN_BITMASK);

		_finderPathCountByRefreshTokenContentHash = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByRefreshTokenContentHash",
			new String[] {Long.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(OAuth2AuthorizationImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		TableMapperFactory.removeTableMapper(
			"OA2Auths_OA2ScopeGrants#oAuth2AuthorizationId");
	}

	@Override
	@Reference(
		target = OAuthTwoPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.oauth2.provider.model.OAuth2Authorization"),
			true);
	}

	@Override
	@Reference(
		target = OAuthTwoPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = OAuthTwoPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	protected TableMapper<OAuth2Authorization, OAuth2ScopeGrant>
		oAuth2AuthorizationToOAuth2ScopeGrantTableMapper;

	private static final String _SQL_SELECT_OAUTH2AUTHORIZATION =
		"SELECT oAuth2Authorization FROM OAuth2Authorization oAuth2Authorization";

	private static final String _SQL_SELECT_OAUTH2AUTHORIZATION_WHERE =
		"SELECT oAuth2Authorization FROM OAuth2Authorization oAuth2Authorization WHERE ";

	private static final String _SQL_COUNT_OAUTH2AUTHORIZATION =
		"SELECT COUNT(oAuth2Authorization) FROM OAuth2Authorization oAuth2Authorization";

	private static final String _SQL_COUNT_OAUTH2AUTHORIZATION_WHERE =
		"SELECT COUNT(oAuth2Authorization) FROM OAuth2Authorization oAuth2Authorization WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "oAuth2Authorization.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No OAuth2Authorization exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No OAuth2Authorization exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		OAuth2AuthorizationPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"oAuth2ApplicationScopeAliasesId"});

	static {
		try {
			Class.forName(OAuthTwoPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new ExceptionInInitializerError(cnfe);
		}
	}

}