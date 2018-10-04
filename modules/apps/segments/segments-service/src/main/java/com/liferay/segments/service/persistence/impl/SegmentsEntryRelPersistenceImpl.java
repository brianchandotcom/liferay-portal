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

package com.liferay.segments.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.petra.string.StringBundler;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import com.liferay.segments.exception.NoSuchEntryRelException;
import com.liferay.segments.model.SegmentsEntryRel;
import com.liferay.segments.model.impl.SegmentsEntryRelImpl;
import com.liferay.segments.model.impl.SegmentsEntryRelModelImpl;
import com.liferay.segments.service.persistence.SegmentsEntryRelPersistence;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the segments entry rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Eduardo Garcia
 * @see SegmentsEntryRelPersistence
 * @see com.liferay.segments.service.persistence.SegmentsEntryRelUtil
 * @generated
 */
@ProviderType
public class SegmentsEntryRelPersistenceImpl extends BasePersistenceImpl<SegmentsEntryRel>
	implements SegmentsEntryRelPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link SegmentsEntryRelUtil} to access the segments entry rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = SegmentsEntryRelImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(SegmentsEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			SegmentsEntryRelModelImpl.FINDER_CACHE_ENABLED,
			SegmentsEntryRelImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(SegmentsEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			SegmentsEntryRelModelImpl.FINDER_CACHE_ENABLED,
			SegmentsEntryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SegmentsEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			SegmentsEntryRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_SEGMENTSENTRYID =
		new FinderPath(SegmentsEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			SegmentsEntryRelModelImpl.FINDER_CACHE_ENABLED,
			SegmentsEntryRelImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findBySegmentsEntryId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SEGMENTSENTRYID =
		new FinderPath(SegmentsEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			SegmentsEntryRelModelImpl.FINDER_CACHE_ENABLED,
			SegmentsEntryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findBySegmentsEntryId",
			new String[] { Long.class.getName() },
			SegmentsEntryRelModelImpl.SEGMENTSENTRYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_SEGMENTSENTRYID = new FinderPath(SegmentsEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			SegmentsEntryRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countBySegmentsEntryId", new String[] { Long.class.getName() });

	/**
	 * Returns all the segments entry rels where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @return the matching segments entry rels
	 */
	@Override
	public List<SegmentsEntryRel> findBySegmentsEntryId(long segmentsEntryId) {
		return findBySegmentsEntryId(segmentsEntryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entry rels where segmentsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param start the lower bound of the range of segments entry rels
	 * @param end the upper bound of the range of segments entry rels (not inclusive)
	 * @return the range of matching segments entry rels
	 */
	@Override
	public List<SegmentsEntryRel> findBySegmentsEntryId(long segmentsEntryId,
		int start, int end) {
		return findBySegmentsEntryId(segmentsEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entry rels where segmentsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param start the lower bound of the range of segments entry rels
	 * @param end the upper bound of the range of segments entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entry rels
	 */
	@Override
	public List<SegmentsEntryRel> findBySegmentsEntryId(long segmentsEntryId,
		int start, int end,
		OrderByComparator<SegmentsEntryRel> orderByComparator) {
		return findBySegmentsEntryId(segmentsEntryId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments entry rels where segmentsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param start the lower bound of the range of segments entry rels
	 * @param end the upper bound of the range of segments entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching segments entry rels
	 */
	@Override
	public List<SegmentsEntryRel> findBySegmentsEntryId(long segmentsEntryId,
		int start, int end,
		OrderByComparator<SegmentsEntryRel> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SEGMENTSENTRYID;
			finderArgs = new Object[] { segmentsEntryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_SEGMENTSENTRYID;
			finderArgs = new Object[] {
					segmentsEntryId,
					
					start, end, orderByComparator
				};
		}

		List<SegmentsEntryRel> list = null;

		if (retrieveFromCache) {
			list = (List<SegmentsEntryRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsEntryRel segmentsEntryRel : list) {
					if ((segmentsEntryId != segmentsEntryRel.getSegmentsEntryId())) {
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

			query.append(_SQL_SELECT_SEGMENTSENTRYREL_WHERE);

			query.append(_FINDER_COLUMN_SEGMENTSENTRYID_SEGMENTSENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SegmentsEntryRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(segmentsEntryId);

				if (!pagination) {
					list = (List<SegmentsEntryRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SegmentsEntryRel>)QueryUtil.list(q,
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
	 * Returns the first segments entry rel in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry rel
	 * @throws NoSuchEntryRelException if a matching segments entry rel could not be found
	 */
	@Override
	public SegmentsEntryRel findBySegmentsEntryId_First(long segmentsEntryId,
		OrderByComparator<SegmentsEntryRel> orderByComparator)
		throws NoSuchEntryRelException {
		SegmentsEntryRel segmentsEntryRel = fetchBySegmentsEntryId_First(segmentsEntryId,
				orderByComparator);

		if (segmentsEntryRel != null) {
			return segmentsEntryRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("segmentsEntryId=");
		msg.append(segmentsEntryId);

		msg.append("}");

		throw new NoSuchEntryRelException(msg.toString());
	}

	/**
	 * Returns the first segments entry rel in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry rel, or <code>null</code> if a matching segments entry rel could not be found
	 */
	@Override
	public SegmentsEntryRel fetchBySegmentsEntryId_First(long segmentsEntryId,
		OrderByComparator<SegmentsEntryRel> orderByComparator) {
		List<SegmentsEntryRel> list = findBySegmentsEntryId(segmentsEntryId, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last segments entry rel in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry rel
	 * @throws NoSuchEntryRelException if a matching segments entry rel could not be found
	 */
	@Override
	public SegmentsEntryRel findBySegmentsEntryId_Last(long segmentsEntryId,
		OrderByComparator<SegmentsEntryRel> orderByComparator)
		throws NoSuchEntryRelException {
		SegmentsEntryRel segmentsEntryRel = fetchBySegmentsEntryId_Last(segmentsEntryId,
				orderByComparator);

		if (segmentsEntryRel != null) {
			return segmentsEntryRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("segmentsEntryId=");
		msg.append(segmentsEntryId);

		msg.append("}");

		throw new NoSuchEntryRelException(msg.toString());
	}

	/**
	 * Returns the last segments entry rel in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry rel, or <code>null</code> if a matching segments entry rel could not be found
	 */
	@Override
	public SegmentsEntryRel fetchBySegmentsEntryId_Last(long segmentsEntryId,
		OrderByComparator<SegmentsEntryRel> orderByComparator) {
		int count = countBySegmentsEntryId(segmentsEntryId);

		if (count == 0) {
			return null;
		}

		List<SegmentsEntryRel> list = findBySegmentsEntryId(segmentsEntryId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the segments entry rels before and after the current segments entry rel in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryRelId the primary key of the current segments entry rel
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments entry rel
	 * @throws NoSuchEntryRelException if a segments entry rel with the primary key could not be found
	 */
	@Override
	public SegmentsEntryRel[] findBySegmentsEntryId_PrevAndNext(
		long segmentsEntryRelId, long segmentsEntryId,
		OrderByComparator<SegmentsEntryRel> orderByComparator)
		throws NoSuchEntryRelException {
		SegmentsEntryRel segmentsEntryRel = findByPrimaryKey(segmentsEntryRelId);

		Session session = null;

		try {
			session = openSession();

			SegmentsEntryRel[] array = new SegmentsEntryRelImpl[3];

			array[0] = getBySegmentsEntryId_PrevAndNext(session,
					segmentsEntryRel, segmentsEntryId, orderByComparator, true);

			array[1] = segmentsEntryRel;

			array[2] = getBySegmentsEntryId_PrevAndNext(session,
					segmentsEntryRel, segmentsEntryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SegmentsEntryRel getBySegmentsEntryId_PrevAndNext(
		Session session, SegmentsEntryRel segmentsEntryRel,
		long segmentsEntryId,
		OrderByComparator<SegmentsEntryRel> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SEGMENTSENTRYREL_WHERE);

		query.append(_FINDER_COLUMN_SEGMENTSENTRYID_SEGMENTSENTRYID_2);

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
			query.append(SegmentsEntryRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(segmentsEntryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(segmentsEntryRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SegmentsEntryRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the segments entry rels where segmentsEntryId = &#63; from the database.
	 *
	 * @param segmentsEntryId the segments entry ID
	 */
	@Override
	public void removeBySegmentsEntryId(long segmentsEntryId) {
		for (SegmentsEntryRel segmentsEntryRel : findBySegmentsEntryId(
				segmentsEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(segmentsEntryRel);
		}
	}

	/**
	 * Returns the number of segments entry rels where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @return the number of matching segments entry rels
	 */
	@Override
	public int countBySegmentsEntryId(long segmentsEntryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_SEGMENTSENTRYID;

		Object[] finderArgs = new Object[] { segmentsEntryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SEGMENTSENTRYREL_WHERE);

			query.append(_FINDER_COLUMN_SEGMENTSENTRYID_SEGMENTSENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(segmentsEntryId);

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

	private static final String _FINDER_COLUMN_SEGMENTSENTRYID_SEGMENTSENTRYID_2 =
		"segmentsEntryRel.segmentsEntryId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_CN_CPK = new FinderPath(SegmentsEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			SegmentsEntryRelModelImpl.FINDER_CACHE_ENABLED,
			SegmentsEntryRelImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCN_CPK",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CN_CPK =
		new FinderPath(SegmentsEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			SegmentsEntryRelModelImpl.FINDER_CACHE_ENABLED,
			SegmentsEntryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCN_CPK",
			new String[] { Long.class.getName(), Long.class.getName() },
			SegmentsEntryRelModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SegmentsEntryRelModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CN_CPK = new FinderPath(SegmentsEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			SegmentsEntryRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCN_CPK",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the segments entry rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching segments entry rels
	 */
	@Override
	public List<SegmentsEntryRel> findByCN_CPK(long classNameId, long classPK) {
		return findByCN_CPK(classNameId, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entry rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of segments entry rels
	 * @param end the upper bound of the range of segments entry rels (not inclusive)
	 * @return the range of matching segments entry rels
	 */
	@Override
	public List<SegmentsEntryRel> findByCN_CPK(long classNameId, long classPK,
		int start, int end) {
		return findByCN_CPK(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entry rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of segments entry rels
	 * @param end the upper bound of the range of segments entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entry rels
	 */
	@Override
	public List<SegmentsEntryRel> findByCN_CPK(long classNameId, long classPK,
		int start, int end,
		OrderByComparator<SegmentsEntryRel> orderByComparator) {
		return findByCN_CPK(classNameId, classPK, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments entry rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of segments entry rels
	 * @param end the upper bound of the range of segments entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching segments entry rels
	 */
	@Override
	public List<SegmentsEntryRel> findByCN_CPK(long classNameId, long classPK,
		int start, int end,
		OrderByComparator<SegmentsEntryRel> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CN_CPK;
			finderArgs = new Object[] { classNameId, classPK };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_CN_CPK;
			finderArgs = new Object[] {
					classNameId, classPK,
					
					start, end, orderByComparator
				};
		}

		List<SegmentsEntryRel> list = null;

		if (retrieveFromCache) {
			list = (List<SegmentsEntryRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsEntryRel segmentsEntryRel : list) {
					if ((classNameId != segmentsEntryRel.getClassNameId()) ||
							(classPK != segmentsEntryRel.getClassPK())) {
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

			query.append(_SQL_SELECT_SEGMENTSENTRYREL_WHERE);

			query.append(_FINDER_COLUMN_CN_CPK_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_CN_CPK_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SegmentsEntryRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (!pagination) {
					list = (List<SegmentsEntryRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SegmentsEntryRel>)QueryUtil.list(q,
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
	 * Returns the first segments entry rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry rel
	 * @throws NoSuchEntryRelException if a matching segments entry rel could not be found
	 */
	@Override
	public SegmentsEntryRel findByCN_CPK_First(long classNameId, long classPK,
		OrderByComparator<SegmentsEntryRel> orderByComparator)
		throws NoSuchEntryRelException {
		SegmentsEntryRel segmentsEntryRel = fetchByCN_CPK_First(classNameId,
				classPK, orderByComparator);

		if (segmentsEntryRel != null) {
			return segmentsEntryRel;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append("}");

		throw new NoSuchEntryRelException(msg.toString());
	}

	/**
	 * Returns the first segments entry rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry rel, or <code>null</code> if a matching segments entry rel could not be found
	 */
	@Override
	public SegmentsEntryRel fetchByCN_CPK_First(long classNameId, long classPK,
		OrderByComparator<SegmentsEntryRel> orderByComparator) {
		List<SegmentsEntryRel> list = findByCN_CPK(classNameId, classPK, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last segments entry rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry rel
	 * @throws NoSuchEntryRelException if a matching segments entry rel could not be found
	 */
	@Override
	public SegmentsEntryRel findByCN_CPK_Last(long classNameId, long classPK,
		OrderByComparator<SegmentsEntryRel> orderByComparator)
		throws NoSuchEntryRelException {
		SegmentsEntryRel segmentsEntryRel = fetchByCN_CPK_Last(classNameId,
				classPK, orderByComparator);

		if (segmentsEntryRel != null) {
			return segmentsEntryRel;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append("}");

		throw new NoSuchEntryRelException(msg.toString());
	}

	/**
	 * Returns the last segments entry rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry rel, or <code>null</code> if a matching segments entry rel could not be found
	 */
	@Override
	public SegmentsEntryRel fetchByCN_CPK_Last(long classNameId, long classPK,
		OrderByComparator<SegmentsEntryRel> orderByComparator) {
		int count = countByCN_CPK(classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<SegmentsEntryRel> list = findByCN_CPK(classNameId, classPK,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the segments entry rels before and after the current segments entry rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param segmentsEntryRelId the primary key of the current segments entry rel
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments entry rel
	 * @throws NoSuchEntryRelException if a segments entry rel with the primary key could not be found
	 */
	@Override
	public SegmentsEntryRel[] findByCN_CPK_PrevAndNext(
		long segmentsEntryRelId, long classNameId, long classPK,
		OrderByComparator<SegmentsEntryRel> orderByComparator)
		throws NoSuchEntryRelException {
		SegmentsEntryRel segmentsEntryRel = findByPrimaryKey(segmentsEntryRelId);

		Session session = null;

		try {
			session = openSession();

			SegmentsEntryRel[] array = new SegmentsEntryRelImpl[3];

			array[0] = getByCN_CPK_PrevAndNext(session, segmentsEntryRel,
					classNameId, classPK, orderByComparator, true);

			array[1] = segmentsEntryRel;

			array[2] = getByCN_CPK_PrevAndNext(session, segmentsEntryRel,
					classNameId, classPK, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SegmentsEntryRel getByCN_CPK_PrevAndNext(Session session,
		SegmentsEntryRel segmentsEntryRel, long classNameId, long classPK,
		OrderByComparator<SegmentsEntryRel> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_SEGMENTSENTRYREL_WHERE);

		query.append(_FINDER_COLUMN_CN_CPK_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_CN_CPK_CLASSPK_2);

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
			query.append(SegmentsEntryRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(segmentsEntryRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SegmentsEntryRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the segments entry rels where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByCN_CPK(long classNameId, long classPK) {
		for (SegmentsEntryRel segmentsEntryRel : findByCN_CPK(classNameId,
				classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(segmentsEntryRel);
		}
	}

	/**
	 * Returns the number of segments entry rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching segments entry rels
	 */
	@Override
	public int countByCN_CPK(long classNameId, long classPK) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_CN_CPK;

		Object[] finderArgs = new Object[] { classNameId, classPK };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SEGMENTSENTRYREL_WHERE);

			query.append(_FINDER_COLUMN_CN_CPK_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_CN_CPK_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

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

	private static final String _FINDER_COLUMN_CN_CPK_CLASSNAMEID_2 = "segmentsEntryRel.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_CN_CPK_CLASSPK_2 = "segmentsEntryRel.classPK = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_CN_CPK = new FinderPath(SegmentsEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			SegmentsEntryRelModelImpl.FINDER_CACHE_ENABLED,
			SegmentsEntryRelImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_CN_CPK",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_CN_CPK =
		new FinderPath(SegmentsEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			SegmentsEntryRelModelImpl.FINDER_CACHE_ENABLED,
			SegmentsEntryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_CN_CPK",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			SegmentsEntryRelModelImpl.GROUPID_COLUMN_BITMASK |
			SegmentsEntryRelModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SegmentsEntryRelModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_CN_CPK = new FinderPath(SegmentsEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			SegmentsEntryRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_CN_CPK",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

	/**
	 * Returns all the segments entry rels where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching segments entry rels
	 */
	@Override
	public List<SegmentsEntryRel> findByG_CN_CPK(long groupId,
		long classNameId, long classPK) {
		return findByG_CN_CPK(groupId, classNameId, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entry rels where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of segments entry rels
	 * @param end the upper bound of the range of segments entry rels (not inclusive)
	 * @return the range of matching segments entry rels
	 */
	@Override
	public List<SegmentsEntryRel> findByG_CN_CPK(long groupId,
		long classNameId, long classPK, int start, int end) {
		return findByG_CN_CPK(groupId, classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entry rels where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of segments entry rels
	 * @param end the upper bound of the range of segments entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entry rels
	 */
	@Override
	public List<SegmentsEntryRel> findByG_CN_CPK(long groupId,
		long classNameId, long classPK, int start, int end,
		OrderByComparator<SegmentsEntryRel> orderByComparator) {
		return findByG_CN_CPK(groupId, classNameId, classPK, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments entry rels where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of segments entry rels
	 * @param end the upper bound of the range of segments entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching segments entry rels
	 */
	@Override
	public List<SegmentsEntryRel> findByG_CN_CPK(long groupId,
		long classNameId, long classPK, int start, int end,
		OrderByComparator<SegmentsEntryRel> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_CN_CPK;
			finderArgs = new Object[] { groupId, classNameId, classPK };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_CN_CPK;
			finderArgs = new Object[] {
					groupId, classNameId, classPK,
					
					start, end, orderByComparator
				};
		}

		List<SegmentsEntryRel> list = null;

		if (retrieveFromCache) {
			list = (List<SegmentsEntryRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsEntryRel segmentsEntryRel : list) {
					if ((groupId != segmentsEntryRel.getGroupId()) ||
							(classNameId != segmentsEntryRel.getClassNameId()) ||
							(classPK != segmentsEntryRel.getClassPK())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_SEGMENTSENTRYREL_WHERE);

			query.append(_FINDER_COLUMN_G_CN_CPK_GROUPID_2);

			query.append(_FINDER_COLUMN_G_CN_CPK_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_CN_CPK_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SegmentsEntryRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (!pagination) {
					list = (List<SegmentsEntryRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SegmentsEntryRel>)QueryUtil.list(q,
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
	 * Returns the first segments entry rel in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry rel
	 * @throws NoSuchEntryRelException if a matching segments entry rel could not be found
	 */
	@Override
	public SegmentsEntryRel findByG_CN_CPK_First(long groupId,
		long classNameId, long classPK,
		OrderByComparator<SegmentsEntryRel> orderByComparator)
		throws NoSuchEntryRelException {
		SegmentsEntryRel segmentsEntryRel = fetchByG_CN_CPK_First(groupId,
				classNameId, classPK, orderByComparator);

		if (segmentsEntryRel != null) {
			return segmentsEntryRel;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append("}");

		throw new NoSuchEntryRelException(msg.toString());
	}

	/**
	 * Returns the first segments entry rel in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry rel, or <code>null</code> if a matching segments entry rel could not be found
	 */
	@Override
	public SegmentsEntryRel fetchByG_CN_CPK_First(long groupId,
		long classNameId, long classPK,
		OrderByComparator<SegmentsEntryRel> orderByComparator) {
		List<SegmentsEntryRel> list = findByG_CN_CPK(groupId, classNameId,
				classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last segments entry rel in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry rel
	 * @throws NoSuchEntryRelException if a matching segments entry rel could not be found
	 */
	@Override
	public SegmentsEntryRel findByG_CN_CPK_Last(long groupId, long classNameId,
		long classPK, OrderByComparator<SegmentsEntryRel> orderByComparator)
		throws NoSuchEntryRelException {
		SegmentsEntryRel segmentsEntryRel = fetchByG_CN_CPK_Last(groupId,
				classNameId, classPK, orderByComparator);

		if (segmentsEntryRel != null) {
			return segmentsEntryRel;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append("}");

		throw new NoSuchEntryRelException(msg.toString());
	}

	/**
	 * Returns the last segments entry rel in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry rel, or <code>null</code> if a matching segments entry rel could not be found
	 */
	@Override
	public SegmentsEntryRel fetchByG_CN_CPK_Last(long groupId,
		long classNameId, long classPK,
		OrderByComparator<SegmentsEntryRel> orderByComparator) {
		int count = countByG_CN_CPK(groupId, classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<SegmentsEntryRel> list = findByG_CN_CPK(groupId, classNameId,
				classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the segments entry rels before and after the current segments entry rel in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param segmentsEntryRelId the primary key of the current segments entry rel
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments entry rel
	 * @throws NoSuchEntryRelException if a segments entry rel with the primary key could not be found
	 */
	@Override
	public SegmentsEntryRel[] findByG_CN_CPK_PrevAndNext(
		long segmentsEntryRelId, long groupId, long classNameId, long classPK,
		OrderByComparator<SegmentsEntryRel> orderByComparator)
		throws NoSuchEntryRelException {
		SegmentsEntryRel segmentsEntryRel = findByPrimaryKey(segmentsEntryRelId);

		Session session = null;

		try {
			session = openSession();

			SegmentsEntryRel[] array = new SegmentsEntryRelImpl[3];

			array[0] = getByG_CN_CPK_PrevAndNext(session, segmentsEntryRel,
					groupId, classNameId, classPK, orderByComparator, true);

			array[1] = segmentsEntryRel;

			array[2] = getByG_CN_CPK_PrevAndNext(session, segmentsEntryRel,
					groupId, classNameId, classPK, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SegmentsEntryRel getByG_CN_CPK_PrevAndNext(Session session,
		SegmentsEntryRel segmentsEntryRel, long groupId, long classNameId,
		long classPK, OrderByComparator<SegmentsEntryRel> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_SEGMENTSENTRYREL_WHERE);

		query.append(_FINDER_COLUMN_G_CN_CPK_GROUPID_2);

		query.append(_FINDER_COLUMN_G_CN_CPK_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_CN_CPK_CLASSPK_2);

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
			query.append(SegmentsEntryRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(segmentsEntryRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SegmentsEntryRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the segments entry rels where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByG_CN_CPK(long groupId, long classNameId, long classPK) {
		for (SegmentsEntryRel segmentsEntryRel : findByG_CN_CPK(groupId,
				classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(segmentsEntryRel);
		}
	}

	/**
	 * Returns the number of segments entry rels where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching segments entry rels
	 */
	@Override
	public int countByG_CN_CPK(long groupId, long classNameId, long classPK) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_CN_CPK;

		Object[] finderArgs = new Object[] { groupId, classNameId, classPK };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_SEGMENTSENTRYREL_WHERE);

			query.append(_FINDER_COLUMN_G_CN_CPK_GROUPID_2);

			query.append(_FINDER_COLUMN_G_CN_CPK_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_CN_CPK_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

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

	private static final String _FINDER_COLUMN_G_CN_CPK_GROUPID_2 = "segmentsEntryRel.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_CN_CPK_CLASSNAMEID_2 = "segmentsEntryRel.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_CN_CPK_CLASSPK_2 = "segmentsEntryRel.classPK = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_S_CN_CPK = new FinderPath(SegmentsEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			SegmentsEntryRelModelImpl.FINDER_CACHE_ENABLED,
			SegmentsEntryRelImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByS_CN_CPK",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			SegmentsEntryRelModelImpl.SEGMENTSENTRYID_COLUMN_BITMASK |
			SegmentsEntryRelModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SegmentsEntryRelModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_S_CN_CPK = new FinderPath(SegmentsEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			SegmentsEntryRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByS_CN_CPK",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

	/**
	 * Returns the segments entry rel where segmentsEntryId = &#63; and classNameId = &#63; and classPK = &#63; or throws a {@link NoSuchEntryRelException} if it could not be found.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching segments entry rel
	 * @throws NoSuchEntryRelException if a matching segments entry rel could not be found
	 */
	@Override
	public SegmentsEntryRel findByS_CN_CPK(long segmentsEntryId,
		long classNameId, long classPK) throws NoSuchEntryRelException {
		SegmentsEntryRel segmentsEntryRel = fetchByS_CN_CPK(segmentsEntryId,
				classNameId, classPK);

		if (segmentsEntryRel == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("segmentsEntryId=");
			msg.append(segmentsEntryId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchEntryRelException(msg.toString());
		}

		return segmentsEntryRel;
	}

	/**
	 * Returns the segments entry rel where segmentsEntryId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching segments entry rel, or <code>null</code> if a matching segments entry rel could not be found
	 */
	@Override
	public SegmentsEntryRel fetchByS_CN_CPK(long segmentsEntryId,
		long classNameId, long classPK) {
		return fetchByS_CN_CPK(segmentsEntryId, classNameId, classPK, true);
	}

	/**
	 * Returns the segments entry rel where segmentsEntryId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching segments entry rel, or <code>null</code> if a matching segments entry rel could not be found
	 */
	@Override
	public SegmentsEntryRel fetchByS_CN_CPK(long segmentsEntryId,
		long classNameId, long classPK, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { segmentsEntryId, classNameId, classPK };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_S_CN_CPK,
					finderArgs, this);
		}

		if (result instanceof SegmentsEntryRel) {
			SegmentsEntryRel segmentsEntryRel = (SegmentsEntryRel)result;

			if ((segmentsEntryId != segmentsEntryRel.getSegmentsEntryId()) ||
					(classNameId != segmentsEntryRel.getClassNameId()) ||
					(classPK != segmentsEntryRel.getClassPK())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_SEGMENTSENTRYREL_WHERE);

			query.append(_FINDER_COLUMN_S_CN_CPK_SEGMENTSENTRYID_2);

			query.append(_FINDER_COLUMN_S_CN_CPK_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_S_CN_CPK_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(segmentsEntryId);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<SegmentsEntryRel> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_S_CN_CPK,
						finderArgs, list);
				}
				else {
					SegmentsEntryRel segmentsEntryRel = list.get(0);

					result = segmentsEntryRel;

					cacheResult(segmentsEntryRel);
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_S_CN_CPK,
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
			return (SegmentsEntryRel)result;
		}
	}

	/**
	 * Removes the segments entry rel where segmentsEntryId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the segments entry rel that was removed
	 */
	@Override
	public SegmentsEntryRel removeByS_CN_CPK(long segmentsEntryId,
		long classNameId, long classPK) throws NoSuchEntryRelException {
		SegmentsEntryRel segmentsEntryRel = findByS_CN_CPK(segmentsEntryId,
				classNameId, classPK);

		return remove(segmentsEntryRel);
	}

	/**
	 * Returns the number of segments entry rels where segmentsEntryId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching segments entry rels
	 */
	@Override
	public int countByS_CN_CPK(long segmentsEntryId, long classNameId,
		long classPK) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_S_CN_CPK;

		Object[] finderArgs = new Object[] { segmentsEntryId, classNameId, classPK };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_SEGMENTSENTRYREL_WHERE);

			query.append(_FINDER_COLUMN_S_CN_CPK_SEGMENTSENTRYID_2);

			query.append(_FINDER_COLUMN_S_CN_CPK_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_S_CN_CPK_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(segmentsEntryId);

				qPos.add(classNameId);

				qPos.add(classPK);

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

	private static final String _FINDER_COLUMN_S_CN_CPK_SEGMENTSENTRYID_2 = "segmentsEntryRel.segmentsEntryId = ? AND ";
	private static final String _FINDER_COLUMN_S_CN_CPK_CLASSNAMEID_2 = "segmentsEntryRel.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_S_CN_CPK_CLASSPK_2 = "segmentsEntryRel.classPK = ?";

	public SegmentsEntryRelPersistenceImpl() {
		setModelClass(SegmentsEntryRel.class);
	}

	/**
	 * Caches the segments entry rel in the entity cache if it is enabled.
	 *
	 * @param segmentsEntryRel the segments entry rel
	 */
	@Override
	public void cacheResult(SegmentsEntryRel segmentsEntryRel) {
		entityCache.putResult(SegmentsEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			SegmentsEntryRelImpl.class, segmentsEntryRel.getPrimaryKey(),
			segmentsEntryRel);

		finderCache.putResult(FINDER_PATH_FETCH_BY_S_CN_CPK,
			new Object[] {
				segmentsEntryRel.getSegmentsEntryId(),
				segmentsEntryRel.getClassNameId(), segmentsEntryRel.getClassPK()
			}, segmentsEntryRel);

		segmentsEntryRel.resetOriginalValues();
	}

	/**
	 * Caches the segments entry rels in the entity cache if it is enabled.
	 *
	 * @param segmentsEntryRels the segments entry rels
	 */
	@Override
	public void cacheResult(List<SegmentsEntryRel> segmentsEntryRels) {
		for (SegmentsEntryRel segmentsEntryRel : segmentsEntryRels) {
			if (entityCache.getResult(
						SegmentsEntryRelModelImpl.ENTITY_CACHE_ENABLED,
						SegmentsEntryRelImpl.class,
						segmentsEntryRel.getPrimaryKey()) == null) {
				cacheResult(segmentsEntryRel);
			}
			else {
				segmentsEntryRel.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all segments entry rels.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(SegmentsEntryRelImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the segments entry rel.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SegmentsEntryRel segmentsEntryRel) {
		entityCache.removeResult(SegmentsEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			SegmentsEntryRelImpl.class, segmentsEntryRel.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((SegmentsEntryRelModelImpl)segmentsEntryRel,
			true);
	}

	@Override
	public void clearCache(List<SegmentsEntryRel> segmentsEntryRels) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SegmentsEntryRel segmentsEntryRel : segmentsEntryRels) {
			entityCache.removeResult(SegmentsEntryRelModelImpl.ENTITY_CACHE_ENABLED,
				SegmentsEntryRelImpl.class, segmentsEntryRel.getPrimaryKey());

			clearUniqueFindersCache((SegmentsEntryRelModelImpl)segmentsEntryRel,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		SegmentsEntryRelModelImpl segmentsEntryRelModelImpl) {
		Object[] args = new Object[] {
				segmentsEntryRelModelImpl.getSegmentsEntryId(),
				segmentsEntryRelModelImpl.getClassNameId(),
				segmentsEntryRelModelImpl.getClassPK()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_S_CN_CPK, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_S_CN_CPK, args,
			segmentsEntryRelModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		SegmentsEntryRelModelImpl segmentsEntryRelModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					segmentsEntryRelModelImpl.getSegmentsEntryId(),
					segmentsEntryRelModelImpl.getClassNameId(),
					segmentsEntryRelModelImpl.getClassPK()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_S_CN_CPK, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_S_CN_CPK, args);
		}

		if ((segmentsEntryRelModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_S_CN_CPK.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					segmentsEntryRelModelImpl.getOriginalSegmentsEntryId(),
					segmentsEntryRelModelImpl.getOriginalClassNameId(),
					segmentsEntryRelModelImpl.getOriginalClassPK()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_S_CN_CPK, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_S_CN_CPK, args);
		}
	}

	/**
	 * Creates a new segments entry rel with the primary key. Does not add the segments entry rel to the database.
	 *
	 * @param segmentsEntryRelId the primary key for the new segments entry rel
	 * @return the new segments entry rel
	 */
	@Override
	public SegmentsEntryRel create(long segmentsEntryRelId) {
		SegmentsEntryRel segmentsEntryRel = new SegmentsEntryRelImpl();

		segmentsEntryRel.setNew(true);
		segmentsEntryRel.setPrimaryKey(segmentsEntryRelId);

		segmentsEntryRel.setCompanyId(companyProvider.getCompanyId());

		return segmentsEntryRel;
	}

	/**
	 * Removes the segments entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param segmentsEntryRelId the primary key of the segments entry rel
	 * @return the segments entry rel that was removed
	 * @throws NoSuchEntryRelException if a segments entry rel with the primary key could not be found
	 */
	@Override
	public SegmentsEntryRel remove(long segmentsEntryRelId)
		throws NoSuchEntryRelException {
		return remove((Serializable)segmentsEntryRelId);
	}

	/**
	 * Removes the segments entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the segments entry rel
	 * @return the segments entry rel that was removed
	 * @throws NoSuchEntryRelException if a segments entry rel with the primary key could not be found
	 */
	@Override
	public SegmentsEntryRel remove(Serializable primaryKey)
		throws NoSuchEntryRelException {
		Session session = null;

		try {
			session = openSession();

			SegmentsEntryRel segmentsEntryRel = (SegmentsEntryRel)session.get(SegmentsEntryRelImpl.class,
					primaryKey);

			if (segmentsEntryRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(segmentsEntryRel);
		}
		catch (NoSuchEntryRelException nsee) {
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
	protected SegmentsEntryRel removeImpl(SegmentsEntryRel segmentsEntryRel) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(segmentsEntryRel)) {
				segmentsEntryRel = (SegmentsEntryRel)session.get(SegmentsEntryRelImpl.class,
						segmentsEntryRel.getPrimaryKeyObj());
			}

			if (segmentsEntryRel != null) {
				session.delete(segmentsEntryRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (segmentsEntryRel != null) {
			clearCache(segmentsEntryRel);
		}

		return segmentsEntryRel;
	}

	@Override
	public SegmentsEntryRel updateImpl(SegmentsEntryRel segmentsEntryRel) {
		boolean isNew = segmentsEntryRel.isNew();

		if (!(segmentsEntryRel instanceof SegmentsEntryRelModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(segmentsEntryRel.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(segmentsEntryRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in segmentsEntryRel proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom SegmentsEntryRel implementation " +
				segmentsEntryRel.getClass());
		}

		SegmentsEntryRelModelImpl segmentsEntryRelModelImpl = (SegmentsEntryRelModelImpl)segmentsEntryRel;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (segmentsEntryRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				segmentsEntryRel.setCreateDate(now);
			}
			else {
				segmentsEntryRel.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!segmentsEntryRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				segmentsEntryRel.setModifiedDate(now);
			}
			else {
				segmentsEntryRel.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (segmentsEntryRel.isNew()) {
				session.save(segmentsEntryRel);

				segmentsEntryRel.setNew(false);
			}
			else {
				segmentsEntryRel = (SegmentsEntryRel)session.merge(segmentsEntryRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!SegmentsEntryRelModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					segmentsEntryRelModelImpl.getSegmentsEntryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_SEGMENTSENTRYID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SEGMENTSENTRYID,
				args);

			args = new Object[] {
					segmentsEntryRelModelImpl.getClassNameId(),
					segmentsEntryRelModelImpl.getClassPK()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_CN_CPK, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CN_CPK,
				args);

			args = new Object[] {
					segmentsEntryRelModelImpl.getGroupId(),
					segmentsEntryRelModelImpl.getClassNameId(),
					segmentsEntryRelModelImpl.getClassPK()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_CN_CPK, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_CN_CPK,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((segmentsEntryRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SEGMENTSENTRYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						segmentsEntryRelModelImpl.getOriginalSegmentsEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_SEGMENTSENTRYID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SEGMENTSENTRYID,
					args);

				args = new Object[] {
						segmentsEntryRelModelImpl.getSegmentsEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_SEGMENTSENTRYID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SEGMENTSENTRYID,
					args);
			}

			if ((segmentsEntryRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CN_CPK.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						segmentsEntryRelModelImpl.getOriginalClassNameId(),
						segmentsEntryRelModelImpl.getOriginalClassPK()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CN_CPK, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CN_CPK,
					args);

				args = new Object[] {
						segmentsEntryRelModelImpl.getClassNameId(),
						segmentsEntryRelModelImpl.getClassPK()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CN_CPK, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CN_CPK,
					args);
			}

			if ((segmentsEntryRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_CN_CPK.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						segmentsEntryRelModelImpl.getOriginalGroupId(),
						segmentsEntryRelModelImpl.getOriginalClassNameId(),
						segmentsEntryRelModelImpl.getOriginalClassPK()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_CN_CPK, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_CN_CPK,
					args);

				args = new Object[] {
						segmentsEntryRelModelImpl.getGroupId(),
						segmentsEntryRelModelImpl.getClassNameId(),
						segmentsEntryRelModelImpl.getClassPK()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_CN_CPK, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_CN_CPK,
					args);
			}
		}

		entityCache.putResult(SegmentsEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			SegmentsEntryRelImpl.class, segmentsEntryRel.getPrimaryKey(),
			segmentsEntryRel, false);

		clearUniqueFindersCache(segmentsEntryRelModelImpl, false);
		cacheUniqueFindersCache(segmentsEntryRelModelImpl);

		segmentsEntryRel.resetOriginalValues();

		return segmentsEntryRel;
	}

	/**
	 * Returns the segments entry rel with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the segments entry rel
	 * @return the segments entry rel
	 * @throws NoSuchEntryRelException if a segments entry rel with the primary key could not be found
	 */
	@Override
	public SegmentsEntryRel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryRelException {
		SegmentsEntryRel segmentsEntryRel = fetchByPrimaryKey(primaryKey);

		if (segmentsEntryRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return segmentsEntryRel;
	}

	/**
	 * Returns the segments entry rel with the primary key or throws a {@link NoSuchEntryRelException} if it could not be found.
	 *
	 * @param segmentsEntryRelId the primary key of the segments entry rel
	 * @return the segments entry rel
	 * @throws NoSuchEntryRelException if a segments entry rel with the primary key could not be found
	 */
	@Override
	public SegmentsEntryRel findByPrimaryKey(long segmentsEntryRelId)
		throws NoSuchEntryRelException {
		return findByPrimaryKey((Serializable)segmentsEntryRelId);
	}

	/**
	 * Returns the segments entry rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the segments entry rel
	 * @return the segments entry rel, or <code>null</code> if a segments entry rel with the primary key could not be found
	 */
	@Override
	public SegmentsEntryRel fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(SegmentsEntryRelModelImpl.ENTITY_CACHE_ENABLED,
				SegmentsEntryRelImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		SegmentsEntryRel segmentsEntryRel = (SegmentsEntryRel)serializable;

		if (segmentsEntryRel == null) {
			Session session = null;

			try {
				session = openSession();

				segmentsEntryRel = (SegmentsEntryRel)session.get(SegmentsEntryRelImpl.class,
						primaryKey);

				if (segmentsEntryRel != null) {
					cacheResult(segmentsEntryRel);
				}
				else {
					entityCache.putResult(SegmentsEntryRelModelImpl.ENTITY_CACHE_ENABLED,
						SegmentsEntryRelImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(SegmentsEntryRelModelImpl.ENTITY_CACHE_ENABLED,
					SegmentsEntryRelImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return segmentsEntryRel;
	}

	/**
	 * Returns the segments entry rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param segmentsEntryRelId the primary key of the segments entry rel
	 * @return the segments entry rel, or <code>null</code> if a segments entry rel with the primary key could not be found
	 */
	@Override
	public SegmentsEntryRel fetchByPrimaryKey(long segmentsEntryRelId) {
		return fetchByPrimaryKey((Serializable)segmentsEntryRelId);
	}

	@Override
	public Map<Serializable, SegmentsEntryRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, SegmentsEntryRel> map = new HashMap<Serializable, SegmentsEntryRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			SegmentsEntryRel segmentsEntryRel = fetchByPrimaryKey(primaryKey);

			if (segmentsEntryRel != null) {
				map.put(primaryKey, segmentsEntryRel);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(SegmentsEntryRelModelImpl.ENTITY_CACHE_ENABLED,
					SegmentsEntryRelImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (SegmentsEntryRel)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_SEGMENTSENTRYREL_WHERE_PKS_IN);

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

			for (SegmentsEntryRel segmentsEntryRel : (List<SegmentsEntryRel>)q.list()) {
				map.put(segmentsEntryRel.getPrimaryKeyObj(), segmentsEntryRel);

				cacheResult(segmentsEntryRel);

				uncachedPrimaryKeys.remove(segmentsEntryRel.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(SegmentsEntryRelModelImpl.ENTITY_CACHE_ENABLED,
					SegmentsEntryRelImpl.class, primaryKey, nullModel);
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
	 * Returns all the segments entry rels.
	 *
	 * @return the segments entry rels
	 */
	@Override
	public List<SegmentsEntryRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of segments entry rels
	 * @param end the upper bound of the range of segments entry rels (not inclusive)
	 * @return the range of segments entry rels
	 */
	@Override
	public List<SegmentsEntryRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of segments entry rels
	 * @param end the upper bound of the range of segments entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of segments entry rels
	 */
	@Override
	public List<SegmentsEntryRel> findAll(int start, int end,
		OrderByComparator<SegmentsEntryRel> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SegmentsEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of segments entry rels
	 * @param end the upper bound of the range of segments entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of segments entry rels
	 */
	@Override
	public List<SegmentsEntryRel> findAll(int start, int end,
		OrderByComparator<SegmentsEntryRel> orderByComparator,
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

		List<SegmentsEntryRel> list = null;

		if (retrieveFromCache) {
			list = (List<SegmentsEntryRel>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_SEGMENTSENTRYREL);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SEGMENTSENTRYREL;

				if (pagination) {
					sql = sql.concat(SegmentsEntryRelModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<SegmentsEntryRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SegmentsEntryRel>)QueryUtil.list(q,
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
	 * Removes all the segments entry rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SegmentsEntryRel segmentsEntryRel : findAll()) {
			remove(segmentsEntryRel);
		}
	}

	/**
	 * Returns the number of segments entry rels.
	 *
	 * @return the number of segments entry rels
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SEGMENTSENTRYREL);

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
		return SegmentsEntryRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the segments entry rel persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(SegmentsEntryRelImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_SEGMENTSENTRYREL = "SELECT segmentsEntryRel FROM SegmentsEntryRel segmentsEntryRel";
	private static final String _SQL_SELECT_SEGMENTSENTRYREL_WHERE_PKS_IN = "SELECT segmentsEntryRel FROM SegmentsEntryRel segmentsEntryRel WHERE segmentsEntryRelId IN (";
	private static final String _SQL_SELECT_SEGMENTSENTRYREL_WHERE = "SELECT segmentsEntryRel FROM SegmentsEntryRel segmentsEntryRel WHERE ";
	private static final String _SQL_COUNT_SEGMENTSENTRYREL = "SELECT COUNT(segmentsEntryRel) FROM SegmentsEntryRel segmentsEntryRel";
	private static final String _SQL_COUNT_SEGMENTSENTRYREL_WHERE = "SELECT COUNT(segmentsEntryRel) FROM SegmentsEntryRel segmentsEntryRel WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "segmentsEntryRel.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SegmentsEntryRel exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SegmentsEntryRel exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(SegmentsEntryRelPersistenceImpl.class);
}