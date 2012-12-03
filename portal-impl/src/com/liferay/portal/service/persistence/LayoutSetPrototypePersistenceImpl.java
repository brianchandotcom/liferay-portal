/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.NoSuchLayoutSetPrototypeException;
import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
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
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.LayoutSetPrototypeImpl;
import com.liferay.portal.model.impl.LayoutSetPrototypeModelImpl;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the layout set prototype service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetPrototypePersistence
 * @see LayoutSetPrototypeUtil
 * @generated
 */
public class LayoutSetPrototypePersistenceImpl extends BasePersistenceImpl<LayoutSetPrototype>
	implements LayoutSetPrototypePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link LayoutSetPrototypeUtil} to access the layout set prototype persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = LayoutSetPrototypeImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetPrototypeModelImpl.FINDER_CACHE_ENABLED,
			LayoutSetPrototypeImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetPrototypeModelImpl.FINDER_CACHE_ENABLED,
			LayoutSetPrototypeImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetPrototypeModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetPrototypeModelImpl.FINDER_CACHE_ENABLED,
			LayoutSetPrototypeImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetPrototypeModelImpl.FINDER_CACHE_ENABLED,
			LayoutSetPrototypeImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			LayoutSetPrototypeModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetPrototypeModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the layout set prototypes where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching layout set prototypes
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutSetPrototype> findByUuid(String uuid)
		throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout set prototypes where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout set prototypes
	 * @param end the upper bound of the range of layout set prototypes (not inclusive)
	 * @return the range of matching layout set prototypes
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutSetPrototype> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout set prototypes where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout set prototypes
	 * @param end the upper bound of the range of layout set prototypes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout set prototypes
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutSetPrototype> findByUuid(String uuid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
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

		List<LayoutSetPrototype> list = (List<LayoutSetPrototype>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (LayoutSetPrototype layoutSetPrototype : list) {
				if (!Validator.equals(uuid, layoutSetPrototype.getUuid())) {
					list = null;

					break;
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

			query.append(_SQL_SELECT_LAYOUTSETPROTOTYPE_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_UUID_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutSetPrototypeModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				if (!pagination) {
					list = (List<LayoutSetPrototype>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<LayoutSetPrototype>(list);
				}
				else {
					list = (List<LayoutSetPrototype>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first layout set prototype in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout set prototype
	 * @throws com.liferay.portal.NoSuchLayoutSetPrototypeException if a matching layout set prototype could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutSetPrototype findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutSetPrototypeException, SystemException {
		LayoutSetPrototype layoutSetPrototype = fetchByUuid_First(uuid,
				orderByComparator);

		if (layoutSetPrototype != null) {
			return layoutSetPrototype;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutSetPrototypeException(msg.toString());
	}

	/**
	 * Returns the first layout set prototype in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout set prototype, or <code>null</code> if a matching layout set prototype could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutSetPrototype fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<LayoutSetPrototype> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout set prototype in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout set prototype
	 * @throws com.liferay.portal.NoSuchLayoutSetPrototypeException if a matching layout set prototype could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutSetPrototype findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutSetPrototypeException, SystemException {
		LayoutSetPrototype layoutSetPrototype = fetchByUuid_Last(uuid,
				orderByComparator);

		if (layoutSetPrototype != null) {
			return layoutSetPrototype;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutSetPrototypeException(msg.toString());
	}

	/**
	 * Returns the last layout set prototype in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout set prototype, or <code>null</code> if a matching layout set prototype could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutSetPrototype fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<LayoutSetPrototype> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout set prototypes before and after the current layout set prototype in the ordered set where uuid = &#63;.
	 *
	 * @param layoutSetPrototypeId the primary key of the current layout set prototype
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout set prototype
	 * @throws com.liferay.portal.NoSuchLayoutSetPrototypeException if a layout set prototype with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutSetPrototype[] findByUuid_PrevAndNext(
		long layoutSetPrototypeId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutSetPrototypeException, SystemException {
		LayoutSetPrototype layoutSetPrototype = findByPrimaryKey(layoutSetPrototypeId);

		Session session = null;

		try {
			session = openSession();

			LayoutSetPrototype[] array = new LayoutSetPrototypeImpl[3];

			array[0] = getByUuid_PrevAndNext(session, layoutSetPrototype, uuid,
					orderByComparator, true);

			array[1] = layoutSetPrototype;

			array[2] = getByUuid_PrevAndNext(session, layoutSetPrototype, uuid,
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

	protected LayoutSetPrototype getByUuid_PrevAndNext(Session session,
		LayoutSetPrototype layoutSetPrototype, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTSETPROTOTYPE_WHERE);

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else {
			if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}
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
			query.append(LayoutSetPrototypeModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (uuid != null) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutSetPrototype);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutSetPrototype> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout set prototypes that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching layout set prototypes that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutSetPrototype> filterFindByUuid(String uuid)
		throws SystemException {
		return filterFindByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout set prototypes that the user has permission to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout set prototypes
	 * @param end the upper bound of the range of layout set prototypes (not inclusive)
	 * @return the range of matching layout set prototypes that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutSetPrototype> filterFindByUuid(String uuid, int start,
		int end) throws SystemException {
		return filterFindByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout set prototypes that the user has permissions to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout set prototypes
	 * @param end the upper bound of the range of layout set prototypes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout set prototypes that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutSetPrototype> filterFindByUuid(String uuid, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
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
			query.append(_FILTER_SQL_SELECT_LAYOUTSETPROTOTYPE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTSETPROTOTYPE_NO_INLINE_DISTINCT_WHERE_1);
		}

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else {
			if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTSETPROTOTYPE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(LayoutSetPrototypeModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutSetPrototypeModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutSetPrototype.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, LayoutSetPrototypeImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, LayoutSetPrototypeImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			if (uuid != null) {
				qPos.add(uuid);
			}

			return (List<LayoutSetPrototype>)QueryUtil.list(q, getDialect(),
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
	 * Returns the layout set prototypes before and after the current layout set prototype in the ordered set of layout set prototypes that the user has permission to view where uuid = &#63;.
	 *
	 * @param layoutSetPrototypeId the primary key of the current layout set prototype
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout set prototype
	 * @throws com.liferay.portal.NoSuchLayoutSetPrototypeException if a layout set prototype with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutSetPrototype[] filterFindByUuid_PrevAndNext(
		long layoutSetPrototypeId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutSetPrototypeException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByUuid_PrevAndNext(layoutSetPrototypeId, uuid,
				orderByComparator);
		}

		LayoutSetPrototype layoutSetPrototype = findByPrimaryKey(layoutSetPrototypeId);

		Session session = null;

		try {
			session = openSession();

			LayoutSetPrototype[] array = new LayoutSetPrototypeImpl[3];

			array[0] = filterGetByUuid_PrevAndNext(session, layoutSetPrototype,
					uuid, orderByComparator, true);

			array[1] = layoutSetPrototype;

			array[2] = filterGetByUuid_PrevAndNext(session, layoutSetPrototype,
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

	protected LayoutSetPrototype filterGetByUuid_PrevAndNext(Session session,
		LayoutSetPrototype layoutSetPrototype, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTSETPROTOTYPE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTSETPROTOTYPE_NO_INLINE_DISTINCT_WHERE_1);
		}

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else {
			if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTSETPROTOTYPE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(LayoutSetPrototypeModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutSetPrototypeModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutSetPrototype.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, LayoutSetPrototypeImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, LayoutSetPrototypeImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		if (uuid != null) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutSetPrototype);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutSetPrototype> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout set prototypes where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (LayoutSetPrototype layoutSetPrototype : findByUuid(uuid,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(layoutSetPrototype);
		}
	}

	/**
	 * Returns the number of layout set prototypes where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching layout set prototypes
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTSETPROTOTYPE_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_UUID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

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

	/**
	 * Returns the number of layout set prototypes that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching layout set prototypes that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByUuid(String uuid) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByUuid(uuid);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_LAYOUTSETPROTOTYPE_WHERE);

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else {
			if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutSetPrototype.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			if (uuid != null) {
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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "layoutSetPrototype.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "layoutSetPrototype.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(layoutSetPrototype.uuid IS NULL OR layoutSetPrototype.uuid = ?)";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetPrototypeModelImpl.FINDER_CACHE_ENABLED,
			LayoutSetPrototypeImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetPrototypeModelImpl.FINDER_CACHE_ENABLED,
			LayoutSetPrototypeImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			LayoutSetPrototypeModelImpl.UUID_COLUMN_BITMASK |
			LayoutSetPrototypeModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetPrototypeModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the layout set prototypes where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching layout set prototypes
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutSetPrototype> findByUuid_C(String uuid, long companyId)
		throws SystemException {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout set prototypes where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout set prototypes
	 * @param end the upper bound of the range of layout set prototypes (not inclusive)
	 * @return the range of matching layout set prototypes
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutSetPrototype> findByUuid_C(String uuid, long companyId,
		int start, int end) throws SystemException {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout set prototypes where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout set prototypes
	 * @param end the upper bound of the range of layout set prototypes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout set prototypes
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutSetPrototype> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
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

		List<LayoutSetPrototype> list = (List<LayoutSetPrototype>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (LayoutSetPrototype layoutSetPrototype : list) {
				if (!Validator.equals(uuid, layoutSetPrototype.getUuid()) ||
						(companyId != layoutSetPrototype.getCompanyId())) {
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

			query.append(_SQL_SELECT_LAYOUTSETPROTOTYPE_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_C_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_C_UUID_2);
				}
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutSetPrototypeModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				if (!pagination) {
					list = (List<LayoutSetPrototype>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<LayoutSetPrototype>(list);
				}
				else {
					list = (List<LayoutSetPrototype>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first layout set prototype in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout set prototype
	 * @throws com.liferay.portal.NoSuchLayoutSetPrototypeException if a matching layout set prototype could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutSetPrototype findByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutSetPrototypeException, SystemException {
		LayoutSetPrototype layoutSetPrototype = fetchByUuid_C_First(uuid,
				companyId, orderByComparator);

		if (layoutSetPrototype != null) {
			return layoutSetPrototype;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutSetPrototypeException(msg.toString());
	}

	/**
	 * Returns the first layout set prototype in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout set prototype, or <code>null</code> if a matching layout set prototype could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutSetPrototype fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<LayoutSetPrototype> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout set prototype in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout set prototype
	 * @throws com.liferay.portal.NoSuchLayoutSetPrototypeException if a matching layout set prototype could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutSetPrototype findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutSetPrototypeException, SystemException {
		LayoutSetPrototype layoutSetPrototype = fetchByUuid_C_Last(uuid,
				companyId, orderByComparator);

		if (layoutSetPrototype != null) {
			return layoutSetPrototype;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutSetPrototypeException(msg.toString());
	}

	/**
	 * Returns the last layout set prototype in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout set prototype, or <code>null</code> if a matching layout set prototype could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutSetPrototype fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid_C(uuid, companyId);

		List<LayoutSetPrototype> list = findByUuid_C(uuid, companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout set prototypes before and after the current layout set prototype in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param layoutSetPrototypeId the primary key of the current layout set prototype
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout set prototype
	 * @throws com.liferay.portal.NoSuchLayoutSetPrototypeException if a layout set prototype with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutSetPrototype[] findByUuid_C_PrevAndNext(
		long layoutSetPrototypeId, String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutSetPrototypeException, SystemException {
		LayoutSetPrototype layoutSetPrototype = findByPrimaryKey(layoutSetPrototypeId);

		Session session = null;

		try {
			session = openSession();

			LayoutSetPrototype[] array = new LayoutSetPrototypeImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, layoutSetPrototype,
					uuid, companyId, orderByComparator, true);

			array[1] = layoutSetPrototype;

			array[2] = getByUuid_C_PrevAndNext(session, layoutSetPrototype,
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

	protected LayoutSetPrototype getByUuid_C_PrevAndNext(Session session,
		LayoutSetPrototype layoutSetPrototype, String uuid, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTSETPROTOTYPE_WHERE);

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1);
		}
		else {
			if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}
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
			query.append(LayoutSetPrototypeModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (uuid != null) {
			qPos.add(uuid);
		}

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutSetPrototype);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutSetPrototype> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout set prototypes that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching layout set prototypes that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutSetPrototype> filterFindByUuid_C(String uuid,
		long companyId) throws SystemException {
		return filterFindByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout set prototypes that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout set prototypes
	 * @param end the upper bound of the range of layout set prototypes (not inclusive)
	 * @return the range of matching layout set prototypes that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutSetPrototype> filterFindByUuid_C(String uuid,
		long companyId, int start, int end) throws SystemException {
		return filterFindByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout set prototypes that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout set prototypes
	 * @param end the upper bound of the range of layout set prototypes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout set prototypes that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutSetPrototype> filterFindByUuid_C(String uuid,
		long companyId, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
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
			query.append(_FILTER_SQL_SELECT_LAYOUTSETPROTOTYPE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTSETPROTOTYPE_NO_INLINE_DISTINCT_WHERE_1);
		}

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1);
		}
		else {
			if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTSETPROTOTYPE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(LayoutSetPrototypeModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutSetPrototypeModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutSetPrototype.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, LayoutSetPrototypeImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, LayoutSetPrototypeImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			if (uuid != null) {
				qPos.add(uuid);
			}

			qPos.add(companyId);

			return (List<LayoutSetPrototype>)QueryUtil.list(q, getDialect(),
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
	 * Returns the layout set prototypes before and after the current layout set prototype in the ordered set of layout set prototypes that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param layoutSetPrototypeId the primary key of the current layout set prototype
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout set prototype
	 * @throws com.liferay.portal.NoSuchLayoutSetPrototypeException if a layout set prototype with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutSetPrototype[] filterFindByUuid_C_PrevAndNext(
		long layoutSetPrototypeId, String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutSetPrototypeException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByUuid_C_PrevAndNext(layoutSetPrototypeId, uuid,
				companyId, orderByComparator);
		}

		LayoutSetPrototype layoutSetPrototype = findByPrimaryKey(layoutSetPrototypeId);

		Session session = null;

		try {
			session = openSession();

			LayoutSetPrototype[] array = new LayoutSetPrototypeImpl[3];

			array[0] = filterGetByUuid_C_PrevAndNext(session,
					layoutSetPrototype, uuid, companyId, orderByComparator, true);

			array[1] = layoutSetPrototype;

			array[2] = filterGetByUuid_C_PrevAndNext(session,
					layoutSetPrototype, uuid, companyId, orderByComparator,
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

	protected LayoutSetPrototype filterGetByUuid_C_PrevAndNext(
		Session session, LayoutSetPrototype layoutSetPrototype, String uuid,
		long companyId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTSETPROTOTYPE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTSETPROTOTYPE_NO_INLINE_DISTINCT_WHERE_1);
		}

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1);
		}
		else {
			if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTSETPROTOTYPE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(LayoutSetPrototypeModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutSetPrototypeModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutSetPrototype.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, LayoutSetPrototypeImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, LayoutSetPrototypeImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		if (uuid != null) {
			qPos.add(uuid);
		}

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutSetPrototype);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutSetPrototype> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout set prototypes where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid_C(String uuid, long companyId)
		throws SystemException {
		for (LayoutSetPrototype layoutSetPrototype : findByUuid_C(uuid,
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(layoutSetPrototype);
		}
	}

	/**
	 * Returns the number of layout set prototypes where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching layout set prototypes
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid_C(String uuid, long companyId)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTSETPROTOTYPE_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_C_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_C_UUID_2);
				}
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

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

	/**
	 * Returns the number of layout set prototypes that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching layout set prototypes that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByUuid_C(String uuid, long companyId)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByUuid_C(uuid, companyId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_LAYOUTSETPROTOTYPE_WHERE);

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1);
		}
		else {
			if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutSetPrototype.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			if (uuid != null) {
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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "layoutSetPrototype.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "layoutSetPrototype.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(layoutSetPrototype.uuid IS NULL OR layoutSetPrototype.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "layoutSetPrototype.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetPrototypeModelImpl.FINDER_CACHE_ENABLED,
			LayoutSetPrototypeImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetPrototypeModelImpl.FINDER_CACHE_ENABLED,
			LayoutSetPrototypeImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			LayoutSetPrototypeModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetPrototypeModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the layout set prototypes where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching layout set prototypes
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutSetPrototype> findByCompanyId(long companyId)
		throws SystemException {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the layout set prototypes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout set prototypes
	 * @param end the upper bound of the range of layout set prototypes (not inclusive)
	 * @return the range of matching layout set prototypes
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutSetPrototype> findByCompanyId(long companyId, int start,
		int end) throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout set prototypes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout set prototypes
	 * @param end the upper bound of the range of layout set prototypes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout set prototypes
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutSetPrototype> findByCompanyId(long companyId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
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

		List<LayoutSetPrototype> list = (List<LayoutSetPrototype>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (LayoutSetPrototype layoutSetPrototype : list) {
				if ((companyId != layoutSetPrototype.getCompanyId())) {
					list = null;

					break;
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

			query.append(_SQL_SELECT_LAYOUTSETPROTOTYPE_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutSetPrototypeModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<LayoutSetPrototype>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<LayoutSetPrototype>(list);
				}
				else {
					list = (List<LayoutSetPrototype>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first layout set prototype in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout set prototype
	 * @throws com.liferay.portal.NoSuchLayoutSetPrototypeException if a matching layout set prototype could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutSetPrototype findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutSetPrototypeException, SystemException {
		LayoutSetPrototype layoutSetPrototype = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (layoutSetPrototype != null) {
			return layoutSetPrototype;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutSetPrototypeException(msg.toString());
	}

	/**
	 * Returns the first layout set prototype in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout set prototype, or <code>null</code> if a matching layout set prototype could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutSetPrototype fetchByCompanyId_First(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<LayoutSetPrototype> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout set prototype in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout set prototype
	 * @throws com.liferay.portal.NoSuchLayoutSetPrototypeException if a matching layout set prototype could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutSetPrototype findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutSetPrototypeException, SystemException {
		LayoutSetPrototype layoutSetPrototype = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (layoutSetPrototype != null) {
			return layoutSetPrototype;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutSetPrototypeException(msg.toString());
	}

	/**
	 * Returns the last layout set prototype in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout set prototype, or <code>null</code> if a matching layout set prototype could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutSetPrototype fetchByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByCompanyId(companyId);

		List<LayoutSetPrototype> list = findByCompanyId(companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout set prototypes before and after the current layout set prototype in the ordered set where companyId = &#63;.
	 *
	 * @param layoutSetPrototypeId the primary key of the current layout set prototype
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout set prototype
	 * @throws com.liferay.portal.NoSuchLayoutSetPrototypeException if a layout set prototype with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutSetPrototype[] findByCompanyId_PrevAndNext(
		long layoutSetPrototypeId, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutSetPrototypeException, SystemException {
		LayoutSetPrototype layoutSetPrototype = findByPrimaryKey(layoutSetPrototypeId);

		Session session = null;

		try {
			session = openSession();

			LayoutSetPrototype[] array = new LayoutSetPrototypeImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, layoutSetPrototype,
					companyId, orderByComparator, true);

			array[1] = layoutSetPrototype;

			array[2] = getByCompanyId_PrevAndNext(session, layoutSetPrototype,
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

	protected LayoutSetPrototype getByCompanyId_PrevAndNext(Session session,
		LayoutSetPrototype layoutSetPrototype, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTSETPROTOTYPE_WHERE);

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
			query.append(LayoutSetPrototypeModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutSetPrototype);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutSetPrototype> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout set prototypes that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching layout set prototypes that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutSetPrototype> filterFindByCompanyId(long companyId)
		throws SystemException {
		return filterFindByCompanyId(companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout set prototypes that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout set prototypes
	 * @param end the upper bound of the range of layout set prototypes (not inclusive)
	 * @return the range of matching layout set prototypes that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutSetPrototype> filterFindByCompanyId(long companyId,
		int start, int end) throws SystemException {
		return filterFindByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout set prototypes that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout set prototypes
	 * @param end the upper bound of the range of layout set prototypes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout set prototypes that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutSetPrototype> filterFindByCompanyId(long companyId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
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
			query.append(_FILTER_SQL_SELECT_LAYOUTSETPROTOTYPE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTSETPROTOTYPE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTSETPROTOTYPE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(LayoutSetPrototypeModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutSetPrototypeModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutSetPrototype.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, LayoutSetPrototypeImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, LayoutSetPrototypeImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			return (List<LayoutSetPrototype>)QueryUtil.list(q, getDialect(),
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
	 * Returns the layout set prototypes before and after the current layout set prototype in the ordered set of layout set prototypes that the user has permission to view where companyId = &#63;.
	 *
	 * @param layoutSetPrototypeId the primary key of the current layout set prototype
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout set prototype
	 * @throws com.liferay.portal.NoSuchLayoutSetPrototypeException if a layout set prototype with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutSetPrototype[] filterFindByCompanyId_PrevAndNext(
		long layoutSetPrototypeId, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutSetPrototypeException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByCompanyId_PrevAndNext(layoutSetPrototypeId, companyId,
				orderByComparator);
		}

		LayoutSetPrototype layoutSetPrototype = findByPrimaryKey(layoutSetPrototypeId);

		Session session = null;

		try {
			session = openSession();

			LayoutSetPrototype[] array = new LayoutSetPrototypeImpl[3];

			array[0] = filterGetByCompanyId_PrevAndNext(session,
					layoutSetPrototype, companyId, orderByComparator, true);

			array[1] = layoutSetPrototype;

			array[2] = filterGetByCompanyId_PrevAndNext(session,
					layoutSetPrototype, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutSetPrototype filterGetByCompanyId_PrevAndNext(
		Session session, LayoutSetPrototype layoutSetPrototype, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTSETPROTOTYPE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTSETPROTOTYPE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTSETPROTOTYPE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(LayoutSetPrototypeModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutSetPrototypeModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutSetPrototype.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, LayoutSetPrototypeImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, LayoutSetPrototypeImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutSetPrototype);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutSetPrototype> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout set prototypes where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCompanyId(long companyId) throws SystemException {
		for (LayoutSetPrototype layoutSetPrototype : findByCompanyId(
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(layoutSetPrototype);
		}
	}

	/**
	 * Returns the number of layout set prototypes where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching layout set prototypes
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCompanyId(long companyId) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTSETPROTOTYPE_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

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

	/**
	 * Returns the number of layout set prototypes that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching layout set prototypes that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByCompanyId(long companyId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByCompanyId(companyId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_LAYOUTSETPROTOTYPE_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutSetPrototype.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "layoutSetPrototype.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_A = new FinderPath(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetPrototypeModelImpl.FINDER_CACHE_ENABLED,
			LayoutSetPrototypeImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_A = new FinderPath(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetPrototypeModelImpl.FINDER_CACHE_ENABLED,
			LayoutSetPrototypeImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_A",
			new String[] { Long.class.getName(), Boolean.class.getName() },
			LayoutSetPrototypeModelImpl.COMPANYID_COLUMN_BITMASK |
			LayoutSetPrototypeModelImpl.ACTIVE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_A = new FinderPath(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetPrototypeModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_A",
			new String[] { Long.class.getName(), Boolean.class.getName() });

	/**
	 * Returns all the layout set prototypes where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the matching layout set prototypes
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutSetPrototype> findByC_A(long companyId, boolean active)
		throws SystemException {
		return findByC_A(companyId, active, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout set prototypes where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of layout set prototypes
	 * @param end the upper bound of the range of layout set prototypes (not inclusive)
	 * @return the range of matching layout set prototypes
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutSetPrototype> findByC_A(long companyId, boolean active,
		int start, int end) throws SystemException {
		return findByC_A(companyId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout set prototypes where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of layout set prototypes
	 * @param end the upper bound of the range of layout set prototypes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout set prototypes
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutSetPrototype> findByC_A(long companyId, boolean active,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
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

		List<LayoutSetPrototype> list = (List<LayoutSetPrototype>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (LayoutSetPrototype layoutSetPrototype : list) {
				if ((companyId != layoutSetPrototype.getCompanyId()) ||
						(active != layoutSetPrototype.getActive())) {
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

			query.append(_SQL_SELECT_LAYOUTSETPROTOTYPE_WHERE);

			query.append(_FINDER_COLUMN_C_A_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_A_ACTIVE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutSetPrototypeModelImpl.ORDER_BY_JPQL);
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
					list = (List<LayoutSetPrototype>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<LayoutSetPrototype>(list);
				}
				else {
					list = (List<LayoutSetPrototype>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first layout set prototype in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout set prototype
	 * @throws com.liferay.portal.NoSuchLayoutSetPrototypeException if a matching layout set prototype could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutSetPrototype findByC_A_First(long companyId, boolean active,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutSetPrototypeException, SystemException {
		LayoutSetPrototype layoutSetPrototype = fetchByC_A_First(companyId,
				active, orderByComparator);

		if (layoutSetPrototype != null) {
			return layoutSetPrototype;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", active=");
		msg.append(active);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutSetPrototypeException(msg.toString());
	}

	/**
	 * Returns the first layout set prototype in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout set prototype, or <code>null</code> if a matching layout set prototype could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutSetPrototype fetchByC_A_First(long companyId, boolean active,
		OrderByComparator orderByComparator) throws SystemException {
		List<LayoutSetPrototype> list = findByC_A(companyId, active, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout set prototype in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout set prototype
	 * @throws com.liferay.portal.NoSuchLayoutSetPrototypeException if a matching layout set prototype could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutSetPrototype findByC_A_Last(long companyId, boolean active,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutSetPrototypeException, SystemException {
		LayoutSetPrototype layoutSetPrototype = fetchByC_A_Last(companyId,
				active, orderByComparator);

		if (layoutSetPrototype != null) {
			return layoutSetPrototype;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", active=");
		msg.append(active);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLayoutSetPrototypeException(msg.toString());
	}

	/**
	 * Returns the last layout set prototype in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout set prototype, or <code>null</code> if a matching layout set prototype could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutSetPrototype fetchByC_A_Last(long companyId, boolean active,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByC_A(companyId, active);

		List<LayoutSetPrototype> list = findByC_A(companyId, active, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout set prototypes before and after the current layout set prototype in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param layoutSetPrototypeId the primary key of the current layout set prototype
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout set prototype
	 * @throws com.liferay.portal.NoSuchLayoutSetPrototypeException if a layout set prototype with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutSetPrototype[] findByC_A_PrevAndNext(
		long layoutSetPrototypeId, long companyId, boolean active,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutSetPrototypeException, SystemException {
		LayoutSetPrototype layoutSetPrototype = findByPrimaryKey(layoutSetPrototypeId);

		Session session = null;

		try {
			session = openSession();

			LayoutSetPrototype[] array = new LayoutSetPrototypeImpl[3];

			array[0] = getByC_A_PrevAndNext(session, layoutSetPrototype,
					companyId, active, orderByComparator, true);

			array[1] = layoutSetPrototype;

			array[2] = getByC_A_PrevAndNext(session, layoutSetPrototype,
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

	protected LayoutSetPrototype getByC_A_PrevAndNext(Session session,
		LayoutSetPrototype layoutSetPrototype, long companyId, boolean active,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTSETPROTOTYPE_WHERE);

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
			query.append(LayoutSetPrototypeModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(active);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutSetPrototype);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutSetPrototype> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout set prototypes that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the matching layout set prototypes that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutSetPrototype> filterFindByC_A(long companyId,
		boolean active) throws SystemException {
		return filterFindByC_A(companyId, active, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout set prototypes that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of layout set prototypes
	 * @param end the upper bound of the range of layout set prototypes (not inclusive)
	 * @return the range of matching layout set prototypes that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutSetPrototype> filterFindByC_A(long companyId,
		boolean active, int start, int end) throws SystemException {
		return filterFindByC_A(companyId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout set prototypes that the user has permissions to view where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of layout set prototypes
	 * @param end the upper bound of the range of layout set prototypes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout set prototypes that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutSetPrototype> filterFindByC_A(long companyId,
		boolean active, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
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
			query.append(_FILTER_SQL_SELECT_LAYOUTSETPROTOTYPE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTSETPROTOTYPE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_C_A_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_A_ACTIVE_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTSETPROTOTYPE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(LayoutSetPrototypeModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutSetPrototypeModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutSetPrototype.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, LayoutSetPrototypeImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, LayoutSetPrototypeImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			qPos.add(active);

			return (List<LayoutSetPrototype>)QueryUtil.list(q, getDialect(),
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
	 * Returns the layout set prototypes before and after the current layout set prototype in the ordered set of layout set prototypes that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * @param layoutSetPrototypeId the primary key of the current layout set prototype
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout set prototype
	 * @throws com.liferay.portal.NoSuchLayoutSetPrototypeException if a layout set prototype with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutSetPrototype[] filterFindByC_A_PrevAndNext(
		long layoutSetPrototypeId, long companyId, boolean active,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutSetPrototypeException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByC_A_PrevAndNext(layoutSetPrototypeId, companyId,
				active, orderByComparator);
		}

		LayoutSetPrototype layoutSetPrototype = findByPrimaryKey(layoutSetPrototypeId);

		Session session = null;

		try {
			session = openSession();

			LayoutSetPrototype[] array = new LayoutSetPrototypeImpl[3];

			array[0] = filterGetByC_A_PrevAndNext(session, layoutSetPrototype,
					companyId, active, orderByComparator, true);

			array[1] = layoutSetPrototype;

			array[2] = filterGetByC_A_PrevAndNext(session, layoutSetPrototype,
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

	protected LayoutSetPrototype filterGetByC_A_PrevAndNext(Session session,
		LayoutSetPrototype layoutSetPrototype, long companyId, boolean active,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTSETPROTOTYPE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTSETPROTOTYPE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_C_A_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_A_ACTIVE_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTSETPROTOTYPE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(LayoutSetPrototypeModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutSetPrototypeModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutSetPrototype.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, LayoutSetPrototypeImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, LayoutSetPrototypeImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(active);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutSetPrototype);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutSetPrototype> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout set prototypes where companyId = &#63; and active = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_A(long companyId, boolean active)
		throws SystemException {
		for (LayoutSetPrototype layoutSetPrototype : findByC_A(companyId,
				active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(layoutSetPrototype);
		}
	}

	/**
	 * Returns the number of layout set prototypes where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the number of matching layout set prototypes
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_A(long companyId, boolean active)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_A;

		Object[] finderArgs = new Object[] { companyId, active };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTSETPROTOTYPE_WHERE);

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

	/**
	 * Returns the number of layout set prototypes that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the number of matching layout set prototypes that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByC_A(long companyId, boolean active)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByC_A(companyId, active);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_LAYOUTSETPROTOTYPE_WHERE);

		query.append(_FINDER_COLUMN_C_A_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_A_ACTIVE_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutSetPrototype.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

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

	private static final String _FINDER_COLUMN_C_A_COMPANYID_2 = "layoutSetPrototype.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_A_ACTIVE_2 = "layoutSetPrototype.active = ?";

	/**
	 * Caches the layout set prototype in the entity cache if it is enabled.
	 *
	 * @param layoutSetPrototype the layout set prototype
	 */
	public void cacheResult(LayoutSetPrototype layoutSetPrototype) {
		EntityCacheUtil.putResult(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetPrototypeImpl.class, layoutSetPrototype.getPrimaryKey(),
			layoutSetPrototype);

		layoutSetPrototype.resetOriginalValues();
	}

	/**
	 * Caches the layout set prototypes in the entity cache if it is enabled.
	 *
	 * @param layoutSetPrototypes the layout set prototypes
	 */
	public void cacheResult(List<LayoutSetPrototype> layoutSetPrototypes) {
		for (LayoutSetPrototype layoutSetPrototype : layoutSetPrototypes) {
			if (EntityCacheUtil.getResult(
						LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
						LayoutSetPrototypeImpl.class,
						layoutSetPrototype.getPrimaryKey()) == null) {
				cacheResult(layoutSetPrototype);
			}
			else {
				layoutSetPrototype.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all layout set prototypes.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(LayoutSetPrototypeImpl.class.getName());
		}

		EntityCacheUtil.clearCache(LayoutSetPrototypeImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the layout set prototype.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LayoutSetPrototype layoutSetPrototype) {
		EntityCacheUtil.removeResult(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetPrototypeImpl.class, layoutSetPrototype.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<LayoutSetPrototype> layoutSetPrototypes) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LayoutSetPrototype layoutSetPrototype : layoutSetPrototypes) {
			EntityCacheUtil.removeResult(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetPrototypeImpl.class, layoutSetPrototype.getPrimaryKey());
		}
	}

	/**
	 * Creates a new layout set prototype with the primary key. Does not add the layout set prototype to the database.
	 *
	 * @param layoutSetPrototypeId the primary key for the new layout set prototype
	 * @return the new layout set prototype
	 */
	public LayoutSetPrototype create(long layoutSetPrototypeId) {
		LayoutSetPrototype layoutSetPrototype = new LayoutSetPrototypeImpl();

		layoutSetPrototype.setNew(true);
		layoutSetPrototype.setPrimaryKey(layoutSetPrototypeId);

		String uuid = PortalUUIDUtil.generate();

		layoutSetPrototype.setUuid(uuid);

		return layoutSetPrototype;
	}

	/**
	 * Removes the layout set prototype with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutSetPrototypeId the primary key of the layout set prototype
	 * @return the layout set prototype that was removed
	 * @throws com.liferay.portal.NoSuchLayoutSetPrototypeException if a layout set prototype with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutSetPrototype remove(long layoutSetPrototypeId)
		throws NoSuchLayoutSetPrototypeException, SystemException {
		return remove(Long.valueOf(layoutSetPrototypeId));
	}

	/**
	 * Removes the layout set prototype with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the layout set prototype
	 * @return the layout set prototype that was removed
	 * @throws com.liferay.portal.NoSuchLayoutSetPrototypeException if a layout set prototype with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LayoutSetPrototype remove(Serializable primaryKey)
		throws NoSuchLayoutSetPrototypeException, SystemException {
		Session session = null;

		try {
			session = openSession();

			LayoutSetPrototype layoutSetPrototype = (LayoutSetPrototype)session.get(LayoutSetPrototypeImpl.class,
					primaryKey);

			if (layoutSetPrototype == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLayoutSetPrototypeException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(layoutSetPrototype);
		}
		catch (NoSuchLayoutSetPrototypeException nsee) {
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
	protected LayoutSetPrototype removeImpl(
		LayoutSetPrototype layoutSetPrototype) throws SystemException {
		layoutSetPrototype = toUnwrappedModel(layoutSetPrototype);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(layoutSetPrototype)) {
				layoutSetPrototype = (LayoutSetPrototype)session.get(LayoutSetPrototypeImpl.class,
						layoutSetPrototype.getPrimaryKeyObj());
			}

			if (layoutSetPrototype != null) {
				session.delete(layoutSetPrototype);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (layoutSetPrototype != null) {
			clearCache(layoutSetPrototype);
		}

		return layoutSetPrototype;
	}

	@Override
	public LayoutSetPrototype updateImpl(
		com.liferay.portal.model.LayoutSetPrototype layoutSetPrototype)
		throws SystemException {
		layoutSetPrototype = toUnwrappedModel(layoutSetPrototype);

		boolean isNew = layoutSetPrototype.isNew();

		LayoutSetPrototypeModelImpl layoutSetPrototypeModelImpl = (LayoutSetPrototypeModelImpl)layoutSetPrototype;

		if (Validator.isNull(layoutSetPrototype.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			layoutSetPrototype.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (layoutSetPrototype.isNew()) {
				session.save(layoutSetPrototype);

				layoutSetPrototype.setNew(false);
			}
			else {
				session.merge(layoutSetPrototype);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !LayoutSetPrototypeModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((layoutSetPrototypeModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutSetPrototypeModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { layoutSetPrototypeModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((layoutSetPrototypeModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutSetPrototypeModelImpl.getOriginalUuid(),
						Long.valueOf(layoutSetPrototypeModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						layoutSetPrototypeModelImpl.getUuid(),
						Long.valueOf(layoutSetPrototypeModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((layoutSetPrototypeModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(layoutSetPrototypeModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] {
						Long.valueOf(layoutSetPrototypeModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((layoutSetPrototypeModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_A.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(layoutSetPrototypeModelImpl.getOriginalCompanyId()),
						Boolean.valueOf(layoutSetPrototypeModelImpl.getOriginalActive())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_A,
					args);

				args = new Object[] {
						Long.valueOf(layoutSetPrototypeModelImpl.getCompanyId()),
						Boolean.valueOf(layoutSetPrototypeModelImpl.getActive())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_A,
					args);
			}
		}

		EntityCacheUtil.putResult(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetPrototypeImpl.class, layoutSetPrototype.getPrimaryKey(),
			layoutSetPrototype);

		return layoutSetPrototype;
	}

	protected LayoutSetPrototype toUnwrappedModel(
		LayoutSetPrototype layoutSetPrototype) {
		if (layoutSetPrototype instanceof LayoutSetPrototypeImpl) {
			return layoutSetPrototype;
		}

		LayoutSetPrototypeImpl layoutSetPrototypeImpl = new LayoutSetPrototypeImpl();

		layoutSetPrototypeImpl.setNew(layoutSetPrototype.isNew());
		layoutSetPrototypeImpl.setPrimaryKey(layoutSetPrototype.getPrimaryKey());

		layoutSetPrototypeImpl.setUuid(layoutSetPrototype.getUuid());
		layoutSetPrototypeImpl.setLayoutSetPrototypeId(layoutSetPrototype.getLayoutSetPrototypeId());
		layoutSetPrototypeImpl.setCompanyId(layoutSetPrototype.getCompanyId());
		layoutSetPrototypeImpl.setCreateDate(layoutSetPrototype.getCreateDate());
		layoutSetPrototypeImpl.setModifiedDate(layoutSetPrototype.getModifiedDate());
		layoutSetPrototypeImpl.setName(layoutSetPrototype.getName());
		layoutSetPrototypeImpl.setDescription(layoutSetPrototype.getDescription());
		layoutSetPrototypeImpl.setSettings(layoutSetPrototype.getSettings());
		layoutSetPrototypeImpl.setActive(layoutSetPrototype.isActive());

		return layoutSetPrototypeImpl;
	}

	/**
	 * Returns the layout set prototype with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout set prototype
	 * @return the layout set prototype
	 * @throws com.liferay.portal.NoSuchModelException if a layout set prototype with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LayoutSetPrototype findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the layout set prototype with the primary key or throws a {@link com.liferay.portal.NoSuchLayoutSetPrototypeException} if it could not be found.
	 *
	 * @param layoutSetPrototypeId the primary key of the layout set prototype
	 * @return the layout set prototype
	 * @throws com.liferay.portal.NoSuchLayoutSetPrototypeException if a layout set prototype with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutSetPrototype findByPrimaryKey(long layoutSetPrototypeId)
		throws NoSuchLayoutSetPrototypeException, SystemException {
		LayoutSetPrototype layoutSetPrototype = fetchByPrimaryKey(layoutSetPrototypeId);

		if (layoutSetPrototype == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					layoutSetPrototypeId);
			}

			throw new NoSuchLayoutSetPrototypeException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				layoutSetPrototypeId);
		}

		return layoutSetPrototype;
	}

	/**
	 * Returns the layout set prototype with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout set prototype
	 * @return the layout set prototype, or <code>null</code> if a layout set prototype with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LayoutSetPrototype fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the layout set prototype with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutSetPrototypeId the primary key of the layout set prototype
	 * @return the layout set prototype, or <code>null</code> if a layout set prototype with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutSetPrototype fetchByPrimaryKey(long layoutSetPrototypeId)
		throws SystemException {
		LayoutSetPrototype layoutSetPrototype = (LayoutSetPrototype)EntityCacheUtil.getResult(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetPrototypeImpl.class, layoutSetPrototypeId);

		if (layoutSetPrototype == _nullLayoutSetPrototype) {
			return null;
		}

		if (layoutSetPrototype == null) {
			Session session = null;

			try {
				session = openSession();

				layoutSetPrototype = (LayoutSetPrototype)session.get(LayoutSetPrototypeImpl.class,
						Long.valueOf(layoutSetPrototypeId));

				if (layoutSetPrototype != null) {
					cacheResult(layoutSetPrototype);
				}
				else {
					EntityCacheUtil.putResult(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
						LayoutSetPrototypeImpl.class, layoutSetPrototypeId,
						_nullLayoutSetPrototype);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(LayoutSetPrototypeModelImpl.ENTITY_CACHE_ENABLED,
					LayoutSetPrototypeImpl.class, layoutSetPrototypeId);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return layoutSetPrototype;
	}

	/**
	 * Returns all the layout set prototypes.
	 *
	 * @return the layout set prototypes
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutSetPrototype> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout set prototypes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout set prototypes
	 * @param end the upper bound of the range of layout set prototypes (not inclusive)
	 * @return the range of layout set prototypes
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutSetPrototype> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout set prototypes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout set prototypes
	 * @param end the upper bound of the range of layout set prototypes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout set prototypes
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutSetPrototype> findAll(int start, int end,
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

		List<LayoutSetPrototype> list = (List<LayoutSetPrototype>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_LAYOUTSETPROTOTYPE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LAYOUTSETPROTOTYPE;

				if (pagination) {
					sql = sql.concat(LayoutSetPrototypeModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<LayoutSetPrototype>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<LayoutSetPrototype>(list);
				}
				else {
					list = (List<LayoutSetPrototype>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Removes all the layout set prototypes from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (LayoutSetPrototype layoutSetPrototype : findAll()) {
			remove(layoutSetPrototype);
		}
	}

	/**
	 * Returns the number of layout set prototypes.
	 *
	 * @return the number of layout set prototypes
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_LAYOUTSETPROTOTYPE);

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
	 * Initializes the layout set prototype persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.LayoutSetPrototype")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<LayoutSetPrototype>> listenersList = new ArrayList<ModelListener<LayoutSetPrototype>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<LayoutSetPrototype>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(LayoutSetPrototypeImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_LAYOUTSETPROTOTYPE = "SELECT layoutSetPrototype FROM LayoutSetPrototype layoutSetPrototype";
	private static final String _SQL_SELECT_LAYOUTSETPROTOTYPE_WHERE = "SELECT layoutSetPrototype FROM LayoutSetPrototype layoutSetPrototype WHERE ";
	private static final String _SQL_COUNT_LAYOUTSETPROTOTYPE = "SELECT COUNT(layoutSetPrototype) FROM LayoutSetPrototype layoutSetPrototype";
	private static final String _SQL_COUNT_LAYOUTSETPROTOTYPE_WHERE = "SELECT COUNT(layoutSetPrototype) FROM LayoutSetPrototype layoutSetPrototype WHERE ";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "layoutSetPrototype.layoutSetPrototypeId";
	private static final String _FILTER_SQL_SELECT_LAYOUTSETPROTOTYPE_WHERE = "SELECT DISTINCT {layoutSetPrototype.*} FROM LayoutSetPrototype layoutSetPrototype WHERE ";
	private static final String _FILTER_SQL_SELECT_LAYOUTSETPROTOTYPE_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {LayoutSetPrototype.*} FROM (SELECT DISTINCT layoutSetPrototype.layoutSetPrototypeId FROM LayoutSetPrototype layoutSetPrototype WHERE ";
	private static final String _FILTER_SQL_SELECT_LAYOUTSETPROTOTYPE_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN LayoutSetPrototype ON TEMP_TABLE.layoutSetPrototypeId = LayoutSetPrototype.layoutSetPrototypeId";
	private static final String _FILTER_SQL_COUNT_LAYOUTSETPROTOTYPE_WHERE = "SELECT COUNT(DISTINCT layoutSetPrototype.layoutSetPrototypeId) AS COUNT_VALUE FROM LayoutSetPrototype layoutSetPrototype WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "layoutSetPrototype";
	private static final String _FILTER_ENTITY_TABLE = "LayoutSetPrototype";
	private static final String _ORDER_BY_ENTITY_ALIAS = "layoutSetPrototype.";
	private static final String _ORDER_BY_ENTITY_TABLE = "LayoutSetPrototype.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No LayoutSetPrototype exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No LayoutSetPrototype exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(LayoutSetPrototypePersistenceImpl.class);
	private static LayoutSetPrototype _nullLayoutSetPrototype = new LayoutSetPrototypeImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<LayoutSetPrototype> toCacheModel() {
				return _nullLayoutSetPrototypeCacheModel;
			}
		};

	private static CacheModel<LayoutSetPrototype> _nullLayoutSetPrototypeCacheModel =
		new CacheModel<LayoutSetPrototype>() {
			public LayoutSetPrototype toEntityModel() {
				return _nullLayoutSetPrototype;
			}
		};
}