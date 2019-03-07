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

import com.liferay.change.tracking.exception.NoSuchCollectionException;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.impl.CTCollectionImpl;
import com.liferay.change.tracking.model.impl.CTCollectionModelImpl;
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
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the ct collection service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class CTCollectionPersistenceImpl
	extends BasePersistenceImpl<CTCollection>
	implements CTCollectionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CTCollectionUtil</code> to access the ct collection persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CTCollectionImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the ct collections where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching ct collections
	 */
	@Override
	public List<CTCollection> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct collections where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTCollectionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct collections
	 * @param end the upper bound of the range of ct collections (not inclusive)
	 * @return the range of matching ct collections
	 */
	@Override
	public List<CTCollection> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct collections where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTCollectionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct collections
	 * @param end the upper bound of the range of ct collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct collections
	 */
	@Override
	public List<CTCollection> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CTCollection> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct collections where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTCollectionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct collections
	 * @param end the upper bound of the range of ct collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ct collections
	 */
	@Override
	public List<CTCollection> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CTCollection> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByCompanyId;
			finderArgs = new Object[] {companyId};
		}
		else {
			finderPath = _finderPathWithPaginationFindByCompanyId;
			finderArgs = new Object[] {
				companyId, start, end, orderByComparator
			};
		}

		List<CTCollection> list = null;

		if (retrieveFromCache) {
			list = (List<CTCollection>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CTCollection ctCollection : list) {
					if ((companyId != ctCollection.getCompanyId())) {
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

			query.append(_SQL_SELECT_CTCOLLECTION_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(CTCollectionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<CTCollection>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CTCollection>)QueryUtil.list(
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
	 * Returns the first ct collection in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct collection
	 * @throws NoSuchCollectionException if a matching ct collection could not be found
	 */
	@Override
	public CTCollection findByCompanyId_First(
			long companyId, OrderByComparator<CTCollection> orderByComparator)
		throws NoSuchCollectionException {

		CTCollection ctCollection = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (ctCollection != null) {
			return ctCollection;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchCollectionException(msg.toString());
	}

	/**
	 * Returns the first ct collection in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct collection, or <code>null</code> if a matching ct collection could not be found
	 */
	@Override
	public CTCollection fetchByCompanyId_First(
		long companyId, OrderByComparator<CTCollection> orderByComparator) {

		List<CTCollection> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ct collection in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct collection
	 * @throws NoSuchCollectionException if a matching ct collection could not be found
	 */
	@Override
	public CTCollection findByCompanyId_Last(
			long companyId, OrderByComparator<CTCollection> orderByComparator)
		throws NoSuchCollectionException {

		CTCollection ctCollection = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (ctCollection != null) {
			return ctCollection;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchCollectionException(msg.toString());
	}

	/**
	 * Returns the last ct collection in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct collection, or <code>null</code> if a matching ct collection could not be found
	 */
	@Override
	public CTCollection fetchByCompanyId_Last(
		long companyId, OrderByComparator<CTCollection> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<CTCollection> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ct collections before and after the current ct collection in the ordered set where companyId = &#63;.
	 *
	 * @param ctCollectionId the primary key of the current ct collection
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct collection
	 * @throws NoSuchCollectionException if a ct collection with the primary key could not be found
	 */
	@Override
	public CTCollection[] findByCompanyId_PrevAndNext(
			long ctCollectionId, long companyId,
			OrderByComparator<CTCollection> orderByComparator)
		throws NoSuchCollectionException {

		CTCollection ctCollection = findByPrimaryKey(ctCollectionId);

		Session session = null;

		try {
			session = openSession();

			CTCollection[] array = new CTCollectionImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, ctCollection, companyId, orderByComparator, true);

			array[1] = ctCollection;

			array[2] = getByCompanyId_PrevAndNext(
				session, ctCollection, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CTCollection getByCompanyId_PrevAndNext(
		Session session, CTCollection ctCollection, long companyId,
		OrderByComparator<CTCollection> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CTCOLLECTION_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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
			query.append(CTCollectionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(ctCollection)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<CTCollection> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ct collections where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (CTCollection ctCollection :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(ctCollection);
		}
	}

	/**
	 * Returns the number of ct collections where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching ct collections
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CTCOLLECTION_WHERE);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"ctCollection.companyId = ?";

	private FinderPath _finderPathFetchByC_N;
	private FinderPath _finderPathCountByC_N;

	/**
	 * Returns the ct collection where companyId = &#63; and name = &#63; or throws a <code>NoSuchCollectionException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching ct collection
	 * @throws NoSuchCollectionException if a matching ct collection could not be found
	 */
	@Override
	public CTCollection findByC_N(long companyId, String name)
		throws NoSuchCollectionException {

		CTCollection ctCollection = fetchByC_N(companyId, name);

		if (ctCollection == null) {
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

			throw new NoSuchCollectionException(msg.toString());
		}

		return ctCollection;
	}

	/**
	 * Returns the ct collection where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching ct collection, or <code>null</code> if a matching ct collection could not be found
	 */
	@Override
	public CTCollection fetchByC_N(long companyId, String name) {
		return fetchByC_N(companyId, name, true);
	}

	/**
	 * Returns the ct collection where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching ct collection, or <code>null</code> if a matching ct collection could not be found
	 */
	@Override
	public CTCollection fetchByC_N(
		long companyId, String name, boolean retrieveFromCache) {

		name = Objects.toString(name, "");

		Object[] finderArgs = new Object[] {companyId, name};

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_N, finderArgs, this);
		}

		if (result instanceof CTCollection) {
			CTCollection ctCollection = (CTCollection)result;

			if ((companyId != ctCollection.getCompanyId()) ||
				!Objects.equals(name, ctCollection.getName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_CTCOLLECTION_WHERE);

			query.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
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

				List<CTCollection> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(
						_finderPathFetchByC_N, finderArgs, list);
				}
				else {
					CTCollection ctCollection = list.get(0);

					result = ctCollection;

					cacheResult(ctCollection);
				}
			}
			catch (Exception e) {
				finderCache.removeResult(_finderPathFetchByC_N, finderArgs);

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
			return (CTCollection)result;
		}
	}

	/**
	 * Removes the ct collection where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the ct collection that was removed
	 */
	@Override
	public CTCollection removeByC_N(long companyId, String name)
		throws NoSuchCollectionException {

		CTCollection ctCollection = findByC_N(companyId, name);

		return remove(ctCollection);
	}

	/**
	 * Returns the number of ct collections where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching ct collections
	 */
	@Override
	public int countByC_N(long companyId, String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByC_N;

		Object[] finderArgs = new Object[] {companyId, name};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CTCOLLECTION_WHERE);

			query.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
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

	private static final String _FINDER_COLUMN_C_N_COMPANYID_2 =
		"ctCollection.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_N_NAME_2 =
		"ctCollection.name = ?";

	private static final String _FINDER_COLUMN_C_N_NAME_3 =
		"(ctCollection.name IS NULL OR ctCollection.name = '')";

	public CTCollectionPersistenceImpl() {
		setModelClass(CTCollection.class);

		setModelImplClass(CTCollectionImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(CTCollectionModelImpl.ENTITY_CACHE_ENABLED);
	}

	/**
	 * Caches the ct collection in the entity cache if it is enabled.
	 *
	 * @param ctCollection the ct collection
	 */
	@Override
	public void cacheResult(CTCollection ctCollection) {
		entityCache.putResult(
			CTCollectionModelImpl.ENTITY_CACHE_ENABLED, CTCollectionImpl.class,
			ctCollection.getPrimaryKey(), ctCollection);

		finderCache.putResult(
			_finderPathFetchByC_N,
			new Object[] {ctCollection.getCompanyId(), ctCollection.getName()},
			ctCollection);

		ctCollection.resetOriginalValues();
	}

	/**
	 * Caches the ct collections in the entity cache if it is enabled.
	 *
	 * @param ctCollections the ct collections
	 */
	@Override
	public void cacheResult(List<CTCollection> ctCollections) {
		for (CTCollection ctCollection : ctCollections) {
			if (entityCache.getResult(
					CTCollectionModelImpl.ENTITY_CACHE_ENABLED,
					CTCollectionImpl.class, ctCollection.getPrimaryKey()) ==
						null) {

				cacheResult(ctCollection);
			}
			else {
				ctCollection.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all ct collections.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CTCollectionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the ct collection.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CTCollection ctCollection) {
		entityCache.removeResult(
			CTCollectionModelImpl.ENTITY_CACHE_ENABLED, CTCollectionImpl.class,
			ctCollection.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CTCollectionModelImpl)ctCollection, true);
	}

	@Override
	public void clearCache(List<CTCollection> ctCollections) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CTCollection ctCollection : ctCollections) {
			entityCache.removeResult(
				CTCollectionModelImpl.ENTITY_CACHE_ENABLED,
				CTCollectionImpl.class, ctCollection.getPrimaryKey());

			clearUniqueFindersCache((CTCollectionModelImpl)ctCollection, true);
		}
	}

	protected void cacheUniqueFindersCache(
		CTCollectionModelImpl ctCollectionModelImpl) {

		Object[] args = new Object[] {
			ctCollectionModelImpl.getCompanyId(),
			ctCollectionModelImpl.getName()
		};

		finderCache.putResult(
			_finderPathCountByC_N, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByC_N, args, ctCollectionModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CTCollectionModelImpl ctCollectionModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				ctCollectionModelImpl.getCompanyId(),
				ctCollectionModelImpl.getName()
			};

			finderCache.removeResult(_finderPathCountByC_N, args);
			finderCache.removeResult(_finderPathFetchByC_N, args);
		}

		if ((ctCollectionModelImpl.getColumnBitmask() &
			 _finderPathFetchByC_N.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				ctCollectionModelImpl.getOriginalCompanyId(),
				ctCollectionModelImpl.getOriginalName()
			};

			finderCache.removeResult(_finderPathCountByC_N, args);
			finderCache.removeResult(_finderPathFetchByC_N, args);
		}
	}

	/**
	 * Creates a new ct collection with the primary key. Does not add the ct collection to the database.
	 *
	 * @param ctCollectionId the primary key for the new ct collection
	 * @return the new ct collection
	 */
	@Override
	public CTCollection create(long ctCollectionId) {
		CTCollection ctCollection = new CTCollectionImpl();

		ctCollection.setNew(true);
		ctCollection.setPrimaryKey(ctCollectionId);

		ctCollection.setCompanyId(companyProvider.getCompanyId());

		return ctCollection;
	}

	/**
	 * Removes the ct collection with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctCollectionId the primary key of the ct collection
	 * @return the ct collection that was removed
	 * @throws NoSuchCollectionException if a ct collection with the primary key could not be found
	 */
	@Override
	public CTCollection remove(long ctCollectionId)
		throws NoSuchCollectionException {

		return remove((Serializable)ctCollectionId);
	}

	/**
	 * Removes the ct collection with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the ct collection
	 * @return the ct collection that was removed
	 * @throws NoSuchCollectionException if a ct collection with the primary key could not be found
	 */
	@Override
	public CTCollection remove(Serializable primaryKey)
		throws NoSuchCollectionException {

		Session session = null;

		try {
			session = openSession();

			CTCollection ctCollection = (CTCollection)session.get(
				CTCollectionImpl.class, primaryKey);

			if (ctCollection == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCollectionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(ctCollection);
		}
		catch (NoSuchCollectionException nsee) {
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
	protected CTCollection removeImpl(CTCollection ctCollection) {
		ctCollectionToCTEntryTableMapper.deleteLeftPrimaryKeyTableMappings(
			ctCollection.getPrimaryKey());

		ctCollectionToCTEntryAggregateTableMapper.
			deleteLeftPrimaryKeyTableMappings(ctCollection.getPrimaryKey());

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ctCollection)) {
				ctCollection = (CTCollection)session.get(
					CTCollectionImpl.class, ctCollection.getPrimaryKeyObj());
			}

			if (ctCollection != null) {
				session.delete(ctCollection);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (ctCollection != null) {
			clearCache(ctCollection);
		}

		return ctCollection;
	}

	@Override
	public CTCollection updateImpl(CTCollection ctCollection) {
		boolean isNew = ctCollection.isNew();

		if (!(ctCollection instanceof CTCollectionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(ctCollection.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					ctCollection);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in ctCollection proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CTCollection implementation " +
					ctCollection.getClass());
		}

		CTCollectionModelImpl ctCollectionModelImpl =
			(CTCollectionModelImpl)ctCollection;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (ctCollection.getCreateDate() == null)) {
			if (serviceContext == null) {
				ctCollection.setCreateDate(now);
			}
			else {
				ctCollection.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!ctCollectionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				ctCollection.setModifiedDate(now);
			}
			else {
				ctCollection.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctCollection.isNew()) {
				session.save(ctCollection);

				ctCollection.setNew(false);
			}
			else {
				ctCollection = (CTCollection)session.merge(ctCollection);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CTCollectionModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {ctCollectionModelImpl.getCompanyId()};

			finderCache.removeResult(_finderPathCountByCompanyId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByCompanyId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((ctCollectionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCompanyId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					ctCollectionModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByCompanyId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);

				args = new Object[] {ctCollectionModelImpl.getCompanyId()};

				finderCache.removeResult(_finderPathCountByCompanyId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);
			}
		}

		entityCache.putResult(
			CTCollectionModelImpl.ENTITY_CACHE_ENABLED, CTCollectionImpl.class,
			ctCollection.getPrimaryKey(), ctCollection, false);

		clearUniqueFindersCache(ctCollectionModelImpl, false);
		cacheUniqueFindersCache(ctCollectionModelImpl);

		ctCollection.resetOriginalValues();

		return ctCollection;
	}

	/**
	 * Returns the ct collection with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ct collection
	 * @return the ct collection
	 * @throws NoSuchCollectionException if a ct collection with the primary key could not be found
	 */
	@Override
	public CTCollection findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCollectionException {

		CTCollection ctCollection = fetchByPrimaryKey(primaryKey);

		if (ctCollection == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCollectionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return ctCollection;
	}

	/**
	 * Returns the ct collection with the primary key or throws a <code>NoSuchCollectionException</code> if it could not be found.
	 *
	 * @param ctCollectionId the primary key of the ct collection
	 * @return the ct collection
	 * @throws NoSuchCollectionException if a ct collection with the primary key could not be found
	 */
	@Override
	public CTCollection findByPrimaryKey(long ctCollectionId)
		throws NoSuchCollectionException {

		return findByPrimaryKey((Serializable)ctCollectionId);
	}

	/**
	 * Returns the ct collection with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ctCollectionId the primary key of the ct collection
	 * @return the ct collection, or <code>null</code> if a ct collection with the primary key could not be found
	 */
	@Override
	public CTCollection fetchByPrimaryKey(long ctCollectionId) {
		return fetchByPrimaryKey((Serializable)ctCollectionId);
	}

	/**
	 * Returns all the ct collections.
	 *
	 * @return the ct collections
	 */
	@Override
	public List<CTCollection> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTCollectionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct collections
	 * @param end the upper bound of the range of ct collections (not inclusive)
	 * @return the range of ct collections
	 */
	@Override
	public List<CTCollection> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTCollectionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct collections
	 * @param end the upper bound of the range of ct collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct collections
	 */
	@Override
	public List<CTCollection> findAll(
		int start, int end, OrderByComparator<CTCollection> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTCollectionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct collections
	 * @param end the upper bound of the range of ct collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of ct collections
	 */
	@Override
	public List<CTCollection> findAll(
		int start, int end, OrderByComparator<CTCollection> orderByComparator,
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

		List<CTCollection> list = null;

		if (retrieveFromCache) {
			list = (List<CTCollection>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CTCOLLECTION);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CTCOLLECTION;

				if (pagination) {
					sql = sql.concat(CTCollectionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CTCollection>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CTCollection>)QueryUtil.list(
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
	 * Removes all the ct collections from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CTCollection ctCollection : findAll()) {
			remove(ctCollection);
		}
	}

	/**
	 * Returns the number of ct collections.
	 *
	 * @return the number of ct collections
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CTCOLLECTION);

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
	 * Returns the primaryKeys of ct entries associated with the ct collection.
	 *
	 * @param pk the primary key of the ct collection
	 * @return long[] of the primaryKeys of ct entries associated with the ct collection
	 */
	@Override
	public long[] getCTEntryPrimaryKeys(long pk) {
		long[] pks = ctCollectionToCTEntryTableMapper.getRightPrimaryKeys(pk);

		return pks.clone();
	}

	/**
	 * Returns all the ct entries associated with the ct collection.
	 *
	 * @param pk the primary key of the ct collection
	 * @return the ct entries associated with the ct collection
	 */
	@Override
	public List<com.liferay.change.tracking.model.CTEntry> getCTEntries(
		long pk) {

		return getCTEntries(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the ct entries associated with the ct collection.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTCollectionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the ct collection
	 * @param start the lower bound of the range of ct collections
	 * @param end the upper bound of the range of ct collections (not inclusive)
	 * @return the range of ct entries associated with the ct collection
	 */
	@Override
	public List<com.liferay.change.tracking.model.CTEntry> getCTEntries(
		long pk, int start, int end) {

		return getCTEntries(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct entries associated with the ct collection.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTCollectionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the ct collection
	 * @param start the lower bound of the range of ct collections
	 * @param end the upper bound of the range of ct collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct entries associated with the ct collection
	 */
	@Override
	public List<com.liferay.change.tracking.model.CTEntry> getCTEntries(
		long pk, int start, int end,
		OrderByComparator<com.liferay.change.tracking.model.CTEntry>
			orderByComparator) {

		return ctCollectionToCTEntryTableMapper.getRightBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of ct entries associated with the ct collection.
	 *
	 * @param pk the primary key of the ct collection
	 * @return the number of ct entries associated with the ct collection
	 */
	@Override
	public int getCTEntriesSize(long pk) {
		long[] pks = ctCollectionToCTEntryTableMapper.getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the ct entry is associated with the ct collection.
	 *
	 * @param pk the primary key of the ct collection
	 * @param ctEntryPK the primary key of the ct entry
	 * @return <code>true</code> if the ct entry is associated with the ct collection; <code>false</code> otherwise
	 */
	@Override
	public boolean containsCTEntry(long pk, long ctEntryPK) {
		return ctCollectionToCTEntryTableMapper.containsTableMapping(
			pk, ctEntryPK);
	}

	/**
	 * Returns <code>true</code> if the ct collection has any ct entries associated with it.
	 *
	 * @param pk the primary key of the ct collection to check for associations with ct entries
	 * @return <code>true</code> if the ct collection has any ct entries associated with it; <code>false</code> otherwise
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
	 * Adds an association between the ct collection and the ct entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct collection
	 * @param ctEntryPK the primary key of the ct entry
	 */
	@Override
	public void addCTEntry(long pk, long ctEntryPK) {
		CTCollection ctCollection = fetchByPrimaryKey(pk);

		if (ctCollection == null) {
			ctCollectionToCTEntryTableMapper.addTableMapping(
				companyProvider.getCompanyId(), pk, ctEntryPK);
		}
		else {
			ctCollectionToCTEntryTableMapper.addTableMapping(
				ctCollection.getCompanyId(), pk, ctEntryPK);
		}
	}

	/**
	 * Adds an association between the ct collection and the ct entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct collection
	 * @param ctEntry the ct entry
	 */
	@Override
	public void addCTEntry(
		long pk, com.liferay.change.tracking.model.CTEntry ctEntry) {

		CTCollection ctCollection = fetchByPrimaryKey(pk);

		if (ctCollection == null) {
			ctCollectionToCTEntryTableMapper.addTableMapping(
				companyProvider.getCompanyId(), pk, ctEntry.getPrimaryKey());
		}
		else {
			ctCollectionToCTEntryTableMapper.addTableMapping(
				ctCollection.getCompanyId(), pk, ctEntry.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the ct collection and the ct entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct collection
	 * @param ctEntryPKs the primary keys of the ct entries
	 */
	@Override
	public void addCTEntries(long pk, long[] ctEntryPKs) {
		long companyId = 0;

		CTCollection ctCollection = fetchByPrimaryKey(pk);

		if (ctCollection == null) {
			companyId = companyProvider.getCompanyId();
		}
		else {
			companyId = ctCollection.getCompanyId();
		}

		ctCollectionToCTEntryTableMapper.addTableMappings(
			companyId, pk, ctEntryPKs);
	}

	/**
	 * Adds an association between the ct collection and the ct entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct collection
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
	 * Clears all associations between the ct collection and its ct entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct collection to clear the associated ct entries from
	 */
	@Override
	public void clearCTEntries(long pk) {
		ctCollectionToCTEntryTableMapper.deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the ct collection and the ct entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct collection
	 * @param ctEntryPK the primary key of the ct entry
	 */
	@Override
	public void removeCTEntry(long pk, long ctEntryPK) {
		ctCollectionToCTEntryTableMapper.deleteTableMapping(pk, ctEntryPK);
	}

	/**
	 * Removes the association between the ct collection and the ct entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct collection
	 * @param ctEntry the ct entry
	 */
	@Override
	public void removeCTEntry(
		long pk, com.liferay.change.tracking.model.CTEntry ctEntry) {

		ctCollectionToCTEntryTableMapper.deleteTableMapping(
			pk, ctEntry.getPrimaryKey());
	}

	/**
	 * Removes the association between the ct collection and the ct entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct collection
	 * @param ctEntryPKs the primary keys of the ct entries
	 */
	@Override
	public void removeCTEntries(long pk, long[] ctEntryPKs) {
		ctCollectionToCTEntryTableMapper.deleteTableMappings(pk, ctEntryPKs);
	}

	/**
	 * Removes the association between the ct collection and the ct entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct collection
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
	 * Sets the ct entries associated with the ct collection, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct collection
	 * @param ctEntryPKs the primary keys of the ct entries to be associated with the ct collection
	 */
	@Override
	public void setCTEntries(long pk, long[] ctEntryPKs) {
		Set<Long> newCTEntryPKsSet = SetUtil.fromArray(ctEntryPKs);
		Set<Long> oldCTEntryPKsSet = SetUtil.fromArray(
			ctCollectionToCTEntryTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removeCTEntryPKsSet = new HashSet<Long>(oldCTEntryPKsSet);

		removeCTEntryPKsSet.removeAll(newCTEntryPKsSet);

		ctCollectionToCTEntryTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeCTEntryPKsSet));

		newCTEntryPKsSet.removeAll(oldCTEntryPKsSet);

		long companyId = 0;

		CTCollection ctCollection = fetchByPrimaryKey(pk);

		if (ctCollection == null) {
			companyId = companyProvider.getCompanyId();
		}
		else {
			companyId = ctCollection.getCompanyId();
		}

		ctCollectionToCTEntryTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newCTEntryPKsSet));
	}

	/**
	 * Sets the ct entries associated with the ct collection, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct collection
	 * @param ctEntries the ct entries to be associated with the ct collection
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

	/**
	 * Returns the primaryKeys of ct entry aggregates associated with the ct collection.
	 *
	 * @param pk the primary key of the ct collection
	 * @return long[] of the primaryKeys of ct entry aggregates associated with the ct collection
	 */
	@Override
	public long[] getCTEntryAggregatePrimaryKeys(long pk) {
		long[] pks =
			ctCollectionToCTEntryAggregateTableMapper.getRightPrimaryKeys(pk);

		return pks.clone();
	}

	/**
	 * Returns all the ct entry aggregates associated with the ct collection.
	 *
	 * @param pk the primary key of the ct collection
	 * @return the ct entry aggregates associated with the ct collection
	 */
	@Override
	public List<com.liferay.change.tracking.model.CTEntryAggregate>
		getCTEntryAggregates(long pk) {

		return getCTEntryAggregates(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the ct entry aggregates associated with the ct collection.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTCollectionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the ct collection
	 * @param start the lower bound of the range of ct collections
	 * @param end the upper bound of the range of ct collections (not inclusive)
	 * @return the range of ct entry aggregates associated with the ct collection
	 */
	@Override
	public List<com.liferay.change.tracking.model.CTEntryAggregate>
		getCTEntryAggregates(long pk, int start, int end) {

		return getCTEntryAggregates(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct entry aggregates associated with the ct collection.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTCollectionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the ct collection
	 * @param start the lower bound of the range of ct collections
	 * @param end the upper bound of the range of ct collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct entry aggregates associated with the ct collection
	 */
	@Override
	public List<com.liferay.change.tracking.model.CTEntryAggregate>
		getCTEntryAggregates(
			long pk, int start, int end,
			OrderByComparator
				<com.liferay.change.tracking.model.CTEntryAggregate>
					orderByComparator) {

		return ctCollectionToCTEntryAggregateTableMapper.getRightBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of ct entry aggregates associated with the ct collection.
	 *
	 * @param pk the primary key of the ct collection
	 * @return the number of ct entry aggregates associated with the ct collection
	 */
	@Override
	public int getCTEntryAggregatesSize(long pk) {
		long[] pks =
			ctCollectionToCTEntryAggregateTableMapper.getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the ct entry aggregate is associated with the ct collection.
	 *
	 * @param pk the primary key of the ct collection
	 * @param ctEntryAggregatePK the primary key of the ct entry aggregate
	 * @return <code>true</code> if the ct entry aggregate is associated with the ct collection; <code>false</code> otherwise
	 */
	@Override
	public boolean containsCTEntryAggregate(long pk, long ctEntryAggregatePK) {
		return ctCollectionToCTEntryAggregateTableMapper.containsTableMapping(
			pk, ctEntryAggregatePK);
	}

	/**
	 * Returns <code>true</code> if the ct collection has any ct entry aggregates associated with it.
	 *
	 * @param pk the primary key of the ct collection to check for associations with ct entry aggregates
	 * @return <code>true</code> if the ct collection has any ct entry aggregates associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsCTEntryAggregates(long pk) {
		if (getCTEntryAggregatesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the ct collection and the ct entry aggregate. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct collection
	 * @param ctEntryAggregatePK the primary key of the ct entry aggregate
	 */
	@Override
	public void addCTEntryAggregate(long pk, long ctEntryAggregatePK) {
		CTCollection ctCollection = fetchByPrimaryKey(pk);

		if (ctCollection == null) {
			ctCollectionToCTEntryAggregateTableMapper.addTableMapping(
				companyProvider.getCompanyId(), pk, ctEntryAggregatePK);
		}
		else {
			ctCollectionToCTEntryAggregateTableMapper.addTableMapping(
				ctCollection.getCompanyId(), pk, ctEntryAggregatePK);
		}
	}

	/**
	 * Adds an association between the ct collection and the ct entry aggregate. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct collection
	 * @param ctEntryAggregate the ct entry aggregate
	 */
	@Override
	public void addCTEntryAggregate(
		long pk,
		com.liferay.change.tracking.model.CTEntryAggregate ctEntryAggregate) {

		CTCollection ctCollection = fetchByPrimaryKey(pk);

		if (ctCollection == null) {
			ctCollectionToCTEntryAggregateTableMapper.addTableMapping(
				companyProvider.getCompanyId(), pk,
				ctEntryAggregate.getPrimaryKey());
		}
		else {
			ctCollectionToCTEntryAggregateTableMapper.addTableMapping(
				ctCollection.getCompanyId(), pk,
				ctEntryAggregate.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the ct collection and the ct entry aggregates. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct collection
	 * @param ctEntryAggregatePKs the primary keys of the ct entry aggregates
	 */
	@Override
	public void addCTEntryAggregates(long pk, long[] ctEntryAggregatePKs) {
		long companyId = 0;

		CTCollection ctCollection = fetchByPrimaryKey(pk);

		if (ctCollection == null) {
			companyId = companyProvider.getCompanyId();
		}
		else {
			companyId = ctCollection.getCompanyId();
		}

		ctCollectionToCTEntryAggregateTableMapper.addTableMappings(
			companyId, pk, ctEntryAggregatePKs);
	}

	/**
	 * Adds an association between the ct collection and the ct entry aggregates. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct collection
	 * @param ctEntryAggregates the ct entry aggregates
	 */
	@Override
	public void addCTEntryAggregates(
		long pk,
		List<com.liferay.change.tracking.model.CTEntryAggregate>
			ctEntryAggregates) {

		addCTEntryAggregates(
			pk,
			ListUtil.toLongArray(
				ctEntryAggregates,
				com.liferay.change.tracking.model.CTEntryAggregate.
					CT_ENTRY_AGGREGATE_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the ct collection and its ct entry aggregates. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct collection to clear the associated ct entry aggregates from
	 */
	@Override
	public void clearCTEntryAggregates(long pk) {
		ctCollectionToCTEntryAggregateTableMapper.
			deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the ct collection and the ct entry aggregate. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct collection
	 * @param ctEntryAggregatePK the primary key of the ct entry aggregate
	 */
	@Override
	public void removeCTEntryAggregate(long pk, long ctEntryAggregatePK) {
		ctCollectionToCTEntryAggregateTableMapper.deleteTableMapping(
			pk, ctEntryAggregatePK);
	}

	/**
	 * Removes the association between the ct collection and the ct entry aggregate. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct collection
	 * @param ctEntryAggregate the ct entry aggregate
	 */
	@Override
	public void removeCTEntryAggregate(
		long pk,
		com.liferay.change.tracking.model.CTEntryAggregate ctEntryAggregate) {

		ctCollectionToCTEntryAggregateTableMapper.deleteTableMapping(
			pk, ctEntryAggregate.getPrimaryKey());
	}

	/**
	 * Removes the association between the ct collection and the ct entry aggregates. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct collection
	 * @param ctEntryAggregatePKs the primary keys of the ct entry aggregates
	 */
	@Override
	public void removeCTEntryAggregates(long pk, long[] ctEntryAggregatePKs) {
		ctCollectionToCTEntryAggregateTableMapper.deleteTableMappings(
			pk, ctEntryAggregatePKs);
	}

	/**
	 * Removes the association between the ct collection and the ct entry aggregates. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct collection
	 * @param ctEntryAggregates the ct entry aggregates
	 */
	@Override
	public void removeCTEntryAggregates(
		long pk,
		List<com.liferay.change.tracking.model.CTEntryAggregate>
			ctEntryAggregates) {

		removeCTEntryAggregates(
			pk,
			ListUtil.toLongArray(
				ctEntryAggregates,
				com.liferay.change.tracking.model.CTEntryAggregate.
					CT_ENTRY_AGGREGATE_ID_ACCESSOR));
	}

	/**
	 * Sets the ct entry aggregates associated with the ct collection, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct collection
	 * @param ctEntryAggregatePKs the primary keys of the ct entry aggregates to be associated with the ct collection
	 */
	@Override
	public void setCTEntryAggregates(long pk, long[] ctEntryAggregatePKs) {
		Set<Long> newCTEntryAggregatePKsSet = SetUtil.fromArray(
			ctEntryAggregatePKs);
		Set<Long> oldCTEntryAggregatePKsSet = SetUtil.fromArray(
			ctCollectionToCTEntryAggregateTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removeCTEntryAggregatePKsSet = new HashSet<Long>(
			oldCTEntryAggregatePKsSet);

		removeCTEntryAggregatePKsSet.removeAll(newCTEntryAggregatePKsSet);

		ctCollectionToCTEntryAggregateTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeCTEntryAggregatePKsSet));

		newCTEntryAggregatePKsSet.removeAll(oldCTEntryAggregatePKsSet);

		long companyId = 0;

		CTCollection ctCollection = fetchByPrimaryKey(pk);

		if (ctCollection == null) {
			companyId = companyProvider.getCompanyId();
		}
		else {
			companyId = ctCollection.getCompanyId();
		}

		ctCollectionToCTEntryAggregateTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newCTEntryAggregatePKsSet));
	}

	/**
	 * Sets the ct entry aggregates associated with the ct collection, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct collection
	 * @param ctEntryAggregates the ct entry aggregates to be associated with the ct collection
	 */
	@Override
	public void setCTEntryAggregates(
		long pk,
		List<com.liferay.change.tracking.model.CTEntryAggregate>
			ctEntryAggregates) {

		try {
			long[] ctEntryAggregatePKs = new long[ctEntryAggregates.size()];

			for (int i = 0; i < ctEntryAggregates.size(); i++) {
				com.liferay.change.tracking.model.CTEntryAggregate
					ctEntryAggregate = ctEntryAggregates.get(i);

				ctEntryAggregatePKs[i] = ctEntryAggregate.getPrimaryKey();
			}

			setCTEntryAggregates(pk, ctEntryAggregatePKs);
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
		return "ctCollectionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CTCOLLECTION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CTCollectionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the ct collection persistence.
	 */
	public void afterPropertiesSet() {
		ctCollectionToCTEntryTableMapper = TableMapperFactory.getTableMapper(
			"CTCollections_CTEntries", "companyId", "ctCollectionId",
			"ctEntryId", this, ctEntryPersistence);

		ctCollectionToCTEntryAggregateTableMapper =
			TableMapperFactory.getTableMapper(
				"CTCollection_CTEntryAggregate", "companyId", "ctCollectionId",
				"ctEntryAggregateId", this, ctEntryAggregatePersistence);

		_finderPathWithPaginationFindAll = new FinderPath(
			CTCollectionModelImpl.ENTITY_CACHE_ENABLED,
			CTCollectionModelImpl.FINDER_CACHE_ENABLED, CTCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			CTCollectionModelImpl.ENTITY_CACHE_ENABLED,
			CTCollectionModelImpl.FINDER_CACHE_ENABLED, CTCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			CTCollectionModelImpl.ENTITY_CACHE_ENABLED,
			CTCollectionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			CTCollectionModelImpl.ENTITY_CACHE_ENABLED,
			CTCollectionModelImpl.FINDER_CACHE_ENABLED, CTCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			CTCollectionModelImpl.ENTITY_CACHE_ENABLED,
			CTCollectionModelImpl.FINDER_CACHE_ENABLED, CTCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] {Long.class.getName()},
			CTCollectionModelImpl.COMPANYID_COLUMN_BITMASK |
			CTCollectionModelImpl.CREATEDATE_COLUMN_BITMASK);

		_finderPathCountByCompanyId = new FinderPath(
			CTCollectionModelImpl.ENTITY_CACHE_ENABLED,
			CTCollectionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] {Long.class.getName()});

		_finderPathFetchByC_N = new FinderPath(
			CTCollectionModelImpl.ENTITY_CACHE_ENABLED,
			CTCollectionModelImpl.FINDER_CACHE_ENABLED, CTCollectionImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_N",
			new String[] {Long.class.getName(), String.class.getName()},
			CTCollectionModelImpl.COMPANYID_COLUMN_BITMASK |
			CTCollectionModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByC_N = new FinderPath(
			CTCollectionModelImpl.ENTITY_CACHE_ENABLED,
			CTCollectionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_N",
			new String[] {Long.class.getName(), String.class.getName()});
	}

	public void destroy() {
		entityCache.removeCache(CTCollectionImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		TableMapperFactory.removeTableMapper("CTCollections_CTEntries");
		TableMapperFactory.removeTableMapper("CTCollection_CTEntryAggregate");
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	@BeanReference(type = CTEntryPersistence.class)
	protected CTEntryPersistence ctEntryPersistence;

	protected TableMapper
		<CTCollection, com.liferay.change.tracking.model.CTEntry>
			ctCollectionToCTEntryTableMapper;

	@BeanReference(type = CTEntryAggregatePersistence.class)
	protected CTEntryAggregatePersistence ctEntryAggregatePersistence;

	protected TableMapper
		<CTCollection, com.liferay.change.tracking.model.CTEntryAggregate>
			ctCollectionToCTEntryAggregateTableMapper;

	private static final String _SQL_SELECT_CTCOLLECTION =
		"SELECT ctCollection FROM CTCollection ctCollection";

	private static final String _SQL_SELECT_CTCOLLECTION_WHERE =
		"SELECT ctCollection FROM CTCollection ctCollection WHERE ";

	private static final String _SQL_COUNT_CTCOLLECTION =
		"SELECT COUNT(ctCollection) FROM CTCollection ctCollection";

	private static final String _SQL_COUNT_CTCOLLECTION_WHERE =
		"SELECT COUNT(ctCollection) FROM CTCollection ctCollection WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "ctCollection.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CTCollection exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CTCollection exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CTCollectionPersistenceImpl.class);

}