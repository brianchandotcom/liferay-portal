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

package com.liferay.web.hook.service.persistence.impl;

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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.web.hook.exception.NoSuchEntryException;
import com.liferay.web.hook.model.WebHookEntry;
import com.liferay.web.hook.model.WebHookEntryTable;
import com.liferay.web.hook.model.impl.WebHookEntryImpl;
import com.liferay.web.hook.model.impl.WebHookEntryModelImpl;
import com.liferay.web.hook.service.persistence.WebHookEntryPersistence;
import com.liferay.web.hook.service.persistence.impl.constants.WebHookPersistenceConstants;

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
 * The persistence implementation for the web hook entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = {WebHookEntryPersistence.class, BasePersistence.class})
public class WebHookEntryPersistenceImpl
	extends BasePersistenceImpl<WebHookEntry>
	implements WebHookEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>WebHookEntryUtil</code> to access the web hook entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		WebHookEntryImpl.class.getName();

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
	 * Returns all the web hook entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching web hook entries
	 */
	@Override
	public List<WebHookEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the web hook entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebHookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of web hook entries
	 * @param end the upper bound of the range of web hook entries (not inclusive)
	 * @return the range of matching web hook entries
	 */
	@Override
	public List<WebHookEntry> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the web hook entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebHookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of web hook entries
	 * @param end the upper bound of the range of web hook entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching web hook entries
	 */
	@Override
	public List<WebHookEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<WebHookEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the web hook entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebHookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of web hook entries
	 * @param end the upper bound of the range of web hook entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching web hook entries
	 */
	@Override
	public List<WebHookEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<WebHookEntry> orderByComparator,
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

		List<WebHookEntry> list = null;

		if (useFinderCache) {
			list = (List<WebHookEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (WebHookEntry webHookEntry : list) {
					if (!uuid.equals(webHookEntry.getUuid())) {
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

			sb.append(_SQL_SELECT_WEBHOOKENTRY_WHERE);

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
				sb.append(WebHookEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<WebHookEntry>)QueryUtil.list(
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
	 * Returns the first web hook entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching web hook entry
	 * @throws NoSuchEntryException if a matching web hook entry could not be found
	 */
	@Override
	public WebHookEntry findByUuid_First(
			String uuid, OrderByComparator<WebHookEntry> orderByComparator)
		throws NoSuchEntryException {

		WebHookEntry webHookEntry = fetchByUuid_First(uuid, orderByComparator);

		if (webHookEntry != null) {
			return webHookEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first web hook entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching web hook entry, or <code>null</code> if a matching web hook entry could not be found
	 */
	@Override
	public WebHookEntry fetchByUuid_First(
		String uuid, OrderByComparator<WebHookEntry> orderByComparator) {

		List<WebHookEntry> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last web hook entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching web hook entry
	 * @throws NoSuchEntryException if a matching web hook entry could not be found
	 */
	@Override
	public WebHookEntry findByUuid_Last(
			String uuid, OrderByComparator<WebHookEntry> orderByComparator)
		throws NoSuchEntryException {

		WebHookEntry webHookEntry = fetchByUuid_Last(uuid, orderByComparator);

		if (webHookEntry != null) {
			return webHookEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last web hook entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching web hook entry, or <code>null</code> if a matching web hook entry could not be found
	 */
	@Override
	public WebHookEntry fetchByUuid_Last(
		String uuid, OrderByComparator<WebHookEntry> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<WebHookEntry> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the web hook entries before and after the current web hook entry in the ordered set where uuid = &#63;.
	 *
	 * @param webHookEntryId the primary key of the current web hook entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next web hook entry
	 * @throws NoSuchEntryException if a web hook entry with the primary key could not be found
	 */
	@Override
	public WebHookEntry[] findByUuid_PrevAndNext(
			long webHookEntryId, String uuid,
			OrderByComparator<WebHookEntry> orderByComparator)
		throws NoSuchEntryException {

		uuid = Objects.toString(uuid, "");

		WebHookEntry webHookEntry = findByPrimaryKey(webHookEntryId);

		Session session = null;

		try {
			session = openSession();

			WebHookEntry[] array = new WebHookEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, webHookEntry, uuid, orderByComparator, true);

			array[1] = webHookEntry;

			array[2] = getByUuid_PrevAndNext(
				session, webHookEntry, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected WebHookEntry getByUuid_PrevAndNext(
		Session session, WebHookEntry webHookEntry, String uuid,
		OrderByComparator<WebHookEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_WEBHOOKENTRY_WHERE);

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
			sb.append(WebHookEntryModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(webHookEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<WebHookEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the web hook entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (WebHookEntry webHookEntry :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(webHookEntry);
		}
	}

	/**
	 * Returns the number of web hook entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching web hook entries
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_WEBHOOKENTRY_WHERE);

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
		"webHookEntry.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(webHookEntry.uuid IS NULL OR webHookEntry.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the web hook entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching web hook entries
	 */
	@Override
	public List<WebHookEntry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the web hook entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebHookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of web hook entries
	 * @param end the upper bound of the range of web hook entries (not inclusive)
	 * @return the range of matching web hook entries
	 */
	@Override
	public List<WebHookEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the web hook entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebHookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of web hook entries
	 * @param end the upper bound of the range of web hook entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching web hook entries
	 */
	@Override
	public List<WebHookEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<WebHookEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the web hook entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebHookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of web hook entries
	 * @param end the upper bound of the range of web hook entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching web hook entries
	 */
	@Override
	public List<WebHookEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<WebHookEntry> orderByComparator,
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

		List<WebHookEntry> list = null;

		if (useFinderCache) {
			list = (List<WebHookEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (WebHookEntry webHookEntry : list) {
					if (!uuid.equals(webHookEntry.getUuid()) ||
						(companyId != webHookEntry.getCompanyId())) {

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

			sb.append(_SQL_SELECT_WEBHOOKENTRY_WHERE);

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
				sb.append(WebHookEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<WebHookEntry>)QueryUtil.list(
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
	 * Returns the first web hook entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching web hook entry
	 * @throws NoSuchEntryException if a matching web hook entry could not be found
	 */
	@Override
	public WebHookEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<WebHookEntry> orderByComparator)
		throws NoSuchEntryException {

		WebHookEntry webHookEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (webHookEntry != null) {
			return webHookEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first web hook entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching web hook entry, or <code>null</code> if a matching web hook entry could not be found
	 */
	@Override
	public WebHookEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<WebHookEntry> orderByComparator) {

		List<WebHookEntry> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last web hook entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching web hook entry
	 * @throws NoSuchEntryException if a matching web hook entry could not be found
	 */
	@Override
	public WebHookEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<WebHookEntry> orderByComparator)
		throws NoSuchEntryException {

		WebHookEntry webHookEntry = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (webHookEntry != null) {
			return webHookEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last web hook entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching web hook entry, or <code>null</code> if a matching web hook entry could not be found
	 */
	@Override
	public WebHookEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<WebHookEntry> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<WebHookEntry> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the web hook entries before and after the current web hook entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param webHookEntryId the primary key of the current web hook entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next web hook entry
	 * @throws NoSuchEntryException if a web hook entry with the primary key could not be found
	 */
	@Override
	public WebHookEntry[] findByUuid_C_PrevAndNext(
			long webHookEntryId, String uuid, long companyId,
			OrderByComparator<WebHookEntry> orderByComparator)
		throws NoSuchEntryException {

		uuid = Objects.toString(uuid, "");

		WebHookEntry webHookEntry = findByPrimaryKey(webHookEntryId);

		Session session = null;

		try {
			session = openSession();

			WebHookEntry[] array = new WebHookEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, webHookEntry, uuid, companyId, orderByComparator,
				true);

			array[1] = webHookEntry;

			array[2] = getByUuid_C_PrevAndNext(
				session, webHookEntry, uuid, companyId, orderByComparator,
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

	protected WebHookEntry getByUuid_C_PrevAndNext(
		Session session, WebHookEntry webHookEntry, String uuid, long companyId,
		OrderByComparator<WebHookEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_WEBHOOKENTRY_WHERE);

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
			sb.append(WebHookEntryModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(webHookEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<WebHookEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the web hook entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (WebHookEntry webHookEntry :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(webHookEntry);
		}
	}

	/**
	 * Returns the number of web hook entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching web hook entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_WEBHOOKENTRY_WHERE);

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
		"webHookEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(webHookEntry.uuid IS NULL OR webHookEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"webHookEntry.companyId = ?";

	private FinderPath _finderPathFetchByC_D_U;
	private FinderPath _finderPathCountByC_D_U;

	/**
	 * Returns the web hook entry where companyId = &#63; and destination = &#63; and url = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param destination the destination
	 * @param url the url
	 * @return the matching web hook entry
	 * @throws NoSuchEntryException if a matching web hook entry could not be found
	 */
	@Override
	public WebHookEntry findByC_D_U(
			long companyId, String destination, String url)
		throws NoSuchEntryException {

		WebHookEntry webHookEntry = fetchByC_D_U(companyId, destination, url);

		if (webHookEntry == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", destination=");
			sb.append(destination);

			sb.append(", url=");
			sb.append(url);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchEntryException(sb.toString());
		}

		return webHookEntry;
	}

	/**
	 * Returns the web hook entry where companyId = &#63; and destination = &#63; and url = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param destination the destination
	 * @param url the url
	 * @return the matching web hook entry, or <code>null</code> if a matching web hook entry could not be found
	 */
	@Override
	public WebHookEntry fetchByC_D_U(
		long companyId, String destination, String url) {

		return fetchByC_D_U(companyId, destination, url, true);
	}

	/**
	 * Returns the web hook entry where companyId = &#63; and destination = &#63; and url = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param destination the destination
	 * @param url the url
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching web hook entry, or <code>null</code> if a matching web hook entry could not be found
	 */
	@Override
	public WebHookEntry fetchByC_D_U(
		long companyId, String destination, String url,
		boolean useFinderCache) {

		destination = Objects.toString(destination, "");
		url = Objects.toString(url, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, destination, url};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByC_D_U, finderArgs);
		}

		if (result instanceof WebHookEntry) {
			WebHookEntry webHookEntry = (WebHookEntry)result;

			if ((companyId != webHookEntry.getCompanyId()) ||
				!Objects.equals(destination, webHookEntry.getDestination()) ||
				!Objects.equals(url, webHookEntry.getUrl())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_WEBHOOKENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_D_U_COMPANYID_2);

			boolean bindDestination = false;

			if (destination.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_D_U_DESTINATION_3);
			}
			else {
				bindDestination = true;

				sb.append(_FINDER_COLUMN_C_D_U_DESTINATION_2);
			}

			boolean bindUrl = false;

			if (url.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_D_U_URL_3);
			}
			else {
				bindUrl = true;

				sb.append(_FINDER_COLUMN_C_D_U_URL_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindDestination) {
					queryPos.add(destination);
				}

				if (bindUrl) {
					queryPos.add(url);
				}

				List<WebHookEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_D_U, finderArgs, list);
					}
				}
				else {
					WebHookEntry webHookEntry = list.get(0);

					result = webHookEntry;

					cacheResult(webHookEntry);
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
			return (WebHookEntry)result;
		}
	}

	/**
	 * Removes the web hook entry where companyId = &#63; and destination = &#63; and url = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param destination the destination
	 * @param url the url
	 * @return the web hook entry that was removed
	 */
	@Override
	public WebHookEntry removeByC_D_U(
			long companyId, String destination, String url)
		throws NoSuchEntryException {

		WebHookEntry webHookEntry = findByC_D_U(companyId, destination, url);

		return remove(webHookEntry);
	}

	/**
	 * Returns the number of web hook entries where companyId = &#63; and destination = &#63; and url = &#63;.
	 *
	 * @param companyId the company ID
	 * @param destination the destination
	 * @param url the url
	 * @return the number of matching web hook entries
	 */
	@Override
	public int countByC_D_U(long companyId, String destination, String url) {
		destination = Objects.toString(destination, "");
		url = Objects.toString(url, "");

		FinderPath finderPath = _finderPathCountByC_D_U;

		Object[] finderArgs = new Object[] {companyId, destination, url};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_WEBHOOKENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_D_U_COMPANYID_2);

			boolean bindDestination = false;

			if (destination.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_D_U_DESTINATION_3);
			}
			else {
				bindDestination = true;

				sb.append(_FINDER_COLUMN_C_D_U_DESTINATION_2);
			}

			boolean bindUrl = false;

			if (url.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_D_U_URL_3);
			}
			else {
				bindUrl = true;

				sb.append(_FINDER_COLUMN_C_D_U_URL_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindDestination) {
					queryPos.add(destination);
				}

				if (bindUrl) {
					queryPos.add(url);
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

	private static final String _FINDER_COLUMN_C_D_U_COMPANYID_2 =
		"webHookEntry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_D_U_DESTINATION_2 =
		"webHookEntry.destination = ? AND ";

	private static final String _FINDER_COLUMN_C_D_U_DESTINATION_3 =
		"(webHookEntry.destination IS NULL OR webHookEntry.destination = '') AND ";

	private static final String _FINDER_COLUMN_C_D_U_URL_2 =
		"webHookEntry.url = ?";

	private static final String _FINDER_COLUMN_C_D_U_URL_3 =
		"(webHookEntry.url IS NULL OR webHookEntry.url = '')";

	public WebHookEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(WebHookEntry.class);

		setModelImplClass(WebHookEntryImpl.class);
		setModelPKClass(long.class);

		setTable(WebHookEntryTable.INSTANCE);
	}

	/**
	 * Caches the web hook entry in the entity cache if it is enabled.
	 *
	 * @param webHookEntry the web hook entry
	 */
	@Override
	public void cacheResult(WebHookEntry webHookEntry) {
		entityCache.putResult(
			WebHookEntryImpl.class, webHookEntry.getPrimaryKey(), webHookEntry);

		finderCache.putResult(
			_finderPathFetchByC_D_U,
			new Object[] {
				webHookEntry.getCompanyId(), webHookEntry.getDestination(),
				webHookEntry.getUrl()
			},
			webHookEntry);
	}

	/**
	 * Caches the web hook entries in the entity cache if it is enabled.
	 *
	 * @param webHookEntries the web hook entries
	 */
	@Override
	public void cacheResult(List<WebHookEntry> webHookEntries) {
		for (WebHookEntry webHookEntry : webHookEntries) {
			if (entityCache.getResult(
					WebHookEntryImpl.class, webHookEntry.getPrimaryKey()) ==
						null) {

				cacheResult(webHookEntry);
			}
		}
	}

	/**
	 * Clears the cache for all web hook entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(WebHookEntryImpl.class);

		finderCache.clearCache(WebHookEntryImpl.class);
	}

	/**
	 * Clears the cache for the web hook entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(WebHookEntry webHookEntry) {
		entityCache.removeResult(WebHookEntryImpl.class, webHookEntry);
	}

	@Override
	public void clearCache(List<WebHookEntry> webHookEntries) {
		for (WebHookEntry webHookEntry : webHookEntries) {
			entityCache.removeResult(WebHookEntryImpl.class, webHookEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(WebHookEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(WebHookEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		WebHookEntryModelImpl webHookEntryModelImpl) {

		Object[] args = new Object[] {
			webHookEntryModelImpl.getCompanyId(),
			webHookEntryModelImpl.getDestination(),
			webHookEntryModelImpl.getUrl()
		};

		finderCache.putResult(_finderPathCountByC_D_U, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_D_U, args, webHookEntryModelImpl);
	}

	/**
	 * Creates a new web hook entry with the primary key. Does not add the web hook entry to the database.
	 *
	 * @param webHookEntryId the primary key for the new web hook entry
	 * @return the new web hook entry
	 */
	@Override
	public WebHookEntry create(long webHookEntryId) {
		WebHookEntry webHookEntry = new WebHookEntryImpl();

		webHookEntry.setNew(true);
		webHookEntry.setPrimaryKey(webHookEntryId);

		String uuid = PortalUUIDUtil.generate();

		webHookEntry.setUuid(uuid);

		webHookEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return webHookEntry;
	}

	/**
	 * Removes the web hook entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param webHookEntryId the primary key of the web hook entry
	 * @return the web hook entry that was removed
	 * @throws NoSuchEntryException if a web hook entry with the primary key could not be found
	 */
	@Override
	public WebHookEntry remove(long webHookEntryId)
		throws NoSuchEntryException {

		return remove((Serializable)webHookEntryId);
	}

	/**
	 * Removes the web hook entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the web hook entry
	 * @return the web hook entry that was removed
	 * @throws NoSuchEntryException if a web hook entry with the primary key could not be found
	 */
	@Override
	public WebHookEntry remove(Serializable primaryKey)
		throws NoSuchEntryException {

		Session session = null;

		try {
			session = openSession();

			WebHookEntry webHookEntry = (WebHookEntry)session.get(
				WebHookEntryImpl.class, primaryKey);

			if (webHookEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(webHookEntry);
		}
		catch (NoSuchEntryException noSuchEntityException) {
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
	protected WebHookEntry removeImpl(WebHookEntry webHookEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(webHookEntry)) {
				webHookEntry = (WebHookEntry)session.get(
					WebHookEntryImpl.class, webHookEntry.getPrimaryKeyObj());
			}

			if (webHookEntry != null) {
				session.delete(webHookEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (webHookEntry != null) {
			clearCache(webHookEntry);
		}

		return webHookEntry;
	}

	@Override
	public WebHookEntry updateImpl(WebHookEntry webHookEntry) {
		boolean isNew = webHookEntry.isNew();

		if (!(webHookEntry instanceof WebHookEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(webHookEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					webHookEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in webHookEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom WebHookEntry implementation " +
					webHookEntry.getClass());
		}

		WebHookEntryModelImpl webHookEntryModelImpl =
			(WebHookEntryModelImpl)webHookEntry;

		if (Validator.isNull(webHookEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			webHookEntry.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (webHookEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				webHookEntry.setCreateDate(date);
			}
			else {
				webHookEntry.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!webHookEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				webHookEntry.setModifiedDate(date);
			}
			else {
				webHookEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(webHookEntry);
			}
			else {
				webHookEntry = (WebHookEntry)session.merge(webHookEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			WebHookEntryImpl.class, webHookEntryModelImpl, false, true);

		cacheUniqueFindersCache(webHookEntryModelImpl);

		if (isNew) {
			webHookEntry.setNew(false);
		}

		webHookEntry.resetOriginalValues();

		return webHookEntry;
	}

	/**
	 * Returns the web hook entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the web hook entry
	 * @return the web hook entry
	 * @throws NoSuchEntryException if a web hook entry with the primary key could not be found
	 */
	@Override
	public WebHookEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryException {

		WebHookEntry webHookEntry = fetchByPrimaryKey(primaryKey);

		if (webHookEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return webHookEntry;
	}

	/**
	 * Returns the web hook entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param webHookEntryId the primary key of the web hook entry
	 * @return the web hook entry
	 * @throws NoSuchEntryException if a web hook entry with the primary key could not be found
	 */
	@Override
	public WebHookEntry findByPrimaryKey(long webHookEntryId)
		throws NoSuchEntryException {

		return findByPrimaryKey((Serializable)webHookEntryId);
	}

	/**
	 * Returns the web hook entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param webHookEntryId the primary key of the web hook entry
	 * @return the web hook entry, or <code>null</code> if a web hook entry with the primary key could not be found
	 */
	@Override
	public WebHookEntry fetchByPrimaryKey(long webHookEntryId) {
		return fetchByPrimaryKey((Serializable)webHookEntryId);
	}

	/**
	 * Returns all the web hook entries.
	 *
	 * @return the web hook entries
	 */
	@Override
	public List<WebHookEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the web hook entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebHookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of web hook entries
	 * @param end the upper bound of the range of web hook entries (not inclusive)
	 * @return the range of web hook entries
	 */
	@Override
	public List<WebHookEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the web hook entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebHookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of web hook entries
	 * @param end the upper bound of the range of web hook entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of web hook entries
	 */
	@Override
	public List<WebHookEntry> findAll(
		int start, int end, OrderByComparator<WebHookEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the web hook entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebHookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of web hook entries
	 * @param end the upper bound of the range of web hook entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of web hook entries
	 */
	@Override
	public List<WebHookEntry> findAll(
		int start, int end, OrderByComparator<WebHookEntry> orderByComparator,
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

		List<WebHookEntry> list = null;

		if (useFinderCache) {
			list = (List<WebHookEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_WEBHOOKENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_WEBHOOKENTRY;

				sql = sql.concat(WebHookEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<WebHookEntry>)QueryUtil.list(
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
	 * Removes all the web hook entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (WebHookEntry webHookEntry : findAll()) {
			remove(webHookEntry);
		}
	}

	/**
	 * Returns the number of web hook entries.
	 *
	 * @return the number of web hook entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_WEBHOOKENTRY);

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
		return "webHookEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_WEBHOOKENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return WebHookEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the web hook entry persistence.
	 */
	@Activate
	public void activate() {
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

		_finderPathFetchByC_D_U = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_D_U",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {"companyId", "destination", "url"}, true);

		_finderPathCountByC_D_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_D_U",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {"companyId", "destination", "url"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(WebHookEntryImpl.class.getName());
	}

	@Override
	@Reference(
		target = WebHookPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = WebHookPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = WebHookPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_WEBHOOKENTRY =
		"SELECT webHookEntry FROM WebHookEntry webHookEntry";

	private static final String _SQL_SELECT_WEBHOOKENTRY_WHERE =
		"SELECT webHookEntry FROM WebHookEntry webHookEntry WHERE ";

	private static final String _SQL_COUNT_WEBHOOKENTRY =
		"SELECT COUNT(webHookEntry) FROM WebHookEntry webHookEntry";

	private static final String _SQL_COUNT_WEBHOOKENTRY_WHERE =
		"SELECT COUNT(webHookEntry) FROM WebHookEntry webHookEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "webHookEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No WebHookEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No WebHookEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		WebHookEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private WebHookEntryModelArgumentsResolver
		_webHookEntryModelArgumentsResolver;

}