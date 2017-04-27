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

import com.liferay.commerce.product.exception.NoSuchCPDefinitionMediaException;
import com.liferay.commerce.product.model.CPDefinitionMedia;
import com.liferay.commerce.product.model.impl.CPDefinitionMediaImpl;
import com.liferay.commerce.product.model.impl.CPDefinitionMediaModelImpl;
import com.liferay.commerce.product.service.persistence.CPDefinitionMediaPersistence;

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
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;

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
 * The persistence implementation for the cp definition media service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPDefinitionMediaPersistence
 * @see com.liferay.commerce.product.service.persistence.CPDefinitionMediaUtil
 * @generated
 */
@ProviderType
public class CPDefinitionMediaPersistenceImpl extends BasePersistenceImpl<CPDefinitionMedia>
	implements CPDefinitionMediaPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CPDefinitionMediaUtil} to access the cp definition media persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CPDefinitionMediaImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CPDefinitionMediaModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionMediaModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionMediaImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CPDefinitionMediaModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionMediaModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionMediaImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CPDefinitionMediaModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionMediaModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(CPDefinitionMediaModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionMediaModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionMediaImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(CPDefinitionMediaModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionMediaModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionMediaImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			CPDefinitionMediaModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(CPDefinitionMediaModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionMediaModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the cp definition medias where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cp definition medias
	 */
	@Override
	public List<CPDefinitionMedia> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition medias where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionMediaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definition medias
	 * @param end the upper bound of the range of cp definition medias (not inclusive)
	 * @return the range of matching cp definition medias
	 */
	@Override
	public List<CPDefinitionMedia> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition medias where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionMediaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definition medias
	 * @param end the upper bound of the range of cp definition medias (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition medias
	 */
	@Override
	public List<CPDefinitionMedia> findByUuid(String uuid, int start, int end,
		OrderByComparator<CPDefinitionMedia> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition medias where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionMediaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definition medias
	 * @param end the upper bound of the range of cp definition medias (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp definition medias
	 */
	@Override
	public List<CPDefinitionMedia> findByUuid(String uuid, int start, int end,
		OrderByComparator<CPDefinitionMedia> orderByComparator,
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

		List<CPDefinitionMedia> list = null;

		if (retrieveFromCache) {
			list = (List<CPDefinitionMedia>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionMedia cpDefinitionMedia : list) {
					if (!Objects.equals(uuid, cpDefinitionMedia.getUuid())) {
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

			query.append(_SQL_SELECT_CPDEFINITIONMEDIA_WHERE);

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
				query.append(CPDefinitionMediaModelImpl.ORDER_BY_JPQL);
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
					list = (List<CPDefinitionMedia>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPDefinitionMedia>)QueryUtil.list(q,
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
	 * Returns the first cp definition media in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition media
	 * @throws NoSuchCPDefinitionMediaException if a matching cp definition media could not be found
	 */
	@Override
	public CPDefinitionMedia findByUuid_First(String uuid,
		OrderByComparator<CPDefinitionMedia> orderByComparator)
		throws NoSuchCPDefinitionMediaException {
		CPDefinitionMedia cpDefinitionMedia = fetchByUuid_First(uuid,
				orderByComparator);

		if (cpDefinitionMedia != null) {
			return cpDefinitionMedia;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCPDefinitionMediaException(msg.toString());
	}

	/**
	 * Returns the first cp definition media in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition media, or <code>null</code> if a matching cp definition media could not be found
	 */
	@Override
	public CPDefinitionMedia fetchByUuid_First(String uuid,
		OrderByComparator<CPDefinitionMedia> orderByComparator) {
		List<CPDefinitionMedia> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition media in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition media
	 * @throws NoSuchCPDefinitionMediaException if a matching cp definition media could not be found
	 */
	@Override
	public CPDefinitionMedia findByUuid_Last(String uuid,
		OrderByComparator<CPDefinitionMedia> orderByComparator)
		throws NoSuchCPDefinitionMediaException {
		CPDefinitionMedia cpDefinitionMedia = fetchByUuid_Last(uuid,
				orderByComparator);

		if (cpDefinitionMedia != null) {
			return cpDefinitionMedia;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCPDefinitionMediaException(msg.toString());
	}

	/**
	 * Returns the last cp definition media in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition media, or <code>null</code> if a matching cp definition media could not be found
	 */
	@Override
	public CPDefinitionMedia fetchByUuid_Last(String uuid,
		OrderByComparator<CPDefinitionMedia> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionMedia> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition medias before and after the current cp definition media in the ordered set where uuid = &#63;.
	 *
	 * @param CPDefinitionMediaId the primary key of the current cp definition media
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition media
	 * @throws NoSuchCPDefinitionMediaException if a cp definition media with the primary key could not be found
	 */
	@Override
	public CPDefinitionMedia[] findByUuid_PrevAndNext(
		long CPDefinitionMediaId, String uuid,
		OrderByComparator<CPDefinitionMedia> orderByComparator)
		throws NoSuchCPDefinitionMediaException {
		CPDefinitionMedia cpDefinitionMedia = findByPrimaryKey(CPDefinitionMediaId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionMedia[] array = new CPDefinitionMediaImpl[3];

			array[0] = getByUuid_PrevAndNext(session, cpDefinitionMedia, uuid,
					orderByComparator, true);

			array[1] = cpDefinitionMedia;

			array[2] = getByUuid_PrevAndNext(session, cpDefinitionMedia, uuid,
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

	protected CPDefinitionMedia getByUuid_PrevAndNext(Session session,
		CPDefinitionMedia cpDefinitionMedia, String uuid,
		OrderByComparator<CPDefinitionMedia> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CPDEFINITIONMEDIA_WHERE);

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
			query.append(CPDefinitionMediaModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(cpDefinitionMedia);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPDefinitionMedia> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definition medias where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CPDefinitionMedia cpDefinitionMedia : findByUuid(uuid,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpDefinitionMedia);
		}
	}

	/**
	 * Returns the number of cp definition medias where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp definition medias
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CPDEFINITIONMEDIA_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "cpDefinitionMedia.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "cpDefinitionMedia.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(cpDefinitionMedia.uuid IS NULL OR cpDefinitionMedia.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(CPDefinitionMediaModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionMediaModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionMediaImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			CPDefinitionMediaModelImpl.UUID_COLUMN_BITMASK |
			CPDefinitionMediaModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(CPDefinitionMediaModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionMediaModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the cp definition media where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPDefinitionMediaException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp definition media
	 * @throws NoSuchCPDefinitionMediaException if a matching cp definition media could not be found
	 */
	@Override
	public CPDefinitionMedia findByUUID_G(String uuid, long groupId)
		throws NoSuchCPDefinitionMediaException {
		CPDefinitionMedia cpDefinitionMedia = fetchByUUID_G(uuid, groupId);

		if (cpDefinitionMedia == null) {
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

			throw new NoSuchCPDefinitionMediaException(msg.toString());
		}

		return cpDefinitionMedia;
	}

	/**
	 * Returns the cp definition media where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp definition media, or <code>null</code> if a matching cp definition media could not be found
	 */
	@Override
	public CPDefinitionMedia fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the cp definition media where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching cp definition media, or <code>null</code> if a matching cp definition media could not be found
	 */
	@Override
	public CPDefinitionMedia fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof CPDefinitionMedia) {
			CPDefinitionMedia cpDefinitionMedia = (CPDefinitionMedia)result;

			if (!Objects.equals(uuid, cpDefinitionMedia.getUuid()) ||
					(groupId != cpDefinitionMedia.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_CPDEFINITIONMEDIA_WHERE);

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

				List<CPDefinitionMedia> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					CPDefinitionMedia cpDefinitionMedia = list.get(0);

					result = cpDefinitionMedia;

					cacheResult(cpDefinitionMedia);

					if ((cpDefinitionMedia.getUuid() == null) ||
							!cpDefinitionMedia.getUuid().equals(uuid) ||
							(cpDefinitionMedia.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, cpDefinitionMedia);
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
			return (CPDefinitionMedia)result;
		}
	}

	/**
	 * Removes the cp definition media where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the cp definition media that was removed
	 */
	@Override
	public CPDefinitionMedia removeByUUID_G(String uuid, long groupId)
		throws NoSuchCPDefinitionMediaException {
		CPDefinitionMedia cpDefinitionMedia = findByUUID_G(uuid, groupId);

		return remove(cpDefinitionMedia);
	}

	/**
	 * Returns the number of cp definition medias where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching cp definition medias
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CPDEFINITIONMEDIA_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "cpDefinitionMedia.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "cpDefinitionMedia.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(cpDefinitionMedia.uuid IS NULL OR cpDefinitionMedia.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "cpDefinitionMedia.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(CPDefinitionMediaModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionMediaModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionMediaImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(CPDefinitionMediaModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionMediaModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionMediaImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			CPDefinitionMediaModelImpl.UUID_COLUMN_BITMASK |
			CPDefinitionMediaModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(CPDefinitionMediaModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionMediaModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the cp definition medias where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp definition medias
	 */
	@Override
	public List<CPDefinitionMedia> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition medias where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionMediaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition medias
	 * @param end the upper bound of the range of cp definition medias (not inclusive)
	 * @return the range of matching cp definition medias
	 */
	@Override
	public List<CPDefinitionMedia> findByUuid_C(String uuid, long companyId,
		int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition medias where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionMediaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition medias
	 * @param end the upper bound of the range of cp definition medias (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition medias
	 */
	@Override
	public List<CPDefinitionMedia> findByUuid_C(String uuid, long companyId,
		int start, int end,
		OrderByComparator<CPDefinitionMedia> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition medias where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionMediaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition medias
	 * @param end the upper bound of the range of cp definition medias (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp definition medias
	 */
	@Override
	public List<CPDefinitionMedia> findByUuid_C(String uuid, long companyId,
		int start, int end,
		OrderByComparator<CPDefinitionMedia> orderByComparator,
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

		List<CPDefinitionMedia> list = null;

		if (retrieveFromCache) {
			list = (List<CPDefinitionMedia>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionMedia cpDefinitionMedia : list) {
					if (!Objects.equals(uuid, cpDefinitionMedia.getUuid()) ||
							(companyId != cpDefinitionMedia.getCompanyId())) {
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

			query.append(_SQL_SELECT_CPDEFINITIONMEDIA_WHERE);

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
				query.append(CPDefinitionMediaModelImpl.ORDER_BY_JPQL);
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
					list = (List<CPDefinitionMedia>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPDefinitionMedia>)QueryUtil.list(q,
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
	 * Returns the first cp definition media in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition media
	 * @throws NoSuchCPDefinitionMediaException if a matching cp definition media could not be found
	 */
	@Override
	public CPDefinitionMedia findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<CPDefinitionMedia> orderByComparator)
		throws NoSuchCPDefinitionMediaException {
		CPDefinitionMedia cpDefinitionMedia = fetchByUuid_C_First(uuid,
				companyId, orderByComparator);

		if (cpDefinitionMedia != null) {
			return cpDefinitionMedia;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCPDefinitionMediaException(msg.toString());
	}

	/**
	 * Returns the first cp definition media in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition media, or <code>null</code> if a matching cp definition media could not be found
	 */
	@Override
	public CPDefinitionMedia fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<CPDefinitionMedia> orderByComparator) {
		List<CPDefinitionMedia> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition media in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition media
	 * @throws NoSuchCPDefinitionMediaException if a matching cp definition media could not be found
	 */
	@Override
	public CPDefinitionMedia findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<CPDefinitionMedia> orderByComparator)
		throws NoSuchCPDefinitionMediaException {
		CPDefinitionMedia cpDefinitionMedia = fetchByUuid_C_Last(uuid,
				companyId, orderByComparator);

		if (cpDefinitionMedia != null) {
			return cpDefinitionMedia;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCPDefinitionMediaException(msg.toString());
	}

	/**
	 * Returns the last cp definition media in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition media, or <code>null</code> if a matching cp definition media could not be found
	 */
	@Override
	public CPDefinitionMedia fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<CPDefinitionMedia> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionMedia> list = findByUuid_C(uuid, companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition medias before and after the current cp definition media in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CPDefinitionMediaId the primary key of the current cp definition media
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition media
	 * @throws NoSuchCPDefinitionMediaException if a cp definition media with the primary key could not be found
	 */
	@Override
	public CPDefinitionMedia[] findByUuid_C_PrevAndNext(
		long CPDefinitionMediaId, String uuid, long companyId,
		OrderByComparator<CPDefinitionMedia> orderByComparator)
		throws NoSuchCPDefinitionMediaException {
		CPDefinitionMedia cpDefinitionMedia = findByPrimaryKey(CPDefinitionMediaId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionMedia[] array = new CPDefinitionMediaImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, cpDefinitionMedia,
					uuid, companyId, orderByComparator, true);

			array[1] = cpDefinitionMedia;

			array[2] = getByUuid_C_PrevAndNext(session, cpDefinitionMedia,
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

	protected CPDefinitionMedia getByUuid_C_PrevAndNext(Session session,
		CPDefinitionMedia cpDefinitionMedia, String uuid, long companyId,
		OrderByComparator<CPDefinitionMedia> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_CPDEFINITIONMEDIA_WHERE);

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
			query.append(CPDefinitionMediaModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(cpDefinitionMedia);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPDefinitionMedia> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definition medias where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CPDefinitionMedia cpDefinitionMedia : findByUuid_C(uuid,
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpDefinitionMedia);
		}
	}

	/**
	 * Returns the number of cp definition medias where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp definition medias
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CPDEFINITIONMEDIA_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "cpDefinitionMedia.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "cpDefinitionMedia.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(cpDefinitionMedia.uuid IS NULL OR cpDefinitionMedia.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "cpDefinitionMedia.companyId = ?";

	public CPDefinitionMediaPersistenceImpl() {
		setModelClass(CPDefinitionMedia.class);

		try {
			Field field = ReflectionUtil.getDeclaredField(BasePersistenceImpl.class,
					"_dbColumnNames");

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
	 * Caches the cp definition media in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionMedia the cp definition media
	 */
	@Override
	public void cacheResult(CPDefinitionMedia cpDefinitionMedia) {
		entityCache.putResult(CPDefinitionMediaModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionMediaImpl.class, cpDefinitionMedia.getPrimaryKey(),
			cpDefinitionMedia);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				cpDefinitionMedia.getUuid(), cpDefinitionMedia.getGroupId()
			}, cpDefinitionMedia);

		cpDefinitionMedia.resetOriginalValues();
	}

	/**
	 * Caches the cp definition medias in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionMedias the cp definition medias
	 */
	@Override
	public void cacheResult(List<CPDefinitionMedia> cpDefinitionMedias) {
		for (CPDefinitionMedia cpDefinitionMedia : cpDefinitionMedias) {
			if (entityCache.getResult(
						CPDefinitionMediaModelImpl.ENTITY_CACHE_ENABLED,
						CPDefinitionMediaImpl.class,
						cpDefinitionMedia.getPrimaryKey()) == null) {
				cacheResult(cpDefinitionMedia);
			}
			else {
				cpDefinitionMedia.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all cp definition medias.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CPDefinitionMediaImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the cp definition media.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CPDefinitionMedia cpDefinitionMedia) {
		entityCache.removeResult(CPDefinitionMediaModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionMediaImpl.class, cpDefinitionMedia.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CPDefinitionMediaModelImpl)cpDefinitionMedia,
			true);
	}

	@Override
	public void clearCache(List<CPDefinitionMedia> cpDefinitionMedias) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CPDefinitionMedia cpDefinitionMedia : cpDefinitionMedias) {
			entityCache.removeResult(CPDefinitionMediaModelImpl.ENTITY_CACHE_ENABLED,
				CPDefinitionMediaImpl.class, cpDefinitionMedia.getPrimaryKey());

			clearUniqueFindersCache((CPDefinitionMediaModelImpl)cpDefinitionMedia,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		CPDefinitionMediaModelImpl cpDefinitionMediaModelImpl) {
		Object[] args = new Object[] {
				cpDefinitionMediaModelImpl.getUuid(),
				cpDefinitionMediaModelImpl.getGroupId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
			cpDefinitionMediaModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CPDefinitionMediaModelImpl cpDefinitionMediaModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					cpDefinitionMediaModelImpl.getUuid(),
					cpDefinitionMediaModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if ((cpDefinitionMediaModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					cpDefinitionMediaModelImpl.getOriginalUuid(),
					cpDefinitionMediaModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}
	}

	/**
	 * Creates a new cp definition media with the primary key. Does not add the cp definition media to the database.
	 *
	 * @param CPDefinitionMediaId the primary key for the new cp definition media
	 * @return the new cp definition media
	 */
	@Override
	public CPDefinitionMedia create(long CPDefinitionMediaId) {
		CPDefinitionMedia cpDefinitionMedia = new CPDefinitionMediaImpl();

		cpDefinitionMedia.setNew(true);
		cpDefinitionMedia.setPrimaryKey(CPDefinitionMediaId);

		String uuid = PortalUUIDUtil.generate();

		cpDefinitionMedia.setUuid(uuid);

		cpDefinitionMedia.setCompanyId(companyProvider.getCompanyId());

		return cpDefinitionMedia;
	}

	/**
	 * Removes the cp definition media with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPDefinitionMediaId the primary key of the cp definition media
	 * @return the cp definition media that was removed
	 * @throws NoSuchCPDefinitionMediaException if a cp definition media with the primary key could not be found
	 */
	@Override
	public CPDefinitionMedia remove(long CPDefinitionMediaId)
		throws NoSuchCPDefinitionMediaException {
		return remove((Serializable)CPDefinitionMediaId);
	}

	/**
	 * Removes the cp definition media with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cp definition media
	 * @return the cp definition media that was removed
	 * @throws NoSuchCPDefinitionMediaException if a cp definition media with the primary key could not be found
	 */
	@Override
	public CPDefinitionMedia remove(Serializable primaryKey)
		throws NoSuchCPDefinitionMediaException {
		Session session = null;

		try {
			session = openSession();

			CPDefinitionMedia cpDefinitionMedia = (CPDefinitionMedia)session.get(CPDefinitionMediaImpl.class,
					primaryKey);

			if (cpDefinitionMedia == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCPDefinitionMediaException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(cpDefinitionMedia);
		}
		catch (NoSuchCPDefinitionMediaException nsee) {
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
	protected CPDefinitionMedia removeImpl(CPDefinitionMedia cpDefinitionMedia) {
		cpDefinitionMedia = toUnwrappedModel(cpDefinitionMedia);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpDefinitionMedia)) {
				cpDefinitionMedia = (CPDefinitionMedia)session.get(CPDefinitionMediaImpl.class,
						cpDefinitionMedia.getPrimaryKeyObj());
			}

			if (cpDefinitionMedia != null) {
				session.delete(cpDefinitionMedia);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (cpDefinitionMedia != null) {
			clearCache(cpDefinitionMedia);
		}

		return cpDefinitionMedia;
	}

	@Override
	public CPDefinitionMedia updateImpl(CPDefinitionMedia cpDefinitionMedia) {
		cpDefinitionMedia = toUnwrappedModel(cpDefinitionMedia);

		boolean isNew = cpDefinitionMedia.isNew();

		CPDefinitionMediaModelImpl cpDefinitionMediaModelImpl = (CPDefinitionMediaModelImpl)cpDefinitionMedia;

		if (Validator.isNull(cpDefinitionMedia.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			cpDefinitionMedia.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (cpDefinitionMedia.getCreateDate() == null)) {
			if (serviceContext == null) {
				cpDefinitionMedia.setCreateDate(now);
			}
			else {
				cpDefinitionMedia.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!cpDefinitionMediaModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cpDefinitionMedia.setModifiedDate(now);
			}
			else {
				cpDefinitionMedia.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (cpDefinitionMedia.isNew()) {
				session.save(cpDefinitionMedia);

				cpDefinitionMedia.setNew(false);
			}
			else {
				cpDefinitionMedia = (CPDefinitionMedia)session.merge(cpDefinitionMedia);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CPDefinitionMediaModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] { cpDefinitionMediaModelImpl.getUuid() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
				args);

			args = new Object[] {
					cpDefinitionMediaModelImpl.getUuid(),
					cpDefinitionMediaModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((cpDefinitionMediaModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpDefinitionMediaModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { cpDefinitionMediaModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((cpDefinitionMediaModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpDefinitionMediaModelImpl.getOriginalUuid(),
						cpDefinitionMediaModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						cpDefinitionMediaModelImpl.getUuid(),
						cpDefinitionMediaModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}
		}

		entityCache.putResult(CPDefinitionMediaModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionMediaImpl.class, cpDefinitionMedia.getPrimaryKey(),
			cpDefinitionMedia, false);

		clearUniqueFindersCache(cpDefinitionMediaModelImpl, false);
		cacheUniqueFindersCache(cpDefinitionMediaModelImpl);

		cpDefinitionMedia.resetOriginalValues();

		return cpDefinitionMedia;
	}

	protected CPDefinitionMedia toUnwrappedModel(
		CPDefinitionMedia cpDefinitionMedia) {
		if (cpDefinitionMedia instanceof CPDefinitionMediaImpl) {
			return cpDefinitionMedia;
		}

		CPDefinitionMediaImpl cpDefinitionMediaImpl = new CPDefinitionMediaImpl();

		cpDefinitionMediaImpl.setNew(cpDefinitionMedia.isNew());
		cpDefinitionMediaImpl.setPrimaryKey(cpDefinitionMedia.getPrimaryKey());

		cpDefinitionMediaImpl.setUuid(cpDefinitionMedia.getUuid());
		cpDefinitionMediaImpl.setCPDefinitionMediaId(cpDefinitionMedia.getCPDefinitionMediaId());
		cpDefinitionMediaImpl.setGroupId(cpDefinitionMedia.getGroupId());
		cpDefinitionMediaImpl.setCompanyId(cpDefinitionMedia.getCompanyId());
		cpDefinitionMediaImpl.setUserId(cpDefinitionMedia.getUserId());
		cpDefinitionMediaImpl.setUserName(cpDefinitionMedia.getUserName());
		cpDefinitionMediaImpl.setCreateDate(cpDefinitionMedia.getCreateDate());
		cpDefinitionMediaImpl.setModifiedDate(cpDefinitionMedia.getModifiedDate());
		cpDefinitionMediaImpl.setCPDefinitionId(cpDefinitionMedia.getCPDefinitionId());
		cpDefinitionMediaImpl.setFileEntryId(cpDefinitionMedia.getFileEntryId());
		cpDefinitionMediaImpl.setDDMContent(cpDefinitionMedia.getDDMContent());
		cpDefinitionMediaImpl.setPriority(cpDefinitionMedia.getPriority());
		cpDefinitionMediaImpl.setCPMediaTypeId(cpDefinitionMedia.getCPMediaTypeId());

		return cpDefinitionMediaImpl;
	}

	/**
	 * Returns the cp definition media with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp definition media
	 * @return the cp definition media
	 * @throws NoSuchCPDefinitionMediaException if a cp definition media with the primary key could not be found
	 */
	@Override
	public CPDefinitionMedia findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCPDefinitionMediaException {
		CPDefinitionMedia cpDefinitionMedia = fetchByPrimaryKey(primaryKey);

		if (cpDefinitionMedia == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCPDefinitionMediaException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return cpDefinitionMedia;
	}

	/**
	 * Returns the cp definition media with the primary key or throws a {@link NoSuchCPDefinitionMediaException} if it could not be found.
	 *
	 * @param CPDefinitionMediaId the primary key of the cp definition media
	 * @return the cp definition media
	 * @throws NoSuchCPDefinitionMediaException if a cp definition media with the primary key could not be found
	 */
	@Override
	public CPDefinitionMedia findByPrimaryKey(long CPDefinitionMediaId)
		throws NoSuchCPDefinitionMediaException {
		return findByPrimaryKey((Serializable)CPDefinitionMediaId);
	}

	/**
	 * Returns the cp definition media with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp definition media
	 * @return the cp definition media, or <code>null</code> if a cp definition media with the primary key could not be found
	 */
	@Override
	public CPDefinitionMedia fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CPDefinitionMediaModelImpl.ENTITY_CACHE_ENABLED,
				CPDefinitionMediaImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CPDefinitionMedia cpDefinitionMedia = (CPDefinitionMedia)serializable;

		if (cpDefinitionMedia == null) {
			Session session = null;

			try {
				session = openSession();

				cpDefinitionMedia = (CPDefinitionMedia)session.get(CPDefinitionMediaImpl.class,
						primaryKey);

				if (cpDefinitionMedia != null) {
					cacheResult(cpDefinitionMedia);
				}
				else {
					entityCache.putResult(CPDefinitionMediaModelImpl.ENTITY_CACHE_ENABLED,
						CPDefinitionMediaImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CPDefinitionMediaModelImpl.ENTITY_CACHE_ENABLED,
					CPDefinitionMediaImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return cpDefinitionMedia;
	}

	/**
	 * Returns the cp definition media with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPDefinitionMediaId the primary key of the cp definition media
	 * @return the cp definition media, or <code>null</code> if a cp definition media with the primary key could not be found
	 */
	@Override
	public CPDefinitionMedia fetchByPrimaryKey(long CPDefinitionMediaId) {
		return fetchByPrimaryKey((Serializable)CPDefinitionMediaId);
	}

	@Override
	public Map<Serializable, CPDefinitionMedia> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CPDefinitionMedia> map = new HashMap<Serializable, CPDefinitionMedia>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CPDefinitionMedia cpDefinitionMedia = fetchByPrimaryKey(primaryKey);

			if (cpDefinitionMedia != null) {
				map.put(primaryKey, cpDefinitionMedia);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CPDefinitionMediaModelImpl.ENTITY_CACHE_ENABLED,
					CPDefinitionMediaImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CPDefinitionMedia)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_CPDEFINITIONMEDIA_WHERE_PKS_IN);

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

			for (CPDefinitionMedia cpDefinitionMedia : (List<CPDefinitionMedia>)q.list()) {
				map.put(cpDefinitionMedia.getPrimaryKeyObj(), cpDefinitionMedia);

				cacheResult(cpDefinitionMedia);

				uncachedPrimaryKeys.remove(cpDefinitionMedia.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CPDefinitionMediaModelImpl.ENTITY_CACHE_ENABLED,
					CPDefinitionMediaImpl.class, primaryKey, nullModel);
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
	 * Returns all the cp definition medias.
	 *
	 * @return the cp definition medias
	 */
	@Override
	public List<CPDefinitionMedia> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition medias.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionMediaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition medias
	 * @param end the upper bound of the range of cp definition medias (not inclusive)
	 * @return the range of cp definition medias
	 */
	@Override
	public List<CPDefinitionMedia> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition medias.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionMediaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition medias
	 * @param end the upper bound of the range of cp definition medias (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp definition medias
	 */
	@Override
	public List<CPDefinitionMedia> findAll(int start, int end,
		OrderByComparator<CPDefinitionMedia> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition medias.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionMediaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition medias
	 * @param end the upper bound of the range of cp definition medias (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of cp definition medias
	 */
	@Override
	public List<CPDefinitionMedia> findAll(int start, int end,
		OrderByComparator<CPDefinitionMedia> orderByComparator,
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

		List<CPDefinitionMedia> list = null;

		if (retrieveFromCache) {
			list = (List<CPDefinitionMedia>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CPDEFINITIONMEDIA);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CPDEFINITIONMEDIA;

				if (pagination) {
					sql = sql.concat(CPDefinitionMediaModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CPDefinitionMedia>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPDefinitionMedia>)QueryUtil.list(q,
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
	 * Removes all the cp definition medias from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CPDefinitionMedia cpDefinitionMedia : findAll()) {
			remove(cpDefinitionMedia);
		}
	}

	/**
	 * Returns the number of cp definition medias.
	 *
	 * @return the number of cp definition medias
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CPDEFINITIONMEDIA);

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
		return CPDefinitionMediaModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the cp definition media persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CPDefinitionMediaImpl.class.getName());
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
	private static final String _SQL_SELECT_CPDEFINITIONMEDIA = "SELECT cpDefinitionMedia FROM CPDefinitionMedia cpDefinitionMedia";
	private static final String _SQL_SELECT_CPDEFINITIONMEDIA_WHERE_PKS_IN = "SELECT cpDefinitionMedia FROM CPDefinitionMedia cpDefinitionMedia WHERE CPDefinitionMediaId IN (";
	private static final String _SQL_SELECT_CPDEFINITIONMEDIA_WHERE = "SELECT cpDefinitionMedia FROM CPDefinitionMedia cpDefinitionMedia WHERE ";
	private static final String _SQL_COUNT_CPDEFINITIONMEDIA = "SELECT COUNT(cpDefinitionMedia) FROM CPDefinitionMedia cpDefinitionMedia";
	private static final String _SQL_COUNT_CPDEFINITIONMEDIA_WHERE = "SELECT COUNT(cpDefinitionMedia) FROM CPDefinitionMedia cpDefinitionMedia WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "cpDefinitionMedia.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CPDefinitionMedia exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CPDefinitionMedia exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CPDefinitionMediaPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid"
			});
}