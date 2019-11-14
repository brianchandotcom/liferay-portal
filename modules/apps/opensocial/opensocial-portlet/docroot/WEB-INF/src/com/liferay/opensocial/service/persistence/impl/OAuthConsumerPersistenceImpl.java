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

import com.liferay.opensocial.exception.NoSuchOAuthConsumerException;
import com.liferay.opensocial.model.OAuthConsumer;
import com.liferay.opensocial.model.impl.OAuthConsumerImpl;
import com.liferay.opensocial.model.impl.OAuthConsumerModelImpl;
import com.liferay.opensocial.service.persistence.OAuthConsumerPersistence;
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
 * The persistence implementation for the o auth consumer service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class OAuthConsumerPersistenceImpl
	extends BasePersistenceImpl<OAuthConsumer>
	implements OAuthConsumerPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>OAuthConsumerUtil</code> to access the o auth consumer persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		OAuthConsumerImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByGadgetKey;
	private FinderPath _finderPathWithoutPaginationFindByGadgetKey;
	private FinderPath _finderPathCountByGadgetKey;

	/**
	 * Returns all the o auth consumers where gadgetKey = &#63;.
	 *
	 * @param gadgetKey the gadget key
	 * @return the matching o auth consumers
	 */
	@Override
	public List<OAuthConsumer> findByGadgetKey(String gadgetKey) {
		return findByGadgetKey(
			gadgetKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth consumers where gadgetKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthConsumerModelImpl</code>.
	 * </p>
	 *
	 * @param gadgetKey the gadget key
	 * @param start the lower bound of the range of o auth consumers
	 * @param end the upper bound of the range of o auth consumers (not inclusive)
	 * @return the range of matching o auth consumers
	 */
	@Override
	public List<OAuthConsumer> findByGadgetKey(
		String gadgetKey, int start, int end) {

		return findByGadgetKey(gadgetKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth consumers where gadgetKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthConsumerModelImpl</code>.
	 * </p>
	 *
	 * @param gadgetKey the gadget key
	 * @param start the lower bound of the range of o auth consumers
	 * @param end the upper bound of the range of o auth consumers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth consumers
	 */
	@Override
	public List<OAuthConsumer> findByGadgetKey(
		String gadgetKey, int start, int end,
		OrderByComparator<OAuthConsumer> orderByComparator) {

		return findByGadgetKey(gadgetKey, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth consumers where gadgetKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthConsumerModelImpl</code>.
	 * </p>
	 *
	 * @param gadgetKey the gadget key
	 * @param start the lower bound of the range of o auth consumers
	 * @param end the upper bound of the range of o auth consumers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth consumers
	 */
	@Override
	public List<OAuthConsumer> findByGadgetKey(
		String gadgetKey, int start, int end,
		OrderByComparator<OAuthConsumer> orderByComparator,
		boolean useFinderCache) {

		gadgetKey = Objects.toString(gadgetKey, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByGadgetKey;
				finderArgs = new Object[] {gadgetKey};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByGadgetKey;
			finderArgs = new Object[] {
				gadgetKey, start, end, orderByComparator
			};
		}

		List<OAuthConsumer> list = null;

		if (useFinderCache) {
			list = (List<OAuthConsumer>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (OAuthConsumer oAuthConsumer : list) {
					if (!gadgetKey.equals(oAuthConsumer.getGadgetKey())) {
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

			query.append(_SQL_SELECT_OAUTHCONSUMER_WHERE);

			boolean bindGadgetKey = false;

			if (gadgetKey.isEmpty()) {
				query.append(_FINDER_COLUMN_GADGETKEY_GADGETKEY_3);
			}
			else {
				bindGadgetKey = true;

				query.append(_FINDER_COLUMN_GADGETKEY_GADGETKEY_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(OAuthConsumerModelImpl.ORDER_BY_JPQL);
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

				list = (List<OAuthConsumer>)QueryUtil.list(
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
	 * Returns the first o auth consumer in the ordered set where gadgetKey = &#63;.
	 *
	 * @param gadgetKey the gadget key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth consumer
	 * @throws NoSuchOAuthConsumerException if a matching o auth consumer could not be found
	 */
	@Override
	public OAuthConsumer findByGadgetKey_First(
			String gadgetKey,
			OrderByComparator<OAuthConsumer> orderByComparator)
		throws NoSuchOAuthConsumerException {

		OAuthConsumer oAuthConsumer = fetchByGadgetKey_First(
			gadgetKey, orderByComparator);

		if (oAuthConsumer != null) {
			return oAuthConsumer;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("gadgetKey=");
		msg.append(gadgetKey);

		msg.append("}");

		throw new NoSuchOAuthConsumerException(msg.toString());
	}

	/**
	 * Returns the first o auth consumer in the ordered set where gadgetKey = &#63;.
	 *
	 * @param gadgetKey the gadget key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth consumer, or <code>null</code> if a matching o auth consumer could not be found
	 */
	@Override
	public OAuthConsumer fetchByGadgetKey_First(
		String gadgetKey, OrderByComparator<OAuthConsumer> orderByComparator) {

		List<OAuthConsumer> list = findByGadgetKey(
			gadgetKey, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last o auth consumer in the ordered set where gadgetKey = &#63;.
	 *
	 * @param gadgetKey the gadget key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth consumer
	 * @throws NoSuchOAuthConsumerException if a matching o auth consumer could not be found
	 */
	@Override
	public OAuthConsumer findByGadgetKey_Last(
			String gadgetKey,
			OrderByComparator<OAuthConsumer> orderByComparator)
		throws NoSuchOAuthConsumerException {

		OAuthConsumer oAuthConsumer = fetchByGadgetKey_Last(
			gadgetKey, orderByComparator);

		if (oAuthConsumer != null) {
			return oAuthConsumer;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("gadgetKey=");
		msg.append(gadgetKey);

		msg.append("}");

		throw new NoSuchOAuthConsumerException(msg.toString());
	}

	/**
	 * Returns the last o auth consumer in the ordered set where gadgetKey = &#63;.
	 *
	 * @param gadgetKey the gadget key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth consumer, or <code>null</code> if a matching o auth consumer could not be found
	 */
	@Override
	public OAuthConsumer fetchByGadgetKey_Last(
		String gadgetKey, OrderByComparator<OAuthConsumer> orderByComparator) {

		int count = countByGadgetKey(gadgetKey);

		if (count == 0) {
			return null;
		}

		List<OAuthConsumer> list = findByGadgetKey(
			gadgetKey, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the o auth consumers before and after the current o auth consumer in the ordered set where gadgetKey = &#63;.
	 *
	 * @param oAuthConsumerId the primary key of the current o auth consumer
	 * @param gadgetKey the gadget key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth consumer
	 * @throws NoSuchOAuthConsumerException if a o auth consumer with the primary key could not be found
	 */
	@Override
	public OAuthConsumer[] findByGadgetKey_PrevAndNext(
			long oAuthConsumerId, String gadgetKey,
			OrderByComparator<OAuthConsumer> orderByComparator)
		throws NoSuchOAuthConsumerException {

		gadgetKey = Objects.toString(gadgetKey, "");

		OAuthConsumer oAuthConsumer = findByPrimaryKey(oAuthConsumerId);

		Session session = null;

		try {
			session = openSession();

			OAuthConsumer[] array = new OAuthConsumerImpl[3];

			array[0] = getByGadgetKey_PrevAndNext(
				session, oAuthConsumer, gadgetKey, orderByComparator, true);

			array[1] = oAuthConsumer;

			array[2] = getByGadgetKey_PrevAndNext(
				session, oAuthConsumer, gadgetKey, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected OAuthConsumer getByGadgetKey_PrevAndNext(
		Session session, OAuthConsumer oAuthConsumer, String gadgetKey,
		OrderByComparator<OAuthConsumer> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_OAUTHCONSUMER_WHERE);

		boolean bindGadgetKey = false;

		if (gadgetKey.isEmpty()) {
			query.append(_FINDER_COLUMN_GADGETKEY_GADGETKEY_3);
		}
		else {
			bindGadgetKey = true;

			query.append(_FINDER_COLUMN_GADGETKEY_GADGETKEY_2);
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
			query.append(OAuthConsumerModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindGadgetKey) {
			qPos.add(gadgetKey);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						oAuthConsumer)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<OAuthConsumer> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the o auth consumers where gadgetKey = &#63; from the database.
	 *
	 * @param gadgetKey the gadget key
	 */
	@Override
	public void removeByGadgetKey(String gadgetKey) {
		for (OAuthConsumer oAuthConsumer :
				findByGadgetKey(
					gadgetKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(oAuthConsumer);
		}
	}

	/**
	 * Returns the number of o auth consumers where gadgetKey = &#63;.
	 *
	 * @param gadgetKey the gadget key
	 * @return the number of matching o auth consumers
	 */
	@Override
	public int countByGadgetKey(String gadgetKey) {
		gadgetKey = Objects.toString(gadgetKey, "");

		FinderPath finderPath = _finderPathCountByGadgetKey;

		Object[] finderArgs = new Object[] {gadgetKey};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_OAUTHCONSUMER_WHERE);

			boolean bindGadgetKey = false;

			if (gadgetKey.isEmpty()) {
				query.append(_FINDER_COLUMN_GADGETKEY_GADGETKEY_3);
			}
			else {
				bindGadgetKey = true;

				query.append(_FINDER_COLUMN_GADGETKEY_GADGETKEY_2);
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

	private static final String _FINDER_COLUMN_GADGETKEY_GADGETKEY_2 =
		"oAuthConsumer.gadgetKey = ?";

	private static final String _FINDER_COLUMN_GADGETKEY_GADGETKEY_3 =
		"(oAuthConsumer.gadgetKey IS NULL OR oAuthConsumer.gadgetKey = '')";

	private FinderPath _finderPathFetchByG_S;
	private FinderPath _finderPathCountByG_S;

	/**
	 * Returns the o auth consumer where gadgetKey = &#63; and serviceName = &#63; or throws a <code>NoSuchOAuthConsumerException</code> if it could not be found.
	 *
	 * @param gadgetKey the gadget key
	 * @param serviceName the service name
	 * @return the matching o auth consumer
	 * @throws NoSuchOAuthConsumerException if a matching o auth consumer could not be found
	 */
	@Override
	public OAuthConsumer findByG_S(String gadgetKey, String serviceName)
		throws NoSuchOAuthConsumerException {

		OAuthConsumer oAuthConsumer = fetchByG_S(gadgetKey, serviceName);

		if (oAuthConsumer == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("gadgetKey=");
			msg.append(gadgetKey);

			msg.append(", serviceName=");
			msg.append(serviceName);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchOAuthConsumerException(msg.toString());
		}

		return oAuthConsumer;
	}

	/**
	 * Returns the o auth consumer where gadgetKey = &#63; and serviceName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param gadgetKey the gadget key
	 * @param serviceName the service name
	 * @return the matching o auth consumer, or <code>null</code> if a matching o auth consumer could not be found
	 */
	@Override
	public OAuthConsumer fetchByG_S(String gadgetKey, String serviceName) {
		return fetchByG_S(gadgetKey, serviceName, true);
	}

	/**
	 * Returns the o auth consumer where gadgetKey = &#63; and serviceName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param gadgetKey the gadget key
	 * @param serviceName the service name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching o auth consumer, or <code>null</code> if a matching o auth consumer could not be found
	 */
	@Override
	public OAuthConsumer fetchByG_S(
		String gadgetKey, String serviceName, boolean useFinderCache) {

		gadgetKey = Objects.toString(gadgetKey, "");
		serviceName = Objects.toString(serviceName, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {gadgetKey, serviceName};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByG_S, finderArgs, this);
		}

		if (result instanceof OAuthConsumer) {
			OAuthConsumer oAuthConsumer = (OAuthConsumer)result;

			if (!Objects.equals(gadgetKey, oAuthConsumer.getGadgetKey()) ||
				!Objects.equals(serviceName, oAuthConsumer.getServiceName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_OAUTHCONSUMER_WHERE);

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

				List<OAuthConsumer> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						FinderCacheUtil.putResult(
							_finderPathFetchByG_S, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									gadgetKey, serviceName
								};
							}

							_log.warn(
								"OAuthConsumerPersistenceImpl.fetchByG_S(String, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					OAuthConsumer oAuthConsumer = list.get(0);

					result = oAuthConsumer;

					cacheResult(oAuthConsumer);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(
						_finderPathFetchByG_S, finderArgs);
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
			return (OAuthConsumer)result;
		}
	}

	/**
	 * Removes the o auth consumer where gadgetKey = &#63; and serviceName = &#63; from the database.
	 *
	 * @param gadgetKey the gadget key
	 * @param serviceName the service name
	 * @return the o auth consumer that was removed
	 */
	@Override
	public OAuthConsumer removeByG_S(String gadgetKey, String serviceName)
		throws NoSuchOAuthConsumerException {

		OAuthConsumer oAuthConsumer = findByG_S(gadgetKey, serviceName);

		return remove(oAuthConsumer);
	}

	/**
	 * Returns the number of o auth consumers where gadgetKey = &#63; and serviceName = &#63;.
	 *
	 * @param gadgetKey the gadget key
	 * @param serviceName the service name
	 * @return the number of matching o auth consumers
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

			query.append(_SQL_COUNT_OAUTHCONSUMER_WHERE);

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
		"oAuthConsumer.gadgetKey = ? AND ";

	private static final String _FINDER_COLUMN_G_S_GADGETKEY_3 =
		"(oAuthConsumer.gadgetKey IS NULL OR oAuthConsumer.gadgetKey = '') AND ";

	private static final String _FINDER_COLUMN_G_S_SERVICENAME_2 =
		"oAuthConsumer.serviceName = ?";

	private static final String _FINDER_COLUMN_G_S_SERVICENAME_3 =
		"(oAuthConsumer.serviceName IS NULL OR oAuthConsumer.serviceName = '')";

	public OAuthConsumerPersistenceImpl() {
		setModelClass(OAuthConsumer.class);

		setModelImplClass(OAuthConsumerImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(OAuthConsumerModelImpl.ENTITY_CACHE_ENABLED);
	}

	/**
	 * Caches the o auth consumer in the entity cache if it is enabled.
	 *
	 * @param oAuthConsumer the o auth consumer
	 */
	@Override
	public void cacheResult(OAuthConsumer oAuthConsumer) {
		EntityCacheUtil.putResult(
			OAuthConsumerModelImpl.ENTITY_CACHE_ENABLED,
			OAuthConsumerImpl.class, oAuthConsumer.getPrimaryKey(),
			oAuthConsumer);

		FinderCacheUtil.putResult(
			_finderPathFetchByG_S,
			new Object[] {
				oAuthConsumer.getGadgetKey(), oAuthConsumer.getServiceName()
			},
			oAuthConsumer);

		oAuthConsumer.resetOriginalValues();
	}

	/**
	 * Caches the o auth consumers in the entity cache if it is enabled.
	 *
	 * @param oAuthConsumers the o auth consumers
	 */
	@Override
	public void cacheResult(List<OAuthConsumer> oAuthConsumers) {
		for (OAuthConsumer oAuthConsumer : oAuthConsumers) {
			if (EntityCacheUtil.getResult(
					OAuthConsumerModelImpl.ENTITY_CACHE_ENABLED,
					OAuthConsumerImpl.class, oAuthConsumer.getPrimaryKey()) ==
						null) {

				cacheResult(oAuthConsumer);
			}
			else {
				oAuthConsumer.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all o auth consumers.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(OAuthConsumerImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the o auth consumer.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(OAuthConsumer oAuthConsumer) {
		EntityCacheUtil.removeResult(
			OAuthConsumerModelImpl.ENTITY_CACHE_ENABLED,
			OAuthConsumerImpl.class, oAuthConsumer.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((OAuthConsumerModelImpl)oAuthConsumer, true);
	}

	@Override
	public void clearCache(List<OAuthConsumer> oAuthConsumers) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (OAuthConsumer oAuthConsumer : oAuthConsumers) {
			EntityCacheUtil.removeResult(
				OAuthConsumerModelImpl.ENTITY_CACHE_ENABLED,
				OAuthConsumerImpl.class, oAuthConsumer.getPrimaryKey());

			clearUniqueFindersCache(
				(OAuthConsumerModelImpl)oAuthConsumer, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(
				OAuthConsumerModelImpl.ENTITY_CACHE_ENABLED,
				OAuthConsumerImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		OAuthConsumerModelImpl oAuthConsumerModelImpl) {

		Object[] args = new Object[] {
			oAuthConsumerModelImpl.getGadgetKey(),
			oAuthConsumerModelImpl.getServiceName()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByG_S, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByG_S, args, oAuthConsumerModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		OAuthConsumerModelImpl oAuthConsumerModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				oAuthConsumerModelImpl.getGadgetKey(),
				oAuthConsumerModelImpl.getServiceName()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_S, args);
			FinderCacheUtil.removeResult(_finderPathFetchByG_S, args);
		}

		if ((oAuthConsumerModelImpl.getColumnBitmask() &
			 _finderPathFetchByG_S.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				oAuthConsumerModelImpl.getOriginalGadgetKey(),
				oAuthConsumerModelImpl.getOriginalServiceName()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_S, args);
			FinderCacheUtil.removeResult(_finderPathFetchByG_S, args);
		}
	}

	/**
	 * Creates a new o auth consumer with the primary key. Does not add the o auth consumer to the database.
	 *
	 * @param oAuthConsumerId the primary key for the new o auth consumer
	 * @return the new o auth consumer
	 */
	@Override
	public OAuthConsumer create(long oAuthConsumerId) {
		OAuthConsumer oAuthConsumer = new OAuthConsumerImpl();

		oAuthConsumer.setNew(true);
		oAuthConsumer.setPrimaryKey(oAuthConsumerId);

		oAuthConsumer.setCompanyId(CompanyThreadLocal.getCompanyId());

		return oAuthConsumer;
	}

	/**
	 * Removes the o auth consumer with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param oAuthConsumerId the primary key of the o auth consumer
	 * @return the o auth consumer that was removed
	 * @throws NoSuchOAuthConsumerException if a o auth consumer with the primary key could not be found
	 */
	@Override
	public OAuthConsumer remove(long oAuthConsumerId)
		throws NoSuchOAuthConsumerException {

		return remove((Serializable)oAuthConsumerId);
	}

	/**
	 * Removes the o auth consumer with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the o auth consumer
	 * @return the o auth consumer that was removed
	 * @throws NoSuchOAuthConsumerException if a o auth consumer with the primary key could not be found
	 */
	@Override
	public OAuthConsumer remove(Serializable primaryKey)
		throws NoSuchOAuthConsumerException {

		Session session = null;

		try {
			session = openSession();

			OAuthConsumer oAuthConsumer = (OAuthConsumer)session.get(
				OAuthConsumerImpl.class, primaryKey);

			if (oAuthConsumer == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchOAuthConsumerException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(oAuthConsumer);
		}
		catch (NoSuchOAuthConsumerException nsee) {
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
	protected OAuthConsumer removeImpl(OAuthConsumer oAuthConsumer) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(oAuthConsumer)) {
				oAuthConsumer = (OAuthConsumer)session.get(
					OAuthConsumerImpl.class, oAuthConsumer.getPrimaryKeyObj());
			}

			if (oAuthConsumer != null) {
				session.delete(oAuthConsumer);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (oAuthConsumer != null) {
			clearCache(oAuthConsumer);
		}

		return oAuthConsumer;
	}

	@Override
	public OAuthConsumer updateImpl(OAuthConsumer oAuthConsumer) {
		boolean isNew = oAuthConsumer.isNew();

		if (!(oAuthConsumer instanceof OAuthConsumerModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(oAuthConsumer.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					oAuthConsumer);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in oAuthConsumer proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom OAuthConsumer implementation " +
					oAuthConsumer.getClass());
		}

		OAuthConsumerModelImpl oAuthConsumerModelImpl =
			(OAuthConsumerModelImpl)oAuthConsumer;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (oAuthConsumer.getCreateDate() == null)) {
			if (serviceContext == null) {
				oAuthConsumer.setCreateDate(now);
			}
			else {
				oAuthConsumer.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!oAuthConsumerModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				oAuthConsumer.setModifiedDate(now);
			}
			else {
				oAuthConsumer.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (oAuthConsumer.isNew()) {
				session.save(oAuthConsumer);

				oAuthConsumer.setNew(false);
			}
			else {
				oAuthConsumer = (OAuthConsumer)session.merge(oAuthConsumer);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!OAuthConsumerModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				oAuthConsumerModelImpl.getGadgetKey()
			};

			FinderCacheUtil.removeResult(_finderPathCountByGadgetKey, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByGadgetKey, args);

			FinderCacheUtil.removeResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((oAuthConsumerModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByGadgetKey.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					oAuthConsumerModelImpl.getOriginalGadgetKey()
				};

				FinderCacheUtil.removeResult(_finderPathCountByGadgetKey, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByGadgetKey, args);

				args = new Object[] {oAuthConsumerModelImpl.getGadgetKey()};

				FinderCacheUtil.removeResult(_finderPathCountByGadgetKey, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByGadgetKey, args);
			}
		}

		EntityCacheUtil.putResult(
			OAuthConsumerModelImpl.ENTITY_CACHE_ENABLED,
			OAuthConsumerImpl.class, oAuthConsumer.getPrimaryKey(),
			oAuthConsumer, false);

		clearUniqueFindersCache(oAuthConsumerModelImpl, false);
		cacheUniqueFindersCache(oAuthConsumerModelImpl);

		oAuthConsumer.resetOriginalValues();

		return oAuthConsumer;
	}

	/**
	 * Returns the o auth consumer with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the o auth consumer
	 * @return the o auth consumer
	 * @throws NoSuchOAuthConsumerException if a o auth consumer with the primary key could not be found
	 */
	@Override
	public OAuthConsumer findByPrimaryKey(Serializable primaryKey)
		throws NoSuchOAuthConsumerException {

		OAuthConsumer oAuthConsumer = fetchByPrimaryKey(primaryKey);

		if (oAuthConsumer == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchOAuthConsumerException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return oAuthConsumer;
	}

	/**
	 * Returns the o auth consumer with the primary key or throws a <code>NoSuchOAuthConsumerException</code> if it could not be found.
	 *
	 * @param oAuthConsumerId the primary key of the o auth consumer
	 * @return the o auth consumer
	 * @throws NoSuchOAuthConsumerException if a o auth consumer with the primary key could not be found
	 */
	@Override
	public OAuthConsumer findByPrimaryKey(long oAuthConsumerId)
		throws NoSuchOAuthConsumerException {

		return findByPrimaryKey((Serializable)oAuthConsumerId);
	}

	/**
	 * Returns the o auth consumer with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param oAuthConsumerId the primary key of the o auth consumer
	 * @return the o auth consumer, or <code>null</code> if a o auth consumer with the primary key could not be found
	 */
	@Override
	public OAuthConsumer fetchByPrimaryKey(long oAuthConsumerId) {
		return fetchByPrimaryKey((Serializable)oAuthConsumerId);
	}

	/**
	 * Returns all the o auth consumers.
	 *
	 * @return the o auth consumers
	 */
	@Override
	public List<OAuthConsumer> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth consumers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthConsumerModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth consumers
	 * @param end the upper bound of the range of o auth consumers (not inclusive)
	 * @return the range of o auth consumers
	 */
	@Override
	public List<OAuthConsumer> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth consumers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthConsumerModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth consumers
	 * @param end the upper bound of the range of o auth consumers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of o auth consumers
	 */
	@Override
	public List<OAuthConsumer> findAll(
		int start, int end,
		OrderByComparator<OAuthConsumer> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth consumers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthConsumerModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth consumers
	 * @param end the upper bound of the range of o auth consumers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of o auth consumers
	 */
	@Override
	public List<OAuthConsumer> findAll(
		int start, int end, OrderByComparator<OAuthConsumer> orderByComparator,
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

		List<OAuthConsumer> list = null;

		if (useFinderCache) {
			list = (List<OAuthConsumer>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_OAUTHCONSUMER);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_OAUTHCONSUMER;

				sql = sql.concat(OAuthConsumerModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<OAuthConsumer>)QueryUtil.list(
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
	 * Removes all the o auth consumers from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (OAuthConsumer oAuthConsumer : findAll()) {
			remove(oAuthConsumer);
		}
	}

	/**
	 * Returns the number of o auth consumers.
	 *
	 * @return the number of o auth consumers
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_OAUTHCONSUMER);

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
		return "oAuthConsumerId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_OAUTHCONSUMER;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return OAuthConsumerModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the o auth consumer persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			OAuthConsumerModelImpl.ENTITY_CACHE_ENABLED,
			OAuthConsumerModelImpl.FINDER_CACHE_ENABLED,
			OAuthConsumerImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			OAuthConsumerModelImpl.ENTITY_CACHE_ENABLED,
			OAuthConsumerModelImpl.FINDER_CACHE_ENABLED,
			OAuthConsumerImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			OAuthConsumerModelImpl.ENTITY_CACHE_ENABLED,
			OAuthConsumerModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByGadgetKey = new FinderPath(
			OAuthConsumerModelImpl.ENTITY_CACHE_ENABLED,
			OAuthConsumerModelImpl.FINDER_CACHE_ENABLED,
			OAuthConsumerImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByGadgetKey",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByGadgetKey = new FinderPath(
			OAuthConsumerModelImpl.ENTITY_CACHE_ENABLED,
			OAuthConsumerModelImpl.FINDER_CACHE_ENABLED,
			OAuthConsumerImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByGadgetKey", new String[] {String.class.getName()},
			OAuthConsumerModelImpl.GADGETKEY_COLUMN_BITMASK |
			OAuthConsumerModelImpl.SERVICENAME_COLUMN_BITMASK);

		_finderPathCountByGadgetKey = new FinderPath(
			OAuthConsumerModelImpl.ENTITY_CACHE_ENABLED,
			OAuthConsumerModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGadgetKey",
			new String[] {String.class.getName()});

		_finderPathFetchByG_S = new FinderPath(
			OAuthConsumerModelImpl.ENTITY_CACHE_ENABLED,
			OAuthConsumerModelImpl.FINDER_CACHE_ENABLED,
			OAuthConsumerImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByG_S",
			new String[] {String.class.getName(), String.class.getName()},
			OAuthConsumerModelImpl.GADGETKEY_COLUMN_BITMASK |
			OAuthConsumerModelImpl.SERVICENAME_COLUMN_BITMASK);

		_finderPathCountByG_S = new FinderPath(
			OAuthConsumerModelImpl.ENTITY_CACHE_ENABLED,
			OAuthConsumerModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_S",
			new String[] {String.class.getName(), String.class.getName()});
	}

	public void destroy() {
		EntityCacheUtil.removeCache(OAuthConsumerImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_OAUTHCONSUMER =
		"SELECT oAuthConsumer FROM OAuthConsumer oAuthConsumer";

	private static final String _SQL_SELECT_OAUTHCONSUMER_WHERE =
		"SELECT oAuthConsumer FROM OAuthConsumer oAuthConsumer WHERE ";

	private static final String _SQL_COUNT_OAUTHCONSUMER =
		"SELECT COUNT(oAuthConsumer) FROM OAuthConsumer oAuthConsumer";

	private static final String _SQL_COUNT_OAUTHCONSUMER_WHERE =
		"SELECT COUNT(oAuthConsumer) FROM OAuthConsumer oAuthConsumer WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "oAuthConsumer.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No OAuthConsumer exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No OAuthConsumer exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		OAuthConsumerPersistenceImpl.class);

}