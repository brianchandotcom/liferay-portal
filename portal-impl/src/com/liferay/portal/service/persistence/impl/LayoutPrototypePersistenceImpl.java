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

package com.liferay.portal.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.NoSuchLayoutPrototypeException;
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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.MVCCModel;
import com.liferay.portal.model.impl.LayoutPrototypeImpl;
import com.liferay.portal.model.impl.LayoutPrototypeModelImpl;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.persistence.CompanyProvider;
import com.liferay.portal.service.persistence.LayoutPrototypePersistence;

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
 * The persistence implementation for the layout prototype service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPrototypePersistence
 * @see com.liferay.portal.service.persistence.LayoutPrototypeUtil
 * @generated
 */
@ProviderType
public class LayoutPrototypePersistenceImpl extends BasePersistenceImpl<LayoutPrototype>
	implements LayoutPrototypePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link LayoutPrototypeUtil} to access the layout prototype persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = LayoutPrototypeImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(LayoutPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPrototypeModelImpl.FINDER_CACHE_ENABLED,
			LayoutPrototypeImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(LayoutPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPrototypeModelImpl.FINDER_CACHE_ENABLED,
			LayoutPrototypeImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(LayoutPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPrototypeModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(LayoutPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPrototypeModelImpl.FINDER_CACHE_ENABLED,
			LayoutPrototypeImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(LayoutPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPrototypeModelImpl.FINDER_CACHE_ENABLED,
			LayoutPrototypeImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			LayoutPrototypeModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(LayoutPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPrototypeModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the layout prototypes where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching layout prototypes
	 */
	@Override
	public List<LayoutPrototype> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout prototypes where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout prototypes
	 * @param end the upper bound of the range of layout prototypes (not inclusive)
	 * @return the range of matching layout prototypes
	 */
	@Override
	public List<LayoutPrototype> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout prototypes where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout prototypes
	 * @param end the upper bound of the range of layout prototypes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout prototypes
	 */
	@Override
	public List<LayoutPrototype> findByUuid(String uuid, int start, int end,
		OrderByComparator<LayoutPrototype> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout prototypes where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout prototypes
	 * @param end the upper bound of the range of layout prototypes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout prototypes
	 */
	@Override
	public List<LayoutPrototype> findByUuid(String uuid, int start, int end,
		OrderByComparator<LayoutPrototype> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid, start, end, orderByComparator };
		}

		List<LayoutPrototype> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutPrototype>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPrototype layoutPrototype : list) {
					if (!Validator.equals(uuid, layoutPrototype.getUuid())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_LAYOUTPROTOTYPE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutPrototypeModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				if (!pagination) {
					list = (List<LayoutPrototype>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutPrototype>)QueryUtil.list(q,
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
	 * Returns the first layout prototype in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout prototype
	 * @throws NoSuchLayoutPrototypeException if a matching layout prototype could not be found
	 */
	@Override
	public LayoutPrototype findByUuid_First(String uuid,
		OrderByComparator<LayoutPrototype> orderByComparator)
		throws NoSuchLayoutPrototypeException {
		LayoutPrototype layoutPrototype = fetchByUuid_First(uuid,
				orderByComparator);

		if (layoutPrototype != null) {
			return layoutPrototype;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutPrototypeException(msg.toString());
	}

	/**
	 * Returns the first layout prototype in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout prototype, or <code>null</code> if a matching layout prototype could not be found
	 */
	@Override
	public LayoutPrototype fetchByUuid_First(String uuid,
		OrderByComparator<LayoutPrototype> orderByComparator) {
		List<LayoutPrototype> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout prototype in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout prototype
	 * @throws NoSuchLayoutPrototypeException if a matching layout prototype could not be found
	 */
	@Override
	public LayoutPrototype findByUuid_Last(String uuid,
		OrderByComparator<LayoutPrototype> orderByComparator)
		throws NoSuchLayoutPrototypeException {
		LayoutPrototype layoutPrototype = fetchByUuid_Last(uuid,
				orderByComparator);

		if (layoutPrototype != null) {
			return layoutPrototype;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutPrototypeException(msg.toString());
	}

	/**
	 * Returns the last layout prototype in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout prototype, or <code>null</code> if a matching layout prototype could not be found
	 */
	@Override
	public LayoutPrototype fetchByUuid_Last(String uuid,
		OrderByComparator<LayoutPrototype> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<LayoutPrototype> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout prototypes before and after the current layout prototype in the ordered set where uuid = &#63;.
	 *
	 * @param layoutPrototypeId the primary key of the current layout prototype
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout prototype
	 * @throws NoSuchLayoutPrototypeException if a layout prototype with the primary key could not be found
	 */
	@Override
	public LayoutPrototype[] findByUuid_PrevAndNext(long layoutPrototypeId,
		String uuid, OrderByComparator<LayoutPrototype> orderByComparator)
		throws NoSuchLayoutPrototypeException {
		LayoutPrototype layoutPrototype = findByPrimaryKey(layoutPrototypeId);

		Session session = null;

		try {
			session = openSession();

			LayoutPrototype[] array = new LayoutPrototypeImpl[3];

			array[0] = getByUuid_PrevAndNext(session, layoutPrototype, uuid,
					orderByComparator, true);

			array[1] = layoutPrototype;

			array[2] = getByUuid_PrevAndNext(session, layoutPrototype, uuid,
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

	protected LayoutPrototype getByUuid_PrevAndNext(Session session,
		LayoutPrototype layoutPrototype, String uuid,
		OrderByComparator<LayoutPrototype> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTPROTOTYPE_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2);
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
			query.append(LayoutPrototypeModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutPrototype);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutPrototype> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout prototypes that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching layout prototypes that the user has permission to view
	 */
	@Override
	public List<LayoutPrototype> filterFindByUuid(String uuid) {
		return filterFindByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout prototypes that the user has permission to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout prototypes
	 * @param end the upper bound of the range of layout prototypes (not inclusive)
	 * @return the range of matching layout prototypes that the user has permission to view
	 */
	@Override
	public List<LayoutPrototype> filterFindByUuid(String uuid, int start,
		int end) {
		return filterFindByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout prototypes that the user has permissions to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout prototypes
	 * @param end the upper bound of the range of layout prototypes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout prototypes that the user has permission to view
	 */
	@Override
	public List<LayoutPrototype> filterFindByUuid(String uuid, int start,
		int end, OrderByComparator<LayoutPrototype> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByUuid(uuid, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(3 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPROTOTYPE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTPROTOTYPE_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1_SQL);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2_SQL);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPROTOTYPE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(LayoutPrototypeModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutPrototypeModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPrototype.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, LayoutPrototypeImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, LayoutPrototypeImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			if (bindUuid) {
				qPos.add(uuid);
			}

			return (List<LayoutPrototype>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the layout prototypes before and after the current layout prototype in the ordered set of layout prototypes that the user has permission to view where uuid = &#63;.
	 *
	 * @param layoutPrototypeId the primary key of the current layout prototype
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout prototype
	 * @throws NoSuchLayoutPrototypeException if a layout prototype with the primary key could not be found
	 */
	@Override
	public LayoutPrototype[] filterFindByUuid_PrevAndNext(
		long layoutPrototypeId, String uuid,
		OrderByComparator<LayoutPrototype> orderByComparator)
		throws NoSuchLayoutPrototypeException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByUuid_PrevAndNext(layoutPrototypeId, uuid,
				orderByComparator);
		}

		LayoutPrototype layoutPrototype = findByPrimaryKey(layoutPrototypeId);

		Session session = null;

		try {
			session = openSession();

			LayoutPrototype[] array = new LayoutPrototypeImpl[3];

			array[0] = filterGetByUuid_PrevAndNext(session, layoutPrototype,
					uuid, orderByComparator, true);

			array[1] = layoutPrototype;

			array[2] = filterGetByUuid_PrevAndNext(session, layoutPrototype,
					uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPrototype filterGetByUuid_PrevAndNext(Session session,
		LayoutPrototype layoutPrototype, String uuid,
		OrderByComparator<LayoutPrototype> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPROTOTYPE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTPROTOTYPE_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1_SQL);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2_SQL);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPROTOTYPE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(LayoutPrototypeModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutPrototypeModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPrototype.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, LayoutPrototypeImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, LayoutPrototypeImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutPrototype);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutPrototype> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout prototypes where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (LayoutPrototype layoutPrototype : findByUuid(uuid,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(layoutPrototype);
		}
	}

	/**
	 * Returns the number of layout prototypes where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching layout prototypes
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTPROTOTYPE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
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

	/**
	 * Returns the number of layout prototypes that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching layout prototypes that the user has permission to view
	 */
	@Override
	public int filterCountByUuid(String uuid) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByUuid(uuid);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_LAYOUTPROTOTYPE_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1_SQL);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2_SQL);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPrototype.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			if (bindUuid) {
				qPos.add(uuid);
			}

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "layoutPrototype.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "layoutPrototype.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(layoutPrototype.uuid IS NULL OR layoutPrototype.uuid = '')";
	private static final String _FINDER_COLUMN_UUID_UUID_1_SQL = "layoutPrototype.uuid_ IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2_SQL = "layoutPrototype.uuid_ = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3_SQL = "(layoutPrototype.uuid_ IS NULL OR layoutPrototype.uuid_ = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(LayoutPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPrototypeModelImpl.FINDER_CACHE_ENABLED,
			LayoutPrototypeImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(LayoutPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPrototypeModelImpl.FINDER_CACHE_ENABLED,
			LayoutPrototypeImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			LayoutPrototypeModelImpl.UUID_COLUMN_BITMASK |
			LayoutPrototypeModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(LayoutPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPrototypeModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the layout prototypes where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching layout prototypes
	 */
	@Override
	public List<LayoutPrototype> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout prototypes where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout prototypes
	 * @param end the upper bound of the range of layout prototypes (not inclusive)
	 * @return the range of matching layout prototypes
	 */
	@Override
	public List<LayoutPrototype> findByUuid_C(String uuid, long companyId,
		int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout prototypes where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout prototypes
	 * @param end the upper bound of the range of layout prototypes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout prototypes
	 */
	@Override
	public List<LayoutPrototype> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator<LayoutPrototype> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout prototypes where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout prototypes
	 * @param end the upper bound of the range of layout prototypes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout prototypes
	 */
	@Override
	public List<LayoutPrototype> findByUuid_C(String uuid, long companyId,
		int start, int end,
		OrderByComparator<LayoutPrototype> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] { uuid, companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] {
					uuid, companyId,
					
					start, end, orderByComparator
				};
		}

		List<LayoutPrototype> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutPrototype>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPrototype layoutPrototype : list) {
					if (!Validator.equals(uuid, layoutPrototype.getUuid()) ||
							(companyId != layoutPrototype.getCompanyId())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_LAYOUTPROTOTYPE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutPrototypeModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				if (!pagination) {
					list = (List<LayoutPrototype>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutPrototype>)QueryUtil.list(q,
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
	 * Returns the first layout prototype in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout prototype
	 * @throws NoSuchLayoutPrototypeException if a matching layout prototype could not be found
	 */
	@Override
	public LayoutPrototype findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<LayoutPrototype> orderByComparator)
		throws NoSuchLayoutPrototypeException {
		LayoutPrototype layoutPrototype = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (layoutPrototype != null) {
			return layoutPrototype;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutPrototypeException(msg.toString());
	}

	/**
	 * Returns the first layout prototype in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout prototype, or <code>null</code> if a matching layout prototype could not be found
	 */
	@Override
	public LayoutPrototype fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<LayoutPrototype> orderByComparator) {
		List<LayoutPrototype> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout prototype in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout prototype
	 * @throws NoSuchLayoutPrototypeException if a matching layout prototype could not be found
	 */
	@Override
	public LayoutPrototype findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<LayoutPrototype> orderByComparator)
		throws NoSuchLayoutPrototypeException {
		LayoutPrototype layoutPrototype = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (layoutPrototype != null) {
			return layoutPrototype;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutPrototypeException(msg.toString());
	}

	/**
	 * Returns the last layout prototype in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout prototype, or <code>null</code> if a matching layout prototype could not be found
	 */
	@Override
	public LayoutPrototype fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<LayoutPrototype> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<LayoutPrototype> list = findByUuid_C(uuid, companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout prototypes before and after the current layout prototype in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param layoutPrototypeId the primary key of the current layout prototype
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout prototype
	 * @throws NoSuchLayoutPrototypeException if a layout prototype with the primary key could not be found
	 */
	@Override
	public LayoutPrototype[] findByUuid_C_PrevAndNext(long layoutPrototypeId,
		String uuid, long companyId,
		OrderByComparator<LayoutPrototype> orderByComparator)
		throws NoSuchLayoutPrototypeException {
		LayoutPrototype layoutPrototype = findByPrimaryKey(layoutPrototypeId);

		Session session = null;

		try {
			session = openSession();

			LayoutPrototype[] array = new LayoutPrototypeImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, layoutPrototype, uuid,
					companyId, orderByComparator, true);

			array[1] = layoutPrototype;

			array[2] = getByUuid_C_PrevAndNext(session, layoutPrototype, uuid,
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

	protected LayoutPrototype getByUuid_C_PrevAndNext(Session session,
		LayoutPrototype layoutPrototype, String uuid, long companyId,
		OrderByComparator<LayoutPrototype> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTPROTOTYPE_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

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
			query.append(LayoutPrototypeModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutPrototype);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutPrototype> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout prototypes that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching layout prototypes that the user has permission to view
	 */
	@Override
	public List<LayoutPrototype> filterFindByUuid_C(String uuid, long companyId) {
		return filterFindByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout prototypes that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout prototypes
	 * @param end the upper bound of the range of layout prototypes (not inclusive)
	 * @return the range of matching layout prototypes that the user has permission to view
	 */
	@Override
	public List<LayoutPrototype> filterFindByUuid_C(String uuid,
		long companyId, int start, int end) {
		return filterFindByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout prototypes that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout prototypes
	 * @param end the upper bound of the range of layout prototypes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout prototypes that the user has permission to view
	 */
	@Override
	public List<LayoutPrototype> filterFindByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<LayoutPrototype> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByUuid_C(uuid, companyId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPROTOTYPE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTPROTOTYPE_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1_SQL);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2_SQL);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPROTOTYPE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(LayoutPrototypeModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutPrototypeModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPrototype.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, LayoutPrototypeImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, LayoutPrototypeImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			if (bindUuid) {
				qPos.add(uuid);
			}

			qPos.add(companyId);

			return (List<LayoutPrototype>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the layout prototypes before and after the current layout prototype in the ordered set of layout prototypes that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param layoutPrototypeId the primary key of the current layout prototype
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout prototype
	 * @throws NoSuchLayoutPrototypeException if a layout prototype with the primary key could not be found
	 */
	@Override
	public LayoutPrototype[] filterFindByUuid_C_PrevAndNext(
		long layoutPrototypeId, String uuid, long companyId,
		OrderByComparator<LayoutPrototype> orderByComparator)
		throws NoSuchLayoutPrototypeException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByUuid_C_PrevAndNext(layoutPrototypeId, uuid, companyId,
				orderByComparator);
		}

		LayoutPrototype layoutPrototype = findByPrimaryKey(layoutPrototypeId);

		Session session = null;

		try {
			session = openSession();

			LayoutPrototype[] array = new LayoutPrototypeImpl[3];

			array[0] = filterGetByUuid_C_PrevAndNext(session, layoutPrototype,
					uuid, companyId, orderByComparator, true);

			array[1] = layoutPrototype;

			array[2] = filterGetByUuid_C_PrevAndNext(session, layoutPrototype,
					uuid, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPrototype filterGetByUuid_C_PrevAndNext(Session session,
		LayoutPrototype layoutPrototype, String uuid, long companyId,
		OrderByComparator<LayoutPrototype> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPROTOTYPE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTPROTOTYPE_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1_SQL);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2_SQL);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPROTOTYPE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(LayoutPrototypeModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutPrototypeModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPrototype.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, LayoutPrototypeImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, LayoutPrototypeImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutPrototype);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutPrototype> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout prototypes where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (LayoutPrototype layoutPrototype : findByUuid_C(uuid, companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(layoutPrototype);
		}
	}

	/**
	 * Returns the number of layout prototypes where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching layout prototypes
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTPROTOTYPE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

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
	 * Returns the number of layout prototypes that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching layout prototypes that the user has permission to view
	 */
	@Override
	public int filterCountByUuid_C(String uuid, long companyId) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByUuid_C(uuid, companyId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_LAYOUTPROTOTYPE_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1_SQL);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2_SQL);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPrototype.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			if (bindUuid) {
				qPos.add(uuid);
			}

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "layoutPrototype.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "layoutPrototype.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(layoutPrototype.uuid IS NULL OR layoutPrototype.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_1_SQL = "layoutPrototype.uuid_ IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2_SQL = "layoutPrototype.uuid_ = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3_SQL = "(layoutPrototype.uuid_ IS NULL OR layoutPrototype.uuid_ = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "layoutPrototype.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(LayoutPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPrototypeModelImpl.FINDER_CACHE_ENABLED,
			LayoutPrototypeImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(LayoutPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPrototypeModelImpl.FINDER_CACHE_ENABLED,
			LayoutPrototypeImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			LayoutPrototypeModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(LayoutPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPrototypeModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the layout prototypes where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching layout prototypes
	 */
	@Override
	public List<LayoutPrototype> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the layout prototypes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout prototypes
	 * @param end the upper bound of the range of layout prototypes (not inclusive)
	 * @return the range of matching layout prototypes
	 */
	@Override
	public List<LayoutPrototype> findByCompanyId(long companyId, int start,
		int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout prototypes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout prototypes
	 * @param end the upper bound of the range of layout prototypes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout prototypes
	 */
	@Override
	public List<LayoutPrototype> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<LayoutPrototype> orderByComparator) {
		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout prototypes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout prototypes
	 * @param end the upper bound of the range of layout prototypes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout prototypes
	 */
	@Override
	public List<LayoutPrototype> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<LayoutPrototype> orderByComparator,
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

		List<LayoutPrototype> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutPrototype>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPrototype layoutPrototype : list) {
					if ((companyId != layoutPrototype.getCompanyId())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_LAYOUTPROTOTYPE_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutPrototypeModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<LayoutPrototype>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutPrototype>)QueryUtil.list(q,
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
	 * Returns the first layout prototype in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout prototype
	 * @throws NoSuchLayoutPrototypeException if a matching layout prototype could not be found
	 */
	@Override
	public LayoutPrototype findByCompanyId_First(long companyId,
		OrderByComparator<LayoutPrototype> orderByComparator)
		throws NoSuchLayoutPrototypeException {
		LayoutPrototype layoutPrototype = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (layoutPrototype != null) {
			return layoutPrototype;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutPrototypeException(msg.toString());
	}

	/**
	 * Returns the first layout prototype in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout prototype, or <code>null</code> if a matching layout prototype could not be found
	 */
	@Override
	public LayoutPrototype fetchByCompanyId_First(long companyId,
		OrderByComparator<LayoutPrototype> orderByComparator) {
		List<LayoutPrototype> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout prototype in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout prototype
	 * @throws NoSuchLayoutPrototypeException if a matching layout prototype could not be found
	 */
	@Override
	public LayoutPrototype findByCompanyId_Last(long companyId,
		OrderByComparator<LayoutPrototype> orderByComparator)
		throws NoSuchLayoutPrototypeException {
		LayoutPrototype layoutPrototype = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (layoutPrototype != null) {
			return layoutPrototype;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutPrototypeException(msg.toString());
	}

	/**
	 * Returns the last layout prototype in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout prototype, or <code>null</code> if a matching layout prototype could not be found
	 */
	@Override
	public LayoutPrototype fetchByCompanyId_Last(long companyId,
		OrderByComparator<LayoutPrototype> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<LayoutPrototype> list = findByCompanyId(companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout prototypes before and after the current layout prototype in the ordered set where companyId = &#63;.
	 *
	 * @param layoutPrototypeId the primary key of the current layout prototype
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout prototype
	 * @throws NoSuchLayoutPrototypeException if a layout prototype with the primary key could not be found
	 */
	@Override
	public LayoutPrototype[] findByCompanyId_PrevAndNext(
		long layoutPrototypeId, long companyId,
		OrderByComparator<LayoutPrototype> orderByComparator)
		throws NoSuchLayoutPrototypeException {
		LayoutPrototype layoutPrototype = findByPrimaryKey(layoutPrototypeId);

		Session session = null;

		try {
			session = openSession();

			LayoutPrototype[] array = new LayoutPrototypeImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, layoutPrototype,
					companyId, orderByComparator, true);

			array[1] = layoutPrototype;

			array[2] = getByCompanyId_PrevAndNext(session, layoutPrototype,
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

	protected LayoutPrototype getByCompanyId_PrevAndNext(Session session,
		LayoutPrototype layoutPrototype, long companyId,
		OrderByComparator<LayoutPrototype> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTPROTOTYPE_WHERE);

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
			query.append(LayoutPrototypeModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutPrototype);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutPrototype> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout prototypes that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching layout prototypes that the user has permission to view
	 */
	@Override
	public List<LayoutPrototype> filterFindByCompanyId(long companyId) {
		return filterFindByCompanyId(companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout prototypes that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout prototypes
	 * @param end the upper bound of the range of layout prototypes (not inclusive)
	 * @return the range of matching layout prototypes that the user has permission to view
	 */
	@Override
	public List<LayoutPrototype> filterFindByCompanyId(long companyId,
		int start, int end) {
		return filterFindByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout prototypes that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout prototypes
	 * @param end the upper bound of the range of layout prototypes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout prototypes that the user has permission to view
	 */
	@Override
	public List<LayoutPrototype> filterFindByCompanyId(long companyId,
		int start, int end, OrderByComparator<LayoutPrototype> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByCompanyId(companyId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(3 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPROTOTYPE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTPROTOTYPE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPROTOTYPE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(LayoutPrototypeModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutPrototypeModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPrototype.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, LayoutPrototypeImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, LayoutPrototypeImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			return (List<LayoutPrototype>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the layout prototypes before and after the current layout prototype in the ordered set of layout prototypes that the user has permission to view where companyId = &#63;.
	 *
	 * @param layoutPrototypeId the primary key of the current layout prototype
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout prototype
	 * @throws NoSuchLayoutPrototypeException if a layout prototype with the primary key could not be found
	 */
	@Override
	public LayoutPrototype[] filterFindByCompanyId_PrevAndNext(
		long layoutPrototypeId, long companyId,
		OrderByComparator<LayoutPrototype> orderByComparator)
		throws NoSuchLayoutPrototypeException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByCompanyId_PrevAndNext(layoutPrototypeId, companyId,
				orderByComparator);
		}

		LayoutPrototype layoutPrototype = findByPrimaryKey(layoutPrototypeId);

		Session session = null;

		try {
			session = openSession();

			LayoutPrototype[] array = new LayoutPrototypeImpl[3];

			array[0] = filterGetByCompanyId_PrevAndNext(session,
					layoutPrototype, companyId, orderByComparator, true);

			array[1] = layoutPrototype;

			array[2] = filterGetByCompanyId_PrevAndNext(session,
					layoutPrototype, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPrototype filterGetByCompanyId_PrevAndNext(
		Session session, LayoutPrototype layoutPrototype, long companyId,
		OrderByComparator<LayoutPrototype> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPROTOTYPE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTPROTOTYPE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPROTOTYPE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(LayoutPrototypeModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutPrototypeModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPrototype.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, LayoutPrototypeImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, LayoutPrototypeImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutPrototype);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutPrototype> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout prototypes where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (LayoutPrototype layoutPrototype : findByCompanyId(companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(layoutPrototype);
		}
	}

	/**
	 * Returns the number of layout prototypes where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching layout prototypes
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTPROTOTYPE_WHERE);

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
	 * Returns the number of layout prototypes that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching layout prototypes that the user has permission to view
	 */
	@Override
	public int filterCountByCompanyId(long companyId) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByCompanyId(companyId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_LAYOUTPROTOTYPE_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPrototype.class.getName(),
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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "layoutPrototype.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_A = new FinderPath(LayoutPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPrototypeModelImpl.FINDER_CACHE_ENABLED,
			LayoutPrototypeImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_A = new FinderPath(LayoutPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPrototypeModelImpl.FINDER_CACHE_ENABLED,
			LayoutPrototypeImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_A",
			new String[] { Long.class.getName(), Boolean.class.getName() },
			LayoutPrototypeModelImpl.COMPANYID_COLUMN_BITMASK |
			LayoutPrototypeModelImpl.ACTIVE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_A = new FinderPath(LayoutPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPrototypeModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_A",
			new String[] { Long.class.getName(), Boolean.class.getName() });

	/**
	 * Returns all the layout prototypes where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the matching layout prototypes
	 */
	@Override
	public List<LayoutPrototype> findByC_A(long companyId, boolean active) {
		return findByC_A(companyId, active, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout prototypes where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of layout prototypes
	 * @param end the upper bound of the range of layout prototypes (not inclusive)
	 * @return the range of matching layout prototypes
	 */
	@Override
	public List<LayoutPrototype> findByC_A(long companyId, boolean active,
		int start, int end) {
		return findByC_A(companyId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout prototypes where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of layout prototypes
	 * @param end the upper bound of the range of layout prototypes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout prototypes
	 */
	@Override
	public List<LayoutPrototype> findByC_A(long companyId, boolean active,
		int start, int end, OrderByComparator<LayoutPrototype> orderByComparator) {
		return findByC_A(companyId, active, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout prototypes where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of layout prototypes
	 * @param end the upper bound of the range of layout prototypes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout prototypes
	 */
	@Override
	public List<LayoutPrototype> findByC_A(long companyId, boolean active,
		int start, int end,
		OrderByComparator<LayoutPrototype> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_A;
			finderArgs = new Object[] { companyId, active };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_A;
			finderArgs = new Object[] {
					companyId, active,
					
					start, end, orderByComparator
				};
		}

		List<LayoutPrototype> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutPrototype>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPrototype layoutPrototype : list) {
					if ((companyId != layoutPrototype.getCompanyId()) ||
							(active != layoutPrototype.getActive())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_LAYOUTPROTOTYPE_WHERE);

			query.append(_FINDER_COLUMN_C_A_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_A_ACTIVE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutPrototypeModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(active);

				if (!pagination) {
					list = (List<LayoutPrototype>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutPrototype>)QueryUtil.list(q,
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
	 * Returns the first layout prototype in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout prototype
	 * @throws NoSuchLayoutPrototypeException if a matching layout prototype could not be found
	 */
	@Override
	public LayoutPrototype findByC_A_First(long companyId, boolean active,
		OrderByComparator<LayoutPrototype> orderByComparator)
		throws NoSuchLayoutPrototypeException {
		LayoutPrototype layoutPrototype = fetchByC_A_First(companyId, active,
				orderByComparator);

		if (layoutPrototype != null) {
			return layoutPrototype;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", active=");
		msg.append(active);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutPrototypeException(msg.toString());
	}

	/**
	 * Returns the first layout prototype in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout prototype, or <code>null</code> if a matching layout prototype could not be found
	 */
	@Override
	public LayoutPrototype fetchByC_A_First(long companyId, boolean active,
		OrderByComparator<LayoutPrototype> orderByComparator) {
		List<LayoutPrototype> list = findByC_A(companyId, active, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout prototype in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout prototype
	 * @throws NoSuchLayoutPrototypeException if a matching layout prototype could not be found
	 */
	@Override
	public LayoutPrototype findByC_A_Last(long companyId, boolean active,
		OrderByComparator<LayoutPrototype> orderByComparator)
		throws NoSuchLayoutPrototypeException {
		LayoutPrototype layoutPrototype = fetchByC_A_Last(companyId, active,
				orderByComparator);

		if (layoutPrototype != null) {
			return layoutPrototype;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", active=");
		msg.append(active);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutPrototypeException(msg.toString());
	}

	/**
	 * Returns the last layout prototype in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout prototype, or <code>null</code> if a matching layout prototype could not be found
	 */
	@Override
	public LayoutPrototype fetchByC_A_Last(long companyId, boolean active,
		OrderByComparator<LayoutPrototype> orderByComparator) {
		int count = countByC_A(companyId, active);

		if (count == 0) {
			return null;
		}

		List<LayoutPrototype> list = findByC_A(companyId, active, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout prototypes before and after the current layout prototype in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param layoutPrototypeId the primary key of the current layout prototype
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout prototype
	 * @throws NoSuchLayoutPrototypeException if a layout prototype with the primary key could not be found
	 */
	@Override
	public LayoutPrototype[] findByC_A_PrevAndNext(long layoutPrototypeId,
		long companyId, boolean active,
		OrderByComparator<LayoutPrototype> orderByComparator)
		throws NoSuchLayoutPrototypeException {
		LayoutPrototype layoutPrototype = findByPrimaryKey(layoutPrototypeId);

		Session session = null;

		try {
			session = openSession();

			LayoutPrototype[] array = new LayoutPrototypeImpl[3];

			array[0] = getByC_A_PrevAndNext(session, layoutPrototype,
					companyId, active, orderByComparator, true);

			array[1] = layoutPrototype;

			array[2] = getByC_A_PrevAndNext(session, layoutPrototype,
					companyId, active, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPrototype getByC_A_PrevAndNext(Session session,
		LayoutPrototype layoutPrototype, long companyId, boolean active,
		OrderByComparator<LayoutPrototype> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTPROTOTYPE_WHERE);

		query.append(_FINDER_COLUMN_C_A_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_A_ACTIVE_2);

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
			query.append(LayoutPrototypeModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(active);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutPrototype);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutPrototype> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout prototypes that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the matching layout prototypes that the user has permission to view
	 */
	@Override
	public List<LayoutPrototype> filterFindByC_A(long companyId, boolean active) {
		return filterFindByC_A(companyId, active, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout prototypes that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of layout prototypes
	 * @param end the upper bound of the range of layout prototypes (not inclusive)
	 * @return the range of matching layout prototypes that the user has permission to view
	 */
	@Override
	public List<LayoutPrototype> filterFindByC_A(long companyId,
		boolean active, int start, int end) {
		return filterFindByC_A(companyId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout prototypes that the user has permissions to view where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of layout prototypes
	 * @param end the upper bound of the range of layout prototypes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout prototypes that the user has permission to view
	 */
	@Override
	public List<LayoutPrototype> filterFindByC_A(long companyId,
		boolean active, int start, int end,
		OrderByComparator<LayoutPrototype> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByC_A(companyId, active, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPROTOTYPE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTPROTOTYPE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_C_A_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_A_ACTIVE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPROTOTYPE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(LayoutPrototypeModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutPrototypeModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPrototype.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, LayoutPrototypeImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, LayoutPrototypeImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			qPos.add(active);

			return (List<LayoutPrototype>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the layout prototypes before and after the current layout prototype in the ordered set of layout prototypes that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * @param layoutPrototypeId the primary key of the current layout prototype
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout prototype
	 * @throws NoSuchLayoutPrototypeException if a layout prototype with the primary key could not be found
	 */
	@Override
	public LayoutPrototype[] filterFindByC_A_PrevAndNext(
		long layoutPrototypeId, long companyId, boolean active,
		OrderByComparator<LayoutPrototype> orderByComparator)
		throws NoSuchLayoutPrototypeException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByC_A_PrevAndNext(layoutPrototypeId, companyId, active,
				orderByComparator);
		}

		LayoutPrototype layoutPrototype = findByPrimaryKey(layoutPrototypeId);

		Session session = null;

		try {
			session = openSession();

			LayoutPrototype[] array = new LayoutPrototypeImpl[3];

			array[0] = filterGetByC_A_PrevAndNext(session, layoutPrototype,
					companyId, active, orderByComparator, true);

			array[1] = layoutPrototype;

			array[2] = filterGetByC_A_PrevAndNext(session, layoutPrototype,
					companyId, active, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPrototype filterGetByC_A_PrevAndNext(Session session,
		LayoutPrototype layoutPrototype, long companyId, boolean active,
		OrderByComparator<LayoutPrototype> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPROTOTYPE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTPROTOTYPE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_C_A_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_A_ACTIVE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPROTOTYPE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(LayoutPrototypeModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutPrototypeModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPrototype.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, LayoutPrototypeImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, LayoutPrototypeImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(active);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutPrototype);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutPrototype> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout prototypes where companyId = &#63; and active = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 */
	@Override
	public void removeByC_A(long companyId, boolean active) {
		for (LayoutPrototype layoutPrototype : findByC_A(companyId, active,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(layoutPrototype);
		}
	}

	/**
	 * Returns the number of layout prototypes where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the number of matching layout prototypes
	 */
	@Override
	public int countByC_A(long companyId, boolean active) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_A;

		Object[] finderArgs = new Object[] { companyId, active };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTPROTOTYPE_WHERE);

			query.append(_FINDER_COLUMN_C_A_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_A_ACTIVE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(active);

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
	 * Returns the number of layout prototypes that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the number of matching layout prototypes that the user has permission to view
	 */
	@Override
	public int filterCountByC_A(long companyId, boolean active) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByC_A(companyId, active);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_LAYOUTPROTOTYPE_WHERE);

		query.append(_FINDER_COLUMN_C_A_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_A_ACTIVE_2_SQL);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPrototype.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			qPos.add(active);

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

	private static final String _FINDER_COLUMN_C_A_COMPANYID_2 = "layoutPrototype.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_A_ACTIVE_2 = "layoutPrototype.active = ?";
	private static final String _FINDER_COLUMN_C_A_ACTIVE_2_SQL = "layoutPrototype.active_ = ?";

	public LayoutPrototypePersistenceImpl() {
		setModelClass(LayoutPrototype.class);
	}

	/**
	 * Caches the layout prototype in the entity cache if it is enabled.
	 *
	 * @param layoutPrototype the layout prototype
	 */
	@Override
	public void cacheResult(LayoutPrototype layoutPrototype) {
		entityCache.putResult(LayoutPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPrototypeImpl.class, layoutPrototype.getPrimaryKey(),
			layoutPrototype);

		layoutPrototype.resetOriginalValues();
	}

	/**
	 * Caches the layout prototypes in the entity cache if it is enabled.
	 *
	 * @param layoutPrototypes the layout prototypes
	 */
	@Override
	public void cacheResult(List<LayoutPrototype> layoutPrototypes) {
		for (LayoutPrototype layoutPrototype : layoutPrototypes) {
			if (entityCache.getResult(
						LayoutPrototypeModelImpl.ENTITY_CACHE_ENABLED,
						LayoutPrototypeImpl.class,
						layoutPrototype.getPrimaryKey()) == null) {
				cacheResult(layoutPrototype);
			}
			else {
				layoutPrototype.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all layout prototypes.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LayoutPrototypeImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the layout prototype.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LayoutPrototype layoutPrototype) {
		entityCache.removeResult(LayoutPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPrototypeImpl.class, layoutPrototype.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<LayoutPrototype> layoutPrototypes) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LayoutPrototype layoutPrototype : layoutPrototypes) {
			entityCache.removeResult(LayoutPrototypeModelImpl.ENTITY_CACHE_ENABLED,
				LayoutPrototypeImpl.class, layoutPrototype.getPrimaryKey());
		}
	}

	/**
	 * Creates a new layout prototype with the primary key. Does not add the layout prototype to the database.
	 *
	 * @param layoutPrototypeId the primary key for the new layout prototype
	 * @return the new layout prototype
	 */
	@Override
	public LayoutPrototype create(long layoutPrototypeId) {
		LayoutPrototype layoutPrototype = new LayoutPrototypeImpl();

		layoutPrototype.setNew(true);
		layoutPrototype.setPrimaryKey(layoutPrototypeId);

		String uuid = PortalUUIDUtil.generate();

		layoutPrototype.setUuid(uuid);

		return layoutPrototype;
	}

	/**
	 * Removes the layout prototype with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutPrototypeId the primary key of the layout prototype
	 * @return the layout prototype that was removed
	 * @throws NoSuchLayoutPrototypeException if a layout prototype with the primary key could not be found
	 */
	@Override
	public LayoutPrototype remove(long layoutPrototypeId)
		throws NoSuchLayoutPrototypeException {
		return remove((Serializable)layoutPrototypeId);
	}

	/**
	 * Removes the layout prototype with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the layout prototype
	 * @return the layout prototype that was removed
	 * @throws NoSuchLayoutPrototypeException if a layout prototype with the primary key could not be found
	 */
	@Override
	public LayoutPrototype remove(Serializable primaryKey)
		throws NoSuchLayoutPrototypeException {
		Session session = null;

		try {
			session = openSession();

			LayoutPrototype layoutPrototype = (LayoutPrototype)session.get(LayoutPrototypeImpl.class,
					primaryKey);

			if (layoutPrototype == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLayoutPrototypeException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(layoutPrototype);
		}
		catch (NoSuchLayoutPrototypeException nsee) {
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
	protected LayoutPrototype removeImpl(LayoutPrototype layoutPrototype) {
		layoutPrototype = toUnwrappedModel(layoutPrototype);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(layoutPrototype)) {
				layoutPrototype = (LayoutPrototype)session.get(LayoutPrototypeImpl.class,
						layoutPrototype.getPrimaryKeyObj());
			}

			if (layoutPrototype != null) {
				session.delete(layoutPrototype);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (layoutPrototype != null) {
			clearCache(layoutPrototype);
		}

		return layoutPrototype;
	}

	@Override
	public LayoutPrototype updateImpl(LayoutPrototype layoutPrototype) {
		layoutPrototype = toUnwrappedModel(layoutPrototype);

		boolean isNew = layoutPrototype.isNew();

		LayoutPrototypeModelImpl layoutPrototypeModelImpl = (LayoutPrototypeModelImpl)layoutPrototype;

		if (Validator.isNull(layoutPrototype.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			layoutPrototype.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (layoutPrototype.getCreateDate() == null)) {
			if (serviceContext == null) {
				layoutPrototype.setCreateDate(now);
			}
			else {
				layoutPrototype.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!layoutPrototypeModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				layoutPrototype.setModifiedDate(now);
			}
			else {
				layoutPrototype.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (layoutPrototype.isNew()) {
				session.save(layoutPrototype);

				layoutPrototype.setNew(false);
			}
			else {
				layoutPrototype = (LayoutPrototype)session.merge(layoutPrototype);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !LayoutPrototypeModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((layoutPrototypeModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutPrototypeModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { layoutPrototypeModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((layoutPrototypeModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutPrototypeModelImpl.getOriginalUuid(),
						layoutPrototypeModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						layoutPrototypeModelImpl.getUuid(),
						layoutPrototypeModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((layoutPrototypeModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutPrototypeModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] { layoutPrototypeModelImpl.getCompanyId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((layoutPrototypeModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_A.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutPrototypeModelImpl.getOriginalCompanyId(),
						layoutPrototypeModelImpl.getOriginalActive()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_A, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_A,
					args);

				args = new Object[] {
						layoutPrototypeModelImpl.getCompanyId(),
						layoutPrototypeModelImpl.getActive()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_A, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_A,
					args);
			}
		}

		entityCache.putResult(LayoutPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPrototypeImpl.class, layoutPrototype.getPrimaryKey(),
			layoutPrototype, false);

		layoutPrototype.resetOriginalValues();

		return layoutPrototype;
	}

	protected LayoutPrototype toUnwrappedModel(LayoutPrototype layoutPrototype) {
		if (layoutPrototype instanceof LayoutPrototypeImpl) {
			return layoutPrototype;
		}

		LayoutPrototypeImpl layoutPrototypeImpl = new LayoutPrototypeImpl();

		layoutPrototypeImpl.setNew(layoutPrototype.isNew());
		layoutPrototypeImpl.setPrimaryKey(layoutPrototype.getPrimaryKey());

		layoutPrototypeImpl.setMvccVersion(layoutPrototype.getMvccVersion());
		layoutPrototypeImpl.setUuid(layoutPrototype.getUuid());
		layoutPrototypeImpl.setLayoutPrototypeId(layoutPrototype.getLayoutPrototypeId());
		layoutPrototypeImpl.setCompanyId(layoutPrototype.getCompanyId());
		layoutPrototypeImpl.setUserId(layoutPrototype.getUserId());
		layoutPrototypeImpl.setUserName(layoutPrototype.getUserName());
		layoutPrototypeImpl.setCreateDate(layoutPrototype.getCreateDate());
		layoutPrototypeImpl.setModifiedDate(layoutPrototype.getModifiedDate());
		layoutPrototypeImpl.setName(layoutPrototype.getName());
		layoutPrototypeImpl.setDescription(layoutPrototype.getDescription());
		layoutPrototypeImpl.setSettings(layoutPrototype.getSettings());
		layoutPrototypeImpl.setActive(layoutPrototype.isActive());

		return layoutPrototypeImpl;
	}

	/**
	 * Returns the layout prototype with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout prototype
	 * @return the layout prototype
	 * @throws NoSuchLayoutPrototypeException if a layout prototype with the primary key could not be found
	 */
	@Override
	public LayoutPrototype findByPrimaryKey(Serializable primaryKey)
		throws NoSuchLayoutPrototypeException {
		LayoutPrototype layoutPrototype = fetchByPrimaryKey(primaryKey);

		if (layoutPrototype == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchLayoutPrototypeException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return layoutPrototype;
	}

	/**
	 * Returns the layout prototype with the primary key or throws a {@link NoSuchLayoutPrototypeException} if it could not be found.
	 *
	 * @param layoutPrototypeId the primary key of the layout prototype
	 * @return the layout prototype
	 * @throws NoSuchLayoutPrototypeException if a layout prototype with the primary key could not be found
	 */
	@Override
	public LayoutPrototype findByPrimaryKey(long layoutPrototypeId)
		throws NoSuchLayoutPrototypeException {
		return findByPrimaryKey((Serializable)layoutPrototypeId);
	}

	/**
	 * Returns the layout prototype with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout prototype
	 * @return the layout prototype, or <code>null</code> if a layout prototype with the primary key could not be found
	 */
	@Override
	public LayoutPrototype fetchByPrimaryKey(Serializable primaryKey) {
		LayoutPrototype layoutPrototype = (LayoutPrototype)entityCache.getResult(LayoutPrototypeModelImpl.ENTITY_CACHE_ENABLED,
				LayoutPrototypeImpl.class, primaryKey);

		if (layoutPrototype == _nullLayoutPrototype) {
			return null;
		}

		if (layoutPrototype == null) {
			Session session = null;

			try {
				session = openSession();

				layoutPrototype = (LayoutPrototype)session.get(LayoutPrototypeImpl.class,
						primaryKey);

				if (layoutPrototype != null) {
					cacheResult(layoutPrototype);
				}
				else {
					entityCache.putResult(LayoutPrototypeModelImpl.ENTITY_CACHE_ENABLED,
						LayoutPrototypeImpl.class, primaryKey,
						_nullLayoutPrototype);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(LayoutPrototypeModelImpl.ENTITY_CACHE_ENABLED,
					LayoutPrototypeImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return layoutPrototype;
	}

	/**
	 * Returns the layout prototype with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutPrototypeId the primary key of the layout prototype
	 * @return the layout prototype, or <code>null</code> if a layout prototype with the primary key could not be found
	 */
	@Override
	public LayoutPrototype fetchByPrimaryKey(long layoutPrototypeId) {
		return fetchByPrimaryKey((Serializable)layoutPrototypeId);
	}

	@Override
	public Map<Serializable, LayoutPrototype> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, LayoutPrototype> map = new HashMap<Serializable, LayoutPrototype>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			LayoutPrototype layoutPrototype = fetchByPrimaryKey(primaryKey);

			if (layoutPrototype != null) {
				map.put(primaryKey, layoutPrototype);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			LayoutPrototype layoutPrototype = (LayoutPrototype)entityCache.getResult(LayoutPrototypeModelImpl.ENTITY_CACHE_ENABLED,
					LayoutPrototypeImpl.class, primaryKey);

			if (layoutPrototype == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, layoutPrototype);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_LAYOUTPROTOTYPE_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append(String.valueOf(primaryKey));

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (LayoutPrototype layoutPrototype : (List<LayoutPrototype>)q.list()) {
				map.put(layoutPrototype.getPrimaryKeyObj(), layoutPrototype);

				cacheResult(layoutPrototype);

				uncachedPrimaryKeys.remove(layoutPrototype.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(LayoutPrototypeModelImpl.ENTITY_CACHE_ENABLED,
					LayoutPrototypeImpl.class, primaryKey, _nullLayoutPrototype);
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
	 * Returns all the layout prototypes.
	 *
	 * @return the layout prototypes
	 */
	@Override
	public List<LayoutPrototype> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout prototypes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout prototypes
	 * @param end the upper bound of the range of layout prototypes (not inclusive)
	 * @return the range of layout prototypes
	 */
	@Override
	public List<LayoutPrototype> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout prototypes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout prototypes
	 * @param end the upper bound of the range of layout prototypes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout prototypes
	 */
	@Override
	public List<LayoutPrototype> findAll(int start, int end,
		OrderByComparator<LayoutPrototype> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout prototypes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout prototypes
	 * @param end the upper bound of the range of layout prototypes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of layout prototypes
	 */
	@Override
	public List<LayoutPrototype> findAll(int start, int end,
		OrderByComparator<LayoutPrototype> orderByComparator,
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

		List<LayoutPrototype> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutPrototype>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_LAYOUTPROTOTYPE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LAYOUTPROTOTYPE;

				if (pagination) {
					sql = sql.concat(LayoutPrototypeModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<LayoutPrototype>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutPrototype>)QueryUtil.list(q,
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
	 * Removes all the layout prototypes from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LayoutPrototype layoutPrototype : findAll()) {
			remove(layoutPrototype);
		}
	}

	/**
	 * Returns the number of layout prototypes.
	 *
	 * @return the number of layout prototypes
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_LAYOUTPROTOTYPE);

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
		return LayoutPrototypeModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the layout prototype persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(LayoutPrototypeImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = CompanyProvider.class)
	protected CompanyProvider companyProvider;
	protected EntityCache entityCache = EntityCacheUtil.getEntityCache();
	protected FinderCache finderCache = FinderCacheUtil.getFinderCache();
	private static final String _SQL_SELECT_LAYOUTPROTOTYPE = "SELECT layoutPrototype FROM LayoutPrototype layoutPrototype";
	private static final String _SQL_SELECT_LAYOUTPROTOTYPE_WHERE_PKS_IN = "SELECT layoutPrototype FROM LayoutPrototype layoutPrototype WHERE layoutPrototypeId IN (";
	private static final String _SQL_SELECT_LAYOUTPROTOTYPE_WHERE = "SELECT layoutPrototype FROM LayoutPrototype layoutPrototype WHERE ";
	private static final String _SQL_COUNT_LAYOUTPROTOTYPE = "SELECT COUNT(layoutPrototype) FROM LayoutPrototype layoutPrototype";
	private static final String _SQL_COUNT_LAYOUTPROTOTYPE_WHERE = "SELECT COUNT(layoutPrototype) FROM LayoutPrototype layoutPrototype WHERE ";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "layoutPrototype.layoutPrototypeId";
	private static final String _FILTER_SQL_SELECT_LAYOUTPROTOTYPE_WHERE = "SELECT DISTINCT {layoutPrototype.*} FROM LayoutPrototype layoutPrototype WHERE ";
	private static final String _FILTER_SQL_SELECT_LAYOUTPROTOTYPE_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {LayoutPrototype.*} FROM (SELECT DISTINCT layoutPrototype.layoutPrototypeId FROM LayoutPrototype layoutPrototype WHERE ";
	private static final String _FILTER_SQL_SELECT_LAYOUTPROTOTYPE_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN LayoutPrototype ON TEMP_TABLE.layoutPrototypeId = LayoutPrototype.layoutPrototypeId";
	private static final String _FILTER_SQL_COUNT_LAYOUTPROTOTYPE_WHERE = "SELECT COUNT(DISTINCT layoutPrototype.layoutPrototypeId) AS COUNT_VALUE FROM LayoutPrototype layoutPrototype WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "layoutPrototype";
	private static final String _FILTER_ENTITY_TABLE = "LayoutPrototype";
	private static final String _ORDER_BY_ENTITY_ALIAS = "layoutPrototype.";
	private static final String _ORDER_BY_ENTITY_TABLE = "LayoutPrototype.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No LayoutPrototype exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No LayoutPrototype exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(LayoutPrototypePersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid", "settings", "active"
			});
	private static final LayoutPrototype _nullLayoutPrototype = new LayoutPrototypeImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<LayoutPrototype> toCacheModel() {
				return _nullLayoutPrototypeCacheModel;
			}
		};

	private static final CacheModel<LayoutPrototype> _nullLayoutPrototypeCacheModel =
		new NullCacheModel();

	private static class NullCacheModel implements CacheModel<LayoutPrototype>,
		MVCCModel {
		@Override
		public long getMvccVersion() {
			return -1;
		}

		@Override
		public void setMvccVersion(long mvccVersion) {
		}

		@Override
		public LayoutPrototype toEntityModel() {
			return _nullLayoutPrototype;
		}
	}
}