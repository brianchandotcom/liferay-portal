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

package com.liferay.commerce.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.exception.NoSuchShipmentItemException;
import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.commerce.model.impl.CommerceShipmentItemImpl;
import com.liferay.commerce.model.impl.CommerceShipmentItemModelImpl;
import com.liferay.commerce.service.persistence.CommerceShipmentItemPersistence;

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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the commerce shipment item service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShipmentItemPersistence
 * @see com.liferay.commerce.service.persistence.CommerceShipmentItemUtil
 * @generated
 */
@ProviderType
public class CommerceShipmentItemPersistenceImpl extends BasePersistenceImpl<CommerceShipmentItem>
	implements CommerceShipmentItemPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceShipmentItemUtil} to access the commerce shipment item persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceShipmentItemImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceShipmentItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShipmentItemModelImpl.FINDER_CACHE_ENABLED,
			CommerceShipmentItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceShipmentItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShipmentItemModelImpl.FINDER_CACHE_ENABLED,
			CommerceShipmentItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceShipmentItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShipmentItemModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(CommerceShipmentItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShipmentItemModelImpl.FINDER_CACHE_ENABLED,
			CommerceShipmentItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(CommerceShipmentItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShipmentItemModelImpl.FINDER_CACHE_ENABLED,
			CommerceShipmentItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			CommerceShipmentItemModelImpl.GROUPID_COLUMN_BITMASK |
			CommerceShipmentItemModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(CommerceShipmentItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShipmentItemModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce shipment items where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching commerce shipment items
	 */
	@Override
	public List<CommerceShipmentItem> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce shipment items where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce shipment items
	 * @param end the upper bound of the range of commerce shipment items (not inclusive)
	 * @return the range of matching commerce shipment items
	 */
	@Override
	public List<CommerceShipmentItem> findByGroupId(long groupId, int start,
		int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce shipment items where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce shipment items
	 * @param end the upper bound of the range of commerce shipment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipment items
	 */
	@Override
	public List<CommerceShipmentItem> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CommerceShipmentItem> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce shipment items where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce shipment items
	 * @param end the upper bound of the range of commerce shipment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce shipment items
	 */
	@Override
	public List<CommerceShipmentItem> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CommerceShipmentItem> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId, start, end, orderByComparator };
		}

		List<CommerceShipmentItem> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceShipmentItem>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceShipmentItem commerceShipmentItem : list) {
					if ((groupId != commerceShipmentItem.getGroupId())) {
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

			query.append(_SQL_SELECT_COMMERCESHIPMENTITEM_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceShipmentItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<CommerceShipmentItem>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceShipmentItem>)QueryUtil.list(q,
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
	 * Returns the first commerce shipment item in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipment item
	 * @throws NoSuchShipmentItemException if a matching commerce shipment item could not be found
	 */
	@Override
	public CommerceShipmentItem findByGroupId_First(long groupId,
		OrderByComparator<CommerceShipmentItem> orderByComparator)
		throws NoSuchShipmentItemException {
		CommerceShipmentItem commerceShipmentItem = fetchByGroupId_First(groupId,
				orderByComparator);

		if (commerceShipmentItem != null) {
			return commerceShipmentItem;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchShipmentItemException(msg.toString());
	}

	/**
	 * Returns the first commerce shipment item in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipment item, or <code>null</code> if a matching commerce shipment item could not be found
	 */
	@Override
	public CommerceShipmentItem fetchByGroupId_First(long groupId,
		OrderByComparator<CommerceShipmentItem> orderByComparator) {
		List<CommerceShipmentItem> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce shipment item in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipment item
	 * @throws NoSuchShipmentItemException if a matching commerce shipment item could not be found
	 */
	@Override
	public CommerceShipmentItem findByGroupId_Last(long groupId,
		OrderByComparator<CommerceShipmentItem> orderByComparator)
		throws NoSuchShipmentItemException {
		CommerceShipmentItem commerceShipmentItem = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (commerceShipmentItem != null) {
			return commerceShipmentItem;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchShipmentItemException(msg.toString());
	}

	/**
	 * Returns the last commerce shipment item in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipment item, or <code>null</code> if a matching commerce shipment item could not be found
	 */
	@Override
	public CommerceShipmentItem fetchByGroupId_Last(long groupId,
		OrderByComparator<CommerceShipmentItem> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<CommerceShipmentItem> list = findByGroupId(groupId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce shipment items before and after the current commerce shipment item in the ordered set where groupId = &#63;.
	 *
	 * @param commerceShipmentItemId the primary key of the current commerce shipment item
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce shipment item
	 * @throws NoSuchShipmentItemException if a commerce shipment item with the primary key could not be found
	 */
	@Override
	public CommerceShipmentItem[] findByGroupId_PrevAndNext(
		long commerceShipmentItemId, long groupId,
		OrderByComparator<CommerceShipmentItem> orderByComparator)
		throws NoSuchShipmentItemException {
		CommerceShipmentItem commerceShipmentItem = findByPrimaryKey(commerceShipmentItemId);

		Session session = null;

		try {
			session = openSession();

			CommerceShipmentItem[] array = new CommerceShipmentItemImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, commerceShipmentItem,
					groupId, orderByComparator, true);

			array[1] = commerceShipmentItem;

			array[2] = getByGroupId_PrevAndNext(session, commerceShipmentItem,
					groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceShipmentItem getByGroupId_PrevAndNext(Session session,
		CommerceShipmentItem commerceShipmentItem, long groupId,
		OrderByComparator<CommerceShipmentItem> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCESHIPMENTITEM_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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
			query.append(CommerceShipmentItemModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceShipmentItem);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceShipmentItem> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce shipment items where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (CommerceShipmentItem commerceShipmentItem : findByGroupId(
				groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceShipmentItem);
		}
	}

	/**
	 * Returns the number of commerce shipment items where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching commerce shipment items
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCESHIPMENTITEM_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "commerceShipmentItem.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C = new FinderPath(CommerceShipmentItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShipmentItemModelImpl.FINDER_CACHE_ENABLED,
			CommerceShipmentItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C = new FinderPath(CommerceShipmentItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShipmentItemModelImpl.FINDER_CACHE_ENABLED,
			CommerceShipmentItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			CommerceShipmentItemModelImpl.GROUPID_COLUMN_BITMASK |
			CommerceShipmentItemModelImpl.COMMERCESHIPMENTID_COLUMN_BITMASK |
			CommerceShipmentItemModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C = new FinderPath(CommerceShipmentItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShipmentItemModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the commerce shipment items where groupId = &#63; and commerceShipmentId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param commerceShipmentId the commerce shipment ID
	 * @return the matching commerce shipment items
	 */
	@Override
	public List<CommerceShipmentItem> findByG_C(long groupId,
		long commerceShipmentId) {
		return findByG_C(groupId, commerceShipmentId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce shipment items where groupId = &#63; and commerceShipmentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param commerceShipmentId the commerce shipment ID
	 * @param start the lower bound of the range of commerce shipment items
	 * @param end the upper bound of the range of commerce shipment items (not inclusive)
	 * @return the range of matching commerce shipment items
	 */
	@Override
	public List<CommerceShipmentItem> findByG_C(long groupId,
		long commerceShipmentId, int start, int end) {
		return findByG_C(groupId, commerceShipmentId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce shipment items where groupId = &#63; and commerceShipmentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param commerceShipmentId the commerce shipment ID
	 * @param start the lower bound of the range of commerce shipment items
	 * @param end the upper bound of the range of commerce shipment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipment items
	 */
	@Override
	public List<CommerceShipmentItem> findByG_C(long groupId,
		long commerceShipmentId, int start, int end,
		OrderByComparator<CommerceShipmentItem> orderByComparator) {
		return findByG_C(groupId, commerceShipmentId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce shipment items where groupId = &#63; and commerceShipmentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param commerceShipmentId the commerce shipment ID
	 * @param start the lower bound of the range of commerce shipment items
	 * @param end the upper bound of the range of commerce shipment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce shipment items
	 */
	@Override
	public List<CommerceShipmentItem> findByG_C(long groupId,
		long commerceShipmentId, int start, int end,
		OrderByComparator<CommerceShipmentItem> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C;
			finderArgs = new Object[] { groupId, commerceShipmentId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C;
			finderArgs = new Object[] {
					groupId, commerceShipmentId,
					
					start, end, orderByComparator
				};
		}

		List<CommerceShipmentItem> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceShipmentItem>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceShipmentItem commerceShipmentItem : list) {
					if ((groupId != commerceShipmentItem.getGroupId()) ||
							(commerceShipmentId != commerceShipmentItem.getCommerceShipmentId())) {
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

			query.append(_SQL_SELECT_COMMERCESHIPMENTITEM_WHERE);

			query.append(_FINDER_COLUMN_G_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_COMMERCESHIPMENTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceShipmentItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(commerceShipmentId);

				if (!pagination) {
					list = (List<CommerceShipmentItem>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceShipmentItem>)QueryUtil.list(q,
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
	 * Returns the first commerce shipment item in the ordered set where groupId = &#63; and commerceShipmentId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param commerceShipmentId the commerce shipment ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipment item
	 * @throws NoSuchShipmentItemException if a matching commerce shipment item could not be found
	 */
	@Override
	public CommerceShipmentItem findByG_C_First(long groupId,
		long commerceShipmentId,
		OrderByComparator<CommerceShipmentItem> orderByComparator)
		throws NoSuchShipmentItemException {
		CommerceShipmentItem commerceShipmentItem = fetchByG_C_First(groupId,
				commerceShipmentId, orderByComparator);

		if (commerceShipmentItem != null) {
			return commerceShipmentItem;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", commerceShipmentId=");
		msg.append(commerceShipmentId);

		msg.append("}");

		throw new NoSuchShipmentItemException(msg.toString());
	}

	/**
	 * Returns the first commerce shipment item in the ordered set where groupId = &#63; and commerceShipmentId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param commerceShipmentId the commerce shipment ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipment item, or <code>null</code> if a matching commerce shipment item could not be found
	 */
	@Override
	public CommerceShipmentItem fetchByG_C_First(long groupId,
		long commerceShipmentId,
		OrderByComparator<CommerceShipmentItem> orderByComparator) {
		List<CommerceShipmentItem> list = findByG_C(groupId,
				commerceShipmentId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce shipment item in the ordered set where groupId = &#63; and commerceShipmentId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param commerceShipmentId the commerce shipment ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipment item
	 * @throws NoSuchShipmentItemException if a matching commerce shipment item could not be found
	 */
	@Override
	public CommerceShipmentItem findByG_C_Last(long groupId,
		long commerceShipmentId,
		OrderByComparator<CommerceShipmentItem> orderByComparator)
		throws NoSuchShipmentItemException {
		CommerceShipmentItem commerceShipmentItem = fetchByG_C_Last(groupId,
				commerceShipmentId, orderByComparator);

		if (commerceShipmentItem != null) {
			return commerceShipmentItem;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", commerceShipmentId=");
		msg.append(commerceShipmentId);

		msg.append("}");

		throw new NoSuchShipmentItemException(msg.toString());
	}

	/**
	 * Returns the last commerce shipment item in the ordered set where groupId = &#63; and commerceShipmentId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param commerceShipmentId the commerce shipment ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipment item, or <code>null</code> if a matching commerce shipment item could not be found
	 */
	@Override
	public CommerceShipmentItem fetchByG_C_Last(long groupId,
		long commerceShipmentId,
		OrderByComparator<CommerceShipmentItem> orderByComparator) {
		int count = countByG_C(groupId, commerceShipmentId);

		if (count == 0) {
			return null;
		}

		List<CommerceShipmentItem> list = findByG_C(groupId,
				commerceShipmentId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce shipment items before and after the current commerce shipment item in the ordered set where groupId = &#63; and commerceShipmentId = &#63;.
	 *
	 * @param commerceShipmentItemId the primary key of the current commerce shipment item
	 * @param groupId the group ID
	 * @param commerceShipmentId the commerce shipment ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce shipment item
	 * @throws NoSuchShipmentItemException if a commerce shipment item with the primary key could not be found
	 */
	@Override
	public CommerceShipmentItem[] findByG_C_PrevAndNext(
		long commerceShipmentItemId, long groupId, long commerceShipmentId,
		OrderByComparator<CommerceShipmentItem> orderByComparator)
		throws NoSuchShipmentItemException {
		CommerceShipmentItem commerceShipmentItem = findByPrimaryKey(commerceShipmentItemId);

		Session session = null;

		try {
			session = openSession();

			CommerceShipmentItem[] array = new CommerceShipmentItemImpl[3];

			array[0] = getByG_C_PrevAndNext(session, commerceShipmentItem,
					groupId, commerceShipmentId, orderByComparator, true);

			array[1] = commerceShipmentItem;

			array[2] = getByG_C_PrevAndNext(session, commerceShipmentItem,
					groupId, commerceShipmentId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceShipmentItem getByG_C_PrevAndNext(Session session,
		CommerceShipmentItem commerceShipmentItem, long groupId,
		long commerceShipmentId,
		OrderByComparator<CommerceShipmentItem> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCESHIPMENTITEM_WHERE);

		query.append(_FINDER_COLUMN_G_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_COMMERCESHIPMENTID_2);

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
			query.append(CommerceShipmentItemModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(commerceShipmentId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceShipmentItem);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceShipmentItem> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce shipment items where groupId = &#63; and commerceShipmentId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param commerceShipmentId the commerce shipment ID
	 */
	@Override
	public void removeByG_C(long groupId, long commerceShipmentId) {
		for (CommerceShipmentItem commerceShipmentItem : findByG_C(groupId,
				commerceShipmentId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceShipmentItem);
		}
	}

	/**
	 * Returns the number of commerce shipment items where groupId = &#63; and commerceShipmentId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param commerceShipmentId the commerce shipment ID
	 * @return the number of matching commerce shipment items
	 */
	@Override
	public int countByG_C(long groupId, long commerceShipmentId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C;

		Object[] finderArgs = new Object[] { groupId, commerceShipmentId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCESHIPMENTITEM_WHERE);

			query.append(_FINDER_COLUMN_G_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_COMMERCESHIPMENTID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(commerceShipmentId);

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

	private static final String _FINDER_COLUMN_G_C_GROUPID_2 = "commerceShipmentItem.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_COMMERCESHIPMENTID_2 = "commerceShipmentItem.commerceShipmentId = ?";

	public CommerceShipmentItemPersistenceImpl() {
		setModelClass(CommerceShipmentItem.class);
	}

	/**
	 * Caches the commerce shipment item in the entity cache if it is enabled.
	 *
	 * @param commerceShipmentItem the commerce shipment item
	 */
	@Override
	public void cacheResult(CommerceShipmentItem commerceShipmentItem) {
		entityCache.putResult(CommerceShipmentItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShipmentItemImpl.class,
			commerceShipmentItem.getPrimaryKey(), commerceShipmentItem);

		commerceShipmentItem.resetOriginalValues();
	}

	/**
	 * Caches the commerce shipment items in the entity cache if it is enabled.
	 *
	 * @param commerceShipmentItems the commerce shipment items
	 */
	@Override
	public void cacheResult(List<CommerceShipmentItem> commerceShipmentItems) {
		for (CommerceShipmentItem commerceShipmentItem : commerceShipmentItems) {
			if (entityCache.getResult(
						CommerceShipmentItemModelImpl.ENTITY_CACHE_ENABLED,
						CommerceShipmentItemImpl.class,
						commerceShipmentItem.getPrimaryKey()) == null) {
				cacheResult(commerceShipmentItem);
			}
			else {
				commerceShipmentItem.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce shipment items.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceShipmentItemImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce shipment item.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceShipmentItem commerceShipmentItem) {
		entityCache.removeResult(CommerceShipmentItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShipmentItemImpl.class, commerceShipmentItem.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<CommerceShipmentItem> commerceShipmentItems) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceShipmentItem commerceShipmentItem : commerceShipmentItems) {
			entityCache.removeResult(CommerceShipmentItemModelImpl.ENTITY_CACHE_ENABLED,
				CommerceShipmentItemImpl.class,
				commerceShipmentItem.getPrimaryKey());
		}
	}

	/**
	 * Creates a new commerce shipment item with the primary key. Does not add the commerce shipment item to the database.
	 *
	 * @param commerceShipmentItemId the primary key for the new commerce shipment item
	 * @return the new commerce shipment item
	 */
	@Override
	public CommerceShipmentItem create(long commerceShipmentItemId) {
		CommerceShipmentItem commerceShipmentItem = new CommerceShipmentItemImpl();

		commerceShipmentItem.setNew(true);
		commerceShipmentItem.setPrimaryKey(commerceShipmentItemId);

		commerceShipmentItem.setCompanyId(companyProvider.getCompanyId());

		return commerceShipmentItem;
	}

	/**
	 * Removes the commerce shipment item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceShipmentItemId the primary key of the commerce shipment item
	 * @return the commerce shipment item that was removed
	 * @throws NoSuchShipmentItemException if a commerce shipment item with the primary key could not be found
	 */
	@Override
	public CommerceShipmentItem remove(long commerceShipmentItemId)
		throws NoSuchShipmentItemException {
		return remove((Serializable)commerceShipmentItemId);
	}

	/**
	 * Removes the commerce shipment item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce shipment item
	 * @return the commerce shipment item that was removed
	 * @throws NoSuchShipmentItemException if a commerce shipment item with the primary key could not be found
	 */
	@Override
	public CommerceShipmentItem remove(Serializable primaryKey)
		throws NoSuchShipmentItemException {
		Session session = null;

		try {
			session = openSession();

			CommerceShipmentItem commerceShipmentItem = (CommerceShipmentItem)session.get(CommerceShipmentItemImpl.class,
					primaryKey);

			if (commerceShipmentItem == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchShipmentItemException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceShipmentItem);
		}
		catch (NoSuchShipmentItemException nsee) {
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
	protected CommerceShipmentItem removeImpl(
		CommerceShipmentItem commerceShipmentItem) {
		commerceShipmentItem = toUnwrappedModel(commerceShipmentItem);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceShipmentItem)) {
				commerceShipmentItem = (CommerceShipmentItem)session.get(CommerceShipmentItemImpl.class,
						commerceShipmentItem.getPrimaryKeyObj());
			}

			if (commerceShipmentItem != null) {
				session.delete(commerceShipmentItem);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceShipmentItem != null) {
			clearCache(commerceShipmentItem);
		}

		return commerceShipmentItem;
	}

	@Override
	public CommerceShipmentItem updateImpl(
		CommerceShipmentItem commerceShipmentItem) {
		commerceShipmentItem = toUnwrappedModel(commerceShipmentItem);

		boolean isNew = commerceShipmentItem.isNew();

		CommerceShipmentItemModelImpl commerceShipmentItemModelImpl = (CommerceShipmentItemModelImpl)commerceShipmentItem;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceShipmentItem.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceShipmentItem.setCreateDate(now);
			}
			else {
				commerceShipmentItem.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!commerceShipmentItemModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceShipmentItem.setModifiedDate(now);
			}
			else {
				commerceShipmentItem.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceShipmentItem.isNew()) {
				session.save(commerceShipmentItem);

				commerceShipmentItem.setNew(false);
			}
			else {
				commerceShipmentItem = (CommerceShipmentItem)session.merge(commerceShipmentItem);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceShipmentItemModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commerceShipmentItemModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			args = new Object[] {
					commerceShipmentItemModelImpl.getGroupId(),
					commerceShipmentItemModelImpl.getCommerceShipmentId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commerceShipmentItemModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceShipmentItemModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { commerceShipmentItemModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((commerceShipmentItemModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceShipmentItemModelImpl.getOriginalGroupId(),
						commerceShipmentItemModelImpl.getOriginalCommerceShipmentId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C,
					args);

				args = new Object[] {
						commerceShipmentItemModelImpl.getGroupId(),
						commerceShipmentItemModelImpl.getCommerceShipmentId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C,
					args);
			}
		}

		entityCache.putResult(CommerceShipmentItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShipmentItemImpl.class,
			commerceShipmentItem.getPrimaryKey(), commerceShipmentItem, false);

		commerceShipmentItem.resetOriginalValues();

		return commerceShipmentItem;
	}

	protected CommerceShipmentItem toUnwrappedModel(
		CommerceShipmentItem commerceShipmentItem) {
		if (commerceShipmentItem instanceof CommerceShipmentItemImpl) {
			return commerceShipmentItem;
		}

		CommerceShipmentItemImpl commerceShipmentItemImpl = new CommerceShipmentItemImpl();

		commerceShipmentItemImpl.setNew(commerceShipmentItem.isNew());
		commerceShipmentItemImpl.setPrimaryKey(commerceShipmentItem.getPrimaryKey());

		commerceShipmentItemImpl.setCommerceShipmentItemId(commerceShipmentItem.getCommerceShipmentItemId());
		commerceShipmentItemImpl.setGroupId(commerceShipmentItem.getGroupId());
		commerceShipmentItemImpl.setCompanyId(commerceShipmentItem.getCompanyId());
		commerceShipmentItemImpl.setUserId(commerceShipmentItem.getUserId());
		commerceShipmentItemImpl.setUserName(commerceShipmentItem.getUserName());
		commerceShipmentItemImpl.setCreateDate(commerceShipmentItem.getCreateDate());
		commerceShipmentItemImpl.setModifiedDate(commerceShipmentItem.getModifiedDate());
		commerceShipmentItemImpl.setCommerceShipmentId(commerceShipmentItem.getCommerceShipmentId());
		commerceShipmentItemImpl.setCommerceOrderItemId(commerceShipmentItem.getCommerceOrderItemId());
		commerceShipmentItemImpl.setQuantity(commerceShipmentItem.getQuantity());

		return commerceShipmentItemImpl;
	}

	/**
	 * Returns the commerce shipment item with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce shipment item
	 * @return the commerce shipment item
	 * @throws NoSuchShipmentItemException if a commerce shipment item with the primary key could not be found
	 */
	@Override
	public CommerceShipmentItem findByPrimaryKey(Serializable primaryKey)
		throws NoSuchShipmentItemException {
		CommerceShipmentItem commerceShipmentItem = fetchByPrimaryKey(primaryKey);

		if (commerceShipmentItem == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchShipmentItemException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceShipmentItem;
	}

	/**
	 * Returns the commerce shipment item with the primary key or throws a {@link NoSuchShipmentItemException} if it could not be found.
	 *
	 * @param commerceShipmentItemId the primary key of the commerce shipment item
	 * @return the commerce shipment item
	 * @throws NoSuchShipmentItemException if a commerce shipment item with the primary key could not be found
	 */
	@Override
	public CommerceShipmentItem findByPrimaryKey(long commerceShipmentItemId)
		throws NoSuchShipmentItemException {
		return findByPrimaryKey((Serializable)commerceShipmentItemId);
	}

	/**
	 * Returns the commerce shipment item with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce shipment item
	 * @return the commerce shipment item, or <code>null</code> if a commerce shipment item with the primary key could not be found
	 */
	@Override
	public CommerceShipmentItem fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceShipmentItemModelImpl.ENTITY_CACHE_ENABLED,
				CommerceShipmentItemImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceShipmentItem commerceShipmentItem = (CommerceShipmentItem)serializable;

		if (commerceShipmentItem == null) {
			Session session = null;

			try {
				session = openSession();

				commerceShipmentItem = (CommerceShipmentItem)session.get(CommerceShipmentItemImpl.class,
						primaryKey);

				if (commerceShipmentItem != null) {
					cacheResult(commerceShipmentItem);
				}
				else {
					entityCache.putResult(CommerceShipmentItemModelImpl.ENTITY_CACHE_ENABLED,
						CommerceShipmentItemImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceShipmentItemModelImpl.ENTITY_CACHE_ENABLED,
					CommerceShipmentItemImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceShipmentItem;
	}

	/**
	 * Returns the commerce shipment item with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceShipmentItemId the primary key of the commerce shipment item
	 * @return the commerce shipment item, or <code>null</code> if a commerce shipment item with the primary key could not be found
	 */
	@Override
	public CommerceShipmentItem fetchByPrimaryKey(long commerceShipmentItemId) {
		return fetchByPrimaryKey((Serializable)commerceShipmentItemId);
	}

	@Override
	public Map<Serializable, CommerceShipmentItem> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceShipmentItem> map = new HashMap<Serializable, CommerceShipmentItem>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceShipmentItem commerceShipmentItem = fetchByPrimaryKey(primaryKey);

			if (commerceShipmentItem != null) {
				map.put(primaryKey, commerceShipmentItem);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceShipmentItemModelImpl.ENTITY_CACHE_ENABLED,
					CommerceShipmentItemImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CommerceShipmentItem)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCESHIPMENTITEM_WHERE_PKS_IN);

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

			for (CommerceShipmentItem commerceShipmentItem : (List<CommerceShipmentItem>)q.list()) {
				map.put(commerceShipmentItem.getPrimaryKeyObj(),
					commerceShipmentItem);

				cacheResult(commerceShipmentItem);

				uncachedPrimaryKeys.remove(commerceShipmentItem.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceShipmentItemModelImpl.ENTITY_CACHE_ENABLED,
					CommerceShipmentItemImpl.class, primaryKey, nullModel);
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
	 * Returns all the commerce shipment items.
	 *
	 * @return the commerce shipment items
	 */
	@Override
	public List<CommerceShipmentItem> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce shipment items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipment items
	 * @param end the upper bound of the range of commerce shipment items (not inclusive)
	 * @return the range of commerce shipment items
	 */
	@Override
	public List<CommerceShipmentItem> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce shipment items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipment items
	 * @param end the upper bound of the range of commerce shipment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce shipment items
	 */
	@Override
	public List<CommerceShipmentItem> findAll(int start, int end,
		OrderByComparator<CommerceShipmentItem> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce shipment items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipment items
	 * @param end the upper bound of the range of commerce shipment items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce shipment items
	 */
	@Override
	public List<CommerceShipmentItem> findAll(int start, int end,
		OrderByComparator<CommerceShipmentItem> orderByComparator,
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

		List<CommerceShipmentItem> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceShipmentItem>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCESHIPMENTITEM);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCESHIPMENTITEM;

				if (pagination) {
					sql = sql.concat(CommerceShipmentItemModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceShipmentItem>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceShipmentItem>)QueryUtil.list(q,
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
	 * Removes all the commerce shipment items from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceShipmentItem commerceShipmentItem : findAll()) {
			remove(commerceShipmentItem);
		}
	}

	/**
	 * Returns the number of commerce shipment items.
	 *
	 * @return the number of commerce shipment items
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCESHIPMENTITEM);

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
		return CommerceShipmentItemModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce shipment item persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceShipmentItemImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCESHIPMENTITEM = "SELECT commerceShipmentItem FROM CommerceShipmentItem commerceShipmentItem";
	private static final String _SQL_SELECT_COMMERCESHIPMENTITEM_WHERE_PKS_IN = "SELECT commerceShipmentItem FROM CommerceShipmentItem commerceShipmentItem WHERE commerceShipmentItemId IN (";
	private static final String _SQL_SELECT_COMMERCESHIPMENTITEM_WHERE = "SELECT commerceShipmentItem FROM CommerceShipmentItem commerceShipmentItem WHERE ";
	private static final String _SQL_COUNT_COMMERCESHIPMENTITEM = "SELECT COUNT(commerceShipmentItem) FROM CommerceShipmentItem commerceShipmentItem";
	private static final String _SQL_COUNT_COMMERCESHIPMENTITEM_WHERE = "SELECT COUNT(commerceShipmentItem) FROM CommerceShipmentItem commerceShipmentItem WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceShipmentItem.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceShipmentItem exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceShipmentItem exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceShipmentItemPersistenceImpl.class);
}