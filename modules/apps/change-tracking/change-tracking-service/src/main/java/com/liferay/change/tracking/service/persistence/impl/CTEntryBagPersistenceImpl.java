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

import com.liferay.change.tracking.exception.NoSuchEntryBagException;
import com.liferay.change.tracking.model.CTEntryBag;
import com.liferay.change.tracking.model.impl.CTEntryBagImpl;
import com.liferay.change.tracking.model.impl.CTEntryBagModelImpl;
import com.liferay.change.tracking.service.persistence.CTEntryBagPersistence;
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
 * The persistence implementation for the ct entry bag service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class CTEntryBagPersistenceImpl extends BasePersistenceImpl<CTEntryBag>
	implements CTEntryBagPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CTEntryBagUtil</code> to access the ct entry bag persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CTEntryBagImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByO_C;
	private FinderPath _finderPathWithoutPaginationFindByO_C;
	private FinderPath _finderPathCountByO_C;

	/**
	 * Returns all the ct entry bags where ownerCTEntryId = &#63; and ctCollectionId = &#63;.
	 *
	 * @param ownerCTEntryId the owner ct entry ID
	 * @param ctCollectionId the ct collection ID
	 * @return the matching ct entry bags
	 */
	@Override
	public List<CTEntryBag> findByO_C(long ownerCTEntryId, long ctCollectionId) {
		return findByO_C(ownerCTEntryId, ctCollectionId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct entry bags where ownerCTEntryId = &#63; and ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryBagModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ownerCTEntryId the owner ct entry ID
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct entry bags
	 * @param end the upper bound of the range of ct entry bags (not inclusive)
	 * @return the range of matching ct entry bags
	 */
	@Override
	public List<CTEntryBag> findByO_C(long ownerCTEntryId, long ctCollectionId,
		int start, int end) {
		return findByO_C(ownerCTEntryId, ctCollectionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct entry bags where ownerCTEntryId = &#63; and ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryBagModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ownerCTEntryId the owner ct entry ID
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct entry bags
	 * @param end the upper bound of the range of ct entry bags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct entry bags
	 */
	@Override
	public List<CTEntryBag> findByO_C(long ownerCTEntryId, long ctCollectionId,
		int start, int end, OrderByComparator<CTEntryBag> orderByComparator) {
		return findByO_C(ownerCTEntryId, ctCollectionId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct entry bags where ownerCTEntryId = &#63; and ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryBagModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ownerCTEntryId the owner ct entry ID
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct entry bags
	 * @param end the upper bound of the range of ct entry bags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ct entry bags
	 */
	@Override
	public List<CTEntryBag> findByO_C(long ownerCTEntryId, long ctCollectionId,
		int start, int end, OrderByComparator<CTEntryBag> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByO_C;
			finderArgs = new Object[] { ownerCTEntryId, ctCollectionId };
		}
		else {
			finderPath = _finderPathWithPaginationFindByO_C;
			finderArgs = new Object[] {
					ownerCTEntryId, ctCollectionId,
					
					start, end, orderByComparator
				};
		}

		List<CTEntryBag> list = null;

		if (retrieveFromCache) {
			list = (List<CTEntryBag>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CTEntryBag ctEntryBag : list) {
					if ((ownerCTEntryId != ctEntryBag.getOwnerCTEntryId()) ||
							(ctCollectionId != ctEntryBag.getCtCollectionId())) {
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

			query.append(_SQL_SELECT_CTENTRYBAG_WHERE);

			query.append(_FINDER_COLUMN_O_C_OWNERCTENTRYID_2);

			query.append(_FINDER_COLUMN_O_C_CTCOLLECTIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CTEntryBagModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ownerCTEntryId);

				qPos.add(ctCollectionId);

				if (!pagination) {
					list = (List<CTEntryBag>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CTEntryBag>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first ct entry bag in the ordered set where ownerCTEntryId = &#63; and ctCollectionId = &#63;.
	 *
	 * @param ownerCTEntryId the owner ct entry ID
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry bag
	 * @throws NoSuchEntryBagException if a matching ct entry bag could not be found
	 */
	@Override
	public CTEntryBag findByO_C_First(long ownerCTEntryId, long ctCollectionId,
		OrderByComparator<CTEntryBag> orderByComparator)
		throws NoSuchEntryBagException {
		CTEntryBag ctEntryBag = fetchByO_C_First(ownerCTEntryId,
				ctCollectionId, orderByComparator);

		if (ctEntryBag != null) {
			return ctEntryBag;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("ownerCTEntryId=");
		msg.append(ownerCTEntryId);

		msg.append(", ctCollectionId=");
		msg.append(ctCollectionId);

		msg.append("}");

		throw new NoSuchEntryBagException(msg.toString());
	}

	/**
	 * Returns the first ct entry bag in the ordered set where ownerCTEntryId = &#63; and ctCollectionId = &#63;.
	 *
	 * @param ownerCTEntryId the owner ct entry ID
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry bag, or <code>null</code> if a matching ct entry bag could not be found
	 */
	@Override
	public CTEntryBag fetchByO_C_First(long ownerCTEntryId,
		long ctCollectionId, OrderByComparator<CTEntryBag> orderByComparator) {
		List<CTEntryBag> list = findByO_C(ownerCTEntryId, ctCollectionId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ct entry bag in the ordered set where ownerCTEntryId = &#63; and ctCollectionId = &#63;.
	 *
	 * @param ownerCTEntryId the owner ct entry ID
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry bag
	 * @throws NoSuchEntryBagException if a matching ct entry bag could not be found
	 */
	@Override
	public CTEntryBag findByO_C_Last(long ownerCTEntryId, long ctCollectionId,
		OrderByComparator<CTEntryBag> orderByComparator)
		throws NoSuchEntryBagException {
		CTEntryBag ctEntryBag = fetchByO_C_Last(ownerCTEntryId, ctCollectionId,
				orderByComparator);

		if (ctEntryBag != null) {
			return ctEntryBag;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("ownerCTEntryId=");
		msg.append(ownerCTEntryId);

		msg.append(", ctCollectionId=");
		msg.append(ctCollectionId);

		msg.append("}");

		throw new NoSuchEntryBagException(msg.toString());
	}

	/**
	 * Returns the last ct entry bag in the ordered set where ownerCTEntryId = &#63; and ctCollectionId = &#63;.
	 *
	 * @param ownerCTEntryId the owner ct entry ID
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry bag, or <code>null</code> if a matching ct entry bag could not be found
	 */
	@Override
	public CTEntryBag fetchByO_C_Last(long ownerCTEntryId, long ctCollectionId,
		OrderByComparator<CTEntryBag> orderByComparator) {
		int count = countByO_C(ownerCTEntryId, ctCollectionId);

		if (count == 0) {
			return null;
		}

		List<CTEntryBag> list = findByO_C(ownerCTEntryId, ctCollectionId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ct entry bags before and after the current ct entry bag in the ordered set where ownerCTEntryId = &#63; and ctCollectionId = &#63;.
	 *
	 * @param ctEntryBagId the primary key of the current ct entry bag
	 * @param ownerCTEntryId the owner ct entry ID
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct entry bag
	 * @throws NoSuchEntryBagException if a ct entry bag with the primary key could not be found
	 */
	@Override
	public CTEntryBag[] findByO_C_PrevAndNext(long ctEntryBagId,
		long ownerCTEntryId, long ctCollectionId,
		OrderByComparator<CTEntryBag> orderByComparator)
		throws NoSuchEntryBagException {
		CTEntryBag ctEntryBag = findByPrimaryKey(ctEntryBagId);

		Session session = null;

		try {
			session = openSession();

			CTEntryBag[] array = new CTEntryBagImpl[3];

			array[0] = getByO_C_PrevAndNext(session, ctEntryBag,
					ownerCTEntryId, ctCollectionId, orderByComparator, true);

			array[1] = ctEntryBag;

			array[2] = getByO_C_PrevAndNext(session, ctEntryBag,
					ownerCTEntryId, ctCollectionId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CTEntryBag getByO_C_PrevAndNext(Session session,
		CTEntryBag ctEntryBag, long ownerCTEntryId, long ctCollectionId,
		OrderByComparator<CTEntryBag> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_CTENTRYBAG_WHERE);

		query.append(_FINDER_COLUMN_O_C_OWNERCTENTRYID_2);

		query.append(_FINDER_COLUMN_O_C_CTCOLLECTIONID_2);

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
			query.append(CTEntryBagModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(ownerCTEntryId);

		qPos.add(ctCollectionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					ctEntryBag)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<CTEntryBag> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ct entry bags where ownerCTEntryId = &#63; and ctCollectionId = &#63; from the database.
	 *
	 * @param ownerCTEntryId the owner ct entry ID
	 * @param ctCollectionId the ct collection ID
	 */
	@Override
	public void removeByO_C(long ownerCTEntryId, long ctCollectionId) {
		for (CTEntryBag ctEntryBag : findByO_C(ownerCTEntryId, ctCollectionId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ctEntryBag);
		}
	}

	/**
	 * Returns the number of ct entry bags where ownerCTEntryId = &#63; and ctCollectionId = &#63;.
	 *
	 * @param ownerCTEntryId the owner ct entry ID
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching ct entry bags
	 */
	@Override
	public int countByO_C(long ownerCTEntryId, long ctCollectionId) {
		FinderPath finderPath = _finderPathCountByO_C;

		Object[] finderArgs = new Object[] { ownerCTEntryId, ctCollectionId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CTENTRYBAG_WHERE);

			query.append(_FINDER_COLUMN_O_C_OWNERCTENTRYID_2);

			query.append(_FINDER_COLUMN_O_C_CTCOLLECTIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ownerCTEntryId);

				qPos.add(ctCollectionId);

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

	private static final String _FINDER_COLUMN_O_C_OWNERCTENTRYID_2 = "ctEntryBag.ownerCTEntryId = ? AND ";
	private static final String _FINDER_COLUMN_O_C_CTCOLLECTIONID_2 = "ctEntryBag.ctCollectionId = ?";

	public CTEntryBagPersistenceImpl() {
		setModelClass(CTEntryBag.class);

		setModelImplClass(CTEntryBagImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(CTEntryBagModelImpl.ENTITY_CACHE_ENABLED);
	}

	/**
	 * Caches the ct entry bag in the entity cache if it is enabled.
	 *
	 * @param ctEntryBag the ct entry bag
	 */
	@Override
	public void cacheResult(CTEntryBag ctEntryBag) {
		entityCache.putResult(CTEntryBagModelImpl.ENTITY_CACHE_ENABLED,
			CTEntryBagImpl.class, ctEntryBag.getPrimaryKey(), ctEntryBag);

		ctEntryBag.resetOriginalValues();
	}

	/**
	 * Caches the ct entry bags in the entity cache if it is enabled.
	 *
	 * @param ctEntryBags the ct entry bags
	 */
	@Override
	public void cacheResult(List<CTEntryBag> ctEntryBags) {
		for (CTEntryBag ctEntryBag : ctEntryBags) {
			if (entityCache.getResult(
						CTEntryBagModelImpl.ENTITY_CACHE_ENABLED,
						CTEntryBagImpl.class, ctEntryBag.getPrimaryKey()) == null) {
				cacheResult(ctEntryBag);
			}
			else {
				ctEntryBag.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all ct entry bags.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CTEntryBagImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the ct entry bag.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CTEntryBag ctEntryBag) {
		entityCache.removeResult(CTEntryBagModelImpl.ENTITY_CACHE_ENABLED,
			CTEntryBagImpl.class, ctEntryBag.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<CTEntryBag> ctEntryBags) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CTEntryBag ctEntryBag : ctEntryBags) {
			entityCache.removeResult(CTEntryBagModelImpl.ENTITY_CACHE_ENABLED,
				CTEntryBagImpl.class, ctEntryBag.getPrimaryKey());
		}
	}

	/**
	 * Creates a new ct entry bag with the primary key. Does not add the ct entry bag to the database.
	 *
	 * @param ctEntryBagId the primary key for the new ct entry bag
	 * @return the new ct entry bag
	 */
	@Override
	public CTEntryBag create(long ctEntryBagId) {
		CTEntryBag ctEntryBag = new CTEntryBagImpl();

		ctEntryBag.setNew(true);
		ctEntryBag.setPrimaryKey(ctEntryBagId);

		ctEntryBag.setCompanyId(companyProvider.getCompanyId());

		return ctEntryBag;
	}

	/**
	 * Removes the ct entry bag with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctEntryBagId the primary key of the ct entry bag
	 * @return the ct entry bag that was removed
	 * @throws NoSuchEntryBagException if a ct entry bag with the primary key could not be found
	 */
	@Override
	public CTEntryBag remove(long ctEntryBagId) throws NoSuchEntryBagException {
		return remove((Serializable)ctEntryBagId);
	}

	/**
	 * Removes the ct entry bag with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the ct entry bag
	 * @return the ct entry bag that was removed
	 * @throws NoSuchEntryBagException if a ct entry bag with the primary key could not be found
	 */
	@Override
	public CTEntryBag remove(Serializable primaryKey)
		throws NoSuchEntryBagException {
		Session session = null;

		try {
			session = openSession();

			CTEntryBag ctEntryBag = (CTEntryBag)session.get(CTEntryBagImpl.class,
					primaryKey);

			if (ctEntryBag == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryBagException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(ctEntryBag);
		}
		catch (NoSuchEntryBagException nsee) {
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
	protected CTEntryBag removeImpl(CTEntryBag ctEntryBag) {
		ctEntryBagToCTEntryTableMapper.deleteLeftPrimaryKeyTableMappings(ctEntryBag.getPrimaryKey());

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ctEntryBag)) {
				ctEntryBag = (CTEntryBag)session.get(CTEntryBagImpl.class,
						ctEntryBag.getPrimaryKeyObj());
			}

			if (ctEntryBag != null) {
				session.delete(ctEntryBag);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (ctEntryBag != null) {
			clearCache(ctEntryBag);
		}

		return ctEntryBag;
	}

	@Override
	public CTEntryBag updateImpl(CTEntryBag ctEntryBag) {
		boolean isNew = ctEntryBag.isNew();

		if (!(ctEntryBag instanceof CTEntryBagModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(ctEntryBag.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(ctEntryBag);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in ctEntryBag proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CTEntryBag implementation " +
				ctEntryBag.getClass());
		}

		CTEntryBagModelImpl ctEntryBagModelImpl = (CTEntryBagModelImpl)ctEntryBag;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (ctEntryBag.getCreateDate() == null)) {
			if (serviceContext == null) {
				ctEntryBag.setCreateDate(now);
			}
			else {
				ctEntryBag.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!ctEntryBagModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				ctEntryBag.setModifiedDate(now);
			}
			else {
				ctEntryBag.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctEntryBag.isNew()) {
				session.save(ctEntryBag);

				ctEntryBag.setNew(false);
			}
			else {
				ctEntryBag = (CTEntryBag)session.merge(ctEntryBag);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CTEntryBagModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					ctEntryBagModelImpl.getOwnerCTEntryId(),
					ctEntryBagModelImpl.getCtCollectionId()
				};

			finderCache.removeResult(_finderPathCountByO_C, args);
			finderCache.removeResult(_finderPathWithoutPaginationFindByO_C, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(_finderPathWithoutPaginationFindAll,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((ctEntryBagModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByO_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ctEntryBagModelImpl.getOriginalOwnerCTEntryId(),
						ctEntryBagModelImpl.getOriginalCtCollectionId()
					};

				finderCache.removeResult(_finderPathCountByO_C, args);
				finderCache.removeResult(_finderPathWithoutPaginationFindByO_C,
					args);

				args = new Object[] {
						ctEntryBagModelImpl.getOwnerCTEntryId(),
						ctEntryBagModelImpl.getCtCollectionId()
					};

				finderCache.removeResult(_finderPathCountByO_C, args);
				finderCache.removeResult(_finderPathWithoutPaginationFindByO_C,
					args);
			}
		}

		entityCache.putResult(CTEntryBagModelImpl.ENTITY_CACHE_ENABLED,
			CTEntryBagImpl.class, ctEntryBag.getPrimaryKey(), ctEntryBag, false);

		ctEntryBag.resetOriginalValues();

		return ctEntryBag;
	}

	/**
	 * Returns the ct entry bag with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ct entry bag
	 * @return the ct entry bag
	 * @throws NoSuchEntryBagException if a ct entry bag with the primary key could not be found
	 */
	@Override
	public CTEntryBag findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryBagException {
		CTEntryBag ctEntryBag = fetchByPrimaryKey(primaryKey);

		if (ctEntryBag == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryBagException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return ctEntryBag;
	}

	/**
	 * Returns the ct entry bag with the primary key or throws a <code>NoSuchEntryBagException</code> if it could not be found.
	 *
	 * @param ctEntryBagId the primary key of the ct entry bag
	 * @return the ct entry bag
	 * @throws NoSuchEntryBagException if a ct entry bag with the primary key could not be found
	 */
	@Override
	public CTEntryBag findByPrimaryKey(long ctEntryBagId)
		throws NoSuchEntryBagException {
		return findByPrimaryKey((Serializable)ctEntryBagId);
	}

	/**
	 * Returns the ct entry bag with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ctEntryBagId the primary key of the ct entry bag
	 * @return the ct entry bag, or <code>null</code> if a ct entry bag with the primary key could not be found
	 */
	@Override
	public CTEntryBag fetchByPrimaryKey(long ctEntryBagId) {
		return fetchByPrimaryKey((Serializable)ctEntryBagId);
	}

	/**
	 * Returns all the ct entry bags.
	 *
	 * @return the ct entry bags
	 */
	@Override
	public List<CTEntryBag> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct entry bags.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryBagModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entry bags
	 * @param end the upper bound of the range of ct entry bags (not inclusive)
	 * @return the range of ct entry bags
	 */
	@Override
	public List<CTEntryBag> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct entry bags.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryBagModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entry bags
	 * @param end the upper bound of the range of ct entry bags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct entry bags
	 */
	@Override
	public List<CTEntryBag> findAll(int start, int end,
		OrderByComparator<CTEntryBag> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct entry bags.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryBagModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entry bags
	 * @param end the upper bound of the range of ct entry bags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of ct entry bags
	 */
	@Override
	public List<CTEntryBag> findAll(int start, int end,
		OrderByComparator<CTEntryBag> orderByComparator,
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
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<CTEntryBag> list = null;

		if (retrieveFromCache) {
			list = (List<CTEntryBag>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CTENTRYBAG);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CTENTRYBAG;

				if (pagination) {
					sql = sql.concat(CTEntryBagModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CTEntryBag>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CTEntryBag>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the ct entry bags from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CTEntryBag ctEntryBag : findAll()) {
			remove(ctEntryBag);
		}
	}

	/**
	 * Returns the number of ct entry bags.
	 *
	 * @return the number of ct entry bags
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(_finderPathCountAll,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CTENTRYBAG);

				count = (Long)q.uniqueResult();

				finderCache.putResult(_finderPathCountAll, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the primaryKeys of ct entries associated with the ct entry bag.
	 *
	 * @param pk the primary key of the ct entry bag
	 * @return long[] of the primaryKeys of ct entries associated with the ct entry bag
	 */
	@Override
	public long[] getCTEntryPrimaryKeys(long pk) {
		long[] pks = ctEntryBagToCTEntryTableMapper.getRightPrimaryKeys(pk);

		return pks.clone();
	}

	/**
	 * Returns all the ct entries associated with the ct entry bag.
	 *
	 * @param pk the primary key of the ct entry bag
	 * @return the ct entries associated with the ct entry bag
	 */
	@Override
	public List<com.liferay.change.tracking.model.CTEntry> getCTEntries(long pk) {
		return getCTEntries(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the ct entries associated with the ct entry bag.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryBagModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the ct entry bag
	 * @param start the lower bound of the range of ct entry bags
	 * @param end the upper bound of the range of ct entry bags (not inclusive)
	 * @return the range of ct entries associated with the ct entry bag
	 */
	@Override
	public List<com.liferay.change.tracking.model.CTEntry> getCTEntries(
		long pk, int start, int end) {
		return getCTEntries(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct entries associated with the ct entry bag.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryBagModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the ct entry bag
	 * @param start the lower bound of the range of ct entry bags
	 * @param end the upper bound of the range of ct entry bags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct entries associated with the ct entry bag
	 */
	@Override
	public List<com.liferay.change.tracking.model.CTEntry> getCTEntries(
		long pk, int start, int end,
		OrderByComparator<com.liferay.change.tracking.model.CTEntry> orderByComparator) {
		return ctEntryBagToCTEntryTableMapper.getRightBaseModels(pk, start,
			end, orderByComparator);
	}

	/**
	 * Returns the number of ct entries associated with the ct entry bag.
	 *
	 * @param pk the primary key of the ct entry bag
	 * @return the number of ct entries associated with the ct entry bag
	 */
	@Override
	public int getCTEntriesSize(long pk) {
		long[] pks = ctEntryBagToCTEntryTableMapper.getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the ct entry is associated with the ct entry bag.
	 *
	 * @param pk the primary key of the ct entry bag
	 * @param ctEntryPK the primary key of the ct entry
	 * @return <code>true</code> if the ct entry is associated with the ct entry bag; <code>false</code> otherwise
	 */
	@Override
	public boolean containsCTEntry(long pk, long ctEntryPK) {
		return ctEntryBagToCTEntryTableMapper.containsTableMapping(pk, ctEntryPK);
	}

	/**
	 * Returns <code>true</code> if the ct entry bag has any ct entries associated with it.
	 *
	 * @param pk the primary key of the ct entry bag to check for associations with ct entries
	 * @return <code>true</code> if the ct entry bag has any ct entries associated with it; <code>false</code> otherwise
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
	 * Adds an association between the ct entry bag and the ct entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry bag
	 * @param ctEntryPK the primary key of the ct entry
	 */
	@Override
	public void addCTEntry(long pk, long ctEntryPK) {
		CTEntryBag ctEntryBag = fetchByPrimaryKey(pk);

		if (ctEntryBag == null) {
			ctEntryBagToCTEntryTableMapper.addTableMapping(companyProvider.getCompanyId(),
				pk, ctEntryPK);
		}
		else {
			ctEntryBagToCTEntryTableMapper.addTableMapping(ctEntryBag.getCompanyId(),
				pk, ctEntryPK);
		}
	}

	/**
	 * Adds an association between the ct entry bag and the ct entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry bag
	 * @param ctEntry the ct entry
	 */
	@Override
	public void addCTEntry(long pk,
		com.liferay.change.tracking.model.CTEntry ctEntry) {
		CTEntryBag ctEntryBag = fetchByPrimaryKey(pk);

		if (ctEntryBag == null) {
			ctEntryBagToCTEntryTableMapper.addTableMapping(companyProvider.getCompanyId(),
				pk, ctEntry.getPrimaryKey());
		}
		else {
			ctEntryBagToCTEntryTableMapper.addTableMapping(ctEntryBag.getCompanyId(),
				pk, ctEntry.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the ct entry bag and the ct entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry bag
	 * @param ctEntryPKs the primary keys of the ct entries
	 */
	@Override
	public void addCTEntries(long pk, long[] ctEntryPKs) {
		long companyId = 0;

		CTEntryBag ctEntryBag = fetchByPrimaryKey(pk);

		if (ctEntryBag == null) {
			companyId = companyProvider.getCompanyId();
		}
		else {
			companyId = ctEntryBag.getCompanyId();
		}

		ctEntryBagToCTEntryTableMapper.addTableMappings(companyId, pk,
			ctEntryPKs);
	}

	/**
	 * Adds an association between the ct entry bag and the ct entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry bag
	 * @param ctEntries the ct entries
	 */
	@Override
	public void addCTEntries(long pk,
		List<com.liferay.change.tracking.model.CTEntry> ctEntries) {
		addCTEntries(pk,
			ListUtil.toLongArray(ctEntries,
				com.liferay.change.tracking.model.CTEntry.CT_ENTRY_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the ct entry bag and its ct entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry bag to clear the associated ct entries from
	 */
	@Override
	public void clearCTEntries(long pk) {
		ctEntryBagToCTEntryTableMapper.deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the ct entry bag and the ct entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry bag
	 * @param ctEntryPK the primary key of the ct entry
	 */
	@Override
	public void removeCTEntry(long pk, long ctEntryPK) {
		ctEntryBagToCTEntryTableMapper.deleteTableMapping(pk, ctEntryPK);
	}

	/**
	 * Removes the association between the ct entry bag and the ct entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry bag
	 * @param ctEntry the ct entry
	 */
	@Override
	public void removeCTEntry(long pk,
		com.liferay.change.tracking.model.CTEntry ctEntry) {
		ctEntryBagToCTEntryTableMapper.deleteTableMapping(pk,
			ctEntry.getPrimaryKey());
	}

	/**
	 * Removes the association between the ct entry bag and the ct entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry bag
	 * @param ctEntryPKs the primary keys of the ct entries
	 */
	@Override
	public void removeCTEntries(long pk, long[] ctEntryPKs) {
		ctEntryBagToCTEntryTableMapper.deleteTableMappings(pk, ctEntryPKs);
	}

	/**
	 * Removes the association between the ct entry bag and the ct entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry bag
	 * @param ctEntries the ct entries
	 */
	@Override
	public void removeCTEntries(long pk,
		List<com.liferay.change.tracking.model.CTEntry> ctEntries) {
		removeCTEntries(pk,
			ListUtil.toLongArray(ctEntries,
				com.liferay.change.tracking.model.CTEntry.CT_ENTRY_ID_ACCESSOR));
	}

	/**
	 * Sets the ct entries associated with the ct entry bag, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry bag
	 * @param ctEntryPKs the primary keys of the ct entries to be associated with the ct entry bag
	 */
	@Override
	public void setCTEntries(long pk, long[] ctEntryPKs) {
		Set<Long> newCTEntryPKsSet = SetUtil.fromArray(ctEntryPKs);
		Set<Long> oldCTEntryPKsSet = SetUtil.fromArray(ctEntryBagToCTEntryTableMapper.getRightPrimaryKeys(
					pk));

		Set<Long> removeCTEntryPKsSet = new HashSet<Long>(oldCTEntryPKsSet);

		removeCTEntryPKsSet.removeAll(newCTEntryPKsSet);

		ctEntryBagToCTEntryTableMapper.deleteTableMappings(pk,
			ArrayUtil.toLongArray(removeCTEntryPKsSet));

		newCTEntryPKsSet.removeAll(oldCTEntryPKsSet);

		long companyId = 0;

		CTEntryBag ctEntryBag = fetchByPrimaryKey(pk);

		if (ctEntryBag == null) {
			companyId = companyProvider.getCompanyId();
		}
		else {
			companyId = ctEntryBag.getCompanyId();
		}

		ctEntryBagToCTEntryTableMapper.addTableMappings(companyId, pk,
			ArrayUtil.toLongArray(newCTEntryPKsSet));
	}

	/**
	 * Sets the ct entries associated with the ct entry bag, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry bag
	 * @param ctEntries the ct entries to be associated with the ct entry bag
	 */
	@Override
	public void setCTEntries(long pk,
		List<com.liferay.change.tracking.model.CTEntry> ctEntries) {
		try {
			long[] ctEntryPKs = new long[ctEntries.size()];

			for (int i = 0; i < ctEntries.size(); i++) {
				com.liferay.change.tracking.model.CTEntry ctEntry = ctEntries.get(i);

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
		return "ctEntryBagId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CTENTRYBAG;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CTEntryBagModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the ct entry bag persistence.
	 */
	public void afterPropertiesSet() {
		ctEntryBagToCTEntryTableMapper = TableMapperFactory.getTableMapper("CTEntryBags_CTEntries",
				"companyId", "ctEntryBagId", "ctEntryId", this,
				ctEntryPersistence);

		_finderPathWithPaginationFindAll = new FinderPath(CTEntryBagModelImpl.ENTITY_CACHE_ENABLED,
				CTEntryBagModelImpl.FINDER_CACHE_ENABLED, CTEntryBagImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(CTEntryBagModelImpl.ENTITY_CACHE_ENABLED,
				CTEntryBagModelImpl.FINDER_CACHE_ENABLED, CTEntryBagImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
				new String[0]);

		_finderPathCountAll = new FinderPath(CTEntryBagModelImpl.ENTITY_CACHE_ENABLED,
				CTEntryBagModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
				new String[0]);

		_finderPathWithPaginationFindByO_C = new FinderPath(CTEntryBagModelImpl.ENTITY_CACHE_ENABLED,
				CTEntryBagModelImpl.FINDER_CACHE_ENABLED, CTEntryBagImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByO_C",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByO_C = new FinderPath(CTEntryBagModelImpl.ENTITY_CACHE_ENABLED,
				CTEntryBagModelImpl.FINDER_CACHE_ENABLED, CTEntryBagImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByO_C",
				new String[] { Long.class.getName(), Long.class.getName() },
				CTEntryBagModelImpl.OWNERCTENTRYID_COLUMN_BITMASK |
				CTEntryBagModelImpl.CTCOLLECTIONID_COLUMN_BITMASK);

		_finderPathCountByO_C = new FinderPath(CTEntryBagModelImpl.ENTITY_CACHE_ENABLED,
				CTEntryBagModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByO_C",
				new String[] { Long.class.getName(), Long.class.getName() });
	}

	public void destroy() {
		entityCache.removeCache(CTEntryBagImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		TableMapperFactory.removeTableMapper("CTEntryBags_CTEntries");
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	@BeanReference(type = CTEntryPersistence.class)
	protected CTEntryPersistence ctEntryPersistence;
	protected TableMapper<CTEntryBag, com.liferay.change.tracking.model.CTEntry> ctEntryBagToCTEntryTableMapper;
	private static final String _SQL_SELECT_CTENTRYBAG = "SELECT ctEntryBag FROM CTEntryBag ctEntryBag";
	private static final String _SQL_SELECT_CTENTRYBAG_WHERE = "SELECT ctEntryBag FROM CTEntryBag ctEntryBag WHERE ";
	private static final String _SQL_COUNT_CTENTRYBAG = "SELECT COUNT(ctEntryBag) FROM CTEntryBag ctEntryBag";
	private static final String _SQL_COUNT_CTENTRYBAG_WHERE = "SELECT COUNT(ctEntryBag) FROM CTEntryBag ctEntryBag WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "ctEntryBag.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CTEntryBag exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CTEntryBag exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CTEntryBagPersistenceImpl.class);
}