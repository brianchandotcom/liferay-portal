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

import com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionRelException;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.impl.CPDefinitionOptionRelImpl;
import com.liferay.commerce.product.model.impl.CPDefinitionOptionRelModelImpl;
import com.liferay.commerce.product.service.persistence.CPDefinitionOptionRelPersistence;

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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

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
 * The persistence implementation for the cp definition option rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPDefinitionOptionRelPersistence
 * @see com.liferay.commerce.product.service.persistence.CPDefinitionOptionRelUtil
 * @generated
 */
@ProviderType
public class CPDefinitionOptionRelPersistenceImpl extends BasePersistenceImpl<CPDefinitionOptionRel>
	implements CPDefinitionOptionRelPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CPDefinitionOptionRelUtil} to access the cp definition option rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CPDefinitionOptionRelImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CPDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionRelModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionOptionRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CPDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionRelModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionOptionRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CPDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(CPDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionRelModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionOptionRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(CPDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionRelModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionOptionRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			CPDefinitionOptionRelModelImpl.UUID_COLUMN_BITMASK |
			CPDefinitionOptionRelModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(CPDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the cp definition option rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition option rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @return the range of matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByUuid(String uuid, int start,
		int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition option rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByUuid(String uuid, int start,
		int end, OrderByComparator<CPDefinitionOptionRel> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition option rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByUuid(String uuid, int start,
		int end, OrderByComparator<CPDefinitionOptionRel> orderByComparator,
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

		List<CPDefinitionOptionRel> list = null;

		if (retrieveFromCache) {
			list = (List<CPDefinitionOptionRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionOptionRel cpDefinitionOptionRel : list) {
					if (!Objects.equals(uuid, cpDefinitionOptionRel.getUuid())) {
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

			query.append(_SQL_SELECT_CPDEFINITIONOPTIONREL_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals("")) {
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
				query.append(CPDefinitionOptionRelModelImpl.ORDER_BY_JPQL);
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
					list = (List<CPDefinitionOptionRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPDefinitionOptionRel>)QueryUtil.list(q,
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
	 * Returns the first cp definition option rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition option rel
	 * @throws NoSuchCPDefinitionOptionRelException if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel findByUuid_First(String uuid,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws NoSuchCPDefinitionOptionRelException {
		CPDefinitionOptionRel cpDefinitionOptionRel = fetchByUuid_First(uuid,
				orderByComparator);

		if (cpDefinitionOptionRel != null) {
			return cpDefinitionOptionRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchCPDefinitionOptionRelException(msg.toString());
	}

	/**
	 * Returns the first cp definition option rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel fetchByUuid_First(String uuid,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {
		List<CPDefinitionOptionRel> list = findByUuid(uuid, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition option rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition option rel
	 * @throws NoSuchCPDefinitionOptionRelException if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel findByUuid_Last(String uuid,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws NoSuchCPDefinitionOptionRelException {
		CPDefinitionOptionRel cpDefinitionOptionRel = fetchByUuid_Last(uuid,
				orderByComparator);

		if (cpDefinitionOptionRel != null) {
			return cpDefinitionOptionRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchCPDefinitionOptionRelException(msg.toString());
	}

	/**
	 * Returns the last cp definition option rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel fetchByUuid_Last(String uuid,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionOptionRel> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition option rels before and after the current cp definition option rel in the ordered set where uuid = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the primary key of the current cp definition option rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition option rel
	 * @throws NoSuchCPDefinitionOptionRelException if a cp definition option rel with the primary key could not be found
	 */
	@Override
	public CPDefinitionOptionRel[] findByUuid_PrevAndNext(
		long CPDefinitionOptionRelId, String uuid,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws NoSuchCPDefinitionOptionRelException {
		CPDefinitionOptionRel cpDefinitionOptionRel = findByPrimaryKey(CPDefinitionOptionRelId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionOptionRel[] array = new CPDefinitionOptionRelImpl[3];

			array[0] = getByUuid_PrevAndNext(session, cpDefinitionOptionRel,
					uuid, orderByComparator, true);

			array[1] = cpDefinitionOptionRel;

			array[2] = getByUuid_PrevAndNext(session, cpDefinitionOptionRel,
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

	protected CPDefinitionOptionRel getByUuid_PrevAndNext(Session session,
		CPDefinitionOptionRel cpDefinitionOptionRel, String uuid,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator,
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

		query.append(_SQL_SELECT_CPDEFINITIONOPTIONREL_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else if (uuid.equals("")) {
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
			query.append(CPDefinitionOptionRelModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(cpDefinitionOptionRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPDefinitionOptionRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definition option rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CPDefinitionOptionRel cpDefinitionOptionRel : findByUuid(uuid,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpDefinitionOptionRel);
		}
	}

	/**
	 * Returns the number of cp definition option rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp definition option rels
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CPDEFINITIONOPTIONREL_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals("")) {
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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "cpDefinitionOptionRel.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "cpDefinitionOptionRel.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(cpDefinitionOptionRel.uuid IS NULL OR cpDefinitionOptionRel.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(CPDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionRelModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionOptionRelImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			CPDefinitionOptionRelModelImpl.UUID_COLUMN_BITMASK |
			CPDefinitionOptionRelModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(CPDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the cp definition option rel where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPDefinitionOptionRelException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp definition option rel
	 * @throws NoSuchCPDefinitionOptionRelException if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel findByUUID_G(String uuid, long groupId)
		throws NoSuchCPDefinitionOptionRelException {
		CPDefinitionOptionRel cpDefinitionOptionRel = fetchByUUID_G(uuid,
				groupId);

		if (cpDefinitionOptionRel == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchCPDefinitionOptionRelException(msg.toString());
		}

		return cpDefinitionOptionRel;
	}

	/**
	 * Returns the cp definition option rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the cp definition option rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof CPDefinitionOptionRel) {
			CPDefinitionOptionRel cpDefinitionOptionRel = (CPDefinitionOptionRel)result;

			if (!Objects.equals(uuid, cpDefinitionOptionRel.getUuid()) ||
					(groupId != cpDefinitionOptionRel.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_CPDEFINITIONOPTIONREL_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals("")) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<CPDefinitionOptionRel> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					CPDefinitionOptionRel cpDefinitionOptionRel = list.get(0);

					result = cpDefinitionOptionRel;

					cacheResult(cpDefinitionOptionRel);

					if ((cpDefinitionOptionRel.getUuid() == null) ||
							!cpDefinitionOptionRel.getUuid().equals(uuid) ||
							(cpDefinitionOptionRel.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, cpDefinitionOptionRel);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, finderArgs);

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
			return (CPDefinitionOptionRel)result;
		}
	}

	/**
	 * Removes the cp definition option rel where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the cp definition option rel that was removed
	 */
	@Override
	public CPDefinitionOptionRel removeByUUID_G(String uuid, long groupId)
		throws NoSuchCPDefinitionOptionRelException {
		CPDefinitionOptionRel cpDefinitionOptionRel = findByUUID_G(uuid, groupId);

		return remove(cpDefinitionOptionRel);
	}

	/**
	 * Returns the number of cp definition option rels where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching cp definition option rels
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CPDEFINITIONOPTIONREL_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals("")) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "cpDefinitionOptionRel.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "cpDefinitionOptionRel.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(cpDefinitionOptionRel.uuid IS NULL OR cpDefinitionOptionRel.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "cpDefinitionOptionRel.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(CPDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionRelModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionOptionRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(CPDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionRelModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionOptionRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			CPDefinitionOptionRelModelImpl.UUID_COLUMN_BITMASK |
			CPDefinitionOptionRelModelImpl.COMPANYID_COLUMN_BITMASK |
			CPDefinitionOptionRelModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(CPDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the cp definition option rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition option rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @return the range of matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByUuid_C(String uuid,
		long companyId, int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition option rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition option rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator,
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

		List<CPDefinitionOptionRel> list = null;

		if (retrieveFromCache) {
			list = (List<CPDefinitionOptionRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionOptionRel cpDefinitionOptionRel : list) {
					if (!Objects.equals(uuid, cpDefinitionOptionRel.getUuid()) ||
							(companyId != cpDefinitionOptionRel.getCompanyId())) {
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

			query.append(_SQL_SELECT_CPDEFINITIONOPTIONREL_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals("")) {
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
				query.append(CPDefinitionOptionRelModelImpl.ORDER_BY_JPQL);
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
					list = (List<CPDefinitionOptionRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPDefinitionOptionRel>)QueryUtil.list(q,
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
	 * Returns the first cp definition option rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition option rel
	 * @throws NoSuchCPDefinitionOptionRelException if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel findByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws NoSuchCPDefinitionOptionRelException {
		CPDefinitionOptionRel cpDefinitionOptionRel = fetchByUuid_C_First(uuid,
				companyId, orderByComparator);

		if (cpDefinitionOptionRel != null) {
			return cpDefinitionOptionRel;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchCPDefinitionOptionRelException(msg.toString());
	}

	/**
	 * Returns the first cp definition option rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel fetchByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {
		List<CPDefinitionOptionRel> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition option rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition option rel
	 * @throws NoSuchCPDefinitionOptionRelException if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws NoSuchCPDefinitionOptionRelException {
		CPDefinitionOptionRel cpDefinitionOptionRel = fetchByUuid_C_Last(uuid,
				companyId, orderByComparator);

		if (cpDefinitionOptionRel != null) {
			return cpDefinitionOptionRel;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchCPDefinitionOptionRelException(msg.toString());
	}

	/**
	 * Returns the last cp definition option rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel fetchByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionOptionRel> list = findByUuid_C(uuid, companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition option rels before and after the current cp definition option rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the primary key of the current cp definition option rel
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition option rel
	 * @throws NoSuchCPDefinitionOptionRelException if a cp definition option rel with the primary key could not be found
	 */
	@Override
	public CPDefinitionOptionRel[] findByUuid_C_PrevAndNext(
		long CPDefinitionOptionRelId, String uuid, long companyId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws NoSuchCPDefinitionOptionRelException {
		CPDefinitionOptionRel cpDefinitionOptionRel = findByPrimaryKey(CPDefinitionOptionRelId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionOptionRel[] array = new CPDefinitionOptionRelImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, cpDefinitionOptionRel,
					uuid, companyId, orderByComparator, true);

			array[1] = cpDefinitionOptionRel;

			array[2] = getByUuid_C_PrevAndNext(session, cpDefinitionOptionRel,
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

	protected CPDefinitionOptionRel getByUuid_C_PrevAndNext(Session session,
		CPDefinitionOptionRel cpDefinitionOptionRel, String uuid,
		long companyId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator,
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

		query.append(_SQL_SELECT_CPDEFINITIONOPTIONREL_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1);
		}
		else if (uuid.equals("")) {
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
			query.append(CPDefinitionOptionRelModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(cpDefinitionOptionRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPDefinitionOptionRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definition option rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CPDefinitionOptionRel cpDefinitionOptionRel : findByUuid_C(uuid,
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpDefinitionOptionRel);
		}
	}

	/**
	 * Returns the number of cp definition option rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp definition option rels
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CPDEFINITIONOPTIONREL_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals("")) {
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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "cpDefinitionOptionRel.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "cpDefinitionOptionRel.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(cpDefinitionOptionRel.uuid IS NULL OR cpDefinitionOptionRel.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "cpDefinitionOptionRel.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(CPDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionRelModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionOptionRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(CPDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionRelModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionOptionRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			CPDefinitionOptionRelModelImpl.GROUPID_COLUMN_BITMASK |
			CPDefinitionOptionRelModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(CPDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the cp definition option rels where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition option rels where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @return the range of matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByGroupId(long groupId, int start,
		int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition option rels where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CPDefinitionOptionRel> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition option rels where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CPDefinitionOptionRel> orderByComparator,
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

		List<CPDefinitionOptionRel> list = null;

		if (retrieveFromCache) {
			list = (List<CPDefinitionOptionRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionOptionRel cpDefinitionOptionRel : list) {
					if ((groupId != cpDefinitionOptionRel.getGroupId())) {
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

			query.append(_SQL_SELECT_CPDEFINITIONOPTIONREL_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CPDefinitionOptionRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<CPDefinitionOptionRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPDefinitionOptionRel>)QueryUtil.list(q,
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
	 * Returns the first cp definition option rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition option rel
	 * @throws NoSuchCPDefinitionOptionRelException if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel findByGroupId_First(long groupId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws NoSuchCPDefinitionOptionRelException {
		CPDefinitionOptionRel cpDefinitionOptionRel = fetchByGroupId_First(groupId,
				orderByComparator);

		if (cpDefinitionOptionRel != null) {
			return cpDefinitionOptionRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchCPDefinitionOptionRelException(msg.toString());
	}

	/**
	 * Returns the first cp definition option rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel fetchByGroupId_First(long groupId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {
		List<CPDefinitionOptionRel> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition option rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition option rel
	 * @throws NoSuchCPDefinitionOptionRelException if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel findByGroupId_Last(long groupId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws NoSuchCPDefinitionOptionRelException {
		CPDefinitionOptionRel cpDefinitionOptionRel = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (cpDefinitionOptionRel != null) {
			return cpDefinitionOptionRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchCPDefinitionOptionRelException(msg.toString());
	}

	/**
	 * Returns the last cp definition option rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel fetchByGroupId_Last(long groupId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionOptionRel> list = findByGroupId(groupId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition option rels before and after the current cp definition option rel in the ordered set where groupId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the primary key of the current cp definition option rel
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition option rel
	 * @throws NoSuchCPDefinitionOptionRelException if a cp definition option rel with the primary key could not be found
	 */
	@Override
	public CPDefinitionOptionRel[] findByGroupId_PrevAndNext(
		long CPDefinitionOptionRelId, long groupId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws NoSuchCPDefinitionOptionRelException {
		CPDefinitionOptionRel cpDefinitionOptionRel = findByPrimaryKey(CPDefinitionOptionRelId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionOptionRel[] array = new CPDefinitionOptionRelImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, cpDefinitionOptionRel,
					groupId, orderByComparator, true);

			array[1] = cpDefinitionOptionRel;

			array[2] = getByGroupId_PrevAndNext(session, cpDefinitionOptionRel,
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

	protected CPDefinitionOptionRel getByGroupId_PrevAndNext(Session session,
		CPDefinitionOptionRel cpDefinitionOptionRel, long groupId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator,
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

		query.append(_SQL_SELECT_CPDEFINITIONOPTIONREL_WHERE);

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
			query.append(CPDefinitionOptionRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(cpDefinitionOptionRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPDefinitionOptionRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definition option rels where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (CPDefinitionOptionRel cpDefinitionOptionRel : findByGroupId(
				groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpDefinitionOptionRel);
		}
	}

	/**
	 * Returns the number of cp definition option rels where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching cp definition option rels
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CPDEFINITIONOPTIONREL_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "cpDefinitionOptionRel.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(CPDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionRelModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionOptionRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(CPDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionRelModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionOptionRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			CPDefinitionOptionRelModelImpl.COMPANYID_COLUMN_BITMASK |
			CPDefinitionOptionRelModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(CPDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the cp definition option rels where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the cp definition option rels where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @return the range of matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByCompanyId(long companyId,
		int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition option rels where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {
		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition option rels where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator,
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

		List<CPDefinitionOptionRel> list = null;

		if (retrieveFromCache) {
			list = (List<CPDefinitionOptionRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionOptionRel cpDefinitionOptionRel : list) {
					if ((companyId != cpDefinitionOptionRel.getCompanyId())) {
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

			query.append(_SQL_SELECT_CPDEFINITIONOPTIONREL_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CPDefinitionOptionRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<CPDefinitionOptionRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPDefinitionOptionRel>)QueryUtil.list(q,
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
	 * Returns the first cp definition option rel in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition option rel
	 * @throws NoSuchCPDefinitionOptionRelException if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel findByCompanyId_First(long companyId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws NoSuchCPDefinitionOptionRelException {
		CPDefinitionOptionRel cpDefinitionOptionRel = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (cpDefinitionOptionRel != null) {
			return cpDefinitionOptionRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchCPDefinitionOptionRelException(msg.toString());
	}

	/**
	 * Returns the first cp definition option rel in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel fetchByCompanyId_First(long companyId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {
		List<CPDefinitionOptionRel> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition option rel in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition option rel
	 * @throws NoSuchCPDefinitionOptionRelException if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel findByCompanyId_Last(long companyId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws NoSuchCPDefinitionOptionRelException {
		CPDefinitionOptionRel cpDefinitionOptionRel = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (cpDefinitionOptionRel != null) {
			return cpDefinitionOptionRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchCPDefinitionOptionRelException(msg.toString());
	}

	/**
	 * Returns the last cp definition option rel in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel fetchByCompanyId_Last(long companyId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionOptionRel> list = findByCompanyId(companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition option rels before and after the current cp definition option rel in the ordered set where companyId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the primary key of the current cp definition option rel
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition option rel
	 * @throws NoSuchCPDefinitionOptionRelException if a cp definition option rel with the primary key could not be found
	 */
	@Override
	public CPDefinitionOptionRel[] findByCompanyId_PrevAndNext(
		long CPDefinitionOptionRelId, long companyId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws NoSuchCPDefinitionOptionRelException {
		CPDefinitionOptionRel cpDefinitionOptionRel = findByPrimaryKey(CPDefinitionOptionRelId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionOptionRel[] array = new CPDefinitionOptionRelImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session,
					cpDefinitionOptionRel, companyId, orderByComparator, true);

			array[1] = cpDefinitionOptionRel;

			array[2] = getByCompanyId_PrevAndNext(session,
					cpDefinitionOptionRel, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPDefinitionOptionRel getByCompanyId_PrevAndNext(
		Session session, CPDefinitionOptionRel cpDefinitionOptionRel,
		long companyId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator,
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

		query.append(_SQL_SELECT_CPDEFINITIONOPTIONREL_WHERE);

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
			query.append(CPDefinitionOptionRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(cpDefinitionOptionRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPDefinitionOptionRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definition option rels where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (CPDefinitionOptionRel cpDefinitionOptionRel : findByCompanyId(
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpDefinitionOptionRel);
		}
	}

	/**
	 * Returns the number of cp definition option rels where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching cp definition option rels
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CPDEFINITIONOPTIONREL_WHERE);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "cpDefinitionOptionRel.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_CPDEFINITIONID =
		new FinderPath(CPDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionRelModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionOptionRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCPDefinitionId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONID =
		new FinderPath(CPDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionRelModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionOptionRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCPDefinitionId",
			new String[] { Long.class.getName() },
			CPDefinitionOptionRelModelImpl.CPDEFINITIONID_COLUMN_BITMASK |
			CPDefinitionOptionRelModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CPDEFINITIONID = new FinderPath(CPDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCPDefinitionId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the cp definition option rels where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByCPDefinitionId(long CPDefinitionId) {
		return findByCPDefinitionId(CPDefinitionId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition option rels where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @return the range of matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByCPDefinitionId(
		long CPDefinitionId, int start, int end) {
		return findByCPDefinitionId(CPDefinitionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition option rels where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {
		return findByCPDefinitionId(CPDefinitionId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition option rels where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONID;
			finderArgs = new Object[] { CPDefinitionId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_CPDEFINITIONID;
			finderArgs = new Object[] {
					CPDefinitionId,
					
					start, end, orderByComparator
				};
		}

		List<CPDefinitionOptionRel> list = null;

		if (retrieveFromCache) {
			list = (List<CPDefinitionOptionRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionOptionRel cpDefinitionOptionRel : list) {
					if ((CPDefinitionId != cpDefinitionOptionRel.getCPDefinitionId())) {
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

			query.append(_SQL_SELECT_CPDEFINITIONOPTIONREL_WHERE);

			query.append(_FINDER_COLUMN_CPDEFINITIONID_CPDEFINITIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CPDefinitionOptionRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(CPDefinitionId);

				if (!pagination) {
					list = (List<CPDefinitionOptionRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPDefinitionOptionRel>)QueryUtil.list(q,
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
	 * Returns the first cp definition option rel in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition option rel
	 * @throws NoSuchCPDefinitionOptionRelException if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel findByCPDefinitionId_First(
		long CPDefinitionId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws NoSuchCPDefinitionOptionRelException {
		CPDefinitionOptionRel cpDefinitionOptionRel = fetchByCPDefinitionId_First(CPDefinitionId,
				orderByComparator);

		if (cpDefinitionOptionRel != null) {
			return cpDefinitionOptionRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("CPDefinitionId=");
		msg.append(CPDefinitionId);

		msg.append("}");

		throw new NoSuchCPDefinitionOptionRelException(msg.toString());
	}

	/**
	 * Returns the first cp definition option rel in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel fetchByCPDefinitionId_First(
		long CPDefinitionId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {
		List<CPDefinitionOptionRel> list = findByCPDefinitionId(CPDefinitionId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition option rel in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition option rel
	 * @throws NoSuchCPDefinitionOptionRelException if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel findByCPDefinitionId_Last(
		long CPDefinitionId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws NoSuchCPDefinitionOptionRelException {
		CPDefinitionOptionRel cpDefinitionOptionRel = fetchByCPDefinitionId_Last(CPDefinitionId,
				orderByComparator);

		if (cpDefinitionOptionRel != null) {
			return cpDefinitionOptionRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("CPDefinitionId=");
		msg.append(CPDefinitionId);

		msg.append("}");

		throw new NoSuchCPDefinitionOptionRelException(msg.toString());
	}

	/**
	 * Returns the last cp definition option rel in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel fetchByCPDefinitionId_Last(
		long CPDefinitionId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {
		int count = countByCPDefinitionId(CPDefinitionId);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionOptionRel> list = findByCPDefinitionId(CPDefinitionId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition option rels before and after the current cp definition option rel in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the primary key of the current cp definition option rel
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition option rel
	 * @throws NoSuchCPDefinitionOptionRelException if a cp definition option rel with the primary key could not be found
	 */
	@Override
	public CPDefinitionOptionRel[] findByCPDefinitionId_PrevAndNext(
		long CPDefinitionOptionRelId, long CPDefinitionId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws NoSuchCPDefinitionOptionRelException {
		CPDefinitionOptionRel cpDefinitionOptionRel = findByPrimaryKey(CPDefinitionOptionRelId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionOptionRel[] array = new CPDefinitionOptionRelImpl[3];

			array[0] = getByCPDefinitionId_PrevAndNext(session,
					cpDefinitionOptionRel, CPDefinitionId, orderByComparator,
					true);

			array[1] = cpDefinitionOptionRel;

			array[2] = getByCPDefinitionId_PrevAndNext(session,
					cpDefinitionOptionRel, CPDefinitionId, orderByComparator,
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

	protected CPDefinitionOptionRel getByCPDefinitionId_PrevAndNext(
		Session session, CPDefinitionOptionRel cpDefinitionOptionRel,
		long CPDefinitionId,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator,
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

		query.append(_SQL_SELECT_CPDEFINITIONOPTIONREL_WHERE);

		query.append(_FINDER_COLUMN_CPDEFINITIONID_CPDEFINITIONID_2);

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
			query.append(CPDefinitionOptionRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(CPDefinitionId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(cpDefinitionOptionRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPDefinitionOptionRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definition option rels where CPDefinitionId = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 */
	@Override
	public void removeByCPDefinitionId(long CPDefinitionId) {
		for (CPDefinitionOptionRel cpDefinitionOptionRel : findByCPDefinitionId(
				CPDefinitionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpDefinitionOptionRel);
		}
	}

	/**
	 * Returns the number of cp definition option rels where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the number of matching cp definition option rels
	 */
	@Override
	public int countByCPDefinitionId(long CPDefinitionId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_CPDEFINITIONID;

		Object[] finderArgs = new Object[] { CPDefinitionId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CPDEFINITIONOPTIONREL_WHERE);

			query.append(_FINDER_COLUMN_CPDEFINITIONID_CPDEFINITIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(CPDefinitionId);

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

	private static final String _FINDER_COLUMN_CPDEFINITIONID_CPDEFINITIONID_2 = "cpDefinitionOptionRel.CPDefinitionId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_SC = new FinderPath(CPDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionRelModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionOptionRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_SC",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_SC = new FinderPath(CPDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionRelModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionOptionRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_SC",
			new String[] { Long.class.getName(), Boolean.class.getName() },
			CPDefinitionOptionRelModelImpl.CPDEFINITIONID_COLUMN_BITMASK |
			CPDefinitionOptionRelModelImpl.SKUCONTRIBUTOR_COLUMN_BITMASK |
			CPDefinitionOptionRelModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_SC = new FinderPath(CPDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_SC",
			new String[] { Long.class.getName(), Boolean.class.getName() });

	/**
	 * Returns all the cp definition option rels where CPDefinitionId = &#63; and skuContributor = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param skuContributor the sku contributor
	 * @return the matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByC_SC(long CPDefinitionId,
		boolean skuContributor) {
		return findByC_SC(CPDefinitionId, skuContributor, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition option rels where CPDefinitionId = &#63; and skuContributor = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param skuContributor the sku contributor
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @return the range of matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByC_SC(long CPDefinitionId,
		boolean skuContributor, int start, int end) {
		return findByC_SC(CPDefinitionId, skuContributor, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition option rels where CPDefinitionId = &#63; and skuContributor = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param skuContributor the sku contributor
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByC_SC(long CPDefinitionId,
		boolean skuContributor, int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {
		return findByC_SC(CPDefinitionId, skuContributor, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition option rels where CPDefinitionId = &#63; and skuContributor = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param skuContributor the sku contributor
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findByC_SC(long CPDefinitionId,
		boolean skuContributor, int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_SC;
			finderArgs = new Object[] { CPDefinitionId, skuContributor };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_SC;
			finderArgs = new Object[] {
					CPDefinitionId, skuContributor,
					
					start, end, orderByComparator
				};
		}

		List<CPDefinitionOptionRel> list = null;

		if (retrieveFromCache) {
			list = (List<CPDefinitionOptionRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionOptionRel cpDefinitionOptionRel : list) {
					if ((CPDefinitionId != cpDefinitionOptionRel.getCPDefinitionId()) ||
							(skuContributor != cpDefinitionOptionRel.isSkuContributor())) {
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

			query.append(_SQL_SELECT_CPDEFINITIONOPTIONREL_WHERE);

			query.append(_FINDER_COLUMN_C_SC_CPDEFINITIONID_2);

			query.append(_FINDER_COLUMN_C_SC_SKUCONTRIBUTOR_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CPDefinitionOptionRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(CPDefinitionId);

				qPos.add(skuContributor);

				if (!pagination) {
					list = (List<CPDefinitionOptionRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPDefinitionOptionRel>)QueryUtil.list(q,
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
	 * Returns the first cp definition option rel in the ordered set where CPDefinitionId = &#63; and skuContributor = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param skuContributor the sku contributor
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition option rel
	 * @throws NoSuchCPDefinitionOptionRelException if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel findByC_SC_First(long CPDefinitionId,
		boolean skuContributor,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws NoSuchCPDefinitionOptionRelException {
		CPDefinitionOptionRel cpDefinitionOptionRel = fetchByC_SC_First(CPDefinitionId,
				skuContributor, orderByComparator);

		if (cpDefinitionOptionRel != null) {
			return cpDefinitionOptionRel;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("CPDefinitionId=");
		msg.append(CPDefinitionId);

		msg.append(", skuContributor=");
		msg.append(skuContributor);

		msg.append("}");

		throw new NoSuchCPDefinitionOptionRelException(msg.toString());
	}

	/**
	 * Returns the first cp definition option rel in the ordered set where CPDefinitionId = &#63; and skuContributor = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param skuContributor the sku contributor
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel fetchByC_SC_First(long CPDefinitionId,
		boolean skuContributor,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {
		List<CPDefinitionOptionRel> list = findByC_SC(CPDefinitionId,
				skuContributor, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition option rel in the ordered set where CPDefinitionId = &#63; and skuContributor = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param skuContributor the sku contributor
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition option rel
	 * @throws NoSuchCPDefinitionOptionRelException if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel findByC_SC_Last(long CPDefinitionId,
		boolean skuContributor,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws NoSuchCPDefinitionOptionRelException {
		CPDefinitionOptionRel cpDefinitionOptionRel = fetchByC_SC_Last(CPDefinitionId,
				skuContributor, orderByComparator);

		if (cpDefinitionOptionRel != null) {
			return cpDefinitionOptionRel;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("CPDefinitionId=");
		msg.append(CPDefinitionId);

		msg.append(", skuContributor=");
		msg.append(skuContributor);

		msg.append("}");

		throw new NoSuchCPDefinitionOptionRelException(msg.toString());
	}

	/**
	 * Returns the last cp definition option rel in the ordered set where CPDefinitionId = &#63; and skuContributor = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param skuContributor the sku contributor
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	 */
	@Override
	public CPDefinitionOptionRel fetchByC_SC_Last(long CPDefinitionId,
		boolean skuContributor,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {
		int count = countByC_SC(CPDefinitionId, skuContributor);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionOptionRel> list = findByC_SC(CPDefinitionId,
				skuContributor, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition option rels before and after the current cp definition option rel in the ordered set where CPDefinitionId = &#63; and skuContributor = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the primary key of the current cp definition option rel
	 * @param CPDefinitionId the cp definition ID
	 * @param skuContributor the sku contributor
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition option rel
	 * @throws NoSuchCPDefinitionOptionRelException if a cp definition option rel with the primary key could not be found
	 */
	@Override
	public CPDefinitionOptionRel[] findByC_SC_PrevAndNext(
		long CPDefinitionOptionRelId, long CPDefinitionId,
		boolean skuContributor,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws NoSuchCPDefinitionOptionRelException {
		CPDefinitionOptionRel cpDefinitionOptionRel = findByPrimaryKey(CPDefinitionOptionRelId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionOptionRel[] array = new CPDefinitionOptionRelImpl[3];

			array[0] = getByC_SC_PrevAndNext(session, cpDefinitionOptionRel,
					CPDefinitionId, skuContributor, orderByComparator, true);

			array[1] = cpDefinitionOptionRel;

			array[2] = getByC_SC_PrevAndNext(session, cpDefinitionOptionRel,
					CPDefinitionId, skuContributor, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPDefinitionOptionRel getByC_SC_PrevAndNext(Session session,
		CPDefinitionOptionRel cpDefinitionOptionRel, long CPDefinitionId,
		boolean skuContributor,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator,
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

		query.append(_SQL_SELECT_CPDEFINITIONOPTIONREL_WHERE);

		query.append(_FINDER_COLUMN_C_SC_CPDEFINITIONID_2);

		query.append(_FINDER_COLUMN_C_SC_SKUCONTRIBUTOR_2);

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
			query.append(CPDefinitionOptionRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(CPDefinitionId);

		qPos.add(skuContributor);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(cpDefinitionOptionRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPDefinitionOptionRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definition option rels where CPDefinitionId = &#63; and skuContributor = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param skuContributor the sku contributor
	 */
	@Override
	public void removeByC_SC(long CPDefinitionId, boolean skuContributor) {
		for (CPDefinitionOptionRel cpDefinitionOptionRel : findByC_SC(
				CPDefinitionId, skuContributor, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(cpDefinitionOptionRel);
		}
	}

	/**
	 * Returns the number of cp definition option rels where CPDefinitionId = &#63; and skuContributor = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param skuContributor the sku contributor
	 * @return the number of matching cp definition option rels
	 */
	@Override
	public int countByC_SC(long CPDefinitionId, boolean skuContributor) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_SC;

		Object[] finderArgs = new Object[] { CPDefinitionId, skuContributor };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CPDEFINITIONOPTIONREL_WHERE);

			query.append(_FINDER_COLUMN_C_SC_CPDEFINITIONID_2);

			query.append(_FINDER_COLUMN_C_SC_SKUCONTRIBUTOR_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(CPDefinitionId);

				qPos.add(skuContributor);

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

	private static final String _FINDER_COLUMN_C_SC_CPDEFINITIONID_2 = "cpDefinitionOptionRel.CPDefinitionId = ? AND ";
	private static final String _FINDER_COLUMN_C_SC_SKUCONTRIBUTOR_2 = "cpDefinitionOptionRel.skuContributor = ?";

	public CPDefinitionOptionRelPersistenceImpl() {
		setModelClass(CPDefinitionOptionRel.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("uuid", "uuid_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the cp definition option rel in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionOptionRel the cp definition option rel
	 */
	@Override
	public void cacheResult(CPDefinitionOptionRel cpDefinitionOptionRel) {
		entityCache.putResult(CPDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionRelImpl.class,
			cpDefinitionOptionRel.getPrimaryKey(), cpDefinitionOptionRel);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				cpDefinitionOptionRel.getUuid(),
				cpDefinitionOptionRel.getGroupId()
			}, cpDefinitionOptionRel);

		cpDefinitionOptionRel.resetOriginalValues();
	}

	/**
	 * Caches the cp definition option rels in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionOptionRels the cp definition option rels
	 */
	@Override
	public void cacheResult(List<CPDefinitionOptionRel> cpDefinitionOptionRels) {
		for (CPDefinitionOptionRel cpDefinitionOptionRel : cpDefinitionOptionRels) {
			if (entityCache.getResult(
						CPDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
						CPDefinitionOptionRelImpl.class,
						cpDefinitionOptionRel.getPrimaryKey()) == null) {
				cacheResult(cpDefinitionOptionRel);
			}
			else {
				cpDefinitionOptionRel.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all cp definition option rels.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CPDefinitionOptionRelImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the cp definition option rel.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CPDefinitionOptionRel cpDefinitionOptionRel) {
		entityCache.removeResult(CPDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionRelImpl.class,
			cpDefinitionOptionRel.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CPDefinitionOptionRelModelImpl)cpDefinitionOptionRel,
			true);
	}

	@Override
	public void clearCache(List<CPDefinitionOptionRel> cpDefinitionOptionRels) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CPDefinitionOptionRel cpDefinitionOptionRel : cpDefinitionOptionRels) {
			entityCache.removeResult(CPDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
				CPDefinitionOptionRelImpl.class,
				cpDefinitionOptionRel.getPrimaryKey());

			clearUniqueFindersCache((CPDefinitionOptionRelModelImpl)cpDefinitionOptionRel,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		CPDefinitionOptionRelModelImpl cpDefinitionOptionRelModelImpl) {
		Object[] args = new Object[] {
				cpDefinitionOptionRelModelImpl.getUuid(),
				cpDefinitionOptionRelModelImpl.getGroupId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
			cpDefinitionOptionRelModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CPDefinitionOptionRelModelImpl cpDefinitionOptionRelModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					cpDefinitionOptionRelModelImpl.getUuid(),
					cpDefinitionOptionRelModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if ((cpDefinitionOptionRelModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					cpDefinitionOptionRelModelImpl.getOriginalUuid(),
					cpDefinitionOptionRelModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}
	}

	/**
	 * Creates a new cp definition option rel with the primary key. Does not add the cp definition option rel to the database.
	 *
	 * @param CPDefinitionOptionRelId the primary key for the new cp definition option rel
	 * @return the new cp definition option rel
	 */
	@Override
	public CPDefinitionOptionRel create(long CPDefinitionOptionRelId) {
		CPDefinitionOptionRel cpDefinitionOptionRel = new CPDefinitionOptionRelImpl();

		cpDefinitionOptionRel.setNew(true);
		cpDefinitionOptionRel.setPrimaryKey(CPDefinitionOptionRelId);

		String uuid = PortalUUIDUtil.generate();

		cpDefinitionOptionRel.setUuid(uuid);

		cpDefinitionOptionRel.setCompanyId(companyProvider.getCompanyId());

		return cpDefinitionOptionRel;
	}

	/**
	 * Removes the cp definition option rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPDefinitionOptionRelId the primary key of the cp definition option rel
	 * @return the cp definition option rel that was removed
	 * @throws NoSuchCPDefinitionOptionRelException if a cp definition option rel with the primary key could not be found
	 */
	@Override
	public CPDefinitionOptionRel remove(long CPDefinitionOptionRelId)
		throws NoSuchCPDefinitionOptionRelException {
		return remove((Serializable)CPDefinitionOptionRelId);
	}

	/**
	 * Removes the cp definition option rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cp definition option rel
	 * @return the cp definition option rel that was removed
	 * @throws NoSuchCPDefinitionOptionRelException if a cp definition option rel with the primary key could not be found
	 */
	@Override
	public CPDefinitionOptionRel remove(Serializable primaryKey)
		throws NoSuchCPDefinitionOptionRelException {
		Session session = null;

		try {
			session = openSession();

			CPDefinitionOptionRel cpDefinitionOptionRel = (CPDefinitionOptionRel)session.get(CPDefinitionOptionRelImpl.class,
					primaryKey);

			if (cpDefinitionOptionRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCPDefinitionOptionRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(cpDefinitionOptionRel);
		}
		catch (NoSuchCPDefinitionOptionRelException nsee) {
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
	protected CPDefinitionOptionRel removeImpl(
		CPDefinitionOptionRel cpDefinitionOptionRel) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpDefinitionOptionRel)) {
				cpDefinitionOptionRel = (CPDefinitionOptionRel)session.get(CPDefinitionOptionRelImpl.class,
						cpDefinitionOptionRel.getPrimaryKeyObj());
			}

			if (cpDefinitionOptionRel != null) {
				session.delete(cpDefinitionOptionRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (cpDefinitionOptionRel != null) {
			clearCache(cpDefinitionOptionRel);
		}

		return cpDefinitionOptionRel;
	}

	@Override
	public CPDefinitionOptionRel updateImpl(
		CPDefinitionOptionRel cpDefinitionOptionRel) {
		boolean isNew = cpDefinitionOptionRel.isNew();

		if (!(cpDefinitionOptionRel instanceof CPDefinitionOptionRelModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(cpDefinitionOptionRel.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(cpDefinitionOptionRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in cpDefinitionOptionRel proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CPDefinitionOptionRel implementation " +
				cpDefinitionOptionRel.getClass());
		}

		CPDefinitionOptionRelModelImpl cpDefinitionOptionRelModelImpl = (CPDefinitionOptionRelModelImpl)cpDefinitionOptionRel;

		if (Validator.isNull(cpDefinitionOptionRel.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			cpDefinitionOptionRel.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (cpDefinitionOptionRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				cpDefinitionOptionRel.setCreateDate(now);
			}
			else {
				cpDefinitionOptionRel.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!cpDefinitionOptionRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cpDefinitionOptionRel.setModifiedDate(now);
			}
			else {
				cpDefinitionOptionRel.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (cpDefinitionOptionRel.isNew()) {
				session.save(cpDefinitionOptionRel);

				cpDefinitionOptionRel.setNew(false);
			}
			else {
				cpDefinitionOptionRel = (CPDefinitionOptionRel)session.merge(cpDefinitionOptionRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CPDefinitionOptionRelModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					cpDefinitionOptionRelModelImpl.getUuid()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
				args);

			args = new Object[] {
					cpDefinitionOptionRelModelImpl.getUuid(),
					cpDefinitionOptionRelModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
				args);

			args = new Object[] { cpDefinitionOptionRelModelImpl.getGroupId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			args = new Object[] { cpDefinitionOptionRelModelImpl.getCompanyId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
				args);

			args = new Object[] {
					cpDefinitionOptionRelModelImpl.getCPDefinitionId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_CPDEFINITIONID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONID,
				args);

			args = new Object[] {
					cpDefinitionOptionRelModelImpl.getCPDefinitionId(),
					cpDefinitionOptionRelModelImpl.isSkuContributor()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_SC, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_SC,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((cpDefinitionOptionRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpDefinitionOptionRelModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { cpDefinitionOptionRelModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((cpDefinitionOptionRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpDefinitionOptionRelModelImpl.getOriginalUuid(),
						cpDefinitionOptionRelModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						cpDefinitionOptionRelModelImpl.getUuid(),
						cpDefinitionOptionRelModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((cpDefinitionOptionRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpDefinitionOptionRelModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { cpDefinitionOptionRelModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((cpDefinitionOptionRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpDefinitionOptionRelModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] {
						cpDefinitionOptionRelModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((cpDefinitionOptionRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpDefinitionOptionRelModelImpl.getOriginalCPDefinitionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CPDEFINITIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONID,
					args);

				args = new Object[] {
						cpDefinitionOptionRelModelImpl.getCPDefinitionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CPDEFINITIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONID,
					args);
			}

			if ((cpDefinitionOptionRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_SC.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpDefinitionOptionRelModelImpl.getOriginalCPDefinitionId(),
						cpDefinitionOptionRelModelImpl.getOriginalSkuContributor()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_SC, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_SC,
					args);

				args = new Object[] {
						cpDefinitionOptionRelModelImpl.getCPDefinitionId(),
						cpDefinitionOptionRelModelImpl.isSkuContributor()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_SC, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_SC,
					args);
			}
		}

		entityCache.putResult(CPDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionRelImpl.class,
			cpDefinitionOptionRel.getPrimaryKey(), cpDefinitionOptionRel, false);

		clearUniqueFindersCache(cpDefinitionOptionRelModelImpl, false);
		cacheUniqueFindersCache(cpDefinitionOptionRelModelImpl);

		cpDefinitionOptionRel.resetOriginalValues();

		return cpDefinitionOptionRel;
	}

	/**
	 * Returns the cp definition option rel with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp definition option rel
	 * @return the cp definition option rel
	 * @throws NoSuchCPDefinitionOptionRelException if a cp definition option rel with the primary key could not be found
	 */
	@Override
	public CPDefinitionOptionRel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCPDefinitionOptionRelException {
		CPDefinitionOptionRel cpDefinitionOptionRel = fetchByPrimaryKey(primaryKey);

		if (cpDefinitionOptionRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCPDefinitionOptionRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return cpDefinitionOptionRel;
	}

	/**
	 * Returns the cp definition option rel with the primary key or throws a {@link NoSuchCPDefinitionOptionRelException} if it could not be found.
	 *
	 * @param CPDefinitionOptionRelId the primary key of the cp definition option rel
	 * @return the cp definition option rel
	 * @throws NoSuchCPDefinitionOptionRelException if a cp definition option rel with the primary key could not be found
	 */
	@Override
	public CPDefinitionOptionRel findByPrimaryKey(long CPDefinitionOptionRelId)
		throws NoSuchCPDefinitionOptionRelException {
		return findByPrimaryKey((Serializable)CPDefinitionOptionRelId);
	}

	/**
	 * Returns the cp definition option rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp definition option rel
	 * @return the cp definition option rel, or <code>null</code> if a cp definition option rel with the primary key could not be found
	 */
	@Override
	public CPDefinitionOptionRel fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CPDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
				CPDefinitionOptionRelImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CPDefinitionOptionRel cpDefinitionOptionRel = (CPDefinitionOptionRel)serializable;

		if (cpDefinitionOptionRel == null) {
			Session session = null;

			try {
				session = openSession();

				cpDefinitionOptionRel = (CPDefinitionOptionRel)session.get(CPDefinitionOptionRelImpl.class,
						primaryKey);

				if (cpDefinitionOptionRel != null) {
					cacheResult(cpDefinitionOptionRel);
				}
				else {
					entityCache.putResult(CPDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
						CPDefinitionOptionRelImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CPDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
					CPDefinitionOptionRelImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return cpDefinitionOptionRel;
	}

	/**
	 * Returns the cp definition option rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPDefinitionOptionRelId the primary key of the cp definition option rel
	 * @return the cp definition option rel, or <code>null</code> if a cp definition option rel with the primary key could not be found
	 */
	@Override
	public CPDefinitionOptionRel fetchByPrimaryKey(long CPDefinitionOptionRelId) {
		return fetchByPrimaryKey((Serializable)CPDefinitionOptionRelId);
	}

	@Override
	public Map<Serializable, CPDefinitionOptionRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CPDefinitionOptionRel> map = new HashMap<Serializable, CPDefinitionOptionRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CPDefinitionOptionRel cpDefinitionOptionRel = fetchByPrimaryKey(primaryKey);

			if (cpDefinitionOptionRel != null) {
				map.put(primaryKey, cpDefinitionOptionRel);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CPDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
					CPDefinitionOptionRelImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CPDefinitionOptionRel)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_CPDEFINITIONOPTIONREL_WHERE_PKS_IN);

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

			for (CPDefinitionOptionRel cpDefinitionOptionRel : (List<CPDefinitionOptionRel>)q.list()) {
				map.put(cpDefinitionOptionRel.getPrimaryKeyObj(),
					cpDefinitionOptionRel);

				cacheResult(cpDefinitionOptionRel);

				uncachedPrimaryKeys.remove(cpDefinitionOptionRel.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CPDefinitionOptionRelModelImpl.ENTITY_CACHE_ENABLED,
					CPDefinitionOptionRelImpl.class, primaryKey, nullModel);
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
	 * Returns all the cp definition option rels.
	 *
	 * @return the cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition option rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @return the range of cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition option rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findAll(int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition option rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition option rels
	 * @param end the upper bound of the range of cp definition option rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of cp definition option rels
	 */
	@Override
	public List<CPDefinitionOptionRel> findAll(int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator,
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

		List<CPDefinitionOptionRel> list = null;

		if (retrieveFromCache) {
			list = (List<CPDefinitionOptionRel>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CPDEFINITIONOPTIONREL);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CPDEFINITIONOPTIONREL;

				if (pagination) {
					sql = sql.concat(CPDefinitionOptionRelModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CPDefinitionOptionRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPDefinitionOptionRel>)QueryUtil.list(q,
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
	 * Removes all the cp definition option rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CPDefinitionOptionRel cpDefinitionOptionRel : findAll()) {
			remove(cpDefinitionOptionRel);
		}
	}

	/**
	 * Returns the number of cp definition option rels.
	 *
	 * @return the number of cp definition option rels
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CPDEFINITIONOPTIONREL);

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
		return CPDefinitionOptionRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the cp definition option rel persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CPDefinitionOptionRelImpl.class.getName());
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
	private static final String _SQL_SELECT_CPDEFINITIONOPTIONREL = "SELECT cpDefinitionOptionRel FROM CPDefinitionOptionRel cpDefinitionOptionRel";
	private static final String _SQL_SELECT_CPDEFINITIONOPTIONREL_WHERE_PKS_IN = "SELECT cpDefinitionOptionRel FROM CPDefinitionOptionRel cpDefinitionOptionRel WHERE CPDefinitionOptionRelId IN (";
	private static final String _SQL_SELECT_CPDEFINITIONOPTIONREL_WHERE = "SELECT cpDefinitionOptionRel FROM CPDefinitionOptionRel cpDefinitionOptionRel WHERE ";
	private static final String _SQL_COUNT_CPDEFINITIONOPTIONREL = "SELECT COUNT(cpDefinitionOptionRel) FROM CPDefinitionOptionRel cpDefinitionOptionRel";
	private static final String _SQL_COUNT_CPDEFINITIONOPTIONREL_WHERE = "SELECT COUNT(cpDefinitionOptionRel) FROM CPDefinitionOptionRel cpDefinitionOptionRel WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "cpDefinitionOptionRel.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CPDefinitionOptionRel exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CPDefinitionOptionRel exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CPDefinitionOptionRelPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid"
			});
}