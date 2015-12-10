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
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.persistence.CompanyProvider;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.spring.extender.service.ServiceReference;

import com.liferay.wiki.exception.NoSuchPageException;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.model.impl.WikiPageImpl;
import com.liferay.wiki.model.impl.WikiPageModelImpl;
import com.liferay.wiki.service.persistence.WikiPagePersistence;

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
 * The persistence implementation for the wiki page service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WikiPagePersistence
 * @see com.liferay.wiki.service.persistence.WikiPageUtil
 * @generated
 */
@ProviderType
public class WikiPagePersistenceImpl extends BasePersistenceImpl<WikiPage>
	implements WikiPagePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link WikiPageUtil} to access the wiki page persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = WikiPageImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			WikiPageModelImpl.UUID_COLUMN_BITMASK |
			WikiPageModelImpl.NODEID_COLUMN_BITMASK |
			WikiPageModelImpl.TITLE_COLUMN_BITMASK |
			WikiPageModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the wiki pages where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching wiki pages
	 */
	@Override
	public List<WikiPage> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the wiki pages where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the wiki pages where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByUuid(String uuid, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the wiki pages where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByUuid(String uuid, int start, int end,
		OrderByComparator<WikiPage> orderByComparator, boolean retrieveFromCache) {
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

		List<WikiPage> list = null;

		if (retrieveFromCache) {
			list = (List<WikiPage>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WikiPage wikiPage : list) {
					if (!Validator.equals(uuid, wikiPage.getUuid())) {
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

			query.append(_SQL_SELECT_WIKIPAGE_WHERE);

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
				query.append(WikiPageModelImpl.ORDER_BY_JPQL);
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
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first wiki page in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByUuid_First(String uuid,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByUuid_First(uuid, orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the first wiki page in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByUuid_First(String uuid,
		OrderByComparator<WikiPage> orderByComparator) {
		List<WikiPage> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last wiki page in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByUuid_Last(String uuid,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByUuid_Last(uuid, orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the last wiki page in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByUuid_Last(String uuid,
		OrderByComparator<WikiPage> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<WikiPage> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where uuid = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	 */
	@Override
	public WikiPage[] findByUuid_PrevAndNext(long pageId, String uuid,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = getByUuid_PrevAndNext(session, wikiPage, uuid,
					orderByComparator, true);

			array[1] = wikiPage;

			array[2] = getByUuid_PrevAndNext(session, wikiPage, uuid,
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

	protected WikiPage getByUuid_PrevAndNext(Session session,
		WikiPage wikiPage, String uuid,
		OrderByComparator<WikiPage> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGE_WHERE);

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
			query.append(WikiPageModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the wiki pages where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (WikiPage wikiPage : findByUuid(uuid, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(wikiPage);
		}
	}

	/**
	 * Returns the number of wiki pages where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching wiki pages
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_WIKIPAGE_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "wikiPage.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "wikiPage.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(wikiPage.uuid IS NULL OR wikiPage.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			WikiPageModelImpl.UUID_COLUMN_BITMASK |
			WikiPageModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the wiki page where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.wiki.NoSuchPageException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByUUID_G(String uuid, long groupId)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByUUID_G(uuid, groupId);

		if (wikiPage == null) {
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

			throw new NoSuchPageException(msg.toString());
		}

		return wikiPage;
	}

	/**
	 * Returns the wiki page where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the wiki page where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof WikiPage) {
			WikiPage wikiPage = (WikiPage)result;

			if (!Validator.equals(uuid, wikiPage.getUuid()) ||
					(groupId != wikiPage.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_WIKIPAGE_WHERE);

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

				List<WikiPage> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					WikiPage wikiPage = list.get(0);

					result = wikiPage;

					cacheResult(wikiPage);

					if ((wikiPage.getUuid() == null) ||
							!wikiPage.getUuid().equals(uuid) ||
							(wikiPage.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, wikiPage);
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
			return (WikiPage)result;
		}
	}

	/**
	 * Removes the wiki page where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the wiki page that was removed
	 */
	@Override
	public WikiPage removeByUUID_G(String uuid, long groupId)
		throws NoSuchPageException {
		WikiPage wikiPage = findByUUID_G(uuid, groupId);

		return remove(wikiPage);
	}

	/**
	 * Returns the number of wiki pages where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching wiki pages
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_WIKIPAGE_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "wikiPage.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "wikiPage.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(wikiPage.uuid IS NULL OR wikiPage.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "wikiPage.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			WikiPageModelImpl.UUID_COLUMN_BITMASK |
			WikiPageModelImpl.COMPANYID_COLUMN_BITMASK |
			WikiPageModelImpl.NODEID_COLUMN_BITMASK |
			WikiPageModelImpl.TITLE_COLUMN_BITMASK |
			WikiPageModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the wiki pages where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching wiki pages
	 */
	@Override
	public List<WikiPage> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the wiki pages where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByUuid_C(String uuid, long companyId, int start,
		int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the wiki pages where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByUuid_C(String uuid, long companyId, int start,
		int end, OrderByComparator<WikiPage> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the wiki pages where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByUuid_C(String uuid, long companyId, int start,
		int end, OrderByComparator<WikiPage> orderByComparator,
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

		List<WikiPage> list = null;

		if (retrieveFromCache) {
			list = (List<WikiPage>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WikiPage wikiPage : list) {
					if (!Validator.equals(uuid, wikiPage.getUuid()) ||
							(companyId != wikiPage.getCompanyId())) {
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

			query.append(_SQL_SELECT_WIKIPAGE_WHERE);

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
				query.append(WikiPageModelImpl.ORDER_BY_JPQL);
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
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first wiki page in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the first wiki page in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<WikiPage> orderByComparator) {
		List<WikiPage> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last wiki page in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the last wiki page in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<WikiPage> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<WikiPage> list = findByUuid_C(uuid, companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	 */
	@Override
	public WikiPage[] findByUuid_C_PrevAndNext(long pageId, String uuid,
		long companyId, OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, wikiPage, uuid,
					companyId, orderByComparator, true);

			array[1] = wikiPage;

			array[2] = getByUuid_C_PrevAndNext(session, wikiPage, uuid,
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

	protected WikiPage getByUuid_C_PrevAndNext(Session session,
		WikiPage wikiPage, String uuid, long companyId,
		OrderByComparator<WikiPage> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGE_WHERE);

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
			query.append(WikiPageModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the wiki pages where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (WikiPage wikiPage : findByUuid_C(uuid, companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(wikiPage);
		}
	}

	/**
	 * Returns the number of wiki pages where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching wiki pages
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_WIKIPAGE_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "wikiPage.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "wikiPage.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(wikiPage.uuid IS NULL OR wikiPage.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "wikiPage.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_RESOURCEPRIMKEY =
		new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByResourcePrimKey",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RESOURCEPRIMKEY =
		new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByResourcePrimKey",
			new String[] { Long.class.getName() },
			WikiPageModelImpl.RESOURCEPRIMKEY_COLUMN_BITMASK |
			WikiPageModelImpl.NODEID_COLUMN_BITMASK |
			WikiPageModelImpl.TITLE_COLUMN_BITMASK |
			WikiPageModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_RESOURCEPRIMKEY = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByResourcePrimKey", new String[] { Long.class.getName() });

	/**
	 * Returns all the wiki pages where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @return the matching wiki pages
	 */
	@Override
	public List<WikiPage> findByResourcePrimKey(long resourcePrimKey) {
		return findByResourcePrimKey(resourcePrimKey, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the wiki pages where resourcePrimKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByResourcePrimKey(long resourcePrimKey,
		int start, int end) {
		return findByResourcePrimKey(resourcePrimKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the wiki pages where resourcePrimKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByResourcePrimKey(long resourcePrimKey,
		int start, int end, OrderByComparator<WikiPage> orderByComparator) {
		return findByResourcePrimKey(resourcePrimKey, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the wiki pages where resourcePrimKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByResourcePrimKey(long resourcePrimKey,
		int start, int end, OrderByComparator<WikiPage> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RESOURCEPRIMKEY;
			finderArgs = new Object[] { resourcePrimKey };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_RESOURCEPRIMKEY;
			finderArgs = new Object[] {
					resourcePrimKey,
					
					start, end, orderByComparator
				};
		}

		List<WikiPage> list = null;

		if (retrieveFromCache) {
			list = (List<WikiPage>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WikiPage wikiPage : list) {
					if ((resourcePrimKey != wikiPage.getResourcePrimKey())) {
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

			query.append(_SQL_SELECT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_RESOURCEPRIMKEY_RESOURCEPRIMKEY_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(WikiPageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourcePrimKey);

				if (!pagination) {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first wiki page in the ordered set where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByResourcePrimKey_First(long resourcePrimKey,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByResourcePrimKey_First(resourcePrimKey,
				orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("resourcePrimKey=");
		msg.append(resourcePrimKey);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the first wiki page in the ordered set where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByResourcePrimKey_First(long resourcePrimKey,
		OrderByComparator<WikiPage> orderByComparator) {
		List<WikiPage> list = findByResourcePrimKey(resourcePrimKey, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last wiki page in the ordered set where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByResourcePrimKey_Last(long resourcePrimKey,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByResourcePrimKey_Last(resourcePrimKey,
				orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("resourcePrimKey=");
		msg.append(resourcePrimKey);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the last wiki page in the ordered set where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByResourcePrimKey_Last(long resourcePrimKey,
		OrderByComparator<WikiPage> orderByComparator) {
		int count = countByResourcePrimKey(resourcePrimKey);

		if (count == 0) {
			return null;
		}

		List<WikiPage> list = findByResourcePrimKey(resourcePrimKey, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where resourcePrimKey = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	 */
	@Override
	public WikiPage[] findByResourcePrimKey_PrevAndNext(long pageId,
		long resourcePrimKey, OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = getByResourcePrimKey_PrevAndNext(session, wikiPage,
					resourcePrimKey, orderByComparator, true);

			array[1] = wikiPage;

			array[2] = getByResourcePrimKey_PrevAndNext(session, wikiPage,
					resourcePrimKey, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected WikiPage getByResourcePrimKey_PrevAndNext(Session session,
		WikiPage wikiPage, long resourcePrimKey,
		OrderByComparator<WikiPage> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGE_WHERE);

		query.append(_FINDER_COLUMN_RESOURCEPRIMKEY_RESOURCEPRIMKEY_2);

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
			query.append(WikiPageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(resourcePrimKey);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the wiki pages where resourcePrimKey = &#63; from the database.
	 *
	 * @param resourcePrimKey the resource prim key
	 */
	@Override
	public void removeByResourcePrimKey(long resourcePrimKey) {
		for (WikiPage wikiPage : findByResourcePrimKey(resourcePrimKey,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(wikiPage);
		}
	}

	/**
	 * Returns the number of wiki pages where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @return the number of matching wiki pages
	 */
	@Override
	public int countByResourcePrimKey(long resourcePrimKey) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_RESOURCEPRIMKEY;

		Object[] finderArgs = new Object[] { resourcePrimKey };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_RESOURCEPRIMKEY_RESOURCEPRIMKEY_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourcePrimKey);

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

	private static final String _FINDER_COLUMN_RESOURCEPRIMKEY_RESOURCEPRIMKEY_2 =
		"wikiPage.resourcePrimKey = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_NODEID = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByNodeId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_NODEID =
		new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByNodeId",
			new String[] { Long.class.getName() },
			WikiPageModelImpl.NODEID_COLUMN_BITMASK |
			WikiPageModelImpl.TITLE_COLUMN_BITMASK |
			WikiPageModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_NODEID = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByNodeId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the wiki pages where nodeId = &#63;.
	 *
	 * @param nodeId the node ID
	 * @return the matching wiki pages
	 */
	@Override
	public List<WikiPage> findByNodeId(long nodeId) {
		return findByNodeId(nodeId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the wiki pages where nodeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByNodeId(long nodeId, int start, int end) {
		return findByNodeId(nodeId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByNodeId(long nodeId, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return findByNodeId(nodeId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByNodeId(long nodeId, int start, int end,
		OrderByComparator<WikiPage> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_NODEID;
			finderArgs = new Object[] { nodeId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_NODEID;
			finderArgs = new Object[] { nodeId, start, end, orderByComparator };
		}

		List<WikiPage> list = null;

		if (retrieveFromCache) {
			list = (List<WikiPage>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WikiPage wikiPage : list) {
					if ((nodeId != wikiPage.getNodeId())) {
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

			query.append(_SQL_SELECT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_NODEID_NODEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(WikiPageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				if (!pagination) {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first wiki page in the ordered set where nodeId = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByNodeId_First(long nodeId,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByNodeId_First(nodeId, orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("nodeId=");
		msg.append(nodeId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByNodeId_First(long nodeId,
		OrderByComparator<WikiPage> orderByComparator) {
		List<WikiPage> list = findByNodeId(nodeId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByNodeId_Last(long nodeId,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByNodeId_Last(nodeId, orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("nodeId=");
		msg.append(nodeId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByNodeId_Last(long nodeId,
		OrderByComparator<WikiPage> orderByComparator) {
		int count = countByNodeId(nodeId);

		if (count == 0) {
			return null;
		}

		List<WikiPage> list = findByNodeId(nodeId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param nodeId the node ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	 */
	@Override
	public WikiPage[] findByNodeId_PrevAndNext(long pageId, long nodeId,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = getByNodeId_PrevAndNext(session, wikiPage, nodeId,
					orderByComparator, true);

			array[1] = wikiPage;

			array[2] = getByNodeId_PrevAndNext(session, wikiPage, nodeId,
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

	protected WikiPage getByNodeId_PrevAndNext(Session session,
		WikiPage wikiPage, long nodeId,
		OrderByComparator<WikiPage> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGE_WHERE);

		query.append(_FINDER_COLUMN_NODEID_NODEID_2);

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
			query.append(WikiPageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(nodeId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the wiki pages where nodeId = &#63; from the database.
	 *
	 * @param nodeId the node ID
	 */
	@Override
	public void removeByNodeId(long nodeId) {
		for (WikiPage wikiPage : findByNodeId(nodeId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(wikiPage);
		}
	}

	/**
	 * Returns the number of wiki pages where nodeId = &#63;.
	 *
	 * @param nodeId the node ID
	 * @return the number of matching wiki pages
	 */
	@Override
	public int countByNodeId(long nodeId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_NODEID;

		Object[] finderArgs = new Object[] { nodeId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_NODEID_NODEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

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

	private static final String _FINDER_COLUMN_NODEID_NODEID_2 = "wikiPage.nodeId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_FORMAT = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByFormat",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FORMAT =
		new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByFormat",
			new String[] { String.class.getName() },
			WikiPageModelImpl.FORMAT_COLUMN_BITMASK |
			WikiPageModelImpl.NODEID_COLUMN_BITMASK |
			WikiPageModelImpl.TITLE_COLUMN_BITMASK |
			WikiPageModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_FORMAT = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByFormat",
			new String[] { String.class.getName() });

	/**
	 * Returns all the wiki pages where format = &#63;.
	 *
	 * @param format the format
	 * @return the matching wiki pages
	 */
	@Override
	public List<WikiPage> findByFormat(String format) {
		return findByFormat(format, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the wiki pages where format = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param format the format
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByFormat(String format, int start, int end) {
		return findByFormat(format, start, end, null);
	}

	/**
	 * Returns an ordered range of all the wiki pages where format = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param format the format
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByFormat(String format, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return findByFormat(format, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the wiki pages where format = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param format the format
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByFormat(String format, int start, int end,
		OrderByComparator<WikiPage> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FORMAT;
			finderArgs = new Object[] { format };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_FORMAT;
			finderArgs = new Object[] { format, start, end, orderByComparator };
		}

		List<WikiPage> list = null;

		if (retrieveFromCache) {
			list = (List<WikiPage>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WikiPage wikiPage : list) {
					if (!Validator.equals(format, wikiPage.getFormat())) {
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

			query.append(_SQL_SELECT_WIKIPAGE_WHERE);

			boolean bindFormat = false;

			if (format == null) {
				query.append(_FINDER_COLUMN_FORMAT_FORMAT_1);
			}
			else if (format.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_FORMAT_FORMAT_3);
			}
			else {
				bindFormat = true;

				query.append(_FINDER_COLUMN_FORMAT_FORMAT_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(WikiPageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindFormat) {
					qPos.add(format);
				}

				if (!pagination) {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first wiki page in the ordered set where format = &#63;.
	 *
	 * @param format the format
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByFormat_First(String format,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByFormat_First(format, orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("format=");
		msg.append(format);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the first wiki page in the ordered set where format = &#63;.
	 *
	 * @param format the format
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByFormat_First(String format,
		OrderByComparator<WikiPage> orderByComparator) {
		List<WikiPage> list = findByFormat(format, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last wiki page in the ordered set where format = &#63;.
	 *
	 * @param format the format
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByFormat_Last(String format,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByFormat_Last(format, orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("format=");
		msg.append(format);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the last wiki page in the ordered set where format = &#63;.
	 *
	 * @param format the format
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByFormat_Last(String format,
		OrderByComparator<WikiPage> orderByComparator) {
		int count = countByFormat(format);

		if (count == 0) {
			return null;
		}

		List<WikiPage> list = findByFormat(format, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where format = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param format the format
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	 */
	@Override
	public WikiPage[] findByFormat_PrevAndNext(long pageId, String format,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = getByFormat_PrevAndNext(session, wikiPage, format,
					orderByComparator, true);

			array[1] = wikiPage;

			array[2] = getByFormat_PrevAndNext(session, wikiPage, format,
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

	protected WikiPage getByFormat_PrevAndNext(Session session,
		WikiPage wikiPage, String format,
		OrderByComparator<WikiPage> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGE_WHERE);

		boolean bindFormat = false;

		if (format == null) {
			query.append(_FINDER_COLUMN_FORMAT_FORMAT_1);
		}
		else if (format.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_FORMAT_FORMAT_3);
		}
		else {
			bindFormat = true;

			query.append(_FINDER_COLUMN_FORMAT_FORMAT_2);
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
			query.append(WikiPageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindFormat) {
			qPos.add(format);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the wiki pages where format = &#63; from the database.
	 *
	 * @param format the format
	 */
	@Override
	public void removeByFormat(String format) {
		for (WikiPage wikiPage : findByFormat(format, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(wikiPage);
		}
	}

	/**
	 * Returns the number of wiki pages where format = &#63;.
	 *
	 * @param format the format
	 * @return the number of matching wiki pages
	 */
	@Override
	public int countByFormat(String format) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_FORMAT;

		Object[] finderArgs = new Object[] { format };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_WIKIPAGE_WHERE);

			boolean bindFormat = false;

			if (format == null) {
				query.append(_FINDER_COLUMN_FORMAT_FORMAT_1);
			}
			else if (format.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_FORMAT_FORMAT_3);
			}
			else {
				bindFormat = true;

				query.append(_FINDER_COLUMN_FORMAT_FORMAT_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindFormat) {
					qPos.add(format);
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

	private static final String _FINDER_COLUMN_FORMAT_FORMAT_1 = "wikiPage.format IS NULL";
	private static final String _FINDER_COLUMN_FORMAT_FORMAT_2 = "wikiPage.format = ?";
	private static final String _FINDER_COLUMN_FORMAT_FORMAT_3 = "(wikiPage.format IS NULL OR wikiPage.format = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_R_N = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByR_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_N = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByR_N",
			new String[] { Long.class.getName(), Long.class.getName() },
			WikiPageModelImpl.RESOURCEPRIMKEY_COLUMN_BITMASK |
			WikiPageModelImpl.NODEID_COLUMN_BITMASK |
			WikiPageModelImpl.TITLE_COLUMN_BITMASK |
			WikiPageModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_R_N = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByR_N",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @return the matching wiki pages
	 */
	@Override
	public List<WikiPage> findByR_N(long resourcePrimKey, long nodeId) {
		return findByR_N(resourcePrimKey, nodeId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByR_N(long resourcePrimKey, long nodeId,
		int start, int end) {
		return findByR_N(resourcePrimKey, nodeId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByR_N(long resourcePrimKey, long nodeId,
		int start, int end, OrderByComparator<WikiPage> orderByComparator) {
		return findByR_N(resourcePrimKey, nodeId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByR_N(long resourcePrimKey, long nodeId,
		int start, int end, OrderByComparator<WikiPage> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_N;
			finderArgs = new Object[] { resourcePrimKey, nodeId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_R_N;
			finderArgs = new Object[] {
					resourcePrimKey, nodeId,
					
					start, end, orderByComparator
				};
		}

		List<WikiPage> list = null;

		if (retrieveFromCache) {
			list = (List<WikiPage>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WikiPage wikiPage : list) {
					if ((resourcePrimKey != wikiPage.getResourcePrimKey()) ||
							(nodeId != wikiPage.getNodeId())) {
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

			query.append(_SQL_SELECT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_R_N_RESOURCEPRIMKEY_2);

			query.append(_FINDER_COLUMN_R_N_NODEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(WikiPageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourcePrimKey);

				qPos.add(nodeId);

				if (!pagination) {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByR_N_First(long resourcePrimKey, long nodeId,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByR_N_First(resourcePrimKey, nodeId,
				orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("resourcePrimKey=");
		msg.append(resourcePrimKey);

		msg.append(", nodeId=");
		msg.append(nodeId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the first wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByR_N_First(long resourcePrimKey, long nodeId,
		OrderByComparator<WikiPage> orderByComparator) {
		List<WikiPage> list = findByR_N(resourcePrimKey, nodeId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByR_N_Last(long resourcePrimKey, long nodeId,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByR_N_Last(resourcePrimKey, nodeId,
				orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("resourcePrimKey=");
		msg.append(resourcePrimKey);

		msg.append(", nodeId=");
		msg.append(nodeId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the last wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByR_N_Last(long resourcePrimKey, long nodeId,
		OrderByComparator<WikiPage> orderByComparator) {
		int count = countByR_N(resourcePrimKey, nodeId);

		if (count == 0) {
			return null;
		}

		List<WikiPage> list = findByR_N(resourcePrimKey, nodeId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	 */
	@Override
	public WikiPage[] findByR_N_PrevAndNext(long pageId, long resourcePrimKey,
		long nodeId, OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = getByR_N_PrevAndNext(session, wikiPage, resourcePrimKey,
					nodeId, orderByComparator, true);

			array[1] = wikiPage;

			array[2] = getByR_N_PrevAndNext(session, wikiPage, resourcePrimKey,
					nodeId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected WikiPage getByR_N_PrevAndNext(Session session, WikiPage wikiPage,
		long resourcePrimKey, long nodeId,
		OrderByComparator<WikiPage> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGE_WHERE);

		query.append(_FINDER_COLUMN_R_N_RESOURCEPRIMKEY_2);

		query.append(_FINDER_COLUMN_R_N_NODEID_2);

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
			query.append(WikiPageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(resourcePrimKey);

		qPos.add(nodeId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63; from the database.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 */
	@Override
	public void removeByR_N(long resourcePrimKey, long nodeId) {
		for (WikiPage wikiPage : findByR_N(resourcePrimKey, nodeId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(wikiPage);
		}
	}

	/**
	 * Returns the number of wiki pages where resourcePrimKey = &#63; and nodeId = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @return the number of matching wiki pages
	 */
	@Override
	public int countByR_N(long resourcePrimKey, long nodeId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_R_N;

		Object[] finderArgs = new Object[] { resourcePrimKey, nodeId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_R_N_RESOURCEPRIMKEY_2);

			query.append(_FINDER_COLUMN_R_N_NODEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourcePrimKey);

				qPos.add(nodeId);

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

	private static final String _FINDER_COLUMN_R_N_RESOURCEPRIMKEY_2 = "wikiPage.resourcePrimKey = ? AND ";
	private static final String _FINDER_COLUMN_R_N_NODEID_2 = "wikiPage.nodeId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_R_S = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByR_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_S = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByR_S",
			new String[] { Long.class.getName(), Integer.class.getName() },
			WikiPageModelImpl.RESOURCEPRIMKEY_COLUMN_BITMASK |
			WikiPageModelImpl.STATUS_COLUMN_BITMASK |
			WikiPageModelImpl.NODEID_COLUMN_BITMASK |
			WikiPageModelImpl.TITLE_COLUMN_BITMASK |
			WikiPageModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_R_S = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByR_S",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the wiki pages where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @return the matching wiki pages
	 */
	@Override
	public List<WikiPage> findByR_S(long resourcePrimKey, int status) {
		return findByR_S(resourcePrimKey, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the wiki pages where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByR_S(long resourcePrimKey, int status,
		int start, int end) {
		return findByR_S(resourcePrimKey, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the wiki pages where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByR_S(long resourcePrimKey, int status,
		int start, int end, OrderByComparator<WikiPage> orderByComparator) {
		return findByR_S(resourcePrimKey, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the wiki pages where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByR_S(long resourcePrimKey, int status,
		int start, int end, OrderByComparator<WikiPage> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_S;
			finderArgs = new Object[] { resourcePrimKey, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_R_S;
			finderArgs = new Object[] {
					resourcePrimKey, status,
					
					start, end, orderByComparator
				};
		}

		List<WikiPage> list = null;

		if (retrieveFromCache) {
			list = (List<WikiPage>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WikiPage wikiPage : list) {
					if ((resourcePrimKey != wikiPage.getResourcePrimKey()) ||
							(status != wikiPage.getStatus())) {
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

			query.append(_SQL_SELECT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_R_S_RESOURCEPRIMKEY_2);

			query.append(_FINDER_COLUMN_R_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(WikiPageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourcePrimKey);

				qPos.add(status);

				if (!pagination) {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first wiki page in the ordered set where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByR_S_First(long resourcePrimKey, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByR_S_First(resourcePrimKey, status,
				orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("resourcePrimKey=");
		msg.append(resourcePrimKey);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the first wiki page in the ordered set where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByR_S_First(long resourcePrimKey, int status,
		OrderByComparator<WikiPage> orderByComparator) {
		List<WikiPage> list = findByR_S(resourcePrimKey, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last wiki page in the ordered set where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByR_S_Last(long resourcePrimKey, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByR_S_Last(resourcePrimKey, status,
				orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("resourcePrimKey=");
		msg.append(resourcePrimKey);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the last wiki page in the ordered set where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByR_S_Last(long resourcePrimKey, int status,
		OrderByComparator<WikiPage> orderByComparator) {
		int count = countByR_S(resourcePrimKey, status);

		if (count == 0) {
			return null;
		}

		List<WikiPage> list = findByR_S(resourcePrimKey, status, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	 */
	@Override
	public WikiPage[] findByR_S_PrevAndNext(long pageId, long resourcePrimKey,
		int status, OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = getByR_S_PrevAndNext(session, wikiPage, resourcePrimKey,
					status, orderByComparator, true);

			array[1] = wikiPage;

			array[2] = getByR_S_PrevAndNext(session, wikiPage, resourcePrimKey,
					status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected WikiPage getByR_S_PrevAndNext(Session session, WikiPage wikiPage,
		long resourcePrimKey, int status,
		OrderByComparator<WikiPage> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGE_WHERE);

		query.append(_FINDER_COLUMN_R_S_RESOURCEPRIMKEY_2);

		query.append(_FINDER_COLUMN_R_S_STATUS_2);

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
			query.append(WikiPageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(resourcePrimKey);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the wiki pages where resourcePrimKey = &#63; and status = &#63; from the database.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 */
	@Override
	public void removeByR_S(long resourcePrimKey, int status) {
		for (WikiPage wikiPage : findByR_S(resourcePrimKey, status,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(wikiPage);
		}
	}

	/**
	 * Returns the number of wiki pages where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @return the number of matching wiki pages
	 */
	@Override
	public int countByR_S(long resourcePrimKey, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_R_S;

		Object[] finderArgs = new Object[] { resourcePrimKey, status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_R_S_RESOURCEPRIMKEY_2);

			query.append(_FINDER_COLUMN_R_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourcePrimKey);

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_R_S_RESOURCEPRIMKEY_2 = "wikiPage.resourcePrimKey = ? AND ";
	private static final String _FINDER_COLUMN_R_S_STATUS_2 = "wikiPage.status = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_N_T = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByN_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_T = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByN_T",
			new String[] { Long.class.getName(), String.class.getName() },
			WikiPageModelImpl.NODEID_COLUMN_BITMASK |
			WikiPageModelImpl.TITLE_COLUMN_BITMASK |
			WikiPageModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_N_T = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByN_T",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns all the wiki pages where nodeId = &#63; and title = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @return the matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_T(long nodeId, String title) {
		return findByN_T(nodeId, title, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the wiki pages where nodeId = &#63; and title = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_T(long nodeId, String title, int start,
		int end) {
		return findByN_T(nodeId, title, start, end, null);
	}

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and title = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_T(long nodeId, String title, int start,
		int end, OrderByComparator<WikiPage> orderByComparator) {
		return findByN_T(nodeId, title, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and title = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_T(long nodeId, String title, int start,
		int end, OrderByComparator<WikiPage> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_T;
			finderArgs = new Object[] { nodeId, title };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_N_T;
			finderArgs = new Object[] {
					nodeId, title,
					
					start, end, orderByComparator
				};
		}

		List<WikiPage> list = null;

		if (retrieveFromCache) {
			list = (List<WikiPage>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WikiPage wikiPage : list) {
					if ((nodeId != wikiPage.getNodeId()) ||
							!Validator.equals(title, wikiPage.getTitle())) {
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

			query.append(_SQL_SELECT_WIKIPAGE_WHERE);

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

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(WikiPageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				if (bindTitle) {
					qPos.add(StringUtil.toLowerCase(title));
				}

				if (!pagination) {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and title = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByN_T_First(long nodeId, String title,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByN_T_First(nodeId, title, orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("nodeId=");
		msg.append(nodeId);

		msg.append(", title=");
		msg.append(title);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and title = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByN_T_First(long nodeId, String title,
		OrderByComparator<WikiPage> orderByComparator) {
		List<WikiPage> list = findByN_T(nodeId, title, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and title = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByN_T_Last(long nodeId, String title,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByN_T_Last(nodeId, title, orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("nodeId=");
		msg.append(nodeId);

		msg.append(", title=");
		msg.append(title);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and title = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByN_T_Last(long nodeId, String title,
		OrderByComparator<WikiPage> orderByComparator) {
		int count = countByN_T(nodeId, title);

		if (count == 0) {
			return null;
		}

		List<WikiPage> list = findByN_T(nodeId, title, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and title = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param nodeId the node ID
	 * @param title the title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	 */
	@Override
	public WikiPage[] findByN_T_PrevAndNext(long pageId, long nodeId,
		String title, OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = getByN_T_PrevAndNext(session, wikiPage, nodeId, title,
					orderByComparator, true);

			array[1] = wikiPage;

			array[2] = getByN_T_PrevAndNext(session, wikiPage, nodeId, title,
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

	protected WikiPage getByN_T_PrevAndNext(Session session, WikiPage wikiPage,
		long nodeId, String title,
		OrderByComparator<WikiPage> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGE_WHERE);

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
			query.append(WikiPageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(nodeId);

		if (bindTitle) {
			qPos.add(StringUtil.toLowerCase(title));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the wiki pages where nodeId = &#63; and title = &#63; from the database.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 */
	@Override
	public void removeByN_T(long nodeId, String title) {
		for (WikiPage wikiPage : findByN_T(nodeId, title, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(wikiPage);
		}
	}

	/**
	 * Returns the number of wiki pages where nodeId = &#63; and title = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @return the number of matching wiki pages
	 */
	@Override
	public int countByN_T(long nodeId, String title) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_N_T;

		Object[] finderArgs = new Object[] { nodeId, title };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_WIKIPAGE_WHERE);

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
					qPos.add(StringUtil.toLowerCase(title));
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

	private static final String _FINDER_COLUMN_N_T_NODEID_2 = "wikiPage.nodeId = ? AND ";
	private static final String _FINDER_COLUMN_N_T_TITLE_1 = "wikiPage.title IS NULL";
	private static final String _FINDER_COLUMN_N_T_TITLE_2 = "lower(wikiPage.title) = ?";
	private static final String _FINDER_COLUMN_N_T_TITLE_3 = "(wikiPage.title IS NULL OR wikiPage.title = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_N_H = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByN_H",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_H = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByN_H",
			new String[] { Long.class.getName(), Boolean.class.getName() },
			WikiPageModelImpl.NODEID_COLUMN_BITMASK |
			WikiPageModelImpl.HEAD_COLUMN_BITMASK |
			WikiPageModelImpl.TITLE_COLUMN_BITMASK |
			WikiPageModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_N_H = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByN_H",
			new String[] { Long.class.getName(), Boolean.class.getName() });

	/**
	 * Returns all the wiki pages where nodeId = &#63; and head = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @return the matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_H(long nodeId, boolean head) {
		return findByN_H(nodeId, head, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the wiki pages where nodeId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_H(long nodeId, boolean head, int start,
		int end) {
		return findByN_H(nodeId, head, start, end, null);
	}

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_H(long nodeId, boolean head, int start,
		int end, OrderByComparator<WikiPage> orderByComparator) {
		return findByN_H(nodeId, head, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_H(long nodeId, boolean head, int start,
		int end, OrderByComparator<WikiPage> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_H;
			finderArgs = new Object[] { nodeId, head };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_N_H;
			finderArgs = new Object[] {
					nodeId, head,
					
					start, end, orderByComparator
				};
		}

		List<WikiPage> list = null;

		if (retrieveFromCache) {
			list = (List<WikiPage>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WikiPage wikiPage : list) {
					if ((nodeId != wikiPage.getNodeId()) ||
							(head != wikiPage.getHead())) {
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

			query.append(_SQL_SELECT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_N_H_NODEID_2);

			query.append(_FINDER_COLUMN_N_H_HEAD_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(WikiPageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				qPos.add(head);

				if (!pagination) {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByN_H_First(long nodeId, boolean head,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByN_H_First(nodeId, head, orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("nodeId=");
		msg.append(nodeId);

		msg.append(", head=");
		msg.append(head);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByN_H_First(long nodeId, boolean head,
		OrderByComparator<WikiPage> orderByComparator) {
		List<WikiPage> list = findByN_H(nodeId, head, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByN_H_Last(long nodeId, boolean head,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByN_H_Last(nodeId, head, orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("nodeId=");
		msg.append(nodeId);

		msg.append(", head=");
		msg.append(head);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByN_H_Last(long nodeId, boolean head,
		OrderByComparator<WikiPage> orderByComparator) {
		int count = countByN_H(nodeId, head);

		if (count == 0) {
			return null;
		}

		List<WikiPage> list = findByN_H(nodeId, head, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and head = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param nodeId the node ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	 */
	@Override
	public WikiPage[] findByN_H_PrevAndNext(long pageId, long nodeId,
		boolean head, OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = getByN_H_PrevAndNext(session, wikiPage, nodeId, head,
					orderByComparator, true);

			array[1] = wikiPage;

			array[2] = getByN_H_PrevAndNext(session, wikiPage, nodeId, head,
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

	protected WikiPage getByN_H_PrevAndNext(Session session, WikiPage wikiPage,
		long nodeId, boolean head,
		OrderByComparator<WikiPage> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGE_WHERE);

		query.append(_FINDER_COLUMN_N_H_NODEID_2);

		query.append(_FINDER_COLUMN_N_H_HEAD_2);

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
			query.append(WikiPageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(nodeId);

		qPos.add(head);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the wiki pages where nodeId = &#63; and head = &#63; from the database.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 */
	@Override
	public void removeByN_H(long nodeId, boolean head) {
		for (WikiPage wikiPage : findByN_H(nodeId, head, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(wikiPage);
		}
	}

	/**
	 * Returns the number of wiki pages where nodeId = &#63; and head = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @return the number of matching wiki pages
	 */
	@Override
	public int countByN_H(long nodeId, boolean head) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_N_H;

		Object[] finderArgs = new Object[] { nodeId, head };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_N_H_NODEID_2);

			query.append(_FINDER_COLUMN_N_H_HEAD_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				qPos.add(head);

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

	private static final String _FINDER_COLUMN_N_H_NODEID_2 = "wikiPage.nodeId = ? AND ";
	private static final String _FINDER_COLUMN_N_H_HEAD_2 = "wikiPage.head = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_N_P = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByN_P",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_P = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByN_P",
			new String[] { Long.class.getName(), String.class.getName() },
			WikiPageModelImpl.NODEID_COLUMN_BITMASK |
			WikiPageModelImpl.PARENTTITLE_COLUMN_BITMASK |
			WikiPageModelImpl.TITLE_COLUMN_BITMASK |
			WikiPageModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_N_P = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByN_P",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns all the wiki pages where nodeId = &#63; and parentTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param parentTitle the parent title
	 * @return the matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_P(long nodeId, String parentTitle) {
		return findByN_P(nodeId, parentTitle, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the wiki pages where nodeId = &#63; and parentTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param parentTitle the parent title
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_P(long nodeId, String parentTitle, int start,
		int end) {
		return findByN_P(nodeId, parentTitle, start, end, null);
	}

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and parentTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param parentTitle the parent title
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_P(long nodeId, String parentTitle, int start,
		int end, OrderByComparator<WikiPage> orderByComparator) {
		return findByN_P(nodeId, parentTitle, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and parentTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param parentTitle the parent title
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_P(long nodeId, String parentTitle, int start,
		int end, OrderByComparator<WikiPage> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_P;
			finderArgs = new Object[] { nodeId, parentTitle };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_N_P;
			finderArgs = new Object[] {
					nodeId, parentTitle,
					
					start, end, orderByComparator
				};
		}

		List<WikiPage> list = null;

		if (retrieveFromCache) {
			list = (List<WikiPage>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WikiPage wikiPage : list) {
					if ((nodeId != wikiPage.getNodeId()) ||
							!Validator.equals(parentTitle,
								wikiPage.getParentTitle())) {
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

			query.append(_SQL_SELECT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_N_P_NODEID_2);

			boolean bindParentTitle = false;

			if (parentTitle == null) {
				query.append(_FINDER_COLUMN_N_P_PARENTTITLE_1);
			}
			else if (parentTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_N_P_PARENTTITLE_3);
			}
			else {
				bindParentTitle = true;

				query.append(_FINDER_COLUMN_N_P_PARENTTITLE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(WikiPageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				if (bindParentTitle) {
					qPos.add(StringUtil.toLowerCase(parentTitle));
				}

				if (!pagination) {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and parentTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param parentTitle the parent title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByN_P_First(long nodeId, String parentTitle,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByN_P_First(nodeId, parentTitle,
				orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("nodeId=");
		msg.append(nodeId);

		msg.append(", parentTitle=");
		msg.append(parentTitle);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and parentTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param parentTitle the parent title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByN_P_First(long nodeId, String parentTitle,
		OrderByComparator<WikiPage> orderByComparator) {
		List<WikiPage> list = findByN_P(nodeId, parentTitle, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and parentTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param parentTitle the parent title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByN_P_Last(long nodeId, String parentTitle,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByN_P_Last(nodeId, parentTitle,
				orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("nodeId=");
		msg.append(nodeId);

		msg.append(", parentTitle=");
		msg.append(parentTitle);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and parentTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param parentTitle the parent title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByN_P_Last(long nodeId, String parentTitle,
		OrderByComparator<WikiPage> orderByComparator) {
		int count = countByN_P(nodeId, parentTitle);

		if (count == 0) {
			return null;
		}

		List<WikiPage> list = findByN_P(nodeId, parentTitle, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and parentTitle = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param nodeId the node ID
	 * @param parentTitle the parent title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	 */
	@Override
	public WikiPage[] findByN_P_PrevAndNext(long pageId, long nodeId,
		String parentTitle, OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = getByN_P_PrevAndNext(session, wikiPage, nodeId,
					parentTitle, orderByComparator, true);

			array[1] = wikiPage;

			array[2] = getByN_P_PrevAndNext(session, wikiPage, nodeId,
					parentTitle, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected WikiPage getByN_P_PrevAndNext(Session session, WikiPage wikiPage,
		long nodeId, String parentTitle,
		OrderByComparator<WikiPage> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGE_WHERE);

		query.append(_FINDER_COLUMN_N_P_NODEID_2);

		boolean bindParentTitle = false;

		if (parentTitle == null) {
			query.append(_FINDER_COLUMN_N_P_PARENTTITLE_1);
		}
		else if (parentTitle.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_N_P_PARENTTITLE_3);
		}
		else {
			bindParentTitle = true;

			query.append(_FINDER_COLUMN_N_P_PARENTTITLE_2);
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
			query.append(WikiPageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(nodeId);

		if (bindParentTitle) {
			qPos.add(StringUtil.toLowerCase(parentTitle));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the wiki pages where nodeId = &#63; and parentTitle = &#63; from the database.
	 *
	 * @param nodeId the node ID
	 * @param parentTitle the parent title
	 */
	@Override
	public void removeByN_P(long nodeId, String parentTitle) {
		for (WikiPage wikiPage : findByN_P(nodeId, parentTitle,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(wikiPage);
		}
	}

	/**
	 * Returns the number of wiki pages where nodeId = &#63; and parentTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param parentTitle the parent title
	 * @return the number of matching wiki pages
	 */
	@Override
	public int countByN_P(long nodeId, String parentTitle) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_N_P;

		Object[] finderArgs = new Object[] { nodeId, parentTitle };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_N_P_NODEID_2);

			boolean bindParentTitle = false;

			if (parentTitle == null) {
				query.append(_FINDER_COLUMN_N_P_PARENTTITLE_1);
			}
			else if (parentTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_N_P_PARENTTITLE_3);
			}
			else {
				bindParentTitle = true;

				query.append(_FINDER_COLUMN_N_P_PARENTTITLE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				if (bindParentTitle) {
					qPos.add(StringUtil.toLowerCase(parentTitle));
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

	private static final String _FINDER_COLUMN_N_P_NODEID_2 = "wikiPage.nodeId = ? AND ";
	private static final String _FINDER_COLUMN_N_P_PARENTTITLE_1 = "wikiPage.parentTitle IS NULL";
	private static final String _FINDER_COLUMN_N_P_PARENTTITLE_2 = "lower(wikiPage.parentTitle) = ?";
	private static final String _FINDER_COLUMN_N_P_PARENTTITLE_3 = "(wikiPage.parentTitle IS NULL OR wikiPage.parentTitle = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_N_R = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByN_R",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_R = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByN_R",
			new String[] { Long.class.getName(), String.class.getName() },
			WikiPageModelImpl.NODEID_COLUMN_BITMASK |
			WikiPageModelImpl.REDIRECTTITLE_COLUMN_BITMASK |
			WikiPageModelImpl.TITLE_COLUMN_BITMASK |
			WikiPageModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_N_R = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByN_R",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns all the wiki pages where nodeId = &#63; and redirectTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param redirectTitle the redirect title
	 * @return the matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_R(long nodeId, String redirectTitle) {
		return findByN_R(nodeId, redirectTitle, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the wiki pages where nodeId = &#63; and redirectTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param redirectTitle the redirect title
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_R(long nodeId, String redirectTitle,
		int start, int end) {
		return findByN_R(nodeId, redirectTitle, start, end, null);
	}

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and redirectTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param redirectTitle the redirect title
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_R(long nodeId, String redirectTitle,
		int start, int end, OrderByComparator<WikiPage> orderByComparator) {
		return findByN_R(nodeId, redirectTitle, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and redirectTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param redirectTitle the redirect title
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_R(long nodeId, String redirectTitle,
		int start, int end, OrderByComparator<WikiPage> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_R;
			finderArgs = new Object[] { nodeId, redirectTitle };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_N_R;
			finderArgs = new Object[] {
					nodeId, redirectTitle,
					
					start, end, orderByComparator
				};
		}

		List<WikiPage> list = null;

		if (retrieveFromCache) {
			list = (List<WikiPage>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WikiPage wikiPage : list) {
					if ((nodeId != wikiPage.getNodeId()) ||
							!Validator.equals(redirectTitle,
								wikiPage.getRedirectTitle())) {
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

			query.append(_SQL_SELECT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_N_R_NODEID_2);

			boolean bindRedirectTitle = false;

			if (redirectTitle == null) {
				query.append(_FINDER_COLUMN_N_R_REDIRECTTITLE_1);
			}
			else if (redirectTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_N_R_REDIRECTTITLE_3);
			}
			else {
				bindRedirectTitle = true;

				query.append(_FINDER_COLUMN_N_R_REDIRECTTITLE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(WikiPageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				if (bindRedirectTitle) {
					qPos.add(StringUtil.toLowerCase(redirectTitle));
				}

				if (!pagination) {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and redirectTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param redirectTitle the redirect title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByN_R_First(long nodeId, String redirectTitle,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByN_R_First(nodeId, redirectTitle,
				orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("nodeId=");
		msg.append(nodeId);

		msg.append(", redirectTitle=");
		msg.append(redirectTitle);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and redirectTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param redirectTitle the redirect title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByN_R_First(long nodeId, String redirectTitle,
		OrderByComparator<WikiPage> orderByComparator) {
		List<WikiPage> list = findByN_R(nodeId, redirectTitle, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and redirectTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param redirectTitle the redirect title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByN_R_Last(long nodeId, String redirectTitle,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByN_R_Last(nodeId, redirectTitle,
				orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("nodeId=");
		msg.append(nodeId);

		msg.append(", redirectTitle=");
		msg.append(redirectTitle);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and redirectTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param redirectTitle the redirect title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByN_R_Last(long nodeId, String redirectTitle,
		OrderByComparator<WikiPage> orderByComparator) {
		int count = countByN_R(nodeId, redirectTitle);

		if (count == 0) {
			return null;
		}

		List<WikiPage> list = findByN_R(nodeId, redirectTitle, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and redirectTitle = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param nodeId the node ID
	 * @param redirectTitle the redirect title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	 */
	@Override
	public WikiPage[] findByN_R_PrevAndNext(long pageId, long nodeId,
		String redirectTitle, OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = getByN_R_PrevAndNext(session, wikiPage, nodeId,
					redirectTitle, orderByComparator, true);

			array[1] = wikiPage;

			array[2] = getByN_R_PrevAndNext(session, wikiPage, nodeId,
					redirectTitle, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected WikiPage getByN_R_PrevAndNext(Session session, WikiPage wikiPage,
		long nodeId, String redirectTitle,
		OrderByComparator<WikiPage> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGE_WHERE);

		query.append(_FINDER_COLUMN_N_R_NODEID_2);

		boolean bindRedirectTitle = false;

		if (redirectTitle == null) {
			query.append(_FINDER_COLUMN_N_R_REDIRECTTITLE_1);
		}
		else if (redirectTitle.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_N_R_REDIRECTTITLE_3);
		}
		else {
			bindRedirectTitle = true;

			query.append(_FINDER_COLUMN_N_R_REDIRECTTITLE_2);
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
			query.append(WikiPageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(nodeId);

		if (bindRedirectTitle) {
			qPos.add(StringUtil.toLowerCase(redirectTitle));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the wiki pages where nodeId = &#63; and redirectTitle = &#63; from the database.
	 *
	 * @param nodeId the node ID
	 * @param redirectTitle the redirect title
	 */
	@Override
	public void removeByN_R(long nodeId, String redirectTitle) {
		for (WikiPage wikiPage : findByN_R(nodeId, redirectTitle,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(wikiPage);
		}
	}

	/**
	 * Returns the number of wiki pages where nodeId = &#63; and redirectTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param redirectTitle the redirect title
	 * @return the number of matching wiki pages
	 */
	@Override
	public int countByN_R(long nodeId, String redirectTitle) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_N_R;

		Object[] finderArgs = new Object[] { nodeId, redirectTitle };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_N_R_NODEID_2);

			boolean bindRedirectTitle = false;

			if (redirectTitle == null) {
				query.append(_FINDER_COLUMN_N_R_REDIRECTTITLE_1);
			}
			else if (redirectTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_N_R_REDIRECTTITLE_3);
			}
			else {
				bindRedirectTitle = true;

				query.append(_FINDER_COLUMN_N_R_REDIRECTTITLE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				if (bindRedirectTitle) {
					qPos.add(StringUtil.toLowerCase(redirectTitle));
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

	private static final String _FINDER_COLUMN_N_R_NODEID_2 = "wikiPage.nodeId = ? AND ";
	private static final String _FINDER_COLUMN_N_R_REDIRECTTITLE_1 = "wikiPage.redirectTitle IS NULL";
	private static final String _FINDER_COLUMN_N_R_REDIRECTTITLE_2 = "lower(wikiPage.redirectTitle) = ?";
	private static final String _FINDER_COLUMN_N_R_REDIRECTTITLE_3 = "(wikiPage.redirectTitle IS NULL OR wikiPage.redirectTitle = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_N_S = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByN_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_S = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByN_S",
			new String[] { Long.class.getName(), Integer.class.getName() },
			WikiPageModelImpl.NODEID_COLUMN_BITMASK |
			WikiPageModelImpl.STATUS_COLUMN_BITMASK |
			WikiPageModelImpl.TITLE_COLUMN_BITMASK |
			WikiPageModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_N_S = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByN_S",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the wiki pages where nodeId = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param status the status
	 * @return the matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_S(long nodeId, int status) {
		return findByN_S(nodeId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the wiki pages where nodeId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_S(long nodeId, int status, int start, int end) {
		return findByN_S(nodeId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_S(long nodeId, int status, int start,
		int end, OrderByComparator<WikiPage> orderByComparator) {
		return findByN_S(nodeId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_S(long nodeId, int status, int start,
		int end, OrderByComparator<WikiPage> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_S;
			finderArgs = new Object[] { nodeId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_N_S;
			finderArgs = new Object[] {
					nodeId, status,
					
					start, end, orderByComparator
				};
		}

		List<WikiPage> list = null;

		if (retrieveFromCache) {
			list = (List<WikiPage>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WikiPage wikiPage : list) {
					if ((nodeId != wikiPage.getNodeId()) ||
							(status != wikiPage.getStatus())) {
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

			query.append(_SQL_SELECT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_N_S_NODEID_2);

			query.append(_FINDER_COLUMN_N_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(WikiPageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				qPos.add(status);

				if (!pagination) {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByN_S_First(long nodeId, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByN_S_First(nodeId, status, orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("nodeId=");
		msg.append(nodeId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByN_S_First(long nodeId, int status,
		OrderByComparator<WikiPage> orderByComparator) {
		List<WikiPage> list = findByN_S(nodeId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByN_S_Last(long nodeId, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByN_S_Last(nodeId, status, orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("nodeId=");
		msg.append(nodeId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByN_S_Last(long nodeId, int status,
		OrderByComparator<WikiPage> orderByComparator) {
		int count = countByN_S(nodeId, status);

		if (count == 0) {
			return null;
		}

		List<WikiPage> list = findByN_S(nodeId, status, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and status = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	 */
	@Override
	public WikiPage[] findByN_S_PrevAndNext(long pageId, long nodeId,
		int status, OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = getByN_S_PrevAndNext(session, wikiPage, nodeId, status,
					orderByComparator, true);

			array[1] = wikiPage;

			array[2] = getByN_S_PrevAndNext(session, wikiPage, nodeId, status,
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

	protected WikiPage getByN_S_PrevAndNext(Session session, WikiPage wikiPage,
		long nodeId, int status, OrderByComparator<WikiPage> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGE_WHERE);

		query.append(_FINDER_COLUMN_N_S_NODEID_2);

		query.append(_FINDER_COLUMN_N_S_STATUS_2);

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
			query.append(WikiPageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(nodeId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the wiki pages where nodeId = &#63; and status = &#63; from the database.
	 *
	 * @param nodeId the node ID
	 * @param status the status
	 */
	@Override
	public void removeByN_S(long nodeId, int status) {
		for (WikiPage wikiPage : findByN_S(nodeId, status, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(wikiPage);
		}
	}

	/**
	 * Returns the number of wiki pages where nodeId = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param status the status
	 * @return the number of matching wiki pages
	 */
	@Override
	public int countByN_S(long nodeId, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_N_S;

		Object[] finderArgs = new Object[] { nodeId, status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_N_S_NODEID_2);

			query.append(_FINDER_COLUMN_N_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_N_S_NODEID_2 = "wikiPage.nodeId = ? AND ";
	private static final String _FINDER_COLUMN_N_S_STATUS_2 = "wikiPage.status = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_R_N_V = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByR_N_V",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Double.class.getName()
			},
			WikiPageModelImpl.RESOURCEPRIMKEY_COLUMN_BITMASK |
			WikiPageModelImpl.NODEID_COLUMN_BITMASK |
			WikiPageModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_R_N_V = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByR_N_V",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Double.class.getName()
			});

	/**
	 * Returns the wiki page where resourcePrimKey = &#63; and nodeId = &#63; and version = &#63; or throws a {@link com.liferay.wiki.NoSuchPageException} if it could not be found.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param version the version
	 * @return the matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByR_N_V(long resourcePrimKey, long nodeId,
		double version) throws NoSuchPageException {
		WikiPage wikiPage = fetchByR_N_V(resourcePrimKey, nodeId, version);

		if (wikiPage == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("resourcePrimKey=");
			msg.append(resourcePrimKey);

			msg.append(", nodeId=");
			msg.append(nodeId);

			msg.append(", version=");
			msg.append(version);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchPageException(msg.toString());
		}

		return wikiPage;
	}

	/**
	 * Returns the wiki page where resourcePrimKey = &#63; and nodeId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param version the version
	 * @return the matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByR_N_V(long resourcePrimKey, long nodeId,
		double version) {
		return fetchByR_N_V(resourcePrimKey, nodeId, version, true);
	}

	/**
	 * Returns the wiki page where resourcePrimKey = &#63; and nodeId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param version the version
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByR_N_V(long resourcePrimKey, long nodeId,
		double version, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { resourcePrimKey, nodeId, version };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_R_N_V,
					finderArgs, this);
		}

		if (result instanceof WikiPage) {
			WikiPage wikiPage = (WikiPage)result;

			if ((resourcePrimKey != wikiPage.getResourcePrimKey()) ||
					(nodeId != wikiPage.getNodeId()) ||
					(version != wikiPage.getVersion())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_R_N_V_RESOURCEPRIMKEY_2);

			query.append(_FINDER_COLUMN_R_N_V_NODEID_2);

			query.append(_FINDER_COLUMN_R_N_V_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourcePrimKey);

				qPos.add(nodeId);

				qPos.add(version);

				List<WikiPage> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_R_N_V,
						finderArgs, list);
				}
				else {
					WikiPage wikiPage = list.get(0);

					result = wikiPage;

					cacheResult(wikiPage);

					if ((wikiPage.getResourcePrimKey() != resourcePrimKey) ||
							(wikiPage.getNodeId() != nodeId) ||
							(wikiPage.getVersion() != version)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_R_N_V,
							finderArgs, wikiPage);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_R_N_V, finderArgs);

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
			return (WikiPage)result;
		}
	}

	/**
	 * Removes the wiki page where resourcePrimKey = &#63; and nodeId = &#63; and version = &#63; from the database.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param version the version
	 * @return the wiki page that was removed
	 */
	@Override
	public WikiPage removeByR_N_V(long resourcePrimKey, long nodeId,
		double version) throws NoSuchPageException {
		WikiPage wikiPage = findByR_N_V(resourcePrimKey, nodeId, version);

		return remove(wikiPage);
	}

	/**
	 * Returns the number of wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and version = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param version the version
	 * @return the number of matching wiki pages
	 */
	@Override
	public int countByR_N_V(long resourcePrimKey, long nodeId, double version) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_R_N_V;

		Object[] finderArgs = new Object[] { resourcePrimKey, nodeId, version };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_R_N_V_RESOURCEPRIMKEY_2);

			query.append(_FINDER_COLUMN_R_N_V_NODEID_2);

			query.append(_FINDER_COLUMN_R_N_V_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourcePrimKey);

				qPos.add(nodeId);

				qPos.add(version);

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

	private static final String _FINDER_COLUMN_R_N_V_RESOURCEPRIMKEY_2 = "wikiPage.resourcePrimKey = ? AND ";
	private static final String _FINDER_COLUMN_R_N_V_NODEID_2 = "wikiPage.nodeId = ? AND ";
	private static final String _FINDER_COLUMN_R_N_V_VERSION_2 = "wikiPage.version = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_R_N_H = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByR_N_H",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_N_H = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByR_N_H",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			WikiPageModelImpl.RESOURCEPRIMKEY_COLUMN_BITMASK |
			WikiPageModelImpl.NODEID_COLUMN_BITMASK |
			WikiPageModelImpl.HEAD_COLUMN_BITMASK |
			WikiPageModelImpl.TITLE_COLUMN_BITMASK |
			WikiPageModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_R_N_H = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByR_N_H",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});

	/**
	 * Returns all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param head the head
	 * @return the matching wiki pages
	 */
	@Override
	public List<WikiPage> findByR_N_H(long resourcePrimKey, long nodeId,
		boolean head) {
		return findByR_N_H(resourcePrimKey, nodeId, head, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param head the head
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByR_N_H(long resourcePrimKey, long nodeId,
		boolean head, int start, int end) {
		return findByR_N_H(resourcePrimKey, nodeId, head, start, end, null);
	}

	/**
	 * Returns an ordered range of all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param head the head
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByR_N_H(long resourcePrimKey, long nodeId,
		boolean head, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return findByR_N_H(resourcePrimKey, nodeId, head, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param head the head
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByR_N_H(long resourcePrimKey, long nodeId,
		boolean head, int start, int end,
		OrderByComparator<WikiPage> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_N_H;
			finderArgs = new Object[] { resourcePrimKey, nodeId, head };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_R_N_H;
			finderArgs = new Object[] {
					resourcePrimKey, nodeId, head,
					
					start, end, orderByComparator
				};
		}

		List<WikiPage> list = null;

		if (retrieveFromCache) {
			list = (List<WikiPage>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WikiPage wikiPage : list) {
					if ((resourcePrimKey != wikiPage.getResourcePrimKey()) ||
							(nodeId != wikiPage.getNodeId()) ||
							(head != wikiPage.getHead())) {
						list = null;

						break;
					}
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

			query.append(_SQL_SELECT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_R_N_H_RESOURCEPRIMKEY_2);

			query.append(_FINDER_COLUMN_R_N_H_NODEID_2);

			query.append(_FINDER_COLUMN_R_N_H_HEAD_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(WikiPageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourcePrimKey);

				qPos.add(nodeId);

				qPos.add(head);

				if (!pagination) {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByR_N_H_First(long resourcePrimKey, long nodeId,
		boolean head, OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByR_N_H_First(resourcePrimKey, nodeId, head,
				orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("resourcePrimKey=");
		msg.append(resourcePrimKey);

		msg.append(", nodeId=");
		msg.append(nodeId);

		msg.append(", head=");
		msg.append(head);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the first wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByR_N_H_First(long resourcePrimKey, long nodeId,
		boolean head, OrderByComparator<WikiPage> orderByComparator) {
		List<WikiPage> list = findByR_N_H(resourcePrimKey, nodeId, head, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByR_N_H_Last(long resourcePrimKey, long nodeId,
		boolean head, OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByR_N_H_Last(resourcePrimKey, nodeId, head,
				orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("resourcePrimKey=");
		msg.append(resourcePrimKey);

		msg.append(", nodeId=");
		msg.append(nodeId);

		msg.append(", head=");
		msg.append(head);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the last wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByR_N_H_Last(long resourcePrimKey, long nodeId,
		boolean head, OrderByComparator<WikiPage> orderByComparator) {
		int count = countByR_N_H(resourcePrimKey, nodeId, head);

		if (count == 0) {
			return null;
		}

		List<WikiPage> list = findByR_N_H(resourcePrimKey, nodeId, head,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	 */
	@Override
	public WikiPage[] findByR_N_H_PrevAndNext(long pageId,
		long resourcePrimKey, long nodeId, boolean head,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = getByR_N_H_PrevAndNext(session, wikiPage,
					resourcePrimKey, nodeId, head, orderByComparator, true);

			array[1] = wikiPage;

			array[2] = getByR_N_H_PrevAndNext(session, wikiPage,
					resourcePrimKey, nodeId, head, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected WikiPage getByR_N_H_PrevAndNext(Session session,
		WikiPage wikiPage, long resourcePrimKey, long nodeId, boolean head,
		OrderByComparator<WikiPage> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGE_WHERE);

		query.append(_FINDER_COLUMN_R_N_H_RESOURCEPRIMKEY_2);

		query.append(_FINDER_COLUMN_R_N_H_NODEID_2);

		query.append(_FINDER_COLUMN_R_N_H_HEAD_2);

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
			query.append(WikiPageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(resourcePrimKey);

		qPos.add(nodeId);

		qPos.add(head);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and head = &#63; from the database.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param head the head
	 */
	@Override
	public void removeByR_N_H(long resourcePrimKey, long nodeId, boolean head) {
		for (WikiPage wikiPage : findByR_N_H(resourcePrimKey, nodeId, head,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(wikiPage);
		}
	}

	/**
	 * Returns the number of wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param head the head
	 * @return the number of matching wiki pages
	 */
	@Override
	public int countByR_N_H(long resourcePrimKey, long nodeId, boolean head) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_R_N_H;

		Object[] finderArgs = new Object[] { resourcePrimKey, nodeId, head };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_R_N_H_RESOURCEPRIMKEY_2);

			query.append(_FINDER_COLUMN_R_N_H_NODEID_2);

			query.append(_FINDER_COLUMN_R_N_H_HEAD_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourcePrimKey);

				qPos.add(nodeId);

				qPos.add(head);

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

	private static final String _FINDER_COLUMN_R_N_H_RESOURCEPRIMKEY_2 = "wikiPage.resourcePrimKey = ? AND ";
	private static final String _FINDER_COLUMN_R_N_H_NODEID_2 = "wikiPage.nodeId = ? AND ";
	private static final String _FINDER_COLUMN_R_N_H_HEAD_2 = "wikiPage.head = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_R_N_S = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByR_N_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_N_S = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByR_N_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			WikiPageModelImpl.RESOURCEPRIMKEY_COLUMN_BITMASK |
			WikiPageModelImpl.NODEID_COLUMN_BITMASK |
			WikiPageModelImpl.STATUS_COLUMN_BITMASK |
			WikiPageModelImpl.TITLE_COLUMN_BITMASK |
			WikiPageModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_R_N_S = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByR_N_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param status the status
	 * @return the matching wiki pages
	 */
	@Override
	public List<WikiPage> findByR_N_S(long resourcePrimKey, long nodeId,
		int status) {
		return findByR_N_S(resourcePrimKey, nodeId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByR_N_S(long resourcePrimKey, long nodeId,
		int status, int start, int end) {
		return findByR_N_S(resourcePrimKey, nodeId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByR_N_S(long resourcePrimKey, long nodeId,
		int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return findByR_N_S(resourcePrimKey, nodeId, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByR_N_S(long resourcePrimKey, long nodeId,
		int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_N_S;
			finderArgs = new Object[] { resourcePrimKey, nodeId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_R_N_S;
			finderArgs = new Object[] {
					resourcePrimKey, nodeId, status,
					
					start, end, orderByComparator
				};
		}

		List<WikiPage> list = null;

		if (retrieveFromCache) {
			list = (List<WikiPage>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WikiPage wikiPage : list) {
					if ((resourcePrimKey != wikiPage.getResourcePrimKey()) ||
							(nodeId != wikiPage.getNodeId()) ||
							(status != wikiPage.getStatus())) {
						list = null;

						break;
					}
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

			query.append(_SQL_SELECT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_R_N_S_RESOURCEPRIMKEY_2);

			query.append(_FINDER_COLUMN_R_N_S_NODEID_2);

			query.append(_FINDER_COLUMN_R_N_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(WikiPageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourcePrimKey);

				qPos.add(nodeId);

				qPos.add(status);

				if (!pagination) {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByR_N_S_First(long resourcePrimKey, long nodeId,
		int status, OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByR_N_S_First(resourcePrimKey, nodeId, status,
				orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("resourcePrimKey=");
		msg.append(resourcePrimKey);

		msg.append(", nodeId=");
		msg.append(nodeId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the first wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByR_N_S_First(long resourcePrimKey, long nodeId,
		int status, OrderByComparator<WikiPage> orderByComparator) {
		List<WikiPage> list = findByR_N_S(resourcePrimKey, nodeId, status, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByR_N_S_Last(long resourcePrimKey, long nodeId,
		int status, OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByR_N_S_Last(resourcePrimKey, nodeId, status,
				orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("resourcePrimKey=");
		msg.append(resourcePrimKey);

		msg.append(", nodeId=");
		msg.append(nodeId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the last wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByR_N_S_Last(long resourcePrimKey, long nodeId,
		int status, OrderByComparator<WikiPage> orderByComparator) {
		int count = countByR_N_S(resourcePrimKey, nodeId, status);

		if (count == 0) {
			return null;
		}

		List<WikiPage> list = findByR_N_S(resourcePrimKey, nodeId, status,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where resourcePrimKey = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	 */
	@Override
	public WikiPage[] findByR_N_S_PrevAndNext(long pageId,
		long resourcePrimKey, long nodeId, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = getByR_N_S_PrevAndNext(session, wikiPage,
					resourcePrimKey, nodeId, status, orderByComparator, true);

			array[1] = wikiPage;

			array[2] = getByR_N_S_PrevAndNext(session, wikiPage,
					resourcePrimKey, nodeId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected WikiPage getByR_N_S_PrevAndNext(Session session,
		WikiPage wikiPage, long resourcePrimKey, long nodeId, int status,
		OrderByComparator<WikiPage> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGE_WHERE);

		query.append(_FINDER_COLUMN_R_N_S_RESOURCEPRIMKEY_2);

		query.append(_FINDER_COLUMN_R_N_S_NODEID_2);

		query.append(_FINDER_COLUMN_R_N_S_STATUS_2);

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
			query.append(WikiPageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(resourcePrimKey);

		qPos.add(nodeId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and status = &#63; from the database.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param status the status
	 */
	@Override
	public void removeByR_N_S(long resourcePrimKey, long nodeId, int status) {
		for (WikiPage wikiPage : findByR_N_S(resourcePrimKey, nodeId, status,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(wikiPage);
		}
	}

	/**
	 * Returns the number of wiki pages where resourcePrimKey = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param nodeId the node ID
	 * @param status the status
	 * @return the number of matching wiki pages
	 */
	@Override
	public int countByR_N_S(long resourcePrimKey, long nodeId, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_R_N_S;

		Object[] finderArgs = new Object[] { resourcePrimKey, nodeId, status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_R_N_S_RESOURCEPRIMKEY_2);

			query.append(_FINDER_COLUMN_R_N_S_NODEID_2);

			query.append(_FINDER_COLUMN_R_N_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourcePrimKey);

				qPos.add(nodeId);

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_R_N_S_RESOURCEPRIMKEY_2 = "wikiPage.resourcePrimKey = ? AND ";
	private static final String _FINDER_COLUMN_R_N_S_NODEID_2 = "wikiPage.nodeId = ? AND ";
	private static final String _FINDER_COLUMN_R_N_S_STATUS_2 = "wikiPage.status = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_N_H = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_N_H",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_N_H = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_N_H",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			WikiPageModelImpl.GROUPID_COLUMN_BITMASK |
			WikiPageModelImpl.NODEID_COLUMN_BITMASK |
			WikiPageModelImpl.HEAD_COLUMN_BITMASK |
			WikiPageModelImpl.TITLE_COLUMN_BITMASK |
			WikiPageModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_N_H = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_N_H",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});

	/**
	 * Returns all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @return the matching wiki pages
	 */
	@Override
	public List<WikiPage> findByG_N_H(long groupId, long nodeId, boolean head) {
		return findByG_N_H(groupId, nodeId, head, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByG_N_H(long groupId, long nodeId, boolean head,
		int start, int end) {
		return findByG_N_H(groupId, nodeId, head, start, end, null);
	}

	/**
	 * Returns an ordered range of all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByG_N_H(long groupId, long nodeId, boolean head,
		int start, int end, OrderByComparator<WikiPage> orderByComparator) {
		return findByG_N_H(groupId, nodeId, head, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByG_N_H(long groupId, long nodeId, boolean head,
		int start, int end, OrderByComparator<WikiPage> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_N_H;
			finderArgs = new Object[] { groupId, nodeId, head };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_N_H;
			finderArgs = new Object[] {
					groupId, nodeId, head,
					
					start, end, orderByComparator
				};
		}

		List<WikiPage> list = null;

		if (retrieveFromCache) {
			list = (List<WikiPage>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WikiPage wikiPage : list) {
					if ((groupId != wikiPage.getGroupId()) ||
							(nodeId != wikiPage.getNodeId()) ||
							(head != wikiPage.getHead())) {
						list = null;

						break;
					}
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

			query.append(_SQL_SELECT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_G_N_H_GROUPID_2);

			query.append(_FINDER_COLUMN_G_N_H_NODEID_2);

			query.append(_FINDER_COLUMN_G_N_H_HEAD_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(WikiPageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(nodeId);

				qPos.add(head);

				if (!pagination) {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByG_N_H_First(long groupId, long nodeId, boolean head,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByG_N_H_First(groupId, nodeId, head,
				orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", nodeId=");
		msg.append(nodeId);

		msg.append(", head=");
		msg.append(head);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the first wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByG_N_H_First(long groupId, long nodeId, boolean head,
		OrderByComparator<WikiPage> orderByComparator) {
		List<WikiPage> list = findByG_N_H(groupId, nodeId, head, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByG_N_H_Last(long groupId, long nodeId, boolean head,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByG_N_H_Last(groupId, nodeId, head,
				orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", nodeId=");
		msg.append(nodeId);

		msg.append(", head=");
		msg.append(head);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the last wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByG_N_H_Last(long groupId, long nodeId, boolean head,
		OrderByComparator<WikiPage> orderByComparator) {
		int count = countByG_N_H(groupId, nodeId, head);

		if (count == 0) {
			return null;
		}

		List<WikiPage> list = findByG_N_H(groupId, nodeId, head, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	 */
	@Override
	public WikiPage[] findByG_N_H_PrevAndNext(long pageId, long groupId,
		long nodeId, boolean head, OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = getByG_N_H_PrevAndNext(session, wikiPage, groupId,
					nodeId, head, orderByComparator, true);

			array[1] = wikiPage;

			array[2] = getByG_N_H_PrevAndNext(session, wikiPage, groupId,
					nodeId, head, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected WikiPage getByG_N_H_PrevAndNext(Session session,
		WikiPage wikiPage, long groupId, long nodeId, boolean head,
		OrderByComparator<WikiPage> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGE_WHERE);

		query.append(_FINDER_COLUMN_G_N_H_GROUPID_2);

		query.append(_FINDER_COLUMN_G_N_H_NODEID_2);

		query.append(_FINDER_COLUMN_G_N_H_HEAD_2);

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
			query.append(WikiPageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(nodeId);

		qPos.add(head);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @return the matching wiki pages that the user has permission to view
	 */
	@Override
	public List<WikiPage> filterFindByG_N_H(long groupId, long nodeId,
		boolean head) {
		return filterFindByG_N_H(groupId, nodeId, head, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages that the user has permission to view
	 */
	@Override
	public List<WikiPage> filterFindByG_N_H(long groupId, long nodeId,
		boolean head, int start, int end) {
		return filterFindByG_N_H(groupId, nodeId, head, start, end, null);
	}

	/**
	 * Returns an ordered range of all the wiki pages that the user has permissions to view where groupId = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages that the user has permission to view
	 */
	@Override
	public List<WikiPage> filterFindByG_N_H(long groupId, long nodeId,
		boolean head, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_N_H(groupId, nodeId, head, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_WIKIPAGE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_WIKIPAGE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_N_H_GROUPID_2);

		query.append(_FINDER_COLUMN_G_N_H_NODEID_2);

		query.append(_FINDER_COLUMN_G_N_H_HEAD_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_WIKIPAGE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(WikiPageModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(WikiPageModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				WikiPage.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, WikiPageImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, WikiPageImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(nodeId);

			qPos.add(head);

			return (List<WikiPage>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set of wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	 */
	@Override
	public WikiPage[] filterFindByG_N_H_PrevAndNext(long pageId, long groupId,
		long nodeId, boolean head, OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_N_H_PrevAndNext(pageId, groupId, nodeId, head,
				orderByComparator);
		}

		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = filterGetByG_N_H_PrevAndNext(session, wikiPage, groupId,
					nodeId, head, orderByComparator, true);

			array[1] = wikiPage;

			array[2] = filterGetByG_N_H_PrevAndNext(session, wikiPage, groupId,
					nodeId, head, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected WikiPage filterGetByG_N_H_PrevAndNext(Session session,
		WikiPage wikiPage, long groupId, long nodeId, boolean head,
		OrderByComparator<WikiPage> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_WIKIPAGE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_WIKIPAGE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_N_H_GROUPID_2);

		query.append(_FINDER_COLUMN_G_N_H_NODEID_2);

		query.append(_FINDER_COLUMN_G_N_H_HEAD_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_WIKIPAGE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(WikiPageModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(WikiPageModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				WikiPage.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, WikiPageImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, WikiPageImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(nodeId);

		qPos.add(head);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 */
	@Override
	public void removeByG_N_H(long groupId, long nodeId, boolean head) {
		for (WikiPage wikiPage : findByG_N_H(groupId, nodeId, head,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(wikiPage);
		}
	}

	/**
	 * Returns the number of wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @return the number of matching wiki pages
	 */
	@Override
	public int countByG_N_H(long groupId, long nodeId, boolean head) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_N_H;

		Object[] finderArgs = new Object[] { groupId, nodeId, head };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_G_N_H_GROUPID_2);

			query.append(_FINDER_COLUMN_G_N_H_NODEID_2);

			query.append(_FINDER_COLUMN_G_N_H_HEAD_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(nodeId);

				qPos.add(head);

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
	 * Returns the number of wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @return the number of matching wiki pages that the user has permission to view
	 */
	@Override
	public int filterCountByG_N_H(long groupId, long nodeId, boolean head) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_N_H(groupId, nodeId, head);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_WIKIPAGE_WHERE);

		query.append(_FINDER_COLUMN_G_N_H_GROUPID_2);

		query.append(_FINDER_COLUMN_G_N_H_NODEID_2);

		query.append(_FINDER_COLUMN_G_N_H_HEAD_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				WikiPage.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(nodeId);

			qPos.add(head);

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

	private static final String _FINDER_COLUMN_G_N_H_GROUPID_2 = "wikiPage.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_N_H_NODEID_2 = "wikiPage.nodeId = ? AND ";
	private static final String _FINDER_COLUMN_G_N_H_HEAD_2 = "wikiPage.head = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_N_S = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_N_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_N_S = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_N_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			WikiPageModelImpl.GROUPID_COLUMN_BITMASK |
			WikiPageModelImpl.NODEID_COLUMN_BITMASK |
			WikiPageModelImpl.STATUS_COLUMN_BITMASK |
			WikiPageModelImpl.TITLE_COLUMN_BITMASK |
			WikiPageModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_N_S = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_N_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the wiki pages where groupId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @return the matching wiki pages
	 */
	@Override
	public List<WikiPage> findByG_N_S(long groupId, long nodeId, int status) {
		return findByG_N_S(groupId, nodeId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the wiki pages where groupId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByG_N_S(long groupId, long nodeId, int status,
		int start, int end) {
		return findByG_N_S(groupId, nodeId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the wiki pages where groupId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByG_N_S(long groupId, long nodeId, int status,
		int start, int end, OrderByComparator<WikiPage> orderByComparator) {
		return findByG_N_S(groupId, nodeId, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the wiki pages where groupId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByG_N_S(long groupId, long nodeId, int status,
		int start, int end, OrderByComparator<WikiPage> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_N_S;
			finderArgs = new Object[] { groupId, nodeId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_N_S;
			finderArgs = new Object[] {
					groupId, nodeId, status,
					
					start, end, orderByComparator
				};
		}

		List<WikiPage> list = null;

		if (retrieveFromCache) {
			list = (List<WikiPage>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WikiPage wikiPage : list) {
					if ((groupId != wikiPage.getGroupId()) ||
							(nodeId != wikiPage.getNodeId()) ||
							(status != wikiPage.getStatus())) {
						list = null;

						break;
					}
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

			query.append(_SQL_SELECT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_G_N_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_N_S_NODEID_2);

			query.append(_FINDER_COLUMN_G_N_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(WikiPageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(nodeId);

				qPos.add(status);

				if (!pagination) {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByG_N_S_First(long groupId, long nodeId, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByG_N_S_First(groupId, nodeId, status,
				orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", nodeId=");
		msg.append(nodeId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the first wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByG_N_S_First(long groupId, long nodeId, int status,
		OrderByComparator<WikiPage> orderByComparator) {
		List<WikiPage> list = findByG_N_S(groupId, nodeId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByG_N_S_Last(long groupId, long nodeId, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByG_N_S_Last(groupId, nodeId, status,
				orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", nodeId=");
		msg.append(nodeId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the last wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByG_N_S_Last(long groupId, long nodeId, int status,
		OrderByComparator<WikiPage> orderByComparator) {
		int count = countByG_N_S(groupId, nodeId, status);

		if (count == 0) {
			return null;
		}

		List<WikiPage> list = findByG_N_S(groupId, nodeId, status, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	 */
	@Override
	public WikiPage[] findByG_N_S_PrevAndNext(long pageId, long groupId,
		long nodeId, int status, OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = getByG_N_S_PrevAndNext(session, wikiPage, groupId,
					nodeId, status, orderByComparator, true);

			array[1] = wikiPage;

			array[2] = getByG_N_S_PrevAndNext(session, wikiPage, groupId,
					nodeId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected WikiPage getByG_N_S_PrevAndNext(Session session,
		WikiPage wikiPage, long groupId, long nodeId, int status,
		OrderByComparator<WikiPage> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGE_WHERE);

		query.append(_FINDER_COLUMN_G_N_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_N_S_NODEID_2);

		query.append(_FINDER_COLUMN_G_N_S_STATUS_2);

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
			query.append(WikiPageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(nodeId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @return the matching wiki pages that the user has permission to view
	 */
	@Override
	public List<WikiPage> filterFindByG_N_S(long groupId, long nodeId,
		int status) {
		return filterFindByG_N_S(groupId, nodeId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages that the user has permission to view
	 */
	@Override
	public List<WikiPage> filterFindByG_N_S(long groupId, long nodeId,
		int status, int start, int end) {
		return filterFindByG_N_S(groupId, nodeId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the wiki pages that the user has permissions to view where groupId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages that the user has permission to view
	 */
	@Override
	public List<WikiPage> filterFindByG_N_S(long groupId, long nodeId,
		int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_N_S(groupId, nodeId, status, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_WIKIPAGE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_WIKIPAGE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_N_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_N_S_NODEID_2);

		query.append(_FINDER_COLUMN_G_N_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_WIKIPAGE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(WikiPageModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(WikiPageModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				WikiPage.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, WikiPageImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, WikiPageImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(nodeId);

			qPos.add(status);

			return (List<WikiPage>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set of wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	 */
	@Override
	public WikiPage[] filterFindByG_N_S_PrevAndNext(long pageId, long groupId,
		long nodeId, int status, OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_N_S_PrevAndNext(pageId, groupId, nodeId, status,
				orderByComparator);
		}

		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = filterGetByG_N_S_PrevAndNext(session, wikiPage, groupId,
					nodeId, status, orderByComparator, true);

			array[1] = wikiPage;

			array[2] = filterGetByG_N_S_PrevAndNext(session, wikiPage, groupId,
					nodeId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected WikiPage filterGetByG_N_S_PrevAndNext(Session session,
		WikiPage wikiPage, long groupId, long nodeId, int status,
		OrderByComparator<WikiPage> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_WIKIPAGE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_WIKIPAGE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_N_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_N_S_NODEID_2);

		query.append(_FINDER_COLUMN_G_N_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_WIKIPAGE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(WikiPageModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(WikiPageModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				WikiPage.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, WikiPageImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, WikiPageImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(nodeId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the wiki pages where groupId = &#63; and nodeId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param status the status
	 */
	@Override
	public void removeByG_N_S(long groupId, long nodeId, int status) {
		for (WikiPage wikiPage : findByG_N_S(groupId, nodeId, status,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(wikiPage);
		}
	}

	/**
	 * Returns the number of wiki pages where groupId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @return the number of matching wiki pages
	 */
	@Override
	public int countByG_N_S(long groupId, long nodeId, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_N_S;

		Object[] finderArgs = new Object[] { groupId, nodeId, status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_G_N_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_N_S_NODEID_2);

			query.append(_FINDER_COLUMN_G_N_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(nodeId);

				qPos.add(status);

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
	 * Returns the number of wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @return the number of matching wiki pages that the user has permission to view
	 */
	@Override
	public int filterCountByG_N_S(long groupId, long nodeId, int status) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_N_S(groupId, nodeId, status);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_WIKIPAGE_WHERE);

		query.append(_FINDER_COLUMN_G_N_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_N_S_NODEID_2);

		query.append(_FINDER_COLUMN_G_N_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				WikiPage.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(nodeId);

			qPos.add(status);

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

	private static final String _FINDER_COLUMN_G_N_S_GROUPID_2 = "wikiPage.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_N_S_NODEID_2 = "wikiPage.nodeId = ? AND ";
	private static final String _FINDER_COLUMN_G_N_S_STATUS_2 = "wikiPage.status = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_U_N_S = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_N_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_N_S = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_N_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			WikiPageModelImpl.USERID_COLUMN_BITMASK |
			WikiPageModelImpl.NODEID_COLUMN_BITMASK |
			WikiPageModelImpl.STATUS_COLUMN_BITMASK |
			WikiPageModelImpl.TITLE_COLUMN_BITMASK |
			WikiPageModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U_N_S = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_N_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the wiki pages where userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @return the matching wiki pages
	 */
	@Override
	public List<WikiPage> findByU_N_S(long userId, long nodeId, int status) {
		return findByU_N_S(userId, nodeId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the wiki pages where userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByU_N_S(long userId, long nodeId, int status,
		int start, int end) {
		return findByU_N_S(userId, nodeId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the wiki pages where userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByU_N_S(long userId, long nodeId, int status,
		int start, int end, OrderByComparator<WikiPage> orderByComparator) {
		return findByU_N_S(userId, nodeId, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the wiki pages where userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByU_N_S(long userId, long nodeId, int status,
		int start, int end, OrderByComparator<WikiPage> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_N_S;
			finderArgs = new Object[] { userId, nodeId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_U_N_S;
			finderArgs = new Object[] {
					userId, nodeId, status,
					
					start, end, orderByComparator
				};
		}

		List<WikiPage> list = null;

		if (retrieveFromCache) {
			list = (List<WikiPage>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WikiPage wikiPage : list) {
					if ((userId != wikiPage.getUserId()) ||
							(nodeId != wikiPage.getNodeId()) ||
							(status != wikiPage.getStatus())) {
						list = null;

						break;
					}
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

			query.append(_SQL_SELECT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_U_N_S_USERID_2);

			query.append(_FINDER_COLUMN_U_N_S_NODEID_2);

			query.append(_FINDER_COLUMN_U_N_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(WikiPageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(nodeId);

				qPos.add(status);

				if (!pagination) {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first wiki page in the ordered set where userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByU_N_S_First(long userId, long nodeId, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByU_N_S_First(userId, nodeId, status,
				orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", nodeId=");
		msg.append(nodeId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the first wiki page in the ordered set where userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByU_N_S_First(long userId, long nodeId, int status,
		OrderByComparator<WikiPage> orderByComparator) {
		List<WikiPage> list = findByU_N_S(userId, nodeId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last wiki page in the ordered set where userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByU_N_S_Last(long userId, long nodeId, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByU_N_S_Last(userId, nodeId, status,
				orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", nodeId=");
		msg.append(nodeId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the last wiki page in the ordered set where userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByU_N_S_Last(long userId, long nodeId, int status,
		OrderByComparator<WikiPage> orderByComparator) {
		int count = countByU_N_S(userId, nodeId, status);

		if (count == 0) {
			return null;
		}

		List<WikiPage> list = findByU_N_S(userId, nodeId, status, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	 */
	@Override
	public WikiPage[] findByU_N_S_PrevAndNext(long pageId, long userId,
		long nodeId, int status, OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = getByU_N_S_PrevAndNext(session, wikiPage, userId,
					nodeId, status, orderByComparator, true);

			array[1] = wikiPage;

			array[2] = getByU_N_S_PrevAndNext(session, wikiPage, userId,
					nodeId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected WikiPage getByU_N_S_PrevAndNext(Session session,
		WikiPage wikiPage, long userId, long nodeId, int status,
		OrderByComparator<WikiPage> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGE_WHERE);

		query.append(_FINDER_COLUMN_U_N_S_USERID_2);

		query.append(_FINDER_COLUMN_U_N_S_NODEID_2);

		query.append(_FINDER_COLUMN_U_N_S_STATUS_2);

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
			query.append(WikiPageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		qPos.add(nodeId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the wiki pages where userId = &#63; and nodeId = &#63; and status = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 */
	@Override
	public void removeByU_N_S(long userId, long nodeId, int status) {
		for (WikiPage wikiPage : findByU_N_S(userId, nodeId, status,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(wikiPage);
		}
	}

	/**
	 * Returns the number of wiki pages where userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @return the number of matching wiki pages
	 */
	@Override
	public int countByU_N_S(long userId, long nodeId, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_U_N_S;

		Object[] finderArgs = new Object[] { userId, nodeId, status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_U_N_S_USERID_2);

			query.append(_FINDER_COLUMN_U_N_S_NODEID_2);

			query.append(_FINDER_COLUMN_U_N_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(nodeId);

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_U_N_S_USERID_2 = "wikiPage.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_N_S_NODEID_2 = "wikiPage.nodeId = ? AND ";
	private static final String _FINDER_COLUMN_U_N_S_STATUS_2 = "wikiPage.status = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_N_T_V = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByN_T_V",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Double.class.getName()
			},
			WikiPageModelImpl.NODEID_COLUMN_BITMASK |
			WikiPageModelImpl.TITLE_COLUMN_BITMASK |
			WikiPageModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_N_T_V = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByN_T_V",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Double.class.getName()
			});

	/**
	 * Returns the wiki page where nodeId = &#63; and title = &#63; and version = &#63; or throws a {@link com.liferay.wiki.NoSuchPageException} if it could not be found.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param version the version
	 * @return the matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByN_T_V(long nodeId, String title, double version)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByN_T_V(nodeId, title, version);

		if (wikiPage == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("nodeId=");
			msg.append(nodeId);

			msg.append(", title=");
			msg.append(title);

			msg.append(", version=");
			msg.append(version);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchPageException(msg.toString());
		}

		return wikiPage;
	}

	/**
	 * Returns the wiki page where nodeId = &#63; and title = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param version the version
	 * @return the matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByN_T_V(long nodeId, String title, double version) {
		return fetchByN_T_V(nodeId, title, version, true);
	}

	/**
	 * Returns the wiki page where nodeId = &#63; and title = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param version the version
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByN_T_V(long nodeId, String title, double version,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { nodeId, title, version };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_N_T_V,
					finderArgs, this);
		}

		if (result instanceof WikiPage) {
			WikiPage wikiPage = (WikiPage)result;

			if ((nodeId != wikiPage.getNodeId()) ||
					!Validator.equals(title, wikiPage.getTitle()) ||
					(version != wikiPage.getVersion())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_N_T_V_NODEID_2);

			boolean bindTitle = false;

			if (title == null) {
				query.append(_FINDER_COLUMN_N_T_V_TITLE_1);
			}
			else if (title.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_N_T_V_TITLE_3);
			}
			else {
				bindTitle = true;

				query.append(_FINDER_COLUMN_N_T_V_TITLE_2);
			}

			query.append(_FINDER_COLUMN_N_T_V_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				if (bindTitle) {
					qPos.add(StringUtil.toLowerCase(title));
				}

				qPos.add(version);

				List<WikiPage> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_N_T_V,
						finderArgs, list);
				}
				else {
					WikiPage wikiPage = list.get(0);

					result = wikiPage;

					cacheResult(wikiPage);

					if ((wikiPage.getNodeId() != nodeId) ||
							(wikiPage.getTitle() == null) ||
							!wikiPage.getTitle().equals(title) ||
							(wikiPage.getVersion() != version)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_N_T_V,
							finderArgs, wikiPage);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_N_T_V, finderArgs);

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
			return (WikiPage)result;
		}
	}

	/**
	 * Removes the wiki page where nodeId = &#63; and title = &#63; and version = &#63; from the database.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param version the version
	 * @return the wiki page that was removed
	 */
	@Override
	public WikiPage removeByN_T_V(long nodeId, String title, double version)
		throws NoSuchPageException {
		WikiPage wikiPage = findByN_T_V(nodeId, title, version);

		return remove(wikiPage);
	}

	/**
	 * Returns the number of wiki pages where nodeId = &#63; and title = &#63; and version = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param version the version
	 * @return the number of matching wiki pages
	 */
	@Override
	public int countByN_T_V(long nodeId, String title, double version) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_N_T_V;

		Object[] finderArgs = new Object[] { nodeId, title, version };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_N_T_V_NODEID_2);

			boolean bindTitle = false;

			if (title == null) {
				query.append(_FINDER_COLUMN_N_T_V_TITLE_1);
			}
			else if (title.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_N_T_V_TITLE_3);
			}
			else {
				bindTitle = true;

				query.append(_FINDER_COLUMN_N_T_V_TITLE_2);
			}

			query.append(_FINDER_COLUMN_N_T_V_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				if (bindTitle) {
					qPos.add(StringUtil.toLowerCase(title));
				}

				qPos.add(version);

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

	private static final String _FINDER_COLUMN_N_T_V_NODEID_2 = "wikiPage.nodeId = ? AND ";
	private static final String _FINDER_COLUMN_N_T_V_TITLE_1 = "wikiPage.title IS NULL AND ";
	private static final String _FINDER_COLUMN_N_T_V_TITLE_2 = "lower(wikiPage.title) = ? AND ";
	private static final String _FINDER_COLUMN_N_T_V_TITLE_3 = "(wikiPage.title IS NULL OR wikiPage.title = '') AND ";
	private static final String _FINDER_COLUMN_N_T_V_VERSION_2 = "wikiPage.version = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_N_T_H = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByN_T_H",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_T_H = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByN_T_H",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName()
			},
			WikiPageModelImpl.NODEID_COLUMN_BITMASK |
			WikiPageModelImpl.TITLE_COLUMN_BITMASK |
			WikiPageModelImpl.HEAD_COLUMN_BITMASK |
			WikiPageModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_N_T_H = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByN_T_H",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName()
			});

	/**
	 * Returns all the wiki pages where nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @return the matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_T_H(long nodeId, String title, boolean head) {
		return findByN_T_H(nodeId, title, head, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the wiki pages where nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_T_H(long nodeId, String title, boolean head,
		int start, int end) {
		return findByN_T_H(nodeId, title, head, start, end, null);
	}

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_T_H(long nodeId, String title, boolean head,
		int start, int end, OrderByComparator<WikiPage> orderByComparator) {
		return findByN_T_H(nodeId, title, head, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_T_H(long nodeId, String title, boolean head,
		int start, int end, OrderByComparator<WikiPage> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_T_H;
			finderArgs = new Object[] { nodeId, title, head };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_N_T_H;
			finderArgs = new Object[] {
					nodeId, title, head,
					
					start, end, orderByComparator
				};
		}

		List<WikiPage> list = null;

		if (retrieveFromCache) {
			list = (List<WikiPage>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WikiPage wikiPage : list) {
					if ((nodeId != wikiPage.getNodeId()) ||
							!Validator.equals(title, wikiPage.getTitle()) ||
							(head != wikiPage.getHead())) {
						list = null;

						break;
					}
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

			query.append(_SQL_SELECT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_N_T_H_NODEID_2);

			boolean bindTitle = false;

			if (title == null) {
				query.append(_FINDER_COLUMN_N_T_H_TITLE_1);
			}
			else if (title.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_N_T_H_TITLE_3);
			}
			else {
				bindTitle = true;

				query.append(_FINDER_COLUMN_N_T_H_TITLE_2);
			}

			query.append(_FINDER_COLUMN_N_T_H_HEAD_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(WikiPageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				if (bindTitle) {
					qPos.add(StringUtil.toLowerCase(title));
				}

				qPos.add(head);

				if (!pagination) {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByN_T_H_First(long nodeId, String title, boolean head,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByN_T_H_First(nodeId, title, head,
				orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("nodeId=");
		msg.append(nodeId);

		msg.append(", title=");
		msg.append(title);

		msg.append(", head=");
		msg.append(head);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByN_T_H_First(long nodeId, String title, boolean head,
		OrderByComparator<WikiPage> orderByComparator) {
		List<WikiPage> list = findByN_T_H(nodeId, title, head, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByN_T_H_Last(long nodeId, String title, boolean head,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByN_T_H_Last(nodeId, title, head,
				orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("nodeId=");
		msg.append(nodeId);

		msg.append(", title=");
		msg.append(title);

		msg.append(", head=");
		msg.append(head);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByN_T_H_Last(long nodeId, String title, boolean head,
		OrderByComparator<WikiPage> orderByComparator) {
		int count = countByN_T_H(nodeId, title, head);

		if (count == 0) {
			return null;
		}

		List<WikiPage> list = findByN_T_H(nodeId, title, head, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	 */
	@Override
	public WikiPage[] findByN_T_H_PrevAndNext(long pageId, long nodeId,
		String title, boolean head,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = getByN_T_H_PrevAndNext(session, wikiPage, nodeId, title,
					head, orderByComparator, true);

			array[1] = wikiPage;

			array[2] = getByN_T_H_PrevAndNext(session, wikiPage, nodeId, title,
					head, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected WikiPage getByN_T_H_PrevAndNext(Session session,
		WikiPage wikiPage, long nodeId, String title, boolean head,
		OrderByComparator<WikiPage> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGE_WHERE);

		query.append(_FINDER_COLUMN_N_T_H_NODEID_2);

		boolean bindTitle = false;

		if (title == null) {
			query.append(_FINDER_COLUMN_N_T_H_TITLE_1);
		}
		else if (title.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_N_T_H_TITLE_3);
		}
		else {
			bindTitle = true;

			query.append(_FINDER_COLUMN_N_T_H_TITLE_2);
		}

		query.append(_FINDER_COLUMN_N_T_H_HEAD_2);

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
			query.append(WikiPageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(nodeId);

		if (bindTitle) {
			qPos.add(StringUtil.toLowerCase(title));
		}

		qPos.add(head);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the wiki pages where nodeId = &#63; and title = &#63; and head = &#63; from the database.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 */
	@Override
	public void removeByN_T_H(long nodeId, String title, boolean head) {
		for (WikiPage wikiPage : findByN_T_H(nodeId, title, head,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(wikiPage);
		}
	}

	/**
	 * Returns the number of wiki pages where nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @return the number of matching wiki pages
	 */
	@Override
	public int countByN_T_H(long nodeId, String title, boolean head) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_N_T_H;

		Object[] finderArgs = new Object[] { nodeId, title, head };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_N_T_H_NODEID_2);

			boolean bindTitle = false;

			if (title == null) {
				query.append(_FINDER_COLUMN_N_T_H_TITLE_1);
			}
			else if (title.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_N_T_H_TITLE_3);
			}
			else {
				bindTitle = true;

				query.append(_FINDER_COLUMN_N_T_H_TITLE_2);
			}

			query.append(_FINDER_COLUMN_N_T_H_HEAD_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				if (bindTitle) {
					qPos.add(StringUtil.toLowerCase(title));
				}

				qPos.add(head);

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

	private static final String _FINDER_COLUMN_N_T_H_NODEID_2 = "wikiPage.nodeId = ? AND ";
	private static final String _FINDER_COLUMN_N_T_H_TITLE_1 = "wikiPage.title IS NULL AND ";
	private static final String _FINDER_COLUMN_N_T_H_TITLE_2 = "lower(wikiPage.title) = ? AND ";
	private static final String _FINDER_COLUMN_N_T_H_TITLE_3 = "(wikiPage.title IS NULL OR wikiPage.title = '') AND ";
	private static final String _FINDER_COLUMN_N_T_H_HEAD_2 = "wikiPage.head = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_N_T_S = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByN_T_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_T_S = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByN_T_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			WikiPageModelImpl.NODEID_COLUMN_BITMASK |
			WikiPageModelImpl.TITLE_COLUMN_BITMASK |
			WikiPageModelImpl.STATUS_COLUMN_BITMASK |
			WikiPageModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_N_T_S = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByN_T_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the wiki pages where nodeId = &#63; and title = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param status the status
	 * @return the matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_T_S(long nodeId, String title, int status) {
		return findByN_T_S(nodeId, title, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the wiki pages where nodeId = &#63; and title = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_T_S(long nodeId, String title, int status,
		int start, int end) {
		return findByN_T_S(nodeId, title, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and title = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_T_S(long nodeId, String title, int status,
		int start, int end, OrderByComparator<WikiPage> orderByComparator) {
		return findByN_T_S(nodeId, title, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and title = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_T_S(long nodeId, String title, int status,
		int start, int end, OrderByComparator<WikiPage> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_T_S;
			finderArgs = new Object[] { nodeId, title, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_N_T_S;
			finderArgs = new Object[] {
					nodeId, title, status,
					
					start, end, orderByComparator
				};
		}

		List<WikiPage> list = null;

		if (retrieveFromCache) {
			list = (List<WikiPage>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WikiPage wikiPage : list) {
					if ((nodeId != wikiPage.getNodeId()) ||
							!Validator.equals(title, wikiPage.getTitle()) ||
							(status != wikiPage.getStatus())) {
						list = null;

						break;
					}
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

			query.append(_SQL_SELECT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_N_T_S_NODEID_2);

			boolean bindTitle = false;

			if (title == null) {
				query.append(_FINDER_COLUMN_N_T_S_TITLE_1);
			}
			else if (title.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_N_T_S_TITLE_3);
			}
			else {
				bindTitle = true;

				query.append(_FINDER_COLUMN_N_T_S_TITLE_2);
			}

			query.append(_FINDER_COLUMN_N_T_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(WikiPageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				if (bindTitle) {
					qPos.add(StringUtil.toLowerCase(title));
				}

				qPos.add(status);

				if (!pagination) {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and title = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByN_T_S_First(long nodeId, String title, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByN_T_S_First(nodeId, title, status,
				orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("nodeId=");
		msg.append(nodeId);

		msg.append(", title=");
		msg.append(title);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and title = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByN_T_S_First(long nodeId, String title, int status,
		OrderByComparator<WikiPage> orderByComparator) {
		List<WikiPage> list = findByN_T_S(nodeId, title, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and title = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByN_T_S_Last(long nodeId, String title, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByN_T_S_Last(nodeId, title, status,
				orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("nodeId=");
		msg.append(nodeId);

		msg.append(", title=");
		msg.append(title);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and title = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByN_T_S_Last(long nodeId, String title, int status,
		OrderByComparator<WikiPage> orderByComparator) {
		int count = countByN_T_S(nodeId, title, status);

		if (count == 0) {
			return null;
		}

		List<WikiPage> list = findByN_T_S(nodeId, title, status, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and title = &#63; and status = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param nodeId the node ID
	 * @param title the title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	 */
	@Override
	public WikiPage[] findByN_T_S_PrevAndNext(long pageId, long nodeId,
		String title, int status, OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = getByN_T_S_PrevAndNext(session, wikiPage, nodeId, title,
					status, orderByComparator, true);

			array[1] = wikiPage;

			array[2] = getByN_T_S_PrevAndNext(session, wikiPage, nodeId, title,
					status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected WikiPage getByN_T_S_PrevAndNext(Session session,
		WikiPage wikiPage, long nodeId, String title, int status,
		OrderByComparator<WikiPage> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGE_WHERE);

		query.append(_FINDER_COLUMN_N_T_S_NODEID_2);

		boolean bindTitle = false;

		if (title == null) {
			query.append(_FINDER_COLUMN_N_T_S_TITLE_1);
		}
		else if (title.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_N_T_S_TITLE_3);
		}
		else {
			bindTitle = true;

			query.append(_FINDER_COLUMN_N_T_S_TITLE_2);
		}

		query.append(_FINDER_COLUMN_N_T_S_STATUS_2);

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
			query.append(WikiPageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(nodeId);

		if (bindTitle) {
			qPos.add(StringUtil.toLowerCase(title));
		}

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the wiki pages where nodeId = &#63; and title = &#63; and status = &#63; from the database.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param status the status
	 */
	@Override
	public void removeByN_T_S(long nodeId, String title, int status) {
		for (WikiPage wikiPage : findByN_T_S(nodeId, title, status,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(wikiPage);
		}
	}

	/**
	 * Returns the number of wiki pages where nodeId = &#63; and title = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param status the status
	 * @return the number of matching wiki pages
	 */
	@Override
	public int countByN_T_S(long nodeId, String title, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_N_T_S;

		Object[] finderArgs = new Object[] { nodeId, title, status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_N_T_S_NODEID_2);

			boolean bindTitle = false;

			if (title == null) {
				query.append(_FINDER_COLUMN_N_T_S_TITLE_1);
			}
			else if (title.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_N_T_S_TITLE_3);
			}
			else {
				bindTitle = true;

				query.append(_FINDER_COLUMN_N_T_S_TITLE_2);
			}

			query.append(_FINDER_COLUMN_N_T_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				if (bindTitle) {
					qPos.add(StringUtil.toLowerCase(title));
				}

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_N_T_S_NODEID_2 = "wikiPage.nodeId = ? AND ";
	private static final String _FINDER_COLUMN_N_T_S_TITLE_1 = "wikiPage.title IS NULL AND ";
	private static final String _FINDER_COLUMN_N_T_S_TITLE_2 = "lower(wikiPage.title) = ? AND ";
	private static final String _FINDER_COLUMN_N_T_S_TITLE_3 = "(wikiPage.title IS NULL OR wikiPage.title = '') AND ";
	private static final String _FINDER_COLUMN_N_T_S_STATUS_2 = "wikiPage.status = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_N_H_P = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByN_H_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_H_P = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByN_H_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			},
			WikiPageModelImpl.NODEID_COLUMN_BITMASK |
			WikiPageModelImpl.HEAD_COLUMN_BITMASK |
			WikiPageModelImpl.PARENTTITLE_COLUMN_BITMASK |
			WikiPageModelImpl.TITLE_COLUMN_BITMASK |
			WikiPageModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_N_H_P = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByN_H_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @return the matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_H_P(long nodeId, boolean head,
		String parentTitle) {
		return findByN_H_P(nodeId, head, parentTitle, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_H_P(long nodeId, boolean head,
		String parentTitle, int start, int end) {
		return findByN_H_P(nodeId, head, parentTitle, start, end, null);
	}

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_H_P(long nodeId, boolean head,
		String parentTitle, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return findByN_H_P(nodeId, head, parentTitle, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_H_P(long nodeId, boolean head,
		String parentTitle, int start, int end,
		OrderByComparator<WikiPage> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_H_P;
			finderArgs = new Object[] { nodeId, head, parentTitle };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_N_H_P;
			finderArgs = new Object[] {
					nodeId, head, parentTitle,
					
					start, end, orderByComparator
				};
		}

		List<WikiPage> list = null;

		if (retrieveFromCache) {
			list = (List<WikiPage>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WikiPage wikiPage : list) {
					if ((nodeId != wikiPage.getNodeId()) ||
							(head != wikiPage.getHead()) ||
							!Validator.equals(parentTitle,
								wikiPage.getParentTitle())) {
						list = null;

						break;
					}
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

			query.append(_SQL_SELECT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_N_H_P_NODEID_2);

			query.append(_FINDER_COLUMN_N_H_P_HEAD_2);

			boolean bindParentTitle = false;

			if (parentTitle == null) {
				query.append(_FINDER_COLUMN_N_H_P_PARENTTITLE_1);
			}
			else if (parentTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_N_H_P_PARENTTITLE_3);
			}
			else {
				bindParentTitle = true;

				query.append(_FINDER_COLUMN_N_H_P_PARENTTITLE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(WikiPageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				qPos.add(head);

				if (bindParentTitle) {
					qPos.add(StringUtil.toLowerCase(parentTitle));
				}

				if (!pagination) {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByN_H_P_First(long nodeId, boolean head,
		String parentTitle, OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByN_H_P_First(nodeId, head, parentTitle,
				orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("nodeId=");
		msg.append(nodeId);

		msg.append(", head=");
		msg.append(head);

		msg.append(", parentTitle=");
		msg.append(parentTitle);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByN_H_P_First(long nodeId, boolean head,
		String parentTitle, OrderByComparator<WikiPage> orderByComparator) {
		List<WikiPage> list = findByN_H_P(nodeId, head, parentTitle, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByN_H_P_Last(long nodeId, boolean head,
		String parentTitle, OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByN_H_P_Last(nodeId, head, parentTitle,
				orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("nodeId=");
		msg.append(nodeId);

		msg.append(", head=");
		msg.append(head);

		msg.append(", parentTitle=");
		msg.append(parentTitle);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByN_H_P_Last(long nodeId, boolean head,
		String parentTitle, OrderByComparator<WikiPage> orderByComparator) {
		int count = countByN_H_P(nodeId, head, parentTitle);

		if (count == 0) {
			return null;
		}

		List<WikiPage> list = findByN_H_P(nodeId, head, parentTitle, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	 */
	@Override
	public WikiPage[] findByN_H_P_PrevAndNext(long pageId, long nodeId,
		boolean head, String parentTitle,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = getByN_H_P_PrevAndNext(session, wikiPage, nodeId, head,
					parentTitle, orderByComparator, true);

			array[1] = wikiPage;

			array[2] = getByN_H_P_PrevAndNext(session, wikiPage, nodeId, head,
					parentTitle, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected WikiPage getByN_H_P_PrevAndNext(Session session,
		WikiPage wikiPage, long nodeId, boolean head, String parentTitle,
		OrderByComparator<WikiPage> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGE_WHERE);

		query.append(_FINDER_COLUMN_N_H_P_NODEID_2);

		query.append(_FINDER_COLUMN_N_H_P_HEAD_2);

		boolean bindParentTitle = false;

		if (parentTitle == null) {
			query.append(_FINDER_COLUMN_N_H_P_PARENTTITLE_1);
		}
		else if (parentTitle.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_N_H_P_PARENTTITLE_3);
		}
		else {
			bindParentTitle = true;

			query.append(_FINDER_COLUMN_N_H_P_PARENTTITLE_2);
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
			query.append(WikiPageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(nodeId);

		qPos.add(head);

		if (bindParentTitle) {
			qPos.add(StringUtil.toLowerCase(parentTitle));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; from the database.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 */
	@Override
	public void removeByN_H_P(long nodeId, boolean head, String parentTitle) {
		for (WikiPage wikiPage : findByN_H_P(nodeId, head, parentTitle,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(wikiPage);
		}
	}

	/**
	 * Returns the number of wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @return the number of matching wiki pages
	 */
	@Override
	public int countByN_H_P(long nodeId, boolean head, String parentTitle) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_N_H_P;

		Object[] finderArgs = new Object[] { nodeId, head, parentTitle };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_N_H_P_NODEID_2);

			query.append(_FINDER_COLUMN_N_H_P_HEAD_2);

			boolean bindParentTitle = false;

			if (parentTitle == null) {
				query.append(_FINDER_COLUMN_N_H_P_PARENTTITLE_1);
			}
			else if (parentTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_N_H_P_PARENTTITLE_3);
			}
			else {
				bindParentTitle = true;

				query.append(_FINDER_COLUMN_N_H_P_PARENTTITLE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				qPos.add(head);

				if (bindParentTitle) {
					qPos.add(StringUtil.toLowerCase(parentTitle));
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

	private static final String _FINDER_COLUMN_N_H_P_NODEID_2 = "wikiPage.nodeId = ? AND ";
	private static final String _FINDER_COLUMN_N_H_P_HEAD_2 = "wikiPage.head = ? AND ";
	private static final String _FINDER_COLUMN_N_H_P_PARENTTITLE_1 = "wikiPage.parentTitle IS NULL";
	private static final String _FINDER_COLUMN_N_H_P_PARENTTITLE_2 = "lower(wikiPage.parentTitle) = ?";
	private static final String _FINDER_COLUMN_N_H_P_PARENTTITLE_3 = "(wikiPage.parentTitle IS NULL OR wikiPage.parentTitle = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_N_H_R = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByN_H_R",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_H_R = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByN_H_R",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			},
			WikiPageModelImpl.NODEID_COLUMN_BITMASK |
			WikiPageModelImpl.HEAD_COLUMN_BITMASK |
			WikiPageModelImpl.REDIRECTTITLE_COLUMN_BITMASK |
			WikiPageModelImpl.TITLE_COLUMN_BITMASK |
			WikiPageModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_N_H_R = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByN_H_R",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @return the matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_H_R(long nodeId, boolean head,
		String redirectTitle) {
		return findByN_H_R(nodeId, head, redirectTitle, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_H_R(long nodeId, boolean head,
		String redirectTitle, int start, int end) {
		return findByN_H_R(nodeId, head, redirectTitle, start, end, null);
	}

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_H_R(long nodeId, boolean head,
		String redirectTitle, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return findByN_H_R(nodeId, head, redirectTitle, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_H_R(long nodeId, boolean head,
		String redirectTitle, int start, int end,
		OrderByComparator<WikiPage> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_H_R;
			finderArgs = new Object[] { nodeId, head, redirectTitle };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_N_H_R;
			finderArgs = new Object[] {
					nodeId, head, redirectTitle,
					
					start, end, orderByComparator
				};
		}

		List<WikiPage> list = null;

		if (retrieveFromCache) {
			list = (List<WikiPage>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WikiPage wikiPage : list) {
					if ((nodeId != wikiPage.getNodeId()) ||
							(head != wikiPage.getHead()) ||
							!Validator.equals(redirectTitle,
								wikiPage.getRedirectTitle())) {
						list = null;

						break;
					}
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

			query.append(_SQL_SELECT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_N_H_R_NODEID_2);

			query.append(_FINDER_COLUMN_N_H_R_HEAD_2);

			boolean bindRedirectTitle = false;

			if (redirectTitle == null) {
				query.append(_FINDER_COLUMN_N_H_R_REDIRECTTITLE_1);
			}
			else if (redirectTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_N_H_R_REDIRECTTITLE_3);
			}
			else {
				bindRedirectTitle = true;

				query.append(_FINDER_COLUMN_N_H_R_REDIRECTTITLE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(WikiPageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				qPos.add(head);

				if (bindRedirectTitle) {
					qPos.add(StringUtil.toLowerCase(redirectTitle));
				}

				if (!pagination) {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and redirectTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByN_H_R_First(long nodeId, boolean head,
		String redirectTitle, OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByN_H_R_First(nodeId, head, redirectTitle,
				orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("nodeId=");
		msg.append(nodeId);

		msg.append(", head=");
		msg.append(head);

		msg.append(", redirectTitle=");
		msg.append(redirectTitle);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and redirectTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByN_H_R_First(long nodeId, boolean head,
		String redirectTitle, OrderByComparator<WikiPage> orderByComparator) {
		List<WikiPage> list = findByN_H_R(nodeId, head, redirectTitle, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and redirectTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByN_H_R_Last(long nodeId, boolean head,
		String redirectTitle, OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByN_H_R_Last(nodeId, head, redirectTitle,
				orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("nodeId=");
		msg.append(nodeId);

		msg.append(", head=");
		msg.append(head);

		msg.append(", redirectTitle=");
		msg.append(redirectTitle);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and redirectTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByN_H_R_Last(long nodeId, boolean head,
		String redirectTitle, OrderByComparator<WikiPage> orderByComparator) {
		int count = countByN_H_R(nodeId, head, redirectTitle);

		if (count == 0) {
			return null;
		}

		List<WikiPage> list = findByN_H_R(nodeId, head, redirectTitle,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and head = &#63; and redirectTitle = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	 */
	@Override
	public WikiPage[] findByN_H_R_PrevAndNext(long pageId, long nodeId,
		boolean head, String redirectTitle,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = getByN_H_R_PrevAndNext(session, wikiPage, nodeId, head,
					redirectTitle, orderByComparator, true);

			array[1] = wikiPage;

			array[2] = getByN_H_R_PrevAndNext(session, wikiPage, nodeId, head,
					redirectTitle, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected WikiPage getByN_H_R_PrevAndNext(Session session,
		WikiPage wikiPage, long nodeId, boolean head, String redirectTitle,
		OrderByComparator<WikiPage> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGE_WHERE);

		query.append(_FINDER_COLUMN_N_H_R_NODEID_2);

		query.append(_FINDER_COLUMN_N_H_R_HEAD_2);

		boolean bindRedirectTitle = false;

		if (redirectTitle == null) {
			query.append(_FINDER_COLUMN_N_H_R_REDIRECTTITLE_1);
		}
		else if (redirectTitle.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_N_H_R_REDIRECTTITLE_3);
		}
		else {
			bindRedirectTitle = true;

			query.append(_FINDER_COLUMN_N_H_R_REDIRECTTITLE_2);
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
			query.append(WikiPageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(nodeId);

		qPos.add(head);

		if (bindRedirectTitle) {
			qPos.add(StringUtil.toLowerCase(redirectTitle));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63; from the database.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 */
	@Override
	public void removeByN_H_R(long nodeId, boolean head, String redirectTitle) {
		for (WikiPage wikiPage : findByN_H_R(nodeId, head, redirectTitle,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(wikiPage);
		}
	}

	/**
	 * Returns the number of wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @return the number of matching wiki pages
	 */
	@Override
	public int countByN_H_R(long nodeId, boolean head, String redirectTitle) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_N_H_R;

		Object[] finderArgs = new Object[] { nodeId, head, redirectTitle };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_N_H_R_NODEID_2);

			query.append(_FINDER_COLUMN_N_H_R_HEAD_2);

			boolean bindRedirectTitle = false;

			if (redirectTitle == null) {
				query.append(_FINDER_COLUMN_N_H_R_REDIRECTTITLE_1);
			}
			else if (redirectTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_N_H_R_REDIRECTTITLE_3);
			}
			else {
				bindRedirectTitle = true;

				query.append(_FINDER_COLUMN_N_H_R_REDIRECTTITLE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				qPos.add(head);

				if (bindRedirectTitle) {
					qPos.add(StringUtil.toLowerCase(redirectTitle));
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

	private static final String _FINDER_COLUMN_N_H_R_NODEID_2 = "wikiPage.nodeId = ? AND ";
	private static final String _FINDER_COLUMN_N_H_R_HEAD_2 = "wikiPage.head = ? AND ";
	private static final String _FINDER_COLUMN_N_H_R_REDIRECTTITLE_1 = "wikiPage.redirectTitle IS NULL";
	private static final String _FINDER_COLUMN_N_H_R_REDIRECTTITLE_2 = "lower(wikiPage.redirectTitle) = ?";
	private static final String _FINDER_COLUMN_N_H_R_REDIRECTTITLE_3 = "(wikiPage.redirectTitle IS NULL OR wikiPage.redirectTitle = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_N_H_S = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByN_H_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_H_S = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByN_H_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName()
			},
			WikiPageModelImpl.NODEID_COLUMN_BITMASK |
			WikiPageModelImpl.HEAD_COLUMN_BITMASK |
			WikiPageModelImpl.STATUS_COLUMN_BITMASK |
			WikiPageModelImpl.TITLE_COLUMN_BITMASK |
			WikiPageModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_N_H_S = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByN_H_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the wiki pages where nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @return the matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_H_S(long nodeId, boolean head, int status) {
		return findByN_H_S(nodeId, head, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the wiki pages where nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_H_S(long nodeId, boolean head, int status,
		int start, int end) {
		return findByN_H_S(nodeId, head, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_H_S(long nodeId, boolean head, int status,
		int start, int end, OrderByComparator<WikiPage> orderByComparator) {
		return findByN_H_S(nodeId, head, status, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_H_S(long nodeId, boolean head, int status,
		int start, int end, OrderByComparator<WikiPage> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_H_S;
			finderArgs = new Object[] { nodeId, head, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_N_H_S;
			finderArgs = new Object[] {
					nodeId, head, status,
					
					start, end, orderByComparator
				};
		}

		List<WikiPage> list = null;

		if (retrieveFromCache) {
			list = (List<WikiPage>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WikiPage wikiPage : list) {
					if ((nodeId != wikiPage.getNodeId()) ||
							(head != wikiPage.getHead()) ||
							(status != wikiPage.getStatus())) {
						list = null;

						break;
					}
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

			query.append(_SQL_SELECT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_N_H_S_NODEID_2);

			query.append(_FINDER_COLUMN_N_H_S_HEAD_2);

			query.append(_FINDER_COLUMN_N_H_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(WikiPageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				qPos.add(head);

				qPos.add(status);

				if (!pagination) {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByN_H_S_First(long nodeId, boolean head, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByN_H_S_First(nodeId, head, status,
				orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("nodeId=");
		msg.append(nodeId);

		msg.append(", head=");
		msg.append(head);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByN_H_S_First(long nodeId, boolean head, int status,
		OrderByComparator<WikiPage> orderByComparator) {
		List<WikiPage> list = findByN_H_S(nodeId, head, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByN_H_S_Last(long nodeId, boolean head, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByN_H_S_Last(nodeId, head, status,
				orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("nodeId=");
		msg.append(nodeId);

		msg.append(", head=");
		msg.append(head);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByN_H_S_Last(long nodeId, boolean head, int status,
		OrderByComparator<WikiPage> orderByComparator) {
		int count = countByN_H_S(nodeId, head, status);

		if (count == 0) {
			return null;
		}

		List<WikiPage> list = findByN_H_S(nodeId, head, status, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	 */
	@Override
	public WikiPage[] findByN_H_S_PrevAndNext(long pageId, long nodeId,
		boolean head, int status, OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = getByN_H_S_PrevAndNext(session, wikiPage, nodeId, head,
					status, orderByComparator, true);

			array[1] = wikiPage;

			array[2] = getByN_H_S_PrevAndNext(session, wikiPage, nodeId, head,
					status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected WikiPage getByN_H_S_PrevAndNext(Session session,
		WikiPage wikiPage, long nodeId, boolean head, int status,
		OrderByComparator<WikiPage> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGE_WHERE);

		query.append(_FINDER_COLUMN_N_H_S_NODEID_2);

		query.append(_FINDER_COLUMN_N_H_S_HEAD_2);

		query.append(_FINDER_COLUMN_N_H_S_STATUS_2);

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
			query.append(WikiPageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(nodeId);

		qPos.add(head);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the wiki pages where nodeId = &#63; and head = &#63; and status = &#63; from the database.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 */
	@Override
	public void removeByN_H_S(long nodeId, boolean head, int status) {
		for (WikiPage wikiPage : findByN_H_S(nodeId, head, status,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(wikiPage);
		}
	}

	/**
	 * Returns the number of wiki pages where nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @return the number of matching wiki pages
	 */
	@Override
	public int countByN_H_S(long nodeId, boolean head, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_N_H_S;

		Object[] finderArgs = new Object[] { nodeId, head, status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_N_H_S_NODEID_2);

			query.append(_FINDER_COLUMN_N_H_S_HEAD_2);

			query.append(_FINDER_COLUMN_N_H_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				qPos.add(head);

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_N_H_S_NODEID_2 = "wikiPage.nodeId = ? AND ";
	private static final String _FINDER_COLUMN_N_H_S_HEAD_2 = "wikiPage.head = ? AND ";
	private static final String _FINDER_COLUMN_N_H_S_STATUS_2 = "wikiPage.status = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_N_H_NOTS = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByN_H_NotS",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_N_H_NOTS =
		new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByN_H_NotS",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the wiki pages where nodeId = &#63; and head = &#63; and status &ne; &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @return the matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_H_NotS(long nodeId, boolean head, int status) {
		return findByN_H_NotS(nodeId, head, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the wiki pages where nodeId = &#63; and head = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_H_NotS(long nodeId, boolean head, int status,
		int start, int end) {
		return findByN_H_NotS(nodeId, head, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_H_NotS(long nodeId, boolean head, int status,
		int start, int end, OrderByComparator<WikiPage> orderByComparator) {
		return findByN_H_NotS(nodeId, head, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_H_NotS(long nodeId, boolean head, int status,
		int start, int end, OrderByComparator<WikiPage> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_N_H_NOTS;
		finderArgs = new Object[] {
				nodeId, head, status,
				
				start, end, orderByComparator
			};

		List<WikiPage> list = null;

		if (retrieveFromCache) {
			list = (List<WikiPage>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WikiPage wikiPage : list) {
					if ((nodeId != wikiPage.getNodeId()) ||
							(head != wikiPage.getHead()) ||
							(status == wikiPage.getStatus())) {
						list = null;

						break;
					}
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

			query.append(_SQL_SELECT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_N_H_NOTS_NODEID_2);

			query.append(_FINDER_COLUMN_N_H_NOTS_HEAD_2);

			query.append(_FINDER_COLUMN_N_H_NOTS_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(WikiPageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				qPos.add(head);

				qPos.add(status);

				if (!pagination) {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and status &ne; &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByN_H_NotS_First(long nodeId, boolean head, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByN_H_NotS_First(nodeId, head, status,
				orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("nodeId=");
		msg.append(nodeId);

		msg.append(", head=");
		msg.append(head);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and status &ne; &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByN_H_NotS_First(long nodeId, boolean head,
		int status, OrderByComparator<WikiPage> orderByComparator) {
		List<WikiPage> list = findByN_H_NotS(nodeId, head, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and status &ne; &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByN_H_NotS_Last(long nodeId, boolean head, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByN_H_NotS_Last(nodeId, head, status,
				orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("nodeId=");
		msg.append(nodeId);

		msg.append(", head=");
		msg.append(head);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and status &ne; &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByN_H_NotS_Last(long nodeId, boolean head, int status,
		OrderByComparator<WikiPage> orderByComparator) {
		int count = countByN_H_NotS(nodeId, head, status);

		if (count == 0) {
			return null;
		}

		List<WikiPage> list = findByN_H_NotS(nodeId, head, status, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and head = &#63; and status &ne; &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	 */
	@Override
	public WikiPage[] findByN_H_NotS_PrevAndNext(long pageId, long nodeId,
		boolean head, int status, OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = getByN_H_NotS_PrevAndNext(session, wikiPage, nodeId,
					head, status, orderByComparator, true);

			array[1] = wikiPage;

			array[2] = getByN_H_NotS_PrevAndNext(session, wikiPage, nodeId,
					head, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected WikiPage getByN_H_NotS_PrevAndNext(Session session,
		WikiPage wikiPage, long nodeId, boolean head, int status,
		OrderByComparator<WikiPage> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGE_WHERE);

		query.append(_FINDER_COLUMN_N_H_NOTS_NODEID_2);

		query.append(_FINDER_COLUMN_N_H_NOTS_HEAD_2);

		query.append(_FINDER_COLUMN_N_H_NOTS_STATUS_2);

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
			query.append(WikiPageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(nodeId);

		qPos.add(head);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the wiki pages where nodeId = &#63; and head = &#63; and status &ne; &#63; from the database.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 */
	@Override
	public void removeByN_H_NotS(long nodeId, boolean head, int status) {
		for (WikiPage wikiPage : findByN_H_NotS(nodeId, head, status,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(wikiPage);
		}
	}

	/**
	 * Returns the number of wiki pages where nodeId = &#63; and head = &#63; and status &ne; &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @return the number of matching wiki pages
	 */
	@Override
	public int countByN_H_NotS(long nodeId, boolean head, int status) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_N_H_NOTS;

		Object[] finderArgs = new Object[] { nodeId, head, status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_N_H_NOTS_NODEID_2);

			query.append(_FINDER_COLUMN_N_H_NOTS_HEAD_2);

			query.append(_FINDER_COLUMN_N_H_NOTS_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				qPos.add(head);

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_N_H_NOTS_NODEID_2 = "wikiPage.nodeId = ? AND ";
	private static final String _FINDER_COLUMN_N_H_NOTS_HEAD_2 = "wikiPage.head = ? AND ";
	private static final String _FINDER_COLUMN_N_H_NOTS_STATUS_2 = "wikiPage.status != ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_N_S = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_U_N_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_N_S =
		new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_U_N_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			WikiPageModelImpl.GROUPID_COLUMN_BITMASK |
			WikiPageModelImpl.USERID_COLUMN_BITMASK |
			WikiPageModelImpl.NODEID_COLUMN_BITMASK |
			WikiPageModelImpl.STATUS_COLUMN_BITMASK |
			WikiPageModelImpl.TITLE_COLUMN_BITMASK |
			WikiPageModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U_N_S = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U_N_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the wiki pages where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @return the matching wiki pages
	 */
	@Override
	public List<WikiPage> findByG_U_N_S(long groupId, long userId, long nodeId,
		int status) {
		return findByG_U_N_S(groupId, userId, nodeId, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the wiki pages where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByG_U_N_S(long groupId, long userId, long nodeId,
		int status, int start, int end) {
		return findByG_U_N_S(groupId, userId, nodeId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the wiki pages where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByG_U_N_S(long groupId, long userId, long nodeId,
		int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return findByG_U_N_S(groupId, userId, nodeId, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the wiki pages where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByG_U_N_S(long groupId, long userId, long nodeId,
		int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_N_S;
			finderArgs = new Object[] { groupId, userId, nodeId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_N_S;
			finderArgs = new Object[] {
					groupId, userId, nodeId, status,
					
					start, end, orderByComparator
				};
		}

		List<WikiPage> list = null;

		if (retrieveFromCache) {
			list = (List<WikiPage>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WikiPage wikiPage : list) {
					if ((groupId != wikiPage.getGroupId()) ||
							(userId != wikiPage.getUserId()) ||
							(nodeId != wikiPage.getNodeId()) ||
							(status != wikiPage.getStatus())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(6 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_G_U_N_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_N_S_USERID_2);

			query.append(_FINDER_COLUMN_G_U_N_S_NODEID_2);

			query.append(_FINDER_COLUMN_G_U_N_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(WikiPageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(nodeId);

				qPos.add(status);

				if (!pagination) {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first wiki page in the ordered set where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByG_U_N_S_First(long groupId, long userId, long nodeId,
		int status, OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByG_U_N_S_First(groupId, userId, nodeId,
				status, orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", nodeId=");
		msg.append(nodeId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the first wiki page in the ordered set where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByG_U_N_S_First(long groupId, long userId,
		long nodeId, int status, OrderByComparator<WikiPage> orderByComparator) {
		List<WikiPage> list = findByG_U_N_S(groupId, userId, nodeId, status, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last wiki page in the ordered set where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByG_U_N_S_Last(long groupId, long userId, long nodeId,
		int status, OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByG_U_N_S_Last(groupId, userId, nodeId,
				status, orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", nodeId=");
		msg.append(nodeId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the last wiki page in the ordered set where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByG_U_N_S_Last(long groupId, long userId, long nodeId,
		int status, OrderByComparator<WikiPage> orderByComparator) {
		int count = countByG_U_N_S(groupId, userId, nodeId, status);

		if (count == 0) {
			return null;
		}

		List<WikiPage> list = findByG_U_N_S(groupId, userId, nodeId, status,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	 */
	@Override
	public WikiPage[] findByG_U_N_S_PrevAndNext(long pageId, long groupId,
		long userId, long nodeId, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = getByG_U_N_S_PrevAndNext(session, wikiPage, groupId,
					userId, nodeId, status, orderByComparator, true);

			array[1] = wikiPage;

			array[2] = getByG_U_N_S_PrevAndNext(session, wikiPage, groupId,
					userId, nodeId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected WikiPage getByG_U_N_S_PrevAndNext(Session session,
		WikiPage wikiPage, long groupId, long userId, long nodeId, int status,
		OrderByComparator<WikiPage> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGE_WHERE);

		query.append(_FINDER_COLUMN_G_U_N_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_N_S_USERID_2);

		query.append(_FINDER_COLUMN_G_U_N_S_NODEID_2);

		query.append(_FINDER_COLUMN_G_U_N_S_STATUS_2);

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
			query.append(WikiPageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		qPos.add(nodeId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the wiki pages that the user has permission to view where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @return the matching wiki pages that the user has permission to view
	 */
	@Override
	public List<WikiPage> filterFindByG_U_N_S(long groupId, long userId,
		long nodeId, int status) {
		return filterFindByG_U_N_S(groupId, userId, nodeId, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the wiki pages that the user has permission to view where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages that the user has permission to view
	 */
	@Override
	public List<WikiPage> filterFindByG_U_N_S(long groupId, long userId,
		long nodeId, int status, int start, int end) {
		return filterFindByG_U_N_S(groupId, userId, nodeId, status, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the wiki pages that the user has permissions to view where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages that the user has permission to view
	 */
	@Override
	public List<WikiPage> filterFindByG_U_N_S(long groupId, long userId,
		long nodeId, int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_N_S(groupId, userId, nodeId, status, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_WIKIPAGE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_WIKIPAGE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_N_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_N_S_USERID_2);

		query.append(_FINDER_COLUMN_G_U_N_S_NODEID_2);

		query.append(_FINDER_COLUMN_G_U_N_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_WIKIPAGE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(WikiPageModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(WikiPageModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				WikiPage.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, WikiPageImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, WikiPageImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			qPos.add(nodeId);

			qPos.add(status);

			return (List<WikiPage>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set of wiki pages that the user has permission to view where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	 */
	@Override
	public WikiPage[] filterFindByG_U_N_S_PrevAndNext(long pageId,
		long groupId, long userId, long nodeId, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_N_S_PrevAndNext(pageId, groupId, userId, nodeId,
				status, orderByComparator);
		}

		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = filterGetByG_U_N_S_PrevAndNext(session, wikiPage,
					groupId, userId, nodeId, status, orderByComparator, true);

			array[1] = wikiPage;

			array[2] = filterGetByG_U_N_S_PrevAndNext(session, wikiPage,
					groupId, userId, nodeId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected WikiPage filterGetByG_U_N_S_PrevAndNext(Session session,
		WikiPage wikiPage, long groupId, long userId, long nodeId, int status,
		OrderByComparator<WikiPage> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_WIKIPAGE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_WIKIPAGE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_N_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_N_S_USERID_2);

		query.append(_FINDER_COLUMN_G_U_N_S_NODEID_2);

		query.append(_FINDER_COLUMN_G_U_N_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_WIKIPAGE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(WikiPageModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(WikiPageModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				WikiPage.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, WikiPageImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, WikiPageImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		qPos.add(nodeId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the wiki pages where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 */
	@Override
	public void removeByG_U_N_S(long groupId, long userId, long nodeId,
		int status) {
		for (WikiPage wikiPage : findByG_U_N_S(groupId, userId, nodeId, status,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(wikiPage);
		}
	}

	/**
	 * Returns the number of wiki pages where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @return the number of matching wiki pages
	 */
	@Override
	public int countByG_U_N_S(long groupId, long userId, long nodeId, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_U_N_S;

		Object[] finderArgs = new Object[] { groupId, userId, nodeId, status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_G_U_N_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_N_S_USERID_2);

			query.append(_FINDER_COLUMN_G_U_N_S_NODEID_2);

			query.append(_FINDER_COLUMN_G_U_N_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(nodeId);

				qPos.add(status);

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
	 * Returns the number of wiki pages that the user has permission to view where groupId = &#63; and userId = &#63; and nodeId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param nodeId the node ID
	 * @param status the status
	 * @return the number of matching wiki pages that the user has permission to view
	 */
	@Override
	public int filterCountByG_U_N_S(long groupId, long userId, long nodeId,
		int status) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_U_N_S(groupId, userId, nodeId, status);
		}

		StringBundler query = new StringBundler(5);

		query.append(_FILTER_SQL_COUNT_WIKIPAGE_WHERE);

		query.append(_FINDER_COLUMN_G_U_N_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_N_S_USERID_2);

		query.append(_FINDER_COLUMN_G_U_N_S_NODEID_2);

		query.append(_FINDER_COLUMN_G_U_N_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				WikiPage.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			qPos.add(nodeId);

			qPos.add(status);

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

	private static final String _FINDER_COLUMN_G_U_N_S_GROUPID_2 = "wikiPage.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_N_S_USERID_2 = "wikiPage.userId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_N_S_NODEID_2 = "wikiPage.nodeId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_N_S_STATUS_2 = "wikiPage.status = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_N_T_H = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_N_T_H",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_N_T_H =
		new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_N_T_H",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Boolean.class.getName()
			},
			WikiPageModelImpl.GROUPID_COLUMN_BITMASK |
			WikiPageModelImpl.NODEID_COLUMN_BITMASK |
			WikiPageModelImpl.TITLE_COLUMN_BITMASK |
			WikiPageModelImpl.HEAD_COLUMN_BITMASK |
			WikiPageModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_N_T_H = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_N_T_H",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Boolean.class.getName()
			});

	/**
	 * Returns all the wiki pages where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @return the matching wiki pages
	 */
	@Override
	public List<WikiPage> findByG_N_T_H(long groupId, long nodeId,
		String title, boolean head) {
		return findByG_N_T_H(groupId, nodeId, title, head, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the wiki pages where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByG_N_T_H(long groupId, long nodeId,
		String title, boolean head, int start, int end) {
		return findByG_N_T_H(groupId, nodeId, title, head, start, end, null);
	}

	/**
	 * Returns an ordered range of all the wiki pages where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByG_N_T_H(long groupId, long nodeId,
		String title, boolean head, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return findByG_N_T_H(groupId, nodeId, title, head, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the wiki pages where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByG_N_T_H(long groupId, long nodeId,
		String title, boolean head, int start, int end,
		OrderByComparator<WikiPage> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_N_T_H;
			finderArgs = new Object[] { groupId, nodeId, title, head };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_N_T_H;
			finderArgs = new Object[] {
					groupId, nodeId, title, head,
					
					start, end, orderByComparator
				};
		}

		List<WikiPage> list = null;

		if (retrieveFromCache) {
			list = (List<WikiPage>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WikiPage wikiPage : list) {
					if ((groupId != wikiPage.getGroupId()) ||
							(nodeId != wikiPage.getNodeId()) ||
							!Validator.equals(title, wikiPage.getTitle()) ||
							(head != wikiPage.getHead())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(6 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_G_N_T_H_GROUPID_2);

			query.append(_FINDER_COLUMN_G_N_T_H_NODEID_2);

			boolean bindTitle = false;

			if (title == null) {
				query.append(_FINDER_COLUMN_G_N_T_H_TITLE_1);
			}
			else if (title.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_N_T_H_TITLE_3);
			}
			else {
				bindTitle = true;

				query.append(_FINDER_COLUMN_G_N_T_H_TITLE_2);
			}

			query.append(_FINDER_COLUMN_G_N_T_H_HEAD_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(WikiPageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(nodeId);

				if (bindTitle) {
					qPos.add(StringUtil.toLowerCase(title));
				}

				qPos.add(head);

				if (!pagination) {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByG_N_T_H_First(long groupId, long nodeId,
		String title, boolean head,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByG_N_T_H_First(groupId, nodeId, title, head,
				orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", nodeId=");
		msg.append(nodeId);

		msg.append(", title=");
		msg.append(title);

		msg.append(", head=");
		msg.append(head);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the first wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByG_N_T_H_First(long groupId, long nodeId,
		String title, boolean head,
		OrderByComparator<WikiPage> orderByComparator) {
		List<WikiPage> list = findByG_N_T_H(groupId, nodeId, title, head, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByG_N_T_H_Last(long groupId, long nodeId, String title,
		boolean head, OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByG_N_T_H_Last(groupId, nodeId, title, head,
				orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", nodeId=");
		msg.append(nodeId);

		msg.append(", title=");
		msg.append(title);

		msg.append(", head=");
		msg.append(head);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the last wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByG_N_T_H_Last(long groupId, long nodeId,
		String title, boolean head,
		OrderByComparator<WikiPage> orderByComparator) {
		int count = countByG_N_T_H(groupId, nodeId, title, head);

		if (count == 0) {
			return null;
		}

		List<WikiPage> list = findByG_N_T_H(groupId, nodeId, title, head,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	 */
	@Override
	public WikiPage[] findByG_N_T_H_PrevAndNext(long pageId, long groupId,
		long nodeId, String title, boolean head,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = getByG_N_T_H_PrevAndNext(session, wikiPage, groupId,
					nodeId, title, head, orderByComparator, true);

			array[1] = wikiPage;

			array[2] = getByG_N_T_H_PrevAndNext(session, wikiPage, groupId,
					nodeId, title, head, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected WikiPage getByG_N_T_H_PrevAndNext(Session session,
		WikiPage wikiPage, long groupId, long nodeId, String title,
		boolean head, OrderByComparator<WikiPage> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGE_WHERE);

		query.append(_FINDER_COLUMN_G_N_T_H_GROUPID_2);

		query.append(_FINDER_COLUMN_G_N_T_H_NODEID_2);

		boolean bindTitle = false;

		if (title == null) {
			query.append(_FINDER_COLUMN_G_N_T_H_TITLE_1);
		}
		else if (title.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_N_T_H_TITLE_3);
		}
		else {
			bindTitle = true;

			query.append(_FINDER_COLUMN_G_N_T_H_TITLE_2);
		}

		query.append(_FINDER_COLUMN_G_N_T_H_HEAD_2);

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
			query.append(WikiPageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(nodeId);

		if (bindTitle) {
			qPos.add(StringUtil.toLowerCase(title));
		}

		qPos.add(head);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @return the matching wiki pages that the user has permission to view
	 */
	@Override
	public List<WikiPage> filterFindByG_N_T_H(long groupId, long nodeId,
		String title, boolean head) {
		return filterFindByG_N_T_H(groupId, nodeId, title, head,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages that the user has permission to view
	 */
	@Override
	public List<WikiPage> filterFindByG_N_T_H(long groupId, long nodeId,
		String title, boolean head, int start, int end) {
		return filterFindByG_N_T_H(groupId, nodeId, title, head, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the wiki pages that the user has permissions to view where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages that the user has permission to view
	 */
	@Override
	public List<WikiPage> filterFindByG_N_T_H(long groupId, long nodeId,
		String title, boolean head, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_N_T_H(groupId, nodeId, title, head, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_WIKIPAGE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_WIKIPAGE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_N_T_H_GROUPID_2);

		query.append(_FINDER_COLUMN_G_N_T_H_NODEID_2);

		boolean bindTitle = false;

		if (title == null) {
			query.append(_FINDER_COLUMN_G_N_T_H_TITLE_1);
		}
		else if (title.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_N_T_H_TITLE_3);
		}
		else {
			bindTitle = true;

			query.append(_FINDER_COLUMN_G_N_T_H_TITLE_2);
		}

		query.append(_FINDER_COLUMN_G_N_T_H_HEAD_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_WIKIPAGE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(WikiPageModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(WikiPageModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				WikiPage.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, WikiPageImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, WikiPageImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(nodeId);

			if (bindTitle) {
				qPos.add(StringUtil.toLowerCase(title));
			}

			qPos.add(head);

			return (List<WikiPage>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set of wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	 */
	@Override
	public WikiPage[] filterFindByG_N_T_H_PrevAndNext(long pageId,
		long groupId, long nodeId, String title, boolean head,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_N_T_H_PrevAndNext(pageId, groupId, nodeId, title,
				head, orderByComparator);
		}

		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = filterGetByG_N_T_H_PrevAndNext(session, wikiPage,
					groupId, nodeId, title, head, orderByComparator, true);

			array[1] = wikiPage;

			array[2] = filterGetByG_N_T_H_PrevAndNext(session, wikiPage,
					groupId, nodeId, title, head, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected WikiPage filterGetByG_N_T_H_PrevAndNext(Session session,
		WikiPage wikiPage, long groupId, long nodeId, String title,
		boolean head, OrderByComparator<WikiPage> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_WIKIPAGE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_WIKIPAGE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_N_T_H_GROUPID_2);

		query.append(_FINDER_COLUMN_G_N_T_H_NODEID_2);

		boolean bindTitle = false;

		if (title == null) {
			query.append(_FINDER_COLUMN_G_N_T_H_TITLE_1);
		}
		else if (title.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_N_T_H_TITLE_3);
		}
		else {
			bindTitle = true;

			query.append(_FINDER_COLUMN_G_N_T_H_TITLE_2);
		}

		query.append(_FINDER_COLUMN_G_N_T_H_HEAD_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_WIKIPAGE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(WikiPageModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(WikiPageModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				WikiPage.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, WikiPageImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, WikiPageImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(nodeId);

		if (bindTitle) {
			qPos.add(StringUtil.toLowerCase(title));
		}

		qPos.add(head);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the wiki pages where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 */
	@Override
	public void removeByG_N_T_H(long groupId, long nodeId, String title,
		boolean head) {
		for (WikiPage wikiPage : findByG_N_T_H(groupId, nodeId, title, head,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(wikiPage);
		}
	}

	/**
	 * Returns the number of wiki pages where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @return the number of matching wiki pages
	 */
	@Override
	public int countByG_N_T_H(long groupId, long nodeId, String title,
		boolean head) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_N_T_H;

		Object[] finderArgs = new Object[] { groupId, nodeId, title, head };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_G_N_T_H_GROUPID_2);

			query.append(_FINDER_COLUMN_G_N_T_H_NODEID_2);

			boolean bindTitle = false;

			if (title == null) {
				query.append(_FINDER_COLUMN_G_N_T_H_TITLE_1);
			}
			else if (title.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_N_T_H_TITLE_3);
			}
			else {
				bindTitle = true;

				query.append(_FINDER_COLUMN_G_N_T_H_TITLE_2);
			}

			query.append(_FINDER_COLUMN_G_N_T_H_HEAD_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(nodeId);

				if (bindTitle) {
					qPos.add(StringUtil.toLowerCase(title));
				}

				qPos.add(head);

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
	 * Returns the number of wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and title = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param title the title
	 * @param head the head
	 * @return the number of matching wiki pages that the user has permission to view
	 */
	@Override
	public int filterCountByG_N_T_H(long groupId, long nodeId, String title,
		boolean head) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_N_T_H(groupId, nodeId, title, head);
		}

		StringBundler query = new StringBundler(5);

		query.append(_FILTER_SQL_COUNT_WIKIPAGE_WHERE);

		query.append(_FINDER_COLUMN_G_N_T_H_GROUPID_2);

		query.append(_FINDER_COLUMN_G_N_T_H_NODEID_2);

		boolean bindTitle = false;

		if (title == null) {
			query.append(_FINDER_COLUMN_G_N_T_H_TITLE_1);
		}
		else if (title.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_N_T_H_TITLE_3);
		}
		else {
			bindTitle = true;

			query.append(_FINDER_COLUMN_G_N_T_H_TITLE_2);
		}

		query.append(_FINDER_COLUMN_G_N_T_H_HEAD_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				WikiPage.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(nodeId);

			if (bindTitle) {
				qPos.add(StringUtil.toLowerCase(title));
			}

			qPos.add(head);

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

	private static final String _FINDER_COLUMN_G_N_T_H_GROUPID_2 = "wikiPage.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_N_T_H_NODEID_2 = "wikiPage.nodeId = ? AND ";
	private static final String _FINDER_COLUMN_G_N_T_H_TITLE_1 = "wikiPage.title IS NULL AND ";
	private static final String _FINDER_COLUMN_G_N_T_H_TITLE_2 = "lower(wikiPage.title) = ? AND ";
	private static final String _FINDER_COLUMN_G_N_T_H_TITLE_3 = "(wikiPage.title IS NULL OR wikiPage.title = '') AND ";
	private static final String _FINDER_COLUMN_G_N_T_H_HEAD_2 = "wikiPage.head = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_N_H_S = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_N_H_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_N_H_S =
		new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_N_H_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(), Integer.class.getName()
			},
			WikiPageModelImpl.GROUPID_COLUMN_BITMASK |
			WikiPageModelImpl.NODEID_COLUMN_BITMASK |
			WikiPageModelImpl.HEAD_COLUMN_BITMASK |
			WikiPageModelImpl.STATUS_COLUMN_BITMASK |
			WikiPageModelImpl.TITLE_COLUMN_BITMASK |
			WikiPageModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_N_H_S = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_N_H_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(), Integer.class.getName()
			});

	/**
	 * Returns all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @return the matching wiki pages
	 */
	@Override
	public List<WikiPage> findByG_N_H_S(long groupId, long nodeId,
		boolean head, int status) {
		return findByG_N_H_S(groupId, nodeId, head, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByG_N_H_S(long groupId, long nodeId,
		boolean head, int status, int start, int end) {
		return findByG_N_H_S(groupId, nodeId, head, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByG_N_H_S(long groupId, long nodeId,
		boolean head, int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return findByG_N_H_S(groupId, nodeId, head, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByG_N_H_S(long groupId, long nodeId,
		boolean head, int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_N_H_S;
			finderArgs = new Object[] { groupId, nodeId, head, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_N_H_S;
			finderArgs = new Object[] {
					groupId, nodeId, head, status,
					
					start, end, orderByComparator
				};
		}

		List<WikiPage> list = null;

		if (retrieveFromCache) {
			list = (List<WikiPage>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WikiPage wikiPage : list) {
					if ((groupId != wikiPage.getGroupId()) ||
							(nodeId != wikiPage.getNodeId()) ||
							(head != wikiPage.getHead()) ||
							(status != wikiPage.getStatus())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(6 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_G_N_H_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_N_H_S_NODEID_2);

			query.append(_FINDER_COLUMN_G_N_H_S_HEAD_2);

			query.append(_FINDER_COLUMN_G_N_H_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(WikiPageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(nodeId);

				qPos.add(head);

				qPos.add(status);

				if (!pagination) {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByG_N_H_S_First(long groupId, long nodeId,
		boolean head, int status, OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByG_N_H_S_First(groupId, nodeId, head, status,
				orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", nodeId=");
		msg.append(nodeId);

		msg.append(", head=");
		msg.append(head);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the first wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByG_N_H_S_First(long groupId, long nodeId,
		boolean head, int status, OrderByComparator<WikiPage> orderByComparator) {
		List<WikiPage> list = findByG_N_H_S(groupId, nodeId, head, status, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByG_N_H_S_Last(long groupId, long nodeId, boolean head,
		int status, OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByG_N_H_S_Last(groupId, nodeId, head, status,
				orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", nodeId=");
		msg.append(nodeId);

		msg.append(", head=");
		msg.append(head);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the last wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByG_N_H_S_Last(long groupId, long nodeId,
		boolean head, int status, OrderByComparator<WikiPage> orderByComparator) {
		int count = countByG_N_H_S(groupId, nodeId, head, status);

		if (count == 0) {
			return null;
		}

		List<WikiPage> list = findByG_N_H_S(groupId, nodeId, head, status,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	 */
	@Override
	public WikiPage[] findByG_N_H_S_PrevAndNext(long pageId, long groupId,
		long nodeId, boolean head, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = getByG_N_H_S_PrevAndNext(session, wikiPage, groupId,
					nodeId, head, status, orderByComparator, true);

			array[1] = wikiPage;

			array[2] = getByG_N_H_S_PrevAndNext(session, wikiPage, groupId,
					nodeId, head, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected WikiPage getByG_N_H_S_PrevAndNext(Session session,
		WikiPage wikiPage, long groupId, long nodeId, boolean head, int status,
		OrderByComparator<WikiPage> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGE_WHERE);

		query.append(_FINDER_COLUMN_G_N_H_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_N_H_S_NODEID_2);

		query.append(_FINDER_COLUMN_G_N_H_S_HEAD_2);

		query.append(_FINDER_COLUMN_G_N_H_S_STATUS_2);

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
			query.append(WikiPageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(nodeId);

		qPos.add(head);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @return the matching wiki pages that the user has permission to view
	 */
	@Override
	public List<WikiPage> filterFindByG_N_H_S(long groupId, long nodeId,
		boolean head, int status) {
		return filterFindByG_N_H_S(groupId, nodeId, head, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages that the user has permission to view
	 */
	@Override
	public List<WikiPage> filterFindByG_N_H_S(long groupId, long nodeId,
		boolean head, int status, int start, int end) {
		return filterFindByG_N_H_S(groupId, nodeId, head, status, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the wiki pages that the user has permissions to view where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages that the user has permission to view
	 */
	@Override
	public List<WikiPage> filterFindByG_N_H_S(long groupId, long nodeId,
		boolean head, int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_N_H_S(groupId, nodeId, head, status, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_WIKIPAGE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_WIKIPAGE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_N_H_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_N_H_S_NODEID_2);

		query.append(_FINDER_COLUMN_G_N_H_S_HEAD_2);

		query.append(_FINDER_COLUMN_G_N_H_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_WIKIPAGE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(WikiPageModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(WikiPageModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				WikiPage.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, WikiPageImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, WikiPageImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(nodeId);

			qPos.add(head);

			qPos.add(status);

			return (List<WikiPage>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set of wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	 */
	@Override
	public WikiPage[] filterFindByG_N_H_S_PrevAndNext(long pageId,
		long groupId, long nodeId, boolean head, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_N_H_S_PrevAndNext(pageId, groupId, nodeId, head,
				status, orderByComparator);
		}

		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = filterGetByG_N_H_S_PrevAndNext(session, wikiPage,
					groupId, nodeId, head, status, orderByComparator, true);

			array[1] = wikiPage;

			array[2] = filterGetByG_N_H_S_PrevAndNext(session, wikiPage,
					groupId, nodeId, head, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected WikiPage filterGetByG_N_H_S_PrevAndNext(Session session,
		WikiPage wikiPage, long groupId, long nodeId, boolean head, int status,
		OrderByComparator<WikiPage> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_WIKIPAGE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_WIKIPAGE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_N_H_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_N_H_S_NODEID_2);

		query.append(_FINDER_COLUMN_G_N_H_S_HEAD_2);

		query.append(_FINDER_COLUMN_G_N_H_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_WIKIPAGE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(WikiPageModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(WikiPageModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				WikiPage.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, WikiPageImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, WikiPageImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(nodeId);

		qPos.add(head);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 */
	@Override
	public void removeByG_N_H_S(long groupId, long nodeId, boolean head,
		int status) {
		for (WikiPage wikiPage : findByG_N_H_S(groupId, nodeId, head, status,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(wikiPage);
		}
	}

	/**
	 * Returns the number of wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @return the number of matching wiki pages
	 */
	@Override
	public int countByG_N_H_S(long groupId, long nodeId, boolean head,
		int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_N_H_S;

		Object[] finderArgs = new Object[] { groupId, nodeId, head, status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_G_N_H_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_N_H_S_NODEID_2);

			query.append(_FINDER_COLUMN_G_N_H_S_HEAD_2);

			query.append(_FINDER_COLUMN_G_N_H_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(nodeId);

				qPos.add(head);

				qPos.add(status);

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
	 * Returns the number of wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and head = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param status the status
	 * @return the number of matching wiki pages that the user has permission to view
	 */
	@Override
	public int filterCountByG_N_H_S(long groupId, long nodeId, boolean head,
		int status) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_N_H_S(groupId, nodeId, head, status);
		}

		StringBundler query = new StringBundler(5);

		query.append(_FILTER_SQL_COUNT_WIKIPAGE_WHERE);

		query.append(_FINDER_COLUMN_G_N_H_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_N_H_S_NODEID_2);

		query.append(_FINDER_COLUMN_G_N_H_S_HEAD_2);

		query.append(_FINDER_COLUMN_G_N_H_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				WikiPage.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(nodeId);

			qPos.add(head);

			qPos.add(status);

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

	private static final String _FINDER_COLUMN_G_N_H_S_GROUPID_2 = "wikiPage.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_N_H_S_NODEID_2 = "wikiPage.nodeId = ? AND ";
	private static final String _FINDER_COLUMN_G_N_H_S_HEAD_2 = "wikiPage.head = ? AND ";
	private static final String _FINDER_COLUMN_G_N_H_S_STATUS_2 = "wikiPage.status = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_N_H_P_S = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByN_H_P_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_H_P_S =
		new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByN_H_P_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(), Integer.class.getName()
			},
			WikiPageModelImpl.NODEID_COLUMN_BITMASK |
			WikiPageModelImpl.HEAD_COLUMN_BITMASK |
			WikiPageModelImpl.PARENTTITLE_COLUMN_BITMASK |
			WikiPageModelImpl.STATUS_COLUMN_BITMASK |
			WikiPageModelImpl.TITLE_COLUMN_BITMASK |
			WikiPageModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_N_H_P_S = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByN_H_P_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(), Integer.class.getName()
			});

	/**
	 * Returns all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @return the matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_H_P_S(long nodeId, boolean head,
		String parentTitle, int status) {
		return findByN_H_P_S(nodeId, head, parentTitle, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_H_P_S(long nodeId, boolean head,
		String parentTitle, int status, int start, int end) {
		return findByN_H_P_S(nodeId, head, parentTitle, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_H_P_S(long nodeId, boolean head,
		String parentTitle, int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return findByN_H_P_S(nodeId, head, parentTitle, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_H_P_S(long nodeId, boolean head,
		String parentTitle, int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_H_P_S;
			finderArgs = new Object[] { nodeId, head, parentTitle, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_N_H_P_S;
			finderArgs = new Object[] {
					nodeId, head, parentTitle, status,
					
					start, end, orderByComparator
				};
		}

		List<WikiPage> list = null;

		if (retrieveFromCache) {
			list = (List<WikiPage>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WikiPage wikiPage : list) {
					if ((nodeId != wikiPage.getNodeId()) ||
							(head != wikiPage.getHead()) ||
							!Validator.equals(parentTitle,
								wikiPage.getParentTitle()) ||
							(status != wikiPage.getStatus())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(6 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_N_H_P_S_NODEID_2);

			query.append(_FINDER_COLUMN_N_H_P_S_HEAD_2);

			boolean bindParentTitle = false;

			if (parentTitle == null) {
				query.append(_FINDER_COLUMN_N_H_P_S_PARENTTITLE_1);
			}
			else if (parentTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_N_H_P_S_PARENTTITLE_3);
			}
			else {
				bindParentTitle = true;

				query.append(_FINDER_COLUMN_N_H_P_S_PARENTTITLE_2);
			}

			query.append(_FINDER_COLUMN_N_H_P_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(WikiPageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				qPos.add(head);

				if (bindParentTitle) {
					qPos.add(StringUtil.toLowerCase(parentTitle));
				}

				qPos.add(status);

				if (!pagination) {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByN_H_P_S_First(long nodeId, boolean head,
		String parentTitle, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByN_H_P_S_First(nodeId, head, parentTitle,
				status, orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("nodeId=");
		msg.append(nodeId);

		msg.append(", head=");
		msg.append(head);

		msg.append(", parentTitle=");
		msg.append(parentTitle);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByN_H_P_S_First(long nodeId, boolean head,
		String parentTitle, int status,
		OrderByComparator<WikiPage> orderByComparator) {
		List<WikiPage> list = findByN_H_P_S(nodeId, head, parentTitle, status,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByN_H_P_S_Last(long nodeId, boolean head,
		String parentTitle, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByN_H_P_S_Last(nodeId, head, parentTitle,
				status, orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("nodeId=");
		msg.append(nodeId);

		msg.append(", head=");
		msg.append(head);

		msg.append(", parentTitle=");
		msg.append(parentTitle);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByN_H_P_S_Last(long nodeId, boolean head,
		String parentTitle, int status,
		OrderByComparator<WikiPage> orderByComparator) {
		int count = countByN_H_P_S(nodeId, head, parentTitle, status);

		if (count == 0) {
			return null;
		}

		List<WikiPage> list = findByN_H_P_S(nodeId, head, parentTitle, status,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	 */
	@Override
	public WikiPage[] findByN_H_P_S_PrevAndNext(long pageId, long nodeId,
		boolean head, String parentTitle, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = getByN_H_P_S_PrevAndNext(session, wikiPage, nodeId,
					head, parentTitle, status, orderByComparator, true);

			array[1] = wikiPage;

			array[2] = getByN_H_P_S_PrevAndNext(session, wikiPage, nodeId,
					head, parentTitle, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected WikiPage getByN_H_P_S_PrevAndNext(Session session,
		WikiPage wikiPage, long nodeId, boolean head, String parentTitle,
		int status, OrderByComparator<WikiPage> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGE_WHERE);

		query.append(_FINDER_COLUMN_N_H_P_S_NODEID_2);

		query.append(_FINDER_COLUMN_N_H_P_S_HEAD_2);

		boolean bindParentTitle = false;

		if (parentTitle == null) {
			query.append(_FINDER_COLUMN_N_H_P_S_PARENTTITLE_1);
		}
		else if (parentTitle.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_N_H_P_S_PARENTTITLE_3);
		}
		else {
			bindParentTitle = true;

			query.append(_FINDER_COLUMN_N_H_P_S_PARENTTITLE_2);
		}

		query.append(_FINDER_COLUMN_N_H_P_S_STATUS_2);

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
			query.append(WikiPageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(nodeId);

		qPos.add(head);

		if (bindParentTitle) {
			qPos.add(StringUtil.toLowerCase(parentTitle));
		}

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63; from the database.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 */
	@Override
	public void removeByN_H_P_S(long nodeId, boolean head, String parentTitle,
		int status) {
		for (WikiPage wikiPage : findByN_H_P_S(nodeId, head, parentTitle,
				status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(wikiPage);
		}
	}

	/**
	 * Returns the number of wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @return the number of matching wiki pages
	 */
	@Override
	public int countByN_H_P_S(long nodeId, boolean head, String parentTitle,
		int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_N_H_P_S;

		Object[] finderArgs = new Object[] { nodeId, head, parentTitle, status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_N_H_P_S_NODEID_2);

			query.append(_FINDER_COLUMN_N_H_P_S_HEAD_2);

			boolean bindParentTitle = false;

			if (parentTitle == null) {
				query.append(_FINDER_COLUMN_N_H_P_S_PARENTTITLE_1);
			}
			else if (parentTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_N_H_P_S_PARENTTITLE_3);
			}
			else {
				bindParentTitle = true;

				query.append(_FINDER_COLUMN_N_H_P_S_PARENTTITLE_2);
			}

			query.append(_FINDER_COLUMN_N_H_P_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				qPos.add(head);

				if (bindParentTitle) {
					qPos.add(StringUtil.toLowerCase(parentTitle));
				}

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_N_H_P_S_NODEID_2 = "wikiPage.nodeId = ? AND ";
	private static final String _FINDER_COLUMN_N_H_P_S_HEAD_2 = "wikiPage.head = ? AND ";
	private static final String _FINDER_COLUMN_N_H_P_S_PARENTTITLE_1 = "wikiPage.parentTitle IS NULL AND ";
	private static final String _FINDER_COLUMN_N_H_P_S_PARENTTITLE_2 = "lower(wikiPage.parentTitle) = ? AND ";
	private static final String _FINDER_COLUMN_N_H_P_S_PARENTTITLE_3 = "(wikiPage.parentTitle IS NULL OR wikiPage.parentTitle = '') AND ";
	private static final String _FINDER_COLUMN_N_H_P_S_STATUS_2 = "wikiPage.status = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_N_H_P_NOTS =
		new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByN_H_P_NotS",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_N_H_P_NOTS =
		new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByN_H_P_NotS",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(), Integer.class.getName()
			});

	/**
	 * Returns all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status &ne; &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @return the matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_H_P_NotS(long nodeId, boolean head,
		String parentTitle, int status) {
		return findByN_H_P_NotS(nodeId, head, parentTitle, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_H_P_NotS(long nodeId, boolean head,
		String parentTitle, int status, int start, int end) {
		return findByN_H_P_NotS(nodeId, head, parentTitle, status, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_H_P_NotS(long nodeId, boolean head,
		String parentTitle, int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return findByN_H_P_NotS(nodeId, head, parentTitle, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_H_P_NotS(long nodeId, boolean head,
		String parentTitle, int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_N_H_P_NOTS;
		finderArgs = new Object[] {
				nodeId, head, parentTitle, status,
				
				start, end, orderByComparator
			};

		List<WikiPage> list = null;

		if (retrieveFromCache) {
			list = (List<WikiPage>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WikiPage wikiPage : list) {
					if ((nodeId != wikiPage.getNodeId()) ||
							(head != wikiPage.getHead()) ||
							!Validator.equals(parentTitle,
								wikiPage.getParentTitle()) ||
							(status == wikiPage.getStatus())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(6 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_N_H_P_NOTS_NODEID_2);

			query.append(_FINDER_COLUMN_N_H_P_NOTS_HEAD_2);

			boolean bindParentTitle = false;

			if (parentTitle == null) {
				query.append(_FINDER_COLUMN_N_H_P_NOTS_PARENTTITLE_1);
			}
			else if (parentTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_N_H_P_NOTS_PARENTTITLE_3);
			}
			else {
				bindParentTitle = true;

				query.append(_FINDER_COLUMN_N_H_P_NOTS_PARENTTITLE_2);
			}

			query.append(_FINDER_COLUMN_N_H_P_NOTS_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(WikiPageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				qPos.add(head);

				if (bindParentTitle) {
					qPos.add(StringUtil.toLowerCase(parentTitle));
				}

				qPos.add(status);

				if (!pagination) {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status &ne; &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByN_H_P_NotS_First(long nodeId, boolean head,
		String parentTitle, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByN_H_P_NotS_First(nodeId, head, parentTitle,
				status, orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("nodeId=");
		msg.append(nodeId);

		msg.append(", head=");
		msg.append(head);

		msg.append(", parentTitle=");
		msg.append(parentTitle);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status &ne; &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByN_H_P_NotS_First(long nodeId, boolean head,
		String parentTitle, int status,
		OrderByComparator<WikiPage> orderByComparator) {
		List<WikiPage> list = findByN_H_P_NotS(nodeId, head, parentTitle,
				status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status &ne; &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByN_H_P_NotS_Last(long nodeId, boolean head,
		String parentTitle, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByN_H_P_NotS_Last(nodeId, head, parentTitle,
				status, orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("nodeId=");
		msg.append(nodeId);

		msg.append(", head=");
		msg.append(head);

		msg.append(", parentTitle=");
		msg.append(parentTitle);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status &ne; &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByN_H_P_NotS_Last(long nodeId, boolean head,
		String parentTitle, int status,
		OrderByComparator<WikiPage> orderByComparator) {
		int count = countByN_H_P_NotS(nodeId, head, parentTitle, status);

		if (count == 0) {
			return null;
		}

		List<WikiPage> list = findByN_H_P_NotS(nodeId, head, parentTitle,
				status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status &ne; &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	 */
	@Override
	public WikiPage[] findByN_H_P_NotS_PrevAndNext(long pageId, long nodeId,
		boolean head, String parentTitle, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = getByN_H_P_NotS_PrevAndNext(session, wikiPage, nodeId,
					head, parentTitle, status, orderByComparator, true);

			array[1] = wikiPage;

			array[2] = getByN_H_P_NotS_PrevAndNext(session, wikiPage, nodeId,
					head, parentTitle, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected WikiPage getByN_H_P_NotS_PrevAndNext(Session session,
		WikiPage wikiPage, long nodeId, boolean head, String parentTitle,
		int status, OrderByComparator<WikiPage> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGE_WHERE);

		query.append(_FINDER_COLUMN_N_H_P_NOTS_NODEID_2);

		query.append(_FINDER_COLUMN_N_H_P_NOTS_HEAD_2);

		boolean bindParentTitle = false;

		if (parentTitle == null) {
			query.append(_FINDER_COLUMN_N_H_P_NOTS_PARENTTITLE_1);
		}
		else if (parentTitle.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_N_H_P_NOTS_PARENTTITLE_3);
		}
		else {
			bindParentTitle = true;

			query.append(_FINDER_COLUMN_N_H_P_NOTS_PARENTTITLE_2);
		}

		query.append(_FINDER_COLUMN_N_H_P_NOTS_STATUS_2);

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
			query.append(WikiPageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(nodeId);

		qPos.add(head);

		if (bindParentTitle) {
			qPos.add(StringUtil.toLowerCase(parentTitle));
		}

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status &ne; &#63; from the database.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 */
	@Override
	public void removeByN_H_P_NotS(long nodeId, boolean head,
		String parentTitle, int status) {
		for (WikiPage wikiPage : findByN_H_P_NotS(nodeId, head, parentTitle,
				status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(wikiPage);
		}
	}

	/**
	 * Returns the number of wiki pages where nodeId = &#63; and head = &#63; and parentTitle = &#63; and status &ne; &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @return the number of matching wiki pages
	 */
	@Override
	public int countByN_H_P_NotS(long nodeId, boolean head, String parentTitle,
		int status) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_N_H_P_NOTS;

		Object[] finderArgs = new Object[] { nodeId, head, parentTitle, status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_N_H_P_NOTS_NODEID_2);

			query.append(_FINDER_COLUMN_N_H_P_NOTS_HEAD_2);

			boolean bindParentTitle = false;

			if (parentTitle == null) {
				query.append(_FINDER_COLUMN_N_H_P_NOTS_PARENTTITLE_1);
			}
			else if (parentTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_N_H_P_NOTS_PARENTTITLE_3);
			}
			else {
				bindParentTitle = true;

				query.append(_FINDER_COLUMN_N_H_P_NOTS_PARENTTITLE_2);
			}

			query.append(_FINDER_COLUMN_N_H_P_NOTS_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				qPos.add(head);

				if (bindParentTitle) {
					qPos.add(StringUtil.toLowerCase(parentTitle));
				}

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_N_H_P_NOTS_NODEID_2 = "wikiPage.nodeId = ? AND ";
	private static final String _FINDER_COLUMN_N_H_P_NOTS_HEAD_2 = "wikiPage.head = ? AND ";
	private static final String _FINDER_COLUMN_N_H_P_NOTS_PARENTTITLE_1 = "wikiPage.parentTitle IS NULL AND ";
	private static final String _FINDER_COLUMN_N_H_P_NOTS_PARENTTITLE_2 = "lower(wikiPage.parentTitle) = ? AND ";
	private static final String _FINDER_COLUMN_N_H_P_NOTS_PARENTTITLE_3 = "(wikiPage.parentTitle IS NULL OR wikiPage.parentTitle = '') AND ";
	private static final String _FINDER_COLUMN_N_H_P_NOTS_STATUS_2 = "wikiPage.status != ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_N_H_R_S = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByN_H_R_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_H_R_S =
		new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByN_H_R_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(), Integer.class.getName()
			},
			WikiPageModelImpl.NODEID_COLUMN_BITMASK |
			WikiPageModelImpl.HEAD_COLUMN_BITMASK |
			WikiPageModelImpl.REDIRECTTITLE_COLUMN_BITMASK |
			WikiPageModelImpl.STATUS_COLUMN_BITMASK |
			WikiPageModelImpl.TITLE_COLUMN_BITMASK |
			WikiPageModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_N_H_R_S = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByN_H_R_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(), Integer.class.getName()
			});

	/**
	 * Returns all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 * @return the matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_H_R_S(long nodeId, boolean head,
		String redirectTitle, int status) {
		return findByN_H_R_S(nodeId, head, redirectTitle, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_H_R_S(long nodeId, boolean head,
		String redirectTitle, int status, int start, int end) {
		return findByN_H_R_S(nodeId, head, redirectTitle, status, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_H_R_S(long nodeId, boolean head,
		String redirectTitle, int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return findByN_H_R_S(nodeId, head, redirectTitle, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_H_R_S(long nodeId, boolean head,
		String redirectTitle, int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_H_R_S;
			finderArgs = new Object[] { nodeId, head, redirectTitle, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_N_H_R_S;
			finderArgs = new Object[] {
					nodeId, head, redirectTitle, status,
					
					start, end, orderByComparator
				};
		}

		List<WikiPage> list = null;

		if (retrieveFromCache) {
			list = (List<WikiPage>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WikiPage wikiPage : list) {
					if ((nodeId != wikiPage.getNodeId()) ||
							(head != wikiPage.getHead()) ||
							!Validator.equals(redirectTitle,
								wikiPage.getRedirectTitle()) ||
							(status != wikiPage.getStatus())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(6 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_N_H_R_S_NODEID_2);

			query.append(_FINDER_COLUMN_N_H_R_S_HEAD_2);

			boolean bindRedirectTitle = false;

			if (redirectTitle == null) {
				query.append(_FINDER_COLUMN_N_H_R_S_REDIRECTTITLE_1);
			}
			else if (redirectTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_N_H_R_S_REDIRECTTITLE_3);
			}
			else {
				bindRedirectTitle = true;

				query.append(_FINDER_COLUMN_N_H_R_S_REDIRECTTITLE_2);
			}

			query.append(_FINDER_COLUMN_N_H_R_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(WikiPageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				qPos.add(head);

				if (bindRedirectTitle) {
					qPos.add(StringUtil.toLowerCase(redirectTitle));
				}

				qPos.add(status);

				if (!pagination) {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByN_H_R_S_First(long nodeId, boolean head,
		String redirectTitle, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByN_H_R_S_First(nodeId, head, redirectTitle,
				status, orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("nodeId=");
		msg.append(nodeId);

		msg.append(", head=");
		msg.append(head);

		msg.append(", redirectTitle=");
		msg.append(redirectTitle);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByN_H_R_S_First(long nodeId, boolean head,
		String redirectTitle, int status,
		OrderByComparator<WikiPage> orderByComparator) {
		List<WikiPage> list = findByN_H_R_S(nodeId, head, redirectTitle,
				status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByN_H_R_S_Last(long nodeId, boolean head,
		String redirectTitle, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByN_H_R_S_Last(nodeId, head, redirectTitle,
				status, orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("nodeId=");
		msg.append(nodeId);

		msg.append(", head=");
		msg.append(head);

		msg.append(", redirectTitle=");
		msg.append(redirectTitle);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByN_H_R_S_Last(long nodeId, boolean head,
		String redirectTitle, int status,
		OrderByComparator<WikiPage> orderByComparator) {
		int count = countByN_H_R_S(nodeId, head, redirectTitle, status);

		if (count == 0) {
			return null;
		}

		List<WikiPage> list = findByN_H_R_S(nodeId, head, redirectTitle,
				status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	 */
	@Override
	public WikiPage[] findByN_H_R_S_PrevAndNext(long pageId, long nodeId,
		boolean head, String redirectTitle, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = getByN_H_R_S_PrevAndNext(session, wikiPage, nodeId,
					head, redirectTitle, status, orderByComparator, true);

			array[1] = wikiPage;

			array[2] = getByN_H_R_S_PrevAndNext(session, wikiPage, nodeId,
					head, redirectTitle, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected WikiPage getByN_H_R_S_PrevAndNext(Session session,
		WikiPage wikiPage, long nodeId, boolean head, String redirectTitle,
		int status, OrderByComparator<WikiPage> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGE_WHERE);

		query.append(_FINDER_COLUMN_N_H_R_S_NODEID_2);

		query.append(_FINDER_COLUMN_N_H_R_S_HEAD_2);

		boolean bindRedirectTitle = false;

		if (redirectTitle == null) {
			query.append(_FINDER_COLUMN_N_H_R_S_REDIRECTTITLE_1);
		}
		else if (redirectTitle.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_N_H_R_S_REDIRECTTITLE_3);
		}
		else {
			bindRedirectTitle = true;

			query.append(_FINDER_COLUMN_N_H_R_S_REDIRECTTITLE_2);
		}

		query.append(_FINDER_COLUMN_N_H_R_S_STATUS_2);

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
			query.append(WikiPageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(nodeId);

		qPos.add(head);

		if (bindRedirectTitle) {
			qPos.add(StringUtil.toLowerCase(redirectTitle));
		}

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status = &#63; from the database.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 */
	@Override
	public void removeByN_H_R_S(long nodeId, boolean head,
		String redirectTitle, int status) {
		for (WikiPage wikiPage : findByN_H_R_S(nodeId, head, redirectTitle,
				status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(wikiPage);
		}
	}

	/**
	 * Returns the number of wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 * @return the number of matching wiki pages
	 */
	@Override
	public int countByN_H_R_S(long nodeId, boolean head, String redirectTitle,
		int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_N_H_R_S;

		Object[] finderArgs = new Object[] { nodeId, head, redirectTitle, status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_N_H_R_S_NODEID_2);

			query.append(_FINDER_COLUMN_N_H_R_S_HEAD_2);

			boolean bindRedirectTitle = false;

			if (redirectTitle == null) {
				query.append(_FINDER_COLUMN_N_H_R_S_REDIRECTTITLE_1);
			}
			else if (redirectTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_N_H_R_S_REDIRECTTITLE_3);
			}
			else {
				bindRedirectTitle = true;

				query.append(_FINDER_COLUMN_N_H_R_S_REDIRECTTITLE_2);
			}

			query.append(_FINDER_COLUMN_N_H_R_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				qPos.add(head);

				if (bindRedirectTitle) {
					qPos.add(StringUtil.toLowerCase(redirectTitle));
				}

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_N_H_R_S_NODEID_2 = "wikiPage.nodeId = ? AND ";
	private static final String _FINDER_COLUMN_N_H_R_S_HEAD_2 = "wikiPage.head = ? AND ";
	private static final String _FINDER_COLUMN_N_H_R_S_REDIRECTTITLE_1 = "wikiPage.redirectTitle IS NULL AND ";
	private static final String _FINDER_COLUMN_N_H_R_S_REDIRECTTITLE_2 = "lower(wikiPage.redirectTitle) = ? AND ";
	private static final String _FINDER_COLUMN_N_H_R_S_REDIRECTTITLE_3 = "(wikiPage.redirectTitle IS NULL OR wikiPage.redirectTitle = '') AND ";
	private static final String _FINDER_COLUMN_N_H_R_S_STATUS_2 = "wikiPage.status = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_N_H_R_NOTS =
		new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByN_H_R_NotS",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_N_H_R_NOTS =
		new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByN_H_R_NotS",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(), Integer.class.getName()
			});

	/**
	 * Returns all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status &ne; &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 * @return the matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_H_R_NotS(long nodeId, boolean head,
		String redirectTitle, int status) {
		return findByN_H_R_NotS(nodeId, head, redirectTitle, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_H_R_NotS(long nodeId, boolean head,
		String redirectTitle, int status, int start, int end) {
		return findByN_H_R_NotS(nodeId, head, redirectTitle, status, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_H_R_NotS(long nodeId, boolean head,
		String redirectTitle, int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return findByN_H_R_NotS(nodeId, head, redirectTitle, status, start,
			end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByN_H_R_NotS(long nodeId, boolean head,
		String redirectTitle, int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_N_H_R_NOTS;
		finderArgs = new Object[] {
				nodeId, head, redirectTitle, status,
				
				start, end, orderByComparator
			};

		List<WikiPage> list = null;

		if (retrieveFromCache) {
			list = (List<WikiPage>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WikiPage wikiPage : list) {
					if ((nodeId != wikiPage.getNodeId()) ||
							(head != wikiPage.getHead()) ||
							!Validator.equals(redirectTitle,
								wikiPage.getRedirectTitle()) ||
							(status == wikiPage.getStatus())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(6 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_N_H_R_NOTS_NODEID_2);

			query.append(_FINDER_COLUMN_N_H_R_NOTS_HEAD_2);

			boolean bindRedirectTitle = false;

			if (redirectTitle == null) {
				query.append(_FINDER_COLUMN_N_H_R_NOTS_REDIRECTTITLE_1);
			}
			else if (redirectTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_N_H_R_NOTS_REDIRECTTITLE_3);
			}
			else {
				bindRedirectTitle = true;

				query.append(_FINDER_COLUMN_N_H_R_NOTS_REDIRECTTITLE_2);
			}

			query.append(_FINDER_COLUMN_N_H_R_NOTS_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(WikiPageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				qPos.add(head);

				if (bindRedirectTitle) {
					qPos.add(StringUtil.toLowerCase(redirectTitle));
				}

				qPos.add(status);

				if (!pagination) {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status &ne; &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByN_H_R_NotS_First(long nodeId, boolean head,
		String redirectTitle, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByN_H_R_NotS_First(nodeId, head,
				redirectTitle, status, orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("nodeId=");
		msg.append(nodeId);

		msg.append(", head=");
		msg.append(head);

		msg.append(", redirectTitle=");
		msg.append(redirectTitle);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the first wiki page in the ordered set where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status &ne; &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByN_H_R_NotS_First(long nodeId, boolean head,
		String redirectTitle, int status,
		OrderByComparator<WikiPage> orderByComparator) {
		List<WikiPage> list = findByN_H_R_NotS(nodeId, head, redirectTitle,
				status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status &ne; &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByN_H_R_NotS_Last(long nodeId, boolean head,
		String redirectTitle, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByN_H_R_NotS_Last(nodeId, head, redirectTitle,
				status, orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("nodeId=");
		msg.append(nodeId);

		msg.append(", head=");
		msg.append(head);

		msg.append(", redirectTitle=");
		msg.append(redirectTitle);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the last wiki page in the ordered set where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status &ne; &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByN_H_R_NotS_Last(long nodeId, boolean head,
		String redirectTitle, int status,
		OrderByComparator<WikiPage> orderByComparator) {
		int count = countByN_H_R_NotS(nodeId, head, redirectTitle, status);

		if (count == 0) {
			return null;
		}

		List<WikiPage> list = findByN_H_R_NotS(nodeId, head, redirectTitle,
				status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status &ne; &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	 */
	@Override
	public WikiPage[] findByN_H_R_NotS_PrevAndNext(long pageId, long nodeId,
		boolean head, String redirectTitle, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = getByN_H_R_NotS_PrevAndNext(session, wikiPage, nodeId,
					head, redirectTitle, status, orderByComparator, true);

			array[1] = wikiPage;

			array[2] = getByN_H_R_NotS_PrevAndNext(session, wikiPage, nodeId,
					head, redirectTitle, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected WikiPage getByN_H_R_NotS_PrevAndNext(Session session,
		WikiPage wikiPage, long nodeId, boolean head, String redirectTitle,
		int status, OrderByComparator<WikiPage> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGE_WHERE);

		query.append(_FINDER_COLUMN_N_H_R_NOTS_NODEID_2);

		query.append(_FINDER_COLUMN_N_H_R_NOTS_HEAD_2);

		boolean bindRedirectTitle = false;

		if (redirectTitle == null) {
			query.append(_FINDER_COLUMN_N_H_R_NOTS_REDIRECTTITLE_1);
		}
		else if (redirectTitle.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_N_H_R_NOTS_REDIRECTTITLE_3);
		}
		else {
			bindRedirectTitle = true;

			query.append(_FINDER_COLUMN_N_H_R_NOTS_REDIRECTTITLE_2);
		}

		query.append(_FINDER_COLUMN_N_H_R_NOTS_STATUS_2);

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
			query.append(WikiPageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(nodeId);

		qPos.add(head);

		if (bindRedirectTitle) {
			qPos.add(StringUtil.toLowerCase(redirectTitle));
		}

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status &ne; &#63; from the database.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 */
	@Override
	public void removeByN_H_R_NotS(long nodeId, boolean head,
		String redirectTitle, int status) {
		for (WikiPage wikiPage : findByN_H_R_NotS(nodeId, head, redirectTitle,
				status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(wikiPage);
		}
	}

	/**
	 * Returns the number of wiki pages where nodeId = &#63; and head = &#63; and redirectTitle = &#63; and status &ne; &#63;.
	 *
	 * @param nodeId the node ID
	 * @param head the head
	 * @param redirectTitle the redirect title
	 * @param status the status
	 * @return the number of matching wiki pages
	 */
	@Override
	public int countByN_H_R_NotS(long nodeId, boolean head,
		String redirectTitle, int status) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_N_H_R_NOTS;

		Object[] finderArgs = new Object[] { nodeId, head, redirectTitle, status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_N_H_R_NOTS_NODEID_2);

			query.append(_FINDER_COLUMN_N_H_R_NOTS_HEAD_2);

			boolean bindRedirectTitle = false;

			if (redirectTitle == null) {
				query.append(_FINDER_COLUMN_N_H_R_NOTS_REDIRECTTITLE_1);
			}
			else if (redirectTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_N_H_R_NOTS_REDIRECTTITLE_3);
			}
			else {
				bindRedirectTitle = true;

				query.append(_FINDER_COLUMN_N_H_R_NOTS_REDIRECTTITLE_2);
			}

			query.append(_FINDER_COLUMN_N_H_R_NOTS_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(nodeId);

				qPos.add(head);

				if (bindRedirectTitle) {
					qPos.add(StringUtil.toLowerCase(redirectTitle));
				}

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_N_H_R_NOTS_NODEID_2 = "wikiPage.nodeId = ? AND ";
	private static final String _FINDER_COLUMN_N_H_R_NOTS_HEAD_2 = "wikiPage.head = ? AND ";
	private static final String _FINDER_COLUMN_N_H_R_NOTS_REDIRECTTITLE_1 = "wikiPage.redirectTitle IS NULL AND ";
	private static final String _FINDER_COLUMN_N_H_R_NOTS_REDIRECTTITLE_2 = "lower(wikiPage.redirectTitle) = ? AND ";
	private static final String _FINDER_COLUMN_N_H_R_NOTS_REDIRECTTITLE_3 = "(wikiPage.redirectTitle IS NULL OR wikiPage.redirectTitle = '') AND ";
	private static final String _FINDER_COLUMN_N_H_R_NOTS_STATUS_2 = "wikiPage.status != ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_N_H_P_S =
		new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_N_H_P_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(), String.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_N_H_P_S =
		new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, WikiPageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_N_H_P_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			WikiPageModelImpl.GROUPID_COLUMN_BITMASK |
			WikiPageModelImpl.NODEID_COLUMN_BITMASK |
			WikiPageModelImpl.HEAD_COLUMN_BITMASK |
			WikiPageModelImpl.PARENTTITLE_COLUMN_BITMASK |
			WikiPageModelImpl.STATUS_COLUMN_BITMASK |
			WikiPageModelImpl.TITLE_COLUMN_BITMASK |
			WikiPageModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_N_H_P_S = new FinderPath(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_N_H_P_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(), String.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @return the matching wiki pages
	 */
	@Override
	public List<WikiPage> findByG_N_H_P_S(long groupId, long nodeId,
		boolean head, String parentTitle, int status) {
		return findByG_N_H_P_S(groupId, nodeId, head, parentTitle, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByG_N_H_P_S(long groupId, long nodeId,
		boolean head, String parentTitle, int status, int start, int end) {
		return findByG_N_H_P_S(groupId, nodeId, head, parentTitle, status,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByG_N_H_P_S(long groupId, long nodeId,
		boolean head, String parentTitle, int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return findByG_N_H_P_S(groupId, nodeId, head, parentTitle, status,
			start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching wiki pages
	 */
	@Override
	public List<WikiPage> findByG_N_H_P_S(long groupId, long nodeId,
		boolean head, String parentTitle, int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_N_H_P_S;
			finderArgs = new Object[] { groupId, nodeId, head, parentTitle, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_N_H_P_S;
			finderArgs = new Object[] {
					groupId, nodeId, head, parentTitle, status,
					
					start, end, orderByComparator
				};
		}

		List<WikiPage> list = null;

		if (retrieveFromCache) {
			list = (List<WikiPage>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WikiPage wikiPage : list) {
					if ((groupId != wikiPage.getGroupId()) ||
							(nodeId != wikiPage.getNodeId()) ||
							(head != wikiPage.getHead()) ||
							!Validator.equals(parentTitle,
								wikiPage.getParentTitle()) ||
							(status != wikiPage.getStatus())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(7 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(7);
			}

			query.append(_SQL_SELECT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_G_N_H_P_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_N_H_P_S_NODEID_2);

			query.append(_FINDER_COLUMN_G_N_H_P_S_HEAD_2);

			boolean bindParentTitle = false;

			if (parentTitle == null) {
				query.append(_FINDER_COLUMN_G_N_H_P_S_PARENTTITLE_1);
			}
			else if (parentTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_N_H_P_S_PARENTTITLE_3);
			}
			else {
				bindParentTitle = true;

				query.append(_FINDER_COLUMN_G_N_H_P_S_PARENTTITLE_2);
			}

			query.append(_FINDER_COLUMN_G_N_H_P_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(WikiPageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(nodeId);

				qPos.add(head);

				if (bindParentTitle) {
					qPos.add(StringUtil.toLowerCase(parentTitle));
				}

				qPos.add(status);

				if (!pagination) {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByG_N_H_P_S_First(long groupId, long nodeId,
		boolean head, String parentTitle, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByG_N_H_P_S_First(groupId, nodeId, head,
				parentTitle, status, orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(12);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", nodeId=");
		msg.append(nodeId);

		msg.append(", head=");
		msg.append(head);

		msg.append(", parentTitle=");
		msg.append(parentTitle);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the first wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByG_N_H_P_S_First(long groupId, long nodeId,
		boolean head, String parentTitle, int status,
		OrderByComparator<WikiPage> orderByComparator) {
		List<WikiPage> list = findByG_N_H_P_S(groupId, nodeId, head,
				parentTitle, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a matching wiki page could not be found
	 */
	@Override
	public WikiPage findByG_N_H_P_S_Last(long groupId, long nodeId,
		boolean head, String parentTitle, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByG_N_H_P_S_Last(groupId, nodeId, head,
				parentTitle, status, orderByComparator);

		if (wikiPage != null) {
			return wikiPage;
		}

		StringBundler msg = new StringBundler(12);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", nodeId=");
		msg.append(nodeId);

		msg.append(", head=");
		msg.append(head);

		msg.append(", parentTitle=");
		msg.append(parentTitle);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPageException(msg.toString());
	}

	/**
	 * Returns the last wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page, or <code>null</code> if a matching wiki page could not be found
	 */
	@Override
	public WikiPage fetchByG_N_H_P_S_Last(long groupId, long nodeId,
		boolean head, String parentTitle, int status,
		OrderByComparator<WikiPage> orderByComparator) {
		int count = countByG_N_H_P_S(groupId, nodeId, head, parentTitle, status);

		if (count == 0) {
			return null;
		}

		List<WikiPage> list = findByG_N_H_P_S(groupId, nodeId, head,
				parentTitle, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set where groupId = &#63; and nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	 */
	@Override
	public WikiPage[] findByG_N_H_P_S_PrevAndNext(long pageId, long groupId,
		long nodeId, boolean head, String parentTitle, int status,
		OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = getByG_N_H_P_S_PrevAndNext(session, wikiPage, groupId,
					nodeId, head, parentTitle, status, orderByComparator, true);

			array[1] = wikiPage;

			array[2] = getByG_N_H_P_S_PrevAndNext(session, wikiPage, groupId,
					nodeId, head, parentTitle, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected WikiPage getByG_N_H_P_S_PrevAndNext(Session session,
		WikiPage wikiPage, long groupId, long nodeId, boolean head,
		String parentTitle, int status,
		OrderByComparator<WikiPage> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_WIKIPAGE_WHERE);

		query.append(_FINDER_COLUMN_G_N_H_P_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_N_H_P_S_NODEID_2);

		query.append(_FINDER_COLUMN_G_N_H_P_S_HEAD_2);

		boolean bindParentTitle = false;

		if (parentTitle == null) {
			query.append(_FINDER_COLUMN_G_N_H_P_S_PARENTTITLE_1);
		}
		else if (parentTitle.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_N_H_P_S_PARENTTITLE_3);
		}
		else {
			bindParentTitle = true;

			query.append(_FINDER_COLUMN_G_N_H_P_S_PARENTTITLE_2);
		}

		query.append(_FINDER_COLUMN_G_N_H_P_S_STATUS_2);

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
			query.append(WikiPageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(nodeId);

		qPos.add(head);

		if (bindParentTitle) {
			qPos.add(StringUtil.toLowerCase(parentTitle));
		}

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @return the matching wiki pages that the user has permission to view
	 */
	@Override
	public List<WikiPage> filterFindByG_N_H_P_S(long groupId, long nodeId,
		boolean head, String parentTitle, int status) {
		return filterFindByG_N_H_P_S(groupId, nodeId, head, parentTitle,
			status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of matching wiki pages that the user has permission to view
	 */
	@Override
	public List<WikiPage> filterFindByG_N_H_P_S(long groupId, long nodeId,
		boolean head, String parentTitle, int status, int start, int end) {
		return filterFindByG_N_H_P_S(groupId, nodeId, head, parentTitle,
			status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the wiki pages that the user has permissions to view where groupId = &#63; and nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki pages that the user has permission to view
	 */
	@Override
	public List<WikiPage> filterFindByG_N_H_P_S(long groupId, long nodeId,
		boolean head, String parentTitle, int status, int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_N_H_P_S(groupId, nodeId, head, parentTitle, status,
				start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(7 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(7);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_WIKIPAGE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_WIKIPAGE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_N_H_P_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_N_H_P_S_NODEID_2);

		query.append(_FINDER_COLUMN_G_N_H_P_S_HEAD_2);

		boolean bindParentTitle = false;

		if (parentTitle == null) {
			query.append(_FINDER_COLUMN_G_N_H_P_S_PARENTTITLE_1);
		}
		else if (parentTitle.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_N_H_P_S_PARENTTITLE_3);
		}
		else {
			bindParentTitle = true;

			query.append(_FINDER_COLUMN_G_N_H_P_S_PARENTTITLE_2);
		}

		query.append(_FINDER_COLUMN_G_N_H_P_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_WIKIPAGE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(WikiPageModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(WikiPageModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				WikiPage.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, WikiPageImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, WikiPageImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(nodeId);

			qPos.add(head);

			if (bindParentTitle) {
				qPos.add(StringUtil.toLowerCase(parentTitle));
			}

			qPos.add(status);

			return (List<WikiPage>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the wiki pages before and after the current wiki page in the ordered set of wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * @param pageId the primary key of the current wiki page
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	 */
	@Override
	public WikiPage[] filterFindByG_N_H_P_S_PrevAndNext(long pageId,
		long groupId, long nodeId, boolean head, String parentTitle,
		int status, OrderByComparator<WikiPage> orderByComparator)
		throws NoSuchPageException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_N_H_P_S_PrevAndNext(pageId, groupId, nodeId, head,
				parentTitle, status, orderByComparator);
		}

		WikiPage wikiPage = findByPrimaryKey(pageId);

		Session session = null;

		try {
			session = openSession();

			WikiPage[] array = new WikiPageImpl[3];

			array[0] = filterGetByG_N_H_P_S_PrevAndNext(session, wikiPage,
					groupId, nodeId, head, parentTitle, status,
					orderByComparator, true);

			array[1] = wikiPage;

			array[2] = filterGetByG_N_H_P_S_PrevAndNext(session, wikiPage,
					groupId, nodeId, head, parentTitle, status,
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

	protected WikiPage filterGetByG_N_H_P_S_PrevAndNext(Session session,
		WikiPage wikiPage, long groupId, long nodeId, boolean head,
		String parentTitle, int status,
		OrderByComparator<WikiPage> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_WIKIPAGE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_WIKIPAGE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_N_H_P_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_N_H_P_S_NODEID_2);

		query.append(_FINDER_COLUMN_G_N_H_P_S_HEAD_2);

		boolean bindParentTitle = false;

		if (parentTitle == null) {
			query.append(_FINDER_COLUMN_G_N_H_P_S_PARENTTITLE_1);
		}
		else if (parentTitle.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_N_H_P_S_PARENTTITLE_3);
		}
		else {
			bindParentTitle = true;

			query.append(_FINDER_COLUMN_G_N_H_P_S_PARENTTITLE_2);
		}

		query.append(_FINDER_COLUMN_G_N_H_P_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_WIKIPAGE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(WikiPageModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(WikiPageModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				WikiPage.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, WikiPageImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, WikiPageImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(nodeId);

		qPos.add(head);

		if (bindParentTitle) {
			qPos.add(StringUtil.toLowerCase(parentTitle));
		}

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(wikiPage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<WikiPage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 */
	@Override
	public void removeByG_N_H_P_S(long groupId, long nodeId, boolean head,
		String parentTitle, int status) {
		for (WikiPage wikiPage : findByG_N_H_P_S(groupId, nodeId, head,
				parentTitle, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(wikiPage);
		}
	}

	/**
	 * Returns the number of wiki pages where groupId = &#63; and nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @return the number of matching wiki pages
	 */
	@Override
	public int countByG_N_H_P_S(long groupId, long nodeId, boolean head,
		String parentTitle, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_N_H_P_S;

		Object[] finderArgs = new Object[] {
				groupId, nodeId, head, parentTitle, status
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_COUNT_WIKIPAGE_WHERE);

			query.append(_FINDER_COLUMN_G_N_H_P_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_N_H_P_S_NODEID_2);

			query.append(_FINDER_COLUMN_G_N_H_P_S_HEAD_2);

			boolean bindParentTitle = false;

			if (parentTitle == null) {
				query.append(_FINDER_COLUMN_G_N_H_P_S_PARENTTITLE_1);
			}
			else if (parentTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_N_H_P_S_PARENTTITLE_3);
			}
			else {
				bindParentTitle = true;

				query.append(_FINDER_COLUMN_G_N_H_P_S_PARENTTITLE_2);
			}

			query.append(_FINDER_COLUMN_G_N_H_P_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(nodeId);

				qPos.add(head);

				if (bindParentTitle) {
					qPos.add(StringUtil.toLowerCase(parentTitle));
				}

				qPos.add(status);

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
	 * Returns the number of wiki pages that the user has permission to view where groupId = &#63; and nodeId = &#63; and head = &#63; and parentTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param nodeId the node ID
	 * @param head the head
	 * @param parentTitle the parent title
	 * @param status the status
	 * @return the number of matching wiki pages that the user has permission to view
	 */
	@Override
	public int filterCountByG_N_H_P_S(long groupId, long nodeId, boolean head,
		String parentTitle, int status) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_N_H_P_S(groupId, nodeId, head, parentTitle, status);
		}

		StringBundler query = new StringBundler(6);

		query.append(_FILTER_SQL_COUNT_WIKIPAGE_WHERE);

		query.append(_FINDER_COLUMN_G_N_H_P_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_N_H_P_S_NODEID_2);

		query.append(_FINDER_COLUMN_G_N_H_P_S_HEAD_2);

		boolean bindParentTitle = false;

		if (parentTitle == null) {
			query.append(_FINDER_COLUMN_G_N_H_P_S_PARENTTITLE_1);
		}
		else if (parentTitle.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_N_H_P_S_PARENTTITLE_3);
		}
		else {
			bindParentTitle = true;

			query.append(_FINDER_COLUMN_G_N_H_P_S_PARENTTITLE_2);
		}

		query.append(_FINDER_COLUMN_G_N_H_P_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				WikiPage.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(nodeId);

			qPos.add(head);

			if (bindParentTitle) {
				qPos.add(StringUtil.toLowerCase(parentTitle));
			}

			qPos.add(status);

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

	private static final String _FINDER_COLUMN_G_N_H_P_S_GROUPID_2 = "wikiPage.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_N_H_P_S_NODEID_2 = "wikiPage.nodeId = ? AND ";
	private static final String _FINDER_COLUMN_G_N_H_P_S_HEAD_2 = "wikiPage.head = ? AND ";
	private static final String _FINDER_COLUMN_G_N_H_P_S_PARENTTITLE_1 = "wikiPage.parentTitle IS NULL AND ";
	private static final String _FINDER_COLUMN_G_N_H_P_S_PARENTTITLE_2 = "lower(wikiPage.parentTitle) = ? AND ";
	private static final String _FINDER_COLUMN_G_N_H_P_S_PARENTTITLE_3 = "(wikiPage.parentTitle IS NULL OR wikiPage.parentTitle = '') AND ";
	private static final String _FINDER_COLUMN_G_N_H_P_S_STATUS_2 = "wikiPage.status = ?";

	public WikiPagePersistenceImpl() {
		setModelClass(WikiPage.class);
	}

	/**
	 * Caches the wiki page in the entity cache if it is enabled.
	 *
	 * @param wikiPage the wiki page
	 */
	@Override
	public void cacheResult(WikiPage wikiPage) {
		entityCache.putResult(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageImpl.class, wikiPage.getPrimaryKey(), wikiPage);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { wikiPage.getUuid(), wikiPage.getGroupId() }, wikiPage);

		finderCache.putResult(FINDER_PATH_FETCH_BY_R_N_V,
			new Object[] {
				wikiPage.getResourcePrimKey(), wikiPage.getNodeId(),
				wikiPage.getVersion()
			}, wikiPage);

		finderCache.putResult(FINDER_PATH_FETCH_BY_N_T_V,
			new Object[] {
				wikiPage.getNodeId(), wikiPage.getTitle(), wikiPage.getVersion()
			}, wikiPage);

		wikiPage.resetOriginalValues();
	}

	/**
	 * Caches the wiki pages in the entity cache if it is enabled.
	 *
	 * @param wikiPages the wiki pages
	 */
	@Override
	public void cacheResult(List<WikiPage> wikiPages) {
		for (WikiPage wikiPage : wikiPages) {
			if (entityCache.getResult(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
						WikiPageImpl.class, wikiPage.getPrimaryKey()) == null) {
				cacheResult(wikiPage);
			}
			else {
				wikiPage.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all wiki pages.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(WikiPageImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the wiki page.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(WikiPage wikiPage) {
		entityCache.removeResult(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageImpl.class, wikiPage.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((WikiPageModelImpl)wikiPage);
	}

	@Override
	public void clearCache(List<WikiPage> wikiPages) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (WikiPage wikiPage : wikiPages) {
			entityCache.removeResult(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
				WikiPageImpl.class, wikiPage.getPrimaryKey());

			clearUniqueFindersCache((WikiPageModelImpl)wikiPage);
		}
	}

	protected void cacheUniqueFindersCache(
		WikiPageModelImpl wikiPageModelImpl, boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] {
					wikiPageModelImpl.getUuid(), wikiPageModelImpl.getGroupId()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
				wikiPageModelImpl);

			args = new Object[] {
					wikiPageModelImpl.getResourcePrimKey(),
					wikiPageModelImpl.getNodeId(),
					wikiPageModelImpl.getVersion()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_R_N_V, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_R_N_V, args,
				wikiPageModelImpl);

			args = new Object[] {
					wikiPageModelImpl.getNodeId(), wikiPageModelImpl.getTitle(),
					wikiPageModelImpl.getVersion()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_N_T_V, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_N_T_V, args,
				wikiPageModelImpl);
		}
		else {
			if ((wikiPageModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						wikiPageModelImpl.getUuid(),
						wikiPageModelImpl.getGroupId()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
					wikiPageModelImpl);
			}

			if ((wikiPageModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_R_N_V.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						wikiPageModelImpl.getResourcePrimKey(),
						wikiPageModelImpl.getNodeId(),
						wikiPageModelImpl.getVersion()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_R_N_V, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_R_N_V, args,
					wikiPageModelImpl);
			}

			if ((wikiPageModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_N_T_V.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						wikiPageModelImpl.getNodeId(),
						wikiPageModelImpl.getTitle(),
						wikiPageModelImpl.getVersion()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_N_T_V, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_N_T_V, args,
					wikiPageModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(WikiPageModelImpl wikiPageModelImpl) {
		Object[] args = new Object[] {
				wikiPageModelImpl.getUuid(), wikiPageModelImpl.getGroupId()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

		if ((wikiPageModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			args = new Object[] {
					wikiPageModelImpl.getOriginalUuid(),
					wikiPageModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		args = new Object[] {
				wikiPageModelImpl.getResourcePrimKey(),
				wikiPageModelImpl.getNodeId(), wikiPageModelImpl.getVersion()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_R_N_V, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_R_N_V, args);

		if ((wikiPageModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_R_N_V.getColumnBitmask()) != 0) {
			args = new Object[] {
					wikiPageModelImpl.getOriginalResourcePrimKey(),
					wikiPageModelImpl.getOriginalNodeId(),
					wikiPageModelImpl.getOriginalVersion()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_R_N_V, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_R_N_V, args);
		}

		args = new Object[] {
				wikiPageModelImpl.getNodeId(), wikiPageModelImpl.getTitle(),
				wikiPageModelImpl.getVersion()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_N_T_V, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_N_T_V, args);

		if ((wikiPageModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_N_T_V.getColumnBitmask()) != 0) {
			args = new Object[] {
					wikiPageModelImpl.getOriginalNodeId(),
					wikiPageModelImpl.getOriginalTitle(),
					wikiPageModelImpl.getOriginalVersion()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_N_T_V, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_N_T_V, args);
		}
	}

	/**
	 * Creates a new wiki page with the primary key. Does not add the wiki page to the database.
	 *
	 * @param pageId the primary key for the new wiki page
	 * @return the new wiki page
	 */
	@Override
	public WikiPage create(long pageId) {
		WikiPage wikiPage = new WikiPageImpl();

		wikiPage.setNew(true);
		wikiPage.setPrimaryKey(pageId);

		String uuid = PortalUUIDUtil.generate();

		wikiPage.setUuid(uuid);

		wikiPage.setCompanyId(companyProvider.getCompanyId());

		return wikiPage;
	}

	/**
	 * Removes the wiki page with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param pageId the primary key of the wiki page
	 * @return the wiki page that was removed
	 * @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	 */
	@Override
	public WikiPage remove(long pageId) throws NoSuchPageException {
		return remove((Serializable)pageId);
	}

	/**
	 * Removes the wiki page with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the wiki page
	 * @return the wiki page that was removed
	 * @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	 */
	@Override
	public WikiPage remove(Serializable primaryKey) throws NoSuchPageException {
		Session session = null;

		try {
			session = openSession();

			WikiPage wikiPage = (WikiPage)session.get(WikiPageImpl.class,
					primaryKey);

			if (wikiPage == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPageException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(wikiPage);
		}
		catch (NoSuchPageException nsee) {
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
	protected WikiPage removeImpl(WikiPage wikiPage) {
		wikiPage = toUnwrappedModel(wikiPage);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(wikiPage)) {
				wikiPage = (WikiPage)session.get(WikiPageImpl.class,
						wikiPage.getPrimaryKeyObj());
			}

			if (wikiPage != null) {
				session.delete(wikiPage);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (wikiPage != null) {
			clearCache(wikiPage);
		}

		return wikiPage;
	}

	@Override
	public WikiPage updateImpl(WikiPage wikiPage) {
		wikiPage = toUnwrappedModel(wikiPage);

		boolean isNew = wikiPage.isNew();

		WikiPageModelImpl wikiPageModelImpl = (WikiPageModelImpl)wikiPage;

		if (Validator.isNull(wikiPage.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			wikiPage.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (wikiPage.getCreateDate() == null)) {
			if (serviceContext == null) {
				wikiPage.setCreateDate(now);
			}
			else {
				wikiPage.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!wikiPageModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				wikiPage.setModifiedDate(now);
			}
			else {
				wikiPage.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		long userId = GetterUtil.getLong(PrincipalThreadLocal.getName());

		if (userId > 0) {
			long companyId = wikiPage.getCompanyId();

			long groupId = wikiPage.getGroupId();

			long pageId = 0;

			if (!isNew) {
				pageId = wikiPage.getPrimaryKey();
			}

			try {
				wikiPage.setTitle(SanitizerUtil.sanitize(companyId, groupId,
						userId,
						com.liferay.wiki.model.WikiPage.class.getName(),
						pageId, ContentTypes.TEXT_PLAIN, Sanitizer.MODE_ALL,
						wikiPage.getTitle(), null));
			}
			catch (SanitizerException se) {
				throw new SystemException(se);
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (wikiPage.isNew()) {
				session.save(wikiPage);

				wikiPage.setNew(false);
			}
			else {
				wikiPage = (WikiPage)session.merge(wikiPage);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !WikiPageModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((wikiPageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { wikiPageModelImpl.getOriginalUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { wikiPageModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((wikiPageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						wikiPageModelImpl.getOriginalUuid(),
						wikiPageModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						wikiPageModelImpl.getUuid(),
						wikiPageModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((wikiPageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RESOURCEPRIMKEY.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						wikiPageModelImpl.getOriginalResourcePrimKey()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_RESOURCEPRIMKEY,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RESOURCEPRIMKEY,
					args);

				args = new Object[] { wikiPageModelImpl.getResourcePrimKey() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_RESOURCEPRIMKEY,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RESOURCEPRIMKEY,
					args);
			}

			if ((wikiPageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_NODEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						wikiPageModelImpl.getOriginalNodeId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_NODEID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_NODEID,
					args);

				args = new Object[] { wikiPageModelImpl.getNodeId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_NODEID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_NODEID,
					args);
			}

			if ((wikiPageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FORMAT.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						wikiPageModelImpl.getOriginalFormat()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_FORMAT, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FORMAT,
					args);

				args = new Object[] { wikiPageModelImpl.getFormat() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_FORMAT, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FORMAT,
					args);
			}

			if ((wikiPageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_N.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						wikiPageModelImpl.getOriginalResourcePrimKey(),
						wikiPageModelImpl.getOriginalNodeId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_R_N, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_N,
					args);

				args = new Object[] {
						wikiPageModelImpl.getResourcePrimKey(),
						wikiPageModelImpl.getNodeId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_R_N, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_N,
					args);
			}

			if ((wikiPageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						wikiPageModelImpl.getOriginalResourcePrimKey(),
						wikiPageModelImpl.getOriginalStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_R_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_S,
					args);

				args = new Object[] {
						wikiPageModelImpl.getResourcePrimKey(),
						wikiPageModelImpl.getStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_R_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_S,
					args);
			}

			if ((wikiPageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						wikiPageModelImpl.getOriginalNodeId(),
						wikiPageModelImpl.getOriginalTitle()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_N_T, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_T,
					args);

				args = new Object[] {
						wikiPageModelImpl.getNodeId(),
						wikiPageModelImpl.getTitle()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_N_T, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_T,
					args);
			}

			if ((wikiPageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_H.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						wikiPageModelImpl.getOriginalNodeId(),
						wikiPageModelImpl.getOriginalHead()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_N_H, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_H,
					args);

				args = new Object[] {
						wikiPageModelImpl.getNodeId(),
						wikiPageModelImpl.getHead()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_N_H, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_H,
					args);
			}

			if ((wikiPageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						wikiPageModelImpl.getOriginalNodeId(),
						wikiPageModelImpl.getOriginalParentTitle()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_N_P, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_P,
					args);

				args = new Object[] {
						wikiPageModelImpl.getNodeId(),
						wikiPageModelImpl.getParentTitle()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_N_P, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_P,
					args);
			}

			if ((wikiPageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_R.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						wikiPageModelImpl.getOriginalNodeId(),
						wikiPageModelImpl.getOriginalRedirectTitle()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_N_R, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_R,
					args);

				args = new Object[] {
						wikiPageModelImpl.getNodeId(),
						wikiPageModelImpl.getRedirectTitle()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_N_R, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_R,
					args);
			}

			if ((wikiPageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						wikiPageModelImpl.getOriginalNodeId(),
						wikiPageModelImpl.getOriginalStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_N_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_S,
					args);

				args = new Object[] {
						wikiPageModelImpl.getNodeId(),
						wikiPageModelImpl.getStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_N_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_S,
					args);
			}

			if ((wikiPageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_N_H.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						wikiPageModelImpl.getOriginalResourcePrimKey(),
						wikiPageModelImpl.getOriginalNodeId(),
						wikiPageModelImpl.getOriginalHead()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_R_N_H, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_N_H,
					args);

				args = new Object[] {
						wikiPageModelImpl.getResourcePrimKey(),
						wikiPageModelImpl.getNodeId(),
						wikiPageModelImpl.getHead()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_R_N_H, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_N_H,
					args);
			}

			if ((wikiPageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_N_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						wikiPageModelImpl.getOriginalResourcePrimKey(),
						wikiPageModelImpl.getOriginalNodeId(),
						wikiPageModelImpl.getOriginalStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_R_N_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_N_S,
					args);

				args = new Object[] {
						wikiPageModelImpl.getResourcePrimKey(),
						wikiPageModelImpl.getNodeId(),
						wikiPageModelImpl.getStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_R_N_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_N_S,
					args);
			}

			if ((wikiPageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_N_H.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						wikiPageModelImpl.getOriginalGroupId(),
						wikiPageModelImpl.getOriginalNodeId(),
						wikiPageModelImpl.getOriginalHead()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_N_H, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_N_H,
					args);

				args = new Object[] {
						wikiPageModelImpl.getGroupId(),
						wikiPageModelImpl.getNodeId(),
						wikiPageModelImpl.getHead()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_N_H, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_N_H,
					args);
			}

			if ((wikiPageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_N_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						wikiPageModelImpl.getOriginalGroupId(),
						wikiPageModelImpl.getOriginalNodeId(),
						wikiPageModelImpl.getOriginalStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_N_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_N_S,
					args);

				args = new Object[] {
						wikiPageModelImpl.getGroupId(),
						wikiPageModelImpl.getNodeId(),
						wikiPageModelImpl.getStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_N_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_N_S,
					args);
			}

			if ((wikiPageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_N_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						wikiPageModelImpl.getOriginalUserId(),
						wikiPageModelImpl.getOriginalNodeId(),
						wikiPageModelImpl.getOriginalStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_U_N_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_N_S,
					args);

				args = new Object[] {
						wikiPageModelImpl.getUserId(),
						wikiPageModelImpl.getNodeId(),
						wikiPageModelImpl.getStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_U_N_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_N_S,
					args);
			}

			if ((wikiPageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_T_H.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						wikiPageModelImpl.getOriginalNodeId(),
						wikiPageModelImpl.getOriginalTitle(),
						wikiPageModelImpl.getOriginalHead()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_N_T_H, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_T_H,
					args);

				args = new Object[] {
						wikiPageModelImpl.getNodeId(),
						wikiPageModelImpl.getTitle(),
						wikiPageModelImpl.getHead()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_N_T_H, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_T_H,
					args);
			}

			if ((wikiPageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_T_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						wikiPageModelImpl.getOriginalNodeId(),
						wikiPageModelImpl.getOriginalTitle(),
						wikiPageModelImpl.getOriginalStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_N_T_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_T_S,
					args);

				args = new Object[] {
						wikiPageModelImpl.getNodeId(),
						wikiPageModelImpl.getTitle(),
						wikiPageModelImpl.getStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_N_T_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_T_S,
					args);
			}

			if ((wikiPageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_H_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						wikiPageModelImpl.getOriginalNodeId(),
						wikiPageModelImpl.getOriginalHead(),
						wikiPageModelImpl.getOriginalParentTitle()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_N_H_P, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_H_P,
					args);

				args = new Object[] {
						wikiPageModelImpl.getNodeId(),
						wikiPageModelImpl.getHead(),
						wikiPageModelImpl.getParentTitle()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_N_H_P, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_H_P,
					args);
			}

			if ((wikiPageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_H_R.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						wikiPageModelImpl.getOriginalNodeId(),
						wikiPageModelImpl.getOriginalHead(),
						wikiPageModelImpl.getOriginalRedirectTitle()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_N_H_R, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_H_R,
					args);

				args = new Object[] {
						wikiPageModelImpl.getNodeId(),
						wikiPageModelImpl.getHead(),
						wikiPageModelImpl.getRedirectTitle()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_N_H_R, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_H_R,
					args);
			}

			if ((wikiPageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_H_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						wikiPageModelImpl.getOriginalNodeId(),
						wikiPageModelImpl.getOriginalHead(),
						wikiPageModelImpl.getOriginalStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_N_H_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_H_S,
					args);

				args = new Object[] {
						wikiPageModelImpl.getNodeId(),
						wikiPageModelImpl.getHead(),
						wikiPageModelImpl.getStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_N_H_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_H_S,
					args);
			}

			if ((wikiPageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_N_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						wikiPageModelImpl.getOriginalGroupId(),
						wikiPageModelImpl.getOriginalUserId(),
						wikiPageModelImpl.getOriginalNodeId(),
						wikiPageModelImpl.getOriginalStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_U_N_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_N_S,
					args);

				args = new Object[] {
						wikiPageModelImpl.getGroupId(),
						wikiPageModelImpl.getUserId(),
						wikiPageModelImpl.getNodeId(),
						wikiPageModelImpl.getStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_U_N_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_N_S,
					args);
			}

			if ((wikiPageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_N_T_H.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						wikiPageModelImpl.getOriginalGroupId(),
						wikiPageModelImpl.getOriginalNodeId(),
						wikiPageModelImpl.getOriginalTitle(),
						wikiPageModelImpl.getOriginalHead()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_N_T_H, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_N_T_H,
					args);

				args = new Object[] {
						wikiPageModelImpl.getGroupId(),
						wikiPageModelImpl.getNodeId(),
						wikiPageModelImpl.getTitle(),
						wikiPageModelImpl.getHead()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_N_T_H, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_N_T_H,
					args);
			}

			if ((wikiPageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_N_H_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						wikiPageModelImpl.getOriginalGroupId(),
						wikiPageModelImpl.getOriginalNodeId(),
						wikiPageModelImpl.getOriginalHead(),
						wikiPageModelImpl.getOriginalStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_N_H_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_N_H_S,
					args);

				args = new Object[] {
						wikiPageModelImpl.getGroupId(),
						wikiPageModelImpl.getNodeId(),
						wikiPageModelImpl.getHead(),
						wikiPageModelImpl.getStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_N_H_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_N_H_S,
					args);
			}

			if ((wikiPageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_H_P_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						wikiPageModelImpl.getOriginalNodeId(),
						wikiPageModelImpl.getOriginalHead(),
						wikiPageModelImpl.getOriginalParentTitle(),
						wikiPageModelImpl.getOriginalStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_N_H_P_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_H_P_S,
					args);

				args = new Object[] {
						wikiPageModelImpl.getNodeId(),
						wikiPageModelImpl.getHead(),
						wikiPageModelImpl.getParentTitle(),
						wikiPageModelImpl.getStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_N_H_P_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_H_P_S,
					args);
			}

			if ((wikiPageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_H_R_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						wikiPageModelImpl.getOriginalNodeId(),
						wikiPageModelImpl.getOriginalHead(),
						wikiPageModelImpl.getOriginalRedirectTitle(),
						wikiPageModelImpl.getOriginalStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_N_H_R_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_H_R_S,
					args);

				args = new Object[] {
						wikiPageModelImpl.getNodeId(),
						wikiPageModelImpl.getHead(),
						wikiPageModelImpl.getRedirectTitle(),
						wikiPageModelImpl.getStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_N_H_R_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_H_R_S,
					args);
			}

			if ((wikiPageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_N_H_P_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						wikiPageModelImpl.getOriginalGroupId(),
						wikiPageModelImpl.getOriginalNodeId(),
						wikiPageModelImpl.getOriginalHead(),
						wikiPageModelImpl.getOriginalParentTitle(),
						wikiPageModelImpl.getOriginalStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_N_H_P_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_N_H_P_S,
					args);

				args = new Object[] {
						wikiPageModelImpl.getGroupId(),
						wikiPageModelImpl.getNodeId(),
						wikiPageModelImpl.getHead(),
						wikiPageModelImpl.getParentTitle(),
						wikiPageModelImpl.getStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_N_H_P_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_N_H_P_S,
					args);
			}
		}

		entityCache.putResult(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
			WikiPageImpl.class, wikiPage.getPrimaryKey(), wikiPage, false);

		clearUniqueFindersCache(wikiPageModelImpl);
		cacheUniqueFindersCache(wikiPageModelImpl, isNew);

		wikiPage.resetOriginalValues();

		return wikiPage;
	}

	protected WikiPage toUnwrappedModel(WikiPage wikiPage) {
		if (wikiPage instanceof WikiPageImpl) {
			return wikiPage;
		}

		WikiPageImpl wikiPageImpl = new WikiPageImpl();

		wikiPageImpl.setNew(wikiPage.isNew());
		wikiPageImpl.setPrimaryKey(wikiPage.getPrimaryKey());

		wikiPageImpl.setUuid(wikiPage.getUuid());
		wikiPageImpl.setPageId(wikiPage.getPageId());
		wikiPageImpl.setResourcePrimKey(wikiPage.getResourcePrimKey());
		wikiPageImpl.setGroupId(wikiPage.getGroupId());
		wikiPageImpl.setCompanyId(wikiPage.getCompanyId());
		wikiPageImpl.setUserId(wikiPage.getUserId());
		wikiPageImpl.setUserName(wikiPage.getUserName());
		wikiPageImpl.setCreateDate(wikiPage.getCreateDate());
		wikiPageImpl.setModifiedDate(wikiPage.getModifiedDate());
		wikiPageImpl.setNodeId(wikiPage.getNodeId());
		wikiPageImpl.setTitle(wikiPage.getTitle());
		wikiPageImpl.setVersion(wikiPage.getVersion());
		wikiPageImpl.setMinorEdit(wikiPage.isMinorEdit());
		wikiPageImpl.setContent(wikiPage.getContent());
		wikiPageImpl.setSummary(wikiPage.getSummary());
		wikiPageImpl.setFormat(wikiPage.getFormat());
		wikiPageImpl.setHead(wikiPage.isHead());
		wikiPageImpl.setParentTitle(wikiPage.getParentTitle());
		wikiPageImpl.setRedirectTitle(wikiPage.getRedirectTitle());
		wikiPageImpl.setLastPublishDate(wikiPage.getLastPublishDate());
		wikiPageImpl.setStatus(wikiPage.getStatus());
		wikiPageImpl.setStatusByUserId(wikiPage.getStatusByUserId());
		wikiPageImpl.setStatusByUserName(wikiPage.getStatusByUserName());
		wikiPageImpl.setStatusDate(wikiPage.getStatusDate());

		return wikiPageImpl;
	}

	/**
	 * Returns the wiki page with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the wiki page
	 * @return the wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	 */
	@Override
	public WikiPage findByPrimaryKey(Serializable primaryKey)
		throws NoSuchPageException {
		WikiPage wikiPage = fetchByPrimaryKey(primaryKey);

		if (wikiPage == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPageException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return wikiPage;
	}

	/**
	 * Returns the wiki page with the primary key or throws a {@link com.liferay.wiki.NoSuchPageException} if it could not be found.
	 *
	 * @param pageId the primary key of the wiki page
	 * @return the wiki page
	 * @throws com.liferay.wiki.NoSuchPageException if a wiki page with the primary key could not be found
	 */
	@Override
	public WikiPage findByPrimaryKey(long pageId) throws NoSuchPageException {
		return findByPrimaryKey((Serializable)pageId);
	}

	/**
	 * Returns the wiki page with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the wiki page
	 * @return the wiki page, or <code>null</code> if a wiki page with the primary key could not be found
	 */
	@Override
	public WikiPage fetchByPrimaryKey(Serializable primaryKey) {
		WikiPage wikiPage = (WikiPage)entityCache.getResult(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
				WikiPageImpl.class, primaryKey);

		if (wikiPage == _nullWikiPage) {
			return null;
		}

		if (wikiPage == null) {
			Session session = null;

			try {
				session = openSession();

				wikiPage = (WikiPage)session.get(WikiPageImpl.class, primaryKey);

				if (wikiPage != null) {
					cacheResult(wikiPage);
				}
				else {
					entityCache.putResult(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
						WikiPageImpl.class, primaryKey, _nullWikiPage);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
					WikiPageImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return wikiPage;
	}

	/**
	 * Returns the wiki page with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param pageId the primary key of the wiki page
	 * @return the wiki page, or <code>null</code> if a wiki page with the primary key could not be found
	 */
	@Override
	public WikiPage fetchByPrimaryKey(long pageId) {
		return fetchByPrimaryKey((Serializable)pageId);
	}

	@Override
	public Map<Serializable, WikiPage> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, WikiPage> map = new HashMap<Serializable, WikiPage>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			WikiPage wikiPage = fetchByPrimaryKey(primaryKey);

			if (wikiPage != null) {
				map.put(primaryKey, wikiPage);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			WikiPage wikiPage = (WikiPage)entityCache.getResult(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
					WikiPageImpl.class, primaryKey);

			if (wikiPage == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, wikiPage);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_WIKIPAGE_WHERE_PKS_IN);

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

			for (WikiPage wikiPage : (List<WikiPage>)q.list()) {
				map.put(wikiPage.getPrimaryKeyObj(), wikiPage);

				cacheResult(wikiPage);

				uncachedPrimaryKeys.remove(wikiPage.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(WikiPageModelImpl.ENTITY_CACHE_ENABLED,
					WikiPageImpl.class, primaryKey, _nullWikiPage);
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
	 * Returns all the wiki pages.
	 *
	 * @return the wiki pages
	 */
	@Override
	public List<WikiPage> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the wiki pages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @return the range of wiki pages
	 */
	@Override
	public List<WikiPage> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the wiki pages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of wiki pages
	 */
	@Override
	public List<WikiPage> findAll(int start, int end,
		OrderByComparator<WikiPage> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the wiki pages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of wiki pages
	 * @param end the upper bound of the range of wiki pages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of wiki pages
	 */
	@Override
	public List<WikiPage> findAll(int start, int end,
		OrderByComparator<WikiPage> orderByComparator, boolean retrieveFromCache) {
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

		List<WikiPage> list = null;

		if (retrieveFromCache) {
			list = (List<WikiPage>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_WIKIPAGE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_WIKIPAGE;

				if (pagination) {
					sql = sql.concat(WikiPageModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<WikiPage>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Removes all the wiki pages from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (WikiPage wikiPage : findAll()) {
			remove(wikiPage);
		}
	}

	/**
	 * Returns the number of wiki pages.
	 *
	 * @return the number of wiki pages
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_WIKIPAGE);

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
		return WikiPageModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the wiki page persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(WikiPageImpl.class.getName());
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
	private static final String _SQL_SELECT_WIKIPAGE = "SELECT wikiPage FROM WikiPage wikiPage";
	private static final String _SQL_SELECT_WIKIPAGE_WHERE_PKS_IN = "SELECT wikiPage FROM WikiPage wikiPage WHERE pageId IN (";
	private static final String _SQL_SELECT_WIKIPAGE_WHERE = "SELECT wikiPage FROM WikiPage wikiPage WHERE ";
	private static final String _SQL_COUNT_WIKIPAGE = "SELECT COUNT(wikiPage) FROM WikiPage wikiPage";
	private static final String _SQL_COUNT_WIKIPAGE_WHERE = "SELECT COUNT(wikiPage) FROM WikiPage wikiPage WHERE ";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "wikiPage.resourcePrimKey";
	private static final String _FILTER_SQL_SELECT_WIKIPAGE_WHERE = "SELECT DISTINCT {wikiPage.*} FROM WikiPage wikiPage WHERE ";
	private static final String _FILTER_SQL_SELECT_WIKIPAGE_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {WikiPage.*} FROM (SELECT DISTINCT wikiPage.pageId FROM WikiPage wikiPage WHERE ";
	private static final String _FILTER_SQL_SELECT_WIKIPAGE_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN WikiPage ON TEMP_TABLE.pageId = WikiPage.pageId";
	private static final String _FILTER_SQL_COUNT_WIKIPAGE_WHERE = "SELECT COUNT(DISTINCT wikiPage.pageId) AS COUNT_VALUE FROM WikiPage wikiPage WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "wikiPage";
	private static final String _FILTER_ENTITY_TABLE = "WikiPage";
	private static final String _ORDER_BY_ENTITY_ALIAS = "wikiPage.";
	private static final String _ORDER_BY_ENTITY_TABLE = "WikiPage.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No WikiPage exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No WikiPage exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(WikiPagePersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid"
			});
	private static final WikiPage _nullWikiPage = new WikiPageImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<WikiPage> toCacheModel() {
				return _nullWikiPageCacheModel;
			}
		};

	private static final CacheModel<WikiPage> _nullWikiPageCacheModel = new CacheModel<WikiPage>() {
			@Override
			public WikiPage toEntityModel() {
				return _nullWikiPage;
			}
		};
}