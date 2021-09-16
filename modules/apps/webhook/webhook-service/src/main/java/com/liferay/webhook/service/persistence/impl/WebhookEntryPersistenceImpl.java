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

package com.liferay.webhook.service.persistence.impl;

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
import com.liferay.webhook.exception.NoSuchWebhookEntryException;
import com.liferay.webhook.model.WebhookEntry;
import com.liferay.webhook.model.WebhookEntryTable;
import com.liferay.webhook.model.impl.WebhookEntryImpl;
import com.liferay.webhook.model.impl.WebhookEntryModelImpl;
import com.liferay.webhook.service.persistence.WebhookEntryPersistence;
import com.liferay.webhook.service.persistence.impl.constants.WebhookPersistenceConstants;

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
 * The persistence implementation for the webhook entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = {WebhookEntryPersistence.class, BasePersistence.class})
public class WebhookEntryPersistenceImpl
	extends BasePersistenceImpl<WebhookEntry>
	implements WebhookEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>WebhookEntryUtil</code> to access the webhook entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		WebhookEntryImpl.class.getName();

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
	 * Returns all the webhook entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching webhook entries
	 */
	@Override
	public List<WebhookEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the webhook entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of webhook entries
	 * @param end the upper bound of the range of webhook entries (not inclusive)
	 * @return the range of matching webhook entries
	 */
	@Override
	public List<WebhookEntry> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the webhook entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of webhook entries
	 * @param end the upper bound of the range of webhook entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching webhook entries
	 */
	@Override
	public List<WebhookEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<WebhookEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the webhook entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of webhook entries
	 * @param end the upper bound of the range of webhook entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching webhook entries
	 */
	@Override
	public List<WebhookEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<WebhookEntry> orderByComparator,
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

		List<WebhookEntry> list = null;

		if (useFinderCache) {
			list = (List<WebhookEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (WebhookEntry webhookEntry : list) {
					if (!uuid.equals(webhookEntry.getUuid())) {
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
				sb.append(WebhookEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<WebhookEntry>)QueryUtil.list(
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
	 * Returns the first webhook entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching webhook entry
	 * @throws NoSuchWebhookEntryException if a matching webhook entry could not be found
	 */
	@Override
	public WebhookEntry findByUuid_First(
			String uuid, OrderByComparator<WebhookEntry> orderByComparator)
		throws NoSuchWebhookEntryException {

		WebhookEntry webhookEntry = fetchByUuid_First(uuid, orderByComparator);

		if (webhookEntry != null) {
			return webhookEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchWebhookEntryException(sb.toString());
	}

	/**
	 * Returns the first webhook entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching webhook entry, or <code>null</code> if a matching webhook entry could not be found
	 */
	@Override
	public WebhookEntry fetchByUuid_First(
		String uuid, OrderByComparator<WebhookEntry> orderByComparator) {

		List<WebhookEntry> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last webhook entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching webhook entry
	 * @throws NoSuchWebhookEntryException if a matching webhook entry could not be found
	 */
	@Override
	public WebhookEntry findByUuid_Last(
			String uuid, OrderByComparator<WebhookEntry> orderByComparator)
		throws NoSuchWebhookEntryException {

		WebhookEntry webhookEntry = fetchByUuid_Last(uuid, orderByComparator);

		if (webhookEntry != null) {
			return webhookEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchWebhookEntryException(sb.toString());
	}

	/**
	 * Returns the last webhook entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching webhook entry, or <code>null</code> if a matching webhook entry could not be found
	 */
	@Override
	public WebhookEntry fetchByUuid_Last(
		String uuid, OrderByComparator<WebhookEntry> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<WebhookEntry> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the webhook entries before and after the current webhook entry in the ordered set where uuid = &#63;.
	 *
	 * @param webhookEntryId the primary key of the current webhook entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next webhook entry
	 * @throws NoSuchWebhookEntryException if a webhook entry with the primary key could not be found
	 */
	@Override
	public WebhookEntry[] findByUuid_PrevAndNext(
			long webhookEntryId, String uuid,
			OrderByComparator<WebhookEntry> orderByComparator)
		throws NoSuchWebhookEntryException {

		uuid = Objects.toString(uuid, "");

		WebhookEntry webhookEntry = findByPrimaryKey(webhookEntryId);

		Session session = null;

		try {
			session = openSession();

			WebhookEntry[] array = new WebhookEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, webhookEntry, uuid, orderByComparator, true);

			array[1] = webhookEntry;

			array[2] = getByUuid_PrevAndNext(
				session, webhookEntry, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected WebhookEntry getByUuid_PrevAndNext(
		Session session, WebhookEntry webhookEntry, String uuid,
		OrderByComparator<WebhookEntry> orderByComparator, boolean previous) {

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
			sb.append(WebhookEntryModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(webhookEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<WebhookEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the webhook entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (WebhookEntry webhookEntry :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(webhookEntry);
		}
	}

	/**
	 * Returns the number of webhook entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching webhook entries
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
		"webhookEntry.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(webhookEntry.uuid IS NULL OR webhookEntry.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the webhook entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching webhook entries
	 */
	@Override
	public List<WebhookEntry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the webhook entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of webhook entries
	 * @param end the upper bound of the range of webhook entries (not inclusive)
	 * @return the range of matching webhook entries
	 */
	@Override
	public List<WebhookEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the webhook entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of webhook entries
	 * @param end the upper bound of the range of webhook entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching webhook entries
	 */
	@Override
	public List<WebhookEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<WebhookEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the webhook entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of webhook entries
	 * @param end the upper bound of the range of webhook entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching webhook entries
	 */
	@Override
	public List<WebhookEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<WebhookEntry> orderByComparator,
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

		List<WebhookEntry> list = null;

		if (useFinderCache) {
			list = (List<WebhookEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (WebhookEntry webhookEntry : list) {
					if (!uuid.equals(webhookEntry.getUuid()) ||
						(companyId != webhookEntry.getCompanyId())) {

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
				sb.append(WebhookEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<WebhookEntry>)QueryUtil.list(
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
	 * Returns the first webhook entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching webhook entry
	 * @throws NoSuchWebhookEntryException if a matching webhook entry could not be found
	 */
	@Override
	public WebhookEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<WebhookEntry> orderByComparator)
		throws NoSuchWebhookEntryException {

		WebhookEntry webhookEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (webhookEntry != null) {
			return webhookEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchWebhookEntryException(sb.toString());
	}

	/**
	 * Returns the first webhook entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching webhook entry, or <code>null</code> if a matching webhook entry could not be found
	 */
	@Override
	public WebhookEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<WebhookEntry> orderByComparator) {

		List<WebhookEntry> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last webhook entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching webhook entry
	 * @throws NoSuchWebhookEntryException if a matching webhook entry could not be found
	 */
	@Override
	public WebhookEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<WebhookEntry> orderByComparator)
		throws NoSuchWebhookEntryException {

		WebhookEntry webhookEntry = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (webhookEntry != null) {
			return webhookEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchWebhookEntryException(sb.toString());
	}

	/**
	 * Returns the last webhook entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching webhook entry, or <code>null</code> if a matching webhook entry could not be found
	 */
	@Override
	public WebhookEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<WebhookEntry> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<WebhookEntry> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the webhook entries before and after the current webhook entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param webhookEntryId the primary key of the current webhook entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next webhook entry
	 * @throws NoSuchWebhookEntryException if a webhook entry with the primary key could not be found
	 */
	@Override
	public WebhookEntry[] findByUuid_C_PrevAndNext(
			long webhookEntryId, String uuid, long companyId,
			OrderByComparator<WebhookEntry> orderByComparator)
		throws NoSuchWebhookEntryException {

		uuid = Objects.toString(uuid, "");

		WebhookEntry webhookEntry = findByPrimaryKey(webhookEntryId);

		Session session = null;

		try {
			session = openSession();

			WebhookEntry[] array = new WebhookEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, webhookEntry, uuid, companyId, orderByComparator,
				true);

			array[1] = webhookEntry;

			array[2] = getByUuid_C_PrevAndNext(
				session, webhookEntry, uuid, companyId, orderByComparator,
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

	protected WebhookEntry getByUuid_C_PrevAndNext(
		Session session, WebhookEntry webhookEntry, String uuid, long companyId,
		OrderByComparator<WebhookEntry> orderByComparator, boolean previous) {

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
			sb.append(WebhookEntryModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(webhookEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<WebhookEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the webhook entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (WebhookEntry webhookEntry :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(webhookEntry);
		}
	}

	/**
	 * Returns the number of webhook entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching webhook entries
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
		"webhookEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(webhookEntry.uuid IS NULL OR webhookEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"webhookEntry.companyId = ?";

	public WebhookEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("active", "active_");

		setDBColumnNames(dbColumnNames);

		setModelClass(WebhookEntry.class);

		setModelImplClass(WebhookEntryImpl.class);
		setModelPKClass(long.class);

		setTable(WebhookEntryTable.INSTANCE);
	}

	/**
	 * Caches the webhook entry in the entity cache if it is enabled.
	 *
	 * @param webhookEntry the webhook entry
	 */
	@Override
	public void cacheResult(WebhookEntry webhookEntry) {
		entityCache.putResult(
			WebhookEntryImpl.class, webhookEntry.getPrimaryKey(), webhookEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the webhook entries in the entity cache if it is enabled.
	 *
	 * @param webhookEntries the webhook entries
	 */
	@Override
	public void cacheResult(List<WebhookEntry> webhookEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (webhookEntries.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (WebhookEntry webhookEntry : webhookEntries) {
			if (entityCache.getResult(
					WebhookEntryImpl.class, webhookEntry.getPrimaryKey()) ==
						null) {

				cacheResult(webhookEntry);
			}
		}
	}

	/**
	 * Clears the cache for all webhook entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(WebhookEntryImpl.class);

		finderCache.clearCache(WebhookEntryImpl.class);
	}

	/**
	 * Clears the cache for the webhook entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(WebhookEntry webhookEntry) {
		entityCache.removeResult(WebhookEntryImpl.class, webhookEntry);
	}

	@Override
	public void clearCache(List<WebhookEntry> webhookEntries) {
		for (WebhookEntry webhookEntry : webhookEntries) {
			entityCache.removeResult(WebhookEntryImpl.class, webhookEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(WebhookEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(WebhookEntryImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new webhook entry with the primary key. Does not add the webhook entry to the database.
	 *
	 * @param webhookEntryId the primary key for the new webhook entry
	 * @return the new webhook entry
	 */
	@Override
	public WebhookEntry create(long webhookEntryId) {
		WebhookEntry webhookEntry = new WebhookEntryImpl();

		webhookEntry.setNew(true);
		webhookEntry.setPrimaryKey(webhookEntryId);

		String uuid = PortalUUIDUtil.generate();

		webhookEntry.setUuid(uuid);

		webhookEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return webhookEntry;
	}

	/**
	 * Removes the webhook entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param webhookEntryId the primary key of the webhook entry
	 * @return the webhook entry that was removed
	 * @throws NoSuchWebhookEntryException if a webhook entry with the primary key could not be found
	 */
	@Override
	public WebhookEntry remove(long webhookEntryId)
		throws NoSuchWebhookEntryException {

		return remove((Serializable)webhookEntryId);
	}

	/**
	 * Removes the webhook entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the webhook entry
	 * @return the webhook entry that was removed
	 * @throws NoSuchWebhookEntryException if a webhook entry with the primary key could not be found
	 */
	@Override
	public WebhookEntry remove(Serializable primaryKey)
		throws NoSuchWebhookEntryException {

		Session session = null;

		try {
			session = openSession();

			WebhookEntry webhookEntry = (WebhookEntry)session.get(
				WebhookEntryImpl.class, primaryKey);

			if (webhookEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchWebhookEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(webhookEntry);
		}
		catch (NoSuchWebhookEntryException noSuchEntityException) {
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
	protected WebhookEntry removeImpl(WebhookEntry webhookEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(webhookEntry)) {
				webhookEntry = (WebhookEntry)session.get(
					WebhookEntryImpl.class, webhookEntry.getPrimaryKeyObj());
			}

			if (webhookEntry != null) {
				session.delete(webhookEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (webhookEntry != null) {
			clearCache(webhookEntry);
		}

		return webhookEntry;
	}

	@Override
	public WebhookEntry updateImpl(WebhookEntry webhookEntry) {
		boolean isNew = webhookEntry.isNew();

		if (!(webhookEntry instanceof WebhookEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(webhookEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					webhookEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in webhookEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom WebhookEntry implementation " +
					webhookEntry.getClass());
		}

		WebhookEntryModelImpl webhookEntryModelImpl =
			(WebhookEntryModelImpl)webhookEntry;

		if (Validator.isNull(webhookEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			webhookEntry.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (webhookEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				webhookEntry.setCreateDate(date);
			}
			else {
				webhookEntry.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!webhookEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				webhookEntry.setModifiedDate(date);
			}
			else {
				webhookEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(webhookEntry);
			}
			else {
				webhookEntry = (WebhookEntry)session.merge(webhookEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			WebhookEntryImpl.class, webhookEntryModelImpl, false, true);

		if (isNew) {
			webhookEntry.setNew(false);
		}

		webhookEntry.resetOriginalValues();

		return webhookEntry;
	}

	/**
	 * Returns the webhook entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the webhook entry
	 * @return the webhook entry
	 * @throws NoSuchWebhookEntryException if a webhook entry with the primary key could not be found
	 */
	@Override
	public WebhookEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchWebhookEntryException {

		WebhookEntry webhookEntry = fetchByPrimaryKey(primaryKey);

		if (webhookEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchWebhookEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return webhookEntry;
	}

	/**
	 * Returns the webhook entry with the primary key or throws a <code>NoSuchWebhookEntryException</code> if it could not be found.
	 *
	 * @param webhookEntryId the primary key of the webhook entry
	 * @return the webhook entry
	 * @throws NoSuchWebhookEntryException if a webhook entry with the primary key could not be found
	 */
	@Override
	public WebhookEntry findByPrimaryKey(long webhookEntryId)
		throws NoSuchWebhookEntryException {

		return findByPrimaryKey((Serializable)webhookEntryId);
	}

	/**
	 * Returns the webhook entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param webhookEntryId the primary key of the webhook entry
	 * @return the webhook entry, or <code>null</code> if a webhook entry with the primary key could not be found
	 */
	@Override
	public WebhookEntry fetchByPrimaryKey(long webhookEntryId) {
		return fetchByPrimaryKey((Serializable)webhookEntryId);
	}

	/**
	 * Returns all the webhook entries.
	 *
	 * @return the webhook entries
	 */
	@Override
	public List<WebhookEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the webhook entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of webhook entries
	 * @param end the upper bound of the range of webhook entries (not inclusive)
	 * @return the range of webhook entries
	 */
	@Override
	public List<WebhookEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the webhook entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of webhook entries
	 * @param end the upper bound of the range of webhook entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of webhook entries
	 */
	@Override
	public List<WebhookEntry> findAll(
		int start, int end, OrderByComparator<WebhookEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the webhook entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WebhookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of webhook entries
	 * @param end the upper bound of the range of webhook entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of webhook entries
	 */
	@Override
	public List<WebhookEntry> findAll(
		int start, int end, OrderByComparator<WebhookEntry> orderByComparator,
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

		List<WebhookEntry> list = null;

		if (useFinderCache) {
			list = (List<WebhookEntry>)finderCache.getResult(
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

				sql = sql.concat(WebhookEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<WebhookEntry>)QueryUtil.list(
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
	 * Removes all the webhook entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (WebhookEntry webhookEntry : findAll()) {
			remove(webhookEntry);
		}
	}

	/**
	 * Returns the number of webhook entries.
	 *
	 * @return the number of webhook entries
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
		return "webhookEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_WEBHOOKENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return WebhookEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the webhook entry persistence.
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
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(WebhookEntryImpl.class.getName());
	}

	@Override
	@Reference(
		target = WebhookPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = WebhookPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = WebhookPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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
		"SELECT webhookEntry FROM WebhookEntry webhookEntry";

	private static final String _SQL_SELECT_WEBHOOKENTRY_WHERE =
		"SELECT webhookEntry FROM WebhookEntry webhookEntry WHERE ";

	private static final String _SQL_COUNT_WEBHOOKENTRY =
		"SELECT COUNT(webhookEntry) FROM WebhookEntry webhookEntry";

	private static final String _SQL_COUNT_WEBHOOKENTRY_WHERE =
		"SELECT COUNT(webhookEntry) FROM WebhookEntry webhookEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "webhookEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No WebhookEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No WebhookEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		WebhookEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "active"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private WebhookEntryModelArgumentsResolver
		_webhookEntryModelArgumentsResolver;

}