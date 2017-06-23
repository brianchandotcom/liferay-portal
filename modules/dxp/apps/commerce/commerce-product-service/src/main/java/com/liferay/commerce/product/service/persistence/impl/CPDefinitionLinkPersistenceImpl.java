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

package com.liferay.commerce.product.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.exception.NoSuchCPDefinitionLinkException;
import com.liferay.commerce.product.model.CPDefinitionLink;
import com.liferay.commerce.product.model.impl.CPDefinitionLinkImpl;
import com.liferay.commerce.product.model.impl.CPDefinitionLinkModelImpl;
import com.liferay.commerce.product.service.persistence.CPDefinitionLinkPersistence;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the cp definition link service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPDefinitionLinkPersistence
 * @see com.liferay.commerce.product.service.persistence.CPDefinitionLinkUtil
 * @generated
 */
@ProviderType
public class CPDefinitionLinkPersistenceImpl extends BasePersistenceImpl<CPDefinitionLink>
	implements CPDefinitionLinkPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CPDefinitionLinkUtil} to access the cp definition link persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CPDefinitionLinkImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CPDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionLinkModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionLinkImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CPDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionLinkModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CPDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionLinkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_CPDEFINITIONID1 =
		new FinderPath(CPDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionLinkModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionLinkImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCPDefinitionId1",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONID1 =
		new FinderPath(CPDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionLinkModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCPDefinitionId1",
			new String[] { Long.class.getName() },
			CPDefinitionLinkModelImpl.CPDEFINITIONID1_COLUMN_BITMASK |
			CPDefinitionLinkModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CPDEFINITIONID1 = new FinderPath(CPDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionLinkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCPDefinitionId1", new String[] { Long.class.getName() });

	/**
	 * Returns all the cp definition links where CPDefinitionId1 = &#63;.
	 *
	 * @param CPDefinitionId1 the cp definition id1
	 * @return the matching cp definition links
	 */
	@Override
	public List<CPDefinitionLink> findByCPDefinitionId1(long CPDefinitionId1) {
		return findByCPDefinitionId1(CPDefinitionId1, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition links where CPDefinitionId1 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param CPDefinitionId1 the cp definition id1
	 * @param start the lower bound of the range of cp definition links
	 * @param end the upper bound of the range of cp definition links (not inclusive)
	 * @return the range of matching cp definition links
	 */
	@Override
	public List<CPDefinitionLink> findByCPDefinitionId1(long CPDefinitionId1,
		int start, int end) {
		return findByCPDefinitionId1(CPDefinitionId1, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition links where CPDefinitionId1 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param CPDefinitionId1 the cp definition id1
	 * @param start the lower bound of the range of cp definition links
	 * @param end the upper bound of the range of cp definition links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition links
	 */
	@Override
	public List<CPDefinitionLink> findByCPDefinitionId1(long CPDefinitionId1,
		int start, int end,
		OrderByComparator<CPDefinitionLink> orderByComparator) {
		return findByCPDefinitionId1(CPDefinitionId1, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition links where CPDefinitionId1 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param CPDefinitionId1 the cp definition id1
	 * @param start the lower bound of the range of cp definition links
	 * @param end the upper bound of the range of cp definition links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp definition links
	 */
	@Override
	public List<CPDefinitionLink> findByCPDefinitionId1(long CPDefinitionId1,
		int start, int end,
		OrderByComparator<CPDefinitionLink> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONID1;
			finderArgs = new Object[] { CPDefinitionId1 };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_CPDEFINITIONID1;
			finderArgs = new Object[] {
					CPDefinitionId1,
					
					start, end, orderByComparator
				};
		}

		List<CPDefinitionLink> list = null;

		if (retrieveFromCache) {
			list = (List<CPDefinitionLink>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionLink cpDefinitionLink : list) {
					if ((CPDefinitionId1 != cpDefinitionLink.getCPDefinitionId1())) {
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

			query.append(_SQL_SELECT_CPDEFINITIONLINK_WHERE);

			query.append(_FINDER_COLUMN_CPDEFINITIONID1_CPDEFINITIONID1_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CPDefinitionLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(CPDefinitionId1);

				if (!pagination) {
					list = (List<CPDefinitionLink>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPDefinitionLink>)QueryUtil.list(q,
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
	 * Returns the first cp definition link in the ordered set where CPDefinitionId1 = &#63;.
	 *
	 * @param CPDefinitionId1 the cp definition id1
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition link
	 * @throws NoSuchCPDefinitionLinkException if a matching cp definition link could not be found
	 */
	@Override
	public CPDefinitionLink findByCPDefinitionId1_First(long CPDefinitionId1,
		OrderByComparator<CPDefinitionLink> orderByComparator)
		throws NoSuchCPDefinitionLinkException {
		CPDefinitionLink cpDefinitionLink = fetchByCPDefinitionId1_First(CPDefinitionId1,
				orderByComparator);

		if (cpDefinitionLink != null) {
			return cpDefinitionLink;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("CPDefinitionId1=");
		msg.append(CPDefinitionId1);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCPDefinitionLinkException(msg.toString());
	}

	/**
	 * Returns the first cp definition link in the ordered set where CPDefinitionId1 = &#63;.
	 *
	 * @param CPDefinitionId1 the cp definition id1
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition link, or <code>null</code> if a matching cp definition link could not be found
	 */
	@Override
	public CPDefinitionLink fetchByCPDefinitionId1_First(long CPDefinitionId1,
		OrderByComparator<CPDefinitionLink> orderByComparator) {
		List<CPDefinitionLink> list = findByCPDefinitionId1(CPDefinitionId1, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition link in the ordered set where CPDefinitionId1 = &#63;.
	 *
	 * @param CPDefinitionId1 the cp definition id1
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition link
	 * @throws NoSuchCPDefinitionLinkException if a matching cp definition link could not be found
	 */
	@Override
	public CPDefinitionLink findByCPDefinitionId1_Last(long CPDefinitionId1,
		OrderByComparator<CPDefinitionLink> orderByComparator)
		throws NoSuchCPDefinitionLinkException {
		CPDefinitionLink cpDefinitionLink = fetchByCPDefinitionId1_Last(CPDefinitionId1,
				orderByComparator);

		if (cpDefinitionLink != null) {
			return cpDefinitionLink;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("CPDefinitionId1=");
		msg.append(CPDefinitionId1);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCPDefinitionLinkException(msg.toString());
	}

	/**
	 * Returns the last cp definition link in the ordered set where CPDefinitionId1 = &#63;.
	 *
	 * @param CPDefinitionId1 the cp definition id1
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition link, or <code>null</code> if a matching cp definition link could not be found
	 */
	@Override
	public CPDefinitionLink fetchByCPDefinitionId1_Last(long CPDefinitionId1,
		OrderByComparator<CPDefinitionLink> orderByComparator) {
		int count = countByCPDefinitionId1(CPDefinitionId1);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionLink> list = findByCPDefinitionId1(CPDefinitionId1,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition links before and after the current cp definition link in the ordered set where CPDefinitionId1 = &#63;.
	 *
	 * @param CPDefinitionLinkId the primary key of the current cp definition link
	 * @param CPDefinitionId1 the cp definition id1
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition link
	 * @throws NoSuchCPDefinitionLinkException if a cp definition link with the primary key could not be found
	 */
	@Override
	public CPDefinitionLink[] findByCPDefinitionId1_PrevAndNext(
		long CPDefinitionLinkId, long CPDefinitionId1,
		OrderByComparator<CPDefinitionLink> orderByComparator)
		throws NoSuchCPDefinitionLinkException {
		CPDefinitionLink cpDefinitionLink = findByPrimaryKey(CPDefinitionLinkId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionLink[] array = new CPDefinitionLinkImpl[3];

			array[0] = getByCPDefinitionId1_PrevAndNext(session,
					cpDefinitionLink, CPDefinitionId1, orderByComparator, true);

			array[1] = cpDefinitionLink;

			array[2] = getByCPDefinitionId1_PrevAndNext(session,
					cpDefinitionLink, CPDefinitionId1, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPDefinitionLink getByCPDefinitionId1_PrevAndNext(
		Session session, CPDefinitionLink cpDefinitionLink,
		long CPDefinitionId1,
		OrderByComparator<CPDefinitionLink> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CPDEFINITIONLINK_WHERE);

		query.append(_FINDER_COLUMN_CPDEFINITIONID1_CPDEFINITIONID1_2);

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
			query.append(CPDefinitionLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(CPDefinitionId1);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(cpDefinitionLink);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPDefinitionLink> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definition links where CPDefinitionId1 = &#63; from the database.
	 *
	 * @param CPDefinitionId1 the cp definition id1
	 */
	@Override
	public void removeByCPDefinitionId1(long CPDefinitionId1) {
		for (CPDefinitionLink cpDefinitionLink : findByCPDefinitionId1(
				CPDefinitionId1, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpDefinitionLink);
		}
	}

	/**
	 * Returns the number of cp definition links where CPDefinitionId1 = &#63;.
	 *
	 * @param CPDefinitionId1 the cp definition id1
	 * @return the number of matching cp definition links
	 */
	@Override
	public int countByCPDefinitionId1(long CPDefinitionId1) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_CPDEFINITIONID1;

		Object[] finderArgs = new Object[] { CPDefinitionId1 };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CPDEFINITIONLINK_WHERE);

			query.append(_FINDER_COLUMN_CPDEFINITIONID1_CPDEFINITIONID1_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(CPDefinitionId1);

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

	private static final String _FINDER_COLUMN_CPDEFINITIONID1_CPDEFINITIONID1_2 =
		"cpDefinitionLink.CPDefinitionId1 = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_CPDEFINITIONID2 =
		new FinderPath(CPDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionLinkModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionLinkImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCPDefinitionId2",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONID2 =
		new FinderPath(CPDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionLinkModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCPDefinitionId2",
			new String[] { Long.class.getName() },
			CPDefinitionLinkModelImpl.CPDEFINITIONID2_COLUMN_BITMASK |
			CPDefinitionLinkModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CPDEFINITIONID2 = new FinderPath(CPDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionLinkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCPDefinitionId2", new String[] { Long.class.getName() });

	/**
	 * Returns all the cp definition links where CPDefinitionId2 = &#63;.
	 *
	 * @param CPDefinitionId2 the cp definition id2
	 * @return the matching cp definition links
	 */
	@Override
	public List<CPDefinitionLink> findByCPDefinitionId2(long CPDefinitionId2) {
		return findByCPDefinitionId2(CPDefinitionId2, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition links where CPDefinitionId2 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param CPDefinitionId2 the cp definition id2
	 * @param start the lower bound of the range of cp definition links
	 * @param end the upper bound of the range of cp definition links (not inclusive)
	 * @return the range of matching cp definition links
	 */
	@Override
	public List<CPDefinitionLink> findByCPDefinitionId2(long CPDefinitionId2,
		int start, int end) {
		return findByCPDefinitionId2(CPDefinitionId2, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition links where CPDefinitionId2 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param CPDefinitionId2 the cp definition id2
	 * @param start the lower bound of the range of cp definition links
	 * @param end the upper bound of the range of cp definition links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition links
	 */
	@Override
	public List<CPDefinitionLink> findByCPDefinitionId2(long CPDefinitionId2,
		int start, int end,
		OrderByComparator<CPDefinitionLink> orderByComparator) {
		return findByCPDefinitionId2(CPDefinitionId2, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition links where CPDefinitionId2 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param CPDefinitionId2 the cp definition id2
	 * @param start the lower bound of the range of cp definition links
	 * @param end the upper bound of the range of cp definition links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp definition links
	 */
	@Override
	public List<CPDefinitionLink> findByCPDefinitionId2(long CPDefinitionId2,
		int start, int end,
		OrderByComparator<CPDefinitionLink> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONID2;
			finderArgs = new Object[] { CPDefinitionId2 };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_CPDEFINITIONID2;
			finderArgs = new Object[] {
					CPDefinitionId2,
					
					start, end, orderByComparator
				};
		}

		List<CPDefinitionLink> list = null;

		if (retrieveFromCache) {
			list = (List<CPDefinitionLink>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionLink cpDefinitionLink : list) {
					if ((CPDefinitionId2 != cpDefinitionLink.getCPDefinitionId2())) {
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

			query.append(_SQL_SELECT_CPDEFINITIONLINK_WHERE);

			query.append(_FINDER_COLUMN_CPDEFINITIONID2_CPDEFINITIONID2_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CPDefinitionLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(CPDefinitionId2);

				if (!pagination) {
					list = (List<CPDefinitionLink>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPDefinitionLink>)QueryUtil.list(q,
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
	 * Returns the first cp definition link in the ordered set where CPDefinitionId2 = &#63;.
	 *
	 * @param CPDefinitionId2 the cp definition id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition link
	 * @throws NoSuchCPDefinitionLinkException if a matching cp definition link could not be found
	 */
	@Override
	public CPDefinitionLink findByCPDefinitionId2_First(long CPDefinitionId2,
		OrderByComparator<CPDefinitionLink> orderByComparator)
		throws NoSuchCPDefinitionLinkException {
		CPDefinitionLink cpDefinitionLink = fetchByCPDefinitionId2_First(CPDefinitionId2,
				orderByComparator);

		if (cpDefinitionLink != null) {
			return cpDefinitionLink;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("CPDefinitionId2=");
		msg.append(CPDefinitionId2);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCPDefinitionLinkException(msg.toString());
	}

	/**
	 * Returns the first cp definition link in the ordered set where CPDefinitionId2 = &#63;.
	 *
	 * @param CPDefinitionId2 the cp definition id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition link, or <code>null</code> if a matching cp definition link could not be found
	 */
	@Override
	public CPDefinitionLink fetchByCPDefinitionId2_First(long CPDefinitionId2,
		OrderByComparator<CPDefinitionLink> orderByComparator) {
		List<CPDefinitionLink> list = findByCPDefinitionId2(CPDefinitionId2, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition link in the ordered set where CPDefinitionId2 = &#63;.
	 *
	 * @param CPDefinitionId2 the cp definition id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition link
	 * @throws NoSuchCPDefinitionLinkException if a matching cp definition link could not be found
	 */
	@Override
	public CPDefinitionLink findByCPDefinitionId2_Last(long CPDefinitionId2,
		OrderByComparator<CPDefinitionLink> orderByComparator)
		throws NoSuchCPDefinitionLinkException {
		CPDefinitionLink cpDefinitionLink = fetchByCPDefinitionId2_Last(CPDefinitionId2,
				orderByComparator);

		if (cpDefinitionLink != null) {
			return cpDefinitionLink;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("CPDefinitionId2=");
		msg.append(CPDefinitionId2);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCPDefinitionLinkException(msg.toString());
	}

	/**
	 * Returns the last cp definition link in the ordered set where CPDefinitionId2 = &#63;.
	 *
	 * @param CPDefinitionId2 the cp definition id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition link, or <code>null</code> if a matching cp definition link could not be found
	 */
	@Override
	public CPDefinitionLink fetchByCPDefinitionId2_Last(long CPDefinitionId2,
		OrderByComparator<CPDefinitionLink> orderByComparator) {
		int count = countByCPDefinitionId2(CPDefinitionId2);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionLink> list = findByCPDefinitionId2(CPDefinitionId2,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition links before and after the current cp definition link in the ordered set where CPDefinitionId2 = &#63;.
	 *
	 * @param CPDefinitionLinkId the primary key of the current cp definition link
	 * @param CPDefinitionId2 the cp definition id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition link
	 * @throws NoSuchCPDefinitionLinkException if a cp definition link with the primary key could not be found
	 */
	@Override
	public CPDefinitionLink[] findByCPDefinitionId2_PrevAndNext(
		long CPDefinitionLinkId, long CPDefinitionId2,
		OrderByComparator<CPDefinitionLink> orderByComparator)
		throws NoSuchCPDefinitionLinkException {
		CPDefinitionLink cpDefinitionLink = findByPrimaryKey(CPDefinitionLinkId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionLink[] array = new CPDefinitionLinkImpl[3];

			array[0] = getByCPDefinitionId2_PrevAndNext(session,
					cpDefinitionLink, CPDefinitionId2, orderByComparator, true);

			array[1] = cpDefinitionLink;

			array[2] = getByCPDefinitionId2_PrevAndNext(session,
					cpDefinitionLink, CPDefinitionId2, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPDefinitionLink getByCPDefinitionId2_PrevAndNext(
		Session session, CPDefinitionLink cpDefinitionLink,
		long CPDefinitionId2,
		OrderByComparator<CPDefinitionLink> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CPDEFINITIONLINK_WHERE);

		query.append(_FINDER_COLUMN_CPDEFINITIONID2_CPDEFINITIONID2_2);

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
			query.append(CPDefinitionLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(CPDefinitionId2);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(cpDefinitionLink);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPDefinitionLink> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definition links where CPDefinitionId2 = &#63; from the database.
	 *
	 * @param CPDefinitionId2 the cp definition id2
	 */
	@Override
	public void removeByCPDefinitionId2(long CPDefinitionId2) {
		for (CPDefinitionLink cpDefinitionLink : findByCPDefinitionId2(
				CPDefinitionId2, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpDefinitionLink);
		}
	}

	/**
	 * Returns the number of cp definition links where CPDefinitionId2 = &#63;.
	 *
	 * @param CPDefinitionId2 the cp definition id2
	 * @return the number of matching cp definition links
	 */
	@Override
	public int countByCPDefinitionId2(long CPDefinitionId2) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_CPDEFINITIONID2;

		Object[] finderArgs = new Object[] { CPDefinitionId2 };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CPDEFINITIONLINK_WHERE);

			query.append(_FINDER_COLUMN_CPDEFINITIONID2_CPDEFINITIONID2_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(CPDefinitionId2);

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

	private static final String _FINDER_COLUMN_CPDEFINITIONID2_CPDEFINITIONID2_2 =
		"cpDefinitionLink.CPDefinitionId2 = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_C1_C2_T = new FinderPath(CPDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionLinkModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionLinkImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByC1_C2_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			CPDefinitionLinkModelImpl.CPDEFINITIONID1_COLUMN_BITMASK |
			CPDefinitionLinkModelImpl.CPDEFINITIONID2_COLUMN_BITMASK |
			CPDefinitionLinkModelImpl.TYPE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C1_C2_T = new FinderPath(CPDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionLinkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC1_C2_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns the cp definition link where CPDefinitionId1 = &#63; and CPDefinitionId2 = &#63; and type = &#63; or throws a {@link NoSuchCPDefinitionLinkException} if it could not be found.
	 *
	 * @param CPDefinitionId1 the cp definition id1
	 * @param CPDefinitionId2 the cp definition id2
	 * @param type the type
	 * @return the matching cp definition link
	 * @throws NoSuchCPDefinitionLinkException if a matching cp definition link could not be found
	 */
	@Override
	public CPDefinitionLink findByC1_C2_T(long CPDefinitionId1,
		long CPDefinitionId2, int type) throws NoSuchCPDefinitionLinkException {
		CPDefinitionLink cpDefinitionLink = fetchByC1_C2_T(CPDefinitionId1,
				CPDefinitionId2, type);

		if (cpDefinitionLink == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("CPDefinitionId1=");
			msg.append(CPDefinitionId1);

			msg.append(", CPDefinitionId2=");
			msg.append(CPDefinitionId2);

			msg.append(", type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchCPDefinitionLinkException(msg.toString());
		}

		return cpDefinitionLink;
	}

	/**
	 * Returns the cp definition link where CPDefinitionId1 = &#63; and CPDefinitionId2 = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPDefinitionId1 the cp definition id1
	 * @param CPDefinitionId2 the cp definition id2
	 * @param type the type
	 * @return the matching cp definition link, or <code>null</code> if a matching cp definition link could not be found
	 */
	@Override
	public CPDefinitionLink fetchByC1_C2_T(long CPDefinitionId1,
		long CPDefinitionId2, int type) {
		return fetchByC1_C2_T(CPDefinitionId1, CPDefinitionId2, type, true);
	}

	/**
	 * Returns the cp definition link where CPDefinitionId1 = &#63; and CPDefinitionId2 = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPDefinitionId1 the cp definition id1
	 * @param CPDefinitionId2 the cp definition id2
	 * @param type the type
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching cp definition link, or <code>null</code> if a matching cp definition link could not be found
	 */
	@Override
	public CPDefinitionLink fetchByC1_C2_T(long CPDefinitionId1,
		long CPDefinitionId2, int type, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] {
				CPDefinitionId1, CPDefinitionId2, type
			};

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C1_C2_T,
					finderArgs, this);
		}

		if (result instanceof CPDefinitionLink) {
			CPDefinitionLink cpDefinitionLink = (CPDefinitionLink)result;

			if ((CPDefinitionId1 != cpDefinitionLink.getCPDefinitionId1()) ||
					(CPDefinitionId2 != cpDefinitionLink.getCPDefinitionId2()) ||
					(type != cpDefinitionLink.getType())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_CPDEFINITIONLINK_WHERE);

			query.append(_FINDER_COLUMN_C1_C2_T_CPDEFINITIONID1_2);

			query.append(_FINDER_COLUMN_C1_C2_T_CPDEFINITIONID2_2);

			query.append(_FINDER_COLUMN_C1_C2_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(CPDefinitionId1);

				qPos.add(CPDefinitionId2);

				qPos.add(type);

				List<CPDefinitionLink> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_C1_C2_T,
						finderArgs, list);
				}
				else {
					CPDefinitionLink cpDefinitionLink = list.get(0);

					result = cpDefinitionLink;

					cacheResult(cpDefinitionLink);

					if ((cpDefinitionLink.getCPDefinitionId1() != CPDefinitionId1) ||
							(cpDefinitionLink.getCPDefinitionId2() != CPDefinitionId2) ||
							(cpDefinitionLink.getType() != type)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_C1_C2_T,
							finderArgs, cpDefinitionLink);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C1_C2_T,
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
			return (CPDefinitionLink)result;
		}
	}

	/**
	 * Removes the cp definition link where CPDefinitionId1 = &#63; and CPDefinitionId2 = &#63; and type = &#63; from the database.
	 *
	 * @param CPDefinitionId1 the cp definition id1
	 * @param CPDefinitionId2 the cp definition id2
	 * @param type the type
	 * @return the cp definition link that was removed
	 */
	@Override
	public CPDefinitionLink removeByC1_C2_T(long CPDefinitionId1,
		long CPDefinitionId2, int type) throws NoSuchCPDefinitionLinkException {
		CPDefinitionLink cpDefinitionLink = findByC1_C2_T(CPDefinitionId1,
				CPDefinitionId2, type);

		return remove(cpDefinitionLink);
	}

	/**
	 * Returns the number of cp definition links where CPDefinitionId1 = &#63; and CPDefinitionId2 = &#63; and type = &#63;.
	 *
	 * @param CPDefinitionId1 the cp definition id1
	 * @param CPDefinitionId2 the cp definition id2
	 * @param type the type
	 * @return the number of matching cp definition links
	 */
	@Override
	public int countByC1_C2_T(long CPDefinitionId1, long CPDefinitionId2,
		int type) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C1_C2_T;

		Object[] finderArgs = new Object[] {
				CPDefinitionId1, CPDefinitionId2, type
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_CPDEFINITIONLINK_WHERE);

			query.append(_FINDER_COLUMN_C1_C2_T_CPDEFINITIONID1_2);

			query.append(_FINDER_COLUMN_C1_C2_T_CPDEFINITIONID2_2);

			query.append(_FINDER_COLUMN_C1_C2_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(CPDefinitionId1);

				qPos.add(CPDefinitionId2);

				qPos.add(type);

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

	private static final String _FINDER_COLUMN_C1_C2_T_CPDEFINITIONID1_2 = "cpDefinitionLink.CPDefinitionId1 = ? AND ";
	private static final String _FINDER_COLUMN_C1_C2_T_CPDEFINITIONID2_2 = "cpDefinitionLink.CPDefinitionId2 = ? AND ";
	private static final String _FINDER_COLUMN_C1_C2_T_TYPE_2 = "cpDefinitionLink.type = ?";

	public CPDefinitionLinkPersistenceImpl() {
		setModelClass(CPDefinitionLink.class);

		try {
			Field field = ReflectionUtil.getDeclaredField(BasePersistenceImpl.class,
					"_dbColumnNames");

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("type", "type_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the cp definition link in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionLink the cp definition link
	 */
	@Override
	public void cacheResult(CPDefinitionLink cpDefinitionLink) {
		entityCache.putResult(CPDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionLinkImpl.class, cpDefinitionLink.getPrimaryKey(),
			cpDefinitionLink);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C1_C2_T,
			new Object[] {
				cpDefinitionLink.getCPDefinitionId1(),
				cpDefinitionLink.getCPDefinitionId2(),
				cpDefinitionLink.getType()
			}, cpDefinitionLink);

		cpDefinitionLink.resetOriginalValues();
	}

	/**
	 * Caches the cp definition links in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionLinks the cp definition links
	 */
	@Override
	public void cacheResult(List<CPDefinitionLink> cpDefinitionLinks) {
		for (CPDefinitionLink cpDefinitionLink : cpDefinitionLinks) {
			if (entityCache.getResult(
						CPDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
						CPDefinitionLinkImpl.class,
						cpDefinitionLink.getPrimaryKey()) == null) {
				cacheResult(cpDefinitionLink);
			}
			else {
				cpDefinitionLink.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all cp definition links.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CPDefinitionLinkImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the cp definition link.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CPDefinitionLink cpDefinitionLink) {
		entityCache.removeResult(CPDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionLinkImpl.class, cpDefinitionLink.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CPDefinitionLinkModelImpl)cpDefinitionLink,
			true);
	}

	@Override
	public void clearCache(List<CPDefinitionLink> cpDefinitionLinks) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CPDefinitionLink cpDefinitionLink : cpDefinitionLinks) {
			entityCache.removeResult(CPDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
				CPDefinitionLinkImpl.class, cpDefinitionLink.getPrimaryKey());

			clearUniqueFindersCache((CPDefinitionLinkModelImpl)cpDefinitionLink,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		CPDefinitionLinkModelImpl cpDefinitionLinkModelImpl) {
		Object[] args = new Object[] {
				cpDefinitionLinkModelImpl.getCPDefinitionId1(),
				cpDefinitionLinkModelImpl.getCPDefinitionId2(),
				cpDefinitionLinkModelImpl.getType()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_C1_C2_T, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_C1_C2_T, args,
			cpDefinitionLinkModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CPDefinitionLinkModelImpl cpDefinitionLinkModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					cpDefinitionLinkModelImpl.getCPDefinitionId1(),
					cpDefinitionLinkModelImpl.getCPDefinitionId2(),
					cpDefinitionLinkModelImpl.getType()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C1_C2_T, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C1_C2_T, args);
		}

		if ((cpDefinitionLinkModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C1_C2_T.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					cpDefinitionLinkModelImpl.getOriginalCPDefinitionId1(),
					cpDefinitionLinkModelImpl.getOriginalCPDefinitionId2(),
					cpDefinitionLinkModelImpl.getOriginalType()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C1_C2_T, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C1_C2_T, args);
		}
	}

	/**
	 * Creates a new cp definition link with the primary key. Does not add the cp definition link to the database.
	 *
	 * @param CPDefinitionLinkId the primary key for the new cp definition link
	 * @return the new cp definition link
	 */
	@Override
	public CPDefinitionLink create(long CPDefinitionLinkId) {
		CPDefinitionLink cpDefinitionLink = new CPDefinitionLinkImpl();

		cpDefinitionLink.setNew(true);
		cpDefinitionLink.setPrimaryKey(CPDefinitionLinkId);

		cpDefinitionLink.setCompanyId(companyProvider.getCompanyId());

		return cpDefinitionLink;
	}

	/**
	 * Removes the cp definition link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPDefinitionLinkId the primary key of the cp definition link
	 * @return the cp definition link that was removed
	 * @throws NoSuchCPDefinitionLinkException if a cp definition link with the primary key could not be found
	 */
	@Override
	public CPDefinitionLink remove(long CPDefinitionLinkId)
		throws NoSuchCPDefinitionLinkException {
		return remove((Serializable)CPDefinitionLinkId);
	}

	/**
	 * Removes the cp definition link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cp definition link
	 * @return the cp definition link that was removed
	 * @throws NoSuchCPDefinitionLinkException if a cp definition link with the primary key could not be found
	 */
	@Override
	public CPDefinitionLink remove(Serializable primaryKey)
		throws NoSuchCPDefinitionLinkException {
		Session session = null;

		try {
			session = openSession();

			CPDefinitionLink cpDefinitionLink = (CPDefinitionLink)session.get(CPDefinitionLinkImpl.class,
					primaryKey);

			if (cpDefinitionLink == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCPDefinitionLinkException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(cpDefinitionLink);
		}
		catch (NoSuchCPDefinitionLinkException nsee) {
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
	protected CPDefinitionLink removeImpl(CPDefinitionLink cpDefinitionLink) {
		cpDefinitionLink = toUnwrappedModel(cpDefinitionLink);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpDefinitionLink)) {
				cpDefinitionLink = (CPDefinitionLink)session.get(CPDefinitionLinkImpl.class,
						cpDefinitionLink.getPrimaryKeyObj());
			}

			if (cpDefinitionLink != null) {
				session.delete(cpDefinitionLink);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (cpDefinitionLink != null) {
			clearCache(cpDefinitionLink);
		}

		return cpDefinitionLink;
	}

	@Override
	public CPDefinitionLink updateImpl(CPDefinitionLink cpDefinitionLink) {
		cpDefinitionLink = toUnwrappedModel(cpDefinitionLink);

		boolean isNew = cpDefinitionLink.isNew();

		CPDefinitionLinkModelImpl cpDefinitionLinkModelImpl = (CPDefinitionLinkModelImpl)cpDefinitionLink;

		Session session = null;

		try {
			session = openSession();

			if (cpDefinitionLink.isNew()) {
				session.save(cpDefinitionLink);

				cpDefinitionLink.setNew(false);
			}
			else {
				cpDefinitionLink = (CPDefinitionLink)session.merge(cpDefinitionLink);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CPDefinitionLinkModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					cpDefinitionLinkModelImpl.getCPDefinitionId1()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_CPDEFINITIONID1, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONID1,
				args);

			args = new Object[] { cpDefinitionLinkModelImpl.getCPDefinitionId2() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_CPDEFINITIONID2, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONID2,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((cpDefinitionLinkModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONID1.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpDefinitionLinkModelImpl.getOriginalCPDefinitionId1()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CPDEFINITIONID1,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONID1,
					args);

				args = new Object[] {
						cpDefinitionLinkModelImpl.getCPDefinitionId1()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CPDEFINITIONID1,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONID1,
					args);
			}

			if ((cpDefinitionLinkModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONID2.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpDefinitionLinkModelImpl.getOriginalCPDefinitionId2()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CPDEFINITIONID2,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONID2,
					args);

				args = new Object[] {
						cpDefinitionLinkModelImpl.getCPDefinitionId2()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CPDEFINITIONID2,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONID2,
					args);
			}
		}

		entityCache.putResult(CPDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionLinkImpl.class, cpDefinitionLink.getPrimaryKey(),
			cpDefinitionLink, false);

		clearUniqueFindersCache(cpDefinitionLinkModelImpl, false);
		cacheUniqueFindersCache(cpDefinitionLinkModelImpl);

		cpDefinitionLink.resetOriginalValues();

		return cpDefinitionLink;
	}

	protected CPDefinitionLink toUnwrappedModel(
		CPDefinitionLink cpDefinitionLink) {
		if (cpDefinitionLink instanceof CPDefinitionLinkImpl) {
			return cpDefinitionLink;
		}

		CPDefinitionLinkImpl cpDefinitionLinkImpl = new CPDefinitionLinkImpl();

		cpDefinitionLinkImpl.setNew(cpDefinitionLink.isNew());
		cpDefinitionLinkImpl.setPrimaryKey(cpDefinitionLink.getPrimaryKey());

		cpDefinitionLinkImpl.setCPDefinitionLinkId(cpDefinitionLink.getCPDefinitionLinkId());
		cpDefinitionLinkImpl.setCompanyId(cpDefinitionLink.getCompanyId());
		cpDefinitionLinkImpl.setUserId(cpDefinitionLink.getUserId());
		cpDefinitionLinkImpl.setUserName(cpDefinitionLink.getUserName());
		cpDefinitionLinkImpl.setCreateDate(cpDefinitionLink.getCreateDate());
		cpDefinitionLinkImpl.setCPDefinitionId1(cpDefinitionLink.getCPDefinitionId1());
		cpDefinitionLinkImpl.setCPDefinitionId2(cpDefinitionLink.getCPDefinitionId2());
		cpDefinitionLinkImpl.setPriority(cpDefinitionLink.getPriority());
		cpDefinitionLinkImpl.setType(cpDefinitionLink.getType());

		return cpDefinitionLinkImpl;
	}

	/**
	 * Returns the cp definition link with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp definition link
	 * @return the cp definition link
	 * @throws NoSuchCPDefinitionLinkException if a cp definition link with the primary key could not be found
	 */
	@Override
	public CPDefinitionLink findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCPDefinitionLinkException {
		CPDefinitionLink cpDefinitionLink = fetchByPrimaryKey(primaryKey);

		if (cpDefinitionLink == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCPDefinitionLinkException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return cpDefinitionLink;
	}

	/**
	 * Returns the cp definition link with the primary key or throws a {@link NoSuchCPDefinitionLinkException} if it could not be found.
	 *
	 * @param CPDefinitionLinkId the primary key of the cp definition link
	 * @return the cp definition link
	 * @throws NoSuchCPDefinitionLinkException if a cp definition link with the primary key could not be found
	 */
	@Override
	public CPDefinitionLink findByPrimaryKey(long CPDefinitionLinkId)
		throws NoSuchCPDefinitionLinkException {
		return findByPrimaryKey((Serializable)CPDefinitionLinkId);
	}

	/**
	 * Returns the cp definition link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp definition link
	 * @return the cp definition link, or <code>null</code> if a cp definition link with the primary key could not be found
	 */
	@Override
	public CPDefinitionLink fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CPDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
				CPDefinitionLinkImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CPDefinitionLink cpDefinitionLink = (CPDefinitionLink)serializable;

		if (cpDefinitionLink == null) {
			Session session = null;

			try {
				session = openSession();

				cpDefinitionLink = (CPDefinitionLink)session.get(CPDefinitionLinkImpl.class,
						primaryKey);

				if (cpDefinitionLink != null) {
					cacheResult(cpDefinitionLink);
				}
				else {
					entityCache.putResult(CPDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
						CPDefinitionLinkImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CPDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
					CPDefinitionLinkImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return cpDefinitionLink;
	}

	/**
	 * Returns the cp definition link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPDefinitionLinkId the primary key of the cp definition link
	 * @return the cp definition link, or <code>null</code> if a cp definition link with the primary key could not be found
	 */
	@Override
	public CPDefinitionLink fetchByPrimaryKey(long CPDefinitionLinkId) {
		return fetchByPrimaryKey((Serializable)CPDefinitionLinkId);
	}

	@Override
	public Map<Serializable, CPDefinitionLink> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CPDefinitionLink> map = new HashMap<Serializable, CPDefinitionLink>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CPDefinitionLink cpDefinitionLink = fetchByPrimaryKey(primaryKey);

			if (cpDefinitionLink != null) {
				map.put(primaryKey, cpDefinitionLink);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CPDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
					CPDefinitionLinkImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CPDefinitionLink)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_CPDEFINITIONLINK_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (CPDefinitionLink cpDefinitionLink : (List<CPDefinitionLink>)q.list()) {
				map.put(cpDefinitionLink.getPrimaryKeyObj(), cpDefinitionLink);

				cacheResult(cpDefinitionLink);

				uncachedPrimaryKeys.remove(cpDefinitionLink.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CPDefinitionLinkModelImpl.ENTITY_CACHE_ENABLED,
					CPDefinitionLinkImpl.class, primaryKey, nullModel);
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
	 * Returns all the cp definition links.
	 *
	 * @return the cp definition links
	 */
	@Override
	public List<CPDefinitionLink> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition links
	 * @param end the upper bound of the range of cp definition links (not inclusive)
	 * @return the range of cp definition links
	 */
	@Override
	public List<CPDefinitionLink> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition links
	 * @param end the upper bound of the range of cp definition links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp definition links
	 */
	@Override
	public List<CPDefinitionLink> findAll(int start, int end,
		OrderByComparator<CPDefinitionLink> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition links
	 * @param end the upper bound of the range of cp definition links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of cp definition links
	 */
	@Override
	public List<CPDefinitionLink> findAll(int start, int end,
		OrderByComparator<CPDefinitionLink> orderByComparator,
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

		List<CPDefinitionLink> list = null;

		if (retrieveFromCache) {
			list = (List<CPDefinitionLink>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CPDEFINITIONLINK);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CPDEFINITIONLINK;

				if (pagination) {
					sql = sql.concat(CPDefinitionLinkModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CPDefinitionLink>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPDefinitionLink>)QueryUtil.list(q,
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
	 * Removes all the cp definition links from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CPDefinitionLink cpDefinitionLink : findAll()) {
			remove(cpDefinitionLink);
		}
	}

	/**
	 * Returns the number of cp definition links.
	 *
	 * @return the number of cp definition links
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CPDEFINITIONLINK);

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
		return CPDefinitionLinkModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the cp definition link persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CPDefinitionLinkImpl.class.getName());
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
	private static final String _SQL_SELECT_CPDEFINITIONLINK = "SELECT cpDefinitionLink FROM CPDefinitionLink cpDefinitionLink";
	private static final String _SQL_SELECT_CPDEFINITIONLINK_WHERE_PKS_IN = "SELECT cpDefinitionLink FROM CPDefinitionLink cpDefinitionLink WHERE CPDefinitionLinkId IN (";
	private static final String _SQL_SELECT_CPDEFINITIONLINK_WHERE = "SELECT cpDefinitionLink FROM CPDefinitionLink cpDefinitionLink WHERE ";
	private static final String _SQL_COUNT_CPDEFINITIONLINK = "SELECT COUNT(cpDefinitionLink) FROM CPDefinitionLink cpDefinitionLink";
	private static final String _SQL_COUNT_CPDEFINITIONLINK_WHERE = "SELECT COUNT(cpDefinitionLink) FROM CPDefinitionLink cpDefinitionLink WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "cpDefinitionLink.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CPDefinitionLink exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CPDefinitionLink exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CPDefinitionLinkPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"type"
			});
}