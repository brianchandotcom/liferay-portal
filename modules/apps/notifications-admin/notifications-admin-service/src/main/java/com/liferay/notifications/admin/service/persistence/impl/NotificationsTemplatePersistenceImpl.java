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

package com.liferay.notifications.admin.service.persistence.impl;

import com.liferay.notifications.admin.exception.NoSuchNotificationsTemplateException;
import com.liferay.notifications.admin.model.NotificationsTemplate;
import com.liferay.notifications.admin.model.NotificationsTemplateTable;
import com.liferay.notifications.admin.model.impl.NotificationsTemplateImpl;
import com.liferay.notifications.admin.model.impl.NotificationsTemplateModelImpl;
import com.liferay.notifications.admin.service.persistence.NotificationsTemplatePersistence;
import com.liferay.notifications.admin.service.persistence.NotificationsTemplateUtil;
import com.liferay.notifications.admin.service.persistence.impl.constants.NotificationsAdminPersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
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
import com.liferay.portal.kernel.uuid.PortalUUID;

import java.io.Serializable;

import java.lang.reflect.Field;
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
 * The persistence implementation for the notifications template service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Gabriel Albuquerque
 * @generated
 */
@Component(
	service = {NotificationsTemplatePersistence.class, BasePersistence.class}
)
public class NotificationsTemplatePersistenceImpl
	extends BasePersistenceImpl<NotificationsTemplate>
	implements NotificationsTemplatePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>NotificationsTemplateUtil</code> to access the notifications template persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		NotificationsTemplateImpl.class.getName();

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
	 * Returns all the notifications templates where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching notifications templates
	 */
	@Override
	public List<NotificationsTemplate> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the notifications templates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of notifications templates
	 * @param end the upper bound of the range of notifications templates (not inclusive)
	 * @return the range of matching notifications templates
	 */
	@Override
	public List<NotificationsTemplate> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the notifications templates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of notifications templates
	 * @param end the upper bound of the range of notifications templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notifications templates
	 */
	@Override
	public List<NotificationsTemplate> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<NotificationsTemplate> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the notifications templates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of notifications templates
	 * @param end the upper bound of the range of notifications templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notifications templates
	 */
	@Override
	public List<NotificationsTemplate> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<NotificationsTemplate> orderByComparator,
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

		List<NotificationsTemplate> list = null;

		if (useFinderCache) {
			list = (List<NotificationsTemplate>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (NotificationsTemplate notificationsTemplate : list) {
					if (!uuid.equals(notificationsTemplate.getUuid())) {
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

			sb.append(_SQL_SELECT_NOTIFICATIONSTEMPLATE_WHERE);

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
				sb.append(NotificationsTemplateModelImpl.ORDER_BY_JPQL);
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

				list = (List<NotificationsTemplate>)QueryUtil.list(
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
	 * Returns the first notifications template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications template
	 * @throws NoSuchNotificationsTemplateException if a matching notifications template could not be found
	 */
	@Override
	public NotificationsTemplate findByUuid_First(
			String uuid,
			OrderByComparator<NotificationsTemplate> orderByComparator)
		throws NoSuchNotificationsTemplateException {

		NotificationsTemplate notificationsTemplate = fetchByUuid_First(
			uuid, orderByComparator);

		if (notificationsTemplate != null) {
			return notificationsTemplate;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchNotificationsTemplateException(sb.toString());
	}

	/**
	 * Returns the first notifications template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications template, or <code>null</code> if a matching notifications template could not be found
	 */
	@Override
	public NotificationsTemplate fetchByUuid_First(
		String uuid,
		OrderByComparator<NotificationsTemplate> orderByComparator) {

		List<NotificationsTemplate> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last notifications template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications template
	 * @throws NoSuchNotificationsTemplateException if a matching notifications template could not be found
	 */
	@Override
	public NotificationsTemplate findByUuid_Last(
			String uuid,
			OrderByComparator<NotificationsTemplate> orderByComparator)
		throws NoSuchNotificationsTemplateException {

		NotificationsTemplate notificationsTemplate = fetchByUuid_Last(
			uuid, orderByComparator);

		if (notificationsTemplate != null) {
			return notificationsTemplate;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchNotificationsTemplateException(sb.toString());
	}

	/**
	 * Returns the last notifications template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications template, or <code>null</code> if a matching notifications template could not be found
	 */
	@Override
	public NotificationsTemplate fetchByUuid_Last(
		String uuid,
		OrderByComparator<NotificationsTemplate> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<NotificationsTemplate> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the notifications templates before and after the current notifications template in the ordered set where uuid = &#63;.
	 *
	 * @param notificationsTemplateId the primary key of the current notifications template
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notifications template
	 * @throws NoSuchNotificationsTemplateException if a notifications template with the primary key could not be found
	 */
	@Override
	public NotificationsTemplate[] findByUuid_PrevAndNext(
			long notificationsTemplateId, String uuid,
			OrderByComparator<NotificationsTemplate> orderByComparator)
		throws NoSuchNotificationsTemplateException {

		uuid = Objects.toString(uuid, "");

		NotificationsTemplate notificationsTemplate = findByPrimaryKey(
			notificationsTemplateId);

		Session session = null;

		try {
			session = openSession();

			NotificationsTemplate[] array = new NotificationsTemplateImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, notificationsTemplate, uuid, orderByComparator, true);

			array[1] = notificationsTemplate;

			array[2] = getByUuid_PrevAndNext(
				session, notificationsTemplate, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected NotificationsTemplate getByUuid_PrevAndNext(
		Session session, NotificationsTemplate notificationsTemplate,
		String uuid, OrderByComparator<NotificationsTemplate> orderByComparator,
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

		sb.append(_SQL_SELECT_NOTIFICATIONSTEMPLATE_WHERE);

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
			sb.append(NotificationsTemplateModelImpl.ORDER_BY_JPQL);
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
						notificationsTemplate)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<NotificationsTemplate> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the notifications templates that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching notifications templates that the user has permission to view
	 */
	@Override
	public List<NotificationsTemplate> filterFindByUuid(String uuid) {
		return filterFindByUuid(
			uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the notifications templates that the user has permission to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of notifications templates
	 * @param end the upper bound of the range of notifications templates (not inclusive)
	 * @return the range of matching notifications templates that the user has permission to view
	 */
	@Override
	public List<NotificationsTemplate> filterFindByUuid(
		String uuid, int start, int end) {

		return filterFindByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the notifications templates that the user has permissions to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of notifications templates
	 * @param end the upper bound of the range of notifications templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notifications templates that the user has permission to view
	 */
	@Override
	public List<NotificationsTemplate> filterFindByUuid(
		String uuid, int start, int end,
		OrderByComparator<NotificationsTemplate> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByUuid(uuid, start, end, orderByComparator);
		}

		uuid = Objects.toString(uuid, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				3 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_NOTIFICATIONSTEMPLATE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_NOTIFICATIONSTEMPLATE_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2_SQL);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_NOTIFICATIONSTEMPLATE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(NotificationsTemplateModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(NotificationsTemplateModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), NotificationsTemplate.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, NotificationsTemplateImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, NotificationsTemplateImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindUuid) {
				queryPos.add(uuid);
			}

			return (List<NotificationsTemplate>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the notifications templates before and after the current notifications template in the ordered set of notifications templates that the user has permission to view where uuid = &#63;.
	 *
	 * @param notificationsTemplateId the primary key of the current notifications template
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notifications template
	 * @throws NoSuchNotificationsTemplateException if a notifications template with the primary key could not be found
	 */
	@Override
	public NotificationsTemplate[] filterFindByUuid_PrevAndNext(
			long notificationsTemplateId, String uuid,
			OrderByComparator<NotificationsTemplate> orderByComparator)
		throws NoSuchNotificationsTemplateException {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByUuid_PrevAndNext(
				notificationsTemplateId, uuid, orderByComparator);
		}

		uuid = Objects.toString(uuid, "");

		NotificationsTemplate notificationsTemplate = findByPrimaryKey(
			notificationsTemplateId);

		Session session = null;

		try {
			session = openSession();

			NotificationsTemplate[] array = new NotificationsTemplateImpl[3];

			array[0] = filterGetByUuid_PrevAndNext(
				session, notificationsTemplate, uuid, orderByComparator, true);

			array[1] = notificationsTemplate;

			array[2] = filterGetByUuid_PrevAndNext(
				session, notificationsTemplate, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected NotificationsTemplate filterGetByUuid_PrevAndNext(
		Session session, NotificationsTemplate notificationsTemplate,
		String uuid, OrderByComparator<NotificationsTemplate> orderByComparator,
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

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_NOTIFICATIONSTEMPLATE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_NOTIFICATIONSTEMPLATE_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2_SQL);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_NOTIFICATIONSTEMPLATE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(NotificationsTemplateModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(NotificationsTemplateModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), NotificationsTemplate.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, NotificationsTemplateImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, NotificationsTemplateImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						notificationsTemplate)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<NotificationsTemplate> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the notifications templates where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (NotificationsTemplate notificationsTemplate :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(notificationsTemplate);
		}
	}

	/**
	 * Returns the number of notifications templates where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching notifications templates
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_NOTIFICATIONSTEMPLATE_WHERE);

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

	/**
	 * Returns the number of notifications templates that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching notifications templates that the user has permission to view
	 */
	@Override
	public int filterCountByUuid(String uuid) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByUuid(uuid);
		}

		uuid = Objects.toString(uuid, "");

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_NOTIFICATIONSTEMPLATE_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2_SQL);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), NotificationsTemplate.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindUuid) {
				queryPos.add(uuid);
			}

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"notificationsTemplate.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(notificationsTemplate.uuid IS NULL OR notificationsTemplate.uuid = '')";

	private static final String _FINDER_COLUMN_UUID_UUID_2_SQL =
		"notificationsTemplate.uuid_ = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3_SQL =
		"(notificationsTemplate.uuid_ IS NULL OR notificationsTemplate.uuid_ = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the notifications templates where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching notifications templates
	 */
	@Override
	public List<NotificationsTemplate> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the notifications templates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of notifications templates
	 * @param end the upper bound of the range of notifications templates (not inclusive)
	 * @return the range of matching notifications templates
	 */
	@Override
	public List<NotificationsTemplate> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the notifications templates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of notifications templates
	 * @param end the upper bound of the range of notifications templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notifications templates
	 */
	@Override
	public List<NotificationsTemplate> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<NotificationsTemplate> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the notifications templates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of notifications templates
	 * @param end the upper bound of the range of notifications templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notifications templates
	 */
	@Override
	public List<NotificationsTemplate> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<NotificationsTemplate> orderByComparator,
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

		List<NotificationsTemplate> list = null;

		if (useFinderCache) {
			list = (List<NotificationsTemplate>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (NotificationsTemplate notificationsTemplate : list) {
					if (!uuid.equals(notificationsTemplate.getUuid()) ||
						(companyId != notificationsTemplate.getCompanyId())) {

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

			sb.append(_SQL_SELECT_NOTIFICATIONSTEMPLATE_WHERE);

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
				sb.append(NotificationsTemplateModelImpl.ORDER_BY_JPQL);
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

				list = (List<NotificationsTemplate>)QueryUtil.list(
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
	 * Returns the first notifications template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications template
	 * @throws NoSuchNotificationsTemplateException if a matching notifications template could not be found
	 */
	@Override
	public NotificationsTemplate findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<NotificationsTemplate> orderByComparator)
		throws NoSuchNotificationsTemplateException {

		NotificationsTemplate notificationsTemplate = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (notificationsTemplate != null) {
			return notificationsTemplate;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchNotificationsTemplateException(sb.toString());
	}

	/**
	 * Returns the first notifications template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notifications template, or <code>null</code> if a matching notifications template could not be found
	 */
	@Override
	public NotificationsTemplate fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<NotificationsTemplate> orderByComparator) {

		List<NotificationsTemplate> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last notifications template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications template
	 * @throws NoSuchNotificationsTemplateException if a matching notifications template could not be found
	 */
	@Override
	public NotificationsTemplate findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<NotificationsTemplate> orderByComparator)
		throws NoSuchNotificationsTemplateException {

		NotificationsTemplate notificationsTemplate = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (notificationsTemplate != null) {
			return notificationsTemplate;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchNotificationsTemplateException(sb.toString());
	}

	/**
	 * Returns the last notifications template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notifications template, or <code>null</code> if a matching notifications template could not be found
	 */
	@Override
	public NotificationsTemplate fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<NotificationsTemplate> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<NotificationsTemplate> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the notifications templates before and after the current notifications template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param notificationsTemplateId the primary key of the current notifications template
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notifications template
	 * @throws NoSuchNotificationsTemplateException if a notifications template with the primary key could not be found
	 */
	@Override
	public NotificationsTemplate[] findByUuid_C_PrevAndNext(
			long notificationsTemplateId, String uuid, long companyId,
			OrderByComparator<NotificationsTemplate> orderByComparator)
		throws NoSuchNotificationsTemplateException {

		uuid = Objects.toString(uuid, "");

		NotificationsTemplate notificationsTemplate = findByPrimaryKey(
			notificationsTemplateId);

		Session session = null;

		try {
			session = openSession();

			NotificationsTemplate[] array = new NotificationsTemplateImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, notificationsTemplate, uuid, companyId,
				orderByComparator, true);

			array[1] = notificationsTemplate;

			array[2] = getByUuid_C_PrevAndNext(
				session, notificationsTemplate, uuid, companyId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected NotificationsTemplate getByUuid_C_PrevAndNext(
		Session session, NotificationsTemplate notificationsTemplate,
		String uuid, long companyId,
		OrderByComparator<NotificationsTemplate> orderByComparator,
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

		sb.append(_SQL_SELECT_NOTIFICATIONSTEMPLATE_WHERE);

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
			sb.append(NotificationsTemplateModelImpl.ORDER_BY_JPQL);
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
						notificationsTemplate)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<NotificationsTemplate> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the notifications templates that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching notifications templates that the user has permission to view
	 */
	@Override
	public List<NotificationsTemplate> filterFindByUuid_C(
		String uuid, long companyId) {

		return filterFindByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the notifications templates that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of notifications templates
	 * @param end the upper bound of the range of notifications templates (not inclusive)
	 * @return the range of matching notifications templates that the user has permission to view
	 */
	@Override
	public List<NotificationsTemplate> filterFindByUuid_C(
		String uuid, long companyId, int start, int end) {

		return filterFindByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the notifications templates that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of notifications templates
	 * @param end the upper bound of the range of notifications templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notifications templates that the user has permission to view
	 */
	@Override
	public List<NotificationsTemplate> filterFindByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<NotificationsTemplate> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByUuid_C(uuid, companyId, start, end, orderByComparator);
		}

		uuid = Objects.toString(uuid, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_NOTIFICATIONSTEMPLATE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_NOTIFICATIONSTEMPLATE_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_UUID_2_SQL);
		}

		sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_NOTIFICATIONSTEMPLATE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(NotificationsTemplateModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(NotificationsTemplateModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), NotificationsTemplate.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, NotificationsTemplateImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, NotificationsTemplateImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindUuid) {
				queryPos.add(uuid);
			}

			queryPos.add(companyId);

			return (List<NotificationsTemplate>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the notifications templates before and after the current notifications template in the ordered set of notifications templates that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param notificationsTemplateId the primary key of the current notifications template
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notifications template
	 * @throws NoSuchNotificationsTemplateException if a notifications template with the primary key could not be found
	 */
	@Override
	public NotificationsTemplate[] filterFindByUuid_C_PrevAndNext(
			long notificationsTemplateId, String uuid, long companyId,
			OrderByComparator<NotificationsTemplate> orderByComparator)
		throws NoSuchNotificationsTemplateException {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByUuid_C_PrevAndNext(
				notificationsTemplateId, uuid, companyId, orderByComparator);
		}

		uuid = Objects.toString(uuid, "");

		NotificationsTemplate notificationsTemplate = findByPrimaryKey(
			notificationsTemplateId);

		Session session = null;

		try {
			session = openSession();

			NotificationsTemplate[] array = new NotificationsTemplateImpl[3];

			array[0] = filterGetByUuid_C_PrevAndNext(
				session, notificationsTemplate, uuid, companyId,
				orderByComparator, true);

			array[1] = notificationsTemplate;

			array[2] = filterGetByUuid_C_PrevAndNext(
				session, notificationsTemplate, uuid, companyId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected NotificationsTemplate filterGetByUuid_C_PrevAndNext(
		Session session, NotificationsTemplate notificationsTemplate,
		String uuid, long companyId,
		OrderByComparator<NotificationsTemplate> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_NOTIFICATIONSTEMPLATE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_NOTIFICATIONSTEMPLATE_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_UUID_2_SQL);
		}

		sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_NOTIFICATIONSTEMPLATE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(NotificationsTemplateModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(NotificationsTemplateModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), NotificationsTemplate.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, NotificationsTemplateImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, NotificationsTemplateImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						notificationsTemplate)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<NotificationsTemplate> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the notifications templates where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (NotificationsTemplate notificationsTemplate :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(notificationsTemplate);
		}
	}

	/**
	 * Returns the number of notifications templates where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching notifications templates
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_NOTIFICATIONSTEMPLATE_WHERE);

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

	/**
	 * Returns the number of notifications templates that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching notifications templates that the user has permission to view
	 */
	@Override
	public int filterCountByUuid_C(String uuid, long companyId) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByUuid_C(uuid, companyId);
		}

		uuid = Objects.toString(uuid, "");

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_NOTIFICATIONSTEMPLATE_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_UUID_2_SQL);
		}

		sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), NotificationsTemplate.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindUuid) {
				queryPos.add(uuid);
			}

			queryPos.add(companyId);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"notificationsTemplate.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(notificationsTemplate.uuid IS NULL OR notificationsTemplate.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_2_SQL =
		"notificationsTemplate.uuid_ = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3_SQL =
		"(notificationsTemplate.uuid_ IS NULL OR notificationsTemplate.uuid_ = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"notificationsTemplate.companyId = ?";

	public NotificationsTemplatePersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(NotificationsTemplate.class);

		setModelImplClass(NotificationsTemplateImpl.class);
		setModelPKClass(long.class);

		setTable(NotificationsTemplateTable.INSTANCE);
	}

	/**
	 * Caches the notifications template in the entity cache if it is enabled.
	 *
	 * @param notificationsTemplate the notifications template
	 */
	@Override
	public void cacheResult(NotificationsTemplate notificationsTemplate) {
		entityCache.putResult(
			NotificationsTemplateImpl.class,
			notificationsTemplate.getPrimaryKey(), notificationsTemplate);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the notifications templates in the entity cache if it is enabled.
	 *
	 * @param notificationsTemplates the notifications templates
	 */
	@Override
	public void cacheResult(
		List<NotificationsTemplate> notificationsTemplates) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (notificationsTemplates.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (NotificationsTemplate notificationsTemplate :
				notificationsTemplates) {

			if (entityCache.getResult(
					NotificationsTemplateImpl.class,
					notificationsTemplate.getPrimaryKey()) == null) {

				cacheResult(notificationsTemplate);
			}
		}
	}

	/**
	 * Clears the cache for all notifications templates.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(NotificationsTemplateImpl.class);

		finderCache.clearCache(NotificationsTemplateImpl.class);
	}

	/**
	 * Clears the cache for the notifications template.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(NotificationsTemplate notificationsTemplate) {
		entityCache.removeResult(
			NotificationsTemplateImpl.class, notificationsTemplate);
	}

	@Override
	public void clearCache(List<NotificationsTemplate> notificationsTemplates) {
		for (NotificationsTemplate notificationsTemplate :
				notificationsTemplates) {

			entityCache.removeResult(
				NotificationsTemplateImpl.class, notificationsTemplate);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(NotificationsTemplateImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				NotificationsTemplateImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new notifications template with the primary key. Does not add the notifications template to the database.
	 *
	 * @param notificationsTemplateId the primary key for the new notifications template
	 * @return the new notifications template
	 */
	@Override
	public NotificationsTemplate create(long notificationsTemplateId) {
		NotificationsTemplate notificationsTemplate =
			new NotificationsTemplateImpl();

		notificationsTemplate.setNew(true);
		notificationsTemplate.setPrimaryKey(notificationsTemplateId);

		String uuid = _portalUUID.generate();

		notificationsTemplate.setUuid(uuid);

		notificationsTemplate.setCompanyId(CompanyThreadLocal.getCompanyId());

		return notificationsTemplate;
	}

	/**
	 * Removes the notifications template with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param notificationsTemplateId the primary key of the notifications template
	 * @return the notifications template that was removed
	 * @throws NoSuchNotificationsTemplateException if a notifications template with the primary key could not be found
	 */
	@Override
	public NotificationsTemplate remove(long notificationsTemplateId)
		throws NoSuchNotificationsTemplateException {

		return remove((Serializable)notificationsTemplateId);
	}

	/**
	 * Removes the notifications template with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the notifications template
	 * @return the notifications template that was removed
	 * @throws NoSuchNotificationsTemplateException if a notifications template with the primary key could not be found
	 */
	@Override
	public NotificationsTemplate remove(Serializable primaryKey)
		throws NoSuchNotificationsTemplateException {

		Session session = null;

		try {
			session = openSession();

			NotificationsTemplate notificationsTemplate =
				(NotificationsTemplate)session.get(
					NotificationsTemplateImpl.class, primaryKey);

			if (notificationsTemplate == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchNotificationsTemplateException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(notificationsTemplate);
		}
		catch (NoSuchNotificationsTemplateException noSuchEntityException) {
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
	protected NotificationsTemplate removeImpl(
		NotificationsTemplate notificationsTemplate) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(notificationsTemplate)) {
				notificationsTemplate = (NotificationsTemplate)session.get(
					NotificationsTemplateImpl.class,
					notificationsTemplate.getPrimaryKeyObj());
			}

			if (notificationsTemplate != null) {
				session.delete(notificationsTemplate);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (notificationsTemplate != null) {
			clearCache(notificationsTemplate);
		}

		return notificationsTemplate;
	}

	@Override
	public NotificationsTemplate updateImpl(
		NotificationsTemplate notificationsTemplate) {

		boolean isNew = notificationsTemplate.isNew();

		if (!(notificationsTemplate instanceof
				NotificationsTemplateModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(notificationsTemplate.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					notificationsTemplate);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in notificationsTemplate proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom NotificationsTemplate implementation " +
					notificationsTemplate.getClass());
		}

		NotificationsTemplateModelImpl notificationsTemplateModelImpl =
			(NotificationsTemplateModelImpl)notificationsTemplate;

		if (Validator.isNull(notificationsTemplate.getUuid())) {
			String uuid = _portalUUID.generate();

			notificationsTemplate.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (notificationsTemplate.getCreateDate() == null)) {
			if (serviceContext == null) {
				notificationsTemplate.setCreateDate(date);
			}
			else {
				notificationsTemplate.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!notificationsTemplateModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				notificationsTemplate.setModifiedDate(date);
			}
			else {
				notificationsTemplate.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(notificationsTemplate);
			}
			else {
				notificationsTemplate = (NotificationsTemplate)session.merge(
					notificationsTemplate);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			NotificationsTemplateImpl.class, notificationsTemplateModelImpl,
			false, true);

		if (isNew) {
			notificationsTemplate.setNew(false);
		}

		notificationsTemplate.resetOriginalValues();

		return notificationsTemplate;
	}

	/**
	 * Returns the notifications template with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the notifications template
	 * @return the notifications template
	 * @throws NoSuchNotificationsTemplateException if a notifications template with the primary key could not be found
	 */
	@Override
	public NotificationsTemplate findByPrimaryKey(Serializable primaryKey)
		throws NoSuchNotificationsTemplateException {

		NotificationsTemplate notificationsTemplate = fetchByPrimaryKey(
			primaryKey);

		if (notificationsTemplate == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchNotificationsTemplateException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return notificationsTemplate;
	}

	/**
	 * Returns the notifications template with the primary key or throws a <code>NoSuchNotificationsTemplateException</code> if it could not be found.
	 *
	 * @param notificationsTemplateId the primary key of the notifications template
	 * @return the notifications template
	 * @throws NoSuchNotificationsTemplateException if a notifications template with the primary key could not be found
	 */
	@Override
	public NotificationsTemplate findByPrimaryKey(long notificationsTemplateId)
		throws NoSuchNotificationsTemplateException {

		return findByPrimaryKey((Serializable)notificationsTemplateId);
	}

	/**
	 * Returns the notifications template with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param notificationsTemplateId the primary key of the notifications template
	 * @return the notifications template, or <code>null</code> if a notifications template with the primary key could not be found
	 */
	@Override
	public NotificationsTemplate fetchByPrimaryKey(
		long notificationsTemplateId) {

		return fetchByPrimaryKey((Serializable)notificationsTemplateId);
	}

	/**
	 * Returns all the notifications templates.
	 *
	 * @return the notifications templates
	 */
	@Override
	public List<NotificationsTemplate> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the notifications templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notifications templates
	 * @param end the upper bound of the range of notifications templates (not inclusive)
	 * @return the range of notifications templates
	 */
	@Override
	public List<NotificationsTemplate> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the notifications templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notifications templates
	 * @param end the upper bound of the range of notifications templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of notifications templates
	 */
	@Override
	public List<NotificationsTemplate> findAll(
		int start, int end,
		OrderByComparator<NotificationsTemplate> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the notifications templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationsTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notifications templates
	 * @param end the upper bound of the range of notifications templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of notifications templates
	 */
	@Override
	public List<NotificationsTemplate> findAll(
		int start, int end,
		OrderByComparator<NotificationsTemplate> orderByComparator,
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

		List<NotificationsTemplate> list = null;

		if (useFinderCache) {
			list = (List<NotificationsTemplate>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_NOTIFICATIONSTEMPLATE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_NOTIFICATIONSTEMPLATE;

				sql = sql.concat(NotificationsTemplateModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<NotificationsTemplate>)QueryUtil.list(
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
	 * Removes all the notifications templates from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (NotificationsTemplate notificationsTemplate : findAll()) {
			remove(notificationsTemplate);
		}
	}

	/**
	 * Returns the number of notifications templates.
	 *
	 * @return the number of notifications templates
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_NOTIFICATIONSTEMPLATE);

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
		return "notificationsTemplateId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_NOTIFICATIONSTEMPLATE;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return NotificationsTemplateModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the notifications template persistence.
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

		_setNotificationsTemplateUtilPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		_setNotificationsTemplateUtilPersistence(null);

		entityCache.removeCache(NotificationsTemplateImpl.class.getName());
	}

	private void _setNotificationsTemplateUtilPersistence(
		NotificationsTemplatePersistence notificationsTemplatePersistence) {

		try {
			Field field = NotificationsTemplateUtil.class.getDeclaredField(
				"_persistence");

			field.setAccessible(true);

			field.set(null, notificationsTemplatePersistence);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	@Override
	@Reference(
		target = NotificationsAdminPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = NotificationsAdminPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = NotificationsAdminPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_NOTIFICATIONSTEMPLATE =
		"SELECT notificationsTemplate FROM NotificationsTemplate notificationsTemplate";

	private static final String _SQL_SELECT_NOTIFICATIONSTEMPLATE_WHERE =
		"SELECT notificationsTemplate FROM NotificationsTemplate notificationsTemplate WHERE ";

	private static final String _SQL_COUNT_NOTIFICATIONSTEMPLATE =
		"SELECT COUNT(notificationsTemplate) FROM NotificationsTemplate notificationsTemplate";

	private static final String _SQL_COUNT_NOTIFICATIONSTEMPLATE_WHERE =
		"SELECT COUNT(notificationsTemplate) FROM NotificationsTemplate notificationsTemplate WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"notificationsTemplate.notificationsTemplateId";

	private static final String _FILTER_SQL_SELECT_NOTIFICATIONSTEMPLATE_WHERE =
		"SELECT DISTINCT {notificationsTemplate.*} FROM NotificationsTemplate notificationsTemplate WHERE ";

	private static final String
		_FILTER_SQL_SELECT_NOTIFICATIONSTEMPLATE_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {NotificationsTemplate.*} FROM (SELECT DISTINCT notificationsTemplate.notificationsTemplateId FROM NotificationsTemplate notificationsTemplate WHERE ";

	private static final String
		_FILTER_SQL_SELECT_NOTIFICATIONSTEMPLATE_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN NotificationsTemplate ON TEMP_TABLE.notificationsTemplateId = NotificationsTemplate.notificationsTemplateId";

	private static final String _FILTER_SQL_COUNT_NOTIFICATIONSTEMPLATE_WHERE =
		"SELECT COUNT(DISTINCT notificationsTemplate.notificationsTemplateId) AS COUNT_VALUE FROM NotificationsTemplate notificationsTemplate WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "notificationsTemplate";

	private static final String _FILTER_ENTITY_TABLE = "NotificationsTemplate";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"notificationsTemplate.";

	private static final String _ORDER_BY_ENTITY_TABLE =
		"NotificationsTemplate.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No NotificationsTemplate exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No NotificationsTemplate exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		NotificationsTemplatePersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private PortalUUID _portalUUID;

	@Reference
	private NotificationsTemplateModelArgumentsResolver
		_notificationsTemplateModelArgumentsResolver;

}