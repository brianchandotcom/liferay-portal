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

package com.liferay.portal.tools.service.builder.test.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryLocalizationVersionException;
import com.liferay.portal.tools.service.builder.test.model.LVEntryLocalizationVersion;
import com.liferay.portal.tools.service.builder.test.model.impl.LVEntryLocalizationVersionImpl;
import com.liferay.portal.tools.service.builder.test.model.impl.LVEntryLocalizationVersionModelImpl;
import com.liferay.portal.tools.service.builder.test.service.persistence.LVEntryLocalizationVersionPersistence;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the lv entry localization version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LVEntryLocalizationVersionPersistence
 * @see com.liferay.portal.tools.service.builder.test.service.persistence.LVEntryLocalizationVersionUtil
 * @generated
 */
@ProviderType
public class LVEntryLocalizationVersionPersistenceImpl
	extends BasePersistenceImpl<LVEntryLocalizationVersion>
	implements LVEntryLocalizationVersionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link LVEntryLocalizationVersionUtil} to access the lv entry localization version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = LVEntryLocalizationVersionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationVersionModelImpl.FINDER_CACHE_ENABLED,
			LVEntryLocalizationVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationVersionModelImpl.FINDER_CACHE_ENABLED,
			LVEntryLocalizationVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationVersionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_LVENTRYLOCALIZATIONID =
		new FinderPath(LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationVersionModelImpl.FINDER_CACHE_ENABLED,
			LVEntryLocalizationVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByLvEntryLocalizationId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LVENTRYLOCALIZATIONID =
		new FinderPath(LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationVersionModelImpl.FINDER_CACHE_ENABLED,
			LVEntryLocalizationVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByLvEntryLocalizationId",
			new String[] { Long.class.getName() },
			LVEntryLocalizationVersionModelImpl.LVENTRYLOCALIZATIONID_COLUMN_BITMASK |
			LVEntryLocalizationVersionModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_LVENTRYLOCALIZATIONID = new FinderPath(LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationVersionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByLvEntryLocalizationId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the lv entry localization versions where lvEntryLocalizationId = &#63;.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @return the matching lv entry localization versions
	 */
	@Override
	public List<LVEntryLocalizationVersion> findByLvEntryLocalizationId(
		long lvEntryLocalizationId) {
		return findByLvEntryLocalizationId(lvEntryLocalizationId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the lv entry localization versions where lvEntryLocalizationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryLocalizationVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @return the range of matching lv entry localization versions
	 */
	@Override
	public List<LVEntryLocalizationVersion> findByLvEntryLocalizationId(
		long lvEntryLocalizationId, int start, int end) {
		return findByLvEntryLocalizationId(lvEntryLocalizationId, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the lv entry localization versions where lvEntryLocalizationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryLocalizationVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entry localization versions
	 */
	@Override
	public List<LVEntryLocalizationVersion> findByLvEntryLocalizationId(
		long lvEntryLocalizationId, int start, int end,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator) {
		return findByLvEntryLocalizationId(lvEntryLocalizationId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the lv entry localization versions where lvEntryLocalizationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryLocalizationVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching lv entry localization versions
	 */
	@Override
	public List<LVEntryLocalizationVersion> findByLvEntryLocalizationId(
		long lvEntryLocalizationId, int start, int end,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LVENTRYLOCALIZATIONID;
			finderArgs = new Object[] { lvEntryLocalizationId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_LVENTRYLOCALIZATIONID;
			finderArgs = new Object[] {
					lvEntryLocalizationId,
					
					start, end, orderByComparator
				};
		}

		List<LVEntryLocalizationVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LVEntryLocalizationVersion>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LVEntryLocalizationVersion lvEntryLocalizationVersion : list) {
					if ((lvEntryLocalizationId != lvEntryLocalizationVersion.getLvEntryLocalizationId())) {
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
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_LVENTRYLOCALIZATIONVERSION_WHERE);

			query.append(_FINDER_COLUMN_LVENTRYLOCALIZATIONID_LVENTRYLOCALIZATIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LVEntryLocalizationVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(lvEntryLocalizationId);

				if (!pagination) {
					list = (List<LVEntryLocalizationVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LVEntryLocalizationVersion>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first lv entry localization version in the ordered set where lvEntryLocalizationId = &#63;.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion findByLvEntryLocalizationId_First(
		long lvEntryLocalizationId,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator)
		throws NoSuchLVEntryLocalizationVersionException {
		LVEntryLocalizationVersion lvEntryLocalizationVersion = fetchByLvEntryLocalizationId_First(lvEntryLocalizationId,
				orderByComparator);

		if (lvEntryLocalizationVersion != null) {
			return lvEntryLocalizationVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("lvEntryLocalizationId=");
		msg.append(lvEntryLocalizationId);

		msg.append("}");

		throw new NoSuchLVEntryLocalizationVersionException(msg.toString());
	}

	/**
	 * Returns the first lv entry localization version in the ordered set where lvEntryLocalizationId = &#63;.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion fetchByLvEntryLocalizationId_First(
		long lvEntryLocalizationId,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator) {
		List<LVEntryLocalizationVersion> list = findByLvEntryLocalizationId(lvEntryLocalizationId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last lv entry localization version in the ordered set where lvEntryLocalizationId = &#63;.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion findByLvEntryLocalizationId_Last(
		long lvEntryLocalizationId,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator)
		throws NoSuchLVEntryLocalizationVersionException {
		LVEntryLocalizationVersion lvEntryLocalizationVersion = fetchByLvEntryLocalizationId_Last(lvEntryLocalizationId,
				orderByComparator);

		if (lvEntryLocalizationVersion != null) {
			return lvEntryLocalizationVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("lvEntryLocalizationId=");
		msg.append(lvEntryLocalizationId);

		msg.append("}");

		throw new NoSuchLVEntryLocalizationVersionException(msg.toString());
	}

	/**
	 * Returns the last lv entry localization version in the ordered set where lvEntryLocalizationId = &#63;.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion fetchByLvEntryLocalizationId_Last(
		long lvEntryLocalizationId,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator) {
		int count = countByLvEntryLocalizationId(lvEntryLocalizationId);

		if (count == 0) {
			return null;
		}

		List<LVEntryLocalizationVersion> list = findByLvEntryLocalizationId(lvEntryLocalizationId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the lv entry localization versions before and after the current lv entry localization version in the ordered set where lvEntryLocalizationId = &#63;.
	 *
	 * @param lvEntryLocalizationVersionId the primary key of the current lv entry localization version
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a lv entry localization version with the primary key could not be found
	 */
	@Override
	public LVEntryLocalizationVersion[] findByLvEntryLocalizationId_PrevAndNext(
		long lvEntryLocalizationVersionId, long lvEntryLocalizationId,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator)
		throws NoSuchLVEntryLocalizationVersionException {
		LVEntryLocalizationVersion lvEntryLocalizationVersion = findByPrimaryKey(lvEntryLocalizationVersionId);

		Session session = null;

		try {
			session = openSession();

			LVEntryLocalizationVersion[] array = new LVEntryLocalizationVersionImpl[3];

			array[0] = getByLvEntryLocalizationId_PrevAndNext(session,
					lvEntryLocalizationVersion, lvEntryLocalizationId,
					orderByComparator, true);

			array[1] = lvEntryLocalizationVersion;

			array[2] = getByLvEntryLocalizationId_PrevAndNext(session,
					lvEntryLocalizationVersion, lvEntryLocalizationId,
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

	protected LVEntryLocalizationVersion getByLvEntryLocalizationId_PrevAndNext(
		Session session, LVEntryLocalizationVersion lvEntryLocalizationVersion,
		long lvEntryLocalizationId,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LVENTRYLOCALIZATIONVERSION_WHERE);

		query.append(_FINDER_COLUMN_LVENTRYLOCALIZATIONID_LVENTRYLOCALIZATIONID_2);

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
			query.append(LVEntryLocalizationVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(lvEntryLocalizationId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(lvEntryLocalizationVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LVEntryLocalizationVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the lv entry localization versions where lvEntryLocalizationId = &#63; from the database.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 */
	@Override
	public void removeByLvEntryLocalizationId(long lvEntryLocalizationId) {
		for (LVEntryLocalizationVersion lvEntryLocalizationVersion : findByLvEntryLocalizationId(
				lvEntryLocalizationId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null)) {
			remove(lvEntryLocalizationVersion);
		}
	}

	/**
	 * Returns the number of lv entry localization versions where lvEntryLocalizationId = &#63;.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @return the number of matching lv entry localization versions
	 */
	@Override
	public int countByLvEntryLocalizationId(long lvEntryLocalizationId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_LVENTRYLOCALIZATIONID;

		Object[] finderArgs = new Object[] { lvEntryLocalizationId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LVENTRYLOCALIZATIONVERSION_WHERE);

			query.append(_FINDER_COLUMN_LVENTRYLOCALIZATIONID_LVENTRYLOCALIZATIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(lvEntryLocalizationId);

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

	private static final String _FINDER_COLUMN_LVENTRYLOCALIZATIONID_LVENTRYLOCALIZATIONID_2 =
		"lvEntryLocalizationVersion.lvEntryLocalizationId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_LVENTRYLOCALIZATIONID_VERSION =
		new FinderPath(LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationVersionModelImpl.FINDER_CACHE_ENABLED,
			LVEntryLocalizationVersionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByLvEntryLocalizationId_Version",
			new String[] { Long.class.getName(), Integer.class.getName() },
			LVEntryLocalizationVersionModelImpl.LVENTRYLOCALIZATIONID_COLUMN_BITMASK |
			LVEntryLocalizationVersionModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_LVENTRYLOCALIZATIONID_VERSION =
		new FinderPath(LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationVersionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByLvEntryLocalizationId_Version",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns the lv entry localization version where lvEntryLocalizationId = &#63; and version = &#63; or throws a {@link NoSuchLVEntryLocalizationVersionException} if it could not be found.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param version the version
	 * @return the matching lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion findByLvEntryLocalizationId_Version(
		long lvEntryLocalizationId, int version)
		throws NoSuchLVEntryLocalizationVersionException {
		LVEntryLocalizationVersion lvEntryLocalizationVersion = fetchByLvEntryLocalizationId_Version(lvEntryLocalizationId,
				version);

		if (lvEntryLocalizationVersion == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("lvEntryLocalizationId=");
			msg.append(lvEntryLocalizationId);

			msg.append(", version=");
			msg.append(version);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchLVEntryLocalizationVersionException(msg.toString());
		}

		return lvEntryLocalizationVersion;
	}

	/**
	 * Returns the lv entry localization version where lvEntryLocalizationId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param version the version
	 * @return the matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion fetchByLvEntryLocalizationId_Version(
		long lvEntryLocalizationId, int version) {
		return fetchByLvEntryLocalizationId_Version(lvEntryLocalizationId,
			version, true);
	}

	/**
	 * Returns the lv entry localization version where lvEntryLocalizationId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param version the version
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion fetchByLvEntryLocalizationId_Version(
		long lvEntryLocalizationId, int version, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { lvEntryLocalizationId, version };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_LVENTRYLOCALIZATIONID_VERSION,
					finderArgs, this);
		}

		if (result instanceof LVEntryLocalizationVersion) {
			LVEntryLocalizationVersion lvEntryLocalizationVersion = (LVEntryLocalizationVersion)result;

			if ((lvEntryLocalizationId != lvEntryLocalizationVersion.getLvEntryLocalizationId()) ||
					(version != lvEntryLocalizationVersion.getVersion())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_LVENTRYLOCALIZATIONVERSION_WHERE);

			query.append(_FINDER_COLUMN_LVENTRYLOCALIZATIONID_VERSION_LVENTRYLOCALIZATIONID_2);

			query.append(_FINDER_COLUMN_LVENTRYLOCALIZATIONID_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(lvEntryLocalizationId);

				qPos.add(version);

				List<LVEntryLocalizationVersion> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_LVENTRYLOCALIZATIONID_VERSION,
						finderArgs, list);
				}
				else {
					LVEntryLocalizationVersion lvEntryLocalizationVersion = list.get(0);

					result = lvEntryLocalizationVersion;

					cacheResult(lvEntryLocalizationVersion);

					if ((lvEntryLocalizationVersion.getLvEntryLocalizationId() != lvEntryLocalizationId) ||
							(lvEntryLocalizationVersion.getVersion() != version)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_LVENTRYLOCALIZATIONID_VERSION,
							finderArgs, lvEntryLocalizationVersion);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_LVENTRYLOCALIZATIONID_VERSION,
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
			return (LVEntryLocalizationVersion)result;
		}
	}

	/**
	 * Removes the lv entry localization version where lvEntryLocalizationId = &#63; and version = &#63; from the database.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param version the version
	 * @return the lv entry localization version that was removed
	 */
	@Override
	public LVEntryLocalizationVersion removeByLvEntryLocalizationId_Version(
		long lvEntryLocalizationId, int version)
		throws NoSuchLVEntryLocalizationVersionException {
		LVEntryLocalizationVersion lvEntryLocalizationVersion = findByLvEntryLocalizationId_Version(lvEntryLocalizationId,
				version);

		return remove(lvEntryLocalizationVersion);
	}

	/**
	 * Returns the number of lv entry localization versions where lvEntryLocalizationId = &#63; and version = &#63;.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param version the version
	 * @return the number of matching lv entry localization versions
	 */
	@Override
	public int countByLvEntryLocalizationId_Version(
		long lvEntryLocalizationId, int version) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_LVENTRYLOCALIZATIONID_VERSION;

		Object[] finderArgs = new Object[] { lvEntryLocalizationId, version };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LVENTRYLOCALIZATIONVERSION_WHERE);

			query.append(_FINDER_COLUMN_LVENTRYLOCALIZATIONID_VERSION_LVENTRYLOCALIZATIONID_2);

			query.append(_FINDER_COLUMN_LVENTRYLOCALIZATIONID_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(lvEntryLocalizationId);

				qPos.add(version);

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

	private static final String _FINDER_COLUMN_LVENTRYLOCALIZATIONID_VERSION_LVENTRYLOCALIZATIONID_2 =
		"lvEntryLocalizationVersion.lvEntryLocalizationId = ? AND ";
	private static final String _FINDER_COLUMN_LVENTRYLOCALIZATIONID_VERSION_VERSION_2 =
		"lvEntryLocalizationVersion.version = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ENTRYID = new FinderPath(LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationVersionModelImpl.FINDER_CACHE_ENABLED,
			LVEntryLocalizationVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByEntryId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ENTRYID =
		new FinderPath(LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationVersionModelImpl.FINDER_CACHE_ENABLED,
			LVEntryLocalizationVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByEntryId",
			new String[] { Long.class.getName() },
			LVEntryLocalizationVersionModelImpl.ENTRYID_COLUMN_BITMASK |
			LVEntryLocalizationVersionModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ENTRYID = new FinderPath(LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationVersionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByEntryId", new String[] { Long.class.getName() });

	/**
	 * Returns all the lv entry localization versions where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @return the matching lv entry localization versions
	 */
	@Override
	public List<LVEntryLocalizationVersion> findByEntryId(long entryId) {
		return findByEntryId(entryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the lv entry localization versions where entryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryLocalizationVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param entryId the entry ID
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @return the range of matching lv entry localization versions
	 */
	@Override
	public List<LVEntryLocalizationVersion> findByEntryId(long entryId,
		int start, int end) {
		return findByEntryId(entryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the lv entry localization versions where entryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryLocalizationVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param entryId the entry ID
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entry localization versions
	 */
	@Override
	public List<LVEntryLocalizationVersion> findByEntryId(long entryId,
		int start, int end,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator) {
		return findByEntryId(entryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the lv entry localization versions where entryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryLocalizationVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param entryId the entry ID
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching lv entry localization versions
	 */
	@Override
	public List<LVEntryLocalizationVersion> findByEntryId(long entryId,
		int start, int end,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ENTRYID;
			finderArgs = new Object[] { entryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_ENTRYID;
			finderArgs = new Object[] { entryId, start, end, orderByComparator };
		}

		List<LVEntryLocalizationVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LVEntryLocalizationVersion>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LVEntryLocalizationVersion lvEntryLocalizationVersion : list) {
					if ((entryId != lvEntryLocalizationVersion.getEntryId())) {
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
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_LVENTRYLOCALIZATIONVERSION_WHERE);

			query.append(_FINDER_COLUMN_ENTRYID_ENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LVEntryLocalizationVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId);

				if (!pagination) {
					list = (List<LVEntryLocalizationVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LVEntryLocalizationVersion>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first lv entry localization version in the ordered set where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion findByEntryId_First(long entryId,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator)
		throws NoSuchLVEntryLocalizationVersionException {
		LVEntryLocalizationVersion lvEntryLocalizationVersion = fetchByEntryId_First(entryId,
				orderByComparator);

		if (lvEntryLocalizationVersion != null) {
			return lvEntryLocalizationVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId=");
		msg.append(entryId);

		msg.append("}");

		throw new NoSuchLVEntryLocalizationVersionException(msg.toString());
	}

	/**
	 * Returns the first lv entry localization version in the ordered set where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion fetchByEntryId_First(long entryId,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator) {
		List<LVEntryLocalizationVersion> list = findByEntryId(entryId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last lv entry localization version in the ordered set where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion findByEntryId_Last(long entryId,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator)
		throws NoSuchLVEntryLocalizationVersionException {
		LVEntryLocalizationVersion lvEntryLocalizationVersion = fetchByEntryId_Last(entryId,
				orderByComparator);

		if (lvEntryLocalizationVersion != null) {
			return lvEntryLocalizationVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId=");
		msg.append(entryId);

		msg.append("}");

		throw new NoSuchLVEntryLocalizationVersionException(msg.toString());
	}

	/**
	 * Returns the last lv entry localization version in the ordered set where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion fetchByEntryId_Last(long entryId,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator) {
		int count = countByEntryId(entryId);

		if (count == 0) {
			return null;
		}

		List<LVEntryLocalizationVersion> list = findByEntryId(entryId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the lv entry localization versions before and after the current lv entry localization version in the ordered set where entryId = &#63;.
	 *
	 * @param lvEntryLocalizationVersionId the primary key of the current lv entry localization version
	 * @param entryId the entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a lv entry localization version with the primary key could not be found
	 */
	@Override
	public LVEntryLocalizationVersion[] findByEntryId_PrevAndNext(
		long lvEntryLocalizationVersionId, long entryId,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator)
		throws NoSuchLVEntryLocalizationVersionException {
		LVEntryLocalizationVersion lvEntryLocalizationVersion = findByPrimaryKey(lvEntryLocalizationVersionId);

		Session session = null;

		try {
			session = openSession();

			LVEntryLocalizationVersion[] array = new LVEntryLocalizationVersionImpl[3];

			array[0] = getByEntryId_PrevAndNext(session,
					lvEntryLocalizationVersion, entryId, orderByComparator, true);

			array[1] = lvEntryLocalizationVersion;

			array[2] = getByEntryId_PrevAndNext(session,
					lvEntryLocalizationVersion, entryId, orderByComparator,
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

	protected LVEntryLocalizationVersion getByEntryId_PrevAndNext(
		Session session, LVEntryLocalizationVersion lvEntryLocalizationVersion,
		long entryId,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LVENTRYLOCALIZATIONVERSION_WHERE);

		query.append(_FINDER_COLUMN_ENTRYID_ENTRYID_2);

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
			query.append(LVEntryLocalizationVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(entryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(lvEntryLocalizationVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LVEntryLocalizationVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the lv entry localization versions where entryId = &#63; from the database.
	 *
	 * @param entryId the entry ID
	 */
	@Override
	public void removeByEntryId(long entryId) {
		for (LVEntryLocalizationVersion lvEntryLocalizationVersion : findByEntryId(
				entryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(lvEntryLocalizationVersion);
		}
	}

	/**
	 * Returns the number of lv entry localization versions where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @return the number of matching lv entry localization versions
	 */
	@Override
	public int countByEntryId(long entryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_ENTRYID;

		Object[] finderArgs = new Object[] { entryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LVENTRYLOCALIZATIONVERSION_WHERE);

			query.append(_FINDER_COLUMN_ENTRYID_ENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId);

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

	private static final String _FINDER_COLUMN_ENTRYID_ENTRYID_2 = "lvEntryLocalizationVersion.entryId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ENTRYID_VERSION =
		new FinderPath(LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationVersionModelImpl.FINDER_CACHE_ENABLED,
			LVEntryLocalizationVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByEntryId_Version",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ENTRYID_VERSION =
		new FinderPath(LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationVersionModelImpl.FINDER_CACHE_ENABLED,
			LVEntryLocalizationVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByEntryId_Version",
			new String[] { Long.class.getName(), Integer.class.getName() },
			LVEntryLocalizationVersionModelImpl.ENTRYID_COLUMN_BITMASK |
			LVEntryLocalizationVersionModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ENTRYID_VERSION = new FinderPath(LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationVersionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByEntryId_Version",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the lv entry localization versions where entryId = &#63; and version = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param version the version
	 * @return the matching lv entry localization versions
	 */
	@Override
	public List<LVEntryLocalizationVersion> findByEntryId_Version(
		long entryId, int version) {
		return findByEntryId_Version(entryId, version, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the lv entry localization versions where entryId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryLocalizationVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param entryId the entry ID
	 * @param version the version
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @return the range of matching lv entry localization versions
	 */
	@Override
	public List<LVEntryLocalizationVersion> findByEntryId_Version(
		long entryId, int version, int start, int end) {
		return findByEntryId_Version(entryId, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the lv entry localization versions where entryId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryLocalizationVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param entryId the entry ID
	 * @param version the version
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entry localization versions
	 */
	@Override
	public List<LVEntryLocalizationVersion> findByEntryId_Version(
		long entryId, int version, int start, int end,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator) {
		return findByEntryId_Version(entryId, version, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the lv entry localization versions where entryId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryLocalizationVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param entryId the entry ID
	 * @param version the version
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching lv entry localization versions
	 */
	@Override
	public List<LVEntryLocalizationVersion> findByEntryId_Version(
		long entryId, int version, int start, int end,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ENTRYID_VERSION;
			finderArgs = new Object[] { entryId, version };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_ENTRYID_VERSION;
			finderArgs = new Object[] {
					entryId, version,
					
					start, end, orderByComparator
				};
		}

		List<LVEntryLocalizationVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LVEntryLocalizationVersion>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LVEntryLocalizationVersion lvEntryLocalizationVersion : list) {
					if ((entryId != lvEntryLocalizationVersion.getEntryId()) ||
							(version != lvEntryLocalizationVersion.getVersion())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_LVENTRYLOCALIZATIONVERSION_WHERE);

			query.append(_FINDER_COLUMN_ENTRYID_VERSION_ENTRYID_2);

			query.append(_FINDER_COLUMN_ENTRYID_VERSION_VERSION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LVEntryLocalizationVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId);

				qPos.add(version);

				if (!pagination) {
					list = (List<LVEntryLocalizationVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LVEntryLocalizationVersion>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first lv entry localization version in the ordered set where entryId = &#63; and version = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion findByEntryId_Version_First(
		long entryId, int version,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator)
		throws NoSuchLVEntryLocalizationVersionException {
		LVEntryLocalizationVersion lvEntryLocalizationVersion = fetchByEntryId_Version_First(entryId,
				version, orderByComparator);

		if (lvEntryLocalizationVersion != null) {
			return lvEntryLocalizationVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId=");
		msg.append(entryId);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLVEntryLocalizationVersionException(msg.toString());
	}

	/**
	 * Returns the first lv entry localization version in the ordered set where entryId = &#63; and version = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion fetchByEntryId_Version_First(
		long entryId, int version,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator) {
		List<LVEntryLocalizationVersion> list = findByEntryId_Version(entryId,
				version, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last lv entry localization version in the ordered set where entryId = &#63; and version = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion findByEntryId_Version_Last(long entryId,
		int version,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator)
		throws NoSuchLVEntryLocalizationVersionException {
		LVEntryLocalizationVersion lvEntryLocalizationVersion = fetchByEntryId_Version_Last(entryId,
				version, orderByComparator);

		if (lvEntryLocalizationVersion != null) {
			return lvEntryLocalizationVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId=");
		msg.append(entryId);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLVEntryLocalizationVersionException(msg.toString());
	}

	/**
	 * Returns the last lv entry localization version in the ordered set where entryId = &#63; and version = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion fetchByEntryId_Version_Last(
		long entryId, int version,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator) {
		int count = countByEntryId_Version(entryId, version);

		if (count == 0) {
			return null;
		}

		List<LVEntryLocalizationVersion> list = findByEntryId_Version(entryId,
				version, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the lv entry localization versions before and after the current lv entry localization version in the ordered set where entryId = &#63; and version = &#63;.
	 *
	 * @param lvEntryLocalizationVersionId the primary key of the current lv entry localization version
	 * @param entryId the entry ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a lv entry localization version with the primary key could not be found
	 */
	@Override
	public LVEntryLocalizationVersion[] findByEntryId_Version_PrevAndNext(
		long lvEntryLocalizationVersionId, long entryId, int version,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator)
		throws NoSuchLVEntryLocalizationVersionException {
		LVEntryLocalizationVersion lvEntryLocalizationVersion = findByPrimaryKey(lvEntryLocalizationVersionId);

		Session session = null;

		try {
			session = openSession();

			LVEntryLocalizationVersion[] array = new LVEntryLocalizationVersionImpl[3];

			array[0] = getByEntryId_Version_PrevAndNext(session,
					lvEntryLocalizationVersion, entryId, version,
					orderByComparator, true);

			array[1] = lvEntryLocalizationVersion;

			array[2] = getByEntryId_Version_PrevAndNext(session,
					lvEntryLocalizationVersion, entryId, version,
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

	protected LVEntryLocalizationVersion getByEntryId_Version_PrevAndNext(
		Session session, LVEntryLocalizationVersion lvEntryLocalizationVersion,
		long entryId, int version,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_LVENTRYLOCALIZATIONVERSION_WHERE);

		query.append(_FINDER_COLUMN_ENTRYID_VERSION_ENTRYID_2);

		query.append(_FINDER_COLUMN_ENTRYID_VERSION_VERSION_2);

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
			query.append(LVEntryLocalizationVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(entryId);

		qPos.add(version);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(lvEntryLocalizationVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LVEntryLocalizationVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the lv entry localization versions where entryId = &#63; and version = &#63; from the database.
	 *
	 * @param entryId the entry ID
	 * @param version the version
	 */
	@Override
	public void removeByEntryId_Version(long entryId, int version) {
		for (LVEntryLocalizationVersion lvEntryLocalizationVersion : findByEntryId_Version(
				entryId, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(lvEntryLocalizationVersion);
		}
	}

	/**
	 * Returns the number of lv entry localization versions where entryId = &#63; and version = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param version the version
	 * @return the number of matching lv entry localization versions
	 */
	@Override
	public int countByEntryId_Version(long entryId, int version) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_ENTRYID_VERSION;

		Object[] finderArgs = new Object[] { entryId, version };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LVENTRYLOCALIZATIONVERSION_WHERE);

			query.append(_FINDER_COLUMN_ENTRYID_VERSION_ENTRYID_2);

			query.append(_FINDER_COLUMN_ENTRYID_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId);

				qPos.add(version);

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

	private static final String _FINDER_COLUMN_ENTRYID_VERSION_ENTRYID_2 = "lvEntryLocalizationVersion.entryId = ? AND ";
	private static final String _FINDER_COLUMN_ENTRYID_VERSION_VERSION_2 = "lvEntryLocalizationVersion.version = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ENTRYID_LANGUAGEID =
		new FinderPath(LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationVersionModelImpl.FINDER_CACHE_ENABLED,
			LVEntryLocalizationVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByEntryId_LanguageId",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ENTRYID_LANGUAGEID =
		new FinderPath(LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationVersionModelImpl.FINDER_CACHE_ENABLED,
			LVEntryLocalizationVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByEntryId_LanguageId",
			new String[] { Long.class.getName(), String.class.getName() },
			LVEntryLocalizationVersionModelImpl.ENTRYID_COLUMN_BITMASK |
			LVEntryLocalizationVersionModelImpl.LANGUAGEID_COLUMN_BITMASK |
			LVEntryLocalizationVersionModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ENTRYID_LANGUAGEID = new FinderPath(LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationVersionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByEntryId_LanguageId",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns all the lv entry localization versions where entryId = &#63; and languageId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param languageId the language ID
	 * @return the matching lv entry localization versions
	 */
	@Override
	public List<LVEntryLocalizationVersion> findByEntryId_LanguageId(
		long entryId, String languageId) {
		return findByEntryId_LanguageId(entryId, languageId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the lv entry localization versions where entryId = &#63; and languageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryLocalizationVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param entryId the entry ID
	 * @param languageId the language ID
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @return the range of matching lv entry localization versions
	 */
	@Override
	public List<LVEntryLocalizationVersion> findByEntryId_LanguageId(
		long entryId, String languageId, int start, int end) {
		return findByEntryId_LanguageId(entryId, languageId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the lv entry localization versions where entryId = &#63; and languageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryLocalizationVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param entryId the entry ID
	 * @param languageId the language ID
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entry localization versions
	 */
	@Override
	public List<LVEntryLocalizationVersion> findByEntryId_LanguageId(
		long entryId, String languageId, int start, int end,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator) {
		return findByEntryId_LanguageId(entryId, languageId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the lv entry localization versions where entryId = &#63; and languageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryLocalizationVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param entryId the entry ID
	 * @param languageId the language ID
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching lv entry localization versions
	 */
	@Override
	public List<LVEntryLocalizationVersion> findByEntryId_LanguageId(
		long entryId, String languageId, int start, int end,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ENTRYID_LANGUAGEID;
			finderArgs = new Object[] { entryId, languageId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_ENTRYID_LANGUAGEID;
			finderArgs = new Object[] {
					entryId, languageId,
					
					start, end, orderByComparator
				};
		}

		List<LVEntryLocalizationVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LVEntryLocalizationVersion>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LVEntryLocalizationVersion lvEntryLocalizationVersion : list) {
					if ((entryId != lvEntryLocalizationVersion.getEntryId()) ||
							!Objects.equals(languageId,
								lvEntryLocalizationVersion.getLanguageId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_LVENTRYLOCALIZATIONVERSION_WHERE);

			query.append(_FINDER_COLUMN_ENTRYID_LANGUAGEID_ENTRYID_2);

			boolean bindLanguageId = false;

			if (languageId == null) {
				query.append(_FINDER_COLUMN_ENTRYID_LANGUAGEID_LANGUAGEID_1);
			}
			else if (languageId.equals("")) {
				query.append(_FINDER_COLUMN_ENTRYID_LANGUAGEID_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(_FINDER_COLUMN_ENTRYID_LANGUAGEID_LANGUAGEID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LVEntryLocalizationVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId);

				if (bindLanguageId) {
					qPos.add(languageId);
				}

				if (!pagination) {
					list = (List<LVEntryLocalizationVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LVEntryLocalizationVersion>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first lv entry localization version in the ordered set where entryId = &#63; and languageId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion findByEntryId_LanguageId_First(
		long entryId, String languageId,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator)
		throws NoSuchLVEntryLocalizationVersionException {
		LVEntryLocalizationVersion lvEntryLocalizationVersion = fetchByEntryId_LanguageId_First(entryId,
				languageId, orderByComparator);

		if (lvEntryLocalizationVersion != null) {
			return lvEntryLocalizationVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId=");
		msg.append(entryId);

		msg.append(", languageId=");
		msg.append(languageId);

		msg.append("}");

		throw new NoSuchLVEntryLocalizationVersionException(msg.toString());
	}

	/**
	 * Returns the first lv entry localization version in the ordered set where entryId = &#63; and languageId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion fetchByEntryId_LanguageId_First(
		long entryId, String languageId,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator) {
		List<LVEntryLocalizationVersion> list = findByEntryId_LanguageId(entryId,
				languageId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last lv entry localization version in the ordered set where entryId = &#63; and languageId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion findByEntryId_LanguageId_Last(
		long entryId, String languageId,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator)
		throws NoSuchLVEntryLocalizationVersionException {
		LVEntryLocalizationVersion lvEntryLocalizationVersion = fetchByEntryId_LanguageId_Last(entryId,
				languageId, orderByComparator);

		if (lvEntryLocalizationVersion != null) {
			return lvEntryLocalizationVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId=");
		msg.append(entryId);

		msg.append(", languageId=");
		msg.append(languageId);

		msg.append("}");

		throw new NoSuchLVEntryLocalizationVersionException(msg.toString());
	}

	/**
	 * Returns the last lv entry localization version in the ordered set where entryId = &#63; and languageId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion fetchByEntryId_LanguageId_Last(
		long entryId, String languageId,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator) {
		int count = countByEntryId_LanguageId(entryId, languageId);

		if (count == 0) {
			return null;
		}

		List<LVEntryLocalizationVersion> list = findByEntryId_LanguageId(entryId,
				languageId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the lv entry localization versions before and after the current lv entry localization version in the ordered set where entryId = &#63; and languageId = &#63;.
	 *
	 * @param lvEntryLocalizationVersionId the primary key of the current lv entry localization version
	 * @param entryId the entry ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a lv entry localization version with the primary key could not be found
	 */
	@Override
	public LVEntryLocalizationVersion[] findByEntryId_LanguageId_PrevAndNext(
		long lvEntryLocalizationVersionId, long entryId, String languageId,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator)
		throws NoSuchLVEntryLocalizationVersionException {
		LVEntryLocalizationVersion lvEntryLocalizationVersion = findByPrimaryKey(lvEntryLocalizationVersionId);

		Session session = null;

		try {
			session = openSession();

			LVEntryLocalizationVersion[] array = new LVEntryLocalizationVersionImpl[3];

			array[0] = getByEntryId_LanguageId_PrevAndNext(session,
					lvEntryLocalizationVersion, entryId, languageId,
					orderByComparator, true);

			array[1] = lvEntryLocalizationVersion;

			array[2] = getByEntryId_LanguageId_PrevAndNext(session,
					lvEntryLocalizationVersion, entryId, languageId,
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

	protected LVEntryLocalizationVersion getByEntryId_LanguageId_PrevAndNext(
		Session session, LVEntryLocalizationVersion lvEntryLocalizationVersion,
		long entryId, String languageId,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_LVENTRYLOCALIZATIONVERSION_WHERE);

		query.append(_FINDER_COLUMN_ENTRYID_LANGUAGEID_ENTRYID_2);

		boolean bindLanguageId = false;

		if (languageId == null) {
			query.append(_FINDER_COLUMN_ENTRYID_LANGUAGEID_LANGUAGEID_1);
		}
		else if (languageId.equals("")) {
			query.append(_FINDER_COLUMN_ENTRYID_LANGUAGEID_LANGUAGEID_3);
		}
		else {
			bindLanguageId = true;

			query.append(_FINDER_COLUMN_ENTRYID_LANGUAGEID_LANGUAGEID_2);
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
			query.append(LVEntryLocalizationVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(entryId);

		if (bindLanguageId) {
			qPos.add(languageId);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(lvEntryLocalizationVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LVEntryLocalizationVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the lv entry localization versions where entryId = &#63; and languageId = &#63; from the database.
	 *
	 * @param entryId the entry ID
	 * @param languageId the language ID
	 */
	@Override
	public void removeByEntryId_LanguageId(long entryId, String languageId) {
		for (LVEntryLocalizationVersion lvEntryLocalizationVersion : findByEntryId_LanguageId(
				entryId, languageId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(lvEntryLocalizationVersion);
		}
	}

	/**
	 * Returns the number of lv entry localization versions where entryId = &#63; and languageId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param languageId the language ID
	 * @return the number of matching lv entry localization versions
	 */
	@Override
	public int countByEntryId_LanguageId(long entryId, String languageId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_ENTRYID_LANGUAGEID;

		Object[] finderArgs = new Object[] { entryId, languageId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LVENTRYLOCALIZATIONVERSION_WHERE);

			query.append(_FINDER_COLUMN_ENTRYID_LANGUAGEID_ENTRYID_2);

			boolean bindLanguageId = false;

			if (languageId == null) {
				query.append(_FINDER_COLUMN_ENTRYID_LANGUAGEID_LANGUAGEID_1);
			}
			else if (languageId.equals("")) {
				query.append(_FINDER_COLUMN_ENTRYID_LANGUAGEID_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(_FINDER_COLUMN_ENTRYID_LANGUAGEID_LANGUAGEID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId);

				if (bindLanguageId) {
					qPos.add(languageId);
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

	private static final String _FINDER_COLUMN_ENTRYID_LANGUAGEID_ENTRYID_2 = "lvEntryLocalizationVersion.entryId = ? AND ";
	private static final String _FINDER_COLUMN_ENTRYID_LANGUAGEID_LANGUAGEID_1 = "lvEntryLocalizationVersion.languageId IS NULL";
	private static final String _FINDER_COLUMN_ENTRYID_LANGUAGEID_LANGUAGEID_2 = "lvEntryLocalizationVersion.languageId = ?";
	private static final String _FINDER_COLUMN_ENTRYID_LANGUAGEID_LANGUAGEID_3 = "(lvEntryLocalizationVersion.languageId IS NULL OR lvEntryLocalizationVersion.languageId = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_ENTRYID_LANGUAGEID_VERSION =
		new FinderPath(LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationVersionModelImpl.FINDER_CACHE_ENABLED,
			LVEntryLocalizationVersionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByEntryId_LanguageId_Version",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			LVEntryLocalizationVersionModelImpl.ENTRYID_COLUMN_BITMASK |
			LVEntryLocalizationVersionModelImpl.LANGUAGEID_COLUMN_BITMASK |
			LVEntryLocalizationVersionModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ENTRYID_LANGUAGEID_VERSION =
		new FinderPath(LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationVersionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByEntryId_LanguageId_Version",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns the lv entry localization version where entryId = &#63; and languageId = &#63; and version = &#63; or throws a {@link NoSuchLVEntryLocalizationVersionException} if it could not be found.
	 *
	 * @param entryId the entry ID
	 * @param languageId the language ID
	 * @param version the version
	 * @return the matching lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion findByEntryId_LanguageId_Version(
		long entryId, String languageId, int version)
		throws NoSuchLVEntryLocalizationVersionException {
		LVEntryLocalizationVersion lvEntryLocalizationVersion = fetchByEntryId_LanguageId_Version(entryId,
				languageId, version);

		if (lvEntryLocalizationVersion == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("entryId=");
			msg.append(entryId);

			msg.append(", languageId=");
			msg.append(languageId);

			msg.append(", version=");
			msg.append(version);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchLVEntryLocalizationVersionException(msg.toString());
		}

		return lvEntryLocalizationVersion;
	}

	/**
	 * Returns the lv entry localization version where entryId = &#63; and languageId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param entryId the entry ID
	 * @param languageId the language ID
	 * @param version the version
	 * @return the matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion fetchByEntryId_LanguageId_Version(
		long entryId, String languageId, int version) {
		return fetchByEntryId_LanguageId_Version(entryId, languageId, version,
			true);
	}

	/**
	 * Returns the lv entry localization version where entryId = &#63; and languageId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param entryId the entry ID
	 * @param languageId the language ID
	 * @param version the version
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion fetchByEntryId_LanguageId_Version(
		long entryId, String languageId, int version, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { entryId, languageId, version };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_ENTRYID_LANGUAGEID_VERSION,
					finderArgs, this);
		}

		if (result instanceof LVEntryLocalizationVersion) {
			LVEntryLocalizationVersion lvEntryLocalizationVersion = (LVEntryLocalizationVersion)result;

			if ((entryId != lvEntryLocalizationVersion.getEntryId()) ||
					!Objects.equals(languageId,
						lvEntryLocalizationVersion.getLanguageId()) ||
					(version != lvEntryLocalizationVersion.getVersion())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_LVENTRYLOCALIZATIONVERSION_WHERE);

			query.append(_FINDER_COLUMN_ENTRYID_LANGUAGEID_VERSION_ENTRYID_2);

			boolean bindLanguageId = false;

			if (languageId == null) {
				query.append(_FINDER_COLUMN_ENTRYID_LANGUAGEID_VERSION_LANGUAGEID_1);
			}
			else if (languageId.equals("")) {
				query.append(_FINDER_COLUMN_ENTRYID_LANGUAGEID_VERSION_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(_FINDER_COLUMN_ENTRYID_LANGUAGEID_VERSION_LANGUAGEID_2);
			}

			query.append(_FINDER_COLUMN_ENTRYID_LANGUAGEID_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId);

				if (bindLanguageId) {
					qPos.add(languageId);
				}

				qPos.add(version);

				List<LVEntryLocalizationVersion> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_ENTRYID_LANGUAGEID_VERSION,
						finderArgs, list);
				}
				else {
					LVEntryLocalizationVersion lvEntryLocalizationVersion = list.get(0);

					result = lvEntryLocalizationVersion;

					cacheResult(lvEntryLocalizationVersion);

					if ((lvEntryLocalizationVersion.getEntryId() != entryId) ||
							(lvEntryLocalizationVersion.getLanguageId() == null) ||
							!lvEntryLocalizationVersion.getLanguageId()
														   .equals(languageId) ||
							(lvEntryLocalizationVersion.getVersion() != version)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_ENTRYID_LANGUAGEID_VERSION,
							finderArgs, lvEntryLocalizationVersion);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_ENTRYID_LANGUAGEID_VERSION,
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
			return (LVEntryLocalizationVersion)result;
		}
	}

	/**
	 * Removes the lv entry localization version where entryId = &#63; and languageId = &#63; and version = &#63; from the database.
	 *
	 * @param entryId the entry ID
	 * @param languageId the language ID
	 * @param version the version
	 * @return the lv entry localization version that was removed
	 */
	@Override
	public LVEntryLocalizationVersion removeByEntryId_LanguageId_Version(
		long entryId, String languageId, int version)
		throws NoSuchLVEntryLocalizationVersionException {
		LVEntryLocalizationVersion lvEntryLocalizationVersion = findByEntryId_LanguageId_Version(entryId,
				languageId, version);

		return remove(lvEntryLocalizationVersion);
	}

	/**
	 * Returns the number of lv entry localization versions where entryId = &#63; and languageId = &#63; and version = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param languageId the language ID
	 * @param version the version
	 * @return the number of matching lv entry localization versions
	 */
	@Override
	public int countByEntryId_LanguageId_Version(long entryId,
		String languageId, int version) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_ENTRYID_LANGUAGEID_VERSION;

		Object[] finderArgs = new Object[] { entryId, languageId, version };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LVENTRYLOCALIZATIONVERSION_WHERE);

			query.append(_FINDER_COLUMN_ENTRYID_LANGUAGEID_VERSION_ENTRYID_2);

			boolean bindLanguageId = false;

			if (languageId == null) {
				query.append(_FINDER_COLUMN_ENTRYID_LANGUAGEID_VERSION_LANGUAGEID_1);
			}
			else if (languageId.equals("")) {
				query.append(_FINDER_COLUMN_ENTRYID_LANGUAGEID_VERSION_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(_FINDER_COLUMN_ENTRYID_LANGUAGEID_VERSION_LANGUAGEID_2);
			}

			query.append(_FINDER_COLUMN_ENTRYID_LANGUAGEID_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId);

				if (bindLanguageId) {
					qPos.add(languageId);
				}

				qPos.add(version);

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

	private static final String _FINDER_COLUMN_ENTRYID_LANGUAGEID_VERSION_ENTRYID_2 =
		"lvEntryLocalizationVersion.entryId = ? AND ";
	private static final String _FINDER_COLUMN_ENTRYID_LANGUAGEID_VERSION_LANGUAGEID_1 =
		"lvEntryLocalizationVersion.languageId IS NULL AND ";
	private static final String _FINDER_COLUMN_ENTRYID_LANGUAGEID_VERSION_LANGUAGEID_2 =
		"lvEntryLocalizationVersion.languageId = ? AND ";
	private static final String _FINDER_COLUMN_ENTRYID_LANGUAGEID_VERSION_LANGUAGEID_3 =
		"(lvEntryLocalizationVersion.languageId IS NULL OR lvEntryLocalizationVersion.languageId = '') AND ";
	private static final String _FINDER_COLUMN_ENTRYID_LANGUAGEID_VERSION_VERSION_2 =
		"lvEntryLocalizationVersion.version = ?";

	public LVEntryLocalizationVersionPersistenceImpl() {
		setModelClass(LVEntryLocalizationVersion.class);
	}

	/**
	 * Caches the lv entry localization version in the entity cache if it is enabled.
	 *
	 * @param lvEntryLocalizationVersion the lv entry localization version
	 */
	@Override
	public void cacheResult(
		LVEntryLocalizationVersion lvEntryLocalizationVersion) {
		entityCache.putResult(LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationVersionImpl.class,
			lvEntryLocalizationVersion.getPrimaryKey(),
			lvEntryLocalizationVersion);

		finderCache.putResult(FINDER_PATH_FETCH_BY_LVENTRYLOCALIZATIONID_VERSION,
			new Object[] {
				lvEntryLocalizationVersion.getLvEntryLocalizationId(),
				lvEntryLocalizationVersion.getVersion()
			}, lvEntryLocalizationVersion);

		finderCache.putResult(FINDER_PATH_FETCH_BY_ENTRYID_LANGUAGEID_VERSION,
			new Object[] {
				lvEntryLocalizationVersion.getEntryId(),
				lvEntryLocalizationVersion.getLanguageId(),
				lvEntryLocalizationVersion.getVersion()
			}, lvEntryLocalizationVersion);

		lvEntryLocalizationVersion.resetOriginalValues();
	}

	/**
	 * Caches the lv entry localization versions in the entity cache if it is enabled.
	 *
	 * @param lvEntryLocalizationVersions the lv entry localization versions
	 */
	@Override
	public void cacheResult(
		List<LVEntryLocalizationVersion> lvEntryLocalizationVersions) {
		for (LVEntryLocalizationVersion lvEntryLocalizationVersion : lvEntryLocalizationVersions) {
			if (entityCache.getResult(
						LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
						LVEntryLocalizationVersionImpl.class,
						lvEntryLocalizationVersion.getPrimaryKey()) == null) {
				cacheResult(lvEntryLocalizationVersion);
			}
			else {
				lvEntryLocalizationVersion.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all lv entry localization versions.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LVEntryLocalizationVersionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the lv entry localization version.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		LVEntryLocalizationVersion lvEntryLocalizationVersion) {
		entityCache.removeResult(LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationVersionImpl.class,
			lvEntryLocalizationVersion.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((LVEntryLocalizationVersionModelImpl)lvEntryLocalizationVersion,
			true);
	}

	@Override
	public void clearCache(
		List<LVEntryLocalizationVersion> lvEntryLocalizationVersions) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LVEntryLocalizationVersion lvEntryLocalizationVersion : lvEntryLocalizationVersions) {
			entityCache.removeResult(LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
				LVEntryLocalizationVersionImpl.class,
				lvEntryLocalizationVersion.getPrimaryKey());

			clearUniqueFindersCache((LVEntryLocalizationVersionModelImpl)lvEntryLocalizationVersion,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		LVEntryLocalizationVersionModelImpl lvEntryLocalizationVersionModelImpl) {
		Object[] args = new Object[] {
				lvEntryLocalizationVersionModelImpl.getLvEntryLocalizationId(),
				lvEntryLocalizationVersionModelImpl.getVersion()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_LVENTRYLOCALIZATIONID_VERSION,
			args, Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_LVENTRYLOCALIZATIONID_VERSION,
			args, lvEntryLocalizationVersionModelImpl, false);

		args = new Object[] {
				lvEntryLocalizationVersionModelImpl.getEntryId(),
				lvEntryLocalizationVersionModelImpl.getLanguageId(),
				lvEntryLocalizationVersionModelImpl.getVersion()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_ENTRYID_LANGUAGEID_VERSION,
			args, Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_ENTRYID_LANGUAGEID_VERSION,
			args, lvEntryLocalizationVersionModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		LVEntryLocalizationVersionModelImpl lvEntryLocalizationVersionModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					lvEntryLocalizationVersionModelImpl.getLvEntryLocalizationId(),
					lvEntryLocalizationVersionModelImpl.getVersion()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_LVENTRYLOCALIZATIONID_VERSION,
				args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_LVENTRYLOCALIZATIONID_VERSION,
				args);
		}

		if ((lvEntryLocalizationVersionModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_LVENTRYLOCALIZATIONID_VERSION.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					lvEntryLocalizationVersionModelImpl.getOriginalLvEntryLocalizationId(),
					lvEntryLocalizationVersionModelImpl.getOriginalVersion()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_LVENTRYLOCALIZATIONID_VERSION,
				args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_LVENTRYLOCALIZATIONID_VERSION,
				args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					lvEntryLocalizationVersionModelImpl.getEntryId(),
					lvEntryLocalizationVersionModelImpl.getLanguageId(),
					lvEntryLocalizationVersionModelImpl.getVersion()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_ENTRYID_LANGUAGEID_VERSION,
				args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_ENTRYID_LANGUAGEID_VERSION,
				args);
		}

		if ((lvEntryLocalizationVersionModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_ENTRYID_LANGUAGEID_VERSION.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					lvEntryLocalizationVersionModelImpl.getOriginalEntryId(),
					lvEntryLocalizationVersionModelImpl.getOriginalLanguageId(),
					lvEntryLocalizationVersionModelImpl.getOriginalVersion()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_ENTRYID_LANGUAGEID_VERSION,
				args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_ENTRYID_LANGUAGEID_VERSION,
				args);
		}
	}

	/**
	 * Creates a new lv entry localization version with the primary key. Does not add the lv entry localization version to the database.
	 *
	 * @param lvEntryLocalizationVersionId the primary key for the new lv entry localization version
	 * @return the new lv entry localization version
	 */
	@Override
	public LVEntryLocalizationVersion create(long lvEntryLocalizationVersionId) {
		LVEntryLocalizationVersion lvEntryLocalizationVersion = new LVEntryLocalizationVersionImpl();

		lvEntryLocalizationVersion.setNew(true);
		lvEntryLocalizationVersion.setPrimaryKey(lvEntryLocalizationVersionId);

		return lvEntryLocalizationVersion;
	}

	/**
	 * Removes the lv entry localization version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param lvEntryLocalizationVersionId the primary key of the lv entry localization version
	 * @return the lv entry localization version that was removed
	 * @throws NoSuchLVEntryLocalizationVersionException if a lv entry localization version with the primary key could not be found
	 */
	@Override
	public LVEntryLocalizationVersion remove(long lvEntryLocalizationVersionId)
		throws NoSuchLVEntryLocalizationVersionException {
		return remove((Serializable)lvEntryLocalizationVersionId);
	}

	/**
	 * Removes the lv entry localization version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the lv entry localization version
	 * @return the lv entry localization version that was removed
	 * @throws NoSuchLVEntryLocalizationVersionException if a lv entry localization version with the primary key could not be found
	 */
	@Override
	public LVEntryLocalizationVersion remove(Serializable primaryKey)
		throws NoSuchLVEntryLocalizationVersionException {
		Session session = null;

		try {
			session = openSession();

			LVEntryLocalizationVersion lvEntryLocalizationVersion = (LVEntryLocalizationVersion)session.get(LVEntryLocalizationVersionImpl.class,
					primaryKey);

			if (lvEntryLocalizationVersion == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLVEntryLocalizationVersionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(lvEntryLocalizationVersion);
		}
		catch (NoSuchLVEntryLocalizationVersionException nsee) {
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
	protected LVEntryLocalizationVersion removeImpl(
		LVEntryLocalizationVersion lvEntryLocalizationVersion) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(lvEntryLocalizationVersion)) {
				lvEntryLocalizationVersion = (LVEntryLocalizationVersion)session.get(LVEntryLocalizationVersionImpl.class,
						lvEntryLocalizationVersion.getPrimaryKeyObj());
			}

			if (lvEntryLocalizationVersion != null) {
				session.delete(lvEntryLocalizationVersion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (lvEntryLocalizationVersion != null) {
			clearCache(lvEntryLocalizationVersion);
		}

		return lvEntryLocalizationVersion;
	}

	@Override
	public LVEntryLocalizationVersion updateImpl(
		LVEntryLocalizationVersion lvEntryLocalizationVersion) {
		boolean isNew = lvEntryLocalizationVersion.isNew();

		if (!(lvEntryLocalizationVersion instanceof LVEntryLocalizationVersionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(lvEntryLocalizationVersion.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(lvEntryLocalizationVersion);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in lvEntryLocalizationVersion proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom LVEntryLocalizationVersion implementation " +
				lvEntryLocalizationVersion.getClass());
		}

		LVEntryLocalizationVersionModelImpl lvEntryLocalizationVersionModelImpl = (LVEntryLocalizationVersionModelImpl)lvEntryLocalizationVersion;

		Session session = null;

		try {
			session = openSession();

			if (lvEntryLocalizationVersion.isNew()) {
				session.save(lvEntryLocalizationVersion);

				lvEntryLocalizationVersion.setNew(false);
			}
			else {
				throw new IllegalArgumentException(
					"LVEntryLocalizationVersion is read only, create a new version instead");
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!LVEntryLocalizationVersionModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					lvEntryLocalizationVersionModelImpl.getLvEntryLocalizationId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_LVENTRYLOCALIZATIONID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LVENTRYLOCALIZATIONID,
				args);

			args = new Object[] { lvEntryLocalizationVersionModelImpl.getEntryId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_ENTRYID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ENTRYID,
				args);

			args = new Object[] {
					lvEntryLocalizationVersionModelImpl.getEntryId(),
					lvEntryLocalizationVersionModelImpl.getVersion()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_ENTRYID_VERSION, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ENTRYID_VERSION,
				args);

			args = new Object[] {
					lvEntryLocalizationVersionModelImpl.getEntryId(),
					lvEntryLocalizationVersionModelImpl.getLanguageId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_ENTRYID_LANGUAGEID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ENTRYID_LANGUAGEID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((lvEntryLocalizationVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LVENTRYLOCALIZATIONID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						lvEntryLocalizationVersionModelImpl.getOriginalLvEntryLocalizationId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_LVENTRYLOCALIZATIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LVENTRYLOCALIZATIONID,
					args);

				args = new Object[] {
						lvEntryLocalizationVersionModelImpl.getLvEntryLocalizationId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_LVENTRYLOCALIZATIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LVENTRYLOCALIZATIONID,
					args);
			}

			if ((lvEntryLocalizationVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ENTRYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						lvEntryLocalizationVersionModelImpl.getOriginalEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ENTRYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ENTRYID,
					args);

				args = new Object[] {
						lvEntryLocalizationVersionModelImpl.getEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ENTRYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ENTRYID,
					args);
			}

			if ((lvEntryLocalizationVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ENTRYID_VERSION.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						lvEntryLocalizationVersionModelImpl.getOriginalEntryId(),
						lvEntryLocalizationVersionModelImpl.getOriginalVersion()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ENTRYID_VERSION,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ENTRYID_VERSION,
					args);

				args = new Object[] {
						lvEntryLocalizationVersionModelImpl.getEntryId(),
						lvEntryLocalizationVersionModelImpl.getVersion()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ENTRYID_VERSION,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ENTRYID_VERSION,
					args);
			}

			if ((lvEntryLocalizationVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ENTRYID_LANGUAGEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						lvEntryLocalizationVersionModelImpl.getOriginalEntryId(),
						lvEntryLocalizationVersionModelImpl.getOriginalLanguageId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ENTRYID_LANGUAGEID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ENTRYID_LANGUAGEID,
					args);

				args = new Object[] {
						lvEntryLocalizationVersionModelImpl.getEntryId(),
						lvEntryLocalizationVersionModelImpl.getLanguageId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ENTRYID_LANGUAGEID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ENTRYID_LANGUAGEID,
					args);
			}
		}

		entityCache.putResult(LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationVersionImpl.class,
			lvEntryLocalizationVersion.getPrimaryKey(),
			lvEntryLocalizationVersion, false);

		clearUniqueFindersCache(lvEntryLocalizationVersionModelImpl, false);
		cacheUniqueFindersCache(lvEntryLocalizationVersionModelImpl);

		lvEntryLocalizationVersion.resetOriginalValues();

		return lvEntryLocalizationVersion;
	}

	/**
	 * Returns the lv entry localization version with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the lv entry localization version
	 * @return the lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a lv entry localization version with the primary key could not be found
	 */
	@Override
	public LVEntryLocalizationVersion findByPrimaryKey(Serializable primaryKey)
		throws NoSuchLVEntryLocalizationVersionException {
		LVEntryLocalizationVersion lvEntryLocalizationVersion = fetchByPrimaryKey(primaryKey);

		if (lvEntryLocalizationVersion == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchLVEntryLocalizationVersionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return lvEntryLocalizationVersion;
	}

	/**
	 * Returns the lv entry localization version with the primary key or throws a {@link NoSuchLVEntryLocalizationVersionException} if it could not be found.
	 *
	 * @param lvEntryLocalizationVersionId the primary key of the lv entry localization version
	 * @return the lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a lv entry localization version with the primary key could not be found
	 */
	@Override
	public LVEntryLocalizationVersion findByPrimaryKey(
		long lvEntryLocalizationVersionId)
		throws NoSuchLVEntryLocalizationVersionException {
		return findByPrimaryKey((Serializable)lvEntryLocalizationVersionId);
	}

	/**
	 * Returns the lv entry localization version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the lv entry localization version
	 * @return the lv entry localization version, or <code>null</code> if a lv entry localization version with the primary key could not be found
	 */
	@Override
	public LVEntryLocalizationVersion fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
				LVEntryLocalizationVersionImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		LVEntryLocalizationVersion lvEntryLocalizationVersion = (LVEntryLocalizationVersion)serializable;

		if (lvEntryLocalizationVersion == null) {
			Session session = null;

			try {
				session = openSession();

				lvEntryLocalizationVersion = (LVEntryLocalizationVersion)session.get(LVEntryLocalizationVersionImpl.class,
						primaryKey);

				if (lvEntryLocalizationVersion != null) {
					cacheResult(lvEntryLocalizationVersion);
				}
				else {
					entityCache.putResult(LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
						LVEntryLocalizationVersionImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
					LVEntryLocalizationVersionImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return lvEntryLocalizationVersion;
	}

	/**
	 * Returns the lv entry localization version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param lvEntryLocalizationVersionId the primary key of the lv entry localization version
	 * @return the lv entry localization version, or <code>null</code> if a lv entry localization version with the primary key could not be found
	 */
	@Override
	public LVEntryLocalizationVersion fetchByPrimaryKey(
		long lvEntryLocalizationVersionId) {
		return fetchByPrimaryKey((Serializable)lvEntryLocalizationVersionId);
	}

	@Override
	public Map<Serializable, LVEntryLocalizationVersion> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, LVEntryLocalizationVersion> map = new HashMap<Serializable, LVEntryLocalizationVersion>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			LVEntryLocalizationVersion lvEntryLocalizationVersion = fetchByPrimaryKey(primaryKey);

			if (lvEntryLocalizationVersion != null) {
				map.put(primaryKey, lvEntryLocalizationVersion);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
					LVEntryLocalizationVersionImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (LVEntryLocalizationVersion)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_LVENTRYLOCALIZATIONVERSION_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(",");
		}

		query.setIndex(query.index() - 1);

		query.append(")");

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (LVEntryLocalizationVersion lvEntryLocalizationVersion : (List<LVEntryLocalizationVersion>)q.list()) {
				map.put(lvEntryLocalizationVersion.getPrimaryKeyObj(),
					lvEntryLocalizationVersion);

				cacheResult(lvEntryLocalizationVersion);

				uncachedPrimaryKeys.remove(lvEntryLocalizationVersion.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
					LVEntryLocalizationVersionImpl.class, primaryKey, nullModel);
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
	 * Returns all the lv entry localization versions.
	 *
	 * @return the lv entry localization versions
	 */
	@Override
	public List<LVEntryLocalizationVersion> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the lv entry localization versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryLocalizationVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @return the range of lv entry localization versions
	 */
	@Override
	public List<LVEntryLocalizationVersion> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the lv entry localization versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryLocalizationVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of lv entry localization versions
	 */
	@Override
	public List<LVEntryLocalizationVersion> findAll(int start, int end,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the lv entry localization versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryLocalizationVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of lv entry localization versions
	 */
	@Override
	public List<LVEntryLocalizationVersion> findAll(int start, int end,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator,
		boolean retrieveFromCache) {
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

		List<LVEntryLocalizationVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LVEntryLocalizationVersion>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_LVENTRYLOCALIZATIONVERSION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LVENTRYLOCALIZATIONVERSION;

				if (pagination) {
					sql = sql.concat(LVEntryLocalizationVersionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<LVEntryLocalizationVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LVEntryLocalizationVersion>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Removes all the lv entry localization versions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LVEntryLocalizationVersion lvEntryLocalizationVersion : findAll()) {
			remove(lvEntryLocalizationVersion);
		}
	}

	/**
	 * Returns the number of lv entry localization versions.
	 *
	 * @return the number of lv entry localization versions
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_LVENTRYLOCALIZATIONVERSION);

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
	protected Map<String, Integer> getTableColumnsMap() {
		return LVEntryLocalizationVersionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the lv entry localization version persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(LVEntryLocalizationVersionImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_LVENTRYLOCALIZATIONVERSION = "SELECT lvEntryLocalizationVersion FROM LVEntryLocalizationVersion lvEntryLocalizationVersion";
	private static final String _SQL_SELECT_LVENTRYLOCALIZATIONVERSION_WHERE_PKS_IN =
		"SELECT lvEntryLocalizationVersion FROM LVEntryLocalizationVersion lvEntryLocalizationVersion WHERE lvEntryLocalizationVersionId IN (";
	private static final String _SQL_SELECT_LVENTRYLOCALIZATIONVERSION_WHERE = "SELECT lvEntryLocalizationVersion FROM LVEntryLocalizationVersion lvEntryLocalizationVersion WHERE ";
	private static final String _SQL_COUNT_LVENTRYLOCALIZATIONVERSION = "SELECT COUNT(lvEntryLocalizationVersion) FROM LVEntryLocalizationVersion lvEntryLocalizationVersion";
	private static final String _SQL_COUNT_LVENTRYLOCALIZATIONVERSION_WHERE = "SELECT COUNT(lvEntryLocalizationVersion) FROM LVEntryLocalizationVersion lvEntryLocalizationVersion WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "lvEntryLocalizationVersion.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No LVEntryLocalizationVersion exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No LVEntryLocalizationVersion exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(LVEntryLocalizationVersionPersistenceImpl.class);
}