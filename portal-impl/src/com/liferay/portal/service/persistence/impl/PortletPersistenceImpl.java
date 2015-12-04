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

package com.liferay.portal.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.NoSuchPortletException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.MVCCModel;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.model.impl.PortletModelImpl;
import com.liferay.portal.service.persistence.CompanyProvider;
import com.liferay.portal.service.persistence.PortletPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the portlet service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PortletPersistence
 * @see com.liferay.portal.service.persistence.PortletUtil
 * @generated
 */
@ProviderType
public class PortletPersistenceImpl extends BasePersistenceImpl<Portlet>
	implements PortletPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link PortletUtil} to access the portlet persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = PortletImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(PortletModelImpl.ENTITY_CACHE_ENABLED,
			PortletModelImpl.FINDER_CACHE_ENABLED, PortletImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(PortletModelImpl.ENTITY_CACHE_ENABLED,
			PortletModelImpl.FINDER_CACHE_ENABLED, PortletImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(PortletModelImpl.ENTITY_CACHE_ENABLED,
			PortletModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(PortletModelImpl.ENTITY_CACHE_ENABLED,
			PortletModelImpl.FINDER_CACHE_ENABLED, PortletImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(PortletModelImpl.ENTITY_CACHE_ENABLED,
			PortletModelImpl.FINDER_CACHE_ENABLED, PortletImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			PortletModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(PortletModelImpl.ENTITY_CACHE_ENABLED,
			PortletModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the portlets where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching portlets
	 */
	@Override
	public List<Portlet> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the portlets where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of portlets
	 * @param end the upper bound of the range of portlets (not inclusive)
	 * @return the range of matching portlets
	 */
	@Override
	public List<Portlet> findByCompanyId(long companyId, int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the portlets where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of portlets
	 * @param end the upper bound of the range of portlets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching portlets
	 */
	@Override
	public List<Portlet> findByCompanyId(long companyId, int start, int end,
		OrderByComparator<Portlet> orderByComparator) {
		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the portlets where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of portlets
	 * @param end the upper bound of the range of portlets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching portlets
	 */
	@Override
	public List<Portlet> findByCompanyId(long companyId, int start, int end,
		OrderByComparator<Portlet> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId, start, end, orderByComparator };
		}

		List<Portlet> list = null;

		if (retrieveFromCache) {
			list = (List<Portlet>)finderCache.getResult(finderPath, finderArgs,
					this);

			if ((list != null) && !list.isEmpty()) {
				for (Portlet portlet : list) {
					if ((companyId != portlet.getCompanyId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_PORTLET_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(PortletModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<Portlet>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Portlet>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first portlet in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet
	 * @throws NoSuchPortletException if a matching portlet could not be found
	 */
	@Override
	public Portlet findByCompanyId_First(long companyId,
		OrderByComparator<Portlet> orderByComparator)
		throws NoSuchPortletException {
		Portlet portlet = fetchByCompanyId_First(companyId, orderByComparator);

		if (portlet != null) {
			return portlet;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPortletException(msg.toString());
	}

	/**
	 * Returns the first portlet in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet, or <code>null</code> if a matching portlet could not be found
	 */
	@Override
	public Portlet fetchByCompanyId_First(long companyId,
		OrderByComparator<Portlet> orderByComparator) {
		List<Portlet> list = findByCompanyId(companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last portlet in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet
	 * @throws NoSuchPortletException if a matching portlet could not be found
	 */
	@Override
	public Portlet findByCompanyId_Last(long companyId,
		OrderByComparator<Portlet> orderByComparator)
		throws NoSuchPortletException {
		Portlet portlet = fetchByCompanyId_Last(companyId, orderByComparator);

		if (portlet != null) {
			return portlet;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPortletException(msg.toString());
	}

	/**
	 * Returns the last portlet in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet, or <code>null</code> if a matching portlet could not be found
	 */
	@Override
	public Portlet fetchByCompanyId_Last(long companyId,
		OrderByComparator<Portlet> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<Portlet> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the portlets before and after the current portlet in the ordered set where companyId = &#63;.
	 *
	 * @param id the primary key of the current portlet
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next portlet
	 * @throws NoSuchPortletException if a portlet with the primary key could not be found
	 */
	@Override
	public Portlet[] findByCompanyId_PrevAndNext(long id, long companyId,
		OrderByComparator<Portlet> orderByComparator)
		throws NoSuchPortletException {
		Portlet portlet = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			Portlet[] array = new PortletImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, portlet, companyId,
					orderByComparator, true);

			array[1] = portlet;

			array[2] = getByCompanyId_PrevAndNext(session, portlet, companyId,
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

	protected Portlet getByCompanyId_PrevAndNext(Session session,
		Portlet portlet, long companyId,
		OrderByComparator<Portlet> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_PORTLET_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

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
			query.append(PortletModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(portlet);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Portlet> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the portlets where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (Portlet portlet : findByCompanyId(companyId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(portlet);
		}
	}

	/**
	 * Returns the number of portlets where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching portlets
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_PORTLET_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "portlet.companyId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_P = new FinderPath(PortletModelImpl.ENTITY_CACHE_ENABLED,
			PortletModelImpl.FINDER_CACHE_ENABLED, PortletImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_P",
			new String[] { Long.class.getName(), String.class.getName() },
			PortletModelImpl.COMPANYID_COLUMN_BITMASK |
			PortletModelImpl.PORTLETID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_P = new FinderPath(PortletModelImpl.ENTITY_CACHE_ENABLED,
			PortletModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_P",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the portlet where companyId = &#63; and portletId = &#63; or throws a {@link NoSuchPortletException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param portletId the portlet ID
	 * @return the matching portlet
	 * @throws NoSuchPortletException if a matching portlet could not be found
	 */
	@Override
	public Portlet findByC_P(long companyId, String portletId)
		throws NoSuchPortletException {
		Portlet portlet = fetchByC_P(companyId, portletId);

		if (portlet == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", portletId=");
			msg.append(portletId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchPortletException(msg.toString());
		}

		return portlet;
	}

	/**
	 * Returns the portlet where companyId = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param portletId the portlet ID
	 * @return the matching portlet, or <code>null</code> if a matching portlet could not be found
	 */
	@Override
	public Portlet fetchByC_P(long companyId, String portletId) {
		return fetchByC_P(companyId, portletId, true);
	}

	/**
	 * Returns the portlet where companyId = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param portletId the portlet ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching portlet, or <code>null</code> if a matching portlet could not be found
	 */
	@Override
	public Portlet fetchByC_P(long companyId, String portletId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { companyId, portletId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_P,
					finderArgs, this);
		}

		if (result instanceof Portlet) {
			Portlet portlet = (Portlet)result;

			if ((companyId != portlet.getCompanyId()) ||
					!Validator.equals(portletId, portlet.getPortletId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_PORTLET_WHERE);

			query.append(_FINDER_COLUMN_C_P_COMPANYID_2);

			boolean bindPortletId = false;

			if (portletId == null) {
				query.append(_FINDER_COLUMN_C_P_PORTLETID_1);
			}
			else if (portletId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_P_PORTLETID_3);
			}
			else {
				bindPortletId = true;

				query.append(_FINDER_COLUMN_C_P_PORTLETID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindPortletId) {
					qPos.add(portletId);
				}

				List<Portlet> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_P, finderArgs,
						list);
				}
				else {
					Portlet portlet = list.get(0);

					result = portlet;

					cacheResult(portlet);

					if ((portlet.getCompanyId() != companyId) ||
							(portlet.getPortletId() == null) ||
							!portlet.getPortletId().equals(portletId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_C_P,
							finderArgs, portlet);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C_P, finderArgs);

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
			return (Portlet)result;
		}
	}

	/**
	 * Removes the portlet where companyId = &#63; and portletId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param portletId the portlet ID
	 * @return the portlet that was removed
	 */
	@Override
	public Portlet removeByC_P(long companyId, String portletId)
		throws NoSuchPortletException {
		Portlet portlet = findByC_P(companyId, portletId);

		return remove(portlet);
	}

	/**
	 * Returns the number of portlets where companyId = &#63; and portletId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param portletId the portlet ID
	 * @return the number of matching portlets
	 */
	@Override
	public int countByC_P(long companyId, String portletId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_P;

		Object[] finderArgs = new Object[] { companyId, portletId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_PORTLET_WHERE);

			query.append(_FINDER_COLUMN_C_P_COMPANYID_2);

			boolean bindPortletId = false;

			if (portletId == null) {
				query.append(_FINDER_COLUMN_C_P_PORTLETID_1);
			}
			else if (portletId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_P_PORTLETID_3);
			}
			else {
				bindPortletId = true;

				query.append(_FINDER_COLUMN_C_P_PORTLETID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindPortletId) {
					qPos.add(portletId);
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

	private static final String _FINDER_COLUMN_C_P_COMPANYID_2 = "portlet.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_P_PORTLETID_1 = "portlet.portletId IS NULL";
	private static final String _FINDER_COLUMN_C_P_PORTLETID_2 = "portlet.portletId = ?";
	private static final String _FINDER_COLUMN_C_P_PORTLETID_3 = "(portlet.portletId IS NULL OR portlet.portletId = '')";

	public PortletPersistenceImpl() {
		setModelClass(Portlet.class);
	}

	/**
	 * Caches the portlet in the entity cache if it is enabled.
	 *
	 * @param portlet the portlet
	 */
	@Override
	public void cacheResult(Portlet portlet) {
		entityCache.putResult(PortletModelImpl.ENTITY_CACHE_ENABLED,
			PortletImpl.class, portlet.getPrimaryKey(), portlet);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_P,
			new Object[] { portlet.getCompanyId(), portlet.getPortletId() },
			portlet);

		portlet.resetOriginalValues();
	}

	/**
	 * Caches the portlets in the entity cache if it is enabled.
	 *
	 * @param portlets the portlets
	 */
	@Override
	public void cacheResult(List<Portlet> portlets) {
		for (Portlet portlet : portlets) {
			if (entityCache.getResult(PortletModelImpl.ENTITY_CACHE_ENABLED,
						PortletImpl.class, portlet.getPrimaryKey()) == null) {
				cacheResult(portlet);
			}
			else {
				portlet.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all portlets.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(PortletImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the portlet.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Portlet portlet) {
		entityCache.removeResult(PortletModelImpl.ENTITY_CACHE_ENABLED,
			PortletImpl.class, portlet.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((PortletModelImpl)portlet);
	}

	@Override
	public void clearCache(List<Portlet> portlets) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Portlet portlet : portlets) {
			entityCache.removeResult(PortletModelImpl.ENTITY_CACHE_ENABLED,
				PortletImpl.class, portlet.getPrimaryKey());

			clearUniqueFindersCache((PortletModelImpl)portlet);
		}
	}

	protected void cacheUniqueFindersCache(PortletModelImpl portletModelImpl,
		boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] {
					portletModelImpl.getCompanyId(),
					portletModelImpl.getPortletId()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_C_P, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_C_P, args,
				portletModelImpl);
		}
		else {
			if ((portletModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						portletModelImpl.getCompanyId(),
						portletModelImpl.getPortletId()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_C_P, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_C_P, args,
					portletModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(PortletModelImpl portletModelImpl) {
		Object[] args = new Object[] {
				portletModelImpl.getCompanyId(), portletModelImpl.getPortletId()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_C_P, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_C_P, args);

		if ((portletModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_P.getColumnBitmask()) != 0) {
			args = new Object[] {
					portletModelImpl.getOriginalCompanyId(),
					portletModelImpl.getOriginalPortletId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_P, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_P, args);
		}
	}

	/**
	 * Creates a new portlet with the primary key. Does not add the portlet to the database.
	 *
	 * @param id the primary key for the new portlet
	 * @return the new portlet
	 */
	@Override
	public Portlet create(long id) {
		Portlet portlet = new PortletImpl();

		portlet.setNew(true);
		portlet.setPrimaryKey(id);

		return portlet;
	}

	/**
	 * Removes the portlet with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param id the primary key of the portlet
	 * @return the portlet that was removed
	 * @throws NoSuchPortletException if a portlet with the primary key could not be found
	 */
	@Override
	public Portlet remove(long id) throws NoSuchPortletException {
		return remove((Serializable)id);
	}

	/**
	 * Removes the portlet with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the portlet
	 * @return the portlet that was removed
	 * @throws NoSuchPortletException if a portlet with the primary key could not be found
	 */
	@Override
	public Portlet remove(Serializable primaryKey)
		throws NoSuchPortletException {
		Session session = null;

		try {
			session = openSession();

			Portlet portlet = (Portlet)session.get(PortletImpl.class, primaryKey);

			if (portlet == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPortletException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(portlet);
		}
		catch (NoSuchPortletException nsee) {
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
	protected Portlet removeImpl(Portlet portlet) {
		portlet = toUnwrappedModel(portlet);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(portlet)) {
				portlet = (Portlet)session.get(PortletImpl.class,
						portlet.getPrimaryKeyObj());
			}

			if (portlet != null) {
				session.delete(portlet);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (portlet != null) {
			clearCache(portlet);
		}

		return portlet;
	}

	@Override
	public Portlet updateImpl(Portlet portlet) {
		portlet = toUnwrappedModel(portlet);

		boolean isNew = portlet.isNew();

		PortletModelImpl portletModelImpl = (PortletModelImpl)portlet;

		Session session = null;

		try {
			session = openSession();

			if (portlet.isNew()) {
				session.save(portlet);

				portlet.setNew(false);
			}
			else {
				portlet = (Portlet)session.merge(portlet);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !PortletModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((portletModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						portletModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] { portletModelImpl.getCompanyId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}
		}

		entityCache.putResult(PortletModelImpl.ENTITY_CACHE_ENABLED,
			PortletImpl.class, portlet.getPrimaryKey(), portlet, false);

		clearUniqueFindersCache(portletModelImpl);
		cacheUniqueFindersCache(portletModelImpl, isNew);

		portlet.resetOriginalValues();

		return portlet;
	}

	protected Portlet toUnwrappedModel(Portlet portlet) {
		if (portlet instanceof PortletImpl) {
			return portlet;
		}

		PortletImpl portletImpl = new PortletImpl();

		portletImpl.setNew(portlet.isNew());
		portletImpl.setPrimaryKey(portlet.getPrimaryKey());

		portletImpl.setMvccVersion(portlet.getMvccVersion());
		portletImpl.setId(portlet.getId());
		portletImpl.setCompanyId(portlet.getCompanyId());
		portletImpl.setPortletId(portlet.getPortletId());
		portletImpl.setRoles(portlet.getRoles());
		portletImpl.setActive(portlet.isActive());

		return portletImpl;
	}

	/**
	 * Returns the portlet with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the portlet
	 * @return the portlet
	 * @throws NoSuchPortletException if a portlet with the primary key could not be found
	 */
	@Override
	public Portlet findByPrimaryKey(Serializable primaryKey)
		throws NoSuchPortletException {
		Portlet portlet = fetchByPrimaryKey(primaryKey);

		if (portlet == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPortletException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return portlet;
	}

	/**
	 * Returns the portlet with the primary key or throws a {@link NoSuchPortletException} if it could not be found.
	 *
	 * @param id the primary key of the portlet
	 * @return the portlet
	 * @throws NoSuchPortletException if a portlet with the primary key could not be found
	 */
	@Override
	public Portlet findByPrimaryKey(long id) throws NoSuchPortletException {
		return findByPrimaryKey((Serializable)id);
	}

	/**
	 * Returns the portlet with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the portlet
	 * @return the portlet, or <code>null</code> if a portlet with the primary key could not be found
	 */
	@Override
	public Portlet fetchByPrimaryKey(Serializable primaryKey) {
		Portlet portlet = (Portlet)entityCache.getResult(PortletModelImpl.ENTITY_CACHE_ENABLED,
				PortletImpl.class, primaryKey);

		if (portlet == _nullPortlet) {
			return null;
		}

		if (portlet == null) {
			Session session = null;

			try {
				session = openSession();

				portlet = (Portlet)session.get(PortletImpl.class, primaryKey);

				if (portlet != null) {
					cacheResult(portlet);
				}
				else {
					entityCache.putResult(PortletModelImpl.ENTITY_CACHE_ENABLED,
						PortletImpl.class, primaryKey, _nullPortlet);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(PortletModelImpl.ENTITY_CACHE_ENABLED,
					PortletImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return portlet;
	}

	/**
	 * Returns the portlet with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param id the primary key of the portlet
	 * @return the portlet, or <code>null</code> if a portlet with the primary key could not be found
	 */
	@Override
	public Portlet fetchByPrimaryKey(long id) {
		return fetchByPrimaryKey((Serializable)id);
	}

	@Override
	public Map<Serializable, Portlet> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, Portlet> map = new HashMap<Serializable, Portlet>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			Portlet portlet = fetchByPrimaryKey(primaryKey);

			if (portlet != null) {
				map.put(primaryKey, portlet);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Portlet portlet = (Portlet)entityCache.getResult(PortletModelImpl.ENTITY_CACHE_ENABLED,
					PortletImpl.class, primaryKey);

			if (portlet == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, portlet);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_PORTLET_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append(String.valueOf(primaryKey));

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (Portlet portlet : (List<Portlet>)q.list()) {
				map.put(portlet.getPrimaryKeyObj(), portlet);

				cacheResult(portlet);

				uncachedPrimaryKeys.remove(portlet.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(PortletModelImpl.ENTITY_CACHE_ENABLED,
					PortletImpl.class, primaryKey, _nullPortlet);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the portlets.
	 *
	 * @return the portlets
	 */
	@Override
	public List<Portlet> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the portlets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of portlets
	 * @param end the upper bound of the range of portlets (not inclusive)
	 * @return the range of portlets
	 */
	@Override
	public List<Portlet> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the portlets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of portlets
	 * @param end the upper bound of the range of portlets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of portlets
	 */
	@Override
	public List<Portlet> findAll(int start, int end,
		OrderByComparator<Portlet> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the portlets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of portlets
	 * @param end the upper bound of the range of portlets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of portlets
	 */
	@Override
	public List<Portlet> findAll(int start, int end,
		OrderByComparator<Portlet> orderByComparator, boolean retrieveFromCache) {
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

		List<Portlet> list = null;

		if (retrieveFromCache) {
			list = (List<Portlet>)finderCache.getResult(finderPath, finderArgs,
					this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_PORTLET);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_PORTLET;

				if (pagination) {
					sql = sql.concat(PortletModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<Portlet>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Portlet>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the portlets from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (Portlet portlet : findAll()) {
			remove(portlet);
		}
	}

	/**
	 * Returns the number of portlets.
	 *
	 * @return the number of portlets
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_PORTLET);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_COUNT_ALL,
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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return PortletModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the portlet persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(PortletImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = CompanyProvider.class)
	protected CompanyProvider companyProvider;
	protected EntityCache entityCache = EntityCacheUtil.getEntityCache();
	protected FinderCache finderCache = FinderCacheUtil.getFinderCache();
	private static final String _SQL_SELECT_PORTLET = "SELECT portlet FROM Portlet portlet";
	private static final String _SQL_SELECT_PORTLET_WHERE_PKS_IN = "SELECT portlet FROM Portlet portlet WHERE id_ IN (";
	private static final String _SQL_SELECT_PORTLET_WHERE = "SELECT portlet FROM Portlet portlet WHERE ";
	private static final String _SQL_COUNT_PORTLET = "SELECT COUNT(portlet) FROM Portlet portlet";
	private static final String _SQL_COUNT_PORTLET_WHERE = "SELECT COUNT(portlet) FROM Portlet portlet WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "portlet.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Portlet exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Portlet exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(PortletPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"id", "active"
			});
	private static final Portlet _nullPortlet = new PortletImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<Portlet> toCacheModel() {
				return _nullPortletCacheModel;
			}
		};

	private static final CacheModel<Portlet> _nullPortletCacheModel = new NullCacheModel();

	private static class NullCacheModel implements CacheModel<Portlet>,
		MVCCModel {
		@Override
		public long getMvccVersion() {
			return -1;
		}

		@Override
		public void setMvccVersion(long mvccVersion) {
		}

		@Override
		public Portlet toEntityModel() {
			return _nullPortlet;
		}
	}
}