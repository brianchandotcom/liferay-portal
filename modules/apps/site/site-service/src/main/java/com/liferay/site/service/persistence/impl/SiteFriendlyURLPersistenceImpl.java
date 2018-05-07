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

package com.liferay.site.service.persistence.impl;

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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import com.liferay.site.exception.NoSuchFriendlyURLException;
import com.liferay.site.model.SiteFriendlyURL;
import com.liferay.site.model.impl.SiteFriendlyURLImpl;
import com.liferay.site.model.impl.SiteFriendlyURLModelImpl;
import com.liferay.site.service.persistence.SiteFriendlyURLPersistence;

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
 * The persistence implementation for the site friendly url service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SiteFriendlyURLPersistence
 * @see com.liferay.site.service.persistence.SiteFriendlyURLUtil
 * @generated
 */
@ProviderType
public class SiteFriendlyURLPersistenceImpl extends BasePersistenceImpl<SiteFriendlyURL>
	implements SiteFriendlyURLPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link SiteFriendlyURLUtil} to access the site friendly url persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = SiteFriendlyURLImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(SiteFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			SiteFriendlyURLModelImpl.FINDER_CACHE_ENABLED,
			SiteFriendlyURLImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(SiteFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			SiteFriendlyURLModelImpl.FINDER_CACHE_ENABLED,
			SiteFriendlyURLImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SiteFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			SiteFriendlyURLModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(SiteFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			SiteFriendlyURLModelImpl.FINDER_CACHE_ENABLED,
			SiteFriendlyURLImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(SiteFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			SiteFriendlyURLModelImpl.FINDER_CACHE_ENABLED,
			SiteFriendlyURLImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			SiteFriendlyURLModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(SiteFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			SiteFriendlyURLModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the site friendly urls where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching site friendly urls
	 */
	@Override
	public List<SiteFriendlyURL> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the site friendly urls where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @return the range of matching site friendly urls
	 */
	@Override
	public List<SiteFriendlyURL> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the site friendly urls where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site friendly urls
	 */
	@Override
	public List<SiteFriendlyURL> findByUuid(String uuid, int start, int end,
		OrderByComparator<SiteFriendlyURL> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the site friendly urls where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching site friendly urls
	 */
	@Override
	public List<SiteFriendlyURL> findByUuid(String uuid, int start, int end,
		OrderByComparator<SiteFriendlyURL> orderByComparator,
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

		List<SiteFriendlyURL> list = null;

		if (retrieveFromCache) {
			list = (List<SiteFriendlyURL>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SiteFriendlyURL siteFriendlyURL : list) {
					if (!Objects.equals(uuid, siteFriendlyURL.getUuid())) {
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

			query.append(_SQL_SELECT_SITEFRIENDLYURL_WHERE);

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
				query.append(SiteFriendlyURLModelImpl.ORDER_BY_JPQL);
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
					list = (List<SiteFriendlyURL>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SiteFriendlyURL>)QueryUtil.list(q,
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
	 * Returns the first site friendly url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site friendly url
	 * @throws NoSuchFriendlyURLException if a matching site friendly url could not be found
	 */
	@Override
	public SiteFriendlyURL findByUuid_First(String uuid,
		OrderByComparator<SiteFriendlyURL> orderByComparator)
		throws NoSuchFriendlyURLException {
		SiteFriendlyURL siteFriendlyURL = fetchByUuid_First(uuid,
				orderByComparator);

		if (siteFriendlyURL != null) {
			return siteFriendlyURL;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchFriendlyURLException(msg.toString());
	}

	/**
	 * Returns the first site friendly url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	@Override
	public SiteFriendlyURL fetchByUuid_First(String uuid,
		OrderByComparator<SiteFriendlyURL> orderByComparator) {
		List<SiteFriendlyURL> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last site friendly url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site friendly url
	 * @throws NoSuchFriendlyURLException if a matching site friendly url could not be found
	 */
	@Override
	public SiteFriendlyURL findByUuid_Last(String uuid,
		OrderByComparator<SiteFriendlyURL> orderByComparator)
		throws NoSuchFriendlyURLException {
		SiteFriendlyURL siteFriendlyURL = fetchByUuid_Last(uuid,
				orderByComparator);

		if (siteFriendlyURL != null) {
			return siteFriendlyURL;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchFriendlyURLException(msg.toString());
	}

	/**
	 * Returns the last site friendly url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	@Override
	public SiteFriendlyURL fetchByUuid_Last(String uuid,
		OrderByComparator<SiteFriendlyURL> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<SiteFriendlyURL> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the site friendly urls before and after the current site friendly url in the ordered set where uuid = &#63;.
	 *
	 * @param siteFriendlyURLId the primary key of the current site friendly url
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next site friendly url
	 * @throws NoSuchFriendlyURLException if a site friendly url with the primary key could not be found
	 */
	@Override
	public SiteFriendlyURL[] findByUuid_PrevAndNext(long siteFriendlyURLId,
		String uuid, OrderByComparator<SiteFriendlyURL> orderByComparator)
		throws NoSuchFriendlyURLException {
		SiteFriendlyURL siteFriendlyURL = findByPrimaryKey(siteFriendlyURLId);

		Session session = null;

		try {
			session = openSession();

			SiteFriendlyURL[] array = new SiteFriendlyURLImpl[3];

			array[0] = getByUuid_PrevAndNext(session, siteFriendlyURL, uuid,
					orderByComparator, true);

			array[1] = siteFriendlyURL;

			array[2] = getByUuid_PrevAndNext(session, siteFriendlyURL, uuid,
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

	protected SiteFriendlyURL getByUuid_PrevAndNext(Session session,
		SiteFriendlyURL siteFriendlyURL, String uuid,
		OrderByComparator<SiteFriendlyURL> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SITEFRIENDLYURL_WHERE);

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
			query.append(SiteFriendlyURLModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(siteFriendlyURL);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SiteFriendlyURL> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the site friendly urls where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (SiteFriendlyURL siteFriendlyURL : findByUuid(uuid,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(siteFriendlyURL);
		}
	}

	/**
	 * Returns the number of site friendly urls where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching site friendly urls
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SITEFRIENDLYURL_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "siteFriendlyURL.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "siteFriendlyURL.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(siteFriendlyURL.uuid IS NULL OR siteFriendlyURL.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(SiteFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			SiteFriendlyURLModelImpl.FINDER_CACHE_ENABLED,
			SiteFriendlyURLImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			SiteFriendlyURLModelImpl.UUID_COLUMN_BITMASK |
			SiteFriendlyURLModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(SiteFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			SiteFriendlyURLModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the site friendly url where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchFriendlyURLException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching site friendly url
	 * @throws NoSuchFriendlyURLException if a matching site friendly url could not be found
	 */
	@Override
	public SiteFriendlyURL findByUUID_G(String uuid, long groupId)
		throws NoSuchFriendlyURLException {
		SiteFriendlyURL siteFriendlyURL = fetchByUUID_G(uuid, groupId);

		if (siteFriendlyURL == null) {
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

			throw new NoSuchFriendlyURLException(msg.toString());
		}

		return siteFriendlyURL;
	}

	/**
	 * Returns the site friendly url where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	@Override
	public SiteFriendlyURL fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the site friendly url where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	@Override
	public SiteFriendlyURL fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof SiteFriendlyURL) {
			SiteFriendlyURL siteFriendlyURL = (SiteFriendlyURL)result;

			if (!Objects.equals(uuid, siteFriendlyURL.getUuid()) ||
					(groupId != siteFriendlyURL.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_SITEFRIENDLYURL_WHERE);

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

				List<SiteFriendlyURL> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					SiteFriendlyURL siteFriendlyURL = list.get(0);

					result = siteFriendlyURL;

					cacheResult(siteFriendlyURL);

					if ((siteFriendlyURL.getUuid() == null) ||
							!siteFriendlyURL.getUuid().equals(uuid) ||
							(siteFriendlyURL.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, siteFriendlyURL);
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
			return (SiteFriendlyURL)result;
		}
	}

	/**
	 * Removes the site friendly url where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the site friendly url that was removed
	 */
	@Override
	public SiteFriendlyURL removeByUUID_G(String uuid, long groupId)
		throws NoSuchFriendlyURLException {
		SiteFriendlyURL siteFriendlyURL = findByUUID_G(uuid, groupId);

		return remove(siteFriendlyURL);
	}

	/**
	 * Returns the number of site friendly urls where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching site friendly urls
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SITEFRIENDLYURL_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "siteFriendlyURL.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "siteFriendlyURL.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(siteFriendlyURL.uuid IS NULL OR siteFriendlyURL.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "siteFriendlyURL.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(SiteFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			SiteFriendlyURLModelImpl.FINDER_CACHE_ENABLED,
			SiteFriendlyURLImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(SiteFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			SiteFriendlyURLModelImpl.FINDER_CACHE_ENABLED,
			SiteFriendlyURLImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			SiteFriendlyURLModelImpl.UUID_COLUMN_BITMASK |
			SiteFriendlyURLModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(SiteFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			SiteFriendlyURLModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the site friendly urls where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching site friendly urls
	 */
	@Override
	public List<SiteFriendlyURL> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the site friendly urls where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @return the range of matching site friendly urls
	 */
	@Override
	public List<SiteFriendlyURL> findByUuid_C(String uuid, long companyId,
		int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the site friendly urls where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site friendly urls
	 */
	@Override
	public List<SiteFriendlyURL> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator<SiteFriendlyURL> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the site friendly urls where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching site friendly urls
	 */
	@Override
	public List<SiteFriendlyURL> findByUuid_C(String uuid, long companyId,
		int start, int end,
		OrderByComparator<SiteFriendlyURL> orderByComparator,
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

		List<SiteFriendlyURL> list = null;

		if (retrieveFromCache) {
			list = (List<SiteFriendlyURL>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SiteFriendlyURL siteFriendlyURL : list) {
					if (!Objects.equals(uuid, siteFriendlyURL.getUuid()) ||
							(companyId != siteFriendlyURL.getCompanyId())) {
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

			query.append(_SQL_SELECT_SITEFRIENDLYURL_WHERE);

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
				query.append(SiteFriendlyURLModelImpl.ORDER_BY_JPQL);
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
					list = (List<SiteFriendlyURL>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SiteFriendlyURL>)QueryUtil.list(q,
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
	 * Returns the first site friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site friendly url
	 * @throws NoSuchFriendlyURLException if a matching site friendly url could not be found
	 */
	@Override
	public SiteFriendlyURL findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<SiteFriendlyURL> orderByComparator)
		throws NoSuchFriendlyURLException {
		SiteFriendlyURL siteFriendlyURL = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (siteFriendlyURL != null) {
			return siteFriendlyURL;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchFriendlyURLException(msg.toString());
	}

	/**
	 * Returns the first site friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	@Override
	public SiteFriendlyURL fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<SiteFriendlyURL> orderByComparator) {
		List<SiteFriendlyURL> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last site friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site friendly url
	 * @throws NoSuchFriendlyURLException if a matching site friendly url could not be found
	 */
	@Override
	public SiteFriendlyURL findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<SiteFriendlyURL> orderByComparator)
		throws NoSuchFriendlyURLException {
		SiteFriendlyURL siteFriendlyURL = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (siteFriendlyURL != null) {
			return siteFriendlyURL;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchFriendlyURLException(msg.toString());
	}

	/**
	 * Returns the last site friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	@Override
	public SiteFriendlyURL fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<SiteFriendlyURL> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<SiteFriendlyURL> list = findByUuid_C(uuid, companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the site friendly urls before and after the current site friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param siteFriendlyURLId the primary key of the current site friendly url
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next site friendly url
	 * @throws NoSuchFriendlyURLException if a site friendly url with the primary key could not be found
	 */
	@Override
	public SiteFriendlyURL[] findByUuid_C_PrevAndNext(long siteFriendlyURLId,
		String uuid, long companyId,
		OrderByComparator<SiteFriendlyURL> orderByComparator)
		throws NoSuchFriendlyURLException {
		SiteFriendlyURL siteFriendlyURL = findByPrimaryKey(siteFriendlyURLId);

		Session session = null;

		try {
			session = openSession();

			SiteFriendlyURL[] array = new SiteFriendlyURLImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, siteFriendlyURL, uuid,
					companyId, orderByComparator, true);

			array[1] = siteFriendlyURL;

			array[2] = getByUuid_C_PrevAndNext(session, siteFriendlyURL, uuid,
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

	protected SiteFriendlyURL getByUuid_C_PrevAndNext(Session session,
		SiteFriendlyURL siteFriendlyURL, String uuid, long companyId,
		OrderByComparator<SiteFriendlyURL> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_SITEFRIENDLYURL_WHERE);

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
			query.append(SiteFriendlyURLModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(siteFriendlyURL);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SiteFriendlyURL> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the site friendly urls where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (SiteFriendlyURL siteFriendlyURL : findByUuid_C(uuid, companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(siteFriendlyURL);
		}
	}

	/**
	 * Returns the number of site friendly urls where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching site friendly urls
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SITEFRIENDLYURL_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "siteFriendlyURL.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "siteFriendlyURL.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(siteFriendlyURL.uuid IS NULL OR siteFriendlyURL.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "siteFriendlyURL.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_G = new FinderPath(SiteFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			SiteFriendlyURLModelImpl.FINDER_CACHE_ENABLED,
			SiteFriendlyURLImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_G",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_G = new FinderPath(SiteFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			SiteFriendlyURLModelImpl.FINDER_CACHE_ENABLED,
			SiteFriendlyURLImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_G",
			new String[] { Long.class.getName(), Long.class.getName() },
			SiteFriendlyURLModelImpl.COMPANYID_COLUMN_BITMASK |
			SiteFriendlyURLModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_G = new FinderPath(SiteFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			SiteFriendlyURLModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_G",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the site friendly urls where companyId = &#63; and groupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @return the matching site friendly urls
	 */
	@Override
	public List<SiteFriendlyURL> findByC_G(long companyId, long groupId) {
		return findByC_G(companyId, groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the site friendly urls where companyId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @return the range of matching site friendly urls
	 */
	@Override
	public List<SiteFriendlyURL> findByC_G(long companyId, long groupId,
		int start, int end) {
		return findByC_G(companyId, groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the site friendly urls where companyId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site friendly urls
	 */
	@Override
	public List<SiteFriendlyURL> findByC_G(long companyId, long groupId,
		int start, int end, OrderByComparator<SiteFriendlyURL> orderByComparator) {
		return findByC_G(companyId, groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the site friendly urls where companyId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching site friendly urls
	 */
	@Override
	public List<SiteFriendlyURL> findByC_G(long companyId, long groupId,
		int start, int end,
		OrderByComparator<SiteFriendlyURL> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_G;
			finderArgs = new Object[] { companyId, groupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_G;
			finderArgs = new Object[] {
					companyId, groupId,
					
					start, end, orderByComparator
				};
		}

		List<SiteFriendlyURL> list = null;

		if (retrieveFromCache) {
			list = (List<SiteFriendlyURL>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SiteFriendlyURL siteFriendlyURL : list) {
					if ((companyId != siteFriendlyURL.getCompanyId()) ||
							(groupId != siteFriendlyURL.getGroupId())) {
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

			query.append(_SQL_SELECT_SITEFRIENDLYURL_WHERE);

			query.append(_FINDER_COLUMN_C_G_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_G_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SiteFriendlyURLModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<SiteFriendlyURL>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SiteFriendlyURL>)QueryUtil.list(q,
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
	 * Returns the first site friendly url in the ordered set where companyId = &#63; and groupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site friendly url
	 * @throws NoSuchFriendlyURLException if a matching site friendly url could not be found
	 */
	@Override
	public SiteFriendlyURL findByC_G_First(long companyId, long groupId,
		OrderByComparator<SiteFriendlyURL> orderByComparator)
		throws NoSuchFriendlyURLException {
		SiteFriendlyURL siteFriendlyURL = fetchByC_G_First(companyId, groupId,
				orderByComparator);

		if (siteFriendlyURL != null) {
			return siteFriendlyURL;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchFriendlyURLException(msg.toString());
	}

	/**
	 * Returns the first site friendly url in the ordered set where companyId = &#63; and groupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	@Override
	public SiteFriendlyURL fetchByC_G_First(long companyId, long groupId,
		OrderByComparator<SiteFriendlyURL> orderByComparator) {
		List<SiteFriendlyURL> list = findByC_G(companyId, groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last site friendly url in the ordered set where companyId = &#63; and groupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site friendly url
	 * @throws NoSuchFriendlyURLException if a matching site friendly url could not be found
	 */
	@Override
	public SiteFriendlyURL findByC_G_Last(long companyId, long groupId,
		OrderByComparator<SiteFriendlyURL> orderByComparator)
		throws NoSuchFriendlyURLException {
		SiteFriendlyURL siteFriendlyURL = fetchByC_G_Last(companyId, groupId,
				orderByComparator);

		if (siteFriendlyURL != null) {
			return siteFriendlyURL;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchFriendlyURLException(msg.toString());
	}

	/**
	 * Returns the last site friendly url in the ordered set where companyId = &#63; and groupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	@Override
	public SiteFriendlyURL fetchByC_G_Last(long companyId, long groupId,
		OrderByComparator<SiteFriendlyURL> orderByComparator) {
		int count = countByC_G(companyId, groupId);

		if (count == 0) {
			return null;
		}

		List<SiteFriendlyURL> list = findByC_G(companyId, groupId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the site friendly urls before and after the current site friendly url in the ordered set where companyId = &#63; and groupId = &#63;.
	 *
	 * @param siteFriendlyURLId the primary key of the current site friendly url
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next site friendly url
	 * @throws NoSuchFriendlyURLException if a site friendly url with the primary key could not be found
	 */
	@Override
	public SiteFriendlyURL[] findByC_G_PrevAndNext(long siteFriendlyURLId,
		long companyId, long groupId,
		OrderByComparator<SiteFriendlyURL> orderByComparator)
		throws NoSuchFriendlyURLException {
		SiteFriendlyURL siteFriendlyURL = findByPrimaryKey(siteFriendlyURLId);

		Session session = null;

		try {
			session = openSession();

			SiteFriendlyURL[] array = new SiteFriendlyURLImpl[3];

			array[0] = getByC_G_PrevAndNext(session, siteFriendlyURL,
					companyId, groupId, orderByComparator, true);

			array[1] = siteFriendlyURL;

			array[2] = getByC_G_PrevAndNext(session, siteFriendlyURL,
					companyId, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SiteFriendlyURL getByC_G_PrevAndNext(Session session,
		SiteFriendlyURL siteFriendlyURL, long companyId, long groupId,
		OrderByComparator<SiteFriendlyURL> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_SITEFRIENDLYURL_WHERE);

		query.append(_FINDER_COLUMN_C_G_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_G_GROUPID_2);

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
			query.append(SiteFriendlyURLModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(siteFriendlyURL);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SiteFriendlyURL> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the site friendly urls where companyId = &#63; and groupId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 */
	@Override
	public void removeByC_G(long companyId, long groupId) {
		for (SiteFriendlyURL siteFriendlyURL : findByC_G(companyId, groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(siteFriendlyURL);
		}
	}

	/**
	 * Returns the number of site friendly urls where companyId = &#63; and groupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @return the number of matching site friendly urls
	 */
	@Override
	public int countByC_G(long companyId, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_G;

		Object[] finderArgs = new Object[] { companyId, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SITEFRIENDLYURL_WHERE);

			query.append(_FINDER_COLUMN_C_G_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

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

	private static final String _FINDER_COLUMN_C_G_COMPANYID_2 = "siteFriendlyURL.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_G_GROUPID_2 = "siteFriendlyURL.groupId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_F = new FinderPath(SiteFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			SiteFriendlyURLModelImpl.FINDER_CACHE_ENABLED,
			SiteFriendlyURLImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByC_F",
			new String[] { Long.class.getName(), String.class.getName() },
			SiteFriendlyURLModelImpl.COMPANYID_COLUMN_BITMASK |
			SiteFriendlyURLModelImpl.FRIENDLYURL_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_F = new FinderPath(SiteFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			SiteFriendlyURLModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_F",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the site friendly url where companyId = &#63; and friendlyURL = &#63; or throws a {@link NoSuchFriendlyURLException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @return the matching site friendly url
	 * @throws NoSuchFriendlyURLException if a matching site friendly url could not be found
	 */
	@Override
	public SiteFriendlyURL findByC_F(long companyId, String friendlyURL)
		throws NoSuchFriendlyURLException {
		SiteFriendlyURL siteFriendlyURL = fetchByC_F(companyId, friendlyURL);

		if (siteFriendlyURL == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", friendlyURL=");
			msg.append(friendlyURL);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchFriendlyURLException(msg.toString());
		}

		return siteFriendlyURL;
	}

	/**
	 * Returns the site friendly url where companyId = &#63; and friendlyURL = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @return the matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	@Override
	public SiteFriendlyURL fetchByC_F(long companyId, String friendlyURL) {
		return fetchByC_F(companyId, friendlyURL, true);
	}

	/**
	 * Returns the site friendly url where companyId = &#63; and friendlyURL = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	@Override
	public SiteFriendlyURL fetchByC_F(long companyId, String friendlyURL,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { companyId, friendlyURL };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_F,
					finderArgs, this);
		}

		if (result instanceof SiteFriendlyURL) {
			SiteFriendlyURL siteFriendlyURL = (SiteFriendlyURL)result;

			if ((companyId != siteFriendlyURL.getCompanyId()) ||
					!Objects.equals(friendlyURL,
						siteFriendlyURL.getFriendlyURL())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_SITEFRIENDLYURL_WHERE);

			query.append(_FINDER_COLUMN_C_F_COMPANYID_2);

			boolean bindFriendlyURL = false;

			if (friendlyURL == null) {
				query.append(_FINDER_COLUMN_C_F_FRIENDLYURL_1);
			}
			else if (friendlyURL.equals("")) {
				query.append(_FINDER_COLUMN_C_F_FRIENDLYURL_3);
			}
			else {
				bindFriendlyURL = true;

				query.append(_FINDER_COLUMN_C_F_FRIENDLYURL_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindFriendlyURL) {
					qPos.add(friendlyURL);
				}

				List<SiteFriendlyURL> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_F, finderArgs,
						list);
				}
				else {
					SiteFriendlyURL siteFriendlyURL = list.get(0);

					result = siteFriendlyURL;

					cacheResult(siteFriendlyURL);

					if ((siteFriendlyURL.getCompanyId() != companyId) ||
							(siteFriendlyURL.getFriendlyURL() == null) ||
							!siteFriendlyURL.getFriendlyURL().equals(friendlyURL)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_C_F,
							finderArgs, siteFriendlyURL);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C_F, finderArgs);

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
			return (SiteFriendlyURL)result;
		}
	}

	/**
	 * Removes the site friendly url where companyId = &#63; and friendlyURL = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @return the site friendly url that was removed
	 */
	@Override
	public SiteFriendlyURL removeByC_F(long companyId, String friendlyURL)
		throws NoSuchFriendlyURLException {
		SiteFriendlyURL siteFriendlyURL = findByC_F(companyId, friendlyURL);

		return remove(siteFriendlyURL);
	}

	/**
	 * Returns the number of site friendly urls where companyId = &#63; and friendlyURL = &#63;.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @return the number of matching site friendly urls
	 */
	@Override
	public int countByC_F(long companyId, String friendlyURL) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_F;

		Object[] finderArgs = new Object[] { companyId, friendlyURL };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SITEFRIENDLYURL_WHERE);

			query.append(_FINDER_COLUMN_C_F_COMPANYID_2);

			boolean bindFriendlyURL = false;

			if (friendlyURL == null) {
				query.append(_FINDER_COLUMN_C_F_FRIENDLYURL_1);
			}
			else if (friendlyURL.equals("")) {
				query.append(_FINDER_COLUMN_C_F_FRIENDLYURL_3);
			}
			else {
				bindFriendlyURL = true;

				query.append(_FINDER_COLUMN_C_F_FRIENDLYURL_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindFriendlyURL) {
					qPos.add(friendlyURL);
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

	private static final String _FINDER_COLUMN_C_F_COMPANYID_2 = "siteFriendlyURL.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_F_FRIENDLYURL_1 = "siteFriendlyURL.friendlyURL IS NULL";
	private static final String _FINDER_COLUMN_C_F_FRIENDLYURL_2 = "siteFriendlyURL.friendlyURL = ?";
	private static final String _FINDER_COLUMN_C_F_FRIENDLYURL_3 = "(siteFriendlyURL.friendlyURL IS NULL OR siteFriendlyURL.friendlyURL = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_G_L = new FinderPath(SiteFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			SiteFriendlyURLModelImpl.FINDER_CACHE_ENABLED,
			SiteFriendlyURLImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_G_L",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			SiteFriendlyURLModelImpl.COMPANYID_COLUMN_BITMASK |
			SiteFriendlyURLModelImpl.GROUPID_COLUMN_BITMASK |
			SiteFriendlyURLModelImpl.LANGUAGEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_G_L = new FinderPath(SiteFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			SiteFriendlyURLModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_G_L",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns the site friendly url where companyId = &#63; and groupId = &#63; and languageId = &#63; or throws a {@link NoSuchFriendlyURLException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param languageId the language ID
	 * @return the matching site friendly url
	 * @throws NoSuchFriendlyURLException if a matching site friendly url could not be found
	 */
	@Override
	public SiteFriendlyURL findByC_G_L(long companyId, long groupId,
		String languageId) throws NoSuchFriendlyURLException {
		SiteFriendlyURL siteFriendlyURL = fetchByC_G_L(companyId, groupId,
				languageId);

		if (siteFriendlyURL == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append(", languageId=");
			msg.append(languageId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchFriendlyURLException(msg.toString());
		}

		return siteFriendlyURL;
	}

	/**
	 * Returns the site friendly url where companyId = &#63; and groupId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param languageId the language ID
	 * @return the matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	@Override
	public SiteFriendlyURL fetchByC_G_L(long companyId, long groupId,
		String languageId) {
		return fetchByC_G_L(companyId, groupId, languageId, true);
	}

	/**
	 * Returns the site friendly url where companyId = &#63; and groupId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param languageId the language ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	@Override
	public SiteFriendlyURL fetchByC_G_L(long companyId, long groupId,
		String languageId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { companyId, groupId, languageId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_G_L,
					finderArgs, this);
		}

		if (result instanceof SiteFriendlyURL) {
			SiteFriendlyURL siteFriendlyURL = (SiteFriendlyURL)result;

			if ((companyId != siteFriendlyURL.getCompanyId()) ||
					(groupId != siteFriendlyURL.getGroupId()) ||
					!Objects.equals(languageId, siteFriendlyURL.getLanguageId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_SITEFRIENDLYURL_WHERE);

			query.append(_FINDER_COLUMN_C_G_L_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_G_L_GROUPID_2);

			boolean bindLanguageId = false;

			if (languageId == null) {
				query.append(_FINDER_COLUMN_C_G_L_LANGUAGEID_1);
			}
			else if (languageId.equals("")) {
				query.append(_FINDER_COLUMN_C_G_L_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(_FINDER_COLUMN_C_G_L_LANGUAGEID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(groupId);

				if (bindLanguageId) {
					qPos.add(languageId);
				}

				List<SiteFriendlyURL> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_G_L,
						finderArgs, list);
				}
				else {
					SiteFriendlyURL siteFriendlyURL = list.get(0);

					result = siteFriendlyURL;

					cacheResult(siteFriendlyURL);

					if ((siteFriendlyURL.getCompanyId() != companyId) ||
							(siteFriendlyURL.getGroupId() != groupId) ||
							(siteFriendlyURL.getLanguageId() == null) ||
							!siteFriendlyURL.getLanguageId().equals(languageId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_C_G_L,
							finderArgs, siteFriendlyURL);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C_G_L, finderArgs);

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
			return (SiteFriendlyURL)result;
		}
	}

	/**
	 * Removes the site friendly url where companyId = &#63; and groupId = &#63; and languageId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param languageId the language ID
	 * @return the site friendly url that was removed
	 */
	@Override
	public SiteFriendlyURL removeByC_G_L(long companyId, long groupId,
		String languageId) throws NoSuchFriendlyURLException {
		SiteFriendlyURL siteFriendlyURL = findByC_G_L(companyId, groupId,
				languageId);

		return remove(siteFriendlyURL);
	}

	/**
	 * Returns the number of site friendly urls where companyId = &#63; and groupId = &#63; and languageId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param languageId the language ID
	 * @return the number of matching site friendly urls
	 */
	@Override
	public int countByC_G_L(long companyId, long groupId, String languageId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_G_L;

		Object[] finderArgs = new Object[] { companyId, groupId, languageId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_SITEFRIENDLYURL_WHERE);

			query.append(_FINDER_COLUMN_C_G_L_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_G_L_GROUPID_2);

			boolean bindLanguageId = false;

			if (languageId == null) {
				query.append(_FINDER_COLUMN_C_G_L_LANGUAGEID_1);
			}
			else if (languageId.equals("")) {
				query.append(_FINDER_COLUMN_C_G_L_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(_FINDER_COLUMN_C_G_L_LANGUAGEID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(groupId);

				if (bindLanguageId) {
					qPos.add(languageId);
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

	private static final String _FINDER_COLUMN_C_G_L_COMPANYID_2 = "siteFriendlyURL.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_G_L_GROUPID_2 = "siteFriendlyURL.groupId = ? AND ";
	private static final String _FINDER_COLUMN_C_G_L_LANGUAGEID_1 = "siteFriendlyURL.languageId IS NULL";
	private static final String _FINDER_COLUMN_C_G_L_LANGUAGEID_2 = "siteFriendlyURL.languageId = ?";
	private static final String _FINDER_COLUMN_C_G_L_LANGUAGEID_3 = "(siteFriendlyURL.languageId IS NULL OR siteFriendlyURL.languageId = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_F_L = new FinderPath(SiteFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			SiteFriendlyURLModelImpl.FINDER_CACHE_ENABLED,
			SiteFriendlyURLImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_F_L",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			SiteFriendlyURLModelImpl.COMPANYID_COLUMN_BITMASK |
			SiteFriendlyURLModelImpl.FRIENDLYURL_COLUMN_BITMASK |
			SiteFriendlyURLModelImpl.LANGUAGEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_F_L = new FinderPath(SiteFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			SiteFriendlyURLModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_F_L",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns the site friendly url where companyId = &#63; and friendlyURL = &#63; and languageId = &#63; or throws a {@link NoSuchFriendlyURLException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @param languageId the language ID
	 * @return the matching site friendly url
	 * @throws NoSuchFriendlyURLException if a matching site friendly url could not be found
	 */
	@Override
	public SiteFriendlyURL findByC_F_L(long companyId, String friendlyURL,
		String languageId) throws NoSuchFriendlyURLException {
		SiteFriendlyURL siteFriendlyURL = fetchByC_F_L(companyId, friendlyURL,
				languageId);

		if (siteFriendlyURL == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", friendlyURL=");
			msg.append(friendlyURL);

			msg.append(", languageId=");
			msg.append(languageId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchFriendlyURLException(msg.toString());
		}

		return siteFriendlyURL;
	}

	/**
	 * Returns the site friendly url where companyId = &#63; and friendlyURL = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @param languageId the language ID
	 * @return the matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	@Override
	public SiteFriendlyURL fetchByC_F_L(long companyId, String friendlyURL,
		String languageId) {
		return fetchByC_F_L(companyId, friendlyURL, languageId, true);
	}

	/**
	 * Returns the site friendly url where companyId = &#63; and friendlyURL = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @param languageId the language ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	@Override
	public SiteFriendlyURL fetchByC_F_L(long companyId, String friendlyURL,
		String languageId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { companyId, friendlyURL, languageId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_F_L,
					finderArgs, this);
		}

		if (result instanceof SiteFriendlyURL) {
			SiteFriendlyURL siteFriendlyURL = (SiteFriendlyURL)result;

			if ((companyId != siteFriendlyURL.getCompanyId()) ||
					!Objects.equals(friendlyURL,
						siteFriendlyURL.getFriendlyURL()) ||
					!Objects.equals(languageId, siteFriendlyURL.getLanguageId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_SITEFRIENDLYURL_WHERE);

			query.append(_FINDER_COLUMN_C_F_L_COMPANYID_2);

			boolean bindFriendlyURL = false;

			if (friendlyURL == null) {
				query.append(_FINDER_COLUMN_C_F_L_FRIENDLYURL_1);
			}
			else if (friendlyURL.equals("")) {
				query.append(_FINDER_COLUMN_C_F_L_FRIENDLYURL_3);
			}
			else {
				bindFriendlyURL = true;

				query.append(_FINDER_COLUMN_C_F_L_FRIENDLYURL_2);
			}

			boolean bindLanguageId = false;

			if (languageId == null) {
				query.append(_FINDER_COLUMN_C_F_L_LANGUAGEID_1);
			}
			else if (languageId.equals("")) {
				query.append(_FINDER_COLUMN_C_F_L_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(_FINDER_COLUMN_C_F_L_LANGUAGEID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindFriendlyURL) {
					qPos.add(friendlyURL);
				}

				if (bindLanguageId) {
					qPos.add(languageId);
				}

				List<SiteFriendlyURL> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_F_L,
						finderArgs, list);
				}
				else {
					SiteFriendlyURL siteFriendlyURL = list.get(0);

					result = siteFriendlyURL;

					cacheResult(siteFriendlyURL);

					if ((siteFriendlyURL.getCompanyId() != companyId) ||
							(siteFriendlyURL.getFriendlyURL() == null) ||
							!siteFriendlyURL.getFriendlyURL().equals(friendlyURL) ||
							(siteFriendlyURL.getLanguageId() == null) ||
							!siteFriendlyURL.getLanguageId().equals(languageId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_C_F_L,
							finderArgs, siteFriendlyURL);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C_F_L, finderArgs);

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
			return (SiteFriendlyURL)result;
		}
	}

	/**
	 * Removes the site friendly url where companyId = &#63; and friendlyURL = &#63; and languageId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @param languageId the language ID
	 * @return the site friendly url that was removed
	 */
	@Override
	public SiteFriendlyURL removeByC_F_L(long companyId, String friendlyURL,
		String languageId) throws NoSuchFriendlyURLException {
		SiteFriendlyURL siteFriendlyURL = findByC_F_L(companyId, friendlyURL,
				languageId);

		return remove(siteFriendlyURL);
	}

	/**
	 * Returns the number of site friendly urls where companyId = &#63; and friendlyURL = &#63; and languageId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @param languageId the language ID
	 * @return the number of matching site friendly urls
	 */
	@Override
	public int countByC_F_L(long companyId, String friendlyURL,
		String languageId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_F_L;

		Object[] finderArgs = new Object[] { companyId, friendlyURL, languageId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_SITEFRIENDLYURL_WHERE);

			query.append(_FINDER_COLUMN_C_F_L_COMPANYID_2);

			boolean bindFriendlyURL = false;

			if (friendlyURL == null) {
				query.append(_FINDER_COLUMN_C_F_L_FRIENDLYURL_1);
			}
			else if (friendlyURL.equals("")) {
				query.append(_FINDER_COLUMN_C_F_L_FRIENDLYURL_3);
			}
			else {
				bindFriendlyURL = true;

				query.append(_FINDER_COLUMN_C_F_L_FRIENDLYURL_2);
			}

			boolean bindLanguageId = false;

			if (languageId == null) {
				query.append(_FINDER_COLUMN_C_F_L_LANGUAGEID_1);
			}
			else if (languageId.equals("")) {
				query.append(_FINDER_COLUMN_C_F_L_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(_FINDER_COLUMN_C_F_L_LANGUAGEID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindFriendlyURL) {
					qPos.add(friendlyURL);
				}

				if (bindLanguageId) {
					qPos.add(languageId);
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

	private static final String _FINDER_COLUMN_C_F_L_COMPANYID_2 = "siteFriendlyURL.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_F_L_FRIENDLYURL_1 = "siteFriendlyURL.friendlyURL IS NULL AND ";
	private static final String _FINDER_COLUMN_C_F_L_FRIENDLYURL_2 = "siteFriendlyURL.friendlyURL = ? AND ";
	private static final String _FINDER_COLUMN_C_F_L_FRIENDLYURL_3 = "(siteFriendlyURL.friendlyURL IS NULL OR siteFriendlyURL.friendlyURL = '') AND ";
	private static final String _FINDER_COLUMN_C_F_L_LANGUAGEID_1 = "siteFriendlyURL.languageId IS NULL";
	private static final String _FINDER_COLUMN_C_F_L_LANGUAGEID_2 = "siteFriendlyURL.languageId = ?";
	private static final String _FINDER_COLUMN_C_F_L_LANGUAGEID_3 = "(siteFriendlyURL.languageId IS NULL OR siteFriendlyURL.languageId = '')";

	public SiteFriendlyURLPersistenceImpl() {
		setModelClass(SiteFriendlyURL.class);

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
	 * Caches the site friendly url in the entity cache if it is enabled.
	 *
	 * @param siteFriendlyURL the site friendly url
	 */
	@Override
	public void cacheResult(SiteFriendlyURL siteFriendlyURL) {
		entityCache.putResult(SiteFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			SiteFriendlyURLImpl.class, siteFriendlyURL.getPrimaryKey(),
			siteFriendlyURL);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { siteFriendlyURL.getUuid(), siteFriendlyURL.getGroupId() },
			siteFriendlyURL);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_F,
			new Object[] {
				siteFriendlyURL.getCompanyId(), siteFriendlyURL.getFriendlyURL()
			}, siteFriendlyURL);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_G_L,
			new Object[] {
				siteFriendlyURL.getCompanyId(), siteFriendlyURL.getGroupId(),
				siteFriendlyURL.getLanguageId()
			}, siteFriendlyURL);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_F_L,
			new Object[] {
				siteFriendlyURL.getCompanyId(), siteFriendlyURL.getFriendlyURL(),
				siteFriendlyURL.getLanguageId()
			}, siteFriendlyURL);

		siteFriendlyURL.resetOriginalValues();
	}

	/**
	 * Caches the site friendly urls in the entity cache if it is enabled.
	 *
	 * @param siteFriendlyURLs the site friendly urls
	 */
	@Override
	public void cacheResult(List<SiteFriendlyURL> siteFriendlyURLs) {
		for (SiteFriendlyURL siteFriendlyURL : siteFriendlyURLs) {
			if (entityCache.getResult(
						SiteFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
						SiteFriendlyURLImpl.class,
						siteFriendlyURL.getPrimaryKey()) == null) {
				cacheResult(siteFriendlyURL);
			}
			else {
				siteFriendlyURL.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all site friendly urls.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(SiteFriendlyURLImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the site friendly url.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SiteFriendlyURL siteFriendlyURL) {
		entityCache.removeResult(SiteFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			SiteFriendlyURLImpl.class, siteFriendlyURL.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((SiteFriendlyURLModelImpl)siteFriendlyURL, true);
	}

	@Override
	public void clearCache(List<SiteFriendlyURL> siteFriendlyURLs) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SiteFriendlyURL siteFriendlyURL : siteFriendlyURLs) {
			entityCache.removeResult(SiteFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
				SiteFriendlyURLImpl.class, siteFriendlyURL.getPrimaryKey());

			clearUniqueFindersCache((SiteFriendlyURLModelImpl)siteFriendlyURL,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		SiteFriendlyURLModelImpl siteFriendlyURLModelImpl) {
		Object[] args = new Object[] {
				siteFriendlyURLModelImpl.getUuid(),
				siteFriendlyURLModelImpl.getGroupId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
			siteFriendlyURLModelImpl, false);

		args = new Object[] {
				siteFriendlyURLModelImpl.getCompanyId(),
				siteFriendlyURLModelImpl.getFriendlyURL()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_C_F, args, Long.valueOf(1),
			false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_C_F, args,
			siteFriendlyURLModelImpl, false);

		args = new Object[] {
				siteFriendlyURLModelImpl.getCompanyId(),
				siteFriendlyURLModelImpl.getGroupId(),
				siteFriendlyURLModelImpl.getLanguageId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_C_G_L, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_C_G_L, args,
			siteFriendlyURLModelImpl, false);

		args = new Object[] {
				siteFriendlyURLModelImpl.getCompanyId(),
				siteFriendlyURLModelImpl.getFriendlyURL(),
				siteFriendlyURLModelImpl.getLanguageId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_C_F_L, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_C_F_L, args,
			siteFriendlyURLModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		SiteFriendlyURLModelImpl siteFriendlyURLModelImpl, boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					siteFriendlyURLModelImpl.getUuid(),
					siteFriendlyURLModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if ((siteFriendlyURLModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					siteFriendlyURLModelImpl.getOriginalUuid(),
					siteFriendlyURLModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					siteFriendlyURLModelImpl.getCompanyId(),
					siteFriendlyURLModelImpl.getFriendlyURL()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_F, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_F, args);
		}

		if ((siteFriendlyURLModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_F.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					siteFriendlyURLModelImpl.getOriginalCompanyId(),
					siteFriendlyURLModelImpl.getOriginalFriendlyURL()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_F, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_F, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					siteFriendlyURLModelImpl.getCompanyId(),
					siteFriendlyURLModelImpl.getGroupId(),
					siteFriendlyURLModelImpl.getLanguageId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_G_L, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_G_L, args);
		}

		if ((siteFriendlyURLModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_G_L.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					siteFriendlyURLModelImpl.getOriginalCompanyId(),
					siteFriendlyURLModelImpl.getOriginalGroupId(),
					siteFriendlyURLModelImpl.getOriginalLanguageId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_G_L, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_G_L, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					siteFriendlyURLModelImpl.getCompanyId(),
					siteFriendlyURLModelImpl.getFriendlyURL(),
					siteFriendlyURLModelImpl.getLanguageId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_F_L, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_F_L, args);
		}

		if ((siteFriendlyURLModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_F_L.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					siteFriendlyURLModelImpl.getOriginalCompanyId(),
					siteFriendlyURLModelImpl.getOriginalFriendlyURL(),
					siteFriendlyURLModelImpl.getOriginalLanguageId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_F_L, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_F_L, args);
		}
	}

	/**
	 * Creates a new site friendly url with the primary key. Does not add the site friendly url to the database.
	 *
	 * @param siteFriendlyURLId the primary key for the new site friendly url
	 * @return the new site friendly url
	 */
	@Override
	public SiteFriendlyURL create(long siteFriendlyURLId) {
		SiteFriendlyURL siteFriendlyURL = new SiteFriendlyURLImpl();

		siteFriendlyURL.setNew(true);
		siteFriendlyURL.setPrimaryKey(siteFriendlyURLId);

		String uuid = PortalUUIDUtil.generate();

		siteFriendlyURL.setUuid(uuid);

		siteFriendlyURL.setCompanyId(companyProvider.getCompanyId());

		return siteFriendlyURL;
	}

	/**
	 * Removes the site friendly url with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param siteFriendlyURLId the primary key of the site friendly url
	 * @return the site friendly url that was removed
	 * @throws NoSuchFriendlyURLException if a site friendly url with the primary key could not be found
	 */
	@Override
	public SiteFriendlyURL remove(long siteFriendlyURLId)
		throws NoSuchFriendlyURLException {
		return remove((Serializable)siteFriendlyURLId);
	}

	/**
	 * Removes the site friendly url with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the site friendly url
	 * @return the site friendly url that was removed
	 * @throws NoSuchFriendlyURLException if a site friendly url with the primary key could not be found
	 */
	@Override
	public SiteFriendlyURL remove(Serializable primaryKey)
		throws NoSuchFriendlyURLException {
		Session session = null;

		try {
			session = openSession();

			SiteFriendlyURL siteFriendlyURL = (SiteFriendlyURL)session.get(SiteFriendlyURLImpl.class,
					primaryKey);

			if (siteFriendlyURL == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFriendlyURLException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(siteFriendlyURL);
		}
		catch (NoSuchFriendlyURLException nsee) {
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
	protected SiteFriendlyURL removeImpl(SiteFriendlyURL siteFriendlyURL) {
		siteFriendlyURL = toUnwrappedModel(siteFriendlyURL);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(siteFriendlyURL)) {
				siteFriendlyURL = (SiteFriendlyURL)session.get(SiteFriendlyURLImpl.class,
						siteFriendlyURL.getPrimaryKeyObj());
			}

			if (siteFriendlyURL != null) {
				session.delete(siteFriendlyURL);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (siteFriendlyURL != null) {
			clearCache(siteFriendlyURL);
		}

		return siteFriendlyURL;
	}

	@Override
	public SiteFriendlyURL updateImpl(SiteFriendlyURL siteFriendlyURL) {
		siteFriendlyURL = toUnwrappedModel(siteFriendlyURL);

		boolean isNew = siteFriendlyURL.isNew();

		SiteFriendlyURLModelImpl siteFriendlyURLModelImpl = (SiteFriendlyURLModelImpl)siteFriendlyURL;

		if (Validator.isNull(siteFriendlyURL.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			siteFriendlyURL.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (siteFriendlyURL.getCreateDate() == null)) {
			if (serviceContext == null) {
				siteFriendlyURL.setCreateDate(now);
			}
			else {
				siteFriendlyURL.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!siteFriendlyURLModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				siteFriendlyURL.setModifiedDate(now);
			}
			else {
				siteFriendlyURL.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (siteFriendlyURL.isNew()) {
				session.save(siteFriendlyURL);

				siteFriendlyURL.setNew(false);
			}
			else {
				siteFriendlyURL = (SiteFriendlyURL)session.merge(siteFriendlyURL);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!SiteFriendlyURLModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] { siteFriendlyURLModelImpl.getUuid() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
				args);

			args = new Object[] {
					siteFriendlyURLModelImpl.getUuid(),
					siteFriendlyURLModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
				args);

			args = new Object[] {
					siteFriendlyURLModelImpl.getCompanyId(),
					siteFriendlyURLModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_G, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_G,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((siteFriendlyURLModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						siteFriendlyURLModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { siteFriendlyURLModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((siteFriendlyURLModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						siteFriendlyURLModelImpl.getOriginalUuid(),
						siteFriendlyURLModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						siteFriendlyURLModelImpl.getUuid(),
						siteFriendlyURLModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((siteFriendlyURLModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						siteFriendlyURLModelImpl.getOriginalCompanyId(),
						siteFriendlyURLModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_G, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_G,
					args);

				args = new Object[] {
						siteFriendlyURLModelImpl.getCompanyId(),
						siteFriendlyURLModelImpl.getGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_G, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_G,
					args);
			}
		}

		entityCache.putResult(SiteFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			SiteFriendlyURLImpl.class, siteFriendlyURL.getPrimaryKey(),
			siteFriendlyURL, false);

		clearUniqueFindersCache(siteFriendlyURLModelImpl, false);
		cacheUniqueFindersCache(siteFriendlyURLModelImpl);

		siteFriendlyURL.resetOriginalValues();

		return siteFriendlyURL;
	}

	protected SiteFriendlyURL toUnwrappedModel(SiteFriendlyURL siteFriendlyURL) {
		if (siteFriendlyURL instanceof SiteFriendlyURLImpl) {
			return siteFriendlyURL;
		}

		SiteFriendlyURLImpl siteFriendlyURLImpl = new SiteFriendlyURLImpl();

		siteFriendlyURLImpl.setNew(siteFriendlyURL.isNew());
		siteFriendlyURLImpl.setPrimaryKey(siteFriendlyURL.getPrimaryKey());

		siteFriendlyURLImpl.setUuid(siteFriendlyURL.getUuid());
		siteFriendlyURLImpl.setSiteFriendlyURLId(siteFriendlyURL.getSiteFriendlyURLId());
		siteFriendlyURLImpl.setCompanyId(siteFriendlyURL.getCompanyId());
		siteFriendlyURLImpl.setUserId(siteFriendlyURL.getUserId());
		siteFriendlyURLImpl.setUserName(siteFriendlyURL.getUserName());
		siteFriendlyURLImpl.setCreateDate(siteFriendlyURL.getCreateDate());
		siteFriendlyURLImpl.setModifiedDate(siteFriendlyURL.getModifiedDate());
		siteFriendlyURLImpl.setGroupId(siteFriendlyURL.getGroupId());
		siteFriendlyURLImpl.setFriendlyURL(siteFriendlyURL.getFriendlyURL());
		siteFriendlyURLImpl.setLanguageId(siteFriendlyURL.getLanguageId());
		siteFriendlyURLImpl.setLastPublishDate(siteFriendlyURL.getLastPublishDate());

		return siteFriendlyURLImpl;
	}

	/**
	 * Returns the site friendly url with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the site friendly url
	 * @return the site friendly url
	 * @throws NoSuchFriendlyURLException if a site friendly url with the primary key could not be found
	 */
	@Override
	public SiteFriendlyURL findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFriendlyURLException {
		SiteFriendlyURL siteFriendlyURL = fetchByPrimaryKey(primaryKey);

		if (siteFriendlyURL == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFriendlyURLException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return siteFriendlyURL;
	}

	/**
	 * Returns the site friendly url with the primary key or throws a {@link NoSuchFriendlyURLException} if it could not be found.
	 *
	 * @param siteFriendlyURLId the primary key of the site friendly url
	 * @return the site friendly url
	 * @throws NoSuchFriendlyURLException if a site friendly url with the primary key could not be found
	 */
	@Override
	public SiteFriendlyURL findByPrimaryKey(long siteFriendlyURLId)
		throws NoSuchFriendlyURLException {
		return findByPrimaryKey((Serializable)siteFriendlyURLId);
	}

	/**
	 * Returns the site friendly url with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the site friendly url
	 * @return the site friendly url, or <code>null</code> if a site friendly url with the primary key could not be found
	 */
	@Override
	public SiteFriendlyURL fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(SiteFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
				SiteFriendlyURLImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		SiteFriendlyURL siteFriendlyURL = (SiteFriendlyURL)serializable;

		if (siteFriendlyURL == null) {
			Session session = null;

			try {
				session = openSession();

				siteFriendlyURL = (SiteFriendlyURL)session.get(SiteFriendlyURLImpl.class,
						primaryKey);

				if (siteFriendlyURL != null) {
					cacheResult(siteFriendlyURL);
				}
				else {
					entityCache.putResult(SiteFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
						SiteFriendlyURLImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(SiteFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
					SiteFriendlyURLImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return siteFriendlyURL;
	}

	/**
	 * Returns the site friendly url with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param siteFriendlyURLId the primary key of the site friendly url
	 * @return the site friendly url, or <code>null</code> if a site friendly url with the primary key could not be found
	 */
	@Override
	public SiteFriendlyURL fetchByPrimaryKey(long siteFriendlyURLId) {
		return fetchByPrimaryKey((Serializable)siteFriendlyURLId);
	}

	@Override
	public Map<Serializable, SiteFriendlyURL> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, SiteFriendlyURL> map = new HashMap<Serializable, SiteFriendlyURL>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			SiteFriendlyURL siteFriendlyURL = fetchByPrimaryKey(primaryKey);

			if (siteFriendlyURL != null) {
				map.put(primaryKey, siteFriendlyURL);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(SiteFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
					SiteFriendlyURLImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (SiteFriendlyURL)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_SITEFRIENDLYURL_WHERE_PKS_IN);

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

			for (SiteFriendlyURL siteFriendlyURL : (List<SiteFriendlyURL>)q.list()) {
				map.put(siteFriendlyURL.getPrimaryKeyObj(), siteFriendlyURL);

				cacheResult(siteFriendlyURL);

				uncachedPrimaryKeys.remove(siteFriendlyURL.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(SiteFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
					SiteFriendlyURLImpl.class, primaryKey, nullModel);
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
	 * Returns all the site friendly urls.
	 *
	 * @return the site friendly urls
	 */
	@Override
	public List<SiteFriendlyURL> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the site friendly urls.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @return the range of site friendly urls
	 */
	@Override
	public List<SiteFriendlyURL> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the site friendly urls.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of site friendly urls
	 */
	@Override
	public List<SiteFriendlyURL> findAll(int start, int end,
		OrderByComparator<SiteFriendlyURL> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the site friendly urls.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of site friendly urls
	 */
	@Override
	public List<SiteFriendlyURL> findAll(int start, int end,
		OrderByComparator<SiteFriendlyURL> orderByComparator,
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

		List<SiteFriendlyURL> list = null;

		if (retrieveFromCache) {
			list = (List<SiteFriendlyURL>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_SITEFRIENDLYURL);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SITEFRIENDLYURL;

				if (pagination) {
					sql = sql.concat(SiteFriendlyURLModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<SiteFriendlyURL>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SiteFriendlyURL>)QueryUtil.list(q,
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
	 * Removes all the site friendly urls from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SiteFriendlyURL siteFriendlyURL : findAll()) {
			remove(siteFriendlyURL);
		}
	}

	/**
	 * Returns the number of site friendly urls.
	 *
	 * @return the number of site friendly urls
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SITEFRIENDLYURL);

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
		return SiteFriendlyURLModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the site friendly url persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(SiteFriendlyURLImpl.class.getName());
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
	private static final String _SQL_SELECT_SITEFRIENDLYURL = "SELECT siteFriendlyURL FROM SiteFriendlyURL siteFriendlyURL";
	private static final String _SQL_SELECT_SITEFRIENDLYURL_WHERE_PKS_IN = "SELECT siteFriendlyURL FROM SiteFriendlyURL siteFriendlyURL WHERE siteFriendlyURLId IN (";
	private static final String _SQL_SELECT_SITEFRIENDLYURL_WHERE = "SELECT siteFriendlyURL FROM SiteFriendlyURL siteFriendlyURL WHERE ";
	private static final String _SQL_COUNT_SITEFRIENDLYURL = "SELECT COUNT(siteFriendlyURL) FROM SiteFriendlyURL siteFriendlyURL";
	private static final String _SQL_COUNT_SITEFRIENDLYURL_WHERE = "SELECT COUNT(siteFriendlyURL) FROM SiteFriendlyURL siteFriendlyURL WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "siteFriendlyURL.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SiteFriendlyURL exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SiteFriendlyURL exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(SiteFriendlyURLPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid"
			});
}