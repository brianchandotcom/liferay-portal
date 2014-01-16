/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchPortletItemException;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.PortletItem;
import com.liferay.portal.model.impl.PortletItemImpl;
import com.liferay.portal.model.impl.PortletItemModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the portlet item service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PortletItemPersistence
 * @see PortletItemUtil
 * @generated
 */
public class PortletItemPersistenceImpl extends BasePersistenceImpl<PortletItem>
	implements PortletItemPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link PortletItemUtil} to access the portlet item persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = PortletItemImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
			PortletItemModelImpl.FINDER_CACHE_ENABLED, PortletItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
			PortletItemModelImpl.FINDER_CACHE_ENABLED, PortletItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
			PortletItemModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C = new FinderPath(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
			PortletItemModelImpl.FINDER_CACHE_ENABLED, PortletItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C = new FinderPath(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
			PortletItemModelImpl.FINDER_CACHE_ENABLED, PortletItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			PortletItemModelImpl.GROUPID_COLUMN_BITMASK |
			PortletItemModelImpl.CLASSNAMEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C = new FinderPath(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
			PortletItemModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the portlet items where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the matching portlet items
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<PortletItem> findByG_C(long groupId, long classNameId)
		throws SystemException {
		return findByG_C(groupId, classNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the portlet items where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of portlet items
	 * @param end the upper bound of the range of portlet items (not inclusive)
	 * @return the range of matching portlet items
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<PortletItem> findByG_C(long groupId, long classNameId,
		int start, int end) throws SystemException {
		return findByG_C(groupId, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the portlet items where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of portlet items
	 * @param end the upper bound of the range of portlet items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching portlet items
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<PortletItem> findByG_C(long groupId, long classNameId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C;
			finderArgs = new Object[] { groupId, classNameId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C;
			finderArgs = new Object[] {
					groupId, classNameId,
					
					start, end, orderByComparator
				};
		}

		List<PortletItem> list = (List<PortletItem>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (PortletItem portletItem : list) {
				if ((groupId != portletItem.getGroupId()) ||
						(classNameId != portletItem.getClassNameId())) {
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

			query.append(_SQL_SELECT_PORTLETITEM_WHERE);

			query.append(_FINDER_COLUMN_G_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(PortletItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				if (!pagination) {
					list = (List<PortletItem>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<PortletItem>(list);
				}
				else {
					list = (List<PortletItem>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first portlet item in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet item
	 * @throws com.liferay.portal.NoSuchPortletItemException if a matching portlet item could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortletItem findByG_C_First(long groupId, long classNameId,
		OrderByComparator orderByComparator)
		throws NoSuchPortletItemException, SystemException {
		PortletItem portletItem = fetchByG_C_First(groupId, classNameId,
				orderByComparator);

		if (portletItem != null) {
			return portletItem;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPortletItemException(msg.toString());
	}

	/**
	 * Returns the first portlet item in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet item, or <code>null</code> if a matching portlet item could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortletItem fetchByG_C_First(long groupId, long classNameId,
		OrderByComparator orderByComparator) throws SystemException {
		List<PortletItem> list = findByG_C(groupId, classNameId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last portlet item in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet item
	 * @throws com.liferay.portal.NoSuchPortletItemException if a matching portlet item could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortletItem findByG_C_Last(long groupId, long classNameId,
		OrderByComparator orderByComparator)
		throws NoSuchPortletItemException, SystemException {
		PortletItem portletItem = fetchByG_C_Last(groupId, classNameId,
				orderByComparator);

		if (portletItem != null) {
			return portletItem;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPortletItemException(msg.toString());
	}

	/**
	 * Returns the last portlet item in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet item, or <code>null</code> if a matching portlet item could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortletItem fetchByG_C_Last(long groupId, long classNameId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByG_C(groupId, classNameId);

		if (count == 0) {
			return null;
		}

		List<PortletItem> list = findByG_C(groupId, classNameId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the portlet items before and after the current portlet item in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param portletItemId the primary key of the current portlet item
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next portlet item
	 * @throws com.liferay.portal.NoSuchPortletItemException if a portlet item with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortletItem[] findByG_C_PrevAndNext(long portletItemId,
		long groupId, long classNameId, OrderByComparator orderByComparator)
		throws NoSuchPortletItemException, SystemException {
		PortletItem portletItem = findByPrimaryKey(portletItemId);

		Session session = null;

		try {
			session = openSession();

			PortletItem[] array = new PortletItemImpl[3];

			array[0] = getByG_C_PrevAndNext(session, portletItem, groupId,
					classNameId, orderByComparator, true);

			array[1] = portletItem;

			array[2] = getByG_C_PrevAndNext(session, portletItem, groupId,
					classNameId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected PortletItem getByG_C_PrevAndNext(Session session,
		PortletItem portletItem, long groupId, long classNameId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_PORTLETITEM_WHERE);

		query.append(_FINDER_COLUMN_G_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

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
			query.append(PortletItemModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(classNameId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(portletItem);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<PortletItem> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the portlet items where groupId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByG_C(long groupId, long classNameId)
		throws SystemException {
		for (PortletItem portletItem : findByG_C(groupId, classNameId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(portletItem);
		}
	}

	/**
	 * Returns the number of portlet items where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the number of matching portlet items
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByG_C(long groupId, long classNameId)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C;

		Object[] finderArgs = new Object[] { groupId, classNameId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_PORTLETITEM_WHERE);

			query.append(_FINDER_COLUMN_G_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

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

	private static final String _FINDER_COLUMN_G_C_GROUPID_2 = "portletItem.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_CLASSNAMEID_2 = "portletItem.classNameId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_P_C = new FinderPath(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
			PortletItemModelImpl.FINDER_CACHE_ENABLED, PortletItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_P_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_C = new FinderPath(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
			PortletItemModelImpl.FINDER_CACHE_ENABLED, PortletItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_P_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName()
			},
			PortletItemModelImpl.GROUPID_COLUMN_BITMASK |
			PortletItemModelImpl.PORTLETID_COLUMN_BITMASK |
			PortletItemModelImpl.CLASSNAMEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P_C = new FinderPath(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
			PortletItemModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName()
			});

	/**
	 * Returns all the portlet items where groupId = &#63; and portletId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param portletId the portlet ID
	 * @param classNameId the class name ID
	 * @return the matching portlet items
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<PortletItem> findByG_P_C(long groupId, String portletId,
		long classNameId) throws SystemException {
		return findByG_P_C(groupId, portletId, classNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the portlet items where groupId = &#63; and portletId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param portletId the portlet ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of portlet items
	 * @param end the upper bound of the range of portlet items (not inclusive)
	 * @return the range of matching portlet items
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<PortletItem> findByG_P_C(long groupId, String portletId,
		long classNameId, int start, int end) throws SystemException {
		return findByG_P_C(groupId, portletId, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the portlet items where groupId = &#63; and portletId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param portletId the portlet ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of portlet items
	 * @param end the upper bound of the range of portlet items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching portlet items
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<PortletItem> findByG_P_C(long groupId, String portletId,
		long classNameId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_C;
			finderArgs = new Object[] { groupId, portletId, classNameId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_P_C;
			finderArgs = new Object[] {
					groupId, portletId, classNameId,
					
					start, end, orderByComparator
				};
		}

		List<PortletItem> list = (List<PortletItem>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (PortletItem portletItem : list) {
				if ((groupId != portletItem.getGroupId()) ||
						!Validator.equals(portletId, portletItem.getPortletId()) ||
						(classNameId != portletItem.getClassNameId())) {
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

			query.append(_SQL_SELECT_PORTLETITEM_WHERE);

			query.append(_FINDER_COLUMN_G_P_C_GROUPID_2);

			boolean bindPortletId = false;

			if (portletId == null) {
				query.append(_FINDER_COLUMN_G_P_C_PORTLETID_1);
			}
			else if (portletId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_P_C_PORTLETID_3);
			}
			else {
				bindPortletId = true;

				query.append(_FINDER_COLUMN_G_P_C_PORTLETID_2);
			}

			query.append(_FINDER_COLUMN_G_P_C_CLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(PortletItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindPortletId) {
					qPos.add(portletId);
				}

				qPos.add(classNameId);

				if (!pagination) {
					list = (List<PortletItem>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<PortletItem>(list);
				}
				else {
					list = (List<PortletItem>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first portlet item in the ordered set where groupId = &#63; and portletId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param portletId the portlet ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet item
	 * @throws com.liferay.portal.NoSuchPortletItemException if a matching portlet item could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortletItem findByG_P_C_First(long groupId, String portletId,
		long classNameId, OrderByComparator orderByComparator)
		throws NoSuchPortletItemException, SystemException {
		PortletItem portletItem = fetchByG_P_C_First(groupId, portletId,
				classNameId, orderByComparator);

		if (portletItem != null) {
			return portletItem;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", portletId=");
		msg.append(portletId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPortletItemException(msg.toString());
	}

	/**
	 * Returns the first portlet item in the ordered set where groupId = &#63; and portletId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param portletId the portlet ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet item, or <code>null</code> if a matching portlet item could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortletItem fetchByG_P_C_First(long groupId, String portletId,
		long classNameId, OrderByComparator orderByComparator)
		throws SystemException {
		List<PortletItem> list = findByG_P_C(groupId, portletId, classNameId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last portlet item in the ordered set where groupId = &#63; and portletId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param portletId the portlet ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet item
	 * @throws com.liferay.portal.NoSuchPortletItemException if a matching portlet item could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortletItem findByG_P_C_Last(long groupId, String portletId,
		long classNameId, OrderByComparator orderByComparator)
		throws NoSuchPortletItemException, SystemException {
		PortletItem portletItem = fetchByG_P_C_Last(groupId, portletId,
				classNameId, orderByComparator);

		if (portletItem != null) {
			return portletItem;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", portletId=");
		msg.append(portletId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPortletItemException(msg.toString());
	}

	/**
	 * Returns the last portlet item in the ordered set where groupId = &#63; and portletId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param portletId the portlet ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet item, or <code>null</code> if a matching portlet item could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortletItem fetchByG_P_C_Last(long groupId, String portletId,
		long classNameId, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByG_P_C(groupId, portletId, classNameId);

		if (count == 0) {
			return null;
		}

		List<PortletItem> list = findByG_P_C(groupId, portletId, classNameId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the portlet items before and after the current portlet item in the ordered set where groupId = &#63; and portletId = &#63; and classNameId = &#63;.
	 *
	 * @param portletItemId the primary key of the current portlet item
	 * @param groupId the group ID
	 * @param portletId the portlet ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next portlet item
	 * @throws com.liferay.portal.NoSuchPortletItemException if a portlet item with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortletItem[] findByG_P_C_PrevAndNext(long portletItemId,
		long groupId, String portletId, long classNameId,
		OrderByComparator orderByComparator)
		throws NoSuchPortletItemException, SystemException {
		PortletItem portletItem = findByPrimaryKey(portletItemId);

		Session session = null;

		try {
			session = openSession();

			PortletItem[] array = new PortletItemImpl[3];

			array[0] = getByG_P_C_PrevAndNext(session, portletItem, groupId,
					portletId, classNameId, orderByComparator, true);

			array[1] = portletItem;

			array[2] = getByG_P_C_PrevAndNext(session, portletItem, groupId,
					portletId, classNameId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected PortletItem getByG_P_C_PrevAndNext(Session session,
		PortletItem portletItem, long groupId, String portletId,
		long classNameId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_PORTLETITEM_WHERE);

		query.append(_FINDER_COLUMN_G_P_C_GROUPID_2);

		boolean bindPortletId = false;

		if (portletId == null) {
			query.append(_FINDER_COLUMN_G_P_C_PORTLETID_1);
		}
		else if (portletId.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_P_C_PORTLETID_3);
		}
		else {
			bindPortletId = true;

			query.append(_FINDER_COLUMN_G_P_C_PORTLETID_2);
		}

		query.append(_FINDER_COLUMN_G_P_C_CLASSNAMEID_2);

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
			query.append(PortletItemModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindPortletId) {
			qPos.add(portletId);
		}

		qPos.add(classNameId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(portletItem);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<PortletItem> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the portlet items where groupId = &#63; and portletId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param portletId the portlet ID
	 * @param classNameId the class name ID
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByG_P_C(long groupId, String portletId, long classNameId)
		throws SystemException {
		for (PortletItem portletItem : findByG_P_C(groupId, portletId,
				classNameId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(portletItem);
		}
	}

	/**
	 * Returns the number of portlet items where groupId = &#63; and portletId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param portletId the portlet ID
	 * @param classNameId the class name ID
	 * @return the number of matching portlet items
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByG_P_C(long groupId, String portletId, long classNameId)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_P_C;

		Object[] finderArgs = new Object[] { groupId, portletId, classNameId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_PORTLETITEM_WHERE);

			query.append(_FINDER_COLUMN_G_P_C_GROUPID_2);

			boolean bindPortletId = false;

			if (portletId == null) {
				query.append(_FINDER_COLUMN_G_P_C_PORTLETID_1);
			}
			else if (portletId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_P_C_PORTLETID_3);
			}
			else {
				bindPortletId = true;

				query.append(_FINDER_COLUMN_G_P_C_PORTLETID_2);
			}

			query.append(_FINDER_COLUMN_G_P_C_CLASSNAMEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindPortletId) {
					qPos.add(portletId);
				}

				qPos.add(classNameId);

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

	private static final String _FINDER_COLUMN_G_P_C_GROUPID_2 = "portletItem.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_C_PORTLETID_1 = "portletItem.portletId IS NULL AND ";
	private static final String _FINDER_COLUMN_G_P_C_PORTLETID_2 = "portletItem.portletId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_C_PORTLETID_3 = "(portletItem.portletId IS NULL OR portletItem.portletId = '') AND ";
	private static final String _FINDER_COLUMN_G_P_C_CLASSNAMEID_2 = "portletItem.classNameId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_N_P_C = new FinderPath(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
			PortletItemModelImpl.FINDER_CACHE_ENABLED, PortletItemImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_N_P_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Long.class.getName()
			},
			PortletItemModelImpl.GROUPID_COLUMN_BITMASK |
			PortletItemModelImpl.NAME_COLUMN_BITMASK |
			PortletItemModelImpl.PORTLETID_COLUMN_BITMASK |
			PortletItemModelImpl.CLASSNAMEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_N_P_C = new FinderPath(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
			PortletItemModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_N_P_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Long.class.getName()
			});

	/**
	 * Returns the portlet item where groupId = &#63; and name = &#63; and portletId = &#63; and classNameId = &#63; or throws a {@link com.liferay.portal.NoSuchPortletItemException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param portletId the portlet ID
	 * @param classNameId the class name ID
	 * @return the matching portlet item
	 * @throws com.liferay.portal.NoSuchPortletItemException if a matching portlet item could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortletItem findByG_N_P_C(long groupId, String name,
		String portletId, long classNameId)
		throws NoSuchPortletItemException, SystemException {
		PortletItem portletItem = fetchByG_N_P_C(groupId, name, portletId,
				classNameId);

		if (portletItem == null) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", name=");
			msg.append(name);

			msg.append(", portletId=");
			msg.append(portletId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchPortletItemException(msg.toString());
		}

		return portletItem;
	}

	/**
	 * Returns the portlet item where groupId = &#63; and name = &#63; and portletId = &#63; and classNameId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param portletId the portlet ID
	 * @param classNameId the class name ID
	 * @return the matching portlet item, or <code>null</code> if a matching portlet item could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortletItem fetchByG_N_P_C(long groupId, String name,
		String portletId, long classNameId) throws SystemException {
		return fetchByG_N_P_C(groupId, name, portletId, classNameId, true);
	}

	/**
	 * Returns the portlet item where groupId = &#63; and name = &#63; and portletId = &#63; and classNameId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param portletId the portlet ID
	 * @param classNameId the class name ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching portlet item, or <code>null</code> if a matching portlet item could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortletItem fetchByG_N_P_C(long groupId, String name,
		String portletId, long classNameId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, name, portletId, classNameId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_N_P_C,
					finderArgs, this);
		}

		if (result instanceof PortletItem) {
			PortletItem portletItem = (PortletItem)result;

			if ((groupId != portletItem.getGroupId()) ||
					!Validator.equals(name, portletItem.getName()) ||
					!Validator.equals(portletId, portletItem.getPortletId()) ||
					(classNameId != portletItem.getClassNameId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_SELECT_PORTLETITEM_WHERE);

			query.append(_FINDER_COLUMN_G_N_P_C_GROUPID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_N_P_C_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_N_P_C_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_N_P_C_NAME_2);
			}

			boolean bindPortletId = false;

			if (portletId == null) {
				query.append(_FINDER_COLUMN_G_N_P_C_PORTLETID_1);
			}
			else if (portletId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_N_P_C_PORTLETID_3);
			}
			else {
				bindPortletId = true;

				query.append(_FINDER_COLUMN_G_N_P_C_PORTLETID_2);
			}

			query.append(_FINDER_COLUMN_G_N_P_C_CLASSNAMEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindName) {
					qPos.add(name.toLowerCase());
				}

				if (bindPortletId) {
					qPos.add(portletId);
				}

				qPos.add(classNameId);

				List<PortletItem> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N_P_C,
						finderArgs, list);
				}
				else {
					if ((list.size() > 1) && _log.isWarnEnabled()) {
						_log.warn(
							"PortletItemPersistenceImpl.fetchByG_N_P_C(long, String, String, long, boolean) with parameters (" +
							StringUtil.merge(finderArgs) +
							") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
					}

					PortletItem portletItem = list.get(0);

					result = portletItem;

					cacheResult(portletItem);

					if ((portletItem.getGroupId() != groupId) ||
							(portletItem.getName() == null) ||
							!portletItem.getName().equals(name) ||
							(portletItem.getPortletId() == null) ||
							!portletItem.getPortletId().equals(portletId) ||
							(portletItem.getClassNameId() != classNameId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N_P_C,
							finderArgs, portletItem);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_N_P_C,
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
			return (PortletItem)result;
		}
	}

	/**
	 * Removes the portlet item where groupId = &#63; and name = &#63; and portletId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param portletId the portlet ID
	 * @param classNameId the class name ID
	 * @return the portlet item that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortletItem removeByG_N_P_C(long groupId, String name,
		String portletId, long classNameId)
		throws NoSuchPortletItemException, SystemException {
		PortletItem portletItem = findByG_N_P_C(groupId, name, portletId,
				classNameId);

		return remove(portletItem);
	}

	/**
	 * Returns the number of portlet items where groupId = &#63; and name = &#63; and portletId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param portletId the portlet ID
	 * @param classNameId the class name ID
	 * @return the number of matching portlet items
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByG_N_P_C(long groupId, String name, String portletId,
		long classNameId) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_N_P_C;

		Object[] finderArgs = new Object[] { groupId, name, portletId, classNameId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_PORTLETITEM_WHERE);

			query.append(_FINDER_COLUMN_G_N_P_C_GROUPID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_N_P_C_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_N_P_C_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_N_P_C_NAME_2);
			}

			boolean bindPortletId = false;

			if (portletId == null) {
				query.append(_FINDER_COLUMN_G_N_P_C_PORTLETID_1);
			}
			else if (portletId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_N_P_C_PORTLETID_3);
			}
			else {
				bindPortletId = true;

				query.append(_FINDER_COLUMN_G_N_P_C_PORTLETID_2);
			}

			query.append(_FINDER_COLUMN_G_N_P_C_CLASSNAMEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindName) {
					qPos.add(name.toLowerCase());
				}

				if (bindPortletId) {
					qPos.add(portletId);
				}

				qPos.add(classNameId);

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

	private static final String _FINDER_COLUMN_G_N_P_C_GROUPID_2 = "portletItem.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_N_P_C_NAME_1 = "portletItem.name IS NULL AND ";
	private static final String _FINDER_COLUMN_G_N_P_C_NAME_2 = "lower(portletItem.name) = ? AND ";
	private static final String _FINDER_COLUMN_G_N_P_C_NAME_3 = "(portletItem.name IS NULL OR portletItem.name = '') AND ";
	private static final String _FINDER_COLUMN_G_N_P_C_PORTLETID_1 = "portletItem.portletId IS NULL AND ";
	private static final String _FINDER_COLUMN_G_N_P_C_PORTLETID_2 = "portletItem.portletId = ? AND ";
	private static final String _FINDER_COLUMN_G_N_P_C_PORTLETID_3 = "(portletItem.portletId IS NULL OR portletItem.portletId = '') AND ";
	private static final String _FINDER_COLUMN_G_N_P_C_CLASSNAMEID_2 = "portletItem.classNameId = ?";

	public PortletItemPersistenceImpl() {
		setModelClass(PortletItem.class);
	}

	/**
	 * Caches the portlet item in the entity cache if it is enabled.
	 *
	 * @param portletItem the portlet item
	 */
	@Override
	public void cacheResult(PortletItem portletItem) {
		EntityCacheUtil.putResult(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
			PortletItemImpl.class, portletItem.getPrimaryKey(), portletItem);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N_P_C,
			new Object[] {
				portletItem.getGroupId(), portletItem.getName(),
				portletItem.getPortletId(), portletItem.getClassNameId()
			}, portletItem);

		portletItem.resetOriginalValues();
	}

	/**
	 * Caches the portlet items in the entity cache if it is enabled.
	 *
	 * @param portletItems the portlet items
	 */
	@Override
	public void cacheResult(List<PortletItem> portletItems) {
		for (PortletItem portletItem : portletItems) {
			if (EntityCacheUtil.getResult(
						PortletItemModelImpl.ENTITY_CACHE_ENABLED,
						PortletItemImpl.class, portletItem.getPrimaryKey()) == null) {
				cacheResult(portletItem);
			}
			else {
				portletItem.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all portlet items.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(PortletItemImpl.class.getName());
		}

		EntityCacheUtil.clearCache(PortletItemImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the portlet item.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(PortletItem portletItem) {
		EntityCacheUtil.removeResult(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
			PortletItemImpl.class, portletItem.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(portletItem);
	}

	@Override
	public void clearCache(List<PortletItem> portletItems) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (PortletItem portletItem : portletItems) {
			EntityCacheUtil.removeResult(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
				PortletItemImpl.class, portletItem.getPrimaryKey());

			clearUniqueFindersCache(portletItem);
		}
	}

	protected void cacheUniqueFindersCache(PortletItem portletItem) {
		if (portletItem.isNew()) {
			Object[] args = new Object[] {
					portletItem.getGroupId(), portletItem.getName(),
					portletItem.getPortletId(), portletItem.getClassNameId()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_N_P_C, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N_P_C, args,
				portletItem);
		}
		else {
			PortletItemModelImpl portletItemModelImpl = (PortletItemModelImpl)portletItem;

			if ((portletItemModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_G_N_P_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						portletItem.getGroupId(), portletItem.getName(),
						portletItem.getPortletId(), portletItem.getClassNameId()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_N_P_C, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N_P_C, args,
					portletItem);
			}
		}
	}

	protected void clearUniqueFindersCache(PortletItem portletItem) {
		PortletItemModelImpl portletItemModelImpl = (PortletItemModelImpl)portletItem;

		Object[] args = new Object[] {
				portletItem.getGroupId(), portletItem.getName(),
				portletItem.getPortletId(), portletItem.getClassNameId()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_N_P_C, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_N_P_C, args);

		if ((portletItemModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_N_P_C.getColumnBitmask()) != 0) {
			args = new Object[] {
					portletItemModelImpl.getOriginalGroupId(),
					portletItemModelImpl.getOriginalName(),
					portletItemModelImpl.getOriginalPortletId(),
					portletItemModelImpl.getOriginalClassNameId()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_N_P_C, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_N_P_C, args);
		}
	}

	/**
	 * Creates a new portlet item with the primary key. Does not add the portlet item to the database.
	 *
	 * @param portletItemId the primary key for the new portlet item
	 * @return the new portlet item
	 */
	@Override
	public PortletItem create(long portletItemId) {
		PortletItem portletItem = new PortletItemImpl();

		portletItem.setNew(true);
		portletItem.setPrimaryKey(portletItemId);

		return portletItem;
	}

	/**
	 * Removes the portlet item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param portletItemId the primary key of the portlet item
	 * @return the portlet item that was removed
	 * @throws com.liferay.portal.NoSuchPortletItemException if a portlet item with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortletItem remove(long portletItemId)
		throws NoSuchPortletItemException, SystemException {
		return remove((Serializable)portletItemId);
	}

	/**
	 * Removes the portlet item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the portlet item
	 * @return the portlet item that was removed
	 * @throws com.liferay.portal.NoSuchPortletItemException if a portlet item with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortletItem remove(Serializable primaryKey)
		throws NoSuchPortletItemException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PortletItem portletItem = (PortletItem)session.get(PortletItemImpl.class,
					primaryKey);

			if (portletItem == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPortletItemException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(portletItem);
		}
		catch (NoSuchPortletItemException nsee) {
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
	protected PortletItem removeImpl(PortletItem portletItem)
		throws SystemException {
		portletItem = toUnwrappedModel(portletItem);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(portletItem)) {
				portletItem = (PortletItem)session.get(PortletItemImpl.class,
						portletItem.getPrimaryKeyObj());
			}

			if (portletItem != null) {
				session.delete(portletItem);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (portletItem != null) {
			clearCache(portletItem);
		}

		return portletItem;
	}

	@Override
	public PortletItem updateImpl(
		com.liferay.portal.model.PortletItem portletItem)
		throws SystemException {
		portletItem = toUnwrappedModel(portletItem);

		boolean isNew = portletItem.isNew();

		PortletItemModelImpl portletItemModelImpl = (PortletItemModelImpl)portletItem;

		Session session = null;

		try {
			session = openSession();

			if (portletItem.isNew()) {
				session.save(portletItem);

				portletItem.setNew(false);
			}
			else {
				session.merge(portletItem);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !PortletItemModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((portletItemModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						portletItemModelImpl.getOriginalGroupId(),
						portletItemModelImpl.getOriginalClassNameId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C,
					args);

				args = new Object[] {
						portletItemModelImpl.getGroupId(),
						portletItemModelImpl.getClassNameId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C,
					args);
			}

			if ((portletItemModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						portletItemModelImpl.getOriginalGroupId(),
						portletItemModelImpl.getOriginalPortletId(),
						portletItemModelImpl.getOriginalClassNameId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_C,
					args);

				args = new Object[] {
						portletItemModelImpl.getGroupId(),
						portletItemModelImpl.getPortletId(),
						portletItemModelImpl.getClassNameId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_C,
					args);
			}
		}

		EntityCacheUtil.putResult(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
			PortletItemImpl.class, portletItem.getPrimaryKey(), portletItem);

		clearUniqueFindersCache(portletItem);
		cacheUniqueFindersCache(portletItem);

		portletItem.resetOriginalValues();

		return portletItem;
	}

	protected PortletItem toUnwrappedModel(PortletItem portletItem) {
		if (portletItem instanceof PortletItemImpl) {
			return portletItem;
		}

		PortletItemImpl portletItemImpl = new PortletItemImpl();

		portletItemImpl.setNew(portletItem.isNew());
		portletItemImpl.setPrimaryKey(portletItem.getPrimaryKey());

		portletItemImpl.setPortletItemId(portletItem.getPortletItemId());
		portletItemImpl.setGroupId(portletItem.getGroupId());
		portletItemImpl.setCompanyId(portletItem.getCompanyId());
		portletItemImpl.setUserId(portletItem.getUserId());
		portletItemImpl.setUserName(portletItem.getUserName());
		portletItemImpl.setCreateDate(portletItem.getCreateDate());
		portletItemImpl.setModifiedDate(portletItem.getModifiedDate());
		portletItemImpl.setName(portletItem.getName());
		portletItemImpl.setPortletId(portletItem.getPortletId());
		portletItemImpl.setClassNameId(portletItem.getClassNameId());
		portletItemImpl.setMvccVersion(portletItem.getMvccVersion());

		return portletItemImpl;
	}

	/**
	 * Returns the portlet item with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the portlet item
	 * @return the portlet item
	 * @throws com.liferay.portal.NoSuchPortletItemException if a portlet item with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortletItem findByPrimaryKey(Serializable primaryKey)
		throws NoSuchPortletItemException, SystemException {
		PortletItem portletItem = fetchByPrimaryKey(primaryKey);

		if (portletItem == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPortletItemException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return portletItem;
	}

	/**
	 * Returns the portlet item with the primary key or throws a {@link com.liferay.portal.NoSuchPortletItemException} if it could not be found.
	 *
	 * @param portletItemId the primary key of the portlet item
	 * @return the portlet item
	 * @throws com.liferay.portal.NoSuchPortletItemException if a portlet item with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortletItem findByPrimaryKey(long portletItemId)
		throws NoSuchPortletItemException, SystemException {
		return findByPrimaryKey((Serializable)portletItemId);
	}

	/**
	 * Returns the portlet item with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the portlet item
	 * @return the portlet item, or <code>null</code> if a portlet item with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortletItem fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		PortletItem portletItem = (PortletItem)EntityCacheUtil.getResult(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
				PortletItemImpl.class, primaryKey);

		if (portletItem == _nullPortletItem) {
			return null;
		}

		if (portletItem == null) {
			Session session = null;

			try {
				session = openSession();

				portletItem = (PortletItem)session.get(PortletItemImpl.class,
						primaryKey);

				if (portletItem != null) {
					cacheResult(portletItem);
				}
				else {
					EntityCacheUtil.putResult(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
						PortletItemImpl.class, primaryKey, _nullPortletItem);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(PortletItemModelImpl.ENTITY_CACHE_ENABLED,
					PortletItemImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return portletItem;
	}

	/**
	 * Returns the portlet item with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param portletItemId the primary key of the portlet item
	 * @return the portlet item, or <code>null</code> if a portlet item with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortletItem fetchByPrimaryKey(long portletItemId)
		throws SystemException {
		return fetchByPrimaryKey((Serializable)portletItemId);
	}

	/**
	 * Returns all the portlet items.
	 *
	 * @return the portlet items
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<PortletItem> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the portlet items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of portlet items
	 * @param end the upper bound of the range of portlet items (not inclusive)
	 * @return the range of portlet items
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<PortletItem> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the portlet items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of portlet items
	 * @param end the upper bound of the range of portlet items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of portlet items
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<PortletItem> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
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

		List<PortletItem> list = (List<PortletItem>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_PORTLETITEM);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_PORTLETITEM;

				if (pagination) {
					sql = sql.concat(PortletItemModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<PortletItem>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<PortletItem>(list);
				}
				else {
					list = (List<PortletItem>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Removes all the portlet items from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeAll() throws SystemException {
		for (PortletItem portletItem : findAll()) {
			remove(portletItem);
		}
	}

	/**
	 * Returns the number of portlet items.
	 *
	 * @return the number of portlet items
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_PORTLETITEM);

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
	 * Initializes the portlet item persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.PortletItem")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<PortletItem>> listenersList = new ArrayList<ModelListener<PortletItem>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<PortletItem>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(PortletItemImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_PORTLETITEM = "SELECT portletItem FROM PortletItem portletItem";
	private static final String _SQL_SELECT_PORTLETITEM_WHERE = "SELECT portletItem FROM PortletItem portletItem WHERE ";
	private static final String _SQL_COUNT_PORTLETITEM = "SELECT COUNT(portletItem) FROM PortletItem portletItem";
	private static final String _SQL_COUNT_PORTLETITEM_WHERE = "SELECT COUNT(portletItem) FROM PortletItem portletItem WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "portletItem.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No PortletItem exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No PortletItem exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(PortletItemPersistenceImpl.class);
	private static PortletItem _nullPortletItem = new PortletItemImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<PortletItem> toCacheModel() {
				return _nullPortletItemCacheModel;
			}
		};

	private static CacheModel<PortletItem> _nullPortletItemCacheModel = new CacheModel<PortletItem>() {
			@Override
			public PortletItem toEntityModel() {
				return _nullPortletItem;
			}
		};
}