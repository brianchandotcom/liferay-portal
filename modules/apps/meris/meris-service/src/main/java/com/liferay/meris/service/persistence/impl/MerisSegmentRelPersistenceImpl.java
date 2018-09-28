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

package com.liferay.meris.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.meris.exception.NoSuchSegmentRelException;
import com.liferay.meris.model.MerisSegmentRel;
import com.liferay.meris.model.impl.MerisSegmentRelImpl;
import com.liferay.meris.model.impl.MerisSegmentRelModelImpl;
import com.liferay.meris.service.persistence.MerisSegmentRelPersistence;

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
 * The persistence implementation for the meris segment rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Eduardo Garcia
 * @see MerisSegmentRelPersistence
 * @see com.liferay.meris.service.persistence.MerisSegmentRelUtil
 * @generated
 */
@ProviderType
public class MerisSegmentRelPersistenceImpl extends BasePersistenceImpl<MerisSegmentRel>
	implements MerisSegmentRelPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link MerisSegmentRelUtil} to access the meris segment rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = MerisSegmentRelImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(MerisSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			MerisSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			MerisSegmentRelImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(MerisSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			MerisSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			MerisSegmentRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(MerisSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			MerisSegmentRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_MERISSEGMENTID =
		new FinderPath(MerisSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			MerisSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			MerisSegmentRelImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByMerisSegmentId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_MERISSEGMENTID =
		new FinderPath(MerisSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			MerisSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			MerisSegmentRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByMerisSegmentId",
			new String[] { Long.class.getName() },
			MerisSegmentRelModelImpl.MERISSEGMENTID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_MERISSEGMENTID = new FinderPath(MerisSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			MerisSegmentRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByMerisSegmentId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the meris segment rels where merisSegmentId = &#63;.
	 *
	 * @param merisSegmentId the meris segment ID
	 * @return the matching meris segment rels
	 */
	@Override
	public List<MerisSegmentRel> findByMerisSegmentId(long merisSegmentId) {
		return findByMerisSegmentId(merisSegmentId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the meris segment rels where merisSegmentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param merisSegmentId the meris segment ID
	 * @param start the lower bound of the range of meris segment rels
	 * @param end the upper bound of the range of meris segment rels (not inclusive)
	 * @return the range of matching meris segment rels
	 */
	@Override
	public List<MerisSegmentRel> findByMerisSegmentId(long merisSegmentId,
		int start, int end) {
		return findByMerisSegmentId(merisSegmentId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the meris segment rels where merisSegmentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param merisSegmentId the meris segment ID
	 * @param start the lower bound of the range of meris segment rels
	 * @param end the upper bound of the range of meris segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching meris segment rels
	 */
	@Override
	public List<MerisSegmentRel> findByMerisSegmentId(long merisSegmentId,
		int start, int end, OrderByComparator<MerisSegmentRel> orderByComparator) {
		return findByMerisSegmentId(merisSegmentId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the meris segment rels where merisSegmentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param merisSegmentId the meris segment ID
	 * @param start the lower bound of the range of meris segment rels
	 * @param end the upper bound of the range of meris segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching meris segment rels
	 */
	@Override
	public List<MerisSegmentRel> findByMerisSegmentId(long merisSegmentId,
		int start, int end,
		OrderByComparator<MerisSegmentRel> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_MERISSEGMENTID;
			finderArgs = new Object[] { merisSegmentId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_MERISSEGMENTID;
			finderArgs = new Object[] {
					merisSegmentId,
					
					start, end, orderByComparator
				};
		}

		List<MerisSegmentRel> list = null;

		if (retrieveFromCache) {
			list = (List<MerisSegmentRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MerisSegmentRel merisSegmentRel : list) {
					if ((merisSegmentId != merisSegmentRel.getMerisSegmentId())) {
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

			query.append(_SQL_SELECT_MERISSEGMENTREL_WHERE);

			query.append(_FINDER_COLUMN_MERISSEGMENTID_MERISSEGMENTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(MerisSegmentRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(merisSegmentId);

				if (!pagination) {
					list = (List<MerisSegmentRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MerisSegmentRel>)QueryUtil.list(q,
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
	 * Returns the first meris segment rel in the ordered set where merisSegmentId = &#63;.
	 *
	 * @param merisSegmentId the meris segment ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching meris segment rel
	 * @throws NoSuchSegmentRelException if a matching meris segment rel could not be found
	 */
	@Override
	public MerisSegmentRel findByMerisSegmentId_First(long merisSegmentId,
		OrderByComparator<MerisSegmentRel> orderByComparator)
		throws NoSuchSegmentRelException {
		MerisSegmentRel merisSegmentRel = fetchByMerisSegmentId_First(merisSegmentId,
				orderByComparator);

		if (merisSegmentRel != null) {
			return merisSegmentRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("merisSegmentId=");
		msg.append(merisSegmentId);

		msg.append("}");

		throw new NoSuchSegmentRelException(msg.toString());
	}

	/**
	 * Returns the first meris segment rel in the ordered set where merisSegmentId = &#63;.
	 *
	 * @param merisSegmentId the meris segment ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching meris segment rel, or <code>null</code> if a matching meris segment rel could not be found
	 */
	@Override
	public MerisSegmentRel fetchByMerisSegmentId_First(long merisSegmentId,
		OrderByComparator<MerisSegmentRel> orderByComparator) {
		List<MerisSegmentRel> list = findByMerisSegmentId(merisSegmentId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last meris segment rel in the ordered set where merisSegmentId = &#63;.
	 *
	 * @param merisSegmentId the meris segment ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching meris segment rel
	 * @throws NoSuchSegmentRelException if a matching meris segment rel could not be found
	 */
	@Override
	public MerisSegmentRel findByMerisSegmentId_Last(long merisSegmentId,
		OrderByComparator<MerisSegmentRel> orderByComparator)
		throws NoSuchSegmentRelException {
		MerisSegmentRel merisSegmentRel = fetchByMerisSegmentId_Last(merisSegmentId,
				orderByComparator);

		if (merisSegmentRel != null) {
			return merisSegmentRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("merisSegmentId=");
		msg.append(merisSegmentId);

		msg.append("}");

		throw new NoSuchSegmentRelException(msg.toString());
	}

	/**
	 * Returns the last meris segment rel in the ordered set where merisSegmentId = &#63;.
	 *
	 * @param merisSegmentId the meris segment ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching meris segment rel, or <code>null</code> if a matching meris segment rel could not be found
	 */
	@Override
	public MerisSegmentRel fetchByMerisSegmentId_Last(long merisSegmentId,
		OrderByComparator<MerisSegmentRel> orderByComparator) {
		int count = countByMerisSegmentId(merisSegmentId);

		if (count == 0) {
			return null;
		}

		List<MerisSegmentRel> list = findByMerisSegmentId(merisSegmentId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the meris segment rels before and after the current meris segment rel in the ordered set where merisSegmentId = &#63;.
	 *
	 * @param merisSegmentRelId the primary key of the current meris segment rel
	 * @param merisSegmentId the meris segment ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next meris segment rel
	 * @throws NoSuchSegmentRelException if a meris segment rel with the primary key could not be found
	 */
	@Override
	public MerisSegmentRel[] findByMerisSegmentId_PrevAndNext(
		long merisSegmentRelId, long merisSegmentId,
		OrderByComparator<MerisSegmentRel> orderByComparator)
		throws NoSuchSegmentRelException {
		MerisSegmentRel merisSegmentRel = findByPrimaryKey(merisSegmentRelId);

		Session session = null;

		try {
			session = openSession();

			MerisSegmentRel[] array = new MerisSegmentRelImpl[3];

			array[0] = getByMerisSegmentId_PrevAndNext(session,
					merisSegmentRel, merisSegmentId, orderByComparator, true);

			array[1] = merisSegmentRel;

			array[2] = getByMerisSegmentId_PrevAndNext(session,
					merisSegmentRel, merisSegmentId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MerisSegmentRel getByMerisSegmentId_PrevAndNext(Session session,
		MerisSegmentRel merisSegmentRel, long merisSegmentId,
		OrderByComparator<MerisSegmentRel> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MERISSEGMENTREL_WHERE);

		query.append(_FINDER_COLUMN_MERISSEGMENTID_MERISSEGMENTID_2);

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
			query.append(MerisSegmentRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(merisSegmentId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(merisSegmentRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MerisSegmentRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the meris segment rels where merisSegmentId = &#63; from the database.
	 *
	 * @param merisSegmentId the meris segment ID
	 */
	@Override
	public void removeByMerisSegmentId(long merisSegmentId) {
		for (MerisSegmentRel merisSegmentRel : findByMerisSegmentId(
				merisSegmentId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(merisSegmentRel);
		}
	}

	/**
	 * Returns the number of meris segment rels where merisSegmentId = &#63;.
	 *
	 * @param merisSegmentId the meris segment ID
	 * @return the number of matching meris segment rels
	 */
	@Override
	public int countByMerisSegmentId(long merisSegmentId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_MERISSEGMENTID;

		Object[] finderArgs = new Object[] { merisSegmentId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MERISSEGMENTREL_WHERE);

			query.append(_FINDER_COLUMN_MERISSEGMENTID_MERISSEGMENTID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(merisSegmentId);

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

	private static final String _FINDER_COLUMN_MERISSEGMENTID_MERISSEGMENTID_2 = "merisSegmentRel.merisSegmentId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_CN_CPK = new FinderPath(MerisSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			MerisSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			MerisSegmentRelImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCN_CPK",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CN_CPK =
		new FinderPath(MerisSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			MerisSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			MerisSegmentRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCN_CPK",
			new String[] { Long.class.getName(), Long.class.getName() },
			MerisSegmentRelModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			MerisSegmentRelModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CN_CPK = new FinderPath(MerisSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			MerisSegmentRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCN_CPK",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the meris segment rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching meris segment rels
	 */
	@Override
	public List<MerisSegmentRel> findByCN_CPK(long classNameId, long classPK) {
		return findByCN_CPK(classNameId, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the meris segment rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of meris segment rels
	 * @param end the upper bound of the range of meris segment rels (not inclusive)
	 * @return the range of matching meris segment rels
	 */
	@Override
	public List<MerisSegmentRel> findByCN_CPK(long classNameId, long classPK,
		int start, int end) {
		return findByCN_CPK(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the meris segment rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of meris segment rels
	 * @param end the upper bound of the range of meris segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching meris segment rels
	 */
	@Override
	public List<MerisSegmentRel> findByCN_CPK(long classNameId, long classPK,
		int start, int end, OrderByComparator<MerisSegmentRel> orderByComparator) {
		return findByCN_CPK(classNameId, classPK, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the meris segment rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of meris segment rels
	 * @param end the upper bound of the range of meris segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching meris segment rels
	 */
	@Override
	public List<MerisSegmentRel> findByCN_CPK(long classNameId, long classPK,
		int start, int end,
		OrderByComparator<MerisSegmentRel> orderByComparator,
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

		List<MerisSegmentRel> list = null;

		if (retrieveFromCache) {
			list = (List<MerisSegmentRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MerisSegmentRel merisSegmentRel : list) {
					if ((classNameId != merisSegmentRel.getClassNameId()) ||
							(classPK != merisSegmentRel.getClassPK())) {
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

			query.append(_SQL_SELECT_MERISSEGMENTREL_WHERE);

			query.append(_FINDER_COLUMN_CN_CPK_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_CN_CPK_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(MerisSegmentRelModelImpl.ORDER_BY_JPQL);
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
					list = (List<MerisSegmentRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MerisSegmentRel>)QueryUtil.list(q,
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
	 * Returns the first meris segment rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching meris segment rel
	 * @throws NoSuchSegmentRelException if a matching meris segment rel could not be found
	 */
	@Override
	public MerisSegmentRel findByCN_CPK_First(long classNameId, long classPK,
		OrderByComparator<MerisSegmentRel> orderByComparator)
		throws NoSuchSegmentRelException {
		MerisSegmentRel merisSegmentRel = fetchByCN_CPK_First(classNameId,
				classPK, orderByComparator);

		if (merisSegmentRel != null) {
			return merisSegmentRel;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append("}");

		throw new NoSuchSegmentRelException(msg.toString());
	}

	/**
	 * Returns the first meris segment rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching meris segment rel, or <code>null</code> if a matching meris segment rel could not be found
	 */
	@Override
	public MerisSegmentRel fetchByCN_CPK_First(long classNameId, long classPK,
		OrderByComparator<MerisSegmentRel> orderByComparator) {
		List<MerisSegmentRel> list = findByCN_CPK(classNameId, classPK, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last meris segment rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching meris segment rel
	 * @throws NoSuchSegmentRelException if a matching meris segment rel could not be found
	 */
	@Override
	public MerisSegmentRel findByCN_CPK_Last(long classNameId, long classPK,
		OrderByComparator<MerisSegmentRel> orderByComparator)
		throws NoSuchSegmentRelException {
		MerisSegmentRel merisSegmentRel = fetchByCN_CPK_Last(classNameId,
				classPK, orderByComparator);

		if (merisSegmentRel != null) {
			return merisSegmentRel;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append("}");

		throw new NoSuchSegmentRelException(msg.toString());
	}

	/**
	 * Returns the last meris segment rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching meris segment rel, or <code>null</code> if a matching meris segment rel could not be found
	 */
	@Override
	public MerisSegmentRel fetchByCN_CPK_Last(long classNameId, long classPK,
		OrderByComparator<MerisSegmentRel> orderByComparator) {
		int count = countByCN_CPK(classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<MerisSegmentRel> list = findByCN_CPK(classNameId, classPK,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the meris segment rels before and after the current meris segment rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param merisSegmentRelId the primary key of the current meris segment rel
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next meris segment rel
	 * @throws NoSuchSegmentRelException if a meris segment rel with the primary key could not be found
	 */
	@Override
	public MerisSegmentRel[] findByCN_CPK_PrevAndNext(long merisSegmentRelId,
		long classNameId, long classPK,
		OrderByComparator<MerisSegmentRel> orderByComparator)
		throws NoSuchSegmentRelException {
		MerisSegmentRel merisSegmentRel = findByPrimaryKey(merisSegmentRelId);

		Session session = null;

		try {
			session = openSession();

			MerisSegmentRel[] array = new MerisSegmentRelImpl[3];

			array[0] = getByCN_CPK_PrevAndNext(session, merisSegmentRel,
					classNameId, classPK, orderByComparator, true);

			array[1] = merisSegmentRel;

			array[2] = getByCN_CPK_PrevAndNext(session, merisSegmentRel,
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

	protected MerisSegmentRel getByCN_CPK_PrevAndNext(Session session,
		MerisSegmentRel merisSegmentRel, long classNameId, long classPK,
		OrderByComparator<MerisSegmentRel> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_MERISSEGMENTREL_WHERE);

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
			query.append(MerisSegmentRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(merisSegmentRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MerisSegmentRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the meris segment rels where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByCN_CPK(long classNameId, long classPK) {
		for (MerisSegmentRel merisSegmentRel : findByCN_CPK(classNameId,
				classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(merisSegmentRel);
		}
	}

	/**
	 * Returns the number of meris segment rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching meris segment rels
	 */
	@Override
	public int countByCN_CPK(long classNameId, long classPK) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_CN_CPK;

		Object[] finderArgs = new Object[] { classNameId, classPK };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MERISSEGMENTREL_WHERE);

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

	private static final String _FINDER_COLUMN_CN_CPK_CLASSNAMEID_2 = "merisSegmentRel.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_CN_CPK_CLASSPK_2 = "merisSegmentRel.classPK = ?";

	public MerisSegmentRelPersistenceImpl() {
		setModelClass(MerisSegmentRel.class);
	}

	/**
	 * Caches the meris segment rel in the entity cache if it is enabled.
	 *
	 * @param merisSegmentRel the meris segment rel
	 */
	@Override
	public void cacheResult(MerisSegmentRel merisSegmentRel) {
		entityCache.putResult(MerisSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			MerisSegmentRelImpl.class, merisSegmentRel.getPrimaryKey(),
			merisSegmentRel);

		merisSegmentRel.resetOriginalValues();
	}

	/**
	 * Caches the meris segment rels in the entity cache if it is enabled.
	 *
	 * @param merisSegmentRels the meris segment rels
	 */
	@Override
	public void cacheResult(List<MerisSegmentRel> merisSegmentRels) {
		for (MerisSegmentRel merisSegmentRel : merisSegmentRels) {
			if (entityCache.getResult(
						MerisSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
						MerisSegmentRelImpl.class,
						merisSegmentRel.getPrimaryKey()) == null) {
				cacheResult(merisSegmentRel);
			}
			else {
				merisSegmentRel.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all meris segment rels.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(MerisSegmentRelImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the meris segment rel.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(MerisSegmentRel merisSegmentRel) {
		entityCache.removeResult(MerisSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			MerisSegmentRelImpl.class, merisSegmentRel.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<MerisSegmentRel> merisSegmentRels) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (MerisSegmentRel merisSegmentRel : merisSegmentRels) {
			entityCache.removeResult(MerisSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
				MerisSegmentRelImpl.class, merisSegmentRel.getPrimaryKey());
		}
	}

	/**
	 * Creates a new meris segment rel with the primary key. Does not add the meris segment rel to the database.
	 *
	 * @param merisSegmentRelId the primary key for the new meris segment rel
	 * @return the new meris segment rel
	 */
	@Override
	public MerisSegmentRel create(long merisSegmentRelId) {
		MerisSegmentRel merisSegmentRel = new MerisSegmentRelImpl();

		merisSegmentRel.setNew(true);
		merisSegmentRel.setPrimaryKey(merisSegmentRelId);

		merisSegmentRel.setCompanyId(companyProvider.getCompanyId());

		return merisSegmentRel;
	}

	/**
	 * Removes the meris segment rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param merisSegmentRelId the primary key of the meris segment rel
	 * @return the meris segment rel that was removed
	 * @throws NoSuchSegmentRelException if a meris segment rel with the primary key could not be found
	 */
	@Override
	public MerisSegmentRel remove(long merisSegmentRelId)
		throws NoSuchSegmentRelException {
		return remove((Serializable)merisSegmentRelId);
	}

	/**
	 * Removes the meris segment rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the meris segment rel
	 * @return the meris segment rel that was removed
	 * @throws NoSuchSegmentRelException if a meris segment rel with the primary key could not be found
	 */
	@Override
	public MerisSegmentRel remove(Serializable primaryKey)
		throws NoSuchSegmentRelException {
		Session session = null;

		try {
			session = openSession();

			MerisSegmentRel merisSegmentRel = (MerisSegmentRel)session.get(MerisSegmentRelImpl.class,
					primaryKey);

			if (merisSegmentRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchSegmentRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(merisSegmentRel);
		}
		catch (NoSuchSegmentRelException nsee) {
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
	protected MerisSegmentRel removeImpl(MerisSegmentRel merisSegmentRel) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(merisSegmentRel)) {
				merisSegmentRel = (MerisSegmentRel)session.get(MerisSegmentRelImpl.class,
						merisSegmentRel.getPrimaryKeyObj());
			}

			if (merisSegmentRel != null) {
				session.delete(merisSegmentRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (merisSegmentRel != null) {
			clearCache(merisSegmentRel);
		}

		return merisSegmentRel;
	}

	@Override
	public MerisSegmentRel updateImpl(MerisSegmentRel merisSegmentRel) {
		boolean isNew = merisSegmentRel.isNew();

		if (!(merisSegmentRel instanceof MerisSegmentRelModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(merisSegmentRel.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(merisSegmentRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in merisSegmentRel proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom MerisSegmentRel implementation " +
				merisSegmentRel.getClass());
		}

		MerisSegmentRelModelImpl merisSegmentRelModelImpl = (MerisSegmentRelModelImpl)merisSegmentRel;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (merisSegmentRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				merisSegmentRel.setCreateDate(now);
			}
			else {
				merisSegmentRel.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!merisSegmentRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				merisSegmentRel.setModifiedDate(now);
			}
			else {
				merisSegmentRel.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (merisSegmentRel.isNew()) {
				session.save(merisSegmentRel);

				merisSegmentRel.setNew(false);
			}
			else {
				merisSegmentRel = (MerisSegmentRel)session.merge(merisSegmentRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!MerisSegmentRelModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					merisSegmentRelModelImpl.getMerisSegmentId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_MERISSEGMENTID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_MERISSEGMENTID,
				args);

			args = new Object[] {
					merisSegmentRelModelImpl.getClassNameId(),
					merisSegmentRelModelImpl.getClassPK()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_CN_CPK, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CN_CPK,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((merisSegmentRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_MERISSEGMENTID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						merisSegmentRelModelImpl.getOriginalMerisSegmentId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_MERISSEGMENTID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_MERISSEGMENTID,
					args);

				args = new Object[] { merisSegmentRelModelImpl.getMerisSegmentId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_MERISSEGMENTID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_MERISSEGMENTID,
					args);
			}

			if ((merisSegmentRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CN_CPK.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						merisSegmentRelModelImpl.getOriginalClassNameId(),
						merisSegmentRelModelImpl.getOriginalClassPK()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CN_CPK, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CN_CPK,
					args);

				args = new Object[] {
						merisSegmentRelModelImpl.getClassNameId(),
						merisSegmentRelModelImpl.getClassPK()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CN_CPK, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CN_CPK,
					args);
			}
		}

		entityCache.putResult(MerisSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			MerisSegmentRelImpl.class, merisSegmentRel.getPrimaryKey(),
			merisSegmentRel, false);

		merisSegmentRel.resetOriginalValues();

		return merisSegmentRel;
	}

	/**
	 * Returns the meris segment rel with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the meris segment rel
	 * @return the meris segment rel
	 * @throws NoSuchSegmentRelException if a meris segment rel with the primary key could not be found
	 */
	@Override
	public MerisSegmentRel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchSegmentRelException {
		MerisSegmentRel merisSegmentRel = fetchByPrimaryKey(primaryKey);

		if (merisSegmentRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchSegmentRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return merisSegmentRel;
	}

	/**
	 * Returns the meris segment rel with the primary key or throws a {@link NoSuchSegmentRelException} if it could not be found.
	 *
	 * @param merisSegmentRelId the primary key of the meris segment rel
	 * @return the meris segment rel
	 * @throws NoSuchSegmentRelException if a meris segment rel with the primary key could not be found
	 */
	@Override
	public MerisSegmentRel findByPrimaryKey(long merisSegmentRelId)
		throws NoSuchSegmentRelException {
		return findByPrimaryKey((Serializable)merisSegmentRelId);
	}

	/**
	 * Returns the meris segment rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the meris segment rel
	 * @return the meris segment rel, or <code>null</code> if a meris segment rel with the primary key could not be found
	 */
	@Override
	public MerisSegmentRel fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(MerisSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
				MerisSegmentRelImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		MerisSegmentRel merisSegmentRel = (MerisSegmentRel)serializable;

		if (merisSegmentRel == null) {
			Session session = null;

			try {
				session = openSession();

				merisSegmentRel = (MerisSegmentRel)session.get(MerisSegmentRelImpl.class,
						primaryKey);

				if (merisSegmentRel != null) {
					cacheResult(merisSegmentRel);
				}
				else {
					entityCache.putResult(MerisSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
						MerisSegmentRelImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(MerisSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
					MerisSegmentRelImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return merisSegmentRel;
	}

	/**
	 * Returns the meris segment rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param merisSegmentRelId the primary key of the meris segment rel
	 * @return the meris segment rel, or <code>null</code> if a meris segment rel with the primary key could not be found
	 */
	@Override
	public MerisSegmentRel fetchByPrimaryKey(long merisSegmentRelId) {
		return fetchByPrimaryKey((Serializable)merisSegmentRelId);
	}

	@Override
	public Map<Serializable, MerisSegmentRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, MerisSegmentRel> map = new HashMap<Serializable, MerisSegmentRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			MerisSegmentRel merisSegmentRel = fetchByPrimaryKey(primaryKey);

			if (merisSegmentRel != null) {
				map.put(primaryKey, merisSegmentRel);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(MerisSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
					MerisSegmentRelImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (MerisSegmentRel)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_MERISSEGMENTREL_WHERE_PKS_IN);

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

			for (MerisSegmentRel merisSegmentRel : (List<MerisSegmentRel>)q.list()) {
				map.put(merisSegmentRel.getPrimaryKeyObj(), merisSegmentRel);

				cacheResult(merisSegmentRel);

				uncachedPrimaryKeys.remove(merisSegmentRel.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(MerisSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
					MerisSegmentRelImpl.class, primaryKey, nullModel);
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
	 * Returns all the meris segment rels.
	 *
	 * @return the meris segment rels
	 */
	@Override
	public List<MerisSegmentRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the meris segment rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of meris segment rels
	 * @param end the upper bound of the range of meris segment rels (not inclusive)
	 * @return the range of meris segment rels
	 */
	@Override
	public List<MerisSegmentRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the meris segment rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of meris segment rels
	 * @param end the upper bound of the range of meris segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of meris segment rels
	 */
	@Override
	public List<MerisSegmentRel> findAll(int start, int end,
		OrderByComparator<MerisSegmentRel> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the meris segment rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of meris segment rels
	 * @param end the upper bound of the range of meris segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of meris segment rels
	 */
	@Override
	public List<MerisSegmentRel> findAll(int start, int end,
		OrderByComparator<MerisSegmentRel> orderByComparator,
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

		List<MerisSegmentRel> list = null;

		if (retrieveFromCache) {
			list = (List<MerisSegmentRel>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_MERISSEGMENTREL);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_MERISSEGMENTREL;

				if (pagination) {
					sql = sql.concat(MerisSegmentRelModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<MerisSegmentRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MerisSegmentRel>)QueryUtil.list(q,
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
	 * Removes all the meris segment rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (MerisSegmentRel merisSegmentRel : findAll()) {
			remove(merisSegmentRel);
		}
	}

	/**
	 * Returns the number of meris segment rels.
	 *
	 * @return the number of meris segment rels
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_MERISSEGMENTREL);

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
		return MerisSegmentRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the meris segment rel persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(MerisSegmentRelImpl.class.getName());
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
	private static final String _SQL_SELECT_MERISSEGMENTREL = "SELECT merisSegmentRel FROM MerisSegmentRel merisSegmentRel";
	private static final String _SQL_SELECT_MERISSEGMENTREL_WHERE_PKS_IN = "SELECT merisSegmentRel FROM MerisSegmentRel merisSegmentRel WHERE merisSegmentRelId IN (";
	private static final String _SQL_SELECT_MERISSEGMENTREL_WHERE = "SELECT merisSegmentRel FROM MerisSegmentRel merisSegmentRel WHERE ";
	private static final String _SQL_COUNT_MERISSEGMENTREL = "SELECT COUNT(merisSegmentRel) FROM MerisSegmentRel merisSegmentRel";
	private static final String _SQL_COUNT_MERISSEGMENTREL_WHERE = "SELECT COUNT(merisSegmentRel) FROM MerisSegmentRel merisSegmentRel WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "merisSegmentRel.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No MerisSegmentRel exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No MerisSegmentRel exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(MerisSegmentRelPersistenceImpl.class);
}