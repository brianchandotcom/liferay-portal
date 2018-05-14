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

package com.liferay.portal.resiliency.spi.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.resiliency.spi.exception.NoSuchDefinitionException;
import com.liferay.portal.resiliency.spi.model.SPIDefinition;
import com.liferay.portal.resiliency.spi.model.impl.SPIDefinitionImpl;
import com.liferay.portal.resiliency.spi.model.impl.SPIDefinitionModelImpl;
import com.liferay.portal.resiliency.spi.service.persistence.SPIDefinitionPersistence;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the spi definition service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Michael C. Han
 * @see SPIDefinitionPersistence
 * @see com.liferay.portal.resiliency.spi.service.persistence.SPIDefinitionUtil
 * @generated
 */
@ProviderType
public class SPIDefinitionPersistenceImpl extends BasePersistenceImpl<SPIDefinition>
	implements SPIDefinitionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link SPIDefinitionUtil} to access the spi definition persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = SPIDefinitionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			SPIDefinitionModelImpl.FINDER_CACHE_ENABLED,
			SPIDefinitionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			SPIDefinitionModelImpl.FINDER_CACHE_ENABLED,
			SPIDefinitionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			SPIDefinitionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			SPIDefinitionModelImpl.FINDER_CACHE_ENABLED,
			SPIDefinitionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			SPIDefinitionModelImpl.FINDER_CACHE_ENABLED,
			SPIDefinitionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCompanyId", new String[] { Long.class.getName() },
			SPIDefinitionModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			SPIDefinitionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the spi definitions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching spi definitions
	 */
	@Override
	public List<SPIDefinition> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the spi definitions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SPIDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @return the range of matching spi definitions
	 */
	@Override
	public List<SPIDefinition> findByCompanyId(long companyId, int start,
		int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the spi definitions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SPIDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching spi definitions
	 */
	@Override
	public List<SPIDefinition> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<SPIDefinition> orderByComparator) {
		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the spi definitions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SPIDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching spi definitions
	 */
	@Override
	public List<SPIDefinition> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<SPIDefinition> orderByComparator,
		boolean retrieveFromCache) {
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

		List<SPIDefinition> list = null;

		if (retrieveFromCache) {
			list = (List<SPIDefinition>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SPIDefinition spiDefinition : list) {
					if ((companyId != spiDefinition.getCompanyId())) {
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

			query.append(_SQL_SELECT_SPIDEFINITION_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SPIDefinitionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<SPIDefinition>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SPIDefinition>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first spi definition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching spi definition
	 * @throws NoSuchDefinitionException if a matching spi definition could not be found
	 */
	@Override
	public SPIDefinition findByCompanyId_First(long companyId,
		OrderByComparator<SPIDefinition> orderByComparator)
		throws NoSuchDefinitionException {
		SPIDefinition spiDefinition = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (spiDefinition != null) {
			return spiDefinition;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchDefinitionException(msg.toString());
	}

	/**
	 * Returns the first spi definition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching spi definition, or <code>null</code> if a matching spi definition could not be found
	 */
	@Override
	public SPIDefinition fetchByCompanyId_First(long companyId,
		OrderByComparator<SPIDefinition> orderByComparator) {
		List<SPIDefinition> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last spi definition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching spi definition
	 * @throws NoSuchDefinitionException if a matching spi definition could not be found
	 */
	@Override
	public SPIDefinition findByCompanyId_Last(long companyId,
		OrderByComparator<SPIDefinition> orderByComparator)
		throws NoSuchDefinitionException {
		SPIDefinition spiDefinition = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (spiDefinition != null) {
			return spiDefinition;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchDefinitionException(msg.toString());
	}

	/**
	 * Returns the last spi definition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching spi definition, or <code>null</code> if a matching spi definition could not be found
	 */
	@Override
	public SPIDefinition fetchByCompanyId_Last(long companyId,
		OrderByComparator<SPIDefinition> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<SPIDefinition> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the spi definitions before and after the current spi definition in the ordered set where companyId = &#63;.
	 *
	 * @param spiDefinitionId the primary key of the current spi definition
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next spi definition
	 * @throws NoSuchDefinitionException if a spi definition with the primary key could not be found
	 */
	@Override
	public SPIDefinition[] findByCompanyId_PrevAndNext(long spiDefinitionId,
		long companyId, OrderByComparator<SPIDefinition> orderByComparator)
		throws NoSuchDefinitionException {
		SPIDefinition spiDefinition = findByPrimaryKey(spiDefinitionId);

		Session session = null;

		try {
			session = openSession();

			SPIDefinition[] array = new SPIDefinitionImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, spiDefinition,
					companyId, orderByComparator, true);

			array[1] = spiDefinition;

			array[2] = getByCompanyId_PrevAndNext(session, spiDefinition,
					companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SPIDefinition getByCompanyId_PrevAndNext(Session session,
		SPIDefinition spiDefinition, long companyId,
		OrderByComparator<SPIDefinition> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SPIDEFINITION_WHERE);

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
			query.append(SPIDefinitionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(spiDefinition);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SPIDefinition> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the spi definitions that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching spi definitions that the user has permission to view
	 */
	@Override
	public List<SPIDefinition> filterFindByCompanyId(long companyId) {
		return filterFindByCompanyId(companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the spi definitions that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SPIDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @return the range of matching spi definitions that the user has permission to view
	 */
	@Override
	public List<SPIDefinition> filterFindByCompanyId(long companyId, int start,
		int end) {
		return filterFindByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the spi definitions that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SPIDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching spi definitions that the user has permission to view
	 */
	@Override
	public List<SPIDefinition> filterFindByCompanyId(long companyId, int start,
		int end, OrderByComparator<SPIDefinition> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByCompanyId(companyId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(3 +
					(orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SPIDEFINITION_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SPIDEFINITION_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SPIDEFINITION_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(SPIDefinitionModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SPIDefinitionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SPIDefinition.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, SPIDefinitionImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, SPIDefinitionImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			return (List<SPIDefinition>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the spi definitions before and after the current spi definition in the ordered set of spi definitions that the user has permission to view where companyId = &#63;.
	 *
	 * @param spiDefinitionId the primary key of the current spi definition
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next spi definition
	 * @throws NoSuchDefinitionException if a spi definition with the primary key could not be found
	 */
	@Override
	public SPIDefinition[] filterFindByCompanyId_PrevAndNext(
		long spiDefinitionId, long companyId,
		OrderByComparator<SPIDefinition> orderByComparator)
		throws NoSuchDefinitionException {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByCompanyId_PrevAndNext(spiDefinitionId, companyId,
				orderByComparator);
		}

		SPIDefinition spiDefinition = findByPrimaryKey(spiDefinitionId);

		Session session = null;

		try {
			session = openSession();

			SPIDefinition[] array = new SPIDefinitionImpl[3];

			array[0] = filterGetByCompanyId_PrevAndNext(session, spiDefinition,
					companyId, orderByComparator, true);

			array[1] = spiDefinition;

			array[2] = filterGetByCompanyId_PrevAndNext(session, spiDefinition,
					companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SPIDefinition filterGetByCompanyId_PrevAndNext(Session session,
		SPIDefinition spiDefinition, long companyId,
		OrderByComparator<SPIDefinition> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SPIDEFINITION_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SPIDEFINITION_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SPIDEFINITION_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(SPIDefinitionModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SPIDefinitionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SPIDefinition.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, SPIDefinitionImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, SPIDefinitionImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(spiDefinition);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SPIDefinition> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the spi definitions where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (SPIDefinition spiDefinition : findByCompanyId(companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(spiDefinition);
		}
	}

	/**
	 * Returns the number of spi definitions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching spi definitions
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SPIDEFINITION_WHERE);

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

	/**
	 * Returns the number of spi definitions that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching spi definitions that the user has permission to view
	 */
	@Override
	public int filterCountByCompanyId(long companyId) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByCompanyId(companyId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_SPIDEFINITION_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SPIDefinition.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "spiDefinition.companyId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_N = new FinderPath(SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			SPIDefinitionModelImpl.FINDER_CACHE_ENABLED,
			SPIDefinitionImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByC_N",
			new String[] { Long.class.getName(), String.class.getName() },
			SPIDefinitionModelImpl.COMPANYID_COLUMN_BITMASK |
			SPIDefinitionModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_N = new FinderPath(SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			SPIDefinitionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_N",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the spi definition where companyId = &#63; and name = &#63; or throws a {@link NoSuchDefinitionException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching spi definition
	 * @throws NoSuchDefinitionException if a matching spi definition could not be found
	 */
	@Override
	public SPIDefinition findByC_N(long companyId, String name)
		throws NoSuchDefinitionException {
		SPIDefinition spiDefinition = fetchByC_N(companyId, name);

		if (spiDefinition == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", name=");
			msg.append(name);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchDefinitionException(msg.toString());
		}

		return spiDefinition;
	}

	/**
	 * Returns the spi definition where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching spi definition, or <code>null</code> if a matching spi definition could not be found
	 */
	@Override
	public SPIDefinition fetchByC_N(long companyId, String name) {
		return fetchByC_N(companyId, name, true);
	}

	/**
	 * Returns the spi definition where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching spi definition, or <code>null</code> if a matching spi definition could not be found
	 */
	@Override
	public SPIDefinition fetchByC_N(long companyId, String name,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { companyId, name };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_N,
					finderArgs, this);
		}

		if (result instanceof SPIDefinition) {
			SPIDefinition spiDefinition = (SPIDefinition)result;

			if ((companyId != spiDefinition.getCompanyId()) ||
					!Objects.equals(name, spiDefinition.getName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_SPIDEFINITION_WHERE);

			query.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_C_N_NAME_1);
			}
			else if (name.equals("")) {
				query.append(_FINDER_COLUMN_C_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindName) {
					qPos.add(name);
				}

				List<SPIDefinition> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_N, finderArgs,
						list);
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							_log.warn(
								"SPIDefinitionPersistenceImpl.fetchByC_N(long, String, boolean) with parameters (" +
								StringUtil.merge(finderArgs) +
								") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					SPIDefinition spiDefinition = list.get(0);

					result = spiDefinition;

					cacheResult(spiDefinition);

					if ((spiDefinition.getCompanyId() != companyId) ||
							(spiDefinition.getName() == null) ||
							!spiDefinition.getName().equals(name)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_C_N,
							finderArgs, spiDefinition);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C_N, finderArgs);

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
			return (SPIDefinition)result;
		}
	}

	/**
	 * Removes the spi definition where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the spi definition that was removed
	 */
	@Override
	public SPIDefinition removeByC_N(long companyId, String name)
		throws NoSuchDefinitionException {
		SPIDefinition spiDefinition = findByC_N(companyId, name);

		return remove(spiDefinition);
	}

	/**
	 * Returns the number of spi definitions where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching spi definitions
	 */
	@Override
	public int countByC_N(long companyId, String name) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_N;

		Object[] finderArgs = new Object[] { companyId, name };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SPIDEFINITION_WHERE);

			query.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_C_N_NAME_1);
			}
			else if (name.equals("")) {
				query.append(_FINDER_COLUMN_C_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindName) {
					qPos.add(name);
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

	private static final String _FINDER_COLUMN_C_N_COMPANYID_2 = "spiDefinition.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_N_NAME_1 = "spiDefinition.name IS NULL";
	private static final String _FINDER_COLUMN_C_N_NAME_2 = "spiDefinition.name = ?";
	private static final String _FINDER_COLUMN_C_N_NAME_3 = "(spiDefinition.name IS NULL OR spiDefinition.name = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_S = new FinderPath(SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			SPIDefinitionModelImpl.FINDER_CACHE_ENABLED,
			SPIDefinitionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_S = new FinderPath(SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			SPIDefinitionModelImpl.FINDER_CACHE_ENABLED,
			SPIDefinitionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByC_S",
			new String[] { Long.class.getName(), Integer.class.getName() },
			SPIDefinitionModelImpl.COMPANYID_COLUMN_BITMASK |
			SPIDefinitionModelImpl.STATUS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_S = new FinderPath(SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			SPIDefinitionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_S",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_S = new FinderPath(SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			SPIDefinitionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_S",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the spi definitions where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching spi definitions
	 */
	@Override
	public List<SPIDefinition> findByC_S(long companyId, int status) {
		return findByC_S(companyId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the spi definitions where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SPIDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @return the range of matching spi definitions
	 */
	@Override
	public List<SPIDefinition> findByC_S(long companyId, int status, int start,
		int end) {
		return findByC_S(companyId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the spi definitions where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SPIDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching spi definitions
	 */
	@Override
	public List<SPIDefinition> findByC_S(long companyId, int status, int start,
		int end, OrderByComparator<SPIDefinition> orderByComparator) {
		return findByC_S(companyId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the spi definitions where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SPIDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching spi definitions
	 */
	@Override
	public List<SPIDefinition> findByC_S(long companyId, int status, int start,
		int end, OrderByComparator<SPIDefinition> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_S;
			finderArgs = new Object[] { companyId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_S;
			finderArgs = new Object[] {
					companyId, status,
					
					start, end, orderByComparator
				};
		}

		List<SPIDefinition> list = null;

		if (retrieveFromCache) {
			list = (List<SPIDefinition>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SPIDefinition spiDefinition : list) {
					if ((companyId != spiDefinition.getCompanyId()) ||
							(status != spiDefinition.getStatus())) {
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

			query.append(_SQL_SELECT_SPIDEFINITION_WHERE);

			query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SPIDefinitionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(status);

				if (!pagination) {
					list = (List<SPIDefinition>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SPIDefinition>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first spi definition in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching spi definition
	 * @throws NoSuchDefinitionException if a matching spi definition could not be found
	 */
	@Override
	public SPIDefinition findByC_S_First(long companyId, int status,
		OrderByComparator<SPIDefinition> orderByComparator)
		throws NoSuchDefinitionException {
		SPIDefinition spiDefinition = fetchByC_S_First(companyId, status,
				orderByComparator);

		if (spiDefinition != null) {
			return spiDefinition;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchDefinitionException(msg.toString());
	}

	/**
	 * Returns the first spi definition in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching spi definition, or <code>null</code> if a matching spi definition could not be found
	 */
	@Override
	public SPIDefinition fetchByC_S_First(long companyId, int status,
		OrderByComparator<SPIDefinition> orderByComparator) {
		List<SPIDefinition> list = findByC_S(companyId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last spi definition in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching spi definition
	 * @throws NoSuchDefinitionException if a matching spi definition could not be found
	 */
	@Override
	public SPIDefinition findByC_S_Last(long companyId, int status,
		OrderByComparator<SPIDefinition> orderByComparator)
		throws NoSuchDefinitionException {
		SPIDefinition spiDefinition = fetchByC_S_Last(companyId, status,
				orderByComparator);

		if (spiDefinition != null) {
			return spiDefinition;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchDefinitionException(msg.toString());
	}

	/**
	 * Returns the last spi definition in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching spi definition, or <code>null</code> if a matching spi definition could not be found
	 */
	@Override
	public SPIDefinition fetchByC_S_Last(long companyId, int status,
		OrderByComparator<SPIDefinition> orderByComparator) {
		int count = countByC_S(companyId, status);

		if (count == 0) {
			return null;
		}

		List<SPIDefinition> list = findByC_S(companyId, status, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the spi definitions before and after the current spi definition in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param spiDefinitionId the primary key of the current spi definition
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next spi definition
	 * @throws NoSuchDefinitionException if a spi definition with the primary key could not be found
	 */
	@Override
	public SPIDefinition[] findByC_S_PrevAndNext(long spiDefinitionId,
		long companyId, int status,
		OrderByComparator<SPIDefinition> orderByComparator)
		throws NoSuchDefinitionException {
		SPIDefinition spiDefinition = findByPrimaryKey(spiDefinitionId);

		Session session = null;

		try {
			session = openSession();

			SPIDefinition[] array = new SPIDefinitionImpl[3];

			array[0] = getByC_S_PrevAndNext(session, spiDefinition, companyId,
					status, orderByComparator, true);

			array[1] = spiDefinition;

			array[2] = getByC_S_PrevAndNext(session, spiDefinition, companyId,
					status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SPIDefinition getByC_S_PrevAndNext(Session session,
		SPIDefinition spiDefinition, long companyId, int status,
		OrderByComparator<SPIDefinition> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_SPIDEFINITION_WHERE);

		query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_S_STATUS_2);

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
			query.append(SPIDefinitionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(spiDefinition);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SPIDefinition> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the spi definitions that the user has permission to view where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching spi definitions that the user has permission to view
	 */
	@Override
	public List<SPIDefinition> filterFindByC_S(long companyId, int status) {
		return filterFindByC_S(companyId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the spi definitions that the user has permission to view where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SPIDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @return the range of matching spi definitions that the user has permission to view
	 */
	@Override
	public List<SPIDefinition> filterFindByC_S(long companyId, int status,
		int start, int end) {
		return filterFindByC_S(companyId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the spi definitions that the user has permissions to view where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SPIDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching spi definitions that the user has permission to view
	 */
	@Override
	public List<SPIDefinition> filterFindByC_S(long companyId, int status,
		int start, int end, OrderByComparator<SPIDefinition> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_S(companyId, status, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SPIDEFINITION_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SPIDEFINITION_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SPIDEFINITION_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(SPIDefinitionModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SPIDefinitionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SPIDefinition.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, SPIDefinitionImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, SPIDefinitionImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			qPos.add(status);

			return (List<SPIDefinition>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the spi definitions before and after the current spi definition in the ordered set of spi definitions that the user has permission to view where companyId = &#63; and status = &#63;.
	 *
	 * @param spiDefinitionId the primary key of the current spi definition
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next spi definition
	 * @throws NoSuchDefinitionException if a spi definition with the primary key could not be found
	 */
	@Override
	public SPIDefinition[] filterFindByC_S_PrevAndNext(long spiDefinitionId,
		long companyId, int status,
		OrderByComparator<SPIDefinition> orderByComparator)
		throws NoSuchDefinitionException {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_S_PrevAndNext(spiDefinitionId, companyId, status,
				orderByComparator);
		}

		SPIDefinition spiDefinition = findByPrimaryKey(spiDefinitionId);

		Session session = null;

		try {
			session = openSession();

			SPIDefinition[] array = new SPIDefinitionImpl[3];

			array[0] = filterGetByC_S_PrevAndNext(session, spiDefinition,
					companyId, status, orderByComparator, true);

			array[1] = spiDefinition;

			array[2] = filterGetByC_S_PrevAndNext(session, spiDefinition,
					companyId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SPIDefinition filterGetByC_S_PrevAndNext(Session session,
		SPIDefinition spiDefinition, long companyId, int status,
		OrderByComparator<SPIDefinition> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SPIDEFINITION_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SPIDEFINITION_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SPIDEFINITION_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(SPIDefinitionModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SPIDefinitionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SPIDefinition.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, SPIDefinitionImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, SPIDefinitionImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(spiDefinition);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SPIDefinition> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the spi definitions that the user has permission to view where companyId = &#63; and status = any &#63;.
	 *
	 * @param companyId the company ID
	 * @param statuses the statuses
	 * @return the matching spi definitions that the user has permission to view
	 */
	@Override
	public List<SPIDefinition> filterFindByC_S(long companyId, int[] statuses) {
		return filterFindByC_S(companyId, statuses, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the spi definitions that the user has permission to view where companyId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SPIDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @return the range of matching spi definitions that the user has permission to view
	 */
	@Override
	public List<SPIDefinition> filterFindByC_S(long companyId, int[] statuses,
		int start, int end) {
		return filterFindByC_S(companyId, statuses, start, end, null);
	}

	/**
	 * Returns an ordered range of all the spi definitions that the user has permission to view where companyId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SPIDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching spi definitions that the user has permission to view
	 */
	@Override
	public List<SPIDefinition> filterFindByC_S(long companyId, int[] statuses,
		int start, int end, OrderByComparator<SPIDefinition> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_S(companyId, statuses, start, end, orderByComparator);
		}

		if (statuses == null) {
			statuses = new int[0];
		}
		else if (statuses.length > 1) {
			statuses = ArrayUtil.unique(statuses);

			Arrays.sort(statuses);
		}

		StringBundler query = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SPIDEFINITION_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SPIDEFINITION_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

		if (statuses.length > 0) {
			query.append("(");

			query.append(_FINDER_COLUMN_C_S_STATUS_7);

			query.append(StringUtil.merge(statuses));

			query.append(")");

			query.append(")");
		}

		query.setStringAt(removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SPIDEFINITION_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(SPIDefinitionModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SPIDefinitionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SPIDefinition.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, SPIDefinitionImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, SPIDefinitionImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			return (List<SPIDefinition>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns all the spi definitions where companyId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SPIDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param statuses the statuses
	 * @return the matching spi definitions
	 */
	@Override
	public List<SPIDefinition> findByC_S(long companyId, int[] statuses) {
		return findByC_S(companyId, statuses, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the spi definitions where companyId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SPIDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @return the range of matching spi definitions
	 */
	@Override
	public List<SPIDefinition> findByC_S(long companyId, int[] statuses,
		int start, int end) {
		return findByC_S(companyId, statuses, start, end, null);
	}

	/**
	 * Returns an ordered range of all the spi definitions where companyId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SPIDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching spi definitions
	 */
	@Override
	public List<SPIDefinition> findByC_S(long companyId, int[] statuses,
		int start, int end, OrderByComparator<SPIDefinition> orderByComparator) {
		return findByC_S(companyId, statuses, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the spi definitions where companyId = &#63; and status = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SPIDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching spi definitions
	 */
	@Override
	public List<SPIDefinition> findByC_S(long companyId, int[] statuses,
		int start, int end, OrderByComparator<SPIDefinition> orderByComparator,
		boolean retrieveFromCache) {
		if (statuses == null) {
			statuses = new int[0];
		}
		else if (statuses.length > 1) {
			statuses = ArrayUtil.unique(statuses);

			Arrays.sort(statuses);
		}

		if (statuses.length == 1) {
			return findByC_S(companyId, statuses[0], start, end,
				orderByComparator);
		}

		boolean pagination = true;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderArgs = new Object[] { companyId, StringUtil.merge(statuses) };
		}
		else {
			finderArgs = new Object[] {
					companyId, StringUtil.merge(statuses),
					
					start, end, orderByComparator
				};
		}

		List<SPIDefinition> list = null;

		if (retrieveFromCache) {
			list = (List<SPIDefinition>)finderCache.getResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_C_S,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SPIDefinition spiDefinition : list) {
					if ((companyId != spiDefinition.getCompanyId()) ||
							!ArrayUtil.contains(statuses,
								spiDefinition.getStatus())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_SPIDEFINITION_WHERE);

			query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

			if (statuses.length > 0) {
				query.append("(");

				query.append(_FINDER_COLUMN_C_S_STATUS_7);

				query.append(StringUtil.merge(statuses));

				query.append(")");

				query.append(")");
			}

			query.setStringAt(removeConjunction(query.stringAt(query.index() -
						1)), query.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SPIDefinitionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<SPIDefinition>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SPIDefinition>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_C_S,
					finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_C_S,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the spi definitions where companyId = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 */
	@Override
	public void removeByC_S(long companyId, int status) {
		for (SPIDefinition spiDefinition : findByC_S(companyId, status,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(spiDefinition);
		}
	}

	/**
	 * Returns the number of spi definitions where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching spi definitions
	 */
	@Override
	public int countByC_S(long companyId, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_S;

		Object[] finderArgs = new Object[] { companyId, status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SPIDEFINITION_WHERE);

			query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(status);

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

	/**
	 * Returns the number of spi definitions where companyId = &#63; and status = any &#63;.
	 *
	 * @param companyId the company ID
	 * @param statuses the statuses
	 * @return the number of matching spi definitions
	 */
	@Override
	public int countByC_S(long companyId, int[] statuses) {
		if (statuses == null) {
			statuses = new int[0];
		}
		else if (statuses.length > 1) {
			statuses = ArrayUtil.unique(statuses);

			Arrays.sort(statuses);
		}

		Object[] finderArgs = new Object[] { companyId, StringUtil.merge(statuses) };

		Long count = (Long)finderCache.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_SPIDEFINITION_WHERE);

			query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

			if (statuses.length > 0) {
				query.append("(");

				query.append(_FINDER_COLUMN_C_S_STATUS_7);

				query.append(StringUtil.merge(statuses));

				query.append(")");

				query.append(")");
			}

			query.setStringAt(removeConjunction(query.stringAt(query.index() -
						1)), query.index() - 1);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_S,
					finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_S,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of spi definitions that the user has permission to view where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching spi definitions that the user has permission to view
	 */
	@Override
	public int filterCountByC_S(long companyId, int status) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByC_S(companyId, status);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_SPIDEFINITION_WHERE);

		query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SPIDefinition.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			qPos.add(status);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the number of spi definitions that the user has permission to view where companyId = &#63; and status = any &#63;.
	 *
	 * @param companyId the company ID
	 * @param statuses the statuses
	 * @return the number of matching spi definitions that the user has permission to view
	 */
	@Override
	public int filterCountByC_S(long companyId, int[] statuses) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByC_S(companyId, statuses);
		}

		if (statuses == null) {
			statuses = new int[0];
		}
		else if (statuses.length > 1) {
			statuses = ArrayUtil.unique(statuses);

			Arrays.sort(statuses);
		}

		StringBundler query = new StringBundler();

		query.append(_FILTER_SQL_COUNT_SPIDEFINITION_WHERE);

		query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

		if (statuses.length > 0) {
			query.append("(");

			query.append(_FINDER_COLUMN_C_S_STATUS_7);

			query.append(StringUtil.merge(statuses));

			query.append(")");

			query.append(")");
		}

		query.setStringAt(removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SPIDefinition.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_C_S_COMPANYID_2 = "spiDefinition.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_S_STATUS_2 = "spiDefinition.status = ?";
	private static final String _FINDER_COLUMN_C_S_STATUS_7 = "spiDefinition.status IN (";
	public static final FinderPath FINDER_PATH_FETCH_BY_CA_CP = new FinderPath(SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			SPIDefinitionModelImpl.FINDER_CACHE_ENABLED,
			SPIDefinitionImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByCA_CP",
			new String[] { String.class.getName(), Integer.class.getName() },
			SPIDefinitionModelImpl.CONNECTORADDRESS_COLUMN_BITMASK |
			SPIDefinitionModelImpl.CONNECTORPORT_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CA_CP = new FinderPath(SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			SPIDefinitionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCA_CP",
			new String[] { String.class.getName(), Integer.class.getName() });

	/**
	 * Returns the spi definition where connectorAddress = &#63; and connectorPort = &#63; or throws a {@link NoSuchDefinitionException} if it could not be found.
	 *
	 * @param connectorAddress the connector address
	 * @param connectorPort the connector port
	 * @return the matching spi definition
	 * @throws NoSuchDefinitionException if a matching spi definition could not be found
	 */
	@Override
	public SPIDefinition findByCA_CP(String connectorAddress, int connectorPort)
		throws NoSuchDefinitionException {
		SPIDefinition spiDefinition = fetchByCA_CP(connectorAddress,
				connectorPort);

		if (spiDefinition == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("connectorAddress=");
			msg.append(connectorAddress);

			msg.append(", connectorPort=");
			msg.append(connectorPort);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchDefinitionException(msg.toString());
		}

		return spiDefinition;
	}

	/**
	 * Returns the spi definition where connectorAddress = &#63; and connectorPort = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param connectorAddress the connector address
	 * @param connectorPort the connector port
	 * @return the matching spi definition, or <code>null</code> if a matching spi definition could not be found
	 */
	@Override
	public SPIDefinition fetchByCA_CP(String connectorAddress, int connectorPort) {
		return fetchByCA_CP(connectorAddress, connectorPort, true);
	}

	/**
	 * Returns the spi definition where connectorAddress = &#63; and connectorPort = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param connectorAddress the connector address
	 * @param connectorPort the connector port
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching spi definition, or <code>null</code> if a matching spi definition could not be found
	 */
	@Override
	public SPIDefinition fetchByCA_CP(String connectorAddress,
		int connectorPort, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { connectorAddress, connectorPort };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_CA_CP,
					finderArgs, this);
		}

		if (result instanceof SPIDefinition) {
			SPIDefinition spiDefinition = (SPIDefinition)result;

			if (!Objects.equals(connectorAddress,
						spiDefinition.getConnectorAddress()) ||
					(connectorPort != spiDefinition.getConnectorPort())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_SPIDEFINITION_WHERE);

			boolean bindConnectorAddress = false;

			if (connectorAddress == null) {
				query.append(_FINDER_COLUMN_CA_CP_CONNECTORADDRESS_1);
			}
			else if (connectorAddress.equals("")) {
				query.append(_FINDER_COLUMN_CA_CP_CONNECTORADDRESS_3);
			}
			else {
				bindConnectorAddress = true;

				query.append(_FINDER_COLUMN_CA_CP_CONNECTORADDRESS_2);
			}

			query.append(_FINDER_COLUMN_CA_CP_CONNECTORPORT_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindConnectorAddress) {
					qPos.add(connectorAddress);
				}

				qPos.add(connectorPort);

				List<SPIDefinition> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_CA_CP,
						finderArgs, list);
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							_log.warn(
								"SPIDefinitionPersistenceImpl.fetchByCA_CP(String, int, boolean) with parameters (" +
								StringUtil.merge(finderArgs) +
								") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					SPIDefinition spiDefinition = list.get(0);

					result = spiDefinition;

					cacheResult(spiDefinition);

					if ((spiDefinition.getConnectorAddress() == null) ||
							!spiDefinition.getConnectorAddress()
											  .equals(connectorAddress) ||
							(spiDefinition.getConnectorPort() != connectorPort)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_CA_CP,
							finderArgs, spiDefinition);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_CA_CP, finderArgs);

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
			return (SPIDefinition)result;
		}
	}

	/**
	 * Removes the spi definition where connectorAddress = &#63; and connectorPort = &#63; from the database.
	 *
	 * @param connectorAddress the connector address
	 * @param connectorPort the connector port
	 * @return the spi definition that was removed
	 */
	@Override
	public SPIDefinition removeByCA_CP(String connectorAddress,
		int connectorPort) throws NoSuchDefinitionException {
		SPIDefinition spiDefinition = findByCA_CP(connectorAddress,
				connectorPort);

		return remove(spiDefinition);
	}

	/**
	 * Returns the number of spi definitions where connectorAddress = &#63; and connectorPort = &#63;.
	 *
	 * @param connectorAddress the connector address
	 * @param connectorPort the connector port
	 * @return the number of matching spi definitions
	 */
	@Override
	public int countByCA_CP(String connectorAddress, int connectorPort) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_CA_CP;

		Object[] finderArgs = new Object[] { connectorAddress, connectorPort };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SPIDEFINITION_WHERE);

			boolean bindConnectorAddress = false;

			if (connectorAddress == null) {
				query.append(_FINDER_COLUMN_CA_CP_CONNECTORADDRESS_1);
			}
			else if (connectorAddress.equals("")) {
				query.append(_FINDER_COLUMN_CA_CP_CONNECTORADDRESS_3);
			}
			else {
				bindConnectorAddress = true;

				query.append(_FINDER_COLUMN_CA_CP_CONNECTORADDRESS_2);
			}

			query.append(_FINDER_COLUMN_CA_CP_CONNECTORPORT_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindConnectorAddress) {
					qPos.add(connectorAddress);
				}

				qPos.add(connectorPort);

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

	private static final String _FINDER_COLUMN_CA_CP_CONNECTORADDRESS_1 = "spiDefinition.connectorAddress IS NULL AND ";
	private static final String _FINDER_COLUMN_CA_CP_CONNECTORADDRESS_2 = "spiDefinition.connectorAddress = ? AND ";
	private static final String _FINDER_COLUMN_CA_CP_CONNECTORADDRESS_3 = "(spiDefinition.connectorAddress IS NULL OR spiDefinition.connectorAddress = '') AND ";
	private static final String _FINDER_COLUMN_CA_CP_CONNECTORPORT_2 = "spiDefinition.connectorPort = ?";

	public SPIDefinitionPersistenceImpl() {
		setModelClass(SPIDefinition.class);
	}

	/**
	 * Caches the spi definition in the entity cache if it is enabled.
	 *
	 * @param spiDefinition the spi definition
	 */
	@Override
	public void cacheResult(SPIDefinition spiDefinition) {
		entityCache.putResult(SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			SPIDefinitionImpl.class, spiDefinition.getPrimaryKey(),
			spiDefinition);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_N,
			new Object[] { spiDefinition.getCompanyId(), spiDefinition.getName() },
			spiDefinition);

		finderCache.putResult(FINDER_PATH_FETCH_BY_CA_CP,
			new Object[] {
				spiDefinition.getConnectorAddress(),
				spiDefinition.getConnectorPort()
			}, spiDefinition);

		spiDefinition.resetOriginalValues();
	}

	/**
	 * Caches the spi definitions in the entity cache if it is enabled.
	 *
	 * @param spiDefinitions the spi definitions
	 */
	@Override
	public void cacheResult(List<SPIDefinition> spiDefinitions) {
		for (SPIDefinition spiDefinition : spiDefinitions) {
			if (entityCache.getResult(
						SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
						SPIDefinitionImpl.class, spiDefinition.getPrimaryKey()) == null) {
				cacheResult(spiDefinition);
			}
			else {
				spiDefinition.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all spi definitions.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(SPIDefinitionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the spi definition.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SPIDefinition spiDefinition) {
		entityCache.removeResult(SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			SPIDefinitionImpl.class, spiDefinition.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((SPIDefinitionModelImpl)spiDefinition, true);
	}

	@Override
	public void clearCache(List<SPIDefinition> spiDefinitions) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SPIDefinition spiDefinition : spiDefinitions) {
			entityCache.removeResult(SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
				SPIDefinitionImpl.class, spiDefinition.getPrimaryKey());

			clearUniqueFindersCache((SPIDefinitionModelImpl)spiDefinition, true);
		}
	}

	protected void cacheUniqueFindersCache(
		SPIDefinitionModelImpl spiDefinitionModelImpl) {
		Object[] args = new Object[] {
				spiDefinitionModelImpl.getCompanyId(),
				spiDefinitionModelImpl.getName()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_C_N, args, Long.valueOf(1),
			false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_C_N, args,
			spiDefinitionModelImpl, false);

		args = new Object[] {
				spiDefinitionModelImpl.getConnectorAddress(),
				spiDefinitionModelImpl.getConnectorPort()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_CA_CP, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_CA_CP, args,
			spiDefinitionModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		SPIDefinitionModelImpl spiDefinitionModelImpl, boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					spiDefinitionModelImpl.getCompanyId(),
					spiDefinitionModelImpl.getName()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_N, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_N, args);
		}

		if ((spiDefinitionModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_N.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					spiDefinitionModelImpl.getOriginalCompanyId(),
					spiDefinitionModelImpl.getOriginalName()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_N, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_N, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					spiDefinitionModelImpl.getConnectorAddress(),
					spiDefinitionModelImpl.getConnectorPort()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_CA_CP, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_CA_CP, args);
		}

		if ((spiDefinitionModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_CA_CP.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					spiDefinitionModelImpl.getOriginalConnectorAddress(),
					spiDefinitionModelImpl.getOriginalConnectorPort()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_CA_CP, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_CA_CP, args);
		}
	}

	/**
	 * Creates a new spi definition with the primary key. Does not add the spi definition to the database.
	 *
	 * @param spiDefinitionId the primary key for the new spi definition
	 * @return the new spi definition
	 */
	@Override
	public SPIDefinition create(long spiDefinitionId) {
		SPIDefinition spiDefinition = new SPIDefinitionImpl();

		spiDefinition.setNew(true);
		spiDefinition.setPrimaryKey(spiDefinitionId);

		spiDefinition.setCompanyId(companyProvider.getCompanyId());

		return spiDefinition;
	}

	/**
	 * Removes the spi definition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param spiDefinitionId the primary key of the spi definition
	 * @return the spi definition that was removed
	 * @throws NoSuchDefinitionException if a spi definition with the primary key could not be found
	 */
	@Override
	public SPIDefinition remove(long spiDefinitionId)
		throws NoSuchDefinitionException {
		return remove((Serializable)spiDefinitionId);
	}

	/**
	 * Removes the spi definition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the spi definition
	 * @return the spi definition that was removed
	 * @throws NoSuchDefinitionException if a spi definition with the primary key could not be found
	 */
	@Override
	public SPIDefinition remove(Serializable primaryKey)
		throws NoSuchDefinitionException {
		Session session = null;

		try {
			session = openSession();

			SPIDefinition spiDefinition = (SPIDefinition)session.get(SPIDefinitionImpl.class,
					primaryKey);

			if (spiDefinition == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchDefinitionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(spiDefinition);
		}
		catch (NoSuchDefinitionException nsee) {
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
	protected SPIDefinition removeImpl(SPIDefinition spiDefinition) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(spiDefinition)) {
				spiDefinition = (SPIDefinition)session.get(SPIDefinitionImpl.class,
						spiDefinition.getPrimaryKeyObj());
			}

			if (spiDefinition != null) {
				session.delete(spiDefinition);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (spiDefinition != null) {
			clearCache(spiDefinition);
		}

		return spiDefinition;
	}

	@Override
	public SPIDefinition updateImpl(SPIDefinition spiDefinition) {
		boolean isNew = spiDefinition.isNew();

		if (!(spiDefinition instanceof SPIDefinitionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(spiDefinition.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(spiDefinition);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in spiDefinition proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom SPIDefinition implementation " +
				spiDefinition.getClass());
		}

		SPIDefinitionModelImpl spiDefinitionModelImpl = (SPIDefinitionModelImpl)spiDefinition;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (spiDefinition.getCreateDate() == null)) {
			if (serviceContext == null) {
				spiDefinition.setCreateDate(now);
			}
			else {
				spiDefinition.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!spiDefinitionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				spiDefinition.setModifiedDate(now);
			}
			else {
				spiDefinition.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (spiDefinition.isNew()) {
				session.save(spiDefinition);

				spiDefinition.setNew(false);
			}
			else {
				spiDefinition = (SPIDefinition)session.merge(spiDefinition);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!SPIDefinitionModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] { spiDefinitionModelImpl.getCompanyId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
				args);

			args = new Object[] {
					spiDefinitionModelImpl.getCompanyId(),
					spiDefinitionModelImpl.getStatus()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_S, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_S,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((spiDefinitionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						spiDefinitionModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] { spiDefinitionModelImpl.getCompanyId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((spiDefinitionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						spiDefinitionModelImpl.getOriginalCompanyId(),
						spiDefinitionModelImpl.getOriginalStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_S,
					args);

				args = new Object[] {
						spiDefinitionModelImpl.getCompanyId(),
						spiDefinitionModelImpl.getStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_S,
					args);
			}
		}

		entityCache.putResult(SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			SPIDefinitionImpl.class, spiDefinition.getPrimaryKey(),
			spiDefinition, false);

		clearUniqueFindersCache(spiDefinitionModelImpl, false);
		cacheUniqueFindersCache(spiDefinitionModelImpl);

		spiDefinition.resetOriginalValues();

		return spiDefinition;
	}

	/**
	 * Returns the spi definition with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the spi definition
	 * @return the spi definition
	 * @throws NoSuchDefinitionException if a spi definition with the primary key could not be found
	 */
	@Override
	public SPIDefinition findByPrimaryKey(Serializable primaryKey)
		throws NoSuchDefinitionException {
		SPIDefinition spiDefinition = fetchByPrimaryKey(primaryKey);

		if (spiDefinition == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchDefinitionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return spiDefinition;
	}

	/**
	 * Returns the spi definition with the primary key or throws a {@link NoSuchDefinitionException} if it could not be found.
	 *
	 * @param spiDefinitionId the primary key of the spi definition
	 * @return the spi definition
	 * @throws NoSuchDefinitionException if a spi definition with the primary key could not be found
	 */
	@Override
	public SPIDefinition findByPrimaryKey(long spiDefinitionId)
		throws NoSuchDefinitionException {
		return findByPrimaryKey((Serializable)spiDefinitionId);
	}

	/**
	 * Returns the spi definition with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the spi definition
	 * @return the spi definition, or <code>null</code> if a spi definition with the primary key could not be found
	 */
	@Override
	public SPIDefinition fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
				SPIDefinitionImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		SPIDefinition spiDefinition = (SPIDefinition)serializable;

		if (spiDefinition == null) {
			Session session = null;

			try {
				session = openSession();

				spiDefinition = (SPIDefinition)session.get(SPIDefinitionImpl.class,
						primaryKey);

				if (spiDefinition != null) {
					cacheResult(spiDefinition);
				}
				else {
					entityCache.putResult(SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
						SPIDefinitionImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
					SPIDefinitionImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return spiDefinition;
	}

	/**
	 * Returns the spi definition with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param spiDefinitionId the primary key of the spi definition
	 * @return the spi definition, or <code>null</code> if a spi definition with the primary key could not be found
	 */
	@Override
	public SPIDefinition fetchByPrimaryKey(long spiDefinitionId) {
		return fetchByPrimaryKey((Serializable)spiDefinitionId);
	}

	@Override
	public Map<Serializable, SPIDefinition> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, SPIDefinition> map = new HashMap<Serializable, SPIDefinition>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			SPIDefinition spiDefinition = fetchByPrimaryKey(primaryKey);

			if (spiDefinition != null) {
				map.put(primaryKey, spiDefinition);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
					SPIDefinitionImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (SPIDefinition)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_SPIDEFINITION_WHERE_PKS_IN);

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

			for (SPIDefinition spiDefinition : (List<SPIDefinition>)q.list()) {
				map.put(spiDefinition.getPrimaryKeyObj(), spiDefinition);

				cacheResult(spiDefinition);

				uncachedPrimaryKeys.remove(spiDefinition.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
					SPIDefinitionImpl.class, primaryKey, nullModel);
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
	 * Returns all the spi definitions.
	 *
	 * @return the spi definitions
	 */
	@Override
	public List<SPIDefinition> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the spi definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SPIDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @return the range of spi definitions
	 */
	@Override
	public List<SPIDefinition> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the spi definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SPIDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of spi definitions
	 */
	@Override
	public List<SPIDefinition> findAll(int start, int end,
		OrderByComparator<SPIDefinition> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the spi definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SPIDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of spi definitions
	 */
	@Override
	public List<SPIDefinition> findAll(int start, int end,
		OrderByComparator<SPIDefinition> orderByComparator,
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

		List<SPIDefinition> list = null;

		if (retrieveFromCache) {
			list = (List<SPIDefinition>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_SPIDEFINITION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SPIDEFINITION;

				if (pagination) {
					sql = sql.concat(SPIDefinitionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<SPIDefinition>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SPIDefinition>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the spi definitions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SPIDefinition spiDefinition : findAll()) {
			remove(spiDefinition);
		}
	}

	/**
	 * Returns the number of spi definitions.
	 *
	 * @return the number of spi definitions
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SPIDEFINITION);

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
		return SPIDefinitionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the spi definition persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(SPIDefinitionImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	protected EntityCache entityCache = EntityCacheUtil.getEntityCache();
	protected FinderCache finderCache = FinderCacheUtil.getFinderCache();
	private static final String _SQL_SELECT_SPIDEFINITION = "SELECT spiDefinition FROM SPIDefinition spiDefinition";
	private static final String _SQL_SELECT_SPIDEFINITION_WHERE_PKS_IN = "SELECT spiDefinition FROM SPIDefinition spiDefinition WHERE spiDefinitionId IN (";
	private static final String _SQL_SELECT_SPIDEFINITION_WHERE = "SELECT spiDefinition FROM SPIDefinition spiDefinition WHERE ";
	private static final String _SQL_COUNT_SPIDEFINITION = "SELECT COUNT(spiDefinition) FROM SPIDefinition spiDefinition";
	private static final String _SQL_COUNT_SPIDEFINITION_WHERE = "SELECT COUNT(spiDefinition) FROM SPIDefinition spiDefinition WHERE ";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "spiDefinition.spiDefinitionId";
	private static final String _FILTER_SQL_SELECT_SPIDEFINITION_WHERE = "SELECT DISTINCT {spiDefinition.*} FROM SPIDefinition spiDefinition WHERE ";
	private static final String _FILTER_SQL_SELECT_SPIDEFINITION_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {SPIDefinition.*} FROM (SELECT DISTINCT spiDefinition.spiDefinitionId FROM SPIDefinition spiDefinition WHERE ";
	private static final String _FILTER_SQL_SELECT_SPIDEFINITION_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN SPIDefinition ON TEMP_TABLE.spiDefinitionId = SPIDefinition.spiDefinitionId";
	private static final String _FILTER_SQL_COUNT_SPIDEFINITION_WHERE = "SELECT COUNT(DISTINCT spiDefinition.spiDefinitionId) AS COUNT_VALUE FROM SPIDefinition spiDefinition WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "spiDefinition";
	private static final String _FILTER_ENTITY_TABLE = "SPIDefinition";
	private static final String _ORDER_BY_ENTITY_ALIAS = "spiDefinition.";
	private static final String _ORDER_BY_ENTITY_TABLE = "SPIDefinition.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SPIDefinition exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SPIDefinition exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(SPIDefinitionPersistenceImpl.class);
}