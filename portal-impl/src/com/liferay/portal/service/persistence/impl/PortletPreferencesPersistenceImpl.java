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

import com.liferay.portal.NoSuchPortletPreferencesException;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.MVCCModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.model.impl.PortletPreferencesImpl;
import com.liferay.portal.model.impl.PortletPreferencesModelImpl;
import com.liferay.portal.service.persistence.PortletPreferencesPersistence;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the portlet preferences service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PortletPreferencesPersistence
 * @see PortletPreferencesUtil
 * @generated
 */
public class PortletPreferencesPersistenceImpl extends BasePersistenceImpl<PortletPreferences>
	implements PortletPreferencesPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link PortletPreferencesUtil} to access the portlet preferences persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = PortletPreferencesImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED,
			PortletPreferencesImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED,
			PortletPreferencesImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_PLID = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED,
			PortletPreferencesImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByPlid",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PLID = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED,
			PortletPreferencesImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByPlid",
			new String[] { Long.class.getName() },
			PortletPreferencesModelImpl.PLID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_PLID = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPlid",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the portlet preferenceses where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the matching portlet preferenceses
	 */
	@Override
	public List<PortletPreferences> findByPlid(long plid) {
		return findByPlid(plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the portlet preferenceses where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletPreferencesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of portlet preferenceses
	 * @param end the upper bound of the range of portlet preferenceses (not inclusive)
	 * @return the range of matching portlet preferenceses
	 */
	@Override
	public List<PortletPreferences> findByPlid(long plid, int start, int end) {
		return findByPlid(plid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the portlet preferenceses where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletPreferencesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of portlet preferenceses
	 * @param end the upper bound of the range of portlet preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching portlet preferenceses
	 */
	@Override
	public List<PortletPreferences> findByPlid(long plid, int start, int end,
		OrderByComparator orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PLID;
			finderArgs = new Object[] { plid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_PLID;
			finderArgs = new Object[] { plid, start, end, orderByComparator };
		}

		List<PortletPreferences> list = (List<PortletPreferences>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (PortletPreferences portletPreferences : list) {
				if ((plid != portletPreferences.getPlid())) {
					list = null;

					break;
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

			query.append(_SQL_SELECT_PORTLETPREFERENCES_WHERE);

			query.append(_FINDER_COLUMN_PLID_PLID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(PortletPreferencesModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(plid);

				if (!pagination) {
					list = (List<PortletPreferences>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<PortletPreferences>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first portlet preferences in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preferences
	 * @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	 */
	@Override
	public PortletPreferences findByPlid_First(long plid,
		OrderByComparator orderByComparator)
		throws NoSuchPortletPreferencesException {
		PortletPreferences portletPreferences = fetchByPlid_First(plid,
				orderByComparator);

		if (portletPreferences != null) {
			return portletPreferences;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("plid=");
		msg.append(plid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPortletPreferencesException(msg.toString());
	}

	/**
	 * Returns the first portlet preferences in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	 */
	@Override
	public PortletPreferences fetchByPlid_First(long plid,
		OrderByComparator orderByComparator) {
		List<PortletPreferences> list = findByPlid(plid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last portlet preferences in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preferences
	 * @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	 */
	@Override
	public PortletPreferences findByPlid_Last(long plid,
		OrderByComparator orderByComparator)
		throws NoSuchPortletPreferencesException {
		PortletPreferences portletPreferences = fetchByPlid_Last(plid,
				orderByComparator);

		if (portletPreferences != null) {
			return portletPreferences;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("plid=");
		msg.append(plid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPortletPreferencesException(msg.toString());
	}

	/**
	 * Returns the last portlet preferences in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	 */
	@Override
	public PortletPreferences fetchByPlid_Last(long plid,
		OrderByComparator orderByComparator) {
		int count = countByPlid(plid);

		if (count == 0) {
			return null;
		}

		List<PortletPreferences> list = findByPlid(plid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the portlet preferenceses before and after the current portlet preferences in the ordered set where plid = &#63;.
	 *
	 * @param portletPreferencesId the primary key of the current portlet preferences
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next portlet preferences
	 * @throws com.liferay.portal.NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	 */
	@Override
	public PortletPreferences[] findByPlid_PrevAndNext(
		long portletPreferencesId, long plid,
		OrderByComparator orderByComparator)
		throws NoSuchPortletPreferencesException {
		PortletPreferences portletPreferences = findByPrimaryKey(portletPreferencesId);

		Session session = null;

		try {
			session = openSession();

			PortletPreferences[] array = new PortletPreferencesImpl[3];

			array[0] = getByPlid_PrevAndNext(session, portletPreferences, plid,
					orderByComparator, true);

			array[1] = portletPreferences;

			array[2] = getByPlid_PrevAndNext(session, portletPreferences, plid,
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

	protected PortletPreferences getByPlid_PrevAndNext(Session session,
		PortletPreferences portletPreferences, long plid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_PORTLETPREFERENCES_WHERE);

		query.append(_FINDER_COLUMN_PLID_PLID_2);

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
			query.append(PortletPreferencesModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(plid);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(portletPreferences);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<PortletPreferences> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the portlet preferenceses where plid = &#63; from the database.
	 *
	 * @param plid the plid
	 */
	@Override
	public void removeByPlid(long plid) {
		for (PortletPreferences portletPreferences : findByPlid(plid,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(portletPreferences);
		}
	}

	/**
	 * Returns the number of portlet preferenceses where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the number of matching portlet preferenceses
	 */
	@Override
	public int countByPlid(long plid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_PLID;

		Object[] finderArgs = new Object[] { plid };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_PORTLETPREFERENCES_WHERE);

			query.append(_FINDER_COLUMN_PLID_PLID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(plid);

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

	private static final String _FINDER_COLUMN_PLID_PLID_2 = "portletPreferences.plid = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_PORTLETID =
		new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED,
			PortletPreferencesImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByPortletId",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PORTLETID =
		new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED,
			PortletPreferencesImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByPortletId",
			new String[] { String.class.getName() },
			PortletPreferencesModelImpl.PORTLETID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_PORTLETID = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPortletId",
			new String[] { String.class.getName() });

	/**
	 * Returns all the portlet preferenceses where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @return the matching portlet preferenceses
	 */
	@Override
	public List<PortletPreferences> findByPortletId(String portletId) {
		return findByPortletId(portletId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the portlet preferenceses where portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletPreferencesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of portlet preferenceses
	 * @param end the upper bound of the range of portlet preferenceses (not inclusive)
	 * @return the range of matching portlet preferenceses
	 */
	@Override
	public List<PortletPreferences> findByPortletId(String portletId,
		int start, int end) {
		return findByPortletId(portletId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the portlet preferenceses where portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletPreferencesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of portlet preferenceses
	 * @param end the upper bound of the range of portlet preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching portlet preferenceses
	 */
	@Override
	public List<PortletPreferences> findByPortletId(String portletId,
		int start, int end, OrderByComparator orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PORTLETID;
			finderArgs = new Object[] { portletId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_PORTLETID;
			finderArgs = new Object[] { portletId, start, end, orderByComparator };
		}

		List<PortletPreferences> list = (List<PortletPreferences>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (PortletPreferences portletPreferences : list) {
				if (!Validator.equals(portletId,
							portletPreferences.getPortletId())) {
					list = null;

					break;
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

			query.append(_SQL_SELECT_PORTLETPREFERENCES_WHERE);

			boolean bindPortletId = false;

			if (portletId == null) {
				query.append(_FINDER_COLUMN_PORTLETID_PORTLETID_1);
			}
			else if (portletId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_PORTLETID_PORTLETID_3);
			}
			else {
				bindPortletId = true;

				query.append(_FINDER_COLUMN_PORTLETID_PORTLETID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(PortletPreferencesModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindPortletId) {
					qPos.add(portletId);
				}

				if (!pagination) {
					list = (List<PortletPreferences>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<PortletPreferences>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first portlet preferences in the ordered set where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preferences
	 * @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	 */
	@Override
	public PortletPreferences findByPortletId_First(String portletId,
		OrderByComparator orderByComparator)
		throws NoSuchPortletPreferencesException {
		PortletPreferences portletPreferences = fetchByPortletId_First(portletId,
				orderByComparator);

		if (portletPreferences != null) {
			return portletPreferences;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("portletId=");
		msg.append(portletId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPortletPreferencesException(msg.toString());
	}

	/**
	 * Returns the first portlet preferences in the ordered set where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	 */
	@Override
	public PortletPreferences fetchByPortletId_First(String portletId,
		OrderByComparator orderByComparator) {
		List<PortletPreferences> list = findByPortletId(portletId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last portlet preferences in the ordered set where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preferences
	 * @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	 */
	@Override
	public PortletPreferences findByPortletId_Last(String portletId,
		OrderByComparator orderByComparator)
		throws NoSuchPortletPreferencesException {
		PortletPreferences portletPreferences = fetchByPortletId_Last(portletId,
				orderByComparator);

		if (portletPreferences != null) {
			return portletPreferences;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("portletId=");
		msg.append(portletId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPortletPreferencesException(msg.toString());
	}

	/**
	 * Returns the last portlet preferences in the ordered set where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	 */
	@Override
	public PortletPreferences fetchByPortletId_Last(String portletId,
		OrderByComparator orderByComparator) {
		int count = countByPortletId(portletId);

		if (count == 0) {
			return null;
		}

		List<PortletPreferences> list = findByPortletId(portletId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the portlet preferenceses before and after the current portlet preferences in the ordered set where portletId = &#63;.
	 *
	 * @param portletPreferencesId the primary key of the current portlet preferences
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next portlet preferences
	 * @throws com.liferay.portal.NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	 */
	@Override
	public PortletPreferences[] findByPortletId_PrevAndNext(
		long portletPreferencesId, String portletId,
		OrderByComparator orderByComparator)
		throws NoSuchPortletPreferencesException {
		PortletPreferences portletPreferences = findByPrimaryKey(portletPreferencesId);

		Session session = null;

		try {
			session = openSession();

			PortletPreferences[] array = new PortletPreferencesImpl[3];

			array[0] = getByPortletId_PrevAndNext(session, portletPreferences,
					portletId, orderByComparator, true);

			array[1] = portletPreferences;

			array[2] = getByPortletId_PrevAndNext(session, portletPreferences,
					portletId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected PortletPreferences getByPortletId_PrevAndNext(Session session,
		PortletPreferences portletPreferences, String portletId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_PORTLETPREFERENCES_WHERE);

		boolean bindPortletId = false;

		if (portletId == null) {
			query.append(_FINDER_COLUMN_PORTLETID_PORTLETID_1);
		}
		else if (portletId.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_PORTLETID_PORTLETID_3);
		}
		else {
			bindPortletId = true;

			query.append(_FINDER_COLUMN_PORTLETID_PORTLETID_2);
		}

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
			query.append(PortletPreferencesModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindPortletId) {
			qPos.add(portletId);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(portletPreferences);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<PortletPreferences> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the portlet preferenceses where portletId = &#63; from the database.
	 *
	 * @param portletId the portlet ID
	 */
	@Override
	public void removeByPortletId(String portletId) {
		for (PortletPreferences portletPreferences : findByPortletId(
				portletId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(portletPreferences);
		}
	}

	/**
	 * Returns the number of portlet preferenceses where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @return the number of matching portlet preferenceses
	 */
	@Override
	public int countByPortletId(String portletId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_PORTLETID;

		Object[] finderArgs = new Object[] { portletId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_PORTLETPREFERENCES_WHERE);

			boolean bindPortletId = false;

			if (portletId == null) {
				query.append(_FINDER_COLUMN_PORTLETID_PORTLETID_1);
			}
			else if (portletId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_PORTLETID_PORTLETID_3);
			}
			else {
				bindPortletId = true;

				query.append(_FINDER_COLUMN_PORTLETID_PORTLETID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindPortletId) {
					qPos.add(portletId);
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

	private static final String _FINDER_COLUMN_PORTLETID_PORTLETID_1 = "portletPreferences.portletId IS NULL";
	private static final String _FINDER_COLUMN_PORTLETID_PORTLETID_2 = "portletPreferences.portletId = ?";
	private static final String _FINDER_COLUMN_PORTLETID_PORTLETID_3 = "(portletPreferences.portletId IS NULL OR portletPreferences.portletId = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_O_P = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED,
			PortletPreferencesImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByO_P",
			new String[] {
				Integer.class.getName(), String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_O_P = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED,
			PortletPreferencesImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByO_P",
			new String[] { Integer.class.getName(), String.class.getName() },
			PortletPreferencesModelImpl.OWNERTYPE_COLUMN_BITMASK |
			PortletPreferencesModelImpl.PORTLETID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_O_P = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByO_P",
			new String[] { Integer.class.getName(), String.class.getName() });

	/**
	 * Returns all the portlet preferenceses where ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @return the matching portlet preferenceses
	 */
	@Override
	public List<PortletPreferences> findByO_P(int ownerType, String portletId) {
		return findByO_P(ownerType, portletId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the portlet preferenceses where ownerType = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletPreferencesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of portlet preferenceses
	 * @param end the upper bound of the range of portlet preferenceses (not inclusive)
	 * @return the range of matching portlet preferenceses
	 */
	@Override
	public List<PortletPreferences> findByO_P(int ownerType, String portletId,
		int start, int end) {
		return findByO_P(ownerType, portletId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the portlet preferenceses where ownerType = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletPreferencesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of portlet preferenceses
	 * @param end the upper bound of the range of portlet preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching portlet preferenceses
	 */
	@Override
	public List<PortletPreferences> findByO_P(int ownerType, String portletId,
		int start, int end, OrderByComparator orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_O_P;
			finderArgs = new Object[] { ownerType, portletId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_O_P;
			finderArgs = new Object[] {
					ownerType, portletId,
					
					start, end, orderByComparator
				};
		}

		List<PortletPreferences> list = (List<PortletPreferences>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (PortletPreferences portletPreferences : list) {
				if ((ownerType != portletPreferences.getOwnerType()) ||
						!Validator.equals(portletId,
							portletPreferences.getPortletId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_PORTLETPREFERENCES_WHERE);

			query.append(_FINDER_COLUMN_O_P_OWNERTYPE_2);

			boolean bindPortletId = false;

			if (portletId == null) {
				query.append(_FINDER_COLUMN_O_P_PORTLETID_1);
			}
			else if (portletId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_O_P_PORTLETID_3);
			}
			else {
				bindPortletId = true;

				query.append(_FINDER_COLUMN_O_P_PORTLETID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(PortletPreferencesModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ownerType);

				if (bindPortletId) {
					qPos.add(portletId);
				}

				if (!pagination) {
					list = (List<PortletPreferences>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<PortletPreferences>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first portlet preferences in the ordered set where ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preferences
	 * @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	 */
	@Override
	public PortletPreferences findByO_P_First(int ownerType, String portletId,
		OrderByComparator orderByComparator)
		throws NoSuchPortletPreferencesException {
		PortletPreferences portletPreferences = fetchByO_P_First(ownerType,
				portletId, orderByComparator);

		if (portletPreferences != null) {
			return portletPreferences;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("ownerType=");
		msg.append(ownerType);

		msg.append(", portletId=");
		msg.append(portletId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPortletPreferencesException(msg.toString());
	}

	/**
	 * Returns the first portlet preferences in the ordered set where ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	 */
	@Override
	public PortletPreferences fetchByO_P_First(int ownerType, String portletId,
		OrderByComparator orderByComparator) {
		List<PortletPreferences> list = findByO_P(ownerType, portletId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last portlet preferences in the ordered set where ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preferences
	 * @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	 */
	@Override
	public PortletPreferences findByO_P_Last(int ownerType, String portletId,
		OrderByComparator orderByComparator)
		throws NoSuchPortletPreferencesException {
		PortletPreferences portletPreferences = fetchByO_P_Last(ownerType,
				portletId, orderByComparator);

		if (portletPreferences != null) {
			return portletPreferences;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("ownerType=");
		msg.append(ownerType);

		msg.append(", portletId=");
		msg.append(portletId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPortletPreferencesException(msg.toString());
	}

	/**
	 * Returns the last portlet preferences in the ordered set where ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	 */
	@Override
	public PortletPreferences fetchByO_P_Last(int ownerType, String portletId,
		OrderByComparator orderByComparator) {
		int count = countByO_P(ownerType, portletId);

		if (count == 0) {
			return null;
		}

		List<PortletPreferences> list = findByO_P(ownerType, portletId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the portlet preferenceses before and after the current portlet preferences in the ordered set where ownerType = &#63; and portletId = &#63;.
	 *
	 * @param portletPreferencesId the primary key of the current portlet preferences
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next portlet preferences
	 * @throws com.liferay.portal.NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	 */
	@Override
	public PortletPreferences[] findByO_P_PrevAndNext(
		long portletPreferencesId, int ownerType, String portletId,
		OrderByComparator orderByComparator)
		throws NoSuchPortletPreferencesException {
		PortletPreferences portletPreferences = findByPrimaryKey(portletPreferencesId);

		Session session = null;

		try {
			session = openSession();

			PortletPreferences[] array = new PortletPreferencesImpl[3];

			array[0] = getByO_P_PrevAndNext(session, portletPreferences,
					ownerType, portletId, orderByComparator, true);

			array[1] = portletPreferences;

			array[2] = getByO_P_PrevAndNext(session, portletPreferences,
					ownerType, portletId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected PortletPreferences getByO_P_PrevAndNext(Session session,
		PortletPreferences portletPreferences, int ownerType, String portletId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_PORTLETPREFERENCES_WHERE);

		query.append(_FINDER_COLUMN_O_P_OWNERTYPE_2);

		boolean bindPortletId = false;

		if (portletId == null) {
			query.append(_FINDER_COLUMN_O_P_PORTLETID_1);
		}
		else if (portletId.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_O_P_PORTLETID_3);
		}
		else {
			bindPortletId = true;

			query.append(_FINDER_COLUMN_O_P_PORTLETID_2);
		}

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
			query.append(PortletPreferencesModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(ownerType);

		if (bindPortletId) {
			qPos.add(portletId);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(portletPreferences);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<PortletPreferences> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the portlet preferenceses where ownerType = &#63; and portletId = &#63; from the database.
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 */
	@Override
	public void removeByO_P(int ownerType, String portletId) {
		for (PortletPreferences portletPreferences : findByO_P(ownerType,
				portletId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(portletPreferences);
		}
	}

	/**
	 * Returns the number of portlet preferenceses where ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @return the number of matching portlet preferenceses
	 */
	@Override
	public int countByO_P(int ownerType, String portletId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_O_P;

		Object[] finderArgs = new Object[] { ownerType, portletId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_PORTLETPREFERENCES_WHERE);

			query.append(_FINDER_COLUMN_O_P_OWNERTYPE_2);

			boolean bindPortletId = false;

			if (portletId == null) {
				query.append(_FINDER_COLUMN_O_P_PORTLETID_1);
			}
			else if (portletId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_O_P_PORTLETID_3);
			}
			else {
				bindPortletId = true;

				query.append(_FINDER_COLUMN_O_P_PORTLETID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ownerType);

				if (bindPortletId) {
					qPos.add(portletId);
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

	private static final String _FINDER_COLUMN_O_P_OWNERTYPE_2 = "portletPreferences.ownerType = ? AND ";
	private static final String _FINDER_COLUMN_O_P_PORTLETID_1 = "portletPreferences.portletId IS NULL";
	private static final String _FINDER_COLUMN_O_P_PORTLETID_2 = "portletPreferences.portletId = ?";
	private static final String _FINDER_COLUMN_O_P_PORTLETID_3 = "(portletPreferences.portletId IS NULL OR portletPreferences.portletId = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_P_P = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED,
			PortletPreferencesImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByP_P",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P_P = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED,
			PortletPreferencesImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByP_P",
			new String[] { Long.class.getName(), String.class.getName() },
			PortletPreferencesModelImpl.PLID_COLUMN_BITMASK |
			PortletPreferencesModelImpl.PORTLETID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_P_P = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByP_P",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns all the portlet preferenceses where plid = &#63; and portletId = &#63;.
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the matching portlet preferenceses
	 */
	@Override
	public List<PortletPreferences> findByP_P(long plid, String portletId) {
		return findByP_P(plid, portletId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the portlet preferenceses where plid = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletPreferencesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of portlet preferenceses
	 * @param end the upper bound of the range of portlet preferenceses (not inclusive)
	 * @return the range of matching portlet preferenceses
	 */
	@Override
	public List<PortletPreferences> findByP_P(long plid, String portletId,
		int start, int end) {
		return findByP_P(plid, portletId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the portlet preferenceses where plid = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletPreferencesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of portlet preferenceses
	 * @param end the upper bound of the range of portlet preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching portlet preferenceses
	 */
	@Override
	public List<PortletPreferences> findByP_P(long plid, String portletId,
		int start, int end, OrderByComparator orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P_P;
			finderArgs = new Object[] { plid, portletId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_P_P;
			finderArgs = new Object[] {
					plid, portletId,
					
					start, end, orderByComparator
				};
		}

		List<PortletPreferences> list = (List<PortletPreferences>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (PortletPreferences portletPreferences : list) {
				if ((plid != portletPreferences.getPlid()) ||
						!Validator.equals(portletId,
							portletPreferences.getPortletId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_PORTLETPREFERENCES_WHERE);

			query.append(_FINDER_COLUMN_P_P_PLID_2);

			boolean bindPortletId = false;

			if (portletId == null) {
				query.append(_FINDER_COLUMN_P_P_PORTLETID_1);
			}
			else if (portletId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_P_P_PORTLETID_3);
			}
			else {
				bindPortletId = true;

				query.append(_FINDER_COLUMN_P_P_PORTLETID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(PortletPreferencesModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(plid);

				if (bindPortletId) {
					qPos.add(portletId);
				}

				if (!pagination) {
					list = (List<PortletPreferences>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<PortletPreferences>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first portlet preferences in the ordered set where plid = &#63; and portletId = &#63;.
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preferences
	 * @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	 */
	@Override
	public PortletPreferences findByP_P_First(long plid, String portletId,
		OrderByComparator orderByComparator)
		throws NoSuchPortletPreferencesException {
		PortletPreferences portletPreferences = fetchByP_P_First(plid,
				portletId, orderByComparator);

		if (portletPreferences != null) {
			return portletPreferences;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("plid=");
		msg.append(plid);

		msg.append(", portletId=");
		msg.append(portletId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPortletPreferencesException(msg.toString());
	}

	/**
	 * Returns the first portlet preferences in the ordered set where plid = &#63; and portletId = &#63;.
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	 */
	@Override
	public PortletPreferences fetchByP_P_First(long plid, String portletId,
		OrderByComparator orderByComparator) {
		List<PortletPreferences> list = findByP_P(plid, portletId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last portlet preferences in the ordered set where plid = &#63; and portletId = &#63;.
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preferences
	 * @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	 */
	@Override
	public PortletPreferences findByP_P_Last(long plid, String portletId,
		OrderByComparator orderByComparator)
		throws NoSuchPortletPreferencesException {
		PortletPreferences portletPreferences = fetchByP_P_Last(plid,
				portletId, orderByComparator);

		if (portletPreferences != null) {
			return portletPreferences;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("plid=");
		msg.append(plid);

		msg.append(", portletId=");
		msg.append(portletId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPortletPreferencesException(msg.toString());
	}

	/**
	 * Returns the last portlet preferences in the ordered set where plid = &#63; and portletId = &#63;.
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	 */
	@Override
	public PortletPreferences fetchByP_P_Last(long plid, String portletId,
		OrderByComparator orderByComparator) {
		int count = countByP_P(plid, portletId);

		if (count == 0) {
			return null;
		}

		List<PortletPreferences> list = findByP_P(plid, portletId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the portlet preferenceses before and after the current portlet preferences in the ordered set where plid = &#63; and portletId = &#63;.
	 *
	 * @param portletPreferencesId the primary key of the current portlet preferences
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next portlet preferences
	 * @throws com.liferay.portal.NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	 */
	@Override
	public PortletPreferences[] findByP_P_PrevAndNext(
		long portletPreferencesId, long plid, String portletId,
		OrderByComparator orderByComparator)
		throws NoSuchPortletPreferencesException {
		PortletPreferences portletPreferences = findByPrimaryKey(portletPreferencesId);

		Session session = null;

		try {
			session = openSession();

			PortletPreferences[] array = new PortletPreferencesImpl[3];

			array[0] = getByP_P_PrevAndNext(session, portletPreferences, plid,
					portletId, orderByComparator, true);

			array[1] = portletPreferences;

			array[2] = getByP_P_PrevAndNext(session, portletPreferences, plid,
					portletId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected PortletPreferences getByP_P_PrevAndNext(Session session,
		PortletPreferences portletPreferences, long plid, String portletId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_PORTLETPREFERENCES_WHERE);

		query.append(_FINDER_COLUMN_P_P_PLID_2);

		boolean bindPortletId = false;

		if (portletId == null) {
			query.append(_FINDER_COLUMN_P_P_PORTLETID_1);
		}
		else if (portletId.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_P_P_PORTLETID_3);
		}
		else {
			bindPortletId = true;

			query.append(_FINDER_COLUMN_P_P_PORTLETID_2);
		}

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
			query.append(PortletPreferencesModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(plid);

		if (bindPortletId) {
			qPos.add(portletId);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(portletPreferences);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<PortletPreferences> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the portlet preferenceses where plid = &#63; and portletId = &#63; from the database.
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 */
	@Override
	public void removeByP_P(long plid, String portletId) {
		for (PortletPreferences portletPreferences : findByP_P(plid, portletId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(portletPreferences);
		}
	}

	/**
	 * Returns the number of portlet preferenceses where plid = &#63; and portletId = &#63;.
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the number of matching portlet preferenceses
	 */
	@Override
	public int countByP_P(long plid, String portletId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_P_P;

		Object[] finderArgs = new Object[] { plid, portletId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_PORTLETPREFERENCES_WHERE);

			query.append(_FINDER_COLUMN_P_P_PLID_2);

			boolean bindPortletId = false;

			if (portletId == null) {
				query.append(_FINDER_COLUMN_P_P_PORTLETID_1);
			}
			else if (portletId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_P_P_PORTLETID_3);
			}
			else {
				bindPortletId = true;

				query.append(_FINDER_COLUMN_P_P_PORTLETID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(plid);

				if (bindPortletId) {
					qPos.add(portletId);
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

	private static final String _FINDER_COLUMN_P_P_PLID_2 = "portletPreferences.plid = ? AND ";
	private static final String _FINDER_COLUMN_P_P_PORTLETID_1 = "portletPreferences.portletId IS NULL";
	private static final String _FINDER_COLUMN_P_P_PORTLETID_2 = "portletPreferences.portletId = ?";
	private static final String _FINDER_COLUMN_P_P_PORTLETID_3 = "(portletPreferences.portletId IS NULL OR portletPreferences.portletId = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_O_O_P = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED,
			PortletPreferencesImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByO_O_P",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_O_O_P = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED,
			PortletPreferencesImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByO_O_P",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Long.class.getName()
			},
			PortletPreferencesModelImpl.OWNERID_COLUMN_BITMASK |
			PortletPreferencesModelImpl.OWNERTYPE_COLUMN_BITMASK |
			PortletPreferencesModelImpl.PLID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_O_O_P = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByO_O_P",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Long.class.getName()
			});

	/**
	 * Returns all the portlet preferenceses where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @return the matching portlet preferenceses
	 */
	@Override
	public List<PortletPreferences> findByO_O_P(long ownerId, int ownerType,
		long plid) {
		return findByO_O_P(ownerId, ownerType, plid, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the portlet preferenceses where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletPreferencesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param start the lower bound of the range of portlet preferenceses
	 * @param end the upper bound of the range of portlet preferenceses (not inclusive)
	 * @return the range of matching portlet preferenceses
	 */
	@Override
	public List<PortletPreferences> findByO_O_P(long ownerId, int ownerType,
		long plid, int start, int end) {
		return findByO_O_P(ownerId, ownerType, plid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the portlet preferenceses where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletPreferencesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param start the lower bound of the range of portlet preferenceses
	 * @param end the upper bound of the range of portlet preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching portlet preferenceses
	 */
	@Override
	public List<PortletPreferences> findByO_O_P(long ownerId, int ownerType,
		long plid, int start, int end, OrderByComparator orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_O_O_P;
			finderArgs = new Object[] { ownerId, ownerType, plid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_O_O_P;
			finderArgs = new Object[] {
					ownerId, ownerType, plid,
					
					start, end, orderByComparator
				};
		}

		List<PortletPreferences> list = (List<PortletPreferences>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (PortletPreferences portletPreferences : list) {
				if ((ownerId != portletPreferences.getOwnerId()) ||
						(ownerType != portletPreferences.getOwnerType()) ||
						(plid != portletPreferences.getPlid())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_PORTLETPREFERENCES_WHERE);

			query.append(_FINDER_COLUMN_O_O_P_OWNERID_2);

			query.append(_FINDER_COLUMN_O_O_P_OWNERTYPE_2);

			query.append(_FINDER_COLUMN_O_O_P_PLID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(PortletPreferencesModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ownerId);

				qPos.add(ownerType);

				qPos.add(plid);

				if (!pagination) {
					list = (List<PortletPreferences>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<PortletPreferences>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first portlet preferences in the ordered set where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preferences
	 * @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	 */
	@Override
	public PortletPreferences findByO_O_P_First(long ownerId, int ownerType,
		long plid, OrderByComparator orderByComparator)
		throws NoSuchPortletPreferencesException {
		PortletPreferences portletPreferences = fetchByO_O_P_First(ownerId,
				ownerType, plid, orderByComparator);

		if (portletPreferences != null) {
			return portletPreferences;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("ownerId=");
		msg.append(ownerId);

		msg.append(", ownerType=");
		msg.append(ownerType);

		msg.append(", plid=");
		msg.append(plid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPortletPreferencesException(msg.toString());
	}

	/**
	 * Returns the first portlet preferences in the ordered set where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	 */
	@Override
	public PortletPreferences fetchByO_O_P_First(long ownerId, int ownerType,
		long plid, OrderByComparator orderByComparator) {
		List<PortletPreferences> list = findByO_O_P(ownerId, ownerType, plid,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last portlet preferences in the ordered set where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preferences
	 * @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	 */
	@Override
	public PortletPreferences findByO_O_P_Last(long ownerId, int ownerType,
		long plid, OrderByComparator orderByComparator)
		throws NoSuchPortletPreferencesException {
		PortletPreferences portletPreferences = fetchByO_O_P_Last(ownerId,
				ownerType, plid, orderByComparator);

		if (portletPreferences != null) {
			return portletPreferences;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("ownerId=");
		msg.append(ownerId);

		msg.append(", ownerType=");
		msg.append(ownerType);

		msg.append(", plid=");
		msg.append(plid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPortletPreferencesException(msg.toString());
	}

	/**
	 * Returns the last portlet preferences in the ordered set where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	 */
	@Override
	public PortletPreferences fetchByO_O_P_Last(long ownerId, int ownerType,
		long plid, OrderByComparator orderByComparator) {
		int count = countByO_O_P(ownerId, ownerType, plid);

		if (count == 0) {
			return null;
		}

		List<PortletPreferences> list = findByO_O_P(ownerId, ownerType, plid,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the portlet preferenceses before and after the current portlet preferences in the ordered set where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	 *
	 * @param portletPreferencesId the primary key of the current portlet preferences
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next portlet preferences
	 * @throws com.liferay.portal.NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	 */
	@Override
	public PortletPreferences[] findByO_O_P_PrevAndNext(
		long portletPreferencesId, long ownerId, int ownerType, long plid,
		OrderByComparator orderByComparator)
		throws NoSuchPortletPreferencesException {
		PortletPreferences portletPreferences = findByPrimaryKey(portletPreferencesId);

		Session session = null;

		try {
			session = openSession();

			PortletPreferences[] array = new PortletPreferencesImpl[3];

			array[0] = getByO_O_P_PrevAndNext(session, portletPreferences,
					ownerId, ownerType, plid, orderByComparator, true);

			array[1] = portletPreferences;

			array[2] = getByO_O_P_PrevAndNext(session, portletPreferences,
					ownerId, ownerType, plid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected PortletPreferences getByO_O_P_PrevAndNext(Session session,
		PortletPreferences portletPreferences, long ownerId, int ownerType,
		long plid, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_PORTLETPREFERENCES_WHERE);

		query.append(_FINDER_COLUMN_O_O_P_OWNERID_2);

		query.append(_FINDER_COLUMN_O_O_P_OWNERTYPE_2);

		query.append(_FINDER_COLUMN_O_O_P_PLID_2);

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
			query.append(PortletPreferencesModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(ownerId);

		qPos.add(ownerType);

		qPos.add(plid);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(portletPreferences);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<PortletPreferences> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the portlet preferenceses where ownerId = &#63; and ownerType = &#63; and plid = &#63; from the database.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 */
	@Override
	public void removeByO_O_P(long ownerId, int ownerType, long plid) {
		for (PortletPreferences portletPreferences : findByO_O_P(ownerId,
				ownerType, plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(portletPreferences);
		}
	}

	/**
	 * Returns the number of portlet preferenceses where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @return the number of matching portlet preferenceses
	 */
	@Override
	public int countByO_O_P(long ownerId, int ownerType, long plid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_O_O_P;

		Object[] finderArgs = new Object[] { ownerId, ownerType, plid };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_PORTLETPREFERENCES_WHERE);

			query.append(_FINDER_COLUMN_O_O_P_OWNERID_2);

			query.append(_FINDER_COLUMN_O_O_P_OWNERTYPE_2);

			query.append(_FINDER_COLUMN_O_O_P_PLID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ownerId);

				qPos.add(ownerType);

				qPos.add(plid);

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

	private static final String _FINDER_COLUMN_O_O_P_OWNERID_2 = "portletPreferences.ownerId = ? AND ";
	private static final String _FINDER_COLUMN_O_O_P_OWNERTYPE_2 = "portletPreferences.ownerType = ? AND ";
	private static final String _FINDER_COLUMN_O_O_P_PLID_2 = "portletPreferences.plid = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_O_O_PI = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED,
			PortletPreferencesImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByO_O_PI",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_O_O_PI =
		new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED,
			PortletPreferencesImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByO_O_PI",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				String.class.getName()
			},
			PortletPreferencesModelImpl.OWNERID_COLUMN_BITMASK |
			PortletPreferencesModelImpl.OWNERTYPE_COLUMN_BITMASK |
			PortletPreferencesModelImpl.PORTLETID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_O_O_PI = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByO_O_PI",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns all the portlet preferenceses where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @return the matching portlet preferenceses
	 */
	@Override
	public List<PortletPreferences> findByO_O_PI(long ownerId, int ownerType,
		String portletId) {
		return findByO_O_PI(ownerId, ownerType, portletId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the portlet preferenceses where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletPreferencesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of portlet preferenceses
	 * @param end the upper bound of the range of portlet preferenceses (not inclusive)
	 * @return the range of matching portlet preferenceses
	 */
	@Override
	public List<PortletPreferences> findByO_O_PI(long ownerId, int ownerType,
		String portletId, int start, int end) {
		return findByO_O_PI(ownerId, ownerType, portletId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the portlet preferenceses where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletPreferencesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of portlet preferenceses
	 * @param end the upper bound of the range of portlet preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching portlet preferenceses
	 */
	@Override
	public List<PortletPreferences> findByO_O_PI(long ownerId, int ownerType,
		String portletId, int start, int end,
		OrderByComparator orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_O_O_PI;
			finderArgs = new Object[] { ownerId, ownerType, portletId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_O_O_PI;
			finderArgs = new Object[] {
					ownerId, ownerType, portletId,
					
					start, end, orderByComparator
				};
		}

		List<PortletPreferences> list = (List<PortletPreferences>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (PortletPreferences portletPreferences : list) {
				if ((ownerId != portletPreferences.getOwnerId()) ||
						(ownerType != portletPreferences.getOwnerType()) ||
						!Validator.equals(portletId,
							portletPreferences.getPortletId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_PORTLETPREFERENCES_WHERE);

			query.append(_FINDER_COLUMN_O_O_PI_OWNERID_2);

			query.append(_FINDER_COLUMN_O_O_PI_OWNERTYPE_2);

			boolean bindPortletId = false;

			if (portletId == null) {
				query.append(_FINDER_COLUMN_O_O_PI_PORTLETID_1);
			}
			else if (portletId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_O_O_PI_PORTLETID_3);
			}
			else {
				bindPortletId = true;

				query.append(_FINDER_COLUMN_O_O_PI_PORTLETID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(PortletPreferencesModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ownerId);

				qPos.add(ownerType);

				if (bindPortletId) {
					qPos.add(portletId);
				}

				if (!pagination) {
					list = (List<PortletPreferences>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<PortletPreferences>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first portlet preferences in the ordered set where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preferences
	 * @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	 */
	@Override
	public PortletPreferences findByO_O_PI_First(long ownerId, int ownerType,
		String portletId, OrderByComparator orderByComparator)
		throws NoSuchPortletPreferencesException {
		PortletPreferences portletPreferences = fetchByO_O_PI_First(ownerId,
				ownerType, portletId, orderByComparator);

		if (portletPreferences != null) {
			return portletPreferences;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("ownerId=");
		msg.append(ownerId);

		msg.append(", ownerType=");
		msg.append(ownerType);

		msg.append(", portletId=");
		msg.append(portletId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPortletPreferencesException(msg.toString());
	}

	/**
	 * Returns the first portlet preferences in the ordered set where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	 */
	@Override
	public PortletPreferences fetchByO_O_PI_First(long ownerId, int ownerType,
		String portletId, OrderByComparator orderByComparator) {
		List<PortletPreferences> list = findByO_O_PI(ownerId, ownerType,
				portletId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last portlet preferences in the ordered set where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preferences
	 * @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	 */
	@Override
	public PortletPreferences findByO_O_PI_Last(long ownerId, int ownerType,
		String portletId, OrderByComparator orderByComparator)
		throws NoSuchPortletPreferencesException {
		PortletPreferences portletPreferences = fetchByO_O_PI_Last(ownerId,
				ownerType, portletId, orderByComparator);

		if (portletPreferences != null) {
			return portletPreferences;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("ownerId=");
		msg.append(ownerId);

		msg.append(", ownerType=");
		msg.append(ownerType);

		msg.append(", portletId=");
		msg.append(portletId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPortletPreferencesException(msg.toString());
	}

	/**
	 * Returns the last portlet preferences in the ordered set where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	 */
	@Override
	public PortletPreferences fetchByO_O_PI_Last(long ownerId, int ownerType,
		String portletId, OrderByComparator orderByComparator) {
		int count = countByO_O_PI(ownerId, ownerType, portletId);

		if (count == 0) {
			return null;
		}

		List<PortletPreferences> list = findByO_O_PI(ownerId, ownerType,
				portletId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the portlet preferenceses before and after the current portlet preferences in the ordered set where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * @param portletPreferencesId the primary key of the current portlet preferences
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next portlet preferences
	 * @throws com.liferay.portal.NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	 */
	@Override
	public PortletPreferences[] findByO_O_PI_PrevAndNext(
		long portletPreferencesId, long ownerId, int ownerType,
		String portletId, OrderByComparator orderByComparator)
		throws NoSuchPortletPreferencesException {
		PortletPreferences portletPreferences = findByPrimaryKey(portletPreferencesId);

		Session session = null;

		try {
			session = openSession();

			PortletPreferences[] array = new PortletPreferencesImpl[3];

			array[0] = getByO_O_PI_PrevAndNext(session, portletPreferences,
					ownerId, ownerType, portletId, orderByComparator, true);

			array[1] = portletPreferences;

			array[2] = getByO_O_PI_PrevAndNext(session, portletPreferences,
					ownerId, ownerType, portletId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected PortletPreferences getByO_O_PI_PrevAndNext(Session session,
		PortletPreferences portletPreferences, long ownerId, int ownerType,
		String portletId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_PORTLETPREFERENCES_WHERE);

		query.append(_FINDER_COLUMN_O_O_PI_OWNERID_2);

		query.append(_FINDER_COLUMN_O_O_PI_OWNERTYPE_2);

		boolean bindPortletId = false;

		if (portletId == null) {
			query.append(_FINDER_COLUMN_O_O_PI_PORTLETID_1);
		}
		else if (portletId.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_O_O_PI_PORTLETID_3);
		}
		else {
			bindPortletId = true;

			query.append(_FINDER_COLUMN_O_O_PI_PORTLETID_2);
		}

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
			query.append(PortletPreferencesModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(ownerId);

		qPos.add(ownerType);

		if (bindPortletId) {
			qPos.add(portletId);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(portletPreferences);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<PortletPreferences> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the portlet preferenceses where ownerId = &#63; and ownerType = &#63; and portletId = &#63; from the database.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 */
	@Override
	public void removeByO_O_PI(long ownerId, int ownerType, String portletId) {
		for (PortletPreferences portletPreferences : findByO_O_PI(ownerId,
				ownerType, portletId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(portletPreferences);
		}
	}

	/**
	 * Returns the number of portlet preferenceses where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @return the number of matching portlet preferenceses
	 */
	@Override
	public int countByO_O_PI(long ownerId, int ownerType, String portletId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_O_O_PI;

		Object[] finderArgs = new Object[] { ownerId, ownerType, portletId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_PORTLETPREFERENCES_WHERE);

			query.append(_FINDER_COLUMN_O_O_PI_OWNERID_2);

			query.append(_FINDER_COLUMN_O_O_PI_OWNERTYPE_2);

			boolean bindPortletId = false;

			if (portletId == null) {
				query.append(_FINDER_COLUMN_O_O_PI_PORTLETID_1);
			}
			else if (portletId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_O_O_PI_PORTLETID_3);
			}
			else {
				bindPortletId = true;

				query.append(_FINDER_COLUMN_O_O_PI_PORTLETID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ownerId);

				qPos.add(ownerType);

				if (bindPortletId) {
					qPos.add(portletId);
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

	private static final String _FINDER_COLUMN_O_O_PI_OWNERID_2 = "portletPreferences.ownerId = ? AND ";
	private static final String _FINDER_COLUMN_O_O_PI_OWNERTYPE_2 = "portletPreferences.ownerType = ? AND ";
	private static final String _FINDER_COLUMN_O_O_PI_PORTLETID_1 = "portletPreferences.portletId IS NULL";
	private static final String _FINDER_COLUMN_O_O_PI_PORTLETID_2 = "portletPreferences.portletId = ?";
	private static final String _FINDER_COLUMN_O_O_PI_PORTLETID_3 = "(portletPreferences.portletId IS NULL OR portletPreferences.portletId = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_O_P_P = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED,
			PortletPreferencesImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByO_P_P",
			new String[] {
				Integer.class.getName(), Long.class.getName(),
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_O_P_P = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED,
			PortletPreferencesImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByO_P_P",
			new String[] {
				Integer.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			PortletPreferencesModelImpl.OWNERTYPE_COLUMN_BITMASK |
			PortletPreferencesModelImpl.PLID_COLUMN_BITMASK |
			PortletPreferencesModelImpl.PORTLETID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_O_P_P = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByO_P_P",
			new String[] {
				Integer.class.getName(), Long.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns all the portlet preferenceses where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the matching portlet preferenceses
	 */
	@Override
	public List<PortletPreferences> findByO_P_P(int ownerType, long plid,
		String portletId) {
		return findByO_P_P(ownerType, plid, portletId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the portlet preferenceses where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletPreferencesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of portlet preferenceses
	 * @param end the upper bound of the range of portlet preferenceses (not inclusive)
	 * @return the range of matching portlet preferenceses
	 */
	@Override
	public List<PortletPreferences> findByO_P_P(int ownerType, long plid,
		String portletId, int start, int end) {
		return findByO_P_P(ownerType, plid, portletId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the portlet preferenceses where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletPreferencesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of portlet preferenceses
	 * @param end the upper bound of the range of portlet preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching portlet preferenceses
	 */
	@Override
	public List<PortletPreferences> findByO_P_P(int ownerType, long plid,
		String portletId, int start, int end,
		OrderByComparator orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_O_P_P;
			finderArgs = new Object[] { ownerType, plid, portletId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_O_P_P;
			finderArgs = new Object[] {
					ownerType, plid, portletId,
					
					start, end, orderByComparator
				};
		}

		List<PortletPreferences> list = (List<PortletPreferences>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (PortletPreferences portletPreferences : list) {
				if ((ownerType != portletPreferences.getOwnerType()) ||
						(plid != portletPreferences.getPlid()) ||
						!Validator.equals(portletId,
							portletPreferences.getPortletId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_PORTLETPREFERENCES_WHERE);

			query.append(_FINDER_COLUMN_O_P_P_OWNERTYPE_2);

			query.append(_FINDER_COLUMN_O_P_P_PLID_2);

			boolean bindPortletId = false;

			if (portletId == null) {
				query.append(_FINDER_COLUMN_O_P_P_PORTLETID_1);
			}
			else if (portletId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_O_P_P_PORTLETID_3);
			}
			else {
				bindPortletId = true;

				query.append(_FINDER_COLUMN_O_P_P_PORTLETID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(PortletPreferencesModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ownerType);

				qPos.add(plid);

				if (bindPortletId) {
					qPos.add(portletId);
				}

				if (!pagination) {
					list = (List<PortletPreferences>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<PortletPreferences>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first portlet preferences in the ordered set where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preferences
	 * @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	 */
	@Override
	public PortletPreferences findByO_P_P_First(int ownerType, long plid,
		String portletId, OrderByComparator orderByComparator)
		throws NoSuchPortletPreferencesException {
		PortletPreferences portletPreferences = fetchByO_P_P_First(ownerType,
				plid, portletId, orderByComparator);

		if (portletPreferences != null) {
			return portletPreferences;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("ownerType=");
		msg.append(ownerType);

		msg.append(", plid=");
		msg.append(plid);

		msg.append(", portletId=");
		msg.append(portletId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPortletPreferencesException(msg.toString());
	}

	/**
	 * Returns the first portlet preferences in the ordered set where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	 */
	@Override
	public PortletPreferences fetchByO_P_P_First(int ownerType, long plid,
		String portletId, OrderByComparator orderByComparator) {
		List<PortletPreferences> list = findByO_P_P(ownerType, plid, portletId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last portlet preferences in the ordered set where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preferences
	 * @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	 */
	@Override
	public PortletPreferences findByO_P_P_Last(int ownerType, long plid,
		String portletId, OrderByComparator orderByComparator)
		throws NoSuchPortletPreferencesException {
		PortletPreferences portletPreferences = fetchByO_P_P_Last(ownerType,
				plid, portletId, orderByComparator);

		if (portletPreferences != null) {
			return portletPreferences;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("ownerType=");
		msg.append(ownerType);

		msg.append(", plid=");
		msg.append(plid);

		msg.append(", portletId=");
		msg.append(portletId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPortletPreferencesException(msg.toString());
	}

	/**
	 * Returns the last portlet preferences in the ordered set where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	 */
	@Override
	public PortletPreferences fetchByO_P_P_Last(int ownerType, long plid,
		String portletId, OrderByComparator orderByComparator) {
		int count = countByO_P_P(ownerType, plid, portletId);

		if (count == 0) {
			return null;
		}

		List<PortletPreferences> list = findByO_P_P(ownerType, plid, portletId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the portlet preferenceses before and after the current portlet preferences in the ordered set where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param portletPreferencesId the primary key of the current portlet preferences
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next portlet preferences
	 * @throws com.liferay.portal.NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	 */
	@Override
	public PortletPreferences[] findByO_P_P_PrevAndNext(
		long portletPreferencesId, int ownerType, long plid, String portletId,
		OrderByComparator orderByComparator)
		throws NoSuchPortletPreferencesException {
		PortletPreferences portletPreferences = findByPrimaryKey(portletPreferencesId);

		Session session = null;

		try {
			session = openSession();

			PortletPreferences[] array = new PortletPreferencesImpl[3];

			array[0] = getByO_P_P_PrevAndNext(session, portletPreferences,
					ownerType, plid, portletId, orderByComparator, true);

			array[1] = portletPreferences;

			array[2] = getByO_P_P_PrevAndNext(session, portletPreferences,
					ownerType, plid, portletId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected PortletPreferences getByO_P_P_PrevAndNext(Session session,
		PortletPreferences portletPreferences, int ownerType, long plid,
		String portletId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_PORTLETPREFERENCES_WHERE);

		query.append(_FINDER_COLUMN_O_P_P_OWNERTYPE_2);

		query.append(_FINDER_COLUMN_O_P_P_PLID_2);

		boolean bindPortletId = false;

		if (portletId == null) {
			query.append(_FINDER_COLUMN_O_P_P_PORTLETID_1);
		}
		else if (portletId.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_O_P_P_PORTLETID_3);
		}
		else {
			bindPortletId = true;

			query.append(_FINDER_COLUMN_O_P_P_PORTLETID_2);
		}

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
			query.append(PortletPreferencesModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(ownerType);

		qPos.add(plid);

		if (bindPortletId) {
			qPos.add(portletId);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(portletPreferences);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<PortletPreferences> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the portlet preferenceses where ownerType = &#63; and plid = &#63; and portletId = &#63; from the database.
	 *
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 */
	@Override
	public void removeByO_P_P(int ownerType, long plid, String portletId) {
		for (PortletPreferences portletPreferences : findByO_P_P(ownerType,
				plid, portletId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(portletPreferences);
		}
	}

	/**
	 * Returns the number of portlet preferenceses where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the number of matching portlet preferenceses
	 */
	@Override
	public int countByO_P_P(int ownerType, long plid, String portletId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_O_P_P;

		Object[] finderArgs = new Object[] { ownerType, plid, portletId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_PORTLETPREFERENCES_WHERE);

			query.append(_FINDER_COLUMN_O_P_P_OWNERTYPE_2);

			query.append(_FINDER_COLUMN_O_P_P_PLID_2);

			boolean bindPortletId = false;

			if (portletId == null) {
				query.append(_FINDER_COLUMN_O_P_P_PORTLETID_1);
			}
			else if (portletId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_O_P_P_PORTLETID_3);
			}
			else {
				bindPortletId = true;

				query.append(_FINDER_COLUMN_O_P_P_PORTLETID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ownerType);

				qPos.add(plid);

				if (bindPortletId) {
					qPos.add(portletId);
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

	private static final String _FINDER_COLUMN_O_P_P_OWNERTYPE_2 = "portletPreferences.ownerType = ? AND ";
	private static final String _FINDER_COLUMN_O_P_P_PLID_2 = "portletPreferences.plid = ? AND ";
	private static final String _FINDER_COLUMN_O_P_P_PORTLETID_1 = "portletPreferences.portletId IS NULL";
	private static final String _FINDER_COLUMN_O_P_P_PORTLETID_2 = "portletPreferences.portletId = ?";
	private static final String _FINDER_COLUMN_O_P_P_PORTLETID_3 = "(portletPreferences.portletId IS NULL OR portletPreferences.portletId = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_O_O_P_P = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED,
			PortletPreferencesImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByO_O_P_P",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Long.class.getName(), String.class.getName()
			},
			PortletPreferencesModelImpl.OWNERID_COLUMN_BITMASK |
			PortletPreferencesModelImpl.OWNERTYPE_COLUMN_BITMASK |
			PortletPreferencesModelImpl.PLID_COLUMN_BITMASK |
			PortletPreferencesModelImpl.PORTLETID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_O_O_P_P = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByO_O_P_P",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Long.class.getName(), String.class.getName()
			});

	/**
	 * Returns the portlet preferences where ownerId = &#63; and ownerType = &#63; and plid = &#63; and portletId = &#63; or throws a {@link com.liferay.portal.NoSuchPortletPreferencesException} if it could not be found.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the matching portlet preferences
	 * @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	 */
	@Override
	public PortletPreferences findByO_O_P_P(long ownerId, int ownerType,
		long plid, String portletId) throws NoSuchPortletPreferencesException {
		PortletPreferences portletPreferences = fetchByO_O_P_P(ownerId,
				ownerType, plid, portletId);

		if (portletPreferences == null) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("ownerId=");
			msg.append(ownerId);

			msg.append(", ownerType=");
			msg.append(ownerType);

			msg.append(", plid=");
			msg.append(plid);

			msg.append(", portletId=");
			msg.append(portletId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchPortletPreferencesException(msg.toString());
		}

		return portletPreferences;
	}

	/**
	 * Returns the portlet preferences where ownerId = &#63; and ownerType = &#63; and plid = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	 */
	@Override
	public PortletPreferences fetchByO_O_P_P(long ownerId, int ownerType,
		long plid, String portletId) {
		return fetchByO_O_P_P(ownerId, ownerType, plid, portletId, true);
	}

	/**
	 * Returns the portlet preferences where ownerId = &#63; and ownerType = &#63; and plid = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	 */
	@Override
	public PortletPreferences fetchByO_O_P_P(long ownerId, int ownerType,
		long plid, String portletId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { ownerId, ownerType, plid, portletId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_O_O_P_P,
					finderArgs, this);
		}

		if (result instanceof PortletPreferences) {
			PortletPreferences portletPreferences = (PortletPreferences)result;

			if ((ownerId != portletPreferences.getOwnerId()) ||
					(ownerType != portletPreferences.getOwnerType()) ||
					(plid != portletPreferences.getPlid()) ||
					!Validator.equals(portletId,
						portletPreferences.getPortletId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_SELECT_PORTLETPREFERENCES_WHERE);

			query.append(_FINDER_COLUMN_O_O_P_P_OWNERID_2);

			query.append(_FINDER_COLUMN_O_O_P_P_OWNERTYPE_2);

			query.append(_FINDER_COLUMN_O_O_P_P_PLID_2);

			boolean bindPortletId = false;

			if (portletId == null) {
				query.append(_FINDER_COLUMN_O_O_P_P_PORTLETID_1);
			}
			else if (portletId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_O_O_P_P_PORTLETID_3);
			}
			else {
				bindPortletId = true;

				query.append(_FINDER_COLUMN_O_O_P_P_PORTLETID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ownerId);

				qPos.add(ownerType);

				qPos.add(plid);

				if (bindPortletId) {
					qPos.add(portletId);
				}

				List<PortletPreferences> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_O_O_P_P,
						finderArgs, list);
				}
				else {
					PortletPreferences portletPreferences = list.get(0);

					result = portletPreferences;

					cacheResult(portletPreferences);

					if ((portletPreferences.getOwnerId() != ownerId) ||
							(portletPreferences.getOwnerType() != ownerType) ||
							(portletPreferences.getPlid() != plid) ||
							(portletPreferences.getPortletId() == null) ||
							!portletPreferences.getPortletId().equals(portletId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_O_O_P_P,
							finderArgs, portletPreferences);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_O_O_P_P,
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
			return (PortletPreferences)result;
		}
	}

	/**
	 * Removes the portlet preferences where ownerId = &#63; and ownerType = &#63; and plid = &#63; and portletId = &#63; from the database.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the portlet preferences that was removed
	 */
	@Override
	public PortletPreferences removeByO_O_P_P(long ownerId, int ownerType,
		long plid, String portletId) throws NoSuchPortletPreferencesException {
		PortletPreferences portletPreferences = findByO_O_P_P(ownerId,
				ownerType, plid, portletId);

		return remove(portletPreferences);
	}

	/**
	 * Returns the number of portlet preferenceses where ownerId = &#63; and ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the number of matching portlet preferenceses
	 */
	@Override
	public int countByO_O_P_P(long ownerId, int ownerType, long plid,
		String portletId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_O_O_P_P;

		Object[] finderArgs = new Object[] { ownerId, ownerType, plid, portletId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_PORTLETPREFERENCES_WHERE);

			query.append(_FINDER_COLUMN_O_O_P_P_OWNERID_2);

			query.append(_FINDER_COLUMN_O_O_P_P_OWNERTYPE_2);

			query.append(_FINDER_COLUMN_O_O_P_P_PLID_2);

			boolean bindPortletId = false;

			if (portletId == null) {
				query.append(_FINDER_COLUMN_O_O_P_P_PORTLETID_1);
			}
			else if (portletId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_O_O_P_P_PORTLETID_3);
			}
			else {
				bindPortletId = true;

				query.append(_FINDER_COLUMN_O_O_P_P_PORTLETID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ownerId);

				qPos.add(ownerType);

				qPos.add(plid);

				if (bindPortletId) {
					qPos.add(portletId);
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

	private static final String _FINDER_COLUMN_O_O_P_P_OWNERID_2 = "portletPreferences.ownerId = ? AND ";
	private static final String _FINDER_COLUMN_O_O_P_P_OWNERTYPE_2 = "portletPreferences.ownerType = ? AND ";
	private static final String _FINDER_COLUMN_O_O_P_P_PLID_2 = "portletPreferences.plid = ? AND ";
	private static final String _FINDER_COLUMN_O_O_P_P_PORTLETID_1 = "portletPreferences.portletId IS NULL";
	private static final String _FINDER_COLUMN_O_O_P_P_PORTLETID_2 = "portletPreferences.portletId = ?";
	private static final String _FINDER_COLUMN_O_O_P_P_PORTLETID_3 = "(portletPreferences.portletId IS NULL OR portletPreferences.portletId = '')";

	public PortletPreferencesPersistenceImpl() {
		setModelClass(PortletPreferences.class);
	}

	/**
	 * Caches the portlet preferences in the entity cache if it is enabled.
	 *
	 * @param portletPreferences the portlet preferences
	 */
	@Override
	public void cacheResult(PortletPreferences portletPreferences) {
		EntityCacheUtil.putResult(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesImpl.class, portletPreferences.getPrimaryKey(),
			portletPreferences);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_O_O_P_P,
			new Object[] {
				portletPreferences.getOwnerId(),
				portletPreferences.getOwnerType(), portletPreferences.getPlid(),
				portletPreferences.getPortletId()
			}, portletPreferences);

		portletPreferences.resetOriginalValues();
	}

	/**
	 * Caches the portlet preferenceses in the entity cache if it is enabled.
	 *
	 * @param portletPreferenceses the portlet preferenceses
	 */
	@Override
	public void cacheResult(List<PortletPreferences> portletPreferenceses) {
		for (PortletPreferences portletPreferences : portletPreferenceses) {
			if (EntityCacheUtil.getResult(
						PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
						PortletPreferencesImpl.class,
						portletPreferences.getPrimaryKey()) == null) {
				cacheResult(portletPreferences);
			}
			else {
				portletPreferences.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all portlet preferenceses.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(PortletPreferencesImpl.class.getName());
		}

		EntityCacheUtil.clearCache(PortletPreferencesImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the portlet preferences.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(PortletPreferences portletPreferences) {
		EntityCacheUtil.removeResult(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesImpl.class, portletPreferences.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(portletPreferences);
	}

	@Override
	public void clearCache(List<PortletPreferences> portletPreferenceses) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (PortletPreferences portletPreferences : portletPreferenceses) {
			EntityCacheUtil.removeResult(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
				PortletPreferencesImpl.class, portletPreferences.getPrimaryKey());

			clearUniqueFindersCache(portletPreferences);
		}
	}

	protected void cacheUniqueFindersCache(
		PortletPreferences portletPreferences) {
		if (portletPreferences.isNew()) {
			Object[] args = new Object[] {
					portletPreferences.getOwnerId(),
					portletPreferences.getOwnerType(),
					portletPreferences.getPlid(),
					portletPreferences.getPortletId()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_O_O_P_P, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_O_O_P_P, args,
				portletPreferences);
		}
		else {
			PortletPreferencesModelImpl portletPreferencesModelImpl = (PortletPreferencesModelImpl)portletPreferences;

			if ((portletPreferencesModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_O_O_P_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						portletPreferences.getOwnerId(),
						portletPreferences.getOwnerType(),
						portletPreferences.getPlid(),
						portletPreferences.getPortletId()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_O_O_P_P, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_O_O_P_P, args,
					portletPreferences);
			}
		}
	}

	protected void clearUniqueFindersCache(
		PortletPreferences portletPreferences) {
		PortletPreferencesModelImpl portletPreferencesModelImpl = (PortletPreferencesModelImpl)portletPreferences;

		Object[] args = new Object[] {
				portletPreferences.getOwnerId(),
				portletPreferences.getOwnerType(), portletPreferences.getPlid(),
				portletPreferences.getPortletId()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_O_O_P_P, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_O_O_P_P, args);

		if ((portletPreferencesModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_O_O_P_P.getColumnBitmask()) != 0) {
			args = new Object[] {
					portletPreferencesModelImpl.getOriginalOwnerId(),
					portletPreferencesModelImpl.getOriginalOwnerType(),
					portletPreferencesModelImpl.getOriginalPlid(),
					portletPreferencesModelImpl.getOriginalPortletId()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_O_O_P_P, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_O_O_P_P, args);
		}
	}

	/**
	 * Creates a new portlet preferences with the primary key. Does not add the portlet preferences to the database.
	 *
	 * @param portletPreferencesId the primary key for the new portlet preferences
	 * @return the new portlet preferences
	 */
	@Override
	public PortletPreferences create(long portletPreferencesId) {
		PortletPreferences portletPreferences = new PortletPreferencesImpl();

		portletPreferences.setNew(true);
		portletPreferences.setPrimaryKey(portletPreferencesId);

		return portletPreferences;
	}

	/**
	 * Removes the portlet preferences with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param portletPreferencesId the primary key of the portlet preferences
	 * @return the portlet preferences that was removed
	 * @throws com.liferay.portal.NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	 */
	@Override
	public PortletPreferences remove(long portletPreferencesId)
		throws NoSuchPortletPreferencesException {
		return remove((Serializable)portletPreferencesId);
	}

	/**
	 * Removes the portlet preferences with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the portlet preferences
	 * @return the portlet preferences that was removed
	 * @throws com.liferay.portal.NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	 */
	@Override
	public PortletPreferences remove(Serializable primaryKey)
		throws NoSuchPortletPreferencesException {
		Session session = null;

		try {
			session = openSession();

			PortletPreferences portletPreferences = (PortletPreferences)session.get(PortletPreferencesImpl.class,
					primaryKey);

			if (portletPreferences == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPortletPreferencesException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(portletPreferences);
		}
		catch (NoSuchPortletPreferencesException nsee) {
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
	protected PortletPreferences removeImpl(
		PortletPreferences portletPreferences) {
		portletPreferences = toUnwrappedModel(portletPreferences);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(portletPreferences)) {
				portletPreferences = (PortletPreferences)session.get(PortletPreferencesImpl.class,
						portletPreferences.getPrimaryKeyObj());
			}

			if (portletPreferences != null) {
				session.delete(portletPreferences);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (portletPreferences != null) {
			clearCache(portletPreferences);
		}

		return portletPreferences;
	}

	@Override
	public PortletPreferences updateImpl(
		com.liferay.portal.model.PortletPreferences portletPreferences) {
		portletPreferences = toUnwrappedModel(portletPreferences);

		boolean isNew = portletPreferences.isNew();

		PortletPreferencesModelImpl portletPreferencesModelImpl = (PortletPreferencesModelImpl)portletPreferences;

		Session session = null;

		try {
			session = openSession();

			if (portletPreferences.isNew()) {
				session.save(portletPreferences);

				portletPreferences.setNew(false);
			}
			else {
				session.merge(portletPreferences);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !PortletPreferencesModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((portletPreferencesModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PLID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						portletPreferencesModelImpl.getOriginalPlid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PLID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PLID,
					args);

				args = new Object[] { portletPreferencesModelImpl.getPlid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PLID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PLID,
					args);
			}

			if ((portletPreferencesModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PORTLETID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						portletPreferencesModelImpl.getOriginalPortletId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PORTLETID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PORTLETID,
					args);

				args = new Object[] { portletPreferencesModelImpl.getPortletId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PORTLETID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PORTLETID,
					args);
			}

			if ((portletPreferencesModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_O_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						portletPreferencesModelImpl.getOriginalOwnerType(),
						portletPreferencesModelImpl.getOriginalPortletId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_O_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_O_P,
					args);

				args = new Object[] {
						portletPreferencesModelImpl.getOwnerType(),
						portletPreferencesModelImpl.getPortletId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_O_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_O_P,
					args);
			}

			if ((portletPreferencesModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						portletPreferencesModelImpl.getOriginalPlid(),
						portletPreferencesModelImpl.getOriginalPortletId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_P_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P_P,
					args);

				args = new Object[] {
						portletPreferencesModelImpl.getPlid(),
						portletPreferencesModelImpl.getPortletId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_P_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P_P,
					args);
			}

			if ((portletPreferencesModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_O_O_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						portletPreferencesModelImpl.getOriginalOwnerId(),
						portletPreferencesModelImpl.getOriginalOwnerType(),
						portletPreferencesModelImpl.getOriginalPlid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_O_O_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_O_O_P,
					args);

				args = new Object[] {
						portletPreferencesModelImpl.getOwnerId(),
						portletPreferencesModelImpl.getOwnerType(),
						portletPreferencesModelImpl.getPlid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_O_O_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_O_O_P,
					args);
			}

			if ((portletPreferencesModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_O_O_PI.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						portletPreferencesModelImpl.getOriginalOwnerId(),
						portletPreferencesModelImpl.getOriginalOwnerType(),
						portletPreferencesModelImpl.getOriginalPortletId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_O_O_PI, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_O_O_PI,
					args);

				args = new Object[] {
						portletPreferencesModelImpl.getOwnerId(),
						portletPreferencesModelImpl.getOwnerType(),
						portletPreferencesModelImpl.getPortletId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_O_O_PI, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_O_O_PI,
					args);
			}

			if ((portletPreferencesModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_O_P_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						portletPreferencesModelImpl.getOriginalOwnerType(),
						portletPreferencesModelImpl.getOriginalPlid(),
						portletPreferencesModelImpl.getOriginalPortletId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_O_P_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_O_P_P,
					args);

				args = new Object[] {
						portletPreferencesModelImpl.getOwnerType(),
						portletPreferencesModelImpl.getPlid(),
						portletPreferencesModelImpl.getPortletId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_O_P_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_O_P_P,
					args);
			}
		}

		EntityCacheUtil.putResult(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesImpl.class, portletPreferences.getPrimaryKey(),
			portletPreferences, false);

		clearUniqueFindersCache(portletPreferences);
		cacheUniqueFindersCache(portletPreferences);

		portletPreferences.resetOriginalValues();

		return portletPreferences;
	}

	protected PortletPreferences toUnwrappedModel(
		PortletPreferences portletPreferences) {
		if (portletPreferences instanceof PortletPreferencesImpl) {
			return portletPreferences;
		}

		PortletPreferencesImpl portletPreferencesImpl = new PortletPreferencesImpl();

		portletPreferencesImpl.setNew(portletPreferences.isNew());
		portletPreferencesImpl.setPrimaryKey(portletPreferences.getPrimaryKey());

		portletPreferencesImpl.setMvccVersion(portletPreferences.getMvccVersion());
		portletPreferencesImpl.setPortletPreferencesId(portletPreferences.getPortletPreferencesId());
		portletPreferencesImpl.setOwnerId(portletPreferences.getOwnerId());
		portletPreferencesImpl.setOwnerType(portletPreferences.getOwnerType());
		portletPreferencesImpl.setPlid(portletPreferences.getPlid());
		portletPreferencesImpl.setPortletId(portletPreferences.getPortletId());
		portletPreferencesImpl.setPreferences(portletPreferences.getPreferences());

		return portletPreferencesImpl;
	}

	/**
	 * Returns the portlet preferences with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the portlet preferences
	 * @return the portlet preferences
	 * @throws com.liferay.portal.NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	 */
	@Override
	public PortletPreferences findByPrimaryKey(Serializable primaryKey)
		throws NoSuchPortletPreferencesException {
		PortletPreferences portletPreferences = fetchByPrimaryKey(primaryKey);

		if (portletPreferences == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPortletPreferencesException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return portletPreferences;
	}

	/**
	 * Returns the portlet preferences with the primary key or throws a {@link com.liferay.portal.NoSuchPortletPreferencesException} if it could not be found.
	 *
	 * @param portletPreferencesId the primary key of the portlet preferences
	 * @return the portlet preferences
	 * @throws com.liferay.portal.NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	 */
	@Override
	public PortletPreferences findByPrimaryKey(long portletPreferencesId)
		throws NoSuchPortletPreferencesException {
		return findByPrimaryKey((Serializable)portletPreferencesId);
	}

	/**
	 * Returns the portlet preferences with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the portlet preferences
	 * @return the portlet preferences, or <code>null</code> if a portlet preferences with the primary key could not be found
	 */
	@Override
	public PortletPreferences fetchByPrimaryKey(Serializable primaryKey) {
		PortletPreferences portletPreferences = (PortletPreferences)EntityCacheUtil.getResult(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
				PortletPreferencesImpl.class, primaryKey);

		if (portletPreferences == _nullPortletPreferences) {
			return null;
		}

		if (portletPreferences == null) {
			Session session = null;

			try {
				session = openSession();

				portletPreferences = (PortletPreferences)session.get(PortletPreferencesImpl.class,
						primaryKey);

				if (portletPreferences != null) {
					cacheResult(portletPreferences);
				}
				else {
					EntityCacheUtil.putResult(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
						PortletPreferencesImpl.class, primaryKey,
						_nullPortletPreferences);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
					PortletPreferencesImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return portletPreferences;
	}

	/**
	 * Returns the portlet preferences with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param portletPreferencesId the primary key of the portlet preferences
	 * @return the portlet preferences, or <code>null</code> if a portlet preferences with the primary key could not be found
	 */
	@Override
	public PortletPreferences fetchByPrimaryKey(long portletPreferencesId) {
		return fetchByPrimaryKey((Serializable)portletPreferencesId);
	}

	@Override
	public Map<Serializable, PortletPreferences> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, PortletPreferences> map = new HashMap<Serializable, PortletPreferences>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			PortletPreferences portletPreferences = fetchByPrimaryKey(primaryKey);

			if (portletPreferences != null) {
				map.put(primaryKey, portletPreferences);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			PortletPreferences portletPreferences = (PortletPreferences)EntityCacheUtil.getResult(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
					PortletPreferencesImpl.class, primaryKey);

			if (portletPreferences == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, portletPreferences);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_PORTLETPREFERENCES_WHERE_PKS_IN);

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

			for (PortletPreferences portletPreferences : (List<PortletPreferences>)q.list()) {
				map.put(portletPreferences.getPrimaryKeyObj(),
					portletPreferences);

				cacheResult(portletPreferences);

				uncachedPrimaryKeys.remove(portletPreferences.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				EntityCacheUtil.putResult(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
					PortletPreferencesImpl.class, primaryKey,
					_nullPortletPreferences);
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
	 * Returns all the portlet preferenceses.
	 *
	 * @return the portlet preferenceses
	 */
	@Override
	public List<PortletPreferences> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the portlet preferenceses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletPreferencesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of portlet preferenceses
	 * @param end the upper bound of the range of portlet preferenceses (not inclusive)
	 * @return the range of portlet preferenceses
	 */
	@Override
	public List<PortletPreferences> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the portlet preferenceses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletPreferencesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of portlet preferenceses
	 * @param end the upper bound of the range of portlet preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of portlet preferenceses
	 */
	@Override
	public List<PortletPreferences> findAll(int start, int end,
		OrderByComparator orderByComparator) {
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

		List<PortletPreferences> list = (List<PortletPreferences>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_PORTLETPREFERENCES);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_PORTLETPREFERENCES;

				if (pagination) {
					sql = sql.concat(PortletPreferencesModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<PortletPreferences>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<PortletPreferences>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Removes all the portlet preferenceses from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (PortletPreferences portletPreferences : findAll()) {
			remove(portletPreferences);
		}
	}

	/**
	 * Returns the number of portlet preferenceses.
	 *
	 * @return the number of portlet preferenceses
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_PORTLETPREFERENCES);

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

	/**
	 * Initializes the portlet preferences persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.PortletPreferences")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<PortletPreferences>> listenersList = new ArrayList<ModelListener<PortletPreferences>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<PortletPreferences>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(PortletPreferencesImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_PORTLETPREFERENCES = "SELECT portletPreferences FROM PortletPreferences portletPreferences";
	private static final String _SQL_SELECT_PORTLETPREFERENCES_WHERE_PKS_IN = "SELECT portletPreferences FROM PortletPreferences portletPreferences WHERE portletPreferencesId IN (";
	private static final String _SQL_SELECT_PORTLETPREFERENCES_WHERE = "SELECT portletPreferences FROM PortletPreferences portletPreferences WHERE ";
	private static final String _SQL_COUNT_PORTLETPREFERENCES = "SELECT COUNT(portletPreferences) FROM PortletPreferences portletPreferences";
	private static final String _SQL_COUNT_PORTLETPREFERENCES_WHERE = "SELECT COUNT(portletPreferences) FROM PortletPreferences portletPreferences WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "portletPreferences.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No PortletPreferences exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No PortletPreferences exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(PortletPreferencesPersistenceImpl.class);
	private static PortletPreferences _nullPortletPreferences = new PortletPreferencesImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<PortletPreferences> toCacheModel() {
				return _nullPortletPreferencesCacheModel;
			}
		};

	private static CacheModel<PortletPreferences> _nullPortletPreferencesCacheModel =
		new NullCacheModel();

	private static class NullCacheModel implements CacheModel<PortletPreferences>,
		MVCCModel {
		@Override
		public long getMvccVersion() {
			return 0;
		}

		@Override
		public void setMvccVersion(long mvccVersion) {
		}

		@Override
		public PortletPreferences toEntityModel() {
			return _nullPortletPreferences;
		}
	}
}