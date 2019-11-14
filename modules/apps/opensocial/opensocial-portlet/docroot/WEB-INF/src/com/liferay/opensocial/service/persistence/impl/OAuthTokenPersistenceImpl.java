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

package com.liferay.opensocial.service.persistence.impl;

import com.liferay.opensocial.exception.NoSuchOAuthTokenException;
import com.liferay.opensocial.model.OAuthToken;
import com.liferay.opensocial.model.impl.OAuthTokenImpl;
import com.liferay.opensocial.model.impl.OAuthTokenModelImpl;
import com.liferay.opensocial.service.persistence.OAuthTokenPersistence;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the o auth token service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class OAuthTokenPersistenceImpl
	extends BasePersistenceImpl<OAuthToken> implements OAuthTokenPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>OAuthTokenUtil</code> to access the o auth token persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		OAuthTokenImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByG_S;
	private FinderPath _finderPathWithoutPaginationFindByG_S;
	private FinderPath _finderPathCountByG_S;

	/**
	 * Returns all the o auth tokens where gadgetKey = &#63; and serviceName = &#63;.
	 *
	 * @param gadgetKey the gadget key
	 * @param serviceName the service name
	 * @return the matching o auth tokens
	 */
	@Override
	public List<OAuthToken> findByG_S(String gadgetKey, String serviceName) {
		return findByG_S(
			gadgetKey, serviceName, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth tokens where gadgetKey = &#63; and serviceName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthTokenModelImpl</code>.
	 * </p>
	 *
	 * @param gadgetKey the gadget key
	 * @param serviceName the service name
	 * @param start the lower bound of the range of o auth tokens
	 * @param end the upper bound of the range of o auth tokens (not inclusive)
	 * @return the range of matching o auth tokens
	 */
	@Override
	public List<OAuthToken> findByG_S(
		String gadgetKey, String serviceName, int start, int end) {

		return findByG_S(gadgetKey, serviceName, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth tokens where gadgetKey = &#63; and serviceName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthTokenModelImpl</code>.
	 * </p>
	 *
	 * @param gadgetKey the gadget key
	 * @param serviceName the service name
	 * @param start the lower bound of the range of o auth tokens
	 * @param end the upper bound of the range of o auth tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth tokens
	 */
	@Override
	public List<OAuthToken> findByG_S(
		String gadgetKey, String serviceName, int start, int end,
		OrderByComparator<OAuthToken> orderByComparator) {

		return findByG_S(
			gadgetKey, serviceName, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth tokens where gadgetKey = &#63; and serviceName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthTokenModelImpl</code>.
	 * </p>
	 *
	 * @param gadgetKey the gadget key
	 * @param serviceName the service name
	 * @param start the lower bound of the range of o auth tokens
	 * @param end the upper bound of the range of o auth tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth tokens
	 */
	@Override
	public List<OAuthToken> findByG_S(
		String gadgetKey, String serviceName, int start, int end,
		OrderByComparator<OAuthToken> orderByComparator,
		boolean useFinderCache) {

		gadgetKey = Objects.toString(gadgetKey, "");
		serviceName = Objects.toString(serviceName, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_S;
				finderArgs = new Object[] {gadgetKey, serviceName};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_S;
			finderArgs = new Object[] {
				gadgetKey, serviceName, start, end, orderByComparator
			};
		}

		List<OAuthToken> list = null;

		if (useFinderCache) {
			list = (List<OAuthToken>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (OAuthToken oAuthToken : list) {
					if (!gadgetKey.equals(oAuthToken.getGadgetKey()) ||
						!serviceName.equals(oAuthToken.getServiceName())) {

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
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_OAUTHTOKEN_WHERE);

			boolean bindGadgetKey = false;

			if (gadgetKey.isEmpty()) {
				query.append(_FINDER_COLUMN_G_S_GADGETKEY_3);
			}
			else {
				bindGadgetKey = true;

				query.append(_FINDER_COLUMN_G_S_GADGETKEY_2);
			}

			boolean bindServiceName = false;

			if (serviceName.isEmpty()) {
				query.append(_FINDER_COLUMN_G_S_SERVICENAME_3);
			}
			else {
				bindServiceName = true;

				query.append(_FINDER_COLUMN_G_S_SERVICENAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(OAuthTokenModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindGadgetKey) {
					qPos.add(gadgetKey);
				}

				if (bindServiceName) {
					qPos.add(serviceName);
				}

				list = (List<OAuthToken>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
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
	 * Returns the first o auth token in the ordered set where gadgetKey = &#63; and serviceName = &#63;.
	 *
	 * @param gadgetKey the gadget key
	 * @param serviceName the service name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth token
	 * @throws NoSuchOAuthTokenException if a matching o auth token could not be found
	 */
	@Override
	public OAuthToken findByG_S_First(
			String gadgetKey, String serviceName,
			OrderByComparator<OAuthToken> orderByComparator)
		throws NoSuchOAuthTokenException {

		OAuthToken oAuthToken = fetchByG_S_First(
			gadgetKey, serviceName, orderByComparator);

		if (oAuthToken != null) {
			return oAuthToken;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("gadgetKey=");
		msg.append(gadgetKey);

		msg.append(", serviceName=");
		msg.append(serviceName);

		msg.append("}");

		throw new NoSuchOAuthTokenException(msg.toString());
	}

	/**
	 * Returns the first o auth token in the ordered set where gadgetKey = &#63; and serviceName = &#63;.
	 *
	 * @param gadgetKey the gadget key
	 * @param serviceName the service name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth token, or <code>null</code> if a matching o auth token could not be found
	 */
	@Override
	public OAuthToken fetchByG_S_First(
		String gadgetKey, String serviceName,
		OrderByComparator<OAuthToken> orderByComparator) {

		List<OAuthToken> list = findByG_S(
			gadgetKey, serviceName, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last o auth token in the ordered set where gadgetKey = &#63; and serviceName = &#63;.
	 *
	 * @param gadgetKey the gadget key
	 * @param serviceName the service name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth token
	 * @throws NoSuchOAuthTokenException if a matching o auth token could not be found
	 */
	@Override
	public OAuthToken findByG_S_Last(
			String gadgetKey, String serviceName,
			OrderByComparator<OAuthToken> orderByComparator)
		throws NoSuchOAuthTokenException {

		OAuthToken oAuthToken = fetchByG_S_Last(
			gadgetKey, serviceName, orderByComparator);

		if (oAuthToken != null) {
			return oAuthToken;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("gadgetKey=");
		msg.append(gadgetKey);

		msg.append(", serviceName=");
		msg.append(serviceName);

		msg.append("}");

		throw new NoSuchOAuthTokenException(msg.toString());
	}

	/**
	 * Returns the last o auth token in the ordered set where gadgetKey = &#63; and serviceName = &#63;.
	 *
	 * @param gadgetKey the gadget key
	 * @param serviceName the service name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth token, or <code>null</code> if a matching o auth token could not be found
	 */
	@Override
	public OAuthToken fetchByG_S_Last(
		String gadgetKey, String serviceName,
		OrderByComparator<OAuthToken> orderByComparator) {

		int count = countByG_S(gadgetKey, serviceName);

		if (count == 0) {
			return null;
		}

		List<OAuthToken> list = findByG_S(
			gadgetKey, serviceName, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the o auth tokens before and after the current o auth token in the ordered set where gadgetKey = &#63; and serviceName = &#63;.
	 *
	 * @param oAuthTokenId the primary key of the current o auth token
	 * @param gadgetKey the gadget key
	 * @param serviceName the service name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth token
	 * @throws NoSuchOAuthTokenException if a o auth token with the primary key could not be found
	 */
	@Override
	public OAuthToken[] findByG_S_PrevAndNext(
			long oAuthTokenId, String gadgetKey, String serviceName,
			OrderByComparator<OAuthToken> orderByComparator)
		throws NoSuchOAuthTokenException {

		gadgetKey = Objects.toString(gadgetKey, "");
		serviceName = Objects.toString(serviceName, "");

		OAuthToken oAuthToken = findByPrimaryKey(oAuthTokenId);

		Session session = null;

		try {
			session = openSession();

			OAuthToken[] array = new OAuthTokenImpl[3];

			array[0] = getByG_S_PrevAndNext(
				session, oAuthToken, gadgetKey, serviceName, orderByComparator,
				true);

			array[1] = oAuthToken;

			array[2] = getByG_S_PrevAndNext(
				session, oAuthToken, gadgetKey, serviceName, orderByComparator,
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

	protected OAuthToken getByG_S_PrevAndNext(
		Session session, OAuthToken oAuthToken, String gadgetKey,
		String serviceName, OrderByComparator<OAuthToken> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_OAUTHTOKEN_WHERE);

		boolean bindGadgetKey = false;

		if (gadgetKey.isEmpty()) {
			query.append(_FINDER_COLUMN_G_S_GADGETKEY_3);
		}
		else {
			bindGadgetKey = true;

			query.append(_FINDER_COLUMN_G_S_GADGETKEY_2);
		}

		boolean bindServiceName = false;

		if (serviceName.isEmpty()) {
			query.append(_FINDER_COLUMN_G_S_SERVICENAME_3);
		}
		else {
			bindServiceName = true;

			query.append(_FINDER_COLUMN_G_S_SERVICENAME_2);
		}

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
			query.append(OAuthTokenModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindGadgetKey) {
			qPos.add(gadgetKey);
		}

		if (bindServiceName) {
			qPos.add(serviceName);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(oAuthToken)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<OAuthToken> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the o auth tokens where gadgetKey = &#63; and serviceName = &#63; from the database.
	 *
	 * @param gadgetKey the gadget key
	 * @param serviceName the service name
	 */
	@Override
	public void removeByG_S(String gadgetKey, String serviceName) {
		for (OAuthToken oAuthToken :
				findByG_S(
					gadgetKey, serviceName, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(oAuthToken);
		}
	}

	/**
	 * Returns the number of o auth tokens where gadgetKey = &#63; and serviceName = &#63;.
	 *
	 * @param gadgetKey the gadget key
	 * @param serviceName the service name
	 * @return the number of matching o auth tokens
	 */
	@Override
	public int countByG_S(String gadgetKey, String serviceName) {
		gadgetKey = Objects.toString(gadgetKey, "");
		serviceName = Objects.toString(serviceName, "");

		FinderPath finderPath = _finderPathCountByG_S;

		Object[] finderArgs = new Object[] {gadgetKey, serviceName};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_OAUTHTOKEN_WHERE);

			boolean bindGadgetKey = false;

			if (gadgetKey.isEmpty()) {
				query.append(_FINDER_COLUMN_G_S_GADGETKEY_3);
			}
			else {
				bindGadgetKey = true;

				query.append(_FINDER_COLUMN_G_S_GADGETKEY_2);
			}

			boolean bindServiceName = false;

			if (serviceName.isEmpty()) {
				query.append(_FINDER_COLUMN_G_S_SERVICENAME_3);
			}
			else {
				bindServiceName = true;

				query.append(_FINDER_COLUMN_G_S_SERVICENAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindGadgetKey) {
					qPos.add(gadgetKey);
				}

				if (bindServiceName) {
					qPos.add(serviceName);
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

	private static final String _FINDER_COLUMN_G_S_GADGETKEY_2 =
		"oAuthToken.gadgetKey = ? AND ";

	private static final String _FINDER_COLUMN_G_S_GADGETKEY_3 =
		"(oAuthToken.gadgetKey IS NULL OR oAuthToken.gadgetKey = '') AND ";

	private static final String _FINDER_COLUMN_G_S_SERVICENAME_2 =
		"oAuthToken.serviceName = ?";

	private static final String _FINDER_COLUMN_G_S_SERVICENAME_3 =
		"(oAuthToken.serviceName IS NULL OR oAuthToken.serviceName = '')";

	private FinderPath _finderPathFetchByU_G_S_M_T;
	private FinderPath _finderPathCountByU_G_S_M_T;

	/**
	 * Returns the o auth token where userId = &#63; and gadgetKey = &#63; and serviceName = &#63; and moduleId = &#63; and tokenName = &#63; or throws a <code>NoSuchOAuthTokenException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @param gadgetKey the gadget key
	 * @param serviceName the service name
	 * @param moduleId the module ID
	 * @param tokenName the token name
	 * @return the matching o auth token
	 * @throws NoSuchOAuthTokenException if a matching o auth token could not be found
	 */
	@Override
	public OAuthToken findByU_G_S_M_T(
			long userId, String gadgetKey, String serviceName, long moduleId,
			String tokenName)
		throws NoSuchOAuthTokenException {

		OAuthToken oAuthToken = fetchByU_G_S_M_T(
			userId, gadgetKey, serviceName, moduleId, tokenName);

		if (oAuthToken == null) {
			StringBundler msg = new StringBundler(12);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", gadgetKey=");
			msg.append(gadgetKey);

			msg.append(", serviceName=");
			msg.append(serviceName);

			msg.append(", moduleId=");
			msg.append(moduleId);

			msg.append(", tokenName=");
			msg.append(tokenName);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchOAuthTokenException(msg.toString());
		}

		return oAuthToken;
	}

	/**
	 * Returns the o auth token where userId = &#63; and gadgetKey = &#63; and serviceName = &#63; and moduleId = &#63; and tokenName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param gadgetKey the gadget key
	 * @param serviceName the service name
	 * @param moduleId the module ID
	 * @param tokenName the token name
	 * @return the matching o auth token, or <code>null</code> if a matching o auth token could not be found
	 */
	@Override
	public OAuthToken fetchByU_G_S_M_T(
		long userId, String gadgetKey, String serviceName, long moduleId,
		String tokenName) {

		return fetchByU_G_S_M_T(
			userId, gadgetKey, serviceName, moduleId, tokenName, true);
	}

	/**
	 * Returns the o auth token where userId = &#63; and gadgetKey = &#63; and serviceName = &#63; and moduleId = &#63; and tokenName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param gadgetKey the gadget key
	 * @param serviceName the service name
	 * @param moduleId the module ID
	 * @param tokenName the token name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching o auth token, or <code>null</code> if a matching o auth token could not be found
	 */
	@Override
	public OAuthToken fetchByU_G_S_M_T(
		long userId, String gadgetKey, String serviceName, long moduleId,
		String tokenName, boolean useFinderCache) {

		gadgetKey = Objects.toString(gadgetKey, "");
		serviceName = Objects.toString(serviceName, "");
		tokenName = Objects.toString(tokenName, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
				userId, gadgetKey, serviceName, moduleId, tokenName
			};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByU_G_S_M_T, finderArgs, this);
		}

		if (result instanceof OAuthToken) {
			OAuthToken oAuthToken = (OAuthToken)result;

			if ((userId != oAuthToken.getUserId()) ||
				!Objects.equals(gadgetKey, oAuthToken.getGadgetKey()) ||
				!Objects.equals(serviceName, oAuthToken.getServiceName()) ||
				(moduleId != oAuthToken.getModuleId()) ||
				!Objects.equals(tokenName, oAuthToken.getTokenName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(7);

			query.append(_SQL_SELECT_OAUTHTOKEN_WHERE);

			query.append(_FINDER_COLUMN_U_G_S_M_T_USERID_2);

			boolean bindGadgetKey = false;

			if (gadgetKey.isEmpty()) {
				query.append(_FINDER_COLUMN_U_G_S_M_T_GADGETKEY_3);
			}
			else {
				bindGadgetKey = true;

				query.append(_FINDER_COLUMN_U_G_S_M_T_GADGETKEY_2);
			}

			boolean bindServiceName = false;

			if (serviceName.isEmpty()) {
				query.append(_FINDER_COLUMN_U_G_S_M_T_SERVICENAME_3);
			}
			else {
				bindServiceName = true;

				query.append(_FINDER_COLUMN_U_G_S_M_T_SERVICENAME_2);
			}

			query.append(_FINDER_COLUMN_U_G_S_M_T_MODULEID_2);

			boolean bindTokenName = false;

			if (tokenName.isEmpty()) {
				query.append(_FINDER_COLUMN_U_G_S_M_T_TOKENNAME_3);
			}
			else {
				bindTokenName = true;

				query.append(_FINDER_COLUMN_U_G_S_M_T_TOKENNAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (bindGadgetKey) {
					qPos.add(gadgetKey);
				}

				if (bindServiceName) {
					qPos.add(serviceName);
				}

				qPos.add(moduleId);

				if (bindTokenName) {
					qPos.add(tokenName);
				}

				List<OAuthToken> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						FinderCacheUtil.putResult(
							_finderPathFetchByU_G_S_M_T, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									userId, gadgetKey, serviceName, moduleId,
									tokenName
								};
							}

							_log.warn(
								"OAuthTokenPersistenceImpl.fetchByU_G_S_M_T(long, String, String, long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					OAuthToken oAuthToken = list.get(0);

					result = oAuthToken;

					cacheResult(oAuthToken);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(
						_finderPathFetchByU_G_S_M_T, finderArgs);
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
			return (OAuthToken)result;
		}
	}

	/**
	 * Removes the o auth token where userId = &#63; and gadgetKey = &#63; and serviceName = &#63; and moduleId = &#63; and tokenName = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param gadgetKey the gadget key
	 * @param serviceName the service name
	 * @param moduleId the module ID
	 * @param tokenName the token name
	 * @return the o auth token that was removed
	 */
	@Override
	public OAuthToken removeByU_G_S_M_T(
			long userId, String gadgetKey, String serviceName, long moduleId,
			String tokenName)
		throws NoSuchOAuthTokenException {

		OAuthToken oAuthToken = findByU_G_S_M_T(
			userId, gadgetKey, serviceName, moduleId, tokenName);

		return remove(oAuthToken);
	}

	/**
	 * Returns the number of o auth tokens where userId = &#63; and gadgetKey = &#63; and serviceName = &#63; and moduleId = &#63; and tokenName = &#63;.
	 *
	 * @param userId the user ID
	 * @param gadgetKey the gadget key
	 * @param serviceName the service name
	 * @param moduleId the module ID
	 * @param tokenName the token name
	 * @return the number of matching o auth tokens
	 */
	@Override
	public int countByU_G_S_M_T(
		long userId, String gadgetKey, String serviceName, long moduleId,
		String tokenName) {

		gadgetKey = Objects.toString(gadgetKey, "");
		serviceName = Objects.toString(serviceName, "");
		tokenName = Objects.toString(tokenName, "");

		FinderPath finderPath = _finderPathCountByU_G_S_M_T;

		Object[] finderArgs = new Object[] {
			userId, gadgetKey, serviceName, moduleId, tokenName
		};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_COUNT_OAUTHTOKEN_WHERE);

			query.append(_FINDER_COLUMN_U_G_S_M_T_USERID_2);

			boolean bindGadgetKey = false;

			if (gadgetKey.isEmpty()) {
				query.append(_FINDER_COLUMN_U_G_S_M_T_GADGETKEY_3);
			}
			else {
				bindGadgetKey = true;

				query.append(_FINDER_COLUMN_U_G_S_M_T_GADGETKEY_2);
			}

			boolean bindServiceName = false;

			if (serviceName.isEmpty()) {
				query.append(_FINDER_COLUMN_U_G_S_M_T_SERVICENAME_3);
			}
			else {
				bindServiceName = true;

				query.append(_FINDER_COLUMN_U_G_S_M_T_SERVICENAME_2);
			}

			query.append(_FINDER_COLUMN_U_G_S_M_T_MODULEID_2);

			boolean bindTokenName = false;

			if (tokenName.isEmpty()) {
				query.append(_FINDER_COLUMN_U_G_S_M_T_TOKENNAME_3);
			}
			else {
				bindTokenName = true;

				query.append(_FINDER_COLUMN_U_G_S_M_T_TOKENNAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (bindGadgetKey) {
					qPos.add(gadgetKey);
				}

				if (bindServiceName) {
					qPos.add(serviceName);
				}

				qPos.add(moduleId);

				if (bindTokenName) {
					qPos.add(tokenName);
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

	private static final String _FINDER_COLUMN_U_G_S_M_T_USERID_2 =
		"oAuthToken.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_G_S_M_T_GADGETKEY_2 =
		"oAuthToken.gadgetKey = ? AND ";

	private static final String _FINDER_COLUMN_U_G_S_M_T_GADGETKEY_3 =
		"(oAuthToken.gadgetKey IS NULL OR oAuthToken.gadgetKey = '') AND ";

	private static final String _FINDER_COLUMN_U_G_S_M_T_SERVICENAME_2 =
		"oAuthToken.serviceName = ? AND ";

	private static final String _FINDER_COLUMN_U_G_S_M_T_SERVICENAME_3 =
		"(oAuthToken.serviceName IS NULL OR oAuthToken.serviceName = '') AND ";

	private static final String _FINDER_COLUMN_U_G_S_M_T_MODULEID_2 =
		"oAuthToken.moduleId = ? AND ";

	private static final String _FINDER_COLUMN_U_G_S_M_T_TOKENNAME_2 =
		"oAuthToken.tokenName = ?";

	private static final String _FINDER_COLUMN_U_G_S_M_T_TOKENNAME_3 =
		"(oAuthToken.tokenName IS NULL OR oAuthToken.tokenName = '')";

	public OAuthTokenPersistenceImpl() {
		setModelClass(OAuthToken.class);

		setModelImplClass(OAuthTokenImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(OAuthTokenModelImpl.ENTITY_CACHE_ENABLED);
	}

	/**
	 * Caches the o auth token in the entity cache if it is enabled.
	 *
	 * @param oAuthToken the o auth token
	 */
	@Override
	public void cacheResult(OAuthToken oAuthToken) {
		EntityCacheUtil.putResult(
			OAuthTokenModelImpl.ENTITY_CACHE_ENABLED, OAuthTokenImpl.class,
			oAuthToken.getPrimaryKey(), oAuthToken);

		FinderCacheUtil.putResult(
			_finderPathFetchByU_G_S_M_T,
			new Object[] {
				oAuthToken.getUserId(), oAuthToken.getGadgetKey(),
				oAuthToken.getServiceName(), oAuthToken.getModuleId(),
				oAuthToken.getTokenName()
			},
			oAuthToken);

		oAuthToken.resetOriginalValues();
	}

	/**
	 * Caches the o auth tokens in the entity cache if it is enabled.
	 *
	 * @param oAuthTokens the o auth tokens
	 */
	@Override
	public void cacheResult(List<OAuthToken> oAuthTokens) {
		for (OAuthToken oAuthToken : oAuthTokens) {
			if (EntityCacheUtil.getResult(
					OAuthTokenModelImpl.ENTITY_CACHE_ENABLED,
					OAuthTokenImpl.class, oAuthToken.getPrimaryKey()) == null) {

				cacheResult(oAuthToken);
			}
			else {
				oAuthToken.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all o auth tokens.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(OAuthTokenImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the o auth token.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(OAuthToken oAuthToken) {
		EntityCacheUtil.removeResult(
			OAuthTokenModelImpl.ENTITY_CACHE_ENABLED, OAuthTokenImpl.class,
			oAuthToken.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((OAuthTokenModelImpl)oAuthToken, true);
	}

	@Override
	public void clearCache(List<OAuthToken> oAuthTokens) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (OAuthToken oAuthToken : oAuthTokens) {
			EntityCacheUtil.removeResult(
				OAuthTokenModelImpl.ENTITY_CACHE_ENABLED, OAuthTokenImpl.class,
				oAuthToken.getPrimaryKey());

			clearUniqueFindersCache((OAuthTokenModelImpl)oAuthToken, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(
				OAuthTokenModelImpl.ENTITY_CACHE_ENABLED, OAuthTokenImpl.class,
				primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		OAuthTokenModelImpl oAuthTokenModelImpl) {

		Object[] args = new Object[] {
			oAuthTokenModelImpl.getUserId(), oAuthTokenModelImpl.getGadgetKey(),
			oAuthTokenModelImpl.getServiceName(),
			oAuthTokenModelImpl.getModuleId(),
			oAuthTokenModelImpl.getTokenName()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByU_G_S_M_T, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByU_G_S_M_T, args, oAuthTokenModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		OAuthTokenModelImpl oAuthTokenModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				oAuthTokenModelImpl.getUserId(),
				oAuthTokenModelImpl.getGadgetKey(),
				oAuthTokenModelImpl.getServiceName(),
				oAuthTokenModelImpl.getModuleId(),
				oAuthTokenModelImpl.getTokenName()
			};

			FinderCacheUtil.removeResult(_finderPathCountByU_G_S_M_T, args);
			FinderCacheUtil.removeResult(_finderPathFetchByU_G_S_M_T, args);
		}

		if ((oAuthTokenModelImpl.getColumnBitmask() &
			 _finderPathFetchByU_G_S_M_T.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				oAuthTokenModelImpl.getOriginalUserId(),
				oAuthTokenModelImpl.getOriginalGadgetKey(),
				oAuthTokenModelImpl.getOriginalServiceName(),
				oAuthTokenModelImpl.getOriginalModuleId(),
				oAuthTokenModelImpl.getOriginalTokenName()
			};

			FinderCacheUtil.removeResult(_finderPathCountByU_G_S_M_T, args);
			FinderCacheUtil.removeResult(_finderPathFetchByU_G_S_M_T, args);
		}
	}

	/**
	 * Creates a new o auth token with the primary key. Does not add the o auth token to the database.
	 *
	 * @param oAuthTokenId the primary key for the new o auth token
	 * @return the new o auth token
	 */
	@Override
	public OAuthToken create(long oAuthTokenId) {
		OAuthToken oAuthToken = new OAuthTokenImpl();

		oAuthToken.setNew(true);
		oAuthToken.setPrimaryKey(oAuthTokenId);

		oAuthToken.setCompanyId(CompanyThreadLocal.getCompanyId());

		return oAuthToken;
	}

	/**
	 * Removes the o auth token with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param oAuthTokenId the primary key of the o auth token
	 * @return the o auth token that was removed
	 * @throws NoSuchOAuthTokenException if a o auth token with the primary key could not be found
	 */
	@Override
	public OAuthToken remove(long oAuthTokenId)
		throws NoSuchOAuthTokenException {

		return remove((Serializable)oAuthTokenId);
	}

	/**
	 * Removes the o auth token with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the o auth token
	 * @return the o auth token that was removed
	 * @throws NoSuchOAuthTokenException if a o auth token with the primary key could not be found
	 */
	@Override
	public OAuthToken remove(Serializable primaryKey)
		throws NoSuchOAuthTokenException {

		Session session = null;

		try {
			session = openSession();

			OAuthToken oAuthToken = (OAuthToken)session.get(
				OAuthTokenImpl.class, primaryKey);

			if (oAuthToken == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchOAuthTokenException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(oAuthToken);
		}
		catch (NoSuchOAuthTokenException nsee) {
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
	protected OAuthToken removeImpl(OAuthToken oAuthToken) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(oAuthToken)) {
				oAuthToken = (OAuthToken)session.get(
					OAuthTokenImpl.class, oAuthToken.getPrimaryKeyObj());
			}

			if (oAuthToken != null) {
				session.delete(oAuthToken);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (oAuthToken != null) {
			clearCache(oAuthToken);
		}

		return oAuthToken;
	}

	@Override
	public OAuthToken updateImpl(OAuthToken oAuthToken) {
		boolean isNew = oAuthToken.isNew();

		if (!(oAuthToken instanceof OAuthTokenModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(oAuthToken.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(oAuthToken);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in oAuthToken proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom OAuthToken implementation " +
					oAuthToken.getClass());
		}

		OAuthTokenModelImpl oAuthTokenModelImpl =
			(OAuthTokenModelImpl)oAuthToken;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (oAuthToken.getCreateDate() == null)) {
			if (serviceContext == null) {
				oAuthToken.setCreateDate(now);
			}
			else {
				oAuthToken.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!oAuthTokenModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				oAuthToken.setModifiedDate(now);
			}
			else {
				oAuthToken.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (oAuthToken.isNew()) {
				session.save(oAuthToken);

				oAuthToken.setNew(false);
			}
			else {
				oAuthToken = (OAuthToken)session.merge(oAuthToken);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!OAuthTokenModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				oAuthTokenModelImpl.getGadgetKey(),
				oAuthTokenModelImpl.getServiceName()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_S, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByG_S, args);

			FinderCacheUtil.removeResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((oAuthTokenModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_S.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					oAuthTokenModelImpl.getOriginalGadgetKey(),
					oAuthTokenModelImpl.getOriginalServiceName()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_S, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_S, args);

				args = new Object[] {
					oAuthTokenModelImpl.getGadgetKey(),
					oAuthTokenModelImpl.getServiceName()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_S, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_S, args);
			}
		}

		EntityCacheUtil.putResult(
			OAuthTokenModelImpl.ENTITY_CACHE_ENABLED, OAuthTokenImpl.class,
			oAuthToken.getPrimaryKey(), oAuthToken, false);

		clearUniqueFindersCache(oAuthTokenModelImpl, false);
		cacheUniqueFindersCache(oAuthTokenModelImpl);

		oAuthToken.resetOriginalValues();

		return oAuthToken;
	}

	/**
	 * Returns the o auth token with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the o auth token
	 * @return the o auth token
	 * @throws NoSuchOAuthTokenException if a o auth token with the primary key could not be found
	 */
	@Override
	public OAuthToken findByPrimaryKey(Serializable primaryKey)
		throws NoSuchOAuthTokenException {

		OAuthToken oAuthToken = fetchByPrimaryKey(primaryKey);

		if (oAuthToken == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchOAuthTokenException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return oAuthToken;
	}

	/**
	 * Returns the o auth token with the primary key or throws a <code>NoSuchOAuthTokenException</code> if it could not be found.
	 *
	 * @param oAuthTokenId the primary key of the o auth token
	 * @return the o auth token
	 * @throws NoSuchOAuthTokenException if a o auth token with the primary key could not be found
	 */
	@Override
	public OAuthToken findByPrimaryKey(long oAuthTokenId)
		throws NoSuchOAuthTokenException {

		return findByPrimaryKey((Serializable)oAuthTokenId);
	}

	/**
	 * Returns the o auth token with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param oAuthTokenId the primary key of the o auth token
	 * @return the o auth token, or <code>null</code> if a o auth token with the primary key could not be found
	 */
	@Override
	public OAuthToken fetchByPrimaryKey(long oAuthTokenId) {
		return fetchByPrimaryKey((Serializable)oAuthTokenId);
	}

	/**
	 * Returns all the o auth tokens.
	 *
	 * @return the o auth tokens
	 */
	@Override
	public List<OAuthToken> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth tokens.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthTokenModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth tokens
	 * @param end the upper bound of the range of o auth tokens (not inclusive)
	 * @return the range of o auth tokens
	 */
	@Override
	public List<OAuthToken> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth tokens.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthTokenModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth tokens
	 * @param end the upper bound of the range of o auth tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of o auth tokens
	 */
	@Override
	public List<OAuthToken> findAll(
		int start, int end, OrderByComparator<OAuthToken> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth tokens.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthTokenModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth tokens
	 * @param end the upper bound of the range of o auth tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of o auth tokens
	 */
	@Override
	public List<OAuthToken> findAll(
		int start, int end, OrderByComparator<OAuthToken> orderByComparator,
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

		List<OAuthToken> list = null;

		if (useFinderCache) {
			list = (List<OAuthToken>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_OAUTHTOKEN);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_OAUTHTOKEN;

				sql = sql.concat(OAuthTokenModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<OAuthToken>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
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
	 * Removes all the o auth tokens from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (OAuthToken oAuthToken : findAll()) {
			remove(oAuthToken);
		}
	}

	/**
	 * Returns the number of o auth tokens.
	 *
	 * @return the number of o auth tokens
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_OAUTHTOKEN);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(
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
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "oAuthTokenId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_OAUTHTOKEN;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return OAuthTokenModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the o auth token persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			OAuthTokenModelImpl.ENTITY_CACHE_ENABLED,
			OAuthTokenModelImpl.FINDER_CACHE_ENABLED, OAuthTokenImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			OAuthTokenModelImpl.ENTITY_CACHE_ENABLED,
			OAuthTokenModelImpl.FINDER_CACHE_ENABLED, OAuthTokenImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			OAuthTokenModelImpl.ENTITY_CACHE_ENABLED,
			OAuthTokenModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByG_S = new FinderPath(
			OAuthTokenModelImpl.ENTITY_CACHE_ENABLED,
			OAuthTokenModelImpl.FINDER_CACHE_ENABLED, OAuthTokenImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_S",
			new String[] {
				String.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_S = new FinderPath(
			OAuthTokenModelImpl.ENTITY_CACHE_ENABLED,
			OAuthTokenModelImpl.FINDER_CACHE_ENABLED, OAuthTokenImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_S",
			new String[] {String.class.getName(), String.class.getName()},
			OAuthTokenModelImpl.GADGETKEY_COLUMN_BITMASK |
			OAuthTokenModelImpl.SERVICENAME_COLUMN_BITMASK);

		_finderPathCountByG_S = new FinderPath(
			OAuthTokenModelImpl.ENTITY_CACHE_ENABLED,
			OAuthTokenModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_S",
			new String[] {String.class.getName(), String.class.getName()});

		_finderPathFetchByU_G_S_M_T = new FinderPath(
			OAuthTokenModelImpl.ENTITY_CACHE_ENABLED,
			OAuthTokenModelImpl.FINDER_CACHE_ENABLED, OAuthTokenImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByU_G_S_M_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			OAuthTokenModelImpl.USERID_COLUMN_BITMASK |
			OAuthTokenModelImpl.GADGETKEY_COLUMN_BITMASK |
			OAuthTokenModelImpl.SERVICENAME_COLUMN_BITMASK |
			OAuthTokenModelImpl.MODULEID_COLUMN_BITMASK |
			OAuthTokenModelImpl.TOKENNAME_COLUMN_BITMASK);

		_finderPathCountByU_G_S_M_T = new FinderPath(
			OAuthTokenModelImpl.ENTITY_CACHE_ENABLED,
			OAuthTokenModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_G_S_M_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	}

	public void destroy() {
		EntityCacheUtil.removeCache(OAuthTokenImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_OAUTHTOKEN =
		"SELECT oAuthToken FROM OAuthToken oAuthToken";

	private static final String _SQL_SELECT_OAUTHTOKEN_WHERE =
		"SELECT oAuthToken FROM OAuthToken oAuthToken WHERE ";

	private static final String _SQL_COUNT_OAUTHTOKEN =
		"SELECT COUNT(oAuthToken) FROM OAuthToken oAuthToken";

	private static final String _SQL_COUNT_OAUTHTOKEN_WHERE =
		"SELECT COUNT(oAuthToken) FROM OAuthToken oAuthToken WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "oAuthToken.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No OAuthToken exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No OAuthToken exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		OAuthTokenPersistenceImpl.class);

}