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

import com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionValueRelException;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.impl.CPDefinitionOptionValueRelImpl;
import com.liferay.commerce.product.model.impl.CPDefinitionOptionValueRelModelImpl;
import com.liferay.commerce.product.service.persistence.CPDefinitionOptionValueRelPersistence;

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
 * The persistence implementation for the cp definition option value rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPDefinitionOptionValueRelPersistence
 * @see com.liferay.commerce.product.service.persistence.CPDefinitionOptionValueRelUtil
 * @generated
 */
@ProviderType
public class CPDefinitionOptionValueRelPersistenceImpl
	extends BasePersistenceImpl<CPDefinitionOptionValueRel>
	implements CPDefinitionOptionValueRelPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CPDefinitionOptionValueRelUtil} to access the cp definition option value rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CPDefinitionOptionValueRelImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CPDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionValueRelModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionOptionValueRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CPDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionValueRelModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionOptionValueRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CPDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionValueRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(CPDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionValueRelModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionOptionValueRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(CPDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionValueRelModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionOptionValueRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			CPDefinitionOptionValueRelModelImpl.UUID_COLUMN_BITMASK |
			CPDefinitionOptionValueRelModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(CPDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionValueRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] { String.class.getName() });

	/**
	 * Returns all the cp definition option value rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cp definition option value rels
	 */
	@Override
	public List<CPDefinitionOptionValueRel> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition option value rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definition option value rels
	 * @param end the upper bound of the range of cp definition option value rels (not inclusive)
	 * @return the range of matching cp definition option value rels
	 */
	@Override
	public List<CPDefinitionOptionValueRel> findByUuid(String uuid, int start,
		int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition option value rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definition option value rels
	 * @param end the upper bound of the range of cp definition option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition option value rels
	 */
	@Override
	public List<CPDefinitionOptionValueRel> findByUuid(String uuid, int start,
		int end, OrderByComparator<CPDefinitionOptionValueRel> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition option value rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definition option value rels
	 * @param end the upper bound of the range of cp definition option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp definition option value rels
	 */
	@Override
	public List<CPDefinitionOptionValueRel> findByUuid(String uuid, int start,
		int end,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator,
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

		List<CPDefinitionOptionValueRel> list = null;

		if (retrieveFromCache) {
			list = (List<CPDefinitionOptionValueRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel : list) {
					if (!Objects.equals(uuid,
								cpDefinitionOptionValueRel.getUuid())) {
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

			query.append(_SQL_SELECT_CPDEFINITIONOPTIONVALUEREL_WHERE);

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
				query.append(CPDefinitionOptionValueRelModelImpl.ORDER_BY_JPQL);
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
					list = (List<CPDefinitionOptionValueRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPDefinitionOptionValueRel>)QueryUtil.list(q,
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
	 * Returns the first cp definition option value rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition option value rel
	 * @throws NoSuchCPDefinitionOptionValueRelException if a matching cp definition option value rel could not be found
	 */
	@Override
	public CPDefinitionOptionValueRel findByUuid_First(String uuid,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator)
		throws NoSuchCPDefinitionOptionValueRelException {
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel = fetchByUuid_First(uuid,
				orderByComparator);

		if (cpDefinitionOptionValueRel != null) {
			return cpDefinitionOptionValueRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchCPDefinitionOptionValueRelException(msg.toString());
	}

	/**
	 * Returns the first cp definition option value rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition option value rel, or <code>null</code> if a matching cp definition option value rel could not be found
	 */
	@Override
	public CPDefinitionOptionValueRel fetchByUuid_First(String uuid,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator) {
		List<CPDefinitionOptionValueRel> list = findByUuid(uuid, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition option value rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition option value rel
	 * @throws NoSuchCPDefinitionOptionValueRelException if a matching cp definition option value rel could not be found
	 */
	@Override
	public CPDefinitionOptionValueRel findByUuid_Last(String uuid,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator)
		throws NoSuchCPDefinitionOptionValueRelException {
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel = fetchByUuid_Last(uuid,
				orderByComparator);

		if (cpDefinitionOptionValueRel != null) {
			return cpDefinitionOptionValueRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchCPDefinitionOptionValueRelException(msg.toString());
	}

	/**
	 * Returns the last cp definition option value rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition option value rel, or <code>null</code> if a matching cp definition option value rel could not be found
	 */
	@Override
	public CPDefinitionOptionValueRel fetchByUuid_Last(String uuid,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionOptionValueRel> list = findByUuid(uuid, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition option value rels before and after the current cp definition option value rel in the ordered set where uuid = &#63;.
	 *
	 * @param CPDefinitionOptionValueRelId the primary key of the current cp definition option value rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition option value rel
	 * @throws NoSuchCPDefinitionOptionValueRelException if a cp definition option value rel with the primary key could not be found
	 */
	@Override
	public CPDefinitionOptionValueRel[] findByUuid_PrevAndNext(
		long CPDefinitionOptionValueRelId, String uuid,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator)
		throws NoSuchCPDefinitionOptionValueRelException {
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel = findByPrimaryKey(CPDefinitionOptionValueRelId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionOptionValueRel[] array = new CPDefinitionOptionValueRelImpl[3];

			array[0] = getByUuid_PrevAndNext(session,
					cpDefinitionOptionValueRel, uuid, orderByComparator, true);

			array[1] = cpDefinitionOptionValueRel;

			array[2] = getByUuid_PrevAndNext(session,
					cpDefinitionOptionValueRel, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPDefinitionOptionValueRel getByUuid_PrevAndNext(
		Session session, CPDefinitionOptionValueRel cpDefinitionOptionValueRel,
		String uuid,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator,
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

		query.append(_SQL_SELECT_CPDEFINITIONOPTIONVALUEREL_WHERE);

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
			query.append(CPDefinitionOptionValueRelModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(cpDefinitionOptionValueRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPDefinitionOptionValueRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definition option value rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel : findByUuid(
				uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpDefinitionOptionValueRel);
		}
	}

	/**
	 * Returns the number of cp definition option value rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp definition option value rels
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CPDEFINITIONOPTIONVALUEREL_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "cpDefinitionOptionValueRel.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "cpDefinitionOptionValueRel.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(cpDefinitionOptionValueRel.uuid IS NULL OR cpDefinitionOptionValueRel.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(CPDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionValueRelModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionOptionValueRelImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			CPDefinitionOptionValueRelModelImpl.UUID_COLUMN_BITMASK |
			CPDefinitionOptionValueRelModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(CPDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionValueRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the cp definition option value rel where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPDefinitionOptionValueRelException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp definition option value rel
	 * @throws NoSuchCPDefinitionOptionValueRelException if a matching cp definition option value rel could not be found
	 */
	@Override
	public CPDefinitionOptionValueRel findByUUID_G(String uuid, long groupId)
		throws NoSuchCPDefinitionOptionValueRelException {
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel = fetchByUUID_G(uuid,
				groupId);

		if (cpDefinitionOptionValueRel == null) {
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

			throw new NoSuchCPDefinitionOptionValueRelException(msg.toString());
		}

		return cpDefinitionOptionValueRel;
	}

	/**
	 * Returns the cp definition option value rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp definition option value rel, or <code>null</code> if a matching cp definition option value rel could not be found
	 */
	@Override
	public CPDefinitionOptionValueRel fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the cp definition option value rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching cp definition option value rel, or <code>null</code> if a matching cp definition option value rel could not be found
	 */
	@Override
	public CPDefinitionOptionValueRel fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof CPDefinitionOptionValueRel) {
			CPDefinitionOptionValueRel cpDefinitionOptionValueRel = (CPDefinitionOptionValueRel)result;

			if (!Objects.equals(uuid, cpDefinitionOptionValueRel.getUuid()) ||
					(groupId != cpDefinitionOptionValueRel.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_CPDEFINITIONOPTIONVALUEREL_WHERE);

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

				List<CPDefinitionOptionValueRel> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					CPDefinitionOptionValueRel cpDefinitionOptionValueRel = list.get(0);

					result = cpDefinitionOptionValueRel;

					cacheResult(cpDefinitionOptionValueRel);

					if ((cpDefinitionOptionValueRel.getUuid() == null) ||
							!cpDefinitionOptionValueRel.getUuid().equals(uuid) ||
							(cpDefinitionOptionValueRel.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, cpDefinitionOptionValueRel);
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
			return (CPDefinitionOptionValueRel)result;
		}
	}

	/**
	 * Removes the cp definition option value rel where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the cp definition option value rel that was removed
	 */
	@Override
	public CPDefinitionOptionValueRel removeByUUID_G(String uuid, long groupId)
		throws NoSuchCPDefinitionOptionValueRelException {
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel = findByUUID_G(uuid,
				groupId);

		return remove(cpDefinitionOptionValueRel);
	}

	/**
	 * Returns the number of cp definition option value rels where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching cp definition option value rels
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CPDEFINITIONOPTIONVALUEREL_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "cpDefinitionOptionValueRel.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "cpDefinitionOptionValueRel.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(cpDefinitionOptionValueRel.uuid IS NULL OR cpDefinitionOptionValueRel.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "cpDefinitionOptionValueRel.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(CPDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionValueRelModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionOptionValueRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(CPDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionValueRelModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionOptionValueRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			CPDefinitionOptionValueRelModelImpl.UUID_COLUMN_BITMASK |
			CPDefinitionOptionValueRelModelImpl.COMPANYID_COLUMN_BITMASK |
			CPDefinitionOptionValueRelModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(CPDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionValueRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the cp definition option value rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp definition option value rels
	 */
	@Override
	public List<CPDefinitionOptionValueRel> findByUuid_C(String uuid,
		long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition option value rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition option value rels
	 * @param end the upper bound of the range of cp definition option value rels (not inclusive)
	 * @return the range of matching cp definition option value rels
	 */
	@Override
	public List<CPDefinitionOptionValueRel> findByUuid_C(String uuid,
		long companyId, int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition option value rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition option value rels
	 * @param end the upper bound of the range of cp definition option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition option value rels
	 */
	@Override
	public List<CPDefinitionOptionValueRel> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition option value rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition option value rels
	 * @param end the upper bound of the range of cp definition option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp definition option value rels
	 */
	@Override
	public List<CPDefinitionOptionValueRel> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator,
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

		List<CPDefinitionOptionValueRel> list = null;

		if (retrieveFromCache) {
			list = (List<CPDefinitionOptionValueRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel : list) {
					if (!Objects.equals(uuid,
								cpDefinitionOptionValueRel.getUuid()) ||
							(companyId != cpDefinitionOptionValueRel.getCompanyId())) {
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

			query.append(_SQL_SELECT_CPDEFINITIONOPTIONVALUEREL_WHERE);

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
				query.append(CPDefinitionOptionValueRelModelImpl.ORDER_BY_JPQL);
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
					list = (List<CPDefinitionOptionValueRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPDefinitionOptionValueRel>)QueryUtil.list(q,
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
	 * Returns the first cp definition option value rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition option value rel
	 * @throws NoSuchCPDefinitionOptionValueRelException if a matching cp definition option value rel could not be found
	 */
	@Override
	public CPDefinitionOptionValueRel findByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator)
		throws NoSuchCPDefinitionOptionValueRelException {
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel = fetchByUuid_C_First(uuid,
				companyId, orderByComparator);

		if (cpDefinitionOptionValueRel != null) {
			return cpDefinitionOptionValueRel;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchCPDefinitionOptionValueRelException(msg.toString());
	}

	/**
	 * Returns the first cp definition option value rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition option value rel, or <code>null</code> if a matching cp definition option value rel could not be found
	 */
	@Override
	public CPDefinitionOptionValueRel fetchByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator) {
		List<CPDefinitionOptionValueRel> list = findByUuid_C(uuid, companyId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition option value rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition option value rel
	 * @throws NoSuchCPDefinitionOptionValueRelException if a matching cp definition option value rel could not be found
	 */
	@Override
	public CPDefinitionOptionValueRel findByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator)
		throws NoSuchCPDefinitionOptionValueRelException {
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel = fetchByUuid_C_Last(uuid,
				companyId, orderByComparator);

		if (cpDefinitionOptionValueRel != null) {
			return cpDefinitionOptionValueRel;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchCPDefinitionOptionValueRelException(msg.toString());
	}

	/**
	 * Returns the last cp definition option value rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition option value rel, or <code>null</code> if a matching cp definition option value rel could not be found
	 */
	@Override
	public CPDefinitionOptionValueRel fetchByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionOptionValueRel> list = findByUuid_C(uuid, companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition option value rels before and after the current cp definition option value rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CPDefinitionOptionValueRelId the primary key of the current cp definition option value rel
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition option value rel
	 * @throws NoSuchCPDefinitionOptionValueRelException if a cp definition option value rel with the primary key could not be found
	 */
	@Override
	public CPDefinitionOptionValueRel[] findByUuid_C_PrevAndNext(
		long CPDefinitionOptionValueRelId, String uuid, long companyId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator)
		throws NoSuchCPDefinitionOptionValueRelException {
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel = findByPrimaryKey(CPDefinitionOptionValueRelId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionOptionValueRel[] array = new CPDefinitionOptionValueRelImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session,
					cpDefinitionOptionValueRel, uuid, companyId,
					orderByComparator, true);

			array[1] = cpDefinitionOptionValueRel;

			array[2] = getByUuid_C_PrevAndNext(session,
					cpDefinitionOptionValueRel, uuid, companyId,
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

	protected CPDefinitionOptionValueRel getByUuid_C_PrevAndNext(
		Session session, CPDefinitionOptionValueRel cpDefinitionOptionValueRel,
		String uuid, long companyId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator,
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

		query.append(_SQL_SELECT_CPDEFINITIONOPTIONVALUEREL_WHERE);

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
			query.append(CPDefinitionOptionValueRelModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(cpDefinitionOptionValueRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPDefinitionOptionValueRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definition option value rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel : findByUuid_C(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpDefinitionOptionValueRel);
		}
	}

	/**
	 * Returns the number of cp definition option value rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp definition option value rels
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CPDEFINITIONOPTIONVALUEREL_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "cpDefinitionOptionValueRel.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "cpDefinitionOptionValueRel.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(cpDefinitionOptionValueRel.uuid IS NULL OR cpDefinitionOptionValueRel.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "cpDefinitionOptionValueRel.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(CPDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionValueRelModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionOptionValueRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(CPDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionValueRelModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionOptionValueRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			CPDefinitionOptionValueRelModelImpl.GROUPID_COLUMN_BITMASK |
			CPDefinitionOptionValueRelModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(CPDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionValueRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId", new String[] { Long.class.getName() });

	/**
	 * Returns all the cp definition option value rels where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching cp definition option value rels
	 */
	@Override
	public List<CPDefinitionOptionValueRel> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition option value rels where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp definition option value rels
	 * @param end the upper bound of the range of cp definition option value rels (not inclusive)
	 * @return the range of matching cp definition option value rels
	 */
	@Override
	public List<CPDefinitionOptionValueRel> findByGroupId(long groupId,
		int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition option value rels where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp definition option value rels
	 * @param end the upper bound of the range of cp definition option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition option value rels
	 */
	@Override
	public List<CPDefinitionOptionValueRel> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition option value rels where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp definition option value rels
	 * @param end the upper bound of the range of cp definition option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp definition option value rels
	 */
	@Override
	public List<CPDefinitionOptionValueRel> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator,
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

		List<CPDefinitionOptionValueRel> list = null;

		if (retrieveFromCache) {
			list = (List<CPDefinitionOptionValueRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel : list) {
					if ((groupId != cpDefinitionOptionValueRel.getGroupId())) {
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

			query.append(_SQL_SELECT_CPDEFINITIONOPTIONVALUEREL_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CPDefinitionOptionValueRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<CPDefinitionOptionValueRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPDefinitionOptionValueRel>)QueryUtil.list(q,
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
	 * Returns the first cp definition option value rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition option value rel
	 * @throws NoSuchCPDefinitionOptionValueRelException if a matching cp definition option value rel could not be found
	 */
	@Override
	public CPDefinitionOptionValueRel findByGroupId_First(long groupId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator)
		throws NoSuchCPDefinitionOptionValueRelException {
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel = fetchByGroupId_First(groupId,
				orderByComparator);

		if (cpDefinitionOptionValueRel != null) {
			return cpDefinitionOptionValueRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchCPDefinitionOptionValueRelException(msg.toString());
	}

	/**
	 * Returns the first cp definition option value rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition option value rel, or <code>null</code> if a matching cp definition option value rel could not be found
	 */
	@Override
	public CPDefinitionOptionValueRel fetchByGroupId_First(long groupId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator) {
		List<CPDefinitionOptionValueRel> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition option value rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition option value rel
	 * @throws NoSuchCPDefinitionOptionValueRelException if a matching cp definition option value rel could not be found
	 */
	@Override
	public CPDefinitionOptionValueRel findByGroupId_Last(long groupId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator)
		throws NoSuchCPDefinitionOptionValueRelException {
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (cpDefinitionOptionValueRel != null) {
			return cpDefinitionOptionValueRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchCPDefinitionOptionValueRelException(msg.toString());
	}

	/**
	 * Returns the last cp definition option value rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition option value rel, or <code>null</code> if a matching cp definition option value rel could not be found
	 */
	@Override
	public CPDefinitionOptionValueRel fetchByGroupId_Last(long groupId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionOptionValueRel> list = findByGroupId(groupId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition option value rels before and after the current cp definition option value rel in the ordered set where groupId = &#63;.
	 *
	 * @param CPDefinitionOptionValueRelId the primary key of the current cp definition option value rel
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition option value rel
	 * @throws NoSuchCPDefinitionOptionValueRelException if a cp definition option value rel with the primary key could not be found
	 */
	@Override
	public CPDefinitionOptionValueRel[] findByGroupId_PrevAndNext(
		long CPDefinitionOptionValueRelId, long groupId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator)
		throws NoSuchCPDefinitionOptionValueRelException {
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel = findByPrimaryKey(CPDefinitionOptionValueRelId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionOptionValueRel[] array = new CPDefinitionOptionValueRelImpl[3];

			array[0] = getByGroupId_PrevAndNext(session,
					cpDefinitionOptionValueRel, groupId, orderByComparator, true);

			array[1] = cpDefinitionOptionValueRel;

			array[2] = getByGroupId_PrevAndNext(session,
					cpDefinitionOptionValueRel, groupId, orderByComparator,
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

	protected CPDefinitionOptionValueRel getByGroupId_PrevAndNext(
		Session session, CPDefinitionOptionValueRel cpDefinitionOptionValueRel,
		long groupId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator,
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

		query.append(_SQL_SELECT_CPDEFINITIONOPTIONVALUEREL_WHERE);

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
			query.append(CPDefinitionOptionValueRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(cpDefinitionOptionValueRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPDefinitionOptionValueRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definition option value rels where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel : findByGroupId(
				groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpDefinitionOptionValueRel);
		}
	}

	/**
	 * Returns the number of cp definition option value rels where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching cp definition option value rels
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CPDEFINITIONOPTIONVALUEREL_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "cpDefinitionOptionValueRel.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(CPDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionValueRelModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionOptionValueRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(CPDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionValueRelModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionOptionValueRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			CPDefinitionOptionValueRelModelImpl.COMPANYID_COLUMN_BITMASK |
			CPDefinitionOptionValueRelModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(CPDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionValueRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCompanyId", new String[] { Long.class.getName() });

	/**
	 * Returns all the cp definition option value rels where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching cp definition option value rels
	 */
	@Override
	public List<CPDefinitionOptionValueRel> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the cp definition option value rels where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition option value rels
	 * @param end the upper bound of the range of cp definition option value rels (not inclusive)
	 * @return the range of matching cp definition option value rels
	 */
	@Override
	public List<CPDefinitionOptionValueRel> findByCompanyId(long companyId,
		int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition option value rels where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition option value rels
	 * @param end the upper bound of the range of cp definition option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition option value rels
	 */
	@Override
	public List<CPDefinitionOptionValueRel> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator) {
		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition option value rels where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition option value rels
	 * @param end the upper bound of the range of cp definition option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp definition option value rels
	 */
	@Override
	public List<CPDefinitionOptionValueRel> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator,
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

		List<CPDefinitionOptionValueRel> list = null;

		if (retrieveFromCache) {
			list = (List<CPDefinitionOptionValueRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel : list) {
					if ((companyId != cpDefinitionOptionValueRel.getCompanyId())) {
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

			query.append(_SQL_SELECT_CPDEFINITIONOPTIONVALUEREL_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CPDefinitionOptionValueRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<CPDefinitionOptionValueRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPDefinitionOptionValueRel>)QueryUtil.list(q,
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
	 * Returns the first cp definition option value rel in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition option value rel
	 * @throws NoSuchCPDefinitionOptionValueRelException if a matching cp definition option value rel could not be found
	 */
	@Override
	public CPDefinitionOptionValueRel findByCompanyId_First(long companyId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator)
		throws NoSuchCPDefinitionOptionValueRelException {
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (cpDefinitionOptionValueRel != null) {
			return cpDefinitionOptionValueRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchCPDefinitionOptionValueRelException(msg.toString());
	}

	/**
	 * Returns the first cp definition option value rel in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition option value rel, or <code>null</code> if a matching cp definition option value rel could not be found
	 */
	@Override
	public CPDefinitionOptionValueRel fetchByCompanyId_First(long companyId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator) {
		List<CPDefinitionOptionValueRel> list = findByCompanyId(companyId, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition option value rel in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition option value rel
	 * @throws NoSuchCPDefinitionOptionValueRelException if a matching cp definition option value rel could not be found
	 */
	@Override
	public CPDefinitionOptionValueRel findByCompanyId_Last(long companyId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator)
		throws NoSuchCPDefinitionOptionValueRelException {
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (cpDefinitionOptionValueRel != null) {
			return cpDefinitionOptionValueRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchCPDefinitionOptionValueRelException(msg.toString());
	}

	/**
	 * Returns the last cp definition option value rel in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition option value rel, or <code>null</code> if a matching cp definition option value rel could not be found
	 */
	@Override
	public CPDefinitionOptionValueRel fetchByCompanyId_Last(long companyId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionOptionValueRel> list = findByCompanyId(companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition option value rels before and after the current cp definition option value rel in the ordered set where companyId = &#63;.
	 *
	 * @param CPDefinitionOptionValueRelId the primary key of the current cp definition option value rel
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition option value rel
	 * @throws NoSuchCPDefinitionOptionValueRelException if a cp definition option value rel with the primary key could not be found
	 */
	@Override
	public CPDefinitionOptionValueRel[] findByCompanyId_PrevAndNext(
		long CPDefinitionOptionValueRelId, long companyId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator)
		throws NoSuchCPDefinitionOptionValueRelException {
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel = findByPrimaryKey(CPDefinitionOptionValueRelId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionOptionValueRel[] array = new CPDefinitionOptionValueRelImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session,
					cpDefinitionOptionValueRel, companyId, orderByComparator,
					true);

			array[1] = cpDefinitionOptionValueRel;

			array[2] = getByCompanyId_PrevAndNext(session,
					cpDefinitionOptionValueRel, companyId, orderByComparator,
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

	protected CPDefinitionOptionValueRel getByCompanyId_PrevAndNext(
		Session session, CPDefinitionOptionValueRel cpDefinitionOptionValueRel,
		long companyId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator,
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

		query.append(_SQL_SELECT_CPDEFINITIONOPTIONVALUEREL_WHERE);

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
			query.append(CPDefinitionOptionValueRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(cpDefinitionOptionValueRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPDefinitionOptionValueRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definition option value rels where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel : findByCompanyId(
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpDefinitionOptionValueRel);
		}
	}

	/**
	 * Returns the number of cp definition option value rels where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching cp definition option value rels
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CPDEFINITIONOPTIONVALUEREL_WHERE);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "cpDefinitionOptionValueRel.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_CPDEFINITIONOPTIONRELID =
		new FinderPath(CPDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionValueRelModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionOptionValueRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCPDefinitionOptionRelId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONOPTIONRELID =
		new FinderPath(CPDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionValueRelModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionOptionValueRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCPDefinitionOptionRelId",
			new String[] { Long.class.getName() },
			CPDefinitionOptionValueRelModelImpl.CPDEFINITIONOPTIONRELID_COLUMN_BITMASK |
			CPDefinitionOptionValueRelModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CPDEFINITIONOPTIONRELID = new FinderPath(CPDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionValueRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCPDefinitionOptionRelId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the cp definition option value rels where CPDefinitionOptionRelId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @return the matching cp definition option value rels
	 */
	@Override
	public List<CPDefinitionOptionValueRel> findByCPDefinitionOptionRelId(
		long CPDefinitionOptionRelId) {
		return findByCPDefinitionOptionRelId(CPDefinitionOptionRelId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition option value rels where CPDefinitionOptionRelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param start the lower bound of the range of cp definition option value rels
	 * @param end the upper bound of the range of cp definition option value rels (not inclusive)
	 * @return the range of matching cp definition option value rels
	 */
	@Override
	public List<CPDefinitionOptionValueRel> findByCPDefinitionOptionRelId(
		long CPDefinitionOptionRelId, int start, int end) {
		return findByCPDefinitionOptionRelId(CPDefinitionOptionRelId, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition option value rels where CPDefinitionOptionRelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param start the lower bound of the range of cp definition option value rels
	 * @param end the upper bound of the range of cp definition option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition option value rels
	 */
	@Override
	public List<CPDefinitionOptionValueRel> findByCPDefinitionOptionRelId(
		long CPDefinitionOptionRelId, int start, int end,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator) {
		return findByCPDefinitionOptionRelId(CPDefinitionOptionRelId, start,
			end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition option value rels where CPDefinitionOptionRelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param start the lower bound of the range of cp definition option value rels
	 * @param end the upper bound of the range of cp definition option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp definition option value rels
	 */
	@Override
	public List<CPDefinitionOptionValueRel> findByCPDefinitionOptionRelId(
		long CPDefinitionOptionRelId, int start, int end,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONOPTIONRELID;
			finderArgs = new Object[] { CPDefinitionOptionRelId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_CPDEFINITIONOPTIONRELID;
			finderArgs = new Object[] {
					CPDefinitionOptionRelId,
					
					start, end, orderByComparator
				};
		}

		List<CPDefinitionOptionValueRel> list = null;

		if (retrieveFromCache) {
			list = (List<CPDefinitionOptionValueRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel : list) {
					if ((CPDefinitionOptionRelId != cpDefinitionOptionValueRel.getCPDefinitionOptionRelId())) {
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

			query.append(_SQL_SELECT_CPDEFINITIONOPTIONVALUEREL_WHERE);

			query.append(_FINDER_COLUMN_CPDEFINITIONOPTIONRELID_CPDEFINITIONOPTIONRELID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CPDefinitionOptionValueRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(CPDefinitionOptionRelId);

				if (!pagination) {
					list = (List<CPDefinitionOptionValueRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPDefinitionOptionValueRel>)QueryUtil.list(q,
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
	 * Returns the first cp definition option value rel in the ordered set where CPDefinitionOptionRelId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition option value rel
	 * @throws NoSuchCPDefinitionOptionValueRelException if a matching cp definition option value rel could not be found
	 */
	@Override
	public CPDefinitionOptionValueRel findByCPDefinitionOptionRelId_First(
		long CPDefinitionOptionRelId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator)
		throws NoSuchCPDefinitionOptionValueRelException {
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel = fetchByCPDefinitionOptionRelId_First(CPDefinitionOptionRelId,
				orderByComparator);

		if (cpDefinitionOptionValueRel != null) {
			return cpDefinitionOptionValueRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("CPDefinitionOptionRelId=");
		msg.append(CPDefinitionOptionRelId);

		msg.append("}");

		throw new NoSuchCPDefinitionOptionValueRelException(msg.toString());
	}

	/**
	 * Returns the first cp definition option value rel in the ordered set where CPDefinitionOptionRelId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition option value rel, or <code>null</code> if a matching cp definition option value rel could not be found
	 */
	@Override
	public CPDefinitionOptionValueRel fetchByCPDefinitionOptionRelId_First(
		long CPDefinitionOptionRelId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator) {
		List<CPDefinitionOptionValueRel> list = findByCPDefinitionOptionRelId(CPDefinitionOptionRelId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition option value rel in the ordered set where CPDefinitionOptionRelId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition option value rel
	 * @throws NoSuchCPDefinitionOptionValueRelException if a matching cp definition option value rel could not be found
	 */
	@Override
	public CPDefinitionOptionValueRel findByCPDefinitionOptionRelId_Last(
		long CPDefinitionOptionRelId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator)
		throws NoSuchCPDefinitionOptionValueRelException {
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel = fetchByCPDefinitionOptionRelId_Last(CPDefinitionOptionRelId,
				orderByComparator);

		if (cpDefinitionOptionValueRel != null) {
			return cpDefinitionOptionValueRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("CPDefinitionOptionRelId=");
		msg.append(CPDefinitionOptionRelId);

		msg.append("}");

		throw new NoSuchCPDefinitionOptionValueRelException(msg.toString());
	}

	/**
	 * Returns the last cp definition option value rel in the ordered set where CPDefinitionOptionRelId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition option value rel, or <code>null</code> if a matching cp definition option value rel could not be found
	 */
	@Override
	public CPDefinitionOptionValueRel fetchByCPDefinitionOptionRelId_Last(
		long CPDefinitionOptionRelId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator) {
		int count = countByCPDefinitionOptionRelId(CPDefinitionOptionRelId);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionOptionValueRel> list = findByCPDefinitionOptionRelId(CPDefinitionOptionRelId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition option value rels before and after the current cp definition option value rel in the ordered set where CPDefinitionOptionRelId = &#63;.
	 *
	 * @param CPDefinitionOptionValueRelId the primary key of the current cp definition option value rel
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition option value rel
	 * @throws NoSuchCPDefinitionOptionValueRelException if a cp definition option value rel with the primary key could not be found
	 */
	@Override
	public CPDefinitionOptionValueRel[] findByCPDefinitionOptionRelId_PrevAndNext(
		long CPDefinitionOptionValueRelId, long CPDefinitionOptionRelId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator)
		throws NoSuchCPDefinitionOptionValueRelException {
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel = findByPrimaryKey(CPDefinitionOptionValueRelId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionOptionValueRel[] array = new CPDefinitionOptionValueRelImpl[3];

			array[0] = getByCPDefinitionOptionRelId_PrevAndNext(session,
					cpDefinitionOptionValueRel, CPDefinitionOptionRelId,
					orderByComparator, true);

			array[1] = cpDefinitionOptionValueRel;

			array[2] = getByCPDefinitionOptionRelId_PrevAndNext(session,
					cpDefinitionOptionValueRel, CPDefinitionOptionRelId,
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

	protected CPDefinitionOptionValueRel getByCPDefinitionOptionRelId_PrevAndNext(
		Session session, CPDefinitionOptionValueRel cpDefinitionOptionValueRel,
		long CPDefinitionOptionRelId,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator,
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

		query.append(_SQL_SELECT_CPDEFINITIONOPTIONVALUEREL_WHERE);

		query.append(_FINDER_COLUMN_CPDEFINITIONOPTIONRELID_CPDEFINITIONOPTIONRELID_2);

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
			query.append(CPDefinitionOptionValueRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(CPDefinitionOptionRelId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(cpDefinitionOptionValueRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPDefinitionOptionValueRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definition option value rels where CPDefinitionOptionRelId = &#63; from the database.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 */
	@Override
	public void removeByCPDefinitionOptionRelId(long CPDefinitionOptionRelId) {
		for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel : findByCPDefinitionOptionRelId(
				CPDefinitionOptionRelId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null)) {
			remove(cpDefinitionOptionValueRel);
		}
	}

	/**
	 * Returns the number of cp definition option value rels where CPDefinitionOptionRelId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @return the number of matching cp definition option value rels
	 */
	@Override
	public int countByCPDefinitionOptionRelId(long CPDefinitionOptionRelId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_CPDEFINITIONOPTIONRELID;

		Object[] finderArgs = new Object[] { CPDefinitionOptionRelId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CPDEFINITIONOPTIONVALUEREL_WHERE);

			query.append(_FINDER_COLUMN_CPDEFINITIONOPTIONRELID_CPDEFINITIONOPTIONRELID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(CPDefinitionOptionRelId);

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

	private static final String _FINDER_COLUMN_CPDEFINITIONOPTIONRELID_CPDEFINITIONOPTIONRELID_2 =
		"cpDefinitionOptionValueRel.CPDefinitionOptionRelId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_KEY = new FinderPath(CPDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionValueRelModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionOptionValueRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findBykey",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_KEY = new FinderPath(CPDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionValueRelModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionOptionValueRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findBykey",
			new String[] { String.class.getName() },
			CPDefinitionOptionValueRelModelImpl.KEY_COLUMN_BITMASK |
			CPDefinitionOptionValueRelModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_KEY = new FinderPath(CPDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionValueRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countBykey", new String[] { String.class.getName() });

	/**
	 * Returns all the cp definition option value rels where key = &#63;.
	 *
	 * @param key the key
	 * @return the matching cp definition option value rels
	 */
	@Override
	public List<CPDefinitionOptionValueRel> findBykey(String key) {
		return findBykey(key, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition option value rels where key = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param key the key
	 * @param start the lower bound of the range of cp definition option value rels
	 * @param end the upper bound of the range of cp definition option value rels (not inclusive)
	 * @return the range of matching cp definition option value rels
	 */
	@Override
	public List<CPDefinitionOptionValueRel> findBykey(String key, int start,
		int end) {
		return findBykey(key, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition option value rels where key = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param key the key
	 * @param start the lower bound of the range of cp definition option value rels
	 * @param end the upper bound of the range of cp definition option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition option value rels
	 */
	@Override
	public List<CPDefinitionOptionValueRel> findBykey(String key, int start,
		int end, OrderByComparator<CPDefinitionOptionValueRel> orderByComparator) {
		return findBykey(key, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition option value rels where key = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param key the key
	 * @param start the lower bound of the range of cp definition option value rels
	 * @param end the upper bound of the range of cp definition option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp definition option value rels
	 */
	@Override
	public List<CPDefinitionOptionValueRel> findBykey(String key, int start,
		int end,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_KEY;
			finderArgs = new Object[] { key };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_KEY;
			finderArgs = new Object[] { key, start, end, orderByComparator };
		}

		List<CPDefinitionOptionValueRel> list = null;

		if (retrieveFromCache) {
			list = (List<CPDefinitionOptionValueRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel : list) {
					if (!Objects.equals(key, cpDefinitionOptionValueRel.getKey())) {
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

			query.append(_SQL_SELECT_CPDEFINITIONOPTIONVALUEREL_WHERE);

			boolean bindKey = false;

			if (key == null) {
				query.append(_FINDER_COLUMN_KEY_KEY_1);
			}
			else if (key.equals("")) {
				query.append(_FINDER_COLUMN_KEY_KEY_3);
			}
			else {
				bindKey = true;

				query.append(_FINDER_COLUMN_KEY_KEY_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CPDefinitionOptionValueRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindKey) {
					qPos.add(key);
				}

				if (!pagination) {
					list = (List<CPDefinitionOptionValueRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPDefinitionOptionValueRel>)QueryUtil.list(q,
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
	 * Returns the first cp definition option value rel in the ordered set where key = &#63;.
	 *
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition option value rel
	 * @throws NoSuchCPDefinitionOptionValueRelException if a matching cp definition option value rel could not be found
	 */
	@Override
	public CPDefinitionOptionValueRel findBykey_First(String key,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator)
		throws NoSuchCPDefinitionOptionValueRelException {
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel = fetchBykey_First(key,
				orderByComparator);

		if (cpDefinitionOptionValueRel != null) {
			return cpDefinitionOptionValueRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("key=");
		msg.append(key);

		msg.append("}");

		throw new NoSuchCPDefinitionOptionValueRelException(msg.toString());
	}

	/**
	 * Returns the first cp definition option value rel in the ordered set where key = &#63;.
	 *
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition option value rel, or <code>null</code> if a matching cp definition option value rel could not be found
	 */
	@Override
	public CPDefinitionOptionValueRel fetchBykey_First(String key,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator) {
		List<CPDefinitionOptionValueRel> list = findBykey(key, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition option value rel in the ordered set where key = &#63;.
	 *
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition option value rel
	 * @throws NoSuchCPDefinitionOptionValueRelException if a matching cp definition option value rel could not be found
	 */
	@Override
	public CPDefinitionOptionValueRel findBykey_Last(String key,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator)
		throws NoSuchCPDefinitionOptionValueRelException {
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel = fetchBykey_Last(key,
				orderByComparator);

		if (cpDefinitionOptionValueRel != null) {
			return cpDefinitionOptionValueRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("key=");
		msg.append(key);

		msg.append("}");

		throw new NoSuchCPDefinitionOptionValueRelException(msg.toString());
	}

	/**
	 * Returns the last cp definition option value rel in the ordered set where key = &#63;.
	 *
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition option value rel, or <code>null</code> if a matching cp definition option value rel could not be found
	 */
	@Override
	public CPDefinitionOptionValueRel fetchBykey_Last(String key,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator) {
		int count = countBykey(key);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionOptionValueRel> list = findBykey(key, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition option value rels before and after the current cp definition option value rel in the ordered set where key = &#63;.
	 *
	 * @param CPDefinitionOptionValueRelId the primary key of the current cp definition option value rel
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition option value rel
	 * @throws NoSuchCPDefinitionOptionValueRelException if a cp definition option value rel with the primary key could not be found
	 */
	@Override
	public CPDefinitionOptionValueRel[] findBykey_PrevAndNext(
		long CPDefinitionOptionValueRelId, String key,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator)
		throws NoSuchCPDefinitionOptionValueRelException {
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel = findByPrimaryKey(CPDefinitionOptionValueRelId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionOptionValueRel[] array = new CPDefinitionOptionValueRelImpl[3];

			array[0] = getBykey_PrevAndNext(session,
					cpDefinitionOptionValueRel, key, orderByComparator, true);

			array[1] = cpDefinitionOptionValueRel;

			array[2] = getBykey_PrevAndNext(session,
					cpDefinitionOptionValueRel, key, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPDefinitionOptionValueRel getBykey_PrevAndNext(Session session,
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel, String key,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator,
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

		query.append(_SQL_SELECT_CPDEFINITIONOPTIONVALUEREL_WHERE);

		boolean bindKey = false;

		if (key == null) {
			query.append(_FINDER_COLUMN_KEY_KEY_1);
		}
		else if (key.equals("")) {
			query.append(_FINDER_COLUMN_KEY_KEY_3);
		}
		else {
			bindKey = true;

			query.append(_FINDER_COLUMN_KEY_KEY_2);
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
			query.append(CPDefinitionOptionValueRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindKey) {
			qPos.add(key);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(cpDefinitionOptionValueRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPDefinitionOptionValueRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definition option value rels where key = &#63; from the database.
	 *
	 * @param key the key
	 */
	@Override
	public void removeBykey(String key) {
		for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel : findBykey(
				key, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpDefinitionOptionValueRel);
		}
	}

	/**
	 * Returns the number of cp definition option value rels where key = &#63;.
	 *
	 * @param key the key
	 * @return the number of matching cp definition option value rels
	 */
	@Override
	public int countBykey(String key) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_KEY;

		Object[] finderArgs = new Object[] { key };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CPDEFINITIONOPTIONVALUEREL_WHERE);

			boolean bindKey = false;

			if (key == null) {
				query.append(_FINDER_COLUMN_KEY_KEY_1);
			}
			else if (key.equals("")) {
				query.append(_FINDER_COLUMN_KEY_KEY_3);
			}
			else {
				bindKey = true;

				query.append(_FINDER_COLUMN_KEY_KEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindKey) {
					qPos.add(key);
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

	private static final String _FINDER_COLUMN_KEY_KEY_1 = "cpDefinitionOptionValueRel.key IS NULL";
	private static final String _FINDER_COLUMN_KEY_KEY_2 = "cpDefinitionOptionValueRel.key = ?";
	private static final String _FINDER_COLUMN_KEY_KEY_3 = "(cpDefinitionOptionValueRel.key IS NULL OR cpDefinitionOptionValueRel.key = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_K = new FinderPath(CPDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionValueRelModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionOptionValueRelImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_K",
			new String[] { Long.class.getName(), String.class.getName() },
			CPDefinitionOptionValueRelModelImpl.CPDEFINITIONOPTIONRELID_COLUMN_BITMASK |
			CPDefinitionOptionValueRelModelImpl.KEY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_K = new FinderPath(CPDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionValueRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByC_K",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the cp definition option value rel where CPDefinitionOptionRelId = &#63; and key = &#63; or throws a {@link NoSuchCPDefinitionOptionValueRelException} if it could not be found.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param key the key
	 * @return the matching cp definition option value rel
	 * @throws NoSuchCPDefinitionOptionValueRelException if a matching cp definition option value rel could not be found
	 */
	@Override
	public CPDefinitionOptionValueRel findByC_K(long CPDefinitionOptionRelId,
		String key) throws NoSuchCPDefinitionOptionValueRelException {
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel = fetchByC_K(CPDefinitionOptionRelId,
				key);

		if (cpDefinitionOptionValueRel == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("CPDefinitionOptionRelId=");
			msg.append(CPDefinitionOptionRelId);

			msg.append(", key=");
			msg.append(key);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchCPDefinitionOptionValueRelException(msg.toString());
		}

		return cpDefinitionOptionValueRel;
	}

	/**
	 * Returns the cp definition option value rel where CPDefinitionOptionRelId = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param key the key
	 * @return the matching cp definition option value rel, or <code>null</code> if a matching cp definition option value rel could not be found
	 */
	@Override
	public CPDefinitionOptionValueRel fetchByC_K(long CPDefinitionOptionRelId,
		String key) {
		return fetchByC_K(CPDefinitionOptionRelId, key, true);
	}

	/**
	 * Returns the cp definition option value rel where CPDefinitionOptionRelId = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param key the key
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching cp definition option value rel, or <code>null</code> if a matching cp definition option value rel could not be found
	 */
	@Override
	public CPDefinitionOptionValueRel fetchByC_K(long CPDefinitionOptionRelId,
		String key, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { CPDefinitionOptionRelId, key };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_K,
					finderArgs, this);
		}

		if (result instanceof CPDefinitionOptionValueRel) {
			CPDefinitionOptionValueRel cpDefinitionOptionValueRel = (CPDefinitionOptionValueRel)result;

			if ((CPDefinitionOptionRelId != cpDefinitionOptionValueRel.getCPDefinitionOptionRelId()) ||
					!Objects.equals(key, cpDefinitionOptionValueRel.getKey())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_CPDEFINITIONOPTIONVALUEREL_WHERE);

			query.append(_FINDER_COLUMN_C_K_CPDEFINITIONOPTIONRELID_2);

			boolean bindKey = false;

			if (key == null) {
				query.append(_FINDER_COLUMN_C_K_KEY_1);
			}
			else if (key.equals("")) {
				query.append(_FINDER_COLUMN_C_K_KEY_3);
			}
			else {
				bindKey = true;

				query.append(_FINDER_COLUMN_C_K_KEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(CPDefinitionOptionRelId);

				if (bindKey) {
					qPos.add(key);
				}

				List<CPDefinitionOptionValueRel> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_K, finderArgs,
						list);
				}
				else {
					CPDefinitionOptionValueRel cpDefinitionOptionValueRel = list.get(0);

					result = cpDefinitionOptionValueRel;

					cacheResult(cpDefinitionOptionValueRel);

					if ((cpDefinitionOptionValueRel.getCPDefinitionOptionRelId() != CPDefinitionOptionRelId) ||
							(cpDefinitionOptionValueRel.getKey() == null) ||
							!cpDefinitionOptionValueRel.getKey().equals(key)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_C_K,
							finderArgs, cpDefinitionOptionValueRel);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C_K, finderArgs);

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
			return (CPDefinitionOptionValueRel)result;
		}
	}

	/**
	 * Removes the cp definition option value rel where CPDefinitionOptionRelId = &#63; and key = &#63; from the database.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param key the key
	 * @return the cp definition option value rel that was removed
	 */
	@Override
	public CPDefinitionOptionValueRel removeByC_K(
		long CPDefinitionOptionRelId, String key)
		throws NoSuchCPDefinitionOptionValueRelException {
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel = findByC_K(CPDefinitionOptionRelId,
				key);

		return remove(cpDefinitionOptionValueRel);
	}

	/**
	 * Returns the number of cp definition option value rels where CPDefinitionOptionRelId = &#63; and key = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param key the key
	 * @return the number of matching cp definition option value rels
	 */
	@Override
	public int countByC_K(long CPDefinitionOptionRelId, String key) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_K;

		Object[] finderArgs = new Object[] { CPDefinitionOptionRelId, key };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CPDEFINITIONOPTIONVALUEREL_WHERE);

			query.append(_FINDER_COLUMN_C_K_CPDEFINITIONOPTIONRELID_2);

			boolean bindKey = false;

			if (key == null) {
				query.append(_FINDER_COLUMN_C_K_KEY_1);
			}
			else if (key.equals("")) {
				query.append(_FINDER_COLUMN_C_K_KEY_3);
			}
			else {
				bindKey = true;

				query.append(_FINDER_COLUMN_C_K_KEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(CPDefinitionOptionRelId);

				if (bindKey) {
					qPos.add(key);
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

	private static final String _FINDER_COLUMN_C_K_CPDEFINITIONOPTIONRELID_2 = "cpDefinitionOptionValueRel.CPDefinitionOptionRelId = ? AND ";
	private static final String _FINDER_COLUMN_C_K_KEY_1 = "cpDefinitionOptionValueRel.key IS NULL";
	private static final String _FINDER_COLUMN_C_K_KEY_2 = "cpDefinitionOptionValueRel.key = ?";
	private static final String _FINDER_COLUMN_C_K_KEY_3 = "(cpDefinitionOptionValueRel.key IS NULL OR cpDefinitionOptionValueRel.key = '')";

	public CPDefinitionOptionValueRelPersistenceImpl() {
		setModelClass(CPDefinitionOptionValueRel.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("uuid", "uuid_");
			dbColumnNames.put("key", "key_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the cp definition option value rel in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionOptionValueRel the cp definition option value rel
	 */
	@Override
	public void cacheResult(
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel) {
		entityCache.putResult(CPDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionValueRelImpl.class,
			cpDefinitionOptionValueRel.getPrimaryKey(),
			cpDefinitionOptionValueRel);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				cpDefinitionOptionValueRel.getUuid(),
				cpDefinitionOptionValueRel.getGroupId()
			}, cpDefinitionOptionValueRel);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_K,
			new Object[] {
				cpDefinitionOptionValueRel.getCPDefinitionOptionRelId(),
				cpDefinitionOptionValueRel.getKey()
			}, cpDefinitionOptionValueRel);

		cpDefinitionOptionValueRel.resetOriginalValues();
	}

	/**
	 * Caches the cp definition option value rels in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionOptionValueRels the cp definition option value rels
	 */
	@Override
	public void cacheResult(
		List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels) {
		for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel : cpDefinitionOptionValueRels) {
			if (entityCache.getResult(
						CPDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
						CPDefinitionOptionValueRelImpl.class,
						cpDefinitionOptionValueRel.getPrimaryKey()) == null) {
				cacheResult(cpDefinitionOptionValueRel);
			}
			else {
				cpDefinitionOptionValueRel.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all cp definition option value rels.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CPDefinitionOptionValueRelImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the cp definition option value rel.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel) {
		entityCache.removeResult(CPDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionValueRelImpl.class,
			cpDefinitionOptionValueRel.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CPDefinitionOptionValueRelModelImpl)cpDefinitionOptionValueRel,
			true);
	}

	@Override
	public void clearCache(
		List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel : cpDefinitionOptionValueRels) {
			entityCache.removeResult(CPDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
				CPDefinitionOptionValueRelImpl.class,
				cpDefinitionOptionValueRel.getPrimaryKey());

			clearUniqueFindersCache((CPDefinitionOptionValueRelModelImpl)cpDefinitionOptionValueRel,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		CPDefinitionOptionValueRelModelImpl cpDefinitionOptionValueRelModelImpl) {
		Object[] args = new Object[] {
				cpDefinitionOptionValueRelModelImpl.getUuid(),
				cpDefinitionOptionValueRelModelImpl.getGroupId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
			cpDefinitionOptionValueRelModelImpl, false);

		args = new Object[] {
				cpDefinitionOptionValueRelModelImpl.getCPDefinitionOptionRelId(),
				cpDefinitionOptionValueRelModelImpl.getKey()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_C_K, args, Long.valueOf(1),
			false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_C_K, args,
			cpDefinitionOptionValueRelModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CPDefinitionOptionValueRelModelImpl cpDefinitionOptionValueRelModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					cpDefinitionOptionValueRelModelImpl.getUuid(),
					cpDefinitionOptionValueRelModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if ((cpDefinitionOptionValueRelModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					cpDefinitionOptionValueRelModelImpl.getOriginalUuid(),
					cpDefinitionOptionValueRelModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					cpDefinitionOptionValueRelModelImpl.getCPDefinitionOptionRelId(),
					cpDefinitionOptionValueRelModelImpl.getKey()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_K, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_K, args);
		}

		if ((cpDefinitionOptionValueRelModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_K.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					cpDefinitionOptionValueRelModelImpl.getOriginalCPDefinitionOptionRelId(),
					cpDefinitionOptionValueRelModelImpl.getOriginalKey()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_K, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_K, args);
		}
	}

	/**
	 * Creates a new cp definition option value rel with the primary key. Does not add the cp definition option value rel to the database.
	 *
	 * @param CPDefinitionOptionValueRelId the primary key for the new cp definition option value rel
	 * @return the new cp definition option value rel
	 */
	@Override
	public CPDefinitionOptionValueRel create(long CPDefinitionOptionValueRelId) {
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel = new CPDefinitionOptionValueRelImpl();

		cpDefinitionOptionValueRel.setNew(true);
		cpDefinitionOptionValueRel.setPrimaryKey(CPDefinitionOptionValueRelId);

		String uuid = PortalUUIDUtil.generate();

		cpDefinitionOptionValueRel.setUuid(uuid);

		cpDefinitionOptionValueRel.setCompanyId(companyProvider.getCompanyId());

		return cpDefinitionOptionValueRel;
	}

	/**
	 * Removes the cp definition option value rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPDefinitionOptionValueRelId the primary key of the cp definition option value rel
	 * @return the cp definition option value rel that was removed
	 * @throws NoSuchCPDefinitionOptionValueRelException if a cp definition option value rel with the primary key could not be found
	 */
	@Override
	public CPDefinitionOptionValueRel remove(long CPDefinitionOptionValueRelId)
		throws NoSuchCPDefinitionOptionValueRelException {
		return remove((Serializable)CPDefinitionOptionValueRelId);
	}

	/**
	 * Removes the cp definition option value rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cp definition option value rel
	 * @return the cp definition option value rel that was removed
	 * @throws NoSuchCPDefinitionOptionValueRelException if a cp definition option value rel with the primary key could not be found
	 */
	@Override
	public CPDefinitionOptionValueRel remove(Serializable primaryKey)
		throws NoSuchCPDefinitionOptionValueRelException {
		Session session = null;

		try {
			session = openSession();

			CPDefinitionOptionValueRel cpDefinitionOptionValueRel = (CPDefinitionOptionValueRel)session.get(CPDefinitionOptionValueRelImpl.class,
					primaryKey);

			if (cpDefinitionOptionValueRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCPDefinitionOptionValueRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(cpDefinitionOptionValueRel);
		}
		catch (NoSuchCPDefinitionOptionValueRelException nsee) {
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
	protected CPDefinitionOptionValueRel removeImpl(
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpDefinitionOptionValueRel)) {
				cpDefinitionOptionValueRel = (CPDefinitionOptionValueRel)session.get(CPDefinitionOptionValueRelImpl.class,
						cpDefinitionOptionValueRel.getPrimaryKeyObj());
			}

			if (cpDefinitionOptionValueRel != null) {
				session.delete(cpDefinitionOptionValueRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (cpDefinitionOptionValueRel != null) {
			clearCache(cpDefinitionOptionValueRel);
		}

		return cpDefinitionOptionValueRel;
	}

	@Override
	public CPDefinitionOptionValueRel updateImpl(
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel) {
		boolean isNew = cpDefinitionOptionValueRel.isNew();

		if (!(cpDefinitionOptionValueRel instanceof CPDefinitionOptionValueRelModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(cpDefinitionOptionValueRel.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(cpDefinitionOptionValueRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in cpDefinitionOptionValueRel proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CPDefinitionOptionValueRel implementation " +
				cpDefinitionOptionValueRel.getClass());
		}

		CPDefinitionOptionValueRelModelImpl cpDefinitionOptionValueRelModelImpl = (CPDefinitionOptionValueRelModelImpl)cpDefinitionOptionValueRel;

		if (Validator.isNull(cpDefinitionOptionValueRel.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			cpDefinitionOptionValueRel.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (cpDefinitionOptionValueRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				cpDefinitionOptionValueRel.setCreateDate(now);
			}
			else {
				cpDefinitionOptionValueRel.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!cpDefinitionOptionValueRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cpDefinitionOptionValueRel.setModifiedDate(now);
			}
			else {
				cpDefinitionOptionValueRel.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (cpDefinitionOptionValueRel.isNew()) {
				session.save(cpDefinitionOptionValueRel);

				cpDefinitionOptionValueRel.setNew(false);
			}
			else {
				cpDefinitionOptionValueRel = (CPDefinitionOptionValueRel)session.merge(cpDefinitionOptionValueRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CPDefinitionOptionValueRelModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					cpDefinitionOptionValueRelModelImpl.getUuid()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
				args);

			args = new Object[] {
					cpDefinitionOptionValueRelModelImpl.getUuid(),
					cpDefinitionOptionValueRelModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
				args);

			args = new Object[] { cpDefinitionOptionValueRelModelImpl.getGroupId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			args = new Object[] {
					cpDefinitionOptionValueRelModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
				args);

			args = new Object[] {
					cpDefinitionOptionValueRelModelImpl.getCPDefinitionOptionRelId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_CPDEFINITIONOPTIONRELID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONOPTIONRELID,
				args);

			args = new Object[] { cpDefinitionOptionValueRelModelImpl.getKey() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_KEY, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_KEY,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((cpDefinitionOptionValueRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpDefinitionOptionValueRelModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] {
						cpDefinitionOptionValueRelModelImpl.getUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((cpDefinitionOptionValueRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpDefinitionOptionValueRelModelImpl.getOriginalUuid(),
						cpDefinitionOptionValueRelModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						cpDefinitionOptionValueRelModelImpl.getUuid(),
						cpDefinitionOptionValueRelModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((cpDefinitionOptionValueRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpDefinitionOptionValueRelModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						cpDefinitionOptionValueRelModelImpl.getGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((cpDefinitionOptionValueRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpDefinitionOptionValueRelModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] {
						cpDefinitionOptionValueRelModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((cpDefinitionOptionValueRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONOPTIONRELID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpDefinitionOptionValueRelModelImpl.getOriginalCPDefinitionOptionRelId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CPDEFINITIONOPTIONRELID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONOPTIONRELID,
					args);

				args = new Object[] {
						cpDefinitionOptionValueRelModelImpl.getCPDefinitionOptionRelId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CPDEFINITIONOPTIONRELID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONOPTIONRELID,
					args);
			}

			if ((cpDefinitionOptionValueRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_KEY.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpDefinitionOptionValueRelModelImpl.getOriginalKey()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_KEY, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_KEY,
					args);

				args = new Object[] { cpDefinitionOptionValueRelModelImpl.getKey() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_KEY, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_KEY,
					args);
			}
		}

		entityCache.putResult(CPDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionOptionValueRelImpl.class,
			cpDefinitionOptionValueRel.getPrimaryKey(),
			cpDefinitionOptionValueRel, false);

		clearUniqueFindersCache(cpDefinitionOptionValueRelModelImpl, false);
		cacheUniqueFindersCache(cpDefinitionOptionValueRelModelImpl);

		cpDefinitionOptionValueRel.resetOriginalValues();

		return cpDefinitionOptionValueRel;
	}

	/**
	 * Returns the cp definition option value rel with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp definition option value rel
	 * @return the cp definition option value rel
	 * @throws NoSuchCPDefinitionOptionValueRelException if a cp definition option value rel with the primary key could not be found
	 */
	@Override
	public CPDefinitionOptionValueRel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCPDefinitionOptionValueRelException {
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel = fetchByPrimaryKey(primaryKey);

		if (cpDefinitionOptionValueRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCPDefinitionOptionValueRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return cpDefinitionOptionValueRel;
	}

	/**
	 * Returns the cp definition option value rel with the primary key or throws a {@link NoSuchCPDefinitionOptionValueRelException} if it could not be found.
	 *
	 * @param CPDefinitionOptionValueRelId the primary key of the cp definition option value rel
	 * @return the cp definition option value rel
	 * @throws NoSuchCPDefinitionOptionValueRelException if a cp definition option value rel with the primary key could not be found
	 */
	@Override
	public CPDefinitionOptionValueRel findByPrimaryKey(
		long CPDefinitionOptionValueRelId)
		throws NoSuchCPDefinitionOptionValueRelException {
		return findByPrimaryKey((Serializable)CPDefinitionOptionValueRelId);
	}

	/**
	 * Returns the cp definition option value rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp definition option value rel
	 * @return the cp definition option value rel, or <code>null</code> if a cp definition option value rel with the primary key could not be found
	 */
	@Override
	public CPDefinitionOptionValueRel fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CPDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
				CPDefinitionOptionValueRelImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CPDefinitionOptionValueRel cpDefinitionOptionValueRel = (CPDefinitionOptionValueRel)serializable;

		if (cpDefinitionOptionValueRel == null) {
			Session session = null;

			try {
				session = openSession();

				cpDefinitionOptionValueRel = (CPDefinitionOptionValueRel)session.get(CPDefinitionOptionValueRelImpl.class,
						primaryKey);

				if (cpDefinitionOptionValueRel != null) {
					cacheResult(cpDefinitionOptionValueRel);
				}
				else {
					entityCache.putResult(CPDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
						CPDefinitionOptionValueRelImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CPDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
					CPDefinitionOptionValueRelImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return cpDefinitionOptionValueRel;
	}

	/**
	 * Returns the cp definition option value rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPDefinitionOptionValueRelId the primary key of the cp definition option value rel
	 * @return the cp definition option value rel, or <code>null</code> if a cp definition option value rel with the primary key could not be found
	 */
	@Override
	public CPDefinitionOptionValueRel fetchByPrimaryKey(
		long CPDefinitionOptionValueRelId) {
		return fetchByPrimaryKey((Serializable)CPDefinitionOptionValueRelId);
	}

	@Override
	public Map<Serializable, CPDefinitionOptionValueRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CPDefinitionOptionValueRel> map = new HashMap<Serializable, CPDefinitionOptionValueRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CPDefinitionOptionValueRel cpDefinitionOptionValueRel = fetchByPrimaryKey(primaryKey);

			if (cpDefinitionOptionValueRel != null) {
				map.put(primaryKey, cpDefinitionOptionValueRel);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CPDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
					CPDefinitionOptionValueRelImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CPDefinitionOptionValueRel)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_CPDEFINITIONOPTIONVALUEREL_WHERE_PKS_IN);

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

			for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel : (List<CPDefinitionOptionValueRel>)q.list()) {
				map.put(cpDefinitionOptionValueRel.getPrimaryKeyObj(),
					cpDefinitionOptionValueRel);

				cacheResult(cpDefinitionOptionValueRel);

				uncachedPrimaryKeys.remove(cpDefinitionOptionValueRel.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CPDefinitionOptionValueRelModelImpl.ENTITY_CACHE_ENABLED,
					CPDefinitionOptionValueRelImpl.class, primaryKey, nullModel);
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
	 * Returns all the cp definition option value rels.
	 *
	 * @return the cp definition option value rels
	 */
	@Override
	public List<CPDefinitionOptionValueRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition option value rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition option value rels
	 * @param end the upper bound of the range of cp definition option value rels (not inclusive)
	 * @return the range of cp definition option value rels
	 */
	@Override
	public List<CPDefinitionOptionValueRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition option value rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition option value rels
	 * @param end the upper bound of the range of cp definition option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp definition option value rels
	 */
	@Override
	public List<CPDefinitionOptionValueRel> findAll(int start, int end,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition option value rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition option value rels
	 * @param end the upper bound of the range of cp definition option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of cp definition option value rels
	 */
	@Override
	public List<CPDefinitionOptionValueRel> findAll(int start, int end,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator,
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

		List<CPDefinitionOptionValueRel> list = null;

		if (retrieveFromCache) {
			list = (List<CPDefinitionOptionValueRel>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CPDEFINITIONOPTIONVALUEREL);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CPDEFINITIONOPTIONVALUEREL;

				if (pagination) {
					sql = sql.concat(CPDefinitionOptionValueRelModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CPDefinitionOptionValueRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPDefinitionOptionValueRel>)QueryUtil.list(q,
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
	 * Removes all the cp definition option value rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel : findAll()) {
			remove(cpDefinitionOptionValueRel);
		}
	}

	/**
	 * Returns the number of cp definition option value rels.
	 *
	 * @return the number of cp definition option value rels
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CPDEFINITIONOPTIONVALUEREL);

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
		return CPDefinitionOptionValueRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the cp definition option value rel persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CPDefinitionOptionValueRelImpl.class.getName());
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
	private static final String _SQL_SELECT_CPDEFINITIONOPTIONVALUEREL = "SELECT cpDefinitionOptionValueRel FROM CPDefinitionOptionValueRel cpDefinitionOptionValueRel";
	private static final String _SQL_SELECT_CPDEFINITIONOPTIONVALUEREL_WHERE_PKS_IN =
		"SELECT cpDefinitionOptionValueRel FROM CPDefinitionOptionValueRel cpDefinitionOptionValueRel WHERE CPDefinitionOptionValueRelId IN (";
	private static final String _SQL_SELECT_CPDEFINITIONOPTIONVALUEREL_WHERE = "SELECT cpDefinitionOptionValueRel FROM CPDefinitionOptionValueRel cpDefinitionOptionValueRel WHERE ";
	private static final String _SQL_COUNT_CPDEFINITIONOPTIONVALUEREL = "SELECT COUNT(cpDefinitionOptionValueRel) FROM CPDefinitionOptionValueRel cpDefinitionOptionValueRel";
	private static final String _SQL_COUNT_CPDEFINITIONOPTIONVALUEREL_WHERE = "SELECT COUNT(cpDefinitionOptionValueRel) FROM CPDefinitionOptionValueRel cpDefinitionOptionValueRel WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "cpDefinitionOptionValueRel.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CPDefinitionOptionValueRel exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CPDefinitionOptionValueRel exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CPDefinitionOptionValueRelPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid", "key"
			});
}