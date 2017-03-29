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

package com.liferay.commerce.products.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.products.exception.NoSuchProductDefinitionException;
import com.liferay.commerce.products.model.CommerceProductDefinition;
import com.liferay.commerce.products.model.impl.CommerceProductDefinitionImpl;
import com.liferay.commerce.products.model.impl.CommerceProductDefinitionModelImpl;
import com.liferay.commerce.products.service.persistence.CommerceProductDefinitionPersistence;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

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
 * The persistence implementation for the commerce product definition service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceProductDefinitionPersistence
 * @see com.liferay.commerce.products.service.persistence.CommerceProductDefinitionUtil
 * @generated
 */
@ProviderType
public class CommerceProductDefinitionPersistenceImpl
	extends BasePersistenceImpl<CommerceProductDefinition>
	implements CommerceProductDefinitionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceProductDefinitionUtil} to access the commerce product definition persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceProductDefinitionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceProductDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductDefinitionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceProductDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductDefinitionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceProductDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(CommerceProductDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductDefinitionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(CommerceProductDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductDefinitionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			CommerceProductDefinitionModelImpl.UUID_COLUMN_BITMASK |
			CommerceProductDefinitionModelImpl.DISPLAYDATE_COLUMN_BITMASK |
			CommerceProductDefinitionModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(CommerceProductDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] { String.class.getName() });

	/**
	 * Returns all the commerce product definitions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce product definitions
	 */
	@Override
	public List<CommerceProductDefinition> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce product definitions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce product definitions
	 * @param end the upper bound of the range of commerce product definitions (not inclusive)
	 * @return the range of matching commerce product definitions
	 */
	@Override
	public List<CommerceProductDefinition> findByUuid(String uuid, int start,
		int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce product definitions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce product definitions
	 * @param end the upper bound of the range of commerce product definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce product definitions
	 */
	@Override
	public List<CommerceProductDefinition> findByUuid(String uuid, int start,
		int end, OrderByComparator<CommerceProductDefinition> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce product definitions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce product definitions
	 * @param end the upper bound of the range of commerce product definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce product definitions
	 */
	@Override
	public List<CommerceProductDefinition> findByUuid(String uuid, int start,
		int end,
		OrderByComparator<CommerceProductDefinition> orderByComparator,
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

		List<CommerceProductDefinition> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceProductDefinition>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceProductDefinition commerceProductDefinition : list) {
					if (!Objects.equals(uuid,
								commerceProductDefinition.getUuid())) {
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

			query.append(_SQL_SELECT_COMMERCEPRODUCTDEFINITION_WHERE);

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
				query.append(CommerceProductDefinitionModelImpl.ORDER_BY_JPQL);
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
					list = (List<CommerceProductDefinition>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceProductDefinition>)QueryUtil.list(q,
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
	 * Returns the first commerce product definition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product definition
	 * @throws NoSuchProductDefinitionException if a matching commerce product definition could not be found
	 */
	@Override
	public CommerceProductDefinition findByUuid_First(String uuid,
		OrderByComparator<CommerceProductDefinition> orderByComparator)
		throws NoSuchProductDefinitionException {
		CommerceProductDefinition commerceProductDefinition = fetchByUuid_First(uuid,
				orderByComparator);

		if (commerceProductDefinition != null) {
			return commerceProductDefinition;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductDefinitionException(msg.toString());
	}

	/**
	 * Returns the first commerce product definition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product definition, or <code>null</code> if a matching commerce product definition could not be found
	 */
	@Override
	public CommerceProductDefinition fetchByUuid_First(String uuid,
		OrderByComparator<CommerceProductDefinition> orderByComparator) {
		List<CommerceProductDefinition> list = findByUuid(uuid, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce product definition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product definition
	 * @throws NoSuchProductDefinitionException if a matching commerce product definition could not be found
	 */
	@Override
	public CommerceProductDefinition findByUuid_Last(String uuid,
		OrderByComparator<CommerceProductDefinition> orderByComparator)
		throws NoSuchProductDefinitionException {
		CommerceProductDefinition commerceProductDefinition = fetchByUuid_Last(uuid,
				orderByComparator);

		if (commerceProductDefinition != null) {
			return commerceProductDefinition;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductDefinitionException(msg.toString());
	}

	/**
	 * Returns the last commerce product definition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product definition, or <code>null</code> if a matching commerce product definition could not be found
	 */
	@Override
	public CommerceProductDefinition fetchByUuid_Last(String uuid,
		OrderByComparator<CommerceProductDefinition> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CommerceProductDefinition> list = findByUuid(uuid, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce product definitions before and after the current commerce product definition in the ordered set where uuid = &#63;.
	 *
	 * @param commerceProductDefinitionId the primary key of the current commerce product definition
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce product definition
	 * @throws NoSuchProductDefinitionException if a commerce product definition with the primary key could not be found
	 */
	@Override
	public CommerceProductDefinition[] findByUuid_PrevAndNext(
		long commerceProductDefinitionId, String uuid,
		OrderByComparator<CommerceProductDefinition> orderByComparator)
		throws NoSuchProductDefinitionException {
		CommerceProductDefinition commerceProductDefinition = findByPrimaryKey(commerceProductDefinitionId);

		Session session = null;

		try {
			session = openSession();

			CommerceProductDefinition[] array = new CommerceProductDefinitionImpl[3];

			array[0] = getByUuid_PrevAndNext(session,
					commerceProductDefinition, uuid, orderByComparator, true);

			array[1] = commerceProductDefinition;

			array[2] = getByUuid_PrevAndNext(session,
					commerceProductDefinition, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceProductDefinition getByUuid_PrevAndNext(Session session,
		CommerceProductDefinition commerceProductDefinition, String uuid,
		OrderByComparator<CommerceProductDefinition> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEPRODUCTDEFINITION_WHERE);

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
			query.append(CommerceProductDefinitionModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(commerceProductDefinition);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceProductDefinition> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce product definitions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CommerceProductDefinition commerceProductDefinition : findByUuid(
				uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceProductDefinition);
		}
	}

	/**
	 * Returns the number of commerce product definitions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce product definitions
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEPRODUCTDEFINITION_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "commerceProductDefinition.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "commerceProductDefinition.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(commerceProductDefinition.uuid IS NULL OR commerceProductDefinition.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(CommerceProductDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductDefinitionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			CommerceProductDefinitionModelImpl.UUID_COLUMN_BITMASK |
			CommerceProductDefinitionModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(CommerceProductDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the commerce product definition where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchProductDefinitionException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce product definition
	 * @throws NoSuchProductDefinitionException if a matching commerce product definition could not be found
	 */
	@Override
	public CommerceProductDefinition findByUUID_G(String uuid, long groupId)
		throws NoSuchProductDefinitionException {
		CommerceProductDefinition commerceProductDefinition = fetchByUUID_G(uuid,
				groupId);

		if (commerceProductDefinition == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchProductDefinitionException(msg.toString());
		}

		return commerceProductDefinition;
	}

	/**
	 * Returns the commerce product definition where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce product definition, or <code>null</code> if a matching commerce product definition could not be found
	 */
	@Override
	public CommerceProductDefinition fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the commerce product definition where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching commerce product definition, or <code>null</code> if a matching commerce product definition could not be found
	 */
	@Override
	public CommerceProductDefinition fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof CommerceProductDefinition) {
			CommerceProductDefinition commerceProductDefinition = (CommerceProductDefinition)result;

			if (!Objects.equals(uuid, commerceProductDefinition.getUuid()) ||
					(groupId != commerceProductDefinition.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_COMMERCEPRODUCTDEFINITION_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
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

				List<CommerceProductDefinition> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					CommerceProductDefinition commerceProductDefinition = list.get(0);

					result = commerceProductDefinition;

					cacheResult(commerceProductDefinition);

					if ((commerceProductDefinition.getUuid() == null) ||
							!commerceProductDefinition.getUuid().equals(uuid) ||
							(commerceProductDefinition.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, commerceProductDefinition);
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
			return (CommerceProductDefinition)result;
		}
	}

	/**
	 * Removes the commerce product definition where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the commerce product definition that was removed
	 */
	@Override
	public CommerceProductDefinition removeByUUID_G(String uuid, long groupId)
		throws NoSuchProductDefinitionException {
		CommerceProductDefinition commerceProductDefinition = findByUUID_G(uuid,
				groupId);

		return remove(commerceProductDefinition);
	}

	/**
	 * Returns the number of commerce product definitions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching commerce product definitions
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCEPRODUCTDEFINITION_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "commerceProductDefinition.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "commerceProductDefinition.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(commerceProductDefinition.uuid IS NULL OR commerceProductDefinition.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "commerceProductDefinition.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(CommerceProductDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductDefinitionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(CommerceProductDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductDefinitionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			CommerceProductDefinitionModelImpl.UUID_COLUMN_BITMASK |
			CommerceProductDefinitionModelImpl.COMPANYID_COLUMN_BITMASK |
			CommerceProductDefinitionModelImpl.DISPLAYDATE_COLUMN_BITMASK |
			CommerceProductDefinitionModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(CommerceProductDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the commerce product definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce product definitions
	 */
	@Override
	public List<CommerceProductDefinition> findByUuid_C(String uuid,
		long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce product definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce product definitions
	 * @param end the upper bound of the range of commerce product definitions (not inclusive)
	 * @return the range of matching commerce product definitions
	 */
	@Override
	public List<CommerceProductDefinition> findByUuid_C(String uuid,
		long companyId, int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce product definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce product definitions
	 * @param end the upper bound of the range of commerce product definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce product definitions
	 */
	@Override
	public List<CommerceProductDefinition> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CommerceProductDefinition> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce product definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce product definitions
	 * @param end the upper bound of the range of commerce product definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce product definitions
	 */
	@Override
	public List<CommerceProductDefinition> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CommerceProductDefinition> orderByComparator,
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

		List<CommerceProductDefinition> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceProductDefinition>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceProductDefinition commerceProductDefinition : list) {
					if (!Objects.equals(uuid,
								commerceProductDefinition.getUuid()) ||
							(companyId != commerceProductDefinition.getCompanyId())) {
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

			query.append(_SQL_SELECT_COMMERCEPRODUCTDEFINITION_WHERE);

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
				query.append(CommerceProductDefinitionModelImpl.ORDER_BY_JPQL);
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
					list = (List<CommerceProductDefinition>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceProductDefinition>)QueryUtil.list(q,
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
	 * Returns the first commerce product definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product definition
	 * @throws NoSuchProductDefinitionException if a matching commerce product definition could not be found
	 */
	@Override
	public CommerceProductDefinition findByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CommerceProductDefinition> orderByComparator)
		throws NoSuchProductDefinitionException {
		CommerceProductDefinition commerceProductDefinition = fetchByUuid_C_First(uuid,
				companyId, orderByComparator);

		if (commerceProductDefinition != null) {
			return commerceProductDefinition;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductDefinitionException(msg.toString());
	}

	/**
	 * Returns the first commerce product definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product definition, or <code>null</code> if a matching commerce product definition could not be found
	 */
	@Override
	public CommerceProductDefinition fetchByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CommerceProductDefinition> orderByComparator) {
		List<CommerceProductDefinition> list = findByUuid_C(uuid, companyId, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce product definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product definition
	 * @throws NoSuchProductDefinitionException if a matching commerce product definition could not be found
	 */
	@Override
	public CommerceProductDefinition findByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CommerceProductDefinition> orderByComparator)
		throws NoSuchProductDefinitionException {
		CommerceProductDefinition commerceProductDefinition = fetchByUuid_C_Last(uuid,
				companyId, orderByComparator);

		if (commerceProductDefinition != null) {
			return commerceProductDefinition;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductDefinitionException(msg.toString());
	}

	/**
	 * Returns the last commerce product definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product definition, or <code>null</code> if a matching commerce product definition could not be found
	 */
	@Override
	public CommerceProductDefinition fetchByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CommerceProductDefinition> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceProductDefinition> list = findByUuid_C(uuid, companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce product definitions before and after the current commerce product definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceProductDefinitionId the primary key of the current commerce product definition
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce product definition
	 * @throws NoSuchProductDefinitionException if a commerce product definition with the primary key could not be found
	 */
	@Override
	public CommerceProductDefinition[] findByUuid_C_PrevAndNext(
		long commerceProductDefinitionId, String uuid, long companyId,
		OrderByComparator<CommerceProductDefinition> orderByComparator)
		throws NoSuchProductDefinitionException {
		CommerceProductDefinition commerceProductDefinition = findByPrimaryKey(commerceProductDefinitionId);

		Session session = null;

		try {
			session = openSession();

			CommerceProductDefinition[] array = new CommerceProductDefinitionImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session,
					commerceProductDefinition, uuid, companyId,
					orderByComparator, true);

			array[1] = commerceProductDefinition;

			array[2] = getByUuid_C_PrevAndNext(session,
					commerceProductDefinition, uuid, companyId,
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

	protected CommerceProductDefinition getByUuid_C_PrevAndNext(
		Session session, CommerceProductDefinition commerceProductDefinition,
		String uuid, long companyId,
		OrderByComparator<CommerceProductDefinition> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEPRODUCTDEFINITION_WHERE);

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
			query.append(CommerceProductDefinitionModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(commerceProductDefinition);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceProductDefinition> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce product definitions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CommerceProductDefinition commerceProductDefinition : findByUuid_C(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceProductDefinition);
		}
	}

	/**
	 * Returns the number of commerce product definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce product definitions
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCEPRODUCTDEFINITION_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "commerceProductDefinition.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "commerceProductDefinition.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(commerceProductDefinition.uuid IS NULL OR commerceProductDefinition.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "commerceProductDefinition.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(CommerceProductDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductDefinitionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(CommerceProductDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductDefinitionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			CommerceProductDefinitionModelImpl.GROUPID_COLUMN_BITMASK |
			CommerceProductDefinitionModelImpl.DISPLAYDATE_COLUMN_BITMASK |
			CommerceProductDefinitionModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(CommerceProductDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId", new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce product definitions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching commerce product definitions
	 */
	@Override
	public List<CommerceProductDefinition> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce product definitions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce product definitions
	 * @param end the upper bound of the range of commerce product definitions (not inclusive)
	 * @return the range of matching commerce product definitions
	 */
	@Override
	public List<CommerceProductDefinition> findByGroupId(long groupId,
		int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce product definitions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce product definitions
	 * @param end the upper bound of the range of commerce product definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce product definitions
	 */
	@Override
	public List<CommerceProductDefinition> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceProductDefinition> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce product definitions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce product definitions
	 * @param end the upper bound of the range of commerce product definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce product definitions
	 */
	@Override
	public List<CommerceProductDefinition> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceProductDefinition> orderByComparator,
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

		List<CommerceProductDefinition> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceProductDefinition>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceProductDefinition commerceProductDefinition : list) {
					if ((groupId != commerceProductDefinition.getGroupId())) {
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

			query.append(_SQL_SELECT_COMMERCEPRODUCTDEFINITION_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceProductDefinitionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<CommerceProductDefinition>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceProductDefinition>)QueryUtil.list(q,
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
	 * Returns the first commerce product definition in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product definition
	 * @throws NoSuchProductDefinitionException if a matching commerce product definition could not be found
	 */
	@Override
	public CommerceProductDefinition findByGroupId_First(long groupId,
		OrderByComparator<CommerceProductDefinition> orderByComparator)
		throws NoSuchProductDefinitionException {
		CommerceProductDefinition commerceProductDefinition = fetchByGroupId_First(groupId,
				orderByComparator);

		if (commerceProductDefinition != null) {
			return commerceProductDefinition;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductDefinitionException(msg.toString());
	}

	/**
	 * Returns the first commerce product definition in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product definition, or <code>null</code> if a matching commerce product definition could not be found
	 */
	@Override
	public CommerceProductDefinition fetchByGroupId_First(long groupId,
		OrderByComparator<CommerceProductDefinition> orderByComparator) {
		List<CommerceProductDefinition> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce product definition in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product definition
	 * @throws NoSuchProductDefinitionException if a matching commerce product definition could not be found
	 */
	@Override
	public CommerceProductDefinition findByGroupId_Last(long groupId,
		OrderByComparator<CommerceProductDefinition> orderByComparator)
		throws NoSuchProductDefinitionException {
		CommerceProductDefinition commerceProductDefinition = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (commerceProductDefinition != null) {
			return commerceProductDefinition;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductDefinitionException(msg.toString());
	}

	/**
	 * Returns the last commerce product definition in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product definition, or <code>null</code> if a matching commerce product definition could not be found
	 */
	@Override
	public CommerceProductDefinition fetchByGroupId_Last(long groupId,
		OrderByComparator<CommerceProductDefinition> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<CommerceProductDefinition> list = findByGroupId(groupId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce product definitions before and after the current commerce product definition in the ordered set where groupId = &#63;.
	 *
	 * @param commerceProductDefinitionId the primary key of the current commerce product definition
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce product definition
	 * @throws NoSuchProductDefinitionException if a commerce product definition with the primary key could not be found
	 */
	@Override
	public CommerceProductDefinition[] findByGroupId_PrevAndNext(
		long commerceProductDefinitionId, long groupId,
		OrderByComparator<CommerceProductDefinition> orderByComparator)
		throws NoSuchProductDefinitionException {
		CommerceProductDefinition commerceProductDefinition = findByPrimaryKey(commerceProductDefinitionId);

		Session session = null;

		try {
			session = openSession();

			CommerceProductDefinition[] array = new CommerceProductDefinitionImpl[3];

			array[0] = getByGroupId_PrevAndNext(session,
					commerceProductDefinition, groupId, orderByComparator, true);

			array[1] = commerceProductDefinition;

			array[2] = getByGroupId_PrevAndNext(session,
					commerceProductDefinition, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceProductDefinition getByGroupId_PrevAndNext(
		Session session, CommerceProductDefinition commerceProductDefinition,
		long groupId,
		OrderByComparator<CommerceProductDefinition> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEPRODUCTDEFINITION_WHERE);

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
			query.append(CommerceProductDefinitionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceProductDefinition);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceProductDefinition> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the commerce product definitions that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching commerce product definitions that the user has permission to view
	 */
	@Override
	public List<CommerceProductDefinition> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce product definitions that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce product definitions
	 * @param end the upper bound of the range of commerce product definitions (not inclusive)
	 * @return the range of matching commerce product definitions that the user has permission to view
	 */
	@Override
	public List<CommerceProductDefinition> filterFindByGroupId(long groupId,
		int start, int end) {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce product definitions that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce product definitions
	 * @param end the upper bound of the range of commerce product definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce product definitions that the user has permission to view
	 */
	@Override
	public List<CommerceProductDefinition> filterFindByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceProductDefinition> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId(groupId, start, end, orderByComparator);
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
			query.append(_FILTER_SQL_SELECT_COMMERCEPRODUCTDEFINITION_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_COMMERCEPRODUCTDEFINITION_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_COMMERCEPRODUCTDEFINITION_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(CommerceProductDefinitionModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(CommerceProductDefinitionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				CommerceProductDefinition.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS,
					CommerceProductDefinitionImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE,
					CommerceProductDefinitionImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<CommerceProductDefinition>)QueryUtil.list(q,
				getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the commerce product definitions before and after the current commerce product definition in the ordered set of commerce product definitions that the user has permission to view where groupId = &#63;.
	 *
	 * @param commerceProductDefinitionId the primary key of the current commerce product definition
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce product definition
	 * @throws NoSuchProductDefinitionException if a commerce product definition with the primary key could not be found
	 */
	@Override
	public CommerceProductDefinition[] filterFindByGroupId_PrevAndNext(
		long commerceProductDefinitionId, long groupId,
		OrderByComparator<CommerceProductDefinition> orderByComparator)
		throws NoSuchProductDefinitionException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(commerceProductDefinitionId,
				groupId, orderByComparator);
		}

		CommerceProductDefinition commerceProductDefinition = findByPrimaryKey(commerceProductDefinitionId);

		Session session = null;

		try {
			session = openSession();

			CommerceProductDefinition[] array = new CommerceProductDefinitionImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session,
					commerceProductDefinition, groupId, orderByComparator, true);

			array[1] = commerceProductDefinition;

			array[2] = filterGetByGroupId_PrevAndNext(session,
					commerceProductDefinition, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceProductDefinition filterGetByGroupId_PrevAndNext(
		Session session, CommerceProductDefinition commerceProductDefinition,
		long groupId,
		OrderByComparator<CommerceProductDefinition> orderByComparator,
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

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_COMMERCEPRODUCTDEFINITION_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_COMMERCEPRODUCTDEFINITION_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_COMMERCEPRODUCTDEFINITION_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(CommerceProductDefinitionModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(CommerceProductDefinitionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				CommerceProductDefinition.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS,
				CommerceProductDefinitionImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE,
				CommerceProductDefinitionImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceProductDefinition);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceProductDefinition> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce product definitions where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (CommerceProductDefinition commerceProductDefinition : findByGroupId(
				groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceProductDefinition);
		}
	}

	/**
	 * Returns the number of commerce product definitions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching commerce product definitions
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEPRODUCTDEFINITION_WHERE);

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

	/**
	 * Returns the number of commerce product definitions that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching commerce product definitions that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_COMMERCEPRODUCTDEFINITION_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				CommerceProductDefinition.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "commerceProductDefinition.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(CommerceProductDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductDefinitionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(CommerceProductDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductDefinitionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			CommerceProductDefinitionModelImpl.COMPANYID_COLUMN_BITMASK |
			CommerceProductDefinitionModelImpl.DISPLAYDATE_COLUMN_BITMASK |
			CommerceProductDefinitionModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(CommerceProductDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCompanyId", new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce product definitions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce product definitions
	 */
	@Override
	public List<CommerceProductDefinition> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce product definitions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce product definitions
	 * @param end the upper bound of the range of commerce product definitions (not inclusive)
	 * @return the range of matching commerce product definitions
	 */
	@Override
	public List<CommerceProductDefinition> findByCompanyId(long companyId,
		int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce product definitions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce product definitions
	 * @param end the upper bound of the range of commerce product definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce product definitions
	 */
	@Override
	public List<CommerceProductDefinition> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<CommerceProductDefinition> orderByComparator) {
		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce product definitions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce product definitions
	 * @param end the upper bound of the range of commerce product definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce product definitions
	 */
	@Override
	public List<CommerceProductDefinition> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<CommerceProductDefinition> orderByComparator,
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

		List<CommerceProductDefinition> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceProductDefinition>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceProductDefinition commerceProductDefinition : list) {
					if ((companyId != commerceProductDefinition.getCompanyId())) {
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

			query.append(_SQL_SELECT_COMMERCEPRODUCTDEFINITION_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceProductDefinitionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<CommerceProductDefinition>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceProductDefinition>)QueryUtil.list(q,
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
	 * Returns the first commerce product definition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product definition
	 * @throws NoSuchProductDefinitionException if a matching commerce product definition could not be found
	 */
	@Override
	public CommerceProductDefinition findByCompanyId_First(long companyId,
		OrderByComparator<CommerceProductDefinition> orderByComparator)
		throws NoSuchProductDefinitionException {
		CommerceProductDefinition commerceProductDefinition = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (commerceProductDefinition != null) {
			return commerceProductDefinition;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductDefinitionException(msg.toString());
	}

	/**
	 * Returns the first commerce product definition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product definition, or <code>null</code> if a matching commerce product definition could not be found
	 */
	@Override
	public CommerceProductDefinition fetchByCompanyId_First(long companyId,
		OrderByComparator<CommerceProductDefinition> orderByComparator) {
		List<CommerceProductDefinition> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce product definition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product definition
	 * @throws NoSuchProductDefinitionException if a matching commerce product definition could not be found
	 */
	@Override
	public CommerceProductDefinition findByCompanyId_Last(long companyId,
		OrderByComparator<CommerceProductDefinition> orderByComparator)
		throws NoSuchProductDefinitionException {
		CommerceProductDefinition commerceProductDefinition = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (commerceProductDefinition != null) {
			return commerceProductDefinition;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductDefinitionException(msg.toString());
	}

	/**
	 * Returns the last commerce product definition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product definition, or <code>null</code> if a matching commerce product definition could not be found
	 */
	@Override
	public CommerceProductDefinition fetchByCompanyId_Last(long companyId,
		OrderByComparator<CommerceProductDefinition> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceProductDefinition> list = findByCompanyId(companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce product definitions before and after the current commerce product definition in the ordered set where companyId = &#63;.
	 *
	 * @param commerceProductDefinitionId the primary key of the current commerce product definition
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce product definition
	 * @throws NoSuchProductDefinitionException if a commerce product definition with the primary key could not be found
	 */
	@Override
	public CommerceProductDefinition[] findByCompanyId_PrevAndNext(
		long commerceProductDefinitionId, long companyId,
		OrderByComparator<CommerceProductDefinition> orderByComparator)
		throws NoSuchProductDefinitionException {
		CommerceProductDefinition commerceProductDefinition = findByPrimaryKey(commerceProductDefinitionId);

		Session session = null;

		try {
			session = openSession();

			CommerceProductDefinition[] array = new CommerceProductDefinitionImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session,
					commerceProductDefinition, companyId, orderByComparator,
					true);

			array[1] = commerceProductDefinition;

			array[2] = getByCompanyId_PrevAndNext(session,
					commerceProductDefinition, companyId, orderByComparator,
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

	protected CommerceProductDefinition getByCompanyId_PrevAndNext(
		Session session, CommerceProductDefinition commerceProductDefinition,
		long companyId,
		OrderByComparator<CommerceProductDefinition> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEPRODUCTDEFINITION_WHERE);

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
			query.append(CommerceProductDefinitionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceProductDefinition);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceProductDefinition> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce product definitions where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (CommerceProductDefinition commerceProductDefinition : findByCompanyId(
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceProductDefinition);
		}
	}

	/**
	 * Returns the number of commerce product definitions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce product definitions
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEPRODUCTDEFINITION_WHERE);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "commerceProductDefinition.companyId = ?";

	public CommerceProductDefinitionPersistenceImpl() {
		setModelClass(CommerceProductDefinition.class);
	}

	/**
	 * Caches the commerce product definition in the entity cache if it is enabled.
	 *
	 * @param commerceProductDefinition the commerce product definition
	 */
	@Override
	public void cacheResult(CommerceProductDefinition commerceProductDefinition) {
		entityCache.putResult(CommerceProductDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionImpl.class,
			commerceProductDefinition.getPrimaryKey(), commerceProductDefinition);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				commerceProductDefinition.getUuid(),
				commerceProductDefinition.getGroupId()
			}, commerceProductDefinition);

		commerceProductDefinition.resetOriginalValues();
	}

	/**
	 * Caches the commerce product definitions in the entity cache if it is enabled.
	 *
	 * @param commerceProductDefinitions the commerce product definitions
	 */
	@Override
	public void cacheResult(
		List<CommerceProductDefinition> commerceProductDefinitions) {
		for (CommerceProductDefinition commerceProductDefinition : commerceProductDefinitions) {
			if (entityCache.getResult(
						CommerceProductDefinitionModelImpl.ENTITY_CACHE_ENABLED,
						CommerceProductDefinitionImpl.class,
						commerceProductDefinition.getPrimaryKey()) == null) {
				cacheResult(commerceProductDefinition);
			}
			else {
				commerceProductDefinition.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce product definitions.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceProductDefinitionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce product definition.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceProductDefinition commerceProductDefinition) {
		entityCache.removeResult(CommerceProductDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionImpl.class,
			commerceProductDefinition.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CommerceProductDefinitionModelImpl)commerceProductDefinition,
			true);
	}

	@Override
	public void clearCache(
		List<CommerceProductDefinition> commerceProductDefinitions) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceProductDefinition commerceProductDefinition : commerceProductDefinitions) {
			entityCache.removeResult(CommerceProductDefinitionModelImpl.ENTITY_CACHE_ENABLED,
				CommerceProductDefinitionImpl.class,
				commerceProductDefinition.getPrimaryKey());

			clearUniqueFindersCache((CommerceProductDefinitionModelImpl)commerceProductDefinition,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceProductDefinitionModelImpl commerceProductDefinitionModelImpl) {
		Object[] args = new Object[] {
				commerceProductDefinitionModelImpl.getUuid(),
				commerceProductDefinitionModelImpl.getGroupId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
			commerceProductDefinitionModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CommerceProductDefinitionModelImpl commerceProductDefinitionModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					commerceProductDefinitionModelImpl.getUuid(),
					commerceProductDefinitionModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if ((commerceProductDefinitionModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					commerceProductDefinitionModelImpl.getOriginalUuid(),
					commerceProductDefinitionModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}
	}

	/**
	 * Creates a new commerce product definition with the primary key. Does not add the commerce product definition to the database.
	 *
	 * @param commerceProductDefinitionId the primary key for the new commerce product definition
	 * @return the new commerce product definition
	 */
	@Override
	public CommerceProductDefinition create(long commerceProductDefinitionId) {
		CommerceProductDefinition commerceProductDefinition = new CommerceProductDefinitionImpl();

		commerceProductDefinition.setNew(true);
		commerceProductDefinition.setPrimaryKey(commerceProductDefinitionId);

		String uuid = PortalUUIDUtil.generate();

		commerceProductDefinition.setUuid(uuid);

		commerceProductDefinition.setCompanyId(companyProvider.getCompanyId());

		return commerceProductDefinition;
	}

	/**
	 * Removes the commerce product definition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceProductDefinitionId the primary key of the commerce product definition
	 * @return the commerce product definition that was removed
	 * @throws NoSuchProductDefinitionException if a commerce product definition with the primary key could not be found
	 */
	@Override
	public CommerceProductDefinition remove(long commerceProductDefinitionId)
		throws NoSuchProductDefinitionException {
		return remove((Serializable)commerceProductDefinitionId);
	}

	/**
	 * Removes the commerce product definition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce product definition
	 * @return the commerce product definition that was removed
	 * @throws NoSuchProductDefinitionException if a commerce product definition with the primary key could not be found
	 */
	@Override
	public CommerceProductDefinition remove(Serializable primaryKey)
		throws NoSuchProductDefinitionException {
		Session session = null;

		try {
			session = openSession();

			CommerceProductDefinition commerceProductDefinition = (CommerceProductDefinition)session.get(CommerceProductDefinitionImpl.class,
					primaryKey);

			if (commerceProductDefinition == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchProductDefinitionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceProductDefinition);
		}
		catch (NoSuchProductDefinitionException nsee) {
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
	protected CommerceProductDefinition removeImpl(
		CommerceProductDefinition commerceProductDefinition) {
		commerceProductDefinition = toUnwrappedModel(commerceProductDefinition);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceProductDefinition)) {
				commerceProductDefinition = (CommerceProductDefinition)session.get(CommerceProductDefinitionImpl.class,
						commerceProductDefinition.getPrimaryKeyObj());
			}

			if (commerceProductDefinition != null) {
				session.delete(commerceProductDefinition);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceProductDefinition != null) {
			clearCache(commerceProductDefinition);
		}

		return commerceProductDefinition;
	}

	@Override
	public CommerceProductDefinition updateImpl(
		CommerceProductDefinition commerceProductDefinition) {
		commerceProductDefinition = toUnwrappedModel(commerceProductDefinition);

		boolean isNew = commerceProductDefinition.isNew();

		CommerceProductDefinitionModelImpl commerceProductDefinitionModelImpl = (CommerceProductDefinitionModelImpl)commerceProductDefinition;

		if (Validator.isNull(commerceProductDefinition.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			commerceProductDefinition.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceProductDefinition.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceProductDefinition.setCreateDate(now);
			}
			else {
				commerceProductDefinition.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!commerceProductDefinitionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceProductDefinition.setModifiedDate(now);
			}
			else {
				commerceProductDefinition.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceProductDefinition.isNew()) {
				session.save(commerceProductDefinition);

				commerceProductDefinition.setNew(false);
			}
			else {
				commerceProductDefinition = (CommerceProductDefinition)session.merge(commerceProductDefinition);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceProductDefinitionModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commerceProductDefinitionModelImpl.getUuid()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
				args);

			args = new Object[] {
					commerceProductDefinitionModelImpl.getUuid(),
					commerceProductDefinitionModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
				args);

			args = new Object[] { commerceProductDefinitionModelImpl.getGroupId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			args = new Object[] {
					commerceProductDefinitionModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commerceProductDefinitionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceProductDefinitionModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { commerceProductDefinitionModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((commerceProductDefinitionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceProductDefinitionModelImpl.getOriginalUuid(),
						commerceProductDefinitionModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						commerceProductDefinitionModelImpl.getUuid(),
						commerceProductDefinitionModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((commerceProductDefinitionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceProductDefinitionModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						commerceProductDefinitionModelImpl.getGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((commerceProductDefinitionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceProductDefinitionModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] {
						commerceProductDefinitionModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}
		}

		entityCache.putResult(CommerceProductDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductDefinitionImpl.class,
			commerceProductDefinition.getPrimaryKey(),
			commerceProductDefinition, false);

		clearUniqueFindersCache(commerceProductDefinitionModelImpl, false);
		cacheUniqueFindersCache(commerceProductDefinitionModelImpl);

		commerceProductDefinition.resetOriginalValues();

		return commerceProductDefinition;
	}

	protected CommerceProductDefinition toUnwrappedModel(
		CommerceProductDefinition commerceProductDefinition) {
		if (commerceProductDefinition instanceof CommerceProductDefinitionImpl) {
			return commerceProductDefinition;
		}

		CommerceProductDefinitionImpl commerceProductDefinitionImpl = new CommerceProductDefinitionImpl();

		commerceProductDefinitionImpl.setNew(commerceProductDefinition.isNew());
		commerceProductDefinitionImpl.setPrimaryKey(commerceProductDefinition.getPrimaryKey());

		commerceProductDefinitionImpl.setUuid(commerceProductDefinition.getUuid());
		commerceProductDefinitionImpl.setCommerceProductDefinitionId(commerceProductDefinition.getCommerceProductDefinitionId());
		commerceProductDefinitionImpl.setGroupId(commerceProductDefinition.getGroupId());
		commerceProductDefinitionImpl.setCompanyId(commerceProductDefinition.getCompanyId());
		commerceProductDefinitionImpl.setUserId(commerceProductDefinition.getUserId());
		commerceProductDefinitionImpl.setUserName(commerceProductDefinition.getUserName());
		commerceProductDefinitionImpl.setCreateDate(commerceProductDefinition.getCreateDate());
		commerceProductDefinitionImpl.setModifiedDate(commerceProductDefinition.getModifiedDate());
		commerceProductDefinitionImpl.setTitle(commerceProductDefinition.getTitle());
		commerceProductDefinitionImpl.setUrlTitle(commerceProductDefinition.getUrlTitle());
		commerceProductDefinitionImpl.setDescription(commerceProductDefinition.getDescription());
		commerceProductDefinitionImpl.setProductTypeName(commerceProductDefinition.getProductTypeName());
		commerceProductDefinitionImpl.setAvailableIndividually(commerceProductDefinition.isAvailableIndividually());
		commerceProductDefinitionImpl.setDDMStructureKey(commerceProductDefinition.getDDMStructureKey());
		commerceProductDefinitionImpl.setBaseSKU(commerceProductDefinition.getBaseSKU());
		commerceProductDefinitionImpl.setDisplayDate(commerceProductDefinition.getDisplayDate());
		commerceProductDefinitionImpl.setExpirationDate(commerceProductDefinition.getExpirationDate());
		commerceProductDefinitionImpl.setLastPublishDate(commerceProductDefinition.getLastPublishDate());
		commerceProductDefinitionImpl.setStatus(commerceProductDefinition.getStatus());
		commerceProductDefinitionImpl.setStatusByUserId(commerceProductDefinition.getStatusByUserId());
		commerceProductDefinitionImpl.setStatusByUserName(commerceProductDefinition.getStatusByUserName());
		commerceProductDefinitionImpl.setStatusDate(commerceProductDefinition.getStatusDate());

		return commerceProductDefinitionImpl;
	}

	/**
	 * Returns the commerce product definition with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce product definition
	 * @return the commerce product definition
	 * @throws NoSuchProductDefinitionException if a commerce product definition with the primary key could not be found
	 */
	@Override
	public CommerceProductDefinition findByPrimaryKey(Serializable primaryKey)
		throws NoSuchProductDefinitionException {
		CommerceProductDefinition commerceProductDefinition = fetchByPrimaryKey(primaryKey);

		if (commerceProductDefinition == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchProductDefinitionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceProductDefinition;
	}

	/**
	 * Returns the commerce product definition with the primary key or throws a {@link NoSuchProductDefinitionException} if it could not be found.
	 *
	 * @param commerceProductDefinitionId the primary key of the commerce product definition
	 * @return the commerce product definition
	 * @throws NoSuchProductDefinitionException if a commerce product definition with the primary key could not be found
	 */
	@Override
	public CommerceProductDefinition findByPrimaryKey(
		long commerceProductDefinitionId)
		throws NoSuchProductDefinitionException {
		return findByPrimaryKey((Serializable)commerceProductDefinitionId);
	}

	/**
	 * Returns the commerce product definition with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce product definition
	 * @return the commerce product definition, or <code>null</code> if a commerce product definition with the primary key could not be found
	 */
	@Override
	public CommerceProductDefinition fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceProductDefinitionModelImpl.ENTITY_CACHE_ENABLED,
				CommerceProductDefinitionImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceProductDefinition commerceProductDefinition = (CommerceProductDefinition)serializable;

		if (commerceProductDefinition == null) {
			Session session = null;

			try {
				session = openSession();

				commerceProductDefinition = (CommerceProductDefinition)session.get(CommerceProductDefinitionImpl.class,
						primaryKey);

				if (commerceProductDefinition != null) {
					cacheResult(commerceProductDefinition);
				}
				else {
					entityCache.putResult(CommerceProductDefinitionModelImpl.ENTITY_CACHE_ENABLED,
						CommerceProductDefinitionImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceProductDefinitionModelImpl.ENTITY_CACHE_ENABLED,
					CommerceProductDefinitionImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceProductDefinition;
	}

	/**
	 * Returns the commerce product definition with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceProductDefinitionId the primary key of the commerce product definition
	 * @return the commerce product definition, or <code>null</code> if a commerce product definition with the primary key could not be found
	 */
	@Override
	public CommerceProductDefinition fetchByPrimaryKey(
		long commerceProductDefinitionId) {
		return fetchByPrimaryKey((Serializable)commerceProductDefinitionId);
	}

	@Override
	public Map<Serializable, CommerceProductDefinition> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceProductDefinition> map = new HashMap<Serializable, CommerceProductDefinition>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceProductDefinition commerceProductDefinition = fetchByPrimaryKey(primaryKey);

			if (commerceProductDefinition != null) {
				map.put(primaryKey, commerceProductDefinition);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceProductDefinitionModelImpl.ENTITY_CACHE_ENABLED,
					CommerceProductDefinitionImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CommerceProductDefinition)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCEPRODUCTDEFINITION_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (CommerceProductDefinition commerceProductDefinition : (List<CommerceProductDefinition>)q.list()) {
				map.put(commerceProductDefinition.getPrimaryKeyObj(),
					commerceProductDefinition);

				cacheResult(commerceProductDefinition);

				uncachedPrimaryKeys.remove(commerceProductDefinition.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceProductDefinitionModelImpl.ENTITY_CACHE_ENABLED,
					CommerceProductDefinitionImpl.class, primaryKey, nullModel);
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
	 * Returns all the commerce product definitions.
	 *
	 * @return the commerce product definitions
	 */
	@Override
	public List<CommerceProductDefinition> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce product definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce product definitions
	 * @param end the upper bound of the range of commerce product definitions (not inclusive)
	 * @return the range of commerce product definitions
	 */
	@Override
	public List<CommerceProductDefinition> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce product definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce product definitions
	 * @param end the upper bound of the range of commerce product definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce product definitions
	 */
	@Override
	public List<CommerceProductDefinition> findAll(int start, int end,
		OrderByComparator<CommerceProductDefinition> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce product definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce product definitions
	 * @param end the upper bound of the range of commerce product definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce product definitions
	 */
	@Override
	public List<CommerceProductDefinition> findAll(int start, int end,
		OrderByComparator<CommerceProductDefinition> orderByComparator,
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

		List<CommerceProductDefinition> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceProductDefinition>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCEPRODUCTDEFINITION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEPRODUCTDEFINITION;

				if (pagination) {
					sql = sql.concat(CommerceProductDefinitionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceProductDefinition>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceProductDefinition>)QueryUtil.list(q,
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
	 * Removes all the commerce product definitions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceProductDefinition commerceProductDefinition : findAll()) {
			remove(commerceProductDefinition);
		}
	}

	/**
	 * Returns the number of commerce product definitions.
	 *
	 * @return the number of commerce product definitions
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCEPRODUCTDEFINITION);

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
		return CommerceProductDefinitionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce product definition persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceProductDefinitionImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCEPRODUCTDEFINITION = "SELECT commerceProductDefinition FROM CommerceProductDefinition commerceProductDefinition";
	private static final String _SQL_SELECT_COMMERCEPRODUCTDEFINITION_WHERE_PKS_IN =
		"SELECT commerceProductDefinition FROM CommerceProductDefinition commerceProductDefinition WHERE commerceProductDefinitionId IN (";
	private static final String _SQL_SELECT_COMMERCEPRODUCTDEFINITION_WHERE = "SELECT commerceProductDefinition FROM CommerceProductDefinition commerceProductDefinition WHERE ";
	private static final String _SQL_COUNT_COMMERCEPRODUCTDEFINITION = "SELECT COUNT(commerceProductDefinition) FROM CommerceProductDefinition commerceProductDefinition";
	private static final String _SQL_COUNT_COMMERCEPRODUCTDEFINITION_WHERE = "SELECT COUNT(commerceProductDefinition) FROM CommerceProductDefinition commerceProductDefinition WHERE ";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "commerceProductDefinition.commerceProductDefinitionId";
	private static final String _FILTER_SQL_SELECT_COMMERCEPRODUCTDEFINITION_WHERE =
		"SELECT DISTINCT {commerceProductDefinition.*} FROM CommerceProductDefinition commerceProductDefinition WHERE ";
	private static final String _FILTER_SQL_SELECT_COMMERCEPRODUCTDEFINITION_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {CommerceProductDefinition.*} FROM (SELECT DISTINCT commerceProductDefinition.commerceProductDefinitionId FROM CommerceProductDefinition commerceProductDefinition WHERE ";
	private static final String _FILTER_SQL_SELECT_COMMERCEPRODUCTDEFINITION_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN CommerceProductDefinition ON TEMP_TABLE.commerceProductDefinitionId = CommerceProductDefinition.commerceProductDefinitionId";
	private static final String _FILTER_SQL_COUNT_COMMERCEPRODUCTDEFINITION_WHERE =
		"SELECT COUNT(DISTINCT commerceProductDefinition.commerceProductDefinitionId) AS COUNT_VALUE FROM CommerceProductDefinition commerceProductDefinition WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "commerceProductDefinition";
	private static final String _FILTER_ENTITY_TABLE = "CommerceProductDefinition";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceProductDefinition.";
	private static final String _ORDER_BY_ENTITY_TABLE = "CommerceProductDefinition.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceProductDefinition exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceProductDefinition exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceProductDefinitionPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid"
			});
}