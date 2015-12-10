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

package com.liferay.wiki.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

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

import com.liferay.wiki.exception.NoSuchPageResourceException;
import com.liferay.wiki.model.WikiPageResource;
import com.liferay.wiki.model.impl.WikiPageResourceImpl;
import com.liferay.wiki.model.impl.WikiPageResourceModelImpl;
import com.liferay.wiki.service.persistence.WikiPageResourcePersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the wiki page resource service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WikiPageResourcePersistence
 * @see com.liferay.wiki.service.persistence.WikiPageResourceUtil
 * @generated
 */
@ProviderType
public class WikiPageResourcePersistenceImpl extends BasePersistenceImpl<WikiPageResource>
	implements WikiPageResourcePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link WikiPageResourceUtil} to access the wiki page resource persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = WikiPageResourceImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(WikiPageResourceModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageResourceModelImpl.FINDER_CACHE_ENABLED,
			WikiPageResourceImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(WikiPageResourceModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageResourceModelImpl.FINDER_CACHE_ENABLED,
			WikiPageResourceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(WikiPageResourceModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageResourceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(WikiPageResourceModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageResourceModelImpl.FINDER_CACHE_ENABLED,
			WikiPageResourceImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(WikiPageResourceModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageResourceModelImpl.FINDER_CACHE_ENABLED,
			WikiPageResourceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			WikiPageResourceModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(WikiPageResourceModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageResourceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the wiki page resources where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching wiki page resources
	 */
	@Override
	public List<WikiPageResource> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the wiki page resources where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of wiki page resources
	 * @param end the upper bound of the range of wiki page resources (not inclusive)
	 * @return the range of matching wiki page resources
	 */
	@Override
	public List<WikiPageResource> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the wiki page resources where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of wiki page resources
	 * @param end the upper bound of the range of wiki page resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki page resources
	 */
	@Override
	public List<WikiPageResource> findByUuid(String uuid, int start, int end,
		OrderByComparator<WikiPageResource> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the wiki page resources where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of wiki page resources
	 * @param end the upper bound of the range of wiki page resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching wiki page resources
	 */
	@Override
	public List<WikiPageResource> findByUuid(String uuid, int start, int end,
		OrderByComparator<WikiPageResource> orderByComparator,
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

		List<WikiPageResource> list = null;

		if (retrieveFromCache) {
			list = (List<WikiPageResource>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WikiPageResource wikiPageResource : list) {
					if (!Validator.equals(uuid, wikiPageResource.getUuid())) {
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

			query.append(_SQL_SELECT_WIKIPAGERESOURCE_WHERE);

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
				query.append(WikiPageResourceModelImpl.ORDER_BY_JPQL);
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
					list = (List<WikiPageResource>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WikiPageResource>)QueryUtil.list(q,
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
	 * Returns the first wiki page resource in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page resource
	 * @throws com.liferay.wiki.NoSuchPageResourceException if a matching wiki page resource could not be found
	 */
	@Override
	public WikiPageResource findByUuid_First(String uuid,
		OrderByComparator<WikiPageResource> orderByComparator)
		throws NoSuchPageResourceException {
		WikiPageResource wikiPageResource = fetchByUuid_First(uuid,
				orderByComparator);

		if (wikiPageResource != null) {
			return wikiPageResource;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageResourceException(msg.toString());
	}

	/**
	 * Returns the first wiki page resource in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page resource, or <code>null</code> if a matching wiki page resource could not be found
	 */
	@Override
	public WikiPageResource fetchByUuid_First(String uuid,
		OrderByComparator<WikiPageResource> orderByComparator) {
		List<WikiPageResource> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last wiki page resource in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page resource
	 * @throws com.liferay.wiki.NoSuchPageResourceException if a matching wiki page resource could not be found
	 */
	@Override
	public WikiPageResource findByUuid_Last(String uuid,
		OrderByComparator<WikiPageResource> orderByComparator)
		throws NoSuchPageResourceException {
		WikiPageResource wikiPageResource = fetchByUuid_Last(uuid,
				orderByComparator);

		if (wikiPageResource != null) {
			return wikiPageResource;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageResourceException(msg.toString());
	}

	/**
	 * Returns the last wiki page resource in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page resource, or <code>null</code> if a matching wiki page resource could not be found
	 */
	@Override
	public WikiPageResource fetchByUuid_Last(String uuid,
		OrderByComparator<WikiPageResource> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<WikiPageResource> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the wiki page resources before and after the current wiki page resource in the ordered set where uuid = &#63;.
	 *
	 * @param resourcePrimKey the primary key of the current wiki page resource
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page resource
	 * @throws com.liferay.wiki.NoSuchPageResourceException if a wiki page resource with the primary key could not be found
	 */
	@Override
	public WikiPageResource[] findByUuid_PrevAndNext(long resourcePrimKey,
		String uuid, OrderByComparator<WikiPageResource> orderByComparator)
		throws NoSuchPageResourceException {
		WikiPageResource wikiPageResource = findByPrimaryKey(resourcePrimKey);

		Session session = null;

		try {
			session = openSession();

			WikiPageResource[] array = new WikiPageResourceImpl[3];

			array[0] = getByUuid_PrevAndNext(session, wikiPageResource, uuid,
					orderByComparator, true);

			array[1] = wikiPageResource;

			array[2] = getByUuid_PrevAndNext(session, wikiPageResource, uuid,
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

	protected WikiPageResource getByUuid_PrevAndNext(Session session,
		WikiPageResource wikiPageResource, String uuid,
		OrderByComparator<WikiPageResource> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGERESOURCE_WHERE);

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
			query.append(WikiPageResourceModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(wikiPageResource);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPageResource> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the wiki page resources where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (WikiPageResource wikiPageResource : findByUuid(uuid,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(wikiPageResource);
		}
	}

	/**
	 * Returns the number of wiki page resources where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching wiki page resources
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_WIKIPAGERESOURCE_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "wikiPageResource.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "wikiPageResource.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(wikiPageResource.uuid IS NULL OR wikiPageResource.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(WikiPageResourceModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageResourceModelImpl.FINDER_CACHE_ENABLED,
			WikiPageResourceImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			WikiPageResourceModelImpl.UUID_COLUMN_BITMASK |
			WikiPageResourceModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(WikiPageResourceModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageResourceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the wiki page resource where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.wiki.NoSuchPageResourceException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching wiki page resource
	 * @throws com.liferay.wiki.NoSuchPageResourceException if a matching wiki page resource could not be found
	 */
	@Override
	public WikiPageResource findByUUID_G(String uuid, long groupId)
		throws NoSuchPageResourceException {
		WikiPageResource wikiPageResource = fetchByUUID_G(uuid, groupId);

		if (wikiPageResource == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchPageResourceException(msg.toString());
		}

		return wikiPageResource;
	}

	/**
	 * Returns the wiki page resource where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching wiki page resource, or <code>null</code> if a matching wiki page resource could not be found
	 */
	@Override
	public WikiPageResource fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the wiki page resource where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching wiki page resource, or <code>null</code> if a matching wiki page resource could not be found
	 */
	@Override
	public WikiPageResource fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof WikiPageResource) {
			WikiPageResource wikiPageResource = (WikiPageResource)result;

			if (!Validator.equals(uuid, wikiPageResource.getUuid()) ||
					(groupId != wikiPageResource.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_WIKIPAGERESOURCE_WHERE);

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

				List<WikiPageResource> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					WikiPageResource wikiPageResource = list.get(0);

					result = wikiPageResource;

					cacheResult(wikiPageResource);

					if ((wikiPageResource.getUuid() == null) ||
							!wikiPageResource.getUuid().equals(uuid) ||
							(wikiPageResource.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, wikiPageResource);
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
			return (WikiPageResource)result;
		}
	}

	/**
	 * Removes the wiki page resource where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the wiki page resource that was removed
	 */
	@Override
	public WikiPageResource removeByUUID_G(String uuid, long groupId)
		throws NoSuchPageResourceException {
		WikiPageResource wikiPageResource = findByUUID_G(uuid, groupId);

		return remove(wikiPageResource);
	}

	/**
	 * Returns the number of wiki page resources where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching wiki page resources
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_WIKIPAGERESOURCE_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "wikiPageResource.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "wikiPageResource.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(wikiPageResource.uuid IS NULL OR wikiPageResource.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "wikiPageResource.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(WikiPageResourceModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageResourceModelImpl.FINDER_CACHE_ENABLED,
			WikiPageResourceImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(WikiPageResourceModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageResourceModelImpl.FINDER_CACHE_ENABLED,
			WikiPageResourceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			WikiPageResourceModelImpl.UUID_COLUMN_BITMASK |
			WikiPageResourceModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(WikiPageResourceModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageResourceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the wiki page resources where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching wiki page resources
	 */
	@Override
	public List<WikiPageResource> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the wiki page resources where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of wiki page resources
	 * @param end the upper bound of the range of wiki page resources (not inclusive)
	 * @return the range of matching wiki page resources
	 */
	@Override
	public List<WikiPageResource> findByUuid_C(String uuid, long companyId,
		int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the wiki page resources where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of wiki page resources
	 * @param end the upper bound of the range of wiki page resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki page resources
	 */
	@Override
	public List<WikiPageResource> findByUuid_C(String uuid, long companyId,
		int start, int end,
		OrderByComparator<WikiPageResource> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the wiki page resources where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of wiki page resources
	 * @param end the upper bound of the range of wiki page resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching wiki page resources
	 */
	@Override
	public List<WikiPageResource> findByUuid_C(String uuid, long companyId,
		int start, int end,
		OrderByComparator<WikiPageResource> orderByComparator,
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

		List<WikiPageResource> list = null;

		if (retrieveFromCache) {
			list = (List<WikiPageResource>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WikiPageResource wikiPageResource : list) {
					if (!Validator.equals(uuid, wikiPageResource.getUuid()) ||
							(companyId != wikiPageResource.getCompanyId())) {
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

			query.append(_SQL_SELECT_WIKIPAGERESOURCE_WHERE);

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
				query.append(WikiPageResourceModelImpl.ORDER_BY_JPQL);
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
					list = (List<WikiPageResource>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WikiPageResource>)QueryUtil.list(q,
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
	 * Returns the first wiki page resource in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page resource
	 * @throws com.liferay.wiki.NoSuchPageResourceException if a matching wiki page resource could not be found
	 */
	@Override
	public WikiPageResource findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<WikiPageResource> orderByComparator)
		throws NoSuchPageResourceException {
		WikiPageResource wikiPageResource = fetchByUuid_C_First(uuid,
				companyId, orderByComparator);

		if (wikiPageResource != null) {
			return wikiPageResource;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageResourceException(msg.toString());
	}

	/**
	 * Returns the first wiki page resource in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page resource, or <code>null</code> if a matching wiki page resource could not be found
	 */
	@Override
	public WikiPageResource fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<WikiPageResource> orderByComparator) {
		List<WikiPageResource> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last wiki page resource in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page resource
	 * @throws com.liferay.wiki.NoSuchPageResourceException if a matching wiki page resource could not be found
	 */
	@Override
	public WikiPageResource findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<WikiPageResource> orderByComparator)
		throws NoSuchPageResourceException {
		WikiPageResource wikiPageResource = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (wikiPageResource != null) {
			return wikiPageResource;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageResourceException(msg.toString());
	}

	/**
	 * Returns the last wiki page resource in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page resource, or <code>null</code> if a matching wiki page resource could not be found
	 */
	@Override
	public WikiPageResource fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<WikiPageResource> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<WikiPageResource> list = findByUuid_C(uuid, companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the wiki page resources before and after the current wiki page resource in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param resourcePrimKey the primary key of the current wiki page resource
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page resource
	 * @throws com.liferay.wiki.NoSuchPageResourceException if a wiki page resource with the primary key could not be found
	 */
	@Override
	public WikiPageResource[] findByUuid_C_PrevAndNext(long resourcePrimKey,
		String uuid, long companyId,
		OrderByComparator<WikiPageResource> orderByComparator)
		throws NoSuchPageResourceException {
		WikiPageResource wikiPageResource = findByPrimaryKey(resourcePrimKey);

		Session session = null;

		try {
			session = openSession();

			WikiPageResource[] array = new WikiPageResourceImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, wikiPageResource, uuid,
					companyId, orderByComparator, true);

			array[1] = wikiPageResource;

			array[2] = getByUuid_C_PrevAndNext(session, wikiPageResource, uuid,
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

	protected WikiPageResource getByUuid_C_PrevAndNext(Session session,
		WikiPageResource wikiPageResource, String uuid, long companyId,
		OrderByComparator<WikiPageResource> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGERESOURCE_WHERE);

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
			query.append(WikiPageResourceModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(wikiPageResource);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPageResource> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the wiki page resources where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (WikiPageResource wikiPageResource : findByUuid_C(uuid, companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(wikiPageResource);
		}
	}

	/**
	 * Returns the number of wiki page resources where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching wiki page resources
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_WIKIPAGERESOURCE_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "wikiPageResource.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "wikiPageResource.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(wikiPageResource.uuid IS NULL OR wikiPageResource.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "wikiPageResource.companyId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_N_T = new FinderPath(WikiPageResourceModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageResourceModelImpl.FINDER_CACHE_ENABLED,
			WikiPageResourceImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByN_T",
			new String[] { Long.class.getName(), String.class.getName() },
			WikiPageResourceModelImpl.NODEID_COLUMN_BITMASK |
			WikiPageResourceModelImpl.TITLE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_N_T = new FinderPath(WikiPageResourceModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageResourceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByN_T",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the wiki page resource where nodeId = &#63; and title = &#63; or throws a {@link com.liferay.wiki.NoSuchPageResourceException} if it could not be found.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @return the matching wiki page resource
	 * @throws com.liferay.wiki.NoSuchPageResourceException if a matching wiki page resource could not be found
	 */
	@Override
	public WikiPageResource findByN_T(long nodeId, String title)
		throws NoSuchPageResourceException {
		WikiPageResource wikiPageResource = fetchByN_T(nodeId, title);

		if (wikiPageResource == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("nodeId=");
			msg.append(nodeId);

			msg.append(", title=");
			msg.append(title);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchPageResourceException(msg.toString());
		}

		return wikiPageResource;
	}

	/**
	 * Returns the wiki page resource where nodeId = &#63; and title = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @return the matching wiki page resource, or <code>null</code> if a matching wiki page resource could not be found
	 */
	@Override
	public WikiPageResource fetchByN_T(long nodeId, String title) {
		return fetchByN_T(nodeId, title, true);
	}

	/**
	 * Returns the wiki page resource where nodeId = &#63; and title = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching wiki page resource, or <code>null</code> if a matching wiki page resource could not be found
	 */
	@Override
	public WikiPageResource fetchByN_T(long nodeId, String title,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { nodeId, title };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_N_T,
					finderArgs, this);
		}

		if (result instanceof WikiPageResource) {
			WikiPageResource wikiPageResource = (WikiPageResource)result;

			if ((nodeId != wikiPageResource.getNodeId()) ||
					!Validator.equals(title, wikiPageResource.getTitle())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_WIKIPAGERESOURCE_WHERE);

			query.append(_FINDER_COLUMN_N_T_NODEID_2);

			boolean bindTitle = false;

			if (title == null) {
				query.append(_FINDER_COLUMN_N_T_TITLE_1);
			}
			else if (title.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_N_T_TITLE_3);
			}
			else {
				bindTitle = true;

				query.append(_FINDER_COLUMN_N_T_TITLE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				if (bindTitle) {
					qPos.add(title);
				}

				List<WikiPageResource> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_N_T, finderArgs,
						list);
				}
				else {
					WikiPageResource wikiPageResource = list.get(0);

					result = wikiPageResource;

					cacheResult(wikiPageResource);

					if ((wikiPageResource.getNodeId() != nodeId) ||
							(wikiPageResource.getTitle() == null) ||
							!wikiPageResource.getTitle().equals(title)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_N_T,
							finderArgs, wikiPageResource);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_N_T, finderArgs);

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
			return (WikiPageResource)result;
		}
	}

	/**
	 * Removes the wiki page resource where nodeId = &#63; and title = &#63; from the database.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @return the wiki page resource that was removed
	 */
	@Override
	public WikiPageResource removeByN_T(long nodeId, String title)
		throws NoSuchPageResourceException {
		WikiPageResource wikiPageResource = findByN_T(nodeId, title);

		return remove(wikiPageResource);
	}

	/**
	 * Returns the number of wiki page resources where nodeId = &#63; and title = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @return the number of matching wiki page resources
	 */
	@Override
	public int countByN_T(long nodeId, String title) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_N_T;

		Object[] finderArgs = new Object[] { nodeId, title };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_WIKIPAGERESOURCE_WHERE);

			query.append(_FINDER_COLUMN_N_T_NODEID_2);

			boolean bindTitle = false;

			if (title == null) {
				query.append(_FINDER_COLUMN_N_T_TITLE_1);
			}
			else if (title.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_N_T_TITLE_3);
			}
			else {
				bindTitle = true;

				query.append(_FINDER_COLUMN_N_T_TITLE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				if (bindTitle) {
					qPos.add(title);
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

	private static final String _FINDER_COLUMN_N_T_NODEID_2 = "wikiPageResource.nodeId = ? AND ";
	private static final String _FINDER_COLUMN_N_T_TITLE_1 = "wikiPageResource.title IS NULL";
	private static final String _FINDER_COLUMN_N_T_TITLE_2 = "wikiPageResource.title = ?";
	private static final String _FINDER_COLUMN_N_T_TITLE_3 = "(wikiPageResource.title IS NULL OR wikiPageResource.title = '')";

	public WikiPageResourcePersistenceImpl() {
		setModelClass(WikiPageResource.class);
	}

	/**
	 * Caches the wiki page resource in the entity cache if it is enabled.
	 *
	 * @param wikiPageResource the wiki page resource
	 */
	@Override
	public void cacheResult(WikiPageResource wikiPageResource) {
		entityCache.putResult(WikiPageResourceModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageResourceImpl.class, wikiPageResource.getPrimaryKey(),
			wikiPageResource);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				wikiPageResource.getUuid(), wikiPageResource.getGroupId()
			}, wikiPageResource);

		finderCache.putResult(FINDER_PATH_FETCH_BY_N_T,
			new Object[] {
				wikiPageResource.getNodeId(), wikiPageResource.getTitle()
			}, wikiPageResource);

		wikiPageResource.resetOriginalValues();
	}

	/**
	 * Caches the wiki page resources in the entity cache if it is enabled.
	 *
	 * @param wikiPageResources the wiki page resources
	 */
	@Override
	public void cacheResult(List<WikiPageResource> wikiPageResources) {
		for (WikiPageResource wikiPageResource : wikiPageResources) {
			if (entityCache.getResult(
						WikiPageResourceModelImpl.ENTITY_CACHE_ENABLED,
						WikiPageResourceImpl.class,
						wikiPageResource.getPrimaryKey()) == null) {
				cacheResult(wikiPageResource);
			}
			else {
				wikiPageResource.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all wiki page resources.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(WikiPageResourceImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the wiki page resource.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(WikiPageResource wikiPageResource) {
		entityCache.removeResult(WikiPageResourceModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageResourceImpl.class, wikiPageResource.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((WikiPageResourceModelImpl)wikiPageResource);
	}

	@Override
	public void clearCache(List<WikiPageResource> wikiPageResources) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (WikiPageResource wikiPageResource : wikiPageResources) {
			entityCache.removeResult(WikiPageResourceModelImpl.ENTITY_CACHE_ENABLED,
				WikiPageResourceImpl.class, wikiPageResource.getPrimaryKey());

			clearUniqueFindersCache((WikiPageResourceModelImpl)wikiPageResource);
		}
	}

	protected void cacheUniqueFindersCache(
		WikiPageResourceModelImpl wikiPageResourceModelImpl, boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] {
					wikiPageResourceModelImpl.getUuid(),
					wikiPageResourceModelImpl.getGroupId()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
				wikiPageResourceModelImpl);

			args = new Object[] {
					wikiPageResourceModelImpl.getNodeId(),
					wikiPageResourceModelImpl.getTitle()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_N_T, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_N_T, args,
				wikiPageResourceModelImpl);
		}
		else {
			if ((wikiPageResourceModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						wikiPageResourceModelImpl.getUuid(),
						wikiPageResourceModelImpl.getGroupId()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
					wikiPageResourceModelImpl);
			}

			if ((wikiPageResourceModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_N_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						wikiPageResourceModelImpl.getNodeId(),
						wikiPageResourceModelImpl.getTitle()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_N_T, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_N_T, args,
					wikiPageResourceModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(
		WikiPageResourceModelImpl wikiPageResourceModelImpl) {
		Object[] args = new Object[] {
				wikiPageResourceModelImpl.getUuid(),
				wikiPageResourceModelImpl.getGroupId()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

		if ((wikiPageResourceModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			args = new Object[] {
					wikiPageResourceModelImpl.getOriginalUuid(),
					wikiPageResourceModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		args = new Object[] {
				wikiPageResourceModelImpl.getNodeId(),
				wikiPageResourceModelImpl.getTitle()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_N_T, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_N_T, args);

		if ((wikiPageResourceModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_N_T.getColumnBitmask()) != 0) {
			args = new Object[] {
					wikiPageResourceModelImpl.getOriginalNodeId(),
					wikiPageResourceModelImpl.getOriginalTitle()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_N_T, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_N_T, args);
		}
	}

	/**
	 * Creates a new wiki page resource with the primary key. Does not add the wiki page resource to the database.
	 *
	 * @param resourcePrimKey the primary key for the new wiki page resource
	 * @return the new wiki page resource
	 */
	@Override
	public WikiPageResource create(long resourcePrimKey) {
		WikiPageResource wikiPageResource = new WikiPageResourceImpl();

		wikiPageResource.setNew(true);
		wikiPageResource.setPrimaryKey(resourcePrimKey);

		String uuid = PortalUUIDUtil.generate();

		wikiPageResource.setUuid(uuid);

		wikiPageResource.setCompanyId(companyProvider.getCompanyId());

		return wikiPageResource;
	}

	/**
	 * Removes the wiki page resource with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param resourcePrimKey the primary key of the wiki page resource
	 * @return the wiki page resource that was removed
	 * @throws com.liferay.wiki.NoSuchPageResourceException if a wiki page resource with the primary key could not be found
	 */
	@Override
	public WikiPageResource remove(long resourcePrimKey)
		throws NoSuchPageResourceException {
		return remove((Serializable)resourcePrimKey);
	}

	/**
	 * Removes the wiki page resource with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the wiki page resource
	 * @return the wiki page resource that was removed
	 * @throws com.liferay.wiki.NoSuchPageResourceException if a wiki page resource with the primary key could not be found
	 */
	@Override
	public WikiPageResource remove(Serializable primaryKey)
		throws NoSuchPageResourceException {
		Session session = null;

		try {
			session = openSession();

			WikiPageResource wikiPageResource = (WikiPageResource)session.get(WikiPageResourceImpl.class,
					primaryKey);

			if (wikiPageResource == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPageResourceException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(wikiPageResource);
		}
		catch (NoSuchPageResourceException nsee) {
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
	protected WikiPageResource removeImpl(WikiPageResource wikiPageResource) {
		wikiPageResource = toUnwrappedModel(wikiPageResource);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(wikiPageResource)) {
				wikiPageResource = (WikiPageResource)session.get(WikiPageResourceImpl.class,
						wikiPageResource.getPrimaryKeyObj());
			}

			if (wikiPageResource != null) {
				session.delete(wikiPageResource);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (wikiPageResource != null) {
			clearCache(wikiPageResource);
		}

		return wikiPageResource;
	}

	@Override
	public WikiPageResource updateImpl(WikiPageResource wikiPageResource) {
		wikiPageResource = toUnwrappedModel(wikiPageResource);

		boolean isNew = wikiPageResource.isNew();

		WikiPageResourceModelImpl wikiPageResourceModelImpl = (WikiPageResourceModelImpl)wikiPageResource;

		if (Validator.isNull(wikiPageResource.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			wikiPageResource.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (wikiPageResource.isNew()) {
				session.save(wikiPageResource);

				wikiPageResource.setNew(false);
			}
			else {
				wikiPageResource = (WikiPageResource)session.merge(wikiPageResource);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !WikiPageResourceModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((wikiPageResourceModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						wikiPageResourceModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { wikiPageResourceModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((wikiPageResourceModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						wikiPageResourceModelImpl.getOriginalUuid(),
						wikiPageResourceModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						wikiPageResourceModelImpl.getUuid(),
						wikiPageResourceModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}
		}

		entityCache.putResult(WikiPageResourceModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageResourceImpl.class, wikiPageResource.getPrimaryKey(),
			wikiPageResource, false);

		clearUniqueFindersCache(wikiPageResourceModelImpl);
		cacheUniqueFindersCache(wikiPageResourceModelImpl, isNew);

		wikiPageResource.resetOriginalValues();

		return wikiPageResource;
	}

	protected WikiPageResource toUnwrappedModel(
		WikiPageResource wikiPageResource) {
		if (wikiPageResource instanceof WikiPageResourceImpl) {
			return wikiPageResource;
		}

		WikiPageResourceImpl wikiPageResourceImpl = new WikiPageResourceImpl();

		wikiPageResourceImpl.setNew(wikiPageResource.isNew());
		wikiPageResourceImpl.setPrimaryKey(wikiPageResource.getPrimaryKey());

		wikiPageResourceImpl.setUuid(wikiPageResource.getUuid());
		wikiPageResourceImpl.setResourcePrimKey(wikiPageResource.getResourcePrimKey());
		wikiPageResourceImpl.setGroupId(wikiPageResource.getGroupId());
		wikiPageResourceImpl.setCompanyId(wikiPageResource.getCompanyId());
		wikiPageResourceImpl.setNodeId(wikiPageResource.getNodeId());
		wikiPageResourceImpl.setTitle(wikiPageResource.getTitle());

		return wikiPageResourceImpl;
	}

	/**
	 * Returns the wiki page resource with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the wiki page resource
	 * @return the wiki page resource
	 * @throws com.liferay.wiki.NoSuchPageResourceException if a wiki page resource with the primary key could not be found
	 */
	@Override
	public WikiPageResource findByPrimaryKey(Serializable primaryKey)
		throws NoSuchPageResourceException {
		WikiPageResource wikiPageResource = fetchByPrimaryKey(primaryKey);

		if (wikiPageResource == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPageResourceException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return wikiPageResource;
	}

	/**
	 * Returns the wiki page resource with the primary key or throws a {@link com.liferay.wiki.NoSuchPageResourceException} if it could not be found.
	 *
	 * @param resourcePrimKey the primary key of the wiki page resource
	 * @return the wiki page resource
	 * @throws com.liferay.wiki.NoSuchPageResourceException if a wiki page resource with the primary key could not be found
	 */
	@Override
	public WikiPageResource findByPrimaryKey(long resourcePrimKey)
		throws NoSuchPageResourceException {
		return findByPrimaryKey((Serializable)resourcePrimKey);
	}

	/**
	 * Returns the wiki page resource with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the wiki page resource
	 * @return the wiki page resource, or <code>null</code> if a wiki page resource with the primary key could not be found
	 */
	@Override
	public WikiPageResource fetchByPrimaryKey(Serializable primaryKey) {
		WikiPageResource wikiPageResource = (WikiPageResource)entityCache.getResult(WikiPageResourceModelImpl.ENTITY_CACHE_ENABLED,
				WikiPageResourceImpl.class, primaryKey);

		if (wikiPageResource == _nullWikiPageResource) {
			return null;
		}

		if (wikiPageResource == null) {
			Session session = null;

			try {
				session = openSession();

				wikiPageResource = (WikiPageResource)session.get(WikiPageResourceImpl.class,
						primaryKey);

				if (wikiPageResource != null) {
					cacheResult(wikiPageResource);
				}
				else {
					entityCache.putResult(WikiPageResourceModelImpl.ENTITY_CACHE_ENABLED,
						WikiPageResourceImpl.class, primaryKey,
						_nullWikiPageResource);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(WikiPageResourceModelImpl.ENTITY_CACHE_ENABLED,
					WikiPageResourceImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return wikiPageResource;
	}

	/**
	 * Returns the wiki page resource with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param resourcePrimKey the primary key of the wiki page resource
	 * @return the wiki page resource, or <code>null</code> if a wiki page resource with the primary key could not be found
	 */
	@Override
	public WikiPageResource fetchByPrimaryKey(long resourcePrimKey) {
		return fetchByPrimaryKey((Serializable)resourcePrimKey);
	}

	@Override
	public Map<Serializable, WikiPageResource> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, WikiPageResource> map = new HashMap<Serializable, WikiPageResource>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			WikiPageResource wikiPageResource = fetchByPrimaryKey(primaryKey);

			if (wikiPageResource != null) {
				map.put(primaryKey, wikiPageResource);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			WikiPageResource wikiPageResource = (WikiPageResource)entityCache.getResult(WikiPageResourceModelImpl.ENTITY_CACHE_ENABLED,
					WikiPageResourceImpl.class, primaryKey);

			if (wikiPageResource == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, wikiPageResource);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_WIKIPAGERESOURCE_WHERE_PKS_IN);

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

			for (WikiPageResource wikiPageResource : (List<WikiPageResource>)q.list()) {
				map.put(wikiPageResource.getPrimaryKeyObj(), wikiPageResource);

				cacheResult(wikiPageResource);

				uncachedPrimaryKeys.remove(wikiPageResource.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(WikiPageResourceModelImpl.ENTITY_CACHE_ENABLED,
					WikiPageResourceImpl.class, primaryKey,
					_nullWikiPageResource);
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
	 * Returns all the wiki page resources.
	 *
	 * @return the wiki page resources
	 */
	@Override
	public List<WikiPageResource> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the wiki page resources.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of wiki page resources
	 * @param end the upper bound of the range of wiki page resources (not inclusive)
	 * @return the range of wiki page resources
	 */
	@Override
	public List<WikiPageResource> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the wiki page resources.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of wiki page resources
	 * @param end the upper bound of the range of wiki page resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of wiki page resources
	 */
	@Override
	public List<WikiPageResource> findAll(int start, int end,
		OrderByComparator<WikiPageResource> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the wiki page resources.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of wiki page resources
	 * @param end the upper bound of the range of wiki page resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of wiki page resources
	 */
	@Override
	public List<WikiPageResource> findAll(int start, int end,
		OrderByComparator<WikiPageResource> orderByComparator,
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

		List<WikiPageResource> list = null;

		if (retrieveFromCache) {
			list = (List<WikiPageResource>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_WIKIPAGERESOURCE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_WIKIPAGERESOURCE;

				if (pagination) {
					sql = sql.concat(WikiPageResourceModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<WikiPageResource>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WikiPageResource>)QueryUtil.list(q,
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
	 * Removes all the wiki page resources from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (WikiPageResource wikiPageResource : findAll()) {
			remove(wikiPageResource);
		}
	}

	/**
	 * Returns the number of wiki page resources.
	 *
	 * @return the number of wiki page resources
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_WIKIPAGERESOURCE);

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
		return WikiPageResourceModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the wiki page resource persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(WikiPageResourceImpl.class.getName());
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
	private static final String _SQL_SELECT_WIKIPAGERESOURCE = "SELECT wikiPageResource FROM WikiPageResource wikiPageResource";
	private static final String _SQL_SELECT_WIKIPAGERESOURCE_WHERE_PKS_IN = "SELECT wikiPageResource FROM WikiPageResource wikiPageResource WHERE resourcePrimKey IN (";
	private static final String _SQL_SELECT_WIKIPAGERESOURCE_WHERE = "SELECT wikiPageResource FROM WikiPageResource wikiPageResource WHERE ";
	private static final String _SQL_COUNT_WIKIPAGERESOURCE = "SELECT COUNT(wikiPageResource) FROM WikiPageResource wikiPageResource";
	private static final String _SQL_COUNT_WIKIPAGERESOURCE_WHERE = "SELECT COUNT(wikiPageResource) FROM WikiPageResource wikiPageResource WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "wikiPageResource.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No WikiPageResource exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No WikiPageResource exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(WikiPageResourcePersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid"
			});
	private static final WikiPageResource _nullWikiPageResource = new WikiPageResourceImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<WikiPageResource> toCacheModel() {
				return _nullWikiPageResourceCacheModel;
			}
		};

	private static final CacheModel<WikiPageResource> _nullWikiPageResourceCacheModel =
		new CacheModel<WikiPageResource>() {
			@Override
			public WikiPageResource toEntityModel() {
				return _nullWikiPageResource;
			}
		};
}