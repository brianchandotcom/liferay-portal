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

package com.liferay.change.tracking.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.change.tracking.exception.NoSuchEntryAggregateException;
import com.liferay.change.tracking.model.CTEntryAggregate;
import com.liferay.change.tracking.model.impl.CTEntryAggregateImpl;
import com.liferay.change.tracking.model.impl.CTEntryAggregateModelImpl;
import com.liferay.change.tracking.service.persistence.CTCollectionPersistence;
import com.liferay.change.tracking.service.persistence.CTEntryAggregatePersistence;
import com.liferay.change.tracking.service.persistence.CTEntryPersistence;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.BeanReference;
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
import com.liferay.portal.kernel.service.persistence.impl.TableMapper;
import com.liferay.portal.kernel.service.persistence.impl.TableMapperFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the ct entry aggregate service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class CTEntryAggregatePersistenceImpl
	extends BasePersistenceImpl<CTEntryAggregate>
	implements CTEntryAggregatePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CTEntryAggregateUtil</code> to access the ct entry aggregate persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CTEntryAggregateImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByOwnerCTEntryID;
	private FinderPath _finderPathWithoutPaginationFindByOwnerCTEntryID;
	private FinderPath _finderPathCountByOwnerCTEntryID;

	/**
	 * Returns all the ct entry aggregates where ownerCTEntryId = &#63;.
	 *
	 * @param ownerCTEntryId the owner ct entry ID
	 * @return the matching ct entry aggregates
	 */
	@Override
	public List<CTEntryAggregate> findByOwnerCTEntryID(long ownerCTEntryId) {
		return findByOwnerCTEntryID(
			ownerCTEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct entry aggregates where ownerCTEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryAggregateModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ownerCTEntryId the owner ct entry ID
	 * @param start the lower bound of the range of ct entry aggregates
	 * @param end the upper bound of the range of ct entry aggregates (not inclusive)
	 * @return the range of matching ct entry aggregates
	 */
	@Override
	public List<CTEntryAggregate> findByOwnerCTEntryID(
		long ownerCTEntryId, int start, int end) {

		return findByOwnerCTEntryID(ownerCTEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct entry aggregates where ownerCTEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryAggregateModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ownerCTEntryId the owner ct entry ID
	 * @param start the lower bound of the range of ct entry aggregates
	 * @param end the upper bound of the range of ct entry aggregates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct entry aggregates
	 */
	@Override
	public List<CTEntryAggregate> findByOwnerCTEntryID(
		long ownerCTEntryId, int start, int end,
		OrderByComparator<CTEntryAggregate> orderByComparator) {

		return findByOwnerCTEntryID(
			ownerCTEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct entry aggregates where ownerCTEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryAggregateModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ownerCTEntryId the owner ct entry ID
	 * @param start the lower bound of the range of ct entry aggregates
	 * @param end the upper bound of the range of ct entry aggregates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ct entry aggregates
	 */
	@Override
	public List<CTEntryAggregate> findByOwnerCTEntryID(
		long ownerCTEntryId, int start, int end,
		OrderByComparator<CTEntryAggregate> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByOwnerCTEntryID;
			finderArgs = new Object[] {ownerCTEntryId};
		}
		else {
			finderPath = _finderPathWithPaginationFindByOwnerCTEntryID;
			finderArgs = new Object[] {
				ownerCTEntryId, start, end, orderByComparator
			};
		}

		List<CTEntryAggregate> list = null;

		if (retrieveFromCache) {
			list = (List<CTEntryAggregate>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CTEntryAggregate ctEntryAggregate : list) {
					if ((ownerCTEntryId !=
							ctEntryAggregate.getOwnerCTEntryId())) {

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

			query.append(_SQL_SELECT_CTENTRYAGGREGATE_WHERE);

			query.append(_FINDER_COLUMN_OWNERCTENTRYID_OWNERCTENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(CTEntryAggregateModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ownerCTEntryId);

				if (!pagination) {
					list = (List<CTEntryAggregate>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CTEntryAggregate>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first ct entry aggregate in the ordered set where ownerCTEntryId = &#63;.
	 *
	 * @param ownerCTEntryId the owner ct entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry aggregate
	 * @throws NoSuchEntryAggregateException if a matching ct entry aggregate could not be found
	 */
	@Override
	public CTEntryAggregate findByOwnerCTEntryID_First(
			long ownerCTEntryId,
			OrderByComparator<CTEntryAggregate> orderByComparator)
		throws NoSuchEntryAggregateException {

		CTEntryAggregate ctEntryAggregate = fetchByOwnerCTEntryID_First(
			ownerCTEntryId, orderByComparator);

		if (ctEntryAggregate != null) {
			return ctEntryAggregate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("ownerCTEntryId=");
		msg.append(ownerCTEntryId);

		msg.append("}");

		throw new NoSuchEntryAggregateException(msg.toString());
	}

	/**
	 * Returns the first ct entry aggregate in the ordered set where ownerCTEntryId = &#63;.
	 *
	 * @param ownerCTEntryId the owner ct entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry aggregate, or <code>null</code> if a matching ct entry aggregate could not be found
	 */
	@Override
	public CTEntryAggregate fetchByOwnerCTEntryID_First(
		long ownerCTEntryId,
		OrderByComparator<CTEntryAggregate> orderByComparator) {

		List<CTEntryAggregate> list = findByOwnerCTEntryID(
			ownerCTEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ct entry aggregate in the ordered set where ownerCTEntryId = &#63;.
	 *
	 * @param ownerCTEntryId the owner ct entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry aggregate
	 * @throws NoSuchEntryAggregateException if a matching ct entry aggregate could not be found
	 */
	@Override
	public CTEntryAggregate findByOwnerCTEntryID_Last(
			long ownerCTEntryId,
			OrderByComparator<CTEntryAggregate> orderByComparator)
		throws NoSuchEntryAggregateException {

		CTEntryAggregate ctEntryAggregate = fetchByOwnerCTEntryID_Last(
			ownerCTEntryId, orderByComparator);

		if (ctEntryAggregate != null) {
			return ctEntryAggregate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("ownerCTEntryId=");
		msg.append(ownerCTEntryId);

		msg.append("}");

		throw new NoSuchEntryAggregateException(msg.toString());
	}

	/**
	 * Returns the last ct entry aggregate in the ordered set where ownerCTEntryId = &#63;.
	 *
	 * @param ownerCTEntryId the owner ct entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry aggregate, or <code>null</code> if a matching ct entry aggregate could not be found
	 */
	@Override
	public CTEntryAggregate fetchByOwnerCTEntryID_Last(
		long ownerCTEntryId,
		OrderByComparator<CTEntryAggregate> orderByComparator) {

		int count = countByOwnerCTEntryID(ownerCTEntryId);

		if (count == 0) {
			return null;
		}

		List<CTEntryAggregate> list = findByOwnerCTEntryID(
			ownerCTEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ct entry aggregates before and after the current ct entry aggregate in the ordered set where ownerCTEntryId = &#63;.
	 *
	 * @param ctEntryAggregateId the primary key of the current ct entry aggregate
	 * @param ownerCTEntryId the owner ct entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct entry aggregate
	 * @throws NoSuchEntryAggregateException if a ct entry aggregate with the primary key could not be found
	 */
	@Override
	public CTEntryAggregate[] findByOwnerCTEntryID_PrevAndNext(
			long ctEntryAggregateId, long ownerCTEntryId,
			OrderByComparator<CTEntryAggregate> orderByComparator)
		throws NoSuchEntryAggregateException {

		CTEntryAggregate ctEntryAggregate = findByPrimaryKey(
			ctEntryAggregateId);

		Session session = null;

		try {
			session = openSession();

			CTEntryAggregate[] array = new CTEntryAggregateImpl[3];

			array[0] = getByOwnerCTEntryID_PrevAndNext(
				session, ctEntryAggregate, ownerCTEntryId, orderByComparator,
				true);

			array[1] = ctEntryAggregate;

			array[2] = getByOwnerCTEntryID_PrevAndNext(
				session, ctEntryAggregate, ownerCTEntryId, orderByComparator,
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

	protected CTEntryAggregate getByOwnerCTEntryID_PrevAndNext(
		Session session, CTEntryAggregate ctEntryAggregate, long ownerCTEntryId,
		OrderByComparator<CTEntryAggregate> orderByComparator,
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

		query.append(_SQL_SELECT_CTENTRYAGGREGATE_WHERE);

		query.append(_FINDER_COLUMN_OWNERCTENTRYID_OWNERCTENTRYID_2);

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
			query.append(CTEntryAggregateModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(ownerCTEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						ctEntryAggregate)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<CTEntryAggregate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ct entry aggregates where ownerCTEntryId = &#63; from the database.
	 *
	 * @param ownerCTEntryId the owner ct entry ID
	 */
	@Override
	public void removeByOwnerCTEntryID(long ownerCTEntryId) {
		for (CTEntryAggregate ctEntryAggregate :
				findByOwnerCTEntryID(
					ownerCTEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(ctEntryAggregate);
		}
	}

	/**
	 * Returns the number of ct entry aggregates where ownerCTEntryId = &#63;.
	 *
	 * @param ownerCTEntryId the owner ct entry ID
	 * @return the number of matching ct entry aggregates
	 */
	@Override
	public int countByOwnerCTEntryID(long ownerCTEntryId) {
		FinderPath finderPath = _finderPathCountByOwnerCTEntryID;

		Object[] finderArgs = new Object[] {ownerCTEntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CTENTRYAGGREGATE_WHERE);

			query.append(_FINDER_COLUMN_OWNERCTENTRYID_OWNERCTENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ownerCTEntryId);

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

	private static final String _FINDER_COLUMN_OWNERCTENTRYID_OWNERCTENTRYID_2 =
		"ctEntryAggregate.ownerCTEntryId = ?";

	public CTEntryAggregatePersistenceImpl() {
		setModelClass(CTEntryAggregate.class);

		setModelImplClass(CTEntryAggregateImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(CTEntryAggregateModelImpl.ENTITY_CACHE_ENABLED);
	}

	/**
	 * Caches the ct entry aggregate in the entity cache if it is enabled.
	 *
	 * @param ctEntryAggregate the ct entry aggregate
	 */
	@Override
	public void cacheResult(CTEntryAggregate ctEntryAggregate) {
		entityCache.putResult(
			CTEntryAggregateModelImpl.ENTITY_CACHE_ENABLED,
			CTEntryAggregateImpl.class, ctEntryAggregate.getPrimaryKey(),
			ctEntryAggregate);

		ctEntryAggregate.resetOriginalValues();
	}

	/**
	 * Caches the ct entry aggregates in the entity cache if it is enabled.
	 *
	 * @param ctEntryAggregates the ct entry aggregates
	 */
	@Override
	public void cacheResult(List<CTEntryAggregate> ctEntryAggregates) {
		for (CTEntryAggregate ctEntryAggregate : ctEntryAggregates) {
			if (entityCache.getResult(
					CTEntryAggregateModelImpl.ENTITY_CACHE_ENABLED,
					CTEntryAggregateImpl.class,
					ctEntryAggregate.getPrimaryKey()) == null) {

				cacheResult(ctEntryAggregate);
			}
			else {
				ctEntryAggregate.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all ct entry aggregates.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CTEntryAggregateImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the ct entry aggregate.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CTEntryAggregate ctEntryAggregate) {
		entityCache.removeResult(
			CTEntryAggregateModelImpl.ENTITY_CACHE_ENABLED,
			CTEntryAggregateImpl.class, ctEntryAggregate.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<CTEntryAggregate> ctEntryAggregates) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CTEntryAggregate ctEntryAggregate : ctEntryAggregates) {
			entityCache.removeResult(
				CTEntryAggregateModelImpl.ENTITY_CACHE_ENABLED,
				CTEntryAggregateImpl.class, ctEntryAggregate.getPrimaryKey());
		}
	}

	/**
	 * Creates a new ct entry aggregate with the primary key. Does not add the ct entry aggregate to the database.
	 *
	 * @param ctEntryAggregateId the primary key for the new ct entry aggregate
	 * @return the new ct entry aggregate
	 */
	@Override
	public CTEntryAggregate create(long ctEntryAggregateId) {
		CTEntryAggregate ctEntryAggregate = new CTEntryAggregateImpl();

		ctEntryAggregate.setNew(true);
		ctEntryAggregate.setPrimaryKey(ctEntryAggregateId);

		ctEntryAggregate.setCompanyId(companyProvider.getCompanyId());

		return ctEntryAggregate;
	}

	/**
	 * Removes the ct entry aggregate with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctEntryAggregateId the primary key of the ct entry aggregate
	 * @return the ct entry aggregate that was removed
	 * @throws NoSuchEntryAggregateException if a ct entry aggregate with the primary key could not be found
	 */
	@Override
	public CTEntryAggregate remove(long ctEntryAggregateId)
		throws NoSuchEntryAggregateException {

		return remove((Serializable)ctEntryAggregateId);
	}

	/**
	 * Removes the ct entry aggregate with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the ct entry aggregate
	 * @return the ct entry aggregate that was removed
	 * @throws NoSuchEntryAggregateException if a ct entry aggregate with the primary key could not be found
	 */
	@Override
	public CTEntryAggregate remove(Serializable primaryKey)
		throws NoSuchEntryAggregateException {

		Session session = null;

		try {
			session = openSession();

			CTEntryAggregate ctEntryAggregate = (CTEntryAggregate)session.get(
				CTEntryAggregateImpl.class, primaryKey);

			if (ctEntryAggregate == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryAggregateException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(ctEntryAggregate);
		}
		catch (NoSuchEntryAggregateException nsee) {
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
	protected CTEntryAggregate removeImpl(CTEntryAggregate ctEntryAggregate) {
		ctEntryAggregateToCTCollectionTableMapper.
			deleteLeftPrimaryKeyTableMappings(ctEntryAggregate.getPrimaryKey());

		ctEntryAggregateToCTEntryTableMapper.deleteLeftPrimaryKeyTableMappings(
			ctEntryAggregate.getPrimaryKey());

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ctEntryAggregate)) {
				ctEntryAggregate = (CTEntryAggregate)session.get(
					CTEntryAggregateImpl.class,
					ctEntryAggregate.getPrimaryKeyObj());
			}

			if (ctEntryAggregate != null) {
				session.delete(ctEntryAggregate);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (ctEntryAggregate != null) {
			clearCache(ctEntryAggregate);
		}

		return ctEntryAggregate;
	}

	@Override
	public CTEntryAggregate updateImpl(CTEntryAggregate ctEntryAggregate) {
		boolean isNew = ctEntryAggregate.isNew();

		if (!(ctEntryAggregate instanceof CTEntryAggregateModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(ctEntryAggregate.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					ctEntryAggregate);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in ctEntryAggregate proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CTEntryAggregate implementation " +
					ctEntryAggregate.getClass());
		}

		CTEntryAggregateModelImpl ctEntryAggregateModelImpl =
			(CTEntryAggregateModelImpl)ctEntryAggregate;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (ctEntryAggregate.getCreateDate() == null)) {
			if (serviceContext == null) {
				ctEntryAggregate.setCreateDate(now);
			}
			else {
				ctEntryAggregate.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!ctEntryAggregateModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				ctEntryAggregate.setModifiedDate(now);
			}
			else {
				ctEntryAggregate.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctEntryAggregate.isNew()) {
				session.save(ctEntryAggregate);

				ctEntryAggregate.setNew(false);
			}
			else {
				ctEntryAggregate = (CTEntryAggregate)session.merge(
					ctEntryAggregate);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CTEntryAggregateModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				ctEntryAggregateModelImpl.getOwnerCTEntryId()
			};

			finderCache.removeResult(_finderPathCountByOwnerCTEntryID, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByOwnerCTEntryID, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((ctEntryAggregateModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByOwnerCTEntryID.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					ctEntryAggregateModelImpl.getOriginalOwnerCTEntryId()
				};

				finderCache.removeResult(
					_finderPathCountByOwnerCTEntryID, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByOwnerCTEntryID, args);

				args = new Object[] {
					ctEntryAggregateModelImpl.getOwnerCTEntryId()
				};

				finderCache.removeResult(
					_finderPathCountByOwnerCTEntryID, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByOwnerCTEntryID, args);
			}
		}

		entityCache.putResult(
			CTEntryAggregateModelImpl.ENTITY_CACHE_ENABLED,
			CTEntryAggregateImpl.class, ctEntryAggregate.getPrimaryKey(),
			ctEntryAggregate, false);

		ctEntryAggregate.resetOriginalValues();

		return ctEntryAggregate;
	}

	/**
	 * Returns the ct entry aggregate with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ct entry aggregate
	 * @return the ct entry aggregate
	 * @throws NoSuchEntryAggregateException if a ct entry aggregate with the primary key could not be found
	 */
	@Override
	public CTEntryAggregate findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryAggregateException {

		CTEntryAggregate ctEntryAggregate = fetchByPrimaryKey(primaryKey);

		if (ctEntryAggregate == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryAggregateException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return ctEntryAggregate;
	}

	/**
	 * Returns the ct entry aggregate with the primary key or throws a <code>NoSuchEntryAggregateException</code> if it could not be found.
	 *
	 * @param ctEntryAggregateId the primary key of the ct entry aggregate
	 * @return the ct entry aggregate
	 * @throws NoSuchEntryAggregateException if a ct entry aggregate with the primary key could not be found
	 */
	@Override
	public CTEntryAggregate findByPrimaryKey(long ctEntryAggregateId)
		throws NoSuchEntryAggregateException {

		return findByPrimaryKey((Serializable)ctEntryAggregateId);
	}

	/**
	 * Returns the ct entry aggregate with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ctEntryAggregateId the primary key of the ct entry aggregate
	 * @return the ct entry aggregate, or <code>null</code> if a ct entry aggregate with the primary key could not be found
	 */
	@Override
	public CTEntryAggregate fetchByPrimaryKey(long ctEntryAggregateId) {
		return fetchByPrimaryKey((Serializable)ctEntryAggregateId);
	}

	/**
	 * Returns all the ct entry aggregates.
	 *
	 * @return the ct entry aggregates
	 */
	@Override
	public List<CTEntryAggregate> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct entry aggregates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryAggregateModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entry aggregates
	 * @param end the upper bound of the range of ct entry aggregates (not inclusive)
	 * @return the range of ct entry aggregates
	 */
	@Override
	public List<CTEntryAggregate> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct entry aggregates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryAggregateModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entry aggregates
	 * @param end the upper bound of the range of ct entry aggregates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct entry aggregates
	 */
	@Override
	public List<CTEntryAggregate> findAll(
		int start, int end,
		OrderByComparator<CTEntryAggregate> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct entry aggregates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryAggregateModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entry aggregates
	 * @param end the upper bound of the range of ct entry aggregates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of ct entry aggregates
	 */
	@Override
	public List<CTEntryAggregate> findAll(
		int start, int end,
		OrderByComparator<CTEntryAggregate> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindAll;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<CTEntryAggregate> list = null;

		if (retrieveFromCache) {
			list = (List<CTEntryAggregate>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CTENTRYAGGREGATE);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CTENTRYAGGREGATE;

				if (pagination) {
					sql = sql.concat(CTEntryAggregateModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CTEntryAggregate>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CTEntryAggregate>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Removes all the ct entry aggregates from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CTEntryAggregate ctEntryAggregate : findAll()) {
			remove(ctEntryAggregate);
		}
	}

	/**
	 * Returns the number of ct entry aggregates.
	 *
	 * @return the number of ct entry aggregates
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CTENTRYAGGREGATE);

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
	 * Returns the primaryKeys of ct collections associated with the ct entry aggregate.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @return long[] of the primaryKeys of ct collections associated with the ct entry aggregate
	 */
	@Override
	public long[] getCTCollectionPrimaryKeys(long pk) {
		long[] pks =
			ctEntryAggregateToCTCollectionTableMapper.getRightPrimaryKeys(pk);

		return pks.clone();
	}

	/**
	 * Returns all the ct collections associated with the ct entry aggregate.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @return the ct collections associated with the ct entry aggregate
	 */
	@Override
	public List<com.liferay.change.tracking.model.CTCollection>
		getCTCollections(long pk) {

		return getCTCollections(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the ct collections associated with the ct entry aggregate.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryAggregateModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param start the lower bound of the range of ct entry aggregates
	 * @param end the upper bound of the range of ct entry aggregates (not inclusive)
	 * @return the range of ct collections associated with the ct entry aggregate
	 */
	@Override
	public List<com.liferay.change.tracking.model.CTCollection>
		getCTCollections(long pk, int start, int end) {

		return getCTCollections(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct collections associated with the ct entry aggregate.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryAggregateModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param start the lower bound of the range of ct entry aggregates
	 * @param end the upper bound of the range of ct entry aggregates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct collections associated with the ct entry aggregate
	 */
	@Override
	public List<com.liferay.change.tracking.model.CTCollection>
		getCTCollections(
			long pk, int start, int end,
			OrderByComparator<com.liferay.change.tracking.model.CTCollection>
				orderByComparator) {

		return ctEntryAggregateToCTCollectionTableMapper.getRightBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of ct collections associated with the ct entry aggregate.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @return the number of ct collections associated with the ct entry aggregate
	 */
	@Override
	public int getCTCollectionsSize(long pk) {
		long[] pks =
			ctEntryAggregateToCTCollectionTableMapper.getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the ct collection is associated with the ct entry aggregate.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctCollectionPK the primary key of the ct collection
	 * @return <code>true</code> if the ct collection is associated with the ct entry aggregate; <code>false</code> otherwise
	 */
	@Override
	public boolean containsCTCollection(long pk, long ctCollectionPK) {
		return ctEntryAggregateToCTCollectionTableMapper.containsTableMapping(
			pk, ctCollectionPK);
	}

	/**
	 * Returns <code>true</code> if the ct entry aggregate has any ct collections associated with it.
	 *
	 * @param pk the primary key of the ct entry aggregate to check for associations with ct collections
	 * @return <code>true</code> if the ct entry aggregate has any ct collections associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsCTCollections(long pk) {
		if (getCTCollectionsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the ct entry aggregate and the ct collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctCollectionPK the primary key of the ct collection
	 */
	@Override
	public void addCTCollection(long pk, long ctCollectionPK) {
		CTEntryAggregate ctEntryAggregate = fetchByPrimaryKey(pk);

		if (ctEntryAggregate == null) {
			ctEntryAggregateToCTCollectionTableMapper.addTableMapping(
				companyProvider.getCompanyId(), pk, ctCollectionPK);
		}
		else {
			ctEntryAggregateToCTCollectionTableMapper.addTableMapping(
				ctEntryAggregate.getCompanyId(), pk, ctCollectionPK);
		}
	}

	/**
	 * Adds an association between the ct entry aggregate and the ct collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctCollection the ct collection
	 */
	@Override
	public void addCTCollection(
		long pk, com.liferay.change.tracking.model.CTCollection ctCollection) {

		CTEntryAggregate ctEntryAggregate = fetchByPrimaryKey(pk);

		if (ctEntryAggregate == null) {
			ctEntryAggregateToCTCollectionTableMapper.addTableMapping(
				companyProvider.getCompanyId(), pk,
				ctCollection.getPrimaryKey());
		}
		else {
			ctEntryAggregateToCTCollectionTableMapper.addTableMapping(
				ctEntryAggregate.getCompanyId(), pk,
				ctCollection.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the ct entry aggregate and the ct collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctCollectionPKs the primary keys of the ct collections
	 */
	@Override
	public void addCTCollections(long pk, long[] ctCollectionPKs) {
		long companyId = 0;

		CTEntryAggregate ctEntryAggregate = fetchByPrimaryKey(pk);

		if (ctEntryAggregate == null) {
			companyId = companyProvider.getCompanyId();
		}
		else {
			companyId = ctEntryAggregate.getCompanyId();
		}

		ctEntryAggregateToCTCollectionTableMapper.addTableMappings(
			companyId, pk, ctCollectionPKs);
	}

	/**
	 * Adds an association between the ct entry aggregate and the ct collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctCollections the ct collections
	 */
	@Override
	public void addCTCollections(
		long pk,
		List<com.liferay.change.tracking.model.CTCollection> ctCollections) {

		addCTCollections(
			pk,
			ListUtil.toLongArray(
				ctCollections,
				com.liferay.change.tracking.model.CTCollection.
					CT_COLLECTION_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the ct entry aggregate and its ct collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate to clear the associated ct collections from
	 */
	@Override
	public void clearCTCollections(long pk) {
		ctEntryAggregateToCTCollectionTableMapper.
			deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the ct entry aggregate and the ct collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctCollectionPK the primary key of the ct collection
	 */
	@Override
	public void removeCTCollection(long pk, long ctCollectionPK) {
		ctEntryAggregateToCTCollectionTableMapper.deleteTableMapping(
			pk, ctCollectionPK);
	}

	/**
	 * Removes the association between the ct entry aggregate and the ct collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctCollection the ct collection
	 */
	@Override
	public void removeCTCollection(
		long pk, com.liferay.change.tracking.model.CTCollection ctCollection) {

		ctEntryAggregateToCTCollectionTableMapper.deleteTableMapping(
			pk, ctCollection.getPrimaryKey());
	}

	/**
	 * Removes the association between the ct entry aggregate and the ct collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctCollectionPKs the primary keys of the ct collections
	 */
	@Override
	public void removeCTCollections(long pk, long[] ctCollectionPKs) {
		ctEntryAggregateToCTCollectionTableMapper.deleteTableMappings(
			pk, ctCollectionPKs);
	}

	/**
	 * Removes the association between the ct entry aggregate and the ct collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctCollections the ct collections
	 */
	@Override
	public void removeCTCollections(
		long pk,
		List<com.liferay.change.tracking.model.CTCollection> ctCollections) {

		removeCTCollections(
			pk,
			ListUtil.toLongArray(
				ctCollections,
				com.liferay.change.tracking.model.CTCollection.
					CT_COLLECTION_ID_ACCESSOR));
	}

	/**
	 * Sets the ct collections associated with the ct entry aggregate, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctCollectionPKs the primary keys of the ct collections to be associated with the ct entry aggregate
	 */
	@Override
	public void setCTCollections(long pk, long[] ctCollectionPKs) {
		Set<Long> newCTCollectionPKsSet = SetUtil.fromArray(ctCollectionPKs);
		Set<Long> oldCTCollectionPKsSet = SetUtil.fromArray(
			ctEntryAggregateToCTCollectionTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removeCTCollectionPKsSet = new HashSet<Long>(
			oldCTCollectionPKsSet);

		removeCTCollectionPKsSet.removeAll(newCTCollectionPKsSet);

		ctEntryAggregateToCTCollectionTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeCTCollectionPKsSet));

		newCTCollectionPKsSet.removeAll(oldCTCollectionPKsSet);

		long companyId = 0;

		CTEntryAggregate ctEntryAggregate = fetchByPrimaryKey(pk);

		if (ctEntryAggregate == null) {
			companyId = companyProvider.getCompanyId();
		}
		else {
			companyId = ctEntryAggregate.getCompanyId();
		}

		ctEntryAggregateToCTCollectionTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newCTCollectionPKsSet));
	}

	/**
	 * Sets the ct collections associated with the ct entry aggregate, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctCollections the ct collections to be associated with the ct entry aggregate
	 */
	@Override
	public void setCTCollections(
		long pk,
		List<com.liferay.change.tracking.model.CTCollection> ctCollections) {

		try {
			long[] ctCollectionPKs = new long[ctCollections.size()];

			for (int i = 0; i < ctCollections.size(); i++) {
				com.liferay.change.tracking.model.CTCollection ctCollection =
					ctCollections.get(i);

				ctCollectionPKs[i] = ctCollection.getPrimaryKey();
			}

			setCTCollections(pk, ctCollectionPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
	}

	/**
	 * Returns the primaryKeys of ct entries associated with the ct entry aggregate.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @return long[] of the primaryKeys of ct entries associated with the ct entry aggregate
	 */
	@Override
	public long[] getCTEntryPrimaryKeys(long pk) {
		long[] pks = ctEntryAggregateToCTEntryTableMapper.getRightPrimaryKeys(
			pk);

		return pks.clone();
	}

	/**
	 * Returns all the ct entries associated with the ct entry aggregate.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @return the ct entries associated with the ct entry aggregate
	 */
	@Override
	public List<com.liferay.change.tracking.model.CTEntry> getCTEntries(
		long pk) {

		return getCTEntries(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the ct entries associated with the ct entry aggregate.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryAggregateModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param start the lower bound of the range of ct entry aggregates
	 * @param end the upper bound of the range of ct entry aggregates (not inclusive)
	 * @return the range of ct entries associated with the ct entry aggregate
	 */
	@Override
	public List<com.liferay.change.tracking.model.CTEntry> getCTEntries(
		long pk, int start, int end) {

		return getCTEntries(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct entries associated with the ct entry aggregate.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryAggregateModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param start the lower bound of the range of ct entry aggregates
	 * @param end the upper bound of the range of ct entry aggregates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct entries associated with the ct entry aggregate
	 */
	@Override
	public List<com.liferay.change.tracking.model.CTEntry> getCTEntries(
		long pk, int start, int end,
		OrderByComparator<com.liferay.change.tracking.model.CTEntry>
			orderByComparator) {

		return ctEntryAggregateToCTEntryTableMapper.getRightBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of ct entries associated with the ct entry aggregate.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @return the number of ct entries associated with the ct entry aggregate
	 */
	@Override
	public int getCTEntriesSize(long pk) {
		long[] pks = ctEntryAggregateToCTEntryTableMapper.getRightPrimaryKeys(
			pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the ct entry is associated with the ct entry aggregate.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctEntryPK the primary key of the ct entry
	 * @return <code>true</code> if the ct entry is associated with the ct entry aggregate; <code>false</code> otherwise
	 */
	@Override
	public boolean containsCTEntry(long pk, long ctEntryPK) {
		return ctEntryAggregateToCTEntryTableMapper.containsTableMapping(
			pk, ctEntryPK);
	}

	/**
	 * Returns <code>true</code> if the ct entry aggregate has any ct entries associated with it.
	 *
	 * @param pk the primary key of the ct entry aggregate to check for associations with ct entries
	 * @return <code>true</code> if the ct entry aggregate has any ct entries associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsCTEntries(long pk) {
		if (getCTEntriesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the ct entry aggregate and the ct entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctEntryPK the primary key of the ct entry
	 */
	@Override
	public void addCTEntry(long pk, long ctEntryPK) {
		CTEntryAggregate ctEntryAggregate = fetchByPrimaryKey(pk);

		if (ctEntryAggregate == null) {
			ctEntryAggregateToCTEntryTableMapper.addTableMapping(
				companyProvider.getCompanyId(), pk, ctEntryPK);
		}
		else {
			ctEntryAggregateToCTEntryTableMapper.addTableMapping(
				ctEntryAggregate.getCompanyId(), pk, ctEntryPK);
		}
	}

	/**
	 * Adds an association between the ct entry aggregate and the ct entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctEntry the ct entry
	 */
	@Override
	public void addCTEntry(
		long pk, com.liferay.change.tracking.model.CTEntry ctEntry) {

		CTEntryAggregate ctEntryAggregate = fetchByPrimaryKey(pk);

		if (ctEntryAggregate == null) {
			ctEntryAggregateToCTEntryTableMapper.addTableMapping(
				companyProvider.getCompanyId(), pk, ctEntry.getPrimaryKey());
		}
		else {
			ctEntryAggregateToCTEntryTableMapper.addTableMapping(
				ctEntryAggregate.getCompanyId(), pk, ctEntry.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the ct entry aggregate and the ct entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctEntryPKs the primary keys of the ct entries
	 */
	@Override
	public void addCTEntries(long pk, long[] ctEntryPKs) {
		long companyId = 0;

		CTEntryAggregate ctEntryAggregate = fetchByPrimaryKey(pk);

		if (ctEntryAggregate == null) {
			companyId = companyProvider.getCompanyId();
		}
		else {
			companyId = ctEntryAggregate.getCompanyId();
		}

		ctEntryAggregateToCTEntryTableMapper.addTableMappings(
			companyId, pk, ctEntryPKs);
	}

	/**
	 * Adds an association between the ct entry aggregate and the ct entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctEntries the ct entries
	 */
	@Override
	public void addCTEntries(
		long pk, List<com.liferay.change.tracking.model.CTEntry> ctEntries) {

		addCTEntries(
			pk,
			ListUtil.toLongArray(
				ctEntries,
				com.liferay.change.tracking.model.CTEntry.
					CT_ENTRY_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the ct entry aggregate and its ct entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate to clear the associated ct entries from
	 */
	@Override
	public void clearCTEntries(long pk) {
		ctEntryAggregateToCTEntryTableMapper.deleteLeftPrimaryKeyTableMappings(
			pk);
	}

	/**
	 * Removes the association between the ct entry aggregate and the ct entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctEntryPK the primary key of the ct entry
	 */
	@Override
	public void removeCTEntry(long pk, long ctEntryPK) {
		ctEntryAggregateToCTEntryTableMapper.deleteTableMapping(pk, ctEntryPK);
	}

	/**
	 * Removes the association between the ct entry aggregate and the ct entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctEntry the ct entry
	 */
	@Override
	public void removeCTEntry(
		long pk, com.liferay.change.tracking.model.CTEntry ctEntry) {

		ctEntryAggregateToCTEntryTableMapper.deleteTableMapping(
			pk, ctEntry.getPrimaryKey());
	}

	/**
	 * Removes the association between the ct entry aggregate and the ct entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctEntryPKs the primary keys of the ct entries
	 */
	@Override
	public void removeCTEntries(long pk, long[] ctEntryPKs) {
		ctEntryAggregateToCTEntryTableMapper.deleteTableMappings(
			pk, ctEntryPKs);
	}

	/**
	 * Removes the association between the ct entry aggregate and the ct entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctEntries the ct entries
	 */
	@Override
	public void removeCTEntries(
		long pk, List<com.liferay.change.tracking.model.CTEntry> ctEntries) {

		removeCTEntries(
			pk,
			ListUtil.toLongArray(
				ctEntries,
				com.liferay.change.tracking.model.CTEntry.
					CT_ENTRY_ID_ACCESSOR));
	}

	/**
	 * Sets the ct entries associated with the ct entry aggregate, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctEntryPKs the primary keys of the ct entries to be associated with the ct entry aggregate
	 */
	@Override
	public void setCTEntries(long pk, long[] ctEntryPKs) {
		Set<Long> newCTEntryPKsSet = SetUtil.fromArray(ctEntryPKs);
		Set<Long> oldCTEntryPKsSet = SetUtil.fromArray(
			ctEntryAggregateToCTEntryTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removeCTEntryPKsSet = new HashSet<Long>(oldCTEntryPKsSet);

		removeCTEntryPKsSet.removeAll(newCTEntryPKsSet);

		ctEntryAggregateToCTEntryTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeCTEntryPKsSet));

		newCTEntryPKsSet.removeAll(oldCTEntryPKsSet);

		long companyId = 0;

		CTEntryAggregate ctEntryAggregate = fetchByPrimaryKey(pk);

		if (ctEntryAggregate == null) {
			companyId = companyProvider.getCompanyId();
		}
		else {
			companyId = ctEntryAggregate.getCompanyId();
		}

		ctEntryAggregateToCTEntryTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newCTEntryPKsSet));
	}

	/**
	 * Sets the ct entries associated with the ct entry aggregate, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctEntries the ct entries to be associated with the ct entry aggregate
	 */
	@Override
	public void setCTEntries(
		long pk, List<com.liferay.change.tracking.model.CTEntry> ctEntries) {

		try {
			long[] ctEntryPKs = new long[ctEntries.size()];

			for (int i = 0; i < ctEntries.size(); i++) {
				com.liferay.change.tracking.model.CTEntry ctEntry =
					ctEntries.get(i);

				ctEntryPKs[i] = ctEntry.getPrimaryKey();
			}

			setCTEntries(pk, ctEntryPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "ctEntryAggregateId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CTENTRYAGGREGATE;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CTEntryAggregateModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the ct entry aggregate persistence.
	 */
	public void afterPropertiesSet() {
		ctEntryAggregateToCTCollectionTableMapper =
			TableMapperFactory.getTableMapper(
				"CTCollection_CTEntryAggregate", "companyId",
				"ctEntryAggregateId", "ctCollectionId", this,
				ctCollectionPersistence);

		ctEntryAggregateToCTEntryTableMapper =
			TableMapperFactory.getTableMapper(
				"CTEntryAggregates_CTEntries", "companyId",
				"ctEntryAggregateId", "ctEntryId", this, ctEntryPersistence);

		_finderPathWithPaginationFindAll = new FinderPath(
			CTEntryAggregateModelImpl.ENTITY_CACHE_ENABLED,
			CTEntryAggregateModelImpl.FINDER_CACHE_ENABLED,
			CTEntryAggregateImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			CTEntryAggregateModelImpl.ENTITY_CACHE_ENABLED,
			CTEntryAggregateModelImpl.FINDER_CACHE_ENABLED,
			CTEntryAggregateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			CTEntryAggregateModelImpl.ENTITY_CACHE_ENABLED,
			CTEntryAggregateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByOwnerCTEntryID = new FinderPath(
			CTEntryAggregateModelImpl.ENTITY_CACHE_ENABLED,
			CTEntryAggregateModelImpl.FINDER_CACHE_ENABLED,
			CTEntryAggregateImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByOwnerCTEntryID",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByOwnerCTEntryID = new FinderPath(
			CTEntryAggregateModelImpl.ENTITY_CACHE_ENABLED,
			CTEntryAggregateModelImpl.FINDER_CACHE_ENABLED,
			CTEntryAggregateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByOwnerCTEntryID",
			new String[] {Long.class.getName()},
			CTEntryAggregateModelImpl.OWNERCTENTRYID_COLUMN_BITMASK);

		_finderPathCountByOwnerCTEntryID = new FinderPath(
			CTEntryAggregateModelImpl.ENTITY_CACHE_ENABLED,
			CTEntryAggregateModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByOwnerCTEntryID",
			new String[] {Long.class.getName()});
	}

	public void destroy() {
		entityCache.removeCache(CTEntryAggregateImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		TableMapperFactory.removeTableMapper("CTCollection_CTEntryAggregate");
		TableMapperFactory.removeTableMapper("CTEntryAggregates_CTEntries");
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	@BeanReference(type = CTCollectionPersistence.class)
	protected CTCollectionPersistence ctCollectionPersistence;

	protected TableMapper
		<CTEntryAggregate, com.liferay.change.tracking.model.CTCollection>
			ctEntryAggregateToCTCollectionTableMapper;

	@BeanReference(type = CTEntryPersistence.class)
	protected CTEntryPersistence ctEntryPersistence;

	protected TableMapper
		<CTEntryAggregate, com.liferay.change.tracking.model.CTEntry>
			ctEntryAggregateToCTEntryTableMapper;

	private static final String _SQL_SELECT_CTENTRYAGGREGATE =
		"SELECT ctEntryAggregate FROM CTEntryAggregate ctEntryAggregate";

	private static final String _SQL_SELECT_CTENTRYAGGREGATE_WHERE =
		"SELECT ctEntryAggregate FROM CTEntryAggregate ctEntryAggregate WHERE ";

	private static final String _SQL_COUNT_CTENTRYAGGREGATE =
		"SELECT COUNT(ctEntryAggregate) FROM CTEntryAggregate ctEntryAggregate";

	private static final String _SQL_COUNT_CTENTRYAGGREGATE_WHERE =
		"SELECT COUNT(ctEntryAggregate) FROM CTEntryAggregate ctEntryAggregate WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "ctEntryAggregate.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CTEntryAggregate exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CTEntryAggregate exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CTEntryAggregatePersistenceImpl.class);

}