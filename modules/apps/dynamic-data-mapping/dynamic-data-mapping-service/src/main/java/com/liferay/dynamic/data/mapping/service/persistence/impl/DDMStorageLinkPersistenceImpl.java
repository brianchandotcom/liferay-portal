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

package com.liferay.dynamic.data.mapping.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.mapping.exception.NoSuchStorageLinkException;
import com.liferay.dynamic.data.mapping.model.DDMStorageLink;
import com.liferay.dynamic.data.mapping.model.impl.DDMStorageLinkImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMStorageLinkModelImpl;
import com.liferay.dynamic.data.mapping.service.persistence.DDMStorageLinkPersistence;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
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
import com.liferay.portal.service.persistence.CompanyProvider;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the d d m storage link service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStorageLinkPersistence
 * @see com.liferay.dynamic.data.mapping.service.persistence.DDMStorageLinkUtil
 * @generated
 */
@ProviderType
public class DDMStorageLinkPersistenceImpl extends BasePersistenceImpl<DDMStorageLink>
	implements DDMStorageLinkPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DDMStorageLinkUtil} to access the d d m storage link persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DDMStorageLinkImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(DDMStorageLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStorageLinkModelImpl.FINDER_CACHE_ENABLED,
			DDMStorageLinkImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(DDMStorageLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStorageLinkModelImpl.FINDER_CACHE_ENABLED,
			DDMStorageLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DDMStorageLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStorageLinkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(DDMStorageLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStorageLinkModelImpl.FINDER_CACHE_ENABLED,
			DDMStorageLinkImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(DDMStorageLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStorageLinkModelImpl.FINDER_CACHE_ENABLED,
			DDMStorageLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			DDMStorageLinkModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(DDMStorageLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStorageLinkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the d d m storage links where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching d d m storage links
	 */
	@Override
	public List<DDMStorageLink> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m storage links where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStorageLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of d d m storage links
	 * @param end the upper bound of the range of d d m storage links (not inclusive)
	 * @return the range of matching d d m storage links
	 */
	@Override
	public List<DDMStorageLink> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m storage links where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStorageLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of d d m storage links
	 * @param end the upper bound of the range of d d m storage links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m storage links
	 */
	@Override
	public List<DDMStorageLink> findByUuid(String uuid, int start, int end,
		OrderByComparator<DDMStorageLink> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the d d m storage links where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStorageLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of d d m storage links
	 * @param end the upper bound of the range of d d m storage links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching d d m storage links
	 */
	@Override
	public List<DDMStorageLink> findByUuid(String uuid, int start, int end,
		OrderByComparator<DDMStorageLink> orderByComparator,
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

		List<DDMStorageLink> list = null;

		if (retrieveFromCache) {
			list = (List<DDMStorageLink>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMStorageLink ddmStorageLink : list) {
					if (!Validator.equals(uuid, ddmStorageLink.getUuid())) {
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

			query.append(_SQL_SELECT_DDMSTORAGELINK_WHERE);

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
				query.append(DDMStorageLinkModelImpl.ORDER_BY_JPQL);
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
					list = (List<DDMStorageLink>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMStorageLink>)QueryUtil.list(q,
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
	 * Returns the first d d m storage link in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m storage link
	 * @throws com.liferay.dynamic.data.mapping.NoSuchStorageLinkException if a matching d d m storage link could not be found
	 */
	@Override
	public DDMStorageLink findByUuid_First(String uuid,
		OrderByComparator<DDMStorageLink> orderByComparator)
		throws NoSuchStorageLinkException {
		DDMStorageLink ddmStorageLink = fetchByUuid_First(uuid,
				orderByComparator);

		if (ddmStorageLink != null) {
			return ddmStorageLink;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStorageLinkException(msg.toString());
	}

	/**
	 * Returns the first d d m storage link in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m storage link, or <code>null</code> if a matching d d m storage link could not be found
	 */
	@Override
	public DDMStorageLink fetchByUuid_First(String uuid,
		OrderByComparator<DDMStorageLink> orderByComparator) {
		List<DDMStorageLink> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m storage link in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m storage link
	 * @throws com.liferay.dynamic.data.mapping.NoSuchStorageLinkException if a matching d d m storage link could not be found
	 */
	@Override
	public DDMStorageLink findByUuid_Last(String uuid,
		OrderByComparator<DDMStorageLink> orderByComparator)
		throws NoSuchStorageLinkException {
		DDMStorageLink ddmStorageLink = fetchByUuid_Last(uuid, orderByComparator);

		if (ddmStorageLink != null) {
			return ddmStorageLink;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStorageLinkException(msg.toString());
	}

	/**
	 * Returns the last d d m storage link in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m storage link, or <code>null</code> if a matching d d m storage link could not be found
	 */
	@Override
	public DDMStorageLink fetchByUuid_Last(String uuid,
		OrderByComparator<DDMStorageLink> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<DDMStorageLink> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m storage links before and after the current d d m storage link in the ordered set where uuid = &#63;.
	 *
	 * @param storageLinkId the primary key of the current d d m storage link
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m storage link
	 * @throws com.liferay.dynamic.data.mapping.NoSuchStorageLinkException if a d d m storage link with the primary key could not be found
	 */
	@Override
	public DDMStorageLink[] findByUuid_PrevAndNext(long storageLinkId,
		String uuid, OrderByComparator<DDMStorageLink> orderByComparator)
		throws NoSuchStorageLinkException {
		DDMStorageLink ddmStorageLink = findByPrimaryKey(storageLinkId);

		Session session = null;

		try {
			session = openSession();

			DDMStorageLink[] array = new DDMStorageLinkImpl[3];

			array[0] = getByUuid_PrevAndNext(session, ddmStorageLink, uuid,
					orderByComparator, true);

			array[1] = ddmStorageLink;

			array[2] = getByUuid_PrevAndNext(session, ddmStorageLink, uuid,
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

	protected DDMStorageLink getByUuid_PrevAndNext(Session session,
		DDMStorageLink ddmStorageLink, String uuid,
		OrderByComparator<DDMStorageLink> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMSTORAGELINK_WHERE);

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
			query.append(DDMStorageLinkModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(ddmStorageLink);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMStorageLink> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the d d m storage links where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (DDMStorageLink ddmStorageLink : findByUuid(uuid,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmStorageLink);
		}
	}

	/**
	 * Returns the number of d d m storage links where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching d d m storage links
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMSTORAGELINK_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "ddmStorageLink.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "ddmStorageLink.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(ddmStorageLink.uuid IS NULL OR ddmStorageLink.uuid = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(DDMStorageLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStorageLinkModelImpl.FINDER_CACHE_ENABLED,
			DDMStorageLinkImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(DDMStorageLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStorageLinkModelImpl.FINDER_CACHE_ENABLED,
			DDMStorageLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			DDMStorageLinkModelImpl.UUID_COLUMN_BITMASK |
			DDMStorageLinkModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(DDMStorageLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStorageLinkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the d d m storage links where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching d d m storage links
	 */
	@Override
	public List<DDMStorageLink> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m storage links where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStorageLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of d d m storage links
	 * @param end the upper bound of the range of d d m storage links (not inclusive)
	 * @return the range of matching d d m storage links
	 */
	@Override
	public List<DDMStorageLink> findByUuid_C(String uuid, long companyId,
		int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m storage links where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStorageLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of d d m storage links
	 * @param end the upper bound of the range of d d m storage links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m storage links
	 */
	@Override
	public List<DDMStorageLink> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator<DDMStorageLink> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the d d m storage links where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStorageLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of d d m storage links
	 * @param end the upper bound of the range of d d m storage links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching d d m storage links
	 */
	@Override
	public List<DDMStorageLink> findByUuid_C(String uuid, long companyId,
		int start, int end,
		OrderByComparator<DDMStorageLink> orderByComparator,
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

		List<DDMStorageLink> list = null;

		if (retrieveFromCache) {
			list = (List<DDMStorageLink>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMStorageLink ddmStorageLink : list) {
					if (!Validator.equals(uuid, ddmStorageLink.getUuid()) ||
							(companyId != ddmStorageLink.getCompanyId())) {
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

			query.append(_SQL_SELECT_DDMSTORAGELINK_WHERE);

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
				query.append(DDMStorageLinkModelImpl.ORDER_BY_JPQL);
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
					list = (List<DDMStorageLink>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMStorageLink>)QueryUtil.list(q,
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
	 * Returns the first d d m storage link in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m storage link
	 * @throws com.liferay.dynamic.data.mapping.NoSuchStorageLinkException if a matching d d m storage link could not be found
	 */
	@Override
	public DDMStorageLink findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<DDMStorageLink> orderByComparator)
		throws NoSuchStorageLinkException {
		DDMStorageLink ddmStorageLink = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (ddmStorageLink != null) {
			return ddmStorageLink;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStorageLinkException(msg.toString());
	}

	/**
	 * Returns the first d d m storage link in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m storage link, or <code>null</code> if a matching d d m storage link could not be found
	 */
	@Override
	public DDMStorageLink fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<DDMStorageLink> orderByComparator) {
		List<DDMStorageLink> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m storage link in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m storage link
	 * @throws com.liferay.dynamic.data.mapping.NoSuchStorageLinkException if a matching d d m storage link could not be found
	 */
	@Override
	public DDMStorageLink findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<DDMStorageLink> orderByComparator)
		throws NoSuchStorageLinkException {
		DDMStorageLink ddmStorageLink = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (ddmStorageLink != null) {
			return ddmStorageLink;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStorageLinkException(msg.toString());
	}

	/**
	 * Returns the last d d m storage link in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m storage link, or <code>null</code> if a matching d d m storage link could not be found
	 */
	@Override
	public DDMStorageLink fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<DDMStorageLink> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<DDMStorageLink> list = findByUuid_C(uuid, companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m storage links before and after the current d d m storage link in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param storageLinkId the primary key of the current d d m storage link
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m storage link
	 * @throws com.liferay.dynamic.data.mapping.NoSuchStorageLinkException if a d d m storage link with the primary key could not be found
	 */
	@Override
	public DDMStorageLink[] findByUuid_C_PrevAndNext(long storageLinkId,
		String uuid, long companyId,
		OrderByComparator<DDMStorageLink> orderByComparator)
		throws NoSuchStorageLinkException {
		DDMStorageLink ddmStorageLink = findByPrimaryKey(storageLinkId);

		Session session = null;

		try {
			session = openSession();

			DDMStorageLink[] array = new DDMStorageLinkImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, ddmStorageLink, uuid,
					companyId, orderByComparator, true);

			array[1] = ddmStorageLink;

			array[2] = getByUuid_C_PrevAndNext(session, ddmStorageLink, uuid,
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

	protected DDMStorageLink getByUuid_C_PrevAndNext(Session session,
		DDMStorageLink ddmStorageLink, String uuid, long companyId,
		OrderByComparator<DDMStorageLink> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMSTORAGELINK_WHERE);

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
			query.append(DDMStorageLinkModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(ddmStorageLink);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMStorageLink> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the d d m storage links where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (DDMStorageLink ddmStorageLink : findByUuid_C(uuid, companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmStorageLink);
		}
	}

	/**
	 * Returns the number of d d m storage links where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching d d m storage links
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDMSTORAGELINK_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "ddmStorageLink.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "ddmStorageLink.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(ddmStorageLink.uuid IS NULL OR ddmStorageLink.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "ddmStorageLink.companyId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_CLASSPK = new FinderPath(DDMStorageLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStorageLinkModelImpl.FINDER_CACHE_ENABLED,
			DDMStorageLinkImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByClassPK", new String[] { Long.class.getName() },
			DDMStorageLinkModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CLASSPK = new FinderPath(DDMStorageLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStorageLinkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByClassPK",
			new String[] { Long.class.getName() });

	/**
	 * Returns the d d m storage link where classPK = &#63; or throws a {@link com.liferay.dynamic.data.mapping.NoSuchStorageLinkException} if it could not be found.
	 *
	 * @param classPK the class p k
	 * @return the matching d d m storage link
	 * @throws com.liferay.dynamic.data.mapping.NoSuchStorageLinkException if a matching d d m storage link could not be found
	 */
	@Override
	public DDMStorageLink findByClassPK(long classPK)
		throws NoSuchStorageLinkException {
		DDMStorageLink ddmStorageLink = fetchByClassPK(classPK);

		if (ddmStorageLink == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchStorageLinkException(msg.toString());
		}

		return ddmStorageLink;
	}

	/**
	 * Returns the d d m storage link where classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classPK the class p k
	 * @return the matching d d m storage link, or <code>null</code> if a matching d d m storage link could not be found
	 */
	@Override
	public DDMStorageLink fetchByClassPK(long classPK) {
		return fetchByClassPK(classPK, true);
	}

	/**
	 * Returns the d d m storage link where classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classPK the class p k
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching d d m storage link, or <code>null</code> if a matching d d m storage link could not be found
	 */
	@Override
	public DDMStorageLink fetchByClassPK(long classPK, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { classPK };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_CLASSPK,
					finderArgs, this);
		}

		if (result instanceof DDMStorageLink) {
			DDMStorageLink ddmStorageLink = (DDMStorageLink)result;

			if ((classPK != ddmStorageLink.getClassPK())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_DDMSTORAGELINK_WHERE);

			query.append(_FINDER_COLUMN_CLASSPK_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classPK);

				List<DDMStorageLink> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_CLASSPK,
						finderArgs, list);
				}
				else {
					DDMStorageLink ddmStorageLink = list.get(0);

					result = ddmStorageLink;

					cacheResult(ddmStorageLink);

					if ((ddmStorageLink.getClassPK() != classPK)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_CLASSPK,
							finderArgs, ddmStorageLink);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_CLASSPK,
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
			return (DDMStorageLink)result;
		}
	}

	/**
	 * Removes the d d m storage link where classPK = &#63; from the database.
	 *
	 * @param classPK the class p k
	 * @return the d d m storage link that was removed
	 */
	@Override
	public DDMStorageLink removeByClassPK(long classPK)
		throws NoSuchStorageLinkException {
		DDMStorageLink ddmStorageLink = findByClassPK(classPK);

		return remove(ddmStorageLink);
	}

	/**
	 * Returns the number of d d m storage links where classPK = &#63;.
	 *
	 * @param classPK the class p k
	 * @return the number of matching d d m storage links
	 */
	@Override
	public int countByClassPK(long classPK) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_CLASSPK;

		Object[] finderArgs = new Object[] { classPK };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMSTORAGELINK_WHERE);

			query.append(_FINDER_COLUMN_CLASSPK_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classPK);

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

	private static final String _FINDER_COLUMN_CLASSPK_CLASSPK_2 = "ddmStorageLink.classPK = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_STRUCTUREID =
		new FinderPath(DDMStorageLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStorageLinkModelImpl.FINDER_CACHE_ENABLED,
			DDMStorageLinkImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByStructureId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STRUCTUREID =
		new FinderPath(DDMStorageLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStorageLinkModelImpl.FINDER_CACHE_ENABLED,
			DDMStorageLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByStructureId",
			new String[] { Long.class.getName() },
			DDMStorageLinkModelImpl.STRUCTUREID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_STRUCTUREID = new FinderPath(DDMStorageLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStorageLinkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByStructureId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the d d m storage links where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @return the matching d d m storage links
	 */
	@Override
	public List<DDMStorageLink> findByStructureId(long structureId) {
		return findByStructureId(structureId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m storage links where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStorageLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of d d m storage links
	 * @param end the upper bound of the range of d d m storage links (not inclusive)
	 * @return the range of matching d d m storage links
	 */
	@Override
	public List<DDMStorageLink> findByStructureId(long structureId, int start,
		int end) {
		return findByStructureId(structureId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m storage links where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStorageLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of d d m storage links
	 * @param end the upper bound of the range of d d m storage links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m storage links
	 */
	@Override
	public List<DDMStorageLink> findByStructureId(long structureId, int start,
		int end, OrderByComparator<DDMStorageLink> orderByComparator) {
		return findByStructureId(structureId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the d d m storage links where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStorageLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of d d m storage links
	 * @param end the upper bound of the range of d d m storage links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching d d m storage links
	 */
	@Override
	public List<DDMStorageLink> findByStructureId(long structureId, int start,
		int end, OrderByComparator<DDMStorageLink> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STRUCTUREID;
			finderArgs = new Object[] { structureId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_STRUCTUREID;
			finderArgs = new Object[] { structureId, start, end, orderByComparator };
		}

		List<DDMStorageLink> list = null;

		if (retrieveFromCache) {
			list = (List<DDMStorageLink>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMStorageLink ddmStorageLink : list) {
					if ((structureId != ddmStorageLink.getStructureId())) {
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

			query.append(_SQL_SELECT_DDMSTORAGELINK_WHERE);

			query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMStorageLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(structureId);

				if (!pagination) {
					list = (List<DDMStorageLink>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMStorageLink>)QueryUtil.list(q,
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
	 * Returns the first d d m storage link in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m storage link
	 * @throws com.liferay.dynamic.data.mapping.NoSuchStorageLinkException if a matching d d m storage link could not be found
	 */
	@Override
	public DDMStorageLink findByStructureId_First(long structureId,
		OrderByComparator<DDMStorageLink> orderByComparator)
		throws NoSuchStorageLinkException {
		DDMStorageLink ddmStorageLink = fetchByStructureId_First(structureId,
				orderByComparator);

		if (ddmStorageLink != null) {
			return ddmStorageLink;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("structureId=");
		msg.append(structureId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStorageLinkException(msg.toString());
	}

	/**
	 * Returns the first d d m storage link in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m storage link, or <code>null</code> if a matching d d m storage link could not be found
	 */
	@Override
	public DDMStorageLink fetchByStructureId_First(long structureId,
		OrderByComparator<DDMStorageLink> orderByComparator) {
		List<DDMStorageLink> list = findByStructureId(structureId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m storage link in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m storage link
	 * @throws com.liferay.dynamic.data.mapping.NoSuchStorageLinkException if a matching d d m storage link could not be found
	 */
	@Override
	public DDMStorageLink findByStructureId_Last(long structureId,
		OrderByComparator<DDMStorageLink> orderByComparator)
		throws NoSuchStorageLinkException {
		DDMStorageLink ddmStorageLink = fetchByStructureId_Last(structureId,
				orderByComparator);

		if (ddmStorageLink != null) {
			return ddmStorageLink;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("structureId=");
		msg.append(structureId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStorageLinkException(msg.toString());
	}

	/**
	 * Returns the last d d m storage link in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m storage link, or <code>null</code> if a matching d d m storage link could not be found
	 */
	@Override
	public DDMStorageLink fetchByStructureId_Last(long structureId,
		OrderByComparator<DDMStorageLink> orderByComparator) {
		int count = countByStructureId(structureId);

		if (count == 0) {
			return null;
		}

		List<DDMStorageLink> list = findByStructureId(structureId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m storage links before and after the current d d m storage link in the ordered set where structureId = &#63;.
	 *
	 * @param storageLinkId the primary key of the current d d m storage link
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m storage link
	 * @throws com.liferay.dynamic.data.mapping.NoSuchStorageLinkException if a d d m storage link with the primary key could not be found
	 */
	@Override
	public DDMStorageLink[] findByStructureId_PrevAndNext(long storageLinkId,
		long structureId, OrderByComparator<DDMStorageLink> orderByComparator)
		throws NoSuchStorageLinkException {
		DDMStorageLink ddmStorageLink = findByPrimaryKey(storageLinkId);

		Session session = null;

		try {
			session = openSession();

			DDMStorageLink[] array = new DDMStorageLinkImpl[3];

			array[0] = getByStructureId_PrevAndNext(session, ddmStorageLink,
					structureId, orderByComparator, true);

			array[1] = ddmStorageLink;

			array[2] = getByStructureId_PrevAndNext(session, ddmStorageLink,
					structureId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMStorageLink getByStructureId_PrevAndNext(Session session,
		DDMStorageLink ddmStorageLink, long structureId,
		OrderByComparator<DDMStorageLink> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMSTORAGELINK_WHERE);

		query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2);

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
			query.append(DDMStorageLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(structureId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmStorageLink);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMStorageLink> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the d d m storage links where structureId = &#63; from the database.
	 *
	 * @param structureId the structure ID
	 */
	@Override
	public void removeByStructureId(long structureId) {
		for (DDMStorageLink ddmStorageLink : findByStructureId(structureId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmStorageLink);
		}
	}

	/**
	 * Returns the number of d d m storage links where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @return the number of matching d d m storage links
	 */
	@Override
	public int countByStructureId(long structureId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_STRUCTUREID;

		Object[] finderArgs = new Object[] { structureId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMSTORAGELINK_WHERE);

			query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(structureId);

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

	private static final String _FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2 = "ddmStorageLink.structureId = ?";

	public DDMStorageLinkPersistenceImpl() {
		setModelClass(DDMStorageLink.class);
	}

	/**
	 * Caches the d d m storage link in the entity cache if it is enabled.
	 *
	 * @param ddmStorageLink the d d m storage link
	 */
	@Override
	public void cacheResult(DDMStorageLink ddmStorageLink) {
		entityCache.putResult(DDMStorageLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStorageLinkImpl.class, ddmStorageLink.getPrimaryKey(),
			ddmStorageLink);

		finderCache.putResult(FINDER_PATH_FETCH_BY_CLASSPK,
			new Object[] { ddmStorageLink.getClassPK() }, ddmStorageLink);

		ddmStorageLink.resetOriginalValues();
	}

	/**
	 * Caches the d d m storage links in the entity cache if it is enabled.
	 *
	 * @param ddmStorageLinks the d d m storage links
	 */
	@Override
	public void cacheResult(List<DDMStorageLink> ddmStorageLinks) {
		for (DDMStorageLink ddmStorageLink : ddmStorageLinks) {
			if (entityCache.getResult(
						DDMStorageLinkModelImpl.ENTITY_CACHE_ENABLED,
						DDMStorageLinkImpl.class, ddmStorageLink.getPrimaryKey()) == null) {
				cacheResult(ddmStorageLink);
			}
			else {
				ddmStorageLink.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all d d m storage links.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DDMStorageLinkImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the d d m storage link.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DDMStorageLink ddmStorageLink) {
		entityCache.removeResult(DDMStorageLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStorageLinkImpl.class, ddmStorageLink.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((DDMStorageLinkModelImpl)ddmStorageLink);
	}

	@Override
	public void clearCache(List<DDMStorageLink> ddmStorageLinks) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DDMStorageLink ddmStorageLink : ddmStorageLinks) {
			entityCache.removeResult(DDMStorageLinkModelImpl.ENTITY_CACHE_ENABLED,
				DDMStorageLinkImpl.class, ddmStorageLink.getPrimaryKey());

			clearUniqueFindersCache((DDMStorageLinkModelImpl)ddmStorageLink);
		}
	}

	protected void cacheUniqueFindersCache(
		DDMStorageLinkModelImpl ddmStorageLinkModelImpl, boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] { ddmStorageLinkModelImpl.getClassPK() };

			finderCache.putResult(FINDER_PATH_COUNT_BY_CLASSPK, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_CLASSPK, args,
				ddmStorageLinkModelImpl);
		}
		else {
			if ((ddmStorageLinkModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_CLASSPK.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmStorageLinkModelImpl.getClassPK()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_CLASSPK, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_CLASSPK, args,
					ddmStorageLinkModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(
		DDMStorageLinkModelImpl ddmStorageLinkModelImpl) {
		Object[] args = new Object[] { ddmStorageLinkModelImpl.getClassPK() };

		finderCache.removeResult(FINDER_PATH_COUNT_BY_CLASSPK, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_CLASSPK, args);

		if ((ddmStorageLinkModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_CLASSPK.getColumnBitmask()) != 0) {
			args = new Object[] { ddmStorageLinkModelImpl.getOriginalClassPK() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_CLASSPK, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_CLASSPK, args);
		}
	}

	/**
	 * Creates a new d d m storage link with the primary key. Does not add the d d m storage link to the database.
	 *
	 * @param storageLinkId the primary key for the new d d m storage link
	 * @return the new d d m storage link
	 */
	@Override
	public DDMStorageLink create(long storageLinkId) {
		DDMStorageLink ddmStorageLink = new DDMStorageLinkImpl();

		ddmStorageLink.setNew(true);
		ddmStorageLink.setPrimaryKey(storageLinkId);

		String uuid = PortalUUIDUtil.generate();

		ddmStorageLink.setUuid(uuid);

		return ddmStorageLink;
	}

	/**
	 * Removes the d d m storage link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param storageLinkId the primary key of the d d m storage link
	 * @return the d d m storage link that was removed
	 * @throws com.liferay.dynamic.data.mapping.NoSuchStorageLinkException if a d d m storage link with the primary key could not be found
	 */
	@Override
	public DDMStorageLink remove(long storageLinkId)
		throws NoSuchStorageLinkException {
		return remove((Serializable)storageLinkId);
	}

	/**
	 * Removes the d d m storage link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the d d m storage link
	 * @return the d d m storage link that was removed
	 * @throws com.liferay.dynamic.data.mapping.NoSuchStorageLinkException if a d d m storage link with the primary key could not be found
	 */
	@Override
	public DDMStorageLink remove(Serializable primaryKey)
		throws NoSuchStorageLinkException {
		Session session = null;

		try {
			session = openSession();

			DDMStorageLink ddmStorageLink = (DDMStorageLink)session.get(DDMStorageLinkImpl.class,
					primaryKey);

			if (ddmStorageLink == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchStorageLinkException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(ddmStorageLink);
		}
		catch (NoSuchStorageLinkException nsee) {
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
	protected DDMStorageLink removeImpl(DDMStorageLink ddmStorageLink) {
		ddmStorageLink = toUnwrappedModel(ddmStorageLink);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ddmStorageLink)) {
				ddmStorageLink = (DDMStorageLink)session.get(DDMStorageLinkImpl.class,
						ddmStorageLink.getPrimaryKeyObj());
			}

			if (ddmStorageLink != null) {
				session.delete(ddmStorageLink);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (ddmStorageLink != null) {
			clearCache(ddmStorageLink);
		}

		return ddmStorageLink;
	}

	@Override
	public DDMStorageLink updateImpl(DDMStorageLink ddmStorageLink) {
		ddmStorageLink = toUnwrappedModel(ddmStorageLink);

		boolean isNew = ddmStorageLink.isNew();

		DDMStorageLinkModelImpl ddmStorageLinkModelImpl = (DDMStorageLinkModelImpl)ddmStorageLink;

		if (Validator.isNull(ddmStorageLink.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			ddmStorageLink.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (ddmStorageLink.isNew()) {
				session.save(ddmStorageLink);

				ddmStorageLink.setNew(false);
			}
			else {
				ddmStorageLink = (DDMStorageLink)session.merge(ddmStorageLink);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !DDMStorageLinkModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((ddmStorageLinkModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmStorageLinkModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { ddmStorageLinkModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((ddmStorageLinkModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmStorageLinkModelImpl.getOriginalUuid(),
						ddmStorageLinkModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						ddmStorageLinkModelImpl.getUuid(),
						ddmStorageLinkModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((ddmStorageLinkModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STRUCTUREID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmStorageLinkModelImpl.getOriginalStructureId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_STRUCTUREID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STRUCTUREID,
					args);

				args = new Object[] { ddmStorageLinkModelImpl.getStructureId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_STRUCTUREID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STRUCTUREID,
					args);
			}
		}

		entityCache.putResult(DDMStorageLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStorageLinkImpl.class, ddmStorageLink.getPrimaryKey(),
			ddmStorageLink, false);

		clearUniqueFindersCache(ddmStorageLinkModelImpl);
		cacheUniqueFindersCache(ddmStorageLinkModelImpl, isNew);

		ddmStorageLink.resetOriginalValues();

		return ddmStorageLink;
	}

	protected DDMStorageLink toUnwrappedModel(DDMStorageLink ddmStorageLink) {
		if (ddmStorageLink instanceof DDMStorageLinkImpl) {
			return ddmStorageLink;
		}

		DDMStorageLinkImpl ddmStorageLinkImpl = new DDMStorageLinkImpl();

		ddmStorageLinkImpl.setNew(ddmStorageLink.isNew());
		ddmStorageLinkImpl.setPrimaryKey(ddmStorageLink.getPrimaryKey());

		ddmStorageLinkImpl.setUuid(ddmStorageLink.getUuid());
		ddmStorageLinkImpl.setStorageLinkId(ddmStorageLink.getStorageLinkId());
		ddmStorageLinkImpl.setCompanyId(ddmStorageLink.getCompanyId());
		ddmStorageLinkImpl.setClassNameId(ddmStorageLink.getClassNameId());
		ddmStorageLinkImpl.setClassPK(ddmStorageLink.getClassPK());
		ddmStorageLinkImpl.setStructureId(ddmStorageLink.getStructureId());

		return ddmStorageLinkImpl;
	}

	/**
	 * Returns the d d m storage link with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the d d m storage link
	 * @return the d d m storage link
	 * @throws com.liferay.dynamic.data.mapping.NoSuchStorageLinkException if a d d m storage link with the primary key could not be found
	 */
	@Override
	public DDMStorageLink findByPrimaryKey(Serializable primaryKey)
		throws NoSuchStorageLinkException {
		DDMStorageLink ddmStorageLink = fetchByPrimaryKey(primaryKey);

		if (ddmStorageLink == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchStorageLinkException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return ddmStorageLink;
	}

	/**
	 * Returns the d d m storage link with the primary key or throws a {@link com.liferay.dynamic.data.mapping.NoSuchStorageLinkException} if it could not be found.
	 *
	 * @param storageLinkId the primary key of the d d m storage link
	 * @return the d d m storage link
	 * @throws com.liferay.dynamic.data.mapping.NoSuchStorageLinkException if a d d m storage link with the primary key could not be found
	 */
	@Override
	public DDMStorageLink findByPrimaryKey(long storageLinkId)
		throws NoSuchStorageLinkException {
		return findByPrimaryKey((Serializable)storageLinkId);
	}

	/**
	 * Returns the d d m storage link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the d d m storage link
	 * @return the d d m storage link, or <code>null</code> if a d d m storage link with the primary key could not be found
	 */
	@Override
	public DDMStorageLink fetchByPrimaryKey(Serializable primaryKey) {
		DDMStorageLink ddmStorageLink = (DDMStorageLink)entityCache.getResult(DDMStorageLinkModelImpl.ENTITY_CACHE_ENABLED,
				DDMStorageLinkImpl.class, primaryKey);

		if (ddmStorageLink == _nullDDMStorageLink) {
			return null;
		}

		if (ddmStorageLink == null) {
			Session session = null;

			try {
				session = openSession();

				ddmStorageLink = (DDMStorageLink)session.get(DDMStorageLinkImpl.class,
						primaryKey);

				if (ddmStorageLink != null) {
					cacheResult(ddmStorageLink);
				}
				else {
					entityCache.putResult(DDMStorageLinkModelImpl.ENTITY_CACHE_ENABLED,
						DDMStorageLinkImpl.class, primaryKey,
						_nullDDMStorageLink);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(DDMStorageLinkModelImpl.ENTITY_CACHE_ENABLED,
					DDMStorageLinkImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return ddmStorageLink;
	}

	/**
	 * Returns the d d m storage link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param storageLinkId the primary key of the d d m storage link
	 * @return the d d m storage link, or <code>null</code> if a d d m storage link with the primary key could not be found
	 */
	@Override
	public DDMStorageLink fetchByPrimaryKey(long storageLinkId) {
		return fetchByPrimaryKey((Serializable)storageLinkId);
	}

	@Override
	public Map<Serializable, DDMStorageLink> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, DDMStorageLink> map = new HashMap<Serializable, DDMStorageLink>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			DDMStorageLink ddmStorageLink = fetchByPrimaryKey(primaryKey);

			if (ddmStorageLink != null) {
				map.put(primaryKey, ddmStorageLink);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			DDMStorageLink ddmStorageLink = (DDMStorageLink)entityCache.getResult(DDMStorageLinkModelImpl.ENTITY_CACHE_ENABLED,
					DDMStorageLinkImpl.class, primaryKey);

			if (ddmStorageLink == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, ddmStorageLink);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_DDMSTORAGELINK_WHERE_PKS_IN);

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

			for (DDMStorageLink ddmStorageLink : (List<DDMStorageLink>)q.list()) {
				map.put(ddmStorageLink.getPrimaryKeyObj(), ddmStorageLink);

				cacheResult(ddmStorageLink);

				uncachedPrimaryKeys.remove(ddmStorageLink.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(DDMStorageLinkModelImpl.ENTITY_CACHE_ENABLED,
					DDMStorageLinkImpl.class, primaryKey, _nullDDMStorageLink);
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
	 * Returns all the d d m storage links.
	 *
	 * @return the d d m storage links
	 */
	@Override
	public List<DDMStorageLink> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m storage links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStorageLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d m storage links
	 * @param end the upper bound of the range of d d m storage links (not inclusive)
	 * @return the range of d d m storage links
	 */
	@Override
	public List<DDMStorageLink> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m storage links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStorageLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d m storage links
	 * @param end the upper bound of the range of d d m storage links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of d d m storage links
	 */
	@Override
	public List<DDMStorageLink> findAll(int start, int end,
		OrderByComparator<DDMStorageLink> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the d d m storage links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStorageLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d m storage links
	 * @param end the upper bound of the range of d d m storage links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of d d m storage links
	 */
	@Override
	public List<DDMStorageLink> findAll(int start, int end,
		OrderByComparator<DDMStorageLink> orderByComparator,
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

		List<DDMStorageLink> list = null;

		if (retrieveFromCache) {
			list = (List<DDMStorageLink>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DDMSTORAGELINK);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DDMSTORAGELINK;

				if (pagination) {
					sql = sql.concat(DDMStorageLinkModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<DDMStorageLink>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMStorageLink>)QueryUtil.list(q,
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
	 * Removes all the d d m storage links from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DDMStorageLink ddmStorageLink : findAll()) {
			remove(ddmStorageLink);
		}
	}

	/**
	 * Returns the number of d d m storage links.
	 *
	 * @return the number of d d m storage links
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DDMSTORAGELINK);

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
		return DDMStorageLinkModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the d d m storage link persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(DDMStorageLinkImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProvider.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_DDMSTORAGELINK = "SELECT ddmStorageLink FROM DDMStorageLink ddmStorageLink";
	private static final String _SQL_SELECT_DDMSTORAGELINK_WHERE_PKS_IN = "SELECT ddmStorageLink FROM DDMStorageLink ddmStorageLink WHERE storageLinkId IN (";
	private static final String _SQL_SELECT_DDMSTORAGELINK_WHERE = "SELECT ddmStorageLink FROM DDMStorageLink ddmStorageLink WHERE ";
	private static final String _SQL_COUNT_DDMSTORAGELINK = "SELECT COUNT(ddmStorageLink) FROM DDMStorageLink ddmStorageLink";
	private static final String _SQL_COUNT_DDMSTORAGELINK_WHERE = "SELECT COUNT(ddmStorageLink) FROM DDMStorageLink ddmStorageLink WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "ddmStorageLink.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DDMStorageLink exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DDMStorageLink exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(DDMStorageLinkPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid"
			});
	private static final DDMStorageLink _nullDDMStorageLink = new DDMStorageLinkImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<DDMStorageLink> toCacheModel() {
				return _nullDDMStorageLinkCacheModel;
			}
		};

	private static final CacheModel<DDMStorageLink> _nullDDMStorageLinkCacheModel =
		new CacheModel<DDMStorageLink>() {
			@Override
			public DDMStorageLink toEntityModel() {
				return _nullDDMStorageLink;
			}
		};
}