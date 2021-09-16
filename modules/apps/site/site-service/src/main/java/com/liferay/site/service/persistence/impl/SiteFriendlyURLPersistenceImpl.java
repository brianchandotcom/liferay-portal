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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.site.exception.NoSuchFriendlyURLException;
import com.liferay.site.model.SiteFriendlyURL;
import com.liferay.site.model.SiteFriendlyURLTable;
import com.liferay.site.model.impl.SiteFriendlyURLImpl;
import com.liferay.site.model.impl.SiteFriendlyURLModelImpl;
import com.liferay.site.service.persistence.SiteFriendlyURLPersistence;
import com.liferay.site.service.persistence.impl.constants.SitePersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the site friendly url service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = {SiteFriendlyURLPersistence.class, BasePersistence.class})
public class SiteFriendlyURLPersistenceImpl
	extends BasePersistenceImpl<SiteFriendlyURL>
	implements SiteFriendlyURLPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>SiteFriendlyURLUtil</code> to access the site friendly url persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		SiteFriendlyURLImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteFriendlyURLModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteFriendlyURLModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site friendly urls
	 */
	@Override
	public List<SiteFriendlyURL> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<SiteFriendlyURL> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the site friendly urls where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteFriendlyURLModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching site friendly urls
	 */
	@Override
	public List<SiteFriendlyURL> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<SiteFriendlyURL> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<SiteFriendlyURL> list = null;

		if (useFinderCache) {
			list = (List<SiteFriendlyURL>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (SiteFriendlyURL siteFriendlyURL : list) {
					if (!uuid.equals(siteFriendlyURL.getUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_SITEFRIENDLYURL_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SiteFriendlyURLModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				list = (List<SiteFriendlyURL>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
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
	public SiteFriendlyURL findByUuid_First(
			String uuid, OrderByComparator<SiteFriendlyURL> orderByComparator)
		throws NoSuchFriendlyURLException {

		SiteFriendlyURL siteFriendlyURL = fetchByUuid_First(
			uuid, orderByComparator);

		if (siteFriendlyURL != null) {
			return siteFriendlyURL;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchFriendlyURLException(sb.toString());
	}

	/**
	 * Returns the first site friendly url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	@Override
	public SiteFriendlyURL fetchByUuid_First(
		String uuid, OrderByComparator<SiteFriendlyURL> orderByComparator) {

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
	public SiteFriendlyURL findByUuid_Last(
			String uuid, OrderByComparator<SiteFriendlyURL> orderByComparator)
		throws NoSuchFriendlyURLException {

		SiteFriendlyURL siteFriendlyURL = fetchByUuid_Last(
			uuid, orderByComparator);

		if (siteFriendlyURL != null) {
			return siteFriendlyURL;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchFriendlyURLException(sb.toString());
	}

	/**
	 * Returns the last site friendly url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	@Override
	public SiteFriendlyURL fetchByUuid_Last(
		String uuid, OrderByComparator<SiteFriendlyURL> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<SiteFriendlyURL> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

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
	public SiteFriendlyURL[] findByUuid_PrevAndNext(
			long siteFriendlyURLId, String uuid,
			OrderByComparator<SiteFriendlyURL> orderByComparator)
		throws NoSuchFriendlyURLException {

		uuid = Objects.toString(uuid, "");

		SiteFriendlyURL siteFriendlyURL = findByPrimaryKey(siteFriendlyURLId);

		Session session = null;

		try {
			session = openSession();

			SiteFriendlyURL[] array = new SiteFriendlyURLImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, siteFriendlyURL, uuid, orderByComparator, true);

			array[1] = siteFriendlyURL;

			array[2] = getByUuid_PrevAndNext(
				session, siteFriendlyURL, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected SiteFriendlyURL getByUuid_PrevAndNext(
		Session session, SiteFriendlyURL siteFriendlyURL, String uuid,
		OrderByComparator<SiteFriendlyURL> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_SITEFRIENDLYURL_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(SiteFriendlyURLModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						siteFriendlyURL)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SiteFriendlyURL> list = query.list();

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
		for (SiteFriendlyURL siteFriendlyURL :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

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
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_SITEFRIENDLYURL_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"siteFriendlyURL.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(siteFriendlyURL.uuid IS NULL OR siteFriendlyURL.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the site friendly url where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchFriendlyURLException</code> if it could not be found.
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
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("uuid=");
			sb.append(uuid);

			sb.append(", groupId=");
			sb.append(groupId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchFriendlyURLException(sb.toString());
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
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	@Override
	public SiteFriendlyURL fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs);
		}

		if (result instanceof SiteFriendlyURL) {
			SiteFriendlyURL siteFriendlyURL = (SiteFriendlyURL)result;

			if (!Objects.equals(uuid, siteFriendlyURL.getUuid()) ||
				(groupId != siteFriendlyURL.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_SITEFRIENDLYURL_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(groupId);

				List<SiteFriendlyURL> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					SiteFriendlyURL siteFriendlyURL = list.get(0);

					result = siteFriendlyURL;

					cacheResult(siteFriendlyURL);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
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
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_SITEFRIENDLYURL_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(groupId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_G_UUID_2 =
		"siteFriendlyURL.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(siteFriendlyURL.uuid IS NULL OR siteFriendlyURL.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"siteFriendlyURL.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the site friendly urls where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching site friendly urls
	 */
	@Override
	public List<SiteFriendlyURL> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the site friendly urls where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteFriendlyURLModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @return the range of matching site friendly urls
	 */
	@Override
	public List<SiteFriendlyURL> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the site friendly urls where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteFriendlyURLModelImpl</code>.
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
	public List<SiteFriendlyURL> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<SiteFriendlyURL> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the site friendly urls where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteFriendlyURLModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching site friendly urls
	 */
	@Override
	public List<SiteFriendlyURL> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<SiteFriendlyURL> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<SiteFriendlyURL> list = null;

		if (useFinderCache) {
			list = (List<SiteFriendlyURL>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (SiteFriendlyURL siteFriendlyURL : list) {
					if (!uuid.equals(siteFriendlyURL.getUuid()) ||
						(companyId != siteFriendlyURL.getCompanyId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_SITEFRIENDLYURL_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SiteFriendlyURLModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				list = (List<SiteFriendlyURL>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
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
	public SiteFriendlyURL findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<SiteFriendlyURL> orderByComparator)
		throws NoSuchFriendlyURLException {

		SiteFriendlyURL siteFriendlyURL = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (siteFriendlyURL != null) {
			return siteFriendlyURL;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchFriendlyURLException(sb.toString());
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
	public SiteFriendlyURL fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<SiteFriendlyURL> orderByComparator) {

		List<SiteFriendlyURL> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

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
	public SiteFriendlyURL findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<SiteFriendlyURL> orderByComparator)
		throws NoSuchFriendlyURLException {

		SiteFriendlyURL siteFriendlyURL = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (siteFriendlyURL != null) {
			return siteFriendlyURL;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchFriendlyURLException(sb.toString());
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
	public SiteFriendlyURL fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<SiteFriendlyURL> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<SiteFriendlyURL> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

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
	public SiteFriendlyURL[] findByUuid_C_PrevAndNext(
			long siteFriendlyURLId, String uuid, long companyId,
			OrderByComparator<SiteFriendlyURL> orderByComparator)
		throws NoSuchFriendlyURLException {

		uuid = Objects.toString(uuid, "");

		SiteFriendlyURL siteFriendlyURL = findByPrimaryKey(siteFriendlyURLId);

		Session session = null;

		try {
			session = openSession();

			SiteFriendlyURL[] array = new SiteFriendlyURLImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, siteFriendlyURL, uuid, companyId, orderByComparator,
				true);

			array[1] = siteFriendlyURL;

			array[2] = getByUuid_C_PrevAndNext(
				session, siteFriendlyURL, uuid, companyId, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected SiteFriendlyURL getByUuid_C_PrevAndNext(
		Session session, SiteFriendlyURL siteFriendlyURL, String uuid,
		long companyId, OrderByComparator<SiteFriendlyURL> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_SITEFRIENDLYURL_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(SiteFriendlyURLModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						siteFriendlyURL)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SiteFriendlyURL> list = query.list();

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
		for (SiteFriendlyURL siteFriendlyURL :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

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
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_SITEFRIENDLYURL_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"siteFriendlyURL.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(siteFriendlyURL.uuid IS NULL OR siteFriendlyURL.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"siteFriendlyURL.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByG_C;
	private FinderPath _finderPathWithoutPaginationFindByG_C;
	private FinderPath _finderPathCountByG_C;

	/**
	 * Returns all the site friendly urls where groupId = &#63; and companyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @return the matching site friendly urls
	 */
	@Override
	public List<SiteFriendlyURL> findByG_C(long groupId, long companyId) {
		return findByG_C(
			groupId, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the site friendly urls where groupId = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteFriendlyURLModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @return the range of matching site friendly urls
	 */
	@Override
	public List<SiteFriendlyURL> findByG_C(
		long groupId, long companyId, int start, int end) {

		return findByG_C(groupId, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the site friendly urls where groupId = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteFriendlyURLModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site friendly urls
	 */
	@Override
	public List<SiteFriendlyURL> findByG_C(
		long groupId, long companyId, int start, int end,
		OrderByComparator<SiteFriendlyURL> orderByComparator) {

		return findByG_C(
			groupId, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the site friendly urls where groupId = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteFriendlyURLModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching site friendly urls
	 */
	@Override
	public List<SiteFriendlyURL> findByG_C(
		long groupId, long companyId, int start, int end,
		OrderByComparator<SiteFriendlyURL> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_C;
				finderArgs = new Object[] {groupId, companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_C;
			finderArgs = new Object[] {
				groupId, companyId, start, end, orderByComparator
			};
		}

		List<SiteFriendlyURL> list = null;

		if (useFinderCache) {
			list = (List<SiteFriendlyURL>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (SiteFriendlyURL siteFriendlyURL : list) {
					if ((groupId != siteFriendlyURL.getGroupId()) ||
						(companyId != siteFriendlyURL.getCompanyId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_SITEFRIENDLYURL_WHERE);

			sb.append(_FINDER_COLUMN_G_C_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SiteFriendlyURLModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(companyId);

				list = (List<SiteFriendlyURL>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first site friendly url in the ordered set where groupId = &#63; and companyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site friendly url
	 * @throws NoSuchFriendlyURLException if a matching site friendly url could not be found
	 */
	@Override
	public SiteFriendlyURL findByG_C_First(
			long groupId, long companyId,
			OrderByComparator<SiteFriendlyURL> orderByComparator)
		throws NoSuchFriendlyURLException {

		SiteFriendlyURL siteFriendlyURL = fetchByG_C_First(
			groupId, companyId, orderByComparator);

		if (siteFriendlyURL != null) {
			return siteFriendlyURL;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchFriendlyURLException(sb.toString());
	}

	/**
	 * Returns the first site friendly url in the ordered set where groupId = &#63; and companyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	@Override
	public SiteFriendlyURL fetchByG_C_First(
		long groupId, long companyId,
		OrderByComparator<SiteFriendlyURL> orderByComparator) {

		List<SiteFriendlyURL> list = findByG_C(
			groupId, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last site friendly url in the ordered set where groupId = &#63; and companyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site friendly url
	 * @throws NoSuchFriendlyURLException if a matching site friendly url could not be found
	 */
	@Override
	public SiteFriendlyURL findByG_C_Last(
			long groupId, long companyId,
			OrderByComparator<SiteFriendlyURL> orderByComparator)
		throws NoSuchFriendlyURLException {

		SiteFriendlyURL siteFriendlyURL = fetchByG_C_Last(
			groupId, companyId, orderByComparator);

		if (siteFriendlyURL != null) {
			return siteFriendlyURL;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchFriendlyURLException(sb.toString());
	}

	/**
	 * Returns the last site friendly url in the ordered set where groupId = &#63; and companyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	@Override
	public SiteFriendlyURL fetchByG_C_Last(
		long groupId, long companyId,
		OrderByComparator<SiteFriendlyURL> orderByComparator) {

		int count = countByG_C(groupId, companyId);

		if (count == 0) {
			return null;
		}

		List<SiteFriendlyURL> list = findByG_C(
			groupId, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the site friendly urls before and after the current site friendly url in the ordered set where groupId = &#63; and companyId = &#63;.
	 *
	 * @param siteFriendlyURLId the primary key of the current site friendly url
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next site friendly url
	 * @throws NoSuchFriendlyURLException if a site friendly url with the primary key could not be found
	 */
	@Override
	public SiteFriendlyURL[] findByG_C_PrevAndNext(
			long siteFriendlyURLId, long groupId, long companyId,
			OrderByComparator<SiteFriendlyURL> orderByComparator)
		throws NoSuchFriendlyURLException {

		SiteFriendlyURL siteFriendlyURL = findByPrimaryKey(siteFriendlyURLId);

		Session session = null;

		try {
			session = openSession();

			SiteFriendlyURL[] array = new SiteFriendlyURLImpl[3];

			array[0] = getByG_C_PrevAndNext(
				session, siteFriendlyURL, groupId, companyId, orderByComparator,
				true);

			array[1] = siteFriendlyURL;

			array[2] = getByG_C_PrevAndNext(
				session, siteFriendlyURL, groupId, companyId, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected SiteFriendlyURL getByG_C_PrevAndNext(
		Session session, SiteFriendlyURL siteFriendlyURL, long groupId,
		long companyId, OrderByComparator<SiteFriendlyURL> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_SITEFRIENDLYURL_WHERE);

		sb.append(_FINDER_COLUMN_G_C_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(SiteFriendlyURLModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						siteFriendlyURL)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SiteFriendlyURL> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the site friendly urls where groupId = &#63; and companyId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 */
	@Override
	public void removeByG_C(long groupId, long companyId) {
		for (SiteFriendlyURL siteFriendlyURL :
				findByG_C(
					groupId, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(siteFriendlyURL);
		}
	}

	/**
	 * Returns the number of site friendly urls where groupId = &#63; and companyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @return the number of matching site friendly urls
	 */
	@Override
	public int countByG_C(long groupId, long companyId) {
		FinderPath finderPath = _finderPathCountByG_C;

		Object[] finderArgs = new Object[] {groupId, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_SITEFRIENDLYURL_WHERE);

			sb.append(_FINDER_COLUMN_G_C_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(companyId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_G_C_GROUPID_2 =
		"siteFriendlyURL.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_COMPANYID_2 =
		"siteFriendlyURL.companyId = ?";

	private FinderPath _finderPathFetchByC_F;
	private FinderPath _finderPathCountByC_F;

	/**
	 * Returns the site friendly url where companyId = &#63; and friendlyURL = &#63; or throws a <code>NoSuchFriendlyURLException</code> if it could not be found.
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
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", friendlyURL=");
			sb.append(friendlyURL);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchFriendlyURLException(sb.toString());
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
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	@Override
	public SiteFriendlyURL fetchByC_F(
		long companyId, String friendlyURL, boolean useFinderCache) {

		friendlyURL = Objects.toString(friendlyURL, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, friendlyURL};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByC_F, finderArgs);
		}

		if (result instanceof SiteFriendlyURL) {
			SiteFriendlyURL siteFriendlyURL = (SiteFriendlyURL)result;

			if ((companyId != siteFriendlyURL.getCompanyId()) ||
				!Objects.equals(
					friendlyURL, siteFriendlyURL.getFriendlyURL())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_SITEFRIENDLYURL_WHERE);

			sb.append(_FINDER_COLUMN_C_F_COMPANYID_2);

			boolean bindFriendlyURL = false;

			if (friendlyURL.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_F_FRIENDLYURL_3);
			}
			else {
				bindFriendlyURL = true;

				sb.append(_FINDER_COLUMN_C_F_FRIENDLYURL_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindFriendlyURL) {
					queryPos.add(friendlyURL);
				}

				List<SiteFriendlyURL> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_F, finderArgs, list);
					}
				}
				else {
					SiteFriendlyURL siteFriendlyURL = list.get(0);

					result = siteFriendlyURL;

					cacheResult(siteFriendlyURL);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
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
		friendlyURL = Objects.toString(friendlyURL, "");

		FinderPath finderPath = _finderPathCountByC_F;

		Object[] finderArgs = new Object[] {companyId, friendlyURL};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_SITEFRIENDLYURL_WHERE);

			sb.append(_FINDER_COLUMN_C_F_COMPANYID_2);

			boolean bindFriendlyURL = false;

			if (friendlyURL.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_F_FRIENDLYURL_3);
			}
			else {
				bindFriendlyURL = true;

				sb.append(_FINDER_COLUMN_C_F_FRIENDLYURL_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindFriendlyURL) {
					queryPos.add(friendlyURL);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_F_COMPANYID_2 =
		"siteFriendlyURL.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_F_FRIENDLYURL_2 =
		"siteFriendlyURL.friendlyURL = ?";

	private static final String _FINDER_COLUMN_C_F_FRIENDLYURL_3 =
		"(siteFriendlyURL.friendlyURL IS NULL OR siteFriendlyURL.friendlyURL = '')";

	private FinderPath _finderPathFetchByG_C_L;
	private FinderPath _finderPathCountByG_C_L;

	/**
	 * Returns the site friendly url where groupId = &#63; and companyId = &#63; and languageId = &#63; or throws a <code>NoSuchFriendlyURLException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @return the matching site friendly url
	 * @throws NoSuchFriendlyURLException if a matching site friendly url could not be found
	 */
	@Override
	public SiteFriendlyURL findByG_C_L(
			long groupId, long companyId, String languageId)
		throws NoSuchFriendlyURLException {

		SiteFriendlyURL siteFriendlyURL = fetchByG_C_L(
			groupId, companyId, languageId);

		if (siteFriendlyURL == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", companyId=");
			sb.append(companyId);

			sb.append(", languageId=");
			sb.append(languageId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchFriendlyURLException(sb.toString());
		}

		return siteFriendlyURL;
	}

	/**
	 * Returns the site friendly url where groupId = &#63; and companyId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @return the matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	@Override
	public SiteFriendlyURL fetchByG_C_L(
		long groupId, long companyId, String languageId) {

		return fetchByG_C_L(groupId, companyId, languageId, true);
	}

	/**
	 * Returns the site friendly url where groupId = &#63; and companyId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	@Override
	public SiteFriendlyURL fetchByG_C_L(
		long groupId, long companyId, String languageId,
		boolean useFinderCache) {

		languageId = Objects.toString(languageId, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {groupId, companyId, languageId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByG_C_L, finderArgs);
		}

		if (result instanceof SiteFriendlyURL) {
			SiteFriendlyURL siteFriendlyURL = (SiteFriendlyURL)result;

			if ((groupId != siteFriendlyURL.getGroupId()) ||
				(companyId != siteFriendlyURL.getCompanyId()) ||
				!Objects.equals(languageId, siteFriendlyURL.getLanguageId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_SITEFRIENDLYURL_WHERE);

			sb.append(_FINDER_COLUMN_G_C_L_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_L_COMPANYID_2);

			boolean bindLanguageId = false;

			if (languageId.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_C_L_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				sb.append(_FINDER_COLUMN_G_C_L_LANGUAGEID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(companyId);

				if (bindLanguageId) {
					queryPos.add(languageId);
				}

				List<SiteFriendlyURL> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByG_C_L, finderArgs, list);
					}
				}
				else {
					SiteFriendlyURL siteFriendlyURL = list.get(0);

					result = siteFriendlyURL;

					cacheResult(siteFriendlyURL);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
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
	 * Removes the site friendly url where groupId = &#63; and companyId = &#63; and languageId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @return the site friendly url that was removed
	 */
	@Override
	public SiteFriendlyURL removeByG_C_L(
			long groupId, long companyId, String languageId)
		throws NoSuchFriendlyURLException {

		SiteFriendlyURL siteFriendlyURL = findByG_C_L(
			groupId, companyId, languageId);

		return remove(siteFriendlyURL);
	}

	/**
	 * Returns the number of site friendly urls where groupId = &#63; and companyId = &#63; and languageId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @return the number of matching site friendly urls
	 */
	@Override
	public int countByG_C_L(long groupId, long companyId, String languageId) {
		languageId = Objects.toString(languageId, "");

		FinderPath finderPath = _finderPathCountByG_C_L;

		Object[] finderArgs = new Object[] {groupId, companyId, languageId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_SITEFRIENDLYURL_WHERE);

			sb.append(_FINDER_COLUMN_G_C_L_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_L_COMPANYID_2);

			boolean bindLanguageId = false;

			if (languageId.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_C_L_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				sb.append(_FINDER_COLUMN_G_C_L_LANGUAGEID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(companyId);

				if (bindLanguageId) {
					queryPos.add(languageId);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_G_C_L_GROUPID_2 =
		"siteFriendlyURL.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_L_COMPANYID_2 =
		"siteFriendlyURL.companyId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_L_LANGUAGEID_2 =
		"siteFriendlyURL.languageId = ?";

	private static final String _FINDER_COLUMN_G_C_L_LANGUAGEID_3 =
		"(siteFriendlyURL.languageId IS NULL OR siteFriendlyURL.languageId = '')";

	private FinderPath _finderPathFetchByC_F_L;
	private FinderPath _finderPathCountByC_F_L;

	/**
	 * Returns the site friendly url where companyId = &#63; and friendlyURL = &#63; and languageId = &#63; or throws a <code>NoSuchFriendlyURLException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @param languageId the language ID
	 * @return the matching site friendly url
	 * @throws NoSuchFriendlyURLException if a matching site friendly url could not be found
	 */
	@Override
	public SiteFriendlyURL findByC_F_L(
			long companyId, String friendlyURL, String languageId)
		throws NoSuchFriendlyURLException {

		SiteFriendlyURL siteFriendlyURL = fetchByC_F_L(
			companyId, friendlyURL, languageId);

		if (siteFriendlyURL == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", friendlyURL=");
			sb.append(friendlyURL);

			sb.append(", languageId=");
			sb.append(languageId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchFriendlyURLException(sb.toString());
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
	public SiteFriendlyURL fetchByC_F_L(
		long companyId, String friendlyURL, String languageId) {

		return fetchByC_F_L(companyId, friendlyURL, languageId, true);
	}

	/**
	 * Returns the site friendly url where companyId = &#63; and friendlyURL = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @param languageId the language ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	 */
	@Override
	public SiteFriendlyURL fetchByC_F_L(
		long companyId, String friendlyURL, String languageId,
		boolean useFinderCache) {

		friendlyURL = Objects.toString(friendlyURL, "");
		languageId = Objects.toString(languageId, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, friendlyURL, languageId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByC_F_L, finderArgs);
		}

		if (result instanceof SiteFriendlyURL) {
			SiteFriendlyURL siteFriendlyURL = (SiteFriendlyURL)result;

			if ((companyId != siteFriendlyURL.getCompanyId()) ||
				!Objects.equals(
					friendlyURL, siteFriendlyURL.getFriendlyURL()) ||
				!Objects.equals(languageId, siteFriendlyURL.getLanguageId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_SITEFRIENDLYURL_WHERE);

			sb.append(_FINDER_COLUMN_C_F_L_COMPANYID_2);

			boolean bindFriendlyURL = false;

			if (friendlyURL.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_F_L_FRIENDLYURL_3);
			}
			else {
				bindFriendlyURL = true;

				sb.append(_FINDER_COLUMN_C_F_L_FRIENDLYURL_2);
			}

			boolean bindLanguageId = false;

			if (languageId.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_F_L_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				sb.append(_FINDER_COLUMN_C_F_L_LANGUAGEID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindFriendlyURL) {
					queryPos.add(friendlyURL);
				}

				if (bindLanguageId) {
					queryPos.add(languageId);
				}

				List<SiteFriendlyURL> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_F_L, finderArgs, list);
					}
				}
				else {
					SiteFriendlyURL siteFriendlyURL = list.get(0);

					result = siteFriendlyURL;

					cacheResult(siteFriendlyURL);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
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
	public SiteFriendlyURL removeByC_F_L(
			long companyId, String friendlyURL, String languageId)
		throws NoSuchFriendlyURLException {

		SiteFriendlyURL siteFriendlyURL = findByC_F_L(
			companyId, friendlyURL, languageId);

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
	public int countByC_F_L(
		long companyId, String friendlyURL, String languageId) {

		friendlyURL = Objects.toString(friendlyURL, "");
		languageId = Objects.toString(languageId, "");

		FinderPath finderPath = _finderPathCountByC_F_L;

		Object[] finderArgs = new Object[] {companyId, friendlyURL, languageId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_SITEFRIENDLYURL_WHERE);

			sb.append(_FINDER_COLUMN_C_F_L_COMPANYID_2);

			boolean bindFriendlyURL = false;

			if (friendlyURL.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_F_L_FRIENDLYURL_3);
			}
			else {
				bindFriendlyURL = true;

				sb.append(_FINDER_COLUMN_C_F_L_FRIENDLYURL_2);
			}

			boolean bindLanguageId = false;

			if (languageId.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_F_L_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				sb.append(_FINDER_COLUMN_C_F_L_LANGUAGEID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindFriendlyURL) {
					queryPos.add(friendlyURL);
				}

				if (bindLanguageId) {
					queryPos.add(languageId);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_F_L_COMPANYID_2 =
		"siteFriendlyURL.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_F_L_FRIENDLYURL_2 =
		"siteFriendlyURL.friendlyURL = ? AND ";

	private static final String _FINDER_COLUMN_C_F_L_FRIENDLYURL_3 =
		"(siteFriendlyURL.friendlyURL IS NULL OR siteFriendlyURL.friendlyURL = '') AND ";

	private static final String _FINDER_COLUMN_C_F_L_LANGUAGEID_2 =
		"siteFriendlyURL.languageId = ?";

	private static final String _FINDER_COLUMN_C_F_L_LANGUAGEID_3 =
		"(siteFriendlyURL.languageId IS NULL OR siteFriendlyURL.languageId = '')";

	public SiteFriendlyURLPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(SiteFriendlyURL.class);

		setModelImplClass(SiteFriendlyURLImpl.class);
		setModelPKClass(long.class);

		setTable(SiteFriendlyURLTable.INSTANCE);
	}

	/**
	 * Caches the site friendly url in the entity cache if it is enabled.
	 *
	 * @param siteFriendlyURL the site friendly url
	 */
	@Override
	public void cacheResult(SiteFriendlyURL siteFriendlyURL) {
		entityCache.putResult(
			SiteFriendlyURLImpl.class, siteFriendlyURL.getPrimaryKey(),
			siteFriendlyURL);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				siteFriendlyURL.getUuid(), siteFriendlyURL.getGroupId()
			},
			siteFriendlyURL);

		finderCache.putResult(
			_finderPathFetchByC_F,
			new Object[] {
				siteFriendlyURL.getCompanyId(), siteFriendlyURL.getFriendlyURL()
			},
			siteFriendlyURL);

		finderCache.putResult(
			_finderPathFetchByG_C_L,
			new Object[] {
				siteFriendlyURL.getGroupId(), siteFriendlyURL.getCompanyId(),
				siteFriendlyURL.getLanguageId()
			},
			siteFriendlyURL);

		finderCache.putResult(
			_finderPathFetchByC_F_L,
			new Object[] {
				siteFriendlyURL.getCompanyId(),
				siteFriendlyURL.getFriendlyURL(),
				siteFriendlyURL.getLanguageId()
			},
			siteFriendlyURL);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the site friendly urls in the entity cache if it is enabled.
	 *
	 * @param siteFriendlyURLs the site friendly urls
	 */
	@Override
	public void cacheResult(List<SiteFriendlyURL> siteFriendlyURLs) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (siteFriendlyURLs.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (SiteFriendlyURL siteFriendlyURL : siteFriendlyURLs) {
			if (entityCache.getResult(
					SiteFriendlyURLImpl.class,
					siteFriendlyURL.getPrimaryKey()) == null) {

				cacheResult(siteFriendlyURL);
			}
		}
	}

	/**
	 * Clears the cache for all site friendly urls.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(SiteFriendlyURLImpl.class);

		finderCache.clearCache(SiteFriendlyURLImpl.class);
	}

	/**
	 * Clears the cache for the site friendly url.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SiteFriendlyURL siteFriendlyURL) {
		entityCache.removeResult(SiteFriendlyURLImpl.class, siteFriendlyURL);
	}

	@Override
	public void clearCache(List<SiteFriendlyURL> siteFriendlyURLs) {
		for (SiteFriendlyURL siteFriendlyURL : siteFriendlyURLs) {
			entityCache.removeResult(
				SiteFriendlyURLImpl.class, siteFriendlyURL);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(SiteFriendlyURLImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(SiteFriendlyURLImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		SiteFriendlyURLModelImpl siteFriendlyURLModelImpl) {

		Object[] args = new Object[] {
			siteFriendlyURLModelImpl.getUuid(),
			siteFriendlyURLModelImpl.getGroupId()
		};

		finderCache.putResult(_finderPathCountByUUID_G, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, siteFriendlyURLModelImpl);

		args = new Object[] {
			siteFriendlyURLModelImpl.getCompanyId(),
			siteFriendlyURLModelImpl.getFriendlyURL()
		};

		finderCache.putResult(_finderPathCountByC_F, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_F, args, siteFriendlyURLModelImpl);

		args = new Object[] {
			siteFriendlyURLModelImpl.getGroupId(),
			siteFriendlyURLModelImpl.getCompanyId(),
			siteFriendlyURLModelImpl.getLanguageId()
		};

		finderCache.putResult(_finderPathCountByG_C_L, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByG_C_L, args, siteFriendlyURLModelImpl);

		args = new Object[] {
			siteFriendlyURLModelImpl.getCompanyId(),
			siteFriendlyURLModelImpl.getFriendlyURL(),
			siteFriendlyURLModelImpl.getLanguageId()
		};

		finderCache.putResult(_finderPathCountByC_F_L, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_F_L, args, siteFriendlyURLModelImpl);
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

		siteFriendlyURL.setCompanyId(CompanyThreadLocal.getCompanyId());

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

			SiteFriendlyURL siteFriendlyURL = (SiteFriendlyURL)session.get(
				SiteFriendlyURLImpl.class, primaryKey);

			if (siteFriendlyURL == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFriendlyURLException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(siteFriendlyURL);
		}
		catch (NoSuchFriendlyURLException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected SiteFriendlyURL removeImpl(SiteFriendlyURL siteFriendlyURL) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(siteFriendlyURL)) {
				siteFriendlyURL = (SiteFriendlyURL)session.get(
					SiteFriendlyURLImpl.class,
					siteFriendlyURL.getPrimaryKeyObj());
			}

			if (siteFriendlyURL != null) {
				session.delete(siteFriendlyURL);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
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
		boolean isNew = siteFriendlyURL.isNew();

		if (!(siteFriendlyURL instanceof SiteFriendlyURLModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(siteFriendlyURL.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					siteFriendlyURL);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in siteFriendlyURL proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom SiteFriendlyURL implementation " +
					siteFriendlyURL.getClass());
		}

		SiteFriendlyURLModelImpl siteFriendlyURLModelImpl =
			(SiteFriendlyURLModelImpl)siteFriendlyURL;

		if (Validator.isNull(siteFriendlyURL.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			siteFriendlyURL.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (siteFriendlyURL.getCreateDate() == null)) {
			if (serviceContext == null) {
				siteFriendlyURL.setCreateDate(date);
			}
			else {
				siteFriendlyURL.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!siteFriendlyURLModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				siteFriendlyURL.setModifiedDate(date);
			}
			else {
				siteFriendlyURL.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(siteFriendlyURL);
			}
			else {
				siteFriendlyURL = (SiteFriendlyURL)session.merge(
					siteFriendlyURL);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			SiteFriendlyURLImpl.class, siteFriendlyURLModelImpl, false, true);

		cacheUniqueFindersCache(siteFriendlyURLModelImpl);

		if (isNew) {
			siteFriendlyURL.setNew(false);
		}

		siteFriendlyURL.resetOriginalValues();

		return siteFriendlyURL;
	}

	/**
	 * Returns the site friendly url with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
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

			throw new NoSuchFriendlyURLException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return siteFriendlyURL;
	}

	/**
	 * Returns the site friendly url with the primary key or throws a <code>NoSuchFriendlyURLException</code> if it could not be found.
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
	 * @param siteFriendlyURLId the primary key of the site friendly url
	 * @return the site friendly url, or <code>null</code> if a site friendly url with the primary key could not be found
	 */
	@Override
	public SiteFriendlyURL fetchByPrimaryKey(long siteFriendlyURLId) {
		return fetchByPrimaryKey((Serializable)siteFriendlyURLId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteFriendlyURLModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteFriendlyURLModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of site friendly urls
	 */
	@Override
	public List<SiteFriendlyURL> findAll(
		int start, int end,
		OrderByComparator<SiteFriendlyURL> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the site friendly urls.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteFriendlyURLModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of site friendly urls
	 * @param end the upper bound of the range of site friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of site friendly urls
	 */
	@Override
	public List<SiteFriendlyURL> findAll(
		int start, int end,
		OrderByComparator<SiteFriendlyURL> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<SiteFriendlyURL> list = null;

		if (useFinderCache) {
			list = (List<SiteFriendlyURL>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_SITEFRIENDLYURL);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_SITEFRIENDLYURL;

				sql = sql.concat(SiteFriendlyURLModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<SiteFriendlyURL>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
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
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_SITEFRIENDLYURL);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				throw processException(exception);
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
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "siteFriendlyURLId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_SITEFRIENDLYURL;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return SiteFriendlyURLModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the site friendly url persistence.
	 */
	@Activate
	public void activate() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"uuid_"}, true);

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			true);

		_finderPathCountByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			false);

		_finderPathFetchByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, true);

		_finderPathCountByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, false);

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathCountByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, false);

		_finderPathWithPaginationFindByG_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "companyId"}, true);

		_finderPathWithoutPaginationFindByG_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "companyId"}, true);

		_finderPathCountByG_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "companyId"}, false);

		_finderPathFetchByC_F = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_F",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "friendlyURL"}, true);

		_finderPathCountByC_F = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_F",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "friendlyURL"}, false);

		_finderPathFetchByG_C_L = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByG_C_L",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			new String[] {"groupId", "companyId", "languageId"}, true);

		_finderPathCountByG_C_L = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_L",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			new String[] {"groupId", "companyId", "languageId"}, false);

		_finderPathFetchByC_F_L = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_F_L",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {"companyId", "friendlyURL", "languageId"}, true);

		_finderPathCountByC_F_L = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_F_L",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {"companyId", "friendlyURL", "languageId"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(SiteFriendlyURLImpl.class.getName());
	}

	@Override
	@Reference(
		target = SitePersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = SitePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = SitePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_SITEFRIENDLYURL =
		"SELECT siteFriendlyURL FROM SiteFriendlyURL siteFriendlyURL";

	private static final String _SQL_SELECT_SITEFRIENDLYURL_WHERE =
		"SELECT siteFriendlyURL FROM SiteFriendlyURL siteFriendlyURL WHERE ";

	private static final String _SQL_COUNT_SITEFRIENDLYURL =
		"SELECT COUNT(siteFriendlyURL) FROM SiteFriendlyURL siteFriendlyURL";

	private static final String _SQL_COUNT_SITEFRIENDLYURL_WHERE =
		"SELECT COUNT(siteFriendlyURL) FROM SiteFriendlyURL siteFriendlyURL WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "siteFriendlyURL.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No SiteFriendlyURL exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No SiteFriendlyURL exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		SiteFriendlyURLPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private SiteFriendlyURLModelArgumentsResolver
		_siteFriendlyURLModelArgumentsResolver;

}